/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/ProductItemVo.java
 * 
 * FILE NAME        	: ProductItemVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;

/**
 * (产品条目VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-张斌,date:2012-12-18 上午10:13:10
 * </p>
 * 
 * @author dp-张斌
 * @date 2012-12-18 上午10:13:10
 * @since
 * @version
 */
public class ProductItemVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4480452754031715805L;
	/**
	 * 产品条目实体
	 */
	private ProductItemEntity productItemEntity;
	/**
	 * 产品条目实体LIST
	 */
	private List<ProductItemEntity> productItemEntityList;
	/**
	 * 产品条目IDS
	 */
	private List<String> productItemids;
	
	/**
	 * 获取 产品条目IDS.
	 *
	 * @return the 产品条目IDS
	 */
	public List<String> getProductItemids() {
		return productItemids;
	}
	
	/**
	 * 设置 产品条目IDS.
	 *
	 * @param productItemids the new 产品条目IDS
	 */
	public void setProductItemids(List<String> productItemids) {
		this.productItemids = productItemids;
	}
	
	/**
	 * 获取 产品条目实体LIST.
	 *
	 * @return the 产品条目实体LIST
	 */
	public List<ProductItemEntity> getProductItemEntityList() {
		return productItemEntityList;
	}
	
	/**
	 * 设置 产品条目实体LIST.
	 *
	 * @param productItemEntityList the new 产品条目实体LIST
	 */
	public void setProductItemEntityList(
			List<ProductItemEntity> productItemEntityList) {
		this.productItemEntityList = productItemEntityList;
	}
	
	/**
	 * 获取 产品条目实体.
	 *
	 * @return the 产品条目实体
	 */
	public ProductItemEntity getProductItemEntity() {
		return productItemEntity;
	}
	
	/**
	 * 设置 产品条目实体.
	 *
	 * @param productItemEntity the new 产品条目实体
	 */
	public void setProductItemEntity(ProductItemEntity productItemEntity) {
		this.productItemEntity = productItemEntity;
	}
	

}