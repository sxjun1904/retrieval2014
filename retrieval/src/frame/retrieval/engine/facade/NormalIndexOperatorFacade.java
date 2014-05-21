package frame.retrieval.engine.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frame.base.core.util.StringClass;
import frame.retrieval.engine.context.ApplicationContext;
import frame.retrieval.engine.context.RFacade;
import frame.retrieval.engine.index.doc.NormalIndexDocument;

/**
 * 索引操作接口
 * 
 * @author sxjun
 *
 */
public class NormalIndexOperatorFacade extends AbstractIndexBaseOperator{
	public NormalIndexOperatorFacade(ICreateIndexAllItem createIndexAllItem) {
		super(createIndexAllItem);
	}
	
	@Override
	public long indexAll() {
		long startTime = System.currentTimeMillis();
		RFacade facade=retrievalApplicationContext.getFacade();
		List<NormalIndexDocument> normalIndexDocumentList = deal(retrievalApplicationContext);
		if(normalIndexDocumentList!=null){
			for(NormalIndexDocument normalIndexDocument:normalIndexDocumentList){
				String indexPathType = StringClass.getFormatPath(normalIndexDocument.getIndexPathType());
				indexPathType = ApplicationContext.initIndexSet(indexPathType);
				normalIndexDocument.setIndexPathType(indexPathType);
			}
			IRDocOperatorFacade docOperatorFacade=facade.createDocOperatorFacade();
			docOperatorFacade.createNormalIndexs(normalIndexDocumentList);
			afterDeal(normalIndexDocumentList);
			System.out.println("PICTURE耗时："+ (((System.currentTimeMillis() - startTime) / 1000))+ " 秒,共完成：" + normalIndexDocumentList.size() + " 条索引");
			return normalIndexDocumentList.size();
		}else{
			return 0;
		}
	}
}
