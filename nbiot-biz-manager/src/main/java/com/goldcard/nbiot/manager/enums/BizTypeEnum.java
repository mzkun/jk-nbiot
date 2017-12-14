package com.goldcard.nbiot.manager.enums;

import org.apache.commons.lang.StringUtils;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：BizTypeEnum.java
 * 文件功能描述：TODO
 * 
 * 创建标识 1903 2017年2月12日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public enum BizTypeEnum {
    
    HW_NORTH_BIZ_DATA("1000","北向数据发送业务");
    
    private BizTypeEnum(String code,String remark){
        this.code=code;
        this.remark=remark;
    }
    
    
    public static BizTypeEnum findEnumByCode(String code){
        for(BizTypeEnum bt:values()){
            if(StringUtils.equals(bt.getCode(), code)){
                return bt;
            }
        }
        return null;
    }
    
    //编码
    private String code;
    //编码描述
    private String remark;
    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }


    
    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }


    
    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
