package frame.base.system.interfaces.common;

import frame.base.system.interfaces.InitSystem;

/**
 * 对象池管理接口
 * @author 
 *
 */
public interface CommonObject {
    /**
     * 设置初始化类
     * @param applicationInitialize
     */
	public void setApplicationInitialize(InitSystem applicationInitialize);

	/**
	 * 设置对象池名称
	 * @param poolname The poolname to set.
	 */
	public void setPoolname(String poolname);
	
	/**
	 * 创建对象池
	 *
	 */
	public void createPool();
	
	/**
	 * 清除对象池中的对象
	 *
	 */
	public void clearPool();
	
	
	
    /**
     * Security程序从对象池中取公共对象，如果没有取得对象，重新初始化系统
     * @param objectname
     *            对象名称
     * @return Object
     */
	public Object getObject(String objectname);
	
    /**
     * 从对象池中取移除对象
     * 
     * @param objectName
     *            对象名称
     * @return Object
     */
    public Object checkOutObject(String objectName);
    
    /**
     * 往系统对象池中放入一个对象
     * @param objectName
     * @param object
     */
    public void checkInObject(String objectName,Object object);
    /**
     * 摧毁对象池
     *
     */
    public void destoryObjectPool();
}
