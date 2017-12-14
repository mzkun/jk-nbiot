package com.goldcard.nbiot.common.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：Json.java
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
public class Json implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8257577486136334658L;
    
    private boolean success=false;
    
    private String msg="";
    
    private Object obj = null;
    
    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }
    
    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }
    
    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    /**
     * @return the obj
     */
    public Object getObj() {
        return obj;
    }
    
    /**
     * @param obj the obj to set
     */
    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
