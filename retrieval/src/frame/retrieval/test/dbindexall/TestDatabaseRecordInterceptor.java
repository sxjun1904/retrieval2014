package frame.retrieval.test.dbindexall;

import java.util.Map;

import frame.base.core.util.RegexUtil;
import frame.retrieval.engine.index.all.database.IBigDataOperator;
import frame.retrieval.engine.index.all.database.IIndexAllDatabaseRecordInterceptor;
import frame.retrieval.engine.index.all.database.impl.BigDataOperator;

public class TestDatabaseRecordInterceptor implements IIndexAllDatabaseRecordInterceptor{

	/**
	 * 将数据库中查询中的记录进行加工处理
	 * @param record
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map interceptor(Map record) {
		
		record.put("DOC_CREATE_TIME", String.valueOf(System.currentTimeMillis()));
		return record;
	}

	@SuppressWarnings("unchecked")
	public Map getFieldsType() {
		return null;
	}

}
