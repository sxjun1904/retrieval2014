package frame.retrieval.engine.index.all.database;

import java.util.List;

import frame.retrieval.engine.index.doc.file.FileIndexDocument;


/**
 * 数据库记录索引相关文件索引信息
 * @author 
 *
 */
public interface IIndexAllDatabaseFileIndexOperator {
	
	/**
	 * 通过数据库主键获取相关文件信息
	 * @param recordId
	 * @return
	 */
	public List<FileIndexDocument> getFileIndexDocuments(String recordId);
	
}
