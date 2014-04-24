package framework.retrieval.test.dbindexall;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.context.ApplicationContext;
import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.facade.DBIndexOperatorFacade;
import framework.retrieval.engine.facade.IRDocOperatorFacade;
import framework.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

public class TestDatabaseIndexAll {	
	
	private RetrievalApplicationContext retrievalApplicationContext=ApplicationContext.getApplicationContent();
	
	public TestDatabaseIndexAll(){
		//ApplicationContext.initIndex("DB");
	}
	
	public void create() {
		DBIndexOperatorFacade indexAll = new DBIndexOperatorFacade(new DatabaseIndexAllItemTestImpl());
		indexAll.indexAll(indexAll.INDEX_BY_THREAD);

	}
	
	public void create1(){
		long startTime=System.currentTimeMillis();
		
		String tableName="TABLE2";
		String keyField="ID";
		String sql="SELECT ID," +
				"TITLE," +
				"CONTENT," +
				"FIELD3," +
				"FIELD4," +
				"FIELD5," +
				"FIELD6 FROM TABLE2 ORDER BY ID ASC";

		RDatabaseIndexAllItem databaseIndexAllItem=retrievalApplicationContext.getFacade().createDatabaseIndexAllItem(false);
		databaseIndexAllItem.setIndexPathType("DB");
		databaseIndexAllItem.setIndexInfoType("TABLE2");
		
		//如果无论记录是否存在，都新增一条索引内容，则使用RetrievalType.RIndexOperatorType.INSERT，
		//如果索引中记录已经存在，则只更新索引中的对应的记录，否则新增记录,则使用RetrievalType.RIndexOperatorType.UPDATE
		databaseIndexAllItem.setIndexOperatorType(RetrievalType.RIndexOperatorType.UPDATE);
		
		databaseIndexAllItem.setTableName(tableName);
		databaseIndexAllItem.setKeyField(keyField);
		databaseIndexAllItem.setDefaultTitleFieldName("TITLE");
		databaseIndexAllItem.setDefaultResumeFieldName("CONTENT");
		databaseIndexAllItem.setPageSize(500);
		databaseIndexAllItem.setSql(sql);
		databaseIndexAllItem.setParam(new Object[]{});
		
		databaseIndexAllItem.setDatabaseRecordInterceptor(new TestDatabaseRecordInterceptor());

		IRDocOperatorFacade docOperatorFacade=retrievalApplicationContext.getFacade().createDocOperatorFacade();
		
		docOperatorFacade.createAll(databaseIndexAllItem);
		
		System.out.println("TABLE2 耗时："+(((System.currentTimeMillis()-startTime)/1000))+" 秒");
	}
	
	public void create3(){
		long startTime=System.currentTimeMillis();
		
		String tableName="TABLE3";
		String keyField="ID";
		String sql="SELECT ID," +
				"TITLE," +
				"CONTENT," +
				"FIELD3," +
				"FIELD4," +
				"FIELD5," +
				"FIELD6 FROM TABLE3 ORDER BY ID ASC";

		RDatabaseIndexAllItem databaseIndexAllItem=retrievalApplicationContext.getFacade().createDatabaseIndexAllItem(false);
		databaseIndexAllItem.setIndexPathType("DB");
		databaseIndexAllItem.setIndexInfoType("TABLE3");
		
		//如果无论记录是否存在，都新增一条索引内容，则使用RetrievalType.RIndexOperatorType.INSERT，
		//如果索引中记录已经存在，则只更新索引中的对应的记录，否则新增记录,则使用RetrievalType.RIndexOperatorType.UPDATE
		databaseIndexAllItem.setIndexOperatorType(RetrievalType.RIndexOperatorType.INSERT);
		
		databaseIndexAllItem.setTableName(tableName);
		databaseIndexAllItem.setKeyField(keyField);
		databaseIndexAllItem.setDefaultTitleFieldName("TITLE");
		databaseIndexAllItem.setDefaultResumeFieldName("CONTENT");
		databaseIndexAllItem.setPageSize(500);
		databaseIndexAllItem.setSql(sql);
		databaseIndexAllItem.setParam(new Object[]{});
		
		databaseIndexAllItem.setDatabaseRecordInterceptor(new TestDatabaseRecordInterceptor());

		IRDocOperatorFacade docOperatorFacade=retrievalApplicationContext.getFacade().createDocOperatorFacade();
		
		docOperatorFacade.createAll(databaseIndexAllItem);
		
		System.out.println("TABLE3 耗时："+(((System.currentTimeMillis()-startTime)/1000))+" 秒");
	}
	
	public void optimize(String indexPathType){
		retrievalApplicationContext.getFacade().createIndexOperatorFacade(indexPathType).optimize();
	}
	
	public static void main(String[] args) {
		final TestDatabaseIndexAll testDatabaseIndexAll=new TestDatabaseIndexAll();
		Thread thread1=new Thread(new Runnable(){

			public void run() {
				testDatabaseIndexAll.create();
			}
			
		});

//		Thread thread2=new Thread(new Runnable(){
//
//			public void run() {
//				testDatabaseIndexAll.create1();
//			}
//			
//		});
//
//		Thread thread3=new Thread(new Runnable(){
//
//			public void run() {
//				testDatabaseIndexAll.create3();
//			}
//			
//		});
//		
		thread1.start();
//		thread2.start();
//		thread3.start();

//		testDatabaseIndexAll.optimize("DB");
		
	}
}
