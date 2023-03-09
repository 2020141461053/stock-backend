package com.example.eback.scheduled;

import com.example.eback.dao.StockDAO;
import com.example.eback.entity.Stock;
import com.example.eback.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

public class UpdataTask {

    @Autowired
    StockDAO stockDAO;
    @Autowired
    StockDataService stockDataService;

    @Scheduled(cron="0 0 0  0/7 * ? ")   //每周执行一次
    public void Save(){
        List<Stock> stockList=stockDAO.findAll();
        Date date=new Date();
        for(Stock stock:stockList){
            String sid=stock.getId();
            stockDataService.StoreT1Data(sid,date,7);
        }
    }
}
