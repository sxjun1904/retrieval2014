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

import java.util.List;

import framework.base.snoic.base.pool.ObjectManager;
import framework.base.snoic.base.pool.ObjectPool;
import framework.base.snoic.system.interfaces.InitSystem;
import framework.base.snoic.system.interfaces.common.CommonObject;

/**
 * 应用程序对象池，应用程序的对象池使用的时候应该继承这个类，并设置初始化类和对象池名称
 * @author : 
 * 
 */
public abstract class ApplicationObjectManager implements CommonObject{
	protected ObjectManager objectManager=ObjectManager.getInstance();
	protected String poolname;
	protected InitSystem applicationInitialize=null;
	protected IApplicationGetObject applicationGetObject=new ApplicationNoInitGetObject();

	/**
	 * 设置对象池名称
	 * @param poolname The poolname to set.
	 */
	public void setPoolname(String poolname) {
		this.poolname = poolname;
	}
	
    /**
     * 设置初始化类
     * @param applicationInitialize
     */
	public synchronized void setApplicationInitialize(InitSystem applicationInitialize) {
		this.applicationInitialize = applicationInitialize;
		if(applicationInitialize!=null){
			applicationGetObject=new ApplicationInitGetObject();
		}
	}
	
	/**
	 * 创建对象池
	 *
	 */
	public void createPool(){
		objectManager.createPool(poolname);
	}

	/**
	 * 创建对象池
	 * @param poolType 对象池类型
	 * 	
	 * 				普通类型的对象池 ObjectPool.NORMAL_POOL
	 * 				弱类型的对象池 ObjectPool.WEAK_POOL
	 */
	public void createPool(String poolType){
		objectManager.createPool(poolname,poolType);
	}
	
	/**
	 * 清除对象池中的对象
	 *
	 */
	public void clearPool(){
		objectManager.clearPool(poolname);
	}
	
    /**
     * Security程序从对象池中取公共对象，如果没有取得对象，重新初始化系统
     * @param objectname
     *            对象名称
     * @return Object
     */
	public Object getObject(String objectname) {
		Object object = applicationGetObject.getObject(objectManager, poolname, objectname, applicationInitialize);
		return object;
	}
	
    /**
	 * 从对象池中取移除对象
	 * 
	 * @param objectName
	 *            对象名称
	 * @return Object
	 */
    public Object checkOutObject(String objectName) {
        Object object=objectManager.checkOutObject(poolname,objectName);
        return object;
    }
    
    /**
     * 往系统对象池中放入一个对象
     * @param objectName
     * @param object
     */
    public void checkInObject(String objectName,Object object){
    	objectManager.checkInObject(poolname,objectName,object);
    }
    
    /**
     * 获取对象池中所有对象名
     * @return List
     */
    public List getObjectNames(){
    	ObjectPool objectPool=objectManager.getObjectPool(poolname);
    	return objectPool.getObjectNames();
    }
    
    /**
     * 获取对象池中所有对象
     * @return List
     */
    public List getObjects(){
    	ObjectPool objectPool=objectManager.getObjectPool(poolname);
    	return objectPool.getObjects();
    }
    
    /**
     * 摧毁对象池
     *
     */
    public void destoryObjectPool(){
    	objectManager.removePool(poolname);
    }
}
