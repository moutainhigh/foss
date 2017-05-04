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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/ProductItemDao.java
 * 
 * FILE NAME        	: ProductItemDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductItemDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductItemDto;
import com.deppon.foss.util.define.FossConstants;


/**
 * 
 * 产品条目管理数据层
 * @author DP-Foss-YueHongJie
 * @date 2012-10-22 下午5:58:43
 */
public class ProductItemDao extends SqlSessionDaoSupport implements IProductItemDao{

    /* 产品条目所在mybatis配置文件中的空间域名 */
    private static final String NAMESPACE =  "foss.pkp.pkp-pricing.pricingProductItem.";
    /**
     * 
     * 添加货物条目
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:54:25
     * @param entity 条目实体list
     */
    @Override
    public int insert(ProductItemEntity entity) {
	this.setProductItemValueAttribute(entity);
	return this.getSqlSession().insert(NAMESPACE + "insertSelective", entity);
    }
    /**
     * 
     * 根据主键查询条目
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:55:12
     * @param id 主键key
     * 
     */
    @Override
    public ProductItemEntity selectByPrimaryKey(String id) {
	return (ProductItemEntity)this.getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", id);
    }
    /**
     * 
     * 激活条目
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:55:24
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public int activationProductItem(List<String> ids) {
	Map p = new HashMap();
	p.put("ids", ids);
	return this.getSqlSession().update(NAMESPACE + "activationProductItem", p);
    }
    /**
     * 
     * 修改条目信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:56:20
     * @param entity 实体条目
     */
    @Override
    public int updateByPrimaryKey(ProductItemEntity entity) {
	 this.setProductItemValueAttribute(entity);
	 return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective",entity);
    }
    /**
     * 
     * 查询条目-分页
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:56:33
     * @param productEntity实体条目
     * @param start 分页起始页 
     * @param limit 分割业
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProductItemEntity> findPagingByCondition(
	    ProductItemEntity productEntity, int start, int limit) {
	RowBounds rowBounds = new RowBounds(start,limit);
	return this.getSqlSession().selectList(NAMESPACE + "findPagingByCondition", productEntity,rowBounds);
    }
    /**
     * 
     * 查询总数
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:56:52
     * @param entity 条目实体 
     */
    @Override
    public Long countPagingByCondition(
	    ProductItemEntity productEntity) {
	return (Long) this.getSqlSession().selectOne(NAMESPACE + "countPagingByCondition", productEntity);
    }
    /**
     * 
     * 按照条件查询条目
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:57:08
     * @param entity 条目实体
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProductItemEntity> findByCondition(ProductItemEntity productEntity) {
	return this.getSqlSession().selectList(NAMESPACE + "findByCondition", productEntity);
    }
    /**
     * 
     * 按照条件下载数据
     * @author DP-Foss-YueHongJie
     * @date 2012-10-22 下午5:57:08
     * @param entity 条目实体
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProductItemEntity> downLoadByCondition(ProductItemEntity productEntity) {
	return this.getSqlSession().selectList(NAMESPACE + "downLoadByCondition", productEntity);
    }
    /**
     * 
     * 根据条件查询产品条目信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-30 下午3:47:09
     * @param dto条目DTO
     * @param billDate 日期查询日期
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProductItemEntity> findProductItemByCache(ProductItemDto dto,
	    Date billDate) {
	dto.setBillDate(billDate);
	return getSqlSession().selectList(NAMESPACE+"findProductItemByCache",dto);
    }
    /**
     *根据名字查找信息，为了导入的时候使用
     * @author zhangdongping
     * @date 2012-12-19 下午10:13:26
     * @param dto
     * @param billDate
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override    
    public List<ProductItemEntity> findProductItemByName(ProductItemDto dto,
	    Date billDate) {
	dto.setBillDate(billDate);
	return getSqlSession().selectList(NAMESPACE+"findProductItemByName",dto);
    }
    
    /**
     * 
     * 根据条目编码与时间查询条目信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-30 下午3:47:27
     * @param itemCode 条目编码
     * @param billDate 查询日期
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ProductItemEntity findProductItemByCode(String itemCode,
	    Date billDate) {
	Map p = new HashMap();
	p.put("itemCode", itemCode);
	p.put("billDate", billDate);
	p.put("active", FossConstants.ACTIVE);
	ProductItemEntity productItem = null;
	List<ProductItemEntity> list = getSqlSession().selectList(NAMESPACE+"findProductItemByCode",p);
	if(CollectionUtils.isNotEmpty(list)){
	    productItem = list.get(0);
	}
	return productItem;
    }
    
    /**
     * @Description: 根据条目编码与时间查询条目信息集合
     * @author FOSSDP-sz
     * @date 2013-1-31 下午2:31:29
     * @param itemCode
     * @return
     * @version V1.0
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<ProductItemEntity> queryProductItemByCode(String itemCode) {
    	Map parameterMap = new HashMap();
    	parameterMap.put("itemCode", itemCode);
    	parameterMap.put("active", FossConstants.ACTIVE);
    	return getSqlSession().selectList(NAMESPACE+"findProductItemByCode",parameterMap);
    }
    /**
     * 
     * 按照产品编码与货物编码查询条目信息
     * @author DP-Foss-YueHongJie
     * @date 2012-10-30 下午3:48:34
     * @param productCode 产品编码
     * @param goodsCode   货物编码
     * @param billDate    查询日期
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ProductItemEntity findProductItemByGoodCodeAndProductCode(
	    String productCode, String goodsCode, Date billDate) {
	Map p = new HashMap();
	p.put("productCode", productCode);
	p.put("goodstypeCode", goodsCode);
	p.put("billDate", billDate);
	p.put("active", FossConstants.ACTIVE);
	ProductItemEntity productItem = null;
	List<ProductItemEntity> list = getSqlSession().selectList(NAMESPACE+"findProductItemByGoodCodeAndProductCode",p);
	if(CollectionUtils.isNotEmpty(list)){
	    productItem = list.get(0);
	}
	return productItem;
    }
    
    /**
     * 
     * <p>设置必要参数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:51:26
     * @param entity
     * @see
     */
    private void setProductItemValueAttribute(ProductItemEntity entity){
	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	UserEntity currentUser = FossUserContext.getCurrentUser();
	entity.setCreateUser(currentUser.getEmployee().getEmpCode());
	entity.setCreateOrgCode(currentDept.getCode());
	entity.setModifyUser(currentUser.getEmployee().getEmpCode());
	entity.setModifyDate(new Date());
	entity.setCreateDate(new Date());
	entity.setVersionNo(new Date().getTime());
    }
    /**
     * 获取产品条目CODE的最大值
     * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author sz
     * @date 2012-12-13 下午3:34:05
     * @return
     * @see
     */
    @Override
    public String selectMaxProductItemCode() {
	return (String)getSqlSession().selectOne(NAMESPACE+"selectMaxProductItemCode");
    }
    
    /**
     * 根据产品二级产品类型查询出对应所有的产品条目
     * @author Foss-105888-Zhangxingwang
     * @date 2015-8-5 10:45:50
     * @param maps
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductItemEntity> findProductItemByLevel2ProductCode(Map<String, Object> maps) {
		return this.getSqlSession().selectList(NAMESPACE + "findProductItemByLevel2ProductCode", maps);
	}
}