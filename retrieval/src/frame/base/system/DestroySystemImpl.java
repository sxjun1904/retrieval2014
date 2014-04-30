package frame.base.system;

import frame.base.core.SnoicsClass;
import frame.base.core.interfaces.log.Log;
import frame.base.core.pool.ObjectManager;
import frame.base.system.common.SystemCommonObjectImpl;
import frame.base.system.common.SystemCommonUtil;
import frame.base.system.conf.SystemConfigInfo;
import frame.base.system.interfaces.DestroySystem;
/**
 * 摧毁系统中所有的对象
 * @author 
 *
 */
public class DestroySystemImpl extends SnoicsClass implements DestroySystem{
	
	private Log log=getLog();
	
	public DestroySystemImpl() {
		
	}
	
	/**
	 * 摧毁系统中所有的对象
	 *
	 */
	public void destroyAll() {
		destoryDestroyClass();
		log.info("进行系统中的 destroy 操作");
		destroyObjectManager();
	}
	
	/**
	 * 进行外部destroy操作
	 *
	 */
	public void destoryDestroyClass() {
		SystemCommonObjectImpl systemCommonObjectImpl = SystemCommonObjectImpl.getInstance();
		SystemConfigInfo systemConfigInfo=(SystemConfigInfo)systemCommonObjectImpl.getObject(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMCONFIGINFO);
		DestroySystem[] destroyClass=systemConfigInfo.getDestroySystem();
		if(destroyClass!=null) {
			int length=destroyClass.length;
			for(int i=0;i<length;i++) {
				if(destroyClass[i]!=null) {
					log.info("进行外部destroy操作 : "+destroyClass[i].getClass().getName());
					destroyClass[i].destroyAll();
				}
			}
		}
	}
	
	/**
	 * 摧毁对象池中的所有对象
	 *
	 */
	private void destroyObjectManager() {
		ObjectManager objectManager=ObjectManager.getInstance();
		objectManager.destroy();
	}
}
