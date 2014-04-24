package framework.retrieval.engine.query.score;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.function.CustomScoreProvider;

public class TimeScoreProvider  extends CustomScoreProvider{  
//    long[] times = null;//2---------
	String[] times = null;//3---------
    public static Long LAST_YEAR = null;
    public static Long THE_YEAR_BEFORE_LAST = null;
    public static Long OTHER_YEAR = null;
    public TimeScoreProvider(IndexReader reader,String timefield) {  
        super(reader);  
      try {  
//    	  times = FieldCache.DEFAULT.getLongs(reader, timefield);//2---------- 
    	  times = FieldCache.DEFAULT.getStrings(reader, timefield);//3---------
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
//      return super.customScore(doc, subQueryScore, valSrcScore);//1-----------
    	
        /*long time = times[doc];  
        System.out.println(doc+":"+time);  
        return subQueryScore*getScore_longTime(time);  //2----------*/
    	
    	String time = times[doc];  
        System.out.println(doc+":"+time);  
        return subQueryScore*getScore_DateTime(time);//3---------
    	
    } 
    
    public long getScore_DateTime(String datestr){
    	long score = (long) 1f;
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Long time = sdf.parse(datestr).getTime();
			
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			long timeStart = sdf.parse(String.valueOf(year)+"-01-01 00:00:00").getTime();
			if(LAST_YEAR==null||LAST_YEAR!=timeStart){
				LAST_YEAR = timeStart;
				THE_YEAR_BEFORE_LAST = sdf.parse(String.valueOf(year-1)+"-01-01 00:00:00").getTime();
				OTHER_YEAR = sdf.parse(String.valueOf(year-2)+"-01-01 00:00:00").getTime();
			}
			if(time<LAST_YEAR)
				return (long) 0.5f;
			else if(time<THE_YEAR_BEFORE_LAST){
				return (long) 0.1f;
			}else if(time<OTHER_YEAR){
				return (long) 0.05f; 
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return score;
    }
    
    public long getScore_longTime(long time){
    	long score = (long) 1f;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
    	try {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			long timeStart = sdf.parse(String.valueOf(year)+"-01-01").getTime();
			if(LAST_YEAR==null||LAST_YEAR!=timeStart){
				LAST_YEAR = timeStart;
				THE_YEAR_BEFORE_LAST = sdf.parse(String.valueOf(year-1)+"-01-01").getTime();
				OTHER_YEAR = sdf.parse(String.valueOf(year-2)+"-01-01").getTime();
			}
			if(time<LAST_YEAR)
				return (long) 0.5f;
			else if(time<THE_YEAR_BEFORE_LAST){
				return (long) 0.1f;
			}else if(time<OTHER_YEAR){
				return (long) 0.05f; 
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return score;
    	
    }
    
    /*public static void main(String[] args) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar cal = Calendar.getInstance();
		    int day = cal.get(Calendar.DATE);
		    int month = cal.get(Calendar.MONTH) + 1;
		    int year = cal.get(Calendar.YEAR);
		    int dow = cal.get(Calendar.DAY_OF_WEEK);
		    int dom = cal.get(Calendar.DAY_OF_MONTH);
		    int doy = cal.get(Calendar.DAY_OF_YEAR);

		    System.out.println("Current Date: " + cal.getTime());
		    System.out.println("Day: " + day);
		    System.out.println("Month: " + month);
		    System.out.println("Year: " + year);
		    System.out.println("Day of Week: " + dow);
		    System.out.println("Day of Month: " + dom);
		    System.out.println("Day of Year: " + doy);
		    
			long timeStart=sdf.parse(String.valueOf(year)+"-01-01").getTime();
			System.out.println(timeStart);
			System.out.println(0.001f);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}*/
      
}  

