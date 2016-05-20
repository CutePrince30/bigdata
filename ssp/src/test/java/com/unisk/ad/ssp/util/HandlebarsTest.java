package com.unisk.ad.ssp.util;

import java.io.IOException;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;

public class HandlebarsTest {

	public static void main(String[] args) throws IOException {
		//Handlebars handlebars = new Handlebars();
		//UserTemplate userTmpl = handlebars.compile("template/ssp2bidder_template").as(UserTemplate.class);
		
		UserTemplate userTmpl = TemplateUtils.getTemplate("template/ssp2bidder_template", UserTemplate.class);
		
		User user = new User("", 27);
		
		Map<String, Object> map = JsonUtils.decode(userTmpl.apply(user), Map.class);
		
		System.out.println(map);
	}
	
}
