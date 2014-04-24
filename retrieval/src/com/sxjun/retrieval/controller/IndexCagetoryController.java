/**
 * code generation
 */
package com.sxjun.retrieval.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.sxjun.core.plugin.redis.RedisKit;
import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.pojo.IndexCagetory;

/**
 * 索引分类Controller
 * @author sxjun
 * @version 2014-01-14
 */
public class IndexCagetoryController extends BaseController<IndexCagetory> {
	private final static String cachename = IndexCagetory.class.getSimpleName();
	
	public void list() {
		list(cachename);
	}
	
	public void form(){
		
		Map<String,String> indexPathTypes = DictUtils.getDictMap(DictUtils.INDEXPATH_TYPE);
		setAttr("indexPathTypes",indexPathTypes);
		form(cachename);
	}
	
	public void save(){
		save(getModel(IndexCagetory.class));
	}
	
	public void delete(){
		delete(cachename);
	}
	
	/*public List<IndexCagetory> getIndexCagetoryList(){
		List<IndexCagetory> l = new ArrayList<IndexCagetory>();
		IndexCagetory indexCagetory = new IndexCagetory();
		indexCagetory.setId(UUID.randomUUID().toString());
		indexCagetory.setIndexInfoType("数据库");
		indexCagetory.setIndexPath("test/java");
		indexCagetory.setIndexPathType("0");
		l.add(indexCagetory);
		return l;
	}*/
}
