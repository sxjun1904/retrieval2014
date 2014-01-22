/**
 *  code generation
 */
package com.sxjun.retrieval.pojo;

import com.sxjun.system.pojo.BasePojo;

/**
 * 数据库索引管理Entity
 * @author sxjun
 * @version 2014-01-14
 */
public class RDatabaseIndex extends BasePojo{
	private String name; 	// 名称
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}


