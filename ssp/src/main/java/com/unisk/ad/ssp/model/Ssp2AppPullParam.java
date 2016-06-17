package com.unisk.ad.ssp.model;

/**
 * Created by sunyunjie on 6/2/16.
 */
public class Ssp2AppPullParam {

    public Ssp2AppPullParam() {
    }

    public Ssp2AppPullParam(String id, String adid, String showjs, String clickjs,
                            String landingPage, String addr, String height, String width) {
        this.id = id;
        this.adid = adid;
        this.showjs = showjs;
        this.clickjs = clickjs;
        this.landingPage = landingPage;
        this.addr = addr;
        this.height = height;
        this.width = width;
    }

    private String id;

    private String adid;

    private String showjs;

    private String clickjs;

    private String landingPage;

    private String addr;

    private String height;

    private String width;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdid() {
        return adid;
    }

    public void setAdid(String adid) {
        this.adid = adid;
    }

    public String getShowjs() {
        return showjs;
    }

    public void setShowjs(String showjs) {
        this.showjs = showjs;
    }

    public String getClickjs() {
        return clickjs;
    }

    public void setClickjs(String clickjs) {
        this.clickjs = clickjs;
    }

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
