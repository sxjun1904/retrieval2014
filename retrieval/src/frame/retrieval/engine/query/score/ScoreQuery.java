package frame.retrieval.engine.query.score;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.function.CustomScoreProvider;
import org.apache.lucene.search.function.CustomScoreQuery;

import frame.retrieval.engine.RetrievalType;

public class ScoreQuery  extends CustomScoreQuery{
//	private String timefield = "time";//2-----------
	private String timefield = RetrievalType.RDocItemSpecialName._IC.toString();//3-----------
	
	
	public ScoreQuery(Query subQuery) {
		super(subQuery);
	}
	
	public ScoreQuery(Query subQuery,String timefield) {
		super(subQuery);
	}

	 @Override  
     protected CustomScoreProvider getCustomScoreProvider(IndexReader reader) throws IOException {  
       return super.getCustomScoreProvider(reader);  
//         return new TimeScoreProvider(reader,timefield);  
     } 
}
