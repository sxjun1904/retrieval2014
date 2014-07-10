package com.sxjun.core.plugin.berkeley.secondary.impl;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.SecondaryDatabase;
import com.sxjun.core.plugin.berkeley.BerkeleyKit;
import com.sxjun.core.plugin.berkeley.BerkeleyPlugin;
import com.sxjun.core.plugin.berkeley.secondary.SecondaryBaseKeyCreater;
import com.sxjun.system.pojo.User;

public class SecUserKeyCreator extends SecondaryBaseKeyCreater{

	public SecUserKeyCreator(Class clazz) {
		super(clazz);
	}

	@Override
	public boolean createSecondaryKey(SecondaryDatabase secDb, DatabaseEntry keyEntry, DatabaseEntry dataEntry, DatabaseEntry resultEntry) {
		try {
			User user = (User) super.dataBinding.entryToObject(dataEntry);
			resultEntry.setData(user.getUsername().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
