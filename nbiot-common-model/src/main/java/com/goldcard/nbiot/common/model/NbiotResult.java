package com.goldcard.nbiot.common.model;

import java.io.Serializable;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：NbiotResult.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月10日
 * 
 * 修改标识：
 * 修改描述：
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public class NbiotResult<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8858256667463019399L;
    
    //结果码
    private String resultCode;
    
    
    //结果描述
    private String resultMsg;
    
    //成功标识
    private boolean success;
    
    //结果数据
    private T  data;

    
    /**
     * @return the resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    
    /**
     * @param resultCode the resultCode to set
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    
    /**
     * @return the resultMsg
     */
    public String getResultMsg() {
        return resultMsg;
    }

    
    /**
     * @param resultMsg the resultMsg to set
     */
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    
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
     * @return the data
     */
    public T getData() {
        return data;
    }

    
    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }
    
    
    

}
