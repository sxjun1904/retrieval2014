package framework.retrieval.oth.ik;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;

import framework.retrieval.engine.analyzer.impl.IKCAnalyzerBuilder;

public class IKWordsUtil {
	private static Dictionary dictionary = Dictionary.initial(DefaultConfig.getInstance());
	private static Analyzer analyzer = new IKCAnalyzerBuilder().createQueryAnalyzer();
	
	
	/**
	 * 增加扩展词汇
	 * @param words
	 */
	public static void addWord(String word){
		List<String> array = new ArrayList<String>();
		array.add(word);
		dictionary.addWords(array);
	}
	
	/**
	 * 增加扩展词汇
	 * @param words
	 */
	public static void addWords(List<String> words){
		dictionary.addWords(words);
	}
	
	/**
	 * 屏蔽词典中的词元
	 * @param words
	 */
	public static void disableWord(String word){
		List<String> array = new ArrayList<String>();
		array.add(word);
		dictionary.disableWords(array);
	}
	
	/**
	 * 屏蔽词典中的词元
	 * @param words
	 */
	public static void disableWords(List<String> words){
		dictionary.disableWords(words);
	}
	
	/**
	 * 遍历分词
	 * @param text
	 * @return
	 */
	public static List<String> ikTokens(String text){
		StringReader reader = null;
		List<String> iktokes = new ArrayList<String>();
		try {
			reader = new StringReader(text);
			//分词   
			TokenStream ts=analyzer.tokenStream("", reader); 
			CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);  
			//遍历分词数据   
			while(ts.incrementToken()){  
				iktokes.add(term.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(reader!=null)
				reader.close(); 
		}
		return iktokes;
	}
	
	/**
	 * 判断word在是否存在ik库中
	 * @param word
	 * @return
	 */
	public static boolean isExists(String word){
		boolean exist = false;
		StringReader reader = null;
		try {
			reader = new StringReader(word);
			//分词   
			TokenStream ts=analyzer.tokenStream("", reader); 
			CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);  
			//遍历分词数据   
			while(ts.incrementToken()){  
				if(term.toString().length()==word.length()){
					exist=true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(reader!=null)
				reader.close(); 
		}
		return exist;
	}
}
