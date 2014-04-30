package frame.retrieval.engine.query.formatter.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import frame.retrieval.engine.query.RetrievalQueryException;
import frame.retrieval.engine.query.formatter.IHighlighterMaker;

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
