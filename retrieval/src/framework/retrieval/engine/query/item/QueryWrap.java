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
package framework.retrieval.engine.query.item;

import java.io.Serializable;

import org.apache.lucene.search.Query;

/**
 * Query对象外覆类
 * @author 
 *
 */
public class QueryWrap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6449003355886923541L;

	private QueryItem queryItem;
	private Query query;
	
	/**
	 * 创建QueryWrap
	 * @param query
	 */
	public QueryWrap(Query query){
		this.query=query;
	}
	
	/**
	 * 创建QueryWrap
	 * @param queryItem
	 * @param query
	 */
	public QueryWrap(QueryItem queryItem,Query query){
		this.queryItem=queryItem;
		this.query=query;
	}

	/**
	 * 获取查询字段
	 * @return
	 */
	public QueryItem getQueryItem() {
		return queryItem;
	}

	/**
	 * 获取Query对象
	 * @return
	 */
	public Query getQuery() {
		return query;
	}
	
}
