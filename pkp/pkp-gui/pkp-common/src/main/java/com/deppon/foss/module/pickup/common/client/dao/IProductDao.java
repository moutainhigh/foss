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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IProductDao.java
 * 
 * FILE NAME        	: IProductDao.java
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

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;

/**
 * 
 * 产品数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-25 下午2:11:08, </p>
 * @author foss-sunrui
 * @date 2012-10-25 下午2:11:08
 * @since
 * @version
 */
public interface IProductDao {
    
    /**
     * 
     * <p>通过主键查询产品</p> 
     * @author foss-sunrui
     * @date 2012-10-25 下午2:11:30
     * @param id
     * @return
     * @see
     */
    ProductEntity queryByPrimaryKey(String id);
    
    /**
     * 
     * <p>通过营业部查询所属产品</p> 
     * @author foss-sunrui
     * @date 2012-10-25 下午2:42:50
     * @param salesDeptId
     * @param productLevel
     * @return
     * @see
     */
    List<ProductEntity> queryBySalesDept(String salesDeptId, String productLevel);
    
    /**
     * 
     * 查询到达部门拥有的产品属性
     * @author 025000-FOSS-helong
     * @date 2013-1-30 上午11:17:43
     * @param arriveDept
     * @param productLevel
     * @return
     */
    List<ProductEntity> queryByArriveDeptProduct(String arriveDept, String productLevel);
    
    /**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
    boolean addProduct(ProductEntity product);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateProduct(ProductEntity product);
	
	 /**
     * 
     * 根据产品编码与营业日期来筛选产品
     * @author Foss-jiangfei
     * @date 2012-11-21 上午8:32:52
     */
    ProductEntity getProductByCache(String productCode, Date billDate) ;

	/**
	 * @param product
	 */
	void delete(ProductEntity product);
	
	/**
	 * 查询到达部门产品
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-5-14
	 */
	public List<ProductEntity> queryByArriveDept(String salesDeptCode, String productLevel);
	
	/**
	 * 查询产品类型
	 * @param entity 参数
	 * @return
	 * 
	 *  版本 		用户		时间		   内容
	 *  0001		zxy			20130929		新增：BUG-56426 可以根据code id等条件查询
	 */
	public List<ProductEntity> findProduct(ProductEntity entity);

	List<ProductEntity> getLevel3ForProductInfo(Map<String, Object> maps);
}