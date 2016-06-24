package com.unisk.ad.ssp.controller.web;

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.unisk.ad.ssp.config.ClientType;
import com.unisk.ad.ssp.config.Constants;
import com.unisk.ad.ssp.config.MediaType;
import com.unisk.ad.ssp.config.Operate;

import com.unisk.ad.ssp.dispatcher.BidderRespDispatcher;
import com.unisk.ad.ssp.integrator.BidderReqIntegrator;
import com.unisk.ad.ssp.util.*;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
@Controller
public class WebController {

    private static Logger log = LoggerFactory.getLogger("ssp");

    @Autowired
    private BidderReqIntegrator bidderReqIntegrator;

    @Autowired
    private BidderRespDispatcher bidderRespDispatcher;

    @RequestMapping(value = "/pull/main.js", method = RequestMethod.GET)
    @ResponseBody
    public String mainJS() throws IOException {
        Template template = TemplateUtils.getTemplate("/template/mainjs_template.beetl");
        template.binding("host", Constants.SSP_IP);
        return template.render();
    }

    @RequestMapping(value = "/pull/info.js", method = RequestMethod.GET)
    @ResponseBody
    public String infoJS(
            HttpServletRequest request,
            @RequestParam(value = "sn", required = true) String sn,
            @RequestParam(value = "slotid", required = true) String slotid,
            @RequestParam(value = "width_page", required = false) String width_page,
            @RequestParam(value = "width_screen", required = false) String width_screen)
            throws IOException {

        String ssp2BidderParaStr = null;
        try {
            ssp2BidderParaStr = bidderReqIntegrator.generateBidderPullReq(MediaType.WEB, null, null, slotid, null);
        } catch (Exception e) {
            return RenderUtils.render(Constants.FAILED_CODE, "failed: ssp向bidder请求参数有误, 错误信息: " + e.getMessage(),
                    Constants.EMPTY_STRING);
        }

        if (log.isDebugEnabled()) {
            log.debug("send to bidder: {}", ssp2BidderParaStr);
        }

        // 请求bidder
		String bidder2sspStr = null;
		try {
			bidder2sspStr = HttpUtils.doPost(Constants.BIDDER_URL, ssp2BidderParaStr);
		}
		catch (Exception e) {
            log.error("向bidder发送请求失败,请检查网络: {}", e);
            return RenderUtils.render(Constants.FAILED_CODE, "failed: 向bidder发送请求失败", Constants.EMPTY_STRING);
		}

        if (log.isDebugEnabled()) {
            log.debug("received from bidder: {}", bidder2sspStr);
        }

        if (bidder2sspStr == null) {
            return RenderUtils.render(Constants.FAILED_CODE, "failed: bidder无返回数据或程序解析错误", Constants.EMPTY_STRING);
        }

        Map<String, Object> otherParaMap = Maps.newHashMap();
        otherParaMap.put("sn", sn);
        otherParaMap.put("ip", request.getRemoteHost());

        String resp = bidderRespDispatcher.generateResp(ClientType.WEB, Operate.PULL, bidder2sspStr, otherParaMap);

        return resp;
    }

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    public String show(HttpServletRequest request, @RequestParam(value = "data", required = true) String data) {
        return null;
    }

    @RequestMapping(value = "/click", method = RequestMethod.POST)
    @ResponseBody
    public String click(HttpServletRequest request, @RequestParam(value = "data", required = true) String data) {
        return null;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        // 向数据库中插入数据

        // 记录日志
        return null;
    }

}
