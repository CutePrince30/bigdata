package com.unisk.ad.ssp.controller.web;

import java.io.IOException;

import com.unisk.ad.ssp.dao.adp.AdpMapper;
import com.unisk.ad.ssp.dao.ssp.SspMapper;

import com.unisk.ad.ssp.util.HttpUtils;
import com.unisk.ad.ssp.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.jknack.handlebars.Handlebars;
import com.unisk.ad.ssp.model.InfoJsParameter;
import com.unisk.ad.ssp.model.MainJsParameter;
import com.unisk.ad.ssp.model.Ssp2BidderParameter;
import com.unisk.ad.ssp.template.InfoJsTemplate;
import com.unisk.ad.ssp.template.MainJsTemplate;
import com.unisk.ad.ssp.template.Ssp2BidderTemplate;
import com.unisk.ad.ssp.util.TemplateUtils;

/**
 * @author sunyj (jaysunyun_361@163.com)
 */
@Controller
public class WebController {

	private static Logger log = LoggerFactory.getLogger(WebController.class);

	@Autowired
	private AdpMapper adpMapper;

	@Autowired
	private SspMapper sspMapper;

	@RequestMapping(value = "/show/main.js", method = RequestMethod.GET)
	@ResponseBody
	public String mainJS() throws IOException {
		Handlebars handlebars = new Handlebars();
		MainJsTemplate template = null;
		try {
			template = handlebars.compile("template/mainjs_template").as(
					MainJsTemplate.class);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		if (template != null) {
			return template.apply(new MainJsParameter("127.0.0.1"));
		}
		return "";
	}

	@RequestMapping(value = "/show/info.js", method = RequestMethod.GET)
	@ResponseBody
	public String infoJS(
			@RequestParam(value = "sn", required = true) String sn,
			@RequestParam(value = "slotid", required = true) String slot_id,
			@RequestParam(value = "width_page", required = false) String width_page,
			@RequestParam(value = "width_screen", required = false) String width_screen)
			throws IOException {
		// 根据id去数据库中查询数据
		Ssp2BidderParameter ssp2BidderParameter = sspMapper.selectOneBySlotId(slot_id);

		// 生成要发给ssp2bidder的数据
		Ssp2BidderTemplate ssp2BidderTemplate = TemplateUtils.getTemplate(
				"template/ssp2bidder_template", Ssp2BidderTemplate.class);

		String ssp2BidderParaStr = ssp2BidderTemplate.apply(ssp2BidderParameter);

		// 请求bidder
//		String bidder2sspStr = null;
//		try {
//			bidder2sspStr = HttpUtils.doPost("127.0.0.1", ssp2BidderParaStr);
//		}
//		catch (Exception e) {
//			log.info("向bidder发送请求失败: {}", e);
//			return "failed";
//		}

		// 解析bidder返回的数据
//		String adid = JsonUtils.findValueAsText(bidder2sspStr, "adid");

		String adid = "2166";

		// 根据bidder返回数据的adid查库
		InfoJsParameter infoJsPara1 = adpMapper.selectOneByAdidFromAd(adid);
		InfoJsParameter infoJsPara2 = adpMapper.selectOneByAdidFromStuff(adid);

		if (infoJsPara1 != null) {
			// 将结果放入一个InfoJsParameter对象中
			infoJsPara2.setAddr(infoJsPara1.getAddr());
			infoJsPara2.setText(infoJsPara1.getText());
			infoJsPara2.setType(infoJsPara1.getType());
		}
		infoJsPara2.setSn(sn);
		infoJsPara2.setPushId("123");

		// 生成对应json文件
		InfoJsTemplate infoJsTemplate = TemplateUtils.getTemplate(
				"template/infojs_template", InfoJsTemplate.class);

		return infoJsTemplate.apply(infoJsPara2);
	}

}
