package frame.base.system.common;

import frame.base.core.ApplicationObjectManager;
import frame.base.core.pool.ObjectManager;
/**
 * 管理ObjectPool中的公共对象
 * @author 
 *
 */
public class SystemCommonObjectImpl extends ApplicationObjectManager{

	private ObjectManager objectManager = ObjectManager.getInstance();
	private String poolname=SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME;
	private static SystemCommonObjectImpl systemCommonObjectImpl=new SystemCommonObjectImpl();
	
	private SystemCommonObjectImpl(){
		setPoolname(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME);
		createPool();
	}
	
	public static SystemCommonObjectImpl getInstance(){
			return systemCommonObjectImpl;
	}
	
    /**
     * Application程序从对象池中取公共对象
     * @param objectName
     *            对象名称
     * @return Object
     */
    public Object getObject(String objectName) {
		Object object = objectManager.getObject(poolname, objectName);
		return object;
	}
}