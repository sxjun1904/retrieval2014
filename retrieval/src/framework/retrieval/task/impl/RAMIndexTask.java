package framework.retrieval.task.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.Directory;

import framework.retrieval.engine.analyzer.IRAnalyzerFactory;
import framework.retrieval.engine.common.RetrievalUtil;
import framework.retrieval.engine.context.LuceneProperties;
import framework.retrieval.engine.index.create.RetrievalDocumentException;
import framework.retrieval.engine.index.create.impl.RIndexWriteProvider;
import framework.retrieval.engine.index.create.impl.RIndexWriterWrap;
import framework.retrieval.task.ITask;

public class RAMIndexTask implements ITask<RIndexWriterWrap> {
	private Log log=RetrievalUtil.getLog(this.getClass());
	
	private RIndexWriteProvider indexWriteProvider=null;
	private List<Document> documents;
	
	public RAMIndexTask(RIndexWriteProvider indexWriteProvider,List<Document> documents){
		this.indexWriteProvider = indexWriteProvider;
		this.documents = documents;
	}

	@Override
	public RIndexWriterWrap call() throws Exception {
		RIndexWriterWrap ramIndexWriterWrap=indexWriteProvider.createRamIndexWriter();
		try{
			int length = documents.size();
			for(int i=0;i<length;i++){
				Document document=documents.get(i);
				ramIndexWriterWrap.getIndexWriter().addDocument(document);
			}
			ramIndexWriterWrap.getIndexWriter().commit();
		}catch(Exception e){
			throw new RetrievalDocumentException(e);
		}
		return ramIndexWriterWrap;
	}
}
