/**
 * code generation
 */
package com.sxjun.retrieval.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import com.jfinal.core.Controller;
import com.sxjun.retrieval.pojo.IndexCagetory;
import com.sxjun.retrieval.pojo.InitField;

/**
 * 索引分类Controller
 * @author sxjun
 * @version 2014-01-14
 */
public class IndexCagetoryController extends Controller {

	public void index() {
	}
	
	public void list() {
		List<IndexCagetory> indexCagetoryList = getIndexCagetoryList(); 
		setAttr("indexCagetory",indexCagetoryList);
		render("indexCagetoryList.jsp");
	}
	
	public void form(){
		String id = getPara();
		if(StringUtils.isNotBlank(id))
			setAttr("indexCagetory",getIndexCagetoryList().get(0));
		render("indexCagetoryForm.jsp");
	}
	
	public void save(){
		IndexCagetory indexCagetory = getModel(IndexCagetory.class);
		render("indexCagetoryForm.jsp");
	}
	
	public void delete(){
		String id=getPara();
		list();
	}

	public List<IndexCagetory> getIndexCagetoryList(){
		List<IndexCagetory> l = new ArrayList<IndexCagetory>();
		IndexCagetory indexCagetory = new IndexCagetory();
		indexCagetory.setId(UUID.randomUUID().toString());
		indexCagetory.setIndexInfoType("谁家");
		indexCagetory.setIndexPath("test/java");
		indexCagetory.setIndexPathType("DB");
		l.add(indexCagetory);
		return l;
	}
}
