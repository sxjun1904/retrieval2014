package com.sxjun.system.controller;

import com.jfinal.core.Controller;
import com.sxjun.system.pojo.User;

public class LoginController extends Controller{
	public void index(){
		User user = getModel(User.class);
		System.out.println("username="+user.getUsername());
		if(user!=null&&"admin".equals(user.getUsername())){
			// 放数据至session 
			setSessionAttr("user", user); 
			render("/WEB-INF/index.jsp");
		}else if(getSessionAttr("user")!=null){
			render("/WEB-INF/index.jsp");
		}else{
			render("/WEB-INF/login.jsp");
		}
		
		
	}
	
}
