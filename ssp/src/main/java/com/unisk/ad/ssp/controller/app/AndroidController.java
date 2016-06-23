package com.unisk.ad.ssp.controller.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import com.unisk.ad.ssp.config.ClientType;
import com.unisk.ad.ssp.config.Constants;
import com.unisk.ad.ssp.config.Operate;
import com.unisk.ad.ssp.model.LogParam;
import com.unisk.ad.ssp.util.HttpUtils;
import com.unisk.ad.ssp.util.JsonUtils;
import com.unisk.ad.ssp.util.RenderUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = "android")
@Controller
public class AndroidController extends AppController {

    private static Logger log = LoggerFactory.getLogger("ssp");

    @RequestMapping(value = "/pull", method = RequestMethod.POST)
    @ResponseBody
    public String pull(HttpServletRequest request, @RequestParam(value = "data", required = true) String data) {
        //data = "{\"appid\":\"4\",\"slotid\":\"6\",\"device\":{\"ip\":\"10.23.45.67\",\"os\":\"iOS\",\"model\":\"iPhone5,1\",\"geo\":{\"lon\":116.4736795,\"type\":1,\"lat\":39.9960702},\"osv\":\"7.0.6\",\"js\":1,\"dnt\":0,\"sh\":1024,\"s_density\":2,\"connectiontype\":2,\"dpidsha1\":\"7c222fb2927d828af22f592134e8932480637c0d\",\"didsha1\":\"1231231238912839123812\",\"macsha1\":\"2445934589348534534534\",\"ua\":\"Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_6 like Mac OS X)AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B206\",\"carrier\":\"46000\",\"language\":\"zh\",\"make\":\"Apple\",\"sw\":768,\"imei\":\"12312312312312\"}}";

        if (log.isDebugEnabled()) {
            log.debug("received from android: {}", data);
        }

        String ssp2BidderParaStr = super.generateBidderPullReq(data);

        if (log.isDebugEnabled()) {
            log.debug("send to ssp2bidder: {}", ssp2BidderParaStr);
        }

        if (StringUtils.isEmpty(ssp2BidderParaStr)) {
            return RenderUtils.render(Constants.FAILED_CODE, "failed: ssp向bidder请求参数有误", Constants.EMPTY_STRING);
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

        // 为了记录日志,传入ip
        Map<String, Object> map = Maps.newHashMap();
        map.put("ip", request.getRemoteHost());

        String resp = bidderRespDispatcher.generateResp(ClientType.ANDROID, Operate.PULL, bidder2sspStr, map);

        return RenderUtils.render(Constants.SUCCESS_CODE, "success", resp);

    }

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    public String show(HttpServletRequest request, @RequestParam(value = "data", required = true) String data) {
        String param = request.getQueryString();

        if (log.isDebugEnabled()) {
            log.debug("received from android: {}", data);
        }

        super.sendBidderShowAsyncReq(param);

        return RenderUtils.render(Constants.SUCCESS_CODE, "success", "{}");
    }

    @RequestMapping(value = "/click", method = RequestMethod.POST)
    @ResponseBody
    public String click(HttpServletRequest request, @RequestParam(value = "data", required = true) String data) {
        String param = request.getQueryString();

        if (log.isDebugEnabled()) {
            log.debug("received from android: {}", data);
        }
        // 因为不需要利用bidder返回的数据,所以启动线程向bibber发起请求

        super.sendBidderClickAsyncReq(param);

        //返回给客户端landing_page
        JsonNode dataNode = JsonUtils.readTree(data);
        String landing_page = JsonUtils.readValueAsText(dataNode, "landing_page");

        String resp = bidderRespDispatcher.generateResp(ClientType.ANDROID, Operate.CLICK, landing_page, null);

        return RenderUtils.render(Constants.SUCCESS_CODE, "success", resp);
    }

}
