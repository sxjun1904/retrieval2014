package frame.base.core.log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import frame.base.core.interfaces.log.Log;
import frame.base.core.util.DateTime;
import frame.base.core.util.StringClass;
import frame.base.core.util.file.FileHelper;
/**
 * 普通日志
 * @author 
 *
 */
public class NormalLog implements Log{

	public final static String SHOWTIME_ON="ON";
	
	private static DateTime theDateTime=new DateTime();

	private String showtime=SHOWTIME_ON;
	
    private String messageName="";

    private static PrintWriter printWriter=null;

    private String logfile="";
    
    private FileHelper snoicsFile = new FileHelper();

    private int logType = 0;
    
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

    public NormalLog(){
    	init();
    }
    
    /**
     * newInstance
     * @param logfile 日志文件
     * @param logType 日志类型
     */
    public NormalLog(String logfile, int logType) {
    	logfile=StringClass.getString(logfile);
        this.logfile=logfile;
        this.logType = logType;
        initialize();
        init();
    }
    
    /**
     * 初始化
     */
    private synchronized void init(){
    	if(predatelongtime==-1){
    		String nowdatetime=dateTime.getNowDate();
    		Date nowdate=dateTime.parseDate(nowdatetime,null);
    		long thelongtime=nowdate.getTime();
    		predatelongtime=thelongtime;
    		logDate=dateTime.getNowDate();
    	}
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
     * 设置配置文件
     * @param configFile 配置文件名
     */
    public void setConfigFile(String configFile){
    	
    }
	
    /**
     * 初始化
     */
    public void initialize(){
    	if(!logfile.equals("")) {
        	if(printWriter==null) {
                if (logType > 0) {
                	logfile = StringClass.getFormatPath(logfile);
                    try {
                        boolean flag = snoicsFile.isFile(logfile);
                        boolean createfileFlag=false;
                        if (!flag) {
                        	createfileFlag=snoicsFile.createFile(logfile);
                        }else {
                        	createfileFlag=true;
                        }
                        if(createfileFlag) {
                        	if(printWriter==null){
                            	printWriter = new PrintWriter(new FileWriter(logfile, true),true);
                        	}
                        }else if(!createfileFlag){
                        	printWriter=null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        	}
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
     * @param message
     */
    public void fatal(Object message){
    	
        switch (logType) {
        case Level_ALL:
        case Level_DEBUG:
        case Level_INFO:
        case Level_WARN:
        case Level_ERROR:
        case Level_FATAL:
        	String time="";
        	time=getShowTime();

            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [FATAL] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
        	
            if(message instanceof Exception){
            	System.out.println(stringBuffer.toString());
                ((Exception)message).printStackTrace();
            }else if(message instanceof Throwable){
            	System.out.println(stringBuffer.toString());
                ((Throwable)message).printStackTrace();
            }else{
                System.out.println(stringBuffer.toString());
            }
            
            if(printWriter!=null) {
                renameLogFile();
                
                if(message instanceof Exception){
                	stringBuffer.append(getAllStackTrace(message));
                	printWriter.println(stringBuffer.toString());
                }else if(message instanceof Throwable){
                	stringBuffer.append(getAllStackTrace(message));
                	printWriter.println(stringBuffer.toString());
                }else{
                	printWriter.println(stringBuffer.toString());
                }
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
        	
            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [FATAL] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
        	stringBuffer.append(message);
        	
            System.out.println(stringBuffer.toString());
            throwable.printStackTrace();
            
            if(printWriter!=null) {
                renameLogFile();
            	stringBuffer.append("\n");
            	stringBuffer.append(getAllStackTrace(throwable));
            	printWriter.println(stringBuffer.toString());
            }
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

            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [ERROR] ");
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
            
            if(printWriter!=null) {
                renameLogFile();
                
                if(message instanceof Exception){
                	stringBuffer.append(getAllStackTrace(message));
                	printWriter.println(stringBuffer.toString());
                }else if(message instanceof Throwable){
                	stringBuffer.append(getAllStackTrace(message));
                	printWriter.println(stringBuffer.toString());
                }else{
                	printWriter.println(stringBuffer.toString());
                }
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
        	
            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [ERROR] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
        	stringBuffer.append(message);
        	
            System.out.println(stringBuffer.toString());
            throwable.printStackTrace();
            
            if(printWriter!=null) {
                renameLogFile();
            	stringBuffer.append("\n");
            	stringBuffer.append(getAllStackTrace(throwable));
                printWriter.println(stringBuffer.toString());
            }
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

            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [WARN] ");
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
            
            if(printWriter!=null) {
                renameLogFile();
                if(message instanceof Exception){
                	stringBuffer.append(getAllStackTrace(message));
                	printWriter.println(stringBuffer.toString());
                }else if(message instanceof Throwable){
                	stringBuffer.append(getAllStackTrace(message));
                	printWriter.println(stringBuffer.toString());
                }else{
                	printWriter.println(stringBuffer.toString());
                }
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

            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [WARN] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
        	stringBuffer.append(message);
        	
            System.out.println(stringBuffer.toString());
            throwable.printStackTrace();
            
            if(printWriter!=null) {
                renameLogFile();
            	stringBuffer.append("\n");
            	stringBuffer.append(getAllStackTrace(message));
                printWriter.println(stringBuffer.toString());
            }
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
            
            if(printWriter!=null) {
                renameLogFile();
                if(message instanceof Exception){
                	stringBuffer.append(getAllStackTrace(message));
                	printWriter.println(stringBuffer.toString());
                }else if(message instanceof Throwable){
                	stringBuffer.append(getAllStackTrace(message));
                	printWriter.println(stringBuffer.toString());
                }else{
                	printWriter.println(stringBuffer.toString());
                }
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

            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [INFO] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
        	stringBuffer.append(message);

            System.out.println(stringBuffer.toString());
            throwable.printStackTrace();
            
            if(printWriter!=null) {
                renameLogFile();
            	stringBuffer.append("\n");
            	stringBuffer.append(getAllStackTrace(message));
                printWriter.println(stringBuffer.toString());
            }
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
            //System.out.println("----------------------------------------------");
            System.out.println(messageName+"error information : ");
            e.printStackTrace();
            //printWriter.println("----------------------------------------------");
            if(printWriter!=null) {
                renameLogFile();
                printWriter.println(messageName+"error information : ");
                e.printStackTrace(printWriter);
            }
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
            
            if(printWriter!=null) {
                renameLogFile();
                
                if(message instanceof Exception){
                	stringBuffer.append(getAllStackTrace(message));
                	printWriter.println(stringBuffer.toString());
                }else if(message instanceof Throwable){
                	stringBuffer.append(getAllStackTrace(message));
                	printWriter.println(stringBuffer.toString());
                }else{
                	printWriter.println(stringBuffer.toString());
                }
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

            StringBuffer stringBuffer=new StringBuffer();
        	stringBuffer.append(time);
        	stringBuffer.append(" [DEBUG] ");
        	stringBuffer.append(messageName);
        	stringBuffer.append(" : ");
        	stringBuffer.append(message);

            System.out.println(stringBuffer.toString());
            throwable.printStackTrace();
            
            if(printWriter!=null) {
                renameLogFile();
            	stringBuffer.append("\n");
            	stringBuffer.append(getAllStackTrace(throwable));
                printWriter.println(stringBuffer.toString());
            }
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
    		synchronized (printWriter) {
    			printWriter.close();
    			snoicsFile.renameFile(logfile, logfile + logDate);
    			logDate=dateTime.getNowDate();
    			boolean flag = snoicsFile.isFile(logfile);
    			if (!flag) {
    				snoicsFile.createFile(logfile);
    			}
    			try {
    				printWriter = new PrintWriter(new FileWriter(logfile, true),true);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
	}
    
    private String getAllStackTrace(Object object){
    	StringBuffer error=new StringBuffer();
        if(object instanceof Exception){
        	Exception exception=(Exception)object;
        	StackTraceElement[] trace = exception.getStackTrace();
            for (int i=0; i < trace.length; i++){
            	error.append("\nat ");
            	error.append(trace[i]);
            }
            Throwable ourCause = exception.getCause();
            if(ourCause!=null){
                trace=ourCause.getStackTrace();
                for (int i=0; i < trace.length; i++){
                	error.append("\nat ");
                	error.append(trace[i]);
                }
            }
        }else if(object instanceof Throwable){
        	Exception exception=(Exception)object;
        	StackTraceElement[] trace = exception.getStackTrace();
            for (int i=0; i < trace.length; i++){
            	error.append("\nat ");
            	error.append(trace[i]);
            }
            Throwable ourCause = exception.getCause();
            if(ourCause!=null){
                trace=ourCause.getStackTrace();
                for (int i=0; i < trace.length; i++){
                	error.append("\nat ");
                	error.append(trace[i]);
                }
            }
        }
        return error.toString();
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