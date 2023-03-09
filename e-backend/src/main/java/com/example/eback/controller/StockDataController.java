package com.example.eback.controller;

import com.example.eback.Listener.StockDataPublisher;
import com.example.eback.dao.StockDataDAO;
import com.example.eback.entity.Stock;
import com.example.eback.entity.StockData;
import com.example.eback.redis.RedisService;
import com.example.eback.result.Result;
import com.example.eback.result.ResultFactory;
import com.example.eback.service.StockDataService;
import com.example.eback.service.StockService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import com.example.eback.WebSocket.WebSocket;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@ApiOperation(value = "股票数据相关接口")
@RestController
public class StockDataController {
    @Autowired
    StockDataService stockDataService;
    @Autowired
    RedisService redisService;

    @Autowired
    StockService stockService;

    @Autowired
    private StockDataPublisher stockDataPublisher;

    @ApiOperation(value = "获取某只股票的全部相关数据,查询mysql里的数据以及添加redis里的，需要排序",notes = "只需要填写int sid")
    @GetMapping("/api/stock_data/get")
    public Result getBysid(@RequestBody StockData stockData){
        List<StockData> stockDatas=stockDataService.findById(stockData.getSid());
        stockDatas.add((StockData)redisService.get(String.valueOf(stockData.getSid())));
        return ResultFactory.buildSuccessResult(stockDatas);
    }

    @ApiOperation(value = "导出某只股票的全部相关数据",notes = "需要该股票的int sid")
    @GetMapping("/api/stock_data/export")
    public void exportCSV(HttpServletResponse response, @RequestBody StockData stockData)throws Exception{
        String filename = stockData.getSid() + ".csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<StockData> writer = new StatefulBeanToCsvBuilder<StockData>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        //获取数据
        List<StockData> stockDatas=stockDataService.findById(stockData.getSid());
        stockDatas.add((StockData)redisService.get(String.valueOf(stockData.getSid())));

        writer.write(stockDatas);

    }


    @ApiOperation(value = "上传股票历史详情信息",notes = "")
    @PostMapping("/api/stock_data/upload")
    public Result uploadCsv(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        }else {
            // parse CSV file to create a list of `User` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                CSVReader csvReader = new CSVReader(reader);
                // create csv bean reader
                CsvToBean<StockData> csvToBean = new CsvToBeanBuilder<StockData>(csvReader)
                        .withType(StockData.class)
                        .withSeparator(',')
                        .withQuoteChar('"')
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of stocks
                List<StockData> stockDataList = csvToBean.parse();
                System.out.print(stockDataList);
                stockDataService.saveDataList(stockDataList);
                // TODO: save stocks in DB?

            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }
        return ResultFactory.buildFailResult("上传成功");
    }



    @ApiOperation(value = "更新某只股票的信息",notes = "全部填写")
    @PostMapping("/api/stock_data/add")
    public Result add(@RequestBody StockData stockData){
        String sid=stockData.getSid();
        if (!stockService.exsistById(sid)){
            return ResultFactory.buildFailResult("无此股票，请先登记");
        }
        Object in_redis=redisService.get(String.valueOf(sid));
        StockData last_data;
        if (in_redis !=null) {
            last_data = (StockData) in_redis;
            /**
             * 对最高、最低点进行更新
             */
            if (last_data.getMax_high() < stockData.getValue()) {
                stockData.setMax_high(stockData.getValue());
                Stock stock = stockService.findById(sid);
                stock.setMax_high(stockData.getValue());
            } else stockData.setMax_high(last_data.getMax_high());

            if (last_data.getMin_low() > stockData.getValue()) {
                stockData.setMin_low(stockData.getValue());
                Stock stock = stockService.findById(sid);
                stock.setMin_low(stockData.getValue());
            } else stockData.setMin_low(last_data.getMin_low());
        }
        else {
            /**
             * 更新数据库里的数据
             */
            redisService.set(String.valueOf(sid),stockData);
            stockDataService.saveData(stockData);
            stockDataPublisher.publishStockDataEvent(stockData);
            return ResultFactory.buildSuccessResult("成功");
        }

        /**
         * 更新数据库里的数据
         */
        stockDataService.saveData(stockData);
        redisService.set(String.valueOf(sid),stockData);
        /**
         同步信息，使用异步的事件驱动
         */
        stockDataPublisher.publishStockDataEvent( stockData);

        return ResultFactory.buildSuccessResult("成功");
    }


}
