package frame.retrieval.engine.facade;

import java.util.List;

import frame.base.core.util.StringClass;
import frame.base.core.util.file.FileHelper;
import frame.retrieval.engine.context.ApplicationContext;
import frame.retrieval.engine.index.doc.file.RFileIndexAllItem;

/**
 * 索引操作接口
 * 
 * @author sxjun
 *
 */
public class FileIndexOperatorFacade extends AbstractIndexBaseOperator{
	public FileIndexOperatorFacade(ICreateIndexAllItem createIndexAllItem) {
		super(createIndexAllItem);
	}
	
	/**
	 * 删除文件
	 */
	public final int DEL_AFTER_INDEX=0;
	
	/**
	 * 不删除文件
	 */
	public final int MOVE_AFTER_INDEX=1;
	
	private FileHelper fileHelper = new FileHelper();

	/**
	 * 创建索引
	 * @return
	 */
	@Override
	public long indexAll(){
		return indexAll(DEL_AFTER_INDEX);
	}
	
	public long indexAll(int afterIndex){
		long startTime = System.currentTimeMillis();
		long indexCount = 0;
		List<RFileIndexAllItem> fileIndexAllItemList = deal(retrievalApplicationContext);
		for(RFileIndexAllItem fileIndexAllItem : fileIndexAllItemList){
			System.out.println("retrievalApplicationContext:"+retrievalApplicationContext);
			String fileBasePath = fileIndexAllItem.getFileBasePath();
			String indexPathType = StringClass.getFormatPath(fileIndexAllItem.getIndexPathType());
			indexPathType = ApplicationContext.initIndexSet(indexPathType);
			fileIndexAllItem.setIndexPathType(indexPathType);
			IRDocOperatorFacade docOperatorFacade = retrievalApplicationContext.getFacade().createDocOperatorFacade();
			indexCount += docOperatorFacade.createAll(fileIndexAllItem);
			retrievalApplicationContext.getFacade().createIndexOperatorFacade(indexPathType).optimize();
			switch (afterIndex){
				case MOVE_AFTER_INDEX:{
					String filebak = retrievalApplicationContext.getDefaultRetrievalProperties().getDefault_move_file_after_index_folder();
					fileHelper.moveFolder(fileBasePath, filebak);
					break;
				}
				case DEL_AFTER_INDEX:{
					fileHelper.delAllFile(fileBasePath);
					break;
				}
				default:{
					fileHelper.delAllFile(fileBasePath);
					break;
				}
			}
		}
		System.out.println("TABLE1 耗时："+ (((System.currentTimeMillis() - startTime) / 1000))+ " 秒,共完成：" + indexCount + " 条索引");
		return indexCount;
	}
}
