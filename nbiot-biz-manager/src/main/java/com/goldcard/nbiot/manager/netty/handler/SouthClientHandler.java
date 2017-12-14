package com.goldcard.nbiot.manager.netty.handler;

import java.util.Map;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：SouthClientHandler.java
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
public interface SouthClientHandler  {
    
    /**
     * 北向消息发送
     * @param params
     */
    public String sendMsgToIot(Map<String, Object> params) throws Exception;

}
