package com.goldcard.nbiot.manager.url.service;

import java.util.List;

import com.goldcard.nbiot.common.model.DeviceInfo;
import com.goldcard.nbiot.common.model.NBUrl;
import com.goldcard.nbiot.common.util.PageBase;

public interface NBUrlService {
	/**
	 * 获取Url列表
	 * */
	public List<NBUrl> getUrlList(String platform);

	/**
	 * 根据平台查询平台相关url
	 * @param platform
	 * @return
	 */
	public List<NBUrl> getUrlByPlatform(String platform);
	
	/**
	 * 根据平台和功能码查询相关url信息
	 * @param nbUrl
	 * @return
	 */
	public NBUrl getUrl(String id);
	
	/**
	 * 保存url信息
	 * */
	public void saveUrl(NBUrl nBUrl);
	
	/**
	 * 更新
	 * @param id
	 */
	public void updateUrl(NBUrl nbUrl);
	
	public PageBase<NBUrl> selectByPage(String platform, int pageIndex, int pageSize);
}
