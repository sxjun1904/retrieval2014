package framework.retrieval.task.quartz;

import java.text.ParseException;
import java.util.List;

import org.quartz.Job;
import org.quartz.SchedulerException;

public class JustSchedulerManage {
	
	private List<JustSchedule> justSchedulelist;
	
	private JustSchedule justSchedule;
	
	public JustSchedulerManage(){
		
	}
	
	public JustSchedulerManage(List<JustSchedule> justSchedulelist){
		this.justSchedulelist = justSchedulelist;
	}
	
	public JustSchedulerManage(JustSchedule justSchedule){
		this.justSchedule = justSchedule;
	}
	
	/**
	 * 添加定时任务
	 */
	public void addJustScheduler(JustSchedule js,Job job){
		addJustScheduler(js,job,QuartzManager.SCHEDULE_TYPE_TRIGGER_SIMPLE);
	}
	
	/**
	 * 添加定时任务
	 */
	public void addJustScheduler(JustSchedule js,Job job,String scheduletype){
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
	public void modifyJustScheduler(JustSchedule js){
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
		for(JustSchedule js : justSchedulelist){
				addJustScheduler(js,job,QuartzManager.SCHEDULE_TYPE_TRIGGER_SIMPLE);
		}
	}
	
	/**
	 *开启任务
	 * @param job
	 */
	public void startUpJustScheduler(Job job, String scheduletype){
		for(JustSchedule js : justSchedulelist){
				addJustScheduler(js,job,scheduletype);
		}
	}
	
	/**
	 * 停止定时任务
	 */
	public void stopJustScheduler(JustSchedule js){
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
		for(JustSchedule js : justSchedulelist){
			stopJustScheduler(js);
		}
	}
	
	/**
	 * 停止定时任务
	 */
	public void shutDownJustScheduler(List<JustSchedule> jslist) {
		for(JustSchedule js : jslist){
			stopJustScheduler(js);
		}
	}
}
