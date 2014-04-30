package frame.retrieval.engine.query.result;

import java.io.Serializable;
import java.util.Map;

import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.query.formatter.IHighlighterFactory;


/**
 * 数据库类型索引查询结果
 * @author 
 *
 */
public class DatabaseQueryResult extends QueryResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1394108956579978808L;

	public DatabaseQueryResult(IHighlighterFactory highlighterFactory,
			Map<String, String> queryResultMap) {
		super(highlighterFactory,queryResultMap);
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
