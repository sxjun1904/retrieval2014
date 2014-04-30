package frame.retrieval.engine.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.IndexWriter;

import frame.retrieval.engine.analyzer.IRAnalyzerFactory;
import frame.retrieval.engine.context.DefaultRetrievalProperties;
import frame.retrieval.engine.context.LuceneProperties;
import frame.retrieval.engine.facade.IRDocOperatorFacade;
import frame.retrieval.engine.index.all.database.IRDatabaseIndexAll;
import frame.retrieval.engine.index.all.file.IRFileIndexAll;
import frame.retrieval.engine.index.all.file.impl.DefaultRFileIndexAll;
import frame.retrieval.engine.index.create.IRIndexOperator;
import frame.retrieval.engine.index.create.impl.RIndexOperator;
import frame.retrieval.engine.index.create.impl.file.IFileContentParserManager;
import frame.retrieval.engine.index.create.impl.file.RFileDocumentMakeup;
import frame.retrieval.engine.index.doc.NormalIndexDocument;
import frame.retrieval.engine.index.doc.database.DatabaseIndexDocument;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;
import frame.retrieval.engine.index.doc.file.FileIndexDocument;
import frame.retrieval.engine.index.doc.file.RFileIndexAllItem;
import frame.retrieval.engine.index.doc.file.internal.RFileDocument;
import frame.retrieval.engine.index.doc.internal.RDocument;


/**
 * 
 * @author 
 *
 */
public class RDocOperatorFacade implements IRDocOperatorFacade{
	private IRIndexOperator indexOperator=null;
	private IRDatabaseIndexAll databaseIndexAll;
	private IFileContentParserManager fileContentParserManager;

	public RDocOperatorFacade(IRDatabaseIndexAll databaseIndexAll,
			IFileContentParserManager fileContentParserManager,
			IRAnalyzerFactory analyzerFactory,
			LuceneProperties luceneProperties){
		this.databaseIndexAll=databaseIndexAll;
		this.fileContentParserManager=fileContentParserManager;
		indexOperator=new RIndexOperator(analyzerFactory,luceneProperties);
	}
	
	public RDocOperatorFacade(IRDatabaseIndexAll databaseIndexAll,
			IFileContentParserManager fileContentParserManager,
			IRAnalyzerFactory analyzerFactory,
			LuceneProperties luceneProperties,DefaultRetrievalProperties defaultRetrievalProperties){
		this.databaseIndexAll=databaseIndexAll;
		this.fileContentParserManager=fileContentParserManager;
		indexOperator=new RIndexOperator(analyzerFactory,luceneProperties,defaultRetrievalProperties);
	}
	
	
	/**
	 * 创建文本类型索引内容
	 * @param indexDocuments
	 */
	public void createNormalIndexs(List<NormalIndexDocument> indexDocuments){
		List<RDocument> documentList=new ArrayList<RDocument>();
		int length=indexDocuments.size();
		for(int i=0;i<length;i++){
			documentList.add(indexDocuments.get(i).getRDocument());
		}
		
		indexOperator.addIndex(documentList);
	}
	
	/**
	 * 创建文本类型索引内容
	 * @param indexDocument
	 */
	public void create(NormalIndexDocument indexDocument){
		if(indexDocument==null){
			return;
		}
		
		List<NormalIndexDocument> indexDocuments=new ArrayList<NormalIndexDocument>();
		indexDocuments.add(indexDocument);
		createNormalIndexs(indexDocuments);
	}
	

	/**
	 * 创建数据库类型索引内容
	 * @param indexDocuments
	 */
	public void createDatabaseIndexs(List<DatabaseIndexDocument> indexDocuments){
		createDatabaseIndexs(indexDocuments,null);
	}
	
	public void createDatabaseIndexs(List<DatabaseIndexDocument> indexDocuments,IndexWriter indexWriter){
		List<RDocument> documentList=new ArrayList<RDocument>();
		int length=indexDocuments.size();
		for(int i=0;i<length;i++){
			documentList.add(indexDocuments.get(i).getRDocument());
		}
		
		indexOperator.addIndex(documentList,indexWriter);
	}
	
	/**
	 * 创建数据库类型索引内容
	 * @param indexDocument
	 */
	public void create(DatabaseIndexDocument indexDocument){
		if(indexDocument==null){
			return;
		}
		
		List<DatabaseIndexDocument> indexDocuments=new ArrayList<DatabaseIndexDocument>();
		indexDocuments.add(indexDocument);
		createDatabaseIndexs(indexDocuments);
	}
	
	/**
	 * 根据SQL创建数据库类型索引内容
	 * @param indexDocument
	 */
	public long createAll(RDatabaseIndexAllItem databaseIndexAllItem){
		return createAll(databaseIndexAllItem,null);
	}
	
	public long createAll(RDatabaseIndexAllItem databaseIndexAllItem,IndexWriter indexWriter){
		if(databaseIndexAllItem==null){
			return 0;
		}
		
		databaseIndexAll.setDatabaseIndexAllItem(databaseIndexAllItem); 
		long indexCount=databaseIndexAll.indexAll(indexWriter);
		return indexCount;
	}
	
	/**
	 * 根据文件路径创建索引内容
	 * @param indexDocument
	 */
	public long createAll(RFileIndexAllItem fileIndexAllItem){
		if(fileIndexAllItem==null){
			return 0;
		}
		
		IRFileIndexAll fileIndexAll=new DefaultRFileIndexAll();
		fileIndexAll.setFileIndexAllItem(fileIndexAllItem);
		long indexCount=fileIndexAll.indexAll();
		return indexCount;
	}
	
	/**
	 * 创建文件类型索引内容
	 * @param indexDocuments
	 * @param maxFileSize
	 */
	public void createFileIndexs(List<FileIndexDocument> indexDocuments,long maxFileSize){
		List<RDocument> documentList=new ArrayList<RDocument>();
		int length=indexDocuments.size();
		for(int i=0;i<length;i++){
			FileIndexDocument document=indexDocuments.get(i);
			RFileDocument rFileDocument=(RFileDocument)document.getRDocument();
			rFileDocument=new RFileDocumentMakeup(fileContentParserManager,rFileDocument,maxFileSize).makeup();
			documentList.add(rFileDocument);
		}
		
		indexOperator.addFileIndex(documentList);
	}
	
	/**
	 * 创建文件类型索引内容
	 * @param indexDocument
	 * @param maxFileSize
	 */
	public void create(FileIndexDocument indexDocument,long maxFileSize){
		if(indexDocument==null){
			return;
		}
		
		List<FileIndexDocument> indexDocuments=new ArrayList<FileIndexDocument>();
		indexDocuments.add(indexDocument);
		createFileIndexs(indexDocuments,maxFileSize);
	}
	
	/**
	 * 更新索引内容
	 * @param indexDocument
	 */
	public void update(NormalIndexDocument indexDocument){
		indexOperator.updateIndex(indexDocument.getRDocument());
	}
	
	/**
	 * 更新索引内容
	 * @param indexDocument
	 */
	public void update(DatabaseIndexDocument indexDocument){
		indexOperator.updateIndex(indexDocument.getRDocument());
	}
	
	/**
	 * 更新索引内容
	 * @param indexDocument
	 * @param maxFileSize
	 */
	public void update(FileIndexDocument indexDocument,long maxFileSize){
		RFileDocument rFileDocument=(RFileDocument)indexDocument.getRDocument();
		rFileDocument=new RFileDocumentMakeup(fileContentParserManager,rFileDocument,maxFileSize).makeup();
		indexOperator.updateIndex(indexDocument.getRDocument());
	}
	
	/**
	 * 删除索引内容
	 * @param indexPathType
	 * @param documntId
	 */
	public void delete(String indexPathType,String documntId){
		indexOperator.deleteIndex(indexPathType.toUpperCase(), documntId);
	}
	
	/**
	 * 删除数据库索引内容
	 * @param indexPathType
	 * @param tableName
	 * @param recordIds
	 */
	public void delete(String indexPathType,String tableName,List<String> reocrdIds){
		indexOperator.deleteDatabaseIndex(indexPathType.toUpperCase(), tableName,reocrdIds);
	}
	
	/**
	 * 删除索引内容
	 * @param indexPathType
	 * @param documntId
	 */
	public void delete(String indexPathType,List<String> documntIds){
		indexOperator.deleteIndex(indexPathType.toUpperCase(), documntIds);
	}
	
}
