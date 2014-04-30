package frame.base.system.interfaces;
/**
 * 摧毁系统
 * @author 
 *
 */
public interface DestroySystem {
    
    /**
     * 摧毁系统
     */
    public void destroyAll();
    
	/**
	 * 进行外部destroy操作
	 *
	 */
    public void destoryDestroyClass();
    
}
