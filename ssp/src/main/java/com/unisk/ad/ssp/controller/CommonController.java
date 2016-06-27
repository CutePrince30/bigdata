package com.unisk.ad.ssp.controller;

import com.unisk.ad.ssp.config.Constants;
import com.unisk.ad.ssp.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
public class CommonController {

    private static Logger log = LoggerFactory.getLogger("ssp");

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
