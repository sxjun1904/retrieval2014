package framework.retrieval.test.index;

import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.facade.IRDocOperatorFacade;
import framework.retrieval.engine.index.doc.database.DatabaseIndexDocument;
import framework.retrieval.engine.index.doc.internal.RDocItem;
import framework.retrieval.test.init.TestInit;

public class TestDatabaseIndexRecord {
	public static void main(String[] args){
		
		RetrievalApplicationContext retrievalApplicationContext=TestInit.getApplicationContent();

		TestInit.initIndex();
		
		IRDocOperatorFacade docOperatorHelper=retrievalApplicationContext.getFacade().createDocOperatorFacade();
		
		
		String tableName="TABLE1";
		String recordId="849032809432490324093";
		
		DatabaseIndexDocument databaseIndexDocument=retrievalApplicationContext.getFacade().createDatabaseIndexDocument(false);
		
		databaseIndexDocument.setIndexPathType("DB");
		databaseIndexDocument.setIndexInfoType("TABLE1");
		
		databaseIndexDocument.setTableNameAndRecordId(tableName,recordId);

		RDocItem docItem1=new RDocItem();
		docItem1.setName("TITLE");
		docItem1.setContent("SJLKDFJDSLK F");
		
		RDocItem docItem2=new RDocItem();
		docItem2.setName("CONTENT");
		docItem2.setContent("RUEWOJFDLSKJFLKSJGLKJSFLKDSJFLKDSF");
		
		RDocItem docItem3=new RDocItem();
		docItem3.setName("field3");
		docItem3.setContent("adsjflkdsjflkdsf");
		
		RDocItem docItem4=new RDocItem();
		docItem4.setName("field4");
		docItem4.setContent("45432534253");
		
		RDocItem docItem5=new RDocItem();
		docItem5.setName("field5");
		docItem5.setContent("87987yyyyyyyy");
		
		RDocItem docItem6=new RDocItem();
		docItem6.setName("field6");
		docItem6.setContent("87987yyyyyyyy");
		
		databaseIndexDocument.addContent(docItem1);
		databaseIndexDocument.addContent(docItem2);
		databaseIndexDocument.addContent(docItem3);
		databaseIndexDocument.addContent(docItem4);
		databaseIndexDocument.addContent(docItem5);
		databaseIndexDocument.addContent(docItem6);
		
		docOperatorHelper.create(databaseIndexDocument);
		
		
	}
}
