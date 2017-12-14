package com.goldcard.nbiot.common.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：SouthCommandData.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月13日
 * 
 * 修改标识：
 * 修改描述：
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public class SouthCommandData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7103464205634189612L;
    
    private String serviceId;
    
    private String method;
    
    private SouthParas paras;

    
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
     * @return the method
     */
    public String getMethod() {
        return method;
    }
    
    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }
    
    
    /**
     * @return the paras
     */
    public SouthParas getParas() {
        return paras;
    }


    
    /**
     * @param paras the paras to set
     */
    public void setParas(SouthParas paras) {
        this.paras = paras;
    }


    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
