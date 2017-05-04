package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobMonitor;


public interface IJobMonitorService {

	List<JobMonitor> searchMonitorJobByCondition(JobMonitor jobmonitor);

	void addMonitorJob(JobMonitor jobMonitor);

	void modifyMonitorJob(JobMonitor jobMonitor);

	long countMonitorJobBycondition(JobMonitor jobMonitor);

	List<JobMonitor> searchMonitorJobByConditionByPage(JobMonitor jobMonitor,
			int start, int limit);

}
