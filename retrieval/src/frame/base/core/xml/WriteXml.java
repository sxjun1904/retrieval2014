package frame.base.core.xml;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import frame.base.core.exception.SnoicsRuntimeException;
import frame.base.core.util.StringClass;
import frame.base.core.util.file.FileHelper;

/**
 * 写XML<br><br>
 * Example:<br><br>
&nbsp;&nbsp;WriteXml writeXml=new WriteXml();<br>
&nbsp;&nbsp;writeXml.setXmlfile("sul.xml");<br>
&nbsp;&nbsp;writeXml.parseXMLFile();<br>
&nbsp;&nbsp;Element newSuleElement=writeXml.createElement("sul");<br>
&nbsp;&nbsp;writeXml.setAttribute(newSuleElement,"id","snoics");<br>
&nbsp;&nbsp;writeXml.setAttribute(newSuleElement,"name","");<br>
&nbsp;&nbsp;Element newSulClassname=writeXml.createTextNode("classname","framework.base.sample.Classname");<br>
&nbsp;&nbsp;newSuleElement.appendChild((Node)newSulClassname);<br>
&nbsp;&nbsp;Element newUserFields=writeXml.createElement("fields");<br>
&nbsp;&nbsp;Element newRoleFields=writeXml.createElement("fields");<br>
&nbsp;&nbsp;writeXml.setAttribute(newUserFields,"type","user");<br>
&nbsp;&nbsp;writeXml.setAttribute(newRoleFields,"type","role");<br>
&nbsp;&nbsp;newSuleElement.appendChild((Node)newUserFields);<br>
&nbsp;&nbsp;newSuleElement.appendChild((Node)newRoleFields);<br>
&nbsp;&nbsp;writeXml.appendNode((Node)newSuleElement);<br>
&nbsp;&nbsp;System.out.println(writeXml.save());<br>
 * @author 
 *
 */
public class WriteXml{
	//将要被处理的XML
	private Object xmlfile=null;
	
	//当前生成的节点list
	private NodeList currentNodeList=null;
	
	//当前生成的节点
	private Node currentNode=null;
	
	private Document document=null;
	
	//private DOMImplementation dOMImplementation=new DOMImplementationImpl();
	
	public WriteXml() {
		
	}
	
	/**
	 * 解析xml文件
	 *
	 */
	public void parseXMLFile() {
	    ReadXml readXml=new ReadXml();
	    readXml.setXmlfile(xmlfile);
	    readXml.parseXMLFile();
	    this.document=readXml.getDocument();
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
     * 获取根节点 
     * @return Element
     */
    public Element getRoot(){
    	Element root=document.getDocumentElement();
    	return root;
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
     * 创建空的Document
     *
     */
    public void createDocument() {
    	try {
    		document=XMLFactory.getDocument();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * 创建命名空间
     * @param name
     * @param value
     */
    public void createNameSpace(String name,String value){
    	Element root=document.getDocumentElement();
    	root.setAttribute(name,value);
    }
    
    /**
     * 创建Document
     * @param docNS
     * @param name
     * @param documentType
    public void createDocument(String docNS,String name,DocumentType documentType) {
    		document=dOMImplementation.createDocument(docNS,name,documentType);
    }
    
     * 创建DocumentType
     * @param name
     * @param publicId
     * @param systemId
     * @return DocumentType
    public DocumentType createDocumentType(String name,String publicId,String systemId){
    	DocumentType documentType=dOMImplementation.createDocumentType(name,publicId,systemId);
    	return documentType;
    }
    
   */
    
    /**
     * 创建节点
     * @param name
     */
    public Element createElement(String name) {
    	Element element=document.createElement(name);
    	return element;
    }

    /**
     * 创建节点NS
     * @param docNS
     * @param name
     * @return Element
     */
    public Element createElement(String docNS,String name) {
    	Element element=document.createElementNS(docNS,name);
    	return element;
    }
    
    /**
     * 创建Text
     * @param value
     * @return Text
     */
    public Text createText(String value) {
    	Text text=document.createTextNode(value);
    	return text;
    }
    
    /**
     * 创建Text节点
     * @param name
     * @param value
     * @return Element
     */
    public Element createTextNode(String name,String value) {
    	Element element=createElement(name);
    	Text text=createText(value);
    	element.appendChild(text);
    	return element;
    }
    
    /**
     * 创建Text节点
     * @param docNS
     * @param name
     * @param value
     * @return Element
     */
    public Element createTextNode(String docNS,String name,String value) {
    	Element element=createElement(docNS,name);
    	Text text=createText(value);
    	element.appendChild(text);
    	return element;
    }
    
    /**
     * 创建Comment
     * @param value
     * @return Comment
     */
    public Comment createComment(String value){
    	Comment comment=document.createComment(value);
    	return comment;
    }
    
    /**
     * 创建Comment节点
     * @param name
     * @param value
     * @return Element
     */
    public Element createCommentNode(String name,String value){
    	Element element=createElement(name);
    	Comment comment=createComment(value);
    	element.appendChild(comment);
    	return element;
    }
    
    /**
     * 创建Comment节点NS
     * @param docNS
     * @param name
     * @param value
     * @return Element
     */
    public Element createCommentNode(String docNS,String name,String value){
    	Element element=createElement(docNS,name);
    	Comment comment=createComment(value);
    	element.appendChild(comment);
    	return element;
    }
    
    /**
     * 创建EntityReference
     * @param value
     * @return EntityReference
     */
    public EntityReference createEntityReference(String value){
    	EntityReference entityReference=document.createEntityReference(value);
    	return entityReference;
    }
    
    /**
     * 创建EntityReference节点
     * @param name
     * @param value
     * @return Element
     */
    public Element createEntityReferenceNode(String name,String value){
    	Element element=createElement(name);
    	EntityReference entityReference=createEntityReference(value);
    	element.appendChild(entityReference);
    	return element;
    }
    
    /**
     * 创建EntityReference节点NS
     * @param docNS
     * @param name
     * @param value
     * @return Element
     */
    public Element createEntityReferenceNode(String docNS,String name,String value){
    	Element element=createElement(docNS,name);
    	EntityReference entityReference=createEntityReference(value);
    	element.appendChild(entityReference);
    	return element;
    }
    
    /**
     * 创建DocumentFragment
     * @return DocumentFragment
     */
    public DocumentFragment createDocumentFragment(){
    	DocumentFragment documentFragment=document.createDocumentFragment();
    	return documentFragment;
    }
    
    /**
     * 创建DocumentFragment节点
     * @param name
     * @return Element
     */
    public Element createDocumentFragmentNode(String name){
    	Element element=createElement(name);
    	DocumentFragment documentFragment=createDocumentFragment();
    	element.appendChild(documentFragment);
    	return element;
    }
    
    /**
     * 创建DocumentFragment节点NS
     * @param docNS
     * @param name
     * @return Element
     */
    public Element createDocumentFragmentNode(String docNS,String name){
    	Element element=createElement(docNS,name);
    	DocumentFragment documentFragment=createDocumentFragment();
    	element.appendChild(documentFragment);
    	return element;
    }
    
    /**
     * 创建CDATA
     * @param value
     * @return CDATASection
     */
    public CDATASection createCDATASection(String value) {
    	CDATASection cDATASection=document.createCDATASection(value);
    	return cDATASection;
    }
    
    /**
     * 创建CDATA节点
     * @param name
     * @param value
     * @return Element
     */
    public Element createCDATANode(String name,String value) {
    	Element element=createElement(name);
    	CDATASection cDATASection=createCDATASection(value);
    	element.appendChild(cDATASection);
    	return element;
    }
    
    /**
     * 创建CDATA节点
     * @param docNS
     * @param name
     * @param value
     * @return Element
     */
    public Element createCDATANode(String docNS,String name,String value) {
    	Element element=createElement(docNS,name);
    	CDATASection cDATASection=createCDATASection(value);
    	element.appendChild(cDATASection);
    	return element;
    }
    
    /**
     * 创建Attr节点
     * @param name
     * @return Attr
     */
    public Attr createAttr(String name) {
    	Attr attr=document.createAttribute(name);
    	return attr;
    }
    
    /**
     * 设置属性
     * @param element
     * @param name
     * @param value
     */
    public void setAttribute(Element element,String name,String value) {
    	element.setAttribute(name,value);
    }
    
    /**
     * 把节点添加到Document作为根节点
     * @param root
     */
    public void appendRoot(Element root) {
    	document.appendChild(root);
    }
    
    /**
     * 创建并添加为根节点
     * @param name
     */
    public void createRoot(String name) {
    	Element root=createElement(name);
    	appendRoot(root);
    }
    
    /**
     * 添加根节点
     * @param cNode
     */
    public void appendNode(Node cNode) {
    	Element root=document.getDocumentElement();
    	root.appendChild(cNode);
    }
    
    /**
     * 给节点添加一个子节点
     * @param pNode
     * @param cNode
     */
    public void appendNode(Node pNode,Node cNode) {
    	pNode.appendChild(cNode);
    }
    
    /**
     * 更新一个节点
     * @param node
     * @param value
     */
    public void setNodeValue(Node node,String value) {
    	node.getFirstChild().setNodeValue(value);
    }
    
    /**
     * 保存Document
     * @param filename
     * @return boolean
     */
    public boolean save(Object filename) {
    	boolean flag=XMLUtil.saveDocument(document,filename);
    	return flag;
    }
    
    /**
     * 保存Document
     * @return boolean
     */
    public boolean save() {
    	boolean flag=false;
    	if(xmlfile instanceof String) {
    		String stringXmlfile=(String)xmlfile;
    		FileHelper snoicsFile=new FileHelper();
    		if(!snoicsFile.isFile(stringXmlfile)) {
//    			String realpath=StringClass.getRealPath(this.getClass(),"/","UTF-8");
    			String realpath=StringClass.getRealPath(this.getClass());
    			String thexmlfile=StringClass.getFormatPath(realpath+"/"+stringXmlfile);
    			flag=XMLUtil.saveDocument(document,thexmlfile);
    		}else{
    			flag=XMLUtil.saveDocument(document,stringXmlfile);
    		}
    	}else {
    		throw new SnoicsRuntimeException("传入的不是xml文件名，保存失败");
    	}
    	return flag;
    }
    
    public static void main(String[] args) {
    	WriteXml writeXml=new WriteXml();
        writeXml.createDocument();
    	writeXml.createRoot("suls");
    	writeXml.createNameSpace("xmlns","http://www.a.com/b/1");
    	writeXml.createNameSpace("xmlns:ora","http://www.a.com/b/2");
    	Element newcreateDocumentFragmentValue=writeXml.createDocumentFragmentNode("documentFragmentValue");
    	writeXml.appendNode((Node)newcreateDocumentFragmentValue);
    	Element newSuleComment=writeXml.createCommentNode("sulComment","testSulComment");
    	writeXml.appendNode((Node)newSuleComment);

    	Element newEntityReferenceValue=writeXml.createEntityReferenceNode("createEntityReferenceValue","fjsdklfjdls");
    	writeXml.appendNode((Node)newEntityReferenceValue);
    	
    	Element newSuleElement=writeXml.createElement("sul");
    	writeXml.setAttribute(newSuleElement,"id","id1");
    	writeXml.setAttribute(newSuleElement,"name","name1");
    	Element newSulClassname=writeXml.createTextNode("classname","com.a.b.C");
    	newSuleElement.appendChild((Node)newSulClassname);
    	Element newUserFields=writeXml.createElement("fields");
    	Element newRoleFields=writeXml.createElement("fields");
    	writeXml.setAttribute(newUserFields,"type","user");
    	writeXml.setAttribute(newRoleFields,"type","role");
    	newSuleElement.appendChild((Node)newUserFields);
    	newSuleElement.appendChild((Node)newRoleFields);
    	writeXml.appendNode((Node)newSuleElement);
    	System.out.println(writeXml.save("c:/1.xml"));
    }
}
