package framework.retrieval.test.normalindex;

import framework.retrieval.engine.context.RFacade;
import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.facade.IRDocOperatorFacade;
import framework.retrieval.engine.index.doc.NormalIndexDocument;
import framework.retrieval.engine.index.doc.internal.RDocItem;
import framework.retrieval.test.init.TestInit;

public class TestNormalIndex {
	public static void main(String[] args) {

		RetrievalApplicationContext retrievalApplicationContext=TestInit.getApplicationContent();
		TestInit.initIndex();
		
		RFacade facade=retrievalApplicationContext.getFacade();
		
		NormalIndexDocument normalIndexDocument=facade.createNormalIndexDocument(false);
		
		RDocItem docItem1=new RDocItem();
		docItem1.setContent("搜索引擎");
		docItem1.setName("KEY_FIELD");
		normalIndexDocument.addKeyWord(docItem1);

		RDocItem docItem2=new RDocItem();
		docItem2.setContent("速度覅藕断丝连房价多少了咖啡卡拉圣诞节");
		docItem2.setName("TITLE_FIELD");
		normalIndexDocument.addContent(docItem2);

		RDocItem docItem3=new RDocItem();
		docItem3.setContent("哦瓦尔卡及讨论热离开家");
		docItem3.setName("CONTENT_FIELD");
		normalIndexDocument.addContent(docItem3);

		IRDocOperatorFacade docOperatorFacade=facade.createDocOperatorFacade();
		
		docOperatorFacade.create(normalIndexDocument);
		
	}
}
