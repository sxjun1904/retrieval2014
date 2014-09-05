package com.sxjun.retrieval.controller.index.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sxjun.common.proxy.ServiceProxy;
import com.sxjun.common.service.CommonService;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.base.core.util.DateTime;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.ICreateIndexAllItem;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

public class DatabaseIndexAllItem1Impl extends DatabaseIndexAllItemCommon implements ICreateIndexAllItem{
	
	private List<RDatabaseIndex> rDatabaseIndexList;
	private CommonService<RDatabaseIndex> commonService = new ServiceProxy<RDatabaseIndex>().getproxy();
	
	public DatabaseIndexAllItem1Impl(){
		rDatabaseIndexList = commonService.getObjs(RDatabaseIndex.class);
	}
	
	@Override
	public List<RDatabaseIndexAllItem> deal(RetrievalApplicationContext retrievalApplicationContext) {
		
		List <RDatabaseIndexAllItem> l = new ArrayList<RDatabaseIndexAllItem>();
		for(RDatabaseIndex rdI:rDatabaseIndexList){
			if("0".equals(rdI.getIsError())&&"1".equals(rdI.getIsInit())&&"0".equals(rdI.getIsOn())){
				rdI.setIsInit("2");
				rdI.setMediacyTime(new DateTime().parseString(new Date(), null));
				commonService.put(RDatabaseIndex.class, rdI.getId(), rdI);
				String nowTime = new DateTime().getNowDateTime();
				String sql = getIndexTriggerSql(rdI,nowTime,false);
				l.add(create(retrievalApplicationContext,rdI,sql,nowTime));
			}
		}
		return l;
	}

	
	/**
	 * 删除触发器表
	 */
	@Override
	public void afterDeal(Object databaseIndexAllItem) {
		Map<String,Object> transObj = (Map<String, Object>) ((RDatabaseIndexAllItem)databaseIndexAllItem).getTransObject();
		String nowTime = (String) transObj.get("nowTime");
		//RDatabaseIndex rdI = commonService.get(RDatabaseIndex.class, transObj.get("id"));
		RDatabaseIndex rdI = (RDatabaseIndex) transObj.get("rdI");
		//删除索引
		if("1".endsWith(rdI.getIndexOperatorType())){
			judgeAndDelIndexRecord(rdI,nowTime);
		}
		delAllTrigRecord(rdI,nowTime,true);
		rdI.setIsInit("1");
		rdI.setMediacyTime("");
		commonService.put(RDatabaseIndex.class, rdI.getId(), rdI);
	}

}
