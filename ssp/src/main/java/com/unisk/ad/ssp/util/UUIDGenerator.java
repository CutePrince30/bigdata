package com.unisk.ad.ssp.util;

import java.util.UUID;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
public class UUIDGenerator {

    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        System.out.println(generate());
    }

}
