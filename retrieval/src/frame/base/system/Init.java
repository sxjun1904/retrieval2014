package frame.base.system;

import frame.base.system.common.SystemCommonObjectImpl;
import frame.base.system.common.SystemCommonUtil;
import frame.base.system.conf.SystemConfigInfo;
import frame.base.system.interfaces.DestroySystem;
import frame.base.system.interfaces.IInit;
import frame.base.system.interfaces.InitSystem;
/**
 * 初始化系统
 * @author 
 *
 */
public class Init implements IInit{
	private static IInit instance = new Init(); // 唯一实例
	private String instanceLazyFlag = null; // 唯一实例
	private String instanceFlag = null; // 唯一实例
	private Init(){
		
	}
	
	/**
	 * @return Returns the instance.
	 */
	public static IInit getInstance() {
		return instance;
	}

	/**
	 * @return Returns the instanceLazy.
	 */
	public String getInstanceLazyFlag() {
		return instanceLazyFlag;
	}

	/**
	 * @return Returns the instanceFlag.
	 */
	public String getInstanceFlag() {
		return instanceFlag;
	}

	/**
	 * 系统初始化,在系统已经被初始化过的情况下，不会再被初始化
	 */
	public synchronized void init(){
		if(instanceFlag==null) {
	        instanceFlag="initialize";
	        InitSystem initSystem = new InitSystemImpl();
	        initSystem.initialize();
		}
    }
	
	/**
	 * 强制系统重新初始化
	 */
	public synchronized void reInit(){
        instanceFlag="initialize";
        InitSystem initSystem = new InitSystemImpl();
        initSystem.initialize();
    }
	
	/**
	 * 强制摧毁系统，将会摧毁整个系统管理的所有的对象以及所有的初始化信息
	 *
	 */
	public synchronized void destroySystem() {
		SystemCommonObjectImpl systemCommonObjectImpl = SystemCommonObjectImpl.getInstance();
		SystemConfigInfo systemConfigInfo = ((SystemConfigInfo)(systemCommonObjectImpl.getObject(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMCONFIGINFO)));
		String autodestroy=systemConfigInfo.getAutodestroy();
		if(autodestroy.equalsIgnoreCase(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_INIT_NODE_AUTODESTROY_ON)){
			System.out.println("+------------------------+");
			System.out.println("|  Destory System.....   |");
			System.out.println("+------------------------+");
			DestroySystem destroySystem = new DestroySystemImpl();
			destroySystem.destroyAll();
	        instance=null;
			System.out.println("+------------------------+");
			System.out.println("| Destory System success |");
			System.out.println("+------------------------+");
		}
	}
}
