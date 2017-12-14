package com.goldcard.nbiot.common.dal.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.goldcard.nbiot.common.dal.dataobject.PlatformDo;

public interface PlatformDao {
	/**
	 * 获取平台列表
	 * */
	public List<PlatformDo> getPlatformList(@Param("code")String code);

	/**
	 * 根据平台编码查询平台信息
	 * @param code
	 * @return
	 */
	public PlatformDo getPlatformByCode(@Param("code")String code);

	/**
	 * 保存平台信息
	 * */
	public void savePlatform(PlatformDo platformDo);
	
}
