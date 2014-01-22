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

import framework.base.snoic.base.SnoicsClass;
import framework.base.snoic.base.interfaces.log.Log;
import framework.base.snoic.base.pool.ObjectManager;
import framework.base.snoic.system.common.SystemCommonObjectImpl;
import framework.base.snoic.system.common.SystemCommonUtil;
import framework.base.snoic.system.conf.SystemConfigInfo;
import framework.base.snoic.system.interfaces.DestroySystem;
/**
 * 摧毁系统中所有的对象
 * @author 
 *
 */
public class DestroySystemImpl extends SnoicsClass implements DestroySystem{
	
	private Log log=getLog();
	
	public DestroySystemImpl() {
		
	}
	
	/**
	 * 摧毁系统中所有的对象
	 *
	 */
	public void destroyAll() {
		destoryDestroyClass();
		log.info("进行系统中的 destroy 操作");
		destroyObjectManager();
	}
	
	/**
	 * 进行外部destroy操作
	 *
	 */
	public void destoryDestroyClass() {
		SystemCommonObjectImpl systemCommonObjectImpl = SystemCommonObjectImpl.getInstance();
		SystemConfigInfo systemConfigInfo=(SystemConfigInfo)systemCommonObjectImpl.getObject(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMCONFIGINFO);
		DestroySystem[] destroyClass=systemConfigInfo.getDestroySystem();
		if(destroyClass!=null) {
			int length=destroyClass.length;
			for(int i=0;i<length;i++) {
				if(destroyClass[i]!=null) {
					log.info("进行外部destroy操作 : "+destroyClass[i].getClass().getName());
					destroyClass[i].destroyAll();
				}
			}
		}
	}
	
	/**
	 * 摧毁对象池中的所有对象
	 *
	 */
	private void destroyObjectManager() {
		ObjectManager objectManager=ObjectManager.getInstance();
		objectManager.destroy();
	}
}
