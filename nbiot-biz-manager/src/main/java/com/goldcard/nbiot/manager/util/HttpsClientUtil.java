package com.goldcard.nbiot.manager.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("deprecation")
public class HttpsClientUtil extends DefaultHttpClient {
	private static String SELFCERTPATH = null;

	private static String SELFCERTPWD = null;

	private static String TRUSTCAPATH = null;

	private static String TRUSTCAPWD = null;
	static {
		ConfigPropertiesUtil propertiesUtil = ConfigPropertiesUtil.getInstance("config/common.properties");
		// 示例代码 证书路径、证书密钥请根据实际情况替换
		SELFCERTPATH = propertiesUtil.getProperty("selfcertPath");

		SELFCERTPWD = propertiesUtil.getProperty("selfcertPwd");

		TRUSTCAPATH = propertiesUtil.getProperty("trustcaPath");

		// 这里的密码不是CA证书的密码，而是jks证书仓库的密码 （CA证书本身不包含私钥，因此也没有密码）
		TRUSTCAPWD = propertiesUtil.getProperty("trustcaPwd");
	}
	
	private Logger log = LoggerFactory.getLogger(HttpsClientUtil.class);

    /**
     * 双向认证场景 Two-Way Authentication 双向认证场景下，客户端需要 1、导入自己证书，提供自己证书供服务端校验
     * 2、导入服务器CA证书，使用服务端CA证书校验服务端发送过来的证书 3、设置不校验域名 （非商用环境下，沒有使用域名访问）
     * */
    public void initSSLConfigForTwoWay() throws Exception {
    	String selfcertPath = HttpsClientUtil.class.getClassLoader().getResource(SELFCERTPATH).getFile();
    	String trustcaPath = HttpsClientUtil.class.getClassLoader().getResource(TRUSTCAPATH).getFile();
        // 1、导入自己证书
        KeyStore selfCert = KeyStore.getInstance("pkcs12");
        selfCert.load(new FileInputStream(selfcertPath), SELFCERTPWD.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
        kmf.init(selfCert, SELFCERTPWD.toCharArray());

        // 2、导入服务器CA证书
        KeyStore caCert = KeyStore.getInstance("jks");
        caCert.load(new FileInputStream(trustcaPath), TRUSTCAPWD.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
        tmf.init(caCert);

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        // 3、关闭证书域名校验
        // (联调测试环境中，一般没有申请域名，而是使用ip进行访问的，这种场景下必须关闭证书的域名校验功能)
        SSLSocketFactory ssf = new SSLSocketFactory(sc, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        // 如果环境已经申请了域名，并且与证书信息中的域名匹配，才可以开启证书域名校验 （默认也是打开的）
        // SSLSocketFactory ssf = new SSLSocketFactory(sc);

        ClientConnectionManager ccm = this.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 8743, ssf));
    }

    /**
     * 单向认证场景 One-way authentication 单向认证场景下，客户端需要
     * 1、导入服务器CA证书，使用服务端CA证书校验服务端发送过来的证书 2、设置不校验域名 （非商用环境下，沒有使用域名访问）
     * */
    public void initSSLConfigForOneWay() throws Exception {
    	String trustcaPath = HttpsClientUtil.class.getClassLoader().getResource(TRUSTCAPATH).getFile();
        // 1、导入服务器CA证书
        KeyStore caCert = KeyStore.getInstance("jks");
        caCert.load(new FileInputStream(trustcaPath), TRUSTCAPWD.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
        tmf.init(caCert);

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, tmf.getTrustManagers(), null);

        // 2、关闭证书域名校验
        // (联调测试环境中，一般没有申请域名，而是使用ip进行访问的，这种场景下必须关闭证书的域名校验功能)
        SSLSocketFactory ssf = new SSLSocketFactory(sc, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        // 如果环境已经申请了域名，并且与证书信息中的域名匹配，才可以开启证书域名校验 （默认也是打开的）
        // SSLSocketFactory ssf = new SSLSocketFactory(sc);

        ClientConnectionManager ccm = this.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 8743, ssf));
    }
    
    public String doGet(String url, Map<String, String> map, String charset) {
    	HttpGet httpGet = null;
    	String result = null;
    	
    	try {
    		httpGet = new HttpGet(url);
    		// 设置请求和传输超时时间
			int socketTimeout = 30 * 1000;
			int connectTimeout = 30 * 1000;
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

			httpGet.setConfig(requestConfig);
			httpGet.setHeader("app_key", (String) map.get("app_key"));
			httpGet.setHeader("Authorization", (String) map.get("Authorization"));
			HttpResponse response = this.execute(httpGet);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.close();
		}
		return result;
    }

    public String doPost(String url, Map<String, String> map, String charset) {
		HttpPost httpPost = null;
		String result = null;
		try {
			httpPost = new HttpPost(url);
			// 设置请求和传输超时时间
			int socketTimeout = 30 * 1000;
			int connectTimeout = 30 * 1000;
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
			
			httpPost.setConfig(requestConfig);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = this.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.close();
		}
		return result;
	}
    
    /**
     * 通过ContentType为json的格式进行http传输
     *
     * @param url     远程url
     * @param params 传输内容
     * @return 响应内容
     * @throws java.io.IOException
     */
    public String doHttpPostReq(String url,Map<String, String> params, Map<String, String> headParams, String charset) throws Exception {
		HttpPost httpPost = null;
		String result = null;
		try {
			httpPost = new HttpPost(url);
			// 设置请求和传输超时时间
			int socketTimeout = 30 * 1000;
			int connectTimeout = 30 * 1000;
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

			httpPost.setConfig(requestConfig);
			httpPost.setHeader("app_key", (String) headParams.get("app_key"));
			httpPost.setHeader("Authorization", (String) headParams.get("Authorization"));
			String contentStr = JSON.toJSONString(params);
			if (contentStr != null && contentStr.length() > 0) {
				StringEntity entity = new StringEntity(contentStr, ContentType.APPLICATION_JSON);
				entity.setContentType("application/json;charset=UTF-8");
				httpPost.setEntity(entity);
			}
			HttpResponse response = this.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return result;
	}
    
	public String doHttpPostReq(String url, Map<String, String> headParams, String charset) throws Exception {
		HttpPost httpPost = null;
		String result = null;
		try {
			httpPost = new HttpPost(url);
			// 设置请求和传输超时时间
			int socketTimeout = 30 * 1000;
			int connectTimeout = 30 * 1000;
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

			httpPost.setConfig(requestConfig);
			httpPost.setHeader("app_key", (String) headParams.get("app_key"));
			httpPost.setHeader("Authorization", (String) headParams.get("Authorization"));
			String content = (String) headParams.get("content");
			if (content != null && content.length() > 0) {
				StringEntity entity = new StringEntity(content, ContentType.APPLICATION_JSON);
				entity.setContentType("application/json;charset=UTF-8");
				httpPost.setEntity(entity);
			}
			HttpResponse response = this.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return result;
	}
    
    /**
     * 通过ContentType为json的格式进行http传输
     *
     * @param url     远程url
     * @param content 传输内容
     * @return 响应内容
     * @throws java.io.IOException
     */
    public String doHttpPut(String url, Map<String, String> content,Map<String, String> headerParam, String charset){
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
         //JSONObject jsonObj = JSONObject.fromObject(content);
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
         HttpResponse response=null;
         try {
        	 response = this.execute(httpPut);
             HttpEntity entity = response.getEntity();
             StatusLine statusLine = response.getStatusLine();
             log.info("服务器响应代码为========="+statusLine);
             if (entity != null) {
                 info = EntityUtils.toString(entity, charset);
            }
         }catch (Exception e) {
        	 e.printStackTrace();
		 } 
         return info;
    }
    
	public String doHttpDelete(String url, Map<String, String> params, String charset) {
		HttpDelete httpDelete = new HttpDelete(url);
		int socketTimeout = 30 * 1000;
		int connectTimeout = 30 * 1000;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
		httpDelete.setConfig(requestConfig);
		httpDelete.setHeader("app_key", params.get("app_key"));
		httpDelete.setHeader("Authorization", params.get("Authorization"));
		String info = null;
		HttpResponse response = null;
		try {
			response = this.execute(httpDelete);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				info = EntityUtils.toString(entity, charset);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

    private static void oneWay(String url1, Map<String, String> paramap) throws Exception {
        HttpsClientUtil httpClient1 = new HttpsClientUtil();
        httpClient1.initSSLConfigForOneWay();
        String result1 = httpClient1.doPost(url1, paramap, "UTF-8");
        System.out.println("----------result1----------");
        System.out.println(result1);
    }

    private static void twoWay(String url2, Map<String, String> paramap) throws Exception {
    	HttpsClientUtil httpClient2 = new HttpsClientUtil();
        httpClient2.initSSLConfigForTwoWay();
        String result2 = httpClient2.doPost(url2, paramap, "UTF-8");
        System.out.println("----------result2----------");
        System.out.println(result2);
    }

}
