package frame.retrieval.engine.facade.impl;

import frame.base.core.util.StringClass;
import frame.retrieval.engine.analyzer.IRAnalyzerFactory;
import frame.retrieval.engine.context.LuceneProperties;
import frame.retrieval.engine.facade.IRQueryFacade;
import frame.retrieval.engine.index.reader.impl.RIndexReaderProvider;
import frame.retrieval.engine.pool.proxy.IndexReaderProxy;
import frame.retrieval.engine.query.RQuery;
import frame.retrieval.engine.query.formatter.IHighlighterFactory;
import frame.retrieval.engine.query.result.DatabaseQueryResult;
import frame.retrieval.engine.query.result.FileQueryResult;

/**
 * 
 * @author 
 *
 */
public class RQueryFacade implements IRQueryFacade{
	
	private IRAnalyzerFactory analyzerFactory=null;
	private IHighlighterFactory highlighterFactory=null;
	private int queryResultTopDocsNum=500;
	private String baseIndexPath=null;
	private LuceneProperties luceneProperties=null;
	
	private RIndexReaderProvider indexReaderProvider=new RIndexReaderProvider();
	
	/**
	 * 
	 * @param luceneProperties
	 *            
	 * @param analyzerFactory
	 *            分词器工厂
	 *            
	 * @param highlighterFactory
	 *            内容高亮处理器工厂
	 *            
	 * @param queryResultTopDocsNum
	 *            返回的最大结果集数量
	 *            
	 * @param baseIndexPath
	 *            索引文件绝对路径根目录
	 */
	public RQueryFacade(LuceneProperties luceneProperties,
			IRAnalyzerFactory analyzerFactory,
			IHighlighterFactory highlighterFactory,
			int queryResultTopDocsNum,
			String baseIndexPath){
		this.luceneProperties=luceneProperties;
		this.analyzerFactory=analyzerFactory;
		this.highlighterFactory=highlighterFactory;
		this.queryResultTopDocsNum=queryResultTopDocsNum;
		this.baseIndexPath=baseIndexPath;
	}
	
	/**
	 * 创建全文搜索对象
	 * 
	 * @param indexPathType
	 * @return
	 */
	public RQuery createRQuery(Object indexPathType){
		IndexReaderProxy indexReaderProxy=indexReaderProvider.createIndexReader(luceneProperties, baseIndexPath, String.valueOf(indexPathType));
		return new RQuery(analyzerFactory,
				highlighterFactory,
				queryResultTopDocsNum,
				baseIndexPath,
				indexReaderProxy);
	}
	
	/**
	 * 创建全文搜索对象
	 * 
	 * @param indexPathTypes
	 * @return
	 */
	public RQuery createRQuery(String[] indexPathTypes){
		int length=indexPathTypes.length;
		IndexReaderProxy[] indexReaderProxys=new IndexReaderProxy[length];
		for(int i=0;i<length;i++){
			indexReaderProxys[i]=indexReaderProvider.createIndexReader(luceneProperties, baseIndexPath, String.valueOf(indexPathTypes[i]));
		}
		
		return new RQuery(analyzerFactory,
				highlighterFactory,
				queryResultTopDocsNum,
				baseIndexPath,
				indexReaderProxys);
	}
	
	/**
	 * 获取数据库记录在索引中的内容
	 * @param indexPathType
	 * @param tableName
	 * @param recordId
	 * @return
	 */
	public DatabaseQueryResult queryDatabaseDocument(String indexPathType,String tableName,String recordId){
		RQuery query=createRQuery(StringClass.getString(indexPathType.toUpperCase()));
		DatabaseQueryResult queryResult= query.getDatabaseQueryResult(tableName, recordId);
		query.close();
		return queryResult;
	}
	
	/**
	 * 获取数据库记录在索引中的唯一ID
	 * @param indexPathType
	 * @param tableName
	 * @param recordId
	 * @return
	 */
	public String queryDatabaseDocumentIndexId(String indexPathType,String tableName,String recordId){
		RQuery query = createRQuery(StringClass.getString(indexPathType.toUpperCase()));
		String indexId=query.getDatabaseIndexId(tableName, recordId);
		query.close();
		return indexId;
	}
	
	/**
	 * 获取文件在索引中的唯一ID
	 * @param indexPathType
	 * @param fileId
	 * @return
	 */
	public String queryFileDocumentIndexIdByFileId(String indexPathType,String fileId){
		RQuery query =  createRQuery(StringClass.getString(indexPathType.toUpperCase()));
		String indexId=query.getFileIndexIdByFileId(fileId);
		query.close();
		return indexId;
	}
	
	/**
	 * 获取文件在索引中的唯一ID
	 * @param indexPathType
	 * @param fileRelativePath
	 * @return
	 */
	public String queryFileDocumentIndexIdRelativePath(String indexPathType,String fileRelativePath){
		RQuery query =   createRQuery(StringClass.getString(indexPathType.toUpperCase()));
		String indexId=query.getFileIndexIdByFileRelativePath(fileRelativePath);
		query.close();
		return indexId;
	}
	
	/**
	 * 获取文件在索引中的内容
	 * @param indexPathType
	 * @param fileId
	 * @return
	 */
	public FileQueryResult queryFileDocumentByFileId(String indexPathType,String fileId){
		RQuery query=createRQuery(StringClass.getString(indexPathType.toUpperCase()));
		FileQueryResult queryResult=query.getFileQueryResultByFileId(fileId);
		query.close();
		return queryResult;
	}
	
	/**
	 * 获取文件在索引中的内容
	 * @param indexPathType
	 * @param fileRelativePath
	 * @return
	 */
	public FileQueryResult queryFileDocumentByRelativePath(String indexPathType,String fileRelativePath){
		RQuery query=createRQuery(StringClass.getString(indexPathType.toUpperCase()));
		FileQueryResult queryResult=query.getFileQueryResultByFileRelativePath(fileRelativePath);
		query.close();
		return queryResult;
	}
	
	
}
