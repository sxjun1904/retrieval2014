package frame.retrieval.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;
import org.textmining.text.extraction.WordExtractor;


public class WordReader {
	public static void main(String[] args) {
        String wordFilePath03 = "D:/readWordFileTest.doc";
        String wordFilePath07 = "D:/img/JWeb全文检索详细设计.docx";

        WordReader word = new WordReader();

        // 读取 word 2003 版本文件
//        String fileContent03 = word.ReadWordFile2003(wordFilePath03);
//        System.out.println(fileContent03);

        // 读取 word 2007 版本文件
        String fileContent07 = word.ReadWordFile2007(wordFilePath07);
        System.out.println(fileContent07);
    }

    /**
     * 读取 word 2003 版本文件 .doc格式
     * 
     * 使用：tm-extractors-0.4.jar 文件，只可以读取 word2003 版的文件 如果你的word文档使用 wps程序
     * 保存过，会出现以下错误，只需要再用 word程序 打开保存一下就可以解决
     * org.textmining.text.extraction.FastSavedException: Fast-saved files are
     * unsupported at this time
     * 
     * @param filePath
     * @return
     */
    private String ReadWordFile2003(String filePath) {
        String text = "";
        WordExtractor extractor = null;
        FileInputStream inputStream = null;

        try {

            inputStream = new FileInputStream(filePath);
            extractor = new WordExtractor();
            text = extractor.extractText(inputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	try {
        		if(inputStream!=null)
        			inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }

        return text;
    }

    /**
     * 读取 word 2007 以上版本文件 .docx格式
     * 
     * 上面的方法只可读取 word2003版本的文件，无法读取word2007及以上版本的文档， 要想读取word2007
     * word2010版本的文档必须使用apache的poi开源项目包，下载地址：
     * http://www.apache.org/dyn/closer.cgi/poi/release/bin/poi-bin-3.9-20121203.tar.gz
     * 
     * 使用到的 jar 包 
     * poi-3.9-20121203.jar poi-ooxml-3.9-20121203.jar
     * poi-ooxml-schemas-3.9-20121203.jar poi-scratchpad-3.9-20121203.jar
     * xmlbeans-2.3.0.jar dom4j-1.6.1.jar
     * 
     * @param filePath
     * @return
     */
    private String ReadWordFile2007(String filePath) {
        String text = "";
        try {
            OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            text = extractor.getText();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        } catch (OpenXML4JException e) {
            e.printStackTrace();
        }

        return text;
    }
}
