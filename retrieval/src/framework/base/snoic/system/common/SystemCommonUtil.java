/**
 * Copyright 2010 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package framework.base.snoic.system.common;


/**
 * 存放系统公用变量
 * @author 
 *
 */
public class SystemCommonUtil {

	///////////////////////////////////////////////////////////
	////////////配置文件路径      BEGIN                     /////
	//////////////////////////////////////////////////////////
	
    /**
     * 系统对象池名称
     */
	public final static String SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME="SNOICS_snoicssystem_systemobjectpool";

	/**
	 * 存放系统配置文件路径的文件名
	 */
	public final static String PROPERTIES_CONFIGPATH_FILENAME="snoics-configpath.xml";
	/**
	 * 存放系统配置文件路径的节点
	 */
	public final static String PROPERTIES_CONFIGPATH_FILENAME_NODE_PATH="path";
	/**
	 * 存放系统配置文件路径的节点-属性（是否为相对路径）
	 */
	public final static String PROPERTIES_CONFIGPATH_FILENAME_NODE_PATH_PROPERTY_RELATIVIZE="relativize";
	/**
	 * 存放系统配置文件路径的节点-属性（是否为相对路径默认值）
	 */
	public final static String PROPERTIES_CONFIGPATH_FILENAME_NODE_PATH_PROPERTY_RELATIVIZE_DEFAULT_VALUE="false";
	/**
	 * 存放系统配置文件路径的节点-属性（是否为相对路径-true）
	 */
	public final static String PROPERTIES_CONFIGPATH_FILENAME_NODE_PATH_PROPERTY_RELATIVIZE_VALUE_TRUE="true";
	/**
	 * 存放系统配置文件名的节点
	 */
	public final static String PROPERTIES_CONFIGPATH_FILENAME_NODE_FILENAME="filename";
	/////////////////////////////////////////////
	// systemconfig.xml配置文件节点  BEGIN        //
	/////////////////////////////////////////////

	/**
	 * 系统配置文件名称
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE = "snoics-systemconfig.xml";

	/**
	 * 系统初始化参数节点
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_INIT="init";
	/**
	 * 系统初始化时调用的其他类
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_INIT_NODE_INITCLASS="initClass";
	
	/**
	 * 系统摧毁参数节点
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_DESTROY="destroy";
	
	/**
	 * 是否自动摧毁系统
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_INIT_NODE_AUTODESTROY="autodestroy";
	
	/**
	 * 是否自动摧毁系统-打开
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_INIT_NODE_AUTODESTROY_ON="on";
	
	/**
	 * 是否自动摧毁系统-关闭
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_INIT_NODE_AUTODESTROY_OFF="off";
	
	/**
	 * 系统摧毁时调用的其他类
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_INIT_NODE_DESTROYCLASS="destroyClass";

	/**
	 * 系统配置文件节点-配置文件名
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_CONFIGFILE_NODE_FILENAME = "FileName";

	/**
	 * 系统配置文件节点-Log4j配置文件名称
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_LOG4JCONFIG = "Log4jConfig";
	
	/**
	 * 其他配置文件节点
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_CONFIGFILES="configFiles";
	
	/**
	 * 其他配置文件节点
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_CONFIGFILES_CONFIGFILE="configFile";
	
	/**
	 * 其他配置文件节点
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_CONFIGFILES_CONFIGFILE_NAME="name";
	
	/**
	 * 其他配置文件节点
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_CONFIGFILES_CONFIGFILE_FILENAME="filename";
	
	/**
	 *  系统配置文件节点-系统日志日志
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG="SystemLog";

	/**
	 *  系统配置文件节点-系统日志日志
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_OVERRIDESYSTEMOUT="overrideSystemOut";

	/**
	 * 系统配置文件节点-使用日志类型
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE = "SystemLogType";
	
	/**
	 * 系统配置文件节点-使用日志类型-是否初始化LOG4J
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_PROPERTY_INITLOG4J="initLog4j";
	
	/**
	 * 系统配置文件节点-使用日志类型-是否初始化LOG4J-FALSE
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_PROPERTY_INITLOG4J_VALUE_FALSE="false";
	
	/**
	 * 系统配置文件节点-使用日志类型-是否初始化LOG4J-TRUE
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_PROPERTY_INITLOG4J_VALUE_TRUE="true";
	
	/**
	 * 系统配置文件节点-使用日志类型-是否初始化LOG4J-DEFAULT
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_PROPERTY_INITLOG4J_VALUE_DEFAULT=SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_PROPERTY_INITLOG4J_VALUE_TRUE;
	
	/**
	 * 系统配置文件节点-使用系统自带的日志
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_NORMAL = "NORMAL";

	/**
	 * 系统配置文件节点-使用log4j作为系统日志
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_LOG4J = "LOG4J";

	/**
	 * 系统配置文件节点-捕获控制台的所有的输出
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_OVERRIDESYSTEMOUT = "OVERRIDESYSTEMOUT";
	
	/**
	 * 系统配置文件节点-普通日志的配置
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_NORMALLOG="NormalLog";
	
	/**
	 * 系统配置文件节点-当使用系统自带的日志时，生成的日志文件
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_NORMALLOG_LOGFILE = "LogFile";

	/**
	 * 系统配置文件节点-当使用系统自带的日志时，日志的打印形式
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_NORMALLOG_PRINTTYPE = "PrintType";
	
	/**
	 * 系统配置文件节点-当使用系统自带的日志时，是否打印NORMAL日志时间
	 */
	public final static String SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_NORMALLOG_SHOWTIME = "ShowTime";
	
	/////////////////////////////////////////////
	// systemconfig.xml配置文件节点  END          //
	/////////////////////////////////////////////  

	
	//////////////////////////////////////////////
	//  系统公用对象名称     BEGIN                 //
	/////////////////////////////////////////////

	/**
	 * 对象池中的对象-日志对象工厂
	 */
	public final static String SYSTEM_SYSTEMPOOL_COMMONOBJECT_LOGFACTORY = "SNOICS_SYSTEM_COMMONOBJECT_LogFactory";

    /**
     * 对象池中的对象-系统初始化信息
     */
    public final static String SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMCONFIGINFO="SNOICS_SYSTEM_COMMONOBJECT_SystemConfigInfo";

    /**
     * 对象池中的对象-系统参数配置信息
     */
    public final static String SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMPARAMETERS="SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMPARAMETERS_PARAMETERSINFO";

	
	/**
	 * 系统其他参数配置
	 */
	public final static String SYSTEM_PARAMETERS="parameters";
	
	public final static String SYSTEM_PARAMETERS_PARAMETER="parameter";
	
	public final static String SYSTEM_PARAMETERS_PARAMETER_NAME="name";
	
	public final static String SYSTEM_PARAMETERS_PARAMETER_VALUE="value";
	
	public final static String SYSTEM_PARAMETERS_SNOICS_CONFIG_HOME="system.parameters.snoics.config.home";
	
	public final static String SYSTEM_PARAMETERS_SNOICS_CONFIG_LOG_FILE_NAME="system.parameters.snoics.config.log.file.name";
	
	public final static String SYSTEM_PARAMETERS_SNOICS_CONFIG_LOG_LEVEL="system.parameters.snoics.config.log.level";
	
	
	//////////////////////////////////////////////
	//  系统公用对象名称     END                   //
	/////////////////////////////////////////////
}
