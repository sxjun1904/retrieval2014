package frame.retrieval.engine.analyzer.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.util.Version;

import frame.retrieval.engine.analyzer.IRAnalyzerBuilder;
import frame.retrieval.engine.query.RetrievalQueryException;

/**
 * CJKAnalyzer分词Builder
 * @author 
 *
 *
 */

public class CJKAnalyzerBuilder implements IRAnalyzerBuilder {
	
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
	public Analyzer createIndexAnalyzer() {
		return new CJKAnalyzer(luceneVersion);
	}
	
	/**
	 * 获取搜索分词器
	 * @return
	 */
	public Analyzer createQueryAnalyzer() {
		return new CJKAnalyzer(luceneVersion);
	}
	
	/**
	 * 获取相似度评估器
	 * @return
	 */
	public Similarity createSimilarity() {
		return Similarity.getDefault();
	}

	/**
	 * 创建Query对象
	 * @param fieldName
	 * @param queryContent
	 * @return
	 */
	public Query createQuery(String fieldName,String queryContent) {
		return createQuery(fieldName, queryContent,null);
	}

	@Override
	public Query createQuery(String fieldName, String queryContent, Float score) {
		Query query = null;
		QueryParser queryParser=new QueryParser(luceneVersion,fieldName,createQueryAnalyzer());
		try {
			query = queryParser.parse(queryContent);
			if(score!=null)
				query.setBoost(score);
		} catch (Exception e) {
			throw new RetrievalQueryException(e);
		}
		return query;
	}

}
