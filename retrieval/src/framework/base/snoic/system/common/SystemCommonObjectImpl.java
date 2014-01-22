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
package framework.base.snoic.system.common;

import framework.base.snoic.base.ApplicationObjectManager;
import framework.base.snoic.base.pool.ObjectManager;
/**
 * 管理ObjectPool中的公共对象
 * @author 
 *
 */
public class SystemCommonObjectImpl extends ApplicationObjectManager{

	private ObjectManager objectManager = ObjectManager.getInstance();
	private String poolname=SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME;
	private static SystemCommonObjectImpl systemCommonObjectImpl=new SystemCommonObjectImpl();
	
	private SystemCommonObjectImpl(){
		setPoolname(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME);
		createPool();
	}
	
	public static SystemCommonObjectImpl getInstance(){
			return systemCommonObjectImpl;
	}
	
    /**
     * Application程序从对象池中取公共对象
     * @param objectName
     *            对象名称
     * @return Object
     */
    public Object getObject(String objectName) {
		Object object = objectManager.getObject(poolname, objectName);
		return object;
	}
}