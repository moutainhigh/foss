package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridClusterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridLoggingsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridSchedulesEntity;

public class JobGridVo implements Serializable {

	private static final long serialVersionUID = 8406555223816776082L;
	private String selectorParam;
	private List<JobGridSchedulesEntity> jobGridSchedulesEntityList;
	private JobGridClusterEntity jobGridClusterEntity;/**任务分组**/
	private List<JobGridLoggingsEntity> jobGridLoggingsEntityList;
    private List<JobGridClusterEntity> JobGridClusterEntityList;
    private String active;
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	private JobGridSchedulesEntity jobGridSchedulesEntity = new JobGridSchedulesEntity();
	
	private JobGridLoggingsEntity jobGridLoggingsEntity = new JobGridLoggingsEntity();

	public List<JobGridSchedulesEntity> getJobGridSchedulesEntityList() {
		return jobGridSchedulesEntityList;
	}

	public void setJobGridSchedulesEntityList(
			List<JobGridSchedulesEntity> jobGridSchedulesEntityList) {
		this.jobGridSchedulesEntityList = jobGridSchedulesEntityList;
	}

	public List<JobGridLoggingsEntity> getJobGridLoggingsEntityList() {
		return jobGridLoggingsEntityList;
	}
	
	public void setJobGridLoggingsEntityList(
			List<JobGridLoggingsEntity> jobGridLoggingsEntityList) {
		this.jobGridLoggingsEntityList = jobGridLoggingsEntityList;
	}

	public JobGridSchedulesEntity getJobGridSchedulesEntity() {
		return jobGridSchedulesEntity;
	}

	public void setJobGridSchedulesEntity(
			JobGridSchedulesEntity jobGridSchedulesEntity) {
		this.jobGridSchedulesEntity = jobGridSchedulesEntity;
	}

	public JobGridLoggingsEntity getJobGridLoggingsEntity() {
		return jobGridLoggingsEntity;
	}

	public String getSelectorParam() {
		return selectorParam;
	}

	public void setSelectorParam(String selectorParam) {
		this.selectorParam = selectorParam;
	}

	public List<JobGridClusterEntity> getJobGridClusterEntityList() {
		return JobGridClusterEntityList;
	}

	public void setJobGridClusterEntityList(
			List<JobGridClusterEntity> jobGridClusterEntityList) {
		JobGridClusterEntityList = jobGridClusterEntityList;
	}

	public JobGridClusterEntity getJobGridClusterEntity() {
		return jobGridClusterEntity;
	}

	public void setJobGridClusterEntity(JobGridClusterEntity jobGridClusterEntity) {
		this.jobGridClusterEntity = jobGridClusterEntity;
	}

	public void setJobGridLoggingsEntity(JobGridLoggingsEntity jobGridLoggingsEntity) {
		this.jobGridLoggingsEntity = jobGridLoggingsEntity;
	}

	
}
