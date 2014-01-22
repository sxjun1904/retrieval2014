package framework.retrieval.task.quartz;

import java.util.Date;

public class JustSchedule implements java.io.Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	public static final String SCHEDULE_SECONDS = "SCHEDULE_SECONDS";
	public static final String SCHEDULE_MINUTES = "SCHEDULE_MINUTES";
	/**
	 * 调度guid
	 */
	private String scheduleID;
	/**
	 * 调度名称
	 */
	private String scheduleName;
	/**
	 * 调度间隔
	 */
	private String frequency;
	/**
	 * 间隔单位
	 */
	private String frequencyUnits;
	/**
	 * 开始执行时间
	 */
	private Date startDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	/**
	 * 执行次数
	 */
	private String execCount;
	/**
	 * 表达式	
	 */
	private String expression;
	
	/**
	 * 适配器允许运行区间
	 */
	private String runDateRegion;
	
	public JustSchedule() {
		super();
	}

	/**
	 * 设置调度id
	 * @return
	 */
	public String getScheduleID() {
		return scheduleID;
	}

	public void setScheduleID(String scheduleID) {
		this.scheduleID = scheduleID;
	}
	
	/**
	 * 设置调度名称
	 * @return
	 */
	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	/**
	 * 设置调度频率
	 * @return
	 */
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * 设置单位
	 * SCHEDULE_SECONDS 为每秒钟执行
	 * SCHEDULE_MINUTES 为没分钟执行
	 * @return
	 */
	public String getFrequencyUnits() {
		return frequencyUnits;
	}

	public void setFrequencyUnits(String frequencyUnits) {
		this.frequencyUnits = frequencyUnits;
	}

	/**
	 * 设置开始执行时间
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 设置结束执行时间
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 设置执行次数
	 * @return
	 */
	public String getExecCount() {
		return execCount;
	}

	public void setExecCount(String execCount) {
		this.execCount = execCount;
	}

	public String getRunDateRegion() {
		return runDateRegion;
	}

	public void setRunDateRegion(String runDateRegion) {
		this.runDateRegion = runDateRegion;
	}

	/**
	 * 设置表达式
	 * @return
	 */
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	
}
