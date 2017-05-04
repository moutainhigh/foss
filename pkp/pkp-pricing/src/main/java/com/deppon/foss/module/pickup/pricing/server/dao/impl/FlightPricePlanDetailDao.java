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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/FlightPricePlanDetailDao.java
 * 
 * FILE NAME        	: FlightPricePlanDetailDao.java
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
import com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightPricePlanDetailDto;

/**
 * 
 * 航空公司运价方案明细管理
 * @author DP-Foss-YueHongJie
 * @date 2012-10-31 下午4:04:41
 */
public class FlightPricePlanDetailDao extends SqlSessionDaoSupport implements IFlightPricePlanDetailDao {

	/**
	 * ibatis mapper
	 */
    private static final String NAMESPACE="com.deppon.foss.module.pickup.pricing.api.server.dao.FlightPricePlanDetailEntityMapper.";
    
    /**
     * 
     * 新增航空代理公司航班运价方案明细
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 上午10:44:59
     * @return 1：成功；-1：失败
     */
    @Override
    public int addFlightPricePlanDetail(FlightPricePlanDetailEntity entity) {
	this.setFlightPricePlanDetailValueAttribute(entity);
	return this.getSqlSession().insert(NAMESPACE+"insertSelective", entity);
    }
    /**
     * 修改航空代理公司航班运价方案明细
     * @author DP-Foss-YueHongJie
     * @date 2012-10-31 上午11:03:18
     * @param entity 航空公司运价方案实体
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int updateFlightPricePlanDetail(FlightPricePlanDetailEntity entity) {
	this.setFlightPricePlanDetailValueAttribute(entity);
	return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective",entity);
    }
    /**
     * 根据传入对象查询符合条件所有空运代理公司航班运价方案信息明细
     * @author  DP-Foss-YueHongJie
     * @param entity 航空代理公司运价方案实体
     * @return 符合条件的实体列表
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public List<FlightPricePlanDetailEntity> queryFlightPricePlanDetails(
	    FlightPricePlanDetailEntity entity) {
	return this.getSqlSession().selectList(NAMESPACE+"queryFlightPricePlanDetails",entity);
    }
    /**
     * 
     * <p>查询航空运价明细分页</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:45:37
     * @param entity
     * @param start
     * @param limit
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<FlightPricePlanDetailEntity> queryFlightPricePlanDetailPagging(
	    FlightPricePlanDetailEntity entity, int start, int limit) {
	RowBounds rowBounds = new RowBounds(start,limit);
	return this.getSqlSession().selectList(NAMESPACE+"queryFlightPricePlanDetailPagging",entity,rowBounds);
    }
    /**
     * 
     * <p>查询航空运价明细总数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:45:45
     * @param entity
     * @return
     * @see
     */
    @Override
    public Long queryFlightPricePlanDetailPaggingCount(
	    FlightPricePlanDetailEntity entity) {
	return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryFlightPricePlanDetailPaggingCount",entity);
    }
    /**
     * 
     * <p>升级方案明细信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午4:03:20
     * @param flightPricePlanDetailEntity
     * @see
     */
    @Override
    public void copyFlightPricePlanDetailEntity(
	    FlightPricePlanDetailEntity flightPricePlanDetailEntity) {
	this.setFlightPricePlanDetailValueAttribute(flightPricePlanDetailEntity);
	getSqlSession().selectList(NAMESPACE + "insertSelective",flightPricePlanDetailEntity);
    }
    /**
     * 
     * <p>批量激活方案明细</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午4:06:06
     * @param ids
     * @see
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void activeFlightPricePlanDetailByIds(List<String> ids) {
	Map parameterMap = new HashMap();
	parameterMap.put("ids", ids);
	getSqlSession().update(NAMESPACE+"activeFlightPricePlanDetailByIds",parameterMap);
    }
    /**
     * 
     * <p>批量删除方案明细</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午4:07:21
     * @param ids
     * @see
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void deleteFlightPricePlanDetailById(List<String> ids) {
	Map parameterMap = new HashMap();
	parameterMap.put("ids", ids);
	getSqlSession().update(NAMESPACE+"deleteFlightPricePlanDetailById",parameterMap);
    }
    /**
     * 
     * <p>查询单个明细</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午4:26:54
     * @param id
     * @return
     * @see
     */
    @Override
    public FlightPricePlanDetailEntity queryFlightPricePlanDetailById(String id) {
	return (FlightPricePlanDetailEntity) getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey",id);
    }
    
    /**
     * 
     * <p>设置必要条件</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:51:26
     * @param entity
     * @see
     */
    private void setFlightPricePlanDetailValueAttribute(FlightPricePlanDetailEntity entity){
	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	UserEntity currentUser = FossUserContext.getCurrentUser();
	entity.setCreateUser(currentUser.getEmployee().getEmpCode());
	entity.setCreateOrgCode(currentDept.getCode());
	entity.setModifyUser(currentUser.getEmployee().getEmpCode());
	entity.setCreateDate(new Date());
	entity.setVersionNo(new Date().getTime());
    }
    /**
     * 
     * <p>查询唯一航空运价信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-1-15 下午8:11:50
     * @param flightPricePlanId 航空运价主方案信息ID
     * @param destDistictCode 目的地站
     * @param goodsTypeCode 货物类别
     * @param flightNo 航班号
     * @param active 状态
     * @param currencyCode 币种
     * @return
     * @see
     */
    @Override
    public FlightPricePlanDetailEntity queryUniquenessFlightPricePlanDetail(
	    String flightPricePlanId, String destDistictCode,
	    String goodsTypeCode, String flightNo, String active,
	    String currencyCode) {
	FlightPricePlanDetailDto detailDto = new FlightPricePlanDetailDto();
	detailDto.setFlightPricePlanId(flightPricePlanId);
	detailDto.setDestDistrictCode(destDistictCode);
	detailDto.setGoodsTypeCode(goodsTypeCode);
	detailDto.setActive(active);
	detailDto.setFlightNo(flightNo);
	detailDto.setCurrencyCode(currencyCode);
    	return (FlightPricePlanDetailEntity) this.getSqlSession().selectOne(NAMESPACE+"queryUniquenessFlightPricePlanDetail", detailDto);
    }

  
 

}