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
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.analyzer.IRAnalyzerFactory;
import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.context.DefaultRetrievalProperties;
import framework.retrieval.engine.context.LuceneProperties;
import framework.retrieval.engine.index.create.IRIndexOperator;
import framework.retrieval.engine.index.create.IRIndexWriter;
import framework.retrieval.engine.index.create.IRWriteDocument;
import framework.retrieval.engine.index.create.RetrievalDocumentException;
import framework.retrieval.engine.index.doc.internal.RDocument;

/**
 * 
 * @author 
 *
 */
public class RIndexOperator implements IRIndexOperator{
	private Log log=RetrievalUtil.getLog(this.getClass());
	
	private IRAnalyzerFactory analyzerFactory=null;
	private LuceneProperties luceneProperties=null;
	
	private DefaultRetrievalProperties defaultRetrievalProperties = null;
	
	public RIndexOperator(IRAnalyzerFactory analyzerFactory,LuceneProperties luceneProperties){
		new RIndexOperator(analyzerFactory,luceneProperties,null);
	}
	
	public RIndexOperator(IRAnalyzerFactory analyzerFactory,LuceneProperties luceneProperties,DefaultRetrievalProperties defaultRetrievalProperties){
		this.analyzerFactory=analyzerFactory;
		this.luceneProperties=luceneProperties;
		this.defaultRetrievalProperties = defaultRetrievalProperties;
	}

	public String addIndex(RDocument document) {

		if(document.getIndexPathType()==null){
			throw new RetrievalDocumentException("RDocument IndexPathType 属性不允许为空");
		}
		
		List<RDocument> documents=new ArrayList<RDocument>();
		documents.add(document);
		
		List<String> documentIds=addIndex(documents);
		
		if(documentIds==null || documentIds.size()<=0){
			return null;
		}else{
			return documentIds.get(0);
		}
		
	}

	public List<String> addIndex(List<RDocument> documents) {
		return addIndex(documents,null);
	}
	
	public List<String> addIndex(List<RDocument> documents,IndexWriter indexWriter) {
		if(documents==null || documents.size()<=0){
			return null;
		}
		List<String> documentIdList=new ArrayList<String>();
		IRWriteDocument writeDocument=new RWriteDocument(analyzerFactory,luceneProperties,defaultRetrievalProperties);
		int length=documents.size();
		for(int i=0;i<length;i++){

			if(documents.get(i).getIndexPathType()==null){
				throw new RetrievalDocumentException("RDocument IndexPathType 属性不允许为空");
			}
			
			documents.get(i).createId();
			documentIdList.add(documents.get(i).getId());
		}
		
		writeDocument.setDocument(documents);
		writeDocument.writeDocument(indexWriter);
		
		return documentIdList;
	}
	
	/**
	 * 将文档写入索引，并返回索引唯一ID
	 * @param documents
	 * @return
	 */
	public List<String> addFileIndex(List<RDocument> documents){
		if(documents==null || documents.size()<=0){
			return null;
		}
		List<String> documentIdList=new ArrayList<String>();
		IRWriteDocument writeDocument=new RWriteDocument(analyzerFactory,luceneProperties);
		int length=documents.size();
		for(int i=0;i<length;i++){

			if(documents.get(i).getIndexPathType()==null){
				throw new RetrievalDocumentException("RDocument IndexPathType 属性不允许为空");
			}
			
			documents.get(i).createId();
			documentIdList.add(documents.get(i).getId());
		}
		
		writeDocument.setDocument(documents);
		writeDocument.writeFileDocument();
		
		return documentIdList;
	}

	public void deleteIndex(String indexPathType,String documntId) {

		if(indexPathType==null){
			throw new RetrievalDocumentException("IndexPathType 属性不允许为空");
		}
		
		IRIndexWriter rIndexWriter=new RIndexWriter(analyzerFactory,luceneProperties,indexPathType);

		rIndexWriter.deleteDocument(indexPathType,createTerm(String.valueOf(RetrievalType.RDocItemSpecialName._IID),documntId));
	}
	
	/**
	 * 将文档从数据库索引中删除
	 * @param tableName
	 * @param recordId
	 */
	public void deleteDatabaseIndex(String indexPathType,String tableName,List<String> recordIds){

		tableName=tableName.toUpperCase();
		
		if(indexPathType==null){
			throw new RetrievalDocumentException("IndexPathType 属性不允许为空");
		}
		
		if(recordIds==null || recordIds.size()<=0){
			return;
		}
		
		int length=recordIds.size();
		
		List<Term> terms=new ArrayList<Term>();
		
		for(int i=0;i<length;i++){
			terms.add(createTerm(String.valueOf(RetrievalType.RDatabaseDocItemType._DTID),tableName+recordIds.get(i)));
		}

		IRIndexWriter rIndexWriter=new RIndexWriter(analyzerFactory,luceneProperties,indexPathType);
		
		try{
			rIndexWriter.deleteDocument(indexPathType,terms);
		}catch(Exception e){
			RetrievalUtil.errorLog(log, e);
		}
		
	}

	public void deleteIndex(String indexPathType,List<String> documntIds) {

		if(indexPathType==null){
			throw new RetrievalDocumentException("IndexPathType 属性不允许为空");
		}
		
		if(documntIds==null || documntIds.size()<=0){
			return;
		}
		
		int length=documntIds.size();
		
		List<Term> terms=new ArrayList<Term>();
		
		for(int i=0;i<length;i++){
			terms.add(createTerm(String.valueOf(RetrievalType.RDocItemSpecialName._IID),documntIds.get(i)));
		}

		IRIndexWriter rIndexWriter=new RIndexWriter(analyzerFactory,luceneProperties,indexPathType);
		
		rIndexWriter.deleteDocument(indexPathType,terms);
	}

	public void updateIndex(RDocument document) {

		if(document.getIndexPathType()==null){
			throw new RetrievalDocumentException("RDocument IndexPathType 属性不允许为空");
		}
		
		RetrievalUtil.debugLog(log,"更新一条索引："+document);
		
		try{
			deleteIndex(document.getIndexPathType(),document.getId());
		}catch(Exception e){
			RetrievalUtil.errorLog(log, e);
		}
		try{
			addIndex(document);
		}catch(Exception e){
			RetrievalUtil.errorLog(log, e);
		}
		
	}
	
	private Term createTerm(String name,String value){
		return new Term(name,value);
	}
}
