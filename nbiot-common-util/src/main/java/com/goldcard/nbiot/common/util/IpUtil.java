package com.goldcard.nbiot.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：DataModel.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月9日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public class IpUtil {
	private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

	/**
	 * 获取本机ip
	 * @return ip
	 */
	public static String getIp() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress address = null;
			while (interfaces.hasMoreElements()) {
				NetworkInterface ni = interfaces.nextElement();
				Enumeration<InetAddress> addresses = ni.getInetAddresses();
				while (addresses.hasMoreElements()) {
					address = addresses.nextElement();
					if (!address.isLoopbackAddress() && address.getHostAddress().indexOf(":") == -1) {
						return address.getHostAddress();
					}
				}
			}
			logger.info("getHostAddress fail");
			return null;
		} catch (Throwable t) {
			logger.error("getHostAddress error, {}", t);
			return null;
		}
	}

}
