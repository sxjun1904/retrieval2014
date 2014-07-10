/**
 * code generation
 */
package com.sxjun.retrieval.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.quartz.Trigger;

import com.jfinal.core.Controller;
import com.sxjun.retrieval.pojo.TriggerView;

import frame.base.core.util.DateTime;
import frame.retrieval.task.quartz.JustBaseSchedulerManage;

/**
 * 线程监控Controller
 * @author sxjun
 * @version 2014-07-10
 */
public class TriggerViewController extends Controller {

	public void list() {
		List<TriggerView> list = getTriggerViewList();
		setAttr("triggerView",list);
		render("triggerViewList.jsp");
	}
	
	public List<TriggerView> getTriggerViewList(){
		JustBaseSchedulerManage jsm = new JustBaseSchedulerManage();
		List<Trigger> tgList = jsm.getTriggers();
		List<TriggerView> tgviewList = new ArrayList();
		for (Trigger tg : tgList) {
			TriggerView tgview = new TriggerView();
			tgview.setClassSimpleName(tg.getClass().getSimpleName());
			tgview.setFullJobName(tg.getFullJobName());
			tgview.setFullName(tg.getFullName());
			tgview.setJobName(tg.getJobName());
			tgview.setId(tg.getFireInstanceId());
			tgview.setName(tg.getName());
			tgview.setNextFireTime(tg.getNextFireTime()!=null?new DateTime().parseString(tg.getNextFireTime(),null):"");
			tgview.setStartTime(new DateTime().parseString(tg.getStartTime(),null));
			tgviewList.add(tgview);
		}
		return tgviewList;
	}
}
