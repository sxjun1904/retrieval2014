package com.sxjun.retrieval.pojo;

import org.apache.lucene.search.BooleanClause;

import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.query.item.QueryItem;

public class SimpleItem {
	/**
	 * 索引字段类型
	 */
	private String field;
	
	/**
	 * 关键字
	 */
	private String keyword;
	
	/**
	 * 多条件查询时，两个条件的关系
	 * AND("+")
	 * OR("")
	 * NOT("-")
	 */
	private String relationType = QueryType.OR.getValue();
	
	public SimpleItem(){
		
	}
	
	public SimpleItem(String field){
		this.field = field;
	}
	
	public SimpleItem(String field ,String keyword){
		this.field = field;
		this.keyword = keyword;
	}
	
	public SimpleItem(String field ,String keyword,String relationType ){
		this.field = field;
		this.keyword = keyword;
		this.relationType = relationType;
	}
	
	public enum QueryType {
		AND("+"), OR(""), NOT("-");
		private final String value;
		QueryType(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	};
	
	/**
	 * 索引字段类型：
	 * 日期类型字段，以 索引+存储 的方式对这个类型的字段建索引，可以被搜索
	 * DATE
	 * 数值类型字段，以 索引+存储 的方式对这个类型的字段建索引，可以被搜索
	 * NUMBER
	 * 关键字字段，以 索引+存储 的方式对这个类型的字段建索引，可以被搜索
	 * KEYWORD
	 * 以存储的方式对这个类型的字段进行保存,并且不能被搜索
	 * STORE_ONLY
	 * 属性字段，以 分词+索引+存储 的方式对这个类型的字段建索引，可以被搜索
	 * PROPERTY
	 * 文本内容字段，以 分词+索引 的方式对这个类型的字段建索引，可以被搜索
	 * CONTENT
	 */
	private String fieldType = String.valueOf(RetrievalType.RDocItemType.CONTENT);
	
	private SimpleItem simpleItem;
	
	/**
	 * 索引字段
	 * @return
	 */
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	/**
	 * 索引字段类型
	 * @return
	 */
	public RetrievalType.RDocItemType getFieldType() {
		if(String.valueOf(RetrievalType.RDocItemType.CONTENT).equals(fieldType)){
			return RetrievalType.RDocItemType.CONTENT;
		}else if(String.valueOf(RetrievalType.RDocItemType.PROPERTY).equals(fieldType)){
			return RetrievalType.RDocItemType.PROPERTY;
		}else if(String.valueOf(RetrievalType.RDocItemType.STORE_ONLY).equals(fieldType)){
			return RetrievalType.RDocItemType.STORE_ONLY;
		}else if(String.valueOf(RetrievalType.RDocItemType.KEYWORD).equals(fieldType)){
			return RetrievalType.RDocItemType.KEYWORD;
		}else if(String.valueOf(RetrievalType.RDocItemType.NUMBER).equals(fieldType)){
			return RetrievalType.RDocItemType.NUMBER;
		}else if(String.valueOf(RetrievalType.RDocItemType.DATE).equals(fieldType)){
			return RetrievalType.RDocItemType.DATE;
		}else{
			return RetrievalType.RDocItemType.CONTENT;
		}
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	/**
	 * 两个索引之间的关系
	 * @return
	 */
	public String getRelationType() {
		return relationType;
	}
	
	public BooleanClause.Occur getClauseRelationType() {
		if("".endsWith(relationType)){
			return QueryItem.SHOULD;
		}else if("+".endsWith(relationType)){
			return QueryItem.MUST;
		}else if("-".endsWith(relationType)){
			return QueryItem.MUST_NOT;
		}else{
			return QueryItem.SHOULD;
		}
	}
	
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
	/**
	 * 关键字
	 * @return
	 */
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public SimpleItem getSimpleItem() {
		return simpleItem;
	}
	public void setSimpleItem(SimpleItem simpleItem) {
		this.simpleItem = simpleItem;
	}

}
