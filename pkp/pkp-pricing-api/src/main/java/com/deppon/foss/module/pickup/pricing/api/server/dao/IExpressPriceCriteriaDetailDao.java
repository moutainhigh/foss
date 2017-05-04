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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IPriceCriteriaDetailDao.java
 * 
 * FILE NAME        	: IPriceCriteriaDetailDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;

/**
 * 
 * (计价方式明细,负责对所有相关价格明细管理)
 * 
 * @author 岳洪杰
 * @date 2012-10-13 上午10:54:06
 * @since
 * @version
 */
public interface IExpressPriceCriteriaDetailDao {
 
	/**
	 * 
	 * <p>删除单个计价明细</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-24 下午7:36:27
	 * @param id
	 * @return
	 * @see
	 */
	int deleteByPrimaryKey(String id);


	/**
	 * 
	 * <p>
	 * (插入一条计价方式明细)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	int insertSelective(PriceCriteriaDetailEntity record);
	
	/**
	 * 
	 * <p>
	 * (原装插入)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	void copyOriginalSelective(PriceCriteriaDetailEntity record);

	/**
	 * 
	 * <p>
	 * (根据计费过则ID查询计价方式明细列表)
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-20 上午11:15:04
	 * @param record
	 * @see
	 */
	List<PriceCriteriaDetailEntity> selectByValuationId(String valuationId);
	
	/**
	 * 
	 * <p>
	 * (根据计费过则ID查询计价方式明细列表--快递价格方案复制)
	 * </p>
	 * 
	 * @author 王帅
	 * @date 2016年1月14日11:38:57
	 * @param record
	 * @see
	 */
	List<PriceCriteriaDetailEntity> selectByValuationIdForExpressPriceCopy(String valuationId);
	
	/**
	 * <p>根据计费规则ID查询计价方式明细列表 (按计价左区间升序查询)</p> 
	 * @author 094463-foss-xieyantao
	 * @date 2013-8-8 下午4:38:13
	 * @param valuationId
	 * @return
	 * @see
	 */
	List<PriceCriteriaDetailEntity> queryInfosByParentId(String valuationId);
	
	

	/**
	 * 
	 * @Description: 根据主键查询
	 * @author FOSSDP-sz
	 * @date 2013-3-17 上午11:46:09
	 * @param id
	 * @return
	 * @version V1.0
	 */
	PriceCriteriaDetailEntity selectByPrimaryKey(String id);
	
	/**
	 * 
	 * <p>(根据主键集合ID查询相关计价明细实体信息)</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-14 下午5:49:17
	 * @param keys
	 * @return
	 * @see
	 */
	List<PriceCriteriaDetailEntity> selectByPrimaryKeyList(List<String> keys);

	/**
	 * 
	 * <p>
	 * 修改计价方式明细，查询条件是ID
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-22 上午11:15:04
	 * @param record
	 * @see
	 */
	int updateCriteriaDetailByPrimaryKey(PriceCriteriaDetailEntity record);
	/**
	 * 
	 * <p>
	 * 根据计费规则ID修改计价方式明细，查询条件是ID
	 * </p>
	 * 
	 * @author Rosanu
	 * @date 2012-10-22 上午11:15:04
	 * @param record
	 * @see
	 */
	public int updateCriteriaDetailByPricingValuationId(PriceCriteriaDetailEntity record);
	
	/**
	 * 
	 * <p>
	 * 根据计费规则ID激活计价规则明细
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-24 上午11:15:04
	 * @param record
	 * @see
	 */
	void activeCriteriaDetailByValuationIds(List<String> valuationIds);

	/**
	 * 
	 * <p>
	 * (查询各种费用明细)
	 * </p>
	 * 
	 * @author 岳洪杰
	 * @date 2012-10-13 上午11:15:04
	 * @param entity 计价方式明细实体
	 * @see
	 */
	List<PriceCriteriaDetailEntity> findPriceCriteriaDetailByCondition(PriceCriteriaDetailEntity entity);
	
	/**
	 * 
	 * <p>
	 * 根据计费规则ID删除计价规则明细
	 * </p>
	 * 
	 * @author 张斌
	 * @date 2012-10-25 上午11:15:04
	 * @param record
	 * @see
	 */
	void deleteCriteriaDetailByValuationIds(List<String> valuationIds);

	/**
	 * 根据计费规则ID删除计价规则明细
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author sz
	 * @date 2012-12-7 下午5:25:25
	 * @param valuationId
	 * @see
	 */
	void deleteCriteriaDetailByValuationId(String valuationId);
	/**
	 * 
	 * @Description: 数据下载专用
	 * @author FOSSDP-sz
	 * @date 2013-4-10 下午6:09:44
	 * @param entity
	 * @return
	 * @version V1.0
	 */
	List<PriceCriteriaDetailEntity> downLoadPriceCriteriaDetailByCondition(PriceValuationEntity entity);
}