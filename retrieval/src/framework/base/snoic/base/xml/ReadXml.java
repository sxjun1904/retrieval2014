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
package framework.base.snoic.base.xml;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import framework.base.snoic.base.exception.SnoicsRuntimeException;
import framework.base.snoic.base.util.StringClass;
import framework.base.snoic.base.util.file.FileHelper;

/**
 * 读取XML<br><br>
 * 
 * Example:<br><br>
 * 
 * public void readXmlFile(){<br>
    &nbsp;ReadXml readXml=new ReadXml();<br>
    &nbsp;readXml.setXmlfile("sul.xml");<br>
    &nbsp;readXml.parseXMLFile();<br>
    &nbsp;String basenode="";<br>
    &nbsp;try{<br>
    &nbsp;&nbsp;ArrayList value=readXml.getNodeValue("base");<br>
    &nbsp;&nbsp;basenode=(String)value.get(0);<br>
    &nbsp;}catch(Exception e) {<br>
    &nbsp;&nbsp;<br>
    &nbsp;}<br>
    &nbsp;System.out.println("basenode="+basenode);<br>
<br><br>
    &nbsp;readXml.setCurrentNodeList("sul");<br>
    &nbsp;int length=readXml.getCurrentNodeListLength();<br>
    &nbsp;System.out.println("length="+length);<br>
    &nbsp;for(int i=0;i&ltlength;i++) {<br>
        &nbsp;readXml.setCurrentNode(i);<br>
        &nbsp;String id=readXml.getAttribute("id");<br>
        &nbsp;String name=readXml.getAttribute("name");<br>
        &nbsp;String classname=(String)readXml.getNodeValue("classname").get(0);<br>
        &nbsp;System.out.println("id"+i+"="+id);<br>
        &nbsp;System.out.println("name"+i+"="+name);<br>
        &nbsp;System.out.println("classname"+i+"="+classname);<br>
        &nbsp;<br>
        &nbsp;ArrayList nodeValueList=readXml.getNodeValue("a");<br>
        &nbsp;if(nodeValueList!=null) {<br>
            &nbsp;System.out.println("nodeValueList"+i+".size="+nodeValueList.size());<br>
            &nbsp;System.out.println("nodeValueList"+i+"="+nodeValueList);<br>
        &nbsp;}<br>
    &nbsp;}<br>
    &nbsp;try {<br>
        &nbsp;readXml.setCurrentNode(0);<br>
        &nbsp;readXml.setCurrentNodeList("fields");<br>
        &nbsp;readXml.setCurrentNode(0);<br>
        &nbsp;readXml.setCurrentNodeList("field");<br>
        &nbsp;<br>
        &nbsp;length=readXml.getCurrentNodeListLength();<br>
        &nbsp;<br>
        &nbsp;for(int i=0;i&ltlength;i++) {<br>
            &nbsp;readXml.setCurrentNode(i);<br>
            &nbsp;String type=readXml.getAttribute("type");<br>
            &nbsp;System.out.println("field type"+i+"="+type);<br>
            &nbsp;String name="";<br>
            &nbsp;try{<br>
            &nbsp;&nbsp;name=(String)readXml.getNodeValue("name").get(0);<br>
            &nbsp;}catch(Exception e) {<br>
            &nbsp;&nbsp;<br>
            &nbsp;}<br>
            &nbsp;System.out.println("field name"+i+"="+name);<br>
        &nbsp;}<br>
    &nbsp;}catch(Exception e) {<br>
    &nbsp;&nbsp;e.printStackTrace();<br>
    &nbsp;}<br>
    }<br><br>
<br><br>
Example : XmlFile<br><br>
&lt?xml version="1.0" encoding="GBK"?&gt<br>
&ltsuls&gt<br>
&nbsp;&ltbase&gtoa&lt/base&gt<br>
&nbsp;&ltsul id="oa" name="OA"&gt<br>
&nbsp;&nbsp;&ltclassname&gtcom.class1&lt/classname&gt<br>
&nbsp;&nbsp;&lta&gtsdfdsfdf1&lt/a&gt<br>
&nbsp;&nbsp;&lta&gtsdfdsfdf2&lt/a&gt<br>
&nbsp;&lt/sul&gt<br>
&nbsp;&nbsp;&ltfields&gt<br>
&nbsp;&nbsp;&nbsp;&ltfield type="text"&gt<br>
&nbsp;&nbsp;&nbsp;&nbsp;&ltname&gtusername&lt/name&gt<br>
&nbsp;&nbsp;&nbsp;&lt/field&gt<br>
&nbsp;&nbsp;&nbsp;&ltfield type="hidden"&gt<br>
&nbsp;&nbsp;&nbsp;&nbsp;&ltname&gthiddendata&lt/name&gt<br>
&nbsp;&nbsp;&nbsp;&lt/field&gt<br>
&nbsp;&nbsp;&nbsp;&ltfield type="select"&gt<br>
&nbsp;&nbsp;&nbsp;&nbsp;&ltname&gtselect1&lt/name&gt<br>
&nbsp;&nbsp;&nbsp;&lt/field&gt<br>
&nbsp;&nbsp;&nbsp;&ltfield type="radio"&gt<br>
&nbsp;&nbsp;&nbsp;&nbsp;&ltname&gtgender&lt/name&gt<br>
&nbsp;&nbsp;&nbsp;&lt/field&gt<br>
&nbsp;&nbsp;&nbsp;&ltfield type="checkbox"&gt<br>
&nbsp;&nbsp;&nbsp;&nbsp;&ltname&gtbook&lt/name&gt<br>
&nbsp;&nbsp;&nbsp;&lt/field&gt<br>
&nbsp;&nbsp;&nbsp;&ltfield type="textarea"&gt<br>
&nbsp;&nbsp;&nbsp;&nbsp;&ltname&gtremark&lt/name&gt<br>
&nbsp;&nbsp;&lt/fields&gt<br>
&nbsp;&nbsp;&ltfields&gt&lt/fields&gt<br>
&nbsp;&ltsul id="cms" name="cms"&gt<br>
&nbsp;&nbsp;&ltclassname&gtcom.class2&lt/classname&gt<br>
&nbsp;&lt/sul&gt<br>
&nbsp;&ltsul id="system3" name="system3"&gt<br>
&nbsp;&nbsp;&ltclassname&gtcom.class3&lt/classname&gt<br>
&nbsp;&lt/sul&gt<br>
&nbsp;&ltsul id="system4" name="system4"&gt<br>
&nbsp;&nbsp;&ltclassname&gtcom.class4&lt/classname&gt<br>
&nbsp;&lt/sul&gt<br>
&lt/suls&gt<br><br>

 * @author 
 *
 */
public class ReadXml {

	private Document document=null;
	
	//将要被处理的XML
	private Object xmlfile=null;
	
	//当前生成的节点list
	private NodeList currentNodeList=null;
	
	//当前生成的节点
	private Node currentNode=null;
	
	private FileHelper snoicsFile=new FileHelper();
	
	public ReadXml() {
		
	}
	
	/**
	 * 回到执行完parseXMLFile()操作后的初始状态
	 *
	 */
	public void reset() {
		currentNodeList=null;
		currentNode=null;
	}
	
	/**
	 * 设置将要被处理的XML<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;xmlfile可以是以下几种形式：<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   1.绝对路径的文件名 eg: c:\xmlfile.xml<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   2.单个文件名，但是此文件必须放在classpath下 eg: xmlfile.xml<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   3.InputStream<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   4.File<br><br>
	 * @param xmlfile
	 */
	public void setXmlfile(Object xmlfile) {
		this.xmlfile=xmlfile;
	}
	
	/**
	 * 获取将要被处理的XML
	 * @return Object
	 */
	public Object getXmlfile() {
		return xmlfile;
	}
	
    /**
     * 设置当前的NodeList
     * @param currentNodeList
     */
    public void setCurrentNodeList(NodeList currentNodeList) {
    	this.currentNodeList=currentNodeList;
    }
    
    /**
     * 获取当前的NodeList
     * @return NodeList
     */
    public NodeList getCurrentNodeList() {
    	return currentNodeList;
    }
    
    /**
     * 设置当前的Node
     * @param currentNode
     */
    public void setCurrentNode(Node currentNode) {
    	this.currentNode=currentNode;
    }
    
    /**
     * 获取当前的Node 
     * @return Node
     */
    public Node getCurrentNode() {
    	return currentNode;
    }
    
    /**
     * 设置Document对象
     * @param document
     */
    public void setDocument(Document document) {
    	this.document=document;
    }
    
    /**
     * 获取Document对象
     * @return Document
     */
    public Document getDocument() {
    	return document;
    }
    
    /**
     * 获取当前NodeList的长度
     * @return int
     */
    public int getCurrentNodeListLength() {
    	return currentNodeList.getLength();
    }
    
    /**
     * 生成Document对象
     * 
     * @return Document
     */

    public void parseXMLFile() {
    	if(xmlfile instanceof String) {
    		String newxmlfile=StringClass.getFormatPath((String)xmlfile);
    		if(!snoicsFile.isFile(newxmlfile)) {
    			String realpath=StringClass.getRealPath(this.getClass());
    			String thexmlfile=StringClass.getFormatPath(realpath+"/"+newxmlfile);
    			document = XMLUtil.parseXMLFile(thexmlfile);
    		}else{
    			document = XMLUtil.parseXMLFile(newxmlfile);
    		}
    	}else if(xmlfile instanceof InputStream) {
    		InputStream newxmlfile=(InputStream)xmlfile;
    		document = XMLUtil.parseXMLFile(newxmlfile);
    		try{
    			newxmlfile.close();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}else if(xmlfile instanceof File) {
    		File newxmlfile=(File)xmlfile;
    		document = XMLUtil.parseXMLFile(newxmlfile);
    	}
    }

    /**
     * 根据传入的节点名称,生成当前节点的NodeList<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果当前currentNode==null则生成的是根节点下的nodeName下的NodeList<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果当前currentNode!=null则生成的是当前currentNode下的nodeName下的NodeList<br>
     * @param nodeName 节点名称
     * @return boolean
     */
    public boolean setCurrentNodeList(String nodeName) {
    	boolean flag=false;
    	try {
    		NodeList currentNodeList = null;
    		if(currentNode==null) {
    			currentNodeList = document.getDocumentElement().getElementsByTagName(nodeName);
    		}else {
    		    Element nodeElement = (Element)currentNode;
    		    currentNodeList = nodeElement.getElementsByTagName(nodeName);
    		}
            this.currentNodeList = currentNodeList;
            flag=true;
    	}catch(Exception e) {
    		e.printStackTrace() ;
    		flag=false;
    	}
    	return flag;
    }
    
    /**
     * 设置根据当前NodeList和索引值取得当前节点
     * @param nodeIndex 节点索引
     */
    public void setCurrentNode(int nodeIndex) throws SnoicsRuntimeException {
		currentNode = currentNodeList.item(nodeIndex);
		if (currentNode == null) {
			throw new SnoicsRuntimeException();
		}
	}
    
    /**
	 * 设置当前节点
	 * 
	 * @param nodeName
	 *            父节点名称
	 * @param nodeIndex
	 *            节点索引
	 * @return boolean
	 */
    public boolean setCurrentNode(String nodeName,int nodeIndex) {
    	boolean flag=false;
    	flag=setCurrentNodeList(nodeName);
    	if(!flag) {
    		return flag;
    	}
    	setCurrentNode(nodeIndex);
    	return flag;
    }
    
    /**
     * 获取当前节点的属性值
     * @param attributeName
     * @return String 如果不存在这个属性则返回NULL
     */
    public String getAttribute(String attributeName) {
    	return XMLUtil.getAttribute(currentNode,attributeName);
    }
    
    /**
     * 获取节点属性值
     * @param currentNode
     * @param attributeName
     * @return String 如果不存在这个属性则返回NULL
     */
    public String getAttribute(Node currentNode,String attributeName) {
    	return XMLUtil.getAttribute(currentNode,attributeName);
    }
    
    /**
     * 获取当前节点值
     * @return String
     */
    public String getCurrentNodeValue(){
    	String nodeValue="";
    	if(currentNode!=null) {
    		Element node=(Element) currentNode;
			try {
				nodeValue = node.getFirstChild().getNodeValue();
			} catch (Exception e) {

			}
    	}
    	return nodeValue;
    }
    
    /**
     * 获取节点值
     * @param node
     * @return String
     */
    public String getNodeValue(Node node){
    	String nodeValue="";
    	if(node!=null) {
			try {
				nodeValue = node.getFirstChild().getNodeValue();
			} catch (Exception e) {

			}
    	}
    	return nodeValue;
    }
    
    /**
     * 取得当前节点的所有nodeName节点的值
     * @return ArrayList
     */
    public List getNodeValue(String nodeName) {
    	List value=null;
    	
    	NodeList nodeList=null;
    	if(currentNode!=null) {
    		nodeList=getNodeValueNodeList((Element)currentNode,nodeName);
    	}else {
    		nodeList=getNodeValueNodeList(document.getDocumentElement(),nodeName);
    	}
    	
    	if(nodeList==null) {
    		return value;
    	}
    	int length=nodeList.getLength();
    	if(length>0) {
    		value=new ArrayList();
        	for(int i=0;i<length;i++) {
        		Element node=(Element) nodeList.item(i);
            	String nodeValue = "";
				try {
					nodeValue = node.getFirstChild().getNodeValue();
				} catch (Exception e) {

				}
				value.add(nodeValue);
        	}
    	}
    	return value;
    }
    
    /**
     * 取得传入的节点下的节点名为nodeName的NodeList
     * @param node
     * @param nodeName
     * @return NodeList
     */
    private NodeList getNodeValueNodeList(Element element,String nodeName) {
    	NodeList currentNodeList = null;
    	try {
    		currentNodeList = element.getElementsByTagName(nodeName);
    	}catch(Exception e) {
    		
    	}
    	return currentNodeList;
    }
    
    public void test(){
    	setXmlfile("D:\\workspace\\Snoics\\SUL\\build\\sul.xml");
    	parseXMLFile();
    	String[] basenode=null;
    	try{
    		List value=getNodeValue("base");
    		if((value!=null)&&(!value.isEmpty())) {
    			basenode=new String[value.size()];
    			for(int i=0;i<value.size();i++) {
    				basenode[i]=(String)value.get(i);
    				System.out.println("basenode["+i+"]="+basenode[i]);
    			}
    		}
    		
    	}catch(Exception e) {
    		
    	}

    	String[] basenode1=null;
    	try{
    		List value=getNodeValue("base1");
    		if((value!=null)&&(!value.isEmpty())) {
    			basenode1=new String[value.size()];
    			for(int i=0;i<value.size();i++) {
    				basenode1[i]=(String)value.get(i);
    				System.out.println("basenode1["+i+"]="+basenode1[i]);
    			}
    		}
    		
    	}catch(Exception e) {
    		
    	}
    	
    	reset();
    	setCurrentNodeList("sul");
    	int length=getCurrentNodeListLength();
    	System.out.println("length="+length);
    	for(int i=0;i<length;i++) {
        	setCurrentNode(i);
        	String id=getAttribute("id");
        	String name=getAttribute("name");
        	String classname="";
        	try{
        		classname=(String)getNodeValue("classname").get(0);
        	}catch(Exception e) {
        		
        	}
        	System.out.println("id"+i+"="+id);
        	System.out.println("name"+i+"="+name);
        	System.out.println("classname"+i+"="+classname);
        	
        	List nodeValueList=getNodeValue("a");
        	if(nodeValueList!=null) {
            	System.out.println("nodeValueList"+i+".size="+nodeValueList.size());
            	System.out.println("nodeValueList"+i+"="+nodeValueList);
        	}
    	}
    	try {
        	setCurrentNode(0);
        	setCurrentNodeList("fields");
        	setCurrentNode(0);
        	setCurrentNodeList("field");
        	
        	length=getCurrentNodeListLength();
        	
        	for(int i=0;i<length;i++) {
            	setCurrentNode(i);
            	String type=getAttribute("type");
            	System.out.println("field type"+i+"="+type);
            	String name="";
            	try{
            		name=(String)getNodeValue("name").get(0);
            	}catch(Exception e) {
            		
            	}
            	System.out.println("field name"+i+"="+name);
        	}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public static void main(String[] args) {
    	ReadXml readXml=new ReadXml();
    	readXml.setXmlfile("D:\\workspace\\Snoics\\SUL\\build\\sul.xml");
    	readXml.parseXMLFile();
    	String[] basenode=null;
    	try{
    		List value=readXml.getNodeValue("base");
    		if((value!=null)&&(!value.isEmpty())) {
    			basenode=new String[value.size()];
    			for(int i=0;i<value.size();i++) {
    				basenode[i]=(String)value.get(i);
    				System.out.println("basenode["+i+"]="+basenode[i]);
    			}
    		}
    		
    	}catch(Exception e) {
    		
    	}

    	String[] basenode1=null;
    	try{
    		List value=readXml.getNodeValue("base1");
    		if((value!=null)&&(!value.isEmpty())) {
    			basenode1=new String[value.size()];
    			for(int i=0;i<value.size();i++) {
    				basenode1[i]=(String)value.get(i);
    				System.out.println("basenode1["+i+"]="+basenode1[i]);
    			}
    		}
    		
    	}catch(Exception e) {
    		
    	}
    	
    	readXml.reset();
    	readXml.setCurrentNodeList("sul");
    	int length=readXml.getCurrentNodeListLength();
    	System.out.println("length="+length);
    	for(int i=0;i<length;i++) {
        	readXml.setCurrentNode(i);
        	String id=readXml.getAttribute("id");
        	String name=readXml.getAttribute("name");
        	String classname="";
        	try{
        		classname=(String)readXml.getNodeValue("classname").get(0);
        	}catch(Exception e) {
        		
        	}
        	System.out.println("id"+i+"="+id);
        	System.out.println("name"+i+"="+name);
        	System.out.println("classname"+i+"="+classname);
        	
        	List nodeValueList=readXml.getNodeValue("a");
        	if(nodeValueList!=null) {
            	System.out.println("nodeValueList"+i+".size="+nodeValueList.size());
            	System.out.println("nodeValueList"+i+"="+nodeValueList);
        	}
    	}
    	try {
        	readXml.setCurrentNode(0);
        	readXml.setCurrentNodeList("fields");
        	readXml.setCurrentNode(0);
        	readXml.setCurrentNodeList("field");
        	
        	length=readXml.getCurrentNodeListLength();
        	
        	for(int i=0;i<length;i++) {
            	readXml.setCurrentNode(i);
            	String type=readXml.getAttribute("type");
            	System.out.println("field type"+i+"="+type);
            	String name="";
            	try{
            		name=(String)readXml.getNodeValue("name").get(0);
            	}catch(Exception e) {
            		
            	}
            	System.out.println("field name"+i+"="+name);
        	}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
