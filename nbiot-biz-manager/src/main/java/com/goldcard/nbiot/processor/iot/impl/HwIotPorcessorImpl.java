package com.goldcard.nbiot.processor.iot.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goldcard.nbiot.common.enums.ResultCodeEnum;
import com.goldcard.nbiot.common.exception.NbiotException;
import com.goldcard.nbiot.common.model.DownCustomDataPacket;
import com.goldcard.nbiot.common.model.NbiotResult;
import com.goldcard.nbiot.common.model.SonDataPacket;
import com.goldcard.nbiot.common.util.ByteUtil;
import com.goldcard.nbiot.common.util.CheckUtil;
import com.goldcard.nbiot.manager.activema.message.JmsMessage;
import com.goldcard.nbiot.manager.activemq.handler.impl.NbiotNorthHandler;
import com.goldcard.nbiot.manager.activemq.producer.Producer;
import com.goldcard.nbiot.manager.context.NBIotContext;
import com.goldcard.nbiot.manager.enums.BizTypeEnum;
import com.goldcard.nbiot.manager.enums.NotifyTypeEnum;
import com.goldcard.nbiot.manager.holder.SpringContextHolder;
import com.goldcard.nbiot.manager.netty.handler.SouthClientHandler;
import com.goldcard.nbiot.manager.netty.handler.impl.HwSouthClientHandler;
import com.goldcard.nbiot.manager.notify.NotifyEvent;
import com.goldcard.nbiot.manager.redis.JedisCacheTools;
import com.goldcard.nbiot.processor.iot.IOTProcessor;

import redis.clients.jedis.Jedis;

/**
 * ---------------------------------------------------------------- 
 * Copyright * (C) 2010 浙江金卡股份有限公司 版权所有。
 * 
 * 文件名：HWIOTPorcessorImpl.java 
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月10日
 * 
 * 修改标识： 修改描述： 
 * 
 * ----------------------------------------------------------------
 */
@Component
public class HwIotPorcessorImpl implements IOTProcessor {

	private Logger logger = LoggerFactory.getLogger(HwIotPorcessorImpl.class);

	@Resource
	private Producer<Boolean> producer;

	@Resource
	private HwSouthClientHandler hwSouthClientHandler;

	@Resource
	private NbiotNorthHandler nbiotNorthHandler;
	
	@Autowired
    private ApplicationContext applicationContext;
	
	private static final Map<String, String> cacheMap = new ConcurrentHashMap<String, String>();
	
	private static final byte[] begin_bytes = new byte[]{0x7A , 0x72} ;
	
	private static final byte[] begin_bytes_sub = new byte[]{0x6B , 0x62} ;
	
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");

	@Override
	public NbiotResult<Void> doProcess(JsonNode jsonNode,Map<Object, Object> extendMap) {
		logger.debug("接收到的json串为：" + jsonNode.toString());
		
		// 设备id
		String deviceId = jsonNode.get("deviceId").asText();
		
		//如果是结果通知不需要处理
		// 通讯类型
		String notifyType = null;
		if (null == jsonNode.get("notifyType") && null != jsonNode.get("result")){
			return null;
		} else {
			notifyType = jsonNode.get("notifyType").asText();
		}

		Map<String, String> jsonParamMap = new HashMap<String, String>();

		buildJsonParamMap(notifyType, jsonNode, jsonParamMap);

		if (!isSkip(deviceId, jsonParamMap.get("eventTime"))) {
			//Long time = System.currentTimeMillis();//当前时间
			//String startTime = format.format(new Date());
			// dosend
			cacheMap.put(deviceId, jsonParamMap.get("eventTime"));
			sendMsg(jsonParamMap.get("rawData"), deviceId);
			String endTime = format.format(new Date());
			//logger.debug("性能测试数据上行｜" + deviceId + "｜开始时间：" + startTime + "｜结束时间：" + endTime + "｜耗时：" + (System.currentTimeMillis() - time));
		}
		NbiotResult<Void> result = new NbiotResult<Void>();
		result.setResultCode("OK");
		result.setResultMsg("处理成功");
		result.setSuccess(true);
		return result;
	}

	/**
	 * 是否跳过不处理
	 * 
	 * @param deviceId
	 * @param eventTime
	 * @return
	 */
	private boolean isSkip(String deviceId, String eventTime) {
		if (cacheMap.containsKey(deviceId)&& cacheMap.get(deviceId).equals(eventTime)) {
			logger.info("华为iot平台 deviceId:" + deviceId + ",eventTime:"+ eventTime + "数据已经处理，跳过");
			return true;
		}
		return false;
	}
	
	/**
	 * 一条上报数据华为平台会推送两条报文，notifyType=deviceDataChanged为单条数据上报，notifyType=
	 * deviceDatasChanged为批量数据上报，此处进行判断处理， （由于不是每次都推送两条）两条只处理一条即可。
	 * 
	 * @param notifyType
	 * @param jsonNode
	 * @param params
	 */
	private void buildJsonParamMap(String notifyType, JsonNode jsonNode,Map<String, String> params) {

		switch (NotifyTypeEnum.findEnumByCode(notifyType)) {
		case DEVICE_DATA_CHANGED:
			JsonNode serviceNode = jsonNode.get("service");
			params.put("rawData", serviceNode.get("data").get("rawData").asText());
			params.put("eventTime", serviceNode.get("eventTime").asText());
			break;
		case DEVICE_DATAS_CHANGED:
			ObjectNode jsonObj = (ObjectNode) jsonNode.get("services").get(0);
			params.put("rawData", jsonObj.get("data").get("rawData").asText());
			params.put("eventTime", jsonObj.get("eventTime").asText());
			break;
		default:
			break;
		}
	}

	/**
	 * 往采集系统发送消息
	 * 
	 * @param rawData
	 * @param deviceId
	 */
	private void sendMsg(String rawData, String deviceId) {
		final NBIotContext iotContext;
		try {
			
			iotContext = resolveData(rawData, deviceId);
			
			if (!iotContext.isSubFrame()) {
				// 采集平台发送
				send(iotContext, iotContext.isSubFrame());
				return;
			}

			// 处理从iot端的应答
			doFromIotReply(iotContext);
			
			if (!iotContext.isAnswer()) {
				// 给iot平台应答
				replyToIot(iotContext);
				
				// 采集平台发送
				send(iotContext);
			}

		} catch (Exception e) {
			if(e instanceof NbiotException){
				NbiotException ne=(NbiotException)e;
				logger.error("【HwIotPorcessorImpl】处理失败:errorCode="+ne.getErrorCode()+";errorDesc="+ne.getErrorMsg());
			}
			logger.error("【HwIotPorcessorImpl】处理失败，"+e);
		}
	}

	/**
	 * 
	 * @param iotContext
	 */
	private void doFromIotReply(final NBIotContext iotContext) throws Exception {
		if (iotContext.isAnswer()) {
			logger.debug("处理从iot端的应答" + iotContext.getData());
//			DownCustomDataPacket dcdp = (DownCustomDataPacket) CachePool.getCacheItem(iotContext.getDeviceId());
			DownCustomDataPacket downData = (DownCustomDataPacket) JedisCacheTools.getObjectFromJedis(iotContext.getDeviceId() + "_command");

			List<SonDataPacket> dataList = downData.getDataPacket();

			dataList.get(iotContext.getCurrentCount() - 1).setState(true);
			JedisCacheTools.delKeyFromJedis(iotContext.getDeviceId() + "_command");
			JedisCacheTools.addObjectToJedis(iotContext.getDeviceId() + "_command", downData, DownCustomDataPacket.class, 180);
		    Map<String, SouthClientHandler> handlers=(Map<String, SouthClientHandler>) SpringContextHolder.getApplicationContext().getBean("handlerMap");
	    	final Map<String, Object> params=new HashMap<String, Object>();

	    	NotifyEvent<String> notifyEvent = new NotifyEvent<String>(iotContext.getDeviceId(), iotContext.getDeviceId(), iotContext.getDeviceId());
	    	applicationContext.publishEvent(notifyEvent);
	    	
	    	/*if(downData.isState()){
	    		logger.info("数据包已经发送完毕");
	    		return;
	    	}
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
		} else {
			//如果是多包的最后一包，重新发送上一包数据
			if (iotContext.getTotalCount() > 1 && iotContext.getCurrentCount() == iotContext.getTotalCount()) {
				NotifyEvent<String> notifyEvent = new NotifyEvent<String>(iotContext.getDeviceId(), iotContext.getDeviceId(), iotContext.getDeviceId());
		    	applicationContext.publishEvent(notifyEvent);
				/*DownCustomDataPacket downData = (DownCustomDataPacket) JedisCacheTools.getObjectFromJedis(iotContext.getDeviceId());
				if (null != downData) {
					List<SonDataPacket> dataList = downData.getDataPacket();
					final Map<String, Object> params = new HashMap<String, Object>();
					for(SonDataPacket dataPacket:dataList){
			    		if(dataPacket.isState()){
			    			break;
			    		}
			    		params.put(MSG, dataPacket.getMsg());
			    		params.put(DEVICE_ID, downData.getDeviceId());
			            dataPacket.setSendTime(new Date());
			            hwSouthClientHandler.sendMsgToIot(params);
			    	    break;
			    	}
				}*/
			} else {
				JedisCacheTools.delKeyFromJedis(iotContext.getDeviceId() + "_command");
			}
		}
	}

	/**
	 * 应答终端
	 * 
	 * @param iotContext
	 * @throws UnsupportedEncodingException
	 */
	private void replyToIot(NBIotContext iotContext) throws NbiotException {
		//当前帧不是最后一帧才进行应答，否则不应答，以实际下发的指令做为应答内容
		if (iotContext.getCurrentCount() < iotContext.getTotalCount()) {
			String result="";
			try {
				byte[] msg = buildResponseMsg(iotContext);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("msg", msg);
				params.put("deviceId", iotContext.getDeviceId());
				result = hwSouthClientHandler.sendMsgToIot(params);
			} catch (Exception e) {
				logger.error("组装应答消息失败或者调用iot平台异常超时!");
				throw new NbiotException(ResultCodeEnum.INVOK_IOT_FAIL);
			}
			JSONObject jsonObject = JSON.parseObject(result);
			if (jsonObject == null)
			{   logger.error("调用iot平台接口失败，返回结果为空");
				throw new NbiotException(ResultCodeEnum.INVOK_IOT_FAIL);
			}
			String errorCode=jsonObject.getString("error_code");
			logger.info("返回结果:errorCode="+errorCode);
			if (errorCode != null&& !StringUtils.equals(errorCode,ResultCodeEnum.HW_SERVER_ERROR.getValue())) 
		    {
				throw new NbiotException(ResultCodeEnum.INVOK_IOT_FAIL);
			}
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param iotContext
	 * @throws UnsupportedEncodingException
	 */
	private void send(final NBIotContext iotContext) throws NbiotException {
		final String deviceId = iotContext.getDeviceId();
		final String functionCode = iotContext.getFunctionCode();
		final StringBuilder key = new StringBuilder();
		key.append(deviceId).append(functionCode).append("down");
		
		final StringBuilder commandKey = new StringBuilder();
		commandKey.append(deviceId).append(functionCode).append(iotContext.getCurrentCount());
		byte[] transCode = null;
		// 数据包已经完整，发送给采集系统
		if (iotContext.getTotalCount() == 1 && iotContext.getTotalCount() == iotContext.getCurrentCount()) {
			// 往采集平台发送数据
			final String mergeData;
			try {
				//只分一包
				mergeData=iotContext.getData();
				//dtu不加80标识
				if (StringUtils.isNotBlank(mergeData) && mergeData.length() == 26) {
					sendToCollection(ByteUtil.hexStringToBytes(mergeData), deviceId, true);
				} else {
					transCode = addHeadFlag(mergeData);
					sendToCollection(transCode, deviceId, true);
				}
			} catch (Exception e) {
				logger.error("往采集平台发送业务数据失败;"+e);
				throw new NbiotException(ResultCodeEnum.SEND_DATA_TO_COLLECTION_FAIL);
			}
		}
		// 多数据包
		if (iotContext.getTotalCount() > 1 && iotContext.getTotalCount() >= iotContext.getCurrentCount()) {
			try {
				boolean isBroken = false;
				String data = StringUtils.EMPTY;
				Jedis jedis = null;
				try {
					jedis = JedisCacheTools.getJedis();
					data = iotContext.getData();
					
					if(1 == iotContext.getCurrentCount()) {
						JedisCacheTools.delKeyFromJedis(key.toString());
					}
					
					Map<Integer, String> map = (Map<Integer, String>) JedisCacheTools.getObjectFromJedis(key.toString());
					
					if(null == map) {
						map = new ConcurrentHashMap<Integer, String>();
						map.put(iotContext.getCurrentCount(), data);
						JedisCacheTools.addObjectToJedis(key.toString(), map, Map.class, 120);
					} else {
						map.put(iotContext.getCurrentCount(), data);
						JedisCacheTools.addObjectToJedis(key.toString(), map, Map.class, 120);
					}
				} catch (Exception e) {
					isBroken = true;
					logger.warn("failed: key=" + key, data, e);
					// TODO: handle exception
				} finally {
					JedisCacheTools.release(jedis, isBroken);
				}
				
				//分包全部接收完成
				if (iotContext.getTotalCount() == iotContext.getCurrentCount()) {
					Map<Integer, String> map = (Map<Integer, String>) JedisCacheTools.getObjectFromJedis(key.toString());
					String mergeData = mapJoin(map);
					if (null == mergeData) {
						logger.info("该数据包已发送");
						return;
					}
					transCode = addHeadFlag(mergeData);
					logger.info("完整数据包为：" + ByteUtil.byteToHex(transCode));
					sendToCollection(transCode, deviceId, true);
				}
			} catch (Exception e) {
				logger.error("set缓存失败;" + e);
				throw new NbiotException(ResultCodeEnum.SET_CACHE_FAIL);
			}
		}
	}
	
	/**
	 * 发送消息(不分帧)
	 * @param iotContext
	 * @param isSubFrame
	 * @throws NbiotException
	 */
	private void send(final NBIotContext iotContext, final boolean isSubFrame) throws NbiotException {
		try {
			final String deviceId = iotContext.getDeviceId();
			final String message = iotContext.getData(); 
			logger.debug("不分帧数据发送到采集平台" + message);
			sendToCollection(ByteUtil.hexStringToBytes(message), deviceId, isSubFrame);
		} catch (Exception e) {
			logger.error("往采集平台发送业务数据失败;"+e);
			throw new NbiotException(ResultCodeEnum.SEND_DATA_TO_COLLECTION_FAIL);
		}
	}
	
	/**
	 * 数据解析
	 * 
	 * @param rawData
	 * @param deviceId
	 * @return
	 */
	private NBIotContext resolveData(String rawData, String deviceId)throws Exception {
		if (rawData.contains(" ")) {
			rawData = rawData.replace(" ", "+");
		}
		logger.debug("未经过Base64解密的终端指令为：" + rawData);
		byte[] decodeFromString = Base64.decodeBase64(rawData);
		byte[] function = new byte[2];
		System.arraycopy(decodeFromString, 2, function, 0, 2);
		logger.debug("decodeFromString = " + Arrays.toString(decodeFromString));
		byte[] transCode = null;
		//判断表端上来的数据是否经过分帧
		boolean isSubFrame = isSubFrame(decodeFromString);
		
		if (isSubFrame) { //分帧
			// 23位长度代表DTU
			if (decodeFromString.length != 23) {
				logger.info("transCode = " + Arrays.toString(decodeFromString));
				logger.info("transCode = " + ByteUtil.byteToHex(decodeFromString));
			} else {
				logger.info("transCodeDTU = " + Arrays.toString(decodeFromString));
				logger.info("transCodeDTU = " + ByteUtil.byteToHex(decodeFromString));
			}
			int totalCount = Integer.parseInt(ByteUtil.byteToHex(decodeFromString[5]), 16);
			int currentCount = Integer.parseInt(ByteUtil.byteToHex(decodeFromString[6]), 16);
			String functionCode = ByteUtil.byteToHex(function);
			boolean isAnswer = decodeFromString[4] == 0x02;//1：数据帧  2：应答帧
			return new NBIotContext(totalCount, currentCount, functionCode,isAnswer, ByteUtil.byteToHex(decodeFromString), deviceId, isSubFrame);
		} else {
			// 13位长度代表DTU
			if (decodeFromString.length != 13) {
				transCode = new byte[decodeFromString.length + 1];
				System.arraycopy(decodeFromString, 0, transCode, 1,decodeFromString.length);
				transCode[0] = -128;
				logger.debug("transCode = " + Arrays.toString(transCode));
				logger.debug("transCode = " + ByteUtil.byteToHex(transCode));
			} else {
				transCode = new byte[decodeFromString.length];
				System.arraycopy(decodeFromString, 0, transCode, 0,decodeFromString.length);
				logger.debug("transCodeDTU = " + Arrays.toString(transCode));
				logger.debug("transCodeDTU = " + ByteUtil.byteToHex(transCode));
			}
			
			return new NBIotContext(ByteUtil.byteToHex(transCode), deviceId, false, isSubFrame);
		}
	}
	
	/**
	 * 判断数据是否经过分帧
	 * @param decodeFromString
	 * @return
	 */
	public static boolean isSubFrame(byte[] decodeFromString) {
		byte[] head = new byte[2];
		System.arraycopy(decodeFromString, 0, head, 0, 2);
		if (head != null && head.length == 2 && head[0] == begin_bytes[0] && head[1] == begin_bytes[1]) {
			return false;
		} else if (head != null && head.length == 2 && head[0] == begin_bytes_sub[0] && head[1] == begin_bytes_sub[1]) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 向采集平台发送数据包
	 */
	public void sendToCollection(byte[] data, String deviceId, boolean isSubFrame) 	{
		// 将指令传给采集平台，获取响应数据
		JmsMessage jmsMessage = new JmsMessage();
		jmsMessage.setBizType(BizTypeEnum.HW_NORTH_BIZ_DATA.getCode());
		jmsMessage.setSource("HWIOT");
		jmsMessage.getValues().put("decodeFromString",ByteUtil.byteToHex(data));
		jmsMessage.getValues().put("deviceId", deviceId);
		jmsMessage.getValues().put("isSubFrame", isSubFrame);
		jmsMessage.getValues().put("time", System.currentTimeMillis());
		producer.sendMsg(jmsMessage);
	}

	/**
	 * 构建应答消息
	 * 
	 * @param code
	 * @param sum
	 * @param current
	 * 
	 * 6B6246410001010101018B
     * 帧头		帧指令码	帧类型		总帧数		当前第几帧		数据区长度	数据区				校验和CS
     * 6B62		4641	02		01		01			01		01(1:成功；0:失败)	8B
	 * @return
	 */
	public byte[] buildResponseMsg(NBIotContext iotContext) {
//		byte code = ByteUtil.hexToByte(iotContext.getFunctionCode());
		byte code[] =  ByteUtil.hexStringToBytes(iotContext.getFunctionCode());
		byte sum = ByteUtil.hexToByte(iotContext.getTotalCount());
		byte current = ByteUtil.hexToByte(iotContext.getCurrentCount());
		byte[] resp = new byte[11];
		byte[] head = { 0x6B, 0x62, code[0], code[1], 0x02, sum, current, 0x00, 0x01 };
		byte[] data = {0x01};
		System.arraycopy(head, 0, resp, 0, 9);
		System.arraycopy(data, 0, resp, 9, 1);
		byte[] need_cs = Arrays.copyOfRange(resp, 4, resp.length - 1);
		byte cs = CheckUtil.getCS(need_cs);
		resp[resp.length - 1] = cs;
		logger.info("应答消息" + ByteUtil.byteToHex(resp));
		return resp;
	}
	
	/**
	 * 消息追加 80 头标识
	 * @param hexStr
	 * @return
	 */
	public byte[] addHeadFlag(String hexStr) {
		byte[] src = ByteUtil.hexStringToBytes(hexStr);
		byte[] transCode = new byte[src.length + 1];
		
		System.arraycopy(src, 0, transCode, 1,src.length);
		transCode[0] = -128;
		
		return transCode;
	}
	
	/**
	 * 组合包
	 * @param map
	 * @return
	 */
	public String mapJoin(Map map) {
		StringBuilder result = new StringBuilder();
		for (int i = 1; i <= map.size(); i++) {
			result.append(map.get(i));
		}
		logger.info("组包结果：" + result.toString());
		return result.toString();
	}
}
