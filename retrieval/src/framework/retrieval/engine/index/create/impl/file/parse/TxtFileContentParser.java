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

import org.apache.commons.logging.Log;

import framework.base.snoic.base.util.io.InputStreamUtil;
import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.index.doc.file.internal.RFileDocument;

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
