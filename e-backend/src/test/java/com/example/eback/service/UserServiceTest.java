package com.example.eback.service;

import com.example.eback.EBackApplication;
import com.example.eback.entity.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import lombok.Builder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Before
    void test() {

    }

    @Test
    public void getRole() {
        String r = userService.getRole("");
        assertEquals(r,"admin");
    }

    @Test
    public void findByUsername() {
        User user = userService.findByUsername("admin");
        User u = new User();
        u.setId(1);
        u.setUsername("admin");
        u.setName("初始管理员");
        u.setPassword("35b9529f89cfb9b848060ca576237e17");
        u.setSalt("8O+vDNr2sI3N82BI31fu1A==");
        u.setRole("admin");
        assertEquals(user,u);
    }

    @Test
    public void isExist() {
        String username = "admin";
        boolean flag = userService.isExist(username);
        assertTrue(flag);

    }

    @Test
    public void register() {
        User u = new User();
        u.setId(1);
        u.setUsername("admin");
        u.setName("初始管理员");
        u.setPassword("35b9529f89cfb9b848060ca576237e17");
        u.setSalt("8O+vDNr2sI3N82BI31fu1A==");
        u.setRole("admin");
        User u2 =new User();
        u2.setId(5);
        u2.setUsername("test");
        u2.setRole("user");
        u2.setSalt(new SecureRandomNumberGenerator().nextBytes().toString());

        int i = userService.register(u);
        assertEquals(i,2);
    }

    @Test
    public void setPassword() {

    }

    @Test
    public void edit() {

    }

    @Test
    public void resetPassword() {

    }

    @Test
    public void deleteById() {

    }

    @Test
    public void list() {

    }

    @Test
    public void get() {

    }
}