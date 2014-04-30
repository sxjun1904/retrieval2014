package com.sxjun.retrieval.controller.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sxjun.retrieval.controller.index.DatabaseIndexAllItem0Impl;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import frame.retrieval.engine.facade.DBIndexOperatorFacade;
import frame.retrieval.engine.facade.ICreateIndexAllItem;
import frame.retrieval.task.quartz.QuartzManager;

public class DataaseIndexJob0 implements Job {
	private ICreateIndexAllItem iciai= null;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		RDatabaseIndex rDatabaseIndex = (RDatabaseIndex) arg0.getJobDetail().getJobDataMap().get(QuartzManager.JUST_SCHEDULE_RETURN);
		if(rDatabaseIndex!=null)
			iciai = new DatabaseIndexAllItem0Impl(rDatabaseIndex);
		else
			iciai = new DatabaseIndexAllItem0Impl();
		DBIndexOperatorFacade indexAll = new DBIndexOperatorFacade(iciai);
		indexAll.indexAll(indexAll.INDEX_BY_THREAD);
	}
	
}
