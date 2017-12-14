package com.goldcard.nbiot.manager.netty.handler.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldcard.nbiot.common.model.DownCustomDataPacket;
import com.goldcard.nbiot.common.model.SonDataPacket;
import com.goldcard.nbiot.common.util.ByteUtil;
import com.goldcard.nbiot.common.util.CheckUtil;
import com.goldcard.nbiot.manager.notify.NotifyEvent;
import com.goldcard.nbiot.manager.redis.JedisCacheTools;
import com.goldcard.nbiot.manager.util.ChannelInfo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：HwNorthClientHandler.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月13日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
@Component
@Sharable
@Scope("prototype")
public class NorthClientHandler extends SimpleChannelInboundHandler<byte[]>{
    
    public  static Logger logger = LoggerFactory.getLogger(NorthClientHandler.class);
    
    private static final byte[] begin_bytes_1_6 = new byte[]{0x7A , 0x72} ;
    
    @Resource
    private HwSouthClientHandler hwSouthClientHandler;
    
    //设备id
    private static String DEVICE_ID="deviceId";
    
    private static final AttributeKey<Map<String,Object>> arrtibuteKey = AttributeKey.valueOf(DEVICE_ID);
    
    //private static String DECODE_STRING = "decodeFromString";
    
    private static String IS_SUB_FRAME = "isSubFrame";
    
    private static String MSG = "msg";
    
    //private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
    
    @Autowired
    private ApplicationContext applicationContext;
    
    //分帧大小 
//    private static int DATA_SIZE = 180;
    
    @Value("#{config[data_size]}")
    private String dataSize;
    
    
    //private Map<Object, Object> params=new HashMap<Object,Object>();
    

    //初始化
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("初始化channel：" + Thread.currentThread().getName() + " deviceId:" + (String)getAttribute(ctx, DEVICE_ID));
        //缓存通道信息
        //logger.info("通道：" + ctx.channel().id()+",设备id:"+getAttribute(ctx, DEVICE_ID));
        //ChannelInfo.setUpChannel(getAttribute(ctx,DEVICE_ID),ctx.channel());
        //sendCommand(ByteUtil.hexStringToBytes((String)params.get(DECODE_STRING)), ctx);
    }
    
    //销毁
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        //移除通道缓存
        logger.debug(Thread.currentThread().getName());
        ChannelInfo.removeUpChannel((String)getAttribute(ctx,DEVICE_ID));
        logger.info("通道关闭,  deviceId:" + (String)getAttribute(ctx, DEVICE_ID));
    }
    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("通讯异常：" + cause.getMessage(),cause);
        ctx.channel().close();
        
    }
    //发送信息
    /*private void sendCommand(byte[] commandByte , ChannelHandlerContext ctx){
        if(commandByte != null && commandByte.length > 0){
            logger.info(" client send comamnd : "+ByteUtil.byteToHex(commandByte));
            logger.info(Arrays.toString(commandByte));
            ByteBuf out = ctx.alloc().buffer(commandByte.length);
            out.writeBytes(commandByte);
        	ctx.channel().writeAndFlush(out);
        }
    }*/
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent ){
            logger.error("userEventTriggered 【"+ (String)getAttribute(ctx, DEVICE_ID) +"】  IdleStateEvent IdleState.ALL_IDLE TimeOut .................");
            //移除通道缓存
            ChannelInfo.removeUpChannel((String)getAttribute(ctx,DEVICE_ID));
            IdleStateEvent event = (IdleStateEvent) evt;
             if (event.state().equals(IdleState.READER_IDLE)) {
                 ctx.channel().close();
             } 
             if (event.state().equals(IdleState.ALL_IDLE)) {
                 ctx.channel().close();
             }
        }
        
        super.userEventTriggered(ctx, evt);
    }
    
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    	super.channelRegistered(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,byte[] msg) throws Exception {
    	
        if(msg==null||msg.length<1){
            return ;
        }
        
        logger.info("业务系统下发的数据："+ByteUtil.byteToHex(msg));
        //Long time = System.currentTimeMillis();
        //String startTime = dateFormat.format(new Date());
        //判断是否需要进行分帧下发
        if (!(boolean)getAttribute(ctx,IS_SUB_FRAME)) {
        	logger.debug("下发不分帧数据");
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put(MSG, msg);
			paraMap.put(DEVICE_ID, (String)getAttribute(ctx,DEVICE_ID));
			
			hwSouthClientHandler.sendMsgToIot(paraMap);
			
			//String endTime = dateFormat.format(new Date());
			//logger.debug("性能测试数据下行｜" + (String)getAttribute(ctx,DEVICE_ID) + "｜开始时间：" + startTime + "｜结束时间：" + endTime + "｜耗时：" + (System.currentTimeMillis() - time));
			logger.debug("设备: " + (String)getAttribute(ctx,DEVICE_ID) + "指令:" + ByteUtil.byteToHex(msg) +"下发结束 ");
        } else {
        	logger.debug("下发分帧数据");
	        final DownCustomDataPacket downCustomDataPacket = getMessage(msg,(String)getAttribute(ctx,DEVICE_ID));
	        
	        String deviceId = (String) getAttribute(ctx,DEVICE_ID);
	        JedisCacheTools.addObjectToJedis(deviceId + "_command", downCustomDataPacket, DownCustomDataPacket.class, 180);
			
//		    Map<String, SouthClientHandler> handlers = (Map<String, SouthClientHandler>) SpringContextHolder.getApplicationContext().getBean("handlerMap");
//	    	final Map<String, Object> params = new HashMap<String, Object>();
//	    	DownCustomDataPacket downData = (DownCustomDataPacket) JedisCacheTools.getObjectFromJedis(deviceId);
	    	NotifyEvent<String> notifyEvent = new NotifyEvent<String>(deviceId, deviceId, deviceId);
	    	applicationContext.publishEvent(notifyEvent);
	    	/*if(downData.isState()){
	    		logger.info("数据包已经发送完毕");
	    		return;
	    	}
	    	List<SonDataPacket> dataList=downData.getDataPacket();
	    	for(SonDataPacket dataPacket:dataList){
	    		if(dataPacket.isState()){
	    			continue;
	    		}
	    		params.put(MSG, dataPacket.getMsg());
	    		params.put(DEVICE_ID, downData.getDeviceId());
	            dataPacket.setSendTime(new Date());
	    	    handlers.get(downData.getSource()).sendMsgToIot(params);
	    	    break;
	    	}*/
        }
    }
    
    
    /*public void initData(Map<Object, Object> ps){
        this.params = ps;
    }*/

    
    
    /**
     * 根据配置的长度，进行截取数据包
     * @param msg 长报文
     * @return
     */
	public DownCustomDataPacket getMessage(byte[] msg, String deviceId) {
		DownCustomDataPacket downCustomDataPacket = new DownCustomDataPacket();
		int length = msg.length;
		int dataLen = Integer.valueOf(dataSize);
		List<SonDataPacket> dataList = new ArrayList<SonDataPacket>();
		byte[] code = new byte[2];
		// 固定开头校验
		byte[] head = Arrays.copyOfRange(msg, 0, 2);
		//判断下发指令类型
		if(msg.length == 13) { //dtu
			code = Arrays.copyOfRange(msg,0,2);
		} else if (!(head == null || head.length != 2 || head[0] != begin_bytes_1_6[0] || head[1] != begin_bytes_1_6[1])) { //1.6
			code = Arrays.copyOfRange(msg, 3, 5);
		} else { //3.0
			code = Arrays.copyOfRange(msg, 8, 10);
		}
		int index = 0;
		int i = length / dataLen + 1;
		int begin = 0;
		int end = 0;
		for (int k = 1; k <= i; k++) {
			if (length / dataLen == 0) {
				dataLen = length % dataLen;
			}
			byte[] data = new byte[dataLen + 10];
			end = begin + dataLen;
			System.arraycopy(msg, begin, data, 9, dataLen);
			length -= dataLen;
			begin = end;
			data = packHead(data, code, ByteUtil.hexToByte(i), ByteUtil.hexToByte(k), dataLen);
			SonDataPacket sonDataPacket = new SonDataPacket();
			sonDataPacket.setCurrentCount(k);
			sonDataPacket.setIndex(index);
			sonDataPacket.setState(false);
			sonDataPacket.setRetryCount(new AtomicInteger(0));
			sonDataPacket.setMsg(data);
			dataList.add(sonDataPacket);
		}
		downCustomDataPacket.setDataPacket(dataList);
		downCustomDataPacket.setDeviceId(deviceId);
		downCustomDataPacket.setSource("HWIOT");
		downCustomDataPacket.setTotalCount(i);
		downCustomDataPacket.setState(false);
		return downCustomDataPacket;
	}
 
    /**
     * 打包头尾
     * @param data  主数据
     * @param code  指令code
     * @param sum   一共多少帧
     * @param current 当前多少帧
     * @param dataCount  数据域长度
     * @return
     */
	public byte[] packHead(byte[] data, byte[] code, byte sum, byte current, int dataCount) {

		byte[] len = int2Byt(dataCount);
		
//		byte[] head = { 0x6B, 0x62, 0x4A, 0x4B, 0x01, sum, current, len[0], len[1] };
		 byte[] head = {0x6B,0x62,code[0],code[1],0x01,sum,current,len[0], len[1]};
		System.arraycopy(head, 0, data, 0, 9);
		// cs校验位
		byte[] need_cs = Arrays.copyOfRange(data, 4, data.length - 1);
		byte cs = CheckUtil.getCS(need_cs);
		data[data.length - 1] = cs;
		logger.info("打包后的数据为：" + ByteUtil.byteToHex(data));
		return data;
	}
 
	public static byte[] int2Byt(int x) {
		byte[] bb = new byte[2];
		bb[0] = (byte) (x >> 8);
		bb[1] = (byte) (x >> 0);
		return bb;
	}
	
	
	@SuppressWarnings({"unchecked" })
	private <T> T getAttribute(ChannelHandlerContext ctx, String key){
		return (T)getAttribute(ctx.channel(), key);
	}
	
	@SuppressWarnings({ "unchecked" })
	private <T> T getAttribute(Channel channel, String key){
		Attribute<Map<String,Object>> attribute = channel.attr(arrtibuteKey);
		Map<String,Object> map = attribute.get();
		if(map == null){
			logger.info("netty channel 获取的map属性为空", Thread.currentThread());
			return null;
		}
		Object result = map.get(key);
		if(result == null){
			logger.info("netty channel 获取的"+ key +"属性为空");
		}
		return (T)result;
	}

//	public static void main(String[] args) {
//		
//		for(int i=0;i<3;i++){
//	       NotifyEvent<String> notifyEvent = new NotifyEvent<String>(""+i,""+i);
//           EventBusFactory.build().register(NotifyEventListener.class);
//           EventBusFactory.build().postsEvent(notifyEvent);
//		}
//	}
}
