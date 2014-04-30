package frame.retrieval.engine.analyzer;

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
