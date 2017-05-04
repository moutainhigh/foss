package com.deppon.foss.module.pickup.pricing.api.server.dao;

/**
 *  initial comments.
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IPricingValueAddedDao.java
 * 
 * FILE NAME        	: IPricingValueAddedDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceIndustryEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;

public interface IPopValueAddedDao {
	/**
	 * 
	 * <p>
	 * Description: * 根据查询条件查询计费规则(不分页) 比较特殊，因为当deptRegionId
	 * 始发地(ID)，arrvRegionId（目的地ID），goodsTypeId（货物类型ID），productId（产品ID）为空时
	 * 要判断数据库页为空（IS NULL）<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月11日
	 * @param record
	 * @return List<PriceValueAddedEntity>
	 */
	List<PriceValueAddedEntity> selectByName(PriceValueAddedEntity record);
	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月11日
	 * @param record
	 * @return List<PriceValuationEntity>
	 */
	List<PriceValueAddedEntity> selectByCoditionSq(PriceValueAddedEntity record);
	
	
	 /**
	  * 
	  * <p>
	  * Description:修改增值服务<br />
	  * </p>
	  * @author xx
	  * @version 0.1 2014年10月11日
	  * @param record
	  * @return
	  * int
	  */
	public int updateValueAdded (PriceValueAddedEntity record);
	
	/**
	 * 
	 * <p>
	 * Description:插入一条增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月11日
	 * @param record
	 * @return
	 * int
	 */
	int insertSelective(PriceValueAddedEntity record);
	/**
	 * 
	 * <p>
	 * Description:根据条件查询增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param record
	 * @param start
	 * @param limit
	 * void
	 */
	List<PriceValueAddedEntity> selectByCoditionNew(PriceValueAddedEntity record ,int start, int limit);
	/**
	 * 
	 * <p>
	 * Description:根据条件统计增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param record
	 * @param start
	 * @param limit
	 * void
	 */
	Long countByCodition(PriceValueAddedEntity record);
	/**
	 * 
	 * <p>
	 * Description:激活增值<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param valueAddedIds
	 * void
	 */
	void activeValueAdded(List<String> valueAddedIds);
	/**
	 * 
	 * <p>
	 * Description:删除未激活的增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param valueAddedIds
	 * void
	 */

	void deleteValueAdded(List<String> valueAddedIds);
	/**
	 * 
	 * <p>
	 * Description:根据主键id查询<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param id
	 * @return
	 * PriceValueAddedEntity
	 */

	PriceValueAddedEntity selectByPrimaryKey(String id);
	/**
	 * 
	 * <p>
	 * Description:根据条件查询增值服务<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param record
	 * @param start
	 * @return
	 * List<PriceValueAddedEntity>
	 */
	List<PriceValueAddedEntity> selectByCodition(PriceValueAddedEntity record);
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

	List<ResultProductPriceDto> searchValueAddedPricingValuationByCondition(
			QueryBillCacilateValueAddDto queryBillCacilateValueAddDto);
	List<ResultProductPriceDto> searchValueAddedPricingValuationByConditionPic(
			QueryBillCacilateValueAddDto queryBillCacilateValueAddDto);
}