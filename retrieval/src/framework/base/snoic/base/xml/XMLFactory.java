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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
/**
 * XMLFactory
 * @author 
 *
 */
public class XMLFactory {
	
    private XMLFactory() {
    	
    }

    /**
     * 获得 DocumentBuilderFactory 对象 <br>
     * 
     * @return DocumentBuilderFactory
     * @throws Exception
     */
    public static DocumentBuilderFactory getDocumentBuilderFactory() throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        return documentBuilderFactory;
    }

    /**
     * 获得 DocumentBuilder 对象 <br>
     * @return DocumentBuilder
     * @throws Exception
     */
    public static DocumentBuilder getDocumentBuilder() throws Exception {
        DocumentBuilderFactory dbfactory = getDocumentBuilderFactory();
        DocumentBuilder db = dbfactory.newDocumentBuilder();
        return db;
    }

    /**
     * 获得 Document 对象 <br>
     * @return Document
     * @throws Exception
     */
    public static Document getDocument() throws Exception {
        DocumentBuilder documentBuilder = getDocumentBuilder();
        if (documentBuilder == null) {
            return null;
        }
        Document document = documentBuilder.newDocument();
        return document;
    }
    
    /**
     * 获得 Document 对象 <br>
     * @param xmlFile
     * @return Document
     * @throws Exception
     */
    public static Document getDocument(File xmlFile) throws Exception {
        DocumentBuilder documentBuilder = getDocumentBuilder();
        if (documentBuilder == null) {
            return null;
        }
        Document document = documentBuilder.parse(xmlFile);
        return document;
    }

    /**
     * 获得 Document 对象 <br>
     * @param xmlString
     * @return Document
     * @throws Exception
     */
    public static Document getDocument(String xmlString) throws Exception {
        DocumentBuilder documentBuilder = getDocumentBuilder();
        if (documentBuilder == null) {
            return null;
        }
        File file=new File(xmlString);
        Document document=documentBuilder.parse(file);
        return document;
    }

    /**
     * 获得 Document 对象 <br>
     * @param in
     * @return Document
     * @throws Exception
     */
    public static Document getDocument(InputStream in) throws Exception {
        DocumentBuilder documentBuilder = getDocumentBuilder();
        if (documentBuilder == null) {
            return null;
        }
        Document document = documentBuilder.parse(in);
        return document;
    }

    /**
     * 获得 TransformerFactory 对象 <br>
     * 
     * @return TransformerFactory
     * @throws Exception
     */
    public static TransformerFactory getTransformerFactory() throws Exception {
        TransformerFactory tfactory = TransformerFactory.newInstance();
        return tfactory;
    }

    /**
     * @param xslFileName
     *            XSL 文件的路径或 URL 字符串
     * @return 可以在这里加入缓存 Templates 的功能
     */
    public static Transformer getTransformer(String xslFileName) throws Exception {
        TransformerFactory tfactory = getTransformerFactory();
        Transformer transformer = null;
        if(xslFileName!=null) {
            Templates templates = tfactory.newTemplates(new StreamSource(XMLUtil.createURL(xslFileName).openStream()));
            transformer = templates.newTransformer();
        }else {
        	transformer = tfactory.newTransformer();
        }
        return transformer;
    }

    /**
     * 获得 SAXParserFactory 对象 <br>
     * 
     * @return SAXParserFactory
     * @throws Exception
     */
    public static SAXParserFactory getSAXParserFactory() throws Exception {
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        return saxFactory;
    }

    /**
     * 获得 SAXParser 对象 <br>
     * @return SAXParser
     * @throws Exception
     */
    public static SAXParser getSAXParser() throws Exception {
        SAXParserFactory saxFactory = getSAXParserFactory();
        SAXParser saxParser = saxFactory.newSAXParser();
        return saxParser;
    }

}