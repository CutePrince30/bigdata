package com.unisk.ad.ssp.controller.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.unisk.ad.ssp.config.Constants;
import com.unisk.ad.ssp.config.MediaType;
import com.unisk.ad.ssp.dispatcher.BidderRespDispatcher;
import com.unisk.ad.ssp.integrator.BidderReqIntegrator;
import com.unisk.ad.ssp.util.HttpUtils;
import com.unisk.ad.ssp.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
@Controller
public class AppController {

    private static Logger log = LoggerFactory.getLogger("ssp");

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
    protected String generateBidderPullReq(String data) {
        JsonNode dataNode = JsonUtils.readTree(data);
        String appid = JsonUtils.readValueAsText(dataNode, "appid");
        String slotid = JsonUtils.readValueAsText(dataNode, "slotid");
        String device = dataNode.findPath("device").toString();

        return bidderReqIntegrator.generateBidderPullReq(MediaType.APP, appid, null, slotid, device);
    }

    protected void sendBidderShowAsyncReq(final String param) {
        new Thread() {
            @Override
            public void run() {
                if (log.isDebugEnabled()) {
                    log.debug("send to ssp2bidder: {}", param);
                }
                // 请求bidder
                String url = Constants.BIDDER_SHOWJS_URL + "?" + param;
                try {
                    HttpUtils.doGet(url);
                }
                catch (Exception e) {
                    log.error("向bidder发送请求失败,请检查网络: {}", e);
                }
            }
        }.start();
    }

    protected void sendBidderClickAsyncReq(final String param) {
        new Thread() {
            @Override
            public void run() {
                if (log.isDebugEnabled()) {
                    log.debug("send to ssp2bidder: {}", param);
                }
                // 请求bidder
                String url = Constants.BIDDER_CLICKJS_URL + "?" + param;
                try {
                    HttpUtils.doGet(url);
                }
                catch (Exception e) {
                    log.error("向bidder发送请求失败,请检查网络: {}", e);
                }
            }
        }.start();
    }

}
