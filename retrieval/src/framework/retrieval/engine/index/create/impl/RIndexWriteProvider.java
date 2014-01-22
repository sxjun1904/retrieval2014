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
package framework.retrieval.engine.index.create.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import framework.retrieval.engine.analyzer.IRAnalyzerFactory;
import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.context.LuceneProperties;
import framework.retrieval.engine.index.RetrievalIndexLock;
import framework.retrieval.engine.index.create.RetrievalCreateIndexException;
import framework.retrieval.engine.index.directory.RetrievalDirectoryProvider;
import framework.retrieval.engine.pool.proxy.IndexWriterProxy;

/**
 * 索引创建对象提供者
 * @author 
 *
 */
public class RIndexWriteProvider {	
	private Log log=RetrievalUtil.getLog(this.getClass());

	private IRAnalyzerFactory analyzerFactory=null;
	private LuceneProperties luceneProperties=null;
	
	public RIndexWriteProvider(IRAnalyzerFactory analyzerFactory,LuceneProperties luceneProperties){
		this.analyzerFactory=analyzerFactory;
		this.luceneProperties=luceneProperties;
	}
	
	/**
	 * 生成内存RIndexWriterWrap对象
	 * @return
	 */
	public RIndexWriterWrap createRamIndexWriter(){

		RIndexWriterWrap indexWriterWrap=new RIndexWriterWrap();
		
		RAMDirectory ramDir = new RAMDirectory(); 
		
		IndexWriter ramWriter = null;
		try{
			ramWriter=new IndexWriter(ramDir, analyzerFactory.createIndexAnalyzer(), true,MaxFieldLength.UNLIMITED); 
		}catch(Exception e){
			ramDir.close();
			throw new RetrievalCreateIndexException(e);
		}
		
		indexWriterWrap.setDirectory(ramDir);
		indexWriterWrap.setIndexWriter(ramWriter);
		
		return indexWriterWrap;
	}
	
	/**
	 * 获取新增索引
	 * @return
	 */
	public IndexWriter createNewIndexWriter(String indexPathType){
		if(indexPathType==null){
			throw new RetrievalCreateIndexException("indexPathType 不允许为 NULL !!!");
		}

		RetrievalIndexLock.getInstance().lock(indexPathType);
		try{
			IndexWriter indexWriter=null;
			try {
				indexWriter=new IndexWriter(RetrievalDirectoryProvider.getDirectory(luceneProperties.getIndexBasePath(),indexPathType), analyzerFactory.createIndexAnalyzer(), true,MaxFieldLength.UNLIMITED);
				if(luceneProperties.getPropertyValueLuceneMergerFactor()>0){
					indexWriter.setMergeFactor(luceneProperties.getPropertyValueLuceneMergerFactor());
				}
				if(luceneProperties.getPropertyValueLuceneMaxMergeDocs()>0){
					indexWriter.setMaxMergeDocs(luceneProperties.getPropertyValueLuceneMaxMergeDocs());
				}
				if(luceneProperties.getPropertyValueLuceneMaxBufferedDocs()>0){
					indexWriter.setMaxBufferedDocs(luceneProperties.getPropertyValueLuceneMaxBufferedDocs());
				}
				if(luceneProperties.getPropertyValueLuceneRamBufferSizeMB()>0){
					indexWriter.setRAMBufferSizeMB(luceneProperties.getPropertyValueLuceneRamBufferSizeMB());
				}
				if(luceneProperties.getPropertyValueLuceneMaxFieldLength()>0){
					indexWriter.setMaxFieldLength(luceneProperties.getPropertyValueLuceneMaxFieldLength());
				}
			} catch (Exception e) {
				throw new RetrievalCreateIndexException(e);
			}
			return indexWriter;
		}finally{
			RetrievalIndexLock.getInstance().unlock(indexPathType);
		}
		
	}
	
	/**
	 * 获取增量索引
	 * @return
	 */
	public IndexWriter createNormalIndexWriter(String indexPathType){
		if(indexPathType==null){
			throw new RetrievalCreateIndexException("indexPathType 不允许为 NULL !!!");
		}

		RetrievalIndexLock.getInstance().lock(indexPathType);
		try{
			
			IndexWriter indexWriter=null;
			
			indexWriter=luceneProperties.getIndexWriterPool().checkOut(indexPathType);
			
			if(indexWriter!=null){
				return indexWriter;
			}else{
				
				IndexWriterProxy indexWriterProxy=new IndexWriterProxy(indexPathType,
						RetrievalDirectoryProvider.getDirectory(
								luceneProperties.getIndexBasePath(),
								indexPathType), 
						analyzerFactory.createIndexAnalyzer(),
						luceneProperties);
				
				luceneProperties.getIndexWriterPool().checkIn(indexPathType,indexWriterProxy);
				
				indexWriter=luceneProperties.getIndexWriterPool().checkOut(indexPathType);
				
				return indexWriter;
				
			}
		}finally{
			RetrievalIndexLock.getInstance().unlock(indexPathType);
		}

	}
	
	/**
	 * 解除目录锁
	 * @param indexPathType
	 */
	public void unlockDir(String indexPathType){
		Directory directory=RetrievalDirectoryProvider.getDirectory(luceneProperties.getIndexBasePath(),indexPathType.toUpperCase());
		try{
			if(IndexWriter.isLocked(directory)){
				IndexWriter.unlock(directory);
			}
		}catch(Exception e){
			RetrievalUtil.errorLog(log, e);
		}
		try {
			directory.close();
		} catch (IOException e) {
			RetrievalUtil.errorLog(log, e);
		}
	}	
	
	/**
	 * 判断索引是否存在
	 */
	public boolean isExists(String indexPathType) {
		boolean isExists=false;
		
		try {
			isExists=IndexReader.indexExists(RetrievalDirectoryProvider.getDirectory(luceneProperties.getIndexBasePath(),indexPathType));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return isExists;
	}
	
	public void removeIndexWriter(String indexPathType){
		luceneProperties.getIndexWriterPool().remove(indexPathType);
	}
	
	
}
