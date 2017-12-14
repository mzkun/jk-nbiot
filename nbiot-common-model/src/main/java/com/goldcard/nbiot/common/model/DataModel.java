package com.goldcard.nbiot.common.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：DataModel.java
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
public class DataModel implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 8582425607974342851L;

    private String gasComsumption;
    
    private String readingDate;
    
    private String signal;
    
    private String rawData;

    
    /**
     * @return the gasComsumption
     */
    public String getGasComsumption() {
        return gasComsumption;
    }

    
    /**
     * @param gasComsumption the gasComsumption to set
     */
    public void setGasComsumption(String gasComsumption) {
        this.gasComsumption = gasComsumption;
    }

    
    /**
     * @return the readingDate
     */
    public String getReadingDate() {
        return readingDate;
    }

    
    /**
     * @param readingDate the readingDate to set
     */
    public void setReadingDate(String readingDate) {
        this.readingDate = readingDate;
    }

    
    /**
     * @return the signal
     */
    public String getSignal() {
        return signal;
    }

    
    /**
     * @param signal the signal to set
     */
    public void setSignal(String signal) {
        this.signal = signal;
    }

    
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
