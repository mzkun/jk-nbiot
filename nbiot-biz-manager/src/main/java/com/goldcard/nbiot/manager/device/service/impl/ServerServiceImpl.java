package com.goldcard.nbiot.manager.device.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goldcard.nbiot.common.dal.daointerface.ServerDao;
import com.goldcard.nbiot.manager.device.service.ServerService;


@Service
public class ServerServiceImpl implements ServerService{
	
	@Resource
	private ServerDao serverDao;
	
	@Override
	public String getValueByCode(String code) {
		String value=serverDao.getValueByCode(code);
		return value;
	}

	@Override
	public void updateValue(String platform, String accesstoken) {
		Map<String,Object> params=new HashMap<>();
		params.put("platform", platform);
		params.put("accesstoken", accesstoken);
		serverDao.updateValue(params);
	}

	@Override
	public String getDeviceIdByVerifyCode(String verifyCode) {
		String deviceId = serverDao.getDeviceIdByVerifyCode(verifyCode);
		return deviceId;
	}
	
}
