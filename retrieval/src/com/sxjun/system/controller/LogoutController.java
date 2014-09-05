package com.sxjun.system.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.sxjun.common.proxy.ServiceProxy;
import com.sxjun.common.service.CommonService;
import com.sxjun.system.pojo.User;

import frame.base.core.util.MD5Util;

public class LogoutController extends Controller{
	private static final Logger logger = Logger.getLogger(LogoutController.class);
	public void index(){
		removeSessionAttr("user");
		render("/WEB-INF/login.jsp");
	}
}
