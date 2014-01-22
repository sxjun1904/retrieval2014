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

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.logging.Log;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;

import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.context.LuceneProperties;
import framework.retrieval.engine.index.create.RetrievalCreateIndexException;
import framework.retrieval.engine.pool.IIndexWriterPool;

/**
 * IndexWriter代理对象
 *
 * @author:
 *
 *
 */
public class IndexWriterProxy implements MethodInterceptor{
	private Log log=RetrievalUtil.getLog(this.getClass());
	private IndexWriter indexWriterProxyObject=null;
	private volatile boolean finalCloseFlag=false;

	private String indexPathType=null;
	private Directory directory=null;
	private Analyzer analyzer=null;
	private LuceneProperties luceneProperties=null;
	private IIndexWriterPool indexWriterPool=null;
	
	//被引用次数
	private volatile int refCounts=0;
	
	/**
	 * IndexWriter代理对象
	 * @param indexPathType		索引路径类型
	 * @param directory			索引Directory
	 * @param analyzer			分词器
	 * @param luceneProperties	Lucene属性
	 */
	public IndexWriterProxy(String indexPathType,
			Directory directory,
			Analyzer analyzer,
			LuceneProperties luceneProperties){
		this.indexPathType=indexPathType;
		this.directory=directory;
		this.analyzer=analyzer;
		this.luceneProperties=luceneProperties;
		indexWriterPool=luceneProperties.getIndexWriterPool();
	}
	
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
	 * 增加被引用次数
	 */
	private void increaseRefCount(){
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
	 * 生成代理对象
	 * @param directory
	 * @param analyzer
	 * @param luceneProperties
	 */
	private void createProxy(Directory directory,Analyzer analyzer,LuceneProperties luceneProperties){
		
        Class[] argumentTypes=new Class[]{Directory.class,Analyzer.class,boolean.class,MaxFieldLength.class};
        Object[] arguments=new Object[]{directory,analyzer,false,MaxFieldLength.UNLIMITED};
        
    	Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(IndexWriter.class);  
        enhancer.setCallback(this);
		try {
			indexWriterProxyObject = (IndexWriter)enhancer.create(argumentTypes, arguments);
			if(luceneProperties.getPropertyValueLuceneMergerFactor()>0){
				indexWriterProxyObject.setMergeFactor(luceneProperties.getPropertyValueLuceneMergerFactor());
			}
			if(luceneProperties.getPropertyValueLuceneMaxMergeDocs()>0){
				indexWriterProxyObject.setMaxMergeDocs(luceneProperties.getPropertyValueLuceneMaxMergeDocs());
			}
			if(luceneProperties.getPropertyValueLuceneMaxBufferedDocs()>0){
				indexWriterProxyObject.setMaxBufferedDocs(luceneProperties.getPropertyValueLuceneMaxBufferedDocs());
			}
			if(luceneProperties.getPropertyValueLuceneRamBufferSizeMB()>0){
				indexWriterProxyObject.setRAMBufferSizeMB(luceneProperties.getPropertyValueLuceneRamBufferSizeMB());
			}
			if(luceneProperties.getPropertyValueLuceneMaxFieldLength()>0){
				indexWriterProxyObject.setMaxFieldLength(luceneProperties.getPropertyValueLuceneMaxFieldLength());
			}
		} catch (Exception e) {
			throw new RetrievalCreateIndexException(e);
		}
	}
	
	/**
	 * 获取代理对象
	 * @return
	 */
	public IndexWriter getProxy(){
		if(indexWriterProxyObject==null){
			createProxy(directory,analyzer,luceneProperties);
			log.debug("创建代理对象 "+indexWriterProxyObject);
		}
		increaseRefCount();
		return indexWriterProxyObject;
	}
	
	/**
	 * 将对象返回对象池
	 */
	private synchronized void returnPool(){
		reduceRefCount();
		indexWriterPool.checkIn(indexPathType, indexWriterProxyObject);
	}

	/**
	 * 拦截并执行代理方法
	 */
	public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		String methodName=method.getName();
		Object invokeResult = null;
		
		//当外部调用close方法时
		if(methodName.equals("close") && !finalCloseFlag){
			//如果调用的是代理的close方法
			//将代理对象放入对象池
			returnPool();
		}else{
			//执行原始方法
			invokeResult=proxy.invokeSuper(object, args);
		}
		return invokeResult;
	}
	
	public String toString(){
		return "{"
			+"	indexPathType:"+indexPathType
			+"	indexWriterProxyObject:"+indexWriterProxyObject
			+"	refCounts:"+refCounts
			+"}"
			;
	}

}
