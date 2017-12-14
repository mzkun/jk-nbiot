package com.goldcard.nbiot.manager.activema.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：JmsMessage.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月11日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public class JmsMessage implements Serializable {

    private static final long serialVersionUID = -3744350618277858153L;
    
    //业务类型
    private String bizType;
    
    //来源
    private String source;
    
    //承载数据
    private Map<Object, Object> values=new HashMap<Object, Object>();

    
    /**
     * @return the bizType
     */
    public String getBizType() {
        return bizType;
    }

    
    /**
     * @param bizType the bizType to set
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    
    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    
    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }


    
    /**
     * @return the values
     */
    public Map<Object, Object> getValues() {
        return values;
    }


    
    /**
     * @param values the values to set
     */
    public void setValues(Map<Object, Object> values) {
        this.values = values;
    }


    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
