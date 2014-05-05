package frame.retrieval.engine.facade;

import java.util.List;

import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

public interface ICreateIndexAllItem{
	public <T> List<T> deal(RetrievalApplicationContext retrievalApplicationContext);
	public void afterDeal(Object o);
}
