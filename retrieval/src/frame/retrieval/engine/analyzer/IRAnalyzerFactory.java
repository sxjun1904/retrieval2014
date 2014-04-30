package frame.retrieval.engine.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Similarity;

/**
 * 分词器工厂
 *
 * @author 
 *
 *
 */

public interface IRAnalyzerFactory {

	/**
	 * 注册分词器生成器
	 * 
	 * @param analyzerBuilder
	 */
	public void regAnalyzerBuilder(IRAnalyzerBuilder analyzerBuilder);
	

	/**
	 * 获取索引分词器
	 * 
	 * @return
	 */
	public Analyzer createIndexAnalyzer();
	

	/**
	 * 获取搜索分词器
	 * 
	 * @return
	 */
	public Analyzer createQueryAnalyzer();
	

	/**
	 * 获取相似度评估器
	 * 
	 * @return
	 */
	public Similarity createSimilarity();
	

	/**
	 * 创建Query对象
	 * 
	 * @param fieldName
	 * @param queryContent
	 * @return
	 */
	public Query createQuery(String fieldName, String queryContent);
	
	public Query createQuery(String fieldName, String queryContent, Float score);
}
