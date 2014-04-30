package frame.retrieval.engine.query.formatter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;

/**
 * 高亮内容处理器
 * @author 
 *
 */
public interface IHighlighterMaker {

	public Analyzer getAnalyzer();

	public void setAnalyzer(Analyzer analyzer);
	
	/**
	 * 获取高亮代码的前缀
	 * @return
	 */
	public String getHtmlPrefix();

	/**
	 * 设置高亮代码的前缀
	 * @param htmlPrefix
	 */
	public void setHtmlPrefix(String htmlPrefix);

	/**
	 * 获取高亮代码的后缀
	 * @return
	 */
	public String getHtmlSuffix();

	/**
	 * 设置高亮代码的后缀
	 * @param htmlSuffix
	 */
	public void setHtmlSuffix(String htmlSuffix);
	
	/**
	 * 获取高亮格式生成器
	 * @return
	 */
	public Formatter getFormatter();
	
	/**
	 * 生成高亮内容处理器
	 * @param query			Query对象
	 * @param fieldName		需要高亮处理的字段名称
	 * @param keyWord			需要高亮处理的内容
	 * @param resumeLength	摘要长度
	 * @return
	 */
	public String getHighlighter(Query query,
			String fieldName,
			String keyWord,
			int resumeLength);
	
}
