package com.sxjun.core.interceptor;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;


public class RedisTx implements Interceptor{
	private static final Logger logger = Logger.getLogger(RedisTx.class);
	public void intercept(ActionInvocation ai) {
		logger.info("Before action invoking>>>start Redis Transaction");
		ai.invoke();
		logger.info("After action invoking>>>submit Redis Transaction");		
	}
}
