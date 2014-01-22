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
package framework.base.snoic.system.conf;

import java.util.List;
import java.util.Map;

import framework.base.snoic.system.common.SystemCommonUtil;
import framework.base.snoic.system.interfaces.DestroySystem;
import framework.base.snoic.system.interfaces.InitSystem;
/**
 * 储存系统初始化信息
 * @author 
 *
 */
public class SystemConfigInfo {
	private String systemlogtype="";
	
	private String logfile="";
	
	private String logtype="";
	
	private String showtime="";
	
	private Map configFilesMap=null;
		
	private InitSystem[] initClass=null;
	
	private String autodestroy=null;
	
	private DestroySystem[] destroySystem=null;
	
	private List systemThreads=null;
	
	private ParametersInfo parametersInfo=null;
	
	/**
	 * @return Returns the parametersInfo.
	 */
	public ParametersInfo getParametersInfo() {
		return parametersInfo;
	}

	/**
	 * @param parametersInfo The parametersInfo to set.
	 */
	public void setParametersInfo(ParametersInfo parametersInfo) {
		this.parametersInfo = parametersInfo;
	}

	/**
	 * 设置日志类型
	 * @param systemlogtype
	 */
	public void setSystemlogtype(String systemlogtype) {
		this.systemlogtype=systemlogtype;
	}
	
	/**
	 * 获取日志类型
	 * @return String
	 */
	public String getSystemlogtype() {
		return systemlogtype;
	}
	
	/**
	 * 设置存放日志文件
	 * @param logfile
	 */
	public void setLogfile(String logfile) {
		this.logfile=logfile;
	}
	
	/**
	 * 获取存放日志文件
	 * @return String
	 */
	public String getLogfile() {
		return logfile;
	}
	
	/**
	 * 设置日志打印类型
	 * @param logtype
	 */
	public void setLogtype(String logtype) {
		this.logtype=logtype;
	}
	
	/**
	 * 获取日志打印类型
	 * @return String
	 */
	public String getLogtype() {
		return logtype;
	}
	
	/**
	 * 设置是否显示NORMAL日志时间
	 * @param showtime
	 */
	public void setShowtime(String showtime) {
		this.showtime=showtime;
	}
	
	/**
	 * 获取是否显示NORMAL日志时间
	 * @return String
	 */
	public String getShowtime() {
		if(showtime.equals("")) {
			showtime="OFF";
		}
		return showtime;
	}
	
	/**
	 * 设置系统外部初始化类
	 * @param initClass
	 */
	public void setInitClass(InitSystem[] initClass) {
		this.initClass=initClass;
	}
	
	/**
	 * 获取系统外部初始化类
	 * @return InitSystem[]
	 */
	public InitSystem[] getInitClass() {
		return initClass;
	}
	
	/**
	 * 设置是否自动摧毁系统
	 * @param autodestroy
	 */
	public void setAutodestroy(String autodestroy){
		this.autodestroy=autodestroy;
	}
	
	/**
	 * 获取是否自动摧毁系统
	 * @return String
	 */
	public String getAutodestroy(){
		if(autodestroy.equals("")){
			autodestroy=SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_INIT_NODE_AUTODESTROY_OFF;
		}
		return autodestroy;
	}
	
	/**
	 * 设置摧毁系统的外部类
	 * @param destroySystem
	 */
	public void setDestroySystem(DestroySystem[] destroySystem) {
		this.destroySystem=destroySystem;
	}
	
	/**
	 * 获取摧毁系统的外部类
	 * @return DestroySystem[]
	 */
	public DestroySystem[] getDestroySystem() {
		return destroySystem;
	}
	
	/**
	 * 设置系统内置线程信息
	 * @param systemThreads
	 */
	public void setSystemThreads(List systemThreads) {
		this.systemThreads=systemThreads;
	}
	
	/**
	 * 获取系统内置线程信息
	 * @return systemThreads
	 */
	public List getSystemThreads() {
		return systemThreads;
	}

	/**
	 * 获取外部配置文件
	 * @return Returns the configFilesMap.
	 */
	public Map getConfigFilesMap() {
		return configFilesMap;
	}

	/**
	 * 设置外部配置文件
	 * @param configFilesMap The configFilesMap to set.
	 */
	public void setConfigFilesMap(Map configFilesMap) {
		this.configFilesMap = configFilesMap;
	}

	/**
	 * 获取系统配置路径
	 * @return String
	 */
	public String getConfigHome() {
		SystemConfigPath systemConfigPath=SystemConfigPath.getInstance();
		return systemConfigPath.getConfigpath();
	}
}
