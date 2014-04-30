package frame.retrieval.engine.facade;

import java.util.List;

import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

public abstract class AbstractIndexBaseOperator extends AbstractIndexOperatorFacade{
	private ICreateIndexAllItem createIndexAllItem;
	public AbstractIndexBaseOperator (ICreateIndexAllItem createIndexAllItem){
		this.createIndexAllItem = createIndexAllItem;
	}
	
	@Override
	public <T> List<T> deal(RetrievalApplicationContext retrievalApplicationContext) {
		return createIndexAllItem.deal(retrievalApplicationContext);
	}
	
	@Override
	public void afterDeal(RDatabaseIndexAllItem databaseIndexAllItem){
		createIndexAllItem.afterDeal(databaseIndexAllItem);
	}
}
