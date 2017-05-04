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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/SalesProductService.java
 * 
 * FILE NAME        	: SalesProductService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesProductService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;

/**
 * 营业部适用产品 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-14 上午12:55:17
 */
public class SalesProductService implements ISalesProductService {

    /**
     * 下面是dao对象的声明及get,set方法：
     */
    private ISalesProductDao salesProductDao;
    
    private IProductService productService;

    
    /**
     * @param productService the productService to set
     */
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }

    /**
     * 
     * @date Mar 11, 2013 2:27:55 PM
     * @param salesProductDao
     * @see
     */
    public void setSalesProductDao(ISalesProductDao salesProductDao) {
	this.salesProductDao = salesProductDao;
    }
    
    /**
     * 营业部适用产品 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:55:17
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISalesProductService#addSalesProduct(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity)
     */
    @Override
    @Transactional
    public SalesProductEntity addSalesProduct(SalesProductEntity entity) {
	// 检查参数的合法性
	if (null == entity) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getSalesDeptCode()) || StringUtils.isBlank(entity.getProductCode())) {
	    return null;
	}
	return salesProductDao.addSalesProduct(entity);
    }

    /**
     * 通过code标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:55:17
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao#deleteSalesProduct(java.lang.String)
     */
    @Override
    @Transactional
    public SalesProductEntity deleteSalesProduct(SalesProductEntity entity) {
	// 请求合法性判断：
	if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	return salesProductDao.deleteSalesProduct(entity);
    }

    /**
     * 通过code标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:55:17
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao#deleteSalesProductMore(java.lang.String[])
     */
    @Override
    @Transactional
    public SalesProductEntity deleteSalesProductMore(String[] codes , String deleteUser) {
	return salesProductDao.deleteSalesProductMore(codes, deleteUser);
    }

    /**
     * 更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:55:17
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao#updateSalesProduct(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity)
     */
    @Override
    @Transactional
    public SalesProductEntity updateSalesProduct(SalesProductEntity entity) {
	//检查参数的合法性
	if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getSalesDeptCode()) || StringUtils.isBlank(entity.getProductCode())) {
	    return null;
	}
	return salesProductDao.updateSalesProduct(entity);
    }



    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询 
     * 通过 VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:55:17
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductService#querySalesProductByCode(java.lang.String)
     */
    @Override
    public SalesProductEntity querySalesProductByVirtualCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	SalesProductEntity  entity = salesProductDao.querySalesProductByVirtualCode(code);
	entity = attachProductName(entity);
	return entity;
    }


    /**
     * 精确查询 
     * 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductService#querySalesProductByCode(java.lang.String)
     */
    @Override
    public List<SalesProductEntity> querySalesProductBatchByVirtualCode(
	    String[] codes) {
	if (codes==null||codes.length==0){
	    return null;
	}
	
	return salesProductDao.querySalesProductBatchByVirtualCode(codes);
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductService#querySalesProductExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity, int, int)
     */
    @Override
    public List<SalesProductEntity> querySalesProductExactByEntity(
	    SalesProductEntity entity, int start, int limit) {
	return salesProductDao.querySalesProductExactByEntity(
		entity, start, limit);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductService#querySalesProductExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity)
     */
    @Override
    public long querySalesProductExactByEntityCount(SalesProductEntity entity) {
	return salesProductDao.querySalesProductExactByEntityCount(entity);
    }
 
    /**
     * 模糊查询 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:55:17
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductService#deleteSalesProductMore(java.lang.String[])
     */
    @Override
    public List<SalesProductEntity> querySalesProductByEntity(
	    SalesProductEntity entity, int start, int limit) {
	return salesProductDao.querySalesProductByEntity(entity, start, limit);
    }

    /**
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:55:17
     * @see com.deppon.foss.module.baseinfo.server.service.ISalesProductService#querySalesProductCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.SalesProductEntity)
     */
    @Override
    public long querySalesProductByEntityCount(SalesProductEntity entity) {
	return salesProductDao.querySalesProductByEntityCount(entity);
    }
    
	
    
    
    
    /**
     * 下面是特殊方法
     */

    /**
     * 通过SalesDeptCode营业部编码,产品的业务部门类型SALES_TYPE（是出发部门还是到达部门）， 标识来删除
     * 
     * 如果SALES_TYPE为空，则作废营业部的所有产品
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:51:18
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao#deleteSalesProduct(java.lang.String)
     */
    @Override
    public SalesProductEntity deleteSalesProductBySalesDeptCode(SalesProductEntity entity){
	return salesProductDao.deleteSalesProductBySalesDeptCode(entity);
    }

    /**
     * 批量插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:51:18
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao#deleteSalesProduct(java.lang.String)
     */
    @Override
    public List<SalesProductEntity> addSalesProductMore(List<SalesProductEntity> entitys){
	for(SalesProductEntity entity : entitys){
		// 检查参数的合法性
		if (null == entity) {
			entitys.remove(entity);
		}
		if (null != entity && 
				(StringUtils.isBlank(entity.getSalesDeptCode()) || StringUtils.isBlank(entity.getProductCode()))) {
			entitys.remove(entity);
		}
	}
	entitys = salesProductDao.addSalesProductBatch(entitys);
	return entitys;
    }

    
    
    /**
     * 下面是工具方法
     */
    
    
    /**
     * 给营业部所选产品的产品加上“产品名称”
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午3:28:50
     */
    private SalesProductEntity attachProductName(SalesProductEntity entity){
	if(entity == null){
	    return entity;
	}
	if(productService!=null){
	    ProductEntity product=productService.getProductByCache(entity.getProductCode(), new Date());
	    if(product != null){
		entity.setProductName(product.getName());
	    }
	}
	return entity;
    }

}
