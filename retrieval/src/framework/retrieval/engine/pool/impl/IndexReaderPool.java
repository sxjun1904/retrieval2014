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

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;

import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.pool.IIndexReaderPool;
import framework.retrieval.engine.pool.RetrievalIndexPoolException;
import framework.retrieval.engine.pool.proxy.IndexReaderProxy;

/**
 * IndexReader对象池
 *
 * @author:
 *
 *
 */
public class IndexReaderPool implements IIndexReaderPool{
	private Log log=RetrievalUtil.getLog(this.getClass());
	private Set<String> indexPathTypes=null;
	private Map<String,IndexReaderProxy> methodInterceptorPoolMap=null;
	
	public IndexReaderPool(){
		methodInterceptorPoolMap=Collections.synchronizedMap(new HashMap<String,IndexReaderProxy>());
		indexPathTypes=Collections.synchronizedSet(new HashSet<String>());
	}
	
	/**
	 * 将代理对象放入对象池
	 * @param indexPathType
	 * @param indexReaderProxy
	 */
	public synchronized void checkIn(String indexPathType, IndexReaderProxy indexReaderProxy) {
		indexPathType=String.valueOf(indexPathType).toUpperCase();
		if(indexPathTypes.contains(indexPathType)){
			log.debug("对象池中已经存在 "+indexPathType+" 索引的 IndexReaderProxy 对象 ： "+methodInterceptorPoolMap.get(indexPathType));
			return;
		}else{
			indexReaderProxy.getIndexReader();
		}
		indexPathTypes.add(indexPathType);
		methodInterceptorPoolMap.put(indexPathType, indexReaderProxy);
		log.debug("checkIn : "+indexPathType+"|"+indexReaderProxy);
	}

	/**
	 * 从对象池中取出IndexReaderProxy
	 * @param IndexReaderProxy
	 * @param indexPathType
	 * @return
	 */
	public synchronized IndexReaderProxy checkOut(String indexPathType) {
		indexPathType=String.valueOf(indexPathType).toUpperCase();
		IndexReaderProxy indexReaderProxy=methodInterceptorPoolMap.get(indexPathType);
		if(indexReaderProxy!=null){
			if(indexReaderProxy.isReopenFlag()){
				reopen(indexPathType);
				indexReaderProxy=methodInterceptorPoolMap.get(indexPathType);
			}
			indexReaderProxy.increaseRefCount();
		}
		
		log.debug("check out "+indexReaderProxy);
		
		return indexReaderProxy;
	}

	/**
	 * 设置reopen标识
	 * @param indexPathType
	 */
	public synchronized void setReopen(String indexPathType){
		indexPathType=String.valueOf(indexPathType).toUpperCase();
		IndexReaderProxy indexReaderProxy=methodInterceptorPoolMap.get(indexPathType);
		if(indexReaderProxy!=null){
			log.debug("setReopen "+indexReaderProxy);
			indexReaderProxy.setReopenFlag();
		}
	}

	/**
	 * 对IndexReader执行reopen
	 * @param indexPathType
	 */
	private void reopen(String indexPathType){
		indexPathType=String.valueOf(indexPathType).toUpperCase();
		IndexReaderProxy indexReaderProxy=methodInterceptorPoolMap.get(indexPathType);
		
		log.debug("reopen "+indexReaderProxy);
		
		if(indexReaderProxy!=null){

			IndexReaderProxy newIndexReaderProxy=null;
			IndexReader newIndexReader=null;
			
			IndexReader indexReader=indexReaderProxy.getIndexReader();
			try {
				if(indexReader.isCurrent()){
					log.debug("..........................indexReader.isCurrent()  : "+indexReader.isCurrent());
					return;
				}
				newIndexReader=indexReader.reopen(true);
				newIndexReaderProxy=new IndexReaderProxy(indexPathType,indexReaderProxy.getDirectory());
				newIndexReaderProxy.setProxy(newIndexReader);

				log.debug("reopen create new IndexReaderProxy : "+newIndexReaderProxy);
				
			} catch (Exception e) {
				throw new RetrievalIndexPoolException("对象"+indexReaderProxy+".reopen失败",e);
			}
			
			indexPathTypes.remove(indexPathType);
			interRemove(indexPathType);
			
			checkIn(indexPathType, newIndexReaderProxy);
			
		}
	}

	/**
	 * 从对象池中移除IndexReaderProxy
	 * @param indexPathType
	 * @return
	 */
	public synchronized void remove(String indexPathType){
		indexPathType=String.valueOf(indexPathType).toUpperCase();

		log.debug("IndexReaderPool Remove IndexReader "+indexPathType);
		
		indexPathTypes.remove(indexPathType);
		interRemove(indexPathType);
	}

	/**
	 * 清空对象池
	 */
	public synchronized void clean() {
		log.debug("IndexReaderPool Clean indexReaderProxy.............");
		Iterator<String> it=indexPathTypes.iterator();
		while(it.hasNext()){
			String indexPathType=it.next();
			interRemove(indexPathType);
			it.remove();
		}
		log.debug("IndexReaderPool Clean complete.............");
	}
	
	/**
	 * 移除一个IndexReaderProxy对象
	 * @param indexPathType
	 */
	private void interRemove(String indexPathType){
		indexPathType=String.valueOf(indexPathType).toUpperCase();
		IndexReaderProxy indexReaderProxy=methodInterceptorPoolMap.remove(indexPathType);
		log.debug("IndexReaderPool Close indexReaderProxy............."+ indexReaderProxy);
		if(indexReaderProxy!=null){
			indexReaderProxy.setFinalCloseFlag(true);
			try {
				indexReaderProxy.finalClose();
			} catch (Exception e) {
				throw new RetrievalIndexPoolException(indexReaderProxy+"对象关闭失败",e);
			}
		}
	}
	
}
