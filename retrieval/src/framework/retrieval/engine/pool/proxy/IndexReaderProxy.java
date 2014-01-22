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

package framework.retrieval.engine.pool.proxy;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;

import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.index.create.RetrievalCreateIndexException;

/**
 * IndexReader代理对象 
 *
 * @author:
 *
 *
 */
public class IndexReaderProxy {
	private Log log=RetrievalUtil.getLog(this.getClass());
	private IndexReader indexReaderProxyObject=null;
	private Directory directory=null;
	private String indexPathType=null;
	
	private volatile boolean reopenFlag=false;
	private volatile  boolean finalCloseFlag=false;
	//对象被引用次数
	private volatile int refCounts=0;
	
	public IndexReaderProxy(String indexPathType,
			Directory directory){
		this.indexPathType=indexPathType;
		this.directory=directory;	}
	
	/**
	 * 调用close方法时，是否调用真正的close方法
	 * @return
	 */
	public boolean isFinalCloseFlag() {
		return finalCloseFlag;
	}

	/**
	 * 调用close方法时，是否调用真正的close方法
	 * @param finalCloseFlag
	 */
	public void setFinalCloseFlag(boolean finalCloseFlag) {
		this.finalCloseFlag = finalCloseFlag;
	}

	/**
	 * 是否需要被reopen
	 * @return
	 */
	public boolean isReopenFlag() {
		return reopenFlag;
	}

	/**
	 * 设置reopen标识
	 * @param reopenFlag
	 */
	public void setReopenFlag() {
		this.reopenFlag = true;
	}

	/**
	 * 增加被引用次数
	 */
	public void increaseRefCount(){
		refCounts++;
	}
	
	/**
	 * 减少被引用次数
	 */
	private void reduceRefCount(){
		refCounts--;
	}
	
	/**
	 * 获取当前被引用次数
	 * @return
	 */
	public int getRefCount(){
		return refCounts;
	}
	
	/**
	 * 获取当前IndexReader对应的Directory对象
	 * @return
	 */
	public Directory getDirectory() {
		return directory;
	}

	/**
	 * 生成IndexReader对象
	 */
	private void createProxy(){
		try {
			indexReaderProxyObject=IndexReader.open(directory,true);
		} catch (Exception e) {
			throw new RetrievalCreateIndexException(e);
		}
	}
	
	/**
	 * 传入IndexReader对象
	 * @param indexReaderProxyObject
	 */
	public void setProxy(IndexReader indexReaderProxyObject){
		this.indexReaderProxyObject=indexReaderProxyObject;
	}
	
	/**
	 * 获取IndexReader对象
	 * @return
	 */
	public IndexReader getIndexReader(){
		if(indexReaderProxyObject==null){
			createProxy();
			log.debug("创建代理对象 "+indexReaderProxyObject);
		}
		return indexReaderProxyObject;
	}
	
	/**
	 * 将对象返回对象池
	 */
	private void returnPool(){
		reduceRefCount();
	}
	
	/**
	 * 将对象返回对象池
	 */
	public synchronized void close(){
		returnPool();
		log.debug("return IndexReaderProxy to pool : "+indexReaderProxyObject+" refCount : "+getRefCount());
		if(isFinalCloseFlag() && getRefCount()<=0){
			log.debug("final close IndexReader : "+indexReaderProxyObject);
			try {
				indexReaderProxyObject.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 彻底关闭一个IndexReaderProxy对象
	 * @throws IOException
	 */
	public void finalClose() throws IOException{
		if(isFinalCloseFlag() && getRefCount()<=0){
			log.debug("final close IndexReader : "+indexReaderProxyObject);
			try {
				indexReaderProxyObject.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String toString(){
		return "{"
			+"	indexPathType:"+indexPathType
			+"	indexReaderProxyObject:"+indexReaderProxyObject
			+"	refCounts:"+refCounts
			+"}"
			;
	}

}
