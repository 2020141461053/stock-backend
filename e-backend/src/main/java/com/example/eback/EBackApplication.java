package com.example.eback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling //开启定时任务
public class EBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBackApplication.class, args);
    }

}
