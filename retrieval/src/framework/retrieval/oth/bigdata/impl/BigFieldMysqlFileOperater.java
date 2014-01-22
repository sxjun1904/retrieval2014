package framework.retrieval.oth.bigdata.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang.StringUtils;

import framework.base.snoic.base.util.JdbcUtil;
import framework.base.snoic.base.util.file.FileCharsetDetector;
import framework.retrieval.oth.bigdata.IDBBigFieldFileOperator;

public class BigFieldMysqlFileOperater implements IDBBigFieldFileOperator {

	@Override
	public String getStringFromBlob(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, String bolbFieldName, String destFolder,Map<String,String> map) throws Exception {
		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue) || StringUtils.isBlank(bolbFieldName) )
			return null;
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		BufferedReader reader = null;
		InputStream inStream = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(JdbcUtil.getSelectBigFieldSql(tableName, pkName, pkValue, bolbFieldName, false));
			if (rs.next()) {

				inStream = rs.getBinaryStream(1);
				inStream = rs.getAsciiStream(1);
				// data是读出并需要返回的数据，类型是byte[]
				if (inStream != null) {
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
				conn.commit();
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

	public static String getHexString(byte[] b) throws Exception {
		 String result = "";
		 for (int i=0; i < b.length; i++) {
		    result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		 }
		 return result;
	}

	
	@Override
	public String getStringFromClob(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, String colbFieldName, String destFolder,Map<String,String> map) throws Exception {
		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue) || StringUtils.isBlank(colbFieldName) )
			return "";
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		BufferedReader in = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(JdbcUtil.getSelectBigFieldSql(tableName, pkName, pkValue, colbFieldName, false));
			if (rs.next()) {
				Reader reader = rs.getCharacterStream(1);
				if (reader != null) {
					in = new BufferedReader(reader);
					while ((line = in.readLine()) != null) {
						sb.append(line);
					}
				} else {
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
	public void setBlobByFile(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, String bolbFieldName, String fileFullPath,Map<String,String> map) throws Exception {
		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue) || StringUtils.isBlank(bolbFieldName) || StringUtils.isBlank(fileFullPath))
			return;

		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		FileInputStream fin=null;
		try {
			fin = new FileInputStream(fileFullPath);
			// 锁定数据行进行更新，注意“for update”语句
			String sql = "update " + tableName + " set " + getFieldName(bolbFieldName, map) + "=? where " + pkName + " ='" + pkValue + "'";
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setBinaryStream(1, fin, fin.available());
			prestmt.executeUpdate();
			fin.close();
			conn.commit();
			prestmt.close();
			JdbcUtil.closeConnection(conn, closeFlag);
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
		    if(fin!=null){fin.close();}
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.closeConnection(conn, closeFlag);
		}


	}

	@Override
	public void setClobByFile(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, String colbFieldName, String fileFullPath,Map<String,String> map) throws Exception {
		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue) || StringUtils.isBlank(colbFieldName) || StringUtils.isBlank(fileFullPath))
			return;

		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		FileInputStream input=null;
        InputStreamReader reader=null;
        BufferedReader in = null;
        PreparedStatement prestmt=null;
		try {
			String encoding = new FileCharsetDetector().guestFileEncoding( fileFullPath, 2);
			input=new FileInputStream(fileFullPath);
			reader=new InputStreamReader(input, encoding);
			in = new BufferedReader(reader);
			// 锁定数据行进行更新，注意“for update”语句
			String sql = "update " + tableName + " set " + getFieldName(colbFieldName, map)
					+ "=? where " + pkName + " ='" + pkValue + "'";
			prestmt = conn.prepareStatement(sql);
			prestmt.setCharacterStream(1, in);
			prestmt.executeUpdate();
			conn.commit();
			JdbcUtil.closeConnection(conn, closeFlag);
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
		    if(in!=null){in.close();}
		    if(reader!=null){reader.close();}
		    if(input!=null){input.close();}
		    if(prestmt!=null){prestmt.close();}
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

	@Override
	public void setBlobByStream(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, InputStream inStream, String targetFieldName, Map<String, String> map) throws Exception {
		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue) )
			return;

		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			// 锁定数据行进行更新，注意“for update”语句
			String sql = "update " + tableName + " set " + targetFieldName
					+ "=? where " + pkName + " ='" + pkValue + "'";
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setBinaryStream(1, inStream, inStream.available());
			prestmt.executeUpdate();
			inStream.close();
			conn.commit();
			prestmt.close();
			JdbcUtil.closeConnection(conn, closeFlag);
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
		    if(inStream!=null){inStream.close();}
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.closeConnection(conn, closeFlag);
		}
	}

	@Override
	public void setClobByStream(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, InputStream inStream, String targetFieldName, Map<String, String> map) throws Exception {
		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue) )
			return;

		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
        InputStreamReader reader=null;
        BufferedReader in = null;
        PreparedStatement prestmt=null;
		try {
			reader=new InputStreamReader(inStream);
			in = new BufferedReader(reader);
			// 锁定数据行进行更新，注意“for update”语句
			String sql = "update " + tableName + " set " + targetFieldName
					+ "=? where " + pkName + " ='" + pkValue + "'";
			prestmt = conn.prepareStatement(sql);
			prestmt.setCharacterStream(1, in);
			prestmt.executeUpdate();
			conn.commit();
			JdbcUtil.closeConnection(conn, closeFlag);
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
		    if(in!=null){in.close();}
		    if(reader!=null){reader.close();}
		    if(inStream!=null){inStream.close();}
		    if(prestmt!=null){prestmt.close();}
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.closeConnection(conn, closeFlag);
		}

	}

}
