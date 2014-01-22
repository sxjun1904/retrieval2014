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

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

/**
 * 写入索引内容操作接口
 * @author 
 *
 */
public interface IRIndexWriter {

	/**
	 * 增加一个索引,先写入内存,然后在批量将索引写入文件
	 * @param documents
	 */
	public void addDocument(List<Document> documents);
	
	
	/**
	 * 增加一个索引,先写入内存,然后在批量将索引写入文件add by sxjun
	 * @param documents 
	 */
	public void addDocumentNotClose(List<Document> documents,IndexWriter _indexWriter);
	

	/**
	 * 增加一个索引,直接将索引写入文件
	 * @param documents
	 */
	public void addDocumentNowRamSupport(List<Document> documents);
	

	/**
	 * 写入一个索引
	 * @param document
	 */
	public void addDocument(Document document);
	
	/**
	 * 批量删除索引
	 * @param indexPathType
	 * @param terms
	 */
	public void deleteDocument(String indexPathType,List<Term> terms);
	
	/**
	 * 删除一个索引
	 * @param indexPathType
	 * @param term
	 */
	public void deleteDocument(String indexPathType,Term term);
	
}
