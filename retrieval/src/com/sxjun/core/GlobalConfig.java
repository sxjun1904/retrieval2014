package com.sxjun.core;

import java.util.TimeZone;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.sxjun.core.interceptor.SessionInterceptor;
import com.sxjun.core.plugin.berkeley.BerkeleyPlugin;
import com.sxjun.core.plugin.redis.RedisPlugin;
import com.sxjun.core.routes.RetrievalAdminRoutes;
import com.sxjun.core.routes.RetrievalFrontRoutes;
import com.sxjun.core.routes.SystemRoutes;
import com.sxjun.retrieval.common.Global;

import frame.base.core.util.RSAUtil;


public class GlobalConfig extends JFinalConfig {
	private final String json = System.getenv("VCAP_SERVICES");
	private boolean isLocal = StringKit.isBlank(json);
	
	@Override
	public void configConstant(Constants me) {
		Global.properties = loadPropertyFile("conf.properties");
		Global.pageSize = getPropertyToInt("pageSize",10);
		Global.frontPath = getProperty("frontPath","f");
	    Global.adminPath = getProperty("adminPath","a");
	    Global.urlSuffix = getProperty("urlSuffix");
	    Global.databasetype = getProperty("databaseType");
	    Global.initClazz = getProperty("initClazz");
	    Global.licenseInfo = RSAUtil.getLicenceInfo();
	    
		if (isLocal) {
			me.setDevMode(true);
		}
		me.setViewType(ViewType.JSP);
		me.setBaseViewPath("/WEB-INF/");
		me.setError404View("/WEB-INF/error/404.jsp");
		me.setError500View("/WEB-INF/error/500.jsp");
	}
	public void configRoute(Routes me) {
		me.add(new RetrievalAdminRoutes());
		me.add(new RetrievalFrontRoutes());
		me.add(new SystemRoutes());
	}
	public void configPlugin(Plugins me) {
		me.add(new EhCachePlugin());
		
		String host,port,dbIndex;
		host = getProperty("host", "127.0.0.1");
		port = getProperty("port", "6379");
		dbIndex = getProperty("dbIndex", "0");
		RedisPlugin redisPlugin = new RedisPlugin(host,Integer.parseInt(port),Integer.parseInt(dbIndex));
		me.add(redisPlugin);
		
		BerkeleyPlugin berkeleyPlugin = new BerkeleyPlugin();
		me.add(berkeleyPlugin);
	}
	public void configInterceptor(Interceptors me) {
		me.add(new SessionInterceptor());
	}
	public void configHandler(Handlers me) {
		
	}
	
	@Override
	public void afterJFinalStart() {
		// 设置默认时间为北京时间
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
	}
}