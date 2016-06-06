package com.unisk.ad.ssp.config;

public class Constants extends ConfigurableConstants {

	static {
		init("constants.properties");
	}

	public static final String BIDDER_URL = getProperty("bidder_url", "");
	
}