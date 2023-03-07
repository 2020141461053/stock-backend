package com.example.eback.service;

import com.example.eback.dao.StockDAO;
import com.example.eback.dao.StockDataDAO;
import com.example.eback.entity.StockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockDataService {
    @Autowired
    StockDataDAO stockDataDAO;

    public  List<StockData> findById(String sid){
        return stockDataDAO.findBySid(sid);
    }

    public  void saveData(StockData stockData){
        stockDataDAO.save(stockData);
    }

}
