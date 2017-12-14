package com.goldcard.nbiot.web.home.jetty.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.goldcard.nbiot.common.util.JsonUtils;
import com.goldcard.nbiot.processor.iot.IOTProcessor;

/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：RequestProcessorHandler.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月10日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public class RequestProcessorHandler extends AbstractHandler {
	private Logger log = LoggerFactory.getLogger(RequestProcessorHandler.class);
    
    private Map<String, IOTProcessor> processors ;

    /**
	 * @param processors the processors to set
	 */
	public void setProcessors(Map<String, IOTProcessor> processors) {
		this.processors = processors;
	}

	@Override
    public void handle(String data, Request baseRequest, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException, ServletException {
        
        JsonNode jsonNode = JsonUtils.getJsonNode(httpServletRequest.getInputStream());
        
        log.info("IOT上报数据 url:"+ data +" data:" + jsonNode);
        
        Map<Object,Object> extendMap =new HashMap<Object, Object>();
        
        //预留扩展参数
        init(data,extendMap);
        
        //
        processors.get("HwIoT").doProcess(jsonNode, extendMap);
        // response
        httpServletResponse.setContentType("text/html;charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        httpServletResponse.getWriter().println();
    }
    
    /**
     * 扩展字段
     * @param data
     * @param extendMap
     */
    private void init(String data,Map<Object, Object> extendMap){
        
        
    }

}
