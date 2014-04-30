package frame.retrieval.oth.bigdata.impl;

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

import frame.retrieval.engine.RetrievalType;
import frame.retrieval.oth.bigdata.IDBBigFieldFileOperator;
import frame.base.core.util.JdbcUtil;

public class BigFieldSqlserverFileOperater implements IDBBigFieldFileOperator {

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
		BufferedReader reader= null;
		InputStream inStream = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(JdbcUtil.getSelectBigFieldSql(tableName,
					pkName, pkValue, bolbFieldName, false));
			if (rs.next()) {

				inStream = rs.getBinaryStream(1);
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
	public void setBlobByFile(Connection conn, boolean closeFlag,
			String tableName, String pkName, String pkValue,
			String bolbFieldName, String fileFullPath,Map<String,String> map) throws Exception {

		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue) || StringUtils.isBlank(bolbFieldName) || StringUtils.isBlank(fileFullPath))
			return;

		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			FileInputStream fin = new FileInputStream(fileFullPath);
			// 锁定数据行进行更新，注意“for update”语句
			String sql = "update " + tableName + " set " + getFieldName(bolbFieldName, map)
					+ "=? where " + pkName + " ='" + pkValue + "'";
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setBinaryStream(1, fin, fin.available());
			prestmt.executeUpdate();
			fin.close();
			conn.commit();
			JdbcUtil.closeConnection(conn, closeFlag);
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.closeConnection(conn, closeFlag);
		}

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
		BufferedReader in= null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(JdbcUtil.getSelectBigFieldSql(tableName,
					pkName, pkValue, colbFieldName, false));
			if (rs.next()) {
				Reader reader = rs.getCharacterStream(1);
				if(reader != null){
					in = new BufferedReader(reader);
					while ((line = in.readLine()) != null) {
						sb.append(line);
					}
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

		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			
			//编码后存在乱码问题，调整为不指定编码
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileFullPath)));
			// 锁定数据行进行更新，注意“for update”语句
			String sql = "update " + tableName + " set " + getFieldName(colbFieldName, map)+ "=? where " + pkName + " ='" + pkValue + "'";
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setCharacterStream(1, in);
			prestmt.executeUpdate();
			in.close();
			conn.commit();
			JdbcUtil.closeConnection(conn, closeFlag);
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
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
		String url = JdbcUtil.getConnectionURL(RetrievalType.RDatabaseType.SQLSERVER,
				"127.0.0.1", null, "AqiaoTest");
		Connection conn = JdbcUtil.getConnection(RetrievalType.RDatabaseType.SQLSERVER,
				url, "sa", "11111");
		IDBBigFieldFileOperator operater = new BigFieldSqlserverFileOperater();
		String fileFullPath = "";
		// // 测试clob写入 D:\temp\bigfieldtest\莆田市西天尾南少林路沥青加铺工程(路面部分)(投标).xml
		// fileFullPath = "D:\\temp\\bigfieldtest\\莆田市西天尾南少林路沥青加铺工程utf8.xml";
		// fileFullPath = "D:\\temp\\bigfieldtest\\toubiao.txt";
		// operater.setClobByFile(conn,false, "frame_attachstorage",
		// "attachguid",
		// "123456789", "Stringcontent", fileFullPath);
		// System.out.println("clob 写入完成");
		// // 测试clob读取
		// operater.getFileFromClob(conn,true, "frame_attachstorage",
		// "attachguid",
		// "123456789", "Stringcontent", "D:\\temp\\bigfieldtest_down");
		// System.out.println("clob 读取完成");
		// // 测试Blob写入
		// fileFullPath =
		// "D:\\temp\\bigfieldtest\\jboss-4.2.3.GA_精简版2.rar";
		// operater.setBlobByFile(conn, false, "frame_attachstorage",
		// "attachguid", "123456789", "content", fileFullPath);
		// System.out.println("blob写入完成");
		// // // 测试Blob读取
		 operater.getStringFromBlob(conn, true, "Frame_AttachStorage_FROM",
		 "attachguid", "041852aa-fa94-4247-a3d6-9c17b28012d9", "content",
		 "D:\\temp\\bigfieldtest_down",null);
		 System.out.println("blob读出完成");
	}

	@Override
	public void setBlobByStream(Connection conn, boolean closeFlag, String tableName, String pkName, String pkValue, InputStream inStream, String targetFieldName, Map<String, String> map) throws Exception {
		if (conn == null || StringUtils.isBlank(tableName) || StringUtils.isBlank(pkName) || StringUtils.isBlank(pkValue) )
			return;

		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			// 锁定数据行进行更新，注意“for update”语句
			String sql = "update " + tableName + " set " + targetFieldName + "=? where " + pkName + " ='" + pkValue + "'";
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setBinaryStream(1, inStream, inStream.available());
			prestmt.executeUpdate();
			inStream.close();
			conn.commit();
			JdbcUtil.closeConnection(conn, closeFlag);
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
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
		try {
			
			BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
			// 锁定数据行进行更新，注意“for update”语句
			String sql = "update " + tableName + " set " + targetFieldName+ "=? where " + pkName + " ='" + pkValue + "'";
			PreparedStatement prestmt = conn.prepareStatement(sql);
			prestmt.setCharacterStream(1, in);
			prestmt.executeUpdate();
			in.close();
			conn.commit();
			JdbcUtil.closeConnection(conn, closeFlag);
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		} finally {
			conn.setAutoCommit(defaultCommit);
			JdbcUtil.closeConnection(conn, closeFlag);
		}
	}

}
