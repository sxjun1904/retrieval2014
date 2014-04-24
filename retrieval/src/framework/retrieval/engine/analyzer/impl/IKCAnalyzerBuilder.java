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
package framework.retrieval.engine.analyzer.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import framework.retrieval.engine.analyzer.IRAnalyzerBuilder;

/**
 * IKCAnalyzer分词Builder
 * @author 
 *
 *
 */

public class IKCAnalyzerBuilder implements IRAnalyzerBuilder {

	
	private Version luceneVersion=null;

	/**
	 * 设置全文检索属性参数对象
	 * @param luceneVersion
	 */
	public void setLuceneVersion(Version luceneVersion){
		this.luceneVersion=luceneVersion;
	}
	
	/**
	 * 获取索引分词器
	 * @return
	 */
	public Analyzer createIndexAnalyzer(){
		return new IKAnalyzer();
	}
	
	/**
	 * 获取搜索分词器
	 * @return
	 */
	public Analyzer createQueryAnalyzer(){
		return new IKAnalyzer();
	}
	
	/**
	 * 获取相似度评估器
	 * @return
	 */
	public Similarity createSimilarity() {
//		return new IKSimilarity();
		return Similarity.getDefault();
	}

	/**
	 * 创建Query对象
	 * @param fieldName
	 * @param queryContent
	 * @return
	 */
	public Query createQuery(String fieldName,String queryContent) {
		/*Query query = null;
		try {
			query = IKQueryParser.parse(fieldName,queryContent);
			System.out.println(query.toString());
		} catch (Exception e) {
			throw new RetrievalQueryException(e);
		}
		return query;*/
		return createQuery(fieldName,queryContent,null);
	}

	@Override
	public Query createQuery(String fieldName, String queryContent, Float score) {
		Query query = null;
		QueryParser parser = new QueryParser(luceneVersion, fieldName, createQueryAnalyzer());
		parser.setDefaultOperator(QueryParser.AND_OPERATOR);
		try {
			query = parser.parse(queryContent);
			if(score!=null)
				query.setBoost(score);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return query;
	}
}
