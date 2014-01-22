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
package framework.retrieval.engine.index.all.database;

import java.util.Map;

/**
 * 数据库批量索引时单条数据库记录索引创建拦截器
 * @author 
 *
 */
public interface IIndexAllDatabaseRecordInterceptor {
	
	/**
	 * 将数据库中查询中的记录进行加工处理
	 * @param record
	 * @param fieldType
	 * @return
	 */
	public Map<String,Object> interceptor(Map<String,Object> record);
	
	/**
	 * 获取每个字段类型
	 * @return
	 */
	public Map<String,Object> getFieldsType();
	
}
