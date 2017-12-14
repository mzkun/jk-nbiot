package com.goldcard.nbiot.manager.notify;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.goldcard.nbiot.common.model.DownCustomDataPacket;
import com.goldcard.nbiot.common.model.SonDataPacket;
import com.goldcard.nbiot.manager.holder.SpringContextHolder;
import com.goldcard.nbiot.manager.netty.handler.SouthClientHandler;
import com.goldcard.nbiot.manager.redis.JedisCacheTools;

@SuppressWarnings("rawtypes")
@Component
public class NotifyEventListener implements ApplicationListener<NotifyEvent> {

	public static Logger logger = LoggerFactory.getLogger(NotifyEventListener.class);

	// 通讯数据
	private static String MSG = "msg";

	// 设备id
	private static String DEVICE_ID = "deviceId";

	@SuppressWarnings("unchecked")
	@Async
	@Override
	public void onApplicationEvent(final NotifyEvent event) {
		if (event instanceof NotifyEvent) {
			Map<String, SouthClientHandler> handlers = (Map<String, SouthClientHandler>) SpringContextHolder.getApplicationContext().getBean("handlerMap");
			final Map<String, Object> params = new HashMap<String, Object>();
			DownCustomDataPacket downData = (DownCustomDataPacket) JedisCacheTools.getObjectFromJedis(event.getDeviceId() + "_command");

			if (downData.isState()) {
				logger.info("数据包已经发送完毕");
				return;
			}
			List<SonDataPacket> dataList = downData.getDataPacket();
			for (SonDataPacket dataPacket : dataList) {
				if (dataPacket.isState()) {
					continue;
				}
				params.put(MSG, dataPacket.getMsg());
				params.put(DEVICE_ID, downData.getDeviceId());
				dataPacket.setSendTime(new Date());
				try {
					handlers.get(downData.getSource()).sendMsgToIot(params);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	}
}
