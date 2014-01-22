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

import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import framework.base.snoic.base.util.UtilTool;
import framework.base.snoic.base.util.file.FileHelper;

/**
 * 写入properties操作
 * @author 
 *
 */
public class WriteProperties {
	private Object propertiesObject=null;
	private Properties properties=null;
	private FileHelper snoicsFile=new FileHelper();
	private OutputStream outputStream=null;
	private ReadProperties readProperties=new ReadProperties();
	
	/**
	 * 获取propertiesObject
	 * @return Returns the propertiesObject.
	 */
	public Object getPropertiesObject() {
		return propertiesObject;
	}
	
	/**
	 * 设置propertiesObject
	 * @param propertiesObject The propertiesObject to set.
	 */
	public void setPropertiesObject(Object propertiesObject) {
		this.propertiesObject = propertiesObject;
	}
	
	public WriteProperties() {
		
	}
	
	/**
	 * 初始化
	 *
	 */
	public void parse() {
		if(propertiesObject==null) {
			return;
		}
		readProperties.setPropertiesObject(propertiesObject);
		readProperties.parse();
		Map map=readProperties.getKeyValueMap();
		
		readProperties.close();
		
		properties=new Properties();
		if(propertiesObject instanceof String) {
			String filename=(String)propertiesObject;
		    this.outputStream=snoicsFile.fileToOutputStream(filename);
		}else if(propertiesObject instanceof OutputStream) {
			this.outputStream=(OutputStream)propertiesObject;
		}
		if(map!=null){
			Object[][] objects=UtilTool.getMapKeyValue(map);
			if(objects!=null && objects.length>0){
				int length=objects.length;
				for(int i=0;i<length;i++){
					String key=(String)objects[i][0];
					String value=(String)objects[i][1];
					setValue(key,value);
				}
			}
		}
	}
	
	/**
	 * 设置属性值
	 * @param key
	 * @param value
	 */
	public void setValue(String key,String value) {
		properties.setProperty(key,value);
	}
	
	/**
	 * 写入属性文件
	 * @param head
	 */
	public void store(String head) {
		try {
			properties.store(outputStream,head);
		}catch(Exception e) {
			e.printStackTrace();
			close();
		}
	}
	
	/**
	 * 关闭资源
	 *
	 */
	public void close() {
		if(properties!=null) {
			properties.clear();
		}
		if(outputStream!=null) {
			try {
				outputStream.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) {
		String filename="D:\\a.properties";
		WriteProperties writeProperties=new WriteProperties();
		writeProperties.setPropertiesObject(filename);
		writeProperties.parse();
		writeProperties.setValue("fjgov","圣诞节反抗了士大夫但是");
		writeProperties.store("");
		writeProperties.close();
	}
}

