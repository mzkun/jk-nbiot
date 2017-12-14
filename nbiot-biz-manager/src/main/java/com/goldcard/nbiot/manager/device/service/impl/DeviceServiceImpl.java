package com.goldcard.nbiot.manager.device.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.goldcard.nbiot.common.dal.daointerface.DeviceDao;
import com.goldcard.nbiot.common.dal.dataobject.DeviceInfoDO;
import com.goldcard.nbiot.common.dal.dataobject.PlatformDo;
import com.goldcard.nbiot.common.model.DeviceInfo;
import com.goldcard.nbiot.common.model.Platform;
import com.goldcard.nbiot.common.util.PageBase;
import com.goldcard.nbiot.manager.device.service.DeviceService;

/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：DeviceServiceImpl.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月9日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
@Service
public class DeviceServiceImpl implements DeviceService {

    @Resource
    public DeviceDao deviceDao;

    @Override
    public List<DeviceInfo> getDeviceList(String platform, String imei, int page, int row) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("platform", platform);
        map.put("imei", imei);
        map.put("pageStart", (page - 1) * row);
        map.put("pageEnd", page * row);
        List<DeviceInfoDO> ls = deviceDao.getDeviceList(map);
        return transferDOList2InfoList(ls);
    }

    @Override
    public DeviceInfo getDeviceByDeviceId(String DeviceId) {
        DeviceInfoDO deviceInfoDO = deviceDao.getDeviceByDeviceId(DeviceId);
        return transferDO2Info(deviceInfoDO);
    }

    @Override
    public void saveDevice(DeviceInfo deviceInfo) {
        deviceDao.saveDevice(transferInfo2DO(deviceInfo));
    }

    @Override
    public boolean isImeiExists(String imei) {
        int countByVerifyCode = deviceDao.getCountByImei(imei);
        if (countByVerifyCode > 0) {
            return false;
        }
        return true;
    }

    @Override
    public int deleteDeviceByDeviceId(String deviceId) {
        int num = 0;
        try {
            deviceDao.deleteDeviceByDeviceId(deviceId);
        } catch (Exception e) {
            num = 1;
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 将底层DO List转换成上层Info list
     * @param ls
     * @return
     */
    private List<DeviceInfo> transferDOList2InfoList(List<DeviceInfoDO> ls) {
        List<DeviceInfo> dis = new ArrayList<DeviceInfo>();
        if (ls != null && !ls.isEmpty()) {
            for (DeviceInfoDO deviceInfoDO : ls) {
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.setDeviceId(deviceInfoDO.getDeviceId());
                deviceInfo.setGmt_create(deviceInfoDO.getGmt_create());
                deviceInfo.setGmt_modified(deviceInfoDO.getGmt_modified());
                deviceInfo.setId(deviceInfoDO.getId());
                deviceInfo.setPsk(deviceInfoDO.getPsk());
                deviceInfo.setTimeout(deviceInfoDO.getTimeout());
                deviceInfo.setImei(deviceInfoDO.getImei());
                deviceInfo.setPlatform(deviceInfoDO.getPlatform());
                deviceInfo.setPlatformDes(deviceInfoDO.getPlatformDes());
                deviceInfo.setCompanyCode(deviceInfoDO.getCompanyCode());
                dis.add(deviceInfo);
            }
        }
        return dis;
    }
    
    /**
     * 将底层DO 转换成上层Info 
     * @param deviceInfoDO
     * @return
     */
    private DeviceInfo transferDO2Info(DeviceInfoDO deviceInfoDO) {
        DeviceInfo deviceInfo = null;
        if (null != deviceInfoDO) {
            deviceInfo=new DeviceInfo();
            deviceInfo.setDeviceId(deviceInfoDO.getDeviceId());
            deviceInfo.setGmt_create(deviceInfoDO.getGmt_create());
            deviceInfo.setGmt_modified(deviceInfoDO.getGmt_modified());
            deviceInfo.setId(deviceInfoDO.getId());
            deviceInfo.setPsk(deviceInfoDO.getPsk());
            deviceInfo.setTimeout(deviceInfoDO.getTimeout());
            deviceInfo.setImei(deviceInfoDO.getImei());
            deviceInfo.setPlatform(deviceInfoDO.getPlatform());
            deviceInfo.setPlatformDes(deviceInfoDO.getPlatformDes());
            deviceInfo.setCompanyCode(deviceInfoDO.getCompanyCode());
        }
        return deviceInfo;
    }
    
    
    /**
     * 将底层 Info 转换成上层DO 
     * @param deviceInfo
     * @return
     */
    private DeviceInfoDO transferInfo2DO(DeviceInfo deviceInfo) {
        DeviceInfoDO deviceInfoDO = null;
        if (null != deviceInfo) {
            deviceInfoDO=new DeviceInfoDO();
            deviceInfoDO.setDeviceId(deviceInfo.getDeviceId());
            deviceInfoDO.setGmt_create(deviceInfo.getGmt_create());
            deviceInfoDO.setGmt_modified(deviceInfo.getGmt_modified());
            deviceInfoDO.setId(deviceInfo.getId());
            deviceInfoDO.setPsk(deviceInfo.getPsk());
            deviceInfoDO.setTimeout(deviceInfo.getTimeout());
            deviceInfoDO.setImei(deviceInfo.getImei());
            deviceInfoDO.setCompanyCode(deviceInfo.getCompanyCode());
            deviceInfoDO.setPlatform(deviceInfo.getPlatform());
            deviceInfoDO.setPlatformDes(deviceInfo.getPlatformDes());
        }
        return deviceInfoDO;
    }
    
    @Override
    public DeviceInfo selectByDevice(String deviceId) {
    	DeviceInfoDO deviceInfoDO = deviceDao.selectByDevice(deviceId);
    	return transferDO2Info(deviceInfoDO);
    }
    
    @Override
    public PageBase<DeviceInfo> selectByPage(String platform, String imei, int pageIndex, int pageSize) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	PageHelper.startPage(pageIndex, pageSize);
    	map.put("platform", platform);
        map.put("imei", imei);
        List<DeviceInfoDO> ls = deviceDao.getDeviceList(map);
        PageBase<DeviceInfoDO> pageBases =new PageBase<DeviceInfoDO>(ls); 
        
        PageBase<DeviceInfo> deviceInfos = new PageBase<DeviceInfo>();
        deviceInfos.setTotal(pageBases.getTotal());
        deviceInfos.setList(transferDOList2InfoList(ls));
        return deviceInfos;
    }
}
