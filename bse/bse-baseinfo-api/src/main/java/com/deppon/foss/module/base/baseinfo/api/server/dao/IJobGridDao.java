/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridClusterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridLoggingsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridSchedulesEntity;

/**
 * 任务监控DAO接口
 * @author FOSS-DPAP-曾宪涛
 * @version V1.0 2013-03-23
 */
public interface IJobGridDao {

    /**
     * 插入一笔作业对象 信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	public int insertJobGridSchedule(JobGridSchedulesEntity entity);

    /**
     * 更新一笔作业对象 信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	public int updateJobGridSchedule(JobGridSchedulesEntity entity);
	
    /**
     * 更新一笔作业对象调度信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	public int updateActiveOfJobGridSchedule(String id,String blongModule, Integer active);
	
    /**
     * 根据ID获取作业对象信息
     * @author 曾宪涛
     * @param string 
     * @date 2013-03-23
     * @param id, active
     * @return
     */
	public JobGridSchedulesEntity getJobGridScheduleById(String id, String string);
	
    /**
     * 查询作业对象信息的总数量
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	public long getJobGridSchedulesCount(JobGridSchedulesEntity entity);
	 
    /**
     * 查询作业对象信息（分页）
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	public List<JobGridSchedulesEntity> getJobGridSchedulesRecords(JobGridSchedulesEntity entity, int offset, int limit);

    /**
     * 查询任务的历史记录信息的总数量
     * @author 曾宪涛
     * @date 2013-05-14
     * @param entity
     * @return
     */
	public long getJobGridLoggingsCount(JobGridLoggingsEntity entity);
	
    /**
     * 查询任务的历史记录信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	public List<JobGridLoggingsEntity> getJobGridLoggingsRecords(JobGridLoggingsEntity entity, int offset, int limit);
   /**
    * 查询任务分组总记录数
    * @param string
    * @return
    */
	public Long queryJobGridCluSelectorCount(String string,String blongModule);
 /**
  * 查询任务分组
  * @param queryStr
  * @param start
  * @param limit
  * @return
  */
   public List queryJobGridCluSelector(String queryStr,JobGridClusterEntity entity, int start, int limit);
 /**
  * 新增任务分组
  * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
  * @author 268984 
  * @date 2016-3-19 下午4:08:21
  * @param entity
  * @return
  * @see
  */
  public int addJobGridCluSchedule(JobGridClusterEntity entity);
 /**
  * 查询任务分组
  * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
  * @author 268984 
  * @date 2016-3-19 下午4:09:59
  * @param entity
  * @param start
  * @param limit
  * @return
  * @see
  */
  public List queryJobGridCluInfos(JobGridClusterEntity entity, int start, int limit);
 /**
  * 查询任务信息（不分组）
  * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
  * @author 268984 
  * @date 2016-3-22 上午8:59:04
  * @param entity
  * @return
  * @see
  */
 List<JobGridSchedulesEntity> getJobGridSchedulesRecords(
		JobGridSchedulesEntity entity);

}
