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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/ProductVo.java
 * 
 * FILE NAME        	: ProductVo.java
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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductDto;


/**
 * 创建产品前台Vo对象
 * (产品价格前台操作数据)
 * @author foss-yuehongjie 
 * @mail   dpyhj@deppon.com
 * @date 2012-10-18 上午11:58:37
 * @since
 * @version
 */
public class ProductVo implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 4480452754031715803L;
    
    /** 
     * 产品编号集合 
     */
    private List<String> productIds;
    
    /** 
     * 查询产品集合 
     */
    private List<ProductEntity> productEntityList;
    
    /** 
     * 单个产品  
     */
    private ProductEntity productEntity;
    
    /** 
     * 扩展产品dto 
     */
    private ProductDto productDto;
    
    
    /**
     * 获取 扩展产品dto.
     *
     * @return the 扩展产品dto
     */
    public ProductDto getProductDto() {
        return productDto;
    }

    
    /**
     * 设置 扩展产品dto.
     *
     * @param productDto the new 扩展产品dto
     */
    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    /**
     * 获取 查询产品集合.
     *
     * @return the 查询产品集合
     */
    public List<ProductEntity> getProductEntityList() {
        return productEntityList;
    }
    
    /**
     * 设置 查询产品集合.
     *
     * @param productEntityList the new 查询产品集合
     */
    public void setProductEntityList(List<ProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
    }
    
    /**
     * 获取 单个产品.
     *
     * @return the 单个产品
     */
    public ProductEntity getProductEntity() {
        return productEntity;
    }
    
    /**
     * 设置 单个产品.
     *
     * @param productEntity the new 单个产品
     */
    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    /**
     * 获取 产品编号集合.
     *
     * @return the 产品编号集合
     */
    public List<String> getProductIds() {
        return productIds;
    }

    /**
     * 设置 产品编号集合.
     *
     * @param productIds the new 产品编号集合
     */
    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    /**
     * 
     *
     * @return 
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}