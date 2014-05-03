package frame.retrieval.test.normalindex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.RetrievalType.RDatabaseFieldType;
import frame.retrieval.engine.RetrievalType.RDatabaseType;
import frame.retrieval.engine.context.RFacade;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.ICreateIndexAllItem;
import frame.retrieval.engine.facade.IRDocOperatorFacade;
import frame.retrieval.engine.index.all.database.DatabaseLink;
import frame.retrieval.engine.index.all.database.impl.DefaultDBGetMethodImpl;
import frame.retrieval.engine.index.doc.NormalIndexDocument;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;
import frame.retrieval.engine.index.doc.internal.RDocItem;
import frame.retrieval.test.init.TestInit;

public class NormalImageImpl implements ICreateIndexAllItem{

	@Override
	public List<NormalIndexDocument> deal(RetrievalApplicationContext retrievalApplicationContext) {
		RFacade facade=retrievalApplicationContext.getFacade();
		List<NormalIndexDocument> l = new ArrayList<NormalIndexDocument>();
		///////////////////////////////////////////////////////////
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
		
		normalIndexDocument.setIndexInfoType("图片");
		normalIndexDocument.setIndexPathType("picture");
		normalIndexDocument.setId(UUID.randomUUID().toString());
		
		l.add(normalIndexDocument);
		///////////////////////////////////////////////////////////
		return l;
	}

	@Override
	public void afterDeal(RDatabaseIndexAllItem databaseIndexAllItem) {
		// TODO Auto-generated method stub
		
	}

}
