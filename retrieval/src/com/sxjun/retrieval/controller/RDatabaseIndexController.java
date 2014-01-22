/**
 * code generation
 */
package com.sxjun.retrieval.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import com.jfinal.core.Controller;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

/**
 * 数据库索引管理Controller
 * @author sxjun
 * @version 2014-01-14
 */
public class RDatabaseIndexController extends Controller {

	public void index() {
	}
	
	public void list() {
		List<RDatabaseIndex> rDatabaseIndexList = getRDatabaseIndexList(); 
		setAttr("rDatabaseIndex",rDatabaseIndexList);
		render("rDatabaseIndexList.jsp");
	}
	
	public void form(){
		String id = getPara();
		if(StringUtils.isNotBlank(id))
			setAttr("rDatabaseIndex",getRDatabaseIndexList().get(0));
		render("rDatabaseIndexForm.jsp");
	}
	
	public void save(){
		RDatabaseIndex rDatabaseIndex = getModel(RDatabaseIndex.class);
		render("rDatabaseIndexForm.jsp");
	}
	
	public void delete(){
		String id=getPara();
		list();
	}

	public List<RDatabaseIndex> getRDatabaseIndexList(){
		return null;
	}
}
