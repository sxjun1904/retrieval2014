package com.sxjun.retrieval.controller.index.init;

import java.util.List;

import com.sxjun.retrieval.common.DictUtils;
import com.sxjun.retrieval.constant.DefaultConstant.IndexPathType;
import com.sxjun.retrieval.controller.index.IndexCommon;
import com.sxjun.retrieval.controller.job.DatabaseIndexJob1;
import com.sxjun.retrieval.controller.proxy.ServiceProxy;
import com.sxjun.retrieval.controller.service.CommonService;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

public class InitIndexAllItem extends IndexCommon{
	private List<RDatabaseIndex> rDatabaseIndexList;
	private CommonService<RDatabaseIndex> commonService = new ServiceProxy<RDatabaseIndex>().getproxy();
	
	public void init(){
		rDatabaseIndexList = commonService.getObjs(RDatabaseIndex.class);
		for(RDatabaseIndex rdI:rDatabaseIndexList){
			if("0".endsWith(rdI.getIsError())&&"0".endsWith(rdI.getIsOn())&&!(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.IMAGE.getValue())).endsWith(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE,rdI.getIndexCategory().getIndexPathType()))){
				if("1".equals(rdI.getStyle())){//复合风格
					//启动定时任务
					if("1".endsWith(rdI.getIsInit())){
							sechdule(rdI,new DatabaseIndexJob1());
					}
				}
			}
		}
	}
}
