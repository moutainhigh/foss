/**
 *  
 *  
* SR1	
* 
* 根据页面所录入的产品名称与后台数据库校验是否已经存在、存在提示“该产品名称已经存在!”
* 
* SR2	
* 
* 产品编码，
* 
* 取消掉原有设计编码原则，
* 
* 所有产品编码非系统自动生成，
* 
* 编码规则为固定值，
* 
* 当新增某一产品时，
* 
* 必须通知运维人员对产品的信息录入与配置信息的修改。
* 
* 目前确认且固定下来的产品和产品编码如下：
* 
* 一级产品： 
* 
* 汽运 - TRANS_VEHICLE，空运-TRANS_AIRCRAFT
* 
* 二级产品：
* 
* 精准-RECISION_PRODUCTS
* 
* 普货-COMMON_PRODUCTS
* 
* 整车-WHOLE_VEHICLE
* 
* 空运-AIR_FREIGHT
* 
* 三级产品：
* 
* 精准汽运（长途）LRF
* 
* 精准卡航 FLF
* 
* 精准汽运（短途）SRF
* 
* 精准城运 FSF
* 
* 汽运偏线 PLF
* 
* 精准空运 AF
* 
* 整车 WVH
* 
* SR3	
* 
* 点击提交按钮后，系统根据用户所填写的信息产生一条产品信息。
* 
* SR4	
* 
* 在录入完产品信息前台根据所录入的产品编号与名称安全通过校验后，
* 
* 用户选择激活操作,做激活的产品会和去所有已激活的货物类型进行自动绑定生成条目信息。
* 
* 生成的条目信息如下：产品编号，货物编号，条目名称=产品名称（货物名称），
* 
* 条目编号=由大写字母T+五位数字组成，如：T00001记录每增一条自增1。
* 
* SR5	
* 
* 没有激活的产品可以在后续产品列表中去选择记录做激活
* 
* SR6	
* 
* 产品增加层级概念如图：
*  
* 1、	新增界面加入层级选项。
* 
* 2、	   新增界面加入优先级（快、慢车）选项
* 
* 3、	   新增界面加入所属父类产品
* 
* 4、	       新增界面加入简称属性
* 
* 5、	       新增界面顺序号，顺序号主要体现在三级产品上，用于中转对三级产品排序使用
* 
* 6、	新增产品时，当层级选择为一级，父级产品属性隐藏掉不显示，当层级选择为二级， 父级产品内容显示所有已激活一级产品，当层级选择为三级产品，父级产品内容显示所有已激活的二级产品


 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * 
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/ProductService.java
 * 
 * FILE NAME        	: ProductService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductItemDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IWaybillRegionImpPushService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.WaybillRegionInfoDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.ProductException;
import com.deppon.foss.module.pickup.pricing.server.cache.ProductCacheDeal;
import com.deppon.foss.module.pickup.pricing.api.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
/**
 * 
 * 产品服务管理类
 * 
 * @author DP-Foss-YueHongJie
 * 
 * @date 2012-10-22 下午3:07:39
 * 
 */
public class ProductService implements IProductService{
	/**
	 * 日志
	 */
	private static final Logger log = Logger.getLogger(ProductService.class);
	/**
	 * 产品数据管理服务
	 */
    @Inject
    IProductDao productDao;
    /**
	 * 产品条目数据管理服务
	 */
    @Inject
    IProductItemDao productItemDao;
    /**
	 * 货物类型管理服务
	 */
    @Inject
    IGoodsTypeDao goodsTypeDao;
    /**
	 * 员工管理服务
	 */
    @Inject
    IEmployeeService employeeService;
    /**
	 * 产品缓存处理
	 */
    @Inject
    ProductCacheDeal productCacheDeal;
    
    private String productToCubcUrl;
  
    /**
	 * FOSS推送业务逻辑处理接口
	 */
	@Inject
	private IWaybillRegionImpPushService waybillRegionImpPushService;
	
	public void setWaybillRegionImpPushService(
			IWaybillRegionImpPushService waybillRegionImpPushService) {
		this.waybillRegionImpPushService = waybillRegionImpPushService;
	}
	
	
    
    
    /**
     * 获取 产品缓存处理.
     *
     * @return the 产品缓存处理
     */
    public ProductCacheDeal getProductCacheDeal() {
		return productCacheDeal;
    }
    /**
     * 设置 产品缓存处理.
     *
     * @param productCacheDeal the new 产品缓存处理
     */
    public void setProductCacheDeal(ProductCacheDeal productCacheDeal) {
		this.productCacheDeal = productCacheDeal;
    }
    /**
     * 设置 产品数据管理服务.
     *
     * @param productDao the new 产品数据管理服务
     */
    public void setProductDao(IProductDao productDao) {
        this.productDao = productDao;
    }
    /**
     * 设置 产品条目数据管理服务.
     *
     * @param productItemDao the new 产品条目数据管理服务
     */
    public void setProductItemDao(IProductItemDao productItemDao) {
		this.productItemDao = productItemDao;
    }
	/**
	 * 设置 货物类型管理服务.
	 *
	 * @param goodsTypeDao the new 货物类型管理服务
	 */
	public void setGoodsTypeDao(IGoodsTypeDao goodsTypeDao) {
		this.goodsTypeDao = goodsTypeDao;
	}
	/**
	 * 设置 员工管理服务.
	 *
	 * @param employeeService the new 员工管理服务
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	
	/**
	 * @param productToCubcUrl the productToCubcUrl to set
	 */
	public void setProductToCubcUrl(String productToCubcUrl) {
		this.productToCubcUrl = productToCubcUrl;
	}




	/**
	 * 
	 * <p>设置产品信息相关元素名称，包括创建人，修改人，父级产品名称</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-1-25 上午9:55:01
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @see
	 */
	private List<ProductEntity> boxUserName(List<ProductEntity> list) {
		List<ProductEntity> productEntities = new ArrayList<ProductEntity>();
		for (int i = 0; i < list.size(); i++) {
			ProductEntity productEntity = list.get(i);
			if(StringUtil.isNotEmpty(productEntity.getCreateUser())){
				EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(productEntity.getCreateUser());
				if(employeeEntity != null && employeeEntity.getEmpName() != null) {
				    /*设置创建人名称*/
				    productEntity.setCreateUserName(employeeEntity.getEmpName());
				}
			}			
			EmployeeEntity modifyEmployeeEntity = employeeService.queryEmployeeByEmpCode(productEntity.getModifyUser());
			ProductEntity productRefEntity = productDao.selectByPrimaryKey(productEntity.getRefId());
			if(null != productEntity.getRefId()) {
			    /*设置父类产品名称*/
			    productEntity.setParentName(productRefEntity.getName());
			}
			
			if(modifyEmployeeEntity != null && modifyEmployeeEntity.getEmpName() != null) {
			    /*设置修改人名称*/
			    productEntity.setModifyUserName(modifyEmployeeEntity.getEmpName());
			}
			productEntities.add(productEntity);
		}
		return productEntities;
	}
	/**
     * 
     * 分页查询产品信息（按照不同条件分页查询产品信息）
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午3:02:50
     * 
     */
	@Override
    public List<ProductEntity> findProductPagingByCondition(ProductEntity entity,int start, int limit) {
	    	if (PricingConstants.ALL.equals(entity.getActive())) {
		    entity.setActive(null);
		}
		List<ProductEntity> list = productDao.findProductPagingByCondition(entity,start,limit);
		if(CollectionUtils.isNotEmpty(list)) {
			List<ProductEntity> productEntities = this.boxUserName(list);
			return productEntities;
		}
		return list;
    }
	 /**
     * 
     * <p>总记录数(按照条件查询总记录数)</p> 
     * 
     * @author 岳洪杰
     * 
     * @date 2012-10-16 上午11:01:21
     * 
     * @return
     * 
     * @see
     */
    @Override
    public Long countProductPagingByCondition(ProductEntity entity) {
		if (PricingConstants.ALL.equals(entity.getActive())) {
		    entity.setActive(null);
		}
		return productDao.countProductPagingByCondition(entity);
    }
    /**
     * 
     * <p>修改产品(修改产品对象信息)</p> 
     * 
     * @author 岳洪杰
     * 
     * @date 2012-10-16 上午11:02:51
     * 
     * @param productEntity
     * 
     * @see
     */
    @Override
    public int updateProduct(ProductEntity productEntity) {
    	productEntity.setModifyDate(new Date());                                                                                                       
    	productEntity.setModifyUser(getCurrentUserCode());
    	productEntity.setModifyOrgCode(getCurrentOrgCode());
    	productEntity.setVersionNo(new Date().getTime());
    	//FOSS推送产品定义到DTD
    	WaybillRegionInfoDto waybillRegionInfoDto  = new WaybillRegionInfoDto();
    	waybillRegionInfoDto.setProductEntity(productEntity);
    	waybillRegionInfoDto.setJudgmentOperation("update");
    	//推送给合伙人PTP
    	String url = PropertiesUtil.getKeyValue("dop.proctu.address");
    	waybillRegionImpPushService.pushProductWaybillHomeInfo(waybillRegionInfoDto,url);
    	//推送给客户结算中心CUBC
    	waybillRegionImpPushService.pushProductWaybillHomeInfo(waybillRegionInfoDto,productToCubcUrl);

    	return productDao.updateByPrimaryKey(productEntity);
    }
    /**
     * <p>修改产品状态(修改产品对象信息)</p> 
     * 
     * @author sz
     * 
     * @date 2012-12-13 下午3:00:53
     * 
     * @param productEntity
     * 
     * @return
     * 
     * @see
     */
    @Override
	public void activateProducts(List<String> productIds) throws ProductException {
    	List<ProductEntity> productEntityList = new ArrayList<ProductEntity>();
    	WaybillRegionInfoDto waybillRegionInfoDto = new WaybillRegionInfoDto();
    	if(productIds != null && productIds.size() > 0) {
    	    for (int k = 0; k < productIds.size(); k++) {
    	    	String productId = productIds.get(k);
    	    	ProductEntity productEntity = productDao.selectByPrimaryKey(productId);
	        	if(productEntity != null) {
	        	    if(FossConstants.ACTIVE.equals(productEntity.getActive())) {
	        	    	throw new ProductException("【"+productEntity.getName()+"】该产品已经激活，不能再次激活",null);
	        	    }
	        	    
	        	    productEntityList.add(productEntity);
	        	    if(!ProductEntityConstants.PRICING_PRODUCT_LEVEL_1.equals(productEntity.getLevels())) {
	        	    	String currentOrgCode = getCurrentOrgCode();
		        		String currentUserCode = getCurrentUserCode();
		        		GoodsTypeEntity goodsTypeEntity = new GoodsTypeEntity();
		        		goodsTypeEntity.setActive(FossConstants.ACTIVE);
		        		List<GoodsTypeEntity> goodsTypeEntities = goodsTypeDao.findGoodsTypeByCondiction(goodsTypeEntity);
		        		if(!goodsTypeEntities.isEmpty()) {
		        		    for (int i = 0; i < goodsTypeEntities.size(); i++) {
			        			GoodsTypeEntity typeEntity = goodsTypeEntities.get(i);
				        		ProductItemEntity productItemEntity = new ProductItemEntity();
				        		String productItemEntityUUId = UUIDUtils.getUUID();
				        		productItemEntity.setId(productItemEntityUUId);
				        		productItemEntity.setGoodstypeId(typeEntity.getId());
				        		productItemEntity.setGoodstypeCode(typeEntity.getCode());
				        		productItemEntity.setProductId(productEntity.getId());
				        		productItemEntity.setProductCode(productEntity.getCode());
				        		productItemEntity.setCode(String.valueOf(System.currentTimeMillis()));
				        		productItemEntity.setName(productEntity.getName()+"-"+typeEntity.getName());
		                		productItemEntity.setActive(FossConstants.ACTIVE);
		                		productItemEntity.setVersionNo(new Date().getTime());
		                		productItemEntity.setBeginTime(new Date());
		                		productItemEntity.setEndTime(new Date(PricingConstants.ENDTIME));
		                		productItemEntity.setCreateDate(new Date());
		                		productItemEntity.setCreateUser(currentUserCode);
		                		productItemEntity.setCreateOrgCode(currentOrgCode);
		                		productItemDao.insert(productItemEntity);
		        		    }
		        		}
	        	    } 
	        		productEntity.setActive(FossConstants.ACTIVE);
	        		productEntity.setVersionNo(new Date().getTime());
	        		productDao.updateByPrimaryKey(productEntity);
	        	}
    	    }
    	}
    	//FOSS推送产品定义到DTD
    		waybillRegionInfoDto.setJudgmentOperation("active");
    		waybillRegionInfoDto.setProductEntityList(productEntityList);
    		
    		//推送给合伙人PTP
        	String url = PropertiesUtil.getKeyValue("dop.proctu.address");
    		waybillRegionImpPushService.pushProductWaybillHomeInfo(waybillRegionInfoDto,url);
    		//推送给客户结算中心CUBC
        	waybillRegionImpPushService.pushProductWaybillHomeInfo(waybillRegionInfoDto,productToCubcUrl);
    	
    		
    	}
    /**
     * 
     * <p>删除产品</p> 
     * 
     * @author Administrator
     * 
     * @date 2012-12-14 上午10:44:09
     * 
     * @param productIds
     * 
     * @see
     */
    @Override
    public void deleteProduct(List<String> productIds) throws ProductException {
    	WaybillRegionInfoDto waybillRegionInfoDto = new WaybillRegionInfoDto();
    	waybillRegionInfoDto.setProductIds(productIds);
    	waybillRegionInfoDto.setJudgmentOperation("delete");
    	if(productIds != null && productIds.size() > 0) {
    		for (int k = 0; k < productIds.size(); k++) {
    			String productId = productIds.get(k);
    			ProductEntity productEntity = productDao.selectByPrimaryKey(productId);
    			if(FossConstants.ACTIVE.equals(productEntity.getActive())) {
    				throw new ProductException(ProductException.PRICE_DISCOUNT_CHECK_PARAMETER_ERROR_CODE, "该产品已经激活不能删除");
    			}
    			productDao.deleteByPrimaryKey(productId);
    		}
    	}
    	
    }
    /**
     * 
     *	获取当前用户所在部门
     *
     * @return 
     */
    private String getCurrentOrgCode() {
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		return currentDept.getCode();
	}
    /**
     * 
     *	获取当前用户
     *
     * @return 
     */
	private String getCurrentUserCode() {
		UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
		return currentUser.getEmployee().getEmpCode();
	}
	/**
	 * 
     * 添加产品
     * 
     * <p>添加产品 (添加产品信息)</p> 
     * 
     * @author 岳洪杰
     * 
     * @date 2012-10-16 上午11:03:09
     * 
     * @param productEntity
     * 
     * @see
     */
    @Override
    public int addProduct(ProductEntity productEntity) throws ProductException{
    	WaybillRegionInfoDto waybillRegionInfoDto = new WaybillRegionInfoDto();
    	ProductEntity checkNameEntity = new ProductEntity();
		checkNameEntity.setName(productEntity.getName());
		List<ProductEntity> checkNames = productDao.findProductByName(checkNameEntity);
		if (CollectionUtils.isNotEmpty(checkNames)) {
			throw new ProductException("该产品名称已经存在， 不能重添加", null);
		}
		ProductEntity checkCodeEntity = new ProductEntity();
		checkCodeEntity.setCode(productEntity.getCode());
		List<ProductEntity> checkCodeEntitys = productDao.findProduct(checkCodeEntity);
		if (CollectionUtils.isNotEmpty(checkCodeEntitys)) {
			throw new ProductException("该编号已经存在， 不能重添加", null);
		}
		productEntity.setTransportType("汽运"); //暂时默认汽运,后续需要协商处理
		String productEntityUUId = UUIDUtils.getUUID();
		Date currentDate = new Date();
		productEntity.setId(productEntityUUId);
		productEntity.setActive(FossConstants.INACTIVE);
		productEntity.setVersionNo(currentDate.getTime());
		productEntity.setBeginTime(currentDate);
		productEntity.setEndTime(new Date(PricingConstants.ENDTIME));
		productEntity.setCreateDate(currentDate);
		productEntity.setModifyDate(currentDate);
		productEntity.setCreateUser(getCurrentUserCode());
		productEntity.setCreateOrgCode(getCurrentOrgCode());
    	waybillRegionInfoDto.setProductEntity(productEntity);
    	waybillRegionInfoDto.setJudgmentOperation("add");
    	//FOSS推送产品定义到DTD
    	String url = PropertiesUtil.getKeyValue("dop.proctu.address");
    	waybillRegionImpPushService.pushProductWaybillHomeInfo(waybillRegionInfoDto,url);
    	
    	//推送给客户结算中心CUBC
    	waybillRegionImpPushService.pushProductWaybillHomeInfo(waybillRegionInfoDto,productToCubcUrl);
		return productDao.insertSelective(productEntity);
		
    }
    /**
     * 
     * 查询唯一产品信息
     * 
     * <p>(根据主键查询产品)</p> 
     * 
     * @author foss-yuehongjie
     * 
     * @date 2012-10-19 下午2:46:31
     * 
     * @param Id
     * 
     * @return
     * 
     * @see
     */
    @Override
    public ProductEntity findProductById(String productId) {
    	return productDao.selectByPrimaryKey(productId);
    }
    /**
     * 
     * 查询产品信息（按照不同条件查询数据信息）
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-22 下午3:01:48
     */
    @Override
    public List<ProductEntity> findProductByCondition(ProductEntity condtion) {
	return productDao.findProduct(condtion);
    }
    /**
     * 
     * 根据产品编码与营业日期来筛选产品
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-11-7 上午8:32:52
     */
    @Override
    public ProductEntity getProductByCache(String productCode, Date billDate) {
		if (StringUtils.isEmpty(productCode)) {
			return null;
		}
		if (billDate == null) {
			billDate = new Date();
		}
		ProductEntity productEntity = null;
		//客户端不读缓存
		if(SqlUtil.loadCache){
			try {
				productEntity = productCacheDeal.getProductEntityByCache(productCode, billDate);
				return productEntity;
			} catch (Exception e) {
				log.info("无法从产品缓存中获取数据",e);
			}
		}
		return productDao.getProductByCache(productCode, billDate);
    }
    /**
     * 按级别查询产品
     * 从低级往高级找
     */
    @Override
    public ProductEntity getProductLele(String productCode, Date billDate,int n) {
    	if (StringUtils.isEmpty(productCode)) {
			return null;
		}
    	if(n > NumberConstants.NUMBER_3 || n < 1) {
    		return null;
    	}
		if (billDate == null) {
			billDate = new Date();
		}
    	ProductEntity productEntity = null;
		
		for(int i=0;i<=NumberConstants.NUMBER_3-n;i++){
			productEntity= productDao.getProductByCache(productCode, billDate);
			if(productEntity != null) {
				if(productEntity.getLevels() == n) {
					return productEntity;
				} else {
					productCode=productEntity.getParentCode();
				}
			}
		}
		return productEntity;
		
    }
    /**
     * @Description: 根据产品编码刷新缓存
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-22 上午9:50:06
     * 
     * @param productCode
     * 
     * @version V1.0
     */
    public void refreshProductCache(String productCode) {
    	if (StringUtils.isNotBlank(productCode)) {
    		try {
    			productCacheDeal.getProductCache().invalid(productCode);
    		} catch (Exception e) {
				log.info("无法刷新产品缓存数据",e);
			}
		}
    }
    /**
     * 查询有效产品-对外接口
     * 
     * （产品一共分为三等级,客户端可以按照不同传递不同级别参数来获取对应的产品信息
     * 
     *  PricingConstants类中找到PriceEntityConstants内部类中的常量进行设置相关LEVEL级别查询）
     *  
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-29 下午6:18:22
     */
    @Override
    public List<ProductEntity> findExternalProductByCondition(
	    ProductDto condtion, Date billDate) {
	condtion.setActive(FossConstants.ACTIVE);//对于外部只查询有效激活的产品信息
	if(billDate==null)
	{
	    billDate=new Date();
	}
	return productDao.findExternalProductByCondition(condtion,billDate);
    }
    /**
     * 
     * 获取当前有效产品最大更新时间提供缓存中使用
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-11-5 下午8:17:02
     */
    @Override
    public Date getLastModifyTime() {
		//后续缓存实现需要用到
		return null;
    }
    /**
     * 
     * <p>查询所有有效的二级产品</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-13 上午10:32:55
     * 
     * @param condtion
     * 
     * @return
     * 
     * @see
     */
    @Override
    public List<ProductEntity> queryLevel2ProductInfo() {
		ProductDto condtion = new ProductDto();
		condtion.setActive(FossConstants.ACTIVE);
		condtion.setLevels(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LEVEL_2);
		condtion.setBillDate(new Date());
		return productDao.findProductByCondition(condtion);
    }
    /**
     * 
     * <p>(查询所有有效的三级产品)</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-13 上午10:33:16
     * 
     * @param condtion
     * 
     * @return
     * 
     * @see
     */
    @Override
    public List<ProductEntity> queryLevel3ProductInfo() {
		ProductDto condtion = new ProductDto();
		condtion.setActive(FossConstants.ACTIVE);
		condtion.setLevels(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
		condtion.setBillDate(new Date());
		return productDao.findProductByCondition(condtion);
    }
    /**
     * @Description: 根据名称查询产品
     * 
     * Company:IBM
     * 
     * @author IBMDP-sz
     * 
     * @date 2012-12-23 下午10:02:23
     * 
     * @param dto
     * 
     * @return
     * 
     * @version V1.0
     */
	@Override
	public ProductEntity findProductByName(ProductEntity productEntity) {
		List<ProductEntity> productEntities = productDao.findProductByName(productEntity);
		ProductEntity voProductEntity = new ProductEntity();
		if(productEntities != null && productEntities.size() > 0) {
		    voProductEntity = productEntities.get(0);
		}
		return voProductEntity;
	}
	/**
     * 
     * <p>获取有效的2,3级产品信息</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-1-10 下午7:56:11
     * 
     * @return
     * 
     * @see
     */
	@Override
	public List<ProductEntity> getLevel2And3ProductInfo() {
	    ProductDto queryCondtion = new ProductDto();
	    queryCondtion.setActive(FossConstants.ACTIVE);
	    queryCondtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_2);
	    List<ProductEntity> level2Products = productDao.findExternalProductByCondition(queryCondtion, null);
	    queryCondtion.setActive(FossConstants.ACTIVE);
	    queryCondtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
	    List<ProductEntity> level3Products = productDao.findExternalProductByCondition(queryCondtion, null);
	    List<ProductEntity> productEntities = new ArrayList<ProductEntity>();
	    if(null != level2Products){
		productEntities.addAll(level2Products);
	    }
	    if(null != level3Products){
		  productEntities.addAll(level3Products);
	    }
	    return productEntities;
	}
	/**
     * 
     * <p>提供公共选择器查询产品信息，查询规则如下，参数 entity实体中的levels不能为空必须设置二级产品、或者三级产品标志请参考常量
     * 
     * 	PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LEVEL_2与PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LEVEL_3</p>
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-1-29 下午5:06:58
     * 
     * @param entity  产品实体信息
     * 
     * @param start  开始位置
     * 
     * @param limit  截止位置
     * 
     * @return
     * 
     * @see
     */
	@Override
	public List<ProductEntity> queryProductCommonToLevelByCondition(ProductEntity entity,int start, int limit) throws ProductException  {
	    ProductDto queryCondtion = new ProductDto();
	    if(null==entity)
	    {
	    	    throw new ProductException("查询公共选择器参数不对, 请检查",null);
	    }else{
		if(CollectionUtils.isEmpty(entity.getLevelsList())){
		    throw new ProductException("查询公共选择器产品级别参数不能为空!",null);
		}
		    queryCondtion.setActive(FossConstants.ACTIVE);
        	    return productDao.queryProductCommonToLevelByCondition(entity,start,limit);
	    }
	}
	/**
     * 
     * <p>提供公共选择器查询产品总数</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-1-29 下午5:42:24
     * 
     * @param entity
     * 
     * @return
     * 
     * @see
     */
	@Override
	public Long countProductCommonToLevelByCondition(ProductEntity entity) throws ProductException {
		ProductDto queryCondtion = new ProductDto();
		if (null == entity) {
			throw new ProductException("查询公共选择器参数不能为空, 请检查", null);
		}
		if(CollectionUtils.isEmpty(entity.getLevelsList())){
		        throw new ProductException("查询公共选择器参数levelsList不能为空, 请检查", null);
		}
			queryCondtion.setActive(FossConstants.ACTIVE);
			return productDao.countProductCommonToLevelByCondition(entity);
		}
	
	/**
	    * @function 根据产品编码查询所有三级产品
	    * @author Foss-105888-Zhangxingwang
	    * @date 2013-11-2 14:40:18
	    */
	@Override
	public ProductEntity getLevel3ProductInfo(String productCode){
		Map<Object, Object> maps = new HashMap<Object, Object>();
		maps.put("active", FossConstants.YES);
		maps.put("productCode", productCode);
		maps.put("levels", NumberConstants.NUMBER_3);
		return productDao.getLevel3ProductInfo(maps);
	}
	
	/**
	 * 获取物流三级产品类型，物流按照;汽运、空运查询对应的三级产品
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-27 09:24:51
	 * @return
	 */
	@Override
	public List<ProductEntity> getLevel3ForLogistics() {
		List<String> productCodeList = new ArrayList<String>();
		//物流一级汽运
		productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
		//物流一级空运
		productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002);
		return productDao.getLevel3ForProductInfo(productCodeList);
	}
	
	/**
	 * 获取快递三级产品类型，快递目前只按照;汽运查询对应的三级产品，空运请后续添加上去
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-27 09:24:51
	 * @return
	 */
	@Override
	public List<ProductEntity> getLevel3ForExpress() {
		List<String> productCodeList = new ArrayList<String>();
		//快递一级汽运
		productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003);
		//快递一级空运
		return productDao.getLevel3ForProductInfo(productCodeList);
	}
	/**
	 *根据产品父Code查询下级产品
	 */
	public List<ProductEntity> getAllChildsFromParentCode(String parentCode) {
		return productDao.getAllChildsFromParentCode(parentCode);
	}
	
	/**
	 * 判定是否快递
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-3 19:21:26
	 */
	@Override
	public boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime) {
		if(StringUtils.isEmpty(productCode)){
			return false;
		}
		return productDao.onlineDetermineIsExpressByProductCode(productCode, billTime);
	}
	/**
	 * <th>
	 * 根据部门编码,一级产品查询所有对应的三级产品
	 * </th>
	 * @param maps
	 * @return
	 */
	@Override
	public List<ProductEntity> getAllProductEntityByDeptCodeForCargoAndExpress(String deptCode, String typeCode, Date billTime) {
		//判定typeCode是否为空
		if(StringUtils.isEmpty(typeCode)){
			return null;
		}
		//对应一级产品类型
		List<String> productCodeList = new ArrayList<String>();
		//物流标示
		if(WaybillConstants.TYPE_OF_CARGO.equals(typeCode)){
			//物流一级汽运
			productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
			//物流一级空运
			productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002);
			//快递标示
		}else if(WaybillConstants.TYPE_OF_EXPRESS.equals(typeCode)){
			//快递一级汽运
			productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003);
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("deptCode", deptCode);
		maps.put("productCodeList", productCodeList);
		maps.put("active", FossConstants.YES);
		maps.put("levels", NumberConstants.NUMBER_3);
		maps.put("billTime", billTime);
		return productDao.getAllProductEntityByDeptCodeForCargoAndExpress(maps);
	}
	/**
	 * 查询出所有对应的快递三级产品类型编码
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-15 14:02:56
	 */
	@Override
	public List<String> getAllLevels3ExpressProductCode() {
		List<String> productCodeList = new ArrayList<String>();
		//快递一级汽运
		productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20003);
		return productDao.getAllLevels3ProductCode(productCodeList);
	}
	
	/**
	 * 查询出所有对应的零担三级产品类型编码
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-15 14:02:56
	 */
	@Override
	public List<String> getAllLevels3CargoProductCode() {
		List<String> productCodeList = new ArrayList<String>();
		//物流一级汽运
		productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001);
		//物流一级空运
		productCodeList.add(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002);
		return productDao.getAllLevels3ProductCode(productCodeList);
	}

	/**
	 * 根据三级产品查询出对应的一级产品
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-15 14:02:18
	 */
	@Override
	public String getTransTypeByLevel3ProductCode(String productCode) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("productCode", productCode);
		maps.put("active", FossConstants.YES);
		maps.put("levels", NumberConstants.NUMBER_3);
		return productDao.getTransTypeByLevel3ProductCode(maps);
	}
}



