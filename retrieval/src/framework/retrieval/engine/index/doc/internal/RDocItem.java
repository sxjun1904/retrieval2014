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

import framework.base.snoic.base.util.StringClass;
import framework.retrieval.engine.RetrievalType;

/**
 * 待索引文档中的字段
 * @author 
 *
 */
public class RDocItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4327254469035911247L;

	/**
	 * 字段名称
	 */
	private String name;
	
	/**
	 * 字段内容
	 */
	private String content;
	
	/**
	 * 字段类型
	 */
	private RetrievalType.RDocItemType itemType;

	/**
	 * 获取字段名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置字段名称,全部以大写字母的形式保存
	 * @param name
	 */
	public void setName(Object name) {
		this.name = StringClass.getString(name).toUpperCase();
	}

	/**
	 * 获取内容
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * @param content
	 */
	public void setContent(Object content) {
		this.content = StringClass.getString(content);
	}

	/**
	 * 获取索引字段类型
	 * @return
	 */
	public RetrievalType.RDocItemType getItemType() {
		return itemType;
	}
	
	/**
	 * 设置索引字段类型
	 * @param itemType
	 */
	void setItemType(RetrievalType.RDocItemType itemType) {
		this.itemType = itemType;
	}

	public String toString(){
		return this.getClass()+"@"+this.getName()+"|"+this.getContent();
	}
	
}
