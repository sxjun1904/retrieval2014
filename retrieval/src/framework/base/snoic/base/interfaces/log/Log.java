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
package framework.base.snoic.base.interfaces.log;
/**
 * 系统日志接口
 * @author 
 *
 */
public interface Log {
  /**
   * 设置日志配置文件
   * @param configFile
   */
  public void setConfigFile(String configFile);
  
  /**
   * 初始化日志
   *
   */
  public void initialize();
  
  /**
   * 设置Logger
   * @param message
   */
  public void getLogger(String message);
  
  /**
   * 设置Logger
   * @param thisClass
   */
  public void getLogger(Class thisClass);
  
  /**
   * Log type print
   * @param message
   */
  public void print(Object message);
  
  /**
   * Log type print
   * @param message
   * @param throwable
   */
  public void print(Object message,Throwable throwable);
  
  /**
   * Log type info
   * @param message
   */
  public void info(Object message);
  
  /**
   * Log type info
   * @param message
   * @param throwable
   */
  public void info(Object message,Throwable throwable);
  
  /**
   * Log type debug
   * @param message
   */
  public void debug(Object message);
  
  /**
   * Log type debug
   * @param message
   * @param throwable
   */
  public void debug(Object message,Throwable throwable);
  
  /**
   * Log type error
   * @param message
   */
  public void error(Object message);
  
  /**
   * Log type error
   * @param message
   * @param throwable
   */
  public void error(Object message,Throwable throwable);
  
  /**
   * Log type fatal
   * @param message
   */
  public void fatal(Object message);
  
  /**
   * Log type fatal
   * @param message
   * @param throwable
   */
  public void fatal(Object message,Throwable throwable);
  
  /**
   * Log type warn
   * @param message
   */
  public void warn(Object message);
  
  /**
   * Log type warn
   * @param message
   * @param throwable
   */
  public void warn(Object message,Throwable throwable);
}
