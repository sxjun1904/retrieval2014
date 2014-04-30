package frame.base.core.util.jaxb;

import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import frame.base.core.BuildSnoicsClassFactory;
import frame.base.core.interfaces.log.Log;
import frame.base.core.util.StringClass;
import frame.base.core.util.file.FileHelper;

/**
 *  解组Xml
 * @author 
 *
 */

public class XmlUnmarshal implements IXmlUnmarshal, Serializable{
	private static final long serialVersionUID = 7888058823257757418L;
	
	private String nowXmlType="";
	
	private Log log=null;
	private String packageName=null;
	
	private String xmlString=null;
	private String xmlFileName=null;
	private InputStream xmlInputStream=null;
	
	private FileHelper snoicsFileHelper=new FileHelper();
	
	private JAXBContext jaxbContext=null;

	public XmlUnmarshal(){
		log=BuildSnoicsClassFactory.createSnoicsClass().getLog();
		log.getLogger(this.getClass());
	}
	
	/**
	 * 设置DominoObject所在的包名
	 * @param packageName
	 */
	public void setPackageName(String packageName){
		this.packageName=packageName;
	}
	
	/**
	 * 获取DominoObject所在的包名
	 * @return String
	 */
	public String getPackageName(){
		return packageName;
	}
	
	public String getXmlFileName() {
		return xmlFileName;
	}

	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
		nowXmlType=XML_FILE_NAME;
	}

	public InputStream getXmlInputStream() {
		return xmlInputStream;
	}

	public void setXmlInputStream(InputStream xmlInputStream) {
		this.xmlInputStream = xmlInputStream;
		nowXmlType=XML_INPUTSTREAM;
	}
	
	public String getXmlString() {
		return xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
		nowXmlType=XML_STRING;
	}

	/**
	 * 解组xml文件，并获取java对象，每次都重新解组
	 * @return Object
	 * @throws Exception
	 */
	public Object unmarshal() throws Exception{
		Object input=init();
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    Object object = null;
	    if(input instanceof InputStream){
	    	object=unmarshaller.unmarshal((InputStream)input);
	    }else if(input instanceof Reader){
	    	object=unmarshaller.unmarshal((Reader)input);
	    }
	    try{
	    	if(input!=null){
	    		((Closeable)input).close();
	    	}
	    }catch(Exception e){
	    	log.error(e);
	    }
	    return object;
	}

	/**
	 * 解组xml文件，并获取java对象，每次都重新解组
	 * @return Object
	 * @throws Exception
	 */
	public Object unmarshal(ClassLoader classloader) throws Exception{
		Object input=init(classloader);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    Object object = null;
	    if(input instanceof InputStream){
	    	object=unmarshaller.unmarshal((InputStream)input);
	    }else if(input instanceof Reader){
	    	object=unmarshaller.unmarshal((Reader)input);
	    }
	    try{
	    	if(input!=null){
	    		((Closeable)input).close();
	    	}
	    }catch(Exception e){
	    	log.error(e);
	    }
	    return object;
	}
	
	/**
	 * 初始化
	 * @throws XmlUnmarshalException
	 */
	private Object init() throws XmlUnmarshalException{
		Object input=null;
		try {
//			jaxbContext=JAXBContext.newInstance(packageName);
			jaxbContext=JAXBContext.newInstance(packageName,this.getClass().getClassLoader());
		} catch (JAXBException e) {
	    	log.error(e);
		}
		if(nowXmlType.equals(XML_STRING)){
			xmlString=StringClass.getString(xmlString);
			Reader reader=new StringReader(xmlString);
			input=reader;
			return input;
		}else if(nowXmlType.equals(XML_FILE_NAME)){
			String xmlFile=StringClass.getFormatPath((String)xmlFileName);
			input=snoicsFileHelper.fileToInputStream(xmlFile);
	    	if(input==null){
		    	throw new XmlUnmarshalException("生成Xml文件流失败："+xmlFile);
	    	}
		}else if(nowXmlType.equals(XML_INPUTSTREAM)){
			input=(InputStream)xmlInputStream;
		}else{
			throw new XmlUnmarshalException("无法识别的xml类型.");
		}
		return input;
	}
	/**
	 * 初始化
	 * @throws XmlUnmarshalException
	 */
	private Object init(ClassLoader classloader) throws XmlUnmarshalException{
		Object input=null;
		try {
//			jaxbContext=JAXBContext.newInstance(packageName);
			jaxbContext=JAXBContext.newInstance(packageName,classloader);
		} catch (JAXBException e) {
	    	log.error(e);
		}
		if(nowXmlType.equals(XML_STRING)){
			xmlString=StringClass.getString(xmlString);
			Reader reader=new StringReader(xmlString);
			input=reader;
			return input;
		}else if(nowXmlType.equals(XML_FILE_NAME)){
			String xmlFile=StringClass.getFormatPath((String)xmlFileName);
			input=snoicsFileHelper.fileToInputStream(xmlFile);
	    	if(input==null){
		    	throw new XmlUnmarshalException("生成Xml文件流失败："+xmlFile);
	    	}
		}else if(nowXmlType.equals(XML_INPUTSTREAM)){
			input=(InputStream)xmlInputStream;
		}else{
			throw new XmlUnmarshalException("无法识别的xml类型.");
		}
		return input;
	}
}

