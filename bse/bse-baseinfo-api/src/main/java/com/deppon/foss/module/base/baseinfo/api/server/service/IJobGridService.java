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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridClusterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridLoggingsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridSchedulesEntity;

/**
 * 任务管理Service接口
 * @author DPAP
 * @date 2013-03-23
 */
public interface IJobGridService extends IService {
    
    /**
     * 添加作业对象 信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	 public int addJobGridSchedule(JobGridSchedulesEntity entity);
	 
    /**
     * 修改一笔作业对象 信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	 public int modifyJobGridSchedule(JobGridSchedulesEntity entity);
	 
    /**
     * 修改一笔作业对象 信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	 public int modifyActiveOfJobGridSchedule(String id, String blongModule,Integer active);

    /**
     * 根据ID获取作业对象信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param id
     * @return
     */
	public JobGridSchedulesEntity getJobGridScheduleById(String id,String blongModule );
	
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
       * 查询任务分组
       * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
       * @author 268984 
       * @date 2016-3-19 下午3:51:26
       * @param selectorParam
     * @param jobGridClusterEntity 
       * @param start
       * @param limit
       * @return
       * @see
       */
	public List<JobGridClusterEntity> queryJobGridCluSelector(
			String selectorParam, JobGridClusterEntity jobGridClusterEntity, int start, int limit);
    /**
     * 统计任务分组总数
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-3-19 下午3:55:31
     * @param queryStr
     * @return
     * @see
     */
	public long queryJobGridCluSelectorCount(String queryStr,String blongModule);
   /**
    * 新增任务分组
    * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
    * @author 268984 
    * @date 2016-3-19 下午4:05:52
    * @param entity
    * @return
    * @see
    */
	public int addJobGridCluSchedule(JobGridClusterEntity entity);
   /**
    * 查询任务分组（精确）
    * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
    * @author 268984 
    * @date 2016-3-19 下午4:06:50
    * @param entity
    * @param start
    * @param limit
    * @return
    * @see
    */
   List queryJobGridCluInfos(JobGridClusterEntity entity, int start, int limit);


}
