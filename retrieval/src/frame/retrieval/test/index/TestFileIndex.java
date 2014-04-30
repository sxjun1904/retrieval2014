package frame.retrieval.test.index;

import java.io.File;
import java.net.URISyntaxException;

import frame.retrieval.test.init.TestInit;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.IRDocOperatorFacade;
import frame.retrieval.engine.index.doc.file.FileIndexDocument;

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
