package frame.base.core;

import java.io.InputStream;

import frame.base.system.conf.SystemConfigPath;
import frame.base.system.conf.SystemInitConfig;

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

