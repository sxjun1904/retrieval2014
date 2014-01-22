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
package framework.retrieval.engine.facade.impl;

import framework.retrieval.engine.analyzer.IRAnalyzerFactory;
import framework.retrieval.engine.context.LuceneProperties;
import framework.retrieval.engine.facade.IRIndexOperatorFacade;
import framework.retrieval.engine.index.IRIndexManager;
import framework.retrieval.engine.index.impl.RIndexManager;

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
	
}
