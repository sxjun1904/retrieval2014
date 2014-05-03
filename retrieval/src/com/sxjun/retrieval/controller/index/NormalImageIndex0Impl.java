package com.sxjun.retrieval.controller.index;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.Database;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.base.core.util.DateTime;
import frame.base.core.util.JdbcUtil;
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

public class NormalImageIndex0Impl extends NormalImageIndexCommon implements ICreateIndexAllItem{
	private List<RDatabaseIndex> rDatabaseIndexList;
	private CommonService<RDatabaseIndex> commonService = new CommonService<RDatabaseIndex>();

	public NormalImageIndex0Impl(){
		rDatabaseIndexList = commonService.getObjs(RDatabaseIndex.class.getSimpleName());
	}
	
	public NormalImageIndex0Impl(RDatabaseIndex rDatabaseIndex){
		rDatabaseIndexList = new ArrayList<RDatabaseIndex>();
		rDatabaseIndexList.add(rDatabaseIndex);
	}
	
	@Override
	public List<NormalIndexDocument> deal(RetrievalApplicationContext retrievalApplicationContext) {
		RFacade facade=retrievalApplicationContext.getFacade();
		List<NormalIndexDocument> l = new ArrayList<NormalIndexDocument>();
		
		for(RDatabaseIndex rdI:rDatabaseIndexList){
			rdI.setIsInit("2");
			commonService.put(RDatabaseIndex.class.getSimpleName(), rdI.getId(), rdI);
			String sql = rdI.getSql();
			Database db =  rdI.getDatabase();
			RDatabaseType databaseType = DictUtils.changeToRDatabaseType(db.getDatabaseType());
			String url = JdbcUtil.getConnectionURL(databaseType, db.getIp(), db.getPort(), db.getDatabaseName());
			Connection conn =JdbcUtil.getConnection(databaseType, url, db.getUser(), db.getPassword());
			
			if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.ORACLE)) {
				sql = getLimitString_Oracle(sql, 0, 200);
			} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.SQLSERVER)) {
				sql = getLimitString_SqlServer(sql, 0, 200);
			} else if (databaseType != null && databaseType.equals(RetrievalType.RDatabaseType.MYSQL)) {
				sql = getLimitString_MySql(sql, 0, 200);
			}
			List<Map> result = (List<Map>) JdbcUtil.getMapList(conn, sql, true);
			
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
		}
		return l;
	}
	
	@Override
	public void afterDeal(RDatabaseIndexAllItem databaseIndexAllItem) {
		// TODO Auto-generated method stub
		
	}

}
