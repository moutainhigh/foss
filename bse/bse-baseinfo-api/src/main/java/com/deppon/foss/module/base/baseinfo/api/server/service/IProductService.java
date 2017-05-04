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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IProductService.java
 * 
 * FILE NAME        	: IProductService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ProductDto;
/**
 * 
 * 产品定义(配合定价项目降价发券方案引用PKP)
 * @author 187862-dujunhui 
 * @date 2014-11-11 下午2:33:18
 * @since
 * @version
 */
public interface IProductService extends IService {
    
    
    /**
     * 查询唯一产品信息
     * <p>(根据主键查询产品)</p> 
     * @author foss-yuehongjie
     * @date 2012-10-19 下午2:46:31
     * @param Id
     * @return
     * @see
     */
    public ProductEntity findProductById(String id);
    
    /**
     * 
     * 查询产品信息（按照不同条件查询数据信息）
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午3:01:48
     */
     List<ProductEntity> findProductByCondition(ProductEntity condtion);
    
    
    
    /**
     * 查询有效产品-对外接口
     * （产品一共分为三等级,客户端可以按照不同传递不同级别参数来获取对应的产品信息
     *  PricingConstants类中找到PriceEntityConstants内部类中的常量进行设置相关LEVEL级别查询）
     * @author DP-Foss-YueHongJie
     * @date 2012-10-29 下午6:18:22
     */
     List<ProductEntity> findExternalProductByCondition(ProductDto condtion,Date billDate);
    
    /**
     * 
     * 根据产品编码与营业日期来筛选产品
     * @author DP-Foss-YueHongJie
     * @date 2012-11-7 上午8:32:52
     */
     ProductEntity getProductByCache(String productCode ,Date billDate);
    
    /**
     * 
     * @Description: 根据产品编码刷新缓存
     * @author FOSSDP-sz
     * @date 2013-2-22 上午9:50:06
     * @param productCode
     * @version V1.0
     */
     void refreshProductCache(String productCode);
    
    
    /**
     * 
     * 分页查询产品信息（按照不同条件分页查询产品信息）
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午3:02:50
     */
     List<ProductEntity> findProductPagingByCondition(ProductEntity entity,int start,int limit);
    
    /**
     * 
     * <p>总记录数(按照条件查询总记录数)</p> 
     * @author 岳洪杰
     * @date 2012-10-16 上午11:01:21
     * @return
     * @see
     */
     Long countProductPagingByCondition(ProductEntity entity);
    

    /**
     * 
     * <p>修改产品(修改产品对象信息)</p> 
     * @author 岳洪杰
     * @date 2012-10-16 上午11:02:51
     * @param productEntity
     * @see
     */
     int updateProduct(ProductEntity productEntity);
    
    /**
     * <p>修改产品状态(修改产品对象信息)</p> 
     * @author sz
     * @date 2012-12-13 下午3:00:53
     * @param productEntity
     * @return
     * @see
     */
     void activateProducts(List<String> productIds);
    
    /**
     * <p>删除产品</p> 
     * @author Administrator
     * @date 2012-12-14 上午10:44:09
     * @param productIds
     * @see
     */
     void deleteProduct(List<String> productIds);

    /**
     * 添加产品
     * <p>添加产品 (添加产品信息)</p> 
     * @author 岳洪杰
     * @date 2012-10-16 上午11:03:09
     * @param productEntity
     * @see
     */
    public int addProduct(ProductEntity productEntity);
    
    
    /**
     * 
     * 获取当前有效产品最大更新时间提供缓存中使用
     * @author DP-Foss-YueHongJie
     * @date 2012-11-5 下午8:17:02
     */
     Date getLastModifyTime();
    
    /**
     * 
     * <p>查询所有有效的二级产品</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-13 上午10:32:55
     * @param condtion
     * @return
     * @see
     */
     List<ProductEntity> queryLevel2ProductInfo();
    
    /**
     * 
     * <p>(查询所有有效的三级产品)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-13 上午10:33:16
     * @param condtion
     * @return
     * @see
     */
     List<ProductEntity> queryLevel3ProductInfo();
    /**
     * @Description: 根据名称查询产品
     * Company:IBM
     * @author IBMDP-sz
     * @date 2012-12-23 下午10:02:23
     * @param dto
     * @return
     * @version V1.0
     */
     ProductEntity findProductByName(ProductEntity productEntity);
    
    /**
     * 
     * <p>获取有效的2,3级产品信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-1-10 下午7:56:11
     * @return
     * @see
     */
     List<ProductEntity> getLevel2And3ProductInfo();
    
    /**
     * 
     * <p>提供公共选择器查询产品信息，查询规则如下，参数 entity实体中的levels不能为空必须设置二级产品、或者三级产品标志请参考常量
     * 	PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LEVEL_2与PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LEVEL_3</p>
     * @author DP-Foss-YueHongJie
     * @date 2013-1-29 下午5:06:58
     * @param entity  产品实体信息
     * @param start  开始位置
     * @param limit  截止位置
     * @return
     * @see
     */
     List<ProductEntity> queryProductCommonToLevelByCondition(ProductEntity entity,int start, int limit);
    
    /**
     * 
     * <p>提供公共选择器查询产品总数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-1-29 下午5:42:24
     * @param entity
     * @return
     * @see
     */
     Long countProductCommonToLevelByCondition(ProductEntity entity);

     /**
      * @function 根据产品编码查询所有三级产品
      * @author Foss-105888-Zhangxingwang
      * @date 2013-11-2 14:40:18
      */
	ProductEntity getLevel3ProductInfo(String productCode);

	/**
     * 按级别查询产品
     * 从低级往高级找
     */
	ProductEntity getProductLele(String productCode, Date billDate, int n);
	/**
     * 查询三级有效产品（用于三级产品公共选择器）
     * PricingConstants类中找到PriceEntityConstants内部类中的常量进行设置相关LEVEL级别查询）
     * @author 187862-dujunhui
     * @date 2014-11-13 上午9:16:36
     */
    public List<ProductEntity> findThirdLevelProductByCondition(
	    ProductDto condtion, Date billDate, int start, int limit);
    /**
     * 统计三级有效产品条数（用于三级产品公共选择器）
     * PricingConstants类中找到PriceEntityConstants内部类中的常量进行设置相关LEVEL级别查询）
     * @author 187862-dujunhui
     * @date 2014-11-13 上午9:16:36
     */
    public long countThirdLevelProductByCondition(
	    ProductDto condtion, Date billDate);
}