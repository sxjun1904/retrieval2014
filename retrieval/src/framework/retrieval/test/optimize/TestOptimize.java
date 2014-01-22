package framework.retrieval.test.optimize;

import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.facade.IRIndexOperatorFacade;
import framework.retrieval.test.init.TestInit;

public class TestOptimize {

	private RetrievalApplicationContext retrievalApplicationContext=TestInit.getApplicationContent();
	
	public void optimize(){

		TestInit.initIndex();

		IRIndexOperatorFacade dbIndexOperatorHelper=retrievalApplicationContext.getFacade().createIndexOperatorFacade("DB");
		dbIndexOperatorHelper.optimize();

		IRIndexOperatorFacade fileIndexOperatorHelper=retrievalApplicationContext.getFacade().createIndexOperatorFacade("FILE");
		fileIndexOperatorHelper.optimize();
		
		System.out.println("索引优化完成....");
	}
	
	public static void main(String[] args) {

		new TestOptimize().optimize();
		
	}
}
