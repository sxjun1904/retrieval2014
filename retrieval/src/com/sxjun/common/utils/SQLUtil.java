package com.sxjun.common.utils;

import java.sql.Connection;

import com.sxjun.retrieval.pojo.Database;

import frame.base.core.util.JdbcUtil;
import frame.retrieval.engine.RetrievalType.RDatabaseType;

public class SQLUtil {
	public  static final String INDEX_TRIGGER_RECORD= "INDEX_TRIGGER_RECORD";
	
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
				sql = "select TABLE_NAME from information_schema.tables where table_schema='"+databaseName+"' union select table_name from information_schema.views where table_schema='"+databaseName+"'";
			else if(RDatabaseType.ORACLE.equals(databaseType))
				sql = "select  tname as TABLE_NAME from  (select table_name as tname from user_tables  union select view_name as tname from  user_views) order by tname asc";
			else if(RDatabaseType.SQLSERVER.equals(databaseType))
				sql = "select Name as TABLE_NAME from SysObjects where XType='U' or xtype='V' order by Name";
			
		}
		return sql;
	}
	
	public static String getFieldsSql(RDatabaseType databaseType,String databaseName,String table) {
		String sql = null;
		if(databaseType!=null){
			if(RDatabaseType.MYSQL.equals(databaseType))
				sql = "SELECT COLUMN_NAME,COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '"+table+"'  AND table_schema = '"+databaseName+"'";
			else if(RDatabaseType.ORACLE.equals(databaseType))
				sql = "select COLUMN_NAME,DATA_TYPE from user_tab_columns where table_name = '"+table+"' order by column_name asc";
			else if(RDatabaseType.SQLSERVER.equals(databaseType))
				sql = "select Name as COLUMN_NAME,type as DATA_TYPE from SysColumns where id=Object_Id('"+table+"') order by Name";
			
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
				triggername="LG_"+tablename+"_ADD";
			else if(len<26&&len>24)
				triggername="L_"+tablename+"_A";
			else 
				triggername="L_"+tablename.substring(0,26)+"_A";
			
		}else if(type.equals("U")){
			if(len<=20)
				triggername="LG_"+tablename+"_UPDATE";
			else if(len<26&&len>20)
				triggername="L_"+tablename+"_U";
			else 
				triggername="L_"+tablename.substring(0,26)+"_U";
		}else{
			if(len<=20)
				triggername="LG_"+tablename+"_DELETE";
			else if(len<26&&len>20)
				triggername="L_"+tablename+"_D";
			else 
				triggername="L_"+tablename.substring(0,26)+"_D";
		}
		return triggername;
	} 
	
	public static String getTriggerLike(RDatabaseType databaseType,String databaseName){
		String sql = null;
		if(databaseType!=null){
			if(RDatabaseType.MYSQL.equals(databaseType))
				sql = "SELECT trigger_name FROM information_schema.`TRIGGERS`where trigger_schema='"+databaseName+"' and (trigger_name like'LG_%_ADD' or trigger_name like'L_%_A' or trigger_name like'LG_%_UPDATE' or trigger_name like'L_%_U' or trigger_name like'LG_%_DELETE' or trigger_name like'L_%_D')";
			else if(RDatabaseType.ORACLE.equals(databaseType))
				sql = "select trigger_name from user_triggers where trigger_name like 'LG_%_ADD' or trigger_name like'L_%_A' or trigger_name like'LG_%_UPDATE' or trigger_name like'L_%_U' or trigger_name like'LG_%_DELETE' or trigger_name like'L_%_D';";
			else if(RDatabaseType.SQLSERVER.equals(databaseType))
				sql = "select name as trigger_name from sysobjects where (name like 'LG_%_ADD' or name like'L_%_A' or name like'LG_%_UPDATE' or name like'L_%_U' or name like'LG_%_DELETE' or name like'L_%_D') and objectproperty(id,N'IsTrigger')=1";
		}
		return sql;
	}
	
	public static String getTriggerIsExist(RDatabaseType databaseType,String databaseName,String tablename,String type){
		
		String sql = null;
		if(databaseType!=null){
			String triggername = getTriggerName(tablename,type);
			if(RDatabaseType.MYSQL.equals(databaseType))
				sql = "SELECT * FROM information_schema.`TRIGGERS`where trigger_schema='"+databaseName+"' and trigger_name='"+triggername+"'";
			else if(RDatabaseType.ORACLE.equals(databaseType))
				sql = "select * from user_triggers where trigger_name = '"+triggername+"';";
			else if(RDatabaseType.SQLSERVER.equals(databaseType))
				sql = "select * from sysobjects where id=object_id(N'"+triggername+"') and objectproperty(id,N'IsTrigger')=1";
			
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
					+" BEGIN "
					+" delete from "+INDEX_TRIGGER_RECORD+" where TABLENAME='"+tablename+"' and COLUMNNAME='"+primaryKey+"' and Columnvalue=old."+primaryKey+" and Operatetype='U';"
					+" INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" VALUES(uuid(),'"+tablename
						+"','"+primaryKey+"',old."+primaryKey
						+",sysdate(),'U'); end;";
				}else{
					sql+=triggername+" AFTER DELETE on "
					+tablename+" FOR EACH ROW "
					+" BEGIN "
					+" declare insertcount int;"
					+" select count(*) into insertcount from "+INDEX_TRIGGER_RECORD+" where TABLENAME='"+tablename+"' and COLUMNNAME='"+primaryKey+"' and Columnvalue=old."+primaryKey+" and Operatetype='I';"
					+" Delete from "+INDEX_TRIGGER_RECORD+" where TABLENAME='"+tablename+"' and COLUMNNAME='"+primaryKey+"' and Columnvalue=old."+primaryKey+";"
					+" if(insertcount=0) then"
					+" INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" VALUES(uuid(),'"+tablename
						+"','"+primaryKey+"',old."+primaryKey
						+",sysdate(),'D');end if; end;";
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
					+" BEGIN "
					+" delete from "+INDEX_TRIGGER_RECORD+" where TABLENAME='"+tablename+"' and COLUMNNAME='"+primaryKey+"' and Columnvalue=:NEW."+primaryKey+" and Operatetype='U';"
					+" INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" VALUES(DBMS_RANDOM.STRING ('X',32),'"+tablename
						+"','"+primaryKey+"',:old."+primaryKey
						+",sysdate,'U'); end;";
				}else{
					sql+=triggername+" AFTER DELETE on "
					+tablename+" FOR EACH ROW "
					+" declare insertcount int;"
					+" BEGIN "
					+" select count(*) into insertcount from "+INDEX_TRIGGER_RECORD+" where TABLENAME='"+tablename+"' and COLUMNNAME='"+primaryKey+"' and Columnvalue=:NEW."+primaryKey+" and Operatetype='I';"
					+" Delete from "+INDEX_TRIGGER_RECORD+" where TABLENAME='"+tablename+"' and COLUMNNAME='"+primaryKey+"' and Columnvalue=:NEW."+primaryKey+";"
					+" if insertcount=0 then "
					+" INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" VALUES(DBMS_RANDOM.STRING ('X',32),'"+tablename
						+"','"+primaryKey+"',:old."+primaryKey
						+",sysdate,'D');end if; end;";
				}
			}
			else if(RDatabaseType.SQLSERVER.equals(databaseType)){
				sql = "CREATE TRIGGER ";
				String triggername = getTriggerName(tablename,type);
				if(type.equals("C")){ //
					sql+=triggername+" on "
					+tablename+" AFTER INSERT AS Begin"
					+"  INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" (select newid(),'"+tablename
						+"','"+primaryKey+"', "+primaryKey+" "
						+",getdate(),'I' from Inserted); end;";
					
					
				}else if(type.equals("U")){
					sql+=triggername+" on "
					+tablename+" AFTER UPDATE AS Begin "
					+" delete from "+INDEX_TRIGGER_RECORD+" where TABLENAME='"+tablename+"' and COLUMNNAME='"+primaryKey+"' and Columnvalue=(select "+primaryKey+" from Inserted)  and Operatetype='U';"
					+"  INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" (select newid(),'"+tablename
						+"','"+primaryKey+"', "+primaryKey+" "
						+",getdate(),'U' from Inserted);end; "; 
				}else{
					sql+=triggername+" on "
					+tablename+" AFTER DELETE AS" 
					+" declare insertcount int;"
					+" Begin"
					
					+" select insertcount = count(*) from "+INDEX_TRIGGER_RECORD+" where TABLENAME='"+tablename+"' and COLUMNNAME='"+primaryKey+"' and Columnvalue=(select "+primaryKey+" from Inserted) and Operatetype='I';"
					+" Delete from "+INDEX_TRIGGER_RECORD+" where TABLENAME='"+tablename+"' and COLUMNNAME='"+primaryKey+"' and Columnvalue=(select "+primaryKey+" from Inserted);"
					+" if (insertcount=0) begin "
					+"  INSERT INTO "+INDEX_TRIGGER_RECORD+"(ROWGUID,TABLENAME," +
						"COLUMNNAME,Columnvalue,INSERTDATE, Operatetype)" +
						" (select newid(),'"+tablename
						+"','"+primaryKey+"', "+primaryKey+" "
						+",getdate(),'D' from Deleted);end; end;";
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
	
	public static String getDeleteTriggerSql(RDatabaseType databaseType,String triggername){
		String sql = "drop trigger ";
		if(RDatabaseType.MYSQL.equals(databaseType)){
			sql+=" IF EXISTS ";
		}
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
