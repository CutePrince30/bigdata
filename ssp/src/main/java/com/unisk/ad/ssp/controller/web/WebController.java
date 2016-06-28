package com.unisk.ad.ssp.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import com.unisk.ad.ssp.config.ClientType;
import com.unisk.ad.ssp.config.Constants;
import com.unisk.ad.ssp.config.MediaType;
import com.unisk.ad.ssp.config.Operate;

import com.unisk.ad.ssp.controller.CommonController;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
@Controller
public class WebController extends CommonController {

    private static Logger log = LoggerFactory.getLogger("ssp");

    @Autowired
    private BidderReqIntegrator bidderReqIntegrator;

    @Autowired
    private BidderRespDispatcher bidderRespDispatcher;

    @RequestMapping(value = "/pull/main.js", method = RequestMethod.GET)
    @ResponseBody
    public void mainJS(HttpServletResponse response) throws IOException {
        Template template = TemplateUtils.getTemplate("/template/mainjs_template.beetl");
        template.binding("host", Constants.SSP_HOST);
        template.binding("adsUrl", Constants.SSP_WEB_PULL_URL);
        response.setContentType("text/javascript;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(template.render());
    }

    @RequestMapping(value = "/pull/info.js", method = RequestMethod.GET)
    @ResponseBody
    public String infoJS(
            HttpServletRequest request,
            @RequestParam(value = "sn", required = true) String sn,
            @RequestParam(value = "slotid", required = true) String slotid,
            @RequestParam(value = "siteid", required = true) String siteid,
            @RequestParam(value = "width_page", required = false) String width_page,
            @RequestParam(value = "width_screen", required = false) String width_screen)
            throws IOException {

        String ssp2BidderParaStr = null;
        try {
            ssp2BidderParaStr = bidderReqIntegrator.generateBidderPullReq(MediaType.WEB, null, siteid, slotid, null);
        } catch (Exception e) {
            return RenderUtils.render(MediaType.WEB, Operate.PULL, Constants.FAILED_CODE,
                    "failed: ssp向bidder请求参数有误, 错误信息: " + e.getMessage(),
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
            return RenderUtils.render(MediaType.WEB, Operate.PULL, Constants.FAILED_CODE,
                    "failed: 向bidder发送请求失败", Constants.EMPTY_STRING);
		}

        if (log.isDebugEnabled()) {
            log.debug("received from bidder: {}", bidder2sspStr);
        }

        if (bidder2sspStr == null) {
            return RenderUtils.render(MediaType.WEB, Operate.PULL, Constants.FAILED_CODE,
                    "failed: bidder无返回数据或程序解析错误", Constants.EMPTY_STRING);
        }

        Map<String, Object> otherParaMap = Maps.newHashMap();
        otherParaMap.put("sn", sn);
        otherParaMap.put("ip", request.getRemoteHost());

        String resp = bidderRespDispatcher.generateResp(ClientType.WEB, Operate.PULL, bidder2sspStr, otherParaMap);

        return resp;
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @ResponseBody
    public String show(HttpServletRequest request) {
        String param = request.getQueryString();

        super.sendBidderShowAsyncReq(param);

        return RenderUtils.render(MediaType.WEB, Operate.SHOW, Constants.SUCCESS_CODE, "success", "{}");
    }

    @RequestMapping(value = "/click", method = RequestMethod.POST)
    @ResponseBody
    public String click(HttpServletRequest request, @RequestParam(value = "data", required = true) String data) {
        String param = request.getQueryString();

        if (log.isDebugEnabled()) {
            log.debug("received from web: {}", data);
        }

        super.sendBidderClickAsyncReq(param);

        //返回给客户端landing_page
        JsonNode dataNode = JsonUtils.readTree(data);
        String landing_page = JsonUtils.readValueAsText(dataNode, "landing_page");

        String resp = bidderRespDispatcher.generateResp(ClientType.WEB, Operate.CLICK, landing_page, null);

        return RenderUtils.render(MediaType.WEB, Operate.CLICK, Constants.SUCCESS_CODE, "success", resp);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        // 向数据库中插入数据

        // 记录日志
        return null;
    }

}
