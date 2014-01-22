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


/**
 * 索引文件管理
 * 
 * @author 
 *
 */
public interface IRIndexManager {
	
	/**
	 * 判断是否有索引文件存在
	 * @return
	 */
	public boolean isExists();

	/**
	 * 如果索引文件不存在，则初始化生成索引文件，如果索引文件已经存在，则不做任何操作
	 */
	public void create();

	/**
	 * 如果索引文件存在，则重新生成索引文件，如果索引文件不存在，则生成一个新的文件
	 */
	public void reCreate();

	/**
	 * 执行索引优化
	 */
	public void optimize();
	
}
