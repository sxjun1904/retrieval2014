package framework.retrieval.engine.facade;

import java.util.List;

import org.apache.lucene.index.IndexWriter;

import framework.base.snoic.base.util.StringClass;
import framework.retrieval.engine.context.ApplicationContext;
import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;
import framework.retrieval.test.init.TestInit;

/**
 * 索引操作接口
 * 
 * @author sxjun
 *
 */
public abstract class AbstractIndexOperatorFacade implements IIndexOperatorFacade{
	public RetrievalApplicationContext retrievalApplicationContext = ApplicationContext.getApplicationContent();
	public abstract <T> List<T> deal(RetrievalApplicationContext retrievalApplicationContext);
}
