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
package framework.retrieval.engine.index.all.database;

import java.util.List;

import framework.retrieval.engine.index.doc.file.FileIndexDocument;


/**
 * 数据库记录索引相关文件索引信息
 * @author 
 *
 */
public interface IIndexAllDatabaseFileIndexOperator {
	
	/**
	 * 通过数据库主键获取相关文件信息
	 * @param recordId
	 * @return
	 */
	public List<FileIndexDocument> getFileIndexDocuments(String recordId);
	
}
