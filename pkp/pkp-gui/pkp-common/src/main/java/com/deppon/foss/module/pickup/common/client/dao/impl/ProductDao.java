/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/ProductDao.java
 * 
 * FILE NAME        	: ProductDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.common.client.dao.IProductDao;
import com.deppon.foss.module.pickup.common.client.dto.ProductDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 产品数据持久层
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-25 下午2:12:17,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-25 下午2:12:17
 * @since
 * @version
 */
public class ProductDao implements IProductDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "pkp.productEntityMapper.";
	/**
	 * 数据库连接
	 */
	private SqlSession sqlSession;
	/**
	 * 数据库连接
	 * @return void
	 * @since:1.6
	 */
	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 
	 * <p>
	 * (方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-25 下午2:12:40
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.common.client.dao.IProductDao#selectByPrimaryKey(java.lang.String)
	 */
	public ProductEntity queryByPrimaryKey(String id) { 
		return sqlSession.selectOne(NAMESPACE + "selectProductByPrimaryKey", id);
	}

	/**
	 * 
	 * <p>
	 * 通过营业部查询所属产品
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-25 下午2:43:33
	 * @param salesDeptId
	 * @param productLevel
	 * @return
	 * @see com.deppon.foss.module.pickup.common.client.dao.IProductDao#queryBySalesDept(java.lang.String)
	 */
	//BUG-27918集中开单切换某一部门时运输性质重复
	public List<ProductEntity> queryBySalesDept(String salesDeptId,
			String productLevel) {
		ProductDto product = new ProductDto();
		product.setSalesDeptCode(salesDeptId);
		product.setLevels(productLevel);
		product.setSalesType(DictionaryValueConstants.ORG_DEPARTURE);
		product.setActive(FossConstants.ACTIVE);
		
		List<ProductEntity> list = sqlSession.selectList(NAMESPACE + "selectProductBySalesDept",product);
		
		List<ProductEntity> resultList = new ArrayList<ProductEntity>(); 
		
		if(list!=null && list.size()>0){
			for (Iterator<ProductEntity> iterator = list.iterator(); iterator.hasNext();) {
				ProductEntity productEntity = iterator.next();
				boolean hasThisProduct = false;
				for(int i =0;i<resultList.size();i++){
					ProductEntity p2 = resultList.get(i);
					if(p2!=null && p2.getCode()!=null && p2.getCode().equals(productEntity.getCode())){
						hasThisProduct = true;
					}
				}
				if(!hasThisProduct){
					resultList.add(productEntity);
				}
			}
		}
		
		return resultList;
	}
	
	
	/**
	 * 查询到达部门产品
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-5-14
	 */
	public List<ProductEntity> queryByArriveDept(String salesDeptId,
			String productLevel) {
		ProductDto product = new ProductDto();
		product.setSalesDeptCode(salesDeptId);
		product.setLevels(productLevel);
		product.setSalesType(DictionaryValueConstants.ORG_ARRIVE);
		product.setActive(FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE + "selectProductBySalesDept",product);
	}
	
	/**
	 * 
	 * 查询到达部门拥有的产品属性
	 * @author 025000-FOSS-helong
	 * @date 2013-1-30 上午11:18:13
	 * @param arriveDept
	 * @param productLevel
	 * @return
	 */
	public List<ProductEntity> queryByArriveDeptProduct(String arriveDept, String productLevel){
		ProductDto product = new ProductDto();
		product.setSalesDeptCode(arriveDept);
		product.setLevels(productLevel);
		product.setSalesType(DictionaryValueConstants.ORG_ARRIVE);
		product.setActive(FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE + "selectProductBySalesDept",product);
	}

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addProduct(ProductEntity product) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", product.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertProduct", product);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * 功能：更新条记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void updateProduct(ProductEntity product) {
		sqlSession.update(NAMESPACE + "updateProductByPrimaryKeySelective",product);
	}

	/**
	 * 
	 * 根据产品编码与营业日期来筛选产品
	 * 
	 * @author Foss-jiangfei
	 * @date 2012-11-21 上午8:32:52
	 */
	public ProductEntity getProductByCache(String productCode, Date billDate) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("productCode", productCode);
		p.put("billDate", billDate);
		p.put("active", FossConstants.ACTIVE);
		List<ProductEntity> list = sqlSession.selectList(NAMESPACE
				+ "getProductByCacheLocal", p);
		ProductEntity productEntity = null;
		if (list != null && list.size() > 0) {
			productEntity = list.get(0);
		} else {
			productEntity = new ProductEntity();
		}
		return productEntity;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.common.client.dao.IProductDao#delete(com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity)
	 */
	@Override
	public void delete(ProductEntity product) {
		sqlSession.delete(NAMESPACE + "delete",product);
	}
	
	/**
	 * 查询产品类型
	 * @param entity 参数
	 * @return
	 * 
	 *  版本 		用户		时间		   内容
	 *  0001		zxy			20130929		新增：BUG-56426 可以根据code id等条件查询
	 */
	@Override
	public List<ProductEntity> findProduct(ProductEntity entity){
		return sqlSession.selectList(NAMESPACE + "findProduct",entity);
	}

	@Override
	public List<ProductEntity> getLevel3ForProductInfo(Map<String, Object> maps) {
		return sqlSession.selectList(NAMESPACE+"getLevel3ForProductInfo", maps);
	}
}