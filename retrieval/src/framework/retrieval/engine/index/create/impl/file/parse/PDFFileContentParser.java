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
package framework.retrieval.engine.index.create.impl.file.parse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * PDF文件内容解析器
 * @author 
 *
 */
public class PDFFileContentParser extends AbstractFileContentParser {
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
