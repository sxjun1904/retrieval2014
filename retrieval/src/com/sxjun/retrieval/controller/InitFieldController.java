package com.sxjun.retrieval.controller;

import java.util.Map;

import com.jfinal.aop.Before;
import com.sxjun.core.common.controller.BaseController;
import com.sxjun.core.common.utils.DictUtils;
import com.sxjun.core.interceptor.BerkeleyInterceptor;
import com.sxjun.retrieval.pojo.InitField;

public class InitFieldController  extends BaseController<InitField> {
	
	public void list() {
		list(InitField.class);
	}
	
	public void form(){
		Map<String,String> itemTypes = DictUtils.getDictMap(DictUtils.ITEMTYPE_TYPE);
		setAttr("itemTypes",itemTypes);
		
		form(InitField.class);
	}
	
	public void save(){
		save(getModel(InitField.class));
	}
	
	public void delete(){
		delete(InitField.class);
	}
	
	/*public List<InitField> getInitFieldList(){
		List<InitField> dblist = new ArrayList<InitField>();
		InitField initField = new InitField();
		initField.setId(UUID.randomUUID().toString());
		initField.setField("pageurl");
		initField.setDefaultValue("null");
		initField.setFieldType("0");
		initField.setDescription("跳转地址");
		dblist.add(initField);
		return dblist;
	}*/
}
