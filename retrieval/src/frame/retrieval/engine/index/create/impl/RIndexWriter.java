package frame.retrieval.engine.index.create.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;

import frame.retrieval.engine.analyzer.IRAnalyzerFactory;
import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.context.DefaultRetrievalProperties;
import frame.retrieval.engine.context.LuceneProperties;
import frame.retrieval.engine.index.create.IRIndexWriter;
import frame.retrieval.engine.index.create.RetrievalDocumentException;
import frame.retrieval.task.ITask;
import frame.retrieval.task.impl.IndexTaskMain;
import frame.retrieval.task.impl.RAMIndexTask;

/**
 * 
 * @author 
 *
 */
public class RIndexWriter implements IRIndexWriter{	
	private Log log=RetrievalUtil.getLog(this.getClass());

	private RIndexWriteProvider indexWriteProvider=null;
	private String indexPathType=null;
	private DefaultRetrievalProperties defaultRetrievalProperties=null;

	public RIndexWriter(IRAnalyzerFactory analyzerFactory,LuceneProperties luceneProperties,String indexPathType){
		this.indexPathType=indexPathType.toUpperCase();
		indexWriteProvider=new RIndexWriteProvider(analyzerFactory,luceneProperties);
	}
	
	public RIndexWriter(IRAnalyzerFactory analyzerFactory,LuceneProperties luceneProperties,DefaultRetrievalProperties defaultRetrievalProperties,String indexPathType){
		this.indexPathType=indexPathType.toUpperCase();
		indexWriteProvider=new RIndexWriteProvider(analyzerFactory,luceneProperties);
		this.defaultRetrievalProperties = defaultRetrievalProperties;
	}
	
	/**
	 * 增加一个索引,先写入内存,然后在批量将索引写入文件
	 * @param documents
	 */
	public void addDocument(List<Document> documents){
		if(indexPathType==null){
			throw new RetrievalDocumentException("indexPathType 不允许为null");
		}
		
		if(documents==null|| documents.size()<=0){
			return ;
		}
		int length=documents.size();
		RetrievalUtil.debugLog(log, "索引  "+indexPathType+" 增加 "+length+" 条新索引 ");

//		RetrievalIndexLock.getInstance().lock(indexPathType);
		IndexWriter indexWriter=null;
		RIndexWriterWrap ramIndexWriterWrap=indexWriteProvider.createRamIndexWriter();
		try{
			for(int i=0;i<length;i++){
				Document document=documents.get(i);
				ramIndexWriterWrap.getIndexWriter().addDocument(document);
			}
			
			ramIndexWriterWrap.getIndexWriter().commit();
			indexWriter=getIndexWriter(indexPathType);	
			indexWriter.addIndexesNoOptimize(new Directory[]{ramIndexWriterWrap.getDirectory()});
			
		}catch(Exception e){
			throw new RetrievalDocumentException(e);
		}finally{
			if(ramIndexWriterWrap!=null){
				try {
					ramIndexWriterWrap.close();
				} catch (Exception e) {
					RetrievalUtil.errorLog(log, e);
				}
			}
			if(indexWriter!=null){
				try {
					indexWriter.commit();
				} catch (Exception e) {
					RetrievalUtil.errorLog(log, e);
				}
				try {
					indexWriter.close();
				} catch (Exception e) {
					RetrievalUtil.errorLog(log, e);
				}
			}
//			RetrievalIndexLock.getInstance().unlock(indexPathType);
		}
	}
	
	/**
	 * 增加一个索引,先写入内存,然后在批量将索引写入文件
	 * 不关闭索引 add by sxjun 
	 * 举例：将2000调数据用10个线程跑，每个线程200条。将10个线程的数据写入10个内存，然后写到硬盘，最后关闭内存。
	 * @param documents
	 */
	public void addDocumentNotClose(List<Document> documents,IndexWriter _indexWriter){
		if(indexPathType==null){
			throw new RetrievalDocumentException("indexPathType 不允许为null");
		}
		
		if(documents==null|| documents.size()<=0){
			return ;
		}
		int length=documents.size();
		RetrievalUtil.debugLog(log, "索引  "+indexPathType+" 增加 "+length+" 条新索引 ");
		int default_thread_maxnum = defaultRetrievalProperties.getDefault_thread_maxnum();//最大线程数
		int default_single_thread_dealnums = defaultRetrievalProperties.getDefault_single_thread_dealnums();//单线程处理数据量
		int threadnum = (length%default_single_thread_dealnums>0)?(length/default_single_thread_dealnums+1):(length/default_single_thread_dealnums);
		if(threadnum > default_thread_maxnum){
			default_single_thread_dealnums = (int) Math.ceil((double)length/default_thread_maxnum);
			threadnum = default_thread_maxnum;
		}
		
		Directory[] dirs = new Directory[threadnum];
		List<ITask> tasklist = new ArrayList<ITask>();
		
		for(int i = 0;i<threadnum;i++){
			List<Document> docpage = new ArrayList<Document>();
			for(int j = default_single_thread_dealnums*i;j<default_single_thread_dealnums*(i+1)&&j<length;j++){
				docpage.add(documents.get(j));
			}
			RAMIndexTask ramIndexTask = new RAMIndexTask(indexWriteProvider,docpage);
			tasklist.add(ramIndexTask);
		}
		
		List<RIndexWriterWrap> wraplist = new IndexTaskMain().exeFuture(tasklist);
		int size = wraplist.size();
		for(int i = 0 ;i<size;i++){
			dirs[i] = wraplist.get(i).getDirectory();
		}
//		_indexWriter=getIndexWriter(indexPathType);	
		try {
			_indexWriter.addIndexesNoOptimize(dirs);
		} catch (CorruptIndexException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			if(wraplist!=null){
				for(RIndexWriterWrap w : wraplist){
					if(w != null){
						try {
							w.close();
						} catch (Exception e) {
							RetrievalUtil.errorLog(log, e);
						}
					}
				}
				
			}
		}
	}
	
	/**
	 * 增加一个索引,直接将索引写入文件
	 * @param documents
	 */
	public void addDocumentNowRamSupport(List<Document> documents){
		if(indexPathType==null){
			throw new RetrievalDocumentException("indexPathType 不允许为null");
		}
		
		if(documents==null|| documents.size()<=0){
			return ;
		}
		int length=documents.size();
		RetrievalUtil.debugLog(log, "索引  "+indexPathType+" 增加 "+length+" 条新索引 ");

//		RetrievalIndexLock.getInstance().lock(indexPathType);
		IndexWriter indexWriter=null;
		indexWriter=getIndexWriter(indexPathType);	
		try{
			for(int i=0;i<length;i++){
				Document document=documents.get(i);
				indexWriter.addDocument(document);
			}
			
		}catch(Exception e){
			throw new RetrievalDocumentException(e);
		}finally{
			if(indexWriter!=null){
				try {
					indexWriter.commit();
				} catch (Exception e) {
					RetrievalUtil.errorLog(log, e);
				}
				try {
					indexWriter.close();
				} catch (Exception e) {
					RetrievalUtil.errorLog(log, e);
				}
			}
//			RetrievalIndexLock.getInstance().unlock(indexPathType);
		}
	}
	
	
	/**
	 * 写入一个索引
	 * @param document
	 */
	public void addDocument(Document document){
		if(indexPathType==null){
			throw new RetrievalDocumentException("indexPathType 不允许为null");
		}
		
//		RetrievalIndexLock.getInstance().lock(indexPathType);
		IndexWriter indexWriter=null;
		indexWriter=getIndexWriter(indexPathType);	
		try {
			RetrievalUtil.debugLog(log, "索引  "+indexPathType+" 增加一条新索引");
			
			indexWriter.addDocument(document);
		} catch (Exception e) {
			throw new RetrievalDocumentException(e);
		}finally{
			if(indexWriter!=null){
				try {
					indexWriter.commit();
				} catch (Exception e) {
					RetrievalUtil.errorLog(log, e);
				}
				try {
					indexWriter.close();
				} catch (Exception e) {
					RetrievalUtil.errorLog(log, e);
				}
			}
//			RetrievalIndexLock.getInstance().unlock(indexPathType);
		}
	}	
	
	/**
	 * 删除一个索引
	 * @param indexPathType
	 * @param term
	 */
	public void deleteDocument(String indexPathType,Term term) {

//		RetrievalIndexLock.getInstance().lock(indexPathType);

		IndexWriter indexWriter=null;
		try {
			RetrievalUtil.debugLog(log, "删除一个索引："+indexPathType);
			try{
				indexWriter=getIndexWriter(indexPathType);
				indexWriter.deleteDocuments(term);
			}catch(Exception e){
				throw new RetrievalDocumentException(e);
			}
		}finally{
			if(indexWriter!=null){
				try {
					indexWriter.commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					indexWriter.close();
				} catch (Exception e) {
					RetrievalUtil.errorLog(log, e);
				}
			}
//			RetrievalIndexLock.getInstance().unlock(indexPathType);
		}
	}
	
	/**
	 * 批量删除索引
	 * @param indexPathType
	 * @param terms
	 */
	public void deleteDocument(String indexPathType,List<Term> terms) {
		
		if(terms==null || terms.size()<=0){
			return;
		}

//		RetrievalIndexLock.getInstance().lock(indexPathType);

		IndexWriter indexWriter=null;
		try {
			int length=terms.size();

			RetrievalUtil.debugLog(log, "删除"+length+"个索引："+indexPathType);
			try{
				indexWriter=getIndexWriter(indexPathType);
				indexWriter.deleteDocuments(terms.toArray(new Term[length]));
			}catch(Exception e){
				e.printStackTrace();
			}
		}finally{
			if(indexWriter!=null){
				try {
					indexWriter.commit();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					indexWriter.close();
				} catch (Exception e) {
					RetrievalUtil.errorLog(log, e);
				}
			}
//			RetrievalIndexLock.getInstance().unlock(indexPathType);
		}
	}
	
	/**
	 * 生成IndexWriter对象
	 * @param indexPathType
	 * @return
	 */
	private IndexWriter getIndexWriter(String indexPathType){
		
		IndexWriter indexWriter=indexWriteProvider.createNormalIndexWriter(indexPathType);
		
		return indexWriter;
	}
	
}
