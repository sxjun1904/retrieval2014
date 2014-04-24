package com.sxjun.retrieval.pojo;

import com.sxjun.system.pojo.BasePojo;

public class JustSchedule extends BasePojo{
	private static final long serialVersionUID = 5465850670647034254L;
	
	private String scheduleName;//调度名称
	private String expression;//表达式
	private String frequencyUnits;//调度单位
	private String frequency;//调度间隔
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getFrequencyUnits() {
		return frequencyUnits;
	}
	public void setFrequencyUnits(String frequencyUnits) {
		this.frequencyUnits = frequencyUnits;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
}
