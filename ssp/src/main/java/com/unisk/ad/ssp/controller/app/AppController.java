package com.unisk.ad.ssp.controller.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.unisk.ad.ssp.config.Constants;
import com.unisk.ad.ssp.config.MediaType;
import com.unisk.ad.ssp.dispatcher.BidderRespDispatcher;
import com.unisk.ad.ssp.integrator.BidderReqIntegrator;
import com.unisk.ad.ssp.util.HttpUtils;
import com.unisk.ad.ssp.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
@Controller
public class AppController {

    private static Logger log = LoggerFactory.getLogger(AppController.class);

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
    protected String generateBidderShowReq(String data) {
        JsonNode dataNode = JsonUtils.readTree(data);
        String appid = JsonUtils.readValueAsText(dataNode, "appid");
        String slotid = JsonUtils.readValueAsText(dataNode, "slotid");
        String device = dataNode.findPath("device").toString();

        return bidderReqIntegrator.generateBidderShowReq(MediaType.APP, appid, null, slotid, device);
    }

    protected String generateBidderClickReq(String data) {
        Map<String, Object> map = JsonUtils.decode(data, Map.class);
        return bidderReqIntegrator.generateBidderClickReq(map);
    }

    protected void sendBidderClickAsyncReq(final String ssp2BidderParaStr) {
        new Thread() {
            @Override
            public void run() {
                if (StringUtils.isEmpty(ssp2BidderParaStr)) {
                    log.error("failed: ssp向bidder请求参数有误, {}", ssp2BidderParaStr);
                }
                else {
                    if (log.isDebugEnabled()) {
                        log.debug("send to ssp2bidder: {}", ssp2BidderParaStr);
                    }
                    // 请求bidder
                    String bidder2sspStr = null;
                    try {
                        HttpUtils.doPost(Constants.BIDDER_URL, ssp2BidderParaStr);
                    }
                    catch (Exception e) {
                        log.error("向bidder发送请求失败,请检查网络: {}", e);
                    }
                }
            }
        }.start();
    }

}
