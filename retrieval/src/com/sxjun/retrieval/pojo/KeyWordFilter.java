/**
 *  code generation
 */
package com.sxjun.retrieval.pojo;

import com.sxjun.system.pojo.BasePojo;

/**
 * 搜索过滤Entity
 * @author sxjun
 * @version 2014-07-18
 */
public class KeyWordFilter extends BasePojo{
	private String keywords; 	// 名称

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}


