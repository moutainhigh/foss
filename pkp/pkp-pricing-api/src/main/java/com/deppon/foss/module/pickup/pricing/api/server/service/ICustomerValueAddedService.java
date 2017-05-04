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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPricingValueAddedService.java
 * 
 * FILE NAME        	: IPricingValueAddedService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerIndustryEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceFibelPaperPackingEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;

public interface ICustomerValueAddedService {
	/**
	 * .
	 * <p>
	 * 新增增值服务<br/>
	 * 方法名：addValueAdded
	 * </p>
	 * 
	 * @param priceValuationEntity
	 *            计费规则实体
	 * @param priceCriteriaDetailEntity
	 *            计价方式明细实体
	 * @author 张斌
	 * @时间 2012-10-18
	 * @since JDK1.6
	 */
	 void addValueAdded(PriceValuationEntity priceValuationEntity,
			List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityList);

	/**
	 * .
	 * <p>
	 * 修改增值服务<br/>
	 * 方法名：updateValueAdded
	 * </p>
	 * 
	 * @param priceValuationEntity
	 *            计费规则实体
	 * @param addPriceCriteriaDetailEntityList
	 *            新增计价方式明细实体
	 * @param updatePriceCriteriaDetailEntityList
	 *            修改计价方式明细实体
	 * @author 张斌
	 * @时间 2012-10-18
	 * @since JDK1.6
	 */
	 void updateValueAdded(PriceValuationEntity priceValuationEntity,
			List<PriceCriteriaDetailEntity> addPriceCriteriaDetailEntityList,
			List<PriceCriteriaDetailEntity> updatePriceCriteriaDetailEntityList,
			List<PriceCriteriaDetailEntity> deletePriceCriteriaDetailEntityList);

	
	/**
	 * .
	 * <p>
	 * 修改计费规则实体<br/>
	 * 方法名：updatePriceValuation
	 * </p>
	 * 
	 * @param priceValuationEntity
	 *            计费规则实体
	 * @author 张斌
	 * @时间 2012-10-18
	 * @since JDK1.6
	 */
	 void updatePriceValuation(PriceValuationEntity priceValuationEntity);
	/**
	 * .
	 * <p>
	 * 查询增值服务类型<br/>
	 * 方法名：searchValueAddedType
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-10-18
	 * @since JDK1.6
	 */
	 List<PriceEntity> searchValueAddedType(PriceEntity priceEntity);

	/**
	 * .
	 * <p>
	 * 根据ID查询计价方式明细<br/>
	 * 方法名：searchCriteriaDetailById
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-10-20
	 * @since JDK1.6
	 */
	 List<PriceCriteriaDetailEntity> selectByValuationId(
			String criteriaDetailId);

	/**
	 * 
	 * <p>
	 * 根据查询条件查询计费规则
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	List<PriceValuationEntity> selectByCodition(PriceValuationEntity record,
			int start, int limit);

	/**
	 * 
	 * <p>
	 * 根据查询条件统计计费规则
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	Long countByCodition(PriceValuationEntity record);

	/**
	 * 
	 * <p>
	 * (激活增值服务)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-22 上午11:15:04
	 * @param record
	 * @see
	 */
	void activeValueAdded(List<String> valuationIds);

	/**
	 * 
	 * <p>
	 * (删除未激活增值服务)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-25 上午11:15:04
	 * @param record
	 * @see
	 */
	void deleteValueAdded(List<String> valuationIds);

	/**
	 * 
	 * 查询产品条目信息-
	 * 
	 * @author DP-Foss-张斌
	 * @date 2012-10-29 下午6:47:34
	 */
	List<ProductItemEntity> findProductItemByCondiction();

	/**
	 * 
	 * 查询基础产品信息(三级产品)
	 * 
	 * @author DP-Foss-张斌
	 * @date 2012-10-29 下午6:47:34
	 */
	List<ProductEntity> findProductByCondition();
	
	
    /**
     * 
     * 查询货物定义信息
     * @author DP-Foss-张斌
     * @date 2012-10-30 下午5:13:38
     */
     List<GoodsTypeEntity> findGoodsTypeByCondiction();
    
    /**
     * 
     * 根据不同条件查询增值服务计价规则
     * 各种增值服务计价规则查询： 基础增值服务，产品增值服务，区域增值服务
     * @author DP-Foss-YueHongJie
     * @date 2012-11-3 下午2:28:56
     */
     List<ResultProductPriceDto> searchValueAddedPricingValuationByCondition(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto);
    
    /**
    * 
    * （筛选计费规则 - 根据条件按照异常优先级找出最合理的计费规则下的计费明细信息,优先原则为【区域增值服务】->【产品增值服务】-【基础增值服务】
    *  提供给计算增值服务接口中(IBillCaculateService.searchValueAddPriceList) - 用到该放方法
    *   
    * ）
    * 
    * @author DP-Foss-YueHongJie
    * @date 2012-11-7 下午12:52:16
    * @param queryBillCacilateValueAddDto 计费规则与费用明细的组合查询条件
    */
     List<ResultProductPriceDto> siftValuationBillingRuleService(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto);
	/**
	 * 
	 * 查询基础产品信息(二级产品)
	 * 
	 * @author DP-Foss-张斌
	 * @date 2012-10-29 下午6:47:34
	 */
     List<ProductEntity> findTwoLevelProduct();
	/**
	 * 
	 * 查询基础产品信息(一级产品)
	 * 
	 * @author DP-Foss-张斌
	 * @date 2012-10-29 下午6:47:34
	 */
     List<ProductEntity> findOneLevelProduct();
    /**
	 * 
	 * 查询基础产品信息(三级产品)
	 * 
	 * @author DP-Foss-张斌
	 * @date 2012-10-29 下午6:47:34
	 */
	 List<ProductEntity> findThreeLevelProduct();
	/**
	 * 
	 * <p>
	 * (立即激活激活增值服务)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2013-02-25 上午11:15:04
	 * @see
	 */
	 void activeFast(PriceValuationEntity priceValuationEntity);
	 /**
	  * 
	  * @Description: 筛选增值计费规则
	  * 
	  * @author FOSSDP-sz
	  * 
	  * @date 2013-3-26 下午7:03:44
	  * 
	  * @param queryBillCacilateValueAddDto
	  * 
	  * @return
	  * 
	  * @version V1.0
	  * 
	  */
	 Map<String, List<ResultProductPriceDto>> siftValueAddRuleService(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto);
	 
	 /**
	  * 通过id查询纸纤包装的增值信息
	  * @author:218371-foss-zhaoyanjun
	  * @date:2014-11-17
	  */
	 List<PriceFibelPaperPackingEntity> selectByValuationIdAndCode(String criteriaDetailId);
	 
	 /**
	  * 通过id查询纸纤包装的增值信息
	  * @author:218371-foss-zhaoyanjun
	  * @date:2014-11-17
	  */
	 List<PriceFibelPaperPackingEntity> selectByValuationIdAndCodeTrue(String criteriaDetailId);
	 /**
	  * 查询所有二级行业数据
	  * @param valuationId 
	  * @return
	  */
	 List<CustomerIndustryEntity> queryAllSecProfession(String valuationId);
	 /**
	  * 查询基础产品
	  */
	 List<ProductEntity> queryProductList(String valuationId);
}