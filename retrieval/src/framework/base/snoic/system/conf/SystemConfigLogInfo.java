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
package framework.base.snoic.system.conf;

import framework.base.snoic.base.BuildSnoicsClassFactory;
import framework.base.snoic.base.ISnoicsClass;
import framework.base.snoic.base.util.StringClass;
import framework.base.snoic.base.util.file.FileHelper;
import framework.base.snoic.system.common.SystemCommonUtil;


/**
 * 普通日志信息
 * @author 
 *
 */
public class SystemConfigLogInfo {
	private String systemLogType="";
	private String initLog4j=SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_PROPERTY_INITLOG4J_VALUE_DEFAULT;
	private String logFile="";
	private String printType="";
	private String showTime="";
	private String log4jConfigfile="";
	private FileHelper snoicsFileHelper=new FileHelper();
	private ISnoicsClass snoicsClass=BuildSnoicsClassFactory.createSnoicsClass();

    /**
     * 获取LOG4J配置文件
	 * @return Returns the log4jConfigfile.
	 */
	public String getLog4jConfigfile() {
		return log4jConfigfile;
	}

	/**
	 * 设置LOG4J配置文件
	 * @param log4jConfigfile The log4jConfigfile to set.
	 */
	public void setLog4jConfigfile(String log4jConfigfile) {
		this.log4jConfigfile = log4jConfigfile;
	}
	/**
	 * 获取日志文件
	 * @return Returns the logFile.
	 */
	public String getLogFile() {
		return logFile;
	}
	/**
	 * 设置日志文件
	 * @param logFile The logFile to set.
	 */
	public void setLogFile(String logFile) {
		logFile=StringClass.getString(logFile);
		logFile=StringClass.getFormatPath(snoicsClass.getConfigHome()+"/"+logFile);
		
		if(!logFile.equals("")){
			if(!snoicsFileHelper.isFile(logFile)){
				boolean flag=snoicsFileHelper.createFile(logFile);
//				snoicsFileHelper.deleteFile(logFile);
//				snoicsFileHelper.delFolder(logFile);
//				if(!flag){
//					snoicsFileHelper.deleteFile(logFile);
//					snoicsFileHelper.delFolder(logFile);
//					logFile=StringClass.getFormatPath(snoicsClass.getConfigHome()+"/"+logFile);
//				}
			}
		}
		this.logFile = logFile;
	}
	/**
	 * 获取日志打印形式
	 * @return Returns the printType.
	 */
	public String getPrintType() {
		return printType;
	}
	/**
	 * 设置日志打印形式
	 * @param printType The printType to set.
	 */
	public void setPrintType(String printType) {
		this.printType = printType;
	}
	/**
	 * 获取是否显示时间
	 * @return Returns the showTime.
	 */
	public String getShowTime() {
		return showTime;
	}
	/**
	 * 设置是否显示时间
	 * @param showTime The showTime to set.
	 */
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	/**
	 * 获取日志类型
	 * @return Returns the systemLogType.
	 */
	public String getSystemLogType() {
		return systemLogType;
	}
	/**
	 * 设置日志类型
	 * @param systemLogType The systemLogType to set.
	 */
	public void setSystemLogType(String systemLogType) {
		this.systemLogType = systemLogType;
	}

	/**
	 * 获取是否初始化log4j
	 * @return String
	 */
	public String getInitLog4j() {
		return initLog4j;
	}

	/**
	 * 设置是否初始化log4j
	 * @param initLog4j
	 */
	public void setInitLog4j(String initLog4j) {
		this.initLog4j = initLog4j;
	}
	
}
