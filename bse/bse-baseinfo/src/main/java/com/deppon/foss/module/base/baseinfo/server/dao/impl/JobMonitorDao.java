package com.deppon.foss.module.base.baseinfo.server.dao.impl;


import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IJobMonitorDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobMonitor;


/**
 * 查询job数据积累量
 * @author 268984
 *
 */
public class JobMonitorDao extends SqlSessionDaoSupport implements IJobMonitorDao{

	private static final String NAMESPACE = "foss.qrtz.";
	
/**
 * 查询所有要监控的job的信息
 */
@SuppressWarnings("unchecked")
@Override
public List<JobMonitor> queryAllMonitorJob() {
	// TODO Auto-generated method stub
	  return   (List<JobMonitor>) this.getSqlSession().selectList(NAMESPACE+"selectAllJobMonitor");

}
/**
 * 根据条件查询
 */
@SuppressWarnings("unchecked")
@Override
public List<JobMonitor> searchMonitorJobByCondition(JobMonitor jobmonitor) {
	// TODO Auto-generated method stub
	  return   (List<JobMonitor>) this.getSqlSession().selectList(NAMESPACE+"selectJobMonitorByCondition",jobmonitor);
}
@Override
public void addMonitorJob(JobMonitor jobMonitor) {
	// TODO Auto-generated method stub
	   this.getSqlSession().insert(NAMESPACE+"addMonitorJob",jobMonitor);
}
@Override
public void modifyMonitorJob(JobMonitor jobMonitor) {
	// TODO Auto-generated method stub
	this.getSqlSession().update(NAMESPACE+"modifyMonitorJob",jobMonitor);
}
@Override
public long countMonitorJobBycondition(JobMonitor jobMonitor) {
	// TODO Auto-generated method stub
	  return   (Long) this.getSqlSession().selectOne(NAMESPACE+"countJobMonitorByCondition",jobMonitor);
}
@SuppressWarnings("unchecked")
@Override
public List<JobMonitor> searchMonitorJobByConditionByPage(
		JobMonitor jobMonitor, int start, int limit) {
	// TODO Auto-generated method stub
	RowBounds rowBounds = new RowBounds(start, limit);
	return   (List<JobMonitor>) this.getSqlSession().selectList(NAMESPACE+"selectJobMonitorByCondition",jobMonitor,rowBounds);
} 
}
