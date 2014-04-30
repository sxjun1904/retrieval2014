package frame.retrieval.engine.index.create.impl.file.parse;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.poi.hwpf.extractor.WordExtractor;

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
		InputStream fileInputStream = null;
		WordExtractor wordExtractor=null;
		try {
			fileInputStream = new FileInputStream(document.getFile());
			
			wordExtractor=new WordExtractor(fileInputStream);

			content=wordExtractor.getText();

		} catch (Exception e) {
			RetrievalUtil.errorLog(log, document.getFile().getAbsolutePath(),e);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (Exception e) {

				}
			}
		}

		return content;
	}

}
