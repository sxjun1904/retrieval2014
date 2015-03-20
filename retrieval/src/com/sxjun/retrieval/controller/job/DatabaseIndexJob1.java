package com.sxjun.retrieval.controller.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.sxjun.retrieval.controller.index.database.DatabaseIndexAllItem1Impl;

import frame.retrieval.engine.facade.DBIndexOperatorFacade;
import frame.retrieval.engine.facade.ICreateIndexAllItem;

public class DatabaseIndexJob1 implements StatefulJob  {
	private ICreateIndexAllItem iciai= null;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		iciai = new DatabaseIndexAllItem1Impl();
		DBIndexOperatorFacade indexAll = new DBIndexOperatorFacade(iciai);
		indexAll.indexAll(indexAll.INDEX_BY_SIMPLE);
	}
	
}
