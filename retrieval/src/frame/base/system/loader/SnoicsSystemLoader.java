package frame.base.system.loader;

import java.io.InputStream;

import frame.base.core.BuildSnoicsClassFactory;
import frame.base.core.pool.ObjectManager;
import frame.base.system.Init;
import frame.base.system.common.SystemCommonUtil;
import frame.base.system.conf.SystemInitConfig;

/**
 * 系统载入起点
 * @author 
 *
 *
 */

public class SnoicsSystemLoader {
	private SnoicsSystemLoader(){
		
	}
	
	/**
	 * 使用默认的系统配置路径路径查找文件载入配置
	 */
	public static synchronized void load(){
		executeLoad();
	}
	
	/**
	 * 使用指定的系统配置路径路径查找文件载入配置
	 * @param snoicsConfigPathName
	 */
	public static synchronized void load(String snoicsConfigPathName){
		BuildSnoicsClassFactory.createSnoicsClass().setSnoicsConfigPathFileName(snoicsConfigPathName);
		executeLoad();
	}
	
	/**
	 * 使用指定的系统配置路径路径查找文件载入配置
	 * @param snoicsConfigPathName
	 */
	public static synchronized void load(InputStream snoicsConfigPathFileInputStream){
		BuildSnoicsClassFactory.createSnoicsClass().setSnoicsConfigPathFileInputStream(snoicsConfigPathFileInputStream);
		executeLoad();
	}
	
	/**
	 * 使用指定的系统配置对象载入配置
	 * @param snoicsConfigPathName
	 */
	public static synchronized void load(SystemInitConfig systemInitConfig){
		BuildSnoicsClassFactory.createSnoicsClass().setSnoicsConfigPathFileSystemInitConfig(systemInitConfig);
		executeLoad();
	}
	
	private static synchronized void executeLoad(){
		ObjectManager objectManager = ObjectManager.getInstance();
		String poolname=SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME;
		Init init = (Init)Init.getInstance();
		objectManager.clearPool(poolname);
		init.init();
	}
	
	
}
