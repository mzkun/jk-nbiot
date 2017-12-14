package com.goldcard.nbiot.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：JsonUtils.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月10日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public class JsonUtils {
    
    /**
     * 转换数据流为JsonNode
     * @param inputStream
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static JsonNode getJsonNode(final InputStream inputStream) throws IOException, UnsupportedEncodingException {
        // 读取请求内容
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(sb.toString());
        return jsonNode;
    }
    
    /**
     * 将json格式字符串转换成JsonNode
     * @param content
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static JsonNode getJsonNode(final String content) throws IOException, UnsupportedEncodingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(content);
        return jsonNode;
    }

}
