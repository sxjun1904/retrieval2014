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
package framework.base.snoic.base.pool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import framework.base.snoic.base.interfaces.pool.PoolManager;
import framework.base.snoic.base.util.UtilTool;
/**
 * 管理对象池
 * @author 
 *
 */
public class ObjectManager implements PoolManager{
	
    private static ObjectManager instance = new ObjectManager(); // 唯一实例
    
    private static Lock lock=new ReentrantLock();

    private Map<Object,Object> pools = new HashMap<Object,Object>();

    private ObjectManager() {
    	
    }
    
    /**
     * 生成对象池的唯一实例
     */
    public static ObjectManager getInstance() {
        return instance;
    }
    
    /**
     * 创建池
     */
    public void createPool(String poolname) {
    	lock.lock();
    	try{
        	ObjectPool pool =getObjectPool(poolname);
        	if(pool==null){
        		pool=new ObjectPool();
        		pools.put(poolname,pool);
        	}
    	}finally{
    		lock.unlock();
    	}
    }
    
    /**
     * 创建池
     */
    public void createPool(String poolname,String poolType) {
    	lock.lock();
    	try{
        	ObjectPool pool =getObjectPool(poolname);
        	if(pool==null){
        		pool=new ObjectPool(poolType);
        		pools.put(poolname,pool);
        	}
    	}finally{
    		lock.unlock();
    	}
    }
    
    /**
     * 将对象放入对象池中
     * @param poolname 对象池名称
     * @param objectName
     *            对象的名称
     * @param object
     *            对象
     */
    public void checkInObject(String poolname,String objectName, Object object) {
    	lock.lock();
    	try{
            ObjectPool pool = (ObjectPool) pools.get(poolname);
            if (pool != null) {
                pool.checkIn(objectName, object);
                pools.put(poolname,pool);
            }
    	}finally{
    		lock.unlock();
    	}
    }

    /**
     * 将对象从连接池中取出并移除
     * @param poolname 对象池名称
     * @param objectName  将要移除的对象名
     */
    public Object checkOutObject(String poolname,String objectName) {
    	lock.lock();
    	try{
            ObjectPool pool = (ObjectPool) pools.get(poolname);
            Object object=null;
            if (pool != null) {
                object=pool.checkOut(objectName);
                pools.put(poolname,pool);
            }
            return object;
    	}finally{
    		lock.unlock();
    	}
    }

    /**
     * 从对象池中取得对象但是并不移除出对象池
     * @param poolname 对象池名称
     * @param objectName 对象名
     * @return Object
     */
    public Object getObject(String poolname,String objectName) {
        ObjectPool pool = (ObjectPool) pools.get(poolname);
        Object object=null;
        if (pool != null) {
        	object=pool.getObject(objectName);
        }
        return object;
    }
    
    /**
     * 返回对象池中所有的对象名称
     * @param poolname 对象池名称
     * @return ArrayList
     */
    public List<Object> getObjectNames(String poolname){
    	lock.lock();
    	try{
            ObjectPool pool = (ObjectPool) pools.get(poolname);
            if (pool != null) {
                return pool.getObjectNames();
            }
            return null;
    	}finally{
    		lock.unlock();
    	}
    }
   
    /**
     * 取得一个对象池 
     * @param poolname
     * @return ObjectPool
     */
    public ObjectPool getObjectPool(String poolname){
    	ObjectPool pool = (ObjectPool) pools.get(poolname);
    	return pool;
    }
    
    /**
     * 取得所有对象池
     * @return Object[][]
     */
    public Object[][] getObjectPools(){
    	Object[][] objects=UtilTool.getMapKeyValue(pools);
    	return objects;
    }
    
    /**
     * 清除对象池中的对象
     * @param poolname
     */
    public void clearPool(String poolname){
    	lock.lock();
    	try{
        	ObjectPool pool = (ObjectPool) pools.get(poolname);
        	
        	if(pool!=null) {
            	try {
            		pool.clear();
            	}catch(Exception e) {
            		
            	}
        	}
    	}finally{
    		lock.unlock();
    	}
    }
    
    /**
     * 删除一个对象池
     * @param poolname
     */
    public void removePool(String poolname){
    	lock.lock();
    	try{
        	clearPool(poolname);
        	try {
            	pools.remove(poolname);
        	}catch(Exception e) {
        		
        	}
    	}finally{
    		lock.unlock();
    	}
    }
    
    /**
     * 清除所有对象池中的对象
     *
     */
    public void clearObjectManager(){
    	lock.lock();
    	try{
        	Object[][] objects=UtilTool.getMapKeyValue(pools);
        	if(objects!=null){
            	int length=objects.length;
            	for(int i=0;i<length;i++){
            		String objectname=(String)objects[i][0];
            		ObjectPool objectPool=(ObjectPool)objects[i][1];
            		try {
            			objectPool.clear();
            		}catch(Exception e) {
            			
            		}
            		objectPool=null;
            		try {
            			pools.remove(objectname);
            		}catch(Exception e) {
            			
            		}
            		
            	}
            	try {
            		pools.clear();
            	}catch(Exception e) {
            		
            	}
        	}
    	}finally{
    		lock.unlock();
    	}
    }
    
    /**
     * 清除所有对象池中的对象，并摧毁所有对象池
     *
     */
    public void destroy(){
    	lock.lock();
    	try{
        	clearObjectManager();
        	instance = null;
    	}finally{
    		lock.unlock();
    	}
    }
}