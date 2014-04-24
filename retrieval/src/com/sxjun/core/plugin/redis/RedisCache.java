package com.sxjun.core.plugin.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.SerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;


/**
 * User: sxjun
 * Date: 13-10-11
 * Time: 上午7:21
 */
public class RedisCache {
    private String cacheName;
    private Jedis cache;
    
    
    public Transaction mutil(){
    	Transaction transaction = cache.multi();
    	RedisManager.getJedisPool().returnResource(cache);
		return transaction;
    }
    
    public List<Object> exec(Transaction transaction){
    	List<Object> trl = transaction.exec();
    	RedisManager.getJedisPool().returnResource(cache);
    	return trl;
    }
    
    public Pipeline pipeline(){
    	Pipeline p = cache.pipelined();
    	RedisManager.getJedisPool().returnResource(cache);
    	return p;
    }
    
    public void pipelineSync(Pipeline p){
    	p.sync();
    	RedisManager.getJedisPool().returnResource(cache);
    }
    
    public Object _get(Object key){
    	 if (null == key)
             return null;
         byte[] b = cache.get((cacheName+":"+String.valueOf(key)).getBytes());
         return b == null ? null : SerializationUtils.deserialize(b);
    }
    public Object get(Object key) {
        Object o = _get(key);
        RedisManager.getJedisPool().returnResource(cache);
        return o;
    }

    public void put(Object key, Object value) {
        cache.set((cacheName+":"+String.valueOf(key)).getBytes(), value == null ? null : SerializationUtils .serialize((Serializable) value));
        RedisManager.getJedisPool().returnResource(cache);
    }
    
    public List<String>_keys(){
    	List<String> keys = new ArrayList<String>();
        Set<String> keys_list = cache.keys(cacheName+":"+String.valueOf("*"));
        for (String bs : keys_list) {
            keys.add(bs);
        }
        return keys;
    }

    public List<String> getKeys(){
        List<String> keys = _keys();
        RedisManager.getJedisPool().returnResource(cache);
        return keys;
    }
    
    public List getObjs(){
    	List keyList = new ArrayList();
    	List<String> keys = _keys();
    	for(String key : keys){
    		String[] _key = key.split(":");
    		if(_key.length==2)
    			keyList.add(_get(_key[1]));
    	}
    	RedisManager.getJedisPool().returnResource(cache);
    	return keyList;
    }
    

    public void remove(Object key) {
        cache.expire((cacheName+":"+String.valueOf(key)).getBytes(), 0);
        RedisManager.getJedisPool().returnResource(cache);
    }

    public void removeAll(){
        List keys = this.getKeys();
        for (Object key : keys) {
            this.remove(key);
        }
    }

    public RedisCache(String region, Jedis cache) {
        this.cache = cache;
        this.cacheName = region;
    }

    public Jedis getCache() {
        return cache;
    }

    public void setCache(Jedis cache) {
        this.cache = cache;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}
