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
package framework.retrieval.engine.query.result;

import java.io.Serializable;
import java.util.Map;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.query.formatter.IHighlighterFactory;

/**
 * 文件类型索引查询结果
 * @author 
 *
 */
public class FileQueryResult extends QueryResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5999313009528821459L;
	
	public FileQueryResult(IHighlighterFactory highlighterFactory,
			Map<String, String> queryResultMap) {
		super(highlighterFactory,queryResultMap);
	}

	/**
	 * 获取文件ID
	 * @return
	 */
	public String getFileId(){
		return getResult(RetrievalType.RFileDocItemType._FID);
	}
	
	/**
	 * 获取文件名
	 * @return
	 */
	public String getFileName(){
		return getResult(RetrievalType.RFileDocItemType._FN);
	}
	
	/**
	 * 获取文件相对路径
	 * @return
	 */
	public String getFileRelativePath(){
		return getResult(RetrievalType.RFileDocItemType._FP);
	}
	
	/**
	 * 获取文件修改时间
	 * @return
	 */
	public String getFileModify(){
		return getResult(RetrievalType.RFileDocItemType._FM);
	}
	
	/**
	 * 获取文件内容
	 * @return
	 */
	public String getFileContent(){
		return getResult(RetrievalType.RFileDocItemType._FC);
	}
	
	/**
	 * 获取文件内容摘要
	 * @param resumeLength	摘要长度
	 * @return
	 */
	public String getFileContent(int resumeLength){
		return getHighlighterResult(RetrievalType.RFileDocItemType._FC,resumeLength);
	}
	
	/**
	 * 获取数据库表名
	 * @return
	 */
	public String getTableName(){
		return getResult(RetrievalType.RDatabaseDocItemType._DT);
	}
	
	/**
	 * 获取数据库记录ID
	 * @return
	 */
	public String getRecordId(){
		return getResult(RetrievalType.RDatabaseDocItemType._DID);
	}
	
	
}
