package com.sxjun.retrieval.controller.service;

import java.util.List;

import com.sxjun.core.plugin.redis.RedisKit;

public class CommonService<T> {
	
	public void put(String tablename,String id,T t ){
		RedisKit.put(tablename, id, t);
	}
	
	public void remove(String tablename, String id) {
		RedisKit.remove(tablename, id);
	}

	public <T> T get(String tablename, String id) {
		return RedisKit.get(tablename, id);
	}

	public List<T> getObjs(String t) {
		return RedisKit.getObjs(t);
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
