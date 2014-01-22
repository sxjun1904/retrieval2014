package com.sxjun.retrieval.constant;

public class RKeyPrixConstant {
	public final static String KEY_JOIN =":";
	
	/**
	 * 数据源
	 * key:"DATABASE:UUID:数据源名称（自定义）"
	 */
	public final static String KEY_DATABASE_PRIX = "DATABASE";
	
	/**
	 * 基础字段
	 * key:"INITFILED:field名称"
	 */
	public final static String KEY_INITFILED_PRIX = "INITFILED";
	
	/**
	 * 字段映射
	 * key:"FILEDMAPPER:RDatabaseIndex的主键:UUID"
	 */
	public final static String KEY_FILEDMAPPER_PRIX = "FILEDMAPPER";
	
	/**
	 * 特殊字段映射
	 * key:"FILEDSPECIALMAPPER:RDatabaseIndex的主键:UUID"
	 */
	public final static String KEY_FILEDSPECIALMAPPER_PRIX = "FILEDSPECIALMAPPER";
	
	/**
	 * 索引分类
	 * key:"INDEXCAGETORY:RDatabaseIndex的主键"
	 */
	public final static String KEY_INDEXCAGETORY_PRIX = "INDEXCAGETORY";
	
	/**
	 * 索引管理
	 * key:"RDATABASEINDEX:UUID"
	 */
	public final static String KEY_RDATABASEINDEX = "RDATABASEINDEX";
	
}
