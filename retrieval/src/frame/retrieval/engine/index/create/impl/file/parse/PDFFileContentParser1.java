package frame.retrieval.engine.index.create.impl.file.parse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * PDF文件内容解析器
 * @author sxjun 停用
 *
 */
public class PDFFileContentParser1 extends AbstractFileContentParser {
	private Log log=RetrievalUtil.getLog(this.getClass());
	
	public String getContent(RFileDocument document, String charsetName) {
		String content = "";
		InputStream fileInputStream = null;
		COSDocument cosDoc = null;
		try {
			fileInputStream = new FileInputStream(document.getFile());
			PDFParser parser = new PDFParser(fileInputStream);
			parser.parse();
			cosDoc = parser.getDocument();
			if (!cosDoc.isEncrypted()) {
				PDFTextStripper stripper = new PDFTextStripper();
				content = stripper.getText(new PDDocument(cosDoc));
			}
		} catch (Exception e) {
			RetrievalUtil.errorLog(log, document.getFile().getAbsolutePath(),e);
		} finally {
			if (cosDoc != null) {
				try {
					cosDoc.close();
				} catch (IOException e) {

				}
			}
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
