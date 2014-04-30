package frame.retrieval.engine.index.all.database;


public class DatabaseConnection {
	/**
	 * 数据库ip
	 */
	private String ip="";
	/**
	 * 端口
	 */
	private String port="";
	/**
	 * 数据库名称
	 */
	private String databaseName="";
	
	public DatabaseConnection(){}
	
	public DatabaseConnection(String ip,String port,String databaseName){
		this.ip = ip;
		this.port = port;
		this.databaseName =databaseName;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
	
}
