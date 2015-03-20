package com.sxjun.retrieval.pojo;

import java.util.List;

import com.sxjun.system.pojo.BasePojo;

public class RIndex extends BasePojo{
	
	private static final long serialVersionUID = 6896024517401061919L;
	private List<JustSchedule> justScheduleList;//任务调度

	public List<JustSchedule> getJustScheduleList() {
		return justScheduleList;
	}

	public void setJustScheduleList(List<JustSchedule> justScheduleList) {
		this.justScheduleList = justScheduleList;
	}
	
}
