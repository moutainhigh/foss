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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IProductService.java
 * 
 * FILE NAME        	: IProductService.java
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

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;

/**
 * 产品服务接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-yangtong,date:2012-10-31 下午4:09:00,
 * </p>
 * 
 * @author foss-yangtong
 * @date 2012-10-31 下午4:09:00
 * @since
 * @version
 */
public interface IProductService {

	/**
	 * 插条记录
	 */
	void addProduct(ProductEntity product);

	/**
	 * 更新条记录
	 */
	void updateProduct(ProductEntity product);

	/**
	 * 新增或更新记录
	 */
	void saveOrUpdate(ProductEntity product);

	/**
	 * 
	 * 根据产品编码与营业日期来筛选产品
	 * 
	 * @author Foss-jiangfei
	 * @date 2012-11-21 上午8:32:52
	 */

	ProductEntity getProductByCache(String productCode, Date billDate);

	/**
	 * @param product
	 */
	void delete(ProductEntity product);

	/**
	 * 根据营业部code 查询营业部下所属产品
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-28 下午4:24:40
	 */
	List<ProductEntity> queryBySalesDept(String salesDeptCode,
			String productLevel);

	/**
	 * 查询到达部门产品
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-5-14
	 */
	public List<ProductEntity> queryByArriveDept(String salesDeptCode,
			String productLevel);

	/**
	 * 查询到达部门产品
	 * 
	 * @author 076234-FOSS-pgy
	 * @date 2014-3-5
	 */
	List<ProductEntity> searchByArriveDept(String code, String string);

}