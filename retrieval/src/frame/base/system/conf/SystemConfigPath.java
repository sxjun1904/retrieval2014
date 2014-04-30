package frame.base.system.conf;

import java.io.InputStream;
import java.net.URLEncoder;

import frame.base.core.exception.SnoicsRuntimeException;
import frame.base.core.interfaces.log.Log;
import frame.base.core.log.LogFactory;
import frame.base.core.net.UrlTool;
import frame.base.core.util.StringClass;
import frame.base.core.util.file.FileHelper;
import frame.base.core.xml.ReadXml;
import frame.base.system.common.SystemCommonUtil;
/**
 * 系统配置文件路径
 * @author 
 *
 */
public class SystemConfigPath {
    private static SystemConfigPath instance = new SystemConfigPath(); // 唯一实例
    private ReadXml readXml=new ReadXml();
    private String configpath="";
    private String configPathFileName=null;
    private SystemInitConfig systemInitConfig=null;
    private InputStream configPathInputStream=null;
    private String systemFilename="";
    private String spaceChar=" ";
    private FileHelper snoicsFile=new FileHelper();
    private Log log=LogFactory.getLogFactory().builderLog();
    
    private SystemConfigPath(){
    	
    }
    
    /**
     * 生成唯一实例
     */
    public static SystemConfigPath getInstance() {
        return instance;
    }
    
    /**
     * 设置系统配置文件路径
     * @param configpath 路径
     */
    public void setConfigpath(String configpath){
    	if(configpath!=null){
        	configpath=StringClass.getFormatPath(configpath);
        	this.configpath=configpath;
    	}
    }
    
    /**
     * 取得系统配置文件路径
     * @return String
     */
    public String getConfigpath(){
    	return configpath;
    }
    
    /**
     * 获取系统路径配置文件名
     * @return String
     */
	public String getConfigPathFileName() {
		return configPathFileName;
	}
	
	/**
	 * 设置系统路径配置文件名
	 * @param configPathFileName
	 */
	public void setConfigPathFileName(String configPathFileName) {
		this.configPathFileName = configPathFileName;
	}
	
	/**
	 * 设置系统路径配置文件名
	 * @param configPathFileName
	 */
	public void setConfigPathFileName(InputStream configPathInputStream) {
		this.configPathInputStream = configPathInputStream;
	}
	
	/**
	 * 获取系统配置文件名
	 * @return Returns the systemFilename.
	 */
	public String getSystemFilename() {
		return systemFilename;
	}
	/**
	 * 设置系统配置文件名
	 * @param systemFilename The systemFilename to set.
	 */
	public void setSystemFilename(String systemFilename) {
		this.systemFilename = systemFilename;
	}

	public InputStream getConfigPathInputStream() {
		return configPathInputStream;
	}

	public void setConfigPathInputStream(InputStream configPathInputStream) {
		this.configPathInputStream = configPathInputStream;
	}
    
    public SystemInitConfig getSystemInitConfig() {
		return systemInitConfig;
	}

	public void setSystemInitConfig(SystemInitConfig systemInitConfig) {
		this.systemInitConfig = systemInitConfig;
	}

	/**
     * 初始化系统配置文件路径
     *
     */
    public void init() throws SnoicsRuntimeException{
    	log.getLogger(this.getClass());
    	
//		String classPath=StringClass.getFormatPath(StringClass.getRealPath(this.getClass(),"/",null));
		String classPath=StringClass.getFormatPath(StringClass.getRealPath(this.getClass()));
    	log.debug("Class Path:"+classPath);
    	
    	configpath=StringClass.getString(configpath);
    	systemFilename=StringClass.getString(systemFilename);
    	if(!configpath.equals("") && !systemFilename.equals("")){
    		return;
    	}

    	try{
    		spaceChar="\\"+URLEncoder.encode(spaceChar,"utf-8");
        	log.debug("ConfigHome Path spaceChar :"+spaceChar);
    	}catch(Exception e){
    		e.printStackTrace() ;
    	}
    	
		if(systemInitConfig!=null){
			readConfigpath(systemInitConfig);
		}else if(configPathInputStream!=null){
        	readConfigpath(configPathInputStream);
    	}else{
        	InputStream inputStream=null;
        	String theConfigPathFileName="";
        	
        	if((configPathFileName!=null)&&(!configPathFileName.equals(""))){
        		theConfigPathFileName=configPathFileName;
        	}else{
        		theConfigPathFileName=SystemCommonUtil.PROPERTIES_CONFIGPATH_FILENAME;
        	}
    		log.debug("读取系统路径配置文件信息:"+theConfigPathFileName);

    		String realPath=null;
    		
        	if(snoicsFile.isFile(theConfigPathFileName)){
        		realPath=StringClass.getFormatPath(theConfigPathFileName);
            	try{
            		inputStream=snoicsFile.fileToInputStream(theConfigPathFileName);
            	}catch(Exception e){
            		throw new SnoicsRuntimeException("找不到系统路径配置文件 '"+ realPath +"' ，系统处于未初始化状态",e);
//            		e.printStackTrace();
            	}
        	}else{
        		realPath=StringClass.getFormatPath(StringClass.getRealPath(this.getClass())+"/"+theConfigPathFileName);
//        		realPath=StringClass.getFormatPath(StringClass.getRealPath(this.getClass(),"/",null)+"/"+theConfigPathFileName);
            	try{
            		inputStream=this.getClass().getResourceAsStream("/"+theConfigPathFileName);
            	}catch(Exception e){
            		throw new SnoicsRuntimeException("找不到系统路径配置文件 '"+ theConfigPathFileName +"' ，系统处于未初始化状态",e);
            	}
        	}
        	
        	if(inputStream==null){
//        		throw new SnoicsRuntimeException(" 找不到系统路径配置文件 '"+ realPath +"' ，系统处于未初始化状态",e);
        		log.error("找不到系统路径配置文件 '"+ theConfigPathFileName +"' ，系统处于未初始化状态 ");
        		return;
        	}else{
//        		log.info("读取系统路径配置文件 '"+ theConfigPathFileName +"'");
            	readConfigpath(inputStream);
        	}
    	}
    }
    
    /**
     * 初始化系统配置文件路径
     * @param propertiesFilename
     */
    public void init(String propertiesFilename){
    	String configpath=snoicsFile.fileToString(propertiesFilename).trim();
        setConfigpath(configpath);
    }
    
    /**
     * 读取配置文件中的 系统配置文件保存路径
     * @param configpathfile
     * @return String
     */ 
    private void readConfigpath(Object configpathfile) {
    	String configpath="";
    	String systemFilename="";
    	boolean relativize=false;
    	if(configpathfile instanceof SystemInitConfig){
    		SystemInitConfig theSystemInitConfig=(SystemInitConfig)systemInitConfig;
    		configpath=theSystemInitConfig.getConfigpath();
    		systemFilename=theSystemInitConfig.getSystemFilename();
    		relativize=theSystemInitConfig.isRelativize();
    	}else{
        	readXml.reset();
        	readXml.setXmlfile(configpathfile);
        	readXml.parseXMLFile();
            try{
            	configpath=(String)readXml.getNodeValue(SystemCommonUtil.PROPERTIES_CONFIGPATH_FILENAME_NODE_PATH).get(0);
            }catch(Exception e){
            	configpath="";
            }
            
            try{
            	systemFilename=(String)readXml.getNodeValue(SystemCommonUtil.PROPERTIES_CONFIGPATH_FILENAME_NODE_FILENAME).get(0);
            }catch(Exception e){
            	systemFilename=SystemCommonUtil.SYSTEM_SYSTEMCONFIGFILE;
            }
            
            readXml.setCurrentNodeList(SystemCommonUtil.PROPERTIES_CONFIGPATH_FILENAME_NODE_PATH);
            readXml.setCurrentNode(0);

        	String isRelativize="false";
            isRelativize=((String)readXml.getAttribute(SystemCommonUtil.PROPERTIES_CONFIGPATH_FILENAME_NODE_PATH_PROPERTY_RELATIVIZE)).toLowerCase();
            isRelativize=StringClass.getString(isRelativize);
            if(isRelativize.equals("")){
                isRelativize=SystemCommonUtil.PROPERTIES_CONFIGPATH_FILENAME_NODE_PATH_PROPERTY_RELATIVIZE_DEFAULT_VALUE;
            }
            relativize=Boolean.parseBoolean(isRelativize);
    	}
        if(relativize){
//        	String basePath=StringClass.getRealPath(this.getClass(),"",null);
        	String basePath=StringClass.getRealPath(this.getClass());
        	UrlTool snoicsUrl=new UrlTool();
        	basePath=StringClass.getFormatPath(basePath);
        	configpath=StringClass.getFormatPath(configpath);
        	basePath=StringClass.getReplaceString(basePath," ",spaceChar);
        	configpath=StringClass.getReplaceString(configpath," ",spaceChar);
        	configpath=StringClass.getFormatPath((snoicsUrl.getResolved(basePath,configpath)).toString()+"/");
        	configpath=StringClass.getReplaceString(configpath,spaceChar," ");
        }
        
        this.configpath=configpath;
        
        System.setProperty(SystemCommonUtil.SYSTEM_PARAMETERS_SNOICS_CONFIG_HOME, configpath);
        
        this.systemFilename=systemFilename;

    	log.info("ConfigHome : "+configpath);
    	log.info("systemFilename : "+systemFilename);
    	
    }
}
