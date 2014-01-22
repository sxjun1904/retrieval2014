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
package framework.retrieval.engine.index.doc.file;

import java.io.File;
import java.io.Serializable;

import framework.retrieval.engine.index.doc.AbstractIndexDocument;
import framework.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * 当个文件Document
 * @author 
 *
 */
public class FileIndexDocument extends AbstractIndexDocument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4737929916612807540L;
	
	private RFileDocument rDocument=null;
	
	public FileIndexDocument(boolean fullContentFlag,String charsetName){
		rDocument=new RFileDocument(fullContentFlag);
		rDocument.setCharsetName(charsetName);
	}

	/**
	 * <p>
	 * 设置与该文件相关联的文章的数据库表名和主键ID
	 * 
	 * 		<br>如：知识库功能包括文章记录表和文件附件表
	 * 
	 * 		<br>	则tableName和recordId的值应该分别对应的是文章记录表的表名和文章记录表主键ID
	 * <p>
	 * @param tableName
	 * @param recordId
	 */
	public void setTableNameAndRecordId(String tableName,String recordId){
		rDocument.setTableNameAndRecordId(tableName,recordId);
	}
	
	/**
	 * 获取与该文件相关联的文章的数据库表名
	 * @return
	 */
	public String getTableName(){
		return rDocument.getTableName();
	}
	
	/**
	 * 获取与该文件相关联的文章的主键ID
	 * @return
	 */
	public String getRecordId(){
		return rDocument.getRecordId();
	}
	
	/**
	 * 设置文件ID
	 * @param fileId
	 */
	public void setFileId(String fileId){
		rDocument.setFileId(fileId);
	}
	
	/**
	 * 获取文件ID
	 * @return
	 */
	public String getFileId(){
		return rDocument.getFileId();
	}
	
	/**
	 * 设置文件根目录
	 * @param basePath
	 */
	public void setFileBasePath(String basePath){
		rDocument.setBasePath(basePath);
	}
	
	/**
	 * 获取文件根目录
	 * @return
	 */
	public String getFileBasePath(){
		return rDocument.getBasePath();
	}
	
	/**
	 * 获取文件
	 * @return
	 */
	public File getFile() {
		return rDocument.getFile();
	}

	/**
	 * 设置文件
	 * @param fullFileName
	 */
	public void setFile(File file) {
		rDocument.setFile(file);
	}

	/**
	 * 设置文件字符集
	 * @return
	 */
	public String getCharsetName() {
		return rDocument.getCharsetName();
	}

	public RFileDocument getRDocument() {
		return rDocument;
	}
	
	
}
