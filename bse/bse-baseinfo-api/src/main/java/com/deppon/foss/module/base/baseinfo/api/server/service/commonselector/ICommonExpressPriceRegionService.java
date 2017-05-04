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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonPriceRegionService.java
 * 
 * FILE NAME        	: ICommonPriceRegionService.java
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
 * FILE    NAME: ICommonReginService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;

/**
 * 公共组件--快递价格区域Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-8-12 下午6:36:47 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-8-12 下午6:36:47
 * @since
 * @version
 */
public interface ICommonExpressPriceRegionService extends IService{
	/**
	 * <p>根据条件查询区域信息</p> 
	 * @author 094463-foss-xieyantao
	 * @date 2013-8-12 下午6:38:20
	 * @param regionEntity 查询条件
	 * @param start 其实查询位置
	 * @param limit 每页几条
	 * @return
	 * @see
	 */
	public List<PriceRegionEntity> searchRegionByCondition(PriceRegionEntity regionEntity, int start, int limit);
	
	/**
	 * <p>统计总数</p> 
	 * @author 094463-foss-xieyantao
	 * @date 2013-8-12 下午6:41:56
	 * @param regionEntity
	 * @return
	 * @see
	 */
	Long countRegionByCondition(PriceRegionEntity regionEntity);
}
