/**
 *  code generation
 */
package com.sxjun.retrieval.pojo;

import com.sxjun.system.pojo.BasePojo;

/**
 * 状态监控Entity
 * @author sxjun
 * @version 2014-07-10
 */
public class IsInitView extends BasePojo{
	private String databaseName; 	
	private String tableName; 	
	private String indexInfoType; 	
	private String isInit; 	
	private String mediacyTime; 	
	private String info;
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getIndexInfoType() {
		return indexInfoType;
	}
	public void setIndexInfoType(String indexInfoType) {
		this.indexInfoType = indexInfoType;
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
}


