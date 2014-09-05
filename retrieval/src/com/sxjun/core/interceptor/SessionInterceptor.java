package com.sxjun.core.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.sxjun.common.proxy.ServiceProxy;
import com.sxjun.common.service.CommonService;
import com.sxjun.common.utils.Global;
import com.sxjun.system.pojo.User;


public class SessionInterceptor implements Interceptor{
	private static final Logger logger = Logger.getLogger(SessionInterceptor.class);
	private CommonService<User> userService = new ServiceProxy<User>().getproxy();
	private static boolean isInit = false;
	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();  
		User loginUser = controller.getSessionAttr("user");  
//		if (loginUser != null && loginUser.canVisit(ai.getActionKey()))  
		String actionKey = ai.getActionKey();
		//判断license
		Map<String,Object> m = Global.getLicenseInfo();
		if(m!=null){
			String productName = (String) m.get("productName");
			long deadline = (Long) m.get("deadline");
			if(productName.equals(Global.getConfig("productName"))&&(deadline > System.currentTimeMillis())){
				if( "/".equals(actionKey)){
					if(isInit){
						ai.invoke(); 
					}else{
						ctrlto(controller);
					}
				}else if (loginUser != null || actionKey.startsWith("/"+Global.frontPath+"/") || "/rest/".equals(actionKey)){
						ai.invoke();
				}else{
					if(isInit)
						controller.render("/WEB-INF/login.jsp");
					else{
						ctrlto(controller);
					}
				}
			}else{
				controller.render("/WEB-INF/register.jsp");
			}
		}else{
			controller.render("/WEB-INF/register.jsp");
		}
	}
	
	public void ctrlto(Controller controller){
		List<User> userList = userService.getObjs(User.class);
		if(userList!=null&&userList.size()>0){
			isInit = true;
			controller.render("/WEB-INF/login.jsp");
		}else{
			controller.render("/WEB-INF/init.jsp");
		}
	}
	
	public static void setInit(boolean isInit) {
		SessionInterceptor.isInit = isInit;
	}
	
	public static boolean isInit() {
		return isInit;
	}
}
