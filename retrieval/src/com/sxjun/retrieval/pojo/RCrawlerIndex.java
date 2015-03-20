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
public class RCrawlerIndex extends RIndex{
	private static final long serialVersionUID = -8540192254583468758L;
	private IndexCategory indexCategory;//索引分类
	private String name;//索引名
	private String indexPath_id;//索引
	private String keyField;//主键字段
	private String indexOperatorType;//操作类型 0:插入，1:更新
	private String defaultTitleFieldName;//设置数据库字段对应的标题
	private String defaultResumeFieldName;//设置数据库字段对应的摘要字段
	private String databaseRecordInterceptor;//设置拦截器
	private String isOn;//是否启用 0:启动 1:关闭
	private String isError;//是否存在错误
	private String error;//错误信息
	private String condtion;//查询条件
	private String isInit = "0";//是否初始化 0：否，1：是,2：零时
	private String mediacyTime;
	private String url;//要爬的网站路径
	private String numberOfCrawlers;//线程数
	private String maxDepthOfCrawling;//深度
	
	public IndexCategory getIndexCategory() {
		return indexCategory;
	}
	public void setIndexCategory(IndexCategory indexCategory) {
		this.indexCategory = indexCategory;
	}
	public String getIndexPath_id() {
		return indexPath_id;
	}
	public void setIndexPath_id(String indexPath_id) {
		this.indexPath_id = indexPath_id;
	}
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
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
	public String getDatabaseRecordInterceptor() {
		return databaseRecordInterceptor;
	}
	public void setDatabaseRecordInterceptor(String databaseRecordInterceptor) {
		this.databaseRecordInterceptor = databaseRecordInterceptor;
	}
	public String getIsOn() {
		return isOn;
	}
	public void setIsOn(String isOn) {
		this.isOn = isOn;
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
	public void setError(String error) {
		this.error = error;
	}
	public String getCondtion() {
		return condtion;
	}
	public void setCondtion(String condtion) {
		this.condtion = condtion;
	}
	public String getIsInit() {
		return isInit;
	}
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}
	public String getMediacyTime() {
		return mediacyTime;
	}
	public void setMediacyTime(String mediacyTime) {
		this.mediacyTime = mediacyTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumberOfCrawlers() {
		return numberOfCrawlers;
	}
	public void setNumberOfCrawlers(String numberOfCrawlers) {
		this.numberOfCrawlers = numberOfCrawlers;
	}
	public String getMaxDepthOfCrawling() {
		return maxDepthOfCrawling;
	}
	public void setMaxDepthOfCrawling(String maxDepthOfCrawling) {
		this.maxDepthOfCrawling = maxDepthOfCrawling;
	}
	
}


