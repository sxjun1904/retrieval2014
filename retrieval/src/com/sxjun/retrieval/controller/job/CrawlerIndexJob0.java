package com.sxjun.retrieval.controller.job;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.sxjun.core.common.proxy.ServiceProxy;
import com.sxjun.core.common.service.CommonService;
import com.sxjun.core.common.utils.DictUtils;
import com.sxjun.retrieval.constant.DefaultConstant.IndexPathType;
import com.sxjun.retrieval.controller.index.IndexCommon;
import com.sxjun.retrieval.controller.index.crawler.CustomCrawlerController;
import com.sxjun.retrieval.pojo.RCrawlerIndex;

import frame.retrieval.engine.context.ApplicationContext;
import frame.retrieval.engine.context.RetrievalApplicationContext;
import frame.retrieval.engine.facade.ICreateIndexAllItem;
import frame.retrieval.task.quartz.QuartzManager;

public class CrawlerIndexJob0 extends IndexCommon implements StatefulJob {
	private CommonService<RCrawlerIndex> commonService = new ServiceProxy<RCrawlerIndex>().getproxy();
	private static RetrievalApplicationContext retrievalApplicationContext = ApplicationContext.getApplicationContent();
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ICreateIndexAllItem iciai1 = null;
		RCrawlerIndex rCrawlerIndex = (RCrawlerIndex) arg0.getJobDetail().getJobDataMap().get(QuartzManager.JUST_SCHEDULE_RETURN);
		List<RCrawlerIndex> rCrawlerIndexList;
		if(rCrawlerIndex!=null){
			rCrawlerIndexList = new ArrayList<RCrawlerIndex>();
			rCrawlerIndexList.add(rCrawlerIndex);
		}
		else	
			rCrawlerIndexList = commonService.getObjs(RCrawlerIndex.class);
		for(RCrawlerIndex rdI:rCrawlerIndexList){
			if("0".equals(rdI.getIsError())&&"0".equals(rdI.getIsOn())&&(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE, IndexPathType.CUSTOM.getValue())).equals(DictUtils.getDictMapByKey(DictUtils.INDEXPATH_TYPE,rdI.getIndexCategory().getIndexPathType()))){
				CustomCrawlerController crawlerController = new CustomCrawlerController(retrievalApplicationContext,rdI);
				crawlerController.indexAll();
				//启动定时任务
				sechdule(rdI,new CrawlerIndexJob0());
			}
		}
	}
	
}
