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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IProhibitGoodsDao.java
 * 
 * FILE NAME        	: IProhibitGoodsDao.java
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
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;

/**
 * 
 * 禁运物品数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-16 下午5:38:19, </p>
 * @author foss-sunrui
 * @date 2012-10-16 下午5:38:19
 * @since
 * @version
 */
public interface IProhibitGoodsDao {
    
    /**
     * 
     * <p>通过主键获取禁运物品</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:38:22
     * @param id
     * @return
     * @see
     */
    ProhibitedArticlesEntity queryByPrimaryKey(String id);

    /**
     * 
     * <p>获取所有禁运物品</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:38:25
     * @return
     * @see
     */
    List<ProhibitedArticlesEntity> queryAllActive();
    
    /**
     * 
     * <p>判断是否是禁运物品</p> 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:38:29
     * @param goodsName
     * @return
     * @see
     */
    boolean isProhibitGoods(String goodsName);
    
    /**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
    boolean addProhibitedArticles(ProhibitedArticlesEntity prohibitedArticles);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateProhibitedArticles(ProhibitedArticlesEntity prohibitedArticles);

	/**
	 * @param prohibitedArticlesEntity
	 */
	void delete(ProhibitedArticlesEntity prohibitedArticlesEntity);
	
	
	/**
     * 
     * <p>根据类型获取禁运物品</p> 
     * @author WangQianJin
     * @date 2013-05-10
     * @return
     * @see
     */
    List<ProhibitedArticlesEntity> queryProhibitGoodsByType(String type);
    
    /**
     * 
     * <p>获取汽运禁运物品</p> 
     * @author WangQianJin
     * @date 2013-05-10
     * @return
     * @see
     */
    List<ProhibitedArticlesEntity> queryProhibitGoodsForAutomobile(String otherType);
}