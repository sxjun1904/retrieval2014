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
package framework.retrieval.engine.context;

import framework.retrieval.engine.analyzer.IRAnalyzerFactory;
import framework.retrieval.engine.index.all.database.IRDatabaseIndexAll;
import framework.retrieval.engine.index.create.impl.file.IFileContentParserManager;
import framework.retrieval.engine.query.formatter.IHighlighterFactory;


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
