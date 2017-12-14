package com.goldcard.nbiot.manager.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.goldcard.nbiot.common.dal.daointerface.PlatformDao;
import com.goldcard.nbiot.common.dal.dataobject.DeviceInfoDO;
import com.goldcard.nbiot.common.dal.dataobject.PlatformDo;
import com.goldcard.nbiot.common.model.DeviceInfo;
import com.goldcard.nbiot.common.model.Platform;
import com.goldcard.nbiot.common.util.PageBase;
import com.goldcard.nbiot.manager.platform.service.PlatformService;
import com.goldcard.nbiot.manager.redis.JedisCacheTools;

/**
 * 
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：PlatformServiceImpl.java
 * 文件功能描述：TODO
 * 
 * 创建标识 1925 2017-04-01
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
 */
@Service
public class PlatformServiceImpl implements PlatformService{
	
	@Resource
	private PlatformDao platformDao;

	@Override
	public List<Platform> getPlatformList(String code) {
		List<PlatformDo> dos = platformDao.getPlatformList(code);
		return transferDOList2InfoList(dos);
	}
	
	/**
	 * 根据平台编码查询平台信息
	 * @param code
	 * @return
	 */
	public Platform getPlatformByCode(String code){
		PlatformDo platformDo = platformDao.getPlatformByCode(code);
		return transferDO2Info(platformDo);
	}

	@Override
	public void savePlatform(Platform platform) {
		platformDao.savePlatform(transferInfo2DO(platform));
	}
	
	@Override
	public PageBase<Platform> selectByPage(String code, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<PlatformDo> platformDOs = platformDao.getPlatformList(code);
		PageBase<PlatformDo> pageBase = new PageBase<PlatformDo>(platformDOs);
		
		List<Platform> platforms = transferDOList2InfoList(platformDOs);
        for (int i = 0; i < platforms.size(); i++) {
        	String accessToken = JedisCacheTools.getStringFromJedis(platforms.get(i).getPlatformDes());
        	platforms.get(i).setAccessToken(accessToken);
        }
        PageBase<Platform> platformPages = new PageBase<Platform>(); 
        
        platformPages.setTotal(pageBase.getTotal());
        platformPages.setList(platforms);
		
		return platformPages;
	}
	
	 /**
     * 将底层DO List转换成上层Info list
     * @param ls
     * @return
     */
    private List<Platform> transferDOList2InfoList(List<PlatformDo> ls) {
        List<Platform> dis = new ArrayList<Platform>();
        if (ls != null && !ls.isEmpty()) {
            for (PlatformDo platformDo : ls) {
            	Platform platform = new Platform();
            	platform.setCode(platformDo.getCode());
            	platform.setId(platformDo.getId());
            	platform.setPlatform(platformDo.getPlatform());
            	platform.setPlatformDes(platformDo.getPlatformDes());
            	platform.setLocation(platformDo.getLocation());
            	platform.setApp_key(platformDo.getApp_key());
            	platform.setSecret(platformDo.getSecret());
            	platform.setGmt_create(platformDo.getGmt_create());
                dis.add(platform);
            }
        }
        return dis;
    }
    
    /**
     * 将底层DO 转换成上层Info 
     * @param deviceInfoDO
     * @return
     */
	private Platform transferDO2Info(PlatformDo platformDo) {
    	Platform platform = null;
        if (null != platformDo) {
        	platform = new Platform();
        	platform.setCode(platformDo.getCode());
        	platform.setId(platformDo.getId());
        	platform.setPlatform(platformDo.getPlatform());
        	platform.setPlatformDes(platformDo.getPlatformDes());
        	platform.setLocation(platformDo.getLocation());
        	platform.setApp_key(platformDo.getApp_key());
        	platform.setSecret(platformDo.getSecret());
        	platform.setGmt_create(platformDo.getGmt_create());
        }
        return platform;
    }
    
    
    /**
     * 将底层 Info 转换成上层DO 
     * @param deviceInfo
     * @return
     */
    private PlatformDo transferInfo2DO(Platform platform) {
    	PlatformDo platformDo = null;
        if (null != platform) {
        	platformDo = new PlatformDo();
        	platformDo.setCode(platform.getCode());
        	platformDo.setId(platform.getId());
        	platformDo.setPlatform(platform.getPlatform());
        	platformDo.setPlatformDes(platform.getPlatformDes());
        	platformDo.setLocation(platform.getLocation());
        	platformDo.setApp_key(platform.getApp_key());
        	platformDo.setSecret(platform.getSecret());
        	platformDo.setGmt_create(platform.getGmt_create());
        }
        return platformDo;
    }

}
