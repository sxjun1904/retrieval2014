package frame.retrieval.engine.index.all.database;

import java.util.Map;

/**
 * 数据库批量索引时单条数据库记录索引创建拦截器
 * @author 
 *
 */
public interface IIndexAllDatabaseRecordInterceptor {
	
	/**
	 * 将数据库中查询中的记录进行加工处理
	 * @param record
	 * @param fieldType
	 * @return
	 */
	public Map<String,Object> interceptor(Map<String,Object> record);
	
	public Map<String,Object> interceptor(Map<String,Object> record,Map<String,String> fieldMapper);
	
	/**
	 * 获取每个字段类型
	 * @return
	 */
	public Map<String,Object> getFieldsType();
	
}
