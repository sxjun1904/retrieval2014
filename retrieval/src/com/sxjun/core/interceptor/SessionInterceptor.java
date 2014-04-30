package com.sxjun.core.interceptor;

import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.sxjun.retrieval.common.Global;
import com.sxjun.retrieval.pojo.User;

import frame.base.core.util.RSAUtil;


public class SessionInterceptor implements Interceptor{
	private static final Logger logger = Logger.getLogger(SessionInterceptor.class);
	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();  
		User loginUser = controller.getSessionAttr("user");  
//		if (loginUser != null && loginUser.canVisit(ai.getActionKey()))  
		String actionKey = ai.getActionKey();
		Map<String,Object> m = Global.getLicenseInfo();
		if(m!=null){
			String productName = (String) m.get("productName");
			long deadline = (Long) m.get("deadline");
			if(productName.equals(Global.getConfig("productName"))&&(deadline > System.currentTimeMillis())){
				if (loginUser != null || "/".equals(actionKey) || (actionKey).startsWith("/"+Global.frontPath+"/") || "/rest/".equals(actionKey))
					ai.invoke();  
				else 
					controller.render("/WEB-INF/login.jsp");  
			}else{
				controller.render("/WEB-INF/register.jsp");
			}
		}else{
			controller.render("/WEB-INF/register.jsp");
		}
	}
}
