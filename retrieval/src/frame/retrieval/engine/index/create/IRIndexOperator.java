package frame.retrieval.engine.index.create;

import java.util.List;

import org.apache.lucene.index.IndexWriter;

import frame.retrieval.engine.index.doc.internal.RDocument;

/**
 * 索引记录操作接口
 * @author 
 *
 */
public interface IRIndexOperator {
	
	/**
	 * 将文档写入索引，并返回索引唯一ID
	 * @param document
	 * @return
	 */
	public String addIndex(RDocument document);
	
	/**
	 * 将文档写入索引，并返回索引唯一ID
	 * @param documents
	 * @return
	 */
	public List<String> addIndex(List<RDocument> documents);
	
	public List<String> addIndex(List<RDocument> documents, IndexWriter indexWriter);
	
	/**
	 * 将文档写入索引，并返回索引唯一ID
	 * @param documents
	 * @return
	 */
	public List<String> addFileIndex(List<RDocument> documents);
	
	/**
	 * 将文档从索引中删除
	 * @param documntId
	 */
	public void deleteIndex(String indexPathType,String documntId);
	
	/**
	 * 将文档从数据库索引中删除
	 * @param tableName
	 * @param recordIds
	 */
	public void deleteDatabaseIndex(String indexPathType,String tableName,List<String> recordIds);
	
	/**
	 * 将文档从索引中删除
	 * @param documntIds
	 */
	public void deleteIndex(String indexPathType,List<String> documntIds);
	
	/**
	 * 更新索引中的文档
	 * @param document
	 */
	public void updateIndex(RDocument document);
	
}
