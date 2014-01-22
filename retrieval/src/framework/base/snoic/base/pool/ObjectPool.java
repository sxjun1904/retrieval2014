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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import framework.base.snoic.base.interfaces.pool.Pool;
import framework.base.snoic.base.util.UtilTool;

/**
 * 对象池 
 * @author 
 *
 */
public class ObjectPool implements Pool{
	/**
	 * 普通类型的对象池
	 */
    public final static String NORMAL_POOL="NORMAL";
    /**
     * 弱类型的对象池
     */
    public final static String WEAK_POOL="WEAK";
    
    private static Lock lock=new ReentrantLock();
    
	private Map<Object,Object> objectMap = new HashMap<Object,Object>();
    
    public ObjectPool() {
    	
    }
    
    public ObjectPool(String poolType) {
    	if(poolType.equalsIgnoreCase(WEAK_POOL)){
    		objectMap=new WeakHashMap<Object,Object>();
    	}
    }

    /**
     * 将对象放入对象池中
     * 
     * @param objectName
     *            对象的名称
     * @param object
     *            对象
     */
    public void checkIn(String objectName, Object object) {
    	lock.lock();
    	try{
            objectMap.put(objectName, object);
    	}finally{
    		lock.unlock();
    	}
    }

    /**
     * 将对象从连接池中取出并移除
     * 
     * @param objectName
     *            将要移除的对象名
     * @return Object
     */
    public Object checkOut(String objectName) {
    	lock.lock();
    	try{
        	Object object=objectMap.get(objectName);
            objectMap.remove(objectName);
            return object;
    	}finally{
    		lock.unlock();
    	}
    }

    /**
     * 返回对象池中所有的对象名
     * @return ArrayList
     */
    public List getObjectNames(){
    	lock.lock();
    	try{
        	List arraylist=null;
            if (objectMap != null) {
            	arraylist=new ArrayList();
            	
    			int objectHmSize = objectMap.size();
    			Iterator iterator = objectMap.entrySet().iterator();

    			for (int i = 0; i < objectHmSize; i++) {
    				Map.Entry entry = (Map.Entry) iterator.next();
    				String objectHmkey = (String) entry.getKey();
    			    arraylist.add(objectHmkey);
    			}
    		}
            return arraylist;
    	}finally{
    		lock.unlock();
    	}
    }

    /**
     * 返回对象池中所有的对象
     * @return ArrayList
     */
    public List getObjects(){
    	lock.lock();
    	try{
        	List arraylist=null;
            if (objectMap != null) {
            	arraylist=new ArrayList();
            	
    			int objectHmSize = objectMap.size();
    			Iterator iterator = objectMap.entrySet().iterator();

    			for (int i = 0; i < objectHmSize; i++) {
    				Map.Entry entry = (Map.Entry) iterator.next();
    				Object object =  entry.getValue();
    			    arraylist.add(object);
    			}
    		}
            return arraylist;
    	}finally{
    		lock.unlock();
    	}
    }

    /**
     * 重新初始化对象池
     *
     */
    public void clear(){
    	lock.lock();
    	try{
        	Object[][] objects=UtilTool.getMapKeyValue(objectMap);
        	if(objects!=null){
            	int length=objects.length;
            	for(int i=0;i<length;i++){
            		String objectname=(String)objects[i][0];
            		objectMap.put(objectname,null);
            		objectMap.remove(objectname);
            	}
        	}
        	try {
        		objectMap.clear();
        	}catch(Exception e) {
        		
        	}
    	}finally{
    		lock.unlock();
    	}
    }

    /**
     * 从对象池中取得对象
     * 
     * @param objectName
     *            对象名
     * @return Object
     */
    public Object getObject(String objectName) {
        Object object = objectMap.get(objectName);
        return object;
    }
}