package frame.retrieval.engine.index.all.database;

import java.util.List;
import java.util.Map;

import frame.retrieval.engine.index.all.IRIndexAll;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;
import frame.retrieval.engine.index.doc.file.FileIndexDocument;

/**
 * 从数据库中对数据批量创建索引
 * @author 
 *
 */
public interface IRDatabaseIndexAll extends IRIndexAll{

	/**
	 * 获取下一页数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> next();

	/**
	 * 获取数据库批量索引对象
	 */
	public RDatabaseIndexAllItem getDatabaseIndexAllItem();
	
	/**
	 * 设置数据库批量索引对象
	 * @param databaseIndexAllItem
	 */
	public void setDatabaseIndexAllItem(RDatabaseIndexAllItem databaseIndexAllItem);
	
	/**
	 * 获取数据库记录相关文件
	 * @param recordId
	 * @return
	 */
	public List<FileIndexDocument> getFileIndexDocuments(String recordId);

}
