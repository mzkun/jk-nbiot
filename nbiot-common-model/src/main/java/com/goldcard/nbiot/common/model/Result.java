package com.goldcard.nbiot.common.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：Result.java
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
public class Result<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7513614043386383631L;
    
    /**状态码(1:失败 0：成功)*/
    private String state;
    /**返回信息*/
    private T message;
    
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }
    
    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
    
    /**
     * @return the message
     */
    public T getMessage() {
        return message;
    }
    
    /**
     * @param message the message to set
     */
    public void setMessage(T message) {
        this.message = message;
    }
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
