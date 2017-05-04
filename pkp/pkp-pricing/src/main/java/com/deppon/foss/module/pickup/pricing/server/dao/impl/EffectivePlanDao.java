/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/EffectivePlanDao.java
 * 
 * FILE NAME        	: EffectivePlanDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanEntity;


/**
 * 
 * 时效批次管理数据 用于时效部门设定时效主方案信息
 * 每一个主方案信息以某个出发区域为准
 * @author DP-Foss-YueHongJie
 * @date 2012-11-10 上午9:30:35
 */
public class EffectivePlanDao extends SqlSessionDaoSupport implements IEffectivePlanDao{
    //mapper空间名
    private static final String NAME_SPASE="foss.pricing.EffectivePlanEntityMapper.";    						   
    //新增
    private static final  String INSERT_SELECTIVE = "insertSelective";
    //删除
    private static final  String DELETE = "deleteByPrimaryKey";
    //修改
    private static final  String UPDATE_SELECTIVE = "updateByPrimaryKeySelective";
    //查询时效唯一时效信息
    private static final  String QUERY_BY_PRIMARY_KEY = "selectByPrimaryKey";
    private static final  String FIND_EFFECTIVE_PLANBY_REGIONID = "findEffectivePlanByRegionId";
    //查询分页
    private static final  String SEARCH_EFFECTIVE_PLAN_BY_CONDITION = "searchEffectivePlanByCondition";
    //查询分页总数
    private static final  String SEARCH_EFFECTIVE_PLAN_BY_CONDITION_COUNT = "searchEffectivePlanByConditionCount";
    //批量删除时效
    private static final  String DELETE_EFFECTIVE_PLAN_BY_IDS = "deleteEffectivePlanByIds";
    //批量激活时效
    private static final  String ACTIVE_EFFECTIVE_PLAN_BY_IDS = "activeEffectivePlanByIds";
    
    /**
     * 
     * <p>删除单个时效方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:27:30
     * @param id
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao#deleteByPrimaryKey(java.lang.String)
     */
    @Override
    public int deleteByPrimaryKey(String id) {
	return getSqlSession().delete(NAME_SPASE + DELETE, id);
    }

    /**
     * 
     * <p>添加时效方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:27:41
     * @param record
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao#insertSelective(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanEntity)
     */
    @Override
    public int insertSelective(EffectivePlanEntity record) {
	this.setEffectivePlanValuesAttribute(record);
	return getSqlSession().insert(NAME_SPASE + INSERT_SELECTIVE, record);
    }

    /**
     * 
     * <p>查询单个时效方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:27:55
     * @param id
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao#selectByPrimaryKey(java.lang.String)
     */
    @Override
    public EffectivePlanEntity selectByPrimaryKey(String id) {
	String sql = NAME_SPASE + QUERY_BY_PRIMARY_KEY;
	return (EffectivePlanEntity)this.getSqlSession().selectOne(sql,id);
    }

    /**
     * 
     * <p>修改时效方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:28:08
     * @param record
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanEntity)
     */
    @Override
    public int updateByPrimaryKeySelective(EffectivePlanEntity record) {
    	String sql =  NAME_SPASE + UPDATE_SELECTIVE; 
    	return getSqlSession().update(sql, record);
    }
    
 
    public EffectivePlanEntity findEffectivePlanByRegionId(Date cuurentTime,String active,
	    String deptRegionId) { 
	EffectivePlanEntity effectivePlanEntity = new EffectivePlanEntity();
	effectivePlanEntity.setBusinessDate(cuurentTime); //业务日期
	effectivePlanEntity.setDeptRegionId(deptRegionId);//始发区域ID
	effectivePlanEntity.setActive(active);//有效数据
	String sql = NAME_SPASE + FIND_EFFECTIVE_PLANBY_REGIONID;
	return (EffectivePlanEntity) this.getSqlSession().selectOne(sql,effectivePlanEntity);
    }

    /**
     * 
     * <p>分页查询时效方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:29:00
     * @param effectivePlanEntity
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao#searchEffectivePlanByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EffectivePlanEntity> searchEffectivePlanByCondition(
	    EffectivePlanEntity effectivePlanEntity, int start, int limit) {
	RowBounds rowBounds = new RowBounds(start, limit);
	String sql = NAME_SPASE + SEARCH_EFFECTIVE_PLAN_BY_CONDITION;
	return  getSqlSession().selectList(sql,effectivePlanEntity,rowBounds);
    }
    
    /**
     * 
     * <p>查询时效方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:28:23
     * @param effectivePlanEntity
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao#searchEffectivePlanByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EffectivePlanEntity> searchEffectivePlanByCondition(EffectivePlanEntity effectivePlanEntity) {
	String sql = NAME_SPASE + SEARCH_EFFECTIVE_PLAN_BY_CONDITION;
	return  getSqlSession().selectList(sql,effectivePlanEntity);
    }

    
    /**
     * 
     * <p>查询时效总数信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:28:35
     * @param effectivePlanEntity
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao#searchEffectivePlanByConditionCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanEntity)
     */
    @Override
    public Long searchEffectivePlanByConditionCount(
	    EffectivePlanEntity effectivePlanEntity) {
	String sql = NAME_SPASE + SEARCH_EFFECTIVE_PLAN_BY_CONDITION_COUNT;
	return (Long) getSqlSession().selectOne(sql, effectivePlanEntity);
    }

    /**
     * 
     * <p>批量删除时效</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:31:49
     * @param ids 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao#deleteEffectivePlanByIds(java.util.List)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void deleteEffectivePlanByIds(List<String> ids) {
	String sql = NAME_SPASE + DELETE_EFFECTIVE_PLAN_BY_IDS;
	HashMap parameterMap = new HashMap();
	parameterMap.put("ids", ids);
	getSqlSession().delete(sql,parameterMap);
    }

    /**
     * 
     * <p>批量激活时效</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:32:02
     * @param ids 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao#activeEffectivePlanByIds(java.util.List)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void activeEffectivePlanByIds(List<String> ids) {
	String sql = NAME_SPASE + ACTIVE_EFFECTIVE_PLAN_BY_IDS;
	HashMap parameterMap = new HashMap();
	parameterMap.put("ids", ids);
	getSqlSession().delete(sql,parameterMap);
    }
    
    /**
     * 
     * <p>设置必要参数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:51:26
     * @param entity
     * @see
     */
    private void setEffectivePlanValuesAttribute(EffectivePlanEntity entity){
	Date currentDate = new Date();
	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	UserEntity currentUser = FossUserContext.getCurrentUser();
	entity.setCreateUser(currentUser.getEmployee().getEmpCode());
	entity.setCreateOrgCode(currentDept.getCode());
	entity.setModifyUser(currentUser.getEmployee().getEmpCode());
	entity.setVersionInfo(String.valueOf(currentDate.getTime()));
	entity.setVersionNo(currentDate.getTime());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EffectivePlanEntity> searchEffectivePlanByName(
	    EffectivePlanEntity effectivePlanEntity) {
	return getSqlSession().selectList("searchEffectivePlanByName",effectivePlanEntity);
    }
}