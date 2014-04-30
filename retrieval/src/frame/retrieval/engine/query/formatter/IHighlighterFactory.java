package frame.retrieval.engine.query.formatter;

import org.apache.lucene.search.Query;

/**
 * 查询结果内容高亮处理器工厂
 * @author 
 *
 */
public interface IHighlighterFactory {

	/**
	 * 注册高亮内容处理器
	 * @param highlighterMaker
	 */
	public void regHighlighterMaker(IHighlighterMaker highlighterMaker);
	

	/**
	 * 生成高亮内容处理器
	 * @param query			Query对象
	 * @param fieldName		需要高亮处理的字段
	 * @param keyWord			需要高亮处理的内容
	 * @param resumeLength	摘要长度
	 * @return
	 */
	public String getHighlighterValue(Query query,
			String fieldName,
			String keyWord,
			int resumeLength);
}
