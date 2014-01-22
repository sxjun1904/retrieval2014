/**
 * Copyright 2010 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package framework.retrieval.engine.context.impl;

import framework.retrieval.engine.analyzer.IRAnalyzerBuilder;
import framework.retrieval.engine.analyzer.IRAnalyzerFactory;
import framework.retrieval.engine.analyzer.impl.RAnalyzerFactory;
import framework.retrieval.engine.context.IRetrievalFactory;
import framework.retrieval.engine.context.RetrievalLoadException;
import framework.retrieval.engine.context.RetrievalProperties;
import framework.retrieval.engine.index.all.database.IRDatabaseIndexAll;
import framework.retrieval.engine.index.create.impl.file.IFileContentParserManager;
import framework.retrieval.engine.query.formatter.IHighlighterFactory;
import framework.retrieval.engine.query.formatter.IHighlighterMaker;
import framework.retrieval.engine.query.formatter.impl.HighlighterFactory;

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
