/**
 *SR1	
 *
 *1、	产品条目服务名称、和新增人名称 都支持模糊查询。
*
*2、	结果显示列表按照产品名称分组显示。
*
*3、	修改时的信息不可以出现重复已经存在的数据。如产品编号与货物编号已经存在提示 “该产品条目已经存在不可重复添加”
*
*
*
*产品名称	产品定义名称	输出信息		100	
*
*产品条目方案名称	产品条目方案名称	输出信息		80	
*
*条目编号	产品条目方案编号	输出信息		20	
*
*货物类型编号	货物类型编号	输出信息		20	
*
*货物类型名称	货物类型名称	输出信息		80	
*
*产品定义编号	产品编号	输出信息		20	
*
*新增人名称	新增人姓名	输出信息		20	
*
*激活状态	激活状态	输出信息		20	



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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/ProductItemService.java
 * 
 * FILE NAME        	: ProductItemService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductItemDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductItemService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductItemDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.ProductItemServiceException;
import com.deppon.foss.module.pickup.pricing.server.cache.ProductItemCacheDeal;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
public class ProductItemService implements IProductItemService {
	/**
	 * 日志
	 */
	private static final Logger log = Logger.getLogger(ProductItemService.class);
	/**
	 * 产品条目DAO
	 */
    @Inject
    IProductItemDao productItemDao;
    /**
	 * 货物类型SERVICE
	 */
    @Inject
    IGoodsTypeService goodsTypeService;
    /**
	 * 员工SERVICE
	 */
    @Inject
    IEmployeeService employeeService;
    /**
	 * 产品DAO
	 */
    @Inject
    IProductService productService;
    /**
	 * 产品条目缓存处理
	 */
    @Inject
    ProductItemCacheDeal productItemCacheDeal;
    
    
    /**
     * 注入产品条目缓存处理
     * @author WangQianJin
     * @date 2013-7-27 下午12:55:52
     */
	public void setProductItemCacheDeal(ProductItemCacheDeal productItemCacheDeal) {
		this.productItemCacheDeal = productItemCacheDeal;
	}
	/**
     * 设置 产品DAO.
     *
     * @param productService the new 产品DAO
     */
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
    /**
     * 设置 员工SERVICE.
     *
     * @param employeeService the new 员工SERVICE
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    /**
     * 设置 货物类型SERVICE.
     *
     * @param goodsTypeService the new 货物类型SERVICE
     */
    public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
        this.goodsTypeService = goodsTypeService;
    }
    /**
     * 设置 产品条目DAO.
     *
     * @param productItemDao the new 产品条目DAO
     */
    public void setProductItemDao(IProductItemDao productItemDao) {
        this.productItemDao = productItemDao;
    }
    /**
     * 
     * 添加条目
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午6:25:36
     */
    @Override
    public void addProductItem(ProductItemEntity entitys)throws ProductItemServiceException{
	Date currentDate = new Date();
	entitys.setId(UUIDUtils.getUUID());
	entitys.setBeginTime(currentDate);
	entitys.setEndTime(new Date(PricingConstants.ENDTIME));
	ProductItemDto productItemDto = new ProductItemDto();
	productItemDto.setName(entitys.getName());
	List<ProductItemEntity>  productItemEntities = productItemDao.findProductItemByName(productItemDto,new Date());
	ProductItemEntity productItemEntity = productItemDao.findProductItemByGoodCodeAndProductCode(entitys.getProductCode(), entitys.getGoodstypeCode(), currentDate);
	if(CollectionUtils.isNotEmpty(productItemEntities)){
	    throw new ProductItemServiceException("条目名称已经存在，不能重复添加！",null);
	}
	if(null != productItemEntity){
	    throw new ProductItemServiceException("所选产品与货物类型已经有存在的条目，不能重复添加",null);
	}
	productItemDao.insert(entitys);
    }
    /**
     * 
     * 修改产品信息
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午6:49:53
     */
    @Override
    public int updateProductItem(ProductItemEntity entity) {
	ProductItemEntity productItemEntity = productItemDao.selectByPrimaryKey(entity.getId());
	try {
		if(productItemEntity != null){
			PropertyUtils.copyProperties(productItemEntity, entity);
		}
	} catch (IllegalAccessException e) {
		log.info(e.getMessage(), e);
	} catch (InvocationTargetException e) {
		log.info(e.getMessage(), e);
	} catch (NoSuchMethodException e) {
		log.info(e.getMessage(), e);
	}
	ProductItemEntity temp = new ProductItemEntity();
	temp.setName(entity.getName());
	List<ProductItemEntity>  productItemEntities = productItemDao.findByCondition(temp);
	String productItemName = entity.getName();
	String id = entity.getId();
	String code = entity.getCode();
	if(CollectionUtils.isNotEmpty(productItemEntities)){
	    for (ProductItemEntity tempEntity : productItemEntities) {
		if(!StringUtils.equalsIgnoreCase(tempEntity.getId(),id)){
		    if(StringUtils.equals(tempEntity.getName(),productItemName)){
			throw new ProductItemServiceException("条目名称已经存在，不能重复添加！",null);
		    }
		}
	    }
	}
	ProductItemEntity codeEntity = new ProductItemEntity();
	codeEntity.setCode(entity.getCode());
	List<ProductItemEntity>  productItemByCodeEntities = productItemDao.findByCondition(codeEntity);
	if(CollectionUtils.isNotEmpty(productItemByCodeEntities)){
	    for (ProductItemEntity tempEntity : productItemByCodeEntities) {
		if(!StringUtils.equalsIgnoreCase(tempEntity.getId(),id)){
		    if(StringUtils.equalsIgnoreCase(tempEntity.getCode(), code)){
			throw new ProductItemServiceException("该方案编码已经存在，不能重复添加",null);
		    }
		}
	    }
	}
	return productItemDao.updateByPrimaryKey(productItemEntity);
    }
    /**
     * 
     * 激活产品信息
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午6:51:42
     */
    @Override
    public int activateProductItem(List<String> ids) {
	return productItemDao.activationProductItem(ids);
    }
    /**
     * 
     * 根据主键ID查询条目信息
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午6:47:12
     */
    @Override
    public ProductItemEntity findProductItemById(String id) {
	return productItemDao.selectByPrimaryKey(id);
    }
    /**
     * 
     * 查询产品条目信息-
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午6:47:34
     */
    @Override
    public List<ProductItemEntity> findProductItemByCondiction(
	    ProductItemEntity productItem) {
	return productItemDao.findByCondition(productItem); 
    }
    /**
     * 
     * 查询产品分页
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午6:48:20
     */
    @Override
    public List<ProductItemEntity> findProductItemPagingByCondiction(
	    ProductItemEntity productItem, int start, int limit) {
	List<ProductItemEntity> productItemEntities = productItemDao.findPagingByCondition(productItem, start, limit);
	return this.boxProductItemDate(productItemEntities); 
    }
    /**
     * 封装数据
     *
     * @param productItemEntities 
     * 
     * @return 
     */
    private List<ProductItemEntity> boxProductItemDate(List<ProductItemEntity> productItemEntities){
	Date currentDate = new Date();
	List<ProductItemEntity> list  = new ArrayList<ProductItemEntity>();
	for (ProductItemEntity productItemEntity : productItemEntities) {
		if(StringUtil.isNotEmpty(productItemEntity.getCreateUser())){
			EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(productItemEntity.getCreateUser());
			if(null!=employee){
				productItemEntity.setCreateUserName(employee.getEmpName());
			}
		}	    
	    GoodsTypeEntity goodsTypeEntity = goodsTypeService.queryGoodsTypeByGoodTypeCode(productItemEntity.getGoodstypeCode(),currentDate);
	    if(null!=goodsTypeEntity){
		productItemEntity.setGoodstypeName(goodsTypeEntity.getName());
	    }
	    list.add(productItemEntity);
	}
	return list;
    }
    /**
     * 
     * 查询总记录数
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午6:48:45
     */
    @Override
    public Long countProductItemPagingByCondiction(
	    ProductItemEntity productItem) {
	return productItemDao.countPagingByCondition(productItem); 
    }
    /**
     * 
     * mark: 根据条件查询数据
     * 
     * @author FOSSDP-Administrator
     * 
     * @date 2013-3-14 上午10:27:14
     * 
     * @param dto
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public List<ProductItemEntity> findProductItemByCache(ProductItemDto dto,
	    Date billDate) {
	return productItemDao.findProductItemByCache(dto,billDate); 
    }
    /**
     * 
     * mark: 
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-22 上午11:38:01
     * 
     * @param productItemCode
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public ProductItemEntity getProductItemByCache(String productItemCode, Date billDate) {
    	if(StringUtils.isEmpty(productItemCode)) {
    		return null;
    	}
    	if(billDate == null) {
    		billDate = new Date();
    	}
    	ProductItemEntity productItemEntity = null;
    	try {
    		productItemEntity = productItemCacheDeal.getProductItemEntityByCache(productItemCode, billDate);
    		return productItemEntity;
		} catch (Exception e) {
			log.info("无法从产品条目缓存中获取数据",e);
		}
    	productItemEntity = this.findProductItemByCode(productItemCode, billDate);
    	return productItemEntity;
    }
    /**
     * 
     * mark: 刷新缓存
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-22 上午11:38:01
     * 
     * @param productItemCode
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    public void refreshProductItemCache(String productItemCode) {
    	if(StringUtil.isNotBlank(productItemCode)) {
    		try {
    			productItemCacheDeal.getProductItemCache().invalid(productItemCode);
    		} catch (Exception e) {
    			log.info("无法刷新产品条目缓存数据",e);
    		}
    	}
    }
    /**
     * 
     * 根据条目编码与时间查询条目信息
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-30 下午3:47:27
     */
    @Override
    public ProductItemEntity findProductItemByCode(String itemCode,
	    Date billDate) {
	return productItemDao.findProductItemByCode(itemCode,billDate); 
    }
    /**
     * 
     * 按照产品编码与货物编码查询条目信息
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-30 下午3:48:34
     */
    @Override
    public ProductItemEntity findProductItemByGoodCodeAndProductCode(
	    String productCode, String goodsCode, Date billDate) {
	return productItemDao.findProductItemByGoodCodeAndProductCode(productCode, goodsCode, billDate);
    }
    /**
     * 
     * <p>获取二级产品条目</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-1-14 下午4:24:03
     * 
     * @return
     * 
     * @see
     */
    @Override
    public List<ProductItemEntity> queryProductItemLevel2Info() {
	return queryProductItemLevel(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LEVEL_2);
    }
    /**
     * 
     * <p>获取三级产品条目</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-1-14 下午4:24:03
     * 
     * @return
     * 
     * @see
     */
    @Override
    public List<ProductItemEntity> queryProductItemLevel3Info() {
	return queryProductItemLevel(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
    }
    /**
     * 
     * 获取产品条目级别
     *
     * @param level 
     * 
     * @return 
     */
    private List<ProductItemEntity> queryProductItemLevel(Long level){
	ProductItemEntity productItemEntity = new ProductItemEntity();
	productItemEntity.setActive(FossConstants.ACTIVE);
	List<ProductItemEntity> productItemEntitys = productItemDao.findByCondition(productItemEntity);
	List<ProductItemEntity> voproductItemEntitys = new ArrayList<ProductItemEntity>();
	if(CollectionUtils.isNotEmpty(productItemEntitys)){
	    for (int i=0; i<productItemEntitys.size(); i++) {
		ProductItemEntity dbEntity = productItemEntitys.get(i);
		ProductEntity productEntity = productService.findProductById(dbEntity.getProductId());
		if(null != productEntity){
		    if(level.equals(productEntity.getLevels())){
			voproductItemEntitys.add(dbEntity);
		    }
		}
	    }
	}
	return voproductItemEntitys;
    }
    
    /**
     * 根据产品二级产品类型查询出对应所有的产品条目
     * @author Foss-105888-Zhangxingwang
     * @date 2015-8-5 10:45:50
     * @param maps
     * @return
     */
	@Override
	public List<ProductItemEntity> findProductItemByLevel2ProductCode(List<String> productCodeList) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("active", FossConstants.YES);
		maps.put("levels", 2);
		maps.put("productCodeList", productCodeList);
		return productItemDao.findProductItemByLevel2ProductCode(maps);
	}
}














































/**
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
