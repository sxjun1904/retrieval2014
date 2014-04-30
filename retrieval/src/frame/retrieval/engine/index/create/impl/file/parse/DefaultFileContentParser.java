package frame.retrieval.engine.index.create.impl.file.parse;

import frame.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * 默认的文件内容读取器,当文件对所有的解析器类型都匹配不上时,就使用这个默认的内容解析器
 * @author 
 *
 */
public class DefaultFileContentParser extends AbstractFileContentParser{

	public String getContent(RFileDocument document, String charsetName) {
		return "";
	}

}
