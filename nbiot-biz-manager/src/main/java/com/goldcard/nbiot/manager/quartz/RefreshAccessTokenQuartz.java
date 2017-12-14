package com.goldcard.nbiot.manager.quartz;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldcard.nbiot.common.enums.FunctionCodeEnum;
import com.goldcard.nbiot.common.model.NBUrl;
import com.goldcard.nbiot.common.model.Platform;
import com.goldcard.nbiot.manager.platform.service.PlatformService;
import com.goldcard.nbiot.manager.redis.JedisCacheTools;
import com.goldcard.nbiot.manager.url.service.NBUrlService;
import com.goldcard.nbiot.manager.util.HttpClientUtil;
import com.goldcard.nbiot.manager.util.HttpsClientUtil;

public class RefreshAccessTokenQuartz{
	
private static Logger log = LoggerFactory.getLogger(RefreshAccessTokenQuartz.class);
	
	@Autowired
	private PlatformService platformService;
	
	@Autowired
	private NBUrlService nbUrlService;
	
	public RefreshAccessTokenQuartz(){
	}
	
	public void refreshAccessToken(){
		log.info("----开始刷新accessToken！------");
		List<Platform> platforms = platformService.getPlatformList(null);
		for (int i = 0; i < platforms.size(); i++) {
			String platform = platforms.get(i).getCode(); //平台编码
			String platformDes = platforms.get(i).getPlatformDes();
			String app_key = platforms.get(i).getApp_key();
			String secret = platforms.get(i).getSecret();
			
			List<NBUrl> urls = nbUrlService.getUrlByPlatform(platform);
			
			for(int j = 0; j < urls.size(); j++){
				
				String funCode = urls.get(j).getFun_code();
				if (FunctionCodeEnum.LOGIN_URL.getCode().equals(funCode)) {
					String login_url = urls.get(j).getUrl();
					getAccessToken(app_key, secret, login_url, platformDes);
				}
			}
			
		}
		log.info("----刷新accessToken完成！------");
	}

	public void getAccessToken(String app_key, String secret, String loginUrl, String platform){
		String params = "appId="+app_key+"&secret="+secret+"";
		try {
			String httpRespose = null;
			if ("http:".equals(loginUrl.substring(0, 5))){
				httpRespose = HttpClientUtil.doPost(loginUrl,params);
			}else if ("https".equals(loginUrl.substring(0, 5))){
				Map<String, String> map = new HashMap<String, String>();
				map.put("appId", app_key);
				map.put("secret", secret);
				HttpsClientUtil clientUtil = new HttpsClientUtil();
            	clientUtil.initSSLConfigForTwoWay();
				httpRespose = clientUtil.doPost(loginUrl, map, "UTF-8");
			}
			log.info("接受到的响应是=========="+httpRespose);
			if (null != httpRespose && !"".equals(httpRespose)) {
				ObjectMapper mapper = new ObjectMapper();  
				Map<String, String> resMap = mapper.readValue(httpRespose, new TypeReference<HashMap<String,String>>(){});
				
				String accessToken = null;
				if (resMap.containsKey("accessToken")){
					accessToken = resMap.get("accessToken");
					//将令牌信息添加到缓存中
					JedisCacheTools.addStringToJedis(platform, accessToken, 0);
				}
				log.info("accessToken已刷新为======="+accessToken);
			}
		} catch (IOException e) {
			log.error("----刷新accessToken过程中产生异常！------");
			e.printStackTrace();
		} catch (Exception e) {
			log.error("证书校验失败");
			e.printStackTrace();
		}
	}
}
