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
package framework.retrieval.engine.query.formatter.impl;

import org.apache.lucene.search.Query;

import framework.retrieval.engine.query.formatter.IHighlighterFactory;
import framework.retrieval.engine.query.formatter.IHighlighterMaker;

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
