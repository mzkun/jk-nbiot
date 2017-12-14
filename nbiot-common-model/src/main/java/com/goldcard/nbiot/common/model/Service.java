package com.goldcard.nbiot.common.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：Service.java
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
public class Service implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 3278342761814473700L;
    
    private String serviceType;
    
    private String serviceId;
    
    private DataModel data;
    
    private String eventTime;

    
    /**
     * @return the serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    
    /**
     * @param serviceType the serviceType to set
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    
    /**
     * @return the serviceId
     */
    public String getServiceId() {
        return serviceId;
    }

    
    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    
    /**
     * @return the data
     */
    public DataModel getData() {
        return data;
    }

    
    /**
     * @param data the data to set
     */
    public void setData(DataModel data) {
        this.data = data;
    }

    
    /**
     * @return the eventTime
     */
    public String getEventTime() {
        return eventTime;
    }

    
    /**
     * @param eventTime the eventTime to set
     */
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
