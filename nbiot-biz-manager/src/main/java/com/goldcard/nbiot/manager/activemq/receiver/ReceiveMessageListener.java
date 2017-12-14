package com.goldcard.nbiot.manager.activemq.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.goldcard.nbiot.common.util.ByteUtil;
import com.goldcard.nbiot.manager.activema.message.JmsMessage;
import com.goldcard.nbiot.manager.activemq.handler.Handler;
import com.goldcard.nbiot.manager.activemq.handler.impl.NbiotNorthHandler;
import com.goldcard.nbiot.manager.util.ChannelInfo;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：ReceiveMessageListener.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月12日
 * 
 * 修改标识：
 * 修改描述：
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
@Component
public class ReceiveMessageListener implements  MessageListener   {
    
    private   Logger logger = LoggerFactory.getLogger(ReceiveMessageListener.class);
    
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    
    private Map<String, Handler> handlers;
    
    private final static String MSG_DATA="data";
    
    private final static String BIZ_TYPE="bizType";
    
    private final static String DECODE_STRING = "decodeFromString";
    
    @Resource
    private NbiotNorthHandler nbiotNorthHandler;
    
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
    
    @Override
	public void onMessage(final Message message) {
    	try {
			logger.info("mq接收消息：" + ((MapMessage)message).getString(MSG_DATA));
		} catch (JMSException e1) {
			logger.error("从消息队列获取消息出错",e1);
		}
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				MapMessage mapMessage = (MapMessage) message;
				String bizType = StringUtils.EMPTY;
				JmsMessage jmsMessage = null;
				Channel channel = null;
				try {
					bizType = mapMessage.getString(BIZ_TYPE);
					jmsMessage = JSON.parseObject(mapMessage.getString(MSG_DATA), JmsMessage.class);
					// jmsMessage=(JmsMessage)mapMessage.getObject(MSG_DATA);
					final String deviceId = (String) jmsMessage.getValues().get("deviceId");
					
					if (!ChannelInfo.upMap.containsKey(deviceId)) {
						logger.info("设备" + deviceId + "数据初始发送");
						handlers.get(bizType).execute(jmsMessage);
					} else {
						byte[] data = ByteUtil.hexStringToBytes(jmsMessage.getValues().get(DECODE_STRING).toString());
						channel = ChannelInfo.getUpChannel(deviceId);
						logger.debug("通道：" + channel.id()+",设备id:"+deviceId);
						if (channel.isActive()) {
							logger.debug("设备" + deviceId + "数据发送");
							final long startTime = (Long)jmsMessage.getValues().get("time");
							final ChannelFuture f = channel.writeAndFlush(Unpooled.copiedBuffer(data));
							f.addListener(new ChannelFutureListener(){
								@Override
								public void operationComplete(ChannelFuture future) throws Exception {				
									assert f == future;
									//logger.info("设备" + deviceId + "数据发送成功");
									logger.info("性能测试数据上行 ｜" + deviceId + "｜开始时间：" + format.format(startTime) + "｜结束时间：" + format.format(new Date()) + "｜耗时：" + (System.currentTimeMillis() - startTime));
								}
							});
						}
					}
				} catch (JMSException e) {
					logger.error("业务类型:" + bizType + ",data:" + jmsMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    
    /**
     * @return the taskExecutor
     */
/*    public ThreadPoolTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    
    *//**
     * @param taskExecutor the taskExecutor to set
     *//*
    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }*/

    
    /**
     * @return the handlers
     */
    public Map<String, Handler> getHandlers() {
        return handlers;
    }

    
    /**
     * @param handlers the handlers to set
     */
    public void setHandlers(Map<String, Handler> handlers) {
        this.handlers = handlers;
    }
    
    

}
