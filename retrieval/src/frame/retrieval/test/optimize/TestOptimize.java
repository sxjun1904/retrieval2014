package frame.retrieval.test.optimize;

import frame.retrieval.test.init.TestInit;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.IRIndexOperatorFacade;

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
