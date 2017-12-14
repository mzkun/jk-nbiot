/**
 * 
 *//*
package com.goldcard.nbiot.manager.cache;

import java.util.Date;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.goldcard.nbiot.common.model.DownCustomDataPacket;
import com.goldcard.nbiot.common.model.SonDataPacket;

*//**
 * @author 1903
 *
 *//*
@Component
public class RetryManager {
	
	
//	private static final int DEFALUT_RETRY_COUNT=3;
	
//	private static final long DEFAULT_TIME_OUT=30;
	
	//超时时间
    @Value("#{config[retry_time_out]}")
    private String DEFAULT_TIME_OUT;
    
    
    @Value("#{config[retry_count]}")
    private String DEFALUT_RETRY_COUNT;
	
	@PostConstruct
	public void run(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {

				while (true) {

					Set<String> keys = CachePool.getAllItemsKeys();

					for (String key : keys) {

						DownCustomDataPacket dcdp = (DownCustomDataPacket) CachePool.getCacheItem(key);

						for (SonDataPacket sonDataPacket : dcdp.getDataPacket()) {

							if (sonDataPacket.isState()) {
								continue;
							}

							if (sonDataPacket.getRetryCount().intValue() < Integer.valueOf(DEFALUT_RETRY_COUNT)) {
								long l = new Date().getTime() - sonDataPacket.getSendTime().getTime();
								long day = l / (24 * 60 * 60 * 1000);
								long hour = (l / (60 * 60 * 1000) - day * 24);
								long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
								long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
								if (s >= Long.valueOf(DEFAULT_TIME_OUT)) {
//									NotifyEvent<String> notifyEvent = new NotifyEvent<String>(dcdp.getDeviceId(), dcdp.getDeviceId());
//									EventBusFactory.build().register(NotifyEventListener.class);
//									EventBusFactory.build().postsEvent(notifyEvent);
									break;
								}
							}

							CachePool.removeCacheItem(dcdp.getDeviceId());

							break;
						}

					}
				}

			}
		});

	}
}
*/