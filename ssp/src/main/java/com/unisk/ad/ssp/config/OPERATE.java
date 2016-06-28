package com.unisk.ad.ssp.config;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
public enum Operate {

    PULL("pull"), SHOW("show"), CLICK("click"), CREATE("create");

    private String name;

    Operate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
