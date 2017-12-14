/**
 * 
 */
package com.goldcard.nbiot.manager.context;

/**
 * @author 1903
 *
 */
public class NBIotContext {
	
	//总共包数
	private  int totalCount;
	
	//当前包数
	private volatile  int currentCount;
	
	//功能码
	private  String functionCode;
	
	
	private boolean isAnswer;
	
	
	private String  data;
	
	
	private String deviceId;
	
	
	private boolean isSubFrame;


	public NBIotContext(){
		
	}
	
	public NBIotContext(int totalCount, int currentCount, String functionCode, boolean isAnswer, String data, String deviceId, boolean isSubFrame) {
		this.totalCount = totalCount;
		this.currentCount = currentCount;
		this.functionCode = functionCode;
		this.isAnswer = isAnswer;
		this.data = data.substring(18, data.length() - 2);
		this.deviceId = deviceId;
		this.isSubFrame = isSubFrame;
	}
    
	public NBIotContext(String data, String deviceId, boolean isAnswer, boolean isSubFrame) {
		this.data = data;
		this.deviceId = deviceId;
		this.isAnswer = isAnswer;
		this.isSubFrame = isSubFrame;
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
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the isAnswer
	 */
	public boolean isAnswer() {
		return isAnswer;
	}

	/**
	 * @param isAnswer the isAnswer to set
	 */
	public void setAnswer(boolean isAnswer) {
		this.isAnswer = isAnswer;
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
	 * @return the currentCount
	 */
	public int getCurrentCount() {
		return currentCount;
	}

	/**
	 * @param currentCount the currentCount to set
	 */
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	/**
	 * @return the functionCode
	 */
	public String getFunctionCode() {
		return functionCode;
	}

	/**
	 * @param functionCode the functionCode to set
	 */
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public boolean isSubFrame() {
		return isSubFrame;
	}

	public void setSubFrame(boolean isSubFrame) {
		this.isSubFrame = isSubFrame;
	}
	
}
