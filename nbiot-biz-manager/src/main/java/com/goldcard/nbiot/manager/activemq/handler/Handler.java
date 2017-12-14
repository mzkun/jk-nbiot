package com.goldcard.nbiot.manager.activemq.handler;

import com.goldcard.nbiot.manager.activema.message.JmsMessage;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：Handler.java
 * 文件功能描述：TODO
 * 
 * 创建标识 1903 2017年2月12日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public interface Handler {
    
    /**
     * 任务处理接口
     * @param message
     */
    public void execute(final JmsMessage message);

}
