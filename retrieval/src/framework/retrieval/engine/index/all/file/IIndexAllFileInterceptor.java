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

import java.util.Map;

import framework.retrieval.engine.index.doc.file.FileIndexDocument;

/**
 * 文件批量索引时单个文件记录索引创建拦截器
 * @author 
 *
 */
public interface IIndexAllFileInterceptor {

	/**
	 * 拦截获取文件索引的附加信息
	 * @param fileIndexDocument
	 * @return
	 */
	public Map<String,Object> interceptor(FileIndexDocument fileIndexDocument);
	
	/**
	 * 拦截获取文件索引附加信息的每个字段类型
	 * @return
	 */
	public Map<String,Object> getFieldsType();
}
