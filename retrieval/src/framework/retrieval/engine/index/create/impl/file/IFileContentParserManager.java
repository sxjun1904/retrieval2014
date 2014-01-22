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

/**
 * 文件内容解析管理器
 * @author 
 *
 */
public interface IFileContentParserManager {

	/**
	 * 注册文件内容解析器
	 * @param fileType
	 * @param fileContentParser
	 */
	public void regFileContentParser(String fileType,IFileContentParser fileContentParser);
	

	/**
	 * 移除文件内容解析器
	 * @param fileType
	 */
	public void removeFileContentParser(String fileType);
	

	/**
	 * 移除所有文件内容解析器
	 */
	public void removeAllFileContentParser();
	

	/**
	 * 获取文件内容解析器
	 * @param fileType
	 * @return
	 */
	public IFileContentParser getFileContentParser(String fileType);
	

	/**
	 * 获取默认文件内容解析器,当文件类型没有对应的解析器时，默认使用该解析器进行解析
	 * @return
	 */
	public IFileContentParser getDefaultFileContentParser();
	

	/**
	 * 设置默认文件内容解析器,当文件类型没有对应的解析器时，默认使用该解析器进行解析
	 * @param defaultFileContentParser
	 */
	public void setDefaultFileContentParser(
			IFileContentParser defaultFileContentParser);
	
	
}
