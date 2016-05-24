package com.unisk.ad.ssp.model;

/**
 * @author CutePrince
 *
 */
public class Ssp2BidderParameter {

	private String ad_id;

	/* app信息 */
	private String app;
	
	/* imp信息 */
	private String slot_id;
	
	private Integer width;
	
	private Integer height;
	
	/* device信息 */
	private String device;

	/* user信息 */
	private String user;
	
	/**
	 * @return the ad_id
	 */
	public String getAd_id() {
		return ad_id;
	}

	/**
	 * @param ad_id the ad_id to set
	 */
	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}

	/**
	 * @return the app
	 */
	public String getApp() {
		return app;
	}

	/**
	 * @param app the app to set
	 */
	public void setApp(String app) {
		this.app = app;
	}

	/**
	 * @return the slot_id
	 */
	public String getSlot_id() {
		return slot_id;
	}

	/**
	 * @param slot_id the slot_id to set
	 */
	public void setSlot_id(String slot_id) {
		this.slot_id = slot_id;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
}
