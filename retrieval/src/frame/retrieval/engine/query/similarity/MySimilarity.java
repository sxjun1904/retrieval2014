package frame.retrieval.engine.query.similarity;

import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.search.Similarity;

public class MySimilarity extends Similarity{

	 protected boolean discountOverlaps = true;

	  public float computeNorm(String field, FieldInvertState state)
	  {
	    int numTerms;
	    if (this.discountOverlaps)
	      numTerms = state.getLength() - state.getNumOverlap();
	    else
	      numTerms = state.getLength();
	    return state.getBoost() * (float)(1.0D / Math.sqrt(numTerms));
	  }

	 //term freq 表示 term 在一个document的出现次数
	  public float queryNorm(float sumOfSquaredWeights)
	  {
	    return (float)(1.0D / Math.sqrt(sumOfSquaredWeights));
	  }
	  
	  public float tf(float freq)
	  {
	    return (float)Math.sqrt(freq);
	  }

	//这里表示匹配的 term　与 term之间的距离因素
	  public float sloppyFreq(int distance)
	  {
	    return 1.0F / (distance + 1);
	  }

	 //这里表示匹配的docuemnt在全部document的影响因素
	  public float idf(int docFreq, int numDocs)
	  {
	    return (float)(Math.log(numDocs / (docFreq + 1)) + 1.0D);
	  }
	  
	 //这里表示每一个Document中所有匹配的关键字与当前关键字的匹配比例因素影响
	  public float coord(int overlap, int maxOverlap)
	  {
	    return overlap / maxOverlap;
	  }

	  public void setDiscountOverlaps(boolean v)
	  {
	    this.discountOverlaps = v;
	  }

	  public boolean getDiscountOverlaps()
	  {
	    return this.discountOverlaps;
	  }
}
