package com.unisk.ad.ssp.dispatcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.unisk.ad.ssp.config.ClientType;
import com.unisk.ad.ssp.config.Operate;
import com.unisk.ad.ssp.dao.adp.AdpMapper;
import com.unisk.ad.ssp.model.InfoJsParameter;
import com.unisk.ad.ssp.model.Ssp2AppParameter;
import com.unisk.ad.ssp.util.BeanUtils;
import com.unisk.ad.ssp.util.JsonUtils;
import com.unisk.ad.ssp.util.TemplateUtils;
import org.beetl.core.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.unisk.ad.ssp.config.ClientType.*;

/**
 * @author sunyunjie
 */
@Component
public class BidderRespDispatcher {

    @Autowired
    private AdpMapper adpMapper;

    public String generateResp(ClientType ct, Operate op, String respStr, Map<String, Object> otherParaMap) {
        String result = null;
        switch (ct) {
            case WEB:
                result = generateWebResp(op, respStr, otherParaMap);
                break;
            case ANDROID:
            case IOS:
                result = generateAppResp(op, respStr, otherParaMap);
                break;
            default:
                break;
        }
        return result;
    }

    public String generateWebResp(Operate op, String respStr, Map<String, Object> otherParaMap) {
        String result = null;
        switch (op) {
            case SHOW:
                // 解析bidder返回的数据
                JsonNode node = JsonUtils.readTree(respStr);
                String adid = JsonUtils.readValueAsText(node, "adid");
                String width = JsonUtils.readValueAsText(node, "adw");
                String height = JsonUtils.readValueAsText(node, "adh");
                String landingPage = JsonUtils.readValueAsText(node, "adurl");
                final String wurl = JsonUtils.readValueAsText(node, "wurl");

                // 返回bidder请求结果
                //        new Thread() {
                //            public void run() {
                //                HttpUtils.doGet(wurl);
                //            }
                //        }.start();

                //		String adid = "2166";

                // 根据bidder返回数据的adid查库
                InfoJsParameter infoJsPara1 = adpMapper.selectOneByAdidFromAd(adid);
                InfoJsParameter infoJsPara2 = adpMapper.selectOneByAdidFromStuff(adid);

                if (infoJsPara1 != null) {
                    // 将结果放入一个InfoJsParameter对象中
                    BeanUtils.merge(infoJsPara1, infoJsPara2);
                }
                infoJsPara2.setSn(otherParaMap.get("sn").toString());
                infoJsPara2.setClickJs("http://121.40.134.209/click.js");
                infoJsPara2.setShowJs("http://121.40.134.209/show.js");
                infoJsPara2.setHeight(height);
                infoJsPara2.setWidth(width);
                infoJsPara2.setLandingPage(landingPage);

                // 生成对应json文件
                Template infoJsTemplate = TemplateUtils.getTemplate("/template/infojs_template.beetl");
                infoJsTemplate.binding("obj", infoJsPara2);
                result = infoJsTemplate.render();
                break;
            case CLICK:
                break;
            case CREATE:
                break;
        }
        return result;
    }

    public String generateAppResp(Operate op, String respStr, Map<String, Object> otherParaMap) {
        String result = null;
        switch (op) {
            case SHOW:
                JsonNode bidder2sspNode = JsonUtils.readTree(respStr);
                String addr = JsonUtils.readValueAsText(bidder2sspNode, "adi");
                String width = JsonUtils.readValueAsText(bidder2sspNode, "adw");
                String height = JsonUtils.readValueAsText(bidder2sspNode, "adh");
                String landingPage = JsonUtils.readValueAsText(bidder2sspNode, "adurl");

                Ssp2AppParameter ssp2AppParameter = new Ssp2AppParameter(landingPage, addr, height, width);
                Template ssp2AppTemplate = TemplateUtils.getTemplate("/template/ssp2app_template.beetl");
                ssp2AppTemplate.binding("obj", ssp2AppParameter);
                result = ssp2AppTemplate.render();
                break;
            case CLICK:
                break;
            case CREATE:
                break;
        }
        return result;
    }

}
