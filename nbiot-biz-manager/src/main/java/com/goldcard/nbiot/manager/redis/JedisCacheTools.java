package com.goldcard.nbiot.manager.redis;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisException;

/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。 
 * 
 * 文件名：JedisCacheTools.java
 * 文件功能描述：TODO
 * 
 * 
 * 创建标识 1903 2017-02-14
 * 
 * 修改标识：
 * 修改描述：
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
*/
public class JedisCacheTools {

    //目前只有一台不考虑集群
    public static int getDBIndex(){
        return 2;
    }
    
    /**
     * 默认日志打印logger_default
     */
    public  static Logger logger = LoggerFactory.getLogger(JedisCacheTools.class);

    public  static JedisPool jedisPool;
    
  
    public  static Jedis getJedis() throws JedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (JedisException e) {
            logger.warn("failed:jedisPool getResource.", e);
            if (jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            throw e;
        }
        return jedis;
    }

    public  static void release(Jedis jedis, boolean isBroken) {
        if (jedis != null) {
            if (isBroken) {
                jedisPool.returnBrokenResource(jedis);
            } else {
                jedisPool.returnResource(jedis);
            }
        }
    }
    
    /**
     * 存储REDIS队列 顺序存储
     * @param key 字节类型
     * @param value 字节类型
     */
    public static void lpush(String key,String value){
    	Jedis jedis = null;
    	boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.lpush(key,value);
        } catch (Exception e) {
        	  isBroken = true;
              logger.warn("failed: key=" + key, value, e);
        }finally {
        	 release(jedis, isBroken);
        }
    }
    
    
    
    /**
     * 存储REDIS队列 顺序存储
     * @param key 字节类型
     * @param value 字节类型
     */
    public static void hmset(String key,Map<String, String> values){
    	Jedis jedis = null;
    	boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.hmset(key, values);
        } catch (Exception e) {
        	  isBroken = true;
              logger.warn("failed: key=" + key, values, e);
        }finally {
        	 release(jedis, isBroken);
        }
    }
    
    

    public  static String addStringToJedis(String key, String value, int cacheSeconds) {
        Jedis jedis = null;
        boolean isBroken = false;
        String lastVal = null;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            lastVal = jedis.getSet(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed: key=" + key, value, e);
        } finally {
            release(jedis, isBroken);
        }
        return lastVal;
    }

    public  static void addStringToJedis(Map<String, String> batchData, int cacheSeconds) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            Pipeline pipeline = jedis.pipelined();
            for (Map.Entry<String, String> element : batchData.entrySet()) {
                if (cacheSeconds != 0) {
                    pipeline.setex(element.getKey(), cacheSeconds, element.getValue());
                } else {
                    pipeline.set(element.getKey(), element.getValue());
                }
            }
            pipeline.sync();
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            release(jedis, isBroken);
        }
    }

    public  static void addStringListToJedis(String key, List<String> list, int cacheSeconds) {
        if (list != null && list.size() > 0) {
            Jedis jedis = null;
            boolean isBroken = false;
            try {
                jedis = getJedis();
                jedis.select(getDBIndex());
                if (jedis.exists(key)) {
                    jedis.del(key);
                }
                for (String aList : list) {
                    jedis.rpush(key, aList);
                }
                if (cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }
            } catch (JedisException e) {
                isBroken = true;
                logger.warn("failed: key=" + key, list.size(), e);
            } catch (Exception e) {
                isBroken = true;
                logger.warn("failed: key=" +  key, list.size(), e);
            } finally {
                release(jedis, isBroken);
            }
        }
    }
    
    public static void addToSetJedis(String key, String[] value) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            jedis.sadd(key, value);
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed: key=" + key, value, e);
        } finally {
            release(jedis, isBroken);
        }
    }

    public static  void removeSetJedis(String key, String value) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            jedis.srem(key, value);
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed: key=" + key, value, e);
        } finally {
            release(jedis, isBroken);
        }
    }

    public static  void pushDataToListJedis(String key, String data, int cacheSeconds) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            jedis.rpush(key, data);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed: key=" + key, data, e);
        } finally {
            release(jedis, isBroken);
        }
    }

    public static  void pushDataToListJedis(String key, List<String> batchData, int cacheSeconds) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            jedis.del(key);
            jedis.lpush(key, batchData.toArray(new String[batchData.size()]));
            if (cacheSeconds != 0) jedis.expire(key, cacheSeconds);
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed:key=" + key,
                                       batchData != null ? batchData.size() : 0, e);
        } finally {
            release(jedis, isBroken);
        }
    }

    /**
     * 删除list中的元素
     * @param key
     * @param values
     * @param methodName
     */
    public static  void deleteDataFromListJedis(String key, List<String> values) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            Pipeline pipeline = jedis.pipelined();
            if (values != null && !values.isEmpty()) {
                for (String val : values) {
                    pipeline.lrem(key, 0, val);
                }
            }
            pipeline.sync();
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed:key=" + key, values != null ? values.size() : 0, e);
        } finally {
            release(jedis, isBroken);
        }
    }

    public static  void addHashMapToJedis(String key, Map<String, String> map, int cacheSeconds, boolean isModified) {
        boolean isBroken = false;
        Jedis jedis = null;
        if (map != null && map.size() > 0) {
            try {
                jedis = getJedis();
                jedis.select(getDBIndex());
                jedis.hmset(key, map);
                if (cacheSeconds > 0){
                	jedis.expire(key, cacheSeconds);
                }
            } catch (Exception e) {
                isBroken = true;
                logger.warn("failed:key" + key, map.size(), e);
            } finally {
                release(jedis, isBroken);
            }
        }
    }

    public static  void addHashMapToJedis(String key, String field, String value, int cacheSeconds) {
        boolean isBroken = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                jedis.select(getDBIndex());
                jedis.hset(key, field, value);
                if (cacheSeconds != 0) {
                	jedis.expire(key, cacheSeconds);
                }
            }
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed:key=" + key+"&field=" +field, value, e);
        } finally {
            release(jedis, isBroken);
        }
    }

    public static  void updateHashMapToJedis(String key, String incrementField, long incrementValue, String dateField,
                                        String dateValue) {
        boolean isBroken = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            jedis.hincrBy(key, incrementField, incrementValue);
            jedis.hset(key, dateField, dateValue);
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed:key=" +  key+"&incrementField="+ incrementField, incrementValue, e);
        } finally {
            release(jedis, isBroken);
        }
    }

    public static  String getStringFromJedis(String key) {
        String value = null;
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = StringUtils.isNotEmpty(value) && !"nil".equalsIgnoreCase(value) ? value : null;
            }
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed: key=" + key, value, e);
        } finally {
            release(jedis, isBroken);
        }
        return value;
    }

    public static  List<String> getStringFromJedis(String[] keys) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            return jedis.mget(keys);
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed:" +  Arrays.toString(keys), e);
        } finally {
            release(jedis, isBroken);
        }
        return null;
    }

    public static  List<String> getListFromJedis(String key){
        List<String> list = null;
        boolean isBroken = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            if (jedis.exists(key)) {
                list = jedis.lrange(key, 0, -1);
            }
        } catch (JedisException e) {
            isBroken = true;
            logger.warn("failed:key" + key, list != null ? list.size() : 0, e);
        } finally {
            release(jedis, isBroken);
        }
        return list;
    }

    public static  Set<String> getSetFromJedis(String key){
        Set<String> list = null;
        boolean isBroken = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            if (jedis.exists(key)) {
                list = jedis.smembers(key);
            }
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed:key=" +key, list != null ? list.size() : 0, e);
        } finally {
            release(jedis, isBroken);
        }
        return list;
    }

    public static  Map<String, String> getHashMapFromJedis(String key) {
        Map<String, String> hashMap = null;
        boolean isBroken = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            hashMap = jedis.hgetAll(key);
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed:key=" +  key,
                                       hashMap != null ? hashMap.size() : 0, e);
        } finally {
            release(jedis, isBroken);
        }
        return hashMap;
    }

    public static  String getHashMapValueFromJedis(String key, String field) {
        String value = null;
        boolean isBroken = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                jedis.select(getDBIndex());
                if (jedis.exists(key)) {
                    value = jedis.hget(key, field);
                }
            }
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed:key=" +  key+"&field="+field, value, e);
        } finally {
            release(jedis, isBroken);
        }
        return value;
    }
    
    /**
     * 按对象存入缓存
     * @param key
     * @param obj
     * @param clazz
     * @param cacheSeconds
     */
    public static String addObjectToJedis(String key, Object obj,Class<?> clazz, int cacheSeconds) {
    	String result=StringUtils.EMPTY;
        if (obj != null) {
            Jedis jedis = null;
            boolean isBroken = false;
            try {
                jedis = getJedis();
                jedis.select(getDBIndex());
                //if (jedis.exists(key)) {
                //    jedis.del(key);
                //}
                //对对象进行kryo序列化
                byte[] bt= serialize(obj);
                if(null != bt){
                	result=jedis.set(key.getBytes(), bt);
                }
                if (cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }
            } catch (JedisException e) {
                isBroken = true;
                logger.warn("failed: key=" + key, e);
            } catch (Exception e) {
                isBroken = true;
                logger.warn("failed: key=" +  key, e);
            } finally {
                release(jedis, isBroken);
            }
        }
        return result;
    }
    
    /**
     * 按对象存入缓存
     * @param key
     * @param obj
     * @param clazz
     * @param cacheSeconds
     */
    public static Object getObjectFromJedis(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            byte[] reslut = jedis.get(key.getBytes());
            //对详细进行反序列化
            return unserialize(reslut);
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed:key=" + key, e);
        } finally {
            release(jedis, isBroken);
        }
        return null;
    }
    
    
    /**
     * 反序列化
     * 
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
    	if(bytes!=null){
	        ByteArrayInputStream bais = null;
	        try {
	            // 反序列化
	            bais = new ByteArrayInputStream(bytes);
	            ObjectInputStream ois = new ObjectInputStream(bais);
	            return ois.readObject();
	        } catch (Exception e) {
	        	logger.error(e.getMessage(),e);
	        }
    	}
        return null;
    }
    
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        }
        return null;
    }
    
    public static Long getIdentifyId(String identifyName) {
        boolean isBroken = false;
        Jedis jedis = null;
        Long identify = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                jedis.select(getDBIndex());
                identify = jedis.incr(identifyName);
                if (identify == 0) {
                    return jedis.incr(identifyName);
                } else {
                    return identify;
                }
            }
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed:identifyName=" +  identifyName,identify, e);
        } finally {
            release(jedis, isBroken);
        }
        return null;
    }

    /**
     * 删除某db的某个key值
     * @param key
     * @return
     */
    public static Long delKeyFromJedis(String key) {
        boolean isBroken = false;
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            return jedis.del(key);
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed:delKeyFromJedis", e);
        } finally {
            release(jedis, isBroken);
        }
        return result;
    }

    /**
     * 根据dbIndex flushDB每个shard
     *
     * @param dbIndex
     */
    public static void flushDBFromJedis(int dbIndex) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            jedis.flushDB();
        } catch (Exception e) {
            isBroken = true;
            logger.warn("failed:flushDBFromJedis", e);
        } finally {
            release(jedis, isBroken);
        }
    }

    public static boolean existKey(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            return jedis.exists(key);
        } catch (Exception e) {
            isBroken = true;
            logger.warn( "failed:key=" + key, e);
        } finally {
            release(jedis, isBroken);
        }
        return false;
    }
    
    public static String getQueue(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(getDBIndex());
            return jedis.lpop(key);
        } catch (Exception e) {
            isBroken = true;
            logger.warn( "failed:key=" + key, e);
        } finally {
            release(jedis, isBroken);
        }
        return null;
    }
      
    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    
    public static void setJedisPool(JedisPool jedisPool) {
        JedisCacheTools.jedisPool = jedisPool;
    }
    
}
