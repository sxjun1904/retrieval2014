package com.sxjun.retrieval.controller.proxy;

import com.sxjun.retrieval.common.Global;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.controller.service.impl.BerkeleyCommonService;
import com.sxjun.retrieval.controller.service.impl.RedisCommonService;

public class ServiceProxy<T> {
	private static final String BERKELEY_PROXY = "0";
	private static final String REDIS_PROXY = "1";
	public CommonService<T> getproxy(){
		if(REDIS_PROXY.endsWith(Global.getDatabasetype()))
			return new RedisCommonService<T>();
		else if(BERKELEY_PROXY.endsWith(Global.getDatabasetype()))
			return new BerkeleyCommonService<T>();
		else
			return new BerkeleyCommonService<T>();
	}
	
}
