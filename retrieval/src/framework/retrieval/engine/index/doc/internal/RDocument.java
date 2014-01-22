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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.base.snoic.base.util.DateTime;
import framework.base.snoic.base.util.StringClass;
import framework.base.snoic.base.util.UtilTool;
import framework.retrieval.engine.RetrievalConstant;
import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.common.RetrievalUtil;

/**
 * Document
 * @author 
 *
 */
public abstract class RDocument implements Serializable{
	
	private static DateTime dateTime=new DateTime();

	/**
	 * 
	 */
	private static final long serialVersionUID = 7581436851401384279L;

	/**
	 * 索引路径类型
	 * 		设置了这个值之后，索引将被建立到对应的目录下
	 */
	private String indexPathType=null;
	
	/**
	 * 唯一标志
	 */
	private String id=null;
	
	/**
	 * 所有内容
	 */
	private String fullContent="";
	
	/**
	 * 是否将所有内容组合成一个字段存放
	 */
	private boolean fullContentFlag=false;
	
	/**
	 * 索引信息分类标志
	 * 		如：知识库、信息发布、停电信息
	 */
	private String indexInfoType="";
	
	/**
	 * 类型
	 * 		如：数据库、文本、文件
	 */
	private RetrievalType.RIndexSourceType sourceIndexType=RetrievalType.RIndexSourceType.T;
	
	/**
	 * 所有字段信息
	 */
	private Map<String,RDocItem> docItemMap=new HashMap<String,RDocItem>();
	
	/**
	 * 
	 * @param fullContentFlag	是否将所有内容组合成一个字段存放
	 */
	public RDocument(boolean fullContentFlag){
		this.fullContentFlag=fullContentFlag;
	}
	
	/**
	 * 获取索引目录类型
	 * @return
	 */
	public String getIndexPathType() {
		return indexPathType;
	}

	/**
	 * 设置索引目录类型
	 * @param indexPathType
	 */
	public void setIndexPathType(String indexPathType) {
		this.indexPathType = indexPathType.toUpperCase();
	}

	/**
	 * 获取索引唯一ID
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置索引唯一ID
	 * 		对应 Lucene 中的 UN_TOKENIZED 类型,可以被搜索
	 * @param id
	 */
	public void setId(String id) {
		RDocItem docItem=new RDocItem();
		docItem.setItemType(RetrievalType.RDocItemType.KEYWORD);
		docItem.setContent(id);
		docItem.setName(RetrievalType.RDocItemSpecialName._IID);
		putItem(docItem);
	}

	/**
	 * 获取索引信息分类
	 * @return
	 */
	public String getIndexInfoType() {
		return indexInfoType;
	}

	/**
	 * 设置索引信息分类
	 * 		对应 Lucene 中的 UN_TOKENIZED 类型,可以被搜索
	 * @param indexInfoType
	 */
	public void setIndexInfoType(String indexInfoType) {
		this.indexInfoType = indexInfoType;
		RDocItem docItem=new RDocItem();
		docItem.setItemType(RetrievalType.RDocItemType.KEYWORD);
		docItem.setContent(indexInfoType);
		docItem.setName(RetrievalType.RDocItemSpecialName._IBT);
		putItem(docItem);
	}

	/**
	 * 获取索引来源类型
	 * @return
	 */
	public RetrievalType.RIndexSourceType getSourceIndexType() {
		return sourceIndexType;
	}

	/**
	 * 设置索引来源类型
	 * 		对应 Lucene 中的 UN_TOKENIZED 类型,可以被搜索
	 * @param sourceIndexType
	 */
	protected void setSourceIndexType(RetrievalType.RIndexSourceType sourceIndexType) {
		this.sourceIndexType = sourceIndexType;
		RDocItem docItem=new RDocItem();
		docItem.setItemType(RetrievalType.RDocItemType.KEYWORD);
		docItem.setContent(sourceIndexType);
		docItem.setName(RetrievalType.RDocItemSpecialName._IST);
		putItem(docItem);
	}

	/**
	 * 设置日期类型索引内容
	 * 		对应 Lucene 中的 UN_TOKENIZED 类型,可以被搜索
	 * @param property
	 */
	public void addDateProperty(RDocItem date){
		date.setItemType(RetrievalType.RDocItemType.DATE);
		fullContent+=date.getContent();
		putItem(date);
	}

	/**
	 * 设置数值类型索引内容
	 * 		对应 Lucene 中的 UN_TOKENIZED 类型,可以被搜索
	 * @param property
	 */
	public void addNumberProperty(RDocItem number){
		number.setItemType(RetrievalType.RDocItemType.NUMBER);
		fullContent+=number.getContent();
		putItem(number);
	}

	/**
	 * 设置只能被精确搜索的索引属性内容
	 * 		对应 Lucene 中的 UN_TOKENIZED 类型,可以被搜索
	 * @param property
	 */
	public void addUnTokenizedProperty(RDocItem key){
		key.setItemType(RetrievalType.RDocItemType.KEYWORD);
		fullContent+=key.getContent();
		putItem(key);
	}

	/**
	 * 设置索引属性内容
	 * 		对应 Lucene 中的 UN_TOKENIZED 类型,并且不能被搜索
	 * @param property
	 */
	public void addUnTokenizedStoreOnlyProperty(RDocItem key){
		key.setItemType(RetrievalType.RDocItemType.STORE_ONLY);
		putItem(key);
	}

	/**
	 * 设置可以被模糊搜索的索引属性内容
	 * 		对应 Lucene 中的 TOKENIZED 类型,可以被搜索
	 * @param property
	 */
	public void addProperty(RDocItem property) {
		property.setItemType(RetrievalType.RDocItemType.PROPERTY);
		fullContent+=property.getContent();
		putItem(property);
	}

	/**
	 * 设置可以被模糊搜索的索引内容
	 * 		对应 Lucene 中的 TOKENIZED 类型,可以被搜索
	 * @param content
	 */
	public void addContent(RDocItem content) {
		content.setItemType(RetrievalType.RDocItemType.CONTENT);
		fullContent+=content.getContent();
		putItem(content);
	}

	/**
	 * 获取所有索引内容
	 * @return
	 */
	public String getFullContent() {
		return fullContent;
	}
	
	/**
	 * 获取某个索引字段
	 * @param name
	 * @return
	 */
	public RDocItem getDocItem(Object name){
		return docItemMap.get(String.valueOf(name));
	}
	
	/**
	 * 生成一个唯一索引ID
	 */
	public void createId(){
		setId(RetrievalUtil.getIndexId());
	}

	/**
	 * 获取所有索引字段
	 * @return
	 */
	private Map<String, RDocItem> getDocItemMap() {

		RDocItem docItemFullContent=new RDocItem();
		docItemFullContent.setItemType(RetrievalType.RDocItemType.CONTENT);
		if(fullContentFlag){
			docItemFullContent.setContent(fullContent);
		}else{
			docItemFullContent.setContent("");
		}
		docItemFullContent.setName(RetrievalType.RDocItemSpecialName._IAC);
		putItem(docItemFullContent);
		
		String creatTime=dateTime.getNowDateTime();
		
		RDocItem docItem=new RDocItem();
		docItem.setItemType(RetrievalType.RDocItemType.DATE);
		docItem.setContent(creatTime);
		docItem.setName(RetrievalType.RDocItemSpecialName._IC);
		putItem(docItem);

//		RDocItem hitsDocItem=new RDocItem();
//		hitsDocItem.setItemType(RetrievalType.RDocItemType.NUMBER);
//		hitsDocItem.setContent("0");
//		hitsDocItem.setName(RetrievalType.RDocItemSpecialName.R_DOC_ITEM_SPECIAL_NAME_HITS);
//		putItem(hitsDocItem);
		
		return docItemMap;
	}
	
	/**
	 * 获取所有索引字段
	 * @return
	 */
	public List<RDocItem> getDocItemList(){
		List<RDocItem> docItemList=new ArrayList<RDocItem>();
		String fieldNames="";
		
		Map<String, RDocItem> map=getDocItemMap();
		if(map==null || map.size()<=0){
			return docItemList;
		}
		
		Object[][] objects=UtilTool.getMapKeyValue(map);
		
		int length=objects.length;
		for(int i=0;i<length;i++){
			RDocItem docItem=(RDocItem)objects[i][1];
			docItemList.add(docItem);
			fieldNames+=StringClass.getString(objects[i][0]).toUpperCase()+RetrievalConstant.DEFAULT_INDEX_FIELD_NAME_SPLIT;
		}
		
		RDocItem allFieldRDocItem=new RDocItem();
		allFieldRDocItem.setName(RetrievalType.RDocItemSpecialName._IAF);
		allFieldRDocItem.setContent(fieldNames);
		allFieldRDocItem.setItemType(RetrievalType.RDocItemType.STORE_ONLY);
		
		docItemList.add(allFieldRDocItem);
		
		return docItemList;
	}
	
	private void putItem(RDocItem docItem){
		docItemMap.put(docItem.getName(), docItem);
	}
}
