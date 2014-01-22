package framework.retrieval.test.fileindexall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.RetrievalType.RDatabaseFieldType;
import framework.retrieval.engine.RetrievalType.RDatabaseType;
import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.facade.ICreateIndexAllItem;
import framework.retrieval.engine.index.all.database.DatabaseLink;
import framework.retrieval.engine.index.all.database.impl.DefaultDBGetMethodImpl;
import framework.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;
import framework.retrieval.engine.index.doc.file.RFileIndexAllItem;
import framework.retrieval.test.dbindexall.TestDatabaseRecordInterceptor;

public class FileIndexAllItemImpl implements ICreateIndexAllItem{

	@Override
	public List<RFileIndexAllItem> generateApplicationData(RetrievalApplicationContext retrievalApplicationContext) {
		
		List <RFileIndexAllItem> l = new ArrayList<RFileIndexAllItem>();
		RFileIndexAllItem fileIndexAllItem=retrievalApplicationContext.getFacade().createFileIndexAllItem(false,"GBK");
		fileIndexAllItem.setIndexPathType("FILE");

		//如果无论记录是否存在，都新增一条索引内容，则使用RetrievalType.RIndexOperatorType.INSERT，
		//如果索引中记录已经存在，则只更新索引中的对应的记录，否则新增记录,则使用RetrievalType.RIndexOperatorType.UPDATE
		fileIndexAllItem.setIndexOperatorType(RetrievalType.RIndexOperatorType.INSERT);
		fileIndexAllItem.setIndexInfoType("SFILE");
		
//		fileIndexAllItem.setFileBasePath("D:\\workspace\\resources\\docs");
		fileIndexAllItem.setFileBasePath("D:\\a");
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
		l.add(fileIndexAllItem);
		return l;
	}

}
