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

import framework.retrieval.engine.index.doc.file.internal.RFileDocument;

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
