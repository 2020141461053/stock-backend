package com.example.eback;

import com.example.eback.Listener.StockDataPublisher;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EBackApplicationTests {


    @Autowired
    private  StockDataPublisher stockDataPublisher;

    @Test
    void test(){
        //System.out.println("ddd");
        //stockDataPublisher.publishStockDataEvent();

    }

}
