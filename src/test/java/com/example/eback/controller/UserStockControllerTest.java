package com.example.eback.controller;

import com.example.eback.service.FavoriteService;
import com.example.eback.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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
@WebMvcTest(UserStockController.class)
@Transactional
public class UserStockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FavoriteService favoriteService;

    @MockBean
    UserService userService;

    @Before
    public void setUp() throws Exception {
        System.out.println("Test is starting...");
        mockMvc = MockMvcBuilders.standaloneSetup(UserStockController.class).build();
    }

    @Test
    public void add() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/userstock/add")
                        .accept(MediaType.APPLICATION_JSON).param("originContent", "15221365094"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


    }

    @Test
    public void delete() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void getUser() {
    }
}