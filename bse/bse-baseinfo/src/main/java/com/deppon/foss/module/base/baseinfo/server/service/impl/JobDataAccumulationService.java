package com.deppon.foss.module.base.baseinfo.server.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IJobDataAccumulationDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IJobMonitorDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IJobDataAccumulationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobDataAccumulationRecord;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridSchedulesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobMonitor;

/**
 * 查询job数据积累量
 * @author 268984
 *
 */
public class JobDataAccumulationService  implements IJobDataAccumulationService{
     @Autowired
   private IJobDataAccumulationDao jobDataAccumulationDao;
	@Autowired
	private IJobMonitorDao jobMonitorDao;
     
	/**
	 * 查询所有监控的job的未处理数据量
	 * @return
	 */
	@Override
	public List<JobDataAccumulationRecord> queryAllMonitorJobRecord(){
		List<JobMonitor> jobMonitorList  = jobMonitorDao.queryAllMonitorJob();
		List<JobDataAccumulationRecord> record = new ArrayList<JobDataAccumulationRecord>();
		for (int i = 0; i < jobMonitorList.size(); i++) {
			Date d = new Date();
			JobDataAccumulationRecord accumulation = new JobDataAccumulationRecord();
			accumulation.setCreateDate(d);
			accumulation.setJobMonitor(jobMonitorList.get(i));
			accumulation.setJobName(jobMonitorList.get(i).getJobName());
			String querySql = jobMonitorList.get(i).getQuerySql();
			Long dataAccumulation =0l;
			try{
			  dataAccumulation = jobDataAccumulationDao.queryDataAccumulation(querySql);
			}catch(Exception e){
			JobMonitor error = jobMonitorList.get(i);
			 error.setJobDesc("查询未处理数据量出错，请核对脚本是否正确！！");
			}
			accumulation.setDataAccumulation(dataAccumulation);
			record.add(accumulation);
		}
		return record;
	}
	
	/**
	 * 记录job 未处理数据量
	 * @param record
	 */
	@Override
	public void addJobRecords(List<JobDataAccumulationRecord> record){
		for (int i = 0; i < record.size(); i++) {
			jobDataAccumulationDao.addDataAcculation(record.get(i));	
		}
	}
     /**
	 * 根据条件查询,定时任务数据为处理量
	 * @param entity
	 * @return
	 */
	@Override
	public List<JobMonitor> queryDataAccumulationByCondition(JobGridSchedulesEntity entity,int start,int limit) {
		String blongModule = entity.getBlongModule();
		String jobName = entity.getJobName();
		JobMonitor jobmonitor = new JobMonitor();
		jobmonitor.setJobModule(blongModule);
		jobmonitor.setJobName(jobName);
		jobmonitor.setActive("Y");
		List<JobMonitor> jobMonitorList  = jobMonitorDao.searchMonitorJobByConditionByPage(jobmonitor, start, limit);
		for (int i = 0; i < jobMonitorList.size(); i++) {
			String querySql = jobMonitorList.get(i).getQuerySql();
			Long dataAccumulation =0l;
			try{
			  dataAccumulation = jobDataAccumulationDao.queryDataAccumulation(querySql);
			}catch(Exception e){
			JobMonitor error = jobMonitorList.get(i);
			 error.setJobName("查询未处理数据量出错，请核对脚本是否正确！！");
			}			
			jobMonitorList.get(i).setDataAccumulation(dataAccumulation);
		}
		return jobMonitorList;
	
		
	}
	
	/**
	 * 记录定时任务未处理数据量，用于job预警
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-3-24 上午11:40:30
	 * @param entity
	 * @see
	 */
	/*@Override
	public void addDateAccumulation(JobGridSchedulesEntity entity,Date date){
		if(entity==null)
			return;
		//若截止时间为空，则去当前时间前30天为截止时间
		if(date==null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, -30);
			date= calendar.getTime();
		}
		JobDataAccumulationRecord record = new JobDataAccumulationRecord();
		record.setBlongModule(entity.getBlongModule());
		record.setDataAccumulation(entity.getDataAccumulation());
		record.setJobGroup(entity.getJobGroup());
		record.setJobName(entity.getJobName());
		record.setStatDate(date.toLocaleString());
		
		jobDataAccumulationDao.addDataAcculation(record);
	} */
	/**
	 * 查询一段时间内定时任务数据积累量记录
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-3-24 下午3:28:30
	 * @return
	 * @see
	 */
	@Override
	public List<JobDataAccumulationRecord> queryJobDataAccumulationRecord(JobGridSchedulesEntity entity,Date limitDate){
		if(entity==null){
			return null;
		}
		List<JobDataAccumulationRecord> jbRecord = jobDataAccumulationDao.queryJobDataAccumulationRecord(entity,limitDate);
		return jbRecord;
	}

}
