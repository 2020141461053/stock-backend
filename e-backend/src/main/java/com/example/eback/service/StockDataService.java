package com.example.eback.service;

import com.example.eback.dao.StockDAO;
import com.example.eback.dao.StockDataDAO;
import com.example.eback.dao.TnDataDAO;
import com.example.eback.entity.StockData;
import com.example.eback.entity.TnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StockDataService {
    @Autowired
    StockDataDAO stockDataDAO;

    @Autowired
    TnDataDAO tnDataDAO;

    public  List<StockData> findById(String sid){
        return stockDataDAO.findBySid(sid);
    }

    public  void saveData(StockData stockData){
        stockDataDAO.save(stockData);
    }




    public  void  StoreT1Data(String id,Date point,int day){
        Calendar cal = Calendar.getInstance();
        //设置时间
        cal.setTime(point);
        cal.add(Calendar.DATE, -day);
        Date start=cal.getTime();
        List<StockData>stockDataList =stockDataDAO.findByDate(id,start,point);
        TnData tnData=new TnData();

        float high=0;

        float value=0;//平均值

        int turnover=0;//总交易数

        float low=0;//最低

        float valueChange=0;//
        for(StockData stockData:stockDataList){
            if(value==0){
                high=stockData.getHigh();
                low=stockData.getLow();
                value=stockData.getValue();
                turnover=stockData.getTurnover();
            }
            else{
                if (high<stockData.getHigh()){
                    high=stockData.getHigh();
                }if(low>stockData.getLow()){
                    low=stockData.getLow();
                }
                value+=stockData.getValue();
                turnover+=stockData.getTurnover();
            }
        }
        value/=day;
        valueChange=stockDataList.get(0).getValue()-stockDataList.get(stockDataList.size()-1).getValue();
        tnData.setHigh(high);
        tnData.setLow(low);
        tnData.setEnd(point);
        tnData.setStart(start);
        tnData.setStockCode(id);
        tnData.setTurnover(turnover);
        tnData.setValue(value);
        tnData.setValueChange(valueChange);
        tnDataDAO.save(tnData);
    }



}
