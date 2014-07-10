package com.sxjun.retrieval.controller;

import java.util.UUID;

import com.jfinal.core.Controller;
import com.sxjun.core.plugin.berkeley.BerkeleyKit;
import com.sxjun.retrieval.controller.proxy.ServiceProxy;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.InitField;
import com.sxjun.system.pojo.User;

import frame.base.core.util.MD5Util;

public class InitController extends Controller {
	private CommonService<InitField> initFieldService = new ServiceProxy<InitField>().getproxy();
	private CommonService<User> userService = new ServiceProxy<User>().getproxy();
	public void index(){
		init();
	}
	/**
	 * 初始化数据
	 */
    public void init() { 
    	
		/////////////////////////#用户#///////////////////////////////////
		User u = new User();
		u.setId("54b3b8a0-2464-43ea-a170-cb938651fc21");
		u.setUsername("admin");
		u.setPassword(MD5Util.GetMD5Code("11111"));
		userService.put(User.class, u.getId(), u);
		render("/WEB-INF/login.jsp");

    	/////////////////////////#初始字段#///////////////////////////////////
    	InitField if0 = new InitField();
    	if0.setId(UUID.randomUUID().toString());
    	if0.setField("CREATETIME");
    	if0.setDefaultValue("null");
    	if0.setFieldType("NUMBER");
    	if0.setDescription("排序时间");
    	
    	InitField if1 = new InitField();
    	if1.setId(UUID.randomUUID().toString());
    	if1.setField("PAGE_URL");
    	if1.setDefaultValue("null");
    	if1.setFieldType("KEYWORD");
    	if1.setDescription("跳转地址");
    	initFieldService.put(InitField.class, if0.getId(), if0);
    	initFieldService.put(InitField.class, if1.getId(), if1);
    	
    	/////////////////////////##///////////////////////////////////
    } 
    
    public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString());
	}
}
