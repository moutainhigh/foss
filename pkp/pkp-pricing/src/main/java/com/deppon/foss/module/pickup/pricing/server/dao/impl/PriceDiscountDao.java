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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/PriceDiscountDao.java
 * 
 * FILE NAME        	: PriceDiscountDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceDiscountDao;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountParmDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;

/**
 * 折扣方案操作类
 * @author sz
 * @date 2012-12-5 上午9:30:05
 * @since
 * @version
 */
public class PriceDiscountDao extends SqlSessionDaoSupport implements IPriceDiscountDao {
	
	private String namespace = "foss.pricing.priceDiscountEntityMapper.";
	
	/**
	 * <p>根据条件查询价格折扣方案明细</p> 
	 * @author sz
	 * @date 2012-12-4 下午8:22:26
	 * @param dto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDiscountDto> selectPriceDiscountByCodition(PriceDiscountDto dto) {
		return getSqlSession().selectList(namespace+"selectPriceDiscountByCodition", dto);	
	}

	/**
	 * <p>根据条件查询价格折扣方案明细分页</p> 
	 * @author sz
	 * @date 2012-12-4 下午8:22:26
	 * @param dto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDiscountDto> selectPriceDiscountByCodition(
			PriceDiscountDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(namespace+"selectPriceDiscountByCodition", dto, rowBounds);	
	}

	/**
	 * <p>根据条件查询价格折扣方案明细总数</p> 
	 * @author sz
	 * @date 2012-12-13 下午8:58:14
	 * @param dto
	 * @return
	 * @see
	 */
	@Override
	public Long countPriceDiscountByCodition(PriceDiscountDto dto) {
		return (Long)getSqlSession().selectOne(namespace+"countPriceDiscountByCodition", dto);	
	}

	/**
	 * <p>根据条件查询价格折扣方案全信息</p> 
	 * @author sz
	 * @date 2012-12-15 下午8:22:26
	 * @param dto
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDiscountDto> selectPriceDiscountAllByCodition(PriceDiscountDto dto) {
		return getSqlSession().selectList(namespace+"selectPriceDiscountAllByCodition", dto);	
	}
	/**
	 * @Description: 根据计费规则主键查询折扣明细
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-19 下午8:42:41
	 * @param priceValuationId
	 * @return
	 * @version V1.0
	 */
	@Override
	public PriceDiscountDto selectPriceDiscountItemByValuationId(
			String priceValuationId) {
		return (PriceDiscountDto)getSqlSession().selectOne(namespace+"selectDiscountItemByValuationId", priceValuationId);	
	}
	/**
	 * @Description: 根据条件查询产品折扣服务
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-19 上午10:31:26
	 * @param dto
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDiscountDto> calculateProductDiscountByCodition(DiscountParmDto dto) {
		return getSqlSession().selectList(namespace+"calculateProductDiscountByCodition", dto);	
	}
	/**
	 * @Description: 根据条件查询渠道折扣服务
	 * Company:IBM
	 * @author IBMDP-sz
	 * @date 2012-12-19 上午10:31:26
	 * @param dto
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDiscountDto> calculateChannelDiscountByCodition(DiscountParmDto dto) {
		return getSqlSession().selectList(namespace+"calculateChannelDiscountByCodition", dto);	
	}


}