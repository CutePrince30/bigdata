package com.unisk.ad.ssp.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
public class BeanUtils {

    /**
     * 对象之间的属性合并,仅支持Stirng和Integer、int
     *
     * @param origin
     * @param destination
     * @param <T>
     */
    public static <T> void merge(T origin, T destination) {
        if (origin == null || destination == null)
            return;
        if (!origin.getClass().equals(destination.getClass()))
            return;

        Field[] fields = origin.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String type = fields[i].getGenericType().toString();
            try {
                fields[i].setAccessible(true);
                Object value = fields[i].get(origin);
                switch (type) {
                    case "class java.lang.String":
                    case "class java.lang.Integer":
                        if (value != null) {
                            fields[i].set(destination, value);
                        }
                        break;
                    case "int":
                        if (value != 0) {
                            fields[i].set(destination, value);
                        }
                        break;
                }
                fields[i].setAccessible(false);
            }
            catch (Exception e) {
            }
        }
    }

}
