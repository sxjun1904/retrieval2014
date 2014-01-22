/**
 *  code generation
 */
package com.sxjun.retrieval.pojo;

import com.sxjun.system.pojo.BasePojo;

/**
 * 索引分类Entity
 * @author sxjun
 * @version 2014-01-14
 */
public class IndexCagetory extends BasePojo{
	/**
	 * 如：知识库、信息发布、停电信息
	 */
	private String indexInfoType;
	/**
	 * DB、FILE、IMAGE
	 */
	private String IndexPathType;	
	/**
	 * TEST/TEST_WEB
	 */
	private String IndexPath;
	
	public String getIndexInfoType() {
		return indexInfoType;
	}
	
	public void setIndexInfoType(String indexInfoType) {
		this.indexInfoType = indexInfoType;
	}
	
	public String getIndexPathType() {
		return IndexPathType;
	}
	
	public void setIndexPathType(String indexPathType) {
		IndexPathType = indexPathType;
	}
	
	public String getIndexPath() {
		return IndexPath;
	}
	
	public void setIndexPath(String indexPath) {
		IndexPath = indexPath;
	}	
	
}


