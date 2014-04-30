package frame.base.core;

import frame.base.core.pool.ObjectManager;
import frame.base.system.interfaces.InitSystem;

/**
 * 
 *  @author:    
 */

class ApplicationInitGetObject implements IApplicationGetObject{

	public synchronized Object getObject(ObjectManager objectManager, String poolname, String objectname, InitSystem applicationInitialize) {
		Object object = objectManager.getObject(poolname, objectname);
		if (applicationInitialize == null) {
			return object;
		}
		if (object == null) {
			try {
				applicationInitialize.initialize();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			objectManager = ObjectManager.getInstance();
			object = objectManager.getObject(poolname, objectname);
		}
		return object;
	}

}

