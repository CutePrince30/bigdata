package com.unisk.ad.ssp.config;

public class Constants extends ConfigurableConstants {

	static {
		init("constants.properties");
	}

	public static final String BIDDER_URL = getProperty("bidder_url", "");

	public static final String CLICK_JS = getProperty("click_js", "");
	public static final String SHOW_JS = getProperty("show_js", "");

	public static final String WEB2SSP_SHOW_INFO_URL = getProperty("web2ssp_show_info_url", "");
	public static final String SSP_IP = getProperty("ssp_ip", "");

}