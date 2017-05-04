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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IJobGridDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridClusterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridLoggingsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridSchedulesEntity;

/**
 * 任务管理DAO接口实现
 * @author FOSS-DPAP-曾宪涛
 * @version V1.0 2013-03-23
 */
public class JobGridDao extends SqlSessionDaoSupport implements IJobGridDao {

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.jobGrid.";
	
    /**
     * 插入一笔作业对象 信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	@Override
	public int insertJobGridSchedule(JobGridSchedulesEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "insertJobGridSchedule", entity);
	}
	
    /**
     * 更新一笔作业对象 信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	@Override
	public int updateJobGridSchedule(JobGridSchedulesEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateJobGridSchedule", entity);
	}
	
    /**
     * 更新一笔作业对象调度状态
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	@Override
	public int updateActiveOfJobGridSchedule(String id,String blongModule ,Integer active) {
		Map<String, Object> map = new HashMap<String, Object>(NumberConstants.NUMBER_3);
		map.put("id", id);
		map.put("blongModule", blongModule);
		map.put("active", active);
		map.put("updateTime", new Date());
		return this.getSqlSession().update(NAMESPACE + "updateActiveOfJobGridSchedule", map);
	}
	
    /**
     * 根据ID获取作业对象信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param id, active
     * @return
     */
	@Override
	public JobGridSchedulesEntity getJobGridScheduleById(String id,String blongModule) {
		Map<String, String> map = new HashMap<String,String>(); 
		map.put("id", id);
		map.put("blongModule",blongModule );
		return (JobGridSchedulesEntity)this.getSqlSession().selectOne(NAMESPACE + "getJobGridScheduleById", map);
	}

    /**
     * 查询作业对象信息的总数量
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	@Override
	public long getJobGridSchedulesCount(JobGridSchedulesEntity entity) {
        Long count = (Long) getSqlSession().selectOne(NAMESPACE + "getJobGridSchedulesCount", entity);
        return count;
	}

    /**
     * 查询作业对象信息（分页）
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<JobGridSchedulesEntity> getJobGridSchedulesRecords(JobGridSchedulesEntity entity, int offset, int limit) {
		return this.getSqlSession().selectList(NAMESPACE + "getJobGridSchedulesRecords", entity, new RowBounds(offset, limit));
	}
	 /**
     * 查询作业对象信息（不分页）
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<JobGridSchedulesEntity> getJobGridSchedulesRecords(JobGridSchedulesEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE + "getJobGridSchedulesRecords", entity);
	}
    /**
     * 查询任务的历史记录信息的总数量
     * @author 曾宪涛
     * @date 2013-05-14
     * @param entity
     * @return
     */
	@Override
	public long getJobGridLoggingsCount(JobGridLoggingsEntity entity) {
        Long count = (Long) getSqlSession().selectOne(NAMESPACE + "getJobGridLoggingsCount", entity);
        return count;
	}

    /**
     * 查询任务的历史记录信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<JobGridLoggingsEntity> getJobGridLoggingsRecords(JobGridLoggingsEntity entity, 
			int offset, int limit) {
		return this.getSqlSession().selectList(NAMESPACE + "getJobGridLoggingsRecords", entity, new RowBounds(offset, limit));
	}
	/**
	 * 查询任务分组总记录数
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-3-19 下午3:39:54
	 * @param string
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IJobGridDao#queryJobGridCluSelectorCount(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Long queryJobGridCluSelectorCount(String string,String blongModule) {
		 Map map = new HashMap();
	        map.put("selectParam", string);
	        map.put("blongModule", string);
	     return (Long)getSqlSession().selectOne(NAMESPACE+"queryJobGridCluSelectorCount", map);
	}
     /**
      * 查询任务分组
      * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
      * @author 268984 
      * @date 2016-3-19 下午3:40:17
      * @param queryStr
      * @param start
      * @param limit
      * @return 
      * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IJobGridDao#queryJobGridCluSelector(java.lang.String, int, int)
      */
	@Override
	public List queryJobGridCluSelector(String queryStr,JobGridClusterEntity entity, int start, int limit) {
		    Map<String,String> map = new HashMap();
	        map.put("selectParam", queryStr);
	        if(entity!=null){
	         map.put("blongModule", entity.getBlongModule());
	        }else{
	        	 map.put("blongModule", null);
	        }
	        return getSqlSession().selectList(NAMESPACE+"queryJobGridCluSelector", map, new RowBounds(start, limit));
	   
	}
	/**
	 * 新增任务分组
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-3-19 下午4:07:40
	 * @param entity
	 * @return
	 * @see
	 */
	@Override
	 public int addJobGridCluSchedule(JobGridClusterEntity entity)
	    {
	        return getSqlSession().insert(NAMESPACE+"addJobGridCluSchedule", entity);
	    }
	 /**
	  * 查询任务分组（精确）
	  * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	  * @author 268984 
	  * @date 2016-3-19 下午4:09:34
	  * @param entity
	  * @param start
	  * @param limit
	  * @return
	  * @see
	  */
    @Override
    public List queryJobGridCluInfos(JobGridClusterEntity entity, int start, int limit)
    {
        return getSqlSession().selectList(NAMESPACE+"queryJobGridCluInfos", entity, new RowBounds(start, limit));
    }
	
}
