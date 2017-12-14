package com.goldcard.nbiot.manager.netty.handler.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSON;
import com.goldcard.nbiot.common.enums.FunctionCodeEnum;
import com.goldcard.nbiot.common.model.DeviceInfo;
import com.goldcard.nbiot.common.model.NBUrl;
import com.goldcard.nbiot.common.model.Platform;
import com.goldcard.nbiot.common.model.SouthCommandData;
import com.goldcard.nbiot.common.model.SouthParas;
import com.goldcard.nbiot.common.model.SouthPostData;
import com.goldcard.nbiot.manager.device.service.DeviceService;
import com.goldcard.nbiot.manager.device.service.ServerService;
import com.goldcard.nbiot.manager.netty.handler.SouthClientHandler;
import com.goldcard.nbiot.manager.platform.service.PlatformService;
import com.goldcard.nbiot.manager.redis.JedisCacheTools;
import com.goldcard.nbiot.manager.url.service.NBUrlService;
import com.goldcard.nbiot.manager.util.HttpClientUtil;
import com.goldcard.nbiot.manager.util.HttpsClientUtil;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：HwSouthClientHandler.java
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
public class HwSouthClientHandler implements SouthClientHandler {
    
    public  static Logger logger = LoggerFactory.getLogger(HwSouthClientHandler.class);
    
    @Resource
    public ServerService serverService;
    
    @Resource
    public PlatformService platformService;
    
    @Resource
    public NBUrlService nbUrlService;
    
    @Resource
    public DeviceService deviceService;
    
    @Resource
    private ThreadPoolTaskExecutor sendToIotExecutor;
    
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
    
	@Override
    public String sendMsgToIot(final Map<String, Object> params) throws Exception {
        sendToIotExecutor.execute(new Runnable() {
			public void run() {
				try {
					Long time = System.currentTimeMillis();
					String startTime = dateFormat.format(new Date());
					String deviceId = (String) params.get("deviceId");
					logger.debug("通过IoT平台下发指令到设备 " + deviceId);
					DeviceInfo deviceInfo = (DeviceInfo) JedisCacheTools.getObjectFromJedis(deviceId);
					if (null == deviceInfo) {
						deviceInfo = deviceService.selectByDevice(deviceId);
						if (null == deviceInfo) {
							logger.info("【HwSouthClientHandler】设备" + deviceId + "不存在, 数据下发失败");
						} else {
							//将设备信息添加到redis缓存 中
							JedisCacheTools.addObjectToJedis(deviceId, deviceInfo, DeviceInfo.class, 0);
						}
					}
			    	logger.debug("设备 " + deviceId + " 的平台编码：" + deviceInfo.getPlatform());
			    	
			    	Platform platform = (Platform) JedisCacheTools.getObjectFromJedis(deviceInfo.getPlatform());
					if (null == platform){
						platform = platformService.getPlatformByCode(deviceInfo.getPlatform());
						//将平台信息添加到redis缓存中
						JedisCacheTools.addObjectToJedis(deviceInfo.getPlatform(), platform, Platform.class, 0); 
					}
					
					Map<String, String> condition = getCondition(platform, platform.getCode(), FunctionCodeEnum.POST_COMMAND_URL.getCode());
					Map<String, String> condition2 = getCondition(platform, platform.getCode(), FunctionCodeEnum.CALLBACK_URL.getCode());
					
			        //获取http请求URL
			        String accessToken = JedisCacheTools.getStringFromJedis(condition.get("platformDes"));
			        String appKey = platform.getApp_key();
			        String postCommandUrl = MessageFormat.format(condition.get("url"), deviceId);
			        String callBackUrl = condition2.get("url");
			        byte[] msg = (byte[]) params.get("msg");
			        
			        
			        Map<String, String> paramsMap = new HashMap<String, String>();
			        paramsMap.put("app_key", appKey);
			        paramsMap.put("Authorization","Bearer " + accessToken);
			        //将数据做Base64加密处理,并转为json格式
			        String encodeMsg = Base64Utils.encodeToString(msg);
			        Map<String, String> bodyParams = new HashMap<String, String>();
			        paramsMap.put("content", toJsonStr(encodeMsg, callBackUrl));
			        //调用IOT平台接口，将处理后的数据发送到IOT
			        String respData = null;
			        if ("http:".equals(postCommandUrl.substring(0, 5))){
			            respData = HttpClientUtil.doHttpPostReq(postCommandUrl, bodyParams, paramsMap);
			        } else if ("https".equals(postCommandUrl.substring(0, 5))){
			        	HttpsClientUtil clientUtil = new HttpsClientUtil();
			        	clientUtil.initSSLConfigForTwoWay();
			            respData = clientUtil.doHttpPostReq(postCommandUrl, paramsMap, "UTF-8");
			        }
			        //logger.info("性能测试数据下行｜" + deviceId + "调用IoT接口结束");
			        String endTime = dateFormat.format(new Date());
			        logger.info("性能测试数据下行｜" + deviceId + "｜开始时间：" + startTime + "｜结束时间：" + endTime + "｜耗时：" + (System.currentTimeMillis() - time));
			        logger.info("【HwSouthClientHandler】调用iot平台接口返回结果:"+respData);
				} catch (Exception e) {
					logger.error("发送消息到iot出错",e);
				}
			}
		});
        return  "{\"succ_code\":\"success\"}";
    }
    
    
    private  String toJsonStr(String msg, String callBackUrl) {
        SouthPostData southPostData=new SouthPostData();
        southPostData.setCallbackUrl(callBackUrl);
        southPostData.setExpireTime(0); //消息失效时间，平台不再依据此参数做为判断消息是否立即下发的标志
        SouthCommandData southCommandData=new SouthCommandData();
        southCommandData.setServiceId("Query");
        southCommandData.setMethod("QUERY_DATA");
        SouthParas paras=new SouthParas();
        paras.setRawData(msg);
        southCommandData.setParas(paras);
        southPostData.setCommand(southCommandData);
        String result = JSON.toJSONString(southPostData);
        logger.info("下发参数：" + result);
        return result;
    }
    
    /**
     * 获取条件
     * @param platform
     * @param code
     * @param funCode
     * @return
     */
	private Map<String, String> getCondition(Platform platform, String code, String funCode){
		Map<String, String> condition = new HashMap<String, String>();
		String platformDes = platform.getPlatformDes();
		condition.put("platformDes", platformDes);
		
		NBUrl nbUrl = (NBUrl) JedisCacheTools.getObjectFromJedis(platformDes+funCode);
		if (null == nbUrl) {
			List<NBUrl> nbUrls = nbUrlService.getUrlByPlatform(code);
			
			for(int i = 0; i < nbUrls.size(); i++) {
				if (funCode.equals(nbUrls.get(i).getFun_code())){
					nbUrl = nbUrls.get(i);
					//将平台信息添加到redis缓存中
					JedisCacheTools.addObjectToJedis(platformDes+nbUrl.getFun_code(), nbUrl, NBUrl.class, 0); 
				}
			}
		}
		if (null != nbUrl) {
			condition.put("url", nbUrl.getUrl());
		}
		return condition;
	}

}
