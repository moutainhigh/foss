package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobDataAccumulationRecord;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridSchedulesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobMonitor;

public class JobDataAccumulationVo implements Serializable{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	private String blongModule;
	private List<JobGridSchedulesEntity>  jobGridSchedulesEntityList;
	private List<JobMonitor> jobMonitorList;
	/**
	 * 定时任务查询条件
	 */
	private JobGridSchedulesEntity jobGridSchedulesEntity;
	/**
	 * 定时任务数据未处理量记录
	 */
	private List<JobDataAccumulationRecord> jobDataAccumulationRecord ; 
	/**
	 * 查询截止日期
	 */
	private Date limitDate;
	public Date getLimitDate() {
		return limitDate;
	}
	public List<JobMonitor> getJobMonitorList() {
		return jobMonitorList;
	}
	public void setJobMonitorList(List<JobMonitor> jobMonitorList) {
		this.jobMonitorList = jobMonitorList;
	}
	public List<JobGridSchedulesEntity> getJobGridSchedulesEntityList() {
		return jobGridSchedulesEntityList;
	}
	public void setJobGridSchedulesEntityList(
			List<JobGridSchedulesEntity> jobGridSchedulesEntityList) {
		this.jobGridSchedulesEntityList = jobGridSchedulesEntityList;
	}
	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}
	public JobGridSchedulesEntity getJobGridSchedulesEntity() {
		return jobGridSchedulesEntity;
	}
	public void setJobGridSchedulesEntity(
			JobGridSchedulesEntity jobGridSchedulesEntity) {
		this.jobGridSchedulesEntity = jobGridSchedulesEntity;
	}
	public List<JobDataAccumulationRecord> getJobDataAccumulationRecord() {
		return jobDataAccumulationRecord;
	}
	public void setJobDataAccumulationRecord(
			List<JobDataAccumulationRecord> jobDataAccumulationRecord) {
		this.jobDataAccumulationRecord = jobDataAccumulationRecord;
	}
	public String getBlongModule() {
		return blongModule;
	}
	public void setBlongModule(String blongModule) {
		this.blongModule = blongModule;
	}
}
