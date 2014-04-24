package framework.base.snoic.base.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.RetrievalType.RDatabaseType;
import framework.retrieval.engine.index.all.database.DatabaseConnection;

/**
 * 
 * jdbc工具类
 * 
 */
public class JdbcUtil {
	//添加日志，测试队列信息
	private static Logger log = Logger.getLogger(JdbcUtil.class.getName());
	private DatabaseConnection databaseConnection = null;
	public static String getConnectionURL(RetrievalType.RDatabaseType databaseType, DatabaseConnection databaseConnection) {
		return getConnectionURL(databaseType, databaseConnection.getIp(),databaseConnection.getPort(), databaseConnection.getDatabaseName()); 
	}
	
	public static Connection getConnection(RetrievalType.RDatabaseType databaseType, DatabaseConnection databaseConnection) {
		return getConnection( databaseType, databaseConnection.getIp(),databaseConnection.getPort(), databaseConnection.getDatabaseName());
	}
	/**
	 * 获取数据库链接字符串
	 * 
	 * @param dbtype
	 * @param server
	 * @param port
	 * @param dbname
	 * @return
	 */
	public static String getConnectionURL(RetrievalType.RDatabaseType databaseType, String ip,String port, String dbname) {
		if (StringUtils.isBlank(ip))
			return null;
		String url = null;
		if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.ORACLE)) {
			String realserver = (ip.indexOf(":") != -1) ? ip : (ip + ":" + (StringUtils.isBlank(port) ? "1521" : port));
			url = "jdbc:oracle:thin:@" + realserver + ":" + dbname + "";
		} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.SQLSERVER)) {
			String realserver = (ip.indexOf(":") != -1) ? ip : (ip + ":" + (StringUtils.isBlank(port) ? "1433" : port));
			url = "jdbc:sqlserver://" + realserver + ";DatabaseName=" + dbname + "";
		} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.MYSQL)) {
			String realserver = (ip.indexOf(":") != -1) ? ip : (ip + ":" + (StringUtils.isBlank(port) ? "3306" : port));
			url = "jdbc:mysql://" + realserver + "/" + dbname + "?characterEncoding=utf8";
		}
		return url;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @param dbType 数据库类型 mysql ,sqlserver,oracle
	 * @param url 数据库地址
	 * @param user 用户名
	 * @param password 密码
	 * @return Connection
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static Connection getConnection(RetrievalType.RDatabaseType databaseType, String str_url,String str_user, String str_pwd) {
		if (databaseType == null)
			return null;
		String driver = getJdbcDriver(databaseType);

		Connection conn = null;
		DbUtils.loadDriver(driver);
		try {
			conn = DriverManager.getConnection(str_url, str_user, str_pwd);
		} catch (SQLException e) {
			log.error("获取连接异常", e);
		}
		return conn;
	}
	
	public static String getJdbcDriver(RetrievalType.RDatabaseType databaseType) {
		String jdbcDriver = null;
		if(databaseType!=null){
			if(RDatabaseType.MYSQL.equals(databaseType))
				jdbcDriver = RetrievalType.RDatabaseDriver.MYSQL.getValue();
			else if(RDatabaseType.ORACLE.equals(databaseType))
				jdbcDriver = RetrievalType.RDatabaseDriver.ORACLE.getValue();
			else if(RDatabaseType.SQLSERVER.equals(databaseType))
				jdbcDriver = RetrievalType.RDatabaseDriver.SQLSERVER.getValue();
		}
		return jdbcDriver;
	}
	

	/**
	 * 根据主键---实现删除功能 ，不关闭连接
	 * 
	 * @param Connection
	 * 
	 * @param tableName
	 *            表名
	 * @param keyName
	 *            主键名字
	 * @param keyValue
	 *            主键值
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static void deleteByPrimekey(Connection conn, String tableName,String keyName, String keyValue, boolean closeFlag) {
		if (conn == null)
			return;
		String sql = "delete from " + tableName + " where " + keyName + "='"+ keyValue + "'";
		QueryRunner qr = new QueryRunner();
		try {
			qr.update(conn, sql);
		} catch (SQLException e) {
			log.error("删除数据异常", e);
		}
		closeConnection(conn, closeFlag);
	}

	public static int executeSql(Connection conn, String sql, boolean closeFlag) {
		if (conn == null)
			return 0;
		QueryRunner qr = new QueryRunner();
		int rnt = 0;
		try {
			rnt = qr.update(conn, sql);
		} catch (SQLException e) {
			log.error("sql异常", e);
		}
		if(closeFlag){
		closeConnection(conn, closeFlag);
		}
		return rnt;
	}
	
	public static int executeSqlWithException(Connection conn, String sql, boolean closeFlag) throws SQLException {
		if (conn == null)
			return 0;
		QueryRunner qr = new QueryRunner();
		int rnt = qr.update(conn, sql);
		if(closeFlag){
		closeConnection(conn, closeFlag);
		}
		return rnt;
	}
	
	public static int executeNoPreSql(Connection conn, String sql, boolean closeFlag){
		if (conn == null)
			return 0;
		Statement stm=null;
		int rnt=0;
		try {
			stm=conn.createStatement();
			rnt=stm.executeUpdate(sql);
		} catch (SQLException e) {
			log.error("sql执行异常", e);
		}
		if(closeFlag){
			closeConnection(conn, closeFlag);
		}
		return rnt;
	}

	public static List<?> getBeanList(Connection conn, String sql, Class cls,boolean closeFlag) {
		if (conn == null)
			return null;
		QueryRunner qr = new QueryRunner();
		List results = null;
		try {
			results = (List) qr.query(conn, sql, new BeanListHandler(cls));
		} catch (SQLException e) {
			log.error("查询数据异常", e);
		}
		closeConnection(conn, closeFlag);
		return results;
	}
	
	public static List<?> getMapList(Connection conn, String sql,boolean closeFlag) {
		if (conn == null)
			return null;
		QueryRunner qr = new QueryRunner();
		List results = null;
		try {
			results = (List) qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			log.error("查询数据异常", e);
		}
		closeConnection(conn, closeFlag);
		return results;
	}
	
	@Deprecated
	public static void deleteByPrimekeyNotSafe(Connection conn,String tableName, String keyName, String keyValue) { 
		deleteByPrimekey(conn, tableName, keyName, keyValue, false);
	}

	/**
	 * 根据主键---实现删除功能 ，关闭连接
	 * 
	 * @param Connection
	 * 
	 * @param tableName
	 *            表名
	 * @param keyName
	 *            主键名字
	 * @param keyValue
	 *            主键值
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	@Deprecated
	public static void deleteByPrimekeySafe(Connection conn, String tableName,String keyName, String keyValue) {
		deleteByPrimekey(conn, tableName, keyName, keyValue, true);
	}

	/**
	 * 执行sql，不关闭连接
	 * @param sql 原生sql
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	@Deprecated
	public static int executeSqlNotSafe(Connection conn, String sql) {
		return executeSql(conn, sql, false);
	}

	/**
	 * 执行上去了，自动关闭连接 [功能详细描述]
	 * @param conn
	 * @param sql
	 * @return
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	@Deprecated
	public static int executeSqlSafe(Connection conn, String sql) {
		return executeSql(conn, sql, true);
	}

	/**
	 * 
	 * 获取列表，不关闭连接
	 * @param conn
	 * @param sql
	 * @param cls
	 * @return
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public static List<?> getBeanListNotSafe(Connection conn, String sql,Class cls) {
		return getBeanList(conn, sql, cls, false);
	}

	@SuppressWarnings("unchecked")
	@Deprecated
	public static List<?> getBeanListSafe(Connection conn, String sql, Class cls) {
		return getBeanList(conn, sql, cls, true);
	}

	@SuppressWarnings("unchecked")
	@Deprecated
	public static List<?> getMapListNotSafe(Connection conn, String sql) {
		return getMapList(conn, sql, false);
	}

	@SuppressWarnings("unchecked")
	@Deprecated
	public static List<?> getMapListSafe(Connection conn, String sql) {
		return getMapList(conn, sql, true);
	}


	/**
	 * 根据sql获取一条数据对象
	 * 
	 * @param conn
	 * @param sql
	 * @param closeFlag ，true则关闭连接，false 则不关闭连接
	 * @return
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static Map getSingleMapObject(Connection conn, String sql,
			boolean closeFlag) {
		if (conn == null)
			return null;
		QueryRunner qr = new QueryRunner();
		Map map = null;
		try {
			map = qr.query(conn, sql, new MapHandler());
		} catch (SQLException e) {
			log.error("查询数据异常", e);
		}
		if (closeFlag) {
			closeConnection(conn);
		}
		return map;
	}

	/**
	 * 根据sql获取某一条数据的某一个字段值
	 * 
	 * @param conn
	 * @param ObjectName 字段名称
	 * @param sql
	 * @return
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static Object getObject(Connection conn, String ObjectName,String sql) {
		if (conn == null)
			return null;
		QueryRunner qr = new QueryRunner();
		Map map = null;
		try {
			map = qr.query(conn, sql, new MapHandler());
			if (map == null)
				return null;
		} catch (SQLException e) {
			log.error("查询数据异常", e);
		}
		closeConnection(conn);
		return map.get(ObjectName);

	}

	/**
	 * 获取查询语句
	 */
	public static String getSelectBigFieldSql(String tableName, String pkName,
		String pkValue, String bolbFieldName, boolean needUpdate) {
		String sql = "select " + bolbFieldName + " from " + tableName + " where " + pkName + " = '" + pkValue + "' ";
		if (needUpdate) {
			return sql + " for update ";
		} else {
			return sql;
		}
	}

	public static String getUpdateToEmptySql(String tableName, String pkName,String pkValue, String fieldName, boolean isClob) {
		String emptyValue = "";
		if (isClob) {
			emptyValue = "empty_clob()";
		} else {
			emptyValue = "empty_blob()";
		}
		String sql = "UPDATE " + tableName + " SET " + fieldName + "="
				+ emptyValue + " WHERE " + pkName + "='" + pkValue + "'";
		return sql;
	}
	
	public static String getUpdateToOracleBlobSql(String tableName, String pkName, String pkValue, String fieldName, String byteValue) {
		String sql = "UPDATE " + tableName + " SET " + fieldName + "=" +" to_blob('"+byteValue+"')" + " WHERE " + pkName + "='" + pkValue + "'";
		return sql;
	}

	/**
	 * 批处理操作的应用
	 * 
	 * @author chenj
	 * 
	 * @param sql
	 * @param params
	 * @param closeFlag
	 *            是否关闭连接
	 * @return
	 * @throws SQLException
	 */
	public static int[] executeUpdateBatch(Connection conn, String sql, Object[][] params, boolean closeFlag) throws SQLException {
		QueryRunner qr = new QueryRunner();

		int[] count = qr.batch(conn, sql, params);
		if (closeFlag) {// 关闭数据库连接
			closeConnection(conn);
		}
		return count;

	}

	/**
	 * 单条数据的增删改
	 * @param conn
	 * @param sql
	 * @param params
	 * @param closeFlag
	 * @return
	 * @throws SQLException
	 */
	public static int executeSingleSql(Connection conn, String sql,
			Object[] params, boolean closeFlag) throws SQLException {
		QueryRunner qr = new QueryRunner();
		int count = qr.update(conn, sql, params);
		if (closeFlag) {// 关闭数据库连接
			closeConnection(conn); 
		}
		return count;
	}

	public static void closeConnection(Connection conn) {
		DbUtils.closeQuietly(conn);
	}

	public static void closeConnection(Connection conn, boolean closeFlag) {
		if (closeFlag) {
			DbUtils.closeQuietly(conn);
		}
	}

	public static void main(String[] args) throws SQLException {
		// // String url = "jdbc:oracle:thin:@192.168.200.181:1521:orcl";
		// // String url =
		// // "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=AqiaoTest";
		// String url = "jdbc:mysql://127.0.0.1:3306/test";
		// Connection conn = JdbcDbUtil.getConnection(DataEXKeyNames.DB_MYSQL,
		// url, "root", "11111");
		// // String sql =
		// //
		// "select attachguid,to_char(datafield,'yyyy-MM-dd hh24:mi:ss') datafield from  aqiao_test_field where rownum<5";
		// String sql =
		// "select guid,ordernumber,address,time from  receivedata where guid='aaaaaaaaaaaaaaaaaaaaaaaaaa'";
		// // List<Map> list = (List<Map>) JdbcDbUtil.getMapList(conn, sql,
		// true);
		// // Map map = list.get(0);
		// Map map = JdbcDbUtil.getSingleMapObject(conn, sql, true);
		// // Timestamp obj = (Timestamp) map.get("datafield");
		//
		// // Date d=(Date) map.get("datetimefiled");
		// // SimpleDateFormat sdf = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// // System.out.println(sdf.format(d));
		// System.out.println("");

		JdbcUtil util = new JdbcUtil();
		String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=AqiaoTest";
		// String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		Connection conn = util.getConnection(RetrievalType.RDatabaseType.SQLSERVER, url,
				"sa", "11111");
		// Object obj = util.getObject(conn, "create_date",
		// "select id ,name,create_date from TestTable where id=2");
		// System.out.println(obj);
		Object[][] p = new Object[1][3];
		p[0][0] = "99";
		p[0][1] = new Timestamp(Calendar.getInstance().getTimeInMillis());
		p[0][2] = "11";
		// p[1][0] = "22";
		// p[1][1] = "99";
		// p[1][2] = new Timestamp(Calendar.getInstance().getTimeInMillis());
		// String
		// sql="insert into stepBystep3 (id,name3,birthday) values(?,?,?)";
		String sql = "update " + "stepBystep3"
				+ " set sync_sign3=? , sysc_date3=? where id=?";
		try {
			util.executeUpdateBatch(conn, sql, p, true);
		} catch (Exception e) {
			log.error("执行数据异常", e);
		}

	}

}
