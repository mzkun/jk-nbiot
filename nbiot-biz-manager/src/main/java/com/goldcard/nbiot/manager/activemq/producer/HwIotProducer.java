package com.goldcard.nbiot.manager.activemq.producer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.goldcard.nbiot.manager.activema.message.JmsMessage;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：HwIotProducer.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月11日
 * 
 * 修改标识：
 * 修改描述：
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
@Component
public class HwIotProducer implements Producer<Boolean> {
    
    private   Logger logger = LoggerFactory.getLogger(HwIotProducer.class);
    
    
    @Autowired
    private JmsTemplate jmsQueueTemplate;

    @Override
    public Boolean sendMsg(final JmsMessage msg) {
        try {
            jmsQueueTemplate.send(new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString("data", JSON.toJSONString(msg));
                    mapMessage.setString("bizType", msg.getBizType());
                    return mapMessage;
                }
            });
        } catch (Exception e) {
            logger.error("[hw_nbiot]发送消息失败,data:"+msg.toString());
            return false;
        }
        logger.info("[hw_nbiot]发送消息成功,data:"+msg.toString());
        return true;
    }
}
