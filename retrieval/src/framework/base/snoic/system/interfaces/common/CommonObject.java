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
package framework.base.snoic.system.interfaces.common;

import framework.base.snoic.system.interfaces.InitSystem;

/**
 * 对象池管理接口
 * @author 
 *
 */
public interface CommonObject {
    /**
     * 设置初始化类
     * @param applicationInitialize
     */
	public void setApplicationInitialize(InitSystem applicationInitialize);

	/**
	 * 设置对象池名称
	 * @param poolname The poolname to set.
	 */
	public void setPoolname(String poolname);
	
	/**
	 * 创建对象池
	 *
	 */
	public void createPool();
	
	/**
	 * 清除对象池中的对象
	 *
	 */
	public void clearPool();
	
	
	
    /**
     * Security程序从对象池中取公共对象，如果没有取得对象，重新初始化系统
     * @param objectname
     *            对象名称
     * @return Object
     */
	public Object getObject(String objectname);
	
    /**
     * 从对象池中取移除对象
     * 
     * @param objectName
     *            对象名称
     * @return Object
     */
    public Object checkOutObject(String objectName);
    
    /**
     * 往系统对象池中放入一个对象
     * @param objectName
     * @param object
     */
    public void checkInObject(String objectName,Object object);
    /**
     * 摧毁对象池
     *
     */
    public void destoryObjectPool();
}
