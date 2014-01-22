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
package framework.retrieval.engine.query.result;

import java.io.Serializable;
import java.util.Map;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.query.formatter.IHighlighterFactory;


/**
 * 数据库类型索引查询结果
 * @author 
 *
 */
public class DatabaseQueryResult extends QueryResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1394108956579978808L;

	public DatabaseQueryResult(IHighlighterFactory highlighterFactory,
			Map<String, String> queryResultMap) {
		super(highlighterFactory,queryResultMap);
	}
	
	/**
	 * 获取数据库表名
	 * @return
	 */
	public String getTableName(){
		return getResult(RetrievalType.RDatabaseDocItemType._DT);
	}
	
	/**
	 * 获取数据库记录ID
	 * @return
	 */
	public String getRecordId(){
		return getResult(RetrievalType.RDatabaseDocItemType._DID);
	}
	
}
