package com.sxjun.retrieval.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.common.Page;
import com.sxjun.retrieval.common.SQLUtil;
import com.sxjun.retrieval.controller.proxy.ServiceProxy;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.Database;

import frame.base.core.util.JdbcUtil;
import frame.retrieval.engine.RetrievalType.RDatabaseType;


public class DatabaseController extends BaseController<Database> {
	CommonService<Database> commonService = new ServiceProxy<Database>().getproxy();
	
	public void list() {
		Page<Database> page = new Page<Database>(super.getRequest(), super.getResponse());
		List<Database> objs = commonService.getObjs(Database.class);
		page.setList(objs);
		setAttr("page",page);
		render("databaseList.jsp");
	}
	
	public void form(){
		Map<String,String> dt = DictUtils.getDictMap(DictUtils.DATABASE_TYPE);
		setAttr("databaseTypes",dt);
		form(Database.class);
	}
	
	
	public void save(){
		Database db = getModel(Database.class);
		if(databaseTest(db)){
			save(db);
		}else{
			msg = MSG_FAULT;
			setAttr("msg",msg);
			renderJson(new String[]{"msg"});
		}
	}
	
	public void delete(){
		delete(Database.class);
	}
	
	public void databaseTest(){
		Database db = getModel(Database.class);
		if(databaseTest(db))
			msg = MSG_OK;
		else
			msg = MSG_FAULT;
		setAttr("msg",msg);
		renderJson(new String[]{"msg"});
	}
	
	
	public boolean databaseTest(Database db){
		try {
			RDatabaseType type = DictUtils.changeToRDatabaseType(db.getDatabaseType());
			Connection conn = SQLUtil.getConnection(db);
			
			String testSql = SQLUtil.getTestSql(type);
			Map map = JdbcUtil.getSingleMapObject(conn, testSql,true);
			return !map.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*public List<Database> getDbs(){
		List<Database> dblist = new ArrayList<Database>();
		Database db = new Database();
		db.setId(UUID.randomUUID().toString());
		db.setDatabaseName("测试数据库");
		db.setDatabaseType("1");
		db.setIp("127.0.0.1");
		db.setPort("3306");
		db.setUser("root");
		db.setPassword("11111");
		dblist.add(db);
		return dblist;
	}*/
	
}
