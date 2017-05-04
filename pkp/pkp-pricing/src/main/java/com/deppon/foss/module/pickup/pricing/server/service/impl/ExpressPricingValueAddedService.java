/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 输入信息
 * 
 * 服务名称	服务名称	文本框		 50	否
 * 
 * 
 * 输出信息
 * 
 *  服务名称	服务名称	输出信息		80	是
 *  
*状态	启用状态	输出信息		20	是
*
*最后修改人	默认当前操作人	输出信息		20	是
*
*修改时间	最后修改时间	输出信息		20	是
*
*生效日期	生效日期	输出信息		10	是
*
*截止日期	截止日期	输出信息		10	是
*
*操作列	做修改	输出信息		20	是
*
*长短途	长短途定义	输出信息		20	是

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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PricingValueAddedService.java
 * 
 * FILE NAME        	: PricingValueAddedService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressPricingValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricingValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductItemService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionValueAddService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricingCommonException;
import com.deppon.foss.module.pickup.pricing.api.shared.define.ExpWaybillConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * @Description: 增值服务SERVICE
 * 
 * PricingValueAddedService.java Create on 2013-3-18 上午11:00:12
 * 
 * Company:IBM
 * 
 * @author FOSSDP-Administrator
 * 
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * 
 * @version V1.0
 */
@Transactional
public class ExpressPricingValueAddedService implements IExpressPricingValueAddedService {
	/**
	 * 增值服务DAO
	 */
    @Inject
	private IExpressPricingValueAddedDao expressPricingValueAddedDao;
	/**
	 * 设置 增值服务DAO.
	 *
	 * @param pricingValueAddedDao the new 增值服务DAO
	 */
    public void setExpressPricingValueAddedDao(
			IExpressPricingValueAddedDao expressPricingValueAddedDao) {
		this.expressPricingValueAddedDao = expressPricingValueAddedDao;
	}
	/**
	 * 计费规则DAO
	 */
	@Inject
	private IExpressPriceValuationDao expressPriceValuationDao;
	
	/**
	 * 设置 计费规则DAO.
	 *
	 * @param priceValuationDao the new 计费规则DAO
	 */
	public void setExpressPriceValuationDao(
			IExpressPriceValuationDao expressPriceValuationDao) {
		this.expressPriceValuationDao = expressPriceValuationDao;
	}
	/**
	 * 计价方式明细DAO
	 */
	@Inject
	private IExpressPriceCriteriaDetailDao expressPriceCriteriaDetailDao;
	
	/**
	 * 设置 计价方式明细DAO.
	 *
	 * @param priceCriteriaDetailDao the new 计价方式明细DAO
	 */
	public void setExpressPriceCriteriaDetailDao(
			IExpressPriceCriteriaDetailDao expressPriceCriteriaDetailDao) {
		this.expressPriceCriteriaDetailDao = expressPriceCriteriaDetailDao;
	}
	
	/**
	 * 获取基础产品的SERVICE
	 */
	@Inject
	private IProductService productService;

	
	/**
	 * 设置 获取基础产品的SERVICE.
	 *
	 * @param productService the new 获取基础产品的SERVICE
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	/**
	 * 获取产品条目的SERVICE
	 */
	@Inject
	private IProductItemService productItemService;
	/**
	 * 设置 获取产品条目的SERVICE.
	 *
	 * @param productItemService the new 获取产品条目的SERVICE
	 */
	public void setProductItemService(IProductItemService productItemService) {
		this.productItemService = productItemService;
	}
	/**
	 * 获取货物类型的SERVICE 
	 */
	@Inject
	private IGoodsTypeService goodsTypeService;

	/**
	 * 设置 获取货物类型的SERVICE.
	 *
	 * @param goodsTypeService the new 获取货物类型的SERVICE
	 */
	public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
		this.goodsTypeService = goodsTypeService;
	}
    /**
     * 职员service
     */
	@Inject
	private IEmployeeService employeeService;
	/**
	 * 设置 职员service.
	 *
	 * @param employeeService the new 职员service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	/**
	 * 增值区域
	 */
	IRegionValueAddService regionValueAddService;
	
	/**
	 * 增值区域
	 * @param regionValueAddService
	 */
	public void setRegionValueAddService(
			IRegionValueAddService regionValueAddService) {
		this.regionValueAddService = regionValueAddService;
	}
	/**
	 * 区域service
	 */
	private IRegionService regionService;
	/**
	 * 设置 区域service.
	 *
	 * @param regionService the new 区域service
	 */
	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}
	/**
	 * 快递价格区域service
	 */
	private IRegionExpressService regionExpressService;
	
	public void setRegionExpressService(IRegionExpressService regionExpressService) {
		this.regionExpressService = regionExpressService;
	}
	/**
	 * .
	 * <p>
	 * 新增增值服务<br/>
	 * 方法名：addValueAdded
	 * </p>
	 * 
	 * @param priceValuationEntity
	 *            计费规则实体
	 *            
	 * @param priceCriteriaDetailEntity
	 *            计价方式明细实体
	 *            
	 * @author 张斌
	 * 
	 * @时间 2012-10-18
	 * 
	 * @since JDK1.6
	 */
	@Override
	@Transactional
	public void addValueAdded(PriceValuationEntity priceValuationEntity,
			List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityList) {
		long versionNo = new Date().getTime();
		UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
		}
		// 当前登录用户empcode
		String userCode = user.getEmployee().getEmpCode();
		// 当前登录用户所在部门code
		String orgCode = user.getEmployee().getDepartment().getCode();
		// 该计费跪着是否激活
		String active = priceValuationEntity.getActive();
		String code  = this.createCode(priceValuationEntity.getType());
		priceValuationEntity.setCode(code);
		if (FossConstants.ACTIVE.equals(priceValuationEntity.getActive())) {
			//应该被修改的数据的截止日期
			Date endTime = new Date(priceValuationEntity.getBeginTime().getTime() - PricingConstants.ONE_MILLISECOND);
			// 查询截止时间是2999-12-31的数据
			PriceValuationEntity searchModel = new PriceValuationEntity();
			//出发区域
			searchModel.setDeptRegionId(priceValuationEntity.getDeptRegionId());
			//到达区域
			searchModel.setArrvRegionId(priceValuationEntity.getArrvRegionId());
			// 设置长短途
			searchModel.setLongOrShort(priceValuationEntity.getLongOrShort());
			// 设置类型
			searchModel.setType(priceValuationEntity.getType());
			//有效数据
			searchModel.setActive(FossConstants.ACTIVE);
			//业务类型
			searchModel.setPricingEntryCode(priceValuationEntity.getPricingEntryCode());
			//货物
			searchModel.setGoodsTypeCode(priceValuationEntity.getGoodsTypeCode());
			//货物ID
			searchModel.setGoodsTypeId(priceValuationEntity.getGoodsTypeId());
			//产品
			searchModel.setProductCode(priceValuationEntity.getProductCode());			
			// 设置查询开始日期
			searchModel.setBeginTime(priceValuationEntity.getBeginTime());
			
			//根据规则类型,开始时间
			List<PriceValuationEntity> searchResultList = expressPriceValuationDao.selectByCoditionSq(searchModel);
    			for(PriceValuationEntity priceValuationEntityModel : searchResultList){
    			    //针对保费特殊处理
    			    //根据已经重复的计费规则中找出数据库中的保费限保物品与当前所维护的限宝物品是否有冲突,如果限保物品一致,
    			    //则修改上一个计费规则的截止日期为当前日期的前一天
    			    if(StringUtil.equalsIgnoreCase(PriceEntityConstants.PRICING_CODE_BF,priceValuationEntityModel.getPricingEntryCode()))
                            {	
                    		List<PriceCriteriaDetailEntity> oldPriceCriteriaDetailEntityList  = expressPriceCriteriaDetailDao.selectByValuationId(priceValuationEntityModel.getId());
                    		if(CollectionUtils.isNotEmpty(oldPriceCriteriaDetailEntityList))
                    		{
                    		    //如果vo中的限保物品在数据库中已经存在,则修改上一个版本截止日期
			// 如果两者现报物品都不为空，且类型相同，修改上一个版本截止日期
			if (StringUtils
				.isNotEmpty(priceCriteriaDetailEntityList
					.get(0).getSubType())
				&& StringUtils
					.isNotEmpty(oldPriceCriteriaDetailEntityList
						.get(0).getSubType())) {
			    if (priceCriteriaDetailEntityList
				    .get(0)
				    .getSubType()
				    .equalsIgnoreCase(
					    oldPriceCriteriaDetailEntityList
						    .get(0).getSubType())) {
				PriceValuationEntity updateModel = new PriceValuationEntity();
				updateModel.setVersionNo(versionNo);
				updateModel.setId(priceValuationEntityModel
					.getId());
				updateModel.setEndTime(endTime);
				updateModel.setModifyDate(new Date());
				updateModel.setModifyUser(userCode);
				updateModel.setModifyOrgCode(orgCode);
				expressPriceValuationDao.updateValuation(updateModel);
			    }
			    // 如果两者现报物品都为空，则认为相同，修改上一个版本截止日期
			} else if (StringUtils
				.isEmpty(priceCriteriaDetailEntityList.get(0)
					.getSubType())
				&& StringUtils
					.isEmpty(oldPriceCriteriaDetailEntityList
						.get(0).getSubType())) {
			    PriceValuationEntity updateModel = new PriceValuationEntity();
			    updateModel.setVersionNo(versionNo);
			    updateModel
				    .setId(priceValuationEntityModel.getId());
			    updateModel.setEndTime(endTime);
			    updateModel.setModifyDate(new Date());
			    updateModel.setModifyUser(userCode);
			    updateModel.setModifyOrgCode(orgCode);
			    expressPriceValuationDao.updateValuation(updateModel);
			}
                    		}
                            }
    			    //其他增值服务
    			    else
                            {
                                // 修改对应低版本数据
            	            	PriceValuationEntity updateModel = new PriceValuationEntity();
            	   		updateModel.setVersionNo(versionNo);
            	   		updateModel.setId(priceValuationEntityModel.getId());
            	   		updateModel.setEndTime(endTime);
            	   		updateModel.setModifyDate(new Date());
            	   		updateModel.setModifyUser(userCode);
            	   		updateModel.setModifyOrgCode(orgCode);
            	   		expressPriceValuationDao.updateValuation(updateModel);
                            }
    			}
		}
		// 计费规则ID
		String priceValuationEntityId = UUIDUtils.getUUID();
		// 设置ID
		priceValuationEntity.setId(priceValuationEntityId);
		//如果endTime
		if (priceValuationEntity.getEndTime() == null) {
			// 先设置默认值
			priceValuationEntity.setEndTime(new Date(PricingConstants.ENDTIME));
		}
		// 版本号
		priceValuationEntity.setVersionNo(versionNo);
		// 创建人
		priceValuationEntity.setCreateUser(userCode);
		// 修改人
		priceValuationEntity.setModifyUser(userCode);
		// 币种
		priceValuationEntity.setCurrencyCode(DictionaryValueConstants.SETTLEMENT__CURRENCY_CODE__RMB);
		// 创建部门
		priceValuationEntity.setCreateOrgCode(orgCode);
		// 修改部门
		priceValuationEntity.setModifyOrgCode(orgCode);
		// 新增一条数计费规则
		expressPriceValuationDao.insertSelective(priceValuationEntity);
		// 新增计价方式明细
		for (PriceCriteriaDetailEntity priceCriteriaDetailEntity : priceCriteriaDetailEntityList) {
			// 计价方式明细ID
			String priceCriteriaDetailEntityId = UUIDUtils.getUUID();
			priceCriteriaDetailEntity.setActive(active);
			priceCriteriaDetailEntity.setId(priceCriteriaDetailEntityId);
			// 版本号
			priceCriteriaDetailEntity.setVersionNo(versionNo);
			// 价格计算表达式——简单费率规则
			priceCriteriaDetailEntity
					.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
			//DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
			//2015/12/3 liding add
			//因mapper文件中sql的修改，fee在此乘100
			if (priceCriteriaDetailEntity.getFee() != null) {
				long fee = priceCriteriaDetailEntity.getFee();
				if (fee != 0) {
					fee = fee * NumberConstants.NUMBER_100;
					priceCriteriaDetailEntity.setFee(fee);
				}
			}
			//2015/12/3 liding add end
			//当是其他费用和签收回单、超远派送 时采用固定价暂时写死，之后会从缓存中去判断
			if (PricingConstants.PriceEntityConstants.PRICING_CODE_QS.equals(
					priceValuationEntity.getPricingEntryCode())
					|| PricingConstants.PriceEntityConstants.PRICING_CODE_QT
							.equals(priceValuationEntity.getPricingEntryCode())|| PricingConstants.PriceEntityConstants.PRICING_CODE_CY
							.equals(priceValuationEntity.getPricingEntryCode())) {
				// 价格计算表达式——固定价
				priceCriteriaDetailEntity
				.settSrvPriceRuleId(PricingConstants.PRICE_RULE_FIXED_ID);
			}
			// 计费规则ID
			priceCriteriaDetailEntity.setPricingValuationId(priceValuationEntityId);
			expressPriceCriteriaDetailDao.insertSelective(priceCriteriaDetailEntity);
		}
	}
	/**
	 * .
	 * <p>
	 * 查询增值服务类型<br/>
	 * 方法名：searchValueAddedType
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-10-18
	 * 
	 * @since JDK1.6
	 */
	@Override
	public List<PriceEntity> searchValueAddedType(PriceEntity priceEntity) {
		return expressPricingValueAddedDao.searchValueAddedType(priceEntity);
	}
	/**
	 * .
	 * <p>
	 * 根据ID查询计价方式明细<br/>
	 * 方法名：searchCriteriaDetailById
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-10-20
	 * 
	 * @since JDK1.6
	 */
	@Override
	public List<PriceCriteriaDetailEntity> selectByValuationId(
			String valuationId) {
		return expressPriceCriteriaDetailDao.selectByValuationId(valuationId);
	}
	/**
	 * 
	 * <p>
	 * 根据查询条件查询计费规则
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @date 2012-10-20 上午11:15:04
	 * 
	 * @param record
	 * 
	 * @see
	 */
	@Override
	public List<PriceValuationEntity> selectByCodition(
			PriceValuationEntity record, int start, int limit) {
		if (PricingConstants.ALL.equals(record.getPricingEntryCode())) {
			record.setPricingEntryCode(null);
		}
		/**
		 * 查询快递全部基础产品
		 */
		if(PricingConstants.ALL.equals(record.getProductCode())){
			record.setProductCode(null);
			List<String> productCodes = new ArrayList<String>();
			productCodes.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
			productCodes.add(ExpWaybillConstants.ROUND_COUPON_PACKAGE);
			productCodes.add(ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);
			productCodes.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT);
			productCodes.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_GTSE);
			productCodes.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_ICES);
			productCodes.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_GTEC);
			productCodes.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_ICEC);
			record.setProductCodes(productCodes);
		}
		if(record.getBeginTime() != null) {
			Date beginTime = new Date(record.getBeginTime().getTime());
			Date endTime = new Date(record.getBeginTime().getTime());
			record.setBeginTime(beginTime);
			record.setEndTime(endTime);
		}
		List<PriceValuationEntity> priceValuationEntityList = expressPriceValuationDao.selectByCoditionNew(record, start, limit);
		if(CollectionUtils.isNotEmpty(priceValuationEntityList)){
			for(PriceValuationEntity priceValuationEntity:priceValuationEntityList){
				if(StringUtil.isNotBlank(priceValuationEntity.getModifyUser())){
					EmployeeEntity employeeEntity= employeeService.queryEmployeeByEmpCode(priceValuationEntity.getModifyUser());//查询修改人信息
					if(employeeEntity!=null){
						//设置修改人姓名
						priceValuationEntity.setModifyUserName(employeeEntity.getEmpName());
					}
				}
			}
		}
		if(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED.equals(record.getType())){
			for(PriceValuationEntity priceValuationEntity:priceValuationEntityList){
				if(StringUtil.isNotBlank(priceValuationEntity.getArrvRegionId())){
					if(PricingConstants.ALL.equalsIgnoreCase(priceValuationEntity.getArrvRegionId())){
						//设置到达区域名称
						priceValuationEntity.setArrvRegionName(PricingConstants.ALL_REGION_NAME);
					}else{
					    	//如果是经济快递，快递价格区域查询
						if(productService.onlineDetermineIsExpressByProductCode(priceValuationEntity.getProductCode(), new Date())){
							PriceRegionExpressEntity priceRegionExpressEntity = regionExpressService.searchRegionByID(priceValuationEntity.getArrvRegionId(), PricingConstants.PRICING_REGION);
							if(priceRegionExpressEntity!=null){
								//设置到达区域名称
								priceValuationEntity.setArrvRegionName(priceRegionExpressEntity.getRegionName());
							}
						}else{
							PriceRegionEntity priceRegionEntity = regionService.searchRegionByID(priceValuationEntity.getArrvRegionId(), PricingConstants.PRESCRIPTION_REGION);
							if(priceRegionEntity!=null){
								//设置到达区域名称
								priceValuationEntity.setArrvRegionName(priceRegionEntity.getRegionName());
							}
						}
					}
				}
				if(StringUtil.isNotBlank(priceValuationEntity.getDeptRegionId())){
					if(PricingConstants.ALL.equalsIgnoreCase(priceValuationEntity.getDeptRegionId())){
						//设置到达区域名称
						priceValuationEntity.setDeptRegionName(PricingConstants.ALL_REGION_NAME);
					}else{
					        //如果是经济快递，快递价格区域查询
						if(productService.onlineDetermineIsExpressByProductCode(priceValuationEntity.getProductCode(), new Date())){
							PriceRegionExpressEntity priceRegionExpressEntity = regionExpressService.searchRegionByID(priceValuationEntity.getDeptRegionId(), PricingConstants.PRICING_REGION);
							if(priceRegionExpressEntity!=null){
								//设置到达区域名称
								priceValuationEntity.setDeptRegionName(priceRegionExpressEntity.getRegionName());
							}
						}else{
							PriceRegionEntity priceRegionEntity = regionService.searchRegionByID(priceValuationEntity.getDeptRegionId(), PricingConstants.PRESCRIPTION_REGION);
							if(priceRegionEntity!=null){
								//设置出发区域名称
								priceValuationEntity.setDeptRegionName(priceRegionEntity.getRegionName());
							}
						}
					}
				}
			}
		}
		return priceValuationEntityList;
	}
	/**
	 * 
	 * <p>
	 * 根据查询条件统计计费规则
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @date 2012-10-20 上午11:15:04
	 * 
	 * @param record
	 * 
	 * @see
	 */
	@Override
	public Long countByCodition(PriceValuationEntity record) {
		/**
		 * 查询快递全部基础产品
		 */
		if(PricingConstants.ALL.equals(record.getProductCode())){
			record.setProductCode(null);
			List<String> productCodes = new ArrayList<String>();
			productCodes.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
			productCodes.add(ExpWaybillConstants.ROUND_COUPON_PACKAGE);
			productCodes.add(ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);
			productCodes.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT);
			record.setProductCodes(productCodes);
		}
		return expressPriceValuationDao.countByCodition(record);
	}
	/**
	 * 
	 * <p>
	 * (激活增值服务)
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @date 2012-10-22 上午11:15:04
	 * 
	 * @param record
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void activeValueAdded(List<String> valuationIds) {
		// 激活之后修改相关的数据
		long versionNo = new Date().getTime();
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
		}
		// 当前登录用户empcode
		String userCode = user.getEmployee().getEmpCode();
		// 当前登录用户所在部门code
		String orgCode = user.getEmployee().getDepartment().getCode();
		// 查询所有要激活的计费规则ID条件
		PriceValuationEntity searchAllModel = new PriceValuationEntity();
		searchAllModel.setValuationIds(valuationIds);
		// 查询所有要激活的计费规则ID条件
		List<PriceValuationEntity> searchResultAllList = expressPriceValuationDao
				.selectByCodition(searchAllModel);
		for (PriceValuationEntity priceValuationEntity : searchResultAllList) 
		{
//			Date endTime = new Date(priceValuationEntity.getBeginTime()
//					.getTime() - PricingConstants.ONE_DAY_MILLISECOND);
			
			Date endTime = new Date(priceValuationEntity.getBeginTime()
				.getTime() - PricingConstants.ONE_MILLISECOND);
			
			//当天的0点00分00秒
			Date nowDate = DateUtils.convert(DateUtils.convert(new Date(),DateUtils.DATE_FORMAT),DateUtils.DATE_FORMAT);
			if(priceValuationEntity.getBeginTime().getTime()<(nowDate.getTime()+PricingConstants.ONE_MILLISECOND)){
				throw new PricingCommonException("区域名称为："+priceValuationEntity.getName()+"的生效日期小于明天",null);
			}
			// 查询原来的计费规则
			PriceValuationEntity searchModel = new PriceValuationEntity();
			// 设置长短途
			searchModel.setLongOrShort(priceValuationEntity.getLongOrShort());
			// 设置类型
			searchModel.setType(priceValuationEntity.getType());
			searchModel.setActive(FossConstants.ACTIVE);
			searchModel.setPricingEntryCode(priceValuationEntity.getPricingEntryCode());
			// 设置查询开始日期
			searchModel.setBeginTime(priceValuationEntity.getBeginTime());
			searchModel.setDeptRegionId(priceValuationEntity.getDeptRegionId());
			searchModel.setArrvRegionId(priceValuationEntity.getArrvRegionId());
			searchModel.setGoodsTypeCode(priceValuationEntity.getGoodsTypeCode());
			searchModel.setGoodsTypeId(priceValuationEntity.getGoodsTypeId());
			searchModel.setProductCode(priceValuationEntity.getProductCode());	
			// 查询出符合条件的的版本
			List<PriceValuationEntity> searchResultList = expressPriceValuationDao.selectByCoditionSq(searchModel);
			List<PriceCriteriaDetailEntity> newPriceCriteriaDetailEntityList  = expressPriceCriteriaDetailDao.selectByValuationId(priceValuationEntity.getId());
                        for(PriceValuationEntity priceValuationEntityModel:searchResultList)
                        {
                        	if(StringUtil.equalsIgnoreCase(PriceEntityConstants.PRICING_CODE_BF,priceValuationEntityModel.getPricingEntryCode()))
                            	{//判断限保物品是否重复
                            		List<PriceCriteriaDetailEntity> oldPriceCriteriaDetailEntityList  = expressPriceCriteriaDetailDao.selectByValuationId(priceValuationEntityModel.getId());
                            		if(CollectionUtils.isNotEmpty(oldPriceCriteriaDetailEntityList)&&CollectionUtils.isNotEmpty(newPriceCriteriaDetailEntityList))
                            		{
                                		//如果两者现报物品都不为空，且类型不相同，跳过本次循环
                                		if(StringUtils.isNotEmpty(newPriceCriteriaDetailEntityList.get(0).getSubType())
                                				&&StringUtils.isNotEmpty(oldPriceCriteriaDetailEntityList.get(0).getSubType())){
                                			 if(!newPriceCriteriaDetailEntityList.get(0).getSubType().equalsIgnoreCase(
                                					 oldPriceCriteriaDetailEntityList.get(0).getSubType())){
                                				 continue;
                                			}
                                		//如果两者现报物品一个为空，一个不为空，则认为类型不相同，跳过本次循环
                                		}else if((StringUtils.isEmpty(newPriceCriteriaDetailEntityList.get(0).getSubType())&&StringUtils.isNotEmpty(oldPriceCriteriaDetailEntityList.get(0).getSubType()))
                                				||(StringUtils.isNotEmpty(newPriceCriteriaDetailEntityList.get(0).getSubType())&&StringUtils.isEmpty(oldPriceCriteriaDetailEntityList.get(0).getSubType()))){
                                			continue;
                                		}
                            		}
                	            	PriceValuationEntity updateModel = new PriceValuationEntity();
                	            	// 设置版本号
                			updateModel.setVersionNo(versionNo);
                			// 设置ID
                			updateModel.setId(priceValuationEntityModel.getId());
                			updateModel.setEndTime(endTime);
                			updateModel.setModifyDate(new Date());
                			updateModel.setModifyUser(userCode);
                			updateModel.setModifyOrgCode(orgCode);
                			// 修改对应低版本数据
                			expressPriceValuationDao.updateValuation(updateModel);
                               }else{
            	            		PriceValuationEntity updateModel = new PriceValuationEntity();
        	            		// 设置版本号
            	   			updateModel.setVersionNo(versionNo);
            	   			// 设置ID
            	   			updateModel.setId(priceValuationEntityModel.getId());
            	   			updateModel.setEndTime(endTime);
            	   			updateModel.setModifyDate(new Date());
            	   			updateModel.setModifyUser(userCode);
            	   			updateModel.setModifyOrgCode(orgCode);
            	   			// 修改对应低版本数据
            	   			expressPriceValuationDao.updateValuation(updateModel);
                           }
                        }
    		 }
		expressPriceValuationDao.activeValueAdded(valuationIds);
		expressPriceCriteriaDetailDao.activeCriteriaDetailByValuationIds(valuationIds);
	}
	/**
	 * 
	 * <p>
	 * (立即激活激活增值服务)
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @date 2013-02-25 上午11:15:04
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void activeFast(PriceValuationEntity priceValuationEntity) {
		// 激活之后修改相关的数据
				long versionNo = new Date().getTime();
				// 获取当前登录用户
				UserEntity user = (UserEntity) UserContext.getCurrentUser();
				if(user==null){
					throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
				}
				if(null==priceValuationEntity || priceValuationEntity.getId() == null){
				    throw new PricingCommonException("激活参数为空!","激活参数为空!");
				}
				if(null == priceValuationEntity.getBeginTime()){
				    throw new PricingCommonException("激活生效时间参数为空!","激活生效时间参数为空!");
				}
				String id = priceValuationEntity.getId();
				List<String> valuationIds = new ArrayList<String>();
				valuationIds.add(id);
				// 当前登录用户empcode
				String userCode = user.getEmployee().getEmpCode();
				// 当前登录用户所在部门code
				String orgCode = user.getEmployee().getDepartment().getCode();
				
				//修改激活日期,组织机构,登录操作用户信息
				PriceValuationEntity tempPriceValuationEntity = expressPriceValuationDao.selectByPrimaryKey(id);
				tempPriceValuationEntity.setVersionNo(versionNo);
				tempPriceValuationEntity.setModifyDate(new Date());
				tempPriceValuationEntity.setModifyOrgCode(orgCode);
				tempPriceValuationEntity.setModifyUser(userCode);
				tempPriceValuationEntity.setBeginTime(priceValuationEntity.getBeginTime());
				expressPriceValuationDao.updateValuation(tempPriceValuationEntity);
				
				// 查询所有要激活的计费规则ID条件
				PriceValuationEntity searchAllModel = new PriceValuationEntity();
				searchAllModel.setValuationIds(valuationIds);
				// 查询所有要激活的计费规则ID条件
				List<PriceValuationEntity> searchResultAllList = expressPriceValuationDao
						.selectByCodition(searchAllModel);
				for (PriceValuationEntity priceValuation : searchResultAllList) {
					Date endTime = new Date(priceValuation.getBeginTime()
							.getTime() - PricingConstants.ONE_MILLISECOND);
					// 查询原来的计费规则
					PriceValuationEntity searchModel = new PriceValuationEntity();
					// 设置长短途
					searchModel.setLongOrShort(priceValuation.getLongOrShort());
					// 设置类型
					searchModel.setType(priceValuation.getType());
					searchModel.setActive(FossConstants.ACTIVE);
					searchModel.setPricingEntryCode(priceValuation.getPricingEntryCode());
					// 设置查询开始日期
					searchModel.setBeginTime(priceValuationEntity.getBeginTime());
					searchModel.setDeptRegionId(priceValuation.getDeptRegionId());
					searchModel.setArrvRegionId(priceValuation.getArrvRegionId());
					searchModel.setGoodsTypeCode(priceValuation.getGoodsTypeCode());
					searchModel.setGoodsTypeId(priceValuation.getGoodsTypeId());
					searchModel.setProductCode(priceValuation.getProductCode());
					// 查询出符合条件的的版本
					List<PriceValuationEntity> searchResultList = expressPriceValuationDao.selectByCoditionSq(searchModel);
					List<PriceCriteriaDetailEntity> newPriceCriteriaDetailEntityList  = expressPriceCriteriaDetailDao.selectByValuationId(priceValuation.getId());
                		            for(PriceValuationEntity priceValuationEntityModel:searchResultList){
                		            	if(StringUtil.equalsIgnoreCase(PriceEntityConstants.PRICING_CODE_BF,priceValuationEntityModel.getPricingEntryCode())){//判断限保物品是否重复
                		            		List<PriceCriteriaDetailEntity> oldPriceCriteriaDetailEntityList  = expressPriceCriteriaDetailDao.selectByValuationId(priceValuationEntityModel.getId());
                		            		if(CollectionUtils.isNotEmpty(oldPriceCriteriaDetailEntityList)&&CollectionUtils.isNotEmpty(newPriceCriteriaDetailEntityList))
                		            		{
                        		            		//如果两者现报物品都不为空，且类型不相同，跳过本次循环
                        		            		if(StringUtils.isNotEmpty(newPriceCriteriaDetailEntityList.get(0).getSubType())
                        		            				&&StringUtils.isNotEmpty(oldPriceCriteriaDetailEntityList.get(0).getSubType())){
                        		            			 if(!newPriceCriteriaDetailEntityList.get(0).getSubType().equalsIgnoreCase(
                        		            					 oldPriceCriteriaDetailEntityList.get(0).getSubType())){
                        		            				 continue;
                        		            			}
                        		            		//如果两者现报物品一个为空，一个不为空，则认为类型不相同，跳过本次循环
                        		            		}else if((StringUtils.isEmpty(newPriceCriteriaDetailEntityList.get(0).getSubType())&&StringUtils.isNotEmpty(oldPriceCriteriaDetailEntityList.get(0).getSubType()))
                        		            				||(StringUtils.isNotEmpty(newPriceCriteriaDetailEntityList.get(0).getSubType())&&StringUtils.isEmpty(oldPriceCriteriaDetailEntityList.get(0).getSubType()))){
                        		            			continue;
                        		            		}
                		            		}
                			            	PriceValuationEntity updateModel = new PriceValuationEntity();
                			            	// 设置版本号
                					updateModel.setVersionNo(versionNo);
                					// 设置ID
                					updateModel.setId(priceValuationEntityModel.getId());
                					updateModel.setEndTime(endTime);
                					updateModel.setModifyDate(new Date());
                					updateModel.setModifyUser(userCode);
                					updateModel.setModifyOrgCode(orgCode);
                					// 修改对应低版本数据
                					expressPriceValuationDao.updateValuation(updateModel);
                		               }else{
                			            	PriceValuationEntity updateModel = new PriceValuationEntity();
                			            	// 设置版本号
                			   		updateModel.setVersionNo(versionNo);
                			   		// 设置ID
                			   		updateModel.setId(priceValuationEntityModel.getId());
                			   		updateModel.setEndTime(endTime);
                			   		updateModel.setModifyDate(new Date());
                			   		updateModel.setModifyUser(userCode);
                			   		updateModel.setModifyOrgCode(orgCode);
                			   		// 修改对应低版本数据
                			   		expressPriceValuationDao.updateValuation(updateModel);
                		               }
                		        }
				}
				expressPriceValuationDao.activeValueAdded(valuationIds);
				expressPriceCriteriaDetailDao.activeCriteriaDetailByValuationIds(valuationIds);
	}
	/**
	 * 
	 * <p>
	 * (删除未激活增值服务)
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @date 2012-10-25 上午11:15:04
	 * 
	 * @param record
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void deleteValueAdded(List<String> valuationIds) {
		// 删除计费规则
		expressPriceValuationDao.deleteValueAdded(valuationIds);
		// 删除计价方式明细
		expressPriceCriteriaDetailDao.deleteCriteriaDetailByValuationIds(valuationIds);
	}
	/**
	 * .
	 * <p>
	 * 修改增值服务<br/>
	 * 方法名：updateValueAdded
	 * </p>
	 * 
	 * @param priceValuationEntity
	 *            计费规则实体
	 *            
	 * @param addPriceCriteriaDetailEntityList
	 *            新增计价方式明细实体
	 *            
	 * @param updatePriceCriteriaDetailEntityList
	 *            修改计价方式明细实体
	 *            
	 * @author 张斌
	 * 
	 * @时间 2012-10-18
	 * 
	 * @since JDK1.6
	 */
	@Override
	@Transactional
	public void updateValueAdded(PriceValuationEntity priceValuationEntity,
			List<PriceCriteriaDetailEntity> addPriceCriteriaDetailEntityList,
			List<PriceCriteriaDetailEntity> updatePriceCriteriaDetailEntityList,
			List<PriceCriteriaDetailEntity> deletePriceCriteriaDetailEntityList) {
		// 版本号
		long versionNo = new Date().getTime();
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
		}
		// 当前登录用户empcode
		String userCode = user.getEmployee().getEmpCode();
		// 当前登录用户所在部门code
		String orgCode = user.getEmployee().getDepartment().getCode();
		// 该计费规则是否激活
		String active = priceValuationEntity.getActive();
		// 计价方式ID
		String priceCriteriaEntityId = priceValuationEntity.getCriteriaId();
		// 修改部门
		priceValuationEntity.setModifyOrgCode(orgCode);
		// 修改人
		priceValuationEntity.setModifyUser(userCode);
		priceValuationEntity.setVersionNo(new Date().getTime());
		expressPriceValuationDao.updateValuation(priceValuationEntity);
		if (addPriceCriteriaDetailEntityList != null && addPriceCriteriaDetailEntityList.size() > PricingConstants.ZERO) {
			// 新增计价方式明细
			for (PriceCriteriaDetailEntity priceCriteriaDetailEntity : addPriceCriteriaDetailEntityList) {
				String priceCriteriaDetailEntityId = UUIDUtils.getUUID();
				priceCriteriaDetailEntity.setActive(active);
				priceCriteriaDetailEntity.setId(priceCriteriaDetailEntityId);
				priceCriteriaDetailEntity.setVersionNo(versionNo);
				priceCriteriaDetailEntity.setPricingCriteriaId(priceCriteriaEntityId);
				// 价格计算表达式——简单费率规则
				priceCriteriaDetailEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
				// DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
				// 2015/12/3 liding add
				// 因mapper文件中sql的修改，fee在此乘100
				if (priceCriteriaDetailEntity.getFee() != null) {
					long fee = priceCriteriaDetailEntity.getFee();
					if (fee != 0) {
						fee = fee * NumberConstants.NUMBER_100;
						priceCriteriaDetailEntity.setFee(fee);
					}
				}
				//2015/12/3 liding add end
				//当是其他费用和签收回单、超远派送 时采用固定价暂时写死，之后会从缓存中去判断
				if (PricingConstants.PriceEntityConstants.PRICING_CODE_QS.equals(
						priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_QT
								.equals(priceValuationEntity.getPricingEntryCode())|| PricingConstants.PriceEntityConstants.PRICING_CODE_CY
								.equals(priceValuationEntity.getPricingEntryCode())) {
					// 价格计算表达式——固定价
					priceCriteriaDetailEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_FIXED_ID);
				}
				priceCriteriaDetailEntity.setPricingValuationId(priceValuationEntity.getId());
				expressPriceCriteriaDetailDao.insertSelective(priceCriteriaDetailEntity);
			}
		}
		if (updatePriceCriteriaDetailEntityList != null
				&& updatePriceCriteriaDetailEntityList.size() > PricingConstants.ZERO) {
			// 修改的计价方式明细
			for (PriceCriteriaDetailEntity priceCriteriaDetailEntity : updatePriceCriteriaDetailEntityList) {
				//DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
				//2015/12/3 liding add
				//因mapper文件中sql的修改，fee在此乘100
				if (priceCriteriaDetailEntity.getFee() != null) {
					long fee = priceCriteriaDetailEntity.getFee();
					if (fee != 0) {
						fee = fee * NumberConstants.NUMBER_100;
						priceCriteriaDetailEntity.setFee(fee);
					}
				}
				//2015/12/3 liding add end
				expressPriceCriteriaDetailDao.updateCriteriaDetailByPrimaryKey(priceCriteriaDetailEntity);
			}
		} else {
			//只修改限保物品，不修改明细
			if (StringUtils.isNotEmpty(priceValuationEntity.getSubType()) 
					&& StringUtils.isNotEmpty(priceValuationEntity.getId())) {
				PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
				priceCriteriaDetailEntity.setSubType(priceValuationEntity.getSubType());
				priceCriteriaDetailEntity.setPricingValuationId(priceValuationEntity.getId());
				expressPriceCriteriaDetailDao.updateCriteriaDetailByPricingValuationId(priceCriteriaDetailEntity);
			}
		}
		if (deletePriceCriteriaDetailEntityList != null && 
				deletePriceCriteriaDetailEntityList.size() > PricingConstants.ZERO) {
			for (PriceCriteriaDetailEntity dto : deletePriceCriteriaDetailEntityList) {
				expressPriceCriteriaDetailDao.deleteByPrimaryKey(dto.getId());
			}
		}
	}
	/**
	 * 
	 * 查询产品条目信息-
	 * 
	 * @author DP-Foss-张斌
	 * @date 2012-10-29 下午6:47:34
	 */
	@Override
	public List<ProductItemEntity> findProductItemByCondiction() {
		ProductItemEntity productItem = new ProductItemEntity();
		// 查询激活的
		productItem.setActive(FossConstants.ACTIVE);
		return productItemService.findProductItemByCondiction(productItem);
	}
	/**
	 * 
	 * 查询基础产品信息(三级产品)
	 * 
	 * @author DP-Foss-张斌
	 * @date 2012-10-29 下午6:47:34
	 */
	@Override
	public List<ProductEntity> findProductByCondition() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
		return productService.findExternalProductByCondition(condtion, null);
	}
	 /**
     * 
     * 查询货物定义信息
     * @author DP-Foss-张斌
     * @date 2012-10-30 下午5:13:38
     */
	@Override
	public List<GoodsTypeEntity> findGoodsTypeByCondiction() {
		GoodsTypeEntity goodsTypeEntity = new GoodsTypeEntity();
		// 查询激活的
		goodsTypeEntity.setActive(FossConstants.ACTIVE);
		return goodsTypeService.findGoodsTypeByCondiction(goodsTypeEntity);
	}
	/**
     * 
     * 根据不同条件查询增值服务计价规则
     * 
     * 各种增值服务计价规则查询： 基础增值服务，产品增值服务，区域增值服务
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-11-3 下午2:28:56
     */
	@Override
	public List<ResultProductPriceDto> searchValueAddedPricingValuationByCondition(
		QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
	    List<ResultProductPriceDto> regionEntityList = expressPriceValuationDao.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
	    return regionEntityList;
	}
	/**
	 * 
	 * （筛选计费规则 - 根据条件按照异常优先级找出最合理的计费规则下的计费明细信息,优先原则为【区域增值服务】->【产品增值服务】-【基础增值服务】
	 * 提供给计算增值服务接口中(IBillCaculateService.searchValueAddPriceList) - 用到该放方法
	 * 
	 * ）
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-11-7 下午12:52:16
	 * 
	 * @param queryBillCacilateValueAddDto
	 *            计费规则与费用明细的组合查询条件
	 *            
	 */
	@Override
	public List<ResultProductPriceDto> siftValuationBillingRuleService(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto){
	    String oldGoodsTypeCode=queryBillCacilateValueAddDto.getGoodsTypeCode();
	    queryBillCacilateValueAddDto.setGoodsTypeCode(null);
	    queryBillCacilateValueAddDto.setAllNet(PricingConstants.ALL);
	  //区域增值
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED);
	    String longOrShort=queryBillCacilateValueAddDto.getLongOrShort();
	    queryBillCacilateValueAddDto.setLongOrShort(null);	    
	    List<ResultProductPriceDto> entityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
	    entityList=filterGoodsType(entityList,oldGoodsTypeCode);
	    entityList=filterLongORshort(entityList,longOrShort);
	    //筛选合适的区域增值服务
	    entityList = filterBestMapEntity(entityList);
	    if(!CollectionUtils.isEmpty(entityList)){
		 queryBillCacilateValueAddDto.setGoodsTypeCode(oldGoodsTypeCode);		
		return entityList;
	    }
	    queryBillCacilateValueAddDto.setDeptRegionId(null);
	    queryBillCacilateValueAddDto.setArrvRegionId(null);
	    queryBillCacilateValueAddDto.setAllNet(null);
	  //产品区域增值
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_PRODUCTVALUEADDED);
	    entityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
	    entityList=filterGoodsType(entityList,oldGoodsTypeCode);
	    entityList=filterLongORshort(entityList,longOrShort);
	    if(!CollectionUtils.isEmpty(entityList)){
		 queryBillCacilateValueAddDto.setGoodsTypeCode(oldGoodsTypeCode);
		return entityList;
	    }
	    queryBillCacilateValueAddDto.setProductCode(null);
	    queryBillCacilateValueAddDto.setGoodsTypeCode(null);
	  //基础增值	   
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED); 
	    entityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);    
	    entityList=filterLongORshort(entityList,longOrShort);
	    if(!CollectionUtils.isEmpty(entityList)){
		 queryBillCacilateValueAddDto.setGoodsTypeCode(oldGoodsTypeCode);
		 return entityList;
	    }
	    queryBillCacilateValueAddDto.setGoodsTypeCode(oldGoodsTypeCode);
	    return null;
	}
	/**
	 * 
	 * （筛选计费规则 - 根据条件按照异常优先级找出最合理的计费规则下的计费明细信息,优先原则为【区域增值服务】->【产品增值服务】-【基础增值服务】
	 * 提供给计算增值服务接口中(IBillCaculateService.searchValueAddPriceList) - 用到该放方法
	 * 
	 * ）
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-11-7 下午12:52:16
	 * 
	 * @param queryBillCacilateValueAddDto
	 *            计费规则与费用明细的组合查询条件
	 *            
	 */
	@Override
	public Map<String, List<ResultProductPriceDto>> siftValueAddRuleService(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto){
		/**
		 * 查询区域增值服务
		 */
		String oldGoodsTypeCode = queryBillCacilateValueAddDto.getGoodsTypeCode();
		String oldProductCode = queryBillCacilateValueAddDto.getProductCode();
		String longOrShort = queryBillCacilateValueAddDto.getLongOrShort();
		// 去除相关查询条件
		queryBillCacilateValueAddDto.setGoodsTypeCode(null);
		queryBillCacilateValueAddDto.setLongOrShort(null);
		queryBillCacilateValueAddDto.setProductCode(null);
		// 设置相关查询条件
		queryBillCacilateValueAddDto.setAllNet(PricingConstants.ALL);
		// 区域增值标识
		queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED);
		// 执行查询操作
		List<ResultProductPriceDto> regionEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
		// 筛选合适的区域增值服务
		if(CollectionUtils.isNotEmpty(regionEntityList)){
			// 根据产品筛选
			regionEntityList = filterProduct(regionEntityList, oldProductCode);
			// 根据货物类型筛选
			regionEntityList = filterGoodsType(regionEntityList, oldGoodsTypeCode);
			// 根据长短途筛选
			regionEntityList = filterLongORshort(regionEntityList, longOrShort);
			// 根据区域途筛选
			regionEntityList = filterBestMapEntity(regionEntityList);
		}
		
		/**
		 * 查询产品增值服务
		 */
		//如果没有合适的区域增值服务，则查询产品增值服务
	    queryBillCacilateValueAddDto.setDeptRegionId(null);
	    queryBillCacilateValueAddDto.setArrvRegionId(null);
	    queryBillCacilateValueAddDto.setAllNet(null);
	    //产品增值标识
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_PRODUCTVALUEADDED);
	    //设置产品编号
	    queryBillCacilateValueAddDto.setProductCode(oldProductCode);
	    // 执行查询操作
	    //List<ResultProductPriceDto> productEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
		//productEntityList = filterGoodsType(productEntityList, oldGoodsTypeCode);
		//productEntityList = filterLongORshort(productEntityList, longOrShort);
	    /**
		 * 查询基础增值服务
		 */
	    queryBillCacilateValueAddDto.setProductCode(null);
	    queryBillCacilateValueAddDto.setGoodsTypeCode(null);
	    //基础增值标识
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED); 
	    List<ResultProductPriceDto> baseEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);    
		baseEntityList = filterLongORshort(baseEntityList, longOrShort);
		//根据业务要求组合集合数据
		Map<String, List<ResultProductPriceDto>> resultMap = new HashMap<String, List<ResultProductPriceDto>>();
		if (!CollectionUtils.isEmpty(regionEntityList)) {
			resultMap.put("other", regionEntityList);
		} 
		//else if(!CollectionUtils.isEmpty(productEntityList)) {
		//	resultMap.put("other", productEntityList);
		//}
		//if(!CollectionUtils.isEmpty(baseEntityList)) {
		//	resultMap.put("base", baseEntityList);
		//}
		queryBillCacilateValueAddDto.setGoodsTypeCode(oldGoodsTypeCode);
		queryBillCacilateValueAddDto.setProductCode(oldProductCode);
		queryBillCacilateValueAddDto.setLongOrShort(longOrShort);
		return resultMap;
	}



/**
	 * 
	 * @Description: 过滤产品CODE
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-26 下午6:05:22
	 * 
	 * @param entityList
	 * 
	 * @param oldProductCode
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private List<ResultProductPriceDto> filterProduct(List<ResultProductPriceDto> entityList, String oldProductCode) {
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		List<ResultProductPriceDto> allList = new ArrayList<ResultProductPriceDto>();
		List<ResultProductPriceDto> singleList = new ArrayList<ResultProductPriceDto>();
		for (int loop = 0; loop < entityList.size(); loop++) {
			ResultProductPriceDto resultObject = entityList.get(loop);
			if (StringUtil.equalsIgnoreCase(resultObject.getProductCode(), oldProductCode)) {
				singleList.add(resultObject);
			}
			if (StringUtil.isEmpty(resultObject.getProductCode())) {
				allList.add(resultObject);
			}
		}
		if (CollectionUtils.isNotEmpty(singleList)) {
			return singleList;
		} else {
			return allList;
		}
	}
	/**
	 * 
	 *	过滤长短途
	 *
	 * @param entityList
	 *  
	 * @param longOrShort 
	 * 
	 * @return 
	 */
	private List<ResultProductPriceDto> filterLongORshort(List<ResultProductPriceDto> entityList, String longOrShort) {
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		List<ResultProductPriceDto> allList = new ArrayList<ResultProductPriceDto>();
		List<ResultProductPriceDto> singleList = new ArrayList<ResultProductPriceDto>();
		if (StringUtil.isEmpty(longOrShort)) {
			longOrShort = PricingConstants.ALL;
		}
		for (int loop = 0; loop < entityList.size(); loop++) {
			ResultProductPriceDto resultObject = entityList.get(loop);
			if (StringUtil.equalsIgnoreCase(resultObject.getLongOrShort(), longOrShort)) {
				singleList.add(resultObject);
			}
			if (StringUtil.equalsIgnoreCase(resultObject.getLongOrShort(), PricingConstants.ALL)) {
				allList.add(resultObject);
			}

		}
		if (CollectionUtils.isNotEmpty(singleList)) {
			return singleList;
		} else {
			return allList;
		}
	}
	/**
	 * 
	 * 过滤货物类型
	 * 
	 * @param entityList
	 *  
	 * @param oldGoodsTypeCode 
	 * 
	 * @return 
	 */
	private List<ResultProductPriceDto> filterGoodsType(List<ResultProductPriceDto> entityList, String oldGoodsTypeCode) {
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		List<ResultProductPriceDto> allList = new ArrayList<ResultProductPriceDto>();
		List<ResultProductPriceDto> singleList = new ArrayList<ResultProductPriceDto>();
		if (StringUtil.isEmpty(oldGoodsTypeCode)) {
			oldGoodsTypeCode = GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001;
		}
		for (int loop = 0; loop < entityList.size(); loop++) {
			ResultProductPriceDto resultObject = entityList.get(loop);
			if (StringUtil.equalsIgnoreCase(resultObject.getGoodsTypeCode(), oldGoodsTypeCode)) {
				singleList.add(resultObject);
			}
			if (StringUtil.isEmpty(resultObject.getGoodsTypeCode())) {
				allList.add(resultObject);
			}
		}
		if (CollectionUtils.isNotEmpty(singleList)) {
			return singleList;
		} else {
			return allList;
		}
	}
	/**
	 * 
	 * 过滤最优先的区域增值计费规则
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-11-7 下午1:27:13
	 * 
	 */
	private List<ResultProductPriceDto> filterBestMapEntity(List<ResultProductPriceDto> resultProductPriceList) {
		if (CollectionUtils.isEmpty(resultProductPriceList)) {
			return null;
		}
		// 都不全网点数据集合
		List<ResultProductPriceDto> noFullNetWorkList = new ArrayList<ResultProductPriceDto>();
		// 目的地区域为全网点数据集合
		List<ResultProductPriceDto> arrvRegionFullNetworkList = new ArrayList<ResultProductPriceDto>();
		// 始发区域为全网点数据集合
		List<ResultProductPriceDto> deptRegionFullNetWorkList = new ArrayList<ResultProductPriceDto>();
		// 始发区域与目的地区域都为全网点数据集合
		List<ResultProductPriceDto> fullNetWorkList = new ArrayList<ResultProductPriceDto>();
		// 打散数据
		for (ResultProductPriceDto resultProductPriceDto : resultProductPriceList) {
			// 始发区域
			String deptRegionId = resultProductPriceDto.getDeptRegionId();
			// 到达区域
			String arrvRegionId = resultProductPriceDto.getArrvRegionId();
			// 都不全网点
			if (!PricingConstants.ALL.equals(deptRegionId) && !PricingConstants.ALL.equals(arrvRegionId)) {
				noFullNetWorkList.add(resultProductPriceDto);
				// 始发区域为全网点
			} else if (PricingConstants.ALL.equals(deptRegionId) && !PricingConstants.ALL.equals(arrvRegionId)) {
				deptRegionFullNetWorkList.add(resultProductPriceDto);
				// 目的地区域为全网点
			} else if (!PricingConstants.ALL.equals(deptRegionId) && PricingConstants.ALL.equals(arrvRegionId)) {
				arrvRegionFullNetworkList.add(resultProductPriceDto);
				// 始发区域与目的地区域都为全网点
			} else if (PricingConstants.ALL.equals(deptRegionId) && PricingConstants.ALL.equals(arrvRegionId)) {
				fullNetWorkList.add(resultProductPriceDto);
			}
		}
		if (noFullNetWorkList.size() > 0) {
			return noFullNetWorkList;
		} else if (arrvRegionFullNetworkList.size() > PricingConstants.ZERO) {
			return arrvRegionFullNetworkList;
		} else if (deptRegionFullNetWorkList.size() > PricingConstants.ZERO) {
			return deptRegionFullNetWorkList;
		} else {
			return fullNetWorkList;
		}
	}
	/**
	 * .
	 * <p>
	 * 修改计费规则实体<br/>
	 * 方法名：updatePriceValuation
	 * </p>
	 * 
	 * @param priceValuationEntity
	 *            计费规则实体
	 *            
	 * @author 张斌
	 * 
	 * @时间 2012-10-18
	 * 
	 * @since JDK1.6
	 */
	@Override
	@Transactional
	public void updatePriceValuation(PriceValuationEntity priceValuationEntity) {
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if (user == null) {
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN, PricingCommonException.NOT_LOGIN);
		}
		// 当前登录用户empcode
		String userCode = user.getEmployee().getEmpCode();
		// 当前登录用户所在部门code
		String orgCode = user.getEmployee().getDepartment().getCode();
		// 修改部门
		priceValuationEntity.setModifyOrgCode(orgCode);
		// 修改人
		priceValuationEntity.setVersionNo(new Date().getTime());
		priceValuationEntity.setModifyUser(userCode);
		expressPriceValuationDao.updateValuation(priceValuationEntity);
	}
	/**
	 * 
	 * 查询基础产品信息(二级产品)
	 * 
	 * @author DP-Foss-张斌
	 * 
	 * @date 2012-10-29 下午6:47:34
	 */
	@Override
	public List<ProductEntity> findTwoLevelProduct() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_2);
		return productService.findExternalProductByCondition(condtion, null);
	}
	/**
	 * 
	 * 查询基础产品信息(一级产品)
	 * 
	 * @author DP-Foss-张斌
	 * 
	 * @date 2012-10-29 下午6:47:34
	 */
	@Override
	public List<ProductEntity> findOneLevelProduct() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_1);
		return productService.findExternalProductByCondition(condtion, null);
	}
	/**
	 * .
	 * <p>
	 * 自动构建CODE<br/>
	 * 方法名：createCode
	 * </p>
	 * 
	 * @param type
	 *            增值服务类型
	 *            
	 * @return Boolean
	 * 
	 * @author 张斌
	 * 
	 * @时间 2013-1-8
	 * 
	 * @since JDK1.6
	 */
	private String createCode(String type){
		 Date currentTime = new Date();
	     SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	     String dateString = formatter.format(currentTime);
		String code = PricingConstants.EMPTY_STRING;
		if(PricingConstants.VALUATION_TYPE_BASICVALUEADDED.equals(type)){
			code = PricingConstants.PRICING_BASIC_CODE;
		}else if(PricingConstants.VALUATION_TYPE_PRODUCTVALUEADDED.equals(type)){
			code = PricingConstants.PRICING_PRODUCT_CODE;
		}else if(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED.equals(type)){
			code = PricingConstants.PRICING_REGION_CODE;
		}
		code = code.concat(dateString);
		return code;
	}
	/**
	 * .
	 * <p>
	 * 检测增值服务名称<br/>
	 * 方法名：isTheSameValueAddName
	 * </p>
	 * 
	 * @param regionName
	 *            区域名称
	 *            
	 * @return Boolean
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-11-23
	 * 
	 * @since JDK1.6
	 */
	public boolean isTheSameValueAddName(PriceValuationEntity priceValuationEntity, boolean isUpdate) {
		boolean flag = false;
		List<PriceValuationEntity> priceValuationEntityList = expressPriceValuationDao.selectByName(priceValuationEntity);
		if (priceValuationEntityList != null && priceValuationEntityList.size() > 1) {
			flag = true;
		} else if (priceValuationEntityList != null&&priceValuationEntityList.size() == 1) {
			//如果修改还得判断是否ID相同
			if (isUpdate) {
				if (StringUtils.equals(priceValuationEntityList.get(0).getId() , priceValuationEntity.getId()) ) {
					flag = false;
				} else {
					flag = true;
				}
			} else {
				//如果是新增，直接表示有重复
				flag = true;
			}
		}else{
			flag = false;
		}
		return flag;
	}
	/**
	 * 
	 * 查询基础产品信息(三级产品)
	 * 
	 * @author DP-Foss-张斌
	 * 
	 * @date 2012-10-29 下午6:47:34
	 */
	@Override
	public List<ProductEntity> findThreeLevelProduct() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
		return productService.findExternalProductByCondition(condtion, null);
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
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
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
