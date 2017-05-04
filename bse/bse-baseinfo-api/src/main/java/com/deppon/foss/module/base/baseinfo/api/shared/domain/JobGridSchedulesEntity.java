package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class JobGridSchedulesEntity implements Serializable {

	private static final long serialVersionUID = 7639240996207067319L;
	private String id;
	private String instanceId; //任务分组所属实例id
	private String triggerName;				//触发器名称
	private String triggerGroup;			//触发器分组
	private Integer triggerType;				//触发器类型
	private String triggerExpression;		//触发器表达式
	private String triggerState;			//触发器状态
	private String jobName;					//任务名称
	private String jobGroup;				//任务分组
	private String jobClass;				//任务类
	private String jobData;					//任务数据
	private String jobDesc;			//任务描述
	private Date updateTime;				//更新时间
	private Integer active;					//执行标识
    private long   dataAccumulation ;                  //数据堆积量
    private String blongModule;//所属模块
	
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
	public Integer getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(Integer triggerType) {
		this.triggerType = triggerType;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getTriggerExpression() {
		return triggerExpression;
	}
	public void setTriggerExpression(String triggerExpression) {
		this.triggerExpression = triggerExpression;
	}
	public String getTriggerState() {
		return triggerState;
	}
	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
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
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	public long getDataAccumulation() {
		return dataAccumulation;
	}
	public void setDataAccumulation(long dataAccumulation) {
		this.dataAccumulation = dataAccumulation;
	}
	public String getJobData() {
		return jobData;
	}
	public void setJobData(String jobData) {
		this.jobData = jobData;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	
}
