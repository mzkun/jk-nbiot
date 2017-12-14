package com.goldcard.nbiot.web.home.jetty.server;

import java.net.URL;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.goldcard.nbiot.web.home.jetty.handler.RequestProcessorHandler;

/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：NbiotCallBackServer.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017年2月10日
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public class NbiotCallBackServer {
    
    private Logger logger = LoggerFactory.getLogger(NbiotCallBackServer.class);

	private RequestProcessorHandler requestProcessorHandler;
    
    private Server server = null;
    
    //默认端口号(https)
    private  int https_port=8080;
    
    private int http_port = 8082;
    //jetty初始化线程池个数
    private  int corePoolSize=200;
    //jetty最大线程池个数
    private  int maximumPoolSize=200;
    //jetty 链接保持时间
    private  long keepAliveTime=30000;
    
    @Value("#{config[keystore]}")
    private String keyStore;
    
    @Value("#{config[keypass]}")
    private String keyPass;
    
    private  int acceptors=4;
    
    private int selectors=4;

	public void start() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
            	QueuedThreadPool threadPool = new QueuedThreadPool();
            	threadPool.setMinThreads(corePoolSize);  
            	threadPool.setMaxThreads(maximumPoolSize); 
                server = new Server(threadPool);
                
            	//配置HTTP及对应的HTTPS
                HttpConfiguration httpConfig = new HttpConfiguration();
                httpConfig.setSecureScheme("https");
                httpConfig.setSecurePort(https_port);
                httpConfig.setSendServerVersion(true);
                httpConfig.setSendDateHeader(false);
                
                //配置SSL证书相关
                URL url = Thread.currentThread().getContextClassLoader().getResource(keyStore);
                SslContextFactory sslContextFactory = new SslContextFactory();
                sslContextFactory.setKeyStorePath(url.getFile());
                sslContextFactory.setKeyStorePassword(keyPass);
                sslContextFactory.setKeyManagerPassword(keyPass);
                
                // SSL HTTP配置
                HttpConfiguration httpsConfig = new HttpConfiguration();
                httpsConfig.setSecurePort(https_port);
                httpsConfig.setSecureScheme("https");
                httpsConfig.addCustomizer(new SecureRequestCustomizer());
                
                //SSL配置
                ServerConnector sslConnector = new ServerConnector(server, acceptors, selectors, new SslConnectionFactory(sslContextFactory,
                        HttpVersion.HTTP_1_1.asString()), new HttpConnectionFactory(httpsConfig));
                sslConnector.setPort(https_port);
                sslConnector.setIdleTimeout(keepAliveTime);
                sslConnector.setAcceptQueueSize(10000);
                server.addConnector(sslConnector);
                
                //ServerConnector connector=new ServerConnector(server);
                ServerConnector connector=new ServerConnector(server, acceptors, selectors);
                //NetworkTrafficServerConnector connector=new NetworkTrafficServerConnector(server);
                connector.setPort(http_port);
                connector.setIdleTimeout(keepAliveTime);
                connector.setAcceptQueueSize(10000);
                server.addConnector(connector);
                // handler
                HandlerCollection handler =new HandlerCollection();
                handler.setHandlers(new Handler[]{requestProcessorHandler});
                
                server.setHandler(handler);
                try {
                    server.start();
                    logger.info(">>>>>>>>>>>> nbiot CallbackServer start success at port:==>https{}, http{}", https_port, http_port);
                    server.join();  // block until server ready
                    logger.info(">>>>>>>>>>>> nbiot CallbackServer join success at port:==>https{}, http{}", https_port,http_port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void destroy() {
        if (server!=null) {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
	 * @param https_port the https_port to set
	 */
	public void setHttps_port(int https_port) {
		this.https_port = https_port;
	}

	/**
	 * @param http_port the http_port to set
	 */
	public void setHttp_port(int http_port) {
		this.http_port = http_port;
	}

	/**
     * @param corePoolSize the corePoolSize to set
     */
    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    
    /**
     * @param maximumPoolSize the maximumPoolSize to set
     */
    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    
    /**
     * @param keepAliveTime the keepAliveTime to set
     */
    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }
    
    
    /**
	 * @param acceptors the acceptors to set
	 */
	public void setAcceptors(int acceptors) {
		this.acceptors = acceptors;
	}

	/**
	 * @param selectors the selectors to set
	 */
	public void setSelectors(int selectors) {
		this.selectors = selectors;
	}

    

    /**
	 * @param requestProcessorHandler the requestProcessorHandler to set
	 */
	public void setRequestProcessorHandler(RequestProcessorHandler requestProcessorHandler) {
		this.requestProcessorHandler = requestProcessorHandler;
	}
}
