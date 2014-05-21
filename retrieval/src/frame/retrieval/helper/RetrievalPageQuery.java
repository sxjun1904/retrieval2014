package frame.retrieval.helper;

import java.io.Serializable;

import frame.retrieval.engine.RetrievalType;

/**
 * RetrievalQuery
 * @author 
 *
 */
public class RetrievalPageQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7677057725355576779L;
	
	private String orderByFieldName;
	
	private Integer sortFieldType;
	
	private boolean ascFlag;
	
	private int pageSize=10;
	
	private int nowStartPage=-1;
	
	private String titleFieldName=RetrievalType.RDatabaseDefaultDocItemType._TITLE.toString();
	
	private int titleLength=50;
	
	private String resumeFieldName=RetrievalType.RDatabaseDefaultDocItemType._RESUME.toString();
	
	private int resumeLength=300;
	
	private String[] queryFields;

	/**
	 * 获取排序字段
	 * @return
	 */
	public String getOrderByFieldName() {
		return orderByFieldName;
	}

	/**
	 * 设置排序字段
	 * @param orderByFieldName
	 */
	public void setOrderByFieldName(String orderByFieldName) {
		this.orderByFieldName = orderByFieldName;
	}

	/**
	 * 是否升序排列
	 * @return
	 */
	public boolean isAscFlag() {
		return ascFlag;
	}

	/**
	 * 设置是否升序排列
	 * @param ascFlag
	 */
	public void setAscFlag(boolean ascFlag) {
		this.ascFlag = ascFlag;
	}

	/**
	 * 获取结果每页大小
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置结果每页大小
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取当前起始页码
	 * @return
	 */
	public int getNowStartPage() {
		return nowStartPage;
	}

	/**
	 * 设置当前起始页码
	 * @param nowStartPage
	 */
	public void setNowStartPage(int nowStartPage) {
		this.nowStartPage = nowStartPage;
	}

	/**
	 * 获取摘要长度
	 * @return
	 */
	public int getResumeLength() {
		return resumeLength;
	}

	/**
	 * 设置摘要长度
	 * @param resumeLength
	 */
	public void setResumeLength(int resumeLength) {
		this.resumeLength = resumeLength;
	}
	
	/**
	 * 获取标题字段名称
	 * @return
	 */
	public String getTitleFieldName() {
		return titleFieldName;
	}

	/**
	 * 设置标题字段名称
	 * @param titleFieldName
	 */
	public void setTitleFieldName(String titleFieldName) {
		this.titleFieldName = titleFieldName;
	}

	/**
	 * 获取摘要字段名称
	 * @return
	 */
	public String getResumeFieldName() {
		return resumeFieldName;
	}

	/**
	 * 设置摘要字段名称
	 * @param resumeFieldName
	 */
	public void setResumeFieldName(String resumeFieldName) {
		this.resumeFieldName = resumeFieldName;
	}

	/**
	 * 获取标题长度
	 * @return
	 */
	public int getTitleLength() {
		return titleLength;
	}

	/**
	 * 设置标题长度
	 * @param titleLength
	 */
	public void setTitleLength(int titleLength) {
		this.titleLength = titleLength;
	}

	public String[] getQueryFields() {
		return queryFields;
	}

	public void setQueryFields(String[] queryFields) {
		this.queryFields = queryFields;
	}

	public Integer getSortFieldType() {
		return sortFieldType;
	}

	public void setSortFieldType(Integer sortFieldType) {
		this.sortFieldType = sortFieldType;
	}
	
}
