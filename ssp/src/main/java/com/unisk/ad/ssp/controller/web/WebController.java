package com.unisk.ad.ssp.controller.web;

import java.io.IOException;

import com.unisk.ad.ssp.dao.adp.InfoJsMapper;
import com.unisk.ad.ssp.dao.ssp.Ssp2BidderMapper;

import org.springframework.beans.BeanUtils;
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
import com.unisk.ad.ssp.templdate.InfoJsTemplate;
import com.unisk.ad.ssp.templdate.MainJsTemplate;
import com.unisk.ad.ssp.templdate.Ssp2BidderTemplate;
import com.unisk.ad.ssp.util.TemplateUtils;

@Controller
public class WebController {

	@Autowired
	private InfoJsMapper infoJsMapper;
	
	@Autowired
	private Ssp2BidderMapper ssp2BidderMapper;

	@RequestMapping(value = "/main.js", method = RequestMethod.GET)
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

	@RequestMapping(value = "/info.js", method = RequestMethod.GET)
	@ResponseBody
	public String infoJS(
			@RequestParam(value = "sn", required = true) String sn,
			@RequestParam(value = "slotid", required = true) String slot_id,
			@RequestParam(value = "width_page", required = false) String width_page,
			@RequestParam(value = "width_screen", required = false) String width_screen)
			throws IOException {
		// 根据id去数据库中查询数据
		Ssp2BidderParameter ssp2BidderParameter = ssp2BidderMapper.selectOneBySlotId(slot_id);
		
		// 生成要发给ssp2bidder的数据
		Ssp2BidderTemplate ssp2BidderTemplate = TemplateUtils.getTemplate(
				"template/ssp2bidder_template", Ssp2BidderTemplate.class);

		String ssp2BidderParaStr = ssp2BidderTemplate.apply(ssp2BidderParameter);
		
		// 请求bidder
		/*String bidder2sspStr = HttpUtils.doPost("127.0.0.1", ssp2BidderParaStr);*/

		// 解析bidder返回的数据
		/*Map<String, Object> bidder2sspMap = JsonUtils.decode(bidder2sspStr, Map.class);*/
		
		// 根据bidder返回数据的adid查库
		String adid = "2188";
		InfoJsParameter infoJsPara1 = infoJsMapper.selectOneByAdidFromAd(adid);
		InfoJsParameter infoJsPara2 = infoJsMapper.selectOneByAdidFromStuff(adid);

		// 将结果放入一个InfoJsParameter对象中
		BeanUtils.copyProperties(infoJsPara1, infoJsPara2);

		// 生成对应json文件
		InfoJsTemplate infoJsTemplate = TemplateUtils.getTemplate(
				"template/infojs_template", InfoJsTemplate.class);

		infoJsPara2.setSn(sn);
		infoJsPara2.setPushId("123");
		
		return infoJsTemplate.apply(infoJsPara2);
	}

}
