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
package framework.retrieval.engine.index.create.impl.file;

import java.util.HashMap;
import java.util.Map;

import framework.retrieval.engine.index.create.impl.file.parse.DefaultFileContentParser;
import framework.retrieval.engine.index.create.impl.file.parse.ExcelFileContentParser;
import framework.retrieval.engine.index.create.impl.file.parse.HTMLFileContentParser;
import framework.retrieval.engine.index.create.impl.file.parse.PDFFileContentParser;
import framework.retrieval.engine.index.create.impl.file.parse.RTFFileContentParser;
import framework.retrieval.engine.index.create.impl.file.parse.TxtFileContentParser;
import framework.retrieval.engine.index.create.impl.file.parse.WordFileContentParser;

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
