package frame.retrieval.engine.index.create.impl.file.parse;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;

import frame.base.core.util.io.InputStreamUtil;
import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * TXT文件内容解析器
 * @author 
 *
 */
public class TxtFileContentParser extends AbstractFileContentParser{
	private Log log=RetrievalUtil.getLog(this.getClass());
	
	public String getContent(RFileDocument document, String charsetName) {
		String content = "";
		InputStream fileInputStream = null;
		try{
			fileInputStream = new FileInputStream(document.getFile());
			content = InputStreamUtil.getContentsAsString(fileInputStream,charsetName);
			InputStreamUtil.closeInputStream(fileInputStream);
		}catch(Exception e){
			RetrievalUtil.errorLog(log, document.getFile().getAbsolutePath(),e);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (Exception e) {
					RetrievalUtil.errorLog(log, e);
				}
			}
		}
		return content;
	}

}
