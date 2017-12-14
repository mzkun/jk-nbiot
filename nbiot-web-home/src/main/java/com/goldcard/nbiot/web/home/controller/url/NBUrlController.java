package com.goldcard.nbiot.web.home.controller.url;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldcard.nbiot.common.model.NBUrl;
import com.goldcard.nbiot.common.model.Platform;
import com.goldcard.nbiot.common.util.PageBase;
import com.goldcard.nbiot.manager.platform.service.PlatformService;
import com.goldcard.nbiot.manager.redis.JedisCacheTools;
import com.goldcard.nbiot.manager.url.service.NBUrlService;

/**
 * 
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：UrlController.java
 * 文件功能描述：TODO
 * 
 * 创建标识 1925 2017-04-05
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
 */
@Controller
@RequestMapping("/urlAction")
public class NBUrlController {

	Logger log = LoggerFactory.getLogger(NBUrlController.class);
	
	@Autowired
	private NBUrlService nbUrlService;
	
	@Autowired
	private PlatformService platformService;
	
	@RequestMapping("/getUrlList")
	@ResponseBody
	public PageBase<NBUrl> getUrlList(HttpServletRequest request) {
		String platform = null;
		if(!StringUtils.isEmpty(request.getParameter("platform"))){
			platform = request.getParameter("platform");
		}
		int page = Integer.parseInt(request.getParameter("page"));
		int row = Integer.parseInt(request.getParameter("rows"));// 接受参数page和rows
		return nbUrlService.selectByPage(platform, page, row);
	}
	
	@RequestMapping("/addUrl")
	@ResponseBody
	public String saveUrl(String fun_code, String url, String fun_desc, String source){
		Platform platform = (Platform) JedisCacheTools.getObjectFromJedis(source);
		if (null == platform) {
			platform = platformService.getPlatformByCode(source);
			//将平台信息添加到redis缓存中
			JedisCacheTools.addObjectToJedis(source, platform, Platform.class, 0); 
		}
		NBUrl nbUrl = new NBUrl();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		nbUrl.setId(format.format(new Date()));
		nbUrl.setFun_code(fun_code);
		nbUrl.setFun_desc(fun_desc);
		nbUrl.setUrl(url);
		nbUrl.setPlatform(source);
		
		nbUrlService.saveUrl(nbUrl);
		JedisCacheTools.addObjectToJedis(platform.getPlatformDes()+fun_code, nbUrl, NBUrl.class, 0);
		return "成功!";
	}
	
	@RequestMapping("/editUrl")
	@ResponseBody
	public String editUrl(String id, String platformDes, String fun_code, String url, String fun_desc){
		NBUrl nbUrl = (NBUrl) JedisCacheTools.getObjectFromJedis(platformDes+fun_code);
		if (null == nbUrl) {
			nbUrl = nbUrlService.getUrl(id);
		} else {
			
		}
		nbUrl.setFun_code(fun_code);
		nbUrl.setFun_desc(fun_desc);
		nbUrl.setUrl(url);
		nbUrlService.updateUrl(nbUrl);
		JedisCacheTools.addObjectToJedis(platformDes+fun_code, nbUrl, NBUrl.class, 0);
		return "成功";
	}
}
