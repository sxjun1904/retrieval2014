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

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

import framework.retrieval.engine.analyzer.IRAnalyzerFactory;

/**
 * QueryUtil
 * @author 
 *
 */
public class QueryUtil {

	private QueryUtil() {

	}

	/**
	 * 生成按时间排序对象
	 * 
	 * @param orderByFlag
	 * @return
	 */
	public static Sort createCreateTimeSort(boolean orderByFlag) {
		Sort sort = new Sort(new SortField[] { new SortField(null,SortField.DOC, orderByFlag) });
		return sort;
	}

	/**
	 * 生成按得分排序对象
	 * 
	 * @return
	 */
	public static Sort createScoreSort() {
		Sort sort = new Sort(new SortField[] { SortField.FIELD_SCORE,new SortField(null, SortField.DOC, true) });
		return sort;
	}

	/**
	 * 获取查询分词器
	 * 
	 * @return
	 */
	public static Analyzer createQueryAnalyzer(IRAnalyzerFactory analyzerFactory) {
		return analyzerFactory.createQueryAnalyzer();
	}

	/**
	 * 创建Query对象
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static Query createQuery(IRAnalyzerFactory analyzerFactory,String fieldName, String value) {
		return analyzerFactory.createQuery(fieldName, value);
	}
	
	public static Query createQuery(IRAnalyzerFactory analyzerFactory,String fieldName, String value, Float score) {
		return analyzerFactory.createQuery(fieldName, value, score);
	}
}
