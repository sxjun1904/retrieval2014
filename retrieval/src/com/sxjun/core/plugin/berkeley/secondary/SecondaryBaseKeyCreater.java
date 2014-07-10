package com.sxjun.core.plugin.berkeley.secondary;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.SecondaryKeyCreator;
import com.sxjun.core.plugin.berkeley.BerkeleyManager;
import com.sxjun.system.pojo.User;

public abstract class SecondaryBaseKeyCreater  implements SecondaryKeyCreator {
	public EntryBinding dataBinding;
	private Database database;
	private Class clazz;
	private BerkeleyManager berkeleyManager = new BerkeleyManager();
	public SecondaryBaseKeyCreater(Class clazz){
		this.clazz = clazz;
		this.database = berkeleyManager.getBerkeleyCache(clazz).getDatabase();
		StoredClassCatalog classCatalog = new StoredClassCatalog(database);// 实例化catalog
		dataBinding = new SerialBinding(classCatalog,clazz);
		this.dataBinding = dataBinding;
	}
	public Class getClazz() {
		return clazz;
	}
	public BerkeleyManager getBerkeleyManager() {
		return berkeleyManager;
	}
	public EntryBinding getDataBinding() {
		return dataBinding;
	}
}
