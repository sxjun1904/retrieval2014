package frame.retrieval.engine.context;

import org.apache.lucene.index.IndexWriter;

import frame.base.core.util.StringClass;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDocItemType;
import frame.retrieval.engine.facade.IRDocOperatorFacade;
import frame.retrieval.engine.facade.IRIndexOperatorFacade;
import frame.retrieval.engine.facade.IRQueryFacade;
import frame.retrieval.engine.facade.RetrevalIndexInit;
import frame.retrieval.engine.facade.impl.RDocOperatorFacade;
import frame.retrieval.engine.facade.impl.RIndexOperatorFacade;
import frame.retrieval.engine.facade.impl.RQueryFacade;
import frame.retrieval.engine.index.create.impl.RIndexWriteProvider;
import frame.retrieval.engine.index.doc.NormalIndexDocument;
import frame.retrieval.engine.index.doc.database.DatabaseIndexDocument;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;
import frame.retrieval.engine.index.doc.file.FileIndexDocument;
import frame.retrieval.engine.index.doc.file.RFileIndexAllItem;
import frame.retrieval.engine.query.item.QueryItem;

/**
 * 索引读写帮助
 * 
 * @author 
 *
 */
public class RFacade {
	private RetrievalApplicationContext retrievalApplicationContext;

	RFacade(RetrievalApplicationContext retrievalApplicationContext) {
		this.retrievalApplicationContext = retrievalApplicationContext;
	}

	/**
	 * 初始化索引文件
	 * 
	 * @param indexPathTypes
	 *            索引类型名称数组
	 */
	public void initIndex(String[] indexPathTypes) {
		new RetrevalIndexInit().init(retrievalApplicationContext.getRetrievalFactory().getAnalyzerFactory(),
				retrievalApplicationContext.getRetrievalProperties().getLuceneProperties(),
				indexPathTypes);
	}
	
	public String[] initset(String[] indexPathTypes) {
		return new RetrevalIndexInit().initset(retrievalApplicationContext.getRetrievalFactory().getAnalyzerFactory(),
				retrievalApplicationContext.getRetrievalProperties().getLuceneProperties(),
				indexPathTypes,retrievalApplicationContext.getDefaultRetrievalProperties().getDefault_retrieval_indexfile_size());
	}
	
	public String[] getLocalIndexPathTypes(String[] indexPathTypes){
		return new RetrevalIndexInit().getLocalIndexPathTypes(retrievalApplicationContext.getRetrievalProperties().getLuceneProperties(), indexPathTypes);
	}


	/**
	 * 初始化索引文件
	 * 
	 * @param indexPathType
	 *            索引类型名称数组
	 */
	public void initIndex(String indexPathType) {
		new RetrevalIndexInit().init(retrievalApplicationContext.getRetrievalFactory().getAnalyzerFactory(),
				retrievalApplicationContext.getRetrievalProperties().getLuceneProperties(),
				new String[] { indexPathType });
	}

	/**
	 * 创建QueryItem对象
	 * @param docItemType
	 * @param name
	 * @param keyWord
	 * @return
	 */
	public QueryItem createQueryItem(RetrievalType.RDocItemType docItemType,
			Object name,
			String keyWord){
		QueryItem queryItem=new QueryItem(retrievalApplicationContext.getRetrievalFactory().getAnalyzerFactory(),
				docItemType,
				name,
				keyWord);
		return queryItem;
	}
	/**
	 * 创建QueryItem对象
	 * @param docItemType
	 * @param name
	 * @param keyWord
	 * @param score
	 * @return
	 */
	public QueryItem createQueryItem(RetrievalType.RDocItemType docItemType, Object name,String keyWord, Float score){
		QueryItem queryItem=new QueryItem(retrievalApplicationContext.getRetrievalFactory().getAnalyzerFactory(), docItemType, name, keyWord, score);
		return queryItem;
	}
	
	/**
	 * 创建QueryItem对象
	 * @param docItemType
	 * @param name
	 * @param keyWord
	 * @param regex
	 * @return
	 */
	public QueryItem createQueryItem(RetrievalType.RDocItemType docItemType,
			Object name,
			String keyWord,
			boolean regex){
		QueryItem queryItem=new QueryItem(retrievalApplicationContext.getRetrievalFactory().getAnalyzerFactory(),
				docItemType,
				name,
				keyWord,
				regex);
		return queryItem;
	}
	
	/**
	 * 创建当条数据库记录Document
	 * @param fullContentFlag 是否同时再索引中生成一个将所有内容组合存放的字段
	 * @return
	 */
	public DatabaseIndexDocument createDatabaseIndexDocument(boolean fullContentFlag){
		return new DatabaseIndexDocument(fullContentFlag);
	}
	
	/**
	 * 创建单个文件Document
	 * @param fullContentFlag 是否同时再索引中生成一个将所有内容组合存放的字段
	 * @param charsetName 	  文本文件索引时使用的字符集
	 * @return
	 */
	public FileIndexDocument createFileIndexDocument(boolean fullContentFlag,String charsetName){
		return new FileIndexDocument(fullContentFlag,charsetName);
	}
	
	/**
	 * 创建普通类型Document
	 * @param fullContentFlag 是否同时再索引中生成一个将所有内容组合存放的字段
	 * @return
	 */
	public NormalIndexDocument createNormalIndexDocument(boolean fullContentFlag){
		return new NormalIndexDocument(fullContentFlag);
	}
	
	/**
	 * 创建批量文件索引对象
	 * @param fullContentFlag 是否同时再索引中生成一个将所有内容组合存放的字段
	 * @param charsetName 	  文本文件索引时使用的字符集
	 */
	public RFileIndexAllItem createFileIndexAllItem(boolean fullContentFlag,String charsetName){
		charsetName=StringClass.getString(charsetName);
		if(charsetName.equals("")){
			charsetName=retrievalApplicationContext.getRetrievalProperties().getPropertyValueIndexDefaultCharset();
		}
		RFileIndexAllItem fileIndexAllItem=new RFileIndexAllItem(createQueryFacade(),
				createDocOperatorFacade(),
				fullContentFlag,
				retrievalApplicationContext.getRetrievalProperties().getPropertyValueIndexMaxFileDocumentPageSize(),
				charsetName);
		return fileIndexAllItem;
	}

	/**
	 * 创建RFileIndexAllItem对象
	 * @param fullContentFlag 是否同时再索引中生成一个将所有内容组合存放的字段
	 */
	public RDatabaseIndexAllItem createDatabaseIndexAllItem(boolean fullContentFlag){
		RDatabaseIndexAllItem databaseIndexAllItem=new RDatabaseIndexAllItem(createQueryFacade(),
				createDocOperatorFacade(),
				fullContentFlag,
				retrievalApplicationContext.getRetrievalProperties().getPropertyValueIndexMaxDBDocumentPageSize());
		return databaseIndexAllItem;
	}
	
	/**
	 * 获取全文检索查询操作类
	 * 
	 * @return
	 */
	public IRQueryFacade createQueryFacade() {
		return new RQueryFacade(retrievalApplicationContext.getRetrievalProperties().getLuceneProperties(),
				retrievalApplicationContext.getRetrievalFactory().getAnalyzerFactory(),
				retrievalApplicationContext.getRetrievalFactory().getHighlighterFactory(), 
				retrievalApplicationContext.getRetrievalProperties().getPropertyValueQueryResultTopDocsNum(),
				retrievalApplicationContext.getBaseIndexpath());
	}

	/**
	 * 获取索引内容操作类
	 * 
	 * @return
	 */
	public IRDocOperatorFacade createDocOperatorFacade() {
		return new RDocOperatorFacade(retrievalApplicationContext.getRetrievalFactory().getDatabaseIndexAll(),
				retrievalApplicationContext.getRetrievalFactory().getFileContentParserManager(),
				retrievalApplicationContext.getRetrievalFactory().getAnalyzerFactory(), 
				retrievalApplicationContext.getRetrievalProperties().getLuceneProperties(),
				retrievalApplicationContext.getDefaultRetrievalProperties());
	}

	/**
	 * 获取索引文件操作类
	 * 
	 * @param indexPathType
	 * @return
	 */
	public IRIndexOperatorFacade createIndexOperatorFacade(
			String indexPathType) {
		return new RIndexOperatorFacade(retrievalApplicationContext.getRetrievalFactory().getAnalyzerFactory(),
				retrievalApplicationContext.getRetrievalProperties().getLuceneProperties(),
				indexPathType);
	}
	
	public IndexWriter createIndexWriter(String indexPathType){
		RIndexWriteProvider indexWriteProvider=new RIndexWriteProvider(retrievalApplicationContext.getRetrievalFactory().getAnalyzerFactory(),retrievalApplicationContext.getRetrievalProperties().getLuceneProperties());
		return indexWriteProvider.createNormalIndexWriter(indexPathType);
	}
	
	public void closeIndexWriter(IndexWriter indexWriter){
		if(indexWriter!=null){
			try {
				indexWriter.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				indexWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
