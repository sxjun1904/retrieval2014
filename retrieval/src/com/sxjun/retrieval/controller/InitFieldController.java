package com.sxjun.retrieval.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.sxjun.retrieval.pojo.InitField;

public class InitFieldController  extends Controller {
	public void index() {
		
	}
	
	public void list() {
		List<InitField> initFieldList = getInitFieldList(); 
		setAttr("initField",initFieldList);
		render("initFieldList.jsp");
	}
	
	public void form(){
		String id = getPara();
		if(StringUtils.isNotBlank(id))
			setAttr("initField",getInitFieldList().get(0));
		render("initFieldForm.jsp");
	}
	
	public void save(){
		InitField db = getModel(InitField.class);
		render("initFieldForm.jsp");
	}
	
	public void delete(){
		String id=getPara();
		list();
	}
	
	public List<InitField> getInitFieldList(){
		List<InitField> dblist = new ArrayList<InitField>();
		InitField initField = new InitField();
		initField.setId(UUID.randomUUID().toString());
		initField.setField("pageurl");
		initField.setDefaultValue("null");
		initField.setFieldType("0");
		initField.setDescription("跳转地址");
		dblist.add(initField);
		return dblist;
	}
}
