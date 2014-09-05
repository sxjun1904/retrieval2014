package com.sxjun.common.controller;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.sxjun.common.proxy.ServiceProxy;
import com.sxjun.common.service.CommonService;
import com.sxjun.system.pojo.BasePojo;

public abstract class BaseController<T  extends BasePojo> extends Controller{
	protected final static String MSG_OK = "0";
	protected final static String MSG_FAULT = "1";
	protected String msg = MSG_FAULT;
	
	private CommonService<T> commonservice = new ServiceProxy<T>().getproxy();
	
	public void index() {}
	
	/**
	 * 
	 * @param c
	 */
	public void list(T t){
		 list(t.getClass());
	}
	
	/**
	 * 
	 * @param c
	 * @param viewname
	 */
	public void list(T t,String viewname){
		 list(t.getClass(),viewname);
	}
	/**
	 * @param db
	 */
	public void list(Class clazz) {
		String viewname = StrKit.firstCharToLowerCase(clazz.getSimpleName());
		list( clazz, viewname);
	}
	
	/**
	 * 
	 * @param db
	 * @param viewname
	 */
	public void list(Class clazz,String viewname){
		List list = commonservice.getObjs(clazz); 
		setAttr(viewname,list);
		render(viewname+"List.jsp");
	}
	
	public void form(T t){
		 form(t.getClass());
	}
	
	public void form(T t,String viewname){
		 form(t.getClass(),viewname,null);
	}
	
	public void form(Class clazz){
		 String viewname = StrKit.firstCharToLowerCase(clazz.getSimpleName());
		 form(clazz ,viewname,null);
	}
	
	public void form(Class clazz ,String viewname,String key){
		String id = getPara(key==null?"id":key);
		if(StringUtils.isNotBlank(id))
			setAttr(viewname,commonservice.get(clazz, id));
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
			commonservice.put(t.getClass(), id, t);
			msg = MSG_OK;
		} catch (Exception e) {
			msg = MSG_FAULT;
			e.printStackTrace();
		}
		
		setAttr("msg",msg);
		renderJson(new String[]{"msg"});
	}
	
	/**
	 * 
	 * @param t
	 */
	public void delete(Class clazz){
		String id=getPara();
		commonservice.remove(clazz, id);
		list();
	}
	
	public abstract void list();
	public abstract void save();
	
}
