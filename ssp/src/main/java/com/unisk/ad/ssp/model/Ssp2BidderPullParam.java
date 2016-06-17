package com.unisk.ad.ssp.model;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
public class Ssp2BidderPullParam {

    private String reqid;

    private String mediaType;

    /* app信息 */
    private String appid;

    private String category;

    private String storeUrl;

    private String appName;

    private String appBundle;

    private String appVersion;

    /* site信息 */
    private String siteid;

    private String siteName;

    private String sitePage;

    private String siteRefer;

    /* imp信息 */
    private String slotid;

    private String instl;

    private Integer width;

    private Integer height;

    /* device信息 */
    private String device;

    /* user信息 */
    private String user;

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppBundle() {
        return appBundle;
    }

    public void setAppBundle(String appBundle) {
        this.appBundle = appBundle;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getSlotid() {
        return slotid;
    }

    public void setSlotid(String slotid) {
        this.slotid = slotid;
    }

    public String getInstl() {
        return instl;
    }

    public void setInstl(String instl) {
        this.instl = instl;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getDevice() {
        return device == null ? "{}" : device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUser() {
        return user == null ? "{}" : user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSitePage() {
        return sitePage;
    }

    public void setSitePage(String sitePage) {
        this.sitePage = sitePage;
    }

    public String getSiteRefer() {
        return siteRefer;
    }

    public void setSiteRefer(String siteRefer) {
        this.siteRefer = siteRefer;
    }
}
