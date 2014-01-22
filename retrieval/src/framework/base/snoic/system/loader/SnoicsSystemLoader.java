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
package framework.base.snoic.system.loader;

import java.io.InputStream;

import framework.base.snoic.base.BuildSnoicsClassFactory;
import framework.base.snoic.base.pool.ObjectManager;
import framework.base.snoic.system.Init;
import framework.base.snoic.system.common.SystemCommonUtil;
import framework.base.snoic.system.conf.SystemInitConfig;

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
