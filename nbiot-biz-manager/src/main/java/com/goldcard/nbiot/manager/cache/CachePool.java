/**
 * 
 *//*
package com.goldcard.nbiot.manager.cache;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

*//**
 * @author 1903
 *
 *//*
public class CachePool {
	
	
	private final static  Logger logger = LoggerFactory.getLogger(CachePool.class);
	
	
	
	//private final static CachePool instance=new CachePool();//缓存池唯一实例
	
	
	
	private final static  Map<String,Object> cacheItems=Maps.newConcurrentMap();//缓存Map
	
	
	
	
	//private final static ReadWriteLock lock = new ReentrantReadWriteLock(); 
	
	
	

	

	
	private CachePool(){
	    
	}
	
	
	


	*//**
	 * 清除所有Item缓存
	 *//*
	public static  void clearAllItems(){
		try {
			//lock.writeLock().lock();
			cacheItems.clear();
		} catch (Exception e) {
			logger.error("【MapCachePool】清除缓存失败");
		}finally{
			//lock.writeLock().unlock();
		}

	}
	
	
	public static Set<String> getAllItemsKeys(){
		
		try {
			//lock.readLock().lock();
			return cacheItems.keySet();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			//lock.readLock().unlock();
		}
		
		return null;
	}
	*//**
	 * 获取缓存实体
	 * @param name
	 * @return
	 *//*
	public static Object getCacheItem(String name){
		try {
			//lock.readLock().lock();
			if(!cacheItems.containsKey(name)){
				return null;
			}
			CacheItem cacheItem = (CacheItem) cacheItems.get(name);
			if(cacheItem.isExpired()){
				return null;
			}
			return cacheItem.getEntity();
		} catch (Exception e) {
			logger.error("【MapCachePool】获取key="+name+"失败");
		}finally{
			//lock.readLock().unlock();
		}
		return null;

	}
	*//**
	 * 存放缓存信息
	 * @param name
	 * @param obj
	 * @param expires
	 *//*
	public static  void putCacheItem(String name,Object obj,long expires){
		try {
			//lock.writeLock().lock();
			CacheItem cacheItem = (CacheItem) cacheItems.get(name);
			if (null == cacheItem){
				cacheItem = new CacheItem(obj, expires);
			}
			cacheItem.setCreateTime(new Date());
			cacheItem.setEntity(obj);
			cacheItem.setExpireTime(expires);
			if(!cacheItems.containsKey(name)){
				cacheItems.put(name, cacheItem);
			}
		} catch (Exception e) {
			logger.error("【MapCachePool】存取key="+name+";value="+JSON.toJSONString(obj)+"失败");
		}finally{
			//lock.writeLock().unlock();
		}
	}
	
	
	public static void putCacheItem(String name,Object obj){
		putCacheItem(name,obj,-1);
	}
	
	*//**
	 * 移除缓存数据
	 * @param name
	 *//*
	public static  void removeCacheItem(String name){
		try {
			//lock.writeLock().lock();
			if(!cacheItems.containsKey(name)){
				return;
			}
			cacheItems.remove(name);
		} catch (Exception e) {
			logger.error("【MapCachePool】清除key="+name+"失败");
		}finally{
			//lock.writeLock().unlock();
		}

	}
	
	*//**
	 * 获取缓存数据的数量
	 * @return
	 *//*
	public static int getSize(){
		return cacheItems.size();
	}

    

}
*/