package com.goldcard.nbiot.processor.iot;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.goldcard.nbiot.common.model.NbiotResult;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：IOTProcessor.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月10日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public interface IOTProcessor {
    
    /**
     * nb平台业务处理接口
     * @param jsonNode
     * @return
     */
    public NbiotResult<Void> doProcess(JsonNode jsonNode,Map<Object, Object> extendMap);
    
}
