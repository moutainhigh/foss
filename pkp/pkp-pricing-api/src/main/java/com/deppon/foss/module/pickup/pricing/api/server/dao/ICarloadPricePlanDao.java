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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IPricePlanDao.java
 * 
 * FILE NAME        	: IPricePlanDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;
import java.util.List;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceOrgEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;

/**
 * 
 * TODO(价格主方案)
 * @author 潘国仰
 * @date 2014-04-08 上午10:42:51
 * @since
 * @version
 */
public interface ICarloadPricePlanDao {
   
    /**
     * 
     * queryCarloadPricePlanBatchInfoCount(查询整车价格参数方案)
     * @author DP-Foss-PanGuoYang
     * @param pricePlanEntity
     * @return Long
     */
	List<CarloadPricePlanDto> queryCarloadPricePlanBatchInfo(
			CarloadPricePlanDto carloadPricePlanDto, int start, int limit);
	
	/**
     * 
     * queryCarloadPricePlanBatchInfoCount(查询整车价格参数方案总记录数)
     * @author DP-Foss-PanGuoYang
     * @param pricePlanEntity
     * @return Long
     */
	Long queryCarloadPricePlanBatchInfoCount(
			CarloadPricePlanDto carloadPricePlanDto);
	/**
     * 
     * queryCarloadPricePlanBatchInfo(查询整车价格参数方案)
     * @author DP-Foss-PanGuoYang
     * @param pricePlanEntity
     * @return Long
     */
	List<CarloadPriceEntity> queryCarloadPricePlanBatchInfo(
			CarloadPriceEntity queryBean);
	
	/**
     * 
     * insertCarlaodPrice(新整车价格参数方案)
     * @author DP-Foss-PanGuoYang
     * @param pricePlanEntity
     * @return Long
     */
	int insertCarlaodPrice(CarloadPriceEntity carloadPriceEntity);
	
	/**
     * 
     * searchCarloadByPrimaryKey(通过id查询整车价格参数方案)
     * @author DP-Foss-PanGuoYang
     * @param pricePlanEntity
     * @return Long
     */
	CarloadPriceEntity searchCarloadByPrimaryKey(String id);
	
	/**
     * 
     * insertCarlaodPriceOrg(新整车价格参数方案与组织关系)
     * @author DP-Foss-PanGuoYang
     * @param pricePlanEntity
     * @return Long
     */
	void insertCarlaodPriceOrg(CarloadPriceOrgEntity carloadPriceOrgEntity);
	
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
	void insertCarloadPriceDetail(
			CarloadPriceDetailEntity carloadPriceDetailEntity);
	
	/**
     * 
     * <p>(通过整车方案id查询整车价格参数方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-9 上午10:04:06
     * 
     * @return
     * 
     * @see
     */
	List<CarloadPriceDetailEntity> searchCarloadPriceDetailByKey(
			String carloadPriceId);

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
	int updateCarloadPriceDetailPlan(
			CarloadPriceDetailEntity carloadPriceDetailEntity);


	/**
     * 
     * <p>(删除所选择整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	void deleteCarloadPricePlanDetail(String id);

	/**
     * 
     * <p>(通过方案ID查询整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	CarloadPriceDetailEntity selectCarloadPricePlanDetailByplanID(String planID);

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
	List<CarloadPriceOrgEntity> searchCarloadPriceOrglist(String id);

	/**
     * 
     * <p>(查询部门是否已存在激活的整车参数方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	CarloadPricePlanDto searchCarloadPricePlanDto(CarloadPricePlanDto dto);

	/**
     * 
     * <p>(激活的整车参数方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	void updatecarloadPlan(CarloadPriceEntity actionPlan);


	/**
     * <p>(删除整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	void deleteCarloadPricePlanDetailByPlanId(String id);


	/**
     * <p>(删除整车价格参数波动方案适应的部门)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	void deleteCarloadPricePlanOrgByPlanId(String id);


	/**
     * <p>(删除整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	void deleteCarloadPricePlanByPlanId(String id);
	
	/**
     * <p>(查询同一发票标记类型的整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	CarloadPriceDetailEntity searchCarloadPriceDetail(
			CarloadPriceDetailEntity carloadPriceDetailEntity);
	//分页查询
	List<CarloadPriceEntity> queryCarloadPricePlanList(
			CarloadPricePlanDto carloadPricePlanDto, int start, int limit);
	//分页
	Long queryCarloadPricePlanCount(CarloadPricePlanDto carloadPricePlanDto);
	
	/**
     * <p>(查询同一发票标记类型的整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return List<CarloadPricePlanDto>
     * 
     * @see
     */
	List<CarloadPricePlanDto> exportCarloadPricePlanDetail(
			CarloadPricePlanDto carloadPricePlanDto);
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
	public List<CarloadPricePlanDto> selectCarloadPricePlanDetailByOrganizationCode(CarloadPricePlanDto carloadPricePlanDto);
	
    
}