/**
 *  code generation
 */
package com.sxjun.retrieval.pojo;

import com.sxjun.system.pojo.BasePojo;

/**
 * 索引管理Entity
 * @author sxjun
 * @version 2014-07-23
 */
public class IndexManager extends BasePojo{
	private String type; 	// 类型
	private String field; 	// 字段
	private String keyword; 	// 关键字
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}


