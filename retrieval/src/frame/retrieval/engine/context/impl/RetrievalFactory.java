package frame.retrieval.engine.context.impl;

import frame.retrieval.engine.analyzer.IRAnalyzerBuilder;
import frame.retrieval.engine.analyzer.IRAnalyzerFactory;
import frame.retrieval.engine.analyzer.impl.RAnalyzerFactory;
import frame.retrieval.engine.context.IRetrievalFactory;
import frame.retrieval.engine.context.RetrievalLoadException;
import frame.retrieval.engine.context.RetrievalProperties;
import frame.retrieval.engine.index.all.database.IRDatabaseIndexAll;
import frame.retrieval.engine.index.create.impl.file.IFileContentParserManager;
import frame.retrieval.engine.query.formatter.IHighlighterFactory;
import frame.retrieval.engine.query.formatter.IHighlighterMaker;
import frame.retrieval.engine.query.formatter.impl.HighlighterFactory;

/**
 * 全文检索内置关键对象初始化工厂
 * 
 * @author 
 *
 */
public class RetrievalFactory implements IRetrievalFactory {
	private IFileContentParserManager fileContentParserManager=null;
	private IRAnalyzerFactory analyzerFactory=null;
	private IHighlighterFactory highlighterFactory=null;
	@SuppressWarnings("unchecked")
	private Class databaseIndexAllClass=null;
	
	public RetrievalFactory(RetrievalProperties retrievalProperties){
		initContext(retrievalProperties);
	}
	
	private void initContext(RetrievalProperties retrievalProperties){
		try {
			fileContentParserManager=(IFileContentParserManager)retrievalProperties.getPropertyValueRestrievalExtendsClassFileContentParserManager().newInstance();
		} catch (Exception e) {
			throw new RetrievalLoadException(e);
		}
		
		analyzerFactory=new RAnalyzerFactory();
		try {
			IRAnalyzerBuilder analyzerBuilder=(IRAnalyzerBuilder)retrievalProperties.getPropertyValueRestrievalExtendsClassAnalyzerBuilder().newInstance();
			analyzerBuilder.setLuceneVersion(retrievalProperties.getLuceneProperties().getPropertyValueLuceneVersion());
			analyzerFactory.regAnalyzerBuilder(analyzerBuilder);
		} catch (Exception e) {
			throw new RetrievalLoadException(e);
		}
		
		highlighterFactory=new HighlighterFactory();
		try {
			IHighlighterMaker highlighterMaker=(IHighlighterMaker)retrievalProperties.getPropertyValueRestrievalExtendsClassHeighlighterMaker().newInstance();
			highlighterMaker.setAnalyzer(analyzerFactory.createQueryAnalyzer());
			highlighterFactory.regHighlighterMaker(highlighterMaker);
		} catch (Exception e) {
			throw new RetrievalLoadException(e);
		}
		
		databaseIndexAllClass=retrievalProperties.getPropertyValueRestrievalExtendsClassDatabaseIndexAll();
		
	}

	public IFileContentParserManager getFileContentParserManager() {
		return fileContentParserManager;
	}

	public IRAnalyzerFactory getAnalyzerFactory() {
		return analyzerFactory;
	}

	public IHighlighterFactory getHighlighterFactory() {
		return highlighterFactory;
	}

	public IRDatabaseIndexAll getDatabaseIndexAll() {
		IRDatabaseIndexAll databaseIndexAll=null;
		try {
			databaseIndexAll=(IRDatabaseIndexAll)databaseIndexAllClass.newInstance();
		} catch (Exception e) {
			throw new RetrievalLoadException(e);
		}
		return databaseIndexAll;
	}
	
}
