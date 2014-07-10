package com.sxjun.core.plugin.berkeley;


import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.plugin.ehcache.IDataLoader;
import com.sxjun.system.pojo.User;

import frame.base.core.util.MD5Util;


/**
 * User: sxjun
 * Date: 14-07-04
 * Time: 上午7:21
 */
public class BerkeleyKit {
    private static final Logger logger = Logger.getLogger(BerkeleyKit.class);
    private static volatile BerkeleyManager berkeleyManager;


    public static void init(BerkeleyManager berkeleyManager) {
    	BerkeleyKit.berkeleyManager = berkeleyManager;
    }


    public static BerkeleyManager getRedisManager() {
        return berkeleyManager;
    }


    public static void put(Class clazz, Object key, Object value) {
        berkeleyManager.getBerkeleyCache(clazz).put(key,value);
    }

    public static <T> T get(Class clazz, Object key) {
        Object o = berkeleyManager.getBerkeleyCache(clazz).get(key);
        return o == null ? null : (T)o;
    }
    
    public static void close(Class clazz){
    	berkeleyManager.getBerkeleyCache(clazz).close();
    }


    public static List getObjs(Class clazz) {
    	return berkeleyManager.getBerkeleyCache(clazz).getObjs();
    }

    public static void remove(Class clazz, Object key) {
        berkeleyManager.getBerkeleyCache(clazz).remove(key);
    }


    @SuppressWarnings("unchecked")
    public static <T> T get(Class clazz, Object key, IDataLoader dataLoader) {
        Object data = get(clazz, key);
        if (data == null) {
            data = dataLoader.load();
            put(clazz, key, data);
        }
        return (T)data;
    }


    @SuppressWarnings("unchecked")
    public static <T> T get(Class clazz, Object key, Class<? extends IDataLoader> dataLoaderClass) {
        Object data = get(clazz, key);
        if (data == null) {
            try {
                IDataLoader dataLoader = dataLoaderClass.newInstance();
                data = dataLoader.load();
                put(clazz, key, data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return (T)data;
    }
    
    
    


    public static void main(String[] args) {
    	/*
    	 * 初始化数据库
    	 */
    	BerkeleyPlugin berkeleyPlugin = new BerkeleyPlugin();
    	berkeleyPlugin.start();
    	
    	/*
    	 * 添加
    	 */
    	User u = new User();
    	u.setId("1");
    	u.setUsername("admin");
    	u.setPassword(MD5Util.GetMD5Code("11111"));
    	BerkeleyKit.put(User.class, "user:1", u);
    	
    	/*
    	 * 删除
    	 */
    	BerkeleyKit.remove(User.class, "user:1");
    	
    	/*
    	 * 单条记录查询
    	 */
    	User myU1 = BerkeleyKit.get(User.class, "user:1");
    	if(myU1!=null)
    		System.out.println("name:"+myU1.getUsername()+";pwd:"+myU1.getPassword());
    	
    	/*
    	 * 多条记录查询
    	 */
    	List<User> myU2 = BerkeleyKit.getObjs(User.class);
    	for(User u2 : myU2){
    		System.out.println("user:"+u2.getUsername());
    	}
    	
    	/*
    	 * 关闭数据库
    	 */
    	BerkeleyKit.close(User.class);
    	
    }

}