package framework.retrieval.engine.facade;

import java.util.List;

import framework.retrieval.engine.context.RetrievalApplicationContext;

public abstract class AbstractIndexBaseOperator extends AbstractIndexOperatorFacade{
	private ICreateIndexAllItem createIndexAllItem;
	public AbstractIndexBaseOperator (ICreateIndexAllItem createIndexAllItem){
		this.createIndexAllItem = createIndexAllItem;
	}
	
	@Override
	public <T> List<T> deal(RetrievalApplicationContext retrievalApplicationContext) {
		ICreateIndexAllItem proxy = createIndexAllItem;
		return proxy.generateApplicationData(retrievalApplicationContext);
	}
}
