package frame.retrieval.engine.index.create.impl.file.parse;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.commons.logging.Log;

import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * RTF文件内容解析器
 * @author 
 *
 */
public class RTFFileContentParser extends AbstractFileContentParser{
	private Log log=RetrievalUtil.getLog(this.getClass());
	
	public String getContent(RFileDocument document, String charsetName) {
		String content = "";
		InputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(document.getFile());
			DefaultStyledDocument styledDoc = new DefaultStyledDocument();
			RTFEditorKit rtfEditorKit=new RTFEditorKit();
			rtfEditorKit.read(fileInputStream, styledDoc, 0);
			content = styledDoc.getText(0, styledDoc.getLength());
		} catch (Exception e) {
			RetrievalUtil.errorLog(log, document.getFile().getAbsolutePath(),e);
		} finally {
	    	try{
	    		if(fileInputStream!=null){
	    			fileInputStream.close();
	    		}
	    	}catch(Exception e){
	    		RetrievalUtil.errorLog(log, e);
	    	}
		}
		return content;
	}


}
