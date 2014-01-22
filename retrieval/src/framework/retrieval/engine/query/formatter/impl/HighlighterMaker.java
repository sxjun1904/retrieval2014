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

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import framework.retrieval.engine.query.RetrievalQueryException;
import framework.retrieval.engine.query.formatter.IHighlighterMaker;

/**
 * 高亮内容处理
 * @author 
 *
 */
public class HighlighterMaker implements IHighlighterMaker{
	private String htmlPrefix="<span><font color=red>";
	private String htmlSuffix="</font></span>";
	private Analyzer analyzer=null;
	
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public String getHtmlPrefix() {
		return htmlPrefix;
	}

	public void setHtmlPrefix(String htmlPrefix) {
		this.htmlPrefix = htmlPrefix;
	}

	public String getHtmlSuffix() {
		return htmlSuffix;
	}

	public void setHtmlSuffix(String htmlSuffix) {
		this.htmlSuffix = htmlSuffix;
	}

	public Formatter getFormatter(){
		return getFormatter(htmlPrefix,htmlSuffix);
	}
	
	private Formatter getFormatter(String htmlPrefix,String htmlSuffix) {
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(htmlPrefix,htmlSuffix);
		return simpleHTMLFormatter;
	}

	public String getHighlighter(Query query,
			String fieldName,
			String keyWord,
			int resumeLength) {
		
		QueryScorer scorer = new QueryScorer(query);
		
		Highlighter highlighter = new Highlighter(getFormatter(), scorer);
				
		Fragmenter fragmenter = new SimpleFragmenter(resumeLength);
		highlighter.setTextFragmenter(fragmenter);
		
		String result = "";
		
		try {
			result=highlighter.getBestFragment(analyzer, fieldName, keyWord);
		} catch (Exception e) {
			throw new RetrievalQueryException(e);
		}
		
		return result;
	}

}
