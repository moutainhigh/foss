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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonGoodsAreaService.java
 * 
 * FILE NAME        	: ICommonGoodsAreaService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service
 * FILE    NAME: ICommonGoodsAreaService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;

/**
 *库区查询service接口定义
 * @author panGuangJun
 * @date 2012-12-1 上午9:27:32
 */
public interface ICommonGoodsAreaService {
	   /** 
     * <p>按条件查询库区数量</p> 
     * @author panGuangjun
     * @date 2012-12-1 上午8:59:19
     * @param goodsArea
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService#countGoodsAreaByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity)
     */
     long countGoodsAreaByCondition(GoodsAreaEntity goodsArea) ;
    /** 
     * <p>按条件查询库区列表</p> 
    * @author panGuangjun
     * @date 2012-12-1 上午8:58:19
     * @param goodsArea
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService#queryGoodsAreaByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity, int, int)
     */
     List<GoodsAreaEntity> queryGoodsAreaByCondition(GoodsAreaEntity goodsArea,int start,int limit) ;
}
