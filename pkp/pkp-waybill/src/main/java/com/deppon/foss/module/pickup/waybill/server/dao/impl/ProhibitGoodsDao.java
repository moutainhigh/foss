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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/ProhibitGoodsDao.java
 * 
 * FILE NAME        	: ProhibitGoodsDao.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IProhibitGoodsDao;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 禁运物品数据持久层实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-16 下午5:37:03,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-16 下午5:37:03
 * @since
 * @version
 */
public class ProhibitGoodsDao extends iBatis3DaoImpl implements IProhibitGoodsDao {

	private static final String NAMESPACE = "pkp.prohibitGoodsMapper.";


	/**
	 * 
	 * <p>
	 * 通过主键获取禁运物品
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-16 下午5:37:22
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IProhibitGoodsDao#queryByPrimaryKey(java.lang.String)
	 */
	@Override
	public ProhibitedArticlesEntity queryByPrimaryKey(String id) {
		return (ProhibitedArticlesEntity) this.getSqlSession().selectOne(NAMESPACE
				+ "selectProhibitGoodsByPrimaryKey", id);
	}

	/**
	 * 
	 * <p>
	 * 获取所有禁运物品
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-16 下午5:37:26
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IProhibitGoodsDao#queryAllActive()
	 */
	@Override
	public List<ProhibitedArticlesEntity> queryAllActive() {
		return this.getSqlSession().selectList(
				NAMESPACE + "selectProhibitGoodsAllActive", FossConstants.ACTIVE);
	}

	/**
	 * 
	 * <p>
	 * 判断是否是禁运物品
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-16 下午5:37:29
	 * @param goodsName
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IProhibitGoodsDao#isProhibitGoods(java.lang.String)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public boolean isProhibitGoods(String goodsName) {
		ProhibitedArticlesEntity oriPGoods = new ProhibitedArticlesEntity();
		oriPGoods.setGoodsName(goodsName.trim());
		oriPGoods.setActive(FossConstants.ACTIVE);
		
		List pGoods = this.getSqlSession().selectList(NAMESPACE
				+ "selectProhibitGoodsByName", oriPGoods);
		return CollectionUtils.isNotEmpty(pGoods)? true : false;
	}

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Override
	public boolean addProhibitedArticles(
			ProhibitedArticlesEntity prohibitedArticles) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", prohibitedArticles.getId());
		String id = (String) this.getSqlSession().selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			this.getSqlSession().insert(NAMESPACE + "insertProhibitGoods",
				prohibitedArticles);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * 功能：更新条记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void updateProhibitedArticles(
			ProhibitedArticlesEntity prohibitedArticles) {
		this.getSqlSession().update(NAMESPACE + "updateProhibitGoodsByPrimaryKey",
				prohibitedArticles);

	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.common.client.dao.IProhibitGoodsDao#delete(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity)
	 */
	@Override
	public void delete(ProhibitedArticlesEntity prohibitedArticlesEntity) {
		this.getSqlSession().delete(NAMESPACE + "delete",prohibitedArticlesEntity);
	}
	
	/**
     * 
     * <p>根据类型获取禁运物品</p> 
     * @author WangQianJin
     * @date 2013-05-10
     * @return
     * @see
     */
	@Override
    public List<ProhibitedArticlesEntity> queryProhibitGoodsByType(String type){
		ProhibitedArticlesEntity oriPGoods = new ProhibitedArticlesEntity();
		oriPGoods.setGoodsSort(type);
		oriPGoods.setActive(FossConstants.ACTIVE);		
		return this.getSqlSession().selectList(NAMESPACE+ "selectProhibitGoodsByType", oriPGoods);				
    }	
	
	/**
     * 
     * <p>获取汽运禁运物品</p> 
     * @author WangQianJin
     * @date 2013-05-10
     * @return
     * @see
     */
	@Override
    public List<ProhibitedArticlesEntity> queryProhibitGoodsForAutomobile(String otherType){	
		ProhibitedArticlesEntity oriPGoods = new ProhibitedArticlesEntity();
		oriPGoods.setGoodsSort(otherType);
		oriPGoods.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+ "selectProhibitGoodsForAutomobile", oriPGoods);				
    }
}