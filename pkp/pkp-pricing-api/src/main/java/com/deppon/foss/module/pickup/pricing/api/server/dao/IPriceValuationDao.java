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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IPriceValuationDao.java
 * 
 * FILE NAME        	: IPriceValuationDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.NewResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceIndustryEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;

public interface IPriceValuationDao {
	/**
	 * 
	 * @Description: 根据主键删除
	 * @author FOSSDP-sz
	 * @date 2013-3-17 下午12:00:21
	 * @param id
	 * @return
	 * @version V1.0
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * 
	 * <p>
	 * (插入一条计费规则)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	int insertSelective(PriceValuationEntity record);

	/**
	 * 
	 * <p>
	 * 复制方案 插入计费规则
	 * </p>
	 * 
	 * @author Rosanu
	 * @date 2013-09-18 上午11:15:04
	 * @param record
	 * @see
	 */
	public int insertPricingValuation(PriceValuationEntity record);

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
	 * 根据id条件查询计费规则
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	PriceValuationEntity selectByPrimaryKey(String id);

	/**
	 * 
	 * <p>
	 * 根据查询条件查询计费规则(分页)
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
	 * 修改计费规则
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-22 上午11:15:04
	 * @param record
	 * @see
	 */
	int updateValuation(PriceValuationEntity record);

	/**
	 * 
	 * <p>
	 * 根据查询条件查询计费规则(不分页)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-25 上午11:15:04
	 * @param record
	 * @see
	 */
	List<PriceValuationEntity> selectByCodition(PriceValuationEntity record);
	/**
	 * 
	 * <p>根据查询条件查询计费规则(不分页)</p> 
	 * @author sz
	 * @date 2012-12-11 下午4:27:48
	 * @param record
	 * @return
	 * @see
	 */
	List<PriceValuationEntity> queryByCodition(PriceValuationEntity record);
	
	/**
	 * 
	 * <p>根据查询条件查询计费规则(不分页)</p> 
	 * @author sz
	 * @date 2012-12-11 下午4:27:48
	 * @param record
	 * @return
	 * @see
	 */
	List<PriceValuationEntity> queryByCoditionForDownload(PriceValuationEntity record);
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
	 * <p>
	 * 根据查询条件查询计费规则(不分页)
	 * 比较特殊，因为当deptRegionId 始发地(ID)，arrvRegionId（目的地ID），goodsTypeId（货物类型ID），productId（产品ID）为空时
	 * 要判断数据库页为空（IS NULL）
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-25 上午11:15:04
	 * @param record
	 * @see
	 */
	List<PriceValuationEntity> selectByCoditionSq(PriceValuationEntity record);
	
	/**
	 * 
	 * 根据不同条件查询增值服务相关计费规则
	 * @author DP-Foss-YueHongJie
	 * @date 2012-11-3 下午7:25:07
	 */
	List<ResultProductPriceDto> searchValueAddedPricingValuationByCondition(QueryBillCacilateValueAddDto dto);
	/**
	 * 
	 * 根据不同条件查询运费相关计费信息
	 * @author DP-Foss-YueHongJie
	 * @date 2012-11-8 上午9:52:44
	 */
	List<ResultProductPriceDto> queryPriceValuationByCalculaCondition(QueryProductPriceDto dto);
	
	 /**
	  * 零担汽运查询运费计算信息
	  * @param queryProductPriceDto
	  * @return
	  */
	 List<NewResultProductPriceDto> queryPriceValuationByCodition(QueryProductPriceDto queryProductPriceDto);
	
	/**
	 * 
	 * <p>
	 * Description:根据价格方案ID删除记录<br />
	 * </p>
	 * @author 
	 * @version 0.1 2012-11-14
	 * @param pricePlanId
	 * @return
	 * int
	 */
	int deltePriceValuationByPricePlanId(String pricePlanId);
	/**
	 * 
	 * <p>
	 * Description:根据名称查询增值服务方案<br />
	 * </p>
	 * @author 
	 * @version 0.1 2013-1-9
	 * @param record
	 * @return
	 * List<PriceValuationEntity>
	 */
	List<PriceValuationEntity> selectByName(PriceValuationEntity record);
	/**
	 * 
	 * <p>根据查询条件查询汽运计费规则(不分页)</p> 
	 * @author sz
	 * @date 2012-12-11 下午4:27:48
	 * @param record
	 * @return
	 * @see
	 */
	List<PriceValuationEntity> downPricingValuationPricingAutoServerData(PriceValuationEntity record);
	/**
	 * 
	 * <p>根据查询条件查询空运计费规则(不分页)</p> 
	 * @author sz
	 * @date 2012-12-11 下午4:27:48
	 * @param record
	 * @return
	 * @see
	 */
	List<PriceValuationEntity> downPricingValuationPricingAirServerData(PriceValuationEntity record);
	/**
	 * 
	 * <p>
	 * 根据查询条件查询计费规则(不分页)
	 * 比较特殊，因为当deptRegionId 始发地(ID)，arrvRegionId（目的地ID），goodsTypeId（货物类型ID），productId（产品ID）为空时
	 * 要判断数据库页为空（IS NULL）
	 * </p>
	 * 
	 * @author sz
	 * @date 2012-5-13 上午11:15:04
	 * @param record
	 * @see
	 */
	List<PriceValuationEntity> selectByCoditionNew(PriceValuationEntity record, int start, int limit);

	/**
	 * 分页下载基础价格数据
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */ 
	List<PriceValuationEntity> downPricingValuationPricingAutoServerDataByPage(
			PriceValuationEntity entity, int start, int limited);

	/**
	 * 分页下载商品的价格数据
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
	List<PriceValuationEntity> queryByCoditionForDownloadByPage(
			PriceValuationEntity entity, int started, int limited);

	/**
	 * 分页下载商品的价格数据
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
	List<PriceValuationEntity> downPricingValuationPricingAirServerDataByPage(
			PriceValuationEntity entity, int started, int limited);
	
	/**
	 * 查询公布价分段信息
	 * @param queryProductPriceDto
	 * @return
	 */
	List<ResultPriceDto> queryResultPrice(QueryProductPriceDto queryProductPriceDto);
	/**
	 * 
	 * <p>
	 * Description:插入产品<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月21日
	 * @param product
	 * void
	 */
	void insertPriceProductEntity(PriceProductEntity product);
	/**
	 * 
	 * <p>
	 * Description:插入行业<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月21日
	 * @param product
	 * void
	 */
	void insertPriceIndustryEntity(PriceIndustryEntity industry);
	 /**
	  * 
	  * <p>
	  * Description:查询产品<br />
	  * </p>
	  * @author xx
	  * @version 0.1 2014年10月21日
	  * void
	  */
	List<PriceProductEntity> selectPriceProductEntityByValueAddedId(String valueAdddId, String tableName);
	 /**
	  * 
	  * <p>
	  * Description:查询行业<br />
	  * </p>
	  * @author xx
	  * @version 0.1 2014年10月21日
	  * void
	  */
	 List<PriceIndustryEntity> selectPriceIndustryEntityByValueAddedId(String valueAdddId, String tableName);
	 /**
	  * 
	  * <p>
	  * Description:根据id删除产品<br />
	  * </p>
	  * @author xx
	  * @version 0.1 2014年10月21日
	  * void
	  */
	void deleteProductEntityById(String id);
	 /**
	  * 
	  * <p>
	  * Description:根据id删除行业<br />
	  * </p>
	  * @author xx
	  * @version 0.1 2014年10月21日
	  * void
	  */
	 void deleteIndustryEntityById(String id);
	 /**
	 * 根据tableId查询产品list
	 */
	 List<PriceProductEntity> queryProductListByTableId(String tableId);

	List<PriceValuationEntity> queryByValuation(
			PriceValuationEntity seletpriceValuationEntity);
}