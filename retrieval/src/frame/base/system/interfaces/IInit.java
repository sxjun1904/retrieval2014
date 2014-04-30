package frame.base.system.interfaces;

/**
 * 初始化系统
 * @author 
 *
 */
public interface IInit {
	/**
	 * 系统初始化,在系统已经被初始化过的情况下，不会再被初始化
	 */
	public void init();
	
	/**
	 * 强制系统重新初始化
	 */
	public void reInit();
	
	/**
	 * 强制摧毁系统，将会摧毁整个系统管理的所有的对象以及所有的初始化信息
	 *
	 */
	public void destroySystem();
}
