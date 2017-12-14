/**
 * 
 */
package com.goldcard.nbiot.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 1903
 *
 */
public class SonDataPacket implements Serializable {
	
	      /**
	       * 
	      */
	      private static final long serialVersionUID = -4482707678911573679L;

			//当前帧数
			private int currentCount;
			
			private int index;
			
			//执行状态
			private boolean state;
			
		    //数据内容
			private byte[] msg;
			
			//重试次数+
			
			private AtomicInteger retryCount;
			
			
			private Date sendTime;
			

			/**
			 * @return the sendTime
			 */
			public Date getSendTime() {
				return sendTime;
			}


			/**
			 * @param sendTime the sendTime to set
			 */
			public void setSendTime(Date sendTime) {
				this.sendTime = sendTime;
			}


			/**
			 * @return the retryCount
			 */
			public AtomicInteger getRetryCount() {
				return retryCount;
			}


			/**
			 * @param retryCount the retryCount to set
			 */
			public void setRetryCount(AtomicInteger retryCount) {
				this.retryCount = retryCount;
			}


			/**
			 * @return the currentCount
			 */
			public int getCurrentCount() {
				return currentCount;
			}


			/**
			 * @param currentCount the currentCount to set
			 */
			public void setCurrentCount(int currentCount) {
				this.currentCount = currentCount;
			}


			/**
			 * @return the state
			 */
			public boolean isState() {
				return state;
			}


			/**
			 * @param state the state to set
			 */
			public void setState(boolean state) {
				this.state = state;
			}


			/**
			 * @return the msg
			 */
			public byte[] getMsg() {
				return msg;
			}


			/**
			 * @param msg the msg to set
			 */
			public void setMsg(byte[] msg) {
				this.msg = msg;
			}


			/**
			 * @return the index
			 */
			public int getIndex() {
				return index;
			}


			/**
			 * @param index the index to set
			 */
			public void setIndex(int index) {
				this.index = index;
			}
			
			
			
			

}
