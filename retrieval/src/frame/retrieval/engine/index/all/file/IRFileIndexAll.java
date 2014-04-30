package frame.retrieval.engine.index.all.file;

import frame.retrieval.engine.index.all.IRIndexAll;
import frame.retrieval.engine.index.doc.file.RFileIndexAllItem;



/**
 * 对文件进行批量创建索引
 * @author 
 *
 */
public interface IRFileIndexAll extends IRIndexAll{

	/**
	 * 获取文件批量索引对象
	 * @return
	 */
	public RFileIndexAllItem getFileIndexAllItem();

	/**
	 * 设置文件批量索引对象
	 * @param fileIndexAllItem
	 */
	public void setFileIndexAllItem(RFileIndexAllItem fileIndexAllItem);

}
