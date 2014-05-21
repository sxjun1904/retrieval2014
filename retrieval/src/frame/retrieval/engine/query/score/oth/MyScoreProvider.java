package frame.retrieval.engine.query.score.oth;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.function.CustomScoreProvider;

public class MyScoreProvider  extends CustomScoreProvider{  

	String[] scores = null;
	String[] pageranks = null;
    public MyScoreProvider(IndexReader reader,String scoreField,String pagerank) {  
        super(reader);  
      try {  
    	  scores = FieldCache.DEFAULT.getStrings(reader, scoreField);//3---------
    	  pageranks = FieldCache.DEFAULT.getStrings(reader, pagerank);//3---------
        } catch (IOException e) {e.printStackTrace();}
    }  

    //如何根据doc获取相应的field的值  
    /* 
     * 在reader没有关闭之前，所有的数据会存储要一个域缓存中，可以通过域缓存获取很多有用 
     * 的信息filenames = FieldCache.DEFAULT.getStrings(reader, "filename");可以获取 
     * 所有的filename域的信息 
     */  
    @Override  
    public float customScore(int doc, float subQueryScore, float valSrcScore)  
            throws IOException { 
    	Float s = (float) 1;
    	Float r = (float) 1;
    	String score = scores[doc];  
    	String pagerank = pageranks[doc];  
    	s = Float.valueOf(score);
    	r = Float.valueOf(pagerank);
        return (float) (subQueryScore*0.4+s*0.3+r*0.3);
    	
    } 
}  

