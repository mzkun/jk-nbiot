package com.goldcard.nbiot.common.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：SouthParas.java
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
public class SouthParas implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1404253509206226117L;
    
    private String rawData;

    
    /**
     * @return the rawData
     */
    public String getRawData() {
        return rawData;
    }

    
    /**
     * @param rawData the rawData to set
     */
    public void setRawData(String rawData) {
        this.rawData = rawData;
    }
    

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
