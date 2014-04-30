package frame.retrieval.engine.pool;

import frame.retrieval.engine.pool.proxy.IndexReaderProxy;



/**
 * 
 * IndexReader对象池
 * @author:
 *
 *
 */
public interface IIndexReaderPool {
	
	/**
	 * 将代理对象放入对象池
	 * @param indexPathType
	 * @param indexReaderProxy
	 */
	public void checkIn(String indexPathType,IndexReaderProxy indexReaderProxy);
	
	/**
	 * 从对象池中取出IndexReaderProxy
	 * @param <T>
	 * @param indexPathType
	 * @return
	 */
	public <T>T checkOut(String indexPathType);
	
	/**
	 * 设置reopen标识
	 * @param indexPathType
	 */
	public void setReopen(String indexPathType);
	
	/**
	 * 从对象池中移除IndexReaderProxy
	 * @param indexPathType
	 * @return
	 */
	public void remove(String indexPathType);
	
	/**
	 * 清空对象池
	 */
	public void clean();
}
