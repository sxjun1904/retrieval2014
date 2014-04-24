package com.sxjun.retrieval.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.sxjun.core.plugin.redis.RedisKit;
import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.pojo.IndexCagetory;
import com.sxjun.retrieval.pojo.InitField;

public class InitFieldController  extends BaseController<InitField> {
	private final static String cachename = InitField.class.getSimpleName();
	
	public void list() {
		list(cachename);
	}
	
	public void form(){
		Map<String,String> itemTypes = DictUtils.getDictMap(DictUtils.ITEMTYPE_TYPE);
		setAttr("itemTypes",itemTypes);
		
		form(cachename);
	}
	
	public void save(){
		save(getModel(InitField.class));
	}
	
	public void delete(){
		delete(cachename);
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
