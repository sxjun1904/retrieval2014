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
package framework.retrieval.engine.index.doc.file.internal;

import java.io.File;

import framework.base.snoic.base.util.StringClass;
import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.index.doc.internal.RDefaultDocument;

/**
 * 文件类型Document
 * @author 
 *
 */
public class RFileDocument extends RDefaultDocument{
	/**
	 * 文件绝对路径
	 */
	private File file=null;
	
	private String basePath="";
	
	private String charsetName="";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RFileDocument(boolean fullContentFlag){
		super(fullContentFlag);
		//设置索引来源为文件类型
		setSourceIndexType(RetrievalType.RIndexSourceType.F);
	}

	/**
	 * 设置文件ID
	 * @param fileId
	 */
	public void setFileId(String fileId){
		setUnTokenizedPropertyField(RetrievalType.RFileDocItemType._FID,fileId);
	}
	
	/**
	 * 获取文件ID
	 * @return
	 */
	public String getFileId(){
		return getField(RetrievalType.RFileDocItemType._FID);
	}
	
	/**
	 * 设置文件名称
	 * @param fileName
	 */
	public void setFileName(String fileName){
		setPropertieField(RetrievalType.RFileDocItemType._FN,fileName);
	}
	
	/**
	 * 获取文件名称
	 * @return
	 */
	public String getFileName(){
		return getField(RetrievalType.RFileDocItemType._FN);
	}
	
	/**
	 * 设置文件相对路径
	 * @param filePath
	 */
	public void setFileRelativePath(String filePath){
		filePath=StringClass.getFormatPath(filePath);
		setUnTokenizedPropertyField(RetrievalType.RFileDocItemType._FP,filePath);
	}
	
	/**
	 * 获取文件路径
	 * @return
	 */
	public String getFileRelativePath(){
		return getField(RetrievalType.RFileDocItemType._FP);
	}
	
	/**
	 * 设置文件修改时间
	 * @param fileModify
	 */
	public void setFileModify(String fileModify){
		setUnTokenizedPropertyField(RetrievalType.RFileDocItemType._FM,fileModify);
	}
	
	/**
	 * 获取文件修改时间
	 * @return
	 */
	public String getFileModify(){
		return getField(RetrievalType.RFileDocItemType._FM);
	}
	
	/**
	 * 设置文件内容
	 * @param content
	 */
	public void setFileContent(String content){
		setContentField(RetrievalType.RFileDocItemType._FC, content);
	}
	
	/**
	 * 获取文件内容
	 * @return
	 */
	public String getFileContent(){
		return getField(RetrievalType.RFileDocItemType._FC);
	}
	
	/**
	 * 获取文件
	 * @return
	 */
	public File getFile() {
		return file;
	}

	/**
	 * 设置文件
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	/**
	 * 获取文件根目录
	 * @return
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * 设置文件根目录
	 * @param basePath
	 */
	public void setBasePath(String basePath) {
		this.basePath = StringClass.getFormatPath(basePath);
	}

	/**
	 * 设置数据库表名和记录ID
	 * @param tableName
	 * @param recordId
	 */
	public void setTableNameAndRecordId(String tableName,String recordId){
		this.setUnTokenizedPropertyField(RetrievalType.RDatabaseDocItemType._DT, tableName);
		this.setUnTokenizedPropertyField(RetrievalType.RDatabaseDocItemType._DID, recordId);
		this.setUnTokenizedPropertyField(RetrievalType.RDatabaseDocItemType._DTID, tableName.toUpperCase()+recordId);
	}
	
	/**
	 * 获取与该附件相关的数据库表名
	 * @return
	 */
	public String getTableName(){
		return this.getField(RetrievalType.RDatabaseDocItemType._DT);
	}
	
	/**
	 * 获取与该附件相关的数据库记录主键ID
	 * @return
	 */
	public String getRecordId(){
		return this.getField(RetrievalType.RDatabaseDocItemType._DID);
	}

	/**
	 * 获取文件字符集
	 * @return
	 */
	public String getCharsetName() {
		return charsetName;
	}

	/**
	 * 设置文件字符集
	 * @param charsetName
	 */
	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	public String toString(){
		return this.getClass()+"["+getSourceIndexType()+"]"+"@"+this.getId()+"@"+this.getFileRelativePath();
	}
}
