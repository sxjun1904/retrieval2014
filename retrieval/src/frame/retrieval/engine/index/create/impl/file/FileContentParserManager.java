package frame.retrieval.engine.index.create.impl.file;

import java.util.HashMap;
import java.util.Map;

import frame.retrieval.engine.index.create.impl.file.parse.DefaultFileContentParser;
import frame.retrieval.engine.index.create.impl.file.parse.ExcelFileContentParser;
import frame.retrieval.engine.index.create.impl.file.parse.HTMLFileContentParser;
import frame.retrieval.engine.index.create.impl.file.parse.PDFFileContentParser;
import frame.retrieval.engine.index.create.impl.file.parse.RTFFileContentParser;
import frame.retrieval.engine.index.create.impl.file.parse.TxtFileContentParser;
import frame.retrieval.engine.index.create.impl.file.parse.WordFileContentParser;

/**
 * 
 * @author 
 *
 */
public class FileContentParserManager implements IFileContentParserManager{
	private IFileContentParser defaultFileContentParser=new DefaultFileContentParser();
	
	private Map<String,IFileContentParser> parseMap=new HashMap<String,IFileContentParser>();
	
	public FileContentParserManager(){
		parseMap.put("TXT", new TxtFileContentParser());
		parseMap.put("SQL", new TxtFileContentParser());
		parseMap.put("XML", new HTMLFileContentParser());
		parseMap.put("HTML", new HTMLFileContentParser());
		parseMap.put("HTM", new HTMLFileContentParser());
		parseMap.put("PDF", new PDFFileContentParser());
		parseMap.put("RTF", new RTFFileContentParser());
		parseMap.put("DOC", new WordFileContentParser());
		parseMap.put("XLS", new ExcelFileContentParser());
	}
	
	/**
	 * 注册文件内容解析器
	 * @param fileType
	 * @param fileContentParser
	 */
	public void regFileContentParser(String fileType,IFileContentParser fileContentParser){
		parseMap.put(fileType.toUpperCase(), fileContentParser);
	}
	
	/**
	 * 移除文件内容解析器
	 * @param fileType
	 */
	public void removeFileContentParser(String fileType){
		parseMap.remove(fileType.toUpperCase());
	}
	
	/**
	 * 移除所有文件内容解析器
	 */
	public void removeAllFileContentParser(){
		parseMap.clear();
	}
	
	/**
	 * 获取文件内容解析器
	 * @param fileType
	 * @return
	 */
	public IFileContentParser getFileContentParser(String fileType){
		IFileContentParser fileContentParser=parseMap.get(fileType);
		if(fileContentParser==null){
			fileContentParser=defaultFileContentParser;
		}
		return fileContentParser;
	}

	/**
	 * 获取默认文件内容解析器
	 * @return
	 */
	public IFileContentParser getDefaultFileContentParser() {
		return defaultFileContentParser;
	}

	/**
	 * 设置默认文件内容解析器
	 * @param defaultFileContentParser
	 */
	public void setDefaultFileContentParser(
			IFileContentParser defaultFileContentParser) {
		this.defaultFileContentParser = defaultFileContentParser;
	}
	
	
}
