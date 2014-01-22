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
package framework.retrieval.engine.index.all.file;

import framework.retrieval.engine.index.all.IRIndexAll;
import framework.retrieval.engine.index.doc.file.RFileIndexAllItem;



/**
 * 对文件进行批量创建索引
 * @author 
 *
 */
public interface IRFileIndexAll extends IRIndexAll{

	/**
	 * 获取文件批量索引对象
	 * @return
	 */
	public RFileIndexAllItem getFileIndexAllItem();

	/**
	 * 设置文件批量索引对象
	 * @param fileIndexAllItem
	 */
	public void setFileIndexAllItem(RFileIndexAllItem fileIndexAllItem);

}
