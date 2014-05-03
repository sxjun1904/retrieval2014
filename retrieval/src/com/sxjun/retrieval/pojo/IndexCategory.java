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
public class IndexCategory extends BasePojo{
	/**
	 * 如：知识库、信息发布、停电信息
	 */
	private String indexInfoType;
	/**
	 * DB、FILE、IMAGE
	 */
	private String indexPathType;	
	/**
	 * TEST/TEST_WEB
	 */
	private String indexPath;
	public String getIndexInfoType() {
		return indexInfoType;
	}
	public void setIndexInfoType(String indexInfoType) {
		this.indexInfoType = indexInfoType;
	}
	public String getIndexPathType() {
		return indexPathType;
	}
	public void setIndexPathType(String indexPathType) {
		this.indexPathType = indexPathType;
	}
	public String getIndexPath() {
		return indexPath;
	}
	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}
	
	
}


