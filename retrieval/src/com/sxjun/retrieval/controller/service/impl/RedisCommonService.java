package com.sxjun.retrieval.controller.service.impl;

import java.util.List;

import com.jfinal.aop.Before;
import com.sxjun.core.interceptor.BerkeleyInterceptor;
import com.sxjun.core.plugin.redis.RedisKit;
import com.sxjun.retrieval.controller.service.CommonService;

public class RedisCommonService<T> implements CommonService<T>{
	
	public void put(Class clazz,String id,T t ){
		RedisKit.put(clazz.getSimpleName(), id, t);
	}
	
	public void remove(Class clazz, String id) {
		RedisKit.remove(clazz.getSimpleName(), id);
	}

	public <T> T get(Class clazz, String id) {
		return RedisKit.get(clazz.getSimpleName(), id);
	}

	public List<T> getObjs(Class clazz) {
		return RedisKit.getObjs(clazz.getSimpleName());
	}
	
	public void lpush(String cacheName, String pinyin,String hanzi){
		RedisKit.lpush(cacheName, pinyin, hanzi);
	}
	
	public void ltrim(String cacheName, String pinyin,long start,long end){
		RedisKit.ltrim(cacheName, pinyin, start, end);
	}
	
	public List<String> lrange(String cacheName, String pinyin,int start,int end){
		return RedisKit.lrange(cacheName, pinyin, start,end);
	}

}
