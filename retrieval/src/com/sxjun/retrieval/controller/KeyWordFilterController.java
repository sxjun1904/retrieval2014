/**
 * code generation
 */
package com.sxjun.retrieval.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import com.jfinal.core.Controller;
import com.sxjun.common.controller.BaseController;
import com.sxjun.common.proxy.ServiceProxy;
import com.sxjun.common.service.CommonService;
import com.sxjun.retrieval.pojo.KeyWordFilter;

/**
 * 搜索过滤Controller
 * @author sxjun
 * @version 2014-07-18
 */
public class KeyWordFilterController extends BaseController<KeyWordFilter> {
	private CommonService<KeyWordFilter> commonservice = new ServiceProxy<KeyWordFilter>().getproxy();
	public void list() {
		KeyWordFilter k = commonservice.get(KeyWordFilter.class,KeyWordFilter.class.getSimpleName()); 
		setAttr("keyWordFilter",k);
		render("keyWordFilterList.jsp");
	}
	
	public void save(){
		KeyWordFilter k =getModel(KeyWordFilter.class);
		try {
			k.setId(KeyWordFilter.class.getSimpleName());
			commonservice.put(k.getClass(), k.getId(), k);
			msg = MSG_OK;
		} catch (Exception e) {
			msg = MSG_FAULT;
			e.printStackTrace();
		}
		
		setAttr("msg",msg);
		renderJson(new String[]{"msg"});
	}
	
}
