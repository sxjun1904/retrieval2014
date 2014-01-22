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
package framework.base.snoic.base.util.jaxb;

import java.io.InputStream;

/**
 *  解组Xml
 * @author 
 *  
 */

public interface IXmlUnmarshal {
	
	public static final String XML_FILE_NAME="XML_FILE_NAME";
	
	public static final String XML_STRING="XML_STRING";
	
	public static final String XML_INPUTSTREAM="XML_INPUTSTREAM";
	
	
	/**
	 * 设置DominoObject所在的包名
	 * @param packageName
	 */
	public void setPackageName(String packageName);
	
	/**
	 * 获取DominoObject所在的包名
	 * @return String
	 */
	public String getPackageName();
	
	/**
	 * 设置XML文件名称
	 *            可以是xml配置文件名或者是xml的绝对路径，
	 *            如果只是xml的文件名，这个xml文件必须是处在classpath或者configHome下面.
	 */
	public void setXmlFileName(String xmlFileName);
	
	/**
	 * 获取XML文件名称
	 * @return
	 */
	public String getXmlFileName();

	/**
	 * 获取InputStream 格式的XML
	 * @return
	 */
	public InputStream getXmlInputStream();

	/**
	 * 设置InputStream 格式的XML
	 * @param xmlInputStream
	 */
	public void setXmlInputStream(InputStream xmlInputStream);
	
	/**
	 * 获取InputStream 格式的XML
	 * @return
	 */
	public String getXmlString();

	/**
	 * 设置String 格式的XML
	 * @param xmlString
	 */
	public void setXmlString(String xmlString);
	
	/**
	 * 解组xml文件，并获取java对象，每次都重新解组
	 * @return Object
	 * @throws Exception
	 */
	public Object unmarshal() throws Exception;

	/**
	 * 解组xml文件，并获取java对象，每次都重新解组
	 * @return Object
	 * @throws Exception
	 */
	public Object unmarshal(ClassLoader classloader) throws Exception;
}