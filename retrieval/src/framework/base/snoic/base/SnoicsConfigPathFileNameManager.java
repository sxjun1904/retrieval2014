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

import framework.base.snoic.system.conf.SystemConfigPath;
import framework.base.snoic.system.conf.SystemInitConfig;

/**
 *  设置Snoics路径配置文件名
 *  @author:    
 */

class SnoicsConfigPathFileNameManager {
	
	private SnoicsConfigPathFileNameManager(){
		
	}
	
	/**
	 * 设置Snoics路径配置文件名
	 * @param snoicsConfigPathFileName
	 */
	public static void setSnoicsConfigPathFileName(String snoicsConfigPathFileName){
		SystemConfigPath systemConfigPath=SystemConfigPath.getInstance();
		systemConfigPath.setConfigPathFileName(snoicsConfigPathFileName);
	}
	
	/**
	 * 设置Snoics路径配置文件名
	 * @param snoicsConfigPathFileName
	 */
	public static void setSnoicsConfigPathFileInputStream(InputStream snoicsConfigPathFileInputStream){
		SystemConfigPath systemConfigPath=SystemConfigPath.getInstance();
		systemConfigPath.setConfigPathInputStream(snoicsConfigPathFileInputStream);
	}
	
	/**
	 * 设置Snoics路径配置对象
	 * @param snoicsConfigPathFileName
	 */
	public static void setSnoicsConfigPathFileSystemInitConfig(SystemInitConfig systemInitConfig){
		SystemConfigPath systemConfigPath=SystemConfigPath.getInstance();
		systemConfigPath.setSystemInitConfig(systemInitConfig);
	}
}

