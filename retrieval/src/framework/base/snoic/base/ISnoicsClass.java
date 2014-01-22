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
package framework.base.snoic.base;

import java.io.InputStream;

import framework.base.snoic.base.interfaces.log.Log;
import framework.base.snoic.base.log.LogFactory;
import framework.base.snoic.system.conf.SystemConfigInfo;
import framework.base.snoic.system.conf.SystemInitConfig;
import framework.base.snoic.system.interfaces.common.CommonObject;

/**
 * 
 *  @author:    
 */

public interface ISnoicsClass {
	/**
	 * 设置Snoics路径配置文件名
	 * @param snoicsConfigPathFileName
	 */
	public void setSnoicsConfigPathFileName(String snoicsConfigPathFileName);
	
	/**
	 * 设置Snoics路径配置文件
	 * @param snoicsConfigPathFileName
	 */
	public void setSnoicsConfigPathFileInputStream(InputStream snoicsConfigPathFileInputStream);
	
	/**
	 * 设置Snoics路径配置对象
	 * @param systemInitConfig
	 */
	public void setSnoicsConfigPathFileSystemInitConfig(SystemInitConfig systemInitConfig);
	
	/**
	 * 获取日志工厂
	 * @return LogFactory
	 */
	public LogFactory getLogFactory();
	
	/**
	 * 获取数据对象，每次调用一次这个方法都生成一个新的对象
	 * @return Log
	 */
	public Log getLog();
	
	/**
	 * 获取外部配置文件
	 * @param name
	 * @return String
	 */
	public String getConfigFileName(String name);
	
	/**
	 * 获取配置文件路径
	 * @return String
	 */
	public String getConfigHome();
	
	/**
	 * 获取当前系统运行的ClassPath
	 * @return
	 */
	public String getClassPath();
	
	/**
	 * 获取系统配置参数
	 * @param key
	 * @return String
	 */
	public String getParameter(String key);
	
	/**
	 * 获取系统公用对象管理
	 * @return CommonObject
	 */
	public CommonObject getCommonObject();
	
	/**
	 * 获取系统配置信息
	 * @return SystemConfigInfo
	 */
	public SystemConfigInfo getSystemConfigInfo();
}

