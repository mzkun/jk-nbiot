package com.goldcard.nbiot.manager.url.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.goldcard.nbiot.common.dal.daointerface.NBUrlDao;
import com.goldcard.nbiot.common.dal.dataobject.NBUrlDO;
import com.goldcard.nbiot.common.dal.dataobject.PlatformDo;
import com.goldcard.nbiot.common.model.NBUrl;
import com.goldcard.nbiot.common.model.Platform;
import com.goldcard.nbiot.common.util.PageBase;
import com.goldcard.nbiot.manager.redis.JedisCacheTools;
import com.goldcard.nbiot.manager.url.service.NBUrlService;

@Service
public class NBUrlServiceImpl implements NBUrlService {

	@Resource
	private NBUrlDao nbUrlDao;
	
	@Override
	public List<NBUrl> getUrlList(String platform) {
		List<NBUrlDO> dos = nbUrlDao.getUrlList(platform);
		return transferDOList2InfoList(dos);
	}

	@Override
	public void saveUrl(NBUrl url) {
		nbUrlDao.saveUrl(transferInfo2DO(url));
	}
	
	@Override
	public List<NBUrl> getUrlByPlatform(String platform) {
		List<NBUrlDO> dos = nbUrlDao.getUrlByPlatform(platform);
		return transferDOList2InfoList(dos);
	}
	
	@Override
	public NBUrl getUrl(String id) {
		NBUrlDO nbUrlDO = nbUrlDao.getUrl(id);
		return transferDO2Info(nbUrlDO);
	}
	
	@Override
	public void updateUrl(NBUrl nbUrl) {
		nbUrlDao.updateUrl(transferInfo2DO(nbUrl));
	}

	@Override
	public PageBase<NBUrl> selectByPage(String platform, int pageIndex, int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<NBUrlDO> urlDOs = nbUrlDao.getUrlList(platform);
		PageBase<NBUrlDO> pageBase = new PageBase<NBUrlDO>(urlDOs);
		
		List<NBUrl> nbUrls = transferDOList2InfoList(urlDOs);
        PageBase<NBUrl> nburls = new PageBase<NBUrl>(); 
        
        nburls.setTotal(pageBase.getTotal());
        nburls.setList(nbUrls);
		
		return nburls;
	}
	/**
     * 将底层DO List转换成上层Info list
     * @param ls
     * @return
     */
    private List<NBUrl> transferDOList2InfoList(List<NBUrlDO> ls) {
        List<NBUrl> dis = new ArrayList<NBUrl>();
        if (ls != null && !ls.isEmpty()) {
            for (NBUrlDO nbUrlDO : ls) {
            	NBUrl nbUrl = new NBUrl();
            	nbUrl.setId(nbUrlDO.getId());
            	nbUrl.setPlatform(nbUrlDO.getPlatform());
            	nbUrl.setPlatformDes(nbUrlDO.getPlatformDes());
            	nbUrl.setFun_code(nbUrlDO.getFun_code());
            	nbUrl.setFun_desc(nbUrlDO.getFun_desc());
            	nbUrl.setUrl(nbUrlDO.getUrl());
            	nbUrl.setGmt_create(nbUrlDO.getGmt_create());
                dis.add(nbUrl);
            }
        }
        return dis;
    }
    
    /**
     * 将底层DO 转换成上层Info 
     * @param deviceInfoDO
     * @return
     */
	@SuppressWarnings("unused")
	private NBUrl transferDO2Info(NBUrlDO nbUrlDO) {
    	NBUrl nbUrl = null;
        if (null != nbUrlDO) {
        	nbUrl = new NBUrl();
        	nbUrl.setId(nbUrlDO.getId());
        	nbUrl.setPlatform(nbUrlDO.getPlatform());
        	nbUrl.setPlatformDes(nbUrlDO.getPlatformDes());
        	nbUrl.setFun_code(nbUrlDO.getFun_code());
        	nbUrl.setFun_desc(nbUrlDO.getFun_desc());
        	nbUrl.setUrl(nbUrlDO.getUrl());
        	nbUrl.setGmt_create(nbUrlDO.getGmt_create());
        }
        return nbUrl;
    }
    
    
    /**
     * 将底层 Info 转换成上层DO 
     * @param deviceInfo
     * @return
     */
    private NBUrlDO transferInfo2DO(NBUrl nbUrl) {
    	NBUrlDO nbUrlDO = null;
        if (null != nbUrl) {
        	nbUrlDO = new NBUrlDO();
        	nbUrlDO.setId(nbUrl.getId());
        	nbUrlDO.setPlatform(nbUrl.getPlatform());
        	nbUrlDO.setPlatformDes(nbUrl.getPlatformDes());
        	nbUrlDO.setFun_code(nbUrl.getFun_code());
        	nbUrlDO.setFun_desc(nbUrl.getFun_desc());
        	nbUrlDO.setUrl(nbUrl.getUrl());
        	nbUrlDO.setGmt_create(nbUrl.getGmt_create());
        }
        return nbUrlDO;
    }
}
