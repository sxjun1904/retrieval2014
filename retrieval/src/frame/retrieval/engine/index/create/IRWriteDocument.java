package frame.retrieval.engine.index.create;

import java.util.List;

import org.apache.lucene.index.IndexWriter;

import frame.retrieval.engine.index.doc.internal.RDocument;

/**
 * 将内容写入索引文件
 * @author 
 *
 */
public interface IRWriteDocument {
	
	/**
	 * 设置待索引内容
	 * @param documents
	 */
	public void setDocument(List<RDocument> documents);
	
	/**
	 * 将内容写入索引文件
	 */
	public void writeDocument();
	
	public void writeDocument(IndexWriter indexWriter);
	
	/**
	 * 将内容写入索引文件
	 */
	public void writeFileDocument();
	
}
