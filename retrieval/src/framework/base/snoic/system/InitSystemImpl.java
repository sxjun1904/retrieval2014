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
package framework.base.snoic.system;

import java.util.List;
import java.util.Map;

import framework.base.snoic.base.interfaces.log.Log;
import framework.base.snoic.base.log.LogFactory;
import framework.base.snoic.base.log.SystemOut;
import framework.base.snoic.base.pool.ObjectManager;
import framework.base.snoic.base.util.StringClass;
import framework.base.snoic.system.common.SystemCommonUtil;
import framework.base.snoic.system.conf.ParametersInfo;
import framework.base.snoic.system.conf.SystemConfig;
import framework.base.snoic.system.conf.SystemConfigFileName;
import framework.base.snoic.system.conf.SystemConfigInfo;
import framework.base.snoic.system.conf.SystemConfigLogInfo;
import framework.base.snoic.system.exception.SnoicsSystemException;
import framework.base.snoic.system.interfaces.DestroySystem;
import framework.base.snoic.system.interfaces.InitSystem;
/**
 * 系统初始化
 * @author 
 *
 */
public class InitSystemImpl implements InitSystem{
	private static boolean initFlag=false;
	private Log log=null;

    private SystemConfigInfo systemConfigInfo=new SystemConfigInfo();
    private SystemConfig systemConfig = null;
    private ObjectManager objectManager = null;
    private SystemConfigFileName systemConfigFileName = null;
	
    public InitSystemImpl() {
    	
    }

    private void init() {
        //创建系统对象池
    	objectManager.createPool(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME);
    }

	/**
     * 初始化系统配置
     */
    public synchronized void initialize(){
    	
    	systemConfig = SystemConfig.newInstance();
    	objectManager = ObjectManager.getInstance();
    	systemConfigFileName = new SystemConfigFileName();
    	
    	if(initFlag){
    		return;
    	}

    	log=LogFactory.getLogFactory().builderLog();
    	
        log.getLogger(InitSystemImpl.class);
        
//    	log.debug("-----开始初始化系统-----");
    	
    	init();
    	
    	systemConfigFileName.initConfigPath();
    	
    	String configPath=StringClass.getString(systemConfigFileName.getConfigPath());

    	configPath=StringClass.getFormatPath(configPath);
		
    	log.debug("snoics-configpath="+configPath);

    	
    	//如果找不到系统配置文件
    	if(configPath.equals("")) {
    		return;
    	}
    	
    	//日志对象必须最早被初始化
    	try {
    		initLog();
    	}catch(SnoicsSystemException e) {
    		e.printStackTrace();
    	}
    	
    	
    	try{
    		initParameters();
    	}catch(SnoicsSystemException e) {
    		log.error(e);
    	}
    	
    	try{
    		initConfigFiles();
    	}catch(SnoicsSystemException e) {
    		log.error(e);
    	}

    	try {
    		initInitClass();
    	}catch(SnoicsSystemException e) {
    		log.error(e);
    	}
    	try {
    		initDestroyClass();
    	}catch(SnoicsSystemException e) {
    		log.error(e);
    	}
    	
    	objectManager.checkInObject(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME,SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMCONFIGINFO, systemConfigInfo);
//        log.debug("-----系统初始化完成-----");
        
        initFlag=true;
    }
    
    /**
     * 初始化系统日志
     *
     */
    private void initLog() throws SnoicsSystemException{
        String systemlogtype="";
        String logfile = "";
        String initLog4j="";
        String printType = "";
        String showtime="";
    	LogFactory logFactory=LogFactory.getLogFactory();
    	SystemConfigLogInfo logInfo=null;
        
        //初始化日志
    	logInfo = systemConfig.getSystemLogProperties();
        
        systemlogtype=logInfo.getSystemLogType();

        if(systemlogtype.equalsIgnoreCase(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_SYSTEMLOGTYPE_NORMAL)){
        	logfile=logInfo.getLogFile();
        	printType=logInfo.getPrintType();
        	showtime=logInfo.getShowTime();
            logFactory.builderFactory(logfile,printType,showtime);
        }else if(systemlogtype.equalsIgnoreCase(SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE_NODE_SYSTEMLOG_OVERRIDESYSTEMOUT)) {
        	logfile=logInfo.getLogFile();
        	printType=logInfo.getPrintType();
        	showtime=logInfo.getShowTime();
            logfile = StringClass.getFormatPath(logfile);
            SystemOut systemOut=new SystemOut();
            systemOut.setFilename(logfile);
            systemOut.setAppendFlag(true);
            systemOut.changeOut();
            logFactory.builderFactorySystemOut(logfile,printType,showtime);
        }else{
            //初始化log4j
            String log4jConfigFile=systemConfigFileName.getLog4jConfigfile();
        	initLog4j=logInfo.getInitLog4j();
            logFactory.builderFactory(log4jConfigFile,initLog4j);
        }
        objectManager.checkInObject(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME,SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECT_LOGFACTORY, logFactory);
        log=logFactory.builderLog();
        log.getLogger(InitSystemImpl.class);
//        log.debug("系统日志初始化完成");
        log.debug("LogType : "+systemlogtype);
        if(!logfile.equals("")){
        	log.debug("LogFile : " + logfile);
        }
        
        systemConfigInfo.setSystemlogtype(systemlogtype);
        systemConfigInfo.setLogfile(logfile);
        systemConfigInfo.setLogtype(printType);
    }
    
    /**
     * 进行外部初始化操作
     *
     */
    private void initInitClass() throws SnoicsSystemException {
        //进行initClass的初始化操作
    	List initClassString=systemConfig.getInitClass();
        InitSystem[] initClass=null;
        //SnoicsClass snoicsClass=new SnoicsClass();
        //Log log=snoicsClass.getLog();
        //log.getLogger("snoics.system.initialize");
        
        if(initClassString!=null){
//            log.debug("+------------------------+");
//            log.debug("|      initClass ...     |");
//            log.debug("+------------------------+");
        	int length=initClassString.size();
        	initClass=new InitSystem[length];
        	for(int i=0;i<length;i++) {
        		String theInitClass=(String)initClassString.get(i);
        		if((theInitClass!=null)&&(!theInitClass.equals(""))) {
            		try {
            			InitSystem initClassInitSystem = (InitSystem)Class.forName(theInitClass).newInstance();
            			log.debug("开始执行 "+theInitClass+" 中的初始化操作");
            			initClassInitSystem.initialize();
            			initClass[i]=initClassInitSystem;
            		} catch (Exception e) {
            			log.debug("无法实例化："+theInitClass,e);
            		}
        		}
        	}
//            log.debug("+------------------------+");
//            log.debug("|    initClass success   |");
//            log.debug("+------------------------+");
        }
        systemConfigInfo.setInitClass(initClass);

    	objectManager.checkInObject(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME,SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMCONFIGINFO, systemConfigInfo);
    }
    
    /**
     * 初始化外部配置文件
     * @throws SnoicsSystemException
     */
    private void initConfigFiles() throws SnoicsSystemException{
//        log.debug("+------------------------------+");
//        log.debug("|      initConfigFiles ...     |");
//        log.debug("+------------------------------+");
    	Map hashMap=systemConfig.getConfigFiles();
//        log.debug("+------------------------------+");
//        log.debug("|    initConfigFiles success   |");
//        log.debug("+------------------------------+");
    	systemConfigInfo.setConfigFilesMap(hashMap);
    	objectManager.checkInObject(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME,SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMCONFIGINFO, systemConfigInfo);
    }
    
    /**
     * 初始化外部参数配置
     * @throws SnoicsSystemException
     */
    private void initParameters() throws SnoicsSystemException{
//        log.debug("+-----------------------------+");
//        log.debug("|      initParameters ...     |");
//        log.debug("+-----------------------------+");
    	ParametersInfo parametersInfo=systemConfig.getParametersInfo();
//        log.debug("+-----------------------------+");
//        log.debug("|    initParameters success   |");
//        log.debug("+-----------------------------+");
    	systemConfigInfo.setParametersInfo(parametersInfo);
    	objectManager.checkInObject(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME,SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMCONFIGINFO, systemConfigInfo);
    }
    
    /**
     * 进行外部destroy操作
     * @throws SnoicsSystemException
     */
    private void initDestroyClass() throws SnoicsSystemException {
        //进行DestroyClass的初始化操作
    	String autodestroy=systemConfig.getAutodestroy();
    	if(autodestroy.equals("")){
    		autodestroy="off";
    	}
    	List destroyClassString=systemConfig.getDestroyClass();
        DestroySystem[] destroyClass=null;
        
        if(destroyClassString!=null){
//            log.debug("+------------------------+");
//            log.debug("|    destroyClass ...    |");
//            log.debug("+------------------------+");
            log.debug("AutoDestroy="+autodestroy);
        	int length=destroyClassString.size();
        	destroyClass=new DestroySystem[length];
        	for(int i=0;i<length;i++) {
        		String theDestroyClass=(String)destroyClassString.get(i);
        		if((theDestroyClass!=null)&&(!theDestroyClass.equals(""))) {
            		try {
            			DestroySystem destroySystem=(DestroySystem)Class.forName(theDestroyClass).newInstance();
            			destroyClass[i]=destroySystem;
            			log.debug("DestroyClass:  "+theDestroyClass);
            		} catch (Exception e) {
            			log.error("无法实例化："+theDestroyClass,e);
            		}
        		}
        	}
//            log.debug("+------------------------+");
//            log.debug("|  destroyClass success  |");
//            log.debug("+------------------------+");
        }
        systemConfigInfo.setAutodestroy(autodestroy);
        systemConfigInfo.setDestroySystem(destroyClass);

    	objectManager.checkInObject(SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECTPOOLNAME,SystemCommonUtil.SYSTEM_SYSTEMPOOL_COMMONOBJECT_SYSTEMCONFIGINFO, systemConfigInfo);
    }
    
    public static void main(String[] args){
    	InitSystemImpl initSystemImpl=new InitSystemImpl();
    	initSystemImpl.initialize();
    }
}