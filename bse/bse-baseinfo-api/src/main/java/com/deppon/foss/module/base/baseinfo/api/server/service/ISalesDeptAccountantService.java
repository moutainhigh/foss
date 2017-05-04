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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ISalesDeptAccountantService.java
 * 
 * FILE NAME        	: ISalesDeptAccountantService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity;

/**
 * 
 * Service接口
 * 
 * @author 027443-foss-zhaopeng
 * @date 2012-10-30 下午4:03:20
 */
public interface ISalesDeptAccountantService extends IService {

	/**
	 * 
	 * 新增区域会计
	 * 
	 * @author 027443-foss-zhaopeng
	 * @date 2012-10-30 下午4:12:56
	 */
	int addSalesDeptAccountant(SalesDeptAccountantEntity entity);

	/**
	 * 
	 * 修改区域会计
	 * 
	 * @author 027443-foss-zhaopeng
	 * @date 2012-10-30 下午4:17:10
	 */
	int updateSalesDeptAccountant(SalesDeptAccountantEntity entity);

	/**
	 * 
	 * 删除区域会计
	 * 
	 * @author 027443-foss-zhaopeng
	 * @date 2012-10-30 下午4:17:50
	 */
	int deleteSalesDeptAccountantByCodes(String[] codes, String modifyUser);

	/**
	 * 根据查询条件返回总条数
	 * 
	 * @author 027443-foss-zhaopeng
	 * @date 2012-10-30 下午4:36:46
	 */
	Long queryRecordCount(SalesDeptAccountantEntity entity);

	/**
	 * 
	 * 分页查询
	 * 
	 * @author 027443-foss-zhaopeng
	 * @date 2012-10-30 下午4:39:28
	 */
	List<SalesDeptAccountantEntity> querySalesDeptAccountantGroup(
			SalesDeptAccountantEntity entity, int limit, int start);



}
