package com.sxjun.retrieval.pojo;

import com.sxjun.system.pojo.BasePojo;

import frame.retrieval.engine.RetrievalType.RDatabaseType;



public class Database extends BasePojo{
	private static final long serialVersionUID = 6556731078394748188L;
	/**
	 * 数据库名
	 */
	private String databaseName;
	/**
	 * 数据库类型
	 */
	private String databaseType;
	/**
	 * ip
	 */
	private String ip;
	/**
	 * port
	 */
	private String port;
	/**
	 * 用户名
	 */
	private String user;
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 获取数据库名
	 * @return
	 */
	public String getDatabaseName() {
		return databaseName;
	}
	/**
	 * 设置数据库名
	 * @param databaseName
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	/**
	 * 
	 * @return
	 */
	public String getDatabaseType() {
		return databaseType;
	}
	/**
	 * 
	 * @param databaseType
	 */
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
	/**
	 * 获取ip
	 * @return
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置ip
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取port
	 * @return
	 */
	public String getPort() {
		return port;
	}
	/**
	 * 设置port
	 * @param port
	 */
	public void setPort(String port) {
		this.port = port;
	}
	/**
	 * 获取用户名
	 * @return
	 */
	public String getUser() {
		return user;
	}
	/**
	 * 设置用户名
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * 获取密码
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置密码
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
