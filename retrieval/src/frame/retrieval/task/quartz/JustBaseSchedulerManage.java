package frame.retrieval.task.quartz;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

public class JustBaseSchedulerManage {
	
	private List<JustBaseSchedule> justSchedulelist = new ArrayList<JustBaseSchedule>();
	
	private JustBaseSchedule justSchedule;
	
	public JustBaseSchedulerManage(){
		
	}
	
	public JustBaseSchedulerManage(List<JustBaseSchedule> justSchedulelist){
		this.justSchedulelist = justSchedulelist;
	}
	
	public JustBaseSchedulerManage(JustBaseSchedule justSchedule){
		this.justSchedulelist.add(justSchedule);
		this.justSchedule = justSchedule;
	}
	
	/**
	 * 添加定时任务
	 */
	public void addJustScheduler(JustBaseSchedule js,Job job){
		addJustScheduler(js,job,QuartzManager.SCHEDULE_TYPE_TRIGGER_SIMPLE);
	}
	
	/**
	 * 添加定时任务
	 */
	public void addJustScheduler(JustBaseSchedule js,Job job,String scheduletype){
		QuartzManager qm = new QuartzManager(js);
		try {
			qm.addJob(job,scheduletype);
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改触发器
	 */
	public void modifyJustScheduler(){
		if(this.justSchedule!=null)
			modifyJustScheduler(justSchedule);
	}
	
	/**
	 * 修改触发器
	 */
	public void modifyJustScheduler(JustBaseSchedule js){
		QuartzManager qm = new QuartzManager();
		qm.setJustSchedule(js);
		try {
			qm.modifyJobTime();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 停止定时任务
	 */
	public void stopJustScheduler(){
		if(this.justSchedule!=null)
			stopJustScheduler(justSchedule);
	}

	/**
	 *开启任务
	 * @param job
	 */
	public void startUpJustScheduler(Job job){
		for(JustBaseSchedule js : justSchedulelist){
				addJustScheduler(js,job,QuartzManager.SCHEDULE_TYPE_TRIGGER_SIMPLE);
		}
	}
	
	/**
	 *开启任务
	 * @param job
	 */
	public void startUpJustScheduler(Job job, String scheduletype){
		for(JustBaseSchedule js : justSchedulelist){
				addJustScheduler(js,job,scheduletype);
		}
	}
	
	/**
	 * 停止定时任务
	 */
	public void shutDownJustScheduler() {
		for(JustBaseSchedule js : justSchedulelist){
			stopJustScheduler(js);
		}
	}
	
	/**
	 * 停止定时任务
	 */
	public void stopJustScheduler(JustBaseSchedule js){
		QuartzManager qm = new QuartzManager();
		qm.setJustSchedule(js);
		try {
			qm.removeJob();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 停止定时任务
	 */
	public void stopJustScheduler(String jobName){
		QuartzManager qm = new QuartzManager();
		try {
			qm.removeJob(jobName);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 停止定时任务
	 */
	public void shutDownJustScheduler(List<JustBaseSchedule> jslist) {
		for(JustBaseSchedule js : jslist){
			stopJustScheduler(js);
		}
	}
	
	/**
	 * 暂停触发器
	 */
	public void pauseJustScheduler(){
		for(JustBaseSchedule js : justSchedulelist){
			pauseJustScheduler(js);
		}
	}
	
	/**
	 * 暂停触发器
	 */
	public void pauseJustScheduler(JustBaseSchedule js){
		QuartzManager qm = new QuartzManager();
		try {
			qm.setJustSchedule(js);
			qm.pauseTrigger();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 暂停触发器
	 */
	public void pauseJustScheduler(String triggerName){
		QuartzManager qm = new QuartzManager();
		try {
			qm.pauseTrigger(triggerName);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重启触发器
	 */
	public void resumeJustScheduler(){
		for(JustBaseSchedule js : justSchedulelist){
			resumeJustScheduler(js);
		}
	}
	
	/**
	 * 重启触发器
	 */
	public void resumeJustScheduler(JustBaseSchedule js){
		QuartzManager qm = new QuartzManager();
		try {
			qm.setJustSchedule(js);
			qm.resumeTrigger();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重启触发器
	 */
	public void resumeJustScheduler(String triggerName){
		QuartzManager qm = new QuartzManager();
		try {
			qm.resumeTrigger(triggerName);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取触发器列表
	 * @return
	 */
	public List<Trigger> getTriggers(){
		List<Trigger> tgList = new ArrayList<Trigger>();
		try {
			Scheduler scheduler = QuartzManager.sf.getScheduler();
			String[] triggerGroups = scheduler.getTriggerGroupNames();
			for (int i = 0; i < triggerGroups.length; i++) {
				String[] triggers = scheduler.getTriggerNames(triggerGroups[i]);
				for (int j = 0; j < triggers.length; j++) {
					Trigger tg = scheduler.getTrigger(triggers[j],triggerGroups[i]);
					tgList.add(tg);
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return tgList;
	}
}
