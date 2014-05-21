package frame.retrieval.engine.index.create.impl.file.parse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;
import org.textmining.text.extraction.WordExtractor;

import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * WORD文件内容解析器
 * @author 
 *
 */
public class WordFileContentParser extends AbstractFileContentParser {
	private Log log=RetrievalUtil.getLog(this.getClass());
	
	public String getContent(RFileDocument document, String charsetName) {
		String content = "";
		String fileType = RetrievalUtil.getFileType(document.getFile().getName());
		String path = document.getFile().getAbsolutePath();
		if(fileType.equals("DOC"))
			content = ReadWordFile2003(path);
		else if(fileType.equals("DOCX"))
			content = ReadWordFile2007(path);
		return content;
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
