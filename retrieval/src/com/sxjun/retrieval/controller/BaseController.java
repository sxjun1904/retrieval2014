package com.sxjun.retrieval.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.sxjun.core.plugin.redis.RedisKit;
import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.IndexCagetory;
import com.sxjun.retrieval.pojo.InitField;
import com.sxjun.system.pojo.BasePojo;

public abstract class BaseController<T  extends BasePojo> extends Controller{
	protected final static String MSG_OK = "0";
	protected final static String MSG_FAULT = "1";
	protected String msg = MSG_FAULT;
	
	public void index() {}
	
	/**
	 * 
	 * @param c
	 */
	public void list(T c){
		 list(c.getClass().getSimpleName());
	}
	
	/**
	 * 
	 * @param c
	 * @param viewname
	 */
	public void list(T c,String viewname){
		 list(c.getClass().getSimpleName(),viewname);
	}
	/**
	 * @param db
	 */
	public void list(String db) {
		String viewname = StringKit.firstCharToLowerCase(db);
		list( db, viewname);
	}
	
	/**
	 * 
	 * @param db
	 * @param viewname
	 */
	public void list(String db,String viewname){
		List indexCagetoryList = RedisKit.getObjs(db); 
		setAttr(viewname,indexCagetoryList);
		render(viewname+"List.jsp");
	}
	
	public void form(T c){
		 form(c.getClass().getSimpleName());
	}
	
	public void form(T c,String viewname){
		 form(c.getClass().getSimpleName(),viewname,null);
	}
	
	public void form(String db){
		 String viewname = StringKit.firstCharToLowerCase(db);
		 form(db ,viewname,null);
	}
	
	public void form(String db ,String viewname,String key){
		String id = getPara(key==null?"id":key);
		if(StringUtils.isNotBlank(id))
			setAttr(viewname,RedisKit.get(db, id));
		render(viewname+"Form.jsp");
	}
	
	/**
	 * 
	 * @param t
	 */
	public void save(T t){
		try {
			String id = t.getId()==null?UUID.randomUUID().toString():t.getId();
			t.setId(id);
			RedisKit.put(t.getClass().getSimpleName(), id, t);
			msg = MSG_OK;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setAttr("msg",msg);
		renderJson(new String[]{"msg"});
	}
	
	/**
	 * 
	 * @param c
	 */
	public void delete(Class c){
		delete(c.getClass().getSimpleName());
	}
	
	/**
	 * 
	 * @param t
	 */
	public void delete(String t){
		String id=getPara();
		RedisKit.remove(t, id);
		list();
	}
	
	abstract void list();
	abstract void save();
	
}
