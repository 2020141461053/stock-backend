package com.example.eback.scheduled;

import org.springframework.scheduling.annotation.Scheduled;

public class UpdataTask {
    @Scheduled(cron="0 0/1 *  * * ? ")   //每1分钟执行一次
    public void Redis2Mysql(){

    }
}
