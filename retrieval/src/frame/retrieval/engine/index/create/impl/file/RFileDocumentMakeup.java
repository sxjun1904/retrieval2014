package frame.retrieval.engine.index.create.impl.file;

import java.io.File;

import frame.retrieval.engine.RetrievalConstant;
import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.index.doc.file.internal.RFileDocument;

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
