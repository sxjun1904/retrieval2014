package com.sxjun.core.plugin.berkeley.secondary;


import java.util.List;

import org.apache.log4j.Logger;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.SecondaryCursor;
import com.sxjun.core.plugin.berkeley.BerkeleyKit;
import com.sxjun.core.plugin.berkeley.BerkeleyPlugin;
import com.sxjun.core.plugin.berkeley.secondary.impl.SecUserKeyCreator;
import com.sxjun.system.pojo.User;


/**
 * User: sxjun
 * Date: 14-07-04
 * Time: 上午7:21
 */
public class SecondaryBerkeleyKit {
    private static final Logger logger = Logger.getLogger(SecondaryBerkeleyKit.class);
    private SecondaryBerkeleyManager secondaryBerkeleyManager;
    public SecondaryBerkeleyKit(SecondaryBerkeleyManager secondaryBerkeleyManager){
    	this.secondaryBerkeleyManager = secondaryBerkeleyManager;
    }
    
    public void put(Class clazz,String key, Object value){
    	 try {
    		Database db = secondaryBerkeleyManager.getSeckeyCreator().getBerkeleyManager().getBerkeleyCache(clazz).getDatabase();
 			StoredClassCatalog classCatalog = new StoredClassCatalog(db);// 实例化catalog
 			EntryBinding dataBinding = new SerialBinding(classCatalog,clazz);
 			DatabaseEntry theKey = new DatabaseEntry((String.valueOf(clazz.getSimpleName()+":"+key)).getBytes());
 			DatabaseEntry theData = new DatabaseEntry();
 			dataBinding.objectToEntry(value, theData);
 			db.put(null, theKey, theData);
 			logger.debug("put a object");
 		} catch (DatabaseException e) {
 			e.printStackTrace();
 		} catch (IllegalArgumentException e) {
 			e.printStackTrace();
 		}
    }
    
    public List get(String key){
    	List list = null;
    	try {
    		DatabaseEntry secondaryKey = new DatabaseEntry(key.getBytes());
		    DatabaseEntry secondaryData = new DatabaseEntry();
		    SecondaryCursor secCursor = secondaryBerkeleyManager.getSecondaryBerkeley().openSecondaryCursor(null, null);
		    OperationStatus result = secCursor.getFirst(secondaryKey,secondaryData, LockMode.DEFAULT);
		    while (result == OperationStatus.SUCCESS) {
		    	if (new String(secondaryKey.getData()).toUpperCase().equals(key.toUpperCase()) &&secondaryData.getData()!=null && secondaryData.getData().length > 0)
				{
					Object o = secondaryBerkeleyManager.getSeckeyCreator().getDataBinding().entryToObject(secondaryData);
					list.add(o);
				}
				result = secCursor.getNext(secondaryKey, secondaryData, null);
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
    	return list;
    }
    
    public void close(){
    	secondaryBerkeleyManager.close();
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
    	SecondaryBerkeleyManager sbm = new SecondaryBerkeleyManager(new SecUserKeyCreator(User.class));
    	SecondaryBerkeleyKit kit = new SecondaryBerkeleyKit(sbm);
    	User u = new User();
    	u.setId("3");
    	u.setUsername("admin3");
    	u.setPassword("junjun3");
    	kit.put(User.class, "3", u);
    	
    	List<User> l = kit.get("user:3");
    	System.out.println(l.size());
    	
	}
}