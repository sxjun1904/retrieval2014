package com.sxjun.core.plugin.berkeley;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.LockConflictException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;


/**
 * User: sxjun
 * Date: 14-07-04
 * Time: 上午7:21
 */
public class BerkeleyCache {
	private static final Logger logger = Logger.getLogger(BerkeleyCache.class);
    private Class clazz;
    private Database database;    
    private Environment env;
    
    public BerkeleyCache(Class clazz,Environment env, Database database) {
        this.env = env;
        this.database = database;
        this.clazz = clazz;
    }
    
    public void put(Object key, Object value) {
        try {
			StoredClassCatalog classCatalog = new StoredClassCatalog(database);// 实例化catalog
			EntryBinding dataBinding = new SerialBinding(classCatalog,clazz);
			DatabaseEntry theKey = new DatabaseEntry((String.valueOf(clazz.getSimpleName()+":"+key)).getBytes());
			DatabaseEntry theData = new DatabaseEntry();
			dataBinding.objectToEntry(value, theData);
			database.put(null, theKey, theData);
			logger.debug("put a object");
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
    }
    
    public Object get(Object key) {
    	Object o = null;
    	try {
			StoredClassCatalog classCatalog = new StoredClassCatalog(database);// 实例化catalog
			EntryBinding dataBinding = new SerialBinding(classCatalog,clazz);
			DatabaseEntry theKey = new DatabaseEntry((String.valueOf(clazz.getSimpleName()+":"+key)).getBytes());
			DatabaseEntry theData = new DatabaseEntry();
			database.get(null, theKey, theData, LockMode.DEFAULT);
			// 根据存储的类信息还原数据
				
			if (database.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS)
				o= dataBinding.entryToObject(theData);
			logger.debug("get a object");
		} catch (LockConflictException e) {
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
        return o;
    }
    
    public void remove(Object key) {
    	DatabaseEntry theKey = new DatabaseEntry((String.valueOf(clazz.getSimpleName()+":"+key)).getBytes());
    	database.delete(null, theKey);
    	logger.debug("remove a object");
    }
    
	public List getObjs() {
		StoredClassCatalog classCatalog = new StoredClassCatalog(database);// 实例化catalog
		EntryBinding dataBinding = new SerialBinding(classCatalog,clazz);
		
		OperationStatus result;
		DatabaseEntry theKey = new DatabaseEntry();
		DatabaseEntry theData = new DatabaseEntry();
		Transaction tnx = env.beginTransaction(null, null);
		Cursor cursor = database.openCursor(tnx, null);
		result = cursor.getFirst(theKey, theData, null);

		List list = null;
		try {
			list = new ArrayList();
			while (result == OperationStatus.SUCCESS)
			{
				if (new String(theKey.getData()).toUpperCase().startsWith(clazz.getSimpleName().toUpperCase()+":") &&theData.getData()!=null && theData.getData().length > 0)
				{
					Object o = dataBinding.entryToObject(theData);
					list.add(o);
				}
				result = cursor.getNext(theKey, theData, null);
			}
			logger.debug("get objects");
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		cursor.close();
		tnx.commit();
		return list;
	}
	
	public void close(){
		try {
			if (database != null) {
			    database.close();
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
}
