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

import framework.base.snoic.base.util.StringClass;
import framework.base.snoic.system.common.SystemCommonUtil;

/**
 * 系统参数设置
 * 
 * @author 
 * 
 */
public class SystemConfigFileName {

	private String configPath = "";

	private SystemConfig systemConfig = SystemConfig.newInstance();

	private String systemConfigfile = "";

	private String log4jConfigfile = "";

	private SystemConfigPath systemConfigPath = SystemConfigPath.getInstance();

	public SystemConfigFileName() {
		
	}

	/**
	 * 初始化配置文件名
	 */
	public void initConfigPath() {
		if (systemConfigPath.getConfigpath().equals("")) {
			systemConfigPath.init();
		}
		if (systemConfigPath.getConfigpath().equals("")) {
			return;
		}
		String configPath = StringClass.getFormatPath(systemConfigPath
				.getConfigpath()
				+ "/");
		this.configPath = configPath;
		systemConfigfile = configPath + systemConfigPath.getSystemFilename();
		systemConfig.setSystemConfigFile(systemConfigfile);
		
		log4jConfigfile = configPath
				+ systemConfig
						.getConfigFile(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_LOG4JCONFIG);
	}

	/**
	 * 取得配置文件的存放的相对路径
	 * 
	 * @return String 配置文件的存放的相对路径
	 */
	public String getConfigPath() {
		return configPath;
	}

	/**
	 * 设置配置文件的存放的相对路径
	 * 
	 * @param configPath
	 *            配置文件的存放的相对路径
	 */
	public void setConfigPath(String configPath) {
		configPath = StringClass.getFormatPath(configPath);
		this.configPath = configPath;
	}

	/**
	 * 取得系统配置文件名称
	 * 
	 * @return String
	 */
	public String getSystemConfigfile() {
		return systemConfigfile;
	}

	/**
	 * 获得系统配置文件名称
	 * 
	 * @param systemConfigfile
	 */
	public void setSystemConfigfile(String systemConfigfile) {
		this.systemConfigfile = systemConfigfile;
	}

	/**
	 * 获取LOG4J配置文件
	 * 
	 * @return String
	 */
	public String getLog4jConfigfile() {
		return log4jConfigfile;
	}

	/**
	 * 设置LOG4J配置文件
	 * 
	 * @param log4jConfigfile
	 */
	public void setLog4jConfigfile(String log4jConfigfile) {
		this.log4jConfigfile = log4jConfigfile;
	}
}