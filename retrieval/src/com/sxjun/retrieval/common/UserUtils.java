/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sxjun.retrieval.common;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.sxjun.system.pojo.Menu;

/**
 * 用户工具类
 * @author sxjun
 * @version 2013-5-29
 */
public class UserUtils{
	
	public static final String CACHE_MENU_LIST = "menuList";
	
	@Before(CacheInterceptor.class)
	@CacheName("menuList")
	public static List<Menu> getMenuList(){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = CacheKit.get(CACHE_MENU_LIST, CACHE_MENU_LIST);;
		if (menuList == null){
			menuList = getMyMenu();
			CacheKit.put(CACHE_MENU_LIST, CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}
	
	public static List<Menu> getMyMenu(){
		List<Menu> l = new ArrayList<Menu>();
		
		
		Menu menu1 = new Menu();
		menu1.setId("2");
		menu1.setIcon("user");
		menu1.setIsShow("1");
		menu1.setName("索引设置");
		menu1.setParentid("1");
		//menu1.setHref("/database/list");
		menu1.setSort(1);
		
		Menu menu0 = new Menu();
		menu0.setId("10");
		menu0.setIcon("user");
		menu0.setIsShow("1");
		menu0.setName("其他设置");
		menu0.setParentid("1");
		//menu1.setHref("/database/list");
		menu0.setSort(2);
		
//////////////////////////////////////////////////
		
		Menu menu2 = new Menu();
		menu2.setId("3");
		menu2.setIcon("user");
		menu2.setIsShow("1");
		menu2.setName("检索设置");
		menu2.setParentid("2");
		//menu2.setHref("/database/list");
		menu2.setSort(1);
		
		Menu menu14 = new Menu();
		menu14.setId("15");
		menu14.setIcon("user");
		menu14.setIsShow("1");
		menu14.setName("高级设置");
		menu14.setParentid("2");
		//menu14.setHref("/database/list");
		menu14.setSort(23);
		
		Menu menu4 = new Menu();
		menu4.setId("5");
		menu4.setIcon("user");
		menu4.setIsShow("1");
		menu4.setName("爬虫设置");
		menu4.setParentid("2");
		//menu4.setHref("/database/list");
		menu4.setSort(23);
		
		Menu menu10 = new Menu();
		menu10.setId("11");
		menu10.setIcon("user");
		menu10.setIsShow("1");
		menu10.setName("信息设置");
		menu10.setParentid("10");
		menu10.setSort(2);
		
////////////////////////////////////////////////
		Menu menu8 = new Menu();
		menu8.setId("9");
		menu8.setIcon("certificate");
		menu8.setIsShow("1");
		menu8.setName("初始字段");
		menu8.setParentid("3");
		menu8.setHref("/initField/list");
		menu8.setSort(1);
		
		Menu menu9 = new Menu();
		menu9.setId("10");
		menu9.setIcon("random");
		menu9.setIsShow("1");
		menu9.setName("索引分类");
		menu9.setParentid("3");
		menu9.setHref("/indexCategory/list");
		menu9.setSort(27);
		
		Menu menu3 = new Menu();
		menu3.setId("4");
		menu3.setIcon("user");
		menu3.setIsShow("1");
		menu3.setName("数据源设置");
		menu3.setParentid("3");
		menu3.setHref("/database/list");
		menu3.setSort(2);
		
		Menu menu5 = new Menu();
		menu5.setId("5");
		menu5.setIcon("briefcase");
		menu5.setIsShow("1");
		menu5.setName("索引设置");
		menu5.setParentid("3");
		menu5.setHref("/rDatabaseIndex/list");
		menu5.setSort(23);
		
		Menu menu16 = new Menu();
		menu16.setId("17");
		menu16.setIcon("random");
		menu16.setIsShow("1");
		menu16.setName("索引工具");
		menu16.setParentid("15");
		menu16.setHref("/indexManager/list");
		menu16.setSort(25);
		
		Menu menu15 = new Menu();
		menu15.setId("16");
		menu15.setIcon("briefcase");
		menu15.setIsShow("1");
		menu15.setName("分词工具");
		menu15.setParentid("15");
		menu15.setHref("/iKWords/list");
		menu15.setSort(25);
		
		Menu menu13 = new Menu();
		menu13.setId("14");
		menu13.setIcon("align-justify");
		menu13.setIsShow("1");
		menu13.setName("搜索过滤");
		menu13.setParentid("15");
		menu13.setHref("/keyWordFilter/list");
		menu13.setSort(25);
		
		Menu menu12 = new Menu();
		menu12.setId("13");
		menu12.setIcon("th");
		menu12.setIsShow("1");
		menu12.setName("数据监控");
		menu12.setParentid("15");
		menu12.setHref("/monitorView/listTrigger");
		menu12.setSort(23);
		
		Menu menu6 = new Menu();
		menu6.setId("7");
		menu6.setIcon("th");
		menu6.setIsShow("1");
		menu6.setName("字段映射");
		menu6.setParentid("5");
		menu6.setHref("/filedMapper/list");
		menu6.setSort(24);
		
		Menu menu7 = new Menu();
		menu7.setId("8");
		menu7.setIcon("align-justify");
		menu7.setIsShow("1");
		menu7.setName("字段处理");
		menu7.setParentid("5");
		menu7.setHref("/filedSpecialMapper/list");
		menu7.setSort(25);
		
		Menu menu11 = new Menu();
		menu11.setId("12");
		menu11.setIcon("user");
		menu11.setIsShow("1");
		menu11.setName("用户管理");
		menu11.setParentid("11");
		menu11.setHref("/user/list");
		menu11.setSort(2);
		
		
		l.add(menu1);
		l.add(menu2);
		l.add(menu8);
		l.add(menu9);
		l.add(menu3);
		l.add(menu14);
		l.add(menu4);
		l.add(menu5);
		l.add(menu16);
		l.add(menu15);
		l.add(menu13);
		l.add(menu12);
		l.add(menu6);
		l.add(menu7);
		l.add(menu0);
		l.add(menu10);
		l.add(menu11);
		return l;
	}
}
