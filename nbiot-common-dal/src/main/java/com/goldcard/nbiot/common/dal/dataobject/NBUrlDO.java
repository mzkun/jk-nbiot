package com.goldcard.nbiot.common.dal.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：NBUrlDO.java
 * 文件功能描述：TODO
 * 
 * 创建标识 1925 2017-04-05
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
 */
public class NBUrlDO implements Serializable{

	private static final long serialVersionUID = -7634751743622337191L;

	private String id;
	
	private String platform;
	
	private String platformDes;
	
	private String fun_code;

	private String fun_desc;
	
	private String url;
	
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
	 * @return the fun_code
	 */
	public String getFun_code() {
		return fun_code;
	}

	/**
	 * @param fun_code the fun_code to set
	 */
	public void setFun_code(String fun_code) {
		this.fun_code = fun_code;
	}

	/**
	 * @return the fun_desc
	 */
	public String getFun_desc() {
		return fun_desc;
	}

	/**
	 * @param fun_desc the fun_desc to set
	 */
	public void setFun_desc(String fun_desc) {
		this.fun_desc = fun_desc;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
