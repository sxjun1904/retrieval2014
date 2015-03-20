package com.sxjun.retrieval.controller.index.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sxjun.core.common.proxy.ServiceProxy;
import com.sxjun.core.common.service.CommonService;
import com.sxjun.core.common.utils.DictUtils;
import com.sxjun.retrieval.constant.DefaultConstant.IndexPathType;
import com.sxjun.retrieval.controller.job.DatabaseIndexJob1;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.base.core.util.DateTime;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.ICreateIndexAllItem;
import frame.retrieval.engine.index.doc.database.RDatabaseIndexAllItem;

public class DatabaseIndexAllItem0Impl extends DatabaseIndexAllItemCommon implements ICreateIndexAllItem{
	
	private List<RDatabaseIndex> rDatabaseIndexList;
	private CommonService<RDatabaseIndex> commonService = new ServiceProxy<RDatabaseIndex>().getproxy();
	
//	public static Map<String,Double> pagerank= new HashMap<String,Double>();
	
	public DatabaseIndexAllItem0Impl(){
		rDatabaseIndexList = commonService.getObjs(RDatabaseIndex.class);
	}
	
	public DatabaseIndexAllItem0Impl(RDatabaseIndex rDatabaseIndex){
		rDatabaseIndexList = new ArrayList<RDatabaseIndex>();
		rDatabaseIndexList.add(rDatabaseIndex);
	}

	@Override
	public List<RDatabaseIndexAllItem> deal(RetrievalApplicationContext retrievalApplicationContext) {
		this.retrievalApplicationContext = retrievalApplicationContext;
		
		List <RDatabaseIndexAllItem> l = new ArrayList<RDatabaseIndexAllItem>();
		for(RDatabaseIndex rdI:rDatabaseIndexList){
			if("0".equals(rdI.getIsError())&&"0".equals(rdI.getIsInit())&&"0".equals(rdI.getIsOn())&&!(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.IMAGE.getValue())).equals(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE,rdI.getIndexCategory().getIndexPathType()))){
				//List<HtmlEntity> he = getPageRank(rdI);
				/*for(HtmlEntity h :he){
					pagerank.put(h.getPath(), h.getPr());
				}*/
				
				rdI.setIsInit("2");
				rdI.setMediacyTime(new DateTime().parseString(new Date(), null));
				commonService.put(RDatabaseIndex.class, rdI.getId(), rdI);
				
				String nowTime =  new DateTime().getNowDateTime();
				//删除触发器表中记录
				delAllTrigRecord(rdI,nowTime,false);
				
				String sql = rdI.getSql();
				l.add(create(retrievalApplicationContext,rdI,sql,nowTime));
				//启动定时任务
				if("1".equals(rdI.getStyle())){//复合风格
					sechdule(rdI,new DatabaseIndexJob1());
				}
			}
		}
		return l;
	}
	

	@Override
	public void afterDeal(Object databaseIndexAllItem) {
		Map<String,Object> transObj = (Map<String, Object>) ((RDatabaseIndexAllItem)databaseIndexAllItem).getTransObject();
		RDatabaseIndex rdI = (RDatabaseIndex) transObj.get("rdI");
		String nowTime = (String) transObj.get("nowTime");
		judgeAndDelTrigRecord(rdI,nowTime);
		rdI.setIsInit("1");
		rdI.setMediacyTime("");
		commonService.put(RDatabaseIndex.class, rdI.getId(), rdI);
	}
	
}
