package frame.retrieval.engine.facade;

import java.util.List;

import org.apache.lucene.index.IndexWriter;

import frame.retrieval.engine.index.doc.NormalIndexDocument;
import frame.retrieval.engine.index.doc.database.DatabaseIndexDocument;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;
import frame.retrieval.engine.index.doc.file.FileIndexDocument;
import frame.retrieval.engine.index.doc.file.RFileIndexAllItem;

/**
 * 索引记录操作接口
 * @author 
 *
 */
public interface IRDocOperatorFacade {

	
	/**
	 * 创建文本类型索引内容
	 * @param indexDocuments
	 */
	public void createNormalIndexs(List<NormalIndexDocument> indexDocuments);
	

	
	/**
	 * 创建文本类型索引内容
	 * @param indexDocument
	 */
	public void create(NormalIndexDocument indexDocument);
	

	/**
	 * 创建数据库类型索引内容
	 * @param indexDocuments
	 */
	public void createDatabaseIndexs(List<DatabaseIndexDocument> indexDocuments);
	public void createDatabaseIndexs(List<DatabaseIndexDocument> indexDocuments,IndexWriter indexWriter);

	/**
	 * 创建数据库类型索引内容
	 * @param indexDocument
	 */
	public void create(DatabaseIndexDocument indexDocument);
	

	/**
	 * 根据SQL创建数据库类型索引内容
	 * @param indexDocument
	 */
	public long createAll(RDatabaseIndexAllItem databaseIndexAllItem);
	
	public long createAll(RDatabaseIndexAllItem databaseIndexAllItem,IndexWriter indexWriter);
	

	/**
	 * 根据文件路径创建索引内容
	 * @param indexDocument
	 */
	public long createAll(RFileIndexAllItem fileIndexAllItem);
	

	/**
	 * 创建文件类型索引内容
	 * @param indexDocuments
	 * @param maxFileSize
	 */
	public void createFileIndexs(List<FileIndexDocument> indexDocuments,long maxFileSize);
	

	/**
	 * 创建文件类型索引内容
	 * @param indexDocument
	 * @param maxFileSize
	 */
	public void create(FileIndexDocument indexDocument,long maxFileSize);
	

	/**
	 * 更新索引内容
	 * @param indexDocument
	 */
	public void update(NormalIndexDocument indexDocument);
	

	/**
	 * 更新索引内容
	 * @param indexDocument
	 */
	public void update(DatabaseIndexDocument indexDocument);
	

	/**
	 * 更新索引内容
	 * @param indexDocument
	 * @param maxFileSize
	 */
	public void update(FileIndexDocument indexDocument,long maxFileSize);
	

	/**
	 * 删除索引内容
	 * @param indexPathType
	 * @param documntId
	 */
	public void delete(String indexPathType,String documntId);
	

	/**
	 * 删除数据库索引内容
	 * @param indexPathType
	 * @param tableName
	 * @param recordIds
	 */
	public void delete(String indexPathType,String tableName,List<String> reocrdIds);
	

	/**
	 * 删除索引内容
	 * @param indexPathType
	 * @param documntId
	 */
	public void delete(String indexPathType,List<String> documntIds);
	
	
}
