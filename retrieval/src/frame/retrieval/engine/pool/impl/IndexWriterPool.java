package frame.retrieval.engine.pool.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.lucene.index.IndexWriter;

import frame.retrieval.engine.pool.IIndexReaderPool;
import frame.retrieval.engine.pool.IIndexWriterPool;
import frame.retrieval.engine.pool.RetrievalIndexPoolException;
import frame.retrieval.engine.pool.proxy.IndexWriterProxy;
import frame.retrieval.engine.common.RetrievalUtil;

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
