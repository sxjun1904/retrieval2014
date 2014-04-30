package frame.retrieval.engine.index.reader.impl;

import org.apache.lucene.store.Directory;

import frame.retrieval.engine.pool.proxy.IndexReaderProxy;
import frame.retrieval.engine.context.LuceneProperties;
import frame.retrieval.engine.index.RetrievalIndexLock;
import frame.retrieval.engine.index.create.RetrievalCreateIndexException;
import frame.retrieval.engine.index.directory.RetrievalDirectoryProvider;

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
