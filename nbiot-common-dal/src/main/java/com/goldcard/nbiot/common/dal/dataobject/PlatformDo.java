package com.goldcard.nbiot.common.dal.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：PlatformDo.java
 * 文件功能描述：TODO
 * 
 * 创建标识 1903 2017年4月1日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public class PlatformDo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2612173139459076715L;

	private String id;
	
	private String code;
	
	private String platform;
	
	private String platformDes;
	
	private String location;
	
	private String app_key;
	
	private String secret;
	
	private Date gmt_create;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * @return the platformDes
	 */
	public String getPlatformDes() {
		return platformDes;
	}

	/**
	 * @param platformDes the platformDes to set
	 */
	public void setPlatformDes(String platformDes) {
		this.platformDes = platformDes;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the app_key
	 */
	public String getApp_key() {
		return app_key;
	}

	/**
	 * @param app_key the app_key to set
	 */
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @return the gmt_create
	 */
	public Date getGmt_create() {
		return gmt_create;
	}

	/**
	 * @param gmt_create the gmt_create to set
	 */
	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}
	
}
