package framework.retrieval.test.index;

import java.io.File;
import java.net.URISyntaxException;

import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.facade.IRDocOperatorFacade;
import framework.retrieval.engine.index.doc.file.FileIndexDocument;
import framework.retrieval.test.init.TestInit;

public class TestFileIndex {
	public static void main(String[] args) throws URISyntaxException {

		RetrievalApplicationContext retrievalApplicationContext=TestInit.getApplicationContent();

		TestInit.initIndex();
		
		IRDocOperatorFacade docOperatorHelper=retrievalApplicationContext.getFacade().createDocOperatorFacade();
		
		FileIndexDocument fileIndexDocument=retrievalApplicationContext.getFacade().createFileIndexDocument(false,"utf-8");
		fileIndexDocument.setFileBasePath("c:\\doc");
		fileIndexDocument.setFileId("fileId_123");
		fileIndexDocument.setFile(new File("c:\\doc\\1.doc"));
		fileIndexDocument.setIndexPathType("FILE");
		fileIndexDocument.setIndexInfoType("SFILE");
		
		docOperatorHelper.create(fileIndexDocument,3*1024*1024);
		
		System.out.println("\n完成!!!");
	}
}
