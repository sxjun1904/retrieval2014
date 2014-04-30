package frame.base.core;

import java.io.InputStream;

import frame.base.core.interfaces.log.Log;
import frame.base.core.log.LogFactory;
import frame.base.system.conf.SystemConfigInfo;
import frame.base.system.conf.SystemInitConfig;
import frame.base.system.interfaces.common.CommonObject;

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

