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
package framework.retrieval.engine.index.create.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

import framework.base.snoic.base.util.UtilTool;
import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.analyzer.IRAnalyzerFactory;
import framework.retrieval.engine.context.DefaultRetrievalProperties;
import framework.retrieval.engine.context.LuceneProperties;
import framework.retrieval.engine.index.create.IRIndexWriter;
import framework.retrieval.engine.index.create.IRWriteDocument;
import framework.retrieval.engine.index.doc.internal.RDocItem;
import framework.retrieval.engine.index.doc.internal.RDocument;

/**
 * 
 * @author 
 *
 */
public class RWriteDocument implements IRWriteDocument{
	
	private List<RDocument> documents;
	
	private Map<String,List<RDocument>> documentsMap=new Hashtable<String,List<RDocument>>();

	private IRAnalyzerFactory analyzerFactory=null;
	private LuceneProperties luceneProperties=null;
	
	private DefaultRetrievalProperties defaultRetrievalProperties = null;
	
	public RWriteDocument(IRAnalyzerFactory analyzerFactory,LuceneProperties luceneProperties){
		this.analyzerFactory=analyzerFactory;
		this.luceneProperties=luceneProperties;
	}
	
	public RWriteDocument(IRAnalyzerFactory analyzerFactory,LuceneProperties luceneProperties,DefaultRetrievalProperties defaultRetrievalProperties){
		this.analyzerFactory=analyzerFactory;
		this.luceneProperties=luceneProperties;
		this.defaultRetrievalProperties = defaultRetrievalProperties;
	}
	
	/**
	 * 设置一批需要写入的Document
	 */
	public void setDocument(List<RDocument> documents) {
		this.documents=documents;
	}

	/**
	 * 组织索引内容，并重新生成Lucene格式的Document
	 * @param document
	 * @return
	 */
	private Document makeupLuceneDocument(RDocument document) {
		Document luceneDocument = new Document();
		List<RDocItem> docItemList=document.getDocItemList();
		if(docItemList==null || docItemList.size()<=0){
			return null;
		}
		int length=docItemList.size();
		for(int i=0;i<length;i++){
			Field field=createField(docItemList.get(i));
			luceneDocument.add(field);
		}
		
		return luceneDocument;
	}
	
	/**
	 * 创建索引字段
	 * @param docItem
	 * @return
	 */
	private Field createField(RDocItem docItem){

		Field field=null;
		
		if(docItem.getItemType()==RetrievalType.RDocItemType.KEYWORD){
			field=new Field(docItem.getName(),docItem.getContent(),Field.Store.YES,Field.Index.NOT_ANALYZED);
		}else if(docItem.getItemType()==RetrievalType.RDocItemType.DATE){
			field=new Field(docItem.getName(),docItem.getContent(),Field.Store.YES,Field.Index.NOT_ANALYZED);
		}else if(docItem.getItemType()==RetrievalType.RDocItemType.NUMBER){
			field=new Field(docItem.getName(),docItem.getContent(),Field.Store.YES,Field.Index.NOT_ANALYZED);
		}else if(docItem.getItemType()==RetrievalType.RDocItemType.STORE_ONLY){
			field=new Field(docItem.getName(),docItem.getContent(),Field.Store.YES,Field.Index.NO);
		}else if(docItem.getItemType()==RetrievalType.RDocItemType.PROPERTY){
			field=new Field(docItem.getName(),docItem.getContent(),Field.Store.YES,Field.Index.ANALYZED);
		}else if(docItem.getItemType()==RetrievalType.RDocItemType.CONTENT){
			field=new Field(docItem.getName(),docItem.getContent(),Field.Store.YES,Field.Index.ANALYZED);
		}
		
		return field;
	}
	
	/**
	 * 分解所有的RDocument
	 */
	private void splitDocument(){
		
		int length=documents.size();
		for(int i=0;i<length;i++){
			RDocument document=documents.get(i);
			String indexPatyType=document.getIndexPathType();
			List<RDocument> list=documentsMap.get(indexPatyType);
			if(list==null){
				list=new ArrayList<RDocument>();
			}
			list.add(document);
			documentsMap.put(indexPatyType.toUpperCase(), list);
		}
		
	}
	
	/**
	 * 将某种类型的文档写入索引
	 * @param indexPathType
	 * @param documents
	 */
	private void writeDocument(String indexPathType,List<RDocument> documents){
		writeDocument(indexPathType,documents,null);
	}
	
	private void writeDocument(String indexPathType,List<RDocument> documents,IndexWriter _indexWriter){
		if(documents==null || documents.size()<=0){
			return;
		}

		int length=documents.size();
		
		IRIndexWriter indexWriter=new RIndexWriter(analyzerFactory,luceneProperties,defaultRetrievalProperties,indexPathType);
		List<Document> luceneDocuments=new ArrayList<Document>();

		for(int i=0;i<length;i++){
			RDocument document=documents.get(i);
			luceneDocuments.add(makeupLuceneDocument(document));
		}
		if(_indexWriter!=null)
			indexWriter.addDocumentNotClose(luceneDocuments,_indexWriter);
		else
			indexWriter.addDocument(luceneDocuments);
	}
	
	/**
	 * 将某种类型的文档写入索引
	 * @param indexPathType
	 * @param documents
	 */
	private void writeFileDocument(String indexPathType,List<RDocument> documents){
		if(documents==null || documents.size()<=0){
			return;
		}

		int length=documents.size();

		IRIndexWriter indexWriter=new RIndexWriter(analyzerFactory,luceneProperties,indexPathType);
		List<Document> luceneDocuments=new ArrayList<Document>();

		for(int i=0;i<length;i++){
			RDocument document=documents.get(i);
			luceneDocuments.add(makeupLuceneDocument(document));
		}
		
		indexWriter.addDocumentNowRamSupport(luceneDocuments);
	}

	/**
	 * 写入索引
	 */
	@SuppressWarnings("unchecked")
	public void writeDocument() {
		writeDocument(null);
	}
	
	public void writeDocument(IndexWriter indexWriter) {
		if(documents==null || documents.size()<=0){
			return;
		}
		
		splitDocument();
		
		Object[][] objects=UtilTool.getMapKeyValue(documentsMap);
		
		int length=objects.length;
		
		for(int i=0;i<length;i++){
			String indexPathType=(String)objects[i][0];
			List<RDocument> documents=(List<RDocument>)objects[i][1];
			writeDocument(indexPathType.toUpperCase(),documents,indexWriter);
		}
		
	}

	/**
	 * 写入索引
	 */
	@SuppressWarnings("unchecked")
	public void writeFileDocument() {
		if(documents==null || documents.size()<=0){
			return;
		}
		
		splitDocument();
		
		Object[][] objects=UtilTool.getMapKeyValue(documentsMap);
		
		int length=objects.length;
		
		for(int i=0;i<length;i++){
			String indexPathType=(String)objects[i][0];
			List<RDocument> documents=(List<RDocument>)objects[i][1];
			writeFileDocument(indexPathType.toUpperCase(),documents);
		}
		
	}
}
