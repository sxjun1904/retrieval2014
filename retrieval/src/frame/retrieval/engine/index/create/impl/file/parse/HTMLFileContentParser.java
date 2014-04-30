package frame.retrieval.engine.index.create.impl.file.parse;

import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * HTML文件内容解析器
 * @author 
 *
 */
public class HTMLFileContentParser extends AbstractFileContentParser{
	
	public String getContent(RFileDocument document, String charsetName){
		
    	String content=fileHelper.fileToString(document.getFile().getAbsolutePath());
    	
    	content=RetrievalUtil.parseHTML(content, charsetName);
    	
    	return content;
    	
	}

}
