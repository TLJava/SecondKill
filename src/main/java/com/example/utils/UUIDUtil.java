package com.example.utils;

import java.util.UUID;
/**
* UUID工具类
** @author LJava
* @since 1.0.0
*/
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}