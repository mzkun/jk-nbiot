/**
 * 
 */
package com.goldcard.nbiot.manager.notify;

import org.springframework.context.ApplicationEvent;

/**
 * @author 1925
 *
 */
public class NotifyEvent<T> extends ApplicationEvent  {
	
	private static final long serialVersionUID = 5195317924498455915L;

	private String deviceId;
	
	private  T     msg;
	
	public NotifyEvent(Object source,String deviceId,T msg){
		super(source); 
		this.deviceId=deviceId;
		this.msg=msg;
	}


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
	 * @return the msg
	 */
	public T getMsg() {
		return msg;
	}


	/**
	 * @param msg the msg to set
	 */
	public void setMsg(T msg) {
		this.msg = msg;
	}
}
