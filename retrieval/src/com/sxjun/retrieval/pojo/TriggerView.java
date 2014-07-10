/**
 *  code generation
 */
package com.sxjun.retrieval.pojo;

import com.sxjun.system.pojo.BasePojo;

/**
 * 线程监控Entity
 * @author sxjun
 * @version 2014-07-10
 */
public class TriggerView extends BasePojo{
	private String fullJobName; 	
	private String jobName; 	
	private String fullName; 	
	private String name; 	
	private String startTime; 	
	private String nextFireTime; 	
	private String classSimpleName;
	
	public String getFullJobName() {
		return fullJobName;
	}
	public void setFullJobName(String fullJobName) {
		this.fullJobName = fullJobName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public String getClassSimpleName() {
		return classSimpleName;
	}
	public void setClassSimpleName(String classSimpleName) {
		this.classSimpleName = classSimpleName;
	}
}


