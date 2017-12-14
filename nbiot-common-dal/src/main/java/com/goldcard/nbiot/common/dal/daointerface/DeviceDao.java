package com.goldcard.nbiot.common.dal.daointerface;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.goldcard.nbiot.common.dal.dataobject.DeviceInfoDO;
import com.goldcard.nbiot.common.dal.dataobject.PlatformDo;



public interface DeviceDao {
	/**
	 * 获取设备列表
	 * */
	public List<DeviceInfoDO> getDeviceList(Map<String, Object> param);
	/**
	 * 按照DeviceId获取设备基本信息
	 * */
	public DeviceInfoDO getDeviceByDeviceId(String DeviceId);
	/**
	 * 保存返回设备信息
	 * */
	public void saveDevice(DeviceInfoDO deviceInfo);
	
	/**
	 * 获取VerifyCode条数
	 * */
	public int getCountByImei(@Param("imei")String imei);
	
	
	public int deleteDeviceByDeviceId(String deviceId);
	
	/**
	 * 根据设备号查询所属平台
	 * @param deviceId
	 * @return
	 */
	public DeviceInfoDO selectByDevice(String deviceId);
	
}
