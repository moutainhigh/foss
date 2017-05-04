package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobDataAccumulationRecord;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridSchedulesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobMonitor;


public interface IJobDataAccumulationService {
 /**
  * 查询job未处理数据量
  * @param queryString
  * @return
  */
	/*long queryDataAccumulation(JobGridSchedulesEntity queryString);*/
/**
 * 记录定时任务未处理数据量
 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
 * @author 268984 
 * @date 2016-3-24 上午11:41:51
 * @param entity
 * @see
 */
/*void addDateAccumulation(JobGridSchedulesEntity entity,Date date);
*//**
 * 查询一段时间内定时任务的未处理数据量的 记录
 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
 * @author 268984 
 * @date 2016-3-24 下午5:07:51
 * @param entity
 * @param limitDate 截止日期
 * @return
 * @see
 */
List<JobDataAccumulationRecord> queryJobDataAccumulationRecord(
		JobGridSchedulesEntity entity, Date limitDate);
List<JobDataAccumulationRecord> queryAllMonitorJobRecord();
void addJobRecords(List<JobDataAccumulationRecord> record);
List<JobMonitor> queryDataAccumulationByCondition(
		JobGridSchedulesEntity entity ,int start,int limit);

}
