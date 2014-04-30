package frame.retrieval.engine.index.create;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

/**
 * 写入索引内容操作接口
 * @author 
 *
 */
public interface IRIndexWriter {

	/**
	 * 增加一个索引,先写入内存,然后在批量将索引写入文件
	 * @param documents
	 */
	public void addDocument(List<Document> documents);
	
	
	/**
	 * 增加一个索引,先写入内存,然后在批量将索引写入文件add by sxjun
	 * @param documents 
	 */
	public void addDocumentNotClose(List<Document> documents,IndexWriter _indexWriter);
	

	/**
	 * 增加一个索引,直接将索引写入文件
	 * @param documents
	 */
	public void addDocumentNowRamSupport(List<Document> documents);
	

	/**
	 * 写入一个索引
	 * @param document
	 */
	public void addDocument(Document document);
	
	/**
	 * 批量删除索引
	 * @param indexPathType
	 * @param terms
	 */
	public void deleteDocument(String indexPathType,List<Term> terms);
	
	/**
	 * 删除一个索引
	 * @param indexPathType
	 * @param term
	 */
	public void deleteDocument(String indexPathType,Term term);
	
}
