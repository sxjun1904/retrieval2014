package frame.retrieval.engine.index.create.impl.file.parse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * PDF文件内容解析器
 * @author 沈晓军 2014-5-16
 *
 */
public class PDFFileContentParser extends AbstractFileContentParser {
	private Log log=RetrievalUtil.getLog(this.getClass());
	
	public String getContent(RFileDocument document, String charsetName) {
		// 是否排序    
		  boolean sort = false;    
		  // pdf文件名    
		  String pdfFile = document.getFile().getAbsolutePath();    
		  /*// 输入文本文件名称    
		  String textFile = null;    
		  // 编码方式    
		  String encoding = "UTF-8"; */   
		  // 开始提取页数    
		  int startPage = 1;    
		  // 结束提取页数    
		  int endPage = Integer.MAX_VALUE;    
		  // 文件输入流，生成文本文件    
//		  Writer output = null;    
		  // 内存中存储的PDF Document    
		  PDDocument doc = null;  
		  
		  String content = "";
		 try {    
			   try {    
			    // 首先当作一个URL来装载文件，如果得到异常再从本地文件系统//去装载文件    
			    URL url = new URL(pdfFile);    
			   //注意参数已不是以前版本中的URL.而是File。    
			    doc = PDDocument.load(pdfFile);    
			    // 获取PDF的文件名    
			    String fileName = url.getFile();    
			    // 以原来PDF的名称来命名新产生的txt文件    
			    /*if (fileName.length() > 4) {    
			     File outputFile = new File(fileName.substring(0, fileName.length() - 4)+ ".txt");    
			     textFile = outputFile.getName();    
			    }    */
			   } catch (Exception e) {    
				   // 如果作为URL装载得到异常则从文件系统装载    
				   //注意参数已不是以前版本中的URL.而是File。    
				    try {
						doc = PDDocument.load(pdfFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}    
				    /*if (pdfFile.length() > 4) {    
				     textFile = pdfFile.substring(0, pdfFile.length() - 4)+ ".txt";    
				    } */   
			   }    
			   // 文件输入流，写入文件倒textFile    
//			   output = new OutputStreamWriter(new FileOutputStream(textFile), encoding);    
			   // PDFTextStripper来提取文本    
			   PDFTextStripper stripper = null;    
			   stripper = new PDFTextStripper();    
			   // 设置是否排序    
			   stripper.setSortByPosition(sort);    
			   // 设置起始页    
			   stripper.setStartPage(startPage);    
			   // 设置结束页    
			   stripper.setEndPage(endPage);    
			   // 调用PDFTextStripper的writeText提取并输出文本    
//			   stripper.writeText(doc, output);  
			   content = stripper.getText(doc);
			  }catch(Exception e){
				  e.printStackTrace();
			  } finally {    
				   try {
					  /* if (output != null) {    
						    output.close();  // 关闭输出流  
					   } */   
					   if (doc != null) {    
						    doc.close(); // 关闭PDF Document    
					   }
					} catch (IOException e) {
						e.printStackTrace();
					}    
			  }    
		return content;
	}

}
