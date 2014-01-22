package com.sxjun.retrieval.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.sxjun.retrieval.common.Page;
import com.sxjun.retrieval.pojo.Database;


public class DatabaseController extends Controller {

	public void index() {
		
	}
	
	public void list() {
		Page<Database> page = new Page<Database>(super.getRequest(), super.getResponse());
		page.setList(getDbs());
		setAttr("page",page);
		render("databaseList.jsp");
	}
	
	public void form(){
		String id = getPara();
		if(StringUtils.isNotBlank(id))
			setAttr("database",getDbs().get(0));
		render("databaseForm.jsp");
	}
	
	public void save(){
		Database db = getModel(Database.class);
		render("databaseForm.jsp");
	}
	
	public void delete(){
		String id=getPara();
		list();
	}
	
	public List<Database> getDbs(){
		List<Database> dblist = new ArrayList<Database>();
		Database db = new Database();
		db.setId(UUID.randomUUID().toString());
		db.setDatabaseName("测试数据库");
		db.setDatabaseType("Oracle");
		db.setIp("127.0.0.1");
		db.setPort("3306");
		db.setUser("root");
		db.setPassword("11111");
		dblist.add(db);
		return dblist;
	}
	
}
