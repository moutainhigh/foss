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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IProductItemService.java
 * 
 * FILE NAME        	: IProductItemService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductItemDto;


public interface IProductItemService {

    /**
     * 
     * 添加条目
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午6:25:36
     */
     void addProductItem(ProductItemEntity entitys);
    
    /**
     * 
     * 修改产品信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午6:49:53
     */
     int updateProductItem(ProductItemEntity entity);
    
    
    /**
     * 
     * 激活产品信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午6:51:42
     */
     int activateProductItem(List<String> ids);
    
    /**
     * 
     * 根据主键ID查询条目信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午6:47:12
     */
     ProductItemEntity findProductItemById(String id);
    
    /**
     * 
     * 查询产品条目信息-
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午6:47:34
     */
     List<ProductItemEntity> findProductItemByCondiction(ProductItemEntity productItem);
    
    /**
     * 
     * 查询产品分页
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午6:48:20
     */
     List<ProductItemEntity> findProductItemPagingByCondiction(ProductItemEntity productItem,int start,int limit);
    
    
    /**
     * 
     * 查询总记录数
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午6:48:45
     */
     Long countProductItemPagingByCondiction(ProductItemEntity productItem);
    
    /**
     * 
     * @Description: 根据条件查询数据
     * @author FOSSDP-Administrator
     * @date 2013-3-14 上午10:27:14
     * @param dto
     * @param billDate
     * @return
     * @version V1.0
     */
    List<ProductItemEntity> findProductItemByCache(ProductItemDto dto, Date billDate);
    /**
     * 
     * @Description: 
     * @author FOSSDP-sz
     * @date 2013-2-22 上午11:38:01
     * @param productItemCode
     * @param billDate
     * @return
     * @version V1.0
     */
    ProductItemEntity getProductItemByCache(String productItemCode, Date billDate);
    /**
     * 
     * @Description: 
     * @author FOSSDP-sz
     * @date 2013-2-22 上午11:38:01
     * @param productItemCode
     * @param billDate
     * @return
     * @version V1.0
     */
    void refreshProductItemCache(String productItemCode);
    /**
     * 
     * 根据条目编码与时间查询条目信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-30 下午3:47:27
     */
    ProductItemEntity findProductItemByCode(String itemCode,Date billDate);
    
    /**
     * 
     * 按照产品编码与货物编码查询条目信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-30 下午3:48:34
     */
    ProductItemEntity findProductItemByGoodCodeAndProductCode(String productCode,String goodsCode,Date billDate);
    
    /**
     * 
     * <p>获取二级产品条目</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-1-14 下午4:24:03
     * @return
     * @see
     */
    List<ProductItemEntity> queryProductItemLevel2Info();
    
    
    /**
     * 
     * <p>获取三级产品条目</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-1-14 下午4:24:03
     * @return
     * @see
     */
    List<ProductItemEntity> queryProductItemLevel3Info();
    
    /**
     * 根据产品二级产品类型查询出对应所有的产品条目
     * @author Foss-105888-Zhangxingwang
     * @date 2015-8-5 10:45:50
     * @param maps
     * @return
     */
    List<ProductItemEntity> findProductItemByLevel2ProductCode(List<String> productCodeList);
}