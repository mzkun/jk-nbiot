package com.goldcard.nbiot.manager.util;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：ConfigPropertiesUtil.java
 * 文件功能描述：配置文件工具类
 * 
 * 创建标识 1990 2016年7月22日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public class ConfigPropertiesUtil {
	
	private Logger log = LoggerFactory.getLogger(ConfigPropertiesUtil.class);
	
	private static ConfigPropertiesUtil instance=null;
	
	private static Properties prop = null;
	
	private ConfigPropertiesUtil(){
	}
	
	public static ConfigPropertiesUtil getInstance(String filePath) {

		if (instance == null) {
			synchronized (ConfigPropertiesUtil.class) {
				if(instance == null){
					instance = new ConfigPropertiesUtil();
					instance.init(filePath);
				}
			}
		}
		return instance;
	}
	public void init(String filePath){
		prop = new Properties();  
		try {
			prop.load(ConfigPropertiesUtil.class.getClassLoader().getResourceAsStream(filePath));
		} catch (Exception e) {
			log.error("read properties error");
		}
	}
	
	/*** 
     * 根据属性文件中的键值获取对应属性文件中的值 
     * @param key 属性文件中的键值 
     * @return 对应属性文件中的属性值 
     */  
    public String getProperty(String key){  
        return prop.getProperty(key);  
    }  
      
    /**** 
     * 用值填充属性文件中的占位符{0},{1}...,值的顺序必须和参数的顺序是一致的 
     * @param key 属性文件中的键值 
     * @param values 对应属性文件中的占位符的信息 
     * @return  将占位符中的信息对应填充后的字符串 
     */ 
	public String getProperty(String key,String... values){  
        //对应占位符参数值  
        String[] vs=values;  
        //属性文件中的值  
        String v=getProperty(key);  
          
        //如果没有参数  
        if(vs==null||vs.length==0){  
            return getProperty(key);  
        }  
          
        //如果属性文件中没有值,则返回空字符串  
        if(v==null){  
            return "";  
        }  
          
        StringBuffer buffer=new StringBuffer();  
          
        //遍历参数数组  
        for (int i = 0; i < vs.length; i++) {  
            //替换前清空原有替换值  
            buffer.delete(0, buffer.length());  
            Pattern pattern=Pattern.compile("\\{"+i+"\\}");  
            Matcher matcher=pattern.matcher(v);  
                while(matcher.find()){  
                      matcher.appendReplacement(buffer, vs[i]);  
                }  
            matcher.appendTail(buffer);  
            //进行下一次替换  
            v=buffer.toString();  
        }  
        //返回后替换的字符串  
        return buffer.toString();  
    }
	
}
