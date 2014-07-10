package com.sxjun.core.plugin.berkeley.secondary;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseExistsException;
import com.sleepycat.je.DatabaseNotFoundException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.SecondaryConfig;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.SecondaryKeyCreator;

public class SecondaryBerkeleyManager {
	public static final String  SECONDARY_BERKELEY_SUFFIX = "_Secondary";
	private Database database;
	private Environment env;
	private SecondaryDatabase secDatabase;
	private SecondaryBaseKeyCreater seckeyCreator;
	private Class clazz;
	
	public SecondaryBerkeleyManager(SecondaryBaseKeyCreater seckeyCreator){
		this.database = seckeyCreator.getBerkeleyManager().getBerkeleyCache(seckeyCreator.getClass()).getDatabase();
		this.clazz = seckeyCreator.getClazz();
		this.env = seckeyCreator.getBerkeleyManager().env;
		this.seckeyCreator = seckeyCreator;
	}

	public SecondaryDatabase getSecondaryBerkeley(){
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		
		SecondaryConfig secConfig = new SecondaryConfig();//二级库的配置信息
		secConfig.setAllowCreate(true);
		secConfig.setSortedDuplicates(true);// 通常二级库经常是允许多重记录的
		secConfig.setAllowPopulate(true);// 如果设置为true，则表示二级库允许自动填充。当primary database中的内容加进来后自动也会把secondary里的数据也填充进来。
	 
	    try {
			//SecondaryKeyCreator keyCreator = seckeyCreator;// 创建二级库的key创建器
			secConfig.setKeyCreator(seckeyCreator);// 设置二级库的创建器
			String secDbName = clazz.getSimpleName()+SECONDARY_BERKELEY_SUFFIX;//打开二级库
			secDatabase = env.openSecondaryDatabase(null, secDbName, database, secConfig);
		} catch (DatabaseNotFoundException e) {
			e.printStackTrace();
		} catch (DatabaseExistsException e) {
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	    return  secDatabase;
	}
	
	public void close(){
		if (secDatabase != null) {
			 secDatabase.close();
		}
	}

	public SecondaryBaseKeyCreater getSeckeyCreator() {
		return seckeyCreator;
	}

}
