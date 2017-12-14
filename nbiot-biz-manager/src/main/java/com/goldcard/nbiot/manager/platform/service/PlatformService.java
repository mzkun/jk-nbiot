package com.goldcard.nbiot.manager.platform.service;

import java.util.List;

import com.goldcard.nbiot.common.model.Platform;
import com.goldcard.nbiot.common.util.PageBase;

public interface PlatformService {

	/**
	 * 获取平台列表
	 *
	 */
	public List<Platform> getPlatformList(String code);
	
	/**
	 * 根据平台编码查询平台信息
	 * @param code
	 * @return
	 */
	public Platform getPlatformByCode(String code);


	/**
	 * 保存平台信息
	 * 
	 */
	public void savePlatform(Platform platform);
	
	public PageBase<Platform> selectByPage(String code, int pageIndex, int pageSize);
}
