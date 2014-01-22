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
package framework.retrieval.engine.index.doc.database;

import java.io.Serializable;

import framework.retrieval.engine.index.doc.AbstractIndexDocument;
import framework.retrieval.engine.index.doc.database.internal.RDatabaseDocument;

/**
 * 当条数据库记录Document
 * @author 
 *
 */
public class DatabaseIndexDocument extends AbstractIndexDocument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4737929916612807540L;
	
	private RDatabaseDocument rDocument=null;
	
	public DatabaseIndexDocument(boolean fullContentFlag){
		rDocument=new RDatabaseDocument(fullContentFlag);
	}
	
	/**
	 * 设置数据库表名和记录ID
	 * @param tableName
	 */
	public void setTableNameAndRecordId(String tableName,String recordId){
		rDocument.setTableNameAndRecordId(tableName,recordId);
	}
	
	/**
	 * 获取数据库表名
	 * @return
	 */
	public String getTableName(){
		return rDocument.getTableName();
	}
	
	/**
	 * 获取数据库主键ID
	 * @return
	 */
	public String getRecordId(){
		return rDocument.getRecordId();
	}
	
	/**
	 * 设置关键字段名
	 * @param keyfieldName
	 */
	public void setKeyfieldName(String keyfieldName){
		rDocument.setKeyfieldName(keyfieldName);
	}
	
	/**
	 * 获取关键字段名
	 * @return
	 */
	public String getKeyfieldName(){
		return rDocument.getKeyfieldName();
	}

	public RDatabaseDocument getRDocument() {
		return rDocument;
	}
	
	/**
	 * 设置标题
	 */
	public void setDefaultTitle(String title){
		rDocument.setDefaultTitle(title);
	}
	/**
	 * 返回标题
	 * @return
	 */
	public String getDefaultTitle(){
		return rDocument.getDefaultTitle();
	}
	
	/**
	 * 设置摘要
	 */
	public void setDefaultResume(String resume){
		rDocument.setDefaultResume(resume);
	}
	
	/**
	 * 返回摘要
	 * @return
	 */
	public String getDefaultResume(){
		return rDocument.getDefaultResume();
	}


}
