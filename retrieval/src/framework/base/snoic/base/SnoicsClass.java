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
import java.util.Map;

import framework.base.snoic.base.interfaces.log.Log;
import framework.base.snoic.base.log.LogFactory;
import framework.base.snoic.base.util.StringClass;
import framework.base.snoic.system.common.SystemCommonObjectImpl;
import framework.base.snoic.system.common.SystemCommonUtil;
import framework.base.snoic.system.conf.SystemConfigInfo;
import framework.base.snoic.system.conf.SystemConfigPath;
import framework.base.snoic.system.conf.SystemInitConfig;
import framework.base.snoic.system.interfaces.common.CommonObject;

/**
 * 获取系统对象
 * 
 * @author 
 *
 */
public class SnoicsClass implements ISnoicsClass{
	
	protected SnoicsClass(){
		
	}
	
	private CommonObject systemCommonObjectImpl = SystemCommonObjectImpl.getInstance();
	
	/**
	 * 设置Snoics路径配置文件名
	 * @param snoicsConfigPathFileName
	 */
	public void setSnoicsConfigPathFileName(String snoicsConfigPathFileName){
		SnoicsConfigPathFileNameManager.setSnoicsConfigPathFileName(snoicsConfigPathFileName);
	}
	
	/**
	 * 设置Snoics路径配置文件
	 * @param snoicsConfigPathFileName
	 */
	public void setSnoicsConfigPathFileInputStream(InputStream snoicsConfigPathFileInputStream){
		SnoicsConfigPathFileNameManager.setSnoicsConfigPathFileInputStream(snoicsConfigPathFileInputStream);
	}

	
	/**
	 * 设置Snoics路径配置对象
	 * @param systemInitConfig
	 */
	public void setSnoicsConfigPathFileSystemInitConfig(SystemInitConfig systemInitConfig){
		SnoicsConfigPathFileNameManager.setSnoicsConfigPathFileSystemInitConfig(systemInitConfig);
	}
	
	/**
	 * 获取日志工厂
	 * @return LogFactory
	 */
	public LogFactory getLogFactory(){
		LogFactory logFactory=LogFactory.getLogFactory();
		return logFactory;
	}
	
	/**
	 * 获取数据对象，每次调用一次这个方法都生成一个新的对象
	 * @return Log
	 */
	public Log getLog(){
		LogFactory logFactory=getLogFactory();
		if(logFactory==null){
			logFactory=LogFactory.getLogFactory();
		}
		Log log=logFactory.builderLog();
		return log;
	}
	
	/**
	 * 获取外部配置文件
	 * @param name
	 * @return String
	 */
	public String getConfigFileName(String name){
		SystemConfigInfo systemConfigInfo=getSystemConfigInfo();
		if(systemConfigInfo!=null){
			Map<String,String> configFilesMap=systemConfigInfo.getConfigFilesMap();
			String filename=(String)configFilesMap.get(name);
			return filename;
		}
		return null;
	}
	
	/**
	 * 获取配置文件路径
	 * @return String
	 */
	public String getConfigHome(){
		SystemConfigInfo systemConfigInfo=getSystemConfigInfo();
		if(systemConfigInfo!=null){
			return systemConfigInfo.getConfigHome();
		}else{
			SystemConfigPath systemConfigPath=SystemConfigPath.getInstance();
			return systemConfigPath.getConfigpath();
		}
	}
	
	/**
	 * 获取当前系统运行的ClassPath
	 * @return
	 */
	public String getClassPath(){
		String classPath=StringClass.getFormatPath(StringClass.getRealPath(this.getClass()));
		return classPath;
	}
	
	/**
	 * 获取系统配置参数
	 * @param key
	 * @return String
	 */
	public String getParameter(String key) {
		SystemConfigInfo systemConfigInfo=getSystemConfigInfo();
		if(systemConfigInfo!=null){
			return systemConfigInfo.getParametersInfo().getValue(key);
		}
		return null;
	}
	
	/**
	 * 获取系统公用对象管理
	 * @return CommonObject
	 */
	public CommonObject getCommonObject(){
		return systemCommonObjectImpl;
	}
	
	/**
	 * 获取系统配置信息
	 * @return SystemConfigInfo
	 */
	public SystemConfigInfo getSystemConfigInfo(){
		SystemConfigInfo systemConfigInfo=(SystemConfigInfo) (systemCommonObjectImpl.getObject(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMCONFIGINFO));
		return systemConfigInfo;
	}
}
