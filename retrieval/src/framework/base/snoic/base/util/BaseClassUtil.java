/**
 * Copyright 2010 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package framework.base.snoic.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import framework.base.snoic.base.util.regex.Regex;

/**
 * 类反射工具
 * @author 
 *
 */
public class BaseClassUtil {
	private static Regex regex=new Regex();
	
	private BaseClassUtil(){
		
	}	
	
	/**
	 * 通过Class名获取Class的对象实例
	 * @param className
	 * @return Object
	 */
	public static <T> T createObjectByClassName(String className) throws Exception{
		T object=null;
		className=StringClass.getString(className).trim();
			try{
				object=(T)Class.forName(className).newInstance();
			}catch(Exception e){
				throw e;
			}
		return object;
	}
	
	/**
	 * 判断是否是基本类型
	 * @param clz
	 * @return boolean
	 */
	public static boolean isWrapClass(Class clz) {   
	    try {   
	        return ((Class) clz.getField("TYPE").get(null)).isPrimitive();   
	    } catch (Exception e) {   
	        return false;   
	    }   
	}   
	
	/**
	 * 获取一个Class中的所有方法
	 * @param theClass
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List getClassMethod(Class theClass){
		List list=new ArrayList();
		Method[] methods=theClass.getMethods();
		if(methods==null){
			return list;
		}
		int length=methods.length;
		for(int i=0;i<length;i++){
			Method method=methods[i];
			list.add(method);
		}
		return list;
	}
	
	/**
	 * 获取一个Class中符合正则表达式的所有方法
	 * @param theClass
	 * @param regexString
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List getClassMethod(Class theClass,String regexString){
		List list=new ArrayList();
		Method[] methods=theClass.getMethods();
		if(methods==null){
			return list;
		}
		int length=methods.length;
		for(int i=0;i<length;i++){
			Method method=methods[i];
			String name=method.getName();
			if(regex.find(name, regexString)){
				list.add(method);
			}
		}
		return list;
	}
	
	/**
	 * 获取一个Class中的所有方法名称
	 * @param theClass
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List getClassMethodName(Class theClass){
		List list=new ArrayList();
		Method[] methods=theClass.getMethods();
		if(methods==null){
			return list;
		}
		int length=methods.length;
		for(int i=0;i<length;i++){
			Method method=methods[i];
			String name=method.getName();
			list.add(name);
		}
		return list;
	}
	
	/**
	 * 获取一个Class中符合正则表达式的所有方法名
	 * @param theClass
	 * @param regexString
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List getClassMethodName(Class theClass,String regexString){
		List list=new ArrayList();
		Method[] methods=theClass.getMethods();
		if(methods==null){
			return list;
		}
		int length=methods.length;
		for(int i=0;i<length;i++){
			Method method=methods[i];
			String name=method.getName();
			if(regex.find(name, regexString)){
				list.add(name);
			}
		}
		return list;
	}
	
	/**
	 * 获取Class中的所有字段
	 * @param theClass
	 * @param regexString
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List getClassFields(Class theClass,String regexString){
		List list=new ArrayList();
		Field[] fields=theClass.getDeclaredFields();
		
		if(fields==null){
			return list;
		}
		int length=fields.length;
		for(int i=0;i<length;i++){
			Field field=fields[i];
			String name=field.getName();
			if(regex.find(name, regexString)){
				list.add(field);
			}
		}
		return list;
	}
	
	/**
	 * 获取Class中的所有字段
	 * @param theClass
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List getClassFields(Class theClass){
		List list=new ArrayList();
		Field[] fields=theClass.getDeclaredFields();
		
		if(fields==null){
			return list;
		}
		int length=fields.length;
		for(int i=0;i<length;i++){
			Field field=fields[i];
			list.add(field);
		}
		return list;
	}
}