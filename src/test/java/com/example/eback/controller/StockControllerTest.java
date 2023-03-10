package com.example.eback.controller;

import com.example.eback.redis.RedisService;
import com.example.eback.service.StockDataService;
import com.example.eback.service.StockService;
import com.example.eback.service.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(StockController.class)
@Transactional("hibernateTransactionManager")
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    StockDataService stockDataService;

    @MockBean
    StockService stockService;

    @MockBean
    RedisService redisService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(StockController.class).build();
    }


    @Ignore
    public void getAll() {
    }

    @Test
    public void getByCode() throws Exception {
        String stockCode = "testtest";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/stock/get_code")
                        .accept(MediaType.APPLICATION_JSON).param("stockCode",stockCode))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getByName() {
    }

    @Test
    public void uploadCsv() {
    }

    @Test
    public void getUser() {
    }
}