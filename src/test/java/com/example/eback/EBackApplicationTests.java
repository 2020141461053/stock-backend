package com.example.eback;

import com.example.eback.Listener.StockDataPublisher;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertNull;

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
