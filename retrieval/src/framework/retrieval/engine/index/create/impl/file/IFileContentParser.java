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
package framework.retrieval.engine.index.create.impl.file;

import framework.retrieval.engine.index.doc.file.internal.RFileDocument;

/**
 * 文件内容解析接口
 * @author 
 *
 */
public interface IFileContentParser {
	
	/**
	 * 使用默认的字符集解析文件内容
	 * @param document
	 * @param maxFileSize	被索引的最大文件大小，超过这个大小的文件，内容将不做解析
	 */
	public void parse(RFileDocument document,long maxFileSize);
	
	/**
	 * 使用特定的字符集解析文件内容
	 * @param document		被索引的文件索引对象
	 * @param charsetName	被索引的文件字符集
	 * @param maxFileSize	被索引的最大文件大小，超过这个大小的文件，内容将不做解析
	 */
	public void parse(RFileDocument document,String charsetName,long maxFileSize);
	
}
