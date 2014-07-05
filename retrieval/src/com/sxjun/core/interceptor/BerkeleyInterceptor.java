package com.sxjun.core.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class BerkeleyInterceptor implements Interceptor{

	@Override
	public void intercept(ActionInvocation ai) {
		System.out.println("Before action invoking");
		ai.invoke();
		System.out.println("After action invoking");
	}

}
