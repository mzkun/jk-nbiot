package com.goldcard.nbiot.manager.activemq.handler.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import com.goldcard.nbiot.common.util.ByteUtil;
import com.goldcard.nbiot.manager.activema.message.JmsMessage;
import com.goldcard.nbiot.manager.activemq.handler.Handler;
import com.goldcard.nbiot.manager.encoder.CommandEncoder;
import com.goldcard.nbiot.manager.netty.decoder.CommandDecoder;
import com.goldcard.nbiot.manager.netty.handler.impl.NorthClientHandler;
import com.goldcard.nbiot.manager.util.ChannelInfo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：NbiotNorthHandler.java
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
@Component
//@Scope("prototype")
public class NbiotNorthHandler extends ApplicationObjectSupport implements Handler {
    
    
    private   Logger logger = LoggerFactory.getLogger(NbiotNorthHandler.class);
    
    //采集系统host
    @Value("#{config[collect_url]}")
    private String host;
    
    //采集系统端口
    @Value("#{config[collect_port]}")
    private String port;
    
    //超时时间
    @Value("#{config[time_out]}")
    private String clientTimeOut;
    
    @Value("#{config[netty_thread_size]}")
    private int nettyThreadSize;
    
    private Bootstrap b;
    
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
    
    public void sendMessage(JmsMessage message) {
		this.execute(message);
	}
    
    @PostConstruct
    private void startNetty(){
    	EventLoopGroup workGroup = new NioEventLoopGroup(nettyThreadSize);
        try{
            b = new Bootstrap();
            b.group(workGroup)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.SO_KEEPALIVE, true)  
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new IdleStateHandler(0,0,Integer.valueOf(clientTimeOut),TimeUnit.SECONDS));
                    ch.pipeline().addLast(new CommandEncoder());
                    ch.pipeline().addLast(new CommandDecoder());
                    ch.pipeline().addLast((NorthClientHandler)getApplicationContext().getBean("northClientHandler"));
                } 
            });
            logger.info("--------------------------------------netty启动成功------------------------------------------");
            //ChannelFuture f = b.connect(host, Integer.valueOf(port)).sync();
            //f.channel().closeFuture().sync(); 
        }catch(Exception e){
            logger.error("socket client connect method exception ",e);
        }finally{
            //logger.info("workGroup shutdown ..................................................>>");
            //workGroup.shutdownGracefully();
        }
    }
	
    @Override
    public void execute(final JmsMessage message) {
    	ChannelFuture future = b.connect(host, Integer.valueOf(port));
    	final AtomicBoolean sendFlag = new AtomicBoolean(false);
    	
    	
    	future.addListener(new ChannelFutureListener(){
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(!future.isSuccess()){
					logger.error("netty connect to host【"+ host +"】:port【"+ port +"】 fail");
					return;
				}
		        if(sendFlag.compareAndSet(false, true)){
		        	//sendCommand(ByteUtil.hexStringToBytes((String)message.getValues().get("decodeFromString")),future.channel());
		        	connectionSuccess(message, future);
		        }
			}
    	});
    	
    	if(future.isDone()){
    		if(!future.isSuccess()){
				logger.error("netty connect to host【"+ host +"】:port【"+ port +"】 fail");
				return;
			}
    		if(sendFlag.compareAndSet(false, true)){
    			//sendCommand(ByteUtil.hexStringToBytes((String)message.getValues().get("decodeFromString")),future.channel());
    			connectionSuccess(message, future);
    		}
    	}
    	
    	/*final NorthClientHandler northClientHandler = (NorthClientHandler)getApplicationContext().getBean("northClientHandler");
        northClientHandler.initData(message.getValues());
        logger.info(Thread.currentThread().getName());
        new Thread(new Runnable(){
            @Override
            public void run(){
                EventLoopGroup workGroup = new NioEventLoopGroup(1);
                try{
                    Bootstrap b = new Bootstrap();
                    b.group(workGroup)
                     .channel(NioSocketChannel.class)
                     .option(ChannelOption.SO_KEEPALIVE, true)  
                     .option(ChannelOption.TCP_NODELAY, true)
                     .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(0,0,Integer.valueOf(clientTimeOut),TimeUnit.SECONDS));
                            ch.pipeline().addLast(new CommandEncoder());
                            ch.pipeline().addLast(new CommandDecoder());
                            ch.pipeline().addLast(northClientHandler);
                        } 
                    });
                    
                    ChannelFuture f = b.connect(host, Integer.valueOf(port)).sync();
           
                    f.channel().closeFuture().sync(); 
                    
                }catch(Exception e){
                    logger.error("socket client connect method exception ");
                }finally{
                    logger.info("workGroup shutdown ..................................................>>");
                    workGroup.shutdownGracefully();
                }
            }
        }).start();*/
        
    }

    /**
     * 通道连接成功执行
     * @param message
     * @param future
     */
    private void connectionSuccess(JmsMessage message, ChannelFuture future){
    	//缓存通道
    	ChannelInfo.setUpChannel((String)message.getValues().get("deviceId"),future.channel());
    	//设置d属性值
    	future.channel().attr(AttributeKey.valueOf("deviceId")).set(message.getValues());
    	sendCommand(ByteUtil.hexStringToBytes((String)message.getValues().get("decodeFromString")),future.channel());
    	logger.info("性能测试数据上行 dtu｜" + ((String)message.getValues().get("deviceId")) + "｜开始时间：" + format.format(new Date((Long)message.getValues().get("time"))) + "｜结束时间：" + format.format(new Date()) + "｜耗时：" + (System.currentTimeMillis() - (Long)message.getValues().get("time")));
    }
    
    //发送消息
    private void sendCommand(byte[] commandByte , Channel channel){
        if(commandByte != null && commandByte.length > 0){
            logger.info(" client send comamnd : "+ByteUtil.byteToHex(commandByte));
            logger.debug(Arrays.toString(commandByte));
            ByteBuf out = channel.alloc().buffer(commandByte.length);
            out.writeBytes(commandByte);
            channel.writeAndFlush(out);
        }
    }
}
