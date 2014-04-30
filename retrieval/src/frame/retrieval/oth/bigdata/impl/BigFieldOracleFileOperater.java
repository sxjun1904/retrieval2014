package frame.retrieval.oth.bigdata.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang.StringUtils;

import frame.retrieval.engine.RetrievalType;
import frame.retrieval.oth.bigdata.IDBBigFieldFileOperator;
import frame.base.core.util.JdbcUtil;
import frame.base.core.util.file.FileCharsetDetector;

public class BigFieldOracleFileOperater implements IDBBigFieldFileOperator {

	@Override
	public String getStringFromBlob(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, String bolbFieldName, String destFolder,Map<String,String> map) throws Exception {
		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue) || StringUtils.isBlank(bolbFieldName))
			return null;
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		BufferedReader reader = null;
		BufferedInputStream inStream = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(JdbcUtil.getSelectBigFieldSql(tableName,
					pkName, pkValue, bolbFieldName, false));
			if (rs.next()) {
				java.sql.Blob blob = (java.sql.Blob) rs.getBlob(1);

				if (blob != null) {
					inStream = new BufferedInputStream(blob.getBinaryStream());
					ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
					int ch;  
					   while ((ch = inStream.read()) != -1) {  
					    bytestream.write(ch);  
					   }  
					    
					bytestream.flush();
					bytestream.close();
					byte imgdata[] = bytestream.toByteArray(); 
					String result = getHexString(imgdata);
					sb.append(result);
				} else {
				}
			}
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
			if(reader!=null)
				reader.close();
			if(inStream!=null)
				inStream.close();
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stmt);
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.closeConnection(conn, closeFlag);
		}
		return sb.toString();
	}
	
	/**
	 * add by sxjun 将byte装成string
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public static String getHexString(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0xFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
	}
	
	@Override
	public void setBlobByFile(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue,String bolbFieldName, String fileFullPath,Map<String,String> map) throws Exception {

		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue)|| StringUtils.isBlank(bolbFieldName)|| StringUtils.isBlank(fileFullPath))
			return;

		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		ResultSet rs = null;
		Statement stmt = null;
		try {
			FileInputStream fin = new FileInputStream(fileFullPath);
			stmt = conn.createStatement();
			String targetFieldName=getFieldName(bolbFieldName, map);
			stmt.executeUpdate(JdbcUtil.getUpdateToEmptySql(tableName,
					pkName, pkValue, targetFieldName, false));
			rs = stmt.executeQuery(JdbcUtil.getSelectBigFieldSql(tableName,
					pkName, pkValue, targetFieldName, true));
			if (rs.next()) {
				Blob blob = rs.getBlob(1);
				OutputStream out = ((oracle.sql.BLOB) blob)
						.getBinaryOutputStream();
				byte[] b = new byte[((oracle.sql.BLOB) blob).getBufferSize()];
				int len = 0;
				while ((len = fin.read(b)) != -1)
					out.write(b, 0, len);
				fin.close();
				out.close();
				conn.commit();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stmt);
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.closeConnection(conn, closeFlag);
		}

	}
	
	@Override
	public void setBlobByStream(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue,InputStream inStream, String targetFieldName,Map<String,String> map) throws Exception {

		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue))
			return;

		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(JdbcUtil.getUpdateToEmptySql(tableName,pkName, pkValue, targetFieldName, false));
			rs = stmt.executeQuery(JdbcUtil.getSelectBigFieldSql(tableName,pkName, pkValue, targetFieldName, true));
			if (rs.next()) {
				Blob blob = rs.getBlob(1);
				OutputStream out = ((oracle.sql.BLOB) blob).getBinaryOutputStream();
				BufferedOutputStream output = new BufferedOutputStream(out);

				byte[] b = new byte[((oracle.sql.BLOB) blob).getBufferSize()];
				int len = 0;
				while ((len = inStream.read(b)) != -1)
					output.write(b, 0, len);
				output.flush();
				inStream.close();
				output.flush();
				output.close();
				out.close();
				conn.commit();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stmt);
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.closeConnection(conn, closeFlag);
		}

	}

	@Override
	public String getStringFromClob(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue,String colbFieldName, String destFolder,Map<String,String> map) throws Exception {

		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue)|| StringUtils.isBlank(colbFieldName) )
			return "";
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		String line = null;;
		BufferedReader in = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(JdbcUtil.getSelectBigFieldSql(tableName,
					pkName, pkValue, colbFieldName, false));
			if (rs.next()) {
				java.sql.Clob clob = (java.sql.Clob) rs.getClob(1);
				if (clob != null) {
					in = new BufferedReader(clob
							.getCharacterStream());
					while ((line = in.readLine()) != null) {
						sb.append(line);
					}
				} else {
					// 该字段没有clob值
				}
			}
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
			if(in!=null)
				in.close();
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stmt);
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.closeConnection(conn, closeFlag);
		}

		return sb.toString();
	}

	@Override
	public void setClobByFile(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, String colbFieldName, String fileFullPath,Map<String,String> map) throws Exception {

		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue) || StringUtils.isBlank(colbFieldName) || StringUtils.isBlank(fileFullPath))
			return;

		// 获得数据库连接
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String encoding = new FileCharsetDetector().guestFileEncoding(
					fileFullPath, 2);
			stmt = conn.createStatement();
			String targetFieldName=getFieldName(colbFieldName, map);
			stmt.executeUpdate(JdbcUtil.getUpdateToEmptySql(tableName,
					pkName, pkValue, targetFieldName, true));

			rs = stmt.executeQuery(JdbcUtil.getSelectBigFieldSql(tableName,
					pkName, pkValue, targetFieldName, true));
			if (rs.next()) {
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob(1);
				Writer outStream = new BufferedWriter(clob
						.getCharacterOutputStream());

				BufferedReader in = new BufferedReader(new InputStreamReader(
						new FileInputStream(fileFullPath), encoding));

				// data是传入的字符串，定义：String data
				String s = null;
				while ((s = in.readLine()) != null) {
					outStream.write(s);
				}
				in.close();
				outStream.flush();
				outStream.close();
				conn.commit();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stmt);
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.closeConnection(conn, closeFlag);
		}
	}
	
	@Override
	public void setClobByStream(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, InputStream inStream, String targetFieldName,Map<String,String> map) throws Exception {

		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue) )
			return;

		// 获得数据库连接
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(JdbcUtil.getUpdateToEmptySql(tableName, pkName, pkValue, targetFieldName, true));

			rs = stmt.executeQuery(JdbcUtil.getSelectBigFieldSql(tableName,
					pkName, pkValue, targetFieldName, true));
			if (rs.next()) {
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob(1);
				BufferedWriter outStream = new BufferedWriter(clob.getCharacterOutputStream());
				BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
				// data是传入的字符串，定义：String data
				String s = null;
				while ((s = in.readLine()) != null) {
					outStream.write(s);
				}
				in.close();
				outStream.flush();
				outStream.close();
				conn.commit();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(stmt);
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.closeConnection(conn, closeFlag);
		}
	}
	
	public String getFieldName(String sourceFieldName,Map<String,String> map){
        if(map!=null){
            String targetFieldName=map.get(sourceFieldName);
            if(targetFieldName!=null && !"".equals(targetFieldName)){
                return targetFieldName;
            }
        }
        return sourceFieldName;
    }

	public static void main(String[] args) throws Exception {
		String url = JdbcUtil.getConnectionURL(RetrievalType.RDatabaseType.ORACLE,
				"192.168.200.181", null, "orcl");
		Connection conn = JdbcUtil.getConnection(RetrievalType.RDatabaseType.ORACLE,
				url, "epointyz", "11111");
		BigFieldOracleFileOperater operater = new BigFieldOracleFileOperater();
		String fileFullPath = "";
		// // 测试clob写入 D:\temp\bigfieldtest\莆田市西天尾南少林路沥青加铺工程(路面部分)(投标).xml
		// fileFullPath = "D:\\temp\\bigfieldtest\\莆田市西天尾南少林路沥青加铺工程utf8.xml";
		// fileFullPath = "D:\\temp\\bigfieldtest\\toubiao.txt";
		// operater.setClobByFile(conn,false, "frame_attachstorage",
		// "attachguid",
		// "123456789", "Stringcontent", fileFullPath);
		// // 测试clob读取
		// operater.getFileFromClob(conn,true, "frame_attachstorage",
		// "attachguid",
		// "123456789", "Stringcontent", "D:\\temp\\bigfieldtest_down");
		// // 测试Blob写入
		// fileFullPath = "D:\\temp\\bigfieldtest\\即时通讯服务程序20110829.zip";
		// operater.setBlobByFile(conn, false, "frame_attachstorage",
		// "attachguid", "123456789", "content", fileFullPath);
		// System.out.println("blob写入完成");
		// // 测试Blob读取
		 operater.getStringFromBlob(conn, true, "frame_attachstorage",
		 "attachguid", "123456789", "content",
		 "D:\\temp\\bigfieldtest_down",null);
		 System.out.println("blob读出完成");
	}
}
