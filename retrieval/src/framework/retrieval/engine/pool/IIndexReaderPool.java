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

package framework.retrieval.engine.pool;

import framework.retrieval.engine.pool.proxy.IndexReaderProxy;



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
