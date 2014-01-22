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
package framework.retrieval.engine.index.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.LockObtainFailedException;

import framework.retrieval.engine.analyzer.IRAnalyzerFactory;
import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.context.LuceneProperties;
import framework.retrieval.engine.index.IRIndexManager;
import framework.retrieval.engine.index.RetrievalIndexLock;
import framework.retrieval.engine.index.create.RetrievalCreateIndexException;
import framework.retrieval.engine.index.create.impl.RIndexWriteProvider;
import framework.retrieval.engine.index.directory.RetrievalDirectoryProvider;

/**
 * 
 * @author 
 *
 */
public class RIndexManager implements IRIndexManager {
	private Log log=RetrievalUtil.getLog(this.getClass());
	private RIndexWriteProvider indexWriteProvider=null;
	private IRAnalyzerFactory analyzerFactory=null;
	private LuceneProperties luceneProperties=null;
	private String indexPathType=null;
	
	public RIndexManager(IRAnalyzerFactory analyzerFactory,LuceneProperties luceneProperties,String indexPathType){
		this.analyzerFactory=analyzerFactory;
		this.indexPathType=indexPathType.toUpperCase();
		this.luceneProperties=luceneProperties;
		indexWriteProvider=new RIndexWriteProvider(analyzerFactory,luceneProperties);
	}
	
	/**
	 * 获取索引路径类型
	 * @return
	 */
	public String getIndexPathType() {
		return indexPathType;
	}

	/**
	 * 判断索引是否存在
	 */
	public boolean isExists() {
		String path = RetrievalUtil.getIndexPath(luceneProperties.getIndexBasePath(),indexPathType);
		File file = new File(path);

		String[] fileList = file.list();
		if (fileList != null && fileList.length > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建索引
	 */
	public void create() {
		RetrievalIndexLock.getInstance().lock(indexPathType);
		
		IndexWriter indexWriterNormal=null;
		
		try {
			indexWriterNormal=new IndexWriter(RetrievalDirectoryProvider.getDirectory(luceneProperties.getIndexBasePath(),indexPathType), analyzerFactory.createIndexAnalyzer(), false,MaxFieldLength.UNLIMITED);
		} catch (CorruptIndexException e) {
			throw new RetrievalCreateIndexException(e);
		} catch (LockObtainFailedException e) {
			indexWriteProvider.unlockDir(indexPathType);
		} catch (IOException e) {
			IndexWriter indexWriter=indexWriteProvider.createNewIndexWriter(indexPathType);
			try {
				indexWriter.commit();
			} catch (Exception e1) {
				
			}
			try {
				indexWriter.close();
			} catch (Exception e1) {
				
			}
		}finally{
			if(indexWriterNormal!=null){
				try {
					indexWriterNormal.commit();
				} catch (Exception e) {
					
				}
			}
			if(indexWriterNormal!=null){
				try {
					indexWriterNormal.close();
				} catch (Exception e) {
					
				}
			}
			RetrievalIndexLock.getInstance().unlock(indexPathType);
		}
		
	}

	/**
	 * 重建索引
	 */
	public void reCreate() {

		RetrievalIndexLock.getInstance().lock(indexPathType);
		
		luceneProperties.getIndexWriterPool().remove(indexPathType);
		
		try{

			IndexWriter indexWriter=indexWriteProvider.createNewIndexWriter(indexPathType);
			try {
				indexWriter.commit();
			} catch (Exception e1) {
				
			}
			try {
				indexWriter.close();
			} catch (Exception e1) {
				
			}
			
		}catch(Exception e){
			RetrievalUtil.errorLog(log, e);
		}finally{
			RetrievalIndexLock.getInstance().unlock(indexPathType);
		}
		
	}

	/**
	 * 索引优化
	 */
	public void optimize() {

		RetrievalIndexLock.getInstance().lock(indexPathType);
		
		IndexWriter indexWriter=indexWriteProvider.createNormalIndexWriter(indexPathType);

		try{
			try {
				indexWriter.optimize();
			} catch (Exception e) {
				RetrievalUtil.errorLog(log, e);
			}
			try {
				indexWriter.commit();
			} catch (Exception e) {
				RetrievalUtil.errorLog(log, e);
			}
			try {
				indexWriter.close();
			} catch (Exception e) {
				RetrievalUtil.errorLog(log, e);
			}
		}finally{
			RetrievalIndexLock.getInstance().unlock(indexPathType);
		}
		
	}
	
}
