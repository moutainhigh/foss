package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobMonitor;


public class JobMonitorVo  implements Serializable{

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	private JobMonitor jobMonitor;
	private List<JobMonitor> jobMonitorList;
	public JobMonitor getJobMonitor() {
		return jobMonitor;
	}
	public void setJobMonitor(JobMonitor jobMonitor) {
		this.jobMonitor = jobMonitor;
	}
	public List<JobMonitor> getJobMonitorList() {
		return jobMonitorList;
	}

	public void setJobMonitorList(List<JobMonitor> jobMonitorList) {
		this.jobMonitorList = jobMonitorList;
	}
	
}
