package framework.retrieval.test.delete;

import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.facade.IRDocOperatorFacade;
import framework.retrieval.test.init.TestInit;

public class TestDelete {	

	private RetrievalApplicationContext retrievalApplicationContext=TestInit.getApplicationContent();
	
	private IRDocOperatorFacade docOperatorFacade=null;
	
	public TestDelete(){
		
		TestInit.initIndex();
		
		docOperatorFacade=retrievalApplicationContext.getFacade().createDocOperatorFacade();
	}
	
	public void delete (String indexPathType,String documntId){
		docOperatorFacade.delete(indexPathType, documntId);
	}
	
	public static void main(String[] args) {
		TestDelete testDelete=new TestDelete();

		String id="20100723090307199188890800684913";
		
		testDelete.delete("DB", id);
		
		System.out.println("索引记录"+id+"已删除");
	}
}
