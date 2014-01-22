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
package framework.retrieval.engine.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;

import framework.base.snoic.base.util.StringClass;
import framework.base.snoic.base.util.file.FileHelper;
import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.context.impl.RetrievalFactory;

/**
 * 全文检索上下文环境
 * 
 * @author 
 *
 */
public class RetrievalApplicationContext {
	private Log log=RetrievalUtil.getLog(this.getClass());
    private static FileHelper fileHelper=new FileHelper();
    
	/**
	 * 索引文件根目录
	 */
	private String baseIndexPath="";
	
	/**
	 * 全文检索上下文对象
	 */
	private IRetrievalFactory retrievalFactory=null;
	
    /**
     * 全文检索属性参数
     */
	private RetrievalProperties retrievalProperties=null;
	
	private DefaultRetrievalProperties  defaultRetrievalProperties=null;
	
	/**
	 * 索引操作及查询门面类
	 */
	private RFacade facade=null;

	/**
	 * 全文检索上下文环境
	 * @param baseIndexPath	索引文件根目录
	 */
	public RetrievalApplicationContext(String baseIndexPath){
		this.baseIndexPath=baseIndexPath;
		retrievalProperties=new RetrievalProperties();
		defaultRetrievalProperties=new DefaultRetrievalProperties();
		build(retrievalProperties);
	}
	
	public RetrievalApplicationContext(){
		retrievalProperties=new RetrievalProperties();
		defaultRetrievalProperties=new DefaultRetrievalProperties();
		this.baseIndexPath=defaultRetrievalProperties.getDefault_retrieval_workspace();
		build(retrievalProperties);
	}


	/**
	 * 全文检索上下文环境
	 * @param properties		属性配置文件Properties对象
	 * @param baseIndexPath		索引文件根目录
	 */
	public RetrievalApplicationContext(Properties properties,String baseIndexPath){
		this.baseIndexPath=baseIndexPath;
		retrievalProperties=new RetrievalProperties(properties);
		defaultRetrievalProperties=new DefaultRetrievalProperties();
		build(retrievalProperties);
	}
	
	/**
	 * 全文检索上下文环境
	 * @param propertiesFileName	属性配置文件名,文件必须存放在classpath下
	 * @param baseIndexPath			索引文件根目录
	 */
	public RetrievalApplicationContext(String propertiesFileName,String baseIndexPath){
		this.baseIndexPath=baseIndexPath;
		
		InputStream inputStream=this.getClass().getResourceAsStream("/"+propertiesFileName);
		if(inputStream==null){
			RetrievalUtil.debugLog(log,"未发现全文检索属性配置文件:"+propertiesFileName+"，使用默认配置");
			return;
		}
		
		RetrievalUtil.debugLog(log,"发现全文检索属性配置文件:"+propertiesFileName+"，载入配置");
		Properties properties=new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RetrievalLoadException(e);
		}

		retrievalProperties=new RetrievalProperties(properties);
		defaultRetrievalProperties=new DefaultRetrievalProperties();
		build(retrievalProperties);
		
	}

	/**
	 * 全文检索上下文环境
	 * @param retrievalProperties	全文检索属性对象
	 * @param baseIndexPath			索引文件根目录
	 */
	public RetrievalApplicationContext(RetrievalProperties retrievalProperties,String baseIndexPath){
		this.baseIndexPath=baseIndexPath;
		this.retrievalProperties=retrievalProperties;
		build(retrievalProperties);
		defaultRetrievalProperties=new DefaultRetrievalProperties();
	}

	/**
	 * 初始化全文检索运行环境
	 * @param retrievalProperties
	 */
	private void build(RetrievalProperties retrievalProperties){
		baseIndexPath=StringClass.getFormatPath(StringClass.getString(baseIndexPath));
		fileHelper.createDir(baseIndexPath);
		
		retrievalProperties.getLuceneProperties().setIndexBasePath(baseIndexPath);
		retrievalFactory=new RetrievalFactory(retrievalProperties);
		facade=new RFacade(this);
		
		RetrievalUtil.debugLog(log,retrievalProperties);
	}
	
	/**
	 * 获取全文索引绝对路径
	 * @return
	 */
	public String getBaseIndexpath() {
		return retrievalProperties.getLuceneProperties().getIndexBasePath();
	}
	
	/**
	 * 获取全文检索属性对象
	 * 
	 * @return
	 */
	public RetrievalProperties getRetrievalProperties() {
		return retrievalProperties;
	}

	/**
	 * 获取全文检索上下文对象
	 * @return
	 */
	public IRetrievalFactory getRetrievalFactory() {
		return retrievalFactory;
	}

	/**
	 * 获取索引操作及查询门面类
	 * @return
	 */
	public RFacade getFacade() {
		return facade;
	}

	public DefaultRetrievalProperties getDefaultRetrievalProperties() {
		return defaultRetrievalProperties;
	}

	public void setDefaultRetrievalProperties(
			DefaultRetrievalProperties defaultRetrievalProperties) {
		this.defaultRetrievalProperties = defaultRetrievalProperties;
	}
	
}
