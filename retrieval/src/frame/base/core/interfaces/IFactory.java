package frame.base.core.interfaces;
/**
 * 工厂
 * @author 
 *
 */
public interface IFactory {
	
	/**
	 * @return Returns the configfile.
	 */
	public Object getConfigfile();
	/**
	 * @param configfile The configfile to set.
	 */
	public void setConfigfile(Object configfile);
	
	/**
	 * 创建工厂
	 */
	public void buildFactory();
	
	/**
	 * 摧毁工厂
	 *
	 */
	public void destoryFactory();
	
	/**
	 * 获取工厂生成的对象
	 * @return Object
	 */
	public Object getObject();
}
