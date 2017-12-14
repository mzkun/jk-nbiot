package com.goldcard.nbiot.common.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：LoginUserInfo.java
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
public class LoginUserInfo  implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1452391474451199177L;
    //用户名
    private String userName;
    //密码
    private transient String password;
    
    private String imageCode;

    
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    
    /**
     * @return the imageCode
     */
    public String getImageCode() {
        return imageCode;
    }

    
    /**
     * @param imageCode the imageCode to set
     */
    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
