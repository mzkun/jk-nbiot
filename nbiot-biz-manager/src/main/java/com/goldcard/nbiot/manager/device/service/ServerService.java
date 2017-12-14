package com.goldcard.nbiot.manager.device.service;

/**
 * --------------------------------------------------
 * Copyright   浙江金卡股份有限公司
 *
 * Title: ServerService
 * 
 * 功能描述: 
 * 
 * @author 2032
 * @date 2016 上午8:57:07
 * --------------------------------------------------
 */
public interface ServerService {
	/**
	 * 获取Value
	 */
	public String getValueByCode(String code);

	/**
	 * 更新Value
	 */
	public void updateValue(String code, String value);

	/**
	 * 获取deviceId
	 */
	public String getDeviceIdByVerifyCode(String verifyCode);

}
