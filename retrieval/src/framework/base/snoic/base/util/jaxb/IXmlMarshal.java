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

import java.io.OutputStream;
import java.util.List;

/**
 * 编组Xml
 * @author 
 *
 */
public interface IXmlMarshal {
	
	public static final String XML_FILE_NAME="XML_FILE_NAME";
	
	public static final String XML_OUTPUTSTREAM="XML_OUTPUTSTREAM";
	
	/**
	 * 获取DOCTYPE的PublicId
	 * @return String
	 */
	public String getPublicId();

	/**
	 * 设置DOCTYPE的PublicId
	 * @param publicId
	 */
	public void setPublicId(String publicId);

	/**
	 * 获取DOCTYPE的SystemId
	 * @return String
	 */
	public String getSystemId();

	/**
	 * 设置DOCTYPE的SystemId
	 * @param systemId
	 */
	public void setSystemId(String systemId);
	
	/**
	 * 获取需要设置为CDATA类型的节点名称
	 * @return List
	 */
	public List getCdataNodeList();

	/**
	 * 设置需要设置为CDATA类型的节点名称
	 * @param cdataNodeList
	 */
	public void setCdataNodeList(List cdataNodeList);
	
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

	public OutputStream getXmlOutputStream();

	public void setXmlOutputStream(OutputStream xmlOutputStream);
	
	/**
	 * 设置将被编组成XML的object对象
	 * @param object
	 */
	public void setObject(Object object);
	
	/**
	 * 获取设置将被编组成XML的Object对象
	 * @return Object
	 */
	public Object getObject();
	
	/**
	 * 编组xml文件,如果编组的XML是以字符串的形式存储的，则在编组完之后可以获取到这个XML字符串
	 * @return Workflow
	 */
	public String marshal(); 
}

