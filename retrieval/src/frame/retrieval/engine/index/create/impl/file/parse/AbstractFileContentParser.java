package frame.retrieval.engine.index.create.impl.file.parse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;

import frame.base.core.util.DateTime;
import frame.base.core.util.StringClass;
import frame.base.core.util.file.FileHelper;
import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.index.create.impl.file.IFileContentParser;
import frame.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * 文件内容解析抽象类
 * @author 
 *
 */
public abstract class AbstractFileContentParser implements IFileContentParser{
	private Log log=RetrievalUtil.getLog(this.getClass());
	protected static FileHelper fileHelper = new FileHelper();
	protected static DateTime dateTime=new DateTime();
	
	public void parse(RFileDocument document,long maxFileSize) {
		parse(document, null,maxFileSize);
	}

	public void parse(RFileDocument document, String charsetName,long maxFileSize) {
		parseBaseInfo(document, charsetName);
		File file=document.getFile();
		
		long fileSize=file.length();
		
		//如果文件大小超过最大值，则不解析文件内容
		if(fileSize>maxFileSize){
			return;
		}
		
		try{
			String content=getContent(document, charsetName);

			if(content!=null && !content.equals("")){
//				content=content.replaceAll("[\\s<!(&|&nbsp;)>\\-,'\"\\.]", "");
				content=content.replaceAll("[<(&|&nbsp;)>]", "");
				content=content.replaceAll("'", "‘");
				content=content.replaceAll("\"", "“");
			}
			
			document.setFileContent(content);
		}catch(Exception e){
			RetrievalUtil.errorLog(log,e);
		}
	}
	
	/**
	 * 生成文件基本信息
	 * @param document
	 * @param charsetName
	 */
	protected void parseBaseInfo(RFileDocument document, String charsetName){
		
		long lastModifyd=document.getFile().lastModified();
		
		Date date=new Date(lastModifyd);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		String fileModify=dateFormat.format(date);
		
		String absolutePath=StringClass.getFormatPath(document.getFile().getAbsolutePath());
		String basePath=StringClass.getFormatPath(document.getBasePath());
		String relativizePath=StringClass.getReplaceString(absolutePath, basePath, "");
		relativizePath=StringClass.getFormatPath("/"+relativizePath);

		document.setFileName(document.getFile().getName());
		document.setFileRelativePath(relativizePath);
		document.setFileModify(fileModify);
	}

	/**
	 * 获取文件内容
	 * @param document
	 * @param charsetName
	 * @return
	 */
	public abstract String getContent(RFileDocument document,String charsetName);
	
		
}
