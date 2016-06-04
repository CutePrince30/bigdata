package com.unisk.ad.ssp.controller.web;

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.unisk.ad.ssp.config.ClientType;
import com.unisk.ad.ssp.config.MediaType;
import com.unisk.ad.ssp.config.Operate;

import com.unisk.ad.ssp.dispatcher.BidderRespDispatcher;
import com.unisk.ad.ssp.integrator.BidderReqIntegrator;
import com.unisk.ad.ssp.util.*;
import org.beetl.core.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sunyunjie (jaysunyun_361@163.com)
 */
@Controller
public class WebController {

    private static Logger log = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private BidderReqIntegrator bidderReqIntegrator;

    @Autowired
    private BidderRespDispatcher bidderRespDispatcher;

    @RequestMapping(value = "/show/main.js", method = RequestMethod.GET)
    @ResponseBody
    public String mainJS() throws IOException {
        Template template = TemplateUtils.getTemplate("/template/mainjs_template.beetl");
        template.binding("host", "127.0.0.1");
        return template.render();
    }

    @RequestMapping(value = "/show/info.js", method = RequestMethod.GET)
    @ResponseBody
    public String infoJS(
            @RequestParam(value = "sn", required = true) String sn,
            @RequestParam(value = "slotid", required = true) String slotid,
            @RequestParam(value = "width_page", required = false) String width_page,
            @RequestParam(value = "width_screen", required = false) String width_screen)
            throws IOException {

        String ssp2BidderParaStr = bidderReqIntegrator.generateBidderReq(MediaType.APP, null, null, slotid, null);

        System.out.println(ssp2BidderParaStr);

        // 请求bidder
//		String bidder2sspStr = null;
//		try {
//			bidder2sspStr = HttpUtils.doPost("127.0.0.1", ssp2BidderParaStr);
//		}
//		catch (Exception e) {
//			log.info("向bidder发送请求失败: {}", e);
//			return "failed";
//		}

        // 模拟bidder返回数据
        String bidder2sspStr = "{\"id\":\"f0ec439aac8e9eb5a4c151aba5b18ebb\",\"seatbid\":[{\"adid\":\"2166\",\"impid\":\"5cdef32a55397c48b8baeb3cee0c5b5c\",\"wurl\":\"http://dsp.example.com/xxbidres?pushid=xxx&spid=xxx&adid=xxx&src=xxx&default=xxx\",\"adurl\":\"http://www.baidu.com\",\"nurl\":{\"0\":[\"url1\",\"url2\"]},\"curl\":[\"http://dsp1.com/adclick?id=123398923\"],\"adi\":\"http://www.baidu.com/pic/123.jpg\",\"admt\":1,\"adw\":320,\"adh\":50,\"ext\":{}}]}";

        Map<String, Object> otherParaMap = Maps.newHashMap();
        otherParaMap.put("sn", sn);

        String resp = bidderRespDispatcher.generateResp(ClientType.WEB, Operate.SHOW, bidder2sspStr, otherParaMap);

        return resp;
    }

}
