/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sxjun.retrieval.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jfinal.aop.Before;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.sxjun.retrieval.constant.DefaultConstant.DatabaseType;
import com.sxjun.retrieval.constant.DefaultConstant.FieldType;
import com.sxjun.retrieval.constant.DefaultConstant.IndexPathType;
import com.sxjun.retrieval.constant.DefaultConstant.ItemType;
import com.sxjun.retrieval.pojo.Dict;

import frame.retrieval.engine.RetrievalType.RDatabaseType;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
	
	public static final String CACHE_DICT_MAP = "dictMap";
	public static final String CACHE_DICT_N = "dicN_";
	
	public static final int DATABASE_TYPE = 0;
	public static final int FIELD_TYPE = 1;
	public static final int ITEMTYPE_TYPE = 2;
	public static final int INDEXPATH_TYPE = 3;
	
	public static RDatabaseType changeToRDatabaseType(String type){
		RDatabaseType rDatabaseType = null;
		switch(Integer.parseInt(type)){
			case 0 :{
				rDatabaseType = RDatabaseType.MYSQL;
				break;
			}
			case 1 :{
				rDatabaseType = RDatabaseType.ORACLE;
				break;
			}
			case 2 :{
				rDatabaseType = RDatabaseType.SQLSERVER;
				break;
			}
			default:{
				break;
			}
		}
		return rDatabaseType;
	}
	
	/**
	 * 
	 * 0 ：数据库类型
	 * 1：字段特殊处理
	 * @param type
	 * @return
	 */
	@Before(CacheInterceptor.class)
	@CacheName("dictN")
	public static Map<String,String> getDictMap(Integer type){
		Map<String, String> m = CacheKit.get(CACHE_DICT_N+type, CACHE_DICT_N+type);
		if(m==null){
			m = Maps.newHashMap();
			switch(type){
				case 0:{
					m.put(DatabaseType.SQLSERVER.getValue(), DatabaseType.SQLSERVER.toString());
					m.put(DatabaseType.ORACLE.getValue(), DatabaseType.ORACLE.toString());
					m.put(DatabaseType.MYSQL.getValue(), DatabaseType.MYSQL.toString());
					break;
				} 
				case 1:{
					m.put(FieldType.BOLOB.getValue(), FieldType.BOLOB.toString());
					m.put(FieldType.CLOB.getValue(), FieldType.CLOB.toString());
					m.put(FieldType.RMHTML.getValue(), FieldType.RMHTML.toString());
					break;
				}
				case 2:{
					m.put(ItemType.KEYWORD.getValue(),ItemType.KEYWORD.toString());
					m.put(ItemType.DATE.getValue(),ItemType.DATE.toString());
					m.put(ItemType.NUMBER.getValue(),ItemType.NUMBER.toString());
					m.put(ItemType.PROPERTY.getValue(),ItemType.PROPERTY.toString());
					m.put(ItemType.CONTENT.getValue(),ItemType.CONTENT.toString());
					break;
				}
				case 3:{
					m.put(IndexPathType.DB.getValue(),IndexPathType.DB.toString());
					m.put(IndexPathType.FILE.getValue(),IndexPathType.FILE.toString());
					m.put(IndexPathType.IMAGE.getValue(),IndexPathType.IMAGE.toString());
					break;
				}
				default:{
					break;
				}
			}
			CacheKit.put(CACHE_DICT_N+type, CACHE_DICT_N+type, m);
		}
		return m;
	}
	
	public static String getDictMapByKey(Integer type,String val){
		Map<String, String> m = getDictMap(type);
		return m.get(val);
	}
	
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	@Before(CacheInterceptor.class)
	@CacheName("dictList")
	public static List<Dict> getDictList(String type){
		Map<String, List<Dict>> dictMap = CacheKit.get(CACHE_DICT_MAP, CACHE_DICT_MAP);
		if(dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : getTheme()){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheKit.put(CACHE_DICT_MAP, CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList = Lists.newArrayList();
		if (dictList != null){
			dictList = dictMap.get(type);
		}
		return dictList;
	}
	
	
	public static List<Dict> getTheme(){
		List<Dict> dictList = new ArrayList<Dict>();
		Dict dict_default = new Dict();
		dict_default.setId("1");
		dict_default.setLabel("默认主题");
		dict_default.setType("theme");
		dict_default.setValue("default");
		dict_default.setSort(1);
		
		Dict dict_cerulean = new Dict();
		dict_cerulean.setId("2");
		dict_cerulean.setLabel("天蓝主题");
		dict_cerulean.setType("theme");
		dict_cerulean.setValue("cerulean");
		dict_cerulean.setSort(2);
		
		Dict dict_readable = new Dict();
		dict_readable.setId("3");
		dict_readable.setLabel("橙色主题");
		dict_readable.setType("theme");
		dict_readable.setValue("readable");
		dict_readable.setSort(3);
		
		Dict dict_united = new Dict();
		dict_united.setId("4");
		dict_united.setLabel("红色主题");
		dict_united.setType("theme");
		dict_united.setValue("united");
		dict_united.setSort(4);
		
		Dict dict_flat = new Dict();
		dict_flat.setId("5");
		dict_flat.setLabel("Flat主题");
		dict_flat.setType("theme");
		dict_flat.setValue("flat");
		dict_flat.setSort(5);
		
		dictList.add(dict_default);
		dictList.add(dict_cerulean);
		dictList.add(dict_readable);
		dictList.add(dict_united);
		dictList.add(dict_flat);
		return dictList;
		
	}
}
