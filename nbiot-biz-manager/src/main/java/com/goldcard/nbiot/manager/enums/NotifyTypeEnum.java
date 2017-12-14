package com.goldcard.nbiot.manager.enums;
import org.apache.commons.lang.StringUtils;



/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：NotifyTypeEnum.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月10日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public enum NotifyTypeEnum {
 
    DEVICE_DATA_CHANGED("deviceDataChanged","deviceDataChanged"),
    
    DEVICE_DATAS_CHANGED("deviceDatasChanged","deviceDatasChanged")
    ;
    
    private NotifyTypeEnum(String code,String msg){
        this.code=code;
        this.msg=msg;
    }
    
    
    public static NotifyTypeEnum findEnumByCode(String code){
        for(NotifyTypeEnum ne:values()){
            if(StringUtils.equals(ne.getCode(), code)){
                return ne;
            }
        }
        return null;
    }
    
    //编码
    private String code;
    //编码描述
    private String msg;
    
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
    
    

}
