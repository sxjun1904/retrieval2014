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
package framework.retrieval.engine.facade;

import framework.retrieval.engine.query.RQuery;
import framework.retrieval.engine.query.result.DatabaseQueryResult;
import framework.retrieval.engine.query.result.FileQueryResult;


/**
 * 索引查询接口
 * @author 
 *
 */
public interface IRQueryFacade {
	
	/**
	 * 创建全文搜索对象
	 * @param indexPathType
	 * @return
	 */
	public RQuery createRQuery(Object indexPathType);
	

	/**
	 * 创建全文搜索对象
	 * @param indexPathTypes
	 * @return
	 */
	public RQuery createRQuery(String[] indexPathTypes);
	

	/**
	 * 获取数据库记录在索引中的内容
	 * @param indexPathType
	 * @param tableName
	 * @param recordId
	 * @return
	 */
	public DatabaseQueryResult queryDatabaseDocument(String indexPathType,String tableName,String recordId);
	

	/**
	 * 获取数据库记录在索引中的唯一ID
	 * @param indexPathType
	 * @param tableName
	 * @param recordId
	 * @return
	 */
	public String queryDatabaseDocumentIndexId(String indexPathType,String tableName,String recordId);
	

	/**
	 * 获取文件在索引中的唯一ID
	 * @param indexPathType
	 * @param fileId
	 * @return
	 */
	public String queryFileDocumentIndexIdByFileId(String indexPathType,String fileId);
	

	/**
	 * 获取文件在索引中的唯一ID
	 * @param indexPathType
	 * @param fileRelativePath
	 * @return
	 */
	public String queryFileDocumentIndexIdRelativePath(String indexPathType,String fileRelativePath);
	

	/**
	 * 获取文件在索引中的内容
	 * @param indexPathType
	 * @param fileId
	 * @return
	 */
	public FileQueryResult queryFileDocumentByFileId(String indexPathType,String fileId);
	

	/**
	 * 获取文件在索引中的内容
	 * @param indexPathType
	 * @param fileRelativePath
	 * @return
	 */
	public FileQueryResult queryFileDocumentByRelativePath(String indexPathType,String fileRelativePath);
	
}
