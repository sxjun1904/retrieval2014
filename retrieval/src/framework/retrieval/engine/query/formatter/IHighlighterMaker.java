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
package framework.retrieval.engine.query.formatter;

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
