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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/ProductDao.java
 * 
 * FILE NAME        	: ProductDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IProductDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ProductDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;

/**
 * 产品定义DAO(产品数据管理层,对产品的)(配合定价项目降价发券方案引用PKP)
 * @author dujunhui-187862 
 * @date 2014-11-11 下午2:27:08
 * @since
 * @version
 */
public class ProductDao extends SqlSessionDaoSupport implements IProductDao{

    //mybatis namespace
    private static final String NAMESPACE =  "foss.bse.bse-baseinfo.pricingProduct.";
    
    /**
     * 
     * <p>添加产品</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:13:48
     * @param entity
     * @return 
     */
    @Override
    public int insert(ProductEntity entity) {
	return getSqlSession().insert(NAMESPACE + "insertSelective",entity);
    }
    
    /**
     * 
     * <p>添加产品</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:13:58
     * @param record
     * @return 
     */
    @Override
    public int insertSelective(ProductEntity record) {
	this.setProductValueAttribute(record);
    	return getSqlSession().insert(NAMESPACE + "insertSelective",record);
    }

    /**
     * 
     * <p>查询单个产品</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:14:08
     * @param id
     * @return 
     */
    @Override
    public ProductEntity selectByPrimaryKey(String id) {
		return (ProductEntity)getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey" ,id);
    }

    /**
     * 
     * <p>修改产品</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:14:23
     * @param entity
     * @return 
     */
    @Override
    public int updateByPrimaryKey(ProductEntity entity) {
	this.setProductValueAttribute(entity);
	return getSqlSession().update(NAMESPACE + "updateByPrimaryKey",entity);
    }
    
    /**
     * 
     * <p>删除产品</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午4:14:34
     * @param productId
     * @return 
     */
    @Override
    public int deleteByPrimaryKey(String productId) {
	return getSqlSession().delete(NAMESPACE + "deleteByPrimaryKey" , productId);
    }

    /**
    *
    * 分页查询产品信息
    * @author foss-yuehongjie
    * @date 2012-10-20 下午5:16:01
    * @param entity 相关查询条件
    * @param start  第几条
    * @param limit  截止第几条
    * @return List<ProductEntity> 产品泛型集合
    */
   @SuppressWarnings("unchecked")
   @Override
   public List<ProductEntity> findProductPagingByCondition(ProductEntity entity,int start, int limit) {
	RowBounds rowBounds = new RowBounds(start, limit);
	String sql = NAMESPACE + "findExternalProductByCondition";
	return getSqlSession().selectList(sql, entity, rowBounds);
   }
   
   /**
    *
    * 分页查询产品总记录
    * @author foss-yuehongjie
    * @date 2012-10-20 下午5:18:04
    * @param productEntity 	查询条件
    * @return Long 	总记录数
    */
   @Override
   public Long countProductPagingByCondition(ProductEntity productEntity) {
	String sql = NAMESPACE + "countProductPagingByCondition";
	return (Long)getSqlSession().selectOne(sql, productEntity);
   }
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
    @SuppressWarnings("unchecked")
    @Override
    public List<ProductEntity> findProduct(ProductEntity entity) {
	return getSqlSession().selectList(NAMESPACE +"findProduct", entity);
    }
    /**
     *激活产品信息
     * @author foss-yuehongjie
     * @date 2012-10-20 下午5:10:22
     * @param productEntity
     * @return
     * @see
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public int activateProduct(List<String> productIds){
	Map map = new HashMap();
	map.put("ids", productIds);
	return getSqlSession().update(NAMESPACE +"activateProduct", map);
    }
    /**
     * 
     * 根据产品编码查询产品对象
     * @author DP-Foss-YueHongJie
     * @date 2012-10-29 上午11:48:38
     * @param productCode 产品编码
     * @param billDate    日期
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ProductEntity getProductByCache(String productCode, Date billDate) {
		Map p = new HashMap();
		p.put("productCode", productCode);
		p.put("billDate", billDate);
		p.put("active", FossConstants.ACTIVE);
		List<ProductEntity> list = getSqlSession().selectList(NAMESPACE + "getProductByCache", p);
		ProductEntity productEntity = null;
		if (CollectionUtils.isNotEmpty(list)) {
			productEntity = list.get(0);
		} else {
			productEntity = new ProductEntity();
		}
		return productEntity;
    }
    /**
     * 
     * 专供系统外查询有效产品信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-29 下午6:21:18
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProductEntity> findExternalProductByCondition(ProductDto dto,
	    Date billDate) {
	dto.setBillDate(billDate);
	return (ArrayList<ProductEntity>) getSqlSession().selectList(NAMESPACE +"findExternalProductByCondition", dto);
    }
    /**
     * 
     * 查询三级有效产品（用于三级产品公共选择器分页）(配合POP，三级产品只要精准汽运长短途 精准卡航 精准城运)
     * @author 187862-dujunhui
     * @date 2014-11-13 上午9:15:16
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProductEntity> findThirdLevelProductByCondition(ProductDto dto,
	    Date billDate, int start,int limit) {
	dto.setBillDate(billDate);
	RowBounds rowBounds = new RowBounds(start, limit);
	return (ArrayList<ProductEntity>) getSqlSession().selectList(NAMESPACE +"findExternalProductByCondition", dto,rowBounds);
    }
    /**
     * 
     * 统计三级有效产品条数（用于三级产品公共选择器分页）(配合POP，三级产品只要精准汽运长短途 精准卡航 精准城运)
     * @author 187862-dujunhui
     * @date 2014-11-13 上午9:15:16
     */
    @Override
    public long countThirdLevelProductByCondition(ProductDto dto,
	    Date billDate) {
	dto.setBillDate(billDate);
	return (Long) getSqlSession().selectOne(NAMESPACE +"countExternalProductByCondition", dto);
    }
    /**
     * 
     * 获得最后更新时间
     * @author DP-Foss-YueHongJie
     * @date 2012-11-5 下午8:19:02
     */
    @Override
    public Date getLastModifyTime() {
	return (Date)getSqlSession().selectOne(NAMESPACE +"findExternalProductByCondition");
    }
    /**
     * 
     * <p>(根据条件查询产品信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-13 上午10:37:07
     * @param dto
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProductEntity> findProductByCondition(ProductDto dto) {
	return getSqlSession().selectList(NAMESPACE +"findExternalProductByCondition", dto);
    }
    
    /**
     * @Description: 根据名称查询产品
     * Company:IBM
     * @author IBMDP-sz
     * @date 2012-12-23 下午9:52:06
     * @param dto
     * @return
     * @version V1.0
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProductEntity> findProductByName(ProductEntity productEntity) {
	String sql = NAMESPACE + "findProductByName";
	return getSqlSession().selectList(sql, productEntity);
    }
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
   @SuppressWarnings("unchecked")
   @Override
   public List<ProductEntity> queryProductCommonToLevelByCondition(ProductEntity entity,int start, int limit) {
	RowBounds rowBounds = new RowBounds(start, limit);
	String sql = NAMESPACE + "queryProductCommonToLevelByCondition";
	return getSqlSession().selectList(sql, entity, rowBounds);
   }
   /**
    *分页查询产品总记录数 -提供给公共选择器
    * @author foss-yuehongjie
    * @date 2012-10-20 下午5:10:03
    * @param productEntity
    * @return
    * @see
    */
   @Override
   public Long countProductCommonToLevelByCondition(ProductEntity productEntity) {
	String sql = NAMESPACE + "countProductCommonToLevelByCondition";
	return (Long)getSqlSession().selectOne(sql, productEntity);
   }
   
   /**
    * 
    * <p>设置必要参数</p> 
    * @author DP-Foss-YueHongJie
    * @date 2012-12-22 下午3:51:26
    * @param entity
    * @see
    */
   private void setProductValueAttribute(ProductEntity entity){
	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	UserEntity currentUser = FossUserContext.getCurrentUser();
	entity.setCreateUser(currentUser.getEmployee().getEmpCode());
	entity.setCreateOrgCode(currentDept.getCode());
	entity.setModifyUser(currentUser.getEmployee().getEmpCode());
	entity.setCreateDate(new Date());
	entity.setVersionNo(new Date().getTime());
   }
   
   /**
    * 
    * @Description: 根据CODE查询产品实体集合
    * @author FOSSDP-sz
    * @date 2013-1-30 下午3:36:52
    * @param code
    * @return
    * @version V1.0
    */
   @SuppressWarnings({ "unchecked", "rawtypes" })
   @Override
   public List<ProductEntity> queryProductsByCode(String code) {
	   Map parameterMap = new HashMap();
	   parameterMap.put("active", FossConstants.ACTIVE);
	   parameterMap.put("code", code);
	   return getSqlSession().selectList(NAMESPACE + "queryProductsByCode", parameterMap);
   }

   /**
    * @function 根据产品编码查询所有三级产品
    * @author Foss-105888-Zhangxingwang
    * @date 2013-11-2 14:40:18
    */
	@SuppressWarnings("unchecked")
	@Override
	public ProductEntity getLevel3ProductInfo(Map<Object, Object> maps) {
		List<ProductEntity> list = this.getSqlSession().selectList(NAMESPACE + "getLevel3ProductInfo", maps);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	/**
    * <p>获取所有三级产品类型</p>
    * @author Foss-105888-Zhangxingwang
    * @date 2013-11-2 14:40:18
    */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductEntity> getAllLevel3ProductInfo() {
		Map<Object, Object> maps = new HashMap<Object, Object>();
		maps.put("active", FossConstants.YES);
		maps.put("levels", NumberConstants.NUMBER_3);
		return this.getSqlSession().selectList(NAMESPACE+"getAllLevel3ProductInfo", maps);
	}
}