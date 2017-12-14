/**
 * 
 */
package com.goldcard.nbiot.common.exception;

import java.text.MessageFormat;

import com.goldcard.nbiot.common.enums.ResultCodeEnum;

/**
 * @author 1903
 *
 */
public class NbiotException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8662635444158696518L;
	
	  /** 错误代码 */
    private String errorCode;
    
    /** 错误描述 */
    private String errorMsg;

    /** 结果枚举 */
    private ResultCodeEnum resultCodeEnum;

    /**
     * 构造函数
     * @param errorCode
     * @param errorMsg
     */
    public NbiotException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }
    
    /**
     * 构造函数
     * @param resultCodeEnum
     */
    public NbiotException(ResultCodeEnum resultCodeEnum){
    	this.resultCodeEnum = resultCodeEnum;
    	this.errorCode = resultCodeEnum.getValue();
    	this.errorMsg = resultCodeEnum.getMessage();
    }

    /**
     * 构造函数
     * @param resultCodeEnum
     * @param args 可变参数
     * 			         如果对应结果码描述定义有占位符，则根据实际需要传参，否则可不传
     */
    public NbiotException(ResultCodeEnum resultCodeEnum, Object... args) {
    	super(args.length == 0 ? resultCodeEnum.getMessage() 
    			: MessageFormat.format(resultCodeEnum.getMessage(), args));
        this.resultCodeEnum = resultCodeEnum;
        this.errorCode = resultCodeEnum.getValue();
    }

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public ResultCodeEnum getResultCodeEnum() {
		return resultCodeEnum;
	}
}
