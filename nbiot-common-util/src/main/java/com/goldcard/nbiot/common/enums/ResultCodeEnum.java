/**
 * 
 */
package com.goldcard.nbiot.common.enums;

/**
 * @author 1903
 *
 */
public enum ResultCodeEnum {
	
	SYSTEM_ERROR("9999", "系统未知异常"),
	
	SEND_DATA_TO_COLLECTION_FAIL("211111","往采集系统发送业务数据失败"),
	
	SET_CACHE_FAIL("211112","设置缓存失败"),
	
	TIME_OUT("211113","超时异常"),
	
	INVOK_IOT_FAIL("211114","调用iot平台失败"),
	
	HW_SERVER_ERROR("100001","Internal server error"),
	
	HW_INPUT_INVALID("100022","输入参数无效"),
	
	HW_GET_KEY_FAIL("100220","获取appKey失败"),
	
	HW_APP_IS_NOT_EXIST("100203","应用不存在"),
	
	HW_DEVICE_IS_NOT_EXIST("100418","设备数据不存在"),
	
	HW_SERVICE_IS_NOT_EXIST("100431","服务类型不存在");
	
	private final String value;

	private final String message;

	ResultCodeEnum(String value, String message) {
		this.value = value;
		this.message = message;
	}

	public String getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}

	public static ResultCodeEnum valueof(String value) {
		for (ResultCodeEnum e : ResultCodeEnum.values()) {
			if (e.getValue().equals(value)) {
				return e;
			}
		}
		return null;
	}
}
