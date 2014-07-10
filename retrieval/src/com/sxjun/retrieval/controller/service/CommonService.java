package com.sxjun.retrieval.controller.service;

import java.util.List;

import com.sxjun.core.plugin.redis.RedisKit;

public interface CommonService<T> {
	
	public void put(Class clazz,String id,T t );
	
	public void remove(Class clazz, String id);

	public <T> T get(Class clazz, String id) ;

	public List<T> getObjs(Class clazz);
	
	public void lpush(String cacheName, String pinyin,String hanzi);
	
	public void ltrim(String cacheName, String pinyin,long start,long end);
	
	public List<String> lrange(String cacheName, String pinyin,int start,int end);
}
