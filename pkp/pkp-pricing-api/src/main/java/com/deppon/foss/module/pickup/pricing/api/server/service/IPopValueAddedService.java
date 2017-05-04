package com.deppon.foss.module.pickup.pricing.api.server.service;

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

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerIndustryEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;

public interface IPopValueAddedService {
	/**
	 * 
	 * <p>
	 * Description:新增增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param priceValueAddedEntity
	 * @param valueAddedDetailList
	 * void
	 */
	 void addValueAdded(PriceValueAddedEntity priceValueAddedEntity,
			List<PriceValueAddedDetailEntity> valueAddedDetailList);

	/**
	 * 
	 * <p>
	 * Description:修改增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param priceValueAddedEntity
	 * @param addPriceValueAddedDetailEntityList
	 * @param updatericeValueAddedDetailEntityList
	 * @param deletericeValueAddedDetailEntityList
	 * void
	 */
	 void updateValueAdded(PriceValueAddedEntity priceValueAddedEntity,
			List<PriceValueAddedDetailEntity> addPriceValueAddedDetailEntityList,
			List<PriceValueAddedDetailEntity> updatericeValueAddedDetailEntityList,
			List<PriceValueAddedDetailEntity> deletericeValueAddedDetailEntityList);

	
	/**
	 * 
	 * <p>
	 * Description:修改计费规则实体<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param priceValueAddedEntity
	 * void
	 */
	 void updatePriceValueAdded(PriceValueAddedEntity priceValueAddedEntity);
	/**
	 * 
	 * <p>
	 * Description:查询增值服务类型<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param priceEntity
	 * @return
	 * List<PriceEntity>
	 */
	 List<PriceEntity> searchValueAddedType(PriceEntity priceEntity);

	/**
	 * 
	 * <p>
	 * Description:根据ID查询增值服务明细<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param valueAdddedDetailId
	 * @return
	 * List<PriceValueAddedDetailEntity>
	 */
	 List<PriceValueAddedDetailEntity> selectByValueAddedId(
			String valueAdddedId);

	/**
	 * 
	 * <p>
	 * Description:根据条件查询增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param record
	 * @param start
	 * @param limit
	 * @return
	 * List<PriceValueAddedEntity>
	 */
	List<PriceValueAddedEntity> selectByCodition(PriceValueAddedEntity record,
			int start, int limit);

	/**
	 * 
	 * <p>
	 * Description:根据条件查询增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param record
	 * @return
	 * Long
	 */
	Long countByCodition(PriceValueAddedEntity record);

	/**
	 * 
	 * <p>
	 * Description:激活增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param valueAddedIds
	 * void
	 */
	void activeValueAdded(List<String> valueAddedIds);

	/**
	 * 
	 * <p>
	 * Description:删除未激活增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param valueAddedIds
	 * void
	 */
	void deleteValueAdded(List<String> valueAddedIds);

	/**
	 * 
	 * <p>
	 * Description:查询产品条目信息<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * List<ProductItemEntity>
	 */
	List<ProductItemEntity> findProductItemByCondiction();

	/**
	 * 
	 * <p>
	 * Description:查询基础产品信息(三级产品)<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * List<ProductEntity>
	 */
	List<ProductEntity> findProductByCondition();
	
	
    /**
     * 
     * <p>
     * Description:查询货物定义信息<br />
     * </p>
     * @author xx
     * @version 0.1 2014年10月13日
     * @return
     * List<GoodsTypeEntity>
     */
     List<GoodsTypeEntity> findGoodsTypeByCondiction();
    
    /**
     * 
     * <p>
     * Description: 根据不同条件查询增值服务计价规则
     * 各种增值服务计价规则查询： 基础增值服务，产品增值服务，区域增值服务<br />
     * </p>
     * @author xx
     * @version 0.1 2014年10月13日
     * @param queryBillCacilateValueAddDto
     * @return
     * List<ResultProductPriceDto>
     */
     List<ResultProductPriceDto> searchValueAddedPricingValuationByCondition(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto);
    
    /**
     * 
     * <p>
     * Description:（筛选计费规则 - 根据条件按照异常优先级找出最合理的计费规则下的计费明细信息,优先原则为【区域增值服务】->【产品增值服务】-【基础增值服务】
    *  提供给计算增值服务接口中(IBillCaculateService.searchValueAddPriceList) - 用到该放方法<br />
    *  此方法查询为默认的查询方式，根据发货客户查询对应的二级行业，进而进行关联查询对应的增值服务信息
     * </p>
     * @author xx
     * @version 0.1 2014年10月13日
     * @param queryBillCacilateValueAddDto
     * @return
     * List<ResultProductPriceDto>
     */
     Map<String, List<ResultProductPriceDto>> siftValuationBillingRuleService(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto);
    
     
     /**
      * 
      * <p>
      * Description:（筛选计费规则 - 根据条件按照异常优先级找出最合理的计费规则下的计费明细信息,优先原则为【区域增值服务】->【产品增值服务】-【基础增值服务】
     *  提供给计算增值服务接口中(IBillCaculateService.searchValueAddPriceList) - 用到该放方法<br />
     *  此方法主要是针对默认siftValuationBillingRuleService 这个方法查询不出对应的增值信息，之后将二级行业置空，查询与二级行业无关的增值信息
      * </p>
      * @author xx
      * @version 0.1 2014年10月13日
      * @param queryBillCacilateValueAddDto
      * @return
      * List<ResultProductPriceDto>
      */
      Map<String, List<ResultProductPriceDto>> siftValuationBillingRuleServiceNoIndustry(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto);
     
     
     /**
      * 
      * <p>
      * Description:查询基础产品信息(二级产品)
	 * <br />
      * </p>
      * @author xx
      * @version 0.1 2014年10月13日
      * @return
      * List<ProductEntity>
      */
     List<ProductEntity> findTwoLevelProduct();
	/**
	 * 
	 * <p>
	 * Description:查询基础产品信息(一级产品)<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @return
	 * List<ProductEntity>
	 */
     List<ProductEntity> findOneLevelProduct();
    /**
     * 
     * <p>
     * Description:查询基础产品信息(三级产品)<br />
     * </p>
     * @author xx
     * @version 0.1 2014年10月13日
     * @return
     * List<ProductEntity>
     */
	 List<ProductEntity> findThreeLevelProduct();
	/**
	 * 
	 * <p>
	 * Description:立即激活激活增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 * @param priceValueAddedEntity
	 * void
	 */
	 void activeFast(PriceValueAddedEntity priceValueAddedEntity);
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