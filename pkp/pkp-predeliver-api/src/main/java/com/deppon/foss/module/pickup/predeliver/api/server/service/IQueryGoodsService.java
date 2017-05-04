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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/IQueryGoodsService.java
 * 
 * FILE NAME        	: IQueryGoodsService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoDto;

/**
 * 
 * 货量查询Service
 * @author 043258-foss-zhaobin
 * @date 2012-10-16 下午2:34:18
 * @since
 * @version
 */
public interface IQueryGoodsService extends IService
{
	/**
	 * 
	 * 按照查询条件查询货量
	 * @author 043258-foss-zhaobin
	 * @date 2012-10-31 上午10:51:13
	 */
	List<GoodsInfoDto> queryGoods(GoodsInfoConditionDto goodsInfoConditionDto,int start, int limit);
	
	/**
	 * 
	 * 获得总条数
	 * @author 043258-foss-zhaobin
	 * @date 2012-10-31 上午10:51:38
	 */
	Long getGoodsInfoCount(GoodsInfoConditionDto goodsInfoConditionDto);
	
	/**
	 * 
	 * 查询总货量 非分页
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-22 下午3:30:20
	 */
	GoodsInfoConditionDto queryGoodsTotal(GoodsInfoConditionDto goodsInfoConditionDto);
	
	/**
	 * 
	 * 查询总货量 非分页 用于导出
	 * @author 043258-foss-zhaobin
	 * @date 2013-5-31 上午11:06:33
	 */
	InputStream queryGoodsInfo(GoodsInfoConditionDto goodsInfoConditionDto);
	
}