package com.unisk.ad.ssp.config;

public class Constants extends ConfigurableConstants {

	static {
		init("constants.properties");
	}

	public static final int SUCCESS_CODE = 200;
	public static final int FAILED_CODE = 500;

	public static final String EMPTY_STRING = "";
	public static final int EMPTY_INT = 0;

	public static final String BIDDER2SSP_EXAMPLE = "{\"id\":\"f0ec439aac8e9eb5a4c151aba5b18ebb\",\"seatbid\":[{\"adid\":\"2166\",\"impid\":\"5cdef32a55397c48b8baeb3cee0c5b5c\",\"wurl\":\"http://dsp.example.com/xxbidres?pushid=xxx&spid=xxx&adid=xxx&src=xxx&default=xxx\",\"adurl\":\"http://www.baidu.com\",\"nurl\":{\"0\":[\"url1\",\"url2\"]},\"curl\":[\"http://dsp1.com/adclick?id=123398923\"],\"adi\":\"http://www.baidu.com/pic/123.jpg\",\"admt\":1,\"adw\":320,\"adh\":50,\"ext\":{}}]}";;

	public static final String BIDDER_URL = getProperty("bidder_url", EMPTY_STRING);
	public static final String BIDDER_SHOWJS_URL = getProperty("bidder_showjs_url", EMPTY_STRING);
	public static final String BIDDER_CLICKJS_URL = getProperty("bidder_clickjs_url", EMPTY_STRING);

	public static final String SSP_IOS_SHOWJS_URL = getProperty("ssp_ios_showjs_url", EMPTY_STRING);
	public static final String SSP_ANDROID_SHOWJS_URL = getProperty("ssp_android_showjs_url", EMPTY_STRING);
	public static final String SSP_WEB_SHOWJS_URL = getProperty("ssp_web_showjs_url", EMPTY_STRING);

	public static final String SSP_IOS_CLICKJS_URL = getProperty("ssp_ios_clickjs_url", EMPTY_STRING);
	public static final String SSP_ANDROID_CLICKJS_URL = getProperty("ssp_android_clickjs_url", EMPTY_STRING);
	public static final String SSP_WEB_CLICKJS_URL = getProperty("ssp_web_clickjs_url", EMPTY_STRING);

	public static final String CLICK_JS = getProperty("click_js", EMPTY_STRING);
	public static final String SHOW_JS = getProperty("show_js", EMPTY_STRING);

	public static final String WEB2SSP_SHOW_INFO_URL = getProperty("web_show_info_url", EMPTY_STRING);
	public static final String SSP_IP = getProperty("ssp_ip", EMPTY_STRING);

}