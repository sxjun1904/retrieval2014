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
package framework.retrieval.engine.index.doc;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.index.doc.internal.RDefaultDocument;
import framework.retrieval.engine.index.doc.internal.RDocItem;

/**
 * 索引Document
 * @author 
 *
 */
public abstract class AbstractIndexDocument {
	
	public abstract RDefaultDocument getRDocument();
	
	/**
	 * 生成一个唯一索引ID
	 */
	public void createId(){
		getRDocument().createId();
	}
	
	/**
	 * 设置唯一索引ID
	 * @param id
	 */
	public void setId(String id){
		getRDocument().setId(id);
	}
	
	/**
	 * 获取唯一索引ID
	 * @param id
	 * @return
	 */
	public String getId(String id){
		return getRDocument().getId();
	}
	
	/**
	 * 获取索引信息分类
	 * 		如：知识库，信息发布，停电信息等
	 * @return
	 */
	public String getIndexInfoType() {
		return getRDocument().getIndexInfoType();
	}

	/**
	 * 设置索引信息分类
	 * 		如：知识库，信息发布，停电信息等
	 * @param indexInfoType
	 */
	public void setIndexInfoType(String indexInfoType){
		getRDocument().setIndexInfoType(indexInfoType);
	}

	/**
	 * 获取索引目录类型
	 * @return
	 */
	public String getIndexPathType() {
		return getRDocument().getIndexPathType();
	}

	/**
	 * 设置索引目录类型
	 * @param indexPathType
	 */
	public void setIndexPathType(String indexPathType) {
		getRDocument().setIndexPathType(indexPathType);
	}
	
	/**
	 * 获取索引来源类型
	 * @return
	 */
	public RetrievalType.RIndexSourceType getSourceIndexType(){
		return getRDocument().getSourceIndexType();
	}
	
	/**
	 * 设置关键字
	 * @param keyWord
	 */
	public void addKeyWord(RDocItem keyWord) {
		getRDocument().addUnTokenizedProperty(keyWord);
	}

	/**
	 * 设置只能被精确搜索的索引属性内容
	 * @param property
	 */
	public void addUnTokenizedProperty(RDocItem key){
		getRDocument().addUnTokenizedProperty(key);
	}

	/**
	 * 设置只存储，而不能被搜索出的属性内容
	 * @param property
	 */
	public void addUnTokenizedStoreOnlyProperty(RDocItem key){
		getRDocument().addUnTokenizedStoreOnlyProperty(key);
	}

	/**
	 * 设置可以被模糊搜索的索引内容
	 * @param content
	 */
	public void addContent(RDocItem content) {
		getRDocument().addContent(content);
	}
	
	/**
	 * 设置日期类型索引内容
	 * @param content
	 */
	public void addDateProperty(RDocItem content) {
		getRDocument().addDateProperty(content);
		
	}
	
	/**
	 * 设置数字类型索引内容
	 * @param content
	 */
	public void addNumberProperty(RDocItem content) {
		getRDocument().addNumberProperty(content);
	}	
	
}
