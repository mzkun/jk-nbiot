package com.goldcard.nbiot.manager.device.service;

import java.util.List;

import com.goldcard.nbiot.common.model.DeviceInfo;
import com.goldcard.nbiot.common.model.Platform;
import com.goldcard.nbiot.common.util.PageBase;


public interface DeviceService {
	/**
	 * 获取设备列表
	 * */
	public List<DeviceInfo> getDeviceList(String platform, String imei,int page,int row);
	/**
	 * 按照DeviceId获取设备基本信息
	 * */
	public DeviceInfo getDeviceByDeviceId(String DeviceId);
	/**
	 * 保存返回设备信息
	 * */
	public void saveDevice(DeviceInfo deviceInfo);
	
	/**
	 * 判断imei是否存在
	 * */
	public boolean isImeiExists(String imei);
	
	/**
	 * 删除设备
	 * */
	public int deleteDeviceByDeviceId(String deviceId);
	
	/**
	 * 根据设备查询所属平台 
	 * @param deviceId
	 * @return
	 */
	public DeviceInfo selectByDevice(String deviceId);
	
	public PageBase<DeviceInfo> selectByPage(String platform, String imei, int pageIndex, int pageSize);
}
