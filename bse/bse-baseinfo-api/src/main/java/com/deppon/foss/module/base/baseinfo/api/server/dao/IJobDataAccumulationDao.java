package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobDataAccumulationRecord;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridSchedulesEntity;



public interface IJobDataAccumulationDao {
	public long queryDataAccumulation(String queryString);
    /**
     * 记录定时任务未处理数据量
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-3-24 上午11:44:51
     * @param entity
     * @param date
     * @see
     */
	public void addDataAcculation(JobDataAccumulationRecord record);
	/**
	 * 查询一段时间内定时任务的为处理数据量的记录
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-3-24 下午5:09:16
	 * @param entity
	 * @param limitDate
	 * @return
	 * @see
	 */
	public List<JobDataAccumulationRecord> queryJobDataAccumulationRecord(
			JobGridSchedulesEntity entity, Date limitDate);
}
