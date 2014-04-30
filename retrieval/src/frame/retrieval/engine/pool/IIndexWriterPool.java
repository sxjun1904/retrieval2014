package frame.retrieval.engine.pool;

import org.apache.lucene.index.IndexWriter;

import frame.retrieval.engine.pool.proxy.IndexWriterProxy;


/**
 * IndexWriter对象池
 *
 * @author:
 *
 *
 */
public interface IIndexWriterPool {
	
	/**
	 * 将代理对象放入对象池
	 * @param indexPathType
	 * @param indexWriterProxy
	 */
	public void checkIn(String indexPathType,IndexWriterProxy indexWriterProxy);
	
	/**
	 * 将代理对象返回对象池
	 * @param indexPathType
	 * @param indexWriter
	 */
	public void checkIn(String indexPathType,IndexWriter indexWriter);
	
	/**
	 * 从对象池中取出IndexWriter
	 * @param <T>
	 * @param indexPathType
	 * @return
	 */
	public <T>T checkOut(String indexPathType);
	
	/**
	 * 从对象池中移除IndexWriter
	 * @param indexPathType
	 * @return
	 */
	public void remove(String indexPathType);
	
	/**
	 * 清空对象池
	 */
	public void clean();
}
