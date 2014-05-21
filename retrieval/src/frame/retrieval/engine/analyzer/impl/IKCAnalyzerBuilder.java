package frame.retrieval.engine.analyzer.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import frame.retrieval.engine.analyzer.IRAnalyzerBuilder;
import frame.retrieval.engine.query.similarity.MySimilarity;

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
//		return Similarity.getDefault();
		return new MySimilarity();
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
//		parser.setDefaultOperator(QueryParser.AND_OPERATOR);
//		parser.setAllowLeadingWildcard(true);
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
