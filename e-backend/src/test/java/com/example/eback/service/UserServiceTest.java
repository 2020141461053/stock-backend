package com.example.eback.service;

import com.example.eback.EBackApplication;
import com.example.eback.entity.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import lombok.Builder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    private final static User user = new User();
    @Before
    public void initUser() {
        user.setId(1);
        user.setUsername("admin");
        user.setName("初始管理员");
        user.setPassword("35b9529f89cfb9b848060ca576237e17");
        user.setSalt("8O+vDNr2sI3N82BI31fu1A==");
        user.setRole("admin");
    }


    @Test
    public void getRole() {
        String r = userService.getRole("admin");
        assertEquals(r,"admin");
    }

    @Test
    public void findByUsername() {
        User u = userService.findByUsername(user.getUsername());
        assertEquals(u,user);
    }

    @Test
    public void isExist() {
        boolean flag = userService.isExist(user.getUsername());
        assertTrue(flag);
    }

    @Test
    public void register() {

        User user1 = new User();
        user1.setName("testR");
        user1.setUsername("testR");
        int i = userService.register(user);
        int t = userService.register(user1);
        assertEquals(2, i);
        assertEquals(1,t);

    }


    @Test
    public void edit() {
        User testU = userService.findByUsername(user.getUsername());
        user.setName("test");
        user.setRole("test");
        testU.setRole("test");
        testU.setName("test");
        userService.edit(testU);
        assertEquals(user,testU);

    }


}