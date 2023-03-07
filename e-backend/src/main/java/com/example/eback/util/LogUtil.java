package com.example.eback.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {

    public static  void log(String name, String level, String input){
        Logger log= LogManager.getLogger(name);
        switch(level){
            case "info":log.info(input);break;
            case "warn":log.warn(input);break;
            case "error":log.error(input);break;
        }
    }
}
