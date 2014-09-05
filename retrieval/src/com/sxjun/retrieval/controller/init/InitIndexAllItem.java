package com.sxjun.retrieval.controller.init;

import java.util.List;
import java.util.UUID;

import org.quartz.Job;

import com.sxjun.common.proxy.ServiceProxy;
import com.sxjun.common.service.CommonService;
import com.sxjun.common.utils.DictUtils;
import com.sxjun.retrieval.constant.DefaultConstant.IndexPathType;
import com.sxjun.retrieval.controller.index.IndexCommon;
import com.sxjun.retrieval.controller.job.DatabaseIndexJob0;
import com.sxjun.retrieval.controller.job.DatabaseIndexJob1;
import com.sxjun.retrieval.controller.job.NormalImageIndexJob1;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.retrieval.task.quartz.JustBaseSchedule;
import frame.retrieval.task.quartz.JustBaseSchedulerManage;

public class InitIndexAllItem extends IndexCommon implements InitBase {
	private List<RDatabaseIndex> rDatabaseIndexList;
	private CommonService<RDatabaseIndex> commonService = new ServiceProxy<RDatabaseIndex>().getproxy();
	@Override
	public void init(){
		rDatabaseIndexList = commonService.getObjs(RDatabaseIndex.class);
		//初始化状态为0的索引
		Job dij = new DatabaseIndexJob0();
		JustBaseSchedule jbs = new JustBaseSchedule();
		jbs.setScheduleID(UUID.randomUUID().toString());
		jbs.setExecCount("1");
		jbs.setScheduleName(UUID.randomUUID().toString());
		JustBaseSchedulerManage jbsm = new JustBaseSchedulerManage(jbs);
		jbsm.startUpJustScheduler(dij);
		
		//状态为1、2的索引
		for(RDatabaseIndex rdI:rDatabaseIndexList){
			if("0".equals(rdI.getIsError())&&"0".equals(rdI.getIsOn())){
				if("1".equals(rdI.getStyle())){//复合风格
					if(!(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.IMAGE.getValue())).equals(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE,rdI.getIndexCategory().getIndexPathType()))){
						//启动定时任务
						onSechdule(rdI,new DatabaseIndexJob1());
					}else if((DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.IMAGE.getValue())).equals(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE,rdI.getIndexCategory().getIndexPathType()))){
						//启动定时任务
						onSechdule(rdI,new NormalImageIndexJob1());
					}
				}
			}
		}
	}
	
	
	public void onSechdule(RDatabaseIndex rdI,Job job){
		if("1".equals(rdI.getIsInit())){
			sechdule(rdI,job);
		}else if("2".equals(rdI.getIsInit())){
			rdI.setIsInit("1");
			commonService.put(RDatabaseIndex.class, rdI.getId(), rdI);
			sechdule(rdI,job);
		}
	}
}
