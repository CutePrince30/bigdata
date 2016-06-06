package com.unisk.ad.ssp.controller.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.unisk.ad.ssp.config.MediaType;
import com.unisk.ad.ssp.dispatcher.BidderRespDispatcher;
import com.unisk.ad.ssp.integrator.BidderReqIntegrator;
import com.unisk.ad.ssp.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
@Controller
public class AppController {

    @Autowired
    protected BidderReqIntegrator bidderReqIntegrator;

    @Autowired
    protected BidderRespDispatcher bidderRespDispatcher;

    /**
     * 生成app向ssp发送的请求信息
     *
     * @param data
     * @return
     */
    protected String generateBidderReq(String data) {
        JsonNode dataNode = JsonUtils.readTree(data);
        String appid = JsonUtils.readValueAsText(dataNode, "appid");
        String slotid = JsonUtils.readValueAsText(dataNode, "slotid");
        String device = dataNode.findPath("device").toString();

        return bidderReqIntegrator.generateBidderReq(MediaType.APP, appid, null, slotid, device);
    }

}
