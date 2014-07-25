/**
 * code generation
 */
package com.sxjun.retrieval.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.StrKit;
import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.common.Page;
import com.sxjun.retrieval.constant.DefaultConstant.IndexPathType;
import com.sxjun.retrieval.controller.proxy.ServiceProxy;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.IndexCategory;
import com.sxjun.retrieval.pojo.IndexManager;
import com.sxjun.retrieval.pojo.SimpleItem;

import frame.base.core.util.StringClass;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.context.ApplicationContext;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.IRDocOperatorFacade;
import frame.retrieval.engine.query.item.QueryItem;
import frame.retrieval.helper.RetrievalPage;
import frame.retrieval.helper.RetrievalPageQuery;
import frame.retrieval.helper.RetrievalPageQueryHelper;
import frame.retrieval.helper.RetrievalPages;

/**
 * 索引管理Controller
 * @author sxjun
 * @version 2014-07-23
 */
public class IndexManagerController extends BaseController<IndexManager> {
	private RetrievalApplicationContext retrievalApplicationContext = ApplicationContext.getApplicationContent();
	private CommonService<IndexCategory> indexCategoryService = new ServiceProxy<IndexCategory>().getproxy();
	public boolean isImg(String indexPathType){
		return DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.IMAGE.getValue()).equals(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, indexPathType));
		
	}
	public QueryItem createQueryItem(RetrievalType.RDocItemType docItemType,Object name,String value,Float score){
		QueryItem queryItem=retrievalApplicationContext.getFacade().createQueryItem(docItemType, String.valueOf(name), value, score);
		return queryItem;
	}
	
	/**
	 * 获取索引分类
	 * @return
	 */
	public String[] getIndexCategory(){
		List<IndexCategory> l = indexCategoryService.getObjs(IndexCategory.class);
		int image = 0;
		for(IndexCategory ic : l){
			if(isImg(ic.getIndexPathType()))
				image++;
		}
		String[] ils = new String[l.size()-image];
		int count=0;
		for(int i=0;i<l.size();i++){
			IndexCategory icc = l.get(i);
			if(!isImg(icc.getIndexPathType())){
				ils[count++] = icc.getIndexPath();
			}
		}
		return ils;
	}
	
	public RetrievalPages getRetrievalGroupPage(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem,RetrievalPages retrievalPages){
		RetrievalPageQueryHelper retrievalPageQueryHelper=new RetrievalPageQueryHelper(retrievalApplicationContext,getIndexCategory(),queryItem);
		return retrievalPageQueryHelper.getGroupResult(retrievalPageQuery,retrievalPages);
	}
	
	public RetrievalPages searchGroupBody(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem,RetrievalPages retrievalPages){
		return getRetrievalGroupPage(retrievalPageQuery, queryItem, retrievalPages);
	}
	
	public int getRetrievalCount(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem){
		RetrievalPageQueryHelper retrievalPageQueryHelper=new RetrievalPageQueryHelper(retrievalApplicationContext,getIndexCategory(),queryItem);
		return retrievalPageQueryHelper.getResultCount(retrievalPageQuery,true);
	}
	
	public void list() {
		SimpleItem simpleItem = getModel(SimpleItem.class);
		listfor(simpleItem);
	}
	
	public void listfor(SimpleItem simpleItem){
		Map<String,String> itemTypes = DictUtils.getDictMap(DictUtils.ITEMTYPE_TYPE);
		Page<RetrievalPage> page = new Page<RetrievalPage>(getRequest(), getResponse());
		if(simpleItem!=null&&StrKit.notBlank(simpleItem.getKeyword())){
			RetrievalPageQuery retrievalPageQuery = new RetrievalPageQuery();
			retrievalPageQuery.setPageSize(page.getPageSize());
			retrievalPageQuery.setNowStartPage(page.getPageNo()-1);
			retrievalPageQuery.setQueryFields(new String[]{StringClass.getString(RetrievalType.RDocItemSpecialName._IID)});
			
			QueryItem queryItem = createQueryItem(simpleItem.getFieldType(),simpleItem.getField(),simpleItem.getKeyword(),null);
			RetrievalPages retrievalPages = new RetrievalPages();
			if(queryItem!=null){
				retrievalPages = searchGroupBody(retrievalPageQuery,queryItem,retrievalPages);
				int count = getRetrievalCount(retrievalPageQuery, queryItem);
				retrievalPages.setCount(count);
				
			}
			page.setCount(retrievalPages.getCount());
			page.setList(retrievalPages.getRetrievalPageList());
			page.setGroup(retrievalPages.getGroup());
		}
		setAttr("itemTypes",itemTypes);
		setAttr("simpleItem",simpleItem);
		setAttr("page", page);
		render("indexManagerList.jsp");
	}
	
	public void delete(){
		String documntId = getPara();
		IRDocOperatorFacade docOperatorFacade=retrievalApplicationContext.getFacade().createDocOperatorFacade();
		String[] indexPathTypes=ApplicationContext.getLocalIndexPathTypes(getIndexCategory());
		for(String indexPathType :indexPathTypes)
			docOperatorFacade.delete(indexPathType, documntId);
		SimpleItem simpleItem = getModel(SimpleItem.class);
		listfor(simpleItem);
	}
	@Override
	public void save() {
	}
}
