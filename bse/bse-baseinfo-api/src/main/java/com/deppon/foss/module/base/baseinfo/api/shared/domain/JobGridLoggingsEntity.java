package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.io.Serializable;

public class JobGridLoggingsEntity implements Serializable {

	private static final long serialVersionUID = -8113555979197943478L;
	private String id;
	private String instanceId;
	private String triggerName;				//触发器名称
	private String triggerGroup;			//触发器分组
	private String jobName;					//任务名称
	private String jobGroup;				//任务分组
	private String errorMessage;			//错误信息
	private String startTime;
	private String endTime;
	private String blongModule;
	public String getBlongModule() {
		return blongModule;
	}
	public void setBlongModule(String blongModule) {
		this.blongModule = blongModule;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroup() {
		return triggerGroup;
	}
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
