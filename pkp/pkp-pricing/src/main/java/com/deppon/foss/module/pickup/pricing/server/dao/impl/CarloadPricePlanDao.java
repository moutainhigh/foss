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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/PricePlanDao.java
 * 
 * FILE NAME        	: PricePlanDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.ICarloadPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceOrgEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;
/**
 * 
 *  整车价格参数波动方案DAO
 * @author DP-Foss-PanGuoYang
 * @date 2014-04-08 下午3:05:57
 * @version 1.0
 */
public class CarloadPricePlanDao extends SqlSessionDaoSupport implements  ICarloadPricePlanDao{
	/**
	 * ibatis mapper namespace
	 */
    private static final String NAME_SPACE = "foss.pkp.pkp-pricing.PricePlanEntityMapper.";
   
    
    /**
     * 
     * <p>(查询整车价格参数方案)</p> 
     * @author DP-Foss-PanGuoYang
     * @date 2014-04-17 下午1:09:24
     * @param CarloadPricePlanDto
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<CarloadPricePlanDto> queryCarloadPricePlanBatchInfo(
			CarloadPricePlanDto carloadPricePlanDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(NAME_SPACE + "queryCarloadPricePlanBatchInfo", carloadPricePlanDto, rowBounds);
	}
	//分页查询
	@SuppressWarnings("unchecked")
	@Override
	public List<CarloadPriceEntity> queryCarloadPricePlanList(
			CarloadPricePlanDto carloadPricePlanDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(NAME_SPACE + "queryCarloadPricePlanList", carloadPricePlanDto, rowBounds);
	}
	
	/**
     * 
     * <p>(查询整车价格参数方案总记录数)</p> 
     * @author DP-Foss-PanGuoYang
     * @date 2014-04-17 下午1:09:24
     * @param carloadPriceEntity
     * @see
     */
	@Override
	public Long queryCarloadPricePlanBatchInfoCount(
			CarloadPricePlanDto carloadPricePlanDto) {
		return (Long) getSqlSession().selectOne(NAME_SPACE + "queryCarloadPricePlanBatchInfoCount", carloadPricePlanDto);
	}
	
	@Override
	public Long queryCarloadPricePlanCount(
			CarloadPricePlanDto carloadPricePlanDto) {
		return (Long) getSqlSession().selectOne(NAME_SPACE + "queryCarloadPricePlanCount", carloadPricePlanDto);
	}
	
	
	/**
     * 
     * queryCarloadPricePlanBatchInfo(查询整车价格参数方案)
     * @author DP-Foss-PanGuoYang
     * @param pricePlanEntity
     * @return Long
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<CarloadPriceEntity> queryCarloadPricePlanBatchInfo(
			CarloadPriceEntity queryBean) {
		return getSqlSession().selectList(NAME_SPACE + "searchCarloadPricePlanBatchInfo", queryBean);
	}

	/**
     * 
     * insertCarlaodPrice(新整车价格参数方案)
     * @author DP-Foss-PanGuoYang
     * @param pricePlanEntity
     * @return Long
     */
	@Override
	public int insertCarlaodPrice(CarloadPriceEntity carloadPriceEntity) {
		//OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
		UserEntity currentUser = FossUserContext.getCurrentUser();
		carloadPriceEntity.setCreateUser(currentUser.getEmpName());
		carloadPriceEntity.setModifyUser(currentUser.getEmpName());
		return getSqlSession().insert(NAME_SPACE + "insertCarlaodPrice",carloadPriceEntity);
		
	}
	
	/**
     * 
     * searchCarloadByPrimaryKey(通过id查询整车价格参数方案)
     * @author DP-Foss-PanGuoYang
     * @param pricePlanEntity
     * @return Long
     */
	@Override
	public CarloadPriceEntity searchCarloadByPrimaryKey(String id) {
		return (CarloadPriceEntity)getSqlSession().selectOne(NAME_SPACE + "searchCarloadByPrimaryKey", id);
	}
	
	/**
     * 
     * insertCarlaodPriceOrg(新整车价格参数方案与组织关系)
     * @author DP-Foss-PanGuoYang
     * @param pricePlanEntity
     * @return Long
     */
	@Override
	public void insertCarlaodPriceOrg(
			CarloadPriceOrgEntity carloadPriceOrgEntity) {
		getSqlSession().insert(NAME_SPACE + "insertCarlaodPriceOrg",carloadPriceOrgEntity);
		
	}
	/**
     * 
     * <p>(新增整车价格参数方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-9 上午10:04:06
     * 
     * @return
     * 
     * @see
     */
	@Override
	public void insertCarloadPriceDetail(
			CarloadPriceDetailEntity carloadPriceDetailEntity) {
		getSqlSession().insert(NAME_SPACE + "insertCarloadPriceDetail",carloadPriceDetailEntity);
		
	}
	
	/**
     * 
     * <p>(通过整车价格参数方案id查询整车价格参数方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-9 上午10:04:06
     * 
     * @return
     * 
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<CarloadPriceDetailEntity> searchCarloadPriceDetailByKey(
			String carloadPriceId) {
		return getSqlSession().selectList(NAME_SPACE + "searchCarloadPriceDetailByKey", carloadPriceId);
	}
	
	/**
     * 
     * <p>(修改整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@Override
	public int updateCarloadPriceDetailPlan(
			CarloadPriceDetailEntity carloadPriceDetailEntity) {
		return getSqlSession().update(NAME_SPACE + "updateCarloadPriceDetailPlan", carloadPriceDetailEntity);
	}
	
	/**
     * 
     * <p>(删除所选择整车价格参数方案id查询整车价格参数方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-9 上午10:04:06
     * 
     * @return
     * 
     * @see
     */
	@Override
	public void deleteCarloadPricePlanDetail(String id) {
		 getSqlSession().delete(NAME_SPACE + "deleteCarloadPricePlanDetail",id);
		
	}
	
	/**
     * 
     * <p>(id查询整车价格参数方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-9 上午10:04:06
     * 
     * @return
     * 
     * @see
     */
	@Override
	public CarloadPriceDetailEntity selectCarloadPricePlanDetailByplanID(
			String planID) {
		return (CarloadPriceDetailEntity) getSqlSession().selectOne(NAME_SPACE + "selectCarloadPricePlanDetailByplanID", planID);
	}
	/**
     * 
     * <p>(查询适合整车参数方案的部门)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<CarloadPriceOrgEntity> searchCarloadPriceOrglist(String id) {
		List<CarloadPriceOrgEntity> lists = new ArrayList<CarloadPriceOrgEntity>();
		List<CarloadPricePlanDto> dtos= getSqlSession().selectList(NAME_SPACE + "searchCarloadPriceOrglist", id);
		for(CarloadPricePlanDto dto:dtos){
			CarloadPriceOrgEntity  carloadPriceOrgEntity =new CarloadPriceOrgEntity();
			carloadPriceOrgEntity.setCarloadPriceId(id);
			carloadPriceOrgEntity.setOrganizationCode(dto.getOrganizationCode());
			carloadPriceOrgEntity.setOrganizationID(dto.getOrganizationID());
			carloadPriceOrgEntity.setOrganizationName(dto.getOrganizationName());
			lists.add(carloadPriceOrgEntity);
		}
		 return lists;
	}
	
	/**
     * <p>(查询适合整车参数方案的部门)</p> 
     * @author DP-Foss-潘国仰
     * @date 2014-04-08 下午09:03:29
     * @return String
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public CarloadPricePlanDto searchCarloadPricePlanDto(
			CarloadPricePlanDto dto) {
		  List<CarloadPricePlanDto>  cars = (List<CarloadPricePlanDto>) getSqlSession().selectList(NAME_SPACE + "searchCarloadPricePlanDto", dto);
		  if(CollectionUtils.isNotEmpty(cars)){
			  return cars.get(0);
		  }
		  return null;
	}
	
	/**
     * <p>(修改整车参数方案)</p> 
     * @author DP-Foss-潘国仰
     * @date 2014-04-08 下午09:03:29
     * @return String
     * @see
     */
	@Override
	public void updatecarloadPlan(CarloadPriceEntity actionPlan) {
		 getSqlSession().update(NAME_SPACE + "updatecarloadPlan", actionPlan);
	}

	/**
     * <p>(删除整车参数方案明细)</p> 
     * @author DP-Foss-潘国仰
     * @date 2014-04-08 下午09:03:29
     * @return String
     * @see
     */
	@Override
	public void deleteCarloadPricePlanDetailByPlanId(String id) {
		 getSqlSession().delete(NAME_SPACE + "deleteCarloadPricePlanDetailByPlanId",id);
	}
	
	/**
     * <p>(删除整车参数方案适应的部门)</p> 
     * @author DP-Foss-潘国仰
     * @date 2014-04-08 下午09:03:29
     * @return String
     * @see
     */
	@Override
	public void deleteCarloadPricePlanOrgByPlanId(String id) {
		getSqlSession().delete(NAME_SPACE + "deleteCarloadPricePlanOrgByPlanId",id);
		
	}
	
	/**
     * <p>(删除整车参数方案)</p> 
     * @author DP-Foss-潘国仰
     * @date 2014-04-08 下午09:03:29
     * @return String
     * @see
     */
	@Override
	public void deleteCarloadPricePlanByPlanId(String id) {
		getSqlSession().delete(NAME_SPACE + "deleteCarloadPricePlanByPlanId",id);
		
	}
	@Override
	public CarloadPriceDetailEntity searchCarloadPriceDetail(
			CarloadPriceDetailEntity carloadPriceDetailEntity) {
		return (CarloadPriceDetailEntity) getSqlSession().selectOne(NAME_SPACE + "searchCarloadPriceDetail",carloadPriceDetailEntity);
	}
	/**
     * <p>(导出整车参数方案明细)</p> 
     * @author DP-Foss-潘国仰
     * @date 2014-04-08 下午09:03:29
     * @return List<CarloadPricePlanDto>
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<CarloadPricePlanDto> exportCarloadPricePlanDetail(
			CarloadPricePlanDto carloadPricePlanDto) {
		return getSqlSession().selectList(NAME_SPACE + "exportCarloadPricePlanDetail", carloadPricePlanDto);
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.dao.impl.CarloadPricePlanDao.selectCarloadPricePlanDetailByOrganizationCode
	 * @Description:通过组织编码查询当前版本的整车价格波动参数方案
	 *
	 * @param carloadPricePlanDto
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-15 下午3:29:29
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-11-15    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public List<CarloadPricePlanDto> selectCarloadPricePlanDetailByOrganizationCode(
			CarloadPricePlanDto carloadPricePlanDto) {
		return getSqlSession().selectList(NAME_SPACE + "selectCarloadPricePlanDetailByOrganizationCode", carloadPricePlanDto);
	}

	
}