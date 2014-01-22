/**
 *  code generation
 */
package com.sxjun.retrieval.pojo;

import com.sxjun.system.pojo.BasePojo;

/**
 * 字段映射Entity
 * @author sxjun
 * @version 2014-01-14
 */
public class FiledMapper extends BasePojo{
	/**
	 * sql数据库字段
	 */
	private String sqlField;
	/**
	 * index索引字段
	 */
	private String indexField;
	
	/**
	 * 获取sql数据库字段
	 * @return
	 */
	public String getSqlField() {
		return sqlField;
	}
	/**
	 * 设置sql数据库字段
	 */
	public void setSqlField(String sqlField) {
		this.sqlField = sqlField;
	}
	/**
	 * 获取index索引字段
	 * @return
	 */
	public String getIndexField() {
		return indexField;
	}
	/**
	 * 设置index索引字段
	 */
	public void setIndexField(String indexField) {
		this.indexField = indexField;
	}
	
}


