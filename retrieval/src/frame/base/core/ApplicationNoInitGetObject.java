package frame.base.core;

import frame.base.core.pool.ObjectManager;
import frame.base.system.interfaces.InitSystem;

/**
 * 
 *  @author:    
 */

class ApplicationNoInitGetObject implements IApplicationGetObject{

	public Object getObject(ObjectManager objectManager, String poolname, String objectname, InitSystem applicationInitialize) {
		Object object = objectManager.getObject(poolname, objectname);
		return object;
	}

}

