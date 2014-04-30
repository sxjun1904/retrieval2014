package frame.retrieval.engine.index.all;

import org.apache.lucene.index.IndexWriter;

/**
 * 批量创建索引
 * @author 
 *
 */
public interface IRIndexAll {
	
	/**
	 * 给所有内容建索引
	 */
	public long indexAll(); 
	
	public long indexAll(IndexWriter indexWriter);
	
}
