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
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountOrgEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventChannelEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountParmDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceDiscountVo;


/**
 * 折扣方案服务信息
 * @author sz
 * @date 2012-12-18 下午2:58:58
 * @since
 * @version
 */
public interface IPriceDiscountService extends IService{
	
	/**
	 * <p>根据条件查询计价条目折</p> 
	 * @author sz
	 * @date 2012-12-4 下午8:22:26
	 * @param dto
	 * @return
	 * @see
	 */
	List<PriceEntity> selectPriceEntityByCodition();
	
	/**
	 * <p>根据条件查询价格折扣明细</p> 
	 * @author sz
	 * @date 2012-12-4 下午8:22:26
	 * @param dto
	 * @return
	 * @see
	 */
	List<PriceDiscountDto> selectPriceDiscountByCodition(PriceDiscountDto dto);
	
	/**
	 * <p>根据条件查询价格折扣明细总数</p> 
	 * @author sz
	 * @date 2012-12-13 下午8:56:26
	 * @param dto
	 * @return
	 * @see
	 */
	Long countPriceDiscountByCodition(PriceDiscountDto dto);
	
	/**
	 * <p>根据条件查询价格折扣明细</p> 
	 * @author sz
	 * @date 2012-12-4 下午8:22:26
	 * @param dto
	 * @return
	 * @see
	 */
	List<PriceDiscountDto> selectPriceDiscountByCodition(PriceDiscountDto dto, int start, int limit);
	/**
	 * <p>查询折扣价格方案</p> 
	 * @author sz
	 * @date 2012-12-4 下午8:19:49
	 * @param dto
	 * @see
	 */
	PriceDiscountVo selectDiscountProgram(String marketEventId);
	/**
	 * <p>新增折扣价格方案</p> 
	 * @author sz
	 * @date 2012-12-4 下午8:19:49
	 * @param dto
	 * @see
	 */
	PriceDiscountVo addDiscountProgram(MarketingEventEntity marketingEventEntity, List <MarketingEventChannelEntity> channelEntity);
	
	/**
	 * <p>根据条件查询价格折扣方案总数</p> 
	 * @author sz
	 * @date 2012-12-13 下午8:56:26
	 * @param dto
	 * @return
	 * @see
	 */
	Long countPriceProgramByCodition(MarketingEventEntity marketintEvent);
	
	/**
	 * <p>根据条件查询价格折扣方案</p> 
	 * @author sz
	 * @date 2012-12-4 下午8:22:26
	 * @param dto
	 * @return
	 * @see
	 */
	List<MarketingEventEntity> selectPriceProgramByCodition(MarketingEventEntity marketingEvent, int start, int limit);
	/**
	 * <p>增加折扣价格方案明细</p> 
	 * @author sz
	 * @date 2012-12-4 下午8:19:49
	 * @param dto
	 * @see
	 */
	void addDiscountProgramItem(List<DiscountOrgEntity> startDepts, List<DiscountOrgEntity> endDepts, PriceDiscountDto dto);
	/**
	 * <p>增加折扣价格方案明细</p> 
	 * @author sz
	 * @date 2012-12-4 下午8:19:49
	 * @param dto
	 * @see
	 */
	void addDiscountProgramItem(String startAllNet, String arrvAllNet, List<DiscountOrgEntity> startDepts, List<DiscountOrgEntity> endDepts, PriceDiscountDto dto);
	/**
	 * <p>删除价格折扣方案</p> 
	 * @author sz
	 * @date 2012-12-6 下午3:02:48
	 * @param marketingEventId
	 * @see
	 */
	void deleteDiscountProgram(List<String>  marketingEventIds);
	/**
	 * <p>修改价格折扣方案</p> 
	 * @author sz
	 * @date 2012-12-6 下午3:04:15
	 * @param marketingEventEntity
	 * @see
	 */
	void updateDiscountProgram(MarketingEventEntity marketingEventEntity, List<MarketingEventChannelEntity> eventChannelEntity);
	/**
	 * @Description: 中止折扣方案
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-19 下午8:54:30
	 * @param marketEventId
	 * @version V1.0
	 */
	void terminateDiscountProgram(String marketEventId, Date endTime);
	
	/**
	 * @Description: 立即中止折扣方案
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-19 下午8:54:30
	 * @param marketEventId
	 * @version V1.0
	 */
	void terminateImmediatelyDiscountProgram(String marketEventId, Date endTime);
	/**
	 * <p>修改价格折扣方案状态</p> 
	 * @author sz
	 * @date 2012-12-6 下午3:04:15
	 * @param marketingEventEntity
	 * @see
	 */
	void activateDiscountProgram(List<String> marketEventIds);
	
	/**
	 * <p>立即修改价格折扣方案状态</p> 
	 * @author sz
	 * @date 2012-12-6 下午3:04:15
	 * @param marketingEventEntity
	 * @see
	 */
	void activateImmediatelyDiscountProgram(String marketEventId, Date beginTime);
	/**
	 * 
	 * @Description: 拷贝价格折扣方案
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-21 下午2:56:06
	 * @param marketEventId
	 * @return
	 * @version V1.0
	 */
	PriceDiscountVo copyDiscountProgram(String marketEventId);
	
	/**
	 * <p>删除价格折扣方案明细</p> 
	 * @author sz
	 * @date 2012-12-7 下午3:56:58
	 * @param priceValuationId
	 * @see
	 */
	void deleteDiscountProgramItem(String  priceValuationId);
	
	/**
	 * <p>修改价格折扣方案明细</p> 
	 * @author sz
	 * @date 2012-12-7 下午3:57:14
	 * @param priceValuationId
	 * @see
	 */
	void updateDiscountProgramItem(PriceDiscountDto discountDto);
	/**
	 * @Description: 根据计费规则主键查询折扣明细
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-19 下午8:47:06
	 * @param priceValuationId
	 * @return
	 * @version V1.0
	 */
	PriceDiscountDto selectPriceDiscountItemByValuationId(String priceValuationId);
	/**
	 * @Description: 根据条件计算渠道折扣价格
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-19 下午3:08:59
	 * @param dto
	 * @return
	 * @version V1.0
	 */
	List<PriceDiscountDto> calculateChannelDiscount(DiscountParmDto dto);
	/**
	 * @Description: 根据条件计算产品折扣价格
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-19 下午3:08:59
	 * @param dto
	 * @return
	 * @version V1.0
	 */
	List<PriceDiscountDto> calculateProductDiscount(DiscountParmDto dto);
}