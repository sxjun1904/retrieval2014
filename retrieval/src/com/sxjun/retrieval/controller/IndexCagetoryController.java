/**
 * code generation
 */
package com.sxjun.retrieval.controller;

import java.util.Map;

import com.sxjun.core.common.controller.BaseController;
import com.sxjun.core.common.utils.DictUtils;
import com.sxjun.retrieval.pojo.IndexCategory;

/**
 * 索引分类Controller
 * @author sxjun
 * @version 2014-01-14
 */
public class IndexCagetoryController extends BaseController<IndexCategory> {
	
	public void list() {
		list(IndexCategory.class);
	}
	
	public void form(){
		
		Map<String,String> indexPathTypes = DictUtils.getDictMap(DictUtils.INDEXPATH_TYPE);
		setAttr("indexPathTypes",indexPathTypes);
		form(IndexCategory.class);
	}
	
	public void save(){
		save(getModel(IndexCategory.class));
	}
	
	public void delete(){
		delete(IndexCategory.class);
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
