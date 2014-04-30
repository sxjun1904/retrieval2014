package frame.retrieval.test.task.quartz;

import java.text.ParseException;

import org.quartz.SchedulerException;

import frame.retrieval.task.quartz.JustBaseSchedule;
import frame.retrieval.task.quartz.QuartzManager;
import frame.retrieval.task.quartz.job.PrintJob;

public class QuartzTest {
	public static void main(String[] args) {
		JustBaseSchedule js = new JustBaseSchedule();
//		js.setFrequency("20");
//		js.setScheduleID("1");
//		js.setFrequencyUnits(JustSchedule.SCHEDULE_MINUTES);
		js.setScheduleName("quartz");
		js.setExpression("0 44 16 ? * *");
		QuartzManager qm = new QuartzManager(js);
		try {
			qm.addJob(new PrintJob(),QuartzManager.SCHEDULE_TYPE_TRIGGER_CRON);
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
