package frame.retrieval.engine.index.all.database.impl.rdAbstract;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.logging.Log;

import frame.base.core.util.ReflectUtil;
import frame.base.core.util.RegexUtil;
import frame.retrieval.engine.RetrievalType.RDatabaseFieldType;
import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.context.DefaultRetrievalProperties;
import frame.retrieval.engine.index.RetrievalIndexException;
import frame.retrieval.engine.index.all.database.DatabaseLink;
import frame.retrieval.engine.index.all.database.IBigDataOperator;
import frame.retrieval.engine.index.all.database.impl.BigDataOperator;

/**
 * 内置对数据库中的记录批量创建索引接口默认实现
 * 
 * @author 
 *
 */
public class DefaultRDatabaseIndexAllImpl extends
		AbstractDefaultRDatabaseIndexAll {

	private static Log log=RetrievalUtil.getLog(DefaultRDatabaseIndexAllImpl.class);
	
	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	private Connection getConnection() {
		Connection conn = null;
		try {
			String default_retrieval_database_choose_class = DefaultRetrievalProperties.getDefault_retrieval_database_choose_class();
			String[] classAndMethod = default_retrieval_database_choose_class.split(":");
			String className = classAndMethod[0];
			String methodName = null;
			if (classAndMethod.length == 1) {
				methodName = "loadDatabaseLink";
			} else {
				methodName = classAndMethod[1];
			}
			// 反射调用
			DatabaseLink databaseLink = (DatabaseLink) ReflectUtil.invokeMethod(className, methodName);
			DbUtils.loadDriver(databaseLink.getJdbcDriver());
			conn = DriverManager.getConnection(databaseLink.getJdbcUrl(), databaseLink.getJdbcUser(), databaseLink.getJdbcPassword());
		} catch (SQLException e) {
			throw new RetrievalIndexException(e);
		}
		return conn;
	}
	
	private Connection getConnection(DatabaseLink databaseLink){
		Connection conn = null;
		try {
			DbUtils.loadDriver(databaseLink.getJdbcDriver());
			conn = DriverManager.getConnection(databaseLink.getJdbcUrl(), databaseLink.getJdbcUser(), databaseLink.getJdbcPassword());
		} catch (SQLException e) {
			throw new RetrievalIndexException(e);
		}
		return conn;
	}

	/**
	 * 关闭数据库连接
	 * @param conn
	 */
	private void close(Connection conn) {
		DbUtils.closeQuietly(conn);
	}

	/**
	 * 根据分页SQL获取数据
	 * @param limitSql			分页SQL
	 * @param params			SQL参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getResult(DatabaseLink databaseLink,String limitSql,Object[] params) {
		
		Connection conn = null;
		if(databaseLink!=null)
			conn = getConnection(databaseLink);
		else
			conn = getConnection();

		QueryRunner qRunner = new QueryRunner();
		ResultSetHandler rsh = new MapListHandler();
		List<Map> result = null;

		try {
			if (params != null && params.length > 0) {
				result = (List<Map>) qRunner.query(conn, limitSql, rsh, params);
			} else {
				result = (List<Map>) qRunner.query(conn, limitSql, rsh);
			}
			Map<String, Integer[]> fieldSpecialMapper = super.getDatabaseIndexAllItem().getFieldSpecialMapper();
			int length = result.size();
			//处理大字段blob、clob。以及去除html标签
			if(fieldSpecialMapper!=null&&!fieldSpecialMapper.isEmpty()){
				for(int i=0;i<length;i++){
					Map map=(Map)result.get(i);
					for (Map.Entry<String, Integer[]> m : fieldSpecialMapper.entrySet()) {
						String key = m.getKey();
						Integer[] vals = m.getValue();
						Arrays.sort(vals,Collections.reverseOrder());
						Object o = map.get(key);
						if(o!=null){
							int vlen = vals.length;
							if(vlen>0){
								IBigDataOperator bigDataOperator = new BigDataOperator();
								for(Integer val: vals){
									if(RDatabaseFieldType.SQL_FIELDTYPE_BLOB_OR_CLOB.getValue()==val){
										if(o.getClass().getSimpleName().equals("byte[]") || o.getClass().getSimpleName().equals("BLOB"))
											val = RDatabaseFieldType.SQL_FIELDTYPE_BLOB.getValue();
										else if(o.getClass().getSimpleName().equals("char[]") || o.getClass().getSimpleName().equals("CLOB"))
											val = RDatabaseFieldType.SQL_FIELDTYPE_CLOB.getValue();
									}
									if(RDatabaseFieldType.SQL_FIELDTYPE_BLOB.getValue()==val){
										if(o.getClass().getSimpleName().equals("byte[]"))
											o = bigDataOperator.getStringFromBlob((byte[]) o);
										else if(o.getClass().getSimpleName().equals("BLOB"))
											o = bigDataOperator.getStringFromBlob((Blob) o);
									}else if(RDatabaseFieldType.SQL_FIELDTYPE_CLOB.getValue()==val){
										if(o.getClass().getSimpleName().equals("char[]"))
											o = bigDataOperator.getStringFromClob((char[]) o);
										if(o.getClass().getSimpleName().equals("CLOB")){
											o = bigDataOperator.getStringFromClob((Clob) o);
										}
									}else if(RDatabaseFieldType.SQL_FIELDTYPE_RM_HTML.getValue()==val){
										o = RegexUtil.Html2Text(o.toString());
									}	
								}
								map.remove(key);
								map.put(key, o.toString());
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new RetrievalIndexException(e);
		} finally {
			close(conn);
		}
		return result;
	}
}
