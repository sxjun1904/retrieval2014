/**
 * Copyright 2010 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package framework.retrieval.engine.index.all.database.impl.rdAbstract;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.RetrievalType.RDatabaseType;
import framework.retrieval.engine.index.RetrievalIndexException;
import framework.retrieval.engine.index.all.database.DatabaseLink;
import framework.retrieval.engine.index.all.database.impl.AbstractRDatabaseIndexAll;
import framework.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

/**
 * 对数据库中的记录批量创建索引接口实现抽象类
 * @author 
 *
 */
public abstract class AbstractDefaultRDatabaseIndexAll extends AbstractRDatabaseIndexAll{

	/**
	 * 获取当前页数据库记录
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getResultList(){
		RDatabaseIndexAllItem item=getDatabaseIndexAllItem();
		
		String sql=item.getSql();
		DatabaseLink databaseLink = super.getDatabaseIndexAllItem().getDatabaseLink();
		RDatabaseType databaseType = databaseLink.getDatabaseType();
		String databaseDriver = databaseLink.getJdbcDriver();
		String limitSql = null;
		if(databaseType==RDatabaseType.MYSQL||RetrievalType.RDatabaseDriver.MYSQL.getValue().equals(databaseDriver))
			limitSql=getLimitString_MySql(sql, getNowCount(), item.getPageSize());
		else if(databaseType==RDatabaseType.ORACLE||RetrievalType.RDatabaseDriver.ORACLE.getValue().equals(databaseDriver))
			limitSql=getLimitString_Oracle(sql, getNowCount(), item.getPageSize());
		else if(databaseType==RDatabaseType.SQLSERVER||RetrievalType.RDatabaseDriver.SQLSERVER.getValue().equals(databaseDriver))
			limitSql=getLimitString_SqlServer(sql, getNowCount(), item.getPageSize());
		
		setNowCount(getNowCount()+item.getPageSize());
		try {
			return this.getResult(databaseLink,limitSql, item.getParam());
		} catch (Exception e) {
			throw new RetrievalIndexException(e);
		}
	}
	
	/**
	 * 根据分页SQL获取数据
	 * @param limitSql			分页SQL
	 * @param params			SQL参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract List<Map> getResult(DatabaseLink databaseLink,String limitSql,Object[] params);
	
}
