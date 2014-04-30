package frame.retrieval.engine.index.doc.database;

import java.io.Serializable;
import java.util.Map;

import frame.retrieval.engine.RetrievalConstant;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseFieldType;
import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.facade.IRDocOperatorFacade;
import frame.retrieval.engine.facade.IRQueryFacade;
import frame.retrieval.engine.index.all.database.DatabaseLink;
import frame.retrieval.engine.index.all.database.IIndexAllDatabaseFileIndexOperator;
import frame.retrieval.engine.index.all.database.IIndexAllDatabaseRecordInterceptor;

/**
 * 数据库批量索引对象
 * @author 
 *
 */
public class RDatabaseIndexAllItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否同时再索引中生成一个将所有内容组合存放的字段
	 */
	private boolean fullContentFlag=false;
	
	/**
	 * 索引路径类型
	 */
	private String indexPathType=null;
	
	/**
	 * 索引操作类型
	 */
	private RetrievalType.RIndexOperatorType indexOperatorType=null;
	
	/**
	 * 索引信息分类标志
	 * 		如：知识库、信息发布、停电信息
	 */
	private String indexInfoType="";
	
	/**
	 * 默认标题字段名
	 */
	private String defaultTitleFieldName="";
	
	/**
	 * 默认摘要字段名
	 */
	private String defaultResumeFieldName="";
	
	/**
	 * 根据数据库ID，获取相关文件信息
	 */
	private IIndexAllDatabaseFileIndexOperator databaseFileIndexOperator;
	
	/**
	 * 单条数据库记录拦截器
	 */
	private IIndexAllDatabaseRecordInterceptor databaseRecordInterceptor;

	private IRQueryFacade queryFacade=null;
	
	private IRDocOperatorFacade docOperatorFacade=null;
	
	private String tableName;
	
	private String keyField;
	
	private String sql;
	
	private Object[] param;
	
	private int pageSize=500;
	
	private int maxPageSize=2000;
	
	private long maxIndexFileSize=RetrievalConstant.DEFAULT_INDEX_MAX_FILE_SZIE;
	
	/**
	 * 是否要去除重复，默认去除
	 */
	private boolean rmDuplicate=true;
	
	/**
	 * 字段映射，将数据库字段转成索引字段
	 */
	private Map<String,String> fieldMapper;
	
	/**
	 * 字段类型设置
	 */
	private Map<String,String> fieldTypeMapper;
	
	private Map<String,Integer[]>  fieldSpecialMapper;
	
	/**
	 * 数据源信息
	 */
	private DatabaseLink databaseLink;
	
	
	private Object transObject;
	
	/**
	 * 
	 * @param queryFacade 
	 * @param docOperatorFacade 
	 * @param fullContentFlag 是否同时再索引中生成一个将所有内容组合存放的字段
	 * @param maxPageSize 最大文件分页数量，每页读取的记录数量不允许超过这个最大值
	 */
	public RDatabaseIndexAllItem(IRQueryFacade queryFacade,IRDocOperatorFacade docOperatorFacade,boolean fullContentFlag,int maxPageSize){
		this.queryFacade=queryFacade;
		this.docOperatorFacade=docOperatorFacade;
		this.fullContentFlag=fullContentFlag;
		this.maxPageSize=maxPageSize;
	}
	
	public IRQueryFacade getQueryFacade() {
		return queryFacade;
	}

	public IRDocOperatorFacade getDocOperatorFacade() {
		return docOperatorFacade;
	}

	/**
	 * 是否将所有内容组合成一个字段存放
	 * @return
	 */
	public boolean isFullContentFlag() {
		return fullContentFlag;
	}

	/**
	 * 获取信息分类
	 * @return
	 */
	public String getIndexInfoType() {
		return indexInfoType;
	}

	/**
	 * 设置信息分类
	 * @param indexInfoType
	 */
	public void setIndexInfoType(String indexInfoType) {
		this.indexInfoType = indexInfoType;
	}
	
	/**
	 * 获取默认的标题字段名
	 * @return
	 */
	public String getDefaultTitleFieldName(){
		return defaultTitleFieldName;
	}
	
	/**
	 * 设置默认的标题字段名
	 * @param defaultTitleFieldName
	 */
	public void setDefaultTitleFieldName(String defaultTitleFieldName){
		this.defaultTitleFieldName=defaultTitleFieldName;
	}
	
	/**
	 * 获取默认的摘要字段名
	 * @return
	 */
	public String getDefaultResumeFieldName(){
		return defaultResumeFieldName;
	}
	
	/**
	 * 设置默认的摘要字段名
	 * @param defaultResumeFieldName
	 */
	public void setDefaultResumeFieldName(String defaultResumeFieldName){
		this.defaultResumeFieldName=defaultResumeFieldName;
	}

	/**
	 * 获取索引路径类型
	 * @return
	 */
	public String getIndexPathType() {
		return indexPathType;
	}

	/**
	 * 设置索引路径类型
	 * @param indexPathType
	 */
	public void setIndexPathType(String indexPathType) {
		this.indexPathType = RetrievalUtil.getIndexPathTypeFormat(indexPathType);
	}

	/**
	 * 获取索引操作类型
	 * @return
	 */
	public RetrievalType.RIndexOperatorType getIndexOperatorType() {
		return indexOperatorType;
	}

	/**
	 * 设置索引操作类型
	 * @param indexOperatorType
	 */
	public void setIndexOperatorType(RetrievalType.RIndexOperatorType indexOperatorType) {
		this.indexOperatorType = indexOperatorType;
	}

	/**
	 * 获取根据数据库ID，获取相关文件信息处理对象
	 * @return
	 */
	public IIndexAllDatabaseFileIndexOperator getDatabaseFileIndexOperator() {
		return databaseFileIndexOperator;
	}

	/**
	 * 设置根据数据库ID，获取相关文件信息处理对象
	 * @param databaseFileIndexOperator
	 */
	public void setDatabaseFileIndexOperator(
			IIndexAllDatabaseFileIndexOperator databaseFileIndexOperator) {
		this.databaseFileIndexOperator = databaseFileIndexOperator;
	}

	/**
	 * 获取当条数据加工处理器
	 * @return
	 */
	public IIndexAllDatabaseRecordInterceptor getDatabaseRecordInterceptor() {
		return databaseRecordInterceptor;
	}

	/**
	 * 设置当条数据加工处理器
	 * @param databaseRecordInterceptor
	 */
	public void setDatabaseRecordInterceptor(
			IIndexAllDatabaseRecordInterceptor databaseRecordInterceptor) {
		this.databaseRecordInterceptor = databaseRecordInterceptor;
	}

	/**
	 * 获取数据库表名
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 设置数据库表名
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 获取数据库表关键字段名称
	 * @return
	 */
	public String getKeyField() {
		return keyField;
	}

	/**
	 * 设置数据库表关键字段名称
	 * @param keyField
	 */
	public void setKeyField(String keyField) {
		this.keyField = keyField.toUpperCase();
	}

	/**
	 * 获取SQL
	 * @return
	 */
	public String getSql() {
		return sql;
	}
	
	/**
	 * 设置SQL
	 * @param sql
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	/**
	 * 获取SQL参数
	 * @return
	 */
	public Object[] getParam() {
		return param;
	}
	
	/**
	 * 设置SQL参数
	 * @param param
	 */
	public void setParam(Object[] param) {
		this.param = param;
	}

	/**
	 * 获取每页大小，默认值为 100
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页大小，默认值为 100
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		if(pageSize>maxPageSize){
			pageSize=maxPageSize;
		}
		this.pageSize = pageSize;
	}

	public long getMaxIndexFileSize() {
		return maxIndexFileSize;
	}

	public void setMaxIndexFileSize(long maxIndexFileSize) {
		this.maxIndexFileSize = maxIndexFileSize;
	}
	
	public boolean isRmDuplicate() {
		return rmDuplicate;
	}

	/**
	 * 设置建索引时是否取出重复字段
	 * @param rmDuplicate
	 */
	public void setRmDuplicate(boolean rmDuplicate) {
		this.rmDuplicate = rmDuplicate;
	}

	public Map<String, String> getFieldMapper() {
		return fieldMapper;
	}

	/**
	 * 设置字段对应关系，Map<原字段,目标字段>
	 * @param fieldMapper
	 */
	public void setFieldMapper(Map<String, String> fieldMapper) {
		this.fieldMapper = fieldMapper;
	}

	/**
	 * 获取数据库库信息
	 * @return
	 */
	public DatabaseLink getDatabaseLink() {
		return databaseLink;
	}

	/**
	 * 设置数据库库信息
	 * @param databaseLink
	 */
	public void setDatabaseLink(DatabaseLink databaseLink) {
		this.databaseLink = databaseLink;
	}

	public Map<String, String> getFieldTypeMapper() {
		return fieldTypeMapper;
	}

	public void setFieldTypeMapper(Map<String, String> fieldTypeMapper) {
		this.fieldTypeMapper = fieldTypeMapper;
	}

	/**
	 * SQL_FIELDTYPE_PLAIN 0:普通
	 * SQL_FIELDTYPE_RM_HTML 1:去除html标签 
	 * SQL_FIELDTYPE_BLOB 2:获取blob字段
	 * SQL_FIELDTYPE_CLOB 3:获取blob字段
	 * @return
	 */
	public Map<String, Integer[]> getFieldSpecialMapper() {
		return fieldSpecialMapper;
	}

	public void setFieldSpecialMapper(Map<String, Integer[]> fieldSpecialMapper) {
		this.fieldSpecialMapper = fieldSpecialMapper;
	}

	public Object getTransObject() {
		return transObject;
	}

	public void setTransObject(Object transObject) {
		this.transObject = transObject;
	}
	
}
