/**
 * code generation
 */
package com.sxjun.retrieval.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import com.jfinal.core.Controller;
import com.sxjun.core.common.controller.BaseController;
import com.sxjun.core.common.proxy.ServiceProxy;
import com.sxjun.core.common.service.CommonService;
import com.sxjun.retrieval.pojo.IKWords;

import frame.retrieval.oth.ik.IKWordsUtil;

/**
 * 分词工具Controller
 * @author sxjun
 * @version 2014-07-18
 */
public class IKWordsController extends BaseController<IKWords> {
	private CommonService<IKWords> commonservice = new ServiceProxy<IKWords>().getproxy();
	public void list() {
		render("iKWordsList.jsp");
	}
	
	public void words(){
		IKWords ik = getModel(IKWords.class);
		List<String> l = IKWordsUtil.ikTokens(ik.getWords());
		String ikWords="";
		for(String s:l){
			ikWords+=s+" | ";
		}
		setAttr("iKWords",ikWords.substring(0, ikWords.lastIndexOf("|")));
		renderJson(new String[]{"iKWords"});
	}
	
	
	public void save(){
		IKWords ik = getModel(IKWords.class);
		if(!IKWordsUtil.isExists(ik.getWords())){
			ik.setId(ik.getWords());
			commonservice.put(IKWords.class, ik.getId(), ik);
			IKWordsUtil.addWord(ik.getWords());
			msg = MSG_OK;
		}else{
			msg = MSG_FAULT;
		}
		setAttr("msg",msg);
		renderJson(new String[]{"msg"});
	}
	
	public void delete(){
		IKWords ik = getModel(IKWords.class);
		if(IKWordsUtil.isExists(ik.getWords())){
			commonservice.remove(IKWords.class, ik.getWords());
			IKWordsUtil.disableWord(ik.getWords());
			msg = MSG_OK;
		}else{
			msg = MSG_FAULT;
		}
		setAttr("msg",msg);
		renderJson(new String[]{"msg"});
	}
}
