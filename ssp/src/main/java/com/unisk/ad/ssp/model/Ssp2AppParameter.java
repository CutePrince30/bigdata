package com.unisk.ad.ssp.model;

/**
 * Created by sunyunjie on 6/2/16.
 */
public class Ssp2AppParameter {

    public Ssp2AppParameter() {
    }

    public Ssp2AppParameter(String landingPage, String addr, String height, String width) {
        this.landingPage = landingPage;
        this.addr = addr;
        this.height = height;
        this.width = width;
    }

    private String landingPage;

    private String addr;

    private String height;

    private String width;

    public String getLandingPage() {
        return landingPage;
    }

    public void setLandingPage(String landingPage) {
        this.landingPage = landingPage;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
