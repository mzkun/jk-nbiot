/**
 * 
 */
package com.goldcard.nbiot.manager.cache;

import java.util.Date;

/**
 * @author 1903
 *
 */
public class CacheItem {
	
	private Date createTime = new Date();//创建缓存的时间
	
	private long expireTime = 1;//缓存期满的时间
	
	private Object entity;//缓存的实体
	
	
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the expireTime
	 */
	public long getExpireTime() {
		return expireTime;
	}

	/**
	 * @param expireTime the expireTime to set
	 */
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * @return the entity
	 */
	public Object getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public CacheItem(Object obj,long expires){
		this.entity = obj;
		this.expireTime = expires;
	}
	
	public boolean isExpired(){
		return (expireTime != -1 && new Date().getTime()-createTime.getTime() > expireTime);
	}

}
