package frame.retrieval.engine.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;

import frame.base.core.util.PathUtil;
import frame.base.core.util.StringClass;
import frame.base.core.util.file.FileSizeHelper;
import frame.base.core.util.properties.ReadProperties;
import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.index.all.database.impl.rdAbstract.DefaultRDatabaseIndexAllImpl;

public class DefaultRetrievalProperties {
	private static Log log=RetrievalUtil.getLog(DefaultRetrievalProperties.class);
	private static final String RETRIEVAL_PROPERTY_FILE_NAME="retrieval-default.properties";
	
	/**
	 * 单线程默认运行数
	 */
	private static final int DEFAULT_RETRIEVAL_SINGLE_THREAD_DEALNUMS=200;
	private static final String PROPERTIES_RETRIEVAL_SINGLE_THREAD_DEALNUMS="RETRIEVAL_SINGLE_THREAD_DEALNUMS";
	
	/**
	 * 最大并发量
	 */
	private static final int DEFAULT_RETRIEVAL_CONCURRENT_THREAD_MAXNUM=50;
	private static final String PROPERTIES_RETRIEVAL_CONCURRENT_THREAD_MAXNUM="RETRIEVAL_CONCURRENT_THREAD_MAXNUM";
	
	private static final String DEFALUT_RETRIEVAL_WORKSPACE = PathUtil.getDefaultIndexPath();
	private static final String PROPERTIES_RETRIEVAL_WORKSPACE="PROPERTIES_RETRIEVAL_WORKSPACE";
	
	/**
	 * 生成索引路径
	 */
	private static  int default_single_thread_dealnums=DEFAULT_RETRIEVAL_SINGLE_THREAD_DEALNUMS;
	private static  int default_thread_maxnum=DEFAULT_RETRIEVAL_CONCURRENT_THREAD_MAXNUM; 
	private static String default_retrieval_workspace=DEFALUT_RETRIEVAL_WORKSPACE;
	
	/**
	 * 开机启动redis
	 */
	public static final String NO = "no";
	public static final String YES = "yes";
	private static final String DEFAULT_RETRIEVAL_REDIS_START = NO;
	private static final String PROPERTIES_RETRIEVAL_REDIS_START = "PROPERTIES_RETRIEVAL_REDIS_START";
	
	private static final String DEFAULT_RETRIEVAL_REDIS_SERVER_PATH = PathUtil.getDefaultRedisPath();
	private static final String PROPERTIES_RETRIEVAL_REDIS_SERVER_PATH = "PROPERTIES_RETRIEVAL_REDIS_SERVER_PATH";
	
	private static String default_retrieval_redis_start = DEFAULT_RETRIEVAL_REDIS_START;
	private static String default_retrieval_redis_server_path = DEFAULT_RETRIEVAL_REDIS_SERVER_PATH;
	
	
	/**
	 * 数据库连接地址（该功能现在基本已经不用，云因，只能连单个数据库）
	 */
	private static final String PARAM_NAME_JDBC_DRIVER="database.indexall.jdbc.driver";
	private static final String PARAM_NAME_JDBC_URL="database.indexall.jdbc.url";
	private static final String PARAM_NAME_JDBC_USER="database.indexall.jdbc.user";
	private static final String PARAM_NAME_JDBC_PASSWORD="database.indexall.jdbc.password";
	
	private static String jdbcDriver="";
	private static String jdbcUrl="";
	private static String jdbcUser="";
	private static String jdbcPassword="";
	
	/**
	 * 采用数据库的方式
	 */
	private static final String DEFAULT_RETRIEVAL_DATABASE_CHOOSE_CLASS="DEFAULT_RETRIEVAL_DATABASE_CHOOSE_CLASS";
	private static String default_retrieval_database_choose_class="";
	
	/**
	 * imageMagick安裝的路徑
	 */
	private static final String DEFAULT_RETRIEVAL_IMAGEMAGICK_PATH = "DEFAULT_RETRIEVAL_IMAGEMAGICK_PATH";
	private static String default_retrieval_imagemagick_path="";
	
	/**
	 * 單個索引文件夾大小設置
	 */
	private static final String DEFAULT_RETRIEVAL_INDEXFILE_SIZE = "DEFAULT_RETRIEVAL_INDEXFILE_SIZE";
	private static long default_retrieval_indexfile_size=2*FileSizeHelper.FORMAT_SIZE_G;
	
	/**
	 * 文件索引建完后文件夹备份路径
	 */
	private static final String DEFAULT_MOVE_FILE_AFTER_INDEX_FOLDER = "DEFAULT_MOVE_FILE_AFTER_INDEX_FOLDER";
	private static String default_move_file_after_index_folder = PathUtil.getDefaultMoveFileAfterIndexFolderPath();
	
	public DefaultRetrievalProperties(){
		
		ReadProperties readProperties=new ReadProperties();
		
		InputStream inputStream=DefaultRDatabaseIndexAllImpl.class.getResourceAsStream("/"+RETRIEVAL_PROPERTY_FILE_NAME);
		
		if(inputStream!=null){
			
			RetrievalUtil.debugLog(log, "发现DefaultRDatabaseIndexAllImpl属性配置文件:"+RETRIEVAL_PROPERTY_FILE_NAME+"，载入数据库配置");

			Properties properties=new Properties();
			
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				throw new RetrievalLoadException(e);
			}
			
			readProperties.setProperties(properties);
			readProperties.parse();
			String taskparam = null;
			taskparam = StringClass.getString(readProperties.readValue(PROPERTIES_RETRIEVAL_SINGLE_THREAD_DEALNUMS));
			if(!taskparam.equals("")){
				default_single_thread_dealnums = Integer.parseInt(taskparam);
			}
			taskparam = StringClass.getString(readProperties.readValue(PROPERTIES_RETRIEVAL_CONCURRENT_THREAD_MAXNUM));
			if(!taskparam.equals("")){
				default_thread_maxnum = Integer.parseInt(taskparam);
			}
			taskparam = StringClass.getString(readProperties.readValue(PROPERTIES_RETRIEVAL_WORKSPACE));
			if(!taskparam.equals("")){
				default_retrieval_workspace = taskparam;
			}
			taskparam = StringClass.getString(readProperties.readValue(PROPERTIES_RETRIEVAL_REDIS_START));
			if(!taskparam.equals("")){
				default_retrieval_redis_start = taskparam;
			}
			taskparam = StringClass.getString(readProperties.readValue(PROPERTIES_RETRIEVAL_REDIS_SERVER_PATH));
			if(!taskparam.equals("")){
				default_retrieval_redis_server_path = taskparam;
			}
			
			taskparam = StringClass.getString(readProperties.readValue(PARAM_NAME_JDBC_DRIVER));
			if(!taskparam.equals("")){
				jdbcDriver=taskparam;
			}
			taskparam = StringClass.getString(readProperties.readValue(PARAM_NAME_JDBC_URL));
			if(!taskparam.equals("")){
				jdbcUrl=taskparam;
			}
			taskparam = StringClass.getString(readProperties.readValue(PARAM_NAME_JDBC_USER));
			if(!taskparam.equals("")){
				jdbcUser=taskparam;
			}
			taskparam = StringClass.getString(readProperties.readValue(PARAM_NAME_JDBC_PASSWORD));
			if(!taskparam.equals("")){
				jdbcPassword=taskparam;
			}
			taskparam = StringClass.getString(readProperties.readValue(DEFAULT_RETRIEVAL_DATABASE_CHOOSE_CLASS));
			if(!taskparam.equals("")){
				default_retrieval_database_choose_class=taskparam;
			}
			taskparam = StringClass.getString(readProperties.readValue(DEFAULT_RETRIEVAL_IMAGEMAGICK_PATH));
			if(!taskparam.equals("")){
				default_retrieval_imagemagick_path=taskparam;
			}
			taskparam = StringClass.getString(readProperties.readValue(DEFAULT_RETRIEVAL_INDEXFILE_SIZE));
			if(!taskparam.equals("")){
				default_retrieval_indexfile_size=Long.valueOf(taskparam);
			}
			taskparam = StringClass.getString(readProperties.readValue(DEFAULT_MOVE_FILE_AFTER_INDEX_FOLDER));
			if(!taskparam.equals("")){
				default_move_file_after_index_folder=taskparam;
			}
			readProperties.close();
		}
	}

	public static int getDefault_single_thread_dealnums() {
		return default_single_thread_dealnums;
	}

	public static void setDefault_single_thread_dealnums(
			int default_single_thread_dealnums) {
		DefaultRetrievalProperties.default_single_thread_dealnums = default_single_thread_dealnums;
	}

	public static int getDefault_thread_maxnum() {
		return default_thread_maxnum;
	}

	public static void setDefault_thread_maxnum(int default_thread_maxnum) {
		DefaultRetrievalProperties.default_thread_maxnum = default_thread_maxnum;
	}

	public static String getDefault_retrieval_workspace() {
		return default_retrieval_workspace;
	}

	public static void setDefault_retrieval_workspace(
			String default_retrieval_workspace) {
		DefaultRetrievalProperties.default_retrieval_workspace = default_retrieval_workspace;
	}

	public static String getDefault_retrieval_redis_start() {
		return default_retrieval_redis_start;
	}

	public static void setDefault_retrieval_redis_start(
			String default_retrieval_redis_start) {
		DefaultRetrievalProperties.default_retrieval_redis_start = default_retrieval_redis_start;
	}

	public static String getDefault_retrieval_redis_server_path() {
		return default_retrieval_redis_server_path;
	}

	public static void setDefault_retrieval_redis_server_path(
			String default_retrieval_redis_server_path) {
		DefaultRetrievalProperties.default_retrieval_redis_server_path = default_retrieval_redis_server_path;
	}

	public static String getJdbcDriver() {
		return jdbcDriver;
	}

	public static void setJdbcDriver(String jdbcDriver) {
		DefaultRetrievalProperties.jdbcDriver = jdbcDriver;
	}

	public static String getJdbcUrl() {
		return jdbcUrl;
	}

	public static void setJdbcUrl(String jdbcUrl) {
		DefaultRetrievalProperties.jdbcUrl = jdbcUrl;
	}

	public static String getJdbcUser() {
		return jdbcUser;
	}

	public static void setJdbcUser(String jdbcUser) {
		DefaultRetrievalProperties.jdbcUser = jdbcUser;
	}

	public static String getJdbcPassword() {
		return jdbcPassword;
	}

	public static void setJdbcPassword(String jdbcPassword) {
		DefaultRetrievalProperties.jdbcPassword = jdbcPassword;
	}

	public static String getDefault_retrieval_database_choose_class() {
		return default_retrieval_database_choose_class;
	}

	public static void setDefault_retrieval_database_choose_class(
			String default_retrieval_database_choose_class) {
		DefaultRetrievalProperties.default_retrieval_database_choose_class = default_retrieval_database_choose_class;
	}

	public static String getDefault_retrieval_imagemagick_path() {
		return default_retrieval_imagemagick_path;
	}

	public static void setDefault_retrieval_imagemagick_path(
			String default_retrieval_imagemagick_path) {
		DefaultRetrievalProperties.default_retrieval_imagemagick_path = default_retrieval_imagemagick_path;
	}

	public static long getDefault_retrieval_indexfile_size() {
		return default_retrieval_indexfile_size;
	}

	public static void setDefault_retrieval_indexfile_size(
			long default_retrieval_indexfile_size) {
		DefaultRetrievalProperties.default_retrieval_indexfile_size = default_retrieval_indexfile_size;
	}

	public static String getDefault_move_file_after_index_folder() {
		return default_move_file_after_index_folder;
	}

	public static void setDefault_move_file_after_index_folder(
			String default_move_file_after_index_folder) {
		DefaultRetrievalProperties.default_move_file_after_index_folder = default_move_file_after_index_folder;
	}

}
