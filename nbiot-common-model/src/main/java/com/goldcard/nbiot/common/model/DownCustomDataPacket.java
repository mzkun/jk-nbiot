/**
 * 
 */
package com.goldcard.nbiot.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 1903
 *
 */
public class DownCustomDataPacket implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4067227420492953633L;
	
	
	private String deviceId;
	
	/**
	 * 
	 */
	private String source;

	//总帧数
	private int totalCount;
	
	//执行状态
	private boolean state;
	
	//包内容
	private List<SonDataPacket> dataPacket=new ArrayList<SonDataPacket>();
	
	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}


	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the dataPacket
	 */
	public List<SonDataPacket> getDataPacket() {
		return dataPacket;
	}

	/**
	 * @param dataPacket the dataPacket to set
	 */
	public void setDataPacket(List<SonDataPacket> dataPacket) {
		this.dataPacket = dataPacket;
	}

	/**
	 * @return the state
	 */
	public boolean isState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(boolean state) {
		this.state = state;
	}

}
