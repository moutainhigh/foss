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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonSalesDeptAccountantDao.java
 * 
 * FILE NAME        	: ICommonSalesDeptAccountantDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity;

/**
 * 
 * 公共查询选择器--区域会计查询dao接口定义
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-10-30 下午4:42:23
 */
public interface ICommonSalesDeptAccountantDao {

	/**
	 * 根据查询条件返回总条数
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-10-30 下午4:36:46
	 * @param entity:查询条件封装
	 */
	 Long queryRecordCount(SalesDeptAccountantEntity entity);

	/**
	 * 
	 * 分页查询区域会计
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-10-30 下午4:39:28
	 * @param entity:查询条件封装
	 * @param start:开始条数
	 * @param limit：页数大小
	 */
	 List<SalesDeptAccountantEntity> querySalesDeptAccountantGroup(
			SalesDeptAccountantEntity entity, int limit, int start);

}
