package frame.base.core;

import frame.base.core.pool.ObjectManager;
import frame.base.system.interfaces.InitSystem;

/**
 * 
 *  @author:    
 */

interface IApplicationGetObject {

	/**
	 * 程序从对象池中取公共对象
	 * @param objectManager
	 * @param poolname
	 * @param objectname
	 * @param applicationInitialize
	 * @return Object
	 */
	public Object getObject(ObjectManager objectManager,String poolname,String objectname,InitSystem applicationInitialize);
}

