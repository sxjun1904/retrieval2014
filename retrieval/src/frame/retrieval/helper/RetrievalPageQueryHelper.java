package frame.retrieval.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Searcher;

import frame.retrieval.engine.RetrievalConstant;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.query.RQuery;
import frame.retrieval.engine.query.group.Grouping;
import frame.retrieval.engine.query.item.QueryItem;
import frame.retrieval.engine.query.item.QuerySort;
import frame.retrieval.engine.query.item.QueryUtil;
import frame.retrieval.engine.query.result.FileQueryResult;
import frame.retrieval.engine.query.result.QueryResult;
import frame.base.core.util.StringClass;
import frame.retrieval.engine.context.ApplicationContext;
import frame.retrieval.engine.context.RFacade;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.IRQueryFacade;

/**
 * WEB查询帮助类,针对索引进行搜索,并按照一定的方式返回结果
 * @author 
 *
 */
public class RetrievalPageQueryHelper {
	private String[] indexPathTypes=null;
	private QueryItem queryItem=null;
	private RFacade helperFacade=null;
	private IRQueryFacade queryFacade=null;
	private RQuery query=null;
	private QuerySort querySort=null;
	
	public RetrievalPageQueryHelper(RetrievalApplicationContext retrievalApplicationContext,String[] indexPathTypes,QueryItem queryItem){

		/*
		 //remove by sxjun 2014-1-1
		 if(indexPathTypes==null || indexPathTypes.length==0){
			if(indexPathTypes!=null && indexPathTypes.length>0){
				int length=indexPathTypes.length;
				indexPathTypes=new String[length];
				for(int i=0;i<length;i++){
					indexPathTypes[i]=StringClass.getString(indexPathTypes[i]);
				}
			}
		}*/
		this.indexPathTypes=ApplicationContext.getLocalIndexPathTypes(indexPathTypes);
		this.queryItem=queryItem;
		
		helperFacade=retrievalApplicationContext.getFacade();
		queryFacade=helperFacade.createQueryFacade();
	}
	
	/**
	 * 获取搜索结果记录数
	 */
	public int getResultCount(RetrievalPageQuery retrievalQuery){
		return getResultCount(retrievalQuery,false);
	}
	
	/**
	 * 获取搜索结果记录数
	 * flag :是否需要得到真实的查询数
	 */
	public int getResultCount(RetrievalPageQuery retrievalQuery,boolean flag){
		query=queryFacade.createRQuery(indexPathTypes);
		int count=query.getQueryResultsCount(queryItem,flag);
		query.close();
		return count;
	}
	
	public RetrievalPages getGroupResult(RetrievalPageQuery retrievalQuery,RetrievalPages retrievalPages){
		retrievalPages.setRetrievalPageList(getResults(retrievalQuery));
		try {
			retrievalPages = Grouping.groupBy((Searcher)query.getSearcher(), queryItem.getQuery(), new QuerySort(StringClass.getString(RetrievalType.RDocItemSpecialName._IC),true).getSort(), StringClass.getString(RetrievalType.RDocItemSpecialName._IBT), retrievalPages);
//			retrievalPages = Grouping.groupBy((Searcher)query.getSearcher(), queryItem.getQuery(), new QuerySort(StringClass.getString(RetrievalType.RDatabaseDefaultDocItemType.CREATETIME),true).getSort(), StringClass.getString(RetrievalType.RDocItemSpecialName._IBT), retrievalPages);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retrievalPages;
	}

	/**
	 * 获取当前页结果集
	 */
	public List<RetrievalPage> getResults(RetrievalPageQuery retrievalQuery) {
		String orderFieldName=StringClass.getString(retrievalQuery.getOrderByFieldName());
		Integer orderFieldType = retrievalQuery.getSortFieldType();
		boolean ascFlag=retrievalQuery.isAscFlag();
		int pageSize=retrievalQuery.getPageSize();
		int nowPage=retrievalQuery.getNowStartPage();
		
		QueryResult[] queryResults=null;
		query=queryFacade.createRQuery(indexPathTypes);
		if(!orderFieldName.equals("")){
			
			if(orderFieldName.equalsIgnoreCase(RetrievalConstant.DEFAULT_INDEX_QUERY_SORT_CREATETIME_NAME)){
				querySort=new QuerySort(QueryUtil.createCreateTimeSort(ascFlag));
			}else{
				if(orderFieldType!=null)
					querySort=new QuerySort(orderFieldName,orderFieldType,ascFlag);
				else
					querySort=new QuerySort(orderFieldName,ascFlag);
			}
		}
		
		if(querySort!=null){
			if(pageSize<=0){
				queryResults=query.getQueryResults(queryItem, querySort);
			}else{
				queryResults=query.getQueryResults(queryItem, querySort,nowPage*pageSize,(nowPage+1)*pageSize-1);
			}
		}else{
			if(pageSize<=0){
				queryResults=query.getQueryResults(queryItem);
			}else{
				queryResults=query.getQueryResults(queryItem,nowPage*pageSize,(nowPage+1)*pageSize-1);
			}
		}
		
		query.close();
		
		List<RetrievalPage> retrievalPages=new ArrayList<RetrievalPage>();
		
		if(queryResults!=null){
			int length=queryResults.length;
			for(int i=0;i<length;i++){
				RetrievalPage retrievalPage=queryResult2SRetrievalPage(retrievalQuery,queryResults[i]);
				retrievalPages.add(retrievalPage);
			}
		}
//		new Grouping().groupBy(query.getSearcher(), queryItem.getQuery(), querySort.getSort(), groupField, retrievalPage);
		return retrievalPages;
	}
	
	/**
	 * 将索引结果对象与搜索结果对象进行转换
	 * @param retrievalQuery
	 * @param queryResult
	 * @return
	 */
	private RetrievalPage queryResult2SRetrievalPage(RetrievalPageQuery retrievalQuery,QueryResult queryResult){
		
		String titleFieldName=StringClass.getString(retrievalQuery.getTitleFieldName());
		String resumeFieldName=StringClass.getString(retrievalQuery.getResumeFieldName());
		
		String title="";
		String content="";
		
		String sourceIndexType=queryResult.getIndexSourceType();
		String createTime=queryResult.getIndexCreatTime();
		
		RetrievalPage retrievalPage=new RetrievalPage();
		retrievalPage.setSourceIndexType(sourceIndexType);
		retrievalPage.setCreateTime(createTime);
				
		if(sourceIndexType.equals(StringClass.getString(RetrievalType.RIndexSourceType.D))){
			
			if(!titleFieldName.equals("")){
				title=queryResult.getHighlighterResult(retrievalQuery.getTitleFieldName(), retrievalQuery.getTitleLength());
			}
			
			if(!resumeFieldName.equals("")){
				content=queryResult.getHighlighterResult(retrievalQuery.getResumeFieldName(), retrievalQuery.getResumeLength());
			}
			
			RetrievalDatabasePage retrievalDatabasePage=new RetrievalDatabasePage();
			String tableName=StringClass.getString(queryResult.getResult(RetrievalType.RDatabaseDocItemType._DT));
			retrievalDatabasePage.setTableName(tableName);
			String keyfieldName=StringClass.getString(queryResult.getResult(RetrievalType.RDatabaseDocItemType._DK));
			retrievalDatabasePage.setKeyfieldName(keyfieldName);
			String recordId=StringClass.getString(queryResult.getResult(RetrievalType.RDatabaseDocItemType._DID));
			retrievalDatabasePage.setRecordId(recordId);
			
			retrievalPage.setRetrievalDatabasePage(retrievalDatabasePage);
			
			query=queryFacade.createRQuery(indexPathTypes);
			FileQueryResult[] fileQueryResults=query.getFileQueryResultArray(tableName, recordId);
			
			if(fileQueryResults!=null && fileQueryResults.length>0){
				int length=fileQueryResults.length;
				
				RetrievalFilePage[] retrievalFilePages=new RetrievalFilePage[length];
				for(int i=0;i<length;i++){
					FileQueryResult fileQueryResult=fileQueryResults[i];
					
					RetrievalFilePage retrievalFilePage=new RetrievalFilePage();
					retrievalFilePage.setFileId(fileQueryResult.getFileId());
					retrievalFilePage.setFileName(fileQueryResult.getFileName());
					retrievalFilePage.setFileRelativePath(fileQueryResult.getFileRelativePath());
					retrievalFilePage.setFileContent(fileQueryResult.getFileContent());
					retrievalFilePage.setModfiyTime(fileQueryResult.getFileModify());
					
					retrievalFilePages[i]=retrievalFilePage;
					
				}
				retrievalPage.setRetrievalFilePages(retrievalFilePages);
			}
			
			query.close();
			
		}else if(sourceIndexType.equals(StringClass.getString(RetrievalType.RIndexSourceType.F))){

			RetrievalFilePage[] retrievalFilePages=new RetrievalFilePage[1];
			RetrievalFilePage retrievalFilePage=new RetrievalFilePage();
			retrievalFilePage.setFileId(StringClass.getString(queryResult.getResult(RetrievalType.RFileDocItemType._FID)));
			retrievalFilePage.setFileName(StringClass.getString(queryResult.getResult(RetrievalType.RFileDocItemType._FN)));
			retrievalFilePage.setFileContent(StringClass.getString(queryResult.getHighlighterResult(RetrievalType.RFileDocItemType._FC,retrievalQuery.getResumeLength())));
			retrievalFilePage.setFileRelativePath(StringClass.getString(queryResult.getResult(RetrievalType.RFileDocItemType._FP)));
			retrievalFilePage.setModfiyTime(StringClass.getString(queryResult.getResult(RetrievalType.RFileDocItemType._FM)));
			
			retrievalFilePage.setTableName(StringClass.getString(queryResult.getResult(RetrievalType.RDatabaseDocItemType._DT)));
			retrievalFilePage.setRecordId(StringClass.getString(queryResult.getResult(RetrievalType.RDatabaseDocItemType._DID)));
			
			retrievalFilePages[0]=retrievalFilePage;
			
			retrievalPage.setRetrievalFilePages(retrievalFilePages);
			
			title=StringClass.getString(StringClass.getString(queryResult.getHighlighterResult(RetrievalType.RFileDocItemType._FN, retrievalQuery.getResumeLength())));
			content=StringClass.getString(retrievalFilePage.getFileContent());
			
		}else{
			
			if(!titleFieldName.equals("")){
				title=queryResult.getHighlighterResult(retrievalQuery.getTitleFieldName(), retrievalQuery.getTitleLength());
			}
			
			if(!resumeFieldName.equals("")){
				content=queryResult.getHighlighterResult(retrievalQuery.getResumeFieldName(), retrievalQuery.getResumeLength());
			}
			
		}
		
		/******add by sxjun******/
		String[] queryFields = retrievalQuery.getQueryFields();
		if(null!=queryFields){
			int queryFieldLength = retrievalQuery.getQueryFields().length;
			for(String singlefield : queryFields){
				retrievalPage.getRetrievalResultFields().put(singlefield, queryResult.getResult(StringClass.getString(singlefield)));
			}
		}
		/******add by sxjun******/

		retrievalPage.setTitle(title);
		retrievalPage.setContent(content);
		
		return retrievalPage;
	}

}
