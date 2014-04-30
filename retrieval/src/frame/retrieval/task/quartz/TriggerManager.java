package frame.retrieval.task.quartz;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.SimpleTrigger;

public class TriggerManager {
	private JustBaseSchedule justSchedule;
	
	public TriggerManager(){
		
	}
	
	public TriggerManager(JustBaseSchedule dataexEsbSchedule){
		this.justSchedule = dataexEsbSchedule;
	}
	
	/**
	 * 添加一个触发器(使用触发器名，触发器组名)
	 * @param triggerName
	 * @param triggerGroupName
	 * @return
	 */
	public SimpleTrigger getSimpleTrigger(String triggerName,String triggerGroupName){
		SimpleTrigger simpleTrigger= new SimpleTrigger(triggerName,triggerGroupName);
		return setSimpleTrigger(simpleTrigger);
	}
	
	public CronTrigger getCronTrigger(String triggerName,String triggerGroupName){
		CronTrigger cronTrigger= new CronTrigger(triggerName,triggerGroupName);
		return setCronTrigger(cronTrigger);
	}
	
	public CronTrigger setCronTrigger(CronTrigger cronTrigger) {
		try {
			cronTrigger.setCronExpression(justSchedule.getExpression());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cronTrigger;
	}
	
	
	/**
	 * 设置触发器
	 * @param simpleTrigger
	 * @return
	 */
	public SimpleTrigger setSimpleTrigger(SimpleTrigger simpleTrigger) {
		if(null!=justSchedule.getStartDate())//设置开始时间
			simpleTrigger.setStartTime(justSchedule.getStartDate());
		else
			simpleTrigger.setStartTime(new Date());
		if(null!=justSchedule.getEndDate())//设置结束时间
			simpleTrigger.setEndTime(justSchedule.getEndDate());
		if(StringUtils.isNotBlank(justSchedule.getExecCount()))//设置执行次数
			simpleTrigger.setRepeatCount(Integer.parseInt(justSchedule.getExecCount())-1);
		else
			simpleTrigger.setRepeatCount(-1);
		if(StringUtils.isNotBlank(justSchedule.getFrequency()))//设置执行频率
			simpleTrigger.setRepeatInterval(getInterval());
		return simpleTrigger;
	}
	/**
	 * 设置间隔时间
	 * @return
	 */
	public Long getInterval(){
		if(JustBaseSchedule.SCHEDULE_HOURS.equals(justSchedule.getFrequencyUnits()))
			return Long.decode(justSchedule.getFrequency())*60*60*1000L;
		if(JustBaseSchedule.SCHEDULE_MINUTES.equals(justSchedule.getFrequencyUnits()))
			return Long.decode(justSchedule.getFrequency())*60*1000L;
		else if(JustBaseSchedule.SCHEDULE_SECONDS.equals(justSchedule.getFrequencyUnits()))
			return Long.decode(justSchedule.getFrequency())*1000L;
		else
			return Long.decode(justSchedule.getFrequency());
	}
	
	public JustBaseSchedule getDataexEsbSchedule() {
		return justSchedule;
	}
	
	public void setDataexEsbSchedule(JustBaseSchedule justSchedule) {
		this.justSchedule = justSchedule;
	}
}
