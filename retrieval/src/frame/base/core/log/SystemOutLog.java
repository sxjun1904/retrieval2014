package frame.base.core.log;

import java.io.PrintStream;
import java.util.Date;

import frame.base.core.interfaces.log.Log;
import frame.base.core.util.DateTime;
import frame.base.core.util.file.FileHelper;
/**
 * 捕获控制台所有的信息
 * @author 
 *
 */
public class SystemOutLog implements Log{

	private String showtime=SHOWTIME_ON;

	private final static String SHOWTIME_ON="ON";
	
	private static DateTime theDateTime=new DateTime();
	
    private String messageName="";

    private String logfile="";

    private int logType = 0;
    
    private FileHelper snoicsFile = new FileHelper();
    
    //前一次的时间
    private static long predatelongtime=-1;
    
    private DateTime dateTime=new DateTime();
    
    //前一次的日期
    private static String logDate=null;
    
    private static final long DATELONGTIME=1000*60*60*24;
    
    /*
     * 日志级别,用来控制日志信息的输出。
		Level_ALL < Level_DEBUG < Level_INFO < Level_WARN < Level_ERROR < Level_FATAL < Level_OFF
     */
    public static final int Level_OFF=0;
    public static final int Level_FATAL=1;
    public static final int Level_ERROR=2;
    public static final int Level_WARN=3;
    public static final int Level_INFO=4;
    public static final int Level_DEBUG=5;
    public static final int Level_ALL=6;
    
    
    /**
     * 设置配置文件
     * @param configFile 配置文件名
     */
    public void setConfigFile(String configFile){
    	
    }
    
    /**
     * 设置是否要显示NORMAL时间
     * @param showtime
     */
    public void setShowtime(String showtime) {
    	this.showtime=showtime.toUpperCase();
    }
    
    /**
     * 设置日志文件
     * @param logfile 日志文件
     */
    public void setLogfile(String logfile){
    	this.logfile=logfile;
    }
    
    /**
     * 设置日志类型
     * @param printType  日志类型
     */
    public void setPrintType(int printType){
    	this.logType = printType;
    }
    
    /**
     * 初始化
     */
    public void initialize(){
        init();
    }
    
    /**
     * 初始化
     */
    private void init(){
    	if(predatelongtime==-1){
    		String nowdatetime=dateTime.getNowDate();
    		Date nowdate=dateTime.parseDate(nowdatetime,null);
    		long thelongtime=nowdate.getTime();
    		predatelongtime=thelongtime;
    		logDate=dateTime.getNowDate();
    	}
    }
    
    /**
     * getLogger
     * @param messageName
     */
    public void getLogger(String messageName){
        this.messageName=messageName;
    }
    
    /**
     * getLogger
     * @param thisClass
     */
    public void getLogger(Class thisClass){
        this.messageName=thisClass.getName();
    }

    /**
     * Log type fatal
     * @param msg
     */
    public void fatal(Object msg){
    	
        switch (logType) {
        case Level_ALL:
        case Level_DEBUG:
        case Level_INFO:
        case Level_WARN:
        case Level_ERROR:
        case Level_FATAL:
        	String time="";
        	time=getShowTime();
            renameLogFile();
            StringBuffer stringBuffer=new StringBuffer();
            if(msg instanceof Exception){
            	stringBuffer.append(time);
            	stringBuffer.append(" [FATAL] ");
            	stringBuffer.append(messageName);
            	stringBuffer.append(" : ");
            	System.out.println(stringBuffer.toString());
                ((Exception)msg).printStackTrace();
            }else if(msg instanceof Throwable){
            	stringBuffer.append(time);
            	stringBuffer.append(" [FATAL] ");
            	stringBuffer.append(messageName);
            	stringBuffer.append(" : ");
            	System.out.println(stringBuffer.toString());
                ((Throwable)msg).printStackTrace();
            }else{
            	stringBuffer.append(time);
            	stringBuffer.append(" [FATAL] ");
            	stringBuffer.append(messageName);
            	stringBuffer.append(" : ");
            	stringBuffer.append(msg);
            	System.out.println(stringBuffer.toString());
            }
            break;
        default:
            break;
        }
    }
    
    /**
     * Log type fatal
     * @param message
     * @param throwable
     */
    public void fatal(Object message,Throwable throwable){
    	
        switch (logType) {
        case Level_ALL:
        case Level_DEBUG:
        case Level_INFO:
        case Level_WARN:
        case Level_ERROR:
        case Level_FATAL:
        	String time="";
        	time=getShowTime();
            renameLogFile();
            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [FATAL] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
        	stringBuffer.append(message);
            System.out.println(stringBuffer.toString());
            throwable.printStackTrace();
            break;
        default:
            break;
        }
    }
    
    /**
     * Log type error
     * @param message
     */
    public void error(Object message){
    	
        switch (logType) {
        case Level_ALL:
        case Level_DEBUG:
        case Level_INFO:
        case Level_WARN:
        case Level_ERROR:
        	String time="";
        	time=getShowTime();
            renameLogFile();
            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [ERROR] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
            if(message instanceof Exception){
            	System.out.println( stringBuffer.toString() );
                ((Exception)message).printStackTrace();
            }else if(message instanceof Throwable){
            	System.out.println( stringBuffer.toString() );
                ((Throwable)message).printStackTrace();
            }else{
            	stringBuffer.append(message);
                System.out.println( stringBuffer.toString() );
            }
            break;
        default:
            break;
        }
    }
    
    /**
     * Log type debug
     * @param message
     * @param throwable
     */
    public void error(Object message,Throwable throwable){
    	
    	switch (logType) {
        case Level_ALL:
        case Level_DEBUG:
        case Level_INFO:
        case Level_WARN:
        case Level_ERROR:
        	String time="";
        	time=getShowTime();
            renameLogFile();
            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [ERROR] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
        	stringBuffer.append(message);
            System.out.println( stringBuffer.toString() );
            throwable.printStackTrace();
            break;
        default: 
            break;
        }
    }
    
    /**
     * Log type warn
     * @param message
     */
    public void warn(Object message){
    	
        switch (logType) {
        case Level_ALL:
        case Level_DEBUG:
        case Level_INFO:
        case Level_WARN:
        	String time="";
        	time=getShowTime();
            renameLogFile();
            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [WARN] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
            if(message instanceof Exception){
            	System.out.println( stringBuffer.toString() );
                ((Exception)message).printStackTrace();
            }else if(message instanceof Throwable){
            	System.out.println( stringBuffer.toString() );
                ((Throwable)message).printStackTrace();
            }else{
            	stringBuffer.append(message);
                System.out.println( stringBuffer.toString() );
            }
            break;
        default:
            break;
        }
    }
    
    /**
     * Log type warn
     * @param message
     * @param throwable
     */
    public void warn(Object message,Throwable throwable){
    	
        switch (logType) {
        case Level_ALL:
        case Level_DEBUG:
        case Level_INFO:
        case Level_WARN:
        	String time="";
        	time=getShowTime();
            renameLogFile();
            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [WARN] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
        	stringBuffer.append(message);
            System.out.println(stringBuffer.toString());
            throwable.printStackTrace();
            break;
        default:
            break;
        }
    }
    
    /**
     * Log type info
     * @param message
     */
    public void info(Object message) {
    	
        switch (logType) {
        case Level_ALL:
        case Level_DEBUG:
        case Level_INFO:
        	String time="";
        	time=getShowTime();
            renameLogFile();
            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [INFO] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
            if(message instanceof Exception){
            	System.out.println(stringBuffer.toString());
                ((Exception)message).printStackTrace();
            }else if(message instanceof Throwable){
            	System.out.println(stringBuffer.toString());
                ((Throwable)message).printStackTrace();
            }else{
            	stringBuffer.append(message);
                System.out.println(stringBuffer.toString());
            }
            break;
        default:
            break;
        }
    }
    
    /**
     * Log type info
     * @param message
     * @param throwable
     */
    public void info(Object message,Throwable throwable){
        switch (logType) {
        case Level_ALL:
        case Level_DEBUG:
        case Level_INFO:
        	String time="";
        	time=getShowTime();
            renameLogFile();
            
            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [INFO] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
        	stringBuffer.append(message);
        	
            System.out.println(stringBuffer.toString());
            
            throwable.printStackTrace();
            break;
        default:
            break;
        }
    }
    
    /**
     * 将文本信息写入日志文件
     * @deprecated
     * @param msg
     *            日志信息
     */
    public void print(Object msg) {
    	info(msg);
    }

    /**
     * 将文本信息与异常写入日志文件
     * @deprecated
     * @param e
     *            错误信息
     * @param msg
     *            日志信息
     */
    public void print(Object msg,Throwable e) {
    	info(msg,e);
    }

    /**
     * 将文本信息与异常写入日志文件
     * @deprecated
     * @param e
     *            错误信息
     */
    public void print(Throwable e) {
        switch (logType) {
        case Level_ALL:
        case Level_DEBUG:
        case Level_INFO:
            renameLogFile();
            System.out.println(messageName+"error information : "+e);
            break;
        default: 
            break;
        }
    }
    
    /**
     * Log type debug
     * @param message
     */
    public void debug(Object message){
    	
        switch (logType) {
        case Level_ALL:
        case Level_DEBUG:
        	String time="";
        	time=getShowTime();
            renameLogFile();
            
            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [DEBUG] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
        	
            if(message instanceof Exception){
            	System.out.println(stringBuffer.toString());
                ((Exception)message).printStackTrace();
            }else if(message instanceof Throwable){
            	System.out.println(stringBuffer.toString());
                ((Throwable)message).printStackTrace();
            }else{
            	stringBuffer.append(message);
                System.out.println(stringBuffer.toString());
            }
            break;
        default:
            break;
        }
    }
    
    /**
     * Log type debug
     * @param message
     * @param throwable
     */
    public void debug(Object message,Throwable throwable){
    	
        switch (logType) {
        case Level_ALL:
        case Level_DEBUG:
        	String time="";
        	time=getShowTime();
            renameLogFile();
            
            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [DEBUG] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
        	stringBuffer.append(message);
        	
            System.out.println(stringBuffer.toString());
            
            throwable.printStackTrace();
            break;
        default:
            break;
        }
    }

    /**
     * 每天新建一个日志文件
     *
     */
    private synchronized void renameLogFile() {
    	long currentTime=System.currentTimeMillis();
    	if(currentTime-predatelongtime<DATELONGTIME){
    		return;
    	}else{
    		String nowdatetime=dateTime.getNowDate();
    		Date nowdate=dateTime.parseDate(nowdatetime,null);
    		long thelongtime=nowdate.getTime();
    		predatelongtime=thelongtime;
//    		synchronized (this) {
    			snoicsFile.stringToFile(snoicsFile.fileToString(logfile), logfile + logDate);
    			snoicsFile.renameFile(logfile, logfile + logDate);
    			snoicsFile.stringToFile("",logfile);
    			logDate=dateTime.getNowDate();
        		PrintStream printStreamOut=SystemOut.getPrintStreamOut();
        		PrintStream printStreamErr=SystemOut.getPrintStreamErr();
    			printStreamOut.flush();
    			printStreamErr.flush();
//    		}
    	}
	}
    
    /**
     * 获取时间格式
     * @return String
     */
    private String getShowTime(){
//    	DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG, Locale.getDefault());
    	String time="";
    	if(showtime.equalsIgnoreCase(SHOWTIME_ON)) {
    		time=theDateTime.getNowDateTime();
    	}
    	return time;
    }
}
