package com.unisk.ad.ssp.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
public class Log {

    // 状态码
    private int code;

    // 操作名称
    private String name;

    // 版本
    private String version;

    private String source;

    private String exchangeUserId;

    // 当前时间
    private Date time;

    // 用户id
    private String userId;

    // 广告id
    private String adId;

    // 请求id
    private String pushId;

    // ip
    private String ip;

    // 地区
    private String region;

    // 竞价价格
    private String bidPrice;

    // 花费价格
    private String costPrice;

    // 媒体名称
    private String mediaName;

    public Log() {

    }

    public Log(int code, String name, String version, String source, String exchangeUserId,
               Date time, String userId, String adId,
               String pushId, String ip, String region,
               String bidPrice, String costPrice, String mediaName) {
        this.code = code;
        this.name = name;
        this.version = version;
        this.source = source;
        this.exchangeUserId = exchangeUserId;
        this.time = time;
        this.userId = userId;
        this.adId = adId;
        this.pushId = pushId;
        this.ip = ip;
        this.region = region;
        this.bidPrice = bidPrice;
        this.costPrice = costPrice;
        this.mediaName = mediaName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getExchangeUserId() {
        return exchangeUserId;
    }

    public void setExchangeUserId(String exchangeUserId) {
        this.exchangeUserId = exchangeUserId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

}
