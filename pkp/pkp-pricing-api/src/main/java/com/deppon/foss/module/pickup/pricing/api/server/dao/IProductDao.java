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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IProductDao.java
 * 
 * FILE NAME        	: IProductDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IProductDao {
    
 
    /**
     * 添加产品信息
     * @author foss-yuehongjie
     * @date 2012-10-20 下午5:10:58
     * @param record
     * @return
     * @see
     */
    int insert(ProductEntity record);
    
    /**
     * 
     * <p>添加产品信息</p> 
     * @author sz
     * @date 2012-12-13 下午8:20:41
     * @param record
     * @return
     * @see
     */
    int insertSelective(ProductEntity record);

    /**
     * 根据主键查询产品信息
     * @author foss-yuehongjie
     * @date 2012-10-22 下午12:30:09
     * @param id
     * @return
     * @see
     */
    ProductEntity selectByPrimaryKey(String id);

    /**
     *修改产品信息 
     * @author foss-yuehongjie
     * @date 2012-10-20 下午5:10:39
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKey(ProductEntity entity);
    
    /**
     *删除产品信息 
     * @author foss-yuehongjie
     * @date 2012-10-20 下午5:10:39
     * @param record
     * @return
     * @see
     */
    int deleteByPrimaryKey(String productId);
    
    /**
     * 
     * 分页查询产品 
     * @author foss-yuehongjie
     * @date 2012-10-20 下午5:09:52
     * @param productEntity
     * @param start
     * @param limit
     * @return
     * @see
     */
    List<ProductEntity> findProductPagingByCondition(ProductEntity productEntity,int start, int limit);
    
    /**
     *分页查询产品总记录数 
     * @author foss-yuehongjie
     * @date 2012-10-20 下午5:10:03
     * @param productEntity
     * @return
     * @see
     */
    Long countProductPagingByCondition(ProductEntity productEntity);
    
    /**
     *根据条件查询产品信息 
     * @author foss-yuehongjie
     * @date 2012-10-20 下午5:10:22
     * @param productEntity
     * @return
     * @see
     */
    List<ProductEntity> findProduct(ProductEntity productEntity);
    
    /**
     * 
     * <p>(根据条件查询产品信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-13 上午10:37:07
     * @param dto
     * @return
     * @see
     */
    List<ProductEntity> findProductByCondition(ProductDto dto);

	List<com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity> findProductByConditionForDubbo(com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.ProductDto dto);
    /**
     *激活产品信息
     * @author foss-yuehongjie
     * @date 2012-10-20 下午5:10:22
     * @param productEntity
     * @return
     * @see
     */
    int activateProduct(List<String> productIds);
    
    /**
     * 
     * 根据产品编码查询产品对象
     * @author DP-Foss-YueHongJie
     * @date 2012-10-29 上午11:48:38
     * @param productCode 产品编码
     * @param billDate    日期
     */
    ProductEntity getProductByCache(String productCode, Date billDate);

	com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity getProductByCacheForDubbo(String productCode, Date billDate);
    /**
     * 
     * 专供系统外查询有效产品信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-29 下午6:21:18
     */
    List<ProductEntity> findExternalProductByCondition(ProductDto dto, Date billDate);

	List<com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity> findExternalProductByConditionForDubbo(com.deppon.foss.module.pickup.pricing.dubbo.api.shared.dto.ProductDto dto, Date billDate);
    
    /**
     * 
     * 获得最后更新时间
     * @author DP-Foss-YueHongJie
     * @date 2012-11-5 下午8:19:02
     */
    public Date getLastModifyTime();
    
    /**
     * @Description: 根据名称查询产品
     * Company:IBM
     * @author IBMDP-sz
     * @date 2012-12-23 下午9:52:06
     * @param dto
     * @return
     * @version V1.0
     */
    public List<ProductEntity> findProductByName(ProductEntity productEntity);
    /**
     * 
     * 分页查询产品-提供给公共选择器 
     * @author foss-yuehongjie
     * @date 2012-10-20 下午5:09:52
     * @param productEntity
     * @param start
     * @param limit
     * @return
     * @see
     */
    List<ProductEntity> queryProductCommonToLevelByCondition(ProductEntity productEntity,int start, int limit);
    
    /**
     *分页查询产品总记录数 -提供给公共选择器
     * @author foss-yuehongjie
     * @date 2012-10-20 下午5:10:03
     * @param productEntity
     * @return
     * @see
     */
    Long countProductCommonToLevelByCondition(ProductEntity productEntity);
    /**
     * 
     * @Description: 根据CODE查询产品实体集合
     * @author FOSSDP-sz
     * @date 2013-1-30 下午3:36:52
     * @param code
     * @return
     * @version V1.0
     */
    List<ProductEntity> queryProductsByCode(String code);

    /**
     * @function 根据产品编码查询所有三级产品
     * @author Foss-105888-Zhangxingwang
     * @date 2013-11-2 14:40:18
     */
	ProductEntity getLevel3ProductInfo(Map<Object, Object> maps);

	com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity getLevel3ProductInfoForDubbo(Map<Object, Object> maps);

	List<ProductEntity> getAllLevel3ProductInfo();

	/**
	 * <p>根据一级产品查询对应的三级产品类型</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-27 09:50:55
	 * @param productCodeList
	 * @return
	 */
	List<ProductEntity> getLevel3ForProductInfo(List<String> productCodeList);
	/**
	 * 根据父产品Code查询下级产品
	 * @param parentCode
	 * @return
	 */
	public List<ProductEntity> getAllChildsFromParentCode(String parentCode);

	/**
	 * <th>
	 * 根据部门编码,一级产品查询所有对应的三级产品
	 * </th>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-3-31 20:22:18
	 * @param maps
	 * @return
	 */
	List<ProductEntity> getAllProductEntityByDeptCodeForCargoAndExpress(Map<String, Object> maps);

	/**
	 * 在线判定该产品是否为快递
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-4 15:06:33
	 */
	boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime);
	
	/**
	 * 根据一级产品查询对应所有三级产品
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-4 15:06:05
	 */
	List<String> getAllLevels3ProductCode(List<String> productCodeList);

	List<String> getAllLevels3ProductCodeForDubbo(List<String> productCodeList);
	
	/**
	 * 根据三级产品查询对应的一级产品
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-4 15:07:09
	 */
	String getTransTypeByLevel3ProductCode(Map<String, Object> maps);
}