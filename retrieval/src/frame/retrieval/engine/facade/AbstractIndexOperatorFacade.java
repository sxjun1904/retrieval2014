package frame.retrieval.engine.facade;

import java.util.List;

import frame.retrieval.engine.context.ApplicationContext;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

/**
 * 索引操作接口
 * 
 * @author sxjun
 *
 */
public abstract class AbstractIndexOperatorFacade implements IIndexOperatorFacade{
	public RetrievalApplicationContext retrievalApplicationContext = ApplicationContext.getApplicationContent();
	public abstract <T> List<T> deal(RetrievalApplicationContext retrievalApplicationContext);
	public abstract void afterDeal(Object o);
}
