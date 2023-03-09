package com.example.eback.controller;

import com.example.eback.entity.Stock;
import com.example.eback.entity.StockData;
import com.example.eback.entity.User;
import com.example.eback.redis.RedisService;
import com.example.eback.result.Result;
import com.example.eback.result.ResultFactory;
import com.example.eback.service.StockDataService;
import com.example.eback.service.StockService;
import com.example.eback.service.UserService;
import com.example.eback.util.MyPage;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.lang.ProcessBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApiOperation(value = "股票信息相关接口")
@RestController
public class StockController {
    @Autowired
    UserService userService;

    @Autowired
    StockDataService stockDataService;

    @Autowired
    StockService stockService;

    @Autowired
    RedisService redisService;

    @ApiOperation(value = "获取股票列表",notes = "")
    @PostMapping("/api/stock")
    public Result getAll(@RequestParam("pages") int pages,
                         @RequestParam("size") int size,
                         @RequestParam("sort") String sort) {

        MyPage<Stock> stockMypage=stockService.findAll(pages,size,sort);
        List<Stock> stockList=stockMypage.getContent();
        StockData stockInRedis;
        List<Stock> returnList=new ArrayList<>();//返回的列表数据
        for(Stock stock : stockList){
           stockInRedis=(StockData)redisService.get(stock.getId());//查询Redis里的最新数据
           if (stockInRedis ==null){
               stock.setTurnover(0);
               stock.setValue(0);
               stock.setHigh(0);
               stock.setLow(0);
           }
           else {
               stock.setTurnover(stockInRedis.getTurnover());
               stock.setValue(stockInRedis.getValue());
               stock.setHigh(stockInRedis.getHigh());
               stock.setLow(stockInRedis.getLow());
               }
            returnList.add(stock);//添加到列表里
        }
        stockMypage.setContent(returnList);//替换列表
        return ResultFactory.buildSuccessResult(stockMypage);
    }

    @ApiOperation(value = "根据股票代码查询",notes = "需要股票代码")
    @PostMapping("/api/stock/get_code")
    public List<Stock> getByCode(@RequestParam("stockCode") String scode){
        List<Stock> stocks = stockService.findByIdLike("%"+scode+"%");
        return stocks;
    }

    @ApiOperation(value = "根据股票名称查询",notes = "需要股票名称")
    @PostMapping("/api/stock/get_name")
    public List<Stock> getByName(@RequestParam("stockName") String sname) {
        List<Stock> stocks = stockService.findByNameLike("%"+sname+"%");
        return stocks;
    }

    @ApiOperation(value = "批量上传新的股票",notes = "")
    @PostMapping("/api/stock/upload")
    public Result uploadCsv(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        }else {
            // parse CSV file to create a list of `User` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                CSVReader csvReader = new CSVReader(reader);
                // create csv bean reader
                CsvToBean<Stock> csvToBean = new CsvToBeanBuilder<Stock>(csvReader)
                        .withType(Stock.class)
                        .withSeparator(',')
                        .withQuoteChar('"')
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of stocks
                List<Stock> stocks = csvToBean.parse();

                // TODO: save stocks in DB?

                stockService.saveStocks(stocks);

            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }
        return ResultFactory.buildFailResult("上传成功");
    }

    public User getUser (){
        User user= userService.findByUsername(SecurityUtils.getSubject().getPrincipal().toString());
        return   user;
    }

}
