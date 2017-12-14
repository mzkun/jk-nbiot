package com.goldcard.nbiot.manager.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * Http工具类
 * @author mzk
 *
 */
public class HttpClientUtil {
	static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

	private static final String CHARSET = "UTF-8";
	
	public static String doGet(String host,List<NameValuePair> params) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet getMethod = new HttpGet(host);
        String info = null;
        CloseableHttpResponse response = httpclient.execute(getMethod);
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                info = EntityUtils.toString(entity, CHARSET);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            response.close();
        }
        return info;
    }

    public static String doPost(String host,String content) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost postMethod = new HttpPost(host);
      //设置请求和传输超时时间
        int socketTimeout = 30 * 1000;
        int connectTimeout = 30 * 1000;
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .build();
        postMethod.setConfig(requestConfig);
        if (content != null && content.length() > 0) {
            StringEntity entity = new StringEntity(content.toString(), ContentType.APPLICATION_FORM_URLENCODED);
            postMethod.setEntity(entity);
        }
        String info = null;
        CloseableHttpResponse response = httpclient.execute(postMethod);
        try {
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            log.info("服务器响应代码为========="+statusLine);
            if (entity != null) {
                info = EntityUtils.toString(entity, CHARSET);
            }	
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            response.close();
        }
        return info;
    }

    /**
     * 通过ContentType为json的格式进行http传输
     *
     * @param url     远程url
     * @param content 传输内容
     * @return 响应内容
     * @throws java.io.IOException
     */
    public static String doHttpPostReq(String url,Map<String, String> params) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //设置请求和传输超时时间
        int socketTimeout = 30 * 1000;
        int connectTimeout = 30 * 1000;
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .build();
        
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("app_key",(String) params.get("app_key"));
        httpPost.setHeader("Authorization",(String) params.get("Authorization"));
        String content = (String) params.get("content");
        if (content != null && content.length() > 0) {
            StringEntity entity = new StringEntity(content, ContentType.APPLICATION_JSON);
            entity.setContentType("application/json;charset=UTF-8");
            httpPost.setEntity(entity);
        }
        String info = null;
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            log.info("服务器响应代码为========="+statusLine);
            if (entity != null) {
                info = EntityUtils.toString(entity, CHARSET);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            response.close();
        }
        return info;
    }
    
    public static String doHttpPostReq(String url,Map<String, String> params, Map<String, String> headParams) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //设置请求和传输超时时间
        int socketTimeout = 30 * 1000;
        int connectTimeout = 30 * 1000;
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .build();
        
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("app_key",(String) headParams.get("app_key"));
        httpPost.setHeader("Authorization",(String) headParams.get("Authorization"));
        String contentStr = JSON.toJSONString(params);
        if (contentStr != null &&contentStr.length() > 0) {
            StringEntity entity = new StringEntity(contentStr, ContentType.APPLICATION_JSON);
            entity.setContentType("application/json;charset=UTF-8");
            httpPost.setEntity(entity);
        }
        String info = null;
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            log.info("服务器响应代码为========="+statusLine);
            if (entity != null) {
                info = EntityUtils.toString(entity, CHARSET);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            response.close();
        }
        return info;
    }
    
    /**
     * 通过ContentType为json的格式进行http传输
     *
     * @param url     远程url
     * @param content 传输内容
     * @return 响应内容
     * @throws java.io.IOException
     */
    public static String doHttpPut(String url, Map<String, String> content,Map<String, String> headerParam){
    	 CloseableHttpClient httpclient = HttpClients.createDefault();
    	 HttpPut httpPut=new HttpPut(url);
    	//设置请求和传输超时时间
         int socketTimeout = 30 * 1000;
         int connectTimeout = 30 * 1000;
         RequestConfig requestConfig = RequestConfig.custom()
                 .setSocketTimeout(socketTimeout)
                 .setConnectTimeout(connectTimeout)
                 .build();
         httpPut.setConfig(requestConfig);
         httpPut.setHeader("app_key",(String)headerParam.get("app_key"));
         httpPut.setHeader("Authorization",(String)headerParam.get("Authorization"));
//         String replaceContent = content.toString().replaceAll("=",":");
//         JSONObject jsonObj = JSONObject.fromObject(content);
         String contentStr = JSON.toJSONString(content);
         log.info("参数内容：" + contentStr);
         if (contentStr != null && contentStr.length() > 0) {
             StringEntity entity = new StringEntity(contentStr, ContentType.APPLICATION_JSON);
             entity.setContentType("application/json;charset=UTF-8");
             httpPut.setEntity(entity);
         }
         Header[] allHeaders = httpPut.getAllHeaders();
         for (int i = 0; i < allHeaders.length; i++) {
			System.out.println(allHeaders[i]);
		}
         String info = null;
         CloseableHttpResponse response=null;
         try {
        	 response = httpclient.execute(httpPut);
             HttpEntity entity = response.getEntity();
             StatusLine statusLine = response.getStatusLine();
             log.info("服务器响应代码为========="+statusLine);
             if (entity != null) {
                 info = EntityUtils.toString(entity, CHARSET);
            }
         } catch (Exception e) {
        	 e.printStackTrace();
		 } finally {
             try {
            	 if(response!=null){
				response.close();
            	 }
			} catch (IOException e) {
				e.printStackTrace();
			}
         }
         return info;
    }
    
    public static String doHttpDelete(String url,Map<String, String> params){
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	HttpDelete httpDelete = new HttpDelete(url);
    	 int socketTimeout = 30 * 1000;
         int connectTimeout = 30 * 1000;
         RequestConfig requestConfig = RequestConfig.custom()
                 .setSocketTimeout(socketTimeout)
                 .setConnectTimeout(connectTimeout)
                 .build();
         httpDelete.setConfig(requestConfig);
         httpDelete.setHeader("app_key", params.get("app_key"));
         httpDelete.setHeader("Authorization", params.get("Authorization"));
         String info = null;
         CloseableHttpResponse response=null;
         try {
         response = httpclient.execute(httpDelete);
             HttpEntity entity = response.getEntity();
             if (entity != null) {
                 info = EntityUtils.toString(entity, CHARSET);
             }
         } catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
             try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
         }
         return info;
    }
}
