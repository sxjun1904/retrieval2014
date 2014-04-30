package frame.retrieval.engine.query.item;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

import frame.retrieval.engine.analyzer.IRAnalyzerFactory;

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
