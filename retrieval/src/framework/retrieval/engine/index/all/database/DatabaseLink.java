package framework.retrieval.engine.index.all.database;

import org.apache.commons.lang3.StringUtils;

import framework.base.snoic.base.util.JdbcUtil;
import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.RetrievalType.RDatabaseType;

public class DatabaseLink {
	/**
	 * 数据库类型MySql、Oracle、SqlServer
	 */
	private RDatabaseType databaseType;
	/**
	 * 数据库驱动
	 */
	private String jdbcDriver="";
	/**
	 * 数据库地址
	 */
	private String jdbcUrl="";
	/**
	 * 数据库用户名
	 */
	private String jdbcUser="";
	/**
	 * 数据库密码
	 */
	private String jdbcPassword="";
	
	private DatabaseConnection databaseConnection = null;;
	
	public DatabaseLink(){
	}
	
	public DatabaseLink(RDatabaseType databaseType,DatabaseConnection databaseConnection,String jdbcUser,String jdbcPassword){
		this.databaseType = databaseType;
		this.databaseConnection = databaseConnection;
		this.jdbcUser = jdbcUser;
		this.jdbcPassword = jdbcPassword;
		this.jdbcUrl = JdbcUtil.getConnectionURL(databaseType,databaseConnection);
	}
	
	public DatabaseLink(RDatabaseType databaseType,String jdbcUrl,String jdbcUser,String jdbcPassword){
		this.databaseType = databaseType;
		this.jdbcUrl = jdbcUrl;
		this.jdbcUser = jdbcUser;
		this.jdbcPassword = jdbcPassword;
	}
	
	public DatabaseLink(String jdbcDriver,String jdbcUrl,String jdbcUser,String jdbcPassword){
		this.jdbcDriver = jdbcDriver;
		this.jdbcUrl = jdbcUrl;
		this.jdbcUser = jdbcUser;
		this.jdbcPassword = jdbcPassword;
	}
	
	public DatabaseLink(RDatabaseType databaseType,String jdbcDriver,String jdbcUrl,String jdbcUser,String jdbcPassword){
		this.databaseType = databaseType;
		this.jdbcDriver = jdbcDriver;
		this.jdbcUrl = jdbcUrl;
		this.jdbcUser = jdbcUser;
		this.jdbcPassword = jdbcPassword;
	}
	
	public String getJdbcDriver() {
		if(StringUtils.isBlank(jdbcDriver)&&databaseType!=null){
			jdbcDriver = JdbcUtil.getJdbcDriver(databaseType);
		}
		return jdbcDriver;
	}
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getJdbcUser() {
		return jdbcUser;
	}
	public void setJdbcUser(String jdbcUser) {
		this.jdbcUser = jdbcUser;
	}
	public String getJdbcPassword() {
		return jdbcPassword;
	}
	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}
	public RDatabaseType getDatabaseType() {
		return databaseType;
	}
	public void setDatabaseType(RDatabaseType databaseType) {
		this.databaseType = databaseType;
	}

	public DatabaseConnection getDatabaseConnection() {
		return databaseConnection;
	}

	public void setDatabaseConnection(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}
	
}
