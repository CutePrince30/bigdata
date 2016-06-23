package com.unisk.ad.ssp.util;

import java.io.File;

/**
 * Created by sunyunjie on 6/2/16.
 */
public class BeanUtilsTest {

    public static void main(String[] args) {
//        User u1 = new User();
//        u1.setName("sunyj");
//
//        User u2 = new User();
//        u2.setAge(13);
//
//        System.out.println(u2);
//
//        BeanUtils.merge(u1, u2);
//
//        System.out.println(u2);

        File file = new File("ssp/log/temp.log");
        System.out.println(file.getAbsolutePath());
    }

}
