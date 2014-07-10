package frame.retrieval.test.task.quartz;

import java.util.List;

import org.quartz.Trigger;

import frame.base.core.util.DateTime;
import frame.retrieval.task.quartz.JustBaseSchedule;
import frame.retrieval.task.quartz.JustBaseSchedulerManage;
import frame.retrieval.task.quartz.job.PrintJob;


public class QuartzTest {
	public static void main(String[] args){
		for(int i = 0;i<3;i++){
			JustBaseSchedule js = new JustBaseSchedule();
			js.setFrequency("1");
			js.setScheduleID(String.valueOf(i));
			js.setFrequencyUnits(JustBaseSchedule.SCHEDULE_SECONDS);
			js.setScheduleName("quartz"+String.valueOf(i));
//			js.setExpression("0 44 16 ? * *");
			js.setTransObject("ni da ye"+String.valueOf(i));
//			QuartzManager qm = new QuartzManager(js);
			JustBaseSchedulerManage jsm = new JustBaseSchedulerManage(js);
			try {
//				qm.addJob(new PrintJob(),QuartzManager.SCHEDULE_TYPE_TRIGGER_SIMPLE);
				jsm.startUpJustScheduler(new PrintJob());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
//			QuartzManager qm = new QuartzManager();
//			删除触发器
//			qm.removeJob("quartz1");
//			暂停触发器
//			qm.pauseTrigger("quartz1");
			
			JustBaseSchedulerManage jsm = new JustBaseSchedulerManage();
//			jsm.stopJustScheduler("quartz1");
			jsm.pauseJustScheduler("quartz1");
			
			while (true){
				List<Trigger> tgList =  jsm.getTriggers();
				for (Trigger tg : tgList) {
					System.out.println("FullJobName-->"+tg.getFullJobName()+"\r\n"
							+"JobName-->"+tg.getJobName()+"\r\n"
							+"FullName-->"+tg.getFullName()+"\r\n"
							+"Name-->"+tg.getName()+"\r\n"
							+"FireInstanceId-->"+tg.getFireInstanceId()+"\r\n"
							+"StartTime-->"+new DateTime().parseString(tg.getStartTime(),null)+"\r\n"
							+"NextFireTime-->"+new DateTime().parseString(tg.getNextFireTime(),null)+"\r\n"
							+"Class.SimpleName-->"+tg.getClass().getSimpleName()+"\r\n"
							);
				}

//				启用触发器
//				qm.resumeTrigger("quartz1");

				Thread.sleep(20000);
				jsm.resumeJustScheduler("quartz1");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
