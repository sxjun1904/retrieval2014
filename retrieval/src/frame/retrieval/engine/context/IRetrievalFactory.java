package frame.retrieval.engine.context;

import frame.retrieval.engine.analyzer.IRAnalyzerFactory;
import frame.retrieval.engine.index.all.database.IRDatabaseIndexAll;
import frame.retrieval.engine.index.create.impl.file.IFileContentParserManager;
import frame.retrieval.engine.query.formatter.IHighlighterFactory;


/**
 * 全文检索内置关键对象初始化工厂
 * 
 * @author 
 *
 */
public interface IRetrievalFactory {

	/**
	 * 获取文件内容解析管理器
	 * 
	 * @return
	 */
	public IFileContentParserManager getFileContentParserManager();

	/**
	 * 获取分词工厂
	 * 
	 * @return
	 */
	public IRAnalyzerFactory getAnalyzerFactory();

	/**
	 * 获取查询结果内容高亮处理器工厂
	 * 
	 * @return
	 */
	public IHighlighterFactory getHighlighterFactory();


	/**
	 * 获取数据库批量索引操作接口
	 * @return
	 */
	public IRDatabaseIndexAll getDatabaseIndexAll();
}
