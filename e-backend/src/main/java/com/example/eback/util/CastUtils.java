package com.example.eback.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author PBW
 * @date 1/19
 */
public class CastUtils {
    private CastUtils(){
        throw new IllegalStateException("Utility class");
    }
    public static <T> List<T> objectConvertToList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return Collections.emptyList();
    }
}
