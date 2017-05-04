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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/ProductItemVo.java
 * 
 * FILE NAME        	: ProductItemVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.shared.vo
 * FILE    NAME: ProductItemVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductItemEntity;

/**
 * 产品条件查询vo
 * @author 078823-foss-panGuangJun
 * @date 2012-12-4 下午3:01:48
 */
public class ProductItemVo {
	//查询条件
	private ProductItemEntity productItemEntity;
	//返回结果
	private List<ProductItemEntity> productItemEntities;
	/**
	 *getter
	 */
	public ProductItemEntity getProductItemEntity() {
		return productItemEntity;
	}
	/**
	 *setter
	 */
	public void setProductItemEntity(ProductItemEntity productItemEntity) {
		this.productItemEntity = productItemEntity;
	}
	/**
	 *getter
	 */
	public List<ProductItemEntity> getProductItemEntities() {
		return productItemEntities;
	}
	/**
	 *setter
	 */
	public void setProductItemEntities(List<ProductItemEntity> productItemEntities) {
		this.productItemEntities = productItemEntities;
	}
	
}	
