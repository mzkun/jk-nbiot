package com.goldcard.nbiot.common.enums;

/**
 * 
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：FunctionCodeEnum.java
 * 文件功能描述：url功能描述枚举类
 * 
 * 创建标识 1925 2017-04-06
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
 */
public enum FunctionCodeEnum {
	
	LOGIN_URL("login_url","登录应用"),
	REGIST_DEVICE_URL("regist_device_url","设备注册"),
	SET_DEVICE_INFO_URL("set_device_Info_url","设置设备信息"),
	DELETE_DEVICE_URL("delete_device_url","删除设备"),
	CALLBACK_URL("callback_url","回调地址"),
	SUBSCRIBE_CHANGE_URL("subscribe_change_url", "订阅变更"),
	POST_COMMAND_URL("post_command_url", "下发异步命令"),
	SEARCH_DEVICE_STATUS_URL("search_device_status_url", "查询设备激活状态 "),
    SEARCH_DEVICE_CAPABILITIES_URL("search_device_capabilities_url", "查询设备能力");

	private String code;
    private String desc;

    private FunctionCodeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

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
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
