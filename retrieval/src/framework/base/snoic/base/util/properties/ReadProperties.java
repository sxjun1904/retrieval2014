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
package framework.base.snoic.base.util.properties;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import framework.base.snoic.base.util.StringClass;
import framework.base.snoic.base.util.file.FileHelper;

/**
 * 读取properties文件操作
 * @author 
 *
 */
public class ReadProperties {
	private Object propertiesObject=null;
	private Properties properties=null;
	private FileHelper snoicsFile=new FileHelper();
	private InputStream inputStream=null;

	public ReadProperties() {
		
	}
	
	/**
	 * 获取Properties Object
	 * @return Returns the propertiesObject.
	 */
	public Object getPropertiesObject() {
		return propertiesObject;
	}

	/**
	 * 设置Properties Object
	 * @param propertiesObject The propertiesObject to set.
	 */
	public void setPropertiesObject(Object propertiesObject) {
		this.propertiesObject = propertiesObject;
	}
	
	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	/**
	 * 初始化
	 *
	 */
	public void parse() {
		if(propertiesObject==null) {
			return;
		}
		properties=new Properties();
		if(propertiesObject instanceof String) {
			String filename=(String)propertiesObject;
			inputStream=snoicsFile.fileToInputStream(filename);
			try {
				properties.load(inputStream);
			}catch(Exception e) {
				e.printStackTrace();
				close();
			}
		}else if(propertiesObject instanceof Properties) {
			this.properties=(Properties)propertiesObject;
		}else if(propertiesObject instanceof InputStream) {
			inputStream=(InputStream)propertiesObject;
			try {
				properties.load(inputStream);
			}catch(Exception e) {
				e.printStackTrace();
				close();
			}
		}
	}
	
	/**
	 * 获取属性值
	 * @param key
	 * @return String
	 */
	public String readValue(String key) {
		String value=null;
		value=properties.getProperty(key);
		return value;
	}
	
	/**
	 * 获取属性值
	 * @param key
	 * @param defaultKey
	 * @return String
	 */
	public String readValue(String key,String defaultKey) {
		String value=null;
		value=properties.getProperty(key,defaultKey);
		return value;
	}
	
	/**
	 * 获取属性值Map
	 * @return Map
	 */
	public Map getKeyValueMap(){
		Map keyValueMap=new HashMap();
		Enumeration enumeration=properties.propertyNames();
		if(enumeration!=null){
			while(enumeration.hasMoreElements()){
				String parameterName=StringClass.getString((String)enumeration.nextElement());
				String parameterValue=readValue(parameterName);
				keyValueMap.put(parameterName, parameterValue);
			}
		}
		return keyValueMap;
	}
	
	/**
	 * 关闭资源
	 *
	 */
	public void close() {
		if(properties!=null) {
			properties.clear();
		}
		if(inputStream!=null) {
			try {
				inputStream.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) {
		ReadProperties readProperties=new ReadProperties();
		String filename="D:\\jboss-4.0.3SP1-fjgov\\server\\default\\deploy\\fjgov.ear\\fjgovNetyouCMS\\src\\fjgovweb.properties";
		readProperties.setPropertiesObject(filename);
		readProperties.parse();
		System.out.println(readProperties.readValue("fjgov"));
	}
}
