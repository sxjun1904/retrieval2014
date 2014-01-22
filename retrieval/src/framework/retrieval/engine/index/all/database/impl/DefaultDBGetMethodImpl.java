package framework.retrieval.engine.index.all.database.impl;

import framework.retrieval.engine.context.DefaultRetrievalProperties;
import framework.retrieval.engine.index.all.database.DatabaseLink;
import framework.retrieval.engine.index.all.database.IDBGetMethod;

/**
 * 从配置文件中获取数据源信息
 * @author sxjun
 *
 */
public class DefaultDBGetMethodImpl implements IDBGetMethod{
	@Override
	public DatabaseLink loadDatabaseLink() {
		DatabaseLink databaseLink= new DatabaseLink();
		databaseLink.setJdbcDriver(DefaultRetrievalProperties.getJdbcDriver());
		databaseLink.setJdbcUrl(DefaultRetrievalProperties.getJdbcUrl());
		databaseLink.setJdbcUser(DefaultRetrievalProperties.getJdbcUser());
		databaseLink.setJdbcPassword(DefaultRetrievalProperties.getJdbcPassword());
		return databaseLink;
	}

}
