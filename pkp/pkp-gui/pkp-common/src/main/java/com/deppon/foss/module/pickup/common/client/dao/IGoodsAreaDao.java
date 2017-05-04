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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IGoodsAreaDao.java
 * 
 * FILE NAME        	: IGoodsAreaDao.java
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
package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;


/**
 * 库区数据访问接口
 * @author 105089-foss-yangtong
 * @date 2012-10-31 下午2:43:06
 */
public interface IGoodsAreaDao {
	
	/**
	 * 
	 * 功能：按id查询
	 * @param:
	 * @return Department
	 * @since:1.6
	 */
	GoodsAreaEntity queryById(String id);

	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	boolean addGoodsArea(GoodsAreaEntity goodsArea);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateGoodsArea(GoodsAreaEntity goodsArea);
	
	/**
	 * 功能：查询记录
	 * @param:
	 * @return List<Department>
	 * @since:1.6
	 */
	List<GoodsAreaEntity> queryAll();
	 
    /**
     * <p>(按条件查询库区列表)</p> 
     * @author foss-jiangfei
     * @date 2012-11-5 下午12:59:07
     * @param goodsArea
     * @param start
     * @param limit
     * @return
     * @see
     */
	List<GoodsAreaEntity> queryGoodsAreaByCondition(GoodsAreaEntity goodsArea, int start, int limit);
	
	 /** 
     * <p>查询指定外场下的所有库区</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:56:58 AM
     * @param organizationCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao#queryGoodsAreaListByOrganizationCode(java.lang.String)
     */
    List<GoodsAreaEntity> queryGoodsAreaListByOrganizationCode(
	    String organizationCode, String goodsAreaType);

	/**
	 * 删除
	 * @param goodsAreaEntity
	 */
	void delete(GoodsAreaEntity goodsAreaEntity);
   
}