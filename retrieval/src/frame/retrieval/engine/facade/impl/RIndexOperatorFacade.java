package frame.retrieval.engine.facade.impl;

import frame.retrieval.engine.analyzer.IRAnalyzerFactory;
import frame.retrieval.engine.context.LuceneProperties;
import frame.retrieval.engine.facade.IRIndexOperatorFacade;
import frame.retrieval.engine.index.IRIndexManager;
import frame.retrieval.engine.index.impl.RIndexManager;

/**
 * 
 * @author 
 *
 */
public class RIndexOperatorFacade implements IRIndexOperatorFacade{
	private IRIndexManager indexManager=null;
	
	public RIndexOperatorFacade(IRAnalyzerFactory analyzerFactory,
			LuceneProperties luceneProperties,
			String indexPathType){
		indexManager=new RIndexManager(analyzerFactory,luceneProperties,indexPathType);
	}
	
	/**
	 * 创建索引文件，如果索引文件已经存在，则不再创建
	 */
	public void createIndex(){
		indexManager.create();
	}
	
	/**
	 * 创建索引文件，如果索引文件不存在，则创建一个新索引，如果索引文件已经存在，则删除旧索引，创建一个新的索引
	 * 		这个操作会造成已经存在的索引内容丢失，请慎用
	 */
	public void reCreateIndex(){
		indexManager.reCreate();
	}
	
	/**
	 * 优化索引
	 * @param
	 */
	public void optimize(){
		indexManager.optimize();
	}

	@Override
	public void forceMerge(int IndexNum) {
		indexManager.forceMerge(IndexNum);
	}
	
}
