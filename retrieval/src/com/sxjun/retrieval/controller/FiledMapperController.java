/**
 * code generation
 */
package com.sxjun.retrieval.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.sxjun.retrieval.pojo.FiledMapper;

/**
 * 字段映射Controller
 * @author sxjun
 * @version 2014-01-14
 */
public class FiledMapperController extends Controller {

	public void index() {
	}
	
	public void list() {
		List<FiledMapper> filedMapperList = getFiledMapperList(); 
		setAttr("filedMapper",filedMapperList);
		render("filedMapperList.jsp");
	}
	
	public void form(){
		String id = getPara();
		if(StringUtils.isNotBlank(id))
			setAttr("filedMapper",getFiledMapperList().get(0));
		render("filedMapperForm.jsp");
	}
	
	public void save(){
		FiledMapper filedMapper = getModel(FiledMapper.class);
		render("filedMapperForm.jsp");
	}
	
	public void delete(){
		String id=getPara();
		list();
	}

	public List<FiledMapper> getFiledMapperList(){
		List<FiledMapper> l = new ArrayList<FiledMapper>();
		FiledMapper filedMapper = new FiledMapper();
		filedMapper.setId(UUID.randomUUID().toString());
		filedMapper.setSqlField("f");
		filedMapper.setIndexField("s");
		l.add(filedMapper);
		return l;
	}
}
