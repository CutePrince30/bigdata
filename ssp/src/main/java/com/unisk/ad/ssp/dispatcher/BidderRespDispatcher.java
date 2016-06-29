package com.unisk.ad.ssp.dispatcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.unisk.ad.ssp.config.ClientType;
import com.unisk.ad.ssp.config.Constants;
import com.unisk.ad.ssp.config.MediaType;
import com.unisk.ad.ssp.config.Operate;
import com.unisk.ad.ssp.dao.adp.AdpMapper;
import com.unisk.ad.ssp.dao.ssp.SspMapper;
import com.unisk.ad.ssp.model.InfoJsParam;
import com.unisk.ad.ssp.model.Ssp2AppClickParam;
import com.unisk.ad.ssp.model.Ssp2AppPullParam;
import com.unisk.ad.ssp.util.*;
import org.beetl.core.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
@Component
public class BidderRespDispatcher {

    @Autowired
    private SspMapper sspMapper;

    public String generateResp(ClientType ct, Operate op, String respStr, Map<String, Object> otherParaMap) {
        String result = null;
        switch (ct) {
            case WEB:
                result = generateWebResp(ct, op, respStr, otherParaMap);
                break;
            case ANDROID:
            case IOS:
                result = generateAppResp(ct, op, respStr, otherParaMap);
                break;
            default:
                break;
        }
        return result;
    }

    public String generateWebResp(ClientType ct, Operate op, String respStr, Map<String, Object> otherParaMap) {
        Object obj = null;
        Template ssp2WebTemplate = null;

        switch (op) {
            case PULL:
                // 解析bidder返回的数据
                JsonNode node = JsonUtils.readTree(respStr);
                String isDefault = JsonUtils.readValueAsText(node, "isdefault");
                String adid = JsonUtils.readValueAsText(node, "adid");
                final String wurl = JsonUtils.readValueAsText(node, "wurl");

                // 返回bidder请求结果
                new Thread() {
                    public void run() {
                        HttpUtils.doGet(wurl);
                    }
                }.start();
                String param = UrlUtils.TruncateUrlPage(wurl);
                String showJs = getShowUrl(ct) + "?" + param;
                String clickJs = getClickUrl(ct) + "?" + param;

                InfoJsParam infoJsPara = null;
                if (isDefault.equals("0")) {
                    // 正常广告,根据bidder返回数据的adid查库
                    infoJsPara = sspMapper.selectAdInfoByStuffId(adid);
                    if (infoJsPara == null) {
                        return RenderUtils.render(MediaType.WEB, Operate.PULL, Constants.FAILED_CODE,
                                "无广告数据, adid: " + adid, "{}");
                    }
                }
                else {
                    // 默认广告,根据bidder返回的数据生成infoJsPara
                    String width = JsonUtils.readValueAsText(node, "adw");
                    String height = JsonUtils.readValueAsText(node, "adh");
                    String landingPage = JsonUtils.readValueAsText(node, "adurl");
                    String addr = JsonUtils.readValueAsText(node, "adi");
                    String spid = UrlUtils.URLRequest(wurl).get("spid");
                    infoJsPara = new InfoJsParam();
                    infoJsPara.setAdid(adid);
                    infoJsPara.setSpid(spid);
                    infoJsPara.setWidth(width);
                    infoJsPara.setHeight(height);
                    infoJsPara.setLandingPage(landingPage);
                    infoJsPara.setAddr(addr);
                }

                infoJsPara.setSn(otherParaMap.get("sn").toString());
                infoJsPara.setClickJs(clickJs);
                infoJsPara.setShowJs(showJs);
                infoJsPara.setAdType("1");
                infoJsPara.setHasText("0");
                infoJsPara.setSrc("1");
                infoJsPara.setViewType("1");
                infoJsPara.setType("AD_IMAGE");

                obj = infoJsPara;
                ssp2WebTemplate = TemplateUtils.getTemplate("/template/infojs_template.beetl");
                break;
            case SHOW:
                break;
            case CLICK:
                obj = new Ssp2AppClickParam(respStr);
                ssp2WebTemplate = TemplateUtils.getTemplate("/template/ssp2app_click_template.beetl");
            case CREATE:
                break;
        }
        ssp2WebTemplate.binding("obj", obj);

        return ssp2WebTemplate.render();
    }

    public String generateAppResp(ClientType ct, Operate op, String respStr, Map<String, Object> otherParaMap) {
        Object obj = null;
        Template ssp2AppTemplate = null;

        switch (op) {
            case PULL:
                JsonNode node = JsonUtils.readTree(respStr);
                String id = JsonUtils.readValueAsText(node, "id");
                String adid = JsonUtils.readValueAsText(node, "adid");
                String addr = JsonUtils.readValueAsText(node, "adi");
                String width = JsonUtils.readValueAsText(node, "adw");
                String height = JsonUtils.readValueAsText(node, "adh");
                String landingPage = JsonUtils.readValueAsText(node, "adurl");
                final String wurl = JsonUtils.readValueAsText(node, "wurl");

                // 返回bidder请求结果
                new Thread() {
                    public void run() {
                        HttpUtils.doGet(wurl);
                    }
                }.start();

                String param = UrlUtils.TruncateUrlPage(wurl);
                String showJs = getShowUrl(ct) + "?" + param;
                String clickJs = getClickUrl(ct) + "?" + param;

                obj = new Ssp2AppPullParam(id, adid, showJs, clickJs, landingPage, addr, height, width);
                ssp2AppTemplate = TemplateUtils.getTemplate("/template/ssp2app_pull_template.beetl");
                break;
            case SHOW:
                break;
            case CLICK:
                obj = new Ssp2AppClickParam(respStr);
                ssp2AppTemplate = TemplateUtils.getTemplate("/template/ssp2app_click_template.beetl");
                break;
            case CREATE:
                break;
        }
        ssp2AppTemplate.binding("obj", obj);

        return ssp2AppTemplate.render();
    }

    private String getShowUrl(ClientType ct) {
        String show_url = null;
        switch (ct) {
            case IOS:
                show_url = Constants.SSP_IOS_SHOWJS_URL;
                break;
            case ANDROID:
                show_url = Constants.SSP_ANDROID_SHOWJS_URL;
                break;
            case WEB:
                show_url = Constants.SSP_WEB_SHOWJS_URL;
                break;
        }
        return show_url;
    }

    private String getClickUrl(ClientType ct) {
        String click_url = null;
        switch (ct) {
            case IOS:
                click_url = Constants.SSP_IOS_CLICKJS_URL;
                break;
            case ANDROID:
                click_url = Constants.SSP_ANDROID_CLICKJS_URL;
                break;
            case WEB:
                click_url = Constants.SSP_WEB_CLICKJS_URL;
                break;
        }
        return click_url;
    }

}
