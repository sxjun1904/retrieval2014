package framework.retrieval.engine.facade;

import java.util.List;

import org.apache.lucene.index.IndexWriter;

import framework.base.snoic.base.util.StringClass;
import framework.retrieval.engine.context.ApplicationContext;
import framework.retrieval.engine.context.RetrievalApplicationContext;
import framework.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;
import framework.retrieval.test.init.TestInit;

/**
 * 索引操作接口
 * 
 * @author sxjun
 *
 */
public class DBIndexOperatorFacade extends AbstractIndexBaseOperator{
	public DBIndexOperatorFacade(ICreateIndexAllItem createIndexAllItem) {
		super(createIndexAllItem);
	}

	/**
	 * 简单方式创建索引
	 */
	public final int INDEX_BY_SIMPLE=0;
	/**
	 * 内部线程方式串讲索引
	 */
	public final int INDEX_BY_THREAD=1;
	
	/**
	 * 创建索引
	 * @return
	 */
	@Override
	public long indexAll(){
		return indexAll(INDEX_BY_SIMPLE);
	}
	
	public long indexAll(int indexType){
		long startTime = System.currentTimeMillis();
		long indexCount = 0;
		List<RDatabaseIndexAllItem> databaseIndexAllItemList = deal(retrievalApplicationContext);
		for(RDatabaseIndexAllItem databaseIndexAllItem : databaseIndexAllItemList){
			switch (indexType){
				case INDEX_BY_SIMPLE:{
					indexCount = indexBySimple(databaseIndexAllItem,indexCount);
					break;
				}
				case INDEX_BY_THREAD:{
					indexCount = indexByThread(databaseIndexAllItem,indexCount);
					break;
				}
				default:{
					indexCount = indexBySimple(databaseIndexAllItem,indexCount);
					break;
				}
			}
			
		}
		System.out.println("TABLE1 耗时："+ (((System.currentTimeMillis() - startTime) / 1000))+ " 秒,共完成：" + indexCount + " 条索引");
		return indexCount;
	}
	
	/**
	 * 内部线程方式串讲索引200条每个线程
	 * 500一页，3个线程， 耗时：553 秒,共完成：98043 条索引
	 * 1000一页，5个线程， 耗时：331 秒,共完成：98043 条索引
	 * 2000一页，10个线程， 耗时：230 秒,共完成：98043 条索引
	 * 3000一页，15个线程， 耗时：231 秒,共完成：98043 条索引
	 * @param databaseIndexAllItem
	 * @param indexCount
	 * @return
	 */
	public long indexByThread(RDatabaseIndexAllItem databaseIndexAllItem,long indexCount){
		IndexWriter indexWriter = null;
		try {
			System.out.println("retrievalApplicationContext:"+retrievalApplicationContext);
			String indexPathType = StringClass.getFormatPath(databaseIndexAllItem.getIndexPathType());
			indexPathType = ApplicationContext.initIndexSet(indexPathType);
			databaseIndexAllItem.setIndexPathType(indexPathType);
			indexWriter = super.retrievalApplicationContext.getFacade().createIndexWriter(indexPathType);
			IRDocOperatorFacade docOperatorFacade = retrievalApplicationContext.getFacade().createDocOperatorFacade();
			indexCount += docOperatorFacade.createAll(databaseIndexAllItem,indexWriter);
			retrievalApplicationContext.getFacade().createIndexOperatorFacade(indexPathType).forceMerge(5);
			return indexCount;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			retrievalApplicationContext.getFacade().closeIndexWriter(indexWriter);
		}
		return 0;
	}
	
	/**
	 * 简单方式创建索引 
	 * 500一页 耗时：724 秒,共完成：98043 条索引
	 * @param databaseIndexAllItem
	 * @param indexCount
	 * @return
	 */
	public long indexBySimple(RDatabaseIndexAllItem databaseIndexAllItem,long indexCount){
		try {
			System.out.println("retrievalApplicationContext:"+retrievalApplicationContext);
			String indexPathType = StringClass.getFormatPath(databaseIndexAllItem.getIndexPathType());
			indexPathType = ApplicationContext.initIndexSet(indexPathType);
			databaseIndexAllItem.setIndexPathType(indexPathType);
			IRDocOperatorFacade docOperatorFacade = retrievalApplicationContext.getFacade().createDocOperatorFacade();
			indexCount += docOperatorFacade.createAll(databaseIndexAllItem);
//			retrievalApplicationContext.getFacade().createIndexOperatorFacade(indexPathType).optimize();
			retrievalApplicationContext.getFacade().createIndexOperatorFacade(indexPathType).forceMerge(5);
			return indexCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
