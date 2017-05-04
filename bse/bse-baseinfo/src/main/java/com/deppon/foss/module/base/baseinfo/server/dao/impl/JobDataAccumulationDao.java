package com.deppon.foss.module.base.baseinfo.server.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IJobDataAccumulationDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobDataAccumulationRecord;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridSchedulesEntity;

/**
 * 查询job数据积累量
 * @author 268984
 *
 */
public class JobDataAccumulationDao extends SqlSessionDaoSupport implements IJobDataAccumulationDao{

	private static final String NAMESPACE = "foss.qrtz.";
	
	
	
	
	/**
	 * 根据条件查询
	 * @param entity
	 * @return
	 */
	@Override
	public long queryDataAccumulation(String queryString) {	
			  HashMap<String, String> map  = new HashMap<String,String>();
			  map.put("sql", queryString);
		  return   (Long) this.getSqlSession().selectOne("foss.qrtz.QueryBySql",map);
}

   /**
    * 记录定时任务未处理数据量
    * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
    * @author 268984 
    * @date 2016-3-24 上午11:45:42
    * @param entity
    * @param date 
    * @see com.deppon.foss.module.base.qrtz.server.dao.IJobDataAccumulationDao#addDateAcculation(com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridSchedulesEntity, java.util.Date)
    */
	@Override
	public void addDataAcculation(JobDataAccumulationRecord record) {
		this.getSqlSession().insert(NAMESPACE+"addDataAccumulationRecord",record);	
	}

@SuppressWarnings("unchecked")
@Override
public List<JobDataAccumulationRecord> queryJobDataAccumulationRecord(
		JobGridSchedulesEntity entity, Date limitDate) {
	Map<String ,Object> map = new HashMap();
	map.put("jobGroup", entity.getJobGroup());
	map.put("jobName",entity.getJobName() );
	map.put("blongModule",entity.getBlongModule());
	map.put("limitDate",limitDate);
	return this.getSqlSession().selectList(NAMESPACE+"jobDataAcculation.queryDataAcculationRecord",map);	

} 
}
