package com.unisk.ad.ssp.config;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
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
