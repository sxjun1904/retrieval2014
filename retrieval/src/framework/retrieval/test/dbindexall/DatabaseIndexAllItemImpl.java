package framework.retrieval.test.dbindexall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.RetrievalType.RDatabaseFieldType;
import framework.retrieval.engine.RetrievalType.RDatabaseType;
import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.facade.ICreateIndexAllItem;
import framework.retrieval.engine.index.all.database.DatabaseLink;
import framework.retrieval.engine.index.all.database.impl.DefaultDBGetMethodImpl;
import framework.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

public class DatabaseIndexAllItemImpl implements ICreateIndexAllItem{

	@Override
	public List<RDatabaseIndexAllItem> generateApplicationData(RetrievalApplicationContext retrievalApplicationContext) {
		
		List <RDatabaseIndexAllItem> l = new ArrayList<RDatabaseIndexAllItem>();
		//////////////////////////////////////////////////mysql//////////////////////////////////////////////////////////////////////////////
		String tableName = "TEST_WEB";
		String keyField = "GUID";
		String sql = "SELECT GUID,TITLE,CONTENT,CREATETIME,TIME,PAGE_URL,PIC_URL,TESTBLOB FROM TEST_WEB ORDER BY GUID ASC";

		RDatabaseIndexAllItem databaseIndexAllItem = retrievalApplicationContext.getFacade().createDatabaseIndexAllItem(false);
		databaseIndexAllItem.setIndexPathType("DB/B/TEST_WEB");
		databaseIndexAllItem.setIndexInfoType("news");
		//databaseIndexAllItem.setDatabaseType(RDatabaseType.MYSQL);
		// 如果无论记录是否存在，都新增一条索引内容，则使用RetrievalType.RIndexOperatorType.INSERT，
		// 如果索引中记录已经存在，则只更新索引中的对应的记录，否则新增记录,则使用RetrievalType.RIndexOperatorType.UPDATE
		databaseIndexAllItem.setIndexOperatorType(RetrievalType.RIndexOperatorType.INSERT);
		databaseIndexAllItem.setTableName(tableName);
		databaseIndexAllItem.setKeyField(keyField);
		databaseIndexAllItem.setDefaultTitleFieldName("TITLE");
		databaseIndexAllItem.setDefaultResumeFieldName("CONTENT");
		databaseIndexAllItem.setPageSize(5000);
		databaseIndexAllItem.setSql(sql);
		databaseIndexAllItem.setParam(new Object[] {});
		Map<String,Integer[]> sqlSpecialFieldMapper = new HashMap<String,Integer[]>();
		sqlSpecialFieldMapper.put("CONTENT", new Integer[]{RDatabaseFieldType.SQL_FIELDTYPE_RM_HTML.getValue()});
		sqlSpecialFieldMapper.put("TESTBLOB", new Integer[]{RDatabaseFieldType.SQL_FIELDTYPE_BLOB.getValue(),RDatabaseFieldType.SQL_FIELDTYPE_RM_HTML.getValue()});
		databaseIndexAllItem.setFieldSpecialMapper(sqlSpecialFieldMapper);
		Map<String,String> fieldMapper = new HashMap<String,String>();
		fieldMapper.put("PAGE_URL", "my_url");
		databaseIndexAllItem.setFieldMapper(fieldMapper);
		databaseIndexAllItem.setRmDuplicate(false);
		databaseIndexAllItem.setDatabaseRecordInterceptor(new TestDatabaseRecordInterceptor());
		
		//设置数据库的连接信息
		DatabaseLink databaseLink = new DefaultDBGetMethodImpl().loadDatabaseLink();
		databaseIndexAllItem.setDatabaseLink(databaseLink);
		
		l.add(databaseIndexAllItem);
		
		///////////////////////////////////////////////////Oracle/////////////////////////////////////////////////////////////////////////////
		String tableName_Orcl = "VIEW_THREAD_CASE";
		String keyField_Orcl = "ROWGUID";
		String sql_Orcl = "SELECT ROWGUID,SUBJECT,MESSAGE,MAINOUNAME  FROM VIEW_THREAD_CASE T ORDER BY ROWGUID";

		RDatabaseIndexAllItem databaseIndexAllItem_Orcl = retrievalApplicationContext.getFacade().createDatabaseIndexAllItem(false);
		databaseIndexAllItem_Orcl.setIndexPathType("DB/B/VIEW_THREAD_CASE");
		databaseIndexAllItem_Orcl.setIndexInfoType("news");
		// 如果无论记录是否存在，都新增一条索引内容，则使用RetrievalType.RIndexOperatorType.INSERT，
		// 如果索引中记录已经存在，则只更新索引中的对应的记录，否则新增记录,则使用RetrievalType.RIndexOperatorType.UPDATE
		databaseIndexAllItem_Orcl.setIndexOperatorType(RetrievalType.RIndexOperatorType.INSERT);
		databaseIndexAllItem_Orcl.setTableName(tableName_Orcl);
		databaseIndexAllItem_Orcl.setKeyField(keyField_Orcl);
		databaseIndexAllItem_Orcl.setDefaultTitleFieldName("SUBJECT");
		databaseIndexAllItem_Orcl.setDefaultResumeFieldName("MESSAGE");
		databaseIndexAllItem_Orcl.setPageSize(2000);
		databaseIndexAllItem_Orcl.setSql(sql_Orcl);
		databaseIndexAllItem_Orcl.setParam(new Object[] {});
		Map<String,String> fieldMapper_Orcl = new HashMap<String,String>();
		fieldMapper.put("MAINOUNAME", "my_url");
		Map<String,Integer[]> sqlFieldTypeMapper_Orcl = new HashMap<String,Integer[]>();
		sqlFieldTypeMapper_Orcl.put("MESSAGE", new Integer[]{RDatabaseFieldType.SQL_FIELDTYPE_CLOB.getValue(),RDatabaseFieldType.SQL_FIELDTYPE_RM_HTML.getValue()});
		databaseIndexAllItem_Orcl.setFieldSpecialMapper(sqlFieldTypeMapper_Orcl);
		databaseIndexAllItem_Orcl.setFieldMapper(fieldMapper_Orcl);
		databaseIndexAllItem_Orcl.setRmDuplicate(true);
		databaseIndexAllItem_Orcl.setDatabaseRecordInterceptor(new TestDatabaseRecordInterceptor());
		
		//设置数据库的连接信息
		DatabaseLink databaseLink_Orcl = new DatabaseLink(RDatabaseType.ORACLE,"jdbc:oracle:thin:@192.168.200.185:1521:orcl","epointsq365","11111");
		databaseIndexAllItem_Orcl.setDatabaseLink(databaseLink_Orcl);
		
		//l.add(databaseIndexAllItem_Orcl);
		return l;
	}

}
