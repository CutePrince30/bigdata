package com.unisk.ad.ssp.config;

/**
 * Created by sunyunjie on 6/4/16.
 */
public enum MediaType {

    WEB("0"), APP("1");

    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    MediaType (String flag) {
        this.flag = flag;
    }

}
