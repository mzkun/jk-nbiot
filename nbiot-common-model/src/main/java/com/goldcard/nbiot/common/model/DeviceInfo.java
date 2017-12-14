package com.goldcard.nbiot.common.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：DeviceInfo.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月9日
 * 
 * 修改标识：
 * 修改描述：
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public class DeviceInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8724762994536740288L;
    
    private String id;
    
    private String deviceId;
    
    private String timeout;
    
    private String imei;
    
    private String companyCode;
    
    private String platform;
    
    private String platformDes;
    
    private String psk;
    
    private Date gmt_create;
    
    private Date gmt_modified;

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
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    /**
     * @return the timeout
     */
    public String getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    /**
	 * @return the imei
	 */
	public String getImei() {
		return imei;
	}

	/**
	 * @param imei the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
     * @return the psk
     */
    public String getPsk() {
        return psk;
    }
    
    /**
     * @param psk the psk to set
     */
    public void setPsk(String psk) {
        this.psk = psk;
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

    /**
     * @return the gmt_modified
     */
    public Date getGmt_modified() {
        return gmt_modified;
    }
    
    /**
     * @param gmt_modified the gmt_modified to set
     */
    public void setGmt_modified(Date gmt_modified) {
        this.gmt_modified = gmt_modified;
    }
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
