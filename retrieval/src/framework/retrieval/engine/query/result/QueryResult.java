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
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.lucene.search.Query;

import framework.base.snoic.base.util.StringClass;
import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.query.formatter.IHighlighterFactory;

/**
 * 索引查询结果
 * @author 
 *
 */
public class QueryResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4377837108686929872L;
	
	private Log log=RetrievalUtil.getLog(this.getClass());

	protected IHighlighterFactory highlighterFactory=null;
	protected Map<String,String> queryResultMap=null;
	protected Query query;
	protected int hitId=-1;
	protected int allQueryResultCount=0;

	/**
	 * 创建索引查询结果
	 * @param highlighterFactory
	 * @param queryResultMap
	 */
	public QueryResult(IHighlighterFactory highlighterFactory,
			Map<String, String> queryResultMap){
		this.queryResultMap=queryResultMap;
		this.highlighterFactory=highlighterFactory;
	}
	
	/**
	 * 获取查询结果中所有字段
	 * @return
	 */
	public Map<String, String> getQueryResultMap() {
		return queryResultMap;
	}

	/**
	 * 获取Query对象
	 * @return
	 */
	public Query getQuery() {
		return query;
	}

	/**
	 * 设置Query对象
	 * @param query
	 */
	public void setQuery(Query query) {
		this.query = query;
	}

	/**
	 * 获取索引HitID
	 * @return
	 */
	public int getHitId() {
		return hitId;
	}

	/**
	 * 设置索引HitID
	 * @param hitId
	 */
	public void setHitId(int hitId) {
		this.hitId = hitId;
	}

	/**
	 * 获取索引唯一ID
	 * @return
	 */
	public String getIndexId(){
		return queryResultMap.get(StringClass.getString(RetrievalType.RDocItemSpecialName._IID));
	}
	
	/**
	 * 获取索引点击次数
	 * @return
	 */
	public long getIndexHits(){
		String hitsStr=StringClass.getString(queryResultMap.get(StringClass.getString(RetrievalType.RDocItemSpecialName._IH)));
		if(hitsStr.equals("")){
			hitsStr="0";
		}
		long hits=0;
		try{
			hits=Long.parseLong(hitsStr);
		}catch(Exception e){
			RetrievalUtil.errorLog(log, e);
		}
		return hits;
	}
	
	/**
	 * 获取索引信息分类
	 * @return
	 */
	public String getIndexInfoType(){
		return queryResultMap.get(StringClass.getString(RetrievalType.RDocItemSpecialName._IBT));
	}
	
	/**
	 * 获取索引来源类型
	 * @return
	 */
	public String getIndexSourceType(){
		return queryResultMap.get(StringClass.getString(RetrievalType.RDocItemSpecialName._IST));
	}
	
	/**
	 * 获取索引所有内容字段值
	 * @return
	 */
	public String getIndexFullContent(){
		return queryResultMap.get(StringClass.getString(RetrievalType.RDocItemSpecialName._IAC));
	}
	
	/**
	 * 获取索引创建时间
	 * @return
	 */
	public String getIndexCreatTime(){
		return queryResultMap.get(StringClass.getString(RetrievalType.RDocItemSpecialName._IC));
	}
	
	/**
	 * 获取索引所有字段名
	 * @return
	 */
	public List<String> getIndexAllFieldNames(){
		String names = queryResultMap.get(StringClass.getString(RetrievalType.RDocItemSpecialName._IAF));
		return RetrievalUtil.getAllFields(names);
	}
	
	/**
	 * 获取结果
	 * @param fieldName
	 * @return
	 */
	public String getResult(Object fieldName){
		String newFieldName=StringClass.getString(fieldName).toUpperCase();
		return StringClass.getString(queryResultMap.get(newFieldName));
	}
	
	/**
	 * 获取高亮结果
	 * @param fieldName		字段名称
	 * @param resumeLength	摘要长度
	 * @return
	 */
	public String getHighlighterResult(Object fieldName,int resumeLength){
		String newFieldName=StringClass.getString(fieldName).toUpperCase();
		String value= StringClass.getString(queryResultMap.get(newFieldName));
		
		//value=value.replaceAll("<", "&gt");
		//value=value.replaceAll(">", "&lt");
		value=value.replaceAll("'", "‘");
		value=value.replaceAll("\"", "“");
		
		String result=StringClass.getString(highlighterFactory.getHighlighterValue(query,newFieldName,value, resumeLength));
		
		if(result.equals("")){
			result=value;
			if(result.length()>resumeLength){
				result=result.substring(0,resumeLength);
			}
		}
		
		return result;
	}

	/**
	 * 获取本次查询结果总记录数
	 * @return
	 */
	public int getAllQueryResultCount() {
		return allQueryResultCount;
	}

	/**
	 * 设置本次查询结果总记录数
	 * @param allQueryResultCount
	 */
	public void setAllQueryResultCount(int allQueryResultCount) {
		this.allQueryResultCount = allQueryResultCount;
	}

	public String toString(){
		return "allQueryResultCount:"+allQueryResultCount+";nowQueryResult:"+queryResultMap.toString();
	}
}
