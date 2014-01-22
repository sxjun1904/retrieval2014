package framework.retrieval.test.fileindexall;

import framework.retrieval.engine.facade.FileIndexOperatorFacade;

public class TestFileIndexAll {
	public static void main(String[] args) {
		/*RetrievalApplicationContext retrievalApplicationContext=TestInit.getApplicationContent();

		TestInit.initIndex();

		IRDocOperatorFacade docOperatorHelper=retrievalApplicationContext.getFacade().createDocOperatorFacade();

		long startTime=System.currentTimeMillis();
		RFileIndexAllItem fileIndexAllItem=retrievalApplicationContext.getFacade().createFileIndexAllItem(false,"utf-8");
		fileIndexAllItem.setIndexPathType("FILE");
		

		//如果无论记录是否存在，都新增一条索引内容，则使用RetrievalType.RIndexOperatorType.INSERT，
		//如果索引中记录已经存在，则只更新索引中的对应的记录，否则新增记录,则使用RetrievalType.RIndexOperatorType.UPDATE
		fileIndexAllItem.setIndexOperatorType(RetrievalType.RIndexOperatorType.INSERT);
		fileIndexAllItem.setIndexInfoType("SFILE");
		
		fileIndexAllItem.setFileBasePath("D:\\workspace\\resources\\docs");
		fileIndexAllItem.setIncludeSubDir(true);
		fileIndexAllItem.setPageSize(100);
		fileIndexAllItem.setIndexAllFileInterceptor(new TestFileIndexAllInterceptor());
		
		//如果要对所有类型的文件创建索引，则不要做设置一下这些设置，否则在设置过类型之后，将只对这些类型的文件创建索引
		fileIndexAllItem.addFileType("doc");
		fileIndexAllItem.addFileType("docx");
		fileIndexAllItem.addFileType("sql");
		fileIndexAllItem.addFileType("html");
		fileIndexAllItem.addFileType("htm");
		fileIndexAllItem.addFileType("txt");
		fileIndexAllItem.addFileType("xls");
		fileIndexAllItem.addFileType("xlsx");
		
		long count=docOperatorHelper.createAll(fileIndexAllItem);
		
		//优化索引
		retrievalApplicationContext.getFacade().createIndexOperatorFacade("FILE").optimize();
		
		System.out.println("\n\n耗时："+(((System.currentTimeMillis()-startTime)/1000)/60)+" 分钟,完成："+count+"个文件索引");*/
		FileIndexOperatorFacade indexAll = new FileIndexOperatorFacade(new FileIndexAllItemImpl());
		indexAll.indexAll(indexAll.MOVE_AFTER_INDEX);
	}
}
