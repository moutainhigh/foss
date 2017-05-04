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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IProductItemDao.java
 * 
 * FILE NAME        	: IProductItemDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductItemDto;

public interface IProductItemDao {
    
    /**
     * 
     * 添加货物条目
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:54:25
     * @param entity 条目实体list
     */
    int insert(ProductItemEntity entity);

    /**
     * 
     * 根据主键查询条目
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:55:12
     * @param id 主键key
     * 
     */
    ProductItemEntity selectByPrimaryKey(String id);

    /**
     * 
     * 激活条目
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:55:24
     */
    int activationProductItem(List<String> ids);

    /**
     * 
     * 修改条目信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:56:20
     * @param entity 实体条目
     */
    int updateByPrimaryKey(ProductItemEntity entity);
    
    /**
     * 
     * 查询条目-分页
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:56:33
     * @param productEntity实体条目
     * @param start 分页起始页 
     * @param limit 分割业
     */
    List<ProductItemEntity> findPagingByCondition(ProductItemEntity productEntity,int start, int limit);
    
    /**
     * 
     * 查询总数
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:56:52
     * @param entity 条目实体 
     */
    Long countPagingByCondition(ProductItemEntity entity);
    
    /**
     * 
     * 按照条件查询条目
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:57:08
     * @param entity 条目实体
     */
    List<ProductItemEntity> findByCondition(ProductItemEntity entity);
    
    /**
     * 
     * 按照条件下载数据
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:57:08
     * @param entity 条目实体
     */
    List <ProductItemEntity> downLoadByCondition(ProductItemEntity entity);
    
    /**
     * 
     * 根据条件查询产品条目信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-30 下午3:47:09
     * @param dto条目DTO
     * @param billDate 日期查询日期
     */
    List<ProductItemEntity> findProductItemByCache(ProductItemDto dto, Date billDate);
    
    /**
     * 
     * 根据条目编码与时间查询条目信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-30 下午3:47:27
     * @param itemCode 条目编码
     * @param billDate 查询日期
     */
    ProductItemEntity findProductItemByCode(String itemCode,Date billDate);
    
    /**
     * @Description: 根据条目编码与时间查询条目信息集合
     * @author FOSSDP-sz
     * @date 2013-1-31 下午2:31:29
     * @param itemCode
     * @return
     * @version V1.0
     */
    List<ProductItemEntity> queryProductItemByCode(String itemCode);
    
    /**
     * 
     * 按照产品编码与货物编码查询条目信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-30 下午3:48:34
     * @param productCode 产品编码
     * @param goodsCode   货物编码
     * @param billDate    查询日期
     */
    ProductItemEntity findProductItemByGoodCodeAndProductCode(String productCode,String goodsCode,Date billDate);
    
    /**
     * 获取产品条目CODE的最大值
     * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author sz
     * @date 2012-12-13 下午3:34:05
     * @return
     * @see
     */
    String selectMaxProductItemCode();
    
    /**
     *根据名字查找信息，为了导入的时候使用
     * @author zhangdongping
     * @date 2012-12-19 下午10:13:26
     * @param dto
     * @param billDate
     * @return
     * @see
     */
     List<ProductItemEntity> findProductItemByName(ProductItemDto dto, Date billDate);
     
     /**
      * 根据产品二级产品类型查询出对应所有的产品条目
      * @author Foss-105888-Zhangxingwang
      * @date 2015-8-5 10:45:50
      * @param maps
      * @return
      */
     List<ProductItemEntity> findProductItemByLevel2ProductCode(Map<String, Object> maps);
}