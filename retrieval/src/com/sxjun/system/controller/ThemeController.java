package com.sxjun.system.controller;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.sxjun.core.common.utils.CookieUtils;

public class ThemeController extends Controller{
	
	public void index(){
		
		String theme = getPara("theme");
		
		if (StringUtils.isNotBlank(theme)){
			setCookie("theme", theme, CookieUtils.maxAge);
		}else{
			theme = getCookie("theme");
		}
		redirect(getPara("url"));
	}
}
