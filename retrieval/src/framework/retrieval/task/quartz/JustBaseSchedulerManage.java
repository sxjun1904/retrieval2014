package framework.retrieval.task.quartz;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.SchedulerException;

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
	public void shutDownJustScheduler() {
		for(JustBaseSchedule js : justSchedulelist){
			stopJustScheduler(js);
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
}
