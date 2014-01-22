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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import framework.base.snoic.base.util.StringClass;
import framework.base.snoic.base.util.file.FileHelper;
/**
 * XML文件操作
 * @author 
 *
 */
public final class XMLUtil {
	public final static String NODE_TYPE_ELEMENT_NODE="Element";
	public final static String NODE_TYPE_ATTRIBUTE_NODE="Attribute";
	public final static String NODE_TYPE_TEXT_NODE="Text";
	public final static String NODE_TYPE_CDATA_SECTION_NODE="CData section";
	public final static String NODE_TYPE_ENTITY_REFERENCE_NODE="Entity reference";
	public final static String NODE_TYPE_ENTITY_NODE="Entity";
	public final static String NODE_TYPE_PROCESSING_INSTRUCTION_NODE="Processing instruction";
	public final static String NODE_TYPE_COMMENT_NODE="Comment";
	public final static String NODE_TYPE_DOCUMENT_NODE="Document";
	public final static String NODE_TYPE_DOCUMENT_TYPE_NODE="Document type";
	public final static String NODE_TYPE_DOCUMENT_FRAGMENT_NODE="Document fragment";
	public final static String NODE_TYPE_NOTATION_NODE="Notation";
	
    public XMLUtil() {

    }

    /**
     * 获取节点类型
     * @param node
     * @return String
     */
    public String getNodeType(Node node) {
		String type;

		switch (node.getNodeType()) {
		case Node.ELEMENT_NODE: {
			type = "Element";
			break;
		}
		case Node.ATTRIBUTE_NODE: {
			type = "Attribute";
			break;
		}
		case Node.TEXT_NODE: {
			type = "Text";
			break;
		}
		case Node.CDATA_SECTION_NODE: {
			type = "CData section";
			break;
		}
		case Node.ENTITY_REFERENCE_NODE: {
			type = "Entity reference";
			break;
		}
		case Node.ENTITY_NODE: {
			type = "Entity";
			break;
		}
		case Node.PROCESSING_INSTRUCTION_NODE: {
			type = "Processing instruction";
			break;
		}
		case Node.COMMENT_NODE: {
			type = "Comment";
			break;
		}
		case Node.DOCUMENT_NODE: {
			type = "Document";
			break;
		}
		case Node.DOCUMENT_TYPE_NODE: {
			type = "Document type";
			break;
		}
		case Node.DOCUMENT_FRAGMENT_NODE: {
			type = "Document fragment";
			break;
		}
		case Node.NOTATION_NODE: {
			type = "Notation";
			break;
		}
		default: {
			type = "???";
			break;
		}
		}
		return type;
	}
    	
    /**
	 * parse 输入文件的 XML 数据,创建新的 Document 对象
	 * 
	 * @param xmlString
	 * @return Document
	 */
    public static Document parseXMLFile(String xmlString) {
        try {
            Document doc = XMLFactory.getDocument(xmlString);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * parse 输入文件的 XML 数据,创建新的 Document 对象
     * @param xmlFileName File文件
     * @return Document
     */
    public static Document parseXMLFile(File xmlFileName) {
        try {
            Document doc = XMLFactory.getDocument(xmlFileName);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * parse 输入文件的 XML 数据,创建新的 Document 对象
     * @param in InputStream文件流
     * @return Document
     */
    public static Document parseXMLFile(InputStream in) {
        try {
            Document doc = XMLFactory.getDocument(in);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 为节点增加属性和值
     * 
     * @param pNode
     *            Node 节点
     * @param attrName
     *            属性名
     * @param attrValue
     *            属性值
     * @return Node 新建属性节点
     */
    public static Node addAttribute(Node pNode, String attrName,
            String attrValue) {
        Node attributeNode=null;
        try{
            Attr _attr = pNode.getOwnerDocument().createAttribute(attrName);
            _attr.setNodeValue(attrValue);     
            
            attributeNode=pNode.getAttributes().setNamedItem(_attr);
            
        }catch(Exception e){
            attributeNode=null;
        }

        return attributeNode;
    }

    /**
     * 取得节点属性
     * @param pNode
     * @param attrName
     * @return String
     */
    public static String getAttribute(Node pNode,String attrName){
        try{
            NamedNodeMap name=pNode.getAttributes();
            if(name.getLength()>0){
                Node node=name.getNamedItem(attrName);
                if(node!=null){
                    String attributeValue=name.getNamedItem(attrName).getNodeValue();
                    return attributeValue;
                }else{
                    return null;
                }
            }else{
                return null;
            }            
        }catch(Exception e){
            return null;
        }

    }

    /**
     * 设置节点属性
     * @param pNode
     * @param attrName
     * @return boolean
     */
    public static boolean setAttribute(Node pNode,String attrName){
        NamedNodeMap name=pNode.getAttributes();
        if(name.getLength()>0){
            Node thenode=name.getNamedItem(attrName);
            if(thenode!=null){
                thenode.setNodeValue(attrName);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    
    /**
     * 取得子节点列表
     * @param pNode
     * @return NodeList
     */
    public static NodeList getNodeList(Node pNode,String tagName){
        Element nodeElement=(Element)pNode;
        NodeList theNodeList=nodeElement.getElementsByTagName(tagName);
        return theNodeList;
    }
    
    /**
     * 为节点增加子节点
     * 
     * @param name
     *            节点名
     * @param value
     *            节点的 textNode 节点的值
     * @return Node 新建子节点
     */
    public static Node addChildNode(Node pNode, String name, String value) {
        Document _doc = null;
        if (pNode.getNodeType() == Node.DOCUMENT_NODE) {
            _doc = (Document) pNode;
        } else {
            _doc = pNode.getOwnerDocument();
        }

        Element _ele = _doc.createElement(name);
        Node _node = _doc.createTextNode(value);
        _ele.appendChild(_node);

        return pNode.appendChild(_ele);
    }

    /**
     * 得到子节点 textnode 值
     * 
     * @param ele
     * @param tagname
     *            子节点 tag name
     * @return String "" if the ChildNode tag named not found or ChildNode has
     *         no TextNode
     */
    public static String getTextNodeValueByChildTagName(Element ele,String tagname) {
        String s = "";
        try{
            NodeList nodes = ele.getElementsByTagName(tagname);
            if (nodes.getLength() > 0) {
                Node node = getTextNode(nodes.item(0));
                if (node != null) {
                    s = node.getNodeValue();
                }
            }            
        }catch(Exception e){
            s="";
        }

        return s;
    }

    /**
     * 取得theNodeList中所有textnode类型的子节点的值
     * @param theNodeList
     * @return ArrayList
     */
    public static ArrayList getChildTextNodeValues(NodeList theNodeList){
      ArrayList arrayList = new ArrayList();
      
      for(int i=0;i<theNodeList.getLength();i++){
          Node node = getTextNode(theNodeList.item(i));
          if (node != null) {
              String s = node.getNodeValue();
              arrayList.add(s);
          }
      }
      return arrayList;
    }
    
    /**
     * 取得theNode中所有textnode类型的子节点的值
     * @param theNode
     * @return ArrayList
     */
    public static ArrayList getChildTextNodeValues(Node theNode){
      ArrayList arrayList = new ArrayList();
      if (theNode.getNodeType() != Node.TEXT_NODE){
          Element theNodeElement=(Element)theNode;
          NodeList theNodeList=theNodeElement.getChildNodes();
          for(int i=0;i<theNodeList.getLength();i++){
              Node node = getTextNode(theNodeList.item(0));
              if (node != null) {
                  String s = node.getNodeValue();
                  arrayList.add(s);
              }
          }
      }
      return arrayList;
    }
    
    /**
     * 节点的 TextNode 子节点
     * 
     * @param node
     * @return Node null if node have no TextNode
     */
    public static Node getTextNode(Node node) {
        Node textnode = null;
        NodeList nodes = node.getChildNodes();
        int len = nodes.getLength();
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                if (nodes.item(i).getNodeType() == Node.TEXT_NODE) {
                    textnode = nodes.item(i);
                    break;
                }
            }
        }
        return textnode;
    }
    
    /**
     * 创建一个 URL 对象
     * 
     * @param fileName
     *            文件名,可以是 URL 串
     * @return URL 对象
     */
    public static URL createURL(String fileName) throws MalformedURLException {
        URL url = null;
        try {
            url = new URL(fileName);
        } catch (MalformedURLException ex) {
            File f = new File(fileName);
            try {
                String path = f.getAbsolutePath();

                String fs = System.getProperty("file.separator");
                if (fs.length() == 1) {
                    char sep = fs.charAt(0);
                    if (sep != '/') {
                        path = path.replace(sep, '/');
                    }
                    if (path.charAt(0) != '/') {
                        path = '/' + path;
                    }
                }
                path = "file://" + path;
                url = new URL(path);
            } catch (MalformedURLException e) {
                throw e;
            }
        }
        return url;
    }
    
    /**
     * 把Document对象保存到本地文件
     * @param document
     * @param filename
     */
    public static boolean saveDocument(Document document, Object filename) {
    	File xmlFile = null;
    	if(filename instanceof String) {
    		FileHelper snoicsFile = new FileHelper();
        	filename = StringClass.getFormatPath((String)filename);
    		if (!snoicsFile.isFile((String)filename)) {
    			snoicsFile.createFile((String)filename);
    			//return false;
    		}
    		xmlFile = new File((String)filename);
    	}else {
    		xmlFile=(File)filename;
    	}
    	
    	DOMSource dOMSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(xmlFile);
		try {
			//创建一个TransformerFactory对象,再由此创建Transformer,用来输出XML文档。
			Transformer transformer = XMLFactory.getTransformer(null);

			//调用Transformer对象的transform()方法
			transformer.transform(dOMSource, streamResult);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}