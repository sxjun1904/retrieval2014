package frame.retrieval.engine.facade;

/**
 * 索引操作接口
 * 
 * @author 
 *
 */
public interface IRIndexOperatorFacade {
	

	/**
	 * 创建索引文件，如果索引文件已经存在，则不再创建
	 */
	public void createIndex();
	
	/**
	 * 创建索引文件，如果索引文件不存在，则创建一个新索引，如果索引文件已经存在，则删除旧索引，创建一个新的索引
	 * 		这个操作会造成已经存在的索引内容丢失，请慎用
	 */
	public void reCreateIndex();
	
	/**
	 * 优化索引
	 * @param
	 */
	@Deprecated
	public void optimize();
	
	
	public void forceMerge(int IndexNum);

}
