package frame.retrieval.test.query;

import com.sxjun.retrieval.controller.index.DatabaseIndexAllItem0Impl;

import frame.base.core.util.StringClass;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseDefaultDocItemType;
import frame.retrieval.engine.RetrievalType.RDatabaseDocItemType;
import frame.retrieval.engine.query.RQuery;
import frame.retrieval.engine.query.item.QueryItem;
import frame.retrieval.engine.query.item.QuerySort;
import frame.retrieval.engine.query.item.QueryUtil;
import frame.retrieval.engine.query.result.QueryResult;
import frame.retrieval.test.init.TestInit;
import frame.retrieval.engine.context.ApplicationContext;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.IRQueryFacade;

public class TestNormalQuery {

	//private RetrievalApplicationContext retrievalApplicationContext=TestInit.getApplicationContent();
	private RetrievalApplicationContext retrievalApplicationContext = ApplicationContext.getApplicationContent();
	
	private IRQueryFacade queryFacade=null;

	public TestNormalQuery(){
		TestInit.initIndex();
		queryFacade=retrievalApplicationContext.getFacade().createQueryFacade();
	}
	
	public String getDatabaseIndexId(String indexPathType,String tableName,String recordId){
		String indexId=queryFacade.queryDatabaseDocumentIndexId(indexPathType, tableName, recordId);
		return indexId;
	}
	
	public int getDatabaseIndexIdCount(String indexPathType,String tableName,String recordId){
		RQuery query=queryFacade.createRQuery(indexPathType);
		
		QueryItem queryItem0=createQueryItem(RetrievalType.RDocItemType.KEYWORD,
				RetrievalType.RDatabaseDocItemType._DT,
				tableName);
		
		QueryItem queryItem1=createQueryItem(RetrievalType.RDocItemType.KEYWORD,
				RetrievalType.RDatabaseDocItemType._DID,
				recordId);
		
		queryItem0.must(QueryItem.MUST, queryItem1);
		
		int indexIdCount=query.getQueryResultsCount(queryItem0);
		
		query.close();
		
		return indexIdCount;
	}
	
	public QueryResult getQueryResult(String indexPathType,String indexId){
		RQuery query= queryFacade.createRQuery(indexPathType);
		QueryResult queryResult=query.getQueryResultByIndexId(indexId);
		query.close();
		return queryResult;
	}
	
	public QueryResult[] getDatabaseQueryResults(String indexPathType,String tableName,String recordId){
		RQuery query=queryFacade.createRQuery(indexPathType);
		QueryResult[] queryResults=query.getDatabaseQueryResultArray(tableName, recordId);
		query.close();
		return queryResults;
	}
	
	public QueryResult[] getQueryResults(String indexPathType,String queryContent){
		RQuery query=queryFacade.createRQuery(indexPathType);
		QueryResult[] queryResults=query.getFullContentQueryResults(queryContent);
		query.close();
		return queryResults;
	}
	
	public QueryResult[] getQueryResults(String[] indexPathTypes,QueryItem queryItem){
		indexPathTypes=ApplicationContext.getLocalIndexPathTypes(indexPathTypes);
		RQuery query=queryFacade.createRQuery(indexPathTypes);
		QueryResult[] queryResults=query.getQueryResults(queryItem,0,15);
		query.close();
		return queryResults;
	}
	
	public QueryResult[] getQueryResults(String indexPathType,QueryItem queryItem,QuerySort querySort){
		RQuery query=queryFacade.createRQuery(indexPathType);
		QueryResult[] queryResults=query.getQueryResults(queryItem,querySort);
		query.close();
		return queryResults;
	}
	
	public QueryItem createQueryItem(RetrievalType.RDocItemType docItemType,Object name,String value){
		QueryItem queryItem=retrievalApplicationContext.getFacade().createQueryItem(docItemType, String.valueOf(name), value);
		return queryItem;
	}
	
	public void testAllQuery(){
		QueryItem queryItem0=createQueryItem(RetrievalType.RDocItemType.CONTENT,StringClass.getString(RDatabaseDefaultDocItemType._TITLE),"中国");

		QueryItem queryItem=queryItem0;

		System.out.println(queryItem.getQueryWrap().getQuery());
		
		QueryResult[] queryResult2=getQueryResults(new String[]{"JAVA/TEST_IMAGE"}, queryItem);
		
		if(queryResult2!=null){
			int length=queryResult2.length;
			System.out.println("====================================返回结果数："+length+"====================================");
			for(int i=0;i<length;i++){
				System.out.println("========================================================================");
				System.out.println("queryResult2["+i+"]="+queryResult2[i].getQueryResultMap());
				System.out.println("_RESUME="+queryResult2[i].getHighlighterResult(StringClass.getString(RDatabaseDefaultDocItemType._RESUME), 1000));
			}
		}

		System.out.println("===============================Complete=========================================");
		QueryItem queryItem3=createQueryItem(RetrievalType.RDocItemType.KEYWORD,RDatabaseDocItemType._DT,"TEST_WEB");
		QueryItem queryItem1=createQueryItem(RetrievalType.RDocItemType.KEYWORD,RDatabaseDocItemType._DID,"891");
		QueryItem queryItem2=createQueryItem(RetrievalType.RDocItemType.KEYWORD,RDatabaseDocItemType._DK,"GUID");
		QueryItem queryItem4=queryItem3.must(QueryItem.MUST,queryItem1).must(QueryItem.MUST,queryItem2);
		QueryResult[] queryResult3=getQueryResults(new String[]{"JAVA/TEST_WEB"}, queryItem4);
		System.out.println(queryResult3.length);
	}
	
	public static void main(String[] args) {
	
		TestNormalQuery testQuery=new TestNormalQuery();
		testQuery.testAllQuery();
		
	}
}
