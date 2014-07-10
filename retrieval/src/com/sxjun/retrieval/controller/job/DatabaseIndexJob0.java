package com.sxjun.retrieval.controller.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sxjun.retrieval.controller.index.database.DatabaseIndexAllItem0Impl;
import com.sxjun.retrieval.controller.index.image.NormalImageIndex0Impl;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.retrieval.engine.facade.DBIndexOperatorFacade;
import frame.retrieval.engine.facade.ICreateIndexAllItem;
import frame.retrieval.engine.facade.NormalIndexOperatorFacade;
import frame.retrieval.task.quartz.QuartzManager;

public class DatabaseIndexJob0 implements Job {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ICreateIndexAllItem iciai0 = null;
		ICreateIndexAllItem iciai1 = null;
		RDatabaseIndex rDatabaseIndex = (RDatabaseIndex) arg0.getJobDetail().getJobDataMap().get(QuartzManager.JUST_SCHEDULE_RETURN);
		if(rDatabaseIndex!=null)
			iciai0 = new DatabaseIndexAllItem0Impl(rDatabaseIndex);
		else
			iciai0 = new DatabaseIndexAllItem0Impl();
		DBIndexOperatorFacade indexAll = new DBIndexOperatorFacade(iciai0);
		indexAll.indexAll(indexAll.INDEX_BY_SIMPLE);
		
		if(rDatabaseIndex!=null)
			iciai1 = new NormalImageIndex0Impl(rDatabaseIndex);
		else	
			iciai1 = new NormalImageIndex0Impl();
		NormalIndexOperatorFacade imageIndexAll = new NormalIndexOperatorFacade(iciai1);
		imageIndexAll.indexAll();
	}
	
}
