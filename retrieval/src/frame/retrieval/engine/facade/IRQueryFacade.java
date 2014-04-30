package frame.retrieval.engine.facade;

import frame.retrieval.engine.query.RQuery;
import frame.retrieval.engine.query.result.DatabaseQueryResult;
import frame.retrieval.engine.query.result.FileQueryResult;


/**
 * 索引查询接口
 * @author 
 *
 */
public interface IRQueryFacade {
	
	/**
	 * 创建全文搜索对象
	 * @param indexPathType
	 * @return
	 */
	public RQuery createRQuery(Object indexPathType);
	

	/**
	 * 创建全文搜索对象
	 * @param indexPathTypes
	 * @return
	 */
	public RQuery createRQuery(String[] indexPathTypes);
	

	/**
	 * 获取数据库记录在索引中的内容
	 * @param indexPathType
	 * @param tableName
	 * @param recordId
	 * @return
	 */
	public DatabaseQueryResult queryDatabaseDocument(String indexPathType,String tableName,String recordId);
	

	/**
	 * 获取数据库记录在索引中的唯一ID
	 * @param indexPathType
	 * @param tableName
	 * @param recordId
	 * @return
	 */
	public String queryDatabaseDocumentIndexId(String indexPathType,String tableName,String recordId);
	

	/**
	 * 获取文件在索引中的唯一ID
	 * @param indexPathType
	 * @param fileId
	 * @return
	 */
	public String queryFileDocumentIndexIdByFileId(String indexPathType,String fileId);
	

	/**
	 * 获取文件在索引中的唯一ID
	 * @param indexPathType
	 * @param fileRelativePath
	 * @return
	 */
	public String queryFileDocumentIndexIdRelativePath(String indexPathType,String fileRelativePath);
	

	/**
	 * 获取文件在索引中的内容
	 * @param indexPathType
	 * @param fileId
	 * @return
	 */
	public FileQueryResult queryFileDocumentByFileId(String indexPathType,String fileId);
	

	/**
	 * 获取文件在索引中的内容
	 * @param indexPathType
	 * @param fileRelativePath
	 * @return
	 */
	public FileQueryResult queryFileDocumentByRelativePath(String indexPathType,String fileRelativePath);
	
}
