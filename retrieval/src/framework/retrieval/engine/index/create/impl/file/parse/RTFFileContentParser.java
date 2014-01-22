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
import java.io.InputStream;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.commons.logging.Log;

import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.index.doc.file.internal.RFileDocument;

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
