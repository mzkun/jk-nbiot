package com.goldcard.nbiot.common.dal.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.goldcard.nbiot.common.dal.dataobject.NBUrlDO;

public interface NBUrlDao {
	/**
	 * 获取Url列表
	 * */
	List<NBUrlDO> getUrlList(@Param("platform")String platform);

	/**
	 * 根据平台获取url信息
	 * @param platform
	 * @return
	 */
	List<NBUrlDO> getUrlByPlatform(@Param("platform")String platform);
	
	/**
	 *  根据id查询url信息
	 * @param nbUrlDO
	 * @return
	 */
	NBUrlDO getUrl(@Param("id")String id);
	
	/**
	 * 保存url信息
	 * */
	void saveUrl(NBUrlDO urlDO);
	
	/**
	 * 更新
	 * @param id
	 */
	void updateUrl(NBUrlDO urlDO);
	
}
