package com.sxjun.retrieval.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
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
import com.sxjun.retrieval.controller.oth.pinyin.PinyinHanziUtil;
import com.sxjun.retrieval.controller.proxy.ServiceProxy;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.IndexCategory;
import com.sxjun.retrieval.pojo.InitField;
import com.sxjun.retrieval.pojo.SimpleItem;
import com.sxjun.retrieval.pojo.SimpleItem.QueryType;
import com.sxjun.retrieval.pojo.SimpleQuery;

import frame.base.core.util.DateTime;
import frame.base.core.util.StringClass;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseDefaultDocItemType;
import frame.retrieval.engine.RetrievalType.RDocItemSpecialName;
import frame.retrieval.engine.context.ApplicationContext;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.query.item.QueryItem;
import frame.retrieval.engine.query.item.QuerySort;
import frame.retrieval.helper.RetrievalPage;
import frame.retrieval.helper.RetrievalPageQuery;
import frame.retrieval.helper.RetrievalPageQueryHelper;
import frame.retrieval.helper.RetrievalPages;
import frame.retrieval.oth.ik.IKWordsUtil;
import frame.retrieval.oth.mapper.MapperUtil;


public class SearchController extends Controller {
private RetrievalApplicationContext retrievalApplicationContext = ApplicationContext.getApplicationContent();
private CommonService<IndexCategory> indexCategoryService = new ServiceProxy<IndexCategory>().getproxy();
	public boolean isImg(String indexPathType){
		return DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.IMAGE.getValue()).equals(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, indexPathType));
		
	}
	
	public void pinYinHanzi(){
		String pyhz = getDecod(getPara("pyhz")).toLowerCase();
		List<String> pyhzList =PinyinHanziUtil.getRangeWords(pyhz);
//		String json = "{\"data\":["; 
		String json = "["; 
		for(String s :pyhzList){
			json += "{\"title\":\""+s+"\"},";
		}
		if(pyhzList!=null&&pyhzList.size()>0)
		json = json.substring(0,json.length()-1);
//		json += "]}";
		json += "]";
		System.out.println("关键字："+pyhz+";json:"+json);
		renderJson(json);
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
	
	/**
	 * 获取图片分类
	 * @return
	 */
	public String[] getImgCategory(){
		List<IndexCategory> l = indexCategoryService.getObjs(IndexCategory.class);
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
	
	/**
	 * 获取普通索引数
	 * @param retrievalPageQuery
	 * @param queryItem
	 * @return
	 */
	public int getRetrievalCount(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem){
		RetrievalPageQueryHelper retrievalPageQueryHelper=new RetrievalPageQueryHelper(retrievalApplicationContext,getIndexCategory(),queryItem);
		return retrievalPageQueryHelper.getResultCount(retrievalPageQuery,true);
	}
	
	/**
	 * 获取图片数
	 * @param retrievalPageQuery
	 * @param queryItem
	 * @return
	 */
	public int getRetrievalImgCount(RetrievalPageQuery retrievalPageQuery,QueryItem queryItem){
		RetrievalPageQueryHelper retrievalPageQueryHelper=new RetrievalPageQueryHelper(retrievalApplicationContext,getImgCategory(),queryItem);
		return retrievalPageQueryHelper.getResultCount(retrievalPageQuery,true);
	}
	
	/**
	 * 获取图片搜搜相关的页面需要的搜索信息
	 * @return
	 */
	public List<String> getImgCommonQueryFields(){
		List<String> _queryFields = new ArrayList<String>();
		_queryFields.add(StringClass.getString(RDatabaseDefaultDocItemType.PAGE_URL));
		_queryFields.add(StringClass.getString(RDatabaseDefaultDocItemType.CREATETIME));
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
		_queryFields.add(StringClass.getString(RDatabaseDefaultDocItemType.PAGE_URL));
		_queryFields.add(StringClass.getString("IMAGEURL"));//phone
		_queryFields.add(StringClass.getString("ORIGINALURL"));//phone
		_queryFields.add(StringClass.getString(RDatabaseDefaultDocItemType.CREATETIME));
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
		boolean isTitleSearch = false;
		
		//获取全文检索或标题检索
		Integer p1 = getParaToInt(1);
		if(p1!=null&&p1==1)
			isTitleSearch = true;	
		
		//判断是否按时间排序
		Integer p2 = getParaToInt(2);
		if(p2!=null&&(p2==1||p2==2)){
			sq.setSortField(StringClass.getString(RDatabaseDefaultDocItemType.CREATETIME));
			sq.setSortFieldType(QuerySort.SortField_LONG);
			if(p2==1){
				sq.setAscFlag(true);
			}else if(p2==2){
				sq.setAscFlag(false);
			}
		}
		
		//获取关键字
		String p0 = getPara(0);
		if(p0!=null){
			p0=getDecod(p0);
			String[] kwds = p0.split("&");
			for(String k : kwds){
				int eq = k.indexOf("=");
				int lt = k.indexOf("<");
				int gt = k.indexOf(">");
				
				int no = k.indexOf("^");
				int at = k.indexOf("~");
				
				if(eq<0&&lt<0&&gt<0&&at<0){
					if(no>-1){
						SimpleItem titleItem = new SimpleItem(RDatabaseDefaultDocItemType._TITLE.toString(),k.substring(1));
						titleItem.setRelationType(QueryType.NOT.getValue());
						sq.getSimpleItems().add(titleItem);
						if(!isTitleSearch){
							SimpleItem resumeItem = new SimpleItem(RDatabaseDefaultDocItemType._RESUME.toString(),k.substring(1));
							resumeItem.setRelationType(QueryType.NOT.getValue());
							sq.getSimpleItems().add(resumeItem);
						}
					}else{
						//处理词
						if(IKWordsUtil.isExists(k)&&k.length()>1){
							PinyinHanziUtil.add(k);
						}
							
						if(StringKit.isBlank(sq.getKeyword()))
							sq.setKeyword(k);
						else 
							sq.setKeyword(sq.getKeyword()+" "+k);
						SimpleItem titleItem = new SimpleItem(RDatabaseDefaultDocItemType._TITLE.toString(),k);
						sq.getSimpleItems().add(titleItem);
						if(!isTitleSearch){
							SimpleItem resumeItem = new SimpleItem(RDatabaseDefaultDocItemType._RESUME.toString(),k);
							sq.getSimpleItems().add(resumeItem);
						}
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
				}else if(gt>0){
					String[] ss = k.split(">");
					SimpleItem si = new SimpleItem();
					si.setField(StringClass.getString(ss[0]));
					si.setRelationType(QueryType.GT.getValue());
					si.setKeyword(ss[1]);
					sq.getSimpleItems().add(si);
				}else if(lt>0){
					String[] ss = k.split("<");
					SimpleItem si = new SimpleItem();
					si.setField(StringClass.getString(ss[0]));
					si.setRelationType(QueryType.LT.getValue());
					si.setKeyword(ss[1]);
					sq.getSimpleItems().add(si);
				}else if(at>0){
					String[] ss = k.split("~");
					SimpleItem si = new SimpleItem();
					si.setField(StringClass.getString(ss[0]));
					si.setRelationType(QueryType.AT.getValue());
					si.setKeyword(ss[1]);
					sq.getSimpleItems().add(si);
				}
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
		/*List<RetrievalPage> l = page.getList();
		List<RetrievalPage> l1 = new ArrayList<RetrievalPage>();
		
		for(RetrievalPage r:l){
			r.setContent(r.getContent().replace("型号", "<br/>型号").replace("报价", "<br/>报价").replace("主屏尺寸", "<br/>主屏尺寸").replace("参数", "<br/>参数")
			.replace("屏幕分辨", "<br/>屏幕分辨").replace("系统", "<br/>系统").replace("电池容量", "<br/>电池容量").replace("CPU", "<br/>CPU"));
			l1.add(r);
		}
		page.setList(l1);*/
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
			int count = getRetrievalImgCount(retrievalPageQuery, queryItem);
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
		if(!StringUtils.isBlank(simpleQuery.getSortField())){
			retrievalPageQuery.setOrderByFieldName(simpleQuery.getSortField());
			retrievalPageQuery.setAscFlag(simpleQuery.isAscFlag());
		}
		if(simpleQuery.getSortFieldType()!=null){
			retrievalPageQuery.setSortFieldType(simpleQuery.getSortFieldType());
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
	 * 获取时间
	 * @param kw
	 * @return
	 */
	public String getTimeFormat(String kw){
		int len = kw.length();
		if(len<6){
			int left = 6-len;
			for(int i=0;i<left-1;i++)
				kw +="0";
			kw +="101000000";
		}else if(len<8){
			kw +="1000000";
		}else if(len<14){
			int left = 14-len;
			for(int i=0;i<left;i++)
				kw +="0";
		}
		return kw;
	}
	
	/**
	 * 查询语句
	 * @param simpleQuery
	 * @return
	 */
	public QueryItem composeQuerys(SimpleQuery simpleQuery){
		int len = 14;
		long start = Long.valueOf("19000101000000");
		long end = Long.valueOf(new DateTime().parseString(new Date(),"yyyyMMddHHmmss"));
		String datefeild = StringClass.getString(RDatabaseDefaultDocItemType.CREATETIME);
		boolean isRangeQuery = false;
		
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
						if(item.getField().equals(RDatabaseDefaultDocItemType._TITLE.toString())||item.getField().equals(RDatabaseDefaultDocItemType._TITLE.toString())||item.getField().equals(RDatabaseDefaultDocItemType._RESUME.toString())||item.getField().equals(RDatabaseDefaultDocItemType._RESUME.toString()))
							upRelationType = QueryItem.SHOULD;
						else
							upRelationType = QueryItem.MUST;
						if(queryitem!=null){
							if(QueryType.OR.getValue().equals(item.getRelationType()))
								queryitem.should(upRelationType,q);
							else if(QueryType.AND.getValue().equals(item.getRelationType()))
								queryitem.must(upRelationType,q);
							else if(QueryType.NOT.getValue().equals(item.getRelationType()))
								queryitem.mustNot(upRelationType,q);
							else if(QueryType.GT.getValue().equals(item.getRelationType())||QueryType.LT.getValue().equals(item.getRelationType())){
								if(QueryType.GT.getValue().equals(item.getRelationType())){
									kw = getTimeFormat(kw);
									start = Long.valueOf(kw);
								}else if(QueryType.LT.getValue().equals(item.getRelationType())){
									kw = getTimeFormat(kw);
									end = Long.valueOf(kw);
								}
								isRangeQuery = true;
								datefeild = item.getField();
							}else if(QueryType.AT.getValue().equals(item.getRelationType())){
								q = createQueryItem(item.getFieldType(),item.getField(),"*"+kw+"*",null);
								queryitem.must(upRelationType,q);
							}else
								queryitem.should(upRelationType,q);
						}else{
							queryitem = q;
						}
//						upRelationType = item.getClauseRelationType();
						/*if(item.getField().equals("_TITLE")||item.getField().equals("_RESUME"))
							upRelationType = QueryItem.SHOULD;
						else
							upRelationType = QueryItem.MUST;*/
					}
				}
			}
		}
		if(isRangeQuery){
			queryitem.range(start, end, datefeild);
		}
		return queryitem;
	}
}
