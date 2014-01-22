package framework.retrieval.test.query;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.facade.IRQueryFacade;
import framework.retrieval.engine.query.RQuery;
import framework.retrieval.engine.query.item.QueryItem;
import framework.retrieval.engine.query.item.QuerySort;
import framework.retrieval.engine.query.item.QueryUtil;
import framework.retrieval.engine.query.result.QueryResult;
import framework.retrieval.test.init.TestInit;

public class TestQuery {

	private RetrievalApplicationContext retrievalApplicationContext=TestInit.getApplicationContent();
	
	private IRQueryFacade queryFacade=null;

	public TestQuery(){
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
		RQuery query=queryFacade.createRQuery(indexPathTypes);
		QueryResult[] queryResults=query.getQueryResults(queryItem);
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
		String indexId=getDatabaseIndexId("DB", "TABLE1", "23489548309234");
		System.out.println(indexId);
		
		System.out.println(getDatabaseIndexIdCount("DB", "TABLE1", "23489548309234"));
		
		System.out.println(getQueryResult("DB", "20100723091935607365686292676030"));

		QueryResult[] queryResult1=getDatabaseQueryResults("DB", "TABLE1", "23489548309234");
		if(queryResult1!=null){
			int length=queryResult1.length;
			for(int i=0;i<length;i++){
				System.out.println("queryResult1["+i+"]="+queryResult1[i]);
			}
		}
		
		QuerySort querySort=new QuerySort(QueryUtil.createScoreSort());
		
		QueryItem queryItem0=createQueryItem(RetrievalType.RDocItemType.CONTENT,"ACCEPT_CONTENT","平潭");
		QueryItem queryItem1=createQueryItem(RetrievalType.RDocItemType.CONTENT,"HANDLE_OPINION","计划");
		QueryItem queryItem2=createQueryItem(RetrievalType.RDocItemType.CONTENT,"ACCEPT_CONTENT","客户");

		QueryItem queryItem=queryItem0.should(QueryItem.MUST,queryItem1).must(queryItem2);

		System.out.println(queryItem.getQueryWrap().getQuery());
		
		QueryResult[] queryResult2=getQueryResults(new String[]{"DB"}, queryItem);
		
		if(queryResult2!=null){
			int length=queryResult2.length;
			System.out.println("====================================返回结果数："+length+"====================================");
			for(int i=0;i<length;i++){
				System.out.println("========================================================================");
				System.out.println("queryResult2["+i+"]="+queryResult2[i].getQueryResultMap());
				System.out.println("CONTENT="+queryResult2[i].getHighlighterResult("CONTENT", 1000));
			}
		}

		System.out.println("===============================Complete=========================================");
	}
	
	public static void main(String[] args) {
	
		TestQuery testQuery=new TestQuery();
		testQuery.testAllQuery();
		
	}
}
