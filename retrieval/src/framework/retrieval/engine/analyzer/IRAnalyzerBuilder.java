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
package framework.retrieval.engine.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.util.Version;

/**
 *索引分词器
 *
 * @author 
 *
 *
 */

public interface IRAnalyzerBuilder {
	
	/**
	 * 设置全文检索属性参数对象
	 * @param luceneVersion
	 */
	public void setLuceneVersion(Version luceneVersion);
	
	/**
	 * 获取索引分词器
	 * @return
	 */
	public Analyzer createIndexAnalyzer();
	
	/**
	 * 获取搜索分词器
	 * @return
	 */
	public Analyzer createQueryAnalyzer();
	
	/**
	 * 获取相似度评估器
	 * @return
	 */
	public Similarity createSimilarity();

	/**
	 * 创建Query对象
	 * @param fieldName
	 * @param queryContent
	 * @return
	 */
	public Query createQuery(String fieldName,String queryContent);
	
	public Query createQuery(String fieldName,String queryContent, Float score);
}
