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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/FlightPricePlanDao.java
 * 
 * FILE NAME        	: FlightPricePlanDao.java
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
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightPricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.FlightPricePlanException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 航空公司代理运价方案信息DAO
 * @author DP-Foss-YueHongJie
 * @date 2012-12-25 下午4:06:35
 * @version 1.0
 */
public class FlightPricePlanDao extends SqlSessionDaoSupport implements IFlightPricePlanDao{
    //mybatis 
    private static final String NAMESPACE="com.deppon.foss.module.pickup.pricing.FlightPricePlanEntityMapper.";
    
    /**
     * 
     * <p>添加方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:10:04
     * @param entity
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao#addFlightPricePlan(com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity)
     */
    @Override
    public int addFlightPricePlan(FlightPricePlanEntity entity) {
	this.setFlightPricePlanValueAttribute(entity);
	return this.getSqlSession().insert(NAMESPACE+"insertSelective",entity);
    }

    /**
     * 
     * <p>批量删除方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:09:55
     * @param ids 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao#deleteFlightPricePlanById(java.util.List)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void deleteFlightPricePlanById(List<String> ids) {
	Map parameterMap = new HashMap();
	parameterMap.put("ids",ids);
	getSqlSession().delete(NAMESPACE+"deleteFlightPricePlanById",parameterMap);
    }

    /**
     * 
     * <p>修改方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:09:40
     * @param entity
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao#updateFlightPricePlan(com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity)
     */
    @Override
    public int updateFlightPricePlan(FlightPricePlanEntity entity) {
	this.setFlightPricePlanValueAttribute(entity);
	return getSqlSession().delete(NAMESPACE+"updateByPrimaryKeySelective",entity);
    }

    /**
     * 
     * <p>查询</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:08:44
     * @param dto
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao#queryFlightPricePlans(com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightPricePlanDto)
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public List<FlightPricePlanEntity> queryFlightPricePlans(
	    FlightPricePlanDto dto) {
	return getSqlSession().selectList(NAMESPACE+"queryFlightPricePlans", dto);
    }

    /**
     * 
     * <p>批量激活</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:08:24
     * @param ids 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao#activeFlightPricePlanByIds(java.util.List)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void activeFlightPricePlanByIds(List<String> ids) {
	Map parameterMap = new HashMap();
	parameterMap.put("ids", ids);
	getSqlSession().update(NAMESPACE+"activeFlightPricePlanByIds",parameterMap);
    }

    /**
     * 
     * <p>查询分页信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:07:58
     * @param dto
     * @param start
     * @param limit
     * @return
     * @throws FlightPricePlanException 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao#queryFlightPricePlanByEntityPagging(com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<FlightPricePlanEntity> queryFlightPricePlanByEntityPagging(
	    FlightPricePlanEntity dto, int start, int limit)
	    throws FlightPricePlanException {
	RowBounds rowBounds = new RowBounds(start,limit);
	return getSqlSession().selectList(NAMESPACE + "queryFlightPricePlanByEntityPagging",dto,rowBounds);
    }

    /**
     * 
     * <p>查询分页总数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:07:40
     * @param dto
     * @return
     * @throws FlightPricePlanException 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao#queryFlightPricePlanByEntityPaggingCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity)
     */
    @Override
    public Long queryFlightPricePlanByEntityPaggingCount(
	    FlightPricePlanEntity dto) throws FlightPricePlanException {
	return (Long) getSqlSession().selectOne(NAMESPACE + "queryFlightPricePlanByEntityPaggingCount",dto);
    }

    /**
     * 
     * <p>复制方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:07:23
     * @param flightPricePlanEntity 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao#copyFlightPricePlanEntity(com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity)
     */
    @Override
    public void copyFlightPricePlanEntity(
	    FlightPricePlanEntity flightPricePlanEntity) {
	this.setFlightPricePlanValueAttribute(flightPricePlanEntity);
	getSqlSession().selectList(NAMESPACE + "insertSelective",flightPricePlanEntity);
    }
    
    /**
     * 
     * <p>查询单个方案信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:07:09
     * @param id
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao#getFlightPricePlanEntityById(java.lang.String)
     */
    @Override
    public FlightPricePlanEntity getFlightPricePlanEntityById(String id) {
	return (FlightPricePlanEntity) getSqlSession().selectOne(NAMESPACE +"selectByPrimaryKey",id) ;
    }
    
    
    /**
     * 
     * <p>设置必要条件</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:51:26
     * @param entity
     * @see
     */
    private void setFlightPricePlanValueAttribute(FlightPricePlanEntity entity){
	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	UserEntity currentUser = FossUserContext.getCurrentUser();
	entity.setCreateUser(currentUser.getEmployee().getEmpCode());
	entity.setCreateOrgCode(currentDept.getCode());
	entity.setCreateDate(new Date());
	entity.setModifyUser(currentUser.getEmployee().getEmpCode());
	entity.setVersionNo(new Date().getTime());
	entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
    }

    
}