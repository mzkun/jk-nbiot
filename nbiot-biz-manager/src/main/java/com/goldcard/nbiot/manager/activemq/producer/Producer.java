package com.goldcard.nbiot.manager.activemq.producer;

import com.goldcard.nbiot.manager.activema.message.JmsMessage;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：Producer.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月11日
 * 
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public interface Producer<T> {
    
    
    public T  sendMsg(final JmsMessage msg);
    

}
