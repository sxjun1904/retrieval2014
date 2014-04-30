package frame.retrieval.engine.index.all.database.impl;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.lucene.index.IndexWriter;

import frame.base.core.util.RegexUtil;
import frame.base.core.util.StringClass;
import frame.base.core.util.UtilTool;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseFieldType;
import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.facade.IRDocOperatorFacade;
import frame.retrieval.engine.facade.IRQueryFacade;
import frame.retrieval.engine.index.all.RetrievalIndexAllException;
import frame.retrieval.engine.index.all.database.IBigDataOperator;
import frame.retrieval.engine.index.all.database.IIndexAllDatabaseRecordInterceptor;
import frame.retrieval.engine.index.all.database.IRDatabaseIndexAll;
import frame.retrieval.engine.index.doc.database.DatabaseIndexDocument;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;
import frame.retrieval.engine.index.doc.file.FileIndexDocument;
import frame.retrieval.engine.index.doc.internal.RDocItem;
import frame.retrieval.engine.query.result.FileQueryResult;

/**
 * 对数据库中的记录批量创建索引接口实现抽象类
 * @author 
 *
 */
public abstract class AbstractRDatabaseIndexAll implements IRDatabaseIndexAll{
	private Log log=RetrievalUtil.getLog(this.getClass());
	private int nowCount=0;
	private RDatabaseIndexAllItem databaseIndexAllItem=null;
	private IRQueryFacade queryFacade=null;
	private IRDocOperatorFacade docOperatorFacade=null;

	/**
	 * 获取数据库批量索引对象
	 */
	public RDatabaseIndexAllItem getDatabaseIndexAllItem() {
		return databaseIndexAllItem;
	}

	/**
	 * 设置数据库批量索引对象
	 * @param databaseIndexAllItem
	 */
	public void setDatabaseIndexAllItem(RDatabaseIndexAllItem databaseIndexAllItem) {
		if(databaseIndexAllItem.getIndexPathType()==null){
			throw new RetrievalIndexAllException("RDatabaseIndexAllItem 的 indexPathType 属性 不允许为空！！！");
		}
		
		if(databaseIndexAllItem.getIndexOperatorType()==null){
			throw new RetrievalIndexAllException("RDatabaseIndexAllItem 的 RetrievalType.RIndexOperatorType 属性 不允许为空！！！");
		}
		this.databaseIndexAllItem = databaseIndexAllItem;
		this.queryFacade=databaseIndexAllItem.getQueryFacade();
		this.docOperatorFacade=databaseIndexAllItem.getDocOperatorFacade();
		
	}
	
	/**
	 * 获取数据库记录相关文件
	 * @param recordId
	 * @return
	 */
	public List<FileIndexDocument> getFileIndexDocuments(String recordId) {
		if(databaseIndexAllItem.getDatabaseFileIndexOperator()==null){
			return null;
		}
		return databaseIndexAllItem.getDatabaseFileIndexOperator().getFileIndexDocuments(recordId);
	}

	/**
	 * 获取当前记录数
	 * @return
	 */
	protected int getNowCount() {
		return nowCount;
	}

	/**
	 * 设置当前记录数
	 * @param nowCount
	 */
	protected void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

	/**
	 * 生成分页SQL
	 * @param sql
	 * @param from
	 * @param size
	 * @return
	 */
	protected String getLimitString_Oracle(String sql, int from, int size) {
		StringBuffer pagingSelect = new StringBuffer();
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ where rownum <= ");
		pagingSelect.append(from + size);
		pagingSelect.append(") where rownum_ > ");
		pagingSelect.append(from);
		return pagingSelect.toString();
	}
	
	protected String getLimitString_MySql(String sql, int from, int size) {
		StringBuffer pagingSelect = new StringBuffer();
		pagingSelect.append(sql);
		pagingSelect.append(" LIMIT ");
		pagingSelect.append(from);
		pagingSelect.append(",");
		pagingSelect.append(size);
		return pagingSelect.toString();
	}
	
	protected String getLimitString_SqlServer(String sql, int from, int size) {
		StringBuffer pagingSelect = new StringBuffer();
		pagingSelect.append(" select * from ( select row_number()over(order by tempcolumn)temprownumber,*");
		pagingSelect.append(" from (select top ");
		pagingSelect.append(from + size);
		pagingSelect.append(" tempcolumn=0,* from (");
		pagingSelect.append(sql);
		pagingSelect.append(" )t)tt)ttt ");
		pagingSelect.append(" where ttt.temprownumber> ");
		pagingSelect.append(from);
		return pagingSelect.toString();
	}
	
	/**
	 * 通过一条数据库记录生成DatabaseIndexDocument对象
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected DatabaseIndexDocument createDatabaseIndexDocument(Map map){
		IIndexAllDatabaseRecordInterceptor databaseRecordInterceptor=databaseIndexAllItem.getDatabaseRecordInterceptor();

		Map fieldTypeMap=null;
		if(databaseRecordInterceptor!=null){
			map=(Map)databaseRecordInterceptor.interceptor(map);
			fieldTypeMap=databaseRecordInterceptor.getFieldsType();
		}
		
		String recordId=StringClass.getString(map.get(databaseIndexAllItem.getKeyField()));
		String _title = StringClass.getString(map.get(databaseIndexAllItem.getDefaultTitleFieldName()));
		String _resume = StringClass.getString(map.get(databaseIndexAllItem.getDefaultResumeFieldName()));
		
		DatabaseIndexDocument databaseIndexDocument=new DatabaseIndexDocument(databaseIndexAllItem.isFullContentFlag());
		databaseIndexDocument.setIndexInfoType(databaseIndexAllItem.getIndexInfoType());
		databaseIndexDocument.setIndexPathType(databaseIndexAllItem.getIndexPathType());
		databaseIndexDocument.setTableNameAndRecordId(databaseIndexAllItem.getTableName(),recordId);
		databaseIndexDocument.setKeyfieldName(databaseIndexAllItem.getKeyField());
		databaseIndexDocument.setDefaultTitle(_title);
		databaseIndexDocument.setDefaultResume(_resume);
		
		if(databaseIndexAllItem.isRmDuplicate()){
			map.remove(databaseIndexAllItem.getKeyField());
			map.remove(databaseIndexAllItem.getDefaultTitleFieldName());
			map.remove(databaseIndexAllItem.getDefaultResumeFieldName());
		}
		
		Object[][] objects=UtilTool.getMapKeyValue(map);
		int len=objects.length;
		
		Map<String,String> fieldMapper = databaseIndexAllItem.getFieldMapper();
		
		for(int j=0;j<len;j++){
			String name=StringClass.getString(objects[j][0]).toUpperCase();
			String type=StringClass.getString((fieldTypeMap.get(name)));
			//替换数据库字段
			if(fieldMapper!=null&&!fieldMapper.isEmpty()){
				if(fieldMapper.get(name)!=null)
					name =  fieldMapper.get(name);
			}
			String content=StringClass.getString(objects[j][1]);
			RDocItem docItem=new RDocItem();
			docItem.setName(name);
			docItem.setContent(content);
			if(fieldTypeMap!=null){
				if(!type.equals("")){
					if(type.equalsIgnoreCase(String.valueOf(RetrievalType.RDocItemType.KEYWORD))){
						databaseIndexDocument.addKeyWord(docItem);
					}else if(type.equalsIgnoreCase(String.valueOf(RetrievalType.RDocItemType.DATE))){
						databaseIndexDocument.addDateProperty(docItem);
					}else if(type.equalsIgnoreCase(String.valueOf(RetrievalType.RDocItemType.NUMBER))){
						databaseIndexDocument.addNumberProperty(docItem);
					}else if(type.equalsIgnoreCase(String.valueOf(RetrievalType.RDocItemType.PROPERTY))){
						databaseIndexDocument.addContent(docItem);
					}else{
						databaseIndexDocument.addContent(docItem);
					}
				}else{
					databaseIndexDocument.addContent(docItem);	
				}
			}else{
				databaseIndexDocument.addContent(docItem);				
			}
		}
		
		return databaseIndexDocument;
		
	}

	/**
	 * 给所有内容建索引
	 */
	public long indexAll() {
		 return indexAll(null);
	}
	@SuppressWarnings("unchecked")
	public long indexAll(IndexWriter indexWriter) {
		long indexCount=0;
		List list=next();
		
		String indexPathType="";
		boolean debugLogFlag=false;
		if(log.isDebugEnabled()){
			debugLogFlag=true;
		}
		
		while(list!=null && list.size()>0){
			
			List<DatabaseIndexDocument> databaseDocumentList=new ArrayList<DatabaseIndexDocument>();
			
			List<String> deleteRecordIds=new ArrayList<String>();

			List<String> deleteDocumentIdList=new ArrayList<String>();

			List<FileIndexDocument> fileDocumentList=new ArrayList<FileIndexDocument>();
			
			int length=list.size();
			StringBuilder stringBuilder=new StringBuilder();
			
			for(int i=0;i<length;i++){
				Map map=(Map)list.get(i);
				
				DatabaseIndexDocument databaseIndexDocument=createDatabaseIndexDocument(map);

				if(i==0){
					indexPathType=databaseIndexDocument.getIndexPathType();
				}
				
				List<FileIndexDocument> fileIndexDocuments=getFileIndexDocuments(databaseIndexDocument.getRecordId());
				
				if(fileIndexDocuments!=null){
					int fileListSize=fileIndexDocuments.size();
					for(int f=0;f<fileListSize;f++){
						FileIndexDocument fileIndexDocument=fileIndexDocuments.get(f);
						fileIndexDocument.setTableNameAndRecordId(databaseIndexDocument.getTableName(), databaseIndexDocument.getRecordId());
					}
				}
				
				//判断当前操作时属于更新索引，还是重建索引
				if(getDatabaseIndexAllItem().getIndexOperatorType()==RetrievalType.RIndexOperatorType.UPDATE){

					//删除旧数据索引
					deleteRecordIds.add(databaseIndexDocument.getRecordId());

					//删除旧文件索引
					addDeleteFileDocuments(deleteDocumentIdList,
							databaseIndexDocument.getIndexPathType(),
							databaseIndexDocument.getTableName(),
							databaseIndexDocument.getRecordId());
					//生成数据库记录索引
					databaseDocumentList.add(databaseIndexDocument);
					
					//生成数据库对应文件的记录索引
					if(fileIndexDocuments!=null && fileIndexDocuments.size()>0){
						int fileLen=fileIndexDocuments.size();
						for(int k=0;k<fileLen;k++){
							FileIndexDocument fileIndexDocument=fileIndexDocuments.get(k);
							fileDocumentList.add(fileIndexDocument);
						}
					}
					
				}else if(getDatabaseIndexAllItem().getIndexOperatorType()==RetrievalType.RIndexOperatorType.INSERT){
					
					//生成数据库记录索引
					databaseDocumentList.add(databaseIndexDocument);
					//生成数据库对应文件的记录索引
					if(fileIndexDocuments!=null && fileIndexDocuments.size()>0){
						fileDocumentList.addAll(fileIndexDocuments);
					}
					
				}else if(getDatabaseIndexAllItem().getIndexOperatorType()==RetrievalType.RIndexOperatorType.DELETE){

					//删除旧数据索引
					deleteRecordIds.add(databaseIndexDocument.getRecordId());
					//删除旧文件索引
					addDeleteFileDocuments(deleteDocumentIdList,
							databaseIndexDocument.getIndexPathType(),
							databaseIndexDocument.getTableName(),
							databaseIndexDocument.getRecordId());
					
				}
				
			}

			
			if(debugLogFlag){
				if(getDatabaseIndexAllItem().getIndexOperatorType()==RetrievalType.RIndexOperatorType.UPDATE){
					stringBuilder.append("批量数据索引["+indexPathType+"]更新:"+length+"条索引");
				}else if(getDatabaseIndexAllItem().getIndexOperatorType()==RetrievalType.RIndexOperatorType.INSERT){
					stringBuilder.append("批量数据索引["+indexPathType+"]新增:"+length+"条索引");
				}else if(getDatabaseIndexAllItem().getIndexOperatorType()==RetrievalType.RIndexOperatorType.DELETE){
					stringBuilder.append("批量数据索引["+indexPathType+"]删除:"+length+"条索引");
				}
			}
				
			log.debug(stringBuilder);
			
			if(deleteDocumentIdList!=null && deleteDocumentIdList.size()>0){
				docOperatorFacade.delete(getDatabaseIndexAllItem().getIndexPathType(), deleteDocumentIdList);
			}
			
			if(deleteRecordIds!=null && deleteRecordIds.size()>0){
				docOperatorFacade.delete(getDatabaseIndexAllItem().getIndexPathType(), getDatabaseIndexAllItem().getTableName(), deleteRecordIds);
			}
			
			docOperatorFacade.createDatabaseIndexs(databaseDocumentList,indexWriter);
			
			docOperatorFacade.createFileIndexs(fileDocumentList,databaseIndexAllItem.getMaxIndexFileSize());
			
			indexCount+=list.size();
				
			list=next();
		}
		return indexCount;
	}

	/**
	 * 获取下一页数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> next() {
		return getResultList();
	}

	/**
	 * 添加需要和数据库记录一同删除的文件索引
	 * @param deleteDocumentIdList
	 * @param indexPathType
	 * @param tableName
	 * @param recordId
	 */
	private void addDeleteFileDocuments(List<String> deleteDocumentIdList,String indexPathType,String tableName,String recordId){
		if(databaseIndexAllItem.getDatabaseFileIndexOperator()!=null){
			FileQueryResult[] fileQueryResults=queryFacade.createRQuery(indexPathType).getFileQueryResultArray(tableName, recordId);
			if(fileQueryResults!=null && fileQueryResults.length>0){
				int fileslength=fileQueryResults.length;
				for(int i=0;i<fileslength;i++){
					FileQueryResult fileQueryResult=fileQueryResults[i];
					deleteDocumentIdList.add(fileQueryResult.getIndexId());
				}
			}
		}
	}
	
	/**
	 * 获取当前页数据库记录,每调用一次这个方法，就返回一页的记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract List<Map> getResultList();
	
}
