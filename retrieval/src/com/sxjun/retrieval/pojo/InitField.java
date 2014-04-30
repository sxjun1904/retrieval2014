package com.sxjun.retrieval.pojo;

import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.system.pojo.BasePojo;

public class InitField extends BasePojo{
	private static final long serialVersionUID = 288023499993924424L;
	/**
	 * 设置字段
	 */
	private String field;
	/**
	 * 设置字段类型
	 */
	private String fieldType;
	/**
	 * 设置字段默认值
	 */
	private String defaultValue;
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 获取字段
	 * @return
	 */
	public String getField() {
		return field;
	}
	/**
	 * 设置字段
	 * @param field
	 */
	public void setField(String field) {
		this.field = field.toUpperCase();
	}
	/**
	 * 字段类型，分词，不分词
	 * @return
	 */
	public String getFieldType() {
		return fieldType;
	}
	/**
	 * 字段类型，分词，不分词
	 * @param fieldType
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	/**
	 * 获取字段描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 字段描述
	 * @return
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取字段为空时值
	 * @return
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * 设置字段为空时值
	 * @param fieldEmpty
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
}
