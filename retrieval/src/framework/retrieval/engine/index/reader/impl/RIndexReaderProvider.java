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
package framework.retrieval.engine.index.reader.impl;

import org.apache.lucene.store.Directory;

import framework.retrieval.engine.context.LuceneProperties;
import framework.retrieval.engine.index.RetrievalIndexLock;
import framework.retrieval.engine.index.create.RetrievalCreateIndexException;
import framework.retrieval.engine.index.directory.RetrievalDirectoryProvider;
import framework.retrieval.engine.pool.proxy.IndexReaderProxy;

/**
 * IndexReader对象提供者
 * @author 
 *
 */
public class RIndexReaderProvider {
	
	public RIndexReaderProvider(){
		
	}
	
	/**
	 * 创建RIndexReaderWrap对象
	 * @param luceneProperties
	 * @param indexBasePath
	 * @param indexPathType
	 * @return
	 */
	public IndexReaderProxy createIndexReader(LuceneProperties luceneProperties,String indexBasePath,String indexPathType){
		if(indexPathType==null){
			throw new RetrievalCreateIndexException("indexPathType 不允许为 NULL !!!");
		}
		RetrievalIndexLock.getInstance().lock(indexPathType);

		Directory directory=RetrievalDirectoryProvider.getDirectory(indexBasePath,indexPathType.toUpperCase());
		
		IndexReaderProxy indexReaderProxy=null;
		
		try{
			indexReaderProxy=luceneProperties.getIndexReaderPool().checkOut(indexPathType);
			if(indexReaderProxy==null){
				indexReaderProxy=new IndexReaderProxy(indexPathType,directory);
				luceneProperties.getIndexReaderPool().checkIn(indexPathType, indexReaderProxy);
				indexReaderProxy=luceneProperties.getIndexReaderPool().checkOut(indexPathType);
			}
		}finally{
			RetrievalIndexLock.getInstance().unlock(indexPathType);
		}
		return indexReaderProxy;
	}
}
