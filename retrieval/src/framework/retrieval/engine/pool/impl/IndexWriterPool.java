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

package framework.retrieval.engine.pool.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.lucene.index.IndexWriter;

import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.pool.IIndexReaderPool;
import framework.retrieval.engine.pool.IIndexWriterPool;
import framework.retrieval.engine.pool.RetrievalIndexPoolException;
import framework.retrieval.engine.pool.proxy.IndexWriterProxy;

/**
 * IndexWriter对象池
 *
 * @author:
 *
 *
 */
public class IndexWriterPool implements IIndexWriterPool{
	private Log log=RetrievalUtil.getLog(this.getClass());
	private Set<String> indexPathTypes=null;
	private Map<String,IndexWriterProxy> methodInterceptorPoolMap=null;
	private IIndexReaderPool indexReaderPool=null;
	
	public IndexWriterPool(IIndexReaderPool indexReaderPool){
		methodInterceptorPoolMap=Collections.synchronizedMap(new HashMap<String,IndexWriterProxy>());
		indexPathTypes=Collections.synchronizedSet(new HashSet<String>());
		this.indexReaderPool=indexReaderPool;
	}
	
	public synchronized void checkIn(String indexPathType,IndexWriterProxy indexWriterProxy) {
		indexPathType=String.valueOf(indexPathType).toUpperCase();
		if(indexPathTypes.contains(indexPathType)){
			log.debug("对象池中已经存在 "+indexPathType+" 索引的 IndexWriterProxy 对象 ： "+methodInterceptorPoolMap.get(indexPathType));
			return;
		}
		indexPathTypes.add(indexPathType);
		methodInterceptorPoolMap.put(indexPathType, indexWriterProxy);
		log.debug("checkIn : "+indexPathType+"|"+indexWriterProxy);
	}
	
	public void checkIn(String indexPathType,IndexWriter indexWriter) {
		indexPathType=String.valueOf(indexPathType).toUpperCase();
		log.debug("return  "+indexPathType+" to IndexWriterPool........");
		indexReaderPool.setReopen(indexPathType);
	}

	public synchronized IndexWriter checkOut(String indexPathType) {
		indexPathType=String.valueOf(indexPathType).toUpperCase();
		IndexWriter indexWriter=null;
		IndexWriterProxy indexWriterProxy=methodInterceptorPoolMap.get(indexPathType);
		if(indexWriterProxy!=null){
			indexWriter=indexWriterProxy.getProxy();
		}
		return indexWriter;
	}

	public synchronized void remove(String indexPathType) {
		log.debug("IndexWriterPool Remove IndexWriter "+indexPathType);
		indexPathTypes.remove(indexPathType);
		interRemove(indexPathType);
		
	}

	public synchronized void clean() {
		log.debug("IndexWriterPool Clean indexWriterProxy.............");
		Iterator<String> it=indexPathTypes.iterator();
		while(it.hasNext()){
			String indexPathType=it.next();
			interRemove(indexPathType);
			it.remove();
		}
		log.debug("IndexWriterPool Clean complete.............");
	}
	
	private void interRemove(String indexPathType){
		
		IndexWriterProxy indexWriterProxy=methodInterceptorPoolMap.remove(indexPathType);
		log.debug("IndexWriterPool Close indexWriterProxy............."+indexWriterProxy);
		if(indexWriterProxy!=null){
			while(indexWriterProxy.getRefCount()>0){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			indexWriterProxy.setFinalCloseFlag(true);
			try {
				indexReaderPool.remove(indexPathType);
				indexWriterProxy.getProxy().close();
			} catch (Exception e) {
				throw new RetrievalIndexPoolException(indexWriterProxy +" 对象关闭失败",e);
			}
		}
		
	}

}
