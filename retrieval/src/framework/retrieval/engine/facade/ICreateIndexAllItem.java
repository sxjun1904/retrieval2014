package framework.retrieval.engine.facade;

import java.util.List;

import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

public interface ICreateIndexAllItem{
	public <T> List<T> generateApplicationData(RetrievalApplicationContext retrievalApplicationContext);
}
