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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/ValueAddDiscountDao.java
 * 
 * FILE NAME        	: ValueAddDiscountDao.java
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
import com.deppon.foss.module.pickup.pricing.api.server.dao.IValueAddDiscountDao;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountParmDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;


/**
 * @Description: 增值优惠方案操作类
 * ValueAddDiscountDao.java Create on 2012-12-21 上午10:30:00
 * Company:IBM
 * @author IBMDP-sz
 * Copyright (c) 2012 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class ValueAddDiscountDao extends SqlSessionDaoSupport implements IValueAddDiscountDao {
	
	private String namespace = "foss.pricing.valueAddDiscountEntityMapper.";
	
	/**
	 * 
	 * @Description: 根据条件查询价格折扣方案明细
	 * @author FOSSDP-Administrator
	 * @date 2013-3-8 上午10:36:57
	 * @param dto
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDiscountDto> selectPriceDiscountByCodition(PriceDiscountDto dto) {
		return getSqlSession().selectList(namespace+"selectPriceDiscountByCodition", dto);	
	}

	/**
	 * 
	 * @Description: 根据条件查询价格折扣方案明细分页
	 * @author FOSSDP-sz
	 * @date 2013-3-8 上午10:37:04
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDiscountDto> selectPriceDiscountByCodition(
			PriceDiscountDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return getSqlSession().selectList(namespace+"selectPriceDiscountByCodition", dto, rowBounds);	
	}

	/**
	 * 
	 * @Description: 根据条件查询价格折扣方案明细总数
	 * @author FOSSDP-sz
	 * @date 2013-3-8 上午10:37:11
	 * @param dto
	 * @return
	 * @version V1.0
	 */
	@Override
	public Long countPriceDiscountByCodition(PriceDiscountDto dto) {
		return (Long)getSqlSession().selectOne(namespace+"countPriceDiscountByCodition", dto);	
	}

	/**
	 * 
	 * @Description: 根据条件查询价格折扣方案全信息
	 * @author FOSSDP-sz
	 * @date 2013-3-8 上午10:37:24
	 * @param dto
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDiscountDto> selectPriceDiscountAllByCodition(PriceDiscountDto dto) {
		return getSqlSession().selectList(namespace+"selectPriceDiscountAllByCodition", dto);	
	}
	/**
	 * 
	 * @Description: 根据计费规则主键查询折扣明细
	 * @author FOSSDP-sz
	 * @date 2013-3-8 上午10:37:30
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
	 * 
	 * @Description: 根据条件查询产品折扣服务
	 * @author FOSSDP-sz
	 * @date 2013-3-8 上午10:37:39
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
	 * 
	 * @Description: 根据条件查询渠道折扣服务
	 * @author FOSSDP-sz
	 * @date 2013-3-8 上午10:37:47
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