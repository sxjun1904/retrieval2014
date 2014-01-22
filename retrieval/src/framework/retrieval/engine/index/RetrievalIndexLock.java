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
package framework.retrieval.engine.index;

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
