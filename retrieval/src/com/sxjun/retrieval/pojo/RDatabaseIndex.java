/**
 *  code generation
 */
package com.sxjun.retrieval.pojo;

import java.util.List;

import com.sxjun.system.pojo.BasePojo;

/**
 * 索引设置Entity
 * @author sxjun
 * @version 2014-03-11
 */
public class RDatabaseIndex extends BasePojo{
	private static final long serialVersionUID = -8540192254583468758L;
	private Database database;//数据源
	private String database_id;//数据源id
	private IndexCategory indexCategory;//索引分类
	private String indexPath_id;//索引
	private String tableName;//数据表名
	private String keyField;//主键字段
	private String sql;//Sql查询语句
	private String trigSql;//触发器查询sql
	private String indexOperatorType;//操作类型 0:插入，1:更新
	private String defaultTitleFieldName;//设置数据库字段对应的标题
	private String defaultResumeFieldName;//设置数据库字段对应的摘要字段
	private String rmDuplicate = "0";//是否要去除重复标题正文字段；默认为true
	private String databaseRecordInterceptor;//设置拦截器
	private String isOn;//是否启用 0:启动 1:关闭
	private String isError;//是否存在错误
	private String error;//错误信息
	private String condtion;//查询条件
	private List<FiledMapper> filedMapperLsit;//字段映射
	private List<FiledSpecialMapper> filedSpecialMapperLsit;//特殊字段映射
	private List<JustSchedule> justScheduleList;//任务调度
	private String style ;//风格  0:rest 1:复合
	private String indexTriggerRecord;//触发器表
	private String isInit = "0";//是否初始化 0：否，1：是,2：零时
	private String binaryField;
	private String mediacyTime;
	
	public Database getDatabase() {
		return database;
	}
	public void setDatabase(Database database) {
		this.database = database;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getIndexOperatorType() {
		return indexOperatorType;
	}
	public void setIndexOperatorType(String indexOperatorType) {
		this.indexOperatorType = indexOperatorType;
	}
	public String getDefaultTitleFieldName() {
		return defaultTitleFieldName;
	}
	public void setDefaultTitleFieldName(String defaultTitleFieldName) {
		this.defaultTitleFieldName = defaultTitleFieldName;
	}
	public String getDefaultResumeFieldName() {
		return defaultResumeFieldName;
	}
	public void setDefaultResumeFieldName(String defaultResumeFieldName) {
		this.defaultResumeFieldName = defaultResumeFieldName;
	}
	public String getRmDuplicate() {
		return rmDuplicate;
	}
	public void setRmDuplicate(String rmDuplicate) {
		this.rmDuplicate = rmDuplicate;
	}
	public String getDatabaseRecordInterceptor() {
		return databaseRecordInterceptor;
	}
	public void setDatabaseRecordInterceptor(String databaseRecordInterceptor) {
		this.databaseRecordInterceptor = databaseRecordInterceptor;
	}
	public String getDatabase_id() {
		return database_id;
	}
	public void setDatabase_id(String database_id) {
		this.database_id = database_id;
	}
	public List<FiledMapper> getFiledMapperLsit() {
		return filedMapperLsit;
	}
	public void setFiledMapperLsit(List<FiledMapper> filedMapperLsit) {
		this.filedMapperLsit = filedMapperLsit;
	}
	public String getIsOn() {
		return isOn;
	}
	public void setIsOn(String isOn) {
		this.isOn = isOn;
	}
	public List<FiledSpecialMapper> getFiledSpecialMapperLsit() {
		return filedSpecialMapperLsit;
	}
	public void setFiledSpecialMapperLsit(
			List<FiledSpecialMapper> filedSpecialMapperLsit) {
		this.filedSpecialMapperLsit = filedSpecialMapperLsit;
	}
	public String getIsError() {
		return isError;
	}
	public void setIsError(String isError) {
		this.isError = isError;
	}
	public String getError() {
		return error;
	}
	public String getCondtion() {
		return condtion;
	}
	public void setCondtion(String condtion) {
		this.condtion = condtion;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getIndexTriggerRecord() {
		return indexTriggerRecord;
	}
	public void setIndexTriggerRecord(String indexTriggerRecord) {
		this.indexTriggerRecord = indexTriggerRecord;
	}
	public String getIsInit() {
		return isInit;
	}
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}
	public List<JustSchedule> getJustScheduleList() {
		return justScheduleList;
	}
	public String getBinaryField() {
		return binaryField;
	}
	public void setBinaryField(String binaryField) {
		this.binaryField = binaryField;
	}
	public void setJustScheduleList(List<JustSchedule> justScheduleList) {
		this.justScheduleList = justScheduleList;
	}
	public String getIndexPath_id() {
		return indexPath_id;
	}
	public void setIndexPath_id(String indexPath_id) {
		this.indexPath_id = indexPath_id;
	}
	public String getTrigSql() {
		return trigSql;
	}
	public void setTrigSql(String trigSql) {
		this.trigSql = trigSql;
	}
	public IndexCategory getIndexCategory() {
		return indexCategory;
	}
	public void setIndexCategory(IndexCategory indexCategory) {
		this.indexCategory = indexCategory;
	}
	public String getMediacyTime() {
		return mediacyTime;
	}
	public void setMediacyTime(String mediacyTime) {
		this.mediacyTime = mediacyTime;
	}
	
}


