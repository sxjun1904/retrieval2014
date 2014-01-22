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
package framework.retrieval.helper;

import java.io.Serializable;

/**
 * RetrievalFilePage
 * @author 
 *
 */
public class RetrievalFilePage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1704416670020783675L;

	private String fileId="";
	private String fileName="";
	private String fileContent="";
	private String fileRelativePath="";
	private String modfiyTime="";
	private String tableName="";
	private String recordId="";
	
	public RetrievalFilePage(){
		
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getFileRelativePath() {
		return fileRelativePath;
	}

	public void setFileRelativePath(String fileRelativePath) {
		this.fileRelativePath = fileRelativePath;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getModfiyTime() {
		return modfiyTime;
	}

	public void setModfiyTime(String modfiyTime) {
		this.modfiyTime = modfiyTime;
	}
	
	
}
