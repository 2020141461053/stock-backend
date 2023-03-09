package com.example.eback.service;

import com.example.eback.constans.TnDataCode;
import com.example.eback.dao.StockDataDAO;
import com.example.eback.dao.TnDataDAO;
import com.example.eback.entity.StockData;
import com.example.eback.entity.TnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StockDataService {
    @Autowired
    StockDataDAO stockDataDAO;

    @Autowired
    TnDataDAO tnDataDAO;

    public List<StockData> findById(String sid) {
        return stockDataDAO.findBySid(sid);
    }

    public void saveData(StockData stockData) {
        stockDataDAO.save(stockData);
    }

    private  Date getDate(Date end,int day){
        Calendar cal = Calendar.getInstance();
        //设置时间
        cal.setTime(end);
        cal.add(Calendar.DATE, -day);
        return cal.getTime();

    }
    public TnDataCode StoreTnData(String id, Date point, int day) {

        Date start = getDate(point,day);

        List<StockData> stockDataList = stockDataDAO.findByDate(id, start, point);
        if (tnDataDAO.existsByStockCodeAndStartAndEnd(id, start, point)){
            return TnDataCode.IS_EXISTS;
        }

        TnData tnData = new TnData();

        float high = 0;

        float value = 0;//平均值

        int turnover = 0;//总交易数

        float low = 0;//最低

        float valueChange = 0;//
        for (StockData stockData : stockDataList) {
            if (value == 0) {
                high = stockData.getHigh();
                low = stockData.getLow();
                value = stockData.getValue();
                turnover = stockData.getTurnover();
            } else {
                if (high < stockData.getHigh()) {
                    high = stockData.getHigh();
                }
                if (low > stockData.getLow()) {
                    low = stockData.getLow();
                }
                value += stockData.getValue();
                turnover += stockData.getTurnover();
            }
        }
        value /= day;
        valueChange = stockDataList.get(0).getValue() - stockDataList.get(stockDataList.size() - 1).getValue();
        tnData.setHigh(high);
        tnData.setLow(low);
        tnData.setEnd(point);
        tnData.setStart(start);
        tnData.setStockCode(id);
        tnData.setTurnover(turnover);
        tnData.setValue(value);
        tnData.setValueChange(valueChange);
        tnDataDAO.save(tnData);
        return TnDataCode.SAVE_SUCCESS;
    }


    public void saveDataList(List<StockData> stockDataList) {
        stockDataDAO.saveAll(stockDataList);
    }

    public TnDataCode existsByStockCodeAndStartAndEnd(String StockCode, Date start, Date end){
        if (tnDataDAO.existsByStockCodeAndStartAndEnd(StockCode, start, end)){
            return TnDataCode.IS_EXISTS;
        }
        else return TnDataCode.NOT_EXISTS;
    }

    public  TnData getTnData(String StockCode, Date start, Date end){

        return tnDataDAO.findByStockCodeAndStartAndEnd(StockCode, start, end);
    }

}
