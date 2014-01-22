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

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import framework.base.snoic.base.interfaces.log.Log;

/**
 * 封装log4j作为系统日志
 * @author 
 *
 */
public class Log4jLog implements Log{
  private static boolean initFlag=false;
  private static Lock lock=new ReentrantLock();
  private Logger logger=null;
  private String configFile="";
  
  public Log4jLog(){
	  
  }
  
  /**
   * 初始化
   */
  public void initialize(){
	  lock.lock();
	  try{
		  if(!initFlag){
			  System.out.println("log4j config file : "+configFile);
		      DOMConfigurator.configure(configFile);
		      initFlag=true;
		  }
	  }finally{
		  lock.unlock();
	  }
  }

  /**
   * 设置配置文件
   * @param configFile 配置文件
   */
  public void setConfigFile(String configFile){
	  this.configFile=configFile;
  }
  
  /**
   * 取得配置文件
   * @return String
   */
  public String getConfigFile(){
	  return configFile;
  }
  
  /**
   * getLogger
   * @param message
   */
  public void getLogger(String message){
      logger=Logger.getLogger(message);
  }
  
  /**
   * getLogger
   * @param thisClass
   */
  public void getLogger(Class thisClass){
      logger=Logger.getLogger(thisClass);
  }
  
  /**
   * Log type print
   * @deprecated
   * @param message
   */
  public void print(Object message) {
      logger.info(getAllStackTrace(message));
  }
  
  /**
   * Log type print
   * @deprecated
   * @param message
   * @param throwable
   */
  public void print(Object message,Throwable throwable){
      logger.info(message,throwable);
  }
  
  /**
   * Log type info
   * @param message
   */
  public void info(Object message) {
	  logger.info(getAllStackTrace(message));
  }
  
  /**
   * Log type info
   * @param message
   * @param throwable
   */
  public void info(Object message,Throwable throwable){
      logger.info(message,throwable);
  }
  
  /**
   * Log type debug
   * @param message
   */
  public void debug(Object message){
	  logger.debug(getAllStackTrace(message));
  }
  
  /**
   * Log type debug
   * @param message
   * @param throwable
   */
  public void debug(Object message,Throwable throwable){
      logger.debug(message,throwable);
  }
  
  /**
   * Log type error
   * @param message
   */
  public void error(Object message){
      logger.error(getAllStackTrace(message));
  }
  
  /**
   * Log type error
   * @param message
   * @param throwable
   */
  public void error(Object message,Throwable throwable){
      logger.error(message,throwable);      
  }
  
  /**
   * Log type fatal
   * @param message
   */
  public void fatal(Object message){
      logger.fatal(getAllStackTrace(message));
  }
  
  /**
   * Log type fatal
   * @param message
   * @param throwable
   */
  public void fatal(Object message,Throwable throwable){
      logger.fatal(message,throwable);
  }
  
  /**
   * Log type warn
   * @param message
   */
  public void warn(Object message){
      logger.warn(getAllStackTrace(message));
  }
  
  /**
   * Log type warn
   * @param message
   * @param throwable
   */
  public void warn(Object message,Throwable throwable){
      logger.warn(message,throwable);
  }
  
  private String getAllStackTrace(Object object){
  	StringBuffer error=new StringBuffer();
      if(object instanceof Exception){
      	Exception exception=(Exception)object;
      	StackTraceElement[] trace = exception.getStackTrace();
      	error.append(exception.toString());
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
    	Throwable exception=(Throwable)object;
      	error.append(exception.toString());
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
      }else{
    	  error.append(object.toString());
      }
      return error.toString();
  }
}
