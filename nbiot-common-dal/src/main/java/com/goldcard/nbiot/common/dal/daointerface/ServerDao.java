package com.goldcard.nbiot.common.dal.daointerface;

import java.util.Map;

/**
 * -------------------------------------------------- 
 * Copyright 浙江金卡股份有限公司
 *
 * Title: ServerDaoMapper
 * 
 * 功能描述:
 * 
 * @author 2032
 * 
 * @date 2016 下午5:34:45 
 * --------------------------------------------------
 */
public interface ServerDao {

	public String getValueByCode(String code);

	public void updateValue(Map<String, Object> map);

	public String getDeviceIdByVerifyCode(String verifyCode);
}
