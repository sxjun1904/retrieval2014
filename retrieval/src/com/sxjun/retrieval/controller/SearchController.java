package com.sxjun.retrieval.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.BooleanClause;

import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.common.Page;
import com.sxjun.retrieval.constant.DefaultConstant.IndexPathType;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.IndexCategory;
import com.sxjun.retrieval.pojo.SimpleItem;
import com.sxjun.retrieval.pojo.SimpleItem.QueryType;
import com.sxjun.retrieval.pojo.SimpleQuery;

import frame.base.core.util.StringClass;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseDefaultDocItemType;
import frame.retrieval.engine.RetrievalType.RDocItemSpecialName;
import frame.retrieval.engine.context.ApplicationContext;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.IRQueryFacade;
import frame.retrieval.engine.query.RQuery;
import frame.retrieval.engine.query.item.QueryItem;
import frame.retrieval.engine.query.result.QueryResult;
import frame.retrieval.helper.RetrievalPage;
import frame.retrieval.helper.RetrievalPageQuery;
import frame.retrieval.helper.RetrievalPageQueryHelper;
import frame.retrieval.helper.RetrievalPages;
import frame.retrieval.oth.mapper.MapperUtil;


public class SearchController extends Controller {
private RetrievalApplicationContext retrievalApplicationContext = ApplicationContext.getApplicationContent();
	public boolean isImg(String indexPathType){
		return DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.IMAGE.getValue()).equals(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, indexPathType));
		
	}
	/**
	 * 获取索引分类
	 * @return
	 */
	public String[] getIndexCategory(){
		CommonService<IndexCategory> commonService = new CommonService<IndexCategory>();
		List<IndexCategory> l = commonService.getObjs(IndexCategory.class.getSimpleName());
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
	
	/**
	 * 获取图片分类
	 * @return
	 */
	public String[] getImgCategory(){
		CommonService<IndexCategory> commonService = new CommonService<IndexCategory>();
		List<IndexCategory> l = commonService.getObjs(IndexCategory.class.getSimpleName());
		int image = 0;
		for(IndexCategory ic : l){
			if(isImg(ic.getIndexPathType()))
				image++;
		}
		String[] ils = new String[image];
		int count=0;
		for(int i=0;i<l.size();i++){
			IndexCategory icc = l.get(i);
			if(isImg(icc.getIndexPathType())){
				ils[count++] = icc.getIndexPath();
			}
		}
		return ils;
	}
	
	public QueryItem createQueryItem(RetrievalType.RDocItemType docItemType,Object name,String value,Float score){
		QueryItem queryItem=retrievalApplicationContext.getFacade().createQueryItem(docItemType, String.valueOf(name), value, score);
		return queryItem;
	}
	
	/**
	 * 普通搜索
	 * @param retrievalPageQuery
	 * @param queryItem
	 * @return
	 */
	public List<RetrievalPage> getRetrievalPage(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem){
		RetrievalPageQueryHelper retrievalPageQueryHelper=new RetrievalPageQueryHelper(retrievalApplicationContext,getIndexCategory(),queryItem);
		return retrievalPageQueryHelper.getResults(retrievalPageQuery);
	}
	
	/**
	 * 图片搜索
	 * @param retrievalPageQuery
	 * @param queryItem
	 * @return
	 */
	public List<RetrievalPage> getRetrievalImgPage(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem){
		RetrievalPageQueryHelper retrievalPageQueryHelper=new RetrievalPageQueryHelper(retrievalApplicationContext,getImgCategory(),queryItem);
		return retrievalPageQueryHelper.getResults(retrievalPageQuery);
	}
	
	public RetrievalPages getRetrievalGroupPage(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem,RetrievalPages retrievalPages){
		RetrievalPageQueryHelper retrievalPageQueryHelper=new RetrievalPageQueryHelper(retrievalApplicationContext,getIndexCategory(),queryItem);
		return retrievalPageQueryHelper.getGroupResult(retrievalPageQuery,retrievalPages);
	}
	
	public int getRetrievalCount(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem){
		RetrievalPageQueryHelper retrievalPageQueryHelper=new RetrievalPageQueryHelper(retrievalApplicationContext,getIndexCategory(),queryItem);
		return retrievalPageQueryHelper.getResultCount(retrievalPageQuery,true);
	}
	
	/**
	 * 获取图片搜搜相关的页面需要的搜索信息
	 * @return
	 */
	public List<String> getImgCommonQueryFields(){
		List<String> _queryFields = new ArrayList<String>();
		_queryFields.add("PAGE_URL");
		_queryFields.add("CREATETIME");
		_queryFields.add(StringClass.getString(RDocItemSpecialName._IBT));
		_queryFields.add(StringClass.getString(RDatabaseDefaultDocItemType._PATH));
		return _queryFields;
	}
	
	/**
	 * 获取蒲绒搜索的页面需要的搜索信息
	 * @return
	 */
	public List<String> getCommonQueryFields(){
		List<String> _queryFields = new ArrayList<String>();
		_queryFields.add("PAGE_URL");
		_queryFields.add("CREATETIME");
		_queryFields.add(StringClass.getString(RDocItemSpecialName._IBT));
		return _queryFields;
	}

	public void index() {
		render("index.jsp");
	}
	
	public void image(){
		SimpleQuery simpleQuery = getModel(SimpleQuery.class);
		if(StringKit.isBlank(simpleQuery.getKeyword())){
			simpleQuery = getSimpleQuery();
			if(StringKit.isBlank(simpleQuery.getKeyword())){
				index();
				return;
			}
		}
		List<String> queryFields = simpleQuery.getQueryFields();
		List<SimpleItem> simpleItems = simpleQuery.getSimpleItems();
		//需要附带查询出的字段
		
		if(queryFields==null||queryFields.size()==0){
			simpleQuery.setQueryFields(getImgCommonQueryFields());
		}
		//默认标题和摘要字段
		if(simpleItems==null||simpleItems.size()==0){
			SimpleItem titleItem = new SimpleItem(RDatabaseDefaultDocItemType._TITLE.toString());
			simpleItems.add(titleItem);
			SimpleItem resumeItem = new SimpleItem(RDatabaseDefaultDocItemType._RESUME.toString());
			simpleItems.add(resumeItem);
		}
		Page<RetrievalPage> page = new Page<RetrievalPage>(getRequest(), getResponse());
		long startTime = System.currentTimeMillis();
		page = searchImg(simpleQuery,page);
		long endTime = System.currentTimeMillis();
		String time = String.format("%.3f",(double)(endTime-startTime)/1000);
		setAttr("page", page);
		setAttr("time",time);
		setAttr("simpleQuery", simpleQuery);
		render("image.jsp");
	}
	
	public void page(){
		searchFor(Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	public void search(){
		searchFor(Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	
	public String getDecod(String para){
		return URLDecoder.decode(para);
	}
	
	/**
	 * 获取rest风格的搜索，解析uri。没有处理项：1.时间区间。2.like搜索.
	 * 中国;^南京-0-新闻
	 * @return
	 */
	public SimpleQuery getSimpleQuery(){
		
		SimpleQuery sq = new SimpleQuery();
		//获取关键字
		String p0 = getPara(0);
		if(p0!=null){
			p0=getDecod(p0);
			String[] kwds = p0.split(";");
			for(String k : kwds){
				int eq = k.indexOf("=");
				int lt = k.indexOf("<");
				int gt = k.indexOf(">");
				
				int no = k.indexOf("^");
				int like = k.indexOf("~");
				
				if(eq<0&&lt<0&&gt<0){
					if(no>-1){
						SimpleItem titleItem = new SimpleItem(RDatabaseDefaultDocItemType._TITLE.toString(),k.substring(1));
						titleItem.setRelationType(QueryType.NOT.getValue());
						SimpleItem resumeItem = new SimpleItem(RDatabaseDefaultDocItemType._RESUME.toString(),k.substring(1));
						resumeItem.setRelationType(QueryType.NOT.getValue());
						sq.getSimpleItems().add(titleItem);
						sq.getSimpleItems().add(resumeItem);
					}else{
						sq.setKeyword(p0.replace(";", ""));
						SimpleItem titleItem = new SimpleItem(RDatabaseDefaultDocItemType._TITLE.toString(),k);
						sq.getSimpleItems().add(titleItem);
						SimpleItem resumeItem = new SimpleItem(RDatabaseDefaultDocItemType._RESUME.toString(),k);
						sq.getSimpleItems().add(resumeItem);
					}
				}else if(eq>0){
					String[] ss = k.split("=");
					SimpleItem si = new SimpleItem();
					si.setField(StringClass.getString(ss[0]));
					if(no>-1){
						si.setRelationType(QueryType.NOT.getValue());
						si.setKeyword(ss[1].substring(1));
					}else{
						si.setRelationType(QueryType.AND.getValue());
						si.setKeyword(ss[1]);
					}
					sq.getSimpleItems().add(si);
				}else if(lt>0){
				}else if(lt>0){
				}
			}
		}
			
		//获取全文检索或标题检索
		Integer p1 = getParaToInt(1);
		if(p1!=null&&p1==1)
			sq.setResumeField("");
		
		//获取分类
		String p2 = getPara(2);
		if(p2!=null){
			p2=getDecod(p2);
			String[] cates = p2.split(";");
			for(String c : cates){
				int no = c.indexOf("^");
				SimpleItem si = new SimpleItem();
				si.setField(StringClass.getString(RetrievalType.RDocItemSpecialName._IC));
				if(no>-1){
					si.setKeyword(c.substring(1).trim());
					si.setRelationType(QueryType.NOT.getValue());
				}else{
					si.setKeyword(c.trim());
					si.setRelationType(QueryType.AND.getValue());
				}
				sq.getSimpleItems().add(si);
			}
		}
		
		return sq;
	}
	
	/**
	 * 获取xml/json搜索信息
	 * @return
	 */
	public RetrievalPages getCommonPages(){
		SimpleQuery simpleQuery = getSimpleQuery();
		Integer pageNum = getParaToInt("pageNum");
		Integer pageSize = getParaToInt("pageSize");
		if(pageNum!=null)
			simpleQuery.setNowStartPage(pageNum);
		if(pageSize!=null)
			simpleQuery.setPageSize(pageSize);
		List<String> queryFields = simpleQuery.getQueryFields();
		if(queryFields==null||queryFields.size()==0){
			simpleQuery.setQueryFields(getCommonQueryFields());
		}
		RetrievalPages pages = null;
		long startTime = System.currentTimeMillis();
		pages = search(simpleQuery);
		long endTime = System.currentTimeMillis();
		String time = String.format("%.3f",(double)(endTime-startTime)/1000);
		pages.setTime(time);
		return pages;
	}
	
	/**
	 * 获取xml/json搜索信息
	 * @return
	 */
	public RetrievalPages getCommonImgPages(){
		SimpleQuery simpleQuery = getSimpleQuery();
		Integer pageNum = getParaToInt("pageNum");
		Integer pageSize = getParaToInt("pageSize");
		if(pageNum!=null)
			simpleQuery.setNowStartPage(pageNum);
		if(pageSize!=null)
			simpleQuery.setPageSize(pageSize);
		List<String> queryFields = simpleQuery.getQueryFields();
		if(queryFields==null||queryFields.size()==0){
			simpleQuery.setQueryFields(getImgCommonQueryFields());
		}
		RetrievalPages pages = null;
		long startTime = System.currentTimeMillis();
		pages = searchImg(simpleQuery);
		long endTime = System.currentTimeMillis();
		String time = String.format("%.3f",(double)(endTime-startTime)/1000);
		pages.setTime(time);
		return pages;
	}
	
	/**
	 *返回xml格式的搜索结果 
	 */
	public void xml(){
		RetrievalPages pages = getCommonPages();
		try {
			Map<String,Class> map = new HashMap<String,Class>();
			map.put("RetrievalPages", RetrievalPages.class);
			map.put("RetrievalPage", RetrievalPage.class);
			String xml = new MapperUtil(map).toXml(pages);
			System.out.println(xml);
			renderText(xml, "text/xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回json格式的搜索结果
	 */
	public void json(){
		RetrievalPages pages = getCommonPages();
		try {
			String json = new MapperUtil().toJson(pages);
			renderJson(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回json格式的图片搜索结果
	 */
	public void jsonImg(){
		RetrievalPages pages = getCommonImgPages();
		try {
			String json = new MapperUtil().toJson(pages);
			renderJson(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void searchFor(String methodName) {
		SimpleQuery simpleQuery = getModel(SimpleQuery.class);
		if(StringKit.isBlank(simpleQuery.getKeyword())){
			simpleQuery = getSimpleQuery();
			if(StringKit.isBlank(simpleQuery.getKeyword())){
				index();
				return;
			}
		}
		List<String> queryFields = simpleQuery.getQueryFields();
		List<SimpleItem> simpleItems = simpleQuery.getSimpleItems();
		//需要附带查询出的字段
		if(queryFields==null||queryFields.size()==0){
			simpleQuery.setQueryFields(getCommonQueryFields());
		}
		//默认标题和摘要字段
		if(simpleItems==null||simpleItems.size()==0){
			SimpleItem titleItem = new SimpleItem(RDatabaseDefaultDocItemType._TITLE.toString());
			simpleItems.add(titleItem);
			SimpleItem resumeItem = new SimpleItem(RDatabaseDefaultDocItemType._RESUME.toString());
			simpleItems.add(resumeItem);
		}
		Page<RetrievalPage> page = new Page<RetrievalPage>(getRequest(), getResponse());
		long startTime = System.currentTimeMillis();
		page = search(simpleQuery,page);
		long endTime = System.currentTimeMillis();
		String time = String.format("%.3f",(double)(endTime-startTime)/1000);
		setAttr("page", page);
		setAttr("time",time);
		setAttr("simpleQuery", simpleQuery);
		render(methodName+".jsp");
}
	/**
	 * 图片搜索
	 * @param simpleQuery
	 * @param page
	 * @return
	 */
	public Page<RetrievalPage> searchImg(SimpleQuery simpleQuery, Page<RetrievalPage> page){
		simpleQuery.setPageSize(page.getPageSize());
		simpleQuery.setNowStartPage(page.getPageNo());
		RetrievalPages retrievalPages = searchImg(simpleQuery);
		page.setCount(retrievalPages.getCount());
		page.setList(retrievalPages.getRetrievalPageList());
		page.setGroup(retrievalPages.getGroup());
		return page;
	}
	
	/**
	 * 普通搜索
	 * @param simpleQuery
	 * @param page
	 * @return
	 */
	public Page<RetrievalPage> search(SimpleQuery simpleQuery, Page<RetrievalPage> page){
		simpleQuery.setPageSize(page.getPageSize());
		simpleQuery.setNowStartPage(page.getPageNo());
		RetrievalPages retrievalPages = search(simpleQuery);
		page.setCount(retrievalPages.getCount());
		page.setList(retrievalPages.getRetrievalPageList());
		page.setGroup(retrievalPages.getGroup());
		return page;
	}
	
	/**
	 * 搜索图片
	 * @param simpleQuery
	 * @return
	 */
	public RetrievalPages searchImg(SimpleQuery simpleQuery){
		RetrievalPageQuery retrievalPageQuery = generateRetrievalPageQuery(simpleQuery);
		QueryItem queryItem = composeQuerys(simpleQuery);
		RetrievalPages retrievalPages = new RetrievalPages();
		if(queryItem!=null){
			List<RetrievalPage> retrievalPageList = searchImgBody(retrievalPageQuery, queryItem);
			int count = getRetrievalCount(retrievalPageQuery, queryItem);
			retrievalPages.setRetrievalPageList(retrievalPageList);
			retrievalPages.setCount(count);
		}
		return retrievalPages;
	}
	
	/**
	 * 搜索
	 * @param simpleQuery
	 * @return
	 */
	public RetrievalPages search(SimpleQuery simpleQuery){
		RetrievalPageQuery retrievalPageQuery = generateRetrievalPageQuery(simpleQuery);
		QueryItem queryItem = composeQuerys(simpleQuery);
		RetrievalPages retrievalPages = new RetrievalPages();
		if(queryItem!=null){
			/*List<RetrievalPage> retrievalPageList = searchBody(retrievalPageQuery, queryItem);
			int count = getRetrievalCount(retrievalPageQuery, queryItem);
			retrievalPages.setRetrievalPageList(retrievalPageList);
			retrievalPages.setCount(count);*/
			retrievalPages = searchGroupBody(retrievalPageQuery,queryItem,retrievalPages);
			int count = getRetrievalCount(retrievalPageQuery, queryItem);
			retrievalPages.setCount(count);
			
		}
		return retrievalPages;
	}
	
	/**
	 * 搜索图片主体
	 * @param retrievalPageQuery
	 * @param queryItem
	 * @return
	 */
	public List<RetrievalPage> searchImgBody(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem){
		List<RetrievalPage> retrievalPageList = getRetrievalImgPage(retrievalPageQuery, queryItem);
		return retrievalPageList;
	}
	
	/**
	 * 搜索主体
	 * @param retrievalPageQuery
	 * @param queryItem
	 * @return
	 */
	public List<RetrievalPage> searchBody(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem){
		List<RetrievalPage> retrievalPageList = getRetrievalPage(retrievalPageQuery, queryItem);
		return retrievalPageList;
	}
	
	public RetrievalPages searchGroupBody(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem,RetrievalPages retrievalPages){
		return getRetrievalGroupPage(retrievalPageQuery, queryItem, retrievalPages);
	}
	
	/**
	 * 搜索总数目
	 * @param simpleQuery
	 * @return
	 */
	public int searchCount(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem){
		int count = getRetrievalCount(retrievalPageQuery, queryItem);
		return count;
	}
	
	/**
	 * 得到分页查询语句
	 * @param simpleQuery
	 * @return
	 */
	public RetrievalPageQuery generateRetrievalPageQuery(SimpleQuery simpleQuery){
		RetrievalPageQuery retrievalPageQuery = new RetrievalPageQuery();
		if(!StringUtils.isBlank(simpleQuery.getTitleField())){
			retrievalPageQuery.setTitleFieldName(simpleQuery.getTitleField());
			retrievalPageQuery.setTitleLength(simpleQuery.getTitleLength());
		}
		if(!StringUtils.isBlank(simpleQuery.getResumeField())){
			retrievalPageQuery.setResumeFieldName(simpleQuery.getResumeField());
			retrievalPageQuery.setResumeLength(simpleQuery.getResumeLength());
		}
		retrievalPageQuery.setPageSize(simpleQuery.getPageSize());
		retrievalPageQuery.setNowStartPage(simpleQuery.getNowStartPage()-1);
		if(simpleQuery.getQueryFields()!=null){
			int size = simpleQuery.getQueryFields().size();
			String[] queryFields = (String[]) simpleQuery.getQueryFields().toArray(new String[size]);
			retrievalPageQuery.setQueryFields(queryFields);
		}
		return retrievalPageQuery;
	}
	
	/**
	 * 查询语句
	 * @param simpleQuery
	 * @return
	 */
	public QueryItem composeQuerys(SimpleQuery simpleQuery){
		List<SimpleItem> simpleItems = simpleQuery.getSimpleItems();
		QueryItem queryitem = null;
		BooleanClause.Occur upRelationType = null;
		if(simpleItems!=null){
			int itemCount = simpleItems.size();
			if(itemCount>0){
				for(SimpleItem item : simpleItems){
					String kw = null;
					if(!StringUtils.isBlank(item.getKeyword()))
						kw = item.getKeyword();
					else
						kw = simpleQuery.getKeyword();
					if(!StringUtils.isBlank(kw)){
						QueryItem q = createQueryItem(item.getFieldType(),item.getField(),kw,null);
						if(queryitem!=null){
							if(QueryType.OR.getValue().equals(item.getRelationType()))
								queryitem.should(upRelationType,q);
							else if(QueryType.AND.getValue().equals(item.getRelationType()))
								queryitem.must(upRelationType,q);
							else if(QueryType.NOT.getValue().equals(item.getRelationType()))
								queryitem.mustNot(upRelationType,q);
							else
								queryitem.should(upRelationType,q);
						}else{
							queryitem = q;
						}
//						upRelationType = item.getClauseRelationType();
						upRelationType = QueryItem.SHOULD;
					}
				}
			}
		}
		return queryitem;
	}
}
