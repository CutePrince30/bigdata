package com.unisk.ad.ssp.controller.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import com.unisk.ad.ssp.config.ClientType;
import com.unisk.ad.ssp.config.Constants;
import com.unisk.ad.ssp.config.MediaType;
import com.unisk.ad.ssp.config.Operate;
import com.unisk.ad.ssp.dispatcher.BidderRespDispatcher;
import com.unisk.ad.ssp.integrator.BidderReqIntegrator;
import com.unisk.ad.ssp.util.HttpUtils;
import com.unisk.ad.ssp.util.JsonUtils;
import com.unisk.ad.ssp.util.RenderUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
@RequestMapping(value = "ios")
@Controller
public class IOSController extends AppController {

    private static Logger log = LoggerFactory.getLogger("ssp");

    @RequestMapping(value = "/pull", method = RequestMethod.POST)
    @ResponseBody
    public String pull(HttpServletRequest request, @RequestParam(value = "data", required = true) String data) {
        if (log.isDebugEnabled()) {
            log.debug("received from ios: {}", data);
        }

        String ssp2BidderParaStr = null;
        try {
            ssp2BidderParaStr = super.generateBidderPullReq(data);
        }
        catch (Exception e) {
            return RenderUtils.render(MediaType.APP, Operate.PULL, Constants.FAILED_CODE,
                    "failed: ssp向bidder请求参数有误, 错误信息: " + e.getMessage(),
                    Constants.EMPTY_STRING);
        }

        if (log.isDebugEnabled()) {
            log.debug("send to ssp2bidder: {}", ssp2BidderParaStr);
        }

        // 请求bidder
		String bidder2sspStr = null;
		try {
			bidder2sspStr = HttpUtils.doPost(Constants.BIDDER_URL, ssp2BidderParaStr);
		}
		catch (Exception e) {
			log.error("向bidder发送请求失败,请检查网络: {}", e);
			return RenderUtils.render(MediaType.APP, Operate.PULL, Constants.FAILED_CODE,
                    "failed: 向bidder发送请求失败", Constants.EMPTY_STRING);
		}

        if (log.isDebugEnabled()) {
            log.debug("received from bidder: {}", bidder2sspStr);
        }

        if (bidder2sspStr == null) {
            return RenderUtils.render(MediaType.APP, Operate.PULL, Constants.FAILED_CODE,
                    "failed: bidder无返回数据或程序解析错误", Constants.EMPTY_STRING);
        }

        // 为了记录日志,传入ip
        Map<String, Object> map = Maps.newHashMap();
        map.put("ip", request.getRemoteHost());

        String resp = bidderRespDispatcher.generateResp(ClientType.IOS, Operate.PULL, bidder2sspStr, map);

        return RenderUtils.render(MediaType.APP, Operate.PULL, Constants.SUCCESS_CODE, "success", resp);
    }

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    public String show(HttpServletRequest request, @RequestParam(value = "data", required = true) String data) {
        String param = request.getQueryString();

        if (log.isDebugEnabled()) {
            log.debug("received from ios: {}", data);
        }

        super.sendBidderShowAsyncReq(param);

        return RenderUtils.render(MediaType.APP, Operate.SHOW, Constants.SUCCESS_CODE, "success", "{}");
    }

    @RequestMapping(value = "/click", method = RequestMethod.POST)
    @ResponseBody
    public String click(HttpServletRequest request, @RequestParam(value = "data", required = true) String data) {
        String param = request.getQueryString();

        if (log.isDebugEnabled()) {
            log.debug("received from ios: {}", data);
        }

        super.sendBidderClickAsyncReq(param);

        //返回给客户端landing_page
        JsonNode dataNode = JsonUtils.readTree(data);
        String landing_page = JsonUtils.readValueAsText(dataNode, "landing_page");

        String resp = bidderRespDispatcher.generateResp(ClientType.IOS, Operate.CLICK, landing_page, null);

        return RenderUtils.render(MediaType.APP, Operate.CLICK, Constants.SUCCESS_CODE, "success", resp);
    }

}
