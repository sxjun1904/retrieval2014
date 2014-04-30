package frame.retrieval.engine.query.formatter.impl;

import org.apache.lucene.search.Query;

import frame.retrieval.engine.query.formatter.IHighlighterFactory;
import frame.retrieval.engine.query.formatter.IHighlighterMaker;

/**
 * 高亮内容处理对象工厂
 * @author 
 *
 */
public class HighlighterFactory implements IHighlighterFactory{
	
	private IHighlighterMaker highlighterMaker=null;
	
	public HighlighterFactory(){
		
	}
	
	/**
	 * 注册高亮内容处理器
	 * @param highlighterMaker
	 */
	public void regHighlighterMaker(IHighlighterMaker highlighterMaker){
		this.highlighterMaker=highlighterMaker;
	}
	
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
			int resumeLength){
		return highlighterMaker.getHighlighter(query,fieldName, keyWord, resumeLength);
	}
	
}
