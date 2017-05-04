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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/SalesProductService.java
 * 
 * FILE NAME        	: SalesProductService.java
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
package com.deppon.foss.module.pickup.common.client.service.impl;


import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity;
import com.deppon.foss.module.pickup.common.client.dao.ISalesProductDao;
import com.deppon.foss.module.pickup.common.client.service.ISalesProductService;
import com.google.inject.Inject;


/**
 * * 销售产品 服务类
 * @author admin
 *
 */
public class SalesProductService implements ISalesProductService {
	
	@Inject
	ISalesProductDao salesProductDao;
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void addSalesProduct(SalesProductEntity salesProduct) {
		salesProductDao.addSalesProduct(salesProduct);

	}
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void updateSalesProduct(SalesProductEntity salesProduct) {
		salesProductDao.updateSalesProduct(salesProduct);
		
	}
	
	/**
	 * 
	 * 功能：新增或更新记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void saveOrUpdate(SalesProductEntity salesProduct) {
		if(!salesProductDao.addSalesProduct(salesProduct)){
			salesProductDao.updateSalesProduct(salesProduct);

		}
	}

	/**
	 * @param salesProduct
	 */
	public void delete(SalesProductEntity salesProduct) {
		salesProductDao.delete(salesProduct);
	}

}