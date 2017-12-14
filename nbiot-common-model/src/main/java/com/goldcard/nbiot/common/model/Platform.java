package com.goldcard.nbiot.common.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：Platform.java
 * 文件功能描述：TODO
 * 
 * 创建标识 1925 2017-04-05
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
 */
public class Platform implements Serializable{

	private static final long serialVersionUID = 1183945972329049873L;

	private String id;
	
	private String code;
	
	private String platform;
	
	private String location;
	
	private String platformDes;
	
	private String accessToken;
	
	private String app_key;
	
	private String secret;
	
	private Date gmt_create;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPlatformDes() {
		return platformDes;
	}

	public void setPlatformDes(String platformDes) {
		this.platformDes = platformDes;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getApp_key() {
		return app_key;
	}

	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Date getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}

	@Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
