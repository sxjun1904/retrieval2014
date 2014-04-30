package frame.retrieval.task.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import frame.retrieval.task.quartz.QuartzManager;

public class PrintJob implements Job {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println(arg0.getJobDetail().getJobDataMap().get(QuartzManager.JUST_SCHEDULE_RETURN));
	}

}
