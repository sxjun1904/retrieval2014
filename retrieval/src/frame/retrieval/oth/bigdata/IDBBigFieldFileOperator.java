package frame.retrieval.oth.bigdata;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

public interface IDBBigFieldFileOperator {

	/**
	 * 从数据库中获取指定Blob字段
	 * 
	 * @param conn
	 * @param closeFlag
	 * @param tableName
	 * @param pkName
	 * @param pkValue
	 * @param bolbFieldName
	 * @param destFolder
	 * @return
	 * @throws Exception
	 */
	public String getStringFromBlob(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, String bolbFieldName, String destFolder,Map<String,String> map) throws Exception;

	/**
	 * 从数据库中获取指定CLOB字段中
	 * 
	 * @param conn
	 * @param closeFlag
	 * @param tableName
	 * @param pkName
	 * @param pkValue
	 * @param colbFieldName
	 * @param destFolder
	 * @return
	 * @throws Exception
	 */
	public String getStringFromClob(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, String colbFieldName, String destFolder,Map<String,String> map) throws Exception;

	/**
	 * 奖文件写入指定Clob字段
	 * 
	 * @param conn
	 * @param closeFlag
	 * @param tableName
	 * @param pkName
	 * @param pkValue
	 * @param colbFieldName
	 * @param fileFullPath
	 * @throws Exception
	 */
	public void setClobByFile(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, String colbFieldName, String fileFullPath,Map<String,String> map) throws Exception;

	/**
	 * 将文件写入指定的Blob字段中
	 * 
	 * @param conn
	 * @param closeFlag
	 * @param tableName
	 * @param pkName
	 * @param pkValue
	 * @param bolbFieldName
	 * @param fileFullPath
	 * @throws Exception
	 */
	public void setBlobByFile(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, String bolbFieldName, String fileFullPath,Map<String,String> map) throws Exception;

	/**
	 * @param conn
	 * @param closeFlag
	 * @param tableName
	 * @param pkName
	 * @param pkValue
	 * @param inStream
	 * @param targetFieldName
	 * @param map
	 * @throws Exception
	 */
	public void setBlobByStream(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, InputStream inStream, String targetFieldName, Map<String, String> map) throws Exception;

	/**
	 * @param conn
	 * @param closeFlag
	 * @param tableName
	 * @param pkName
	 * @param pkValue
	 * @param inStream
	 * @param targetFieldName
	 * @param map
	 * @throws Exception
	 */
	public void setClobByStream(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, InputStream inStream, String targetFieldName, Map<String, String> map) throws Exception;
}
