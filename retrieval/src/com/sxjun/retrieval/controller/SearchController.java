package com.sxjun.retrieval.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.BooleanClause;

import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.sxjun.retrieval.common.Page;
import com.sxjun.retrieval.pojo.SimpleItem;
import com.sxjun.retrieval.pojo.SimpleItem.QueryType;
import com.sxjun.retrieval.pojo.SimpleQuery;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.RetrievalType.RDatabaseDefaultDocItemType;
import framework.retrieval.engine.context.ApplicationContext;
import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.query.item.QueryItem;
import framework.retrieval.helper.RetrievalPage;
import framework.retrieval.helper.RetrievalPageQuery;
import framework.retrieval.helper.RetrievalPageQueryHelper;
import framework.retrieval.helper.RetrievalPages;
import framework.retrieval.oth.mapper.MapperUtil;


public class SearchController extends Controller {
private RetrievalApplicationContext retrievalApplicationContext = ApplicationContext.getApplicationContent();
	
	public QueryItem createQueryItem(RetrievalType.RDocItemType docItemType,Object name,String value,Float score){
		QueryItem queryItem=retrievalApplicationContext.getFacade().createQueryItem(docItemType, String.valueOf(name), value, score);
		return queryItem;
	}
	
	public List<RetrievalPage> getRetrievalPage(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem){
		RetrievalPageQueryHelper retrievalPageQueryHelper=new RetrievalPageQueryHelper(retrievalApplicationContext,new String[]{"DB/B/TEST_WEB","DB/B/VIEW_THREAD_CASE"},queryItem);
		return retrievalPageQueryHelper.getResults(retrievalPageQuery);
	}
	
	public RetrievalPages getRetrievalGroupPage(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem,RetrievalPages retrievalPages){
		RetrievalPageQueryHelper retrievalPageQueryHelper=new RetrievalPageQueryHelper(retrievalApplicationContext,new String[]{"DB/B/TEST_WEB","DB/B/VIEW_THREAD_CASE"},queryItem);
		return retrievalPageQueryHelper.getGroupResult(retrievalPageQuery,retrievalPages);
	}
	
	public int getRetrievalCount(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem){
		RetrievalPageQueryHelper retrievalPageQueryHelper=new RetrievalPageQueryHelper(retrievalApplicationContext,new String[]{"DB/B/TEST_WEB","DB/B/VIEW_THREAD_CASE"},queryItem);
		return retrievalPageQueryHelper.getResultCount(retrievalPageQuery,true);
	}

	public void index() {
		render("index.jsp");
	}
	
	
	public void search1(){
		searchFor(Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	public void search(){
		searchFor(Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	public void searchFor(String methodName) {
		SimpleQuery simpleQuery = getModel(SimpleQuery.class);
		if(StringKit.isBlank(simpleQuery.getKeyword())){
			index();
			return;
		}
		List<String> queryFields = simpleQuery.getQueryFields();
		List<SimpleItem> simpleItems = simpleQuery.getSimpleItems();
		//需要附带查询出的字段
		if(queryFields==null||queryFields.size()==0){
			List<String> _queryFields = new ArrayList<String>();
			_queryFields.add("PAGE_URL");
			_queryFields.add("CREATETIME");
			simpleQuery.setQueryFields(_queryFields);
		}
		//默认标题和摘要字段
		if(simpleItems==null||simpleItems.size()==0){
			SimpleItem titleItem = new SimpleItem(RDatabaseDefaultDocItemType._TITLE.toString());
			simpleItems.add(titleItem);
			SimpleItem resumeItem = new SimpleItem(RDatabaseDefaultDocItemType._RESUME.toString());
			simpleItems.add(resumeItem);
		}
		String type = getPara();
		if(type==null){
			Page<RetrievalPage> page = new Page<RetrievalPage>(getRequest(), getResponse());
			long startTime = System.currentTimeMillis();
			page = search(simpleQuery,page);
			long endTime = System.currentTimeMillis();
			String time = String.format("%.3f",(double)(endTime-startTime)/1000);
			setAttr("page", page);
			setAttr("time",time);
			setAttr("simpleQuery", simpleQuery);
			render(methodName+".jsp");
		}else{
			RetrievalPages pages = null;
			long startTime = System.currentTimeMillis();
			pages = search(simpleQuery);
			long endTime = System.currentTimeMillis();
			String time = String.format("%.3f",(double)(endTime-startTime)/1000);
			pages.setTime(time);
			
			if("json".equals(type)){
				try {
					String json = new MapperUtil().toJson(pages);
					getResponse().setContentType("application/json");
					PrintWriter out = getResponse().getWriter();
					out.print(json);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if("xml".equals(type)){
				try {
					Map<String,Class> map = new HashMap<String,Class>();
					map.put("RetrievalPages", RetrievalPages.class);
					map.put("RetrievalPage", RetrievalPage.class);
					String xml = new MapperUtil(map).toXml(pages);
					getResponse().setContentType("text/xml");
					PrintWriter out = getResponse().getWriter();
					out.print(xml);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
			}
		}
		
	}
	
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
							if(QueryType.OR.equals(item.getRelationType()))
								queryitem.should(upRelationType,q);
							if(QueryType.AND.equals(item.getRelationType()))
								queryitem.must(upRelationType,q);
							if(QueryType.NOT.equals(item.getRelationType()))
								queryitem.mustNot(upRelationType,q);
							else
								queryitem.should(upRelationType,q);
						}else{
							queryitem = q;
						}
						upRelationType = item.getClauseRelationType();
					}
				}
			}
		}
		return queryitem;
	}
}
