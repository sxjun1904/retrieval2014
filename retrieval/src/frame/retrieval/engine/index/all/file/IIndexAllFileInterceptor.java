package frame.retrieval.engine.index.all.file;

import java.util.Map;

import frame.retrieval.engine.index.doc.file.FileIndexDocument;

/**
 * 文件批量索引时单个文件记录索引创建拦截器
 * @author 
 *
 */
public interface IIndexAllFileInterceptor {

	/**
	 * 拦截获取文件索引的附加信息
	 * @param fileIndexDocument
	 * @return
	 */
	public Map<String,Object> interceptor(FileIndexDocument fileIndexDocument);
	
	/**
	 * 拦截获取文件索引附加信息的每个字段类型
	 * @return
	 */
	public Map<String,Object> getFieldsType();
}
