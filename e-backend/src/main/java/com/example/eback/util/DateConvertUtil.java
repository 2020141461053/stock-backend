package com.example.eback.util;
import org.apache.commons.beanutils.BeanUtilsBean;
/**
 * @author 34719
 * @version 1.0
 * Create by 2023/3/7 22:45
 */
public class DateConvertUtil implements org.apache.commons.beanutils.Converter{

    @Override
    public Object convert(Class type, Object value) {
        if (value == null) {
            return null;
        }
        if (!(value instanceof String)) {
            throw new RuntimeException("Invalid type: " + type.getName() + " (" + value.getClass().getName() + ")");
        }
        String str = (String) value;
        if (str.trim().length() == 0) {
            return null;
        }
        try {
            return java.sql.Date.valueOf(str);
        } catch (Exception e) {
            throw new RuntimeException("Error converting string to date: " + str, e);
        }
    }
}
