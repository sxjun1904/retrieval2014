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
package framework.retrieval.engine.index.create;

import java.util.List;

import org.apache.lucene.index.IndexWriter;

import framework.retrieval.engine.index.doc.internal.RDocument;

/**
 * 索引记录操作接口
 * @author 
 *
 */
public interface IRIndexOperator {
	
	/**
	 * 将文档写入索引，并返回索引唯一ID
	 * @param document
	 * @return
	 */
	public String addIndex(RDocument document);
	
	/**
	 * 将文档写入索引，并返回索引唯一ID
	 * @param documents
	 * @return
	 */
	public List<String> addIndex(List<RDocument> documents);
	
	public List<String> addIndex(List<RDocument> documents, IndexWriter indexWriter);
	
	/**
	 * 将文档写入索引，并返回索引唯一ID
	 * @param documents
	 * @return
	 */
	public List<String> addFileIndex(List<RDocument> documents);
	
	/**
	 * 将文档从索引中删除
	 * @param documntId
	 */
	public void deleteIndex(String indexPathType,String documntId);
	
	/**
	 * 将文档从数据库索引中删除
	 * @param tableName
	 * @param recordIds
	 */
	public void deleteDatabaseIndex(String indexPathType,String tableName,List<String> recordIds);
	
	/**
	 * 将文档从索引中删除
	 * @param documntIds
	 */
	public void deleteIndex(String indexPathType,List<String> documntIds);
	
	/**
	 * 更新索引中的文档
	 * @param document
	 */
	public void updateIndex(RDocument document);
	
}
