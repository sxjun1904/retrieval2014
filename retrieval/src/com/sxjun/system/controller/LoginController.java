package com.sxjun.system.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.sxjun.retrieval.controller.proxy.ServiceProxy;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.system.pojo.User;

import frame.base.core.util.MD5Util;

public class LoginController extends Controller{
	private static final Logger logger = Logger.getLogger(LoginController.class);
	private CommonService<User> commonservice = new ServiceProxy<User>().getproxy();
	public void index(){
		User user = getModel(User.class);
		
		List<User> urList = commonservice.getObjs(User.class);
		if(urList!=null){
			boolean flag = false;
			for(User u:urList){
				if(u.getUsername().equals(user.getUsername())&&u.getPassword().equals(MD5Util.GetMD5Code(user.getPassword()))){
					flag = true;
					break;
				}
			}
			if(flag){
				// 放数据至session 
				setSessionAttr("user", user); 
				logger.info("用户："+user.getUsername()+"登录成功");
				render("/WEB-INF/index.jsp");
			}else if(getSessionAttr("user")!=null){
				render("/WEB-INF/index.jsp");
			}else{
				render("/WEB-INF/login.jsp");
			}
		}else{
			render("/WEB-INF/login.jsp");
		}
	}
}
