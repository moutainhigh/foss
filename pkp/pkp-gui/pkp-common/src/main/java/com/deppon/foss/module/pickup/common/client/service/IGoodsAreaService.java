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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IGoodsAreaService.java
 * 
 * FILE NAME        	: IGoodsAreaService.java
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
package com.deppon.foss.module.pickup.common.client.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;

/**
 * 库区服务
 * 
 * @author 105089-foss-yangtong
 * @date 2012-10-31 下午2:34:31
 */
public interface IGoodsAreaService {

	/**
	 * 
	 * 按id查询
	 * 
	 * @author 105089-foss-yangtong
	 * @date 2012-11-13 上午10:58:45
	 */
	GoodsAreaEntity queryById(String id);

	/**
	 * 
	 * 插条记录
	 * 
	 * @author 105089-foss-yangtong
	 * @date 2012-11-13 上午10:58:45
	 */
	void addGoodsArea(GoodsAreaEntity goodsArea);

	/**
	 * 
	 * 更新条记录
	 * 
	 * @author 105089-foss-yangtong
	 * @date 2012-11-13 上午10:58:45
	 */
	void updateGoodsArea(GoodsAreaEntity goodsArea);

	/**
	 * 
	 * 新增或更新记录
	 * 
	 * @author 105089-foss-yangtong
	 * @date 2012-11-13 上午10:58:45
	 */
	void saveOrUpdate(GoodsAreaEntity goodsArea);

	/**
	 * 
	 * 查询记录
	 * 
	 * @author 105089-foss-yangtong
	 * @date 2012-11-13 上午10:58:45
	 */
	List<GoodsAreaEntity> queryAll();

	/**
	 * <p>
	 * (根据外场部门编码和目的站部门编码取货区编码)
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-5 下午1:22:01
	 * @param organizationCode
	 * @param arriveRegionCode
	 * @return
	 * @see
	 */
	String queryCodeByArriveRegionCode(String organizationCode,
			String arriveRegionCode);

	/**
	 * <p>
	 * 按条件查询库区列表
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date Oct 30, 2012 10:35:16 AM
	 * @param goodsArea
	 * @param start
	 * @param limit
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService#queryGoodsAreaByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity,
	 *      int, int)
	 */

	List<GoodsAreaEntity> queryGoodsAreaByCondition(
			GoodsAreaEntity goodsArea, int start, int limit);
	
	
	/**
     * 
     * <p>根据库区类型（卡货库区，普货库区，贵重物品库区等）</p> 
     * @author foss-zhujunyong
     * @date Nov 2, 2012 3:26:39 PM
     * @param goodsAreaType 库区类型，数据字典中取值
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION 异常货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE  贵重物品货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING   包装货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER     偏线货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION   驻地派送货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_COMMON    混装货区
     * @return 该类型的库区列表
     * @see
     */
    List<GoodsAreaEntity> queryGoodsAreaListByType(String organizationCode, String goodsAreaType);

    
    /**
	 * 删除
	 * @param goodsAreaEntity
	 */
	 void delete(GoodsAreaEntity goodsAreaEntity);
}