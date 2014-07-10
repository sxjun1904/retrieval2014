package com.sxjun.core.plugin.berkeley;

import java.io.File;

import org.apache.log4j.Logger;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseExistsException;
import com.sleepycat.je.DatabaseNotFoundException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentLockedException;
import com.sleepycat.je.EnvironmentNotFoundException;
import com.sleepycat.je.VersionMismatchException;

/**
 * User: sxjun
 * Date: 14-07-04
 * Time: 上午7:21
 */
public class BerkeleyManager {
	private static final Logger logger = Logger.getLogger(BerkeleyManager.class);
	public static Environment env;
	private static Database database;
	public BerkeleyManager() {}

	public static BerkeleyCache getBerkeleyCache(Class clazz) {
		BerkeleyCache cache = null;
		try {
			DatabaseConfig dbConfig = new DatabaseConfig();
			dbConfig.setAllowCreate(true);
			dbConfig.setTransactional(false);
			dbConfig.setDeferredWrite(false);
			database = env.openDatabase(null,clazz.getSimpleName(), dbConfig); 
			cache = new BerkeleyCache(clazz,env,database);
		} catch (DatabaseNotFoundException e) {
			e.printStackTrace();
		} catch (DatabaseExistsException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return cache;
	}

	public void init() {
		try {
			BerkeleyCache cache = null;
			EnvironmentConfig envConfig = new EnvironmentConfig();
			envConfig.setAllowCreate(true);
			envConfig.setTransactional(true);
			envConfig.setLocking(true);

			File envHome = new File("data/retrieval/frontier");
			if (!envHome.exists())
			{
				if (!envHome.mkdirs())
				{
					throw new Exception("Couldn't create this folder: " + envHome.getAbsolutePath());
				}
			}
			env = new Environment(envHome, envConfig);
		} catch (EnvironmentNotFoundException e) {
			e.printStackTrace();
		} catch (EnvironmentLockedException e) {
			e.printStackTrace();
		} catch (VersionMismatchException e) {
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void destroy() {
		 try {
			if (env != null) {
				 env.close();
			 }
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	public void cleanlog(){
		try {
			if (env != null) {
				env.cleanLog(); // 在关闭环境前清理下日志
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

}