package frame.retrieval.engine.query.result;

import java.io.Serializable;
import java.util.Map;

import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.query.formatter.IHighlighterFactory;

/**
 * 文件类型索引查询结果
 * @author 
 *
 */
public class FileQueryResult extends QueryResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5999313009528821459L;
	
	public FileQueryResult(IHighlighterFactory highlighterFactory,
			Map<String, String> queryResultMap) {
		super(highlighterFactory,queryResultMap);
	}

	/**
	 * 获取文件ID
	 * @return
	 */
	public String getFileId(){
		return getResult(RetrievalType.RFileDocItemType._FID);
	}
	
	/**
	 * 获取文件名
	 * @return
	 */
	public String getFileName(){
		return getResult(RetrievalType.RFileDocItemType._FN);
	}
	
	/**
	 * 获取文件相对路径
	 * @return
	 */
	public String getFileRelativePath(){
		return getResult(RetrievalType.RFileDocItemType._FP);
	}
	
	/**
	 * 获取文件修改时间
	 * @return
	 */
	public String getFileModify(){
		return getResult(RetrievalType.RFileDocItemType._FM);
	}
	
	/**
	 * 获取文件内容
	 * @return
	 */
	public String getFileContent(){
		return getResult(RetrievalType.RFileDocItemType._FC);
	}
	
	/**
	 * 获取文件内容摘要
	 * @param resumeLength	摘要长度
	 * @return
	 */
	public String getFileContent(int resumeLength){
		return getHighlighterResult(RetrievalType.RFileDocItemType._FC,resumeLength);
	}
	
	/**
	 * 获取数据库表名
	 * @return
	 */
	public String getTableName(){
		return getResult(RetrievalType.RDatabaseDocItemType._DT);
	}
	
	/**
	 * 获取数据库记录ID
	 * @return
	 */
	public String getRecordId(){
		return getResult(RetrievalType.RDatabaseDocItemType._DID);
	}
	
	
}
