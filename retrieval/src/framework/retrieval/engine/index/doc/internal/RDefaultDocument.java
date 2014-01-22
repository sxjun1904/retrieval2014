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
package framework.retrieval.engine.index.doc.internal;

import java.io.Serializable;

import framework.retrieval.engine.RetrievalType;

/**
 * 普通类型Document
 * @author 
 *
 */
public class RDefaultDocument extends RDocument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3737671086618412214L;

	public RDefaultDocument(boolean fullContentFlag){
		super(fullContentFlag);
		//设置索引来源为文本类型
		setSourceIndexType(RetrievalType.RIndexSourceType.T);
	}
	
	/**
	 * 设置日期类型索引属性内容
	 * @param itemType
	 * @param content
	 */
	protected void setDatePropertieField(Object itemType,String content){
		RDocItem docItem=new RDocItem();
		docItem.setName(itemType);
		docItem.setContent(content);
		addDateProperty(docItem);
	}
	
	
	/**
	 * 设置数值类型索引属性内容
	 * @param itemType
	 * @param content
	 */
	protected void setNumberPropertieField(Object itemType,String content){
		RDocItem docItem=new RDocItem();
		docItem.setName(itemType);
		docItem.setContent(content);
		addNumberProperty(docItem);
	}
	
	
	/**
	 * 设置索引属性内容
	 * @param itemType
	 * @param content
	 */
	protected void setPropertieField(Object itemType,String content){
		RDocItem docItem=new RDocItem();
		docItem.setName(itemType);
		docItem.setContent(content);
		addProperty(docItem);
	}
	
	/**
	 * 设置索引属性内容
	 * @param itemType
	 * @param content
	 */
	protected void setUnTokenizedPropertyField(Object itemType,String content){
		RDocItem docItem=new RDocItem();
		docItem.setName(itemType);
		docItem.setContent(content);
		addUnTokenizedProperty(docItem);
	}
	
	/**
	 * 设置索引属性内容
	 * @param itemType
	 * @param content
	 */
	protected void setUnTokenizedStoreOnlyPropertyField(Object itemType,String content){
		RDocItem docItem=new RDocItem();
		docItem.setName(itemType);
		docItem.setContent(content);
		addUnTokenizedStoreOnlyProperty(docItem);
	}
	
	/**
	 * 设置索引文本内容
	 * @param itemType
	 * @param content
	 */
	protected void setContentField(Object itemType,String content){
		RDocItem docItem=new RDocItem();
		docItem.setName(itemType);
		docItem.setContent(content);
		addContent(docItem);
	}
	
	
	/**
	 * 获取索引字段内容
	 * @param itemType
	 * @return
	 */
	protected String getField(Object itemType){
		RDocItem docItem=getDocItem(itemType);
		if(docItem==null){
			return null;
		}else{
			return docItem.getContent();
		}
	}
	
	public String toString(){
		return this.getClass()+"@"+this.getId()+"["+getSourceIndexType()+"]";
	}
	
}
