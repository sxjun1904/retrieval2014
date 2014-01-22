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
import java.util.Map;

import framework.retrieval.engine.index.all.IRIndexAll;
import framework.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;
import framework.retrieval.engine.index.doc.file.FileIndexDocument;

/**
 * 从数据库中对数据批量创建索引
 * @author 
 *
 */
public interface IRDatabaseIndexAll extends IRIndexAll{

	/**
	 * 获取下一页数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> next();

	/**
	 * 获取数据库批量索引对象
	 */
	public RDatabaseIndexAllItem getDatabaseIndexAllItem();
	
	/**
	 * 设置数据库批量索引对象
	 * @param databaseIndexAllItem
	 */
	public void setDatabaseIndexAllItem(RDatabaseIndexAllItem databaseIndexAllItem);
	
	/**
	 * 获取数据库记录相关文件
	 * @param recordId
	 * @return
	 */
	public List<FileIndexDocument> getFileIndexDocuments(String recordId);

}
