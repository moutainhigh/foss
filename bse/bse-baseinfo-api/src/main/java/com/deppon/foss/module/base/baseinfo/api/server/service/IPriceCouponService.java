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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPriceDiscountService.java
 * 
 * FILE NAME        	: IPriceDiscountService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DiscountOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarketingSchemeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DetailExcelDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PriceCouponDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PriceCouponVo;

/**
 * 折扣方案服务信息
 * @author dujunhui-187862
 * @date 2014-9-24 上午8:16:26
 * @since
 * @version
 */
public interface IPriceCouponService extends IService{
	
	/**
	 * <p>根据条件查询计价条目折</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-9 下午4:44:26
	 * @return
	 * @see
	 */
	List<PriceEntity> selectPriceEntityByCodition();
	/**
	 * <p>根据条件查询价格折扣明细总数</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-8 上午10:54:13
	 * @param dto
	 * @return
	 * @see
	 */
	Long countPriceCouponByCodition(PriceCouponDto dto);
	
	/**
	 * <p>根据条件查询价格折扣明细</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-8 上午10:54:13
	 * @param dto
	 * @return
	 * @see
	 */
	List<PriceCouponDto> selectPriceCouponByCodition(PriceCouponDto dto, int start, int limit);
	/**
	 * <p>查询降价发券主方案信息</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-2 下午3:39:49
	 * @param marketEventId
	 * @see
	 */
	PriceCouponVo selectCouponProgram(String marketEventId);
	/**
	 * <p>新增降价发券方案</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-1 下午6:06:49
	 * @param marketingEventEntity
	 * @see
	 */
	PriceCouponVo addCouponProgram(MarketingSchemeEntity marketingEventEntity);
	
	/**
	 * <p>根据条件查询价格折扣方案总数</p> 
	 * @author dujunhui-187862
	 * @date 2014-9-24 上午8:16:26
	 * @return
	 * @see
	 */
	Long countPriceProgramByCodition(MarketingSchemeEntity entity);
	
	/**
	 * <p>根据条件查询降价发券方案</p> 
	 * @author dujunhui-187862
	 * @date 2014-9-24 上午8:16:26
	 * @return
	 * @see
	 */
	List<MarketingSchemeEntity> selectPriceProgramByCodition(MarketingSchemeEntity entity, int start, int limit);
	/**
	 * <p>增加折扣价格方案明细</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-9 下午4:17:49
	 * @see
	 */
	int addCouponProgramItem(List<DiscountOrgEntity> startDepts, List<DiscountOrgEntity> endDepts, PriceCouponDto dto);
	/**
	 * <p>作废降价发券方案</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-2 下午2:22:48
	 * @param marketingEventId
	 * @see
	 */
	void deleteCouponProgram(List<String>  marketingEventIds);
	/**
	 * <p>修改价格折扣方案</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-20 下午4:27:15
	 * @param marketingSchemeEntity
	 * @see
	 */
	PriceCouponVo updateCouponProgram(MarketingSchemeEntity marketingEventEntity);
	/**
	 * @Description: 立即中止折扣方案
	 * @author dujunhui-187862
	 * @date 2014-10-25 下午4:10:30
	 * @param 
	 * @version
	 */
	void terminateImmediatelyCouponProgram(String marketEventId, Date endTime);
	/**
	 * <p>立即修改价格折扣方案状态</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-25 下午4:01:15
	 * @param marketingEventEntity
	 * @see
	 */
	void activateImmediatelyCouponProgram(String marketEventId, Date beginTime);
	/**
	 * @Description: 拷贝价格折扣方案
	 * @author dujunhui-187862
	 * @date 2014-10-29 上午8:57:06
	 * @param marketEventId
	 * @return
	 * @version
	 */
	PriceCouponVo copyCouponProgram(String marketEventId);
	
	/**
	 * <p>作废降价发券方案明细</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-30 上午9:33:43
	 * @param priceValuationId
	 * @see
	 */
	void deleteCouponProgramItem(String  priceValuationId);
	
	/**
	 * <p>修改价格折扣方案明细</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-30 上午8:50:14
	 * @param 
	 * @see
	 */
	void updateCouponProgramItem(PriceCouponDto discountDto);
	/**
	 * @Description: 根据计费规则主键查询折扣明细
	 * @author dujunhui-187862
	 * @date 2014-10-29 下午4:23:06
	 * @param priceValuationId
	 * @return
	 * @version V1.0
	 */
	PriceCouponDto selectPriceCouponItemByValuationId(String priceValuationId);
	/**
	 *<p>批量插入数据</p>
	 *@author 187862-dujunhui
	 *@date 2014-10-31下午5:32:39
	 *@param excelDtos
	 *@param empCode
	 */
	int addDetailMore(List<DetailExcelDto> excelDtos,
			String empCode);
	/**
	 *<p>插入方法</p>
	 *@author 187862-dujunhui
	 *@date 2014-10-31下午5:33:21
	 *@param entity
	 *@param rowNum
	 *@return
	 */
	int addDetail(PriceCriteriaDetailEntity entity,int rowNum);
	
	/**
	 * @Description: 根据接送货参数查询折扣明细
	 * @author dujunhui-187862
	 * @date 2014-11-10 上午9:31:25
	 * @param 
	 * @return
	 * @version V1.0
	 */
	PriceCouponDto selectPriceCouponByConditionToQuery(String deptOrgId,
			String arrvOrgId,String productCode,BigDecimal weight,BigDecimal volume,
			String isPickUp,String calType,Date billTime);
}