package com.goldcard.nbiot.common.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：DeviceDataFromIOT.java
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
public class DeviceDataFromIOT implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7228686892700583523L;

    private String notifyType;
    
    private String requestId;
    
    private String timestamp;
    
    private String gatewayId;
    
    private String deviceId;
    
    private Service service;//业务数据

    
    /**
     * @return the notifyType
     */
    public String getNotifyType() {
        return notifyType;
    }

    
    /**
     * @param notifyType the notifyType to set
     */
    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    
    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    
    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    
    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    
    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    
    /**
     * @return the gatewayId
     */
    public String getGatewayId() {
        return gatewayId;
    }

    
    /**
     * @param gatewayId the gatewayId to set
     */
    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
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
     * @return the service
     */
    public Service getService() {
        return service;
    }

    
    /**
     * @param service the service to set
     */
    public void setService(Service service) {
        this.service = service;
    }
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
