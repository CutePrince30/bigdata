package com.unisk.ad.ssp.config;

public class Constants extends ConfigurableConstants {

	static {
		init("constants.properties");
	}

	public static final int SUCCESS_CODE = 200;
	public static final int FAILED_CODE = 500;

	public static final String EMPTY_STRING = "";

	public static final String BIDDER2SSP_EXAMPLE = "{\"id\":\"f0ec439aac8e9eb5a4c151aba5b18ebb\",\"seatbid\":[{\"adid\":\"2166\",\"impid\":\"5cdef32a55397c48b8baeb3cee0c5b5c\",\"wurl\":\"http://dsp.example.com/xxbidres?pushid=xxx&spid=xxx&adid=xxx&src=xxx&default=xxx\",\"adurl\":\"http://www.baidu.com\",\"nurl\":{\"0\":[\"url1\",\"url2\"]},\"curl\":[\"http://dsp1.com/adclick?id=123398923\"],\"adi\":\"http://www.baidu.com/pic/123.jpg\",\"admt\":1,\"adw\":320,\"adh\":50,\"ext\":{}}]}";;

	public static final String BIDDER_URL = getProperty("bidder_url", "");

	public static final String CLICK_JS = getProperty("click_js", "");
	public static final String SHOW_JS = getProperty("show_js", "");

	public static final String WEB2SSP_SHOW_INFO_URL = getProperty("web2ssp_show_info_url", "");
	public static final String SSP_IP = getProperty("ssp_ip", "");

}