package frame.retrieval.engine.index;


/**
 * 索引文件管理
 * 
 * @author 
 *
 */
public interface IRIndexManager {
	
	/**
	 * 判断是否有索引文件存在
	 * @return
	 */
	public boolean isExists();

	/**
	 * 如果索引文件不存在，则初始化生成索引文件，如果索引文件已经存在，则不做任何操作
	 */
	public void create();

	/**
	 * 如果索引文件存在，则重新生成索引文件，如果索引文件不存在，则生成一个新的文件
	 */
	public void reCreate();

	/**
	 * 执行索引优化
	 */
	public void optimize();
	
	
	/**
	 * 执行索引优化
	 */
	public void forceMerge(int IndexNum);
	
}
