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
package framework.base.snoic.base.log;

import framework.base.snoic.base.interfaces.log.Log;
import framework.base.snoic.base.util.StringClass;
/**
 * LogFactory生成日志对象
 * @author 
 *
 */
public class LogFactory {
	private static LogFactory instance=new LogFactory();
	
    private static final String LOG_TYPE_LOG4J="LOG4J";
    private static final String LOG_TYPE_NORMAL="NORMAL";
    private static final String LOG_TYPE_OVERRIDESYSTEMOUT="OVERRIDESYSTEMOUT";
    private String systemLogType=LOG_TYPE_NORMAL;
    private String logfile="";
    private String showtime=NormalLog.SHOWTIME_ON;
    
    private int logType = NormalLog.Level_ALL;
    
    private LogFactory(){
    	
    }
    
    /**
     * 获取日志工厂
     * @return LogFactory
     */
    public static LogFactory getLogFactory(){
		return instance;
    }
    
    /**
     * 初始化Log4j日志工厂
     * @param configfile
     * @param initLog4j
     */
    public void builderFactory(String configfile,String initLog4j){
    	systemLogType=LOG_TYPE_LOG4J;
//    	System.out.println("initLog4j----------------------------------================"+initLog4j);
    	if(initLog4j.equalsIgnoreCase("true")){
    		Log4jLog log4jLog=new Log4jLog();
			log4jLog.setConfigFile(configfile);
    		log4jLog.initialize();
    	}
    }
    
    /**
     * 初始化Log4j日志工厂
     * @param configfile
     */
    public void builderFactory(String configfile){
    	systemLogType=LOG_TYPE_LOG4J;
		Log4jLog log4jLog=new Log4jLog();
		log4jLog.setConfigFile(configfile);
		log4jLog.initialize();
    }
    
    /**
     * 初始化normal日志工厂
     * @param logfile 记录日志的文件
     * @param logType 日志打印级别
     * @param initLog4j 是否初始化log4j
     * @param showtime 是否显示NORMAL日志的时间
     */
    public void builderFactory(String logfile,String logType,String showtime){
    	systemLogType=LOG_TYPE_NORMAL;
    	this.logfile=logfile;
    	this.showtime=showtime;
    	try{
    		this.logType=Integer.parseInt(logType);
    	}catch(Exception e){
    		this.logType =NormalLog.Level_ALL;
    	}
    }
    
    /**
     * 初始化OVERRIDESYSTEMOUT日志工厂
     * @param logfile 记录日志的文件
     * @param logType 日志打印级别
     * @param showtime 是否显示NORMAL日志的时间
     */
    public void builderFactorySystemOut(String logfile,String logType,String showtime){
    	systemLogType=LOG_TYPE_OVERRIDESYSTEMOUT;
    	this.logfile=logfile;
    	this.showtime=showtime;
    	try{
    		this.logType=Integer.parseInt(logType);
    	}catch(Exception e){
    		this.logType =NormalLog.Level_ALL;
    	}
    }
    
    /**
     * 从工厂中取得一个日志对象
     * @return Log
     */
    public Log builderLog(){
    	Log log=null;
    	if(systemLogType.equalsIgnoreCase(LOG_TYPE_LOG4J)){
    		Log4jLog log4jLog=new Log4jLog();
    		log=log4jLog;
    	}else if(systemLogType.equalsIgnoreCase(LOG_TYPE_NORMAL)){
    		NormalLog normalLog=new NormalLog(logfile,logType);
    		if(!StringClass.getString(showtime).equals("")){
        		normalLog.setShowtime(showtime);
    		}
    		log=normalLog;
    	}else if(systemLogType.equalsIgnoreCase(LOG_TYPE_OVERRIDESYSTEMOUT)) {
        	SystemOutLog systemOutLog=new SystemOutLog();
        	systemOutLog.setLogfile(logfile);
        	systemOutLog.setPrintType(logType);
        	systemOutLog.setShowtime(showtime);
        	systemOutLog.initialize();
        	log=systemOutLog;
    	}
    	return log;
    }
}
