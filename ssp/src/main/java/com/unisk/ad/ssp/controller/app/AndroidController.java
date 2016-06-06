package com.unisk.ad.ssp.controller.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.unisk.ad.ssp.config.ClientType;
import com.unisk.ad.ssp.config.Constants;
import com.unisk.ad.ssp.config.MediaType;
import com.unisk.ad.ssp.config.Operate;
import com.unisk.ad.ssp.dispatcher.BidderRespDispatcher;
import com.unisk.ad.ssp.integrator.BidderReqIntegrator;
import com.unisk.ad.ssp.model.Ssp2AppParameter;
import com.unisk.ad.ssp.util.HttpUtils;
import com.unisk.ad.ssp.util.JsonUtils;
import com.unisk.ad.ssp.util.TemplateUtils;
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

import java.io.IOException;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
@RequestMapping(value = "android")
@Controller
public class AndroidController extends AppController {

    private static Logger log = LoggerFactory.getLogger(AndroidController.class);

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @ResponseBody
    public String show(@RequestParam(value = "data", required = true) String data) {
        //data = "{\"appid\":\"4\",\"slotid\":\"6\",\"device\":{\"ip\":\"10.23.45.67\",\"os\":\"iOS\",\"model\":\"iPhone5,1\",\"geo\":{\"lon\":116.4736795,\"type\":1,\"lat\":39.9960702},\"osv\":\"7.0.6\",\"js\":1,\"dnt\":0,\"sh\":1024,\"s_density\":2,\"connectiontype\":2,\"dpidsha1\":\"7c222fb2927d828af22f592134e8932480637c0d\",\"didsha1\":\"1231231238912839123812\",\"macsha1\":\"2445934589348534534534\",\"ua\":\"Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_6 like Mac OS X)AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B206\",\"carrier\":\"46000\",\"language\":\"zh\",\"make\":\"Apple\",\"sw\":768,\"imei\":\"12312312312312\"}}";

        if (log.isDebugEnabled()) {
            log.debug("received from android: {}", data);
        }

        String ssp2BidderParaStr = super.generateBidderReq(data);

        if (log.isDebugEnabled()) {
            log.debug("send to ssp2bidder: {}", ssp2BidderParaStr);
        }

        if (StringUtils.isEmpty(ssp2BidderParaStr)) {
            return "failed: ssp向bidder请求参数有误";
        }
        // 请求bidder
		String bidder2sspStr = null;
		try {
			bidder2sspStr = HttpUtils.doPost(Constants.BIDDER_URL, ssp2BidderParaStr);
		}
		catch (Exception e) {
			log.info("向bidder发送请求失败: {}", e);
			return "failed";
		}

        if (log.isDebugEnabled()) {
            log.debug("received from bidder: {}", bidder2sspStr);
        }
        // 模拟bidder返回数据
        //String bidder2sspStr = "{\"id\":\"f0ec439aac8e9eb5a4c151aba5b18ebb\",\"seatbid\":[{\"adid\":\"2166\",\"impid\":\"5cdef32a55397c48b8baeb3cee0c5b5c\",\"wurl\":\"http://dsp.example.com/xxbidres?pushid=xxx&spid=xxx&adid=xxx&src=xxx&default=xxx\",\"adurl\":\"http://www.baidu.com\",\"nurl\":{\"0\":[\"url1\",\"url2\"]},\"curl\":[\"http://dsp1.com/adclick?id=123398923\"],\"adi\":\"http://www.baidu.com/pic/123.jpg\",\"admt\":1,\"adw\":320,\"adh\":50,\"ext\":{}}]}";

        if (bidder2sspStr == null) {
            return "failed: bidder无返回数据或程序解析错误";
        }
        String resp = bidderRespDispatcher.generateResp(ClientType.ANDROID, Operate.SHOW, bidder2sspStr, null);

        return resp;
    }

}
