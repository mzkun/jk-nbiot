package com.goldcard.nbiot.common.model;

import java.io.Serializable;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：SouthPostData.java
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
public class SouthPostData implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -4902722716384146522L;
    
    
    private SouthCommandData command;
    
    private String callbackUrl;
    
    private int expireTime;

    
    /**
     * @return the command
     */
    public SouthCommandData getCommand() {
        return command;
    }

    
    /**
     * @param command the command to set
     */
    public void setCommand(SouthCommandData command) {
        this.command = command;
    }

    
    /**
     * @return the callbackUrl
     */
    public String getCallbackUrl() {
        return callbackUrl;
    }

    
    /**
     * @param callbackUrl the callbackUrl to set
     */
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    
    /**
     * @return the expireTime
     */
    public int getExpireTime() {
        return expireTime;
    }

    
    /**
     * @param expireTime the expireTime to set
     */
    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
    
}
