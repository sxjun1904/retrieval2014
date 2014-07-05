package com.sxjun.retrieval.controller;

import java.util.UUID;

import com.jfinal.core.Controller;
import com.sxjun.retrieval.controller.proxy.ServiceProxy;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.InitField;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

public class InitController extends Controller {
	private CommonService<InitField> commonService = new ServiceProxy<InitField>().getproxy();
	public void index(){
		
	}
	
    public void init() { 
    	///////////////////////////////////////////////////////////
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
		commonService.put(InitField.class, if0.getId(), if0);
		commonService.put(InitField.class, if1.getId(), if1);
		////////////////////////////////////////////////////////////
    } 
}
