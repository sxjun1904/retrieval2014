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
package framework.base.snoic.base.util.jaxb;

import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import framework.base.snoic.base.BuildSnoicsClassFactory;
import framework.base.snoic.base.interfaces.log.Log;
import framework.base.snoic.base.util.StringClass;
import framework.base.snoic.base.util.file.FileHelper;

/**
 * 编组Xml
 * @author 
 *
 *
 */
public class XmlMarshal implements IXmlMarshal, Serializable{
	private static final long serialVersionUID = -325528865463761802L;
	
	private Log log=null;
	
	private String nowXmlType="";
	
	private String publicId;
	private String systemId;
	private List cdataNodeList;
	private String packageName;
	
	private String xmlFileName=null;
	private OutputStream xmlOutputStream=null;
	
	private Object object=null;
	private FileHelper snoicsFileHelper=new FileHelper();

	public List getCdataNodeList() {
		return cdataNodeList;
	}

	public void setCdataNodeList(List cdataNodeList) {
		this.cdataNodeList = cdataNodeList;
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public XmlMarshal(){
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

	public OutputStream getXmlOutputStream() {
		return xmlOutputStream;
	}

	public void setXmlOutputStream(OutputStream xmlOutputStream) {
		this.xmlOutputStream = xmlOutputStream;
	}

	/**
	 * 设置将被编组成XML的Object对象
	 * @param Object
	 */
	public void setObject(Object object){
		this.object=object;
	}
	
	/**
	 * 获取设置将被编组成XML的Object对象
	 * @return Object
	 */
	public Object getObject(){
		return object;
	}
	
	/**
	 * 编组xml文件
	 * @return Workflow
	 */
	public String marshal(){
		Marshaller marshaller = null;
		JAXBContext jaxbContext=null;
		try {
			jaxbContext=JAXBContext.newInstance(packageName);
		} catch (JAXBException e) {
			log.error(e);
		}
		try{
		    marshaller = jaxbContext.createMarshaller();   
		}catch(Exception e){
			log.error(e);
		}
		if(!nowXmlType.equals("")){
			OutputStream outputStream=null;
			try{
				outputStream=init();
			}catch(Exception e){
				log.error("初始化编组失败",e);
			}
			try{
			    XMLSerializer serializer = getXMLSerializer(outputStream);
			    marshaller.marshal(object,serializer.asContentHandler());
			}catch(Exception e){
				log.error("编组失败",e);
			}
		    try{
		    	if(outputStream!=null){
		    		outputStream.close();
		    	}
		    }catch(Exception e){
				log.error(e);
		    }
		}else{
			Writer wirter=new StringWriter();
			XMLSerializer serializer = getXMLSerializer(wirter);
			try{
				marshaller.marshal(object,serializer.asContentHandler());
			}catch(Exception e){
				log.error(e);
			}
			String stringWriter=wirter.toString();
			try{
				wirter.close();
			}catch(Exception e){
				log.error(e);
			}
			return stringWriter;
		}
		return null;
	}
	
	/**
	 * 初始化编组
	 * @return OutputStream
	 * @throws XmlMarshalException
	 */
	private OutputStream init() throws XmlMarshalException{
		OutputStream outputStream=null;
		if(nowXmlType.equals(XML_FILE_NAME)){
			String xmlFile=StringClass.getFormatPath((String)xmlFileName);
			outputStream=snoicsFileHelper.fileToOutputStream(xmlFile);
			if(outputStream==null){
				boolean createFileFlag=snoicsFileHelper.createFile(xmlFile);
				if(createFileFlag){
					outputStream=snoicsFileHelper.fileToOutputStream(xmlFile);
				}else{
					throw new XmlMarshalException("找不到,并且无法生成Xml文件："+xmlFile);
				}
	    	}
		}else if(nowXmlType.equals(XML_OUTPUTSTREAM)){
			outputStream=(OutputStream)xmlOutputStream;
		}else{
			throw new XmlMarshalException("无法识别的xml类型.");
		}
		return outputStream;
	}
	
	/**
	 * 设置XML的格式，以及输出方式
	 * @param outputObject
	 * @return XMLSerializer
	 */
    private XMLSerializer getXMLSerializer(Object outputObject) {
        OutputFormat of = new OutputFormat();

        publicId=StringClass.getString(publicId);
        systemId=StringClass.getString(systemId);
        
        if(!publicId.equals("")){
        	of.setDoctype(publicId,systemId);
        }
        if((cdataNodeList!=null)&&(cdataNodeList.size()>0)){
        	int length=cdataNodeList.size();
        	String[] cdataNodes=new String[length];
        	for(int i=0;i<length;i++){
        		cdataNodes[i]=(String)cdataNodeList.get(i);
        	}
        	of.setCDataElements(cdataNodes);
        }
        of.setPreserveSpace(true);
        of.setIndenting(true);
        XMLSerializer serializer = new XMLSerializer(of);
        if(outputObject instanceof OutputStream){
            serializer.setOutputByteStream((OutputStream)outputObject);
        }else if(outputObject instanceof Writer){
        	serializer.setOutputCharStream((Writer)outputObject);
        }
        return serializer;
    }
}

