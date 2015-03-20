package com.sxjun.retrieval.controller.init;

import java.util.List;
import java.util.UUID;

import org.quartz.Job;

import com.sxjun.core.common.proxy.ServiceProxy;
import com.sxjun.core.common.service.CommonService;
import com.sxjun.core.common.utils.DictUtils;
import com.sxjun.retrieval.constant.DefaultConstant.IndexPathType;
import com.sxjun.retrieval.controller.index.IndexCommon;
import com.sxjun.retrieval.controller.job.CrawlerIndexJob0;
import com.sxjun.retrieval.controller.job.DatabaseIndexJob0;
import com.sxjun.retrieval.controller.job.DatabaseIndexJob1;
import com.sxjun.retrieval.controller.job.NormalImageIndexJob1;
import com.sxjun.retrieval.pojo.RCrawlerIndex;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.retrieval.task.quartz.JustBaseSchedule;
import frame.retrieval.task.quartz.JustBaseSchedulerManage;

public class InitIndexCrawlerItem extends IndexCommon implements InitBase {
	private List<RCrawlerIndex> rCrawlerIndexList;
	private CommonService<RCrawlerIndex> commonService = new ServiceProxy<RCrawlerIndex>().getproxy();
	@Override
	public void init(){
		rCrawlerIndexList = commonService.getObjs(RCrawlerIndex.class);
		
		//状态为1、2的索引
		for(RCrawlerIndex rdI:rCrawlerIndexList){
			if("0".equals(rdI.getIsError())&&"0".equals(rdI.getIsOn())){
				//启动定时任务
				onSechdule(rdI,new CrawlerIndexJob0());
			}
		}
	}
	
	
	public void onSechdule(RCrawlerIndex rdI,Job job){
		if("1".equals(rdI.getIsInit())){
			sechdule(rdI,job);
		}else if("2".equals(rdI.getIsInit())){
			rdI.setIsInit("1");
			commonService.put(RDatabaseIndex.class, rdI.getId(), rdI);
			sechdule(rdI,job);
		}
	}
}
