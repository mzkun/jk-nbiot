package com.goldcard.nbiot.manager.util;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

/**
 * ----------------------------------------------------------------
 * Copyright © 2015 金卡股份
 * 版权所有。
 * 
 * 文件名：ChannelInfo.java
 * 文件功能描述：通道信息类
 *
 * @autor mzk
 * 
 * @Date 2016年11月30日 下午7:34:30
 * 
 * ----------------------------------------------------------------
 */
public class ChannelInfo {
	
	// 下行通道信息map
	public static Map<String,Channel> map=new ConcurrentHashMap<String,Channel>();
	
	// 上行通道
	public static Map<String,Channel> upMap=new ConcurrentHashMap<String,Channel>();
	
	public static Map<String, String> deviceMap=new ConcurrentHashMap<String, String>();
	/**
	 * 缓存通道信息
	 * @param channelId
	 * @param channel
	 */
	public static void setChannel(String channelId,Channel channel){
		map.put(channelId, channel);
	}
	
	/**
	 * 获取通道信息
	 * @param channelId
	 * @return
	 */
	public static Channel getChannel(String channelId){
		if(StringUtils.isEmpty(channelId)){
			return null;
		}
		Channel channel=map.get(channelId);
		return channel;
	}

	/**
	 * 移除通道缓存
	 * @param channelId
	 */
	public static void removeChannel(String channelId){
		map.remove(channelId);
	}
	
	
	/**
	 * 缓存 上行通道信息
	 * @param channelId
	 * @param channel
	 */
	public static void setUpChannel(String meterNum,Channel channel){
		upMap.put(meterNum, channel);
	}
	
	/**
	 * 获取通道信息
	 * @param channelId
	 * @return
	 */
	public static Channel getUpChannel(String meterNum){
		Channel channel=upMap.get(meterNum);
		return channel;
	}

	/**
	 * 移除通道缓存
	 * @param channelId
	 */
	public static void removeUpChannel(String meterNum){
		upMap.remove(meterNum);
	}
	
	/**
	 * 缓存设备信息
	 * @param deviceId
	 * @param meterNum
	 */
	public static void setDeviceMap(String meterNum, String deviceId){
		deviceMap.put(meterNum, deviceId);
	}
	
	/**
	 * 移除设备信息
	 * @param channelId
	 */
	public static void removeDeviceMap(String meterNum){
		deviceMap.remove(meterNum);
	}
	
	/**
	 * 获取设备信息
	 * @param meterNum
	 * @return
	 */
	public static String getDevicemap(String meterNum){
		String deviceId = deviceMap.get(meterNum);
		return deviceId;
	}
}
