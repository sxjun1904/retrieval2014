package frame.retrieval.engine.facade;

import java.util.List;

import frame.base.core.util.StringClass;
import frame.base.core.util.file.FileHelper;
import frame.retrieval.engine.context.ApplicationContext;
import frame.retrieval.engine.context.RFacade;
import frame.retrieval.engine.index.doc.NormalIndexDocument;
import frame.retrieval.engine.index.doc.file.RFileIndexAllItem;

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
		RFacade facade=retrievalApplicationContext.getFacade();
		List<NormalIndexDocument> normalIndexDocumentList = deal(retrievalApplicationContext);
		for(NormalIndexDocument normalIndexDocument:normalIndexDocumentList){
			String indexPathType = StringClass.getFormatPath(normalIndexDocument.getIndexPathType());
			indexPathType = ApplicationContext.initIndexSet(indexPathType);
			normalIndexDocument.setIndexPathType(indexPathType);
		}
		IRDocOperatorFacade docOperatorFacade=facade.createDocOperatorFacade();
		docOperatorFacade.createNormalIndexs(normalIndexDocumentList);
		return normalIndexDocumentList.size();
	}
}
