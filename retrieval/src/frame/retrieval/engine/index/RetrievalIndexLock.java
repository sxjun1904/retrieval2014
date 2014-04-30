package frame.retrieval.engine.index;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 索引同步锁管理
 * 
 * @author 
 *
 */
public class RetrievalIndexLock {
	private static RetrievalIndexLock retrievalIndexLock = new RetrievalIndexLock();
	private Object synchronizedObject = new Object();
	private Map<String, ReentrantLock> lockMap = new Hashtable<String, ReentrantLock>();

	private RetrievalIndexLock() {

	}

	/**
	 * 获取锁管理实例
	 * 
	 * @return
	 */
	public static RetrievalIndexLock getInstance() {
		return retrievalIndexLock;
	}

	/**
	 * 获取锁
	 * 
	 * @param indexPathType
	 */
	public void lock(String indexPathType) {
		ReentrantLock lock = lockMap.get(indexPathType.toUpperCase());
		if (lock == null) {
			synchronized (synchronizedObject) {
				lock = lockMap.get(indexPathType.toUpperCase());
				if (lock == null) {
					lock = new ReentrantLock();
					lockMap.put(indexPathType, lock);
				}
			}
		}
		lock.lock();
	}

	/**
	 * 释放锁
	 * 
	 * @param indexPathType
	 */
	public void unlock(String indexPathType) {
		ReentrantLock lock = lockMap.get(indexPathType.toUpperCase());
		lock.unlock();
	}

}
