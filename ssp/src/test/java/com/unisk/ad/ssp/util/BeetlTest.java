package com.unisk.ad.ssp.util;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class BeetlTest {

    @Test
    public void simpleTemplate() throws IOException {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("hello,${name}");
        t.binding("name", "beetl");
        String str = t.render();
        System.out.println(str);
    }

    //@Test
    public void fileTemplate() throws IOException {
        String root = System.getProperty("user.dir") + File.separator + "template";
        FileResourceLoader resourceLoader = new FileResourceLoader(root, "utf-8");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("/s01/hello.txt");
        String str = t.render();
        System.out.println(str);
    }

    @Test
    public void classPathTemplate() throws IOException {
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        long start = System.currentTimeMillis();
        Template t = gt.getTemplate("/template/demo.beetl");
        t.binding("product", "{\"name\" : \"ipad\"}");

//        Template t1 = gt.getTemplate("/template/demo1.beetl");
//        t1.binding("name", "sunyj");

        String str = t.render();
//        String str1 = t1.render();

        System.out.println(str);
//        System.out.println(str1);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void classPathTemplate1() {
        Template t1 = TemplateUtils.getTemplate("/template/demo.beetl");
        t1.binding("product", "{\"name\" : \"ipad\"}");
        System.out.println(t1.render());

        Template t2 = TemplateUtils.getTemplate("/template/demo.beetl");
        t2.binding("product", "{\"name\" : \"ipod\"}");
        System.out.println(t2.render());
    }

}
