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
package framework.base.snoic.system;

import framework.base.snoic.system.common.SystemCommonObjectImpl;
import framework.base.snoic.system.common.SystemCommonUtil;
import framework.base.snoic.system.conf.SystemConfigInfo;
import framework.base.snoic.system.interfaces.DestroySystem;
import framework.base.snoic.system.interfaces.IInit;
import framework.base.snoic.system.interfaces.InitSystem;
/**
 * 初始化系统
 * @author 
 *
 */
public class Init implements IInit{
	private static IInit instance = new Init(); // 唯一实例
	private String instanceLazyFlag = null; // 唯一实例
	private String instanceFlag = null; // 唯一实例
	private Init(){
		
	}
	
	/**
	 * @return Returns the instance.
	 */
	public static IInit getInstance() {
		return instance;
	}

	/**
	 * @return Returns the instanceLazy.
	 */
	public String getInstanceLazyFlag() {
		return instanceLazyFlag;
	}

	/**
	 * @return Returns the instanceFlag.
	 */
	public String getInstanceFlag() {
		return instanceFlag;
	}

	/**
	 * 系统初始化,在系统已经被初始化过的情况下，不会再被初始化
	 */
	public synchronized void init(){
		if(instanceFlag==null) {
	        instanceFlag="initialize";
	        InitSystem initSystem = new InitSystemImpl();
	        initSystem.initialize();
		}
    }
	
	/**
	 * 强制系统重新初始化
	 */
	public synchronized void reInit(){
        instanceFlag="initialize";
        InitSystem initSystem = new InitSystemImpl();
        initSystem.initialize();
    }
	
	/**
	 * 强制摧毁系统，将会摧毁整个系统管理的所有的对象以及所有的初始化信息
	 *
	 */
	public synchronized void destroySystem() {
		SystemCommonObjectImpl systemCommonObjectImpl = SystemCommonObjectImpl.getInstance();
		SystemConfigInfo systemConfigInfo = ((SystemConfigInfo)(systemCommonObjectImpl.getObject(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMCONFIGINFO)));
		String autodestroy=systemConfigInfo.getAutodestroy();
		if(autodestroy.equalsIgnoreCase(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_INIT_NODE_AUTODESTROY_ON)){
			System.out.println("+------------------------+");
			System.out.println("|  Destory System.....   |");
			System.out.println("+------------------------+");
			DestroySystem destroySystem = new DestroySystemImpl();
			destroySystem.destroyAll();
	        instance=null;
			System.out.println("+------------------------+");
			System.out.println("| Destory System success |");
			System.out.println("+------------------------+");
		}
	}
}
