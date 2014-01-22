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

import java.io.File;

import framework.retrieval.engine.RetrievalConstant;
import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * 将文件内容组织生成索引文档
 * @author 
 *
 */
public class RFileDocumentMakeup {
	
	private RFileDocument documnet;
	private IFileContentParserManager fileContentParserManager;
	private long maxFileSize=RetrievalConstant.DEFAULT_INDEX_MAX_FILE_SZIE;
	
	public RFileDocumentMakeup(IFileContentParserManager fileContentParserManager,RFileDocument documnet,long maxFileSize){
		this.fileContentParserManager=fileContentParserManager;
		this.documnet=documnet;
		this.maxFileSize=maxFileSize;
	}

	/**
	 * 获取待解析文档
	 * @return
	 */
	public RFileDocument getDocumnet() {
		return documnet;
	}
	
	/**
	 * 组织文件内容索引
	 */
	public RFileDocument makeup(){
		File file=documnet.getFile();
		String fileType=RetrievalUtil.getFileType(file.getName());
		
		IFileContentParser fileContentParser=fileContentParserManager.getFileContentParser(fileType);
		fileContentParser.parse(documnet,documnet.getCharsetName(),maxFileSize);
		return documnet;
	}
	
}
