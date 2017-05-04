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
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ProductDto;
/**
 * (产品DAO)(配合定价项目降价发券方案引用PKP)
 * @author dujunhui-187862
 * @date 2014-11-10 下午3:34:06
 * @since
 * @version
 */
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
    /**
     * 
     * 专供系统外查询有效产品信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-29 下午6:21:18
     */
    List<ProductEntity> findExternalProductByCondition(ProductDto dto, Date billDate);
    
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
	
	List<ProductEntity> getAllLevel3ProductInfo();
	/**
     * 
     * 查询三级有效产品（用于三级产品公共选择器分页）
     * @author 187862-dujunhui
     * @date 2014-11-13 上午9:12:16
     */
    public List<ProductEntity> findThirdLevelProductByCondition(ProductDto dto,
	    Date billDate, int start,int limit);
    /**
     * 
     * 统计三级有效产品条数（用于三级产品公共选择器分页）
     * @author 187862-dujunhui
     * @date 2014-11-13 上午9:15:16
     */
    public long countThirdLevelProductByCondition(ProductDto dto,
	    Date billDate);
}