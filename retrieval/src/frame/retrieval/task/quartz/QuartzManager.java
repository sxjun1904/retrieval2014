package frame.retrieval.task.quartz;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
/**
 * @Title:Quartz管理类
 * 
 * @Description:
 * 
 * @Copyright: 
 * @author sxjun  2013-8-29 14:19:01
 * @version 1.00.000
 *
 */

public class QuartzManager {
	   public static SchedulerFactory sf = new StdSchedulerFactory();
	   private static String JOB_GROUP_NAME = "JobGroup";
	   private static String TRIGGER_GROUP_NAME = "TriggerGroup";
	   public final static String JUST_SCHEDULE_RETURN = "justSchedule";
	   public final static String SCHEDULE_TYPE_TRIGGER_SIMPLE = "simple";
	   public final static String SCHEDULE_TYPE_TRIGGER_CRON = "cron";
	   
	   private JustBaseSchedule justSchedule;
	   private TriggerManager triggerManager;  
	   
	   public QuartzManager(){}
	   
	   public QuartzManager(JustBaseSchedule justSchedule){
		   this.justSchedule = justSchedule;
		   this.triggerManager = new TriggerManager(justSchedule);
	   }
	  
	   
	   /**
	    *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	    * @param job     任务
	    * @throws SchedulerException
	    * @throws ParseException
	    */
	   public void addJob(Job job)throws SchedulerException, ParseException{
	       addJob(job,SCHEDULE_TYPE_TRIGGER_SIMPLE);
	   }
	   
	   /**
	    *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	    * @param job     任务
	    * @param time    时间设置，参考quartz说明文档
	    * @throws SchedulerException
	    * @throws ParseException
	    */
	   public void addJob(Job job,String scheduletype)throws SchedulerException, ParseException{
	       addJob(justSchedule.getScheduleName(),JOB_GROUP_NAME,justSchedule.getScheduleName(),TRIGGER_GROUP_NAME, job,scheduletype);
	   }
	   
	   /**
	    * 添加一个定时任务
	    * @param jobName 任务名
	    * @param jobGroupName 任务组名
	    * @param triggerName  触发器名
	    * @param triggerGroupName 触发器组名
	    * @param job     任务
	    * @throws SchedulerException
	    * @throws ParseException
	    */
	   public void addJob(String jobName,String jobGroupName,String triggerName,String triggerGroupName,Job job,String scheduletype)throws SchedulerException, ParseException{
	       Scheduler sched = sf.getScheduler();
	       JobDetail jobDetail = new JobDetail(jobName, jobGroupName, job.getClass());//任务名，任务组，任务执行类
	       jobDetail.getJobDataMap().put(JUST_SCHEDULE_RETURN, justSchedule.getTransObject());
	       Trigger trigger = null;
	       //触发器
	       if(SCHEDULE_TYPE_TRIGGER_SIMPLE.equals(scheduletype)){
	    	   trigger = (SimpleTrigger) triggerManager.getSimpleTrigger(triggerName,triggerGroupName);
		      
	       }else if(SCHEDULE_TYPE_TRIGGER_CRON.equals(scheduletype)){
	    	  /* CronTrigger  trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);//触发器名,触发器组
		       trigger.setCronExpression(time);//触发器时间设定	*/
	    	   trigger = (CronTrigger) triggerManager.getCronTrigger(triggerName,triggerGroupName);
	       }
	       sched.scheduleJob(jobDetail,trigger);
	       if(!sched.isShutdown())
	          sched.start();
	   }
	   
	   /**
	    * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	    * @param triggerName
	    * @param time
	    * @throws SchedulerException
	    * @throws ParseException
	    */
	   public void modifyJobTime() throws SchedulerException, ParseException{
	       modifyJobTime(justSchedule.getScheduleName());
	   }
	   
	   /**
	    * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	    * @param triggerName
	    * @param time
	    * @throws SchedulerException
	    * @throws ParseException
	    */
	   public void modifyJobTime(String triggerName) throws SchedulerException, ParseException{
	       modifyJobTime(triggerName,TRIGGER_GROUP_NAME);
	   }
	   
	   /**
	    * 修改一个任务的触发时间
	    * @param triggerName
	    * @param triggerGroupName
	    * @param time
	    * @throws SchedulerException
	    * @throws ParseException
	    */
	   public void modifyJobTime(String triggerName,String triggerGroupName)throws SchedulerException, ParseException{
	       Scheduler sched = sf.getScheduler();
	       Trigger trigger =  sched.getTrigger(triggerName,triggerGroupName);
	       if(trigger != null){
	          /* CronTrigger  ct = (CronTrigger)trigger;
	           //修改时间
	           ct.setCronExpression(time);*/
	    	   SimpleTrigger st = triggerManager.setSimpleTrigger((SimpleTrigger)trigger);
	           //重启触发器
	           //sched.resumeTrigger(triggerName,triggerGroupName);
	    	   sched.rescheduleJob(triggerName,triggerGroupName,st);
	       }
	   }
	   
	   /**
	    * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	    * @throws SchedulerException
	    */
	   public void removeJob()throws SchedulerException{
		   removeJob(justSchedule.getScheduleName());
	   }
	   
	   /**
	    * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	    * @param jobName
	    * @throws SchedulerException
	    */
	   public void removeJob(String jobName)throws SchedulerException{
	       removeJob(jobName,JOB_GROUP_NAME,jobName,TRIGGER_GROUP_NAME);
	   }
	   
	   /**
	    * 移除一个任务
	    * @param jobName
	    * @param jobGroupName
	    * @param triggerName
	    * @param triggerGroupName
	    * @throws SchedulerException
	    */
	   public void removeJob(String jobName,String jobGroupName,String triggerName,String triggerGroupName) throws SchedulerException{
	       Scheduler sched = sf.getScheduler();
	       sched.pauseTrigger(triggerName,triggerGroupName);//停止触发器
	       sched.unscheduleJob(triggerName,triggerGroupName);//移除触发器
	       sched.deleteJob(jobName,jobGroupName);//删除任务
	   }

		public JustBaseSchedule getJustSchedule() {
			return justSchedule;
		}
	
		public void setJustSchedule(JustBaseSchedule justSchedule) {
			this.justSchedule = justSchedule;
			this.triggerManager = new TriggerManager(justSchedule);
		}
		
		/**
		 * 暂停触发器
		 * @throws SchedulerException
		 */
		public void pauseTrigger() throws SchedulerException{	
			pauseTrigger(justSchedule.getScheduleName(),TRIGGER_GROUP_NAME);
		}
		
		/**
		 * 暂停触发器
		 * @param triggerName
		 * @throws SchedulerException
		 */
		public void pauseTrigger(String triggerName) throws SchedulerException{	
			pauseTrigger(triggerName,TRIGGER_GROUP_NAME);
		}
		
		/**
		 * 暂停触发器
		 * @param triggerName
		 * @param group
		 * @throws SchedulerException
		 */
		public void pauseTrigger(String triggerName,String group) throws SchedulerException{	
			Scheduler sched = sf.getScheduler();
			sched.pauseTrigger(triggerName, group);//停止触发器
		}
		
		/**
		 * 重启触发器
		 * @throws SchedulerException
		 */
		public void resumeTrigger() throws SchedulerException{	
			resumeTrigger(justSchedule.getScheduleName(),TRIGGER_GROUP_NAME);
		}
		
		/**
		 * 重启触发器
		 * @param triggerName
		 * @throws SchedulerException
		 */
		public void resumeTrigger(String triggerName) throws SchedulerException{	
			resumeTrigger(triggerName,TRIGGER_GROUP_NAME);
		}
		
		/**
		 * 重启触发器
		 * @param triggerName
		 * @param group
		 * @throws SchedulerException
		 */
		public void resumeTrigger(String triggerName,String group) throws SchedulerException{		
			Scheduler sched = sf.getScheduler();
			sched.resumeTrigger(triggerName, group);//重启触发器
		}
	   
}
