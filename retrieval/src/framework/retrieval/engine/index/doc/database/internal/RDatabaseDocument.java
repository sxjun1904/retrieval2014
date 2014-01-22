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
package framework.retrieval.engine.index.doc.database.internal;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.index.doc.internal.RDefaultDocument;

/**
 * 数据库Document
 * @author 
 *
 */
public class RDatabaseDocument extends RDefaultDocument{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8477150125822510619L;
	
	public RDatabaseDocument(boolean fullContentFlag){
		super(fullContentFlag);
		//设置索引来源为数据库类型
		setSourceIndexType(RetrievalType.RIndexSourceType.D);
	}
	
	/**
	 * 设置数据库表名和记录ID
	 * @param tableName
	 * @param recordId
	 */
	public void setTableNameAndRecordId(String tableName,String recordId){
		this.setUnTokenizedPropertyField(RetrievalType.RDatabaseDocItemType._DT, tableName.toUpperCase());
		this.setUnTokenizedPropertyField(RetrievalType.RDatabaseDocItemType._DID, recordId);
		this.setUnTokenizedPropertyField(RetrievalType.RDatabaseDocItemType._DTID, tableName.toUpperCase()+recordId);
	}
	
	/**
	 * 获取数据库表名
	 * @return
	 */
	public String getTableName(){
		return this.getField(RetrievalType.RDatabaseDocItemType._DT);
	}
	
	/**
	 * 设置关键字段名
	 * @param keyfieldName
	 */
	public void setKeyfieldName(String keyfieldName){
		this.setUnTokenizedPropertyField(RetrievalType.RDatabaseDocItemType._DK, keyfieldName.toUpperCase());
	}
	
	/**
	 * 获取关键字段名
	 * @return
	 */
	public String getKeyfieldName(){
		return this.getField(RetrievalType.RDatabaseDocItemType._DK);
	}
	
	/**
	 * 获取主键ID
	 * @return
	 */
	public String getRecordId(){
		return this.getField(RetrievalType.RDatabaseDocItemType._DID);
	}

	public String toString(){
		return this.getClass()+"["+getSourceIndexType()+"]"+"@"+this.getId()+" "+this.getTableName()+" "+this.getRecordId();
	}
	
	/**
	 * 设置标题
	 */
	public void setDefaultTitle(String title) {
		this.setContentField(RetrievalType.RDatabaseDefaultDocItemType._TITLE, title);
	}

	/**
	 * 返回标题
	 * @return
	 */
	public String getDefaultTitle() {
		return this.getField(RetrievalType.RDatabaseDefaultDocItemType._TITLE);
	}

	/**
	 * 设置摘要
	 */
	public void setDefaultResume(String resume) {
		this.setContentField(RetrievalType.RDatabaseDefaultDocItemType._RESUME, resume);
	}

	/**
	 * 返回摘要
	 * @return
	 */
	public String getDefaultResume() {
		return this.getField(RetrievalType.RDatabaseDefaultDocItemType._RESUME);
	}
}
