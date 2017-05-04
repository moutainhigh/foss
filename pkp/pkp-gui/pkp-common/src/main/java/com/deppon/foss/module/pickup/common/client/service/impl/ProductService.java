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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/ProductService.java
 * 
 * FILE NAME        	: ProductService.java
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

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.pickup.common.client.dao.IProductDao;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.IProductService;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.google.inject.Inject;

/**
 * 产品服务类
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
public class ProductService implements IProductService {
	
	/**
	 * 产品服务dao
	 */
    @Inject
    public IProductDao productDao;
    
    /**
	 * 运单基础资料服务
	 */
	@Inject
	private IBaseDataService baseDataService;

    /**
     * 
     * 功能：插条记录
     * 
     * @param: orgInfo
     * @return void
     * @since:1.6
     */
    @Transactional
    public void addProduct(ProductEntity product) {
    	productDao.addProduct(product);

    }

    /**
     * 
     * 功能：更新条记录
     * 
     * @param:
     * @return void
     * @since:1.6
     */
    @Transactional
    public void updateProduct(ProductEntity product) {
    	productDao.updateProduct(product);

    }

    /**
     * 
     * 功能：新增或更新记录
     * 
     * @param:
     * @return void
     * @since:1.6
     */
    public void saveOrUpdate(ProductEntity product) {
    	if(!productDao.addProduct(product)){
			productDao.updateProduct(product);
		}
    }

    /**
     * 
     * 根据产品编码与营业日期来筛选产品
     * 
     * @author Foss-jiangfei
     * @date 2012-11-21 上午8:32:52
     */
    @Override
    public ProductEntity getProductByCache(String productCode, Date billDate) {
		if (StringUtils.isEmpty(productCode)) {
		    return null;
		}
		
		//是否在线
		if(CommonUtils.isOnline()){
			return baseDataService.getProductByCache(productCode, billDate);
		}else{
			//如果没有日期就用当前日期
			if (billDate == null) {
				return productDao.getProductByCache(productCode, new Date());
			}else{
				//否则就用传入的日期
				return productDao.getProductByCache(productCode, billDate);
			}
		}
    }

	/**
	 * @param product
	 */
	public void delete(ProductEntity product) {
		productDao.delete(product);
	}

	/**
	 * 通过营业部编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午8:45:47
	 */
	@Override
	public List<ProductEntity> queryBySalesDept(String salesDeptCode, String productLevel) {
		return baseDataService.queryBySalesDept(salesDeptCode,productLevel);		
	}
	
	/**
	 * 查询到达部门产品
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-5-14
	 */
	@Override
	public List<ProductEntity> queryByArriveDept(String salesDeptCode, String productLevel) {
		return baseDataService.queryByArriveDept(salesDeptCode,productLevel);		
	}

	/**
	 * 查询到达部门产品
	 * 
	 * @author 076234-FOSS-pgy
	 * @date 2014-3-5
	 */
	@Override
	public List<ProductEntity> searchByArriveDept(String code, String productLevel) {
		return baseDataService.searchByArriveDept(code,productLevel);
	}
}