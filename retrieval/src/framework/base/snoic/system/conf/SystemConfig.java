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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import framework.base.snoic.base.util.StringClass;
import framework.base.snoic.base.xml.ReadXml;
import framework.base.snoic.system.common.SystemCommonUtil;
/**
 * 读取系统配置文件
 * @author 
 *
 */
public class SystemConfig {
    
    private static SystemConfig instance = new SystemConfig();

    private Object systemConfigFileObject=null;
    
    private ReadXml readXml = new ReadXml();
    
    private SystemConfigPath systemConfigPath=SystemConfigPath.getInstance();
    /**
     * 生成一个配置对象实例
     */
    public static SystemConfig newInstance() {
        return instance;
    }

    private SystemConfig() {
    	
    }
    
    /**
     * 读入配置文件
     * @param systemConfigFileObject 系统配置文件对象
     */
    public void setSystemConfigFile(Object systemConfigFileObject) {
    	readXml.setXmlfile(systemConfigFileObject);
    	readXml.parseXMLFile();
    }
    
    /**
     * 取得系统配置文件对象
     */
    public Object getSystemConfigFile() {
        return systemConfigFileObject;
    }

    /**
     * 取得系统初始化时调用的其他类
     * @return List
     */
    public List getInitClass(){
    	readXml.reset();
    	boolean flag=readXml.setCurrentNodeList(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_INIT);
    	if(!flag) {
    		return null;
    	}
    	if(readXml.getCurrentNodeListLength()<=0){
    		return null;
    	}
    	readXml.setCurrentNode(0);
    	List initClass = null;
    	try{
    		initClass=readXml.getNodeValue(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_INIT_NODE_INITCLASS);
    	}catch(Exception e){
    		
    	}
        return initClass;
    }
    
    /**
     * 获取是否自动摧毁系统
     * @return String
     */
    public String getAutodestroy(){
    	String autodestroy="";
    	readXml.reset();
    	boolean flag=readXml.setCurrentNodeList(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_DESTROY);
    	if(!flag) {
    		return "";
    	}
    	if(readXml.getCurrentNodeListLength()<=0){
    		return "";
    	}
    	readXml.setCurrentNode(0);
    	List autodestroyArrayList = null;
    	try{
    		autodestroyArrayList=readXml.getNodeValue(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_INIT_NODE_AUTODESTROY);
    	}catch(Exception e){
    		
    	}
    	try{
    		autodestroy=(String)autodestroyArrayList.get(0);
    	}catch(Exception e){
    		
    	}
    	return autodestroy;
    }
    
    /**
     * 取得系统摧毁时调用的其他类
     * @return List
     */
    public List getDestroyClass(){
    	readXml.reset();
    	boolean flag=readXml.setCurrentNodeList(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_DESTROY);
    	
    	if(!flag) {
    		return null;
    	}
    	if(readXml.getCurrentNodeListLength()<=0){
    		return null;
    	}
    	readXml.setCurrentNode(0);
    	List destroyClass = null;
    	try{
    		destroyClass=readXml.getNodeValue(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_INIT_NODE_DESTROYCLASS);
    	}catch(Exception e){
    		
    	}
        return destroyClass;
    }
    
    /**
     * 取得系统日志配置
     * 
     * @return HashMap
     */
    public SystemConfigLogInfo getSystemLogProperties() {
    	SystemConfigLogInfo logInfo=new SystemConfigLogInfo();
    	readXml.reset();
    	boolean flag=false;
    	try{
    		flag=readXml.setCurrentNodeList(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG);
    	}catch(Exception e){
    		
    	}
    	if(!flag) {
    		return null;
    	}
    	if(readXml.getCurrentNodeListLength()<=0){
    		return null;
    	}
    	readXml.setCurrentNode(0);
    	
        String systemLogType="";
        
        List value=null;
        try{
        	value=readXml.getNodeValue(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE);
        }catch(Exception e){
        	
        }
        
    	if((value!=null)&&(!value.isEmpty())){
    		systemLogType=(String)value.get(0);
    	}else {
    		systemLogType=SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_LOG4J;
    	}
    	
    	logInfo.setSystemLogType(systemLogType);
    	
    	Node theNode=readXml.getCurrentNode();
    	
    	readXml.setCurrentNodeList(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE);
    	if(readXml.getCurrentNodeListLength()>0){
    		readXml.setCurrentNode(0);
            String initLog4j="";
            initLog4j=readXml.getAttribute(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_PROPERTY_INITLOG4J);
            initLog4j=StringClass.getString(initLog4j).toLowerCase();
            if(initLog4j.equals("")){
            	initLog4j=SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_PROPERTY_INITLOG4J_VALUE_DEFAULT;
            }
        	logInfo.setInitLog4j(initLog4j);
    	}
    	
    	readXml.setCurrentNode(theNode);


    	readXml.setCurrentNodeList(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_NORMALLOG);
    	readXml.setCurrentNode(0);
    	
    	String logFile ="";
        
    	List valueLogFile=null;
        try{
        	valueLogFile=readXml.getNodeValue(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_NORMALLOG_LOGFILE);
        }catch(Exception e){
        	
        }
        
    	if((valueLogFile!=null)&&(!valueLogFile.isEmpty())){
    		logFile=(String)valueLogFile.get(0);
    	}
    	logInfo.setLogFile(logFile);
    	
    	System.setProperty(SystemCommonUtil.SYSTEM_PARAMETERS_SNOICS_CONFIG_LOG_FILE_NAME, logFile);
    	
        String printType ="";   
        List valueLogType=null;
        try{
        	valueLogType=readXml.getNodeValue(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_NORMALLOG_PRINTTYPE);
        }catch(Exception e){
        	
        }
    	if((valueLogType!=null)&&(!valueLogType.isEmpty())){
    		printType=(String)valueLogType.get(0);
    	}
    	logInfo.setPrintType(printType);
    	
    	setLogLevel(printType);
    	
        String showTime ="";   
        List valueShowTime=null;
        try{
        	valueShowTime=readXml.getNodeValue(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_NORMALLOG_SHOWTIME);
        }catch(Exception e){
        	
        }
    	if((valueShowTime!=null)&&(!valueShowTime.isEmpty())){
    		showTime=(String)valueShowTime.get(0);
    	}else {
    		showTime="ON";
    	}
    	logInfo.setShowTime(showTime);

        return logInfo;
    }
    
    /**
     * 取得配置信息
     * @return String 
     */
    public String getConfigFile(String configFileNameNode){
        String configFile="";
    	readXml.reset();
		boolean flag=readXml.setCurrentNodeList(configFileNameNode);
    	if(!flag) {
    		return "";
    	}
    	if(readXml.getCurrentNodeListLength()<=0){
    		return "";
    	}
    	readXml.setCurrentNode(0);
    	
    	List value=null;
    	try{
    		value=readXml.getNodeValue(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_CONFIGFILE_NODE_FILENAME);
    	}catch(Exception e){
    		
    	}
    	if((value!=null)&&(!value.isEmpty())){
    		configFile=(String)value.get(0);
    	}
    	
        return configFile;
    }
    
    /**
     * 获取系统外部配置文件
     * 多个文件用;隔开
     * @return HashMap
     */
    public Map getConfigFiles(){
    	Map hashMap=new HashMap();
    	readXml.reset();
    	readXml.setCurrentNodeList(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_CONFIGFILES);
    	int length=readXml.getCurrentNodeListLength();
    	if(length>0){
    		readXml.setCurrentNode(0);
        	readXml.setCurrentNodeList(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_CONFIGFILES_CONFIGFILE);
			int configFilesLength=readXml.getCurrentNodeListLength();
    		for(int i=0;i<configFilesLength;i++){
    			readXml.setCurrentNode(i);
    			String name="";
    			String filename="";
    			String newFilename="";
    			try {
    				name=(String)readXml.getNodeValue(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_CONFIGFILES_CONFIGFILE_NAME).get(0);
    			}catch(Exception e) {
    				
    			}
    			try {
    				filename=(String)readXml.getNodeValue(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_CONFIGFILES_CONFIGFILE_FILENAME).get(0);
    				filename=StringClass.getString(filename);
    				
    				if((filename.indexOf(";"))>-1){
    					String[] fileNames=filename.split(";");
    					int fileNamesLength=fileNames.length;
    					for(int k=0;k<fileNamesLength;k++){
    						String theFileName=fileNames[k];
            				if(!theFileName.startsWith("file:")){
                				String configPath=systemConfigPath.getConfigpath();
                				theFileName=StringClass.getFormatPath(configPath+"/"+theFileName);
            				}else{
            					theFileName=theFileName.replaceFirst("file:", "");
            				}
            				newFilename+=theFileName+";";
    					}
    				}else{
        				if(!filename.startsWith("file:")){
            				String configPath=systemConfigPath.getConfigpath();
            				filename=StringClass.getFormatPath(configPath+"/"+filename);
        				}else{
        					filename=filename.replaceFirst("file:", "");
        				}
        				newFilename=filename;
    				}
    			}catch(Exception e) {
    				
    			}
				
    			if(name!=null){
    				hashMap.put(name,newFilename);
    			}
    		}
    	}
    	return hashMap;
    }
    
	
	/**
	 * 获取系统参数配置
	 * @return ParametersInfo
	 */
	public ParametersInfo getParametersInfo(){
		ParametersInfo parametersInfo=new ParametersInfo();
		readXml.reset();
		readXml.setCurrentNodeList(SystemCommonUtil.SYSTEM_PARAMETERS);
		int length=readXml.getCurrentNodeListLength();
		if(length<=0){
			return parametersInfo;
		}else{
			readXml.setCurrentNode(0);
			readXml.setCurrentNodeList(SystemCommonUtil.SYSTEM_PARAMETERS_PARAMETER);
			int parLength=readXml.getCurrentNodeListLength();
			if(parLength<=0){
				return parametersInfo;
			}else{
				for(int i=0;i<parLength;i++){
					readXml.setCurrentNode(i);
					String name=null;
					String value=null;
					try {
						name=(String)readXml.getNodeValue(SystemCommonUtil.SYSTEM_PARAMETERS_PARAMETER_NAME).get(0);
					}catch(Exception e) {
						
					}
					try {
						value=(String)readXml.getNodeValue(SystemCommonUtil.SYSTEM_PARAMETERS_PARAMETER_VALUE).get(0);
					}catch(Exception e) {
						
					}
					parametersInfo.setParameter(name,value);
				}
			}
		}
		return parametersInfo;
	}
	
	private void setLogLevel(String level){
		level=StringClass.getString(level);
		
		if(level.equalsIgnoreCase("0")){
			System.setProperty(SystemCommonUtil.SYSTEM_PARAMETERS_SNOICS_CONFIG_LOG_LEVEL, "OFF");
		}
		if(level.equalsIgnoreCase("1")){
			System.setProperty(SystemCommonUtil.SYSTEM_PARAMETERS_SNOICS_CONFIG_LOG_LEVEL, "FATAL");
		}
		if(level.equalsIgnoreCase("2")){
			System.setProperty(SystemCommonUtil.SYSTEM_PARAMETERS_SNOICS_CONFIG_LOG_LEVEL, "ERROR");
		}
		if(level.equalsIgnoreCase("3")){
			System.setProperty(SystemCommonUtil.SYSTEM_PARAMETERS_SNOICS_CONFIG_LOG_LEVEL, "WARN");
		}
		if(level.equalsIgnoreCase("4")){
			System.setProperty(SystemCommonUtil.SYSTEM_PARAMETERS_SNOICS_CONFIG_LOG_LEVEL, "INFO");
		}
		if(level.equalsIgnoreCase("5")){
			System.setProperty(SystemCommonUtil.SYSTEM_PARAMETERS_SNOICS_CONFIG_LOG_LEVEL, "DEBUG");
		}
		if(level.equalsIgnoreCase("6")){
			System.setProperty(SystemCommonUtil.SYSTEM_PARAMETERS_SNOICS_CONFIG_LOG_LEVEL, "ALL");
		}
		
	}
}