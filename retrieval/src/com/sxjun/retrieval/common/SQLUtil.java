package com.sxjun.retrieval.common;

import java.sql.Connection;

import com.sxjun.retrieval.pojo.Database;

import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.base.core.util.JdbcUtil;

public class SQLUtil {
	public  static final String INDEX_TRIGGER_RECORD= "index_trigger_record";
	
	public static String getTestSql(RDatabaseType databaseType) {
		String sql = null;
		if(databaseType!=null){
			if(RDatabaseType.MYSQL.equals(databaseType))
				sql = "select 1 from dual";
			else if(RDatabaseType.ORACLE.equals(databaseType))
				sql = "select 1  from dual";
			else if(RDatabaseType.SQLSERVER.equals(databaseType))
				sql = "select 1";

		}
		return sql;
	}
	
	public static String getTablesSql(RDatabaseType databaseType,String databaseName) {
		String sql = null;
		if(databaseType!=null){
			if(RDatabaseType.MYSQL.equals(databaseType))
				sql = "select table_name from information_schema.tables where table_schema='"+databaseName+"' union select table_name from information_schema.views where table_schema='"+databaseName+"'";
			else if(RDatabaseType.ORACLE.equals(databaseType))
				sql = "select  tname  from  (select table_name as tname from user_tables  union select view_name as tname from  user_views) order by tname asc";
			else if(RDatabaseType.SQLSERVER.equals(databaseType))
				sql = "select Name from SysObjects where XType='U' or xtype='V' order by Name";
			
		}
		return sql;
	}
	
	public static String getFieldsSql(RDatabaseType databaseType,String databaseName,String table) {
		String sql = null;
		if(databaseType!=null){
			if(RDatabaseType.MYSQL.equals(databaseType))
				sql = "SELECT COLUMN_NAME,COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+table+"'  AND table_schema = '"+databaseName+"'";
			else if(RDatabaseType.ORACLE.equals(databaseType))
				sql = "select column_name,data_type from user_tab_columns where table_name = '"+table+"' order by column_name asc";
			else if(RDatabaseType.SQLSERVER.equals(databaseType))
				sql = "select Name,type from SysColumns where id=Object_Id('"+table+"') order by Name";
			
		}
		return sql;
	}
	
	public static String getTableIsExistSql(RDatabaseType databaseType,String databaseName,String table) {
		String sql = null;
		if(databaseType!=null){
			if(RDatabaseType.MYSQL.equals(databaseType))
				sql = "select table_name from information_schema.tables where table_schema='"+databaseName+"' and upper(table_name)='"+table+"'";
			else if(RDatabaseType.ORACLE.equals(databaseType))
				sql = "select table_name from user_tables where upper(table_name)='"+table+"'";
			else if(RDatabaseType.SQLSERVER.equals(databaseType))
				sql = "select Name from SysObjects where XType='U' and upper(Name)='"+table+"'";
			
		}
		return sql;
	}
	
	/**
	 * 	add by sxjun 2014-1-25
	 * 表名+_A来替换_Add，用_U来替换_UPDATE，如果还是太长，则从表名前面开始阶段
	 * @param tablename
	 * @param type
	 * @return
	 */
	public static String getTriggerName(String tablename,String type){
		String triggername=null;
		int len = tablename.length();
		if(type.equals("C")){ //
			if(len<=24)
				triggername="TG_"+tablename+"_ADD";
			else if(len<26&&len>24)
				triggername="T_"+tablename+"_A";
			else 
				triggername="T_"+tablename.substring(0,26)+"_A";
			
		}else if(type.equals("U")){
			if(len<=20)
				triggername="TG_"+tablename+"_UPDATE";
			else if(len<26&&len>20)
				triggername="T_"+tablename+"_U";
			else 
				triggername="T_"+tablename.substring(0,26)+"_U";
		}else{
			if(len<=20)
				triggername="TG_"+tablename+"_DELETE";
			else if(len<26&&len>20)
				triggername="T_"+tablename+"_D";
			else 
				triggername="T_"+tablename.substring(0,26)+"_D";
		}
		return triggername;
	} 
	
	public static String getTriggerIsExist(RDatabaseType databaseType,String databaseName,String tablename,String type){
		
		String sql = null;
		if(databaseType!=null){
			String triggername = getTriggerName(tablename,type);
			if(RDatabaseType.MYSQL.equals(databaseType))
				sql = "SELECT * FROM information_schema.`TRIGGERS`where trigger_schema='"+databaseName+"' and trigger_name='"+triggername+"'";
			else if(RDatabaseType.ORACLE.equals(databaseType))
				sql = "select * into from user_triggers where trigger_name = '"+triggername+"';";
			else if(RDatabaseType.SQLSERVER.equals(databaseType))
				sql = "select * from sysobjects where id=object_id(N'"+triggername+"') and objectproperty(id,N'IsTrigger')=1'";
			
		}
		return sql;
	}
	
	public static String getCreateTriggerSql(RDatabaseType databaseType,String tablename,String primaryKey,String type) {
		String sql = null;
		if(databaseType!=null){
			if(RDatabaseType.MYSQL.equals(databaseType)){
				sql = "CREATE TRIGGER ";
				String triggername = getTriggerName(tablename,type);
				if(type.equals("C")){ //
					sql+=triggername+" AFTER INSERT on "
						+tablename+" FOR EACH ROW "
						+" BEGIN INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" VALUES(uuid(),'"+tablename
						+"','"+primaryKey+"',NEW."+primaryKey
						+",sysdate(),'I'); end;";
				}else if(type.equals("U")){
					sql+=triggername+" AFTER UPDATE on "
					+tablename+" FOR EACH ROW "
					+" BEGIN INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" VALUES(uuid(),'"+tablename
						+"','"+primaryKey+"',old."+primaryKey
						+",sysdate(),'U'); end;";
				}else{
					sql+=triggername+" AFTER DELETE on "
					+tablename+" FOR EACH ROW "
					+" BEGIN INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" VALUES(uuid(),'"+tablename
						+"','"+primaryKey+"',old."+primaryKey
						+",sysdate(),'D'); end;";
				}
			}else if(RDatabaseType.ORACLE.equals(databaseType)){
				sql = "CREATE OR REPLACE TRIGGER ";
				String triggername = getTriggerName(tablename,type);
				if(type.equals("C")){ //
					//oracle中触发器名称有限制，所以要截取，可能会造成触发器相同的情况
				//TODO
					sql+=triggername+" AFTER INSERT on "
					+tablename+" FOR EACH ROW "
					+" BEGIN INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" VALUES(DBMS_RANDOM.STRING ('X',32),'"+tablename
						+"','"+primaryKey+"',:NEW."+primaryKey
						+",sysdate,'I'); end;";
					
				}else if(type.equals("U")){
					sql+=triggername+" AFTER UPDATE on "
					+tablename+" FOR EACH ROW "
					+" BEGIN INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" VALUES(DBMS_RANDOM.STRING ('X',32),'"+tablename
						+"','"+primaryKey+"',:old."+primaryKey
						+",sysdate,'U'); end;";
				}else{
					sql+=triggername+" AFTER DELETE on "
					+tablename+" FOR EACH ROW "
					+" BEGIN INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" VALUES(DBMS_RANDOM.STRING ('X',32),'"+tablename
						+"','"+primaryKey+"',:old."+primaryKey
						+",sysdate,'D'); end;";
				}
			}
			else if(RDatabaseType.SQLSERVER.equals(databaseType)){
				sql = "CREATE TRIGGER ";
				String triggername = getTriggerName(tablename,type);
				if(type.equals("C")){ //
					sql+=triggername+" on "
					+tablename+" AFTER INSERT AS "
					+"  INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" (select newid(),'"+tablename
						+"','"+primaryKey+"', "+primaryKey+" "
						+",getdate(),'I' from Inserted); ";
					
					
				}else if(type.equals("U")){
					sql+=triggername+" on "
					+tablename+" AFTER UPDATE AS "
					+"  INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" (select newid(),'"+tablename
						+"','"+primaryKey+"', "+primaryKey+" "
						+",getdate(),'U' from Inserted); "; 
				}else{
					sql+=triggername+" on "
					+tablename+" AFTER DELETE AS "
					+"  INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" (select newid(),'"+tablename
						+"','"+primaryKey+"', "+primaryKey+" "
						+",getdate(),'D' from Deleted); ";
				}
			}
			
		}
		return sql;
	}
	
	/**
	 * 删除触发器
	 * @param type
	 */
	public static String getDeleteTriggerSql(RDatabaseType databaseType,String tablename,String type){
		String sql = "drop trigger ";
		if(RDatabaseType.MYSQL.equals(databaseType)){
			sql+=" IF EXISTS ";
		}
		String triggername = getTriggerName(tablename,type);
		sql += triggername;
		return sql;
	}
	
	
	public static String getIndexTriggerSql(RDatabaseType databaseType){
		String sql = "";
		if(databaseType!=null){
			if(RDatabaseType.MYSQL.equals(databaseType)){
				sql+="CREATE TABLE `index_trigger_record` ("
				  +"`ROWGUID` varchar(50) NOT NULL,"
				  +"`TABLENAME` varchar(50) DEFAULT NULL,"
				  +"`COLUMNNAME` varchar(50) DEFAULT NULL,"
				  +"`COLUMNVALUE` varchar(100) NOT NULL,"
				  +"`INSERTDATE` datetime DEFAULT NULL,"
				  +"`OPERATETYPE` varchar(10) DEFAULT NULL,"
				  +"`SYNC_DATE` datetime DEFAULT NULL,"
				  +"`SYNC_SIGN` varchar(10) DEFAULT NULL,"
				  +"PRIMARY KEY (`ROWGUID`)"
				  +") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
			}
			else if(RDatabaseType.ORACLE.equals(databaseType)){
				sql+="create table INDEX_TRIGGER_RECORD"
				 +"("
				 +" ROWGUID         NVARCHAR2(50) not null,"
				 +" TABLENAME       NVARCHAR2(50),"
				 +" COLUMNNAME      NVARCHAR2(50),"
				 +" COLUMNVALUE     NVARCHAR2(100),"
				 +" INSERTDATE      DATE,"
				 +" OPERATETYPE     NVARCHAR2(10),"
				 +" SYNC_DATE       DATE,"
				 +" SYNC_SIGN       NVARCHAR2(10)"
				 +" )";
			}
			else if(RDatabaseType.SQLSERVER.equals(databaseType)){
				sql+="CREATE TABLE [dbo].[INDEX_TRIGGER_RECORD] ("
						+"[ROWGUID] nvarchar(50) NOT NULL ,"
						+"[TABLENAME] nvarchar(50) NULL ,"
						+"[COLUMNNAME] nvarchar(50) NULL ,"
						+"[COLUMNVALUE] nvarchar(100) NOT NULL ,"
						+"[INSERTDATE] datetime NULL ,"
						+"[OPERATETYPE] nvarchar(10) NULL ,"
						+"[SYNC_DATE] datetime NULL ,"
						+"[SYNC_SIGN] nvarchar(10) NULL "
						+")";
			}
			
		}
		return sql;
	}
	
	public static Connection getConnection(Database db){
		RDatabaseType type = DictUtils.changeToRDatabaseType(db.getDatabaseType());
		String url = JdbcUtil.getConnectionURL(type, db.getIp(), db.getPort(), db.getDatabaseName());
		Connection conn =JdbcUtil.getConnection(type, url, db.getUser(), db.getPassword());
		return conn;
	}
}
