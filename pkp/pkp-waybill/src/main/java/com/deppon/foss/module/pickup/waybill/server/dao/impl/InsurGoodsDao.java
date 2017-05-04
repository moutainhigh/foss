/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/InsurGoodsDao.java
 * 
 * FILE NAME        	: InsurGoodsDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IInsurGoodsDao;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 限保物品数据持久层实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-16 下午5:33:47,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-16 下午5:33:47
 * @since
 * @version
 */
public class InsurGoodsDao extends iBatis3DaoImpl implements IInsurGoodsDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "pkp.insurGoodsMapper.";

	/**
	 * 
	 * <p>
	 * 通过主键获取先报物品
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-16 下午5:34:15
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IInsurGoodsDao#queryByPrimaryKey(java.lang.String)
	 */
	public LimitedWarrantyItemsEntity queryByPrimaryKey(String id) {
		return (LimitedWarrantyItemsEntity) this.getSqlSession().selectOne(NAMESPACE + "selectInsurGoodsByPrimaryKey",
				id);
	}

	/**
	 * 
	 * <p>
	 * 获取所有的限保物品
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-16 下午5:34:19
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IInsurGoodsDao#queryAllActive()
	 */
	@SuppressWarnings("unchecked")
	public List<LimitedWarrantyItemsEntity> queryAllActive() {
		return this.getSqlSession().selectList(NAMESPACE + "selectInsurGoodsAllActive", FossConstants.YES);
	}

	/**
	 * 
	 * <p>
	 * 判断是否是限保物品
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-16 下午5:34:26
	 * @param goodsName
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IInsurGoodsDao#isInsurGoods(java.lang.String)
	 */
	public LimitedWarrantyItemsEntity isInsurGoods(String goodsName) {
		LimitedWarrantyItemsEntity oriInsurGoods = new LimitedWarrantyItemsEntity();
		oriInsurGoods.setGoodsName(goodsName.trim());
		oriInsurGoods.setActive(FossConstants.ACTIVE);
		LimitedWarrantyItemsEntity insurGoods = (LimitedWarrantyItemsEntity) this.getSqlSession().selectOne(NAMESPACE
				+ "selectInsurGoodsByName", oriInsurGoods);
		return insurGoods;
	}

	/**
	 * 
	 * <p>
	 * 判断是否是限保物品并且带出限保金额
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-15 下午1:00:41
	 * @param goodsName
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IInsurGoodsDao#queryLimitAmount(java.lang.String)
	 */
	public String queryLimitAmount(String goodsName) {
		LimitedWarrantyItemsEntity oriInsurGoods = new LimitedWarrantyItemsEntity();
		oriInsurGoods.setGoodsName(goodsName.trim());
		oriInsurGoods.setActive(FossConstants.ACTIVE);
		LimitedWarrantyItemsEntity insurGoods = (LimitedWarrantyItemsEntity) this.getSqlSession().selectOne(NAMESPACE
				+ "selectInsurGoodsByName", oriInsurGoods);
		if (insurGoods != null) {
			return insurGoods.getLimitedAmount().toString();
		} else {
			return "0";
		}
	}
}