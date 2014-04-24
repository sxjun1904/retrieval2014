package com.sxjun.retrieval.controller.job;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sxjun.retrieval.controller.index.DatabaseIndexAllItemImpl;
import com.sxjun.retrieval.pojo.RDatabaseIndex;

import framework.retrieval.engine.facade.DBIndexOperatorFacade;
import framework.retrieval.engine.facade.ICreateIndexAllItem;
import framework.retrieval.task.quartz.QuartzManager;

public class DataaseIndexJob implements Job {
	private ICreateIndexAllItem iciai= null;
	/*public DataaseIndexJob(){
	}
	public DataaseIndexJob(List<RDatabaseIndex> rDatabaseIndexList){
		this.iciai = new DatabaseIndexAllItemImpl(rDatabaseIndexList);
	}
	public DataaseIndexJob(RDatabaseIndex rDatabaseIndex){
		List<RDatabaseIndex> rDatabaseIndexList = new ArrayList<RDatabaseIndex>();
		rDatabaseIndexList.add(rDatabaseIndex);
		this.iciai = new DatabaseIndexAllItemImpl(rDatabaseIndexList);
	}*/
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<RDatabaseIndex> rDatabaseIndexList = new ArrayList<RDatabaseIndex>();
		RDatabaseIndex rDatabaseIndex = (RDatabaseIndex) arg0.getJobDetail().getJobDataMap().get(QuartzManager.JUST_SCHEDULE_RETURN);
		rDatabaseIndexList.add(rDatabaseIndex);
		iciai = new DatabaseIndexAllItemImpl(rDatabaseIndexList);
		if(iciai!=null){
			DBIndexOperatorFacade indexAll = new DBIndexOperatorFacade(iciai);
			indexAll.indexAll(indexAll.INDEX_BY_THREAD);
		}
	}
	/*public ICreateIndexAllItem getIciai() {
		return iciai;
	}
	
	public void setIciai(List<RDatabaseIndex> rDatabaseIndexList) {
		this.iciai = new DatabaseIndexAllItemImpl(rDatabaseIndexList);
	}
	
	public void setIciai(RDatabaseIndex rDatabaseIndex) {
		List<RDatabaseIndex> rDatabaseIndexList = new ArrayList<RDatabaseIndex>();
		rDatabaseIndexList.add(rDatabaseIndex);
		this.iciai = new DatabaseIndexAllItemImpl(rDatabaseIndexList);
	}*/

}
