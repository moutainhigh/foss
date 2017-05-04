package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobMonitor;


public interface IJobMonitorDao {

	/**]
	 * 查询所有要监控的job信息
	 * @return
	 */
	public List<JobMonitor> queryAllMonitorJob();
   /**
    * 根据条件查询监控的job信息
    * @param jobmonitor
    * @return
    */
	public List<JobMonitor> searchMonitorJobByCondition(JobMonitor jobmonitor);
	/**
	 * 新增任务监控
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016年5月23日 上午10:23:36
	 * @param jobMonitor
	 * @see
	 */
	public void addMonitorJob(JobMonitor jobMonitor);
	/**
	 * 修改监控任务信息
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016年5月23日 上午10:36:25
	 * @param jobMonitor
	 * @see
	 */
	public void modifyMonitorJob(JobMonitor jobMonitor);
	public long countMonitorJobBycondition(JobMonitor jobMonitor);
	public List<JobMonitor> searchMonitorJobByConditionByPage(
			JobMonitor jobMonitor, int start, int limit);
	
	
}
