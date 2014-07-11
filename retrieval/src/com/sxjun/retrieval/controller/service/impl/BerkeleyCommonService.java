package com.sxjun.retrieval.controller.service.impl;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.sxjun.core.interceptor.BerkeleyInterceptor;
import com.sxjun.core.plugin.berkeley.BerkeleyKit;
import com.sxjun.retrieval.controller.service.CommonService;

public class BerkeleyCommonService<T> implements CommonService<T>{
	
	public void put(Class clazz,String id,T t ){
		BerkeleyKit.put(clazz, id, t);
		CacheKit.remove(clazz.getSimpleName(), clazz.getSimpleName());
		CacheKit.remove(clazz.getSimpleName(), clazz.getSimpleName()+":"+id);
		BerkeleyKit.close(clazz);
	}
	
	public void remove(Class clazz, String id) {
		BerkeleyKit.remove(clazz, id);
		CacheKit.remove(clazz.getSimpleName(), clazz.getSimpleName()+":"+id);
		CacheKit.remove(clazz.getSimpleName(), clazz.getSimpleName());
		BerkeleyKit.close(clazz);
	}

	public <T> T get(final Class clazz, final String id) {
		T t = CacheKit.get(clazz.getSimpleName(), clazz.getSimpleName()+":"+id, new IDataLoader(){
			public Object load() {
				return BerkeleyKit.get(clazz, id);
			}});
		BerkeleyKit.close(clazz);
		return t;
	}

	public List<T> getObjs(final Class clazz) {
		List<T> t = CacheKit.get(clazz.getSimpleName(), clazz.getSimpleName(), new IDataLoader(){
			public Object load() {
				return BerkeleyKit.getObjs(clazz);
			}});
		BerkeleyKit.close(clazz);
		return t;
	}

	@Override
	public void lpush(String cacheName, String pinyin, String hanzi) {
		
	}

	@Override
	public void ltrim(String cacheName, String pinyin, long start, long end) {
		
	}

	@Override
	public List<String> lrange(String cacheName, String pinyin, int start, int end) {
		return null;
	}
	
}
