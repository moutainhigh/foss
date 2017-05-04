/**
 *  initial comments.
 *  
 *  BillCaculateService 是开单计算价格、增值服务、折扣、优惠活动的实现类
 *  计算运费分别为：FRT
 *  汽运  - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 0
 *  空运 - t_srv_pice_plan价格表  字段 TRANSPORT_FLAG = 1
 *  
 *  计算增值服务：  VAS
 *  
 *  保费 - BF
 *  
 *  代收货款 - HK
 *  
 *  送货费 - SH
 *  
 *  接货费 - JH
 *  
 *  签收回单 -QS
 *  
 *  保管费 - CCF
 *  
 *  综合信息费 -ZHXX
 *  
 *  燃油附加费 - RYFJ
 *  
 *  中转费 - ZZ
 *     
 *  接货差额补差 -JHCEBC
 *  
 *  电子优惠券 - DZYHQ
 *  
 *  其他费用 -QT
 *  
 *  包装费 -BZ
 *  
 *  送货上楼费 -SHSL
 *  
 *  超远派送费 -CY
 *  
 *  送货进仓费 -SHJCF
 *  
 *  更改费 -GGF
 *  
 *  以上服务相关代码进行不同维度计算。
 *  
 *  可支绑定如产品、区域 等等进行价格、折扣、优惠计算
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/BillCaculateService.java
 * 
 * FILE NAME        	: BillCaculateService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerIndustryService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGradientDiscountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkActivitiesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISpecialdiscountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerIndustryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GradientDiscountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkOptionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialdiscountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.GradientDiscountItemDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionValueAddDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.ISpecialDiscountDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.DiscountTypeInterface;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.ICityMarketPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDiscountPriorityService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectiveExpressPlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPriceValuationService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricingValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IInempDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IMinFeePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterPriceCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterPriceService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPopValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceEntryService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceValuationService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPricingValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionAirService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionValueAddService;
import com.deppon.foss.module.pickup.pricing.api.server.util.JsonUtil;
import com.deppon.foss.module.pickup.pricing.api.server.util.PriceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountOrgConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountPriorityEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExpressDiscountEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MinFeePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.WaybillSpecialDiscountEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountCondition;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountParmDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DiscountResultDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectiveExpressPlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.NewResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryOuterPriceCaccilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultOuterPriceCaccilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.BillCaculateServiceException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.OuterPriceCaculateServiceException;
import com.deppon.foss.module.pickup.pricing.api.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.WaybillConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * The Class BillCaculateService.
 *
 * @Description:开单计费服务
 * BillCaculateService.java Create on 2013-3-17 下午5:50:48
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class BillCaculateService implements IBillCaculateService {  
	
	private static final int NUMBER_100 = 100;

	private static final int NUMBER_800 = 800;

	private static final int NUMBER_1000 = 1000;

	private static final int NUMBER_1000000 = 1000000;
	
	private static final int THREE = 3;
	
	private static final int FOUR = 4;
	//普元规则引擎接口服务地址
		private WebClient clientPyService;
		
		public WebClient getClientPyService() {
			return clientPyService;
		}

		public void setClientPyService(WebClient clientPyService) {
			this.clientPyService = clientPyService;
		}
	/** 日志信息. */
	private static final Logger log = Logger.getLogger(BillCaculateService.class);
//private void setRulengineTimeOut(){
//		
//		WebClient.getConfig(clientPyService).getHttpConduit().getClient().setConnectionTimeout(3000);
//	}
	/**
	 *客户合同信息Service
	 */
	@Inject
	ICusBargainService cusBargainService;
	
	/**
	 * 特惠折扣信息Service
	 * */
	@Inject
	ISpecialdiscountService specialdiscountService;
	
	public void setSpecialdiscountService(
			ISpecialdiscountService specialdiscountService) {
		this.specialdiscountService = specialdiscountService;
	}
	/**
	 *快递折扣方案Service
	 */
//	@Inject
	IExpressDiscountPlanService expressDiscountPlanService;
	
	/**
	 * 内部员工折扣方案
	 */
	@Inject
	IInempDiscountPlanService inempDiscountPlanService;
	/** 计费规则 Service. */
	@Inject
	private IPriceValuationService priceValuationService;
	
	/** 快递计费规则 Service. */
	@Inject
	private IExpressPriceValuationService expressPriceValuationService;
	
	public void setExpressPriceValuationService(
			IExpressPriceValuationService expressPriceValuationService) {
		this.expressPriceValuationService = expressPriceValuationService;
	}


	public void setExpressDiscountPlanService(
			IExpressDiscountPlanService expressDiscountPlanService) {
		this.expressDiscountPlanService = expressDiscountPlanService;
	}
	//查询增值区域
	private IRegionValueAddDao regionValueAddDao;

	public IRegionValueAddDao getRegionValueAddDao() {
		return regionValueAddDao;
	}

	public void setRegionValueAddDao(IRegionValueAddDao regionValueAddDao) {
		this.regionValueAddDao = regionValueAddDao;
	}
	
	public void setInempDiscountPlanService(
			IInempDiscountPlanService inempDiscountPlanService) {
		this.inempDiscountPlanService = inempDiscountPlanService;
	}
	
	//特殊折扣区域DAO
	@Inject
	private ISpecialDiscountDao specialDiscountDao;
	public void setSpecialDiscountDao(ISpecialDiscountDao specialDiscountDao) {
		this.specialDiscountDao = specialDiscountDao;
	}
	
	/** 计价明细 Service. */
	@Inject
	private IEffectivePlanDetailService effectivePlanDetailService;

	/** 出发区域 Service. */
	@Inject
	private IRegionService regionService;
	
	/** 到达区域Service. */
	@Inject
	private IRegionArriveService regionArriveService;	
	
	/** 空运区域 Service. */
	@Inject
	private IRegionAirService regionAirService;	
	
	/**
	 * 偏线计费服务
	 */
	@Inject
	IOuterPriceCaculateService outerPriceCaculateService;
	
	/**
	 * 数据字典
	 */
	@Inject
	IDataDictionaryValueService dataDictionaryValueService;
	
	/**
	 * 快递时效
	 */
	@Inject
	private IEffectiveExpressPlanDetailService effectiveExpressPlanDetailService;
	
	/**
	 * 区域 Service
	 */
	@Inject
	private IRegionExpressService regionExpressService;
	/**
	 * 大礼包service
	 */
	private ICityMarketPlanService cityMarketPlanService;

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void setOuterPriceCaculateService(
			IOuterPriceCaculateService outerPriceCaculateService) {
		this.outerPriceCaculateService = outerPriceCaculateService;
	}

	/**
	 * Sets the region arrive service.
	 *
	 * @param regionArriveService the new region arrive service
	 */
	public void setRegionArriveService(IRegionArriveService regionArriveService) {
		this.regionArriveService = regionArriveService;
	}
	
	
	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}

	/**
	 * Sets the region value add service.
	 *
	 * @param regionValueAddService the new region value add service
	 */
	public void setRegionValueAddService(
			IRegionValueAddService regionValueAddService) {
		this.regionValueAddService = regionValueAddService;
	}

	/** 增值区域. */
	@Inject
	private IRegionValueAddService regionValueAddService;

	/** 计价规则 Service. */
	@Inject
	private IPricingValueAddedService pricingValueAddedService;
	
	/** 新计价规则 Service. */
	@Inject
	private IPopValueAddedService popValueAddedService;
	
	/** 快递计价规则 Service. */
	@Inject
	private IExpressPricingValueAddedService expressPricingValueAddedService;
	
	public void setExpressPricingValueAddedService(
			IExpressPricingValueAddedService expressPricingValueAddedService) {
		this.expressPricingValueAddedService = expressPricingValueAddedService;
	}

	 /**
     * 行政区域接口.
     */
    private IAdministrativeRegionsService administrativeRegionsService;
    
    
	public IAdministrativeRegionsService getAdministrativeRegionsService() {
		return administrativeRegionsService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/** 产品 Service. */
	@Inject
	private IProductService productService;

	/** 货物 Service. */
	@Inject
	private IGoodsTypeService goodsTypeService;

	/** 计价条目DAO. */
	@Inject
	IPriceEntryDao priceEntryDao;
	
	/** 计价条目Service. */
	@Inject
	IPriceEntryService priceEntryService;
	
	/** 系统参数Service. */
	@Inject
	private IConfigurationParamsService configurationParamsService;
	
	/** 经济自提件. */
	private IMinFeePlanService minFeePlanService;
	/**
	 * 营业部
	 */
	private ISaleDepartmentService saleDepartmentService;

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	/** 折扣类型工厂类. */
	@Inject
	DiscountTypeFactory discountTypeFactory;
	
	/**
	 * Sets the min fee plan service.
	 *
	 * @param minFeePlanService the new min fee plan service
	 */
	public void setMinFeePlanService(IMinFeePlanService minFeePlanService) {
		this.minFeePlanService = minFeePlanService;
	}

	/** 折扣优先级 SERVICE. */
	@Inject
	private IDiscountPriorityService discountPriorityService;
	
	/** 部门管理 SERVICE. */
	@Inject
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 客户服务服务接口
	 */
	@Inject
	private ICustomerService customerService;
	/**
	 * 行业服务接口
	 */
	private ICustomerIndustryService customerIndustryService;

	public void setCustomerIndustryService(
			ICustomerIndustryService customerIndustryService) {
		this.customerIndustryService = customerIndustryService;
	}

	/**
	 * 市场活动服务接口
	 */
	@Inject
	private IMarkActivitiesService markActivitiesService;
	
	public void setMarkActivitiesService(
			IMarkActivitiesService markActivitiesService) {
		this.markActivitiesService = markActivitiesService;
	}
	IOuterPriceService outerPriceService;
	public void setOuterPriceService(IOuterPriceService outerPriceService) {
		this.outerPriceService = outerPriceService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * 获取 折扣类型工厂类.
	 *
	 * @return the 折扣类型工厂类
	 */
	public DiscountTypeFactory getDiscountTypeFactory() {
	    return discountTypeFactory;
	}
	/**
	 * 设置 折扣类型工厂类.
	 *
	 * @param discountTypeFactory the new 折扣类型工厂类
	 */
	public void setDiscountTypeFactory(DiscountTypeFactory discountTypeFactory) {
	    this.discountTypeFactory = discountTypeFactory;
	}
	/**
	 * 设置 计价条目DAO.
	 *
	 * @param priceEntryDao the new 计价条目DAO
	 */
	public void setPriceEntryDao(IPriceEntryDao priceEntryDao) {
	    this.priceEntryDao = priceEntryDao;
	}
	/**
	 * 设置 系统参数Service.
	 *
	 * @param configurationParamsService the new 系统参数Service
	 */
	public void setConfigurationParamsService(
		IConfigurationParamsService configurationParamsService) {
	    this.configurationParamsService = configurationParamsService;
	}
	/**
	 * 
	 * 设置 货物 Service.
	 *
	 * @param goodsTypeService the new 货物 Service
	 */
	public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
	    this.goodsTypeService = goodsTypeService;
	}
	/**
	 * 
	 * 
	 * 设置 产品 Service.
	 *
	 * @param productService the new 产品 Service
	 */
	public void setProductService(IProductService productService) {
	    this.productService = productService;
	}
	/**
	 * 设置 计价规则 Service.
	 *
	 * @param pricingValueAddedService the new 计价规则 Service
	 */
	public void setPricingValueAddedService(
		IPricingValueAddedService pricingValueAddedService) {
	    this.pricingValueAddedService = pricingValueAddedService;
	}

	
	public IPopValueAddedService getPopValueAddedService() {
		return popValueAddedService;
	}

	public void setPopValueAddedService(IPopValueAddedService popValueAddedService) {
		this.popValueAddedService = popValueAddedService;
	}

	/**
	 * 
	 * 
	 * 获取 区域 Service.
	 *
	 * @return the 区域 Service
	 */
	public IRegionService getRegionService() {
		return regionService;
	}
	/**
	 * 设置 区域 Service.
	 *
	 * @param regionService the new 区域 Service
	 */
	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}

	/**
	 * 
	 * 
	 * 设置 计费规则 Service.
	 *
	 * @param priceValuationService the new 计费规则 Service
	 */
	public void setPriceValuationService(
			IPriceValuationService priceValuationService) {
		this.priceValuationService = priceValuationService;
	}
	
	/**
	 * 
	 * 
	 * 设置 计价明细 Service.
	 *
	 * @param effectivePlanDetailService the new 计价明细 Service
	 */
	public void setEffectivePlanDetailService(
			IEffectivePlanDetailService effectivePlanDetailService) {
		this.effectivePlanDetailService = effectivePlanDetailService;
	}
	
	/**
	 * 
	 * 
	 * 设置 折扣优先级 SERVICE.
	 *
	 * @param discountPriorityService the new 折扣优先级 SERVICE
	 */
	public void setDiscountPriorityService(
			IDiscountPriorityService discountPriorityService) {
		this.discountPriorityService = discountPriorityService;
	}
	/**
	 * 
	 * 
	 * 设置 部门管理 SERVICE.
	 *
	 * @param orgAdministrativeInfoService the new 部门管理 SERVICE
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 
	 * 
	 * 设置 计价条目Service.
	 *
	 * @param priceEntryService the new 计价条目Service
	 */
	public void setPriceEntryService(IPriceEntryService priceEntryService) {
		this.priceEntryService = priceEntryService;
	}
	/**
	 * 
	 * 
	 * 设置 区域 Service.
	 *
	 * @param regionAirService the new 区域 Service
	 */
	public void setRegionAirService(IRegionAirService regionAirService) {
		this.regionAirService = regionAirService;
	}
	
	/**
	 * <p>
	 * Description: 查询产品时效 <br />
	 * </p>.
	 *
	 * @param originalOrgCode 出发部门
	 * @param destinationOrgCode 到达部门
	 * @param productCode 产品code
	 * @param billDate the bill date
	 * @return the list
	 * @throws BillCaculateServiceException the bill caculate service exception
	 * @author DP-Foss-YueHongJie
	 * @version 0.1 2012-10-25
	 * @parm  billDate 开单日期 可空 ，默认为当前时间
	 * List<EffectivePlanDto>
	 */
	@Override
	public List<EffectivePlanDto> searchEffectivePlanDetailList(String originalOrgCode, 
			String destinationOrgCode, String productCode,Date billDate) throws BillCaculateServiceException{
		return effectivePlanDetailService.queryEffectivePlanDetailListByOrgCode(originalOrgCode, destinationOrgCode, productCode,billDate);
		 
	}
	
	/** 客户优惠信息Service接口. */
	@Inject
    private IPreferentialService preferentialService;
    
	private IGradientDiscountService gradientDiscountService;
	
	public void setGradientDiscountService(
			IGradientDiscountService gradientDiscountService) {
		this.gradientDiscountService = gradientDiscountService;
	}
	@Inject
	private IBargainAppOrgService bargainAppOrgService;
	
	
    /**
     * Check preferentia time.
     *
     * @param queryBillCacilateDto the query bill cacilate dto
     */
    protected void checkPreferentiaTime (QueryBillCacilateDto queryBillCacilateDto){
    	if(StringUtil.isNotEmpty(queryBillCacilateDto.getCustomerCode())){
    		PreferentialInfoDto preferentialInfoDto = preferentialService.queryPriceVersionInfo(queryBillCacilateDto.getCustomerCode(), queryBillCacilateDto.getReceiveDate());
			if(null!=preferentialInfoDto){
				Date preferentiaTime =  preferentialInfoDto.getPriceVersionDate();
				if(null!=preferentiaTime){
					queryBillCacilateDto.setIsMonthlyDate(FossConstants.YES);
					if(FossConstants.YES.equals(queryBillCacilateDto.getIsCurrContract())){
						queryBillCacilateDto.setReceiveDate(queryBillCacilateDto.getReceiveDate());
					}else{
						queryBillCacilateDto.setReceiveDate(preferentiaTime);
					}
				}
			}
		}
    }
	
	/**
	 * <p>
	 * 
	 * Description:计算运费<br />
	 * 
	 * </p>.
	 *
	 * @param queryBillCacilateDto the query bill cacilate dto
	 * @return the list
	 * @throws BillCaculateServiceException the bill caculate service exception
	 * @author DP-Foss-YueHongJie
	 * @version 0.1 2012-10-25
	 * @parm  queryBillCacilateDto 计算运费dto
	 * List<ProductPriceDto>
	 */
	@Override  
	public List<ProductPriceDto> searchProductPriceList(QueryBillCacilateDto queryBillCacilateDto) throws BillCaculateServiceException{
		/**1.1 根据始发部门code 获取始发区域IDT_SRV_PRICE_PLAN
    	 * 1.2 根据达到部门code 获取到达区域ID
    	 * 2.1 根据始发区域ID,到达区域ID, 营业部收货日期, 是否接货查询计费规则和计价方式明细列表，以ProductPriceDto对象返回；
    	 */
	log.debug("FRT start calcuate>>"+new Date());
	// 数据检验
	PriceUtil.checkQueryBillCacilateDtoDate(queryBillCacilateDto);
	//业务时间
	Date discountReceiveDate = queryBillCacilateDto.getReceiveDate();
	//如果客户编码存在, 查询合同优惠信息,取出对应的价格版本日期作为当前查询价格信息。 
	checkPreferentiaTime(queryBillCacilateDto);
	// 如果当前货物为NULL,设置货物编码为通用状态
	if (StringUtil.isEmpty(queryBillCacilateDto.getGoodsCode())) {
		queryBillCacilateDto.setGoodsCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
	}
	String intProductCode = queryBillCacilateDto.getProductCode();
	//获得当前传入的产品、始发部门、到达部门、业务日期、币种、货物类别，是否接货
	String productCode = queryBillCacilateDto.getProductCode();
	String partialLineCode = null;
	if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode)){
		partialLineCode  = queryBillCacilateDto.getDestinationOrgCode();
		queryBillCacilateDto.setDestinationOrgCode(queryBillCacilateDto.getLastOrgCode());
	}
	
	String originalOrgCode = queryBillCacilateDto.getOriginalOrgCode();
	String destinationOrgCode = queryBillCacilateDto.getDestinationOrgCode();
	Date receiveDate = queryBillCacilateDto.getReceiveDate();
	String currencyCode = queryBillCacilateDto.getCurrencyCdoe();
	String flightShift = queryBillCacilateDto.getFlightShift();
	String goodsCode = queryBillCacilateDto.getGoodsCode();
	String combBillTypeCode = queryBillCacilateDto.getCombBillTypeCode();				//zxy 20140507 MANA-1253 新增
	String isReceiveGoods = queryBillCacilateDto.getIsReceiveGoods();
	//默认是否接货为否
	if (StringUtil.isEmpty(isReceiveGoods)) {
		isReceiveGoods = FossConstants.NO;
	}
	//重货
	BigDecimal weight = queryBillCacilateDto.getWeight();
	//轻货
	BigDecimal volume = queryBillCacilateDto.getVolume();
	//查询出发区域ID
	String originalId = queryBillCacilateDto.getDeptRegionId();
	if (StringUtil.isEmpty(originalId)) {
		if (StringUtils.equals(productCode, ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT)
				|| StringUtils.equals(productCode, ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
			originalId = regionAirService.findRegionOrgByDeptNo(originalOrgCode, discountReceiveDate, null,
					PricingConstants.PRICING_REGION);
		} else {
			originalId = regionService.findRegionOrgByDeptNo(originalOrgCode, discountReceiveDate, null,
					PricingConstants.PRICING_REGION);
		}
		if (StringUtil.isEmpty(originalId)) {
			return null;
		}
	}
	// 查询目的地区域ID
	String destinationId = queryBillCacilateDto.getArrvRegionId();
	if (StringUtil.isEmpty(destinationId) && StringUtil.isNotBlank(destinationOrgCode)) {
		if (StringUtils.equals(productCode, ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT)
				|| StringUtils.equals(productCode, ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
			destinationId = regionAirService.findRegionOrgByDeptNo(destinationOrgCode, discountReceiveDate, null,
					PricingConstants.PRICING_REGION);
		} else {
			destinationId = regionArriveService.findRegionOrgByDeptNo(destinationOrgCode, discountReceiveDate, null,
					PricingConstants.ARRIVE_REGION);
		}
		if (StringUtil.isEmpty(destinationId)) {
			return null;
		}
	}
	
		
	//如果当前是客户版本日期没有定位到区域。则以业务时间为准找区域
	if (StringUtil.isEmpty(originalId) && FossConstants.YES.equals(queryBillCacilateDto.getIsMonthlyDate())) {
		if (StringUtils.equals(productCode, ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT)
				|| StringUtils.equals(productCode, ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
			originalId = regionAirService.findRegionOrgByDeptNo(originalOrgCode, discountReceiveDate, null,
					PricingConstants.PRICING_REGION);
		} else {
			originalId = regionService.findRegionOrgByDeptNo(originalOrgCode, discountReceiveDate, null,
					PricingConstants.PRICING_REGION);
		}
		if (StringUtil.isEmpty(originalId)) {
			return null;
		}
	}
	
	//如果当前是客户版本日期没有定位到区域。则以业务时间为准找区域
	if (StringUtil.isEmpty(destinationId) &&  StringUtil.isNotBlank(destinationOrgCode) && FossConstants.YES.equals(queryBillCacilateDto.getIsMonthlyDate())) {
		if (StringUtils.equals(productCode, ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT)
				|| StringUtils.equals(productCode, ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
			destinationId = regionAirService.findRegionOrgByDeptNo(destinationOrgCode,discountReceiveDate , null,
					PricingConstants.PRICING_REGION);
		} else {
			destinationId = regionArriveService.findRegionOrgByDeptNo(destinationOrgCode,discountReceiveDate , null,
					PricingConstants.ARRIVE_REGION);
		}
		if (StringUtil.isEmpty(destinationId)) {
			return null;
		}
	}
	
		
	//运费查询Bean
	QueryProductPriceDto queryProductPriceDto = new QueryProductPriceDto();
	if (null == currencyCode) {
		currencyCode = FossConstants.CURRENCY_CODE_RMB;
	}
	ProductEntity productEntity = productService.getProductByCache(productCode, discountReceiveDate);
	if (productEntity == null) {
		return null;
	}
	//根据客户端传入的三级产品得到二级产品
	productCode = productEntity.getParentCode();
	GoodsTypeEntity goodsTypeEntity = goodsTypeService.getGoodsTypeByCache(goodsCode, receiveDate);
	queryBillCacilateDto.setGoodsTypeName(goodsTypeEntity.getName());
	queryProductPriceDto.setProductCode(productCode);
	//设置货币、始发区域、到达区域、航班类别、货物、是否接货、计费规则类型、状态
	queryProductPriceDto.setCurrencyCode(currencyCode);
	queryProductPriceDto.setOriginalOrgId(originalId);
	queryProductPriceDto.setDestinationId(destinationId);
	queryProductPriceDto.setFlightShift(flightShift);
	queryProductPriceDto.setGoodsTypeCode(goodsCode);
	queryProductPriceDto.setReceiveDate(receiveDate);
	queryProductPriceDto.setIsReceiveGoods(isReceiveGoods);
	queryProductPriceDto.setType(PricingConstants.VALUATION_TYPE_PRICING);//价格定义 
	queryProductPriceDto.setActive(FossConstants.ACTIVE); 
	//zxy 20140522 DEFECT-2949 MANA-1253 start 新增
	if (StringUtils.equals(queryProductPriceDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT)
			|| StringUtils.equals(queryProductPriceDto.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) 
		queryProductPriceDto.setCombBillTypeCode(combBillTypeCode);	//zxy 20140507 MANA-1253 新增
	//zxy 20140522 DEFECT-2949 MANA-1253 end 新增
	//计费条目
	PriceEntity priceEntity = priceEntryService.getPriceEntryByCache(PriceEntityConstants.PRICING_CODE_FRT, discountReceiveDate);
	if (priceEntity == null) {
		return null;
	}
//	--------------------
	List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
	/**
	 * 客户价格方案
	 * dp-foss-dongjialing
	 * 225131
	 */
	if ((StringUtil.equalsIgnoreCase(productCode,
			ProductEntityConstants.PRICING_PRODUCT_C2_C20002) || StringUtil
			.equalsIgnoreCase(productCode,
					ProductEntityConstants.PRICING_PRODUCT_C2_C20001))
			) {
		//设置客户编码
		queryProductPriceDto.setCustomerCode(queryBillCacilateDto.getCustomerCode());
		queryProductPriceDto.setReceiveDate(discountReceiveDate);
		List<ResultProductPriceDto> resultCusList= priceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
		if(StringUtil.isBlank(queryBillCacilateDto.getCustomerCode())) {
			resultCusList = null;
		}
		if(CollectionUtils.isNotEmpty(resultCusList)) {
			caculateresultList = PriceUtil.calculateCostServices(weight, volume, resultCusList, receiveDate,
					productEntity, goodsTypeEntity, priceEntity);
			if(CollectionUtils.isNotEmpty(caculateresultList)) {
				return caculateresultList;
			} else {
			queryProductPriceDto.setCustomerCode(null);
			queryProductPriceDto.setReceiveDate(receiveDate);
			}
		} else {
			queryProductPriceDto.setCustomerCode(null);
			queryProductPriceDto.setReceiveDate(receiveDate);
		}
	}
//	OuterPriceEntity outerPriceEntity=null;
	//liyongfei 如果二级产品是精准或者普货，并且开单时间在定价优化上线时间之后，则采用新规则计算运费（分段计算）
	if ((StringUtil.equalsIgnoreCase(productCode,
			ProductEntityConstants.PRICING_PRODUCT_C2_C20002) || StringUtil
			.equalsIgnoreCase(productCode,
					ProductEntityConstants.PRICING_PRODUCT_C2_C20001))) {
		List<NewResultProductPriceDto> resultList = priceValuationService.queryPriceValuationByCodition(queryProductPriceDto);
		//如果根据新规则计算运费找不到运费则根据原有规则计算(POP-375 上海-廊坊配有汽运价格方案，公布价也能查询到价格，但是开单调用不到运费，更改单也调用不到)
		if(resultList==null || resultList.size()<=0){
			// 根据三级产品查询计算费用
			List<ResultProductPriceDto> resultListold= priceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
			if (StringUtil.equalsIgnoreCase(productCode, ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
				//zxy 20140520 DEFECT-2913 MANA-1253 start 新增:如果未配置单独开单的方案，则取合大票
				if((resultListold == null || resultListold.size() <= 0) && ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(combBillTypeCode)){
					queryProductPriceDto.setCombBillTypeCode(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP);
					resultListold = priceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
					queryProductPriceDto.setCombBillTypeCode(combBillTypeCode);	//还原参数
				}
				//zxy 20140520 DEFECT-2913 MANA-1253 end 新增:如果未配置单独开单的方案，则取合大票
				// 计算空运的价格
				caculateresultList = PriceUtil.calculateAirCostServices(weight, volume, resultListold, receiveDate,
						productEntity, goodsTypeEntity, priceEntity);
			} else {
				// 其他运输方式价格
				caculateresultList = PriceUtil.calculateCostServices(weight, volume, resultListold, receiveDate,
						productEntity, goodsTypeEntity, priceEntity);
			}
		}else{
			caculateresultList = PriceUtil.newCalculateCostServices(weight, volume, resultList, receiveDate,
					productEntity, goodsTypeEntity, priceEntity);
			//如果根据新分段逻辑找到的数据条数大于1则取第一条
			if(caculateresultList.size()>1){
				ProductPriceDto productPriceDto=caculateresultList.get(0);
				List<ProductPriceDto> productPriceDtoNew = new ArrayList<ProductPriceDto>();
				productPriceDtoNew.add(productPriceDto);
				caculateresultList=productPriceDtoNew;
			}
		}
	}else{
		// 根据三级产品查询计算费用
		List<ResultProductPriceDto> resultListold = priceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
		if (StringUtil.equalsIgnoreCase(productCode, ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
			//zxy 20140520 DEFECT-2913 MANA-1253 start 新增:如果未配置单独开单的方案，则取合大票
			if((resultListold == null || resultListold.size() <= 0) && ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(combBillTypeCode)){
				queryProductPriceDto.setCombBillTypeCode(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP);
				resultListold = priceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
				queryProductPriceDto.setCombBillTypeCode(combBillTypeCode);	//还原参数
			}
			//zxy 20140520 DEFECT-2913 MANA-1253 end 新增:如果未配置单独开单的方案，则取合大票
			// 计算空运的价格
			caculateresultList = PriceUtil.calculateAirCostServices(weight, volume, resultListold, receiveDate,
					productEntity, goodsTypeEntity, priceEntity);
		} else {
			// 其他运输方式价格
			caculateresultList = PriceUtil.calculateCostServices(weight, volume, resultListold, receiveDate,
					productEntity, goodsTypeEntity, priceEntity);
		}
	}
	
	//如果当前是算月结客户 
	if(StringUtils.equals(FossConstants.YES,queryBillCacilateDto.getIsMonthlyDate())){
		// 根据三级产品查询计算费用
		queryProductPriceDto.setReceiveDate(discountReceiveDate);
		//liyongfei 如果二级产品是精准或者普货，则采用新规则计算运费（分段计算）
		if ((StringUtil.equalsIgnoreCase(productCode,
				ProductEntityConstants.PRICING_PRODUCT_C2_C20002)
				|| StringUtil.equalsIgnoreCase(productCode,
						ProductEntityConstants.PRICING_PRODUCT_C2_C20001))){
			if (CollectionUtils.isEmpty(caculateresultList)) {
				List<NewResultProductPriceDto> resultList = priceValuationService.queryPriceValuationByCodition(queryProductPriceDto);
				//如果根据新规则计算运费找不到运费则根据原有规则计算(POP-375 上海-廊坊配有汽运价格方案，公布价也能查询到价格，但是开单调用不到运费，更改单也调用不到)
				if(resultList==null || resultList.size()<=0){
					List<ResultProductPriceDto> monthlyResultList = priceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
					if (CollectionUtils.isEmpty(caculateresultList)) {
						if (StringUtil.equalsIgnoreCase(productCode, ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
							//zxy 20140520 DEFECT-2913 MANA-1253 start 新增:如果未配置单独开单的方案，则取合大票
							if((monthlyResultList == null || monthlyResultList.size() <= 0) && ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(combBillTypeCode)){
								queryProductPriceDto.setCombBillTypeCode(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP);
								monthlyResultList = priceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
								queryProductPriceDto.setCombBillTypeCode(combBillTypeCode);	//还原参数
							}
							//zxy 20140520 DEFECT-2913 MANA-1253 end 新增:如果未配置单独开单的方案，则取合大票
							// 计算空运的价格
							caculateresultList = PriceUtil.calculateAirCostServices(weight, volume, monthlyResultList, discountReceiveDate,
									productEntity, goodsTypeEntity, priceEntity);
						} else {
							// 其他运输方式价格
							caculateresultList = PriceUtil.calculateCostServices(weight, volume, monthlyResultList, discountReceiveDate,
									productEntity, goodsTypeEntity, priceEntity);
						}
					}
				
				}else{
				caculateresultList = PriceUtil.newCalculateCostServices(weight, volume, resultList, receiveDate,
					productEntity, goodsTypeEntity, priceEntity);
				//如果根据新分段逻辑找到的数据条数大于1则取第一条
				 if(caculateresultList.size()>1){
					 ProductPriceDto productPriceDto=caculateresultList.get(0);
					 List<ProductPriceDto> productPriceDtoNew = new ArrayList<ProductPriceDto>();
					 productPriceDtoNew.add(productPriceDto);
				 	 caculateresultList=productPriceDtoNew;
				 }
				}
			}/**
			*DP—FOSS—月结客户开单优化
			*
			*比较月结客户的合同版本价格和当前版本价格的大小，舍大取小
			*
			*@author foss-206860
			*/
			else{
				//该需求仅针对精准卡航/城运、精准汽运（长途/短途）四种运输方式--三级产品
				if(WaybillConstants.LRF_FLIGHT.equals(intProductCode)
						|| WaybillConstants.SRF_FLIGHT.equals(intProductCode)
						|| WaybillConstants.TRUCK_FLIGHT.equals(intProductCode)
						|| WaybillConstants.FSF_FLIGHT.equals(intProductCode)){
						List<ProductPriceDto> caculateresultListNewToMonthly = new ArrayList<ProductPriceDto>();
						//获取合同客户的当前版本价格
						List<NewResultProductPriceDto> resultList = priceValuationService.queryPriceValuationByCodition(queryProductPriceDto);
						//如果根据新规则计算运费找不到运费则根据原有规则计算(POP-375 上海-廊坊配有汽运价格方案，公布价也能查询到价格，但是开单调用不到运费，更改单也调用不到)
						if(resultList==null || resultList.size()<=0){
							// 根据三级产品查询计算费用
							List<ResultProductPriceDto> resultListold= priceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
							caculateresultListNewToMonthly = PriceUtil.calculateCostServices(weight, volume, resultListold, receiveDate,
									productEntity, goodsTypeEntity, priceEntity);
						}else{
							caculateresultListNewToMonthly = PriceUtil.newCalculateCostServices(weight, volume, resultList, receiveDate,
									productEntity, goodsTypeEntity, priceEntity);
							//如果根据新分段逻辑找到的数据条数大于1则取第一条
							if(caculateresultListNewToMonthly.size()>1){
								ProductPriceDto productPriceDto=caculateresultListNewToMonthly.get(0);
								List<ProductPriceDto> productPriceDtoNew = new ArrayList<ProductPriceDto>();
								productPriceDtoNew.add(productPriceDto);
								caculateresultListNewToMonthly=productPriceDtoNew;
							}
						}
						if(CollectionUtils.isNotEmpty(caculateresultListNewToMonthly)){
							//针对精准卡航/城运、精准汽运（长途/短途）四中运输方式,同时存在历史价格版本与当前价格版本时，获取纯运费价格较低者。
							//合同客户价格版本
							ProductPriceDto proDtoA = null;
							//合同客户当前价格版本
							ProductPriceDto proDtoB = null;
							for (int i = 0; i < caculateresultList.size(); i++) {
								//获取费用类型为运费的实体DTO
								if(PriceEntityConstants.PRICING_CODE_FRT.equals(caculateresultList.get(i).getPriceEntityCode())){
									proDtoA = caculateresultList.get(i);
									break;
								}
							}
							for (int i = 0; i < caculateresultListNewToMonthly.size(); i++) {
								//获取费用类型为运费的实体DTO
								if(PriceEntityConstants.PRICING_CODE_FRT.equals(caculateresultListNewToMonthly.get(i).getPriceEntityCode())){
									proDtoB = caculateresultListNewToMonthly.get(i);
									break;
								}
							}
							if(proDtoA != null && proDtoB != null){
								//当合同价格版本大于当前价格版本时，取当前价格版本，反之，取合同价格版本
								caculateresultList = proDtoA.getCaculateFee().doubleValue() > proDtoB.getCaculateFee().doubleValue() ? caculateresultListNewToMonthly
										: caculateresultList;
							}else{
								if(proDtoA == null && proDtoB == null){
									return null;
								}else{
									if(proDtoB != null){
										caculateresultList = caculateresultListNewToMonthly; 
									}
								}
							}
						}
					}
				}
		}else{
			List<ResultProductPriceDto> monthlyResultList = priceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
			if (CollectionUtils.isEmpty(caculateresultList)) {
				if (StringUtil.equalsIgnoreCase(productCode, ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
					//zxy 20140520 DEFECT-2913 MANA-1253 start 新增:如果未配置单独开单的方案，则取合大票
					if((monthlyResultList == null || monthlyResultList.size() <= 0) && ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(combBillTypeCode)){
						queryProductPriceDto.setCombBillTypeCode(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP);
						monthlyResultList = priceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
						queryProductPriceDto.setCombBillTypeCode(combBillTypeCode);	//还原参数
					}
					//zxy 20140520 DEFECT-2913 MANA-1253 end 新增:如果未配置单独开单的方案，则取合大票
					// 计算空运的价格
					caculateresultList = PriceUtil.calculateAirCostServices(weight, volume, monthlyResultList, discountReceiveDate,
							productEntity, goodsTypeEntity, priceEntity);
				} else {
					// 其他运输方式价格
					caculateresultList = PriceUtil.calculateCostServices(weight, volume, monthlyResultList, discountReceiveDate,
							productEntity, goodsTypeEntity, priceEntity);
				}
			}
		}
	}
	
	//只有在没有找到价格的情况下才走这个方法   MANA-1242：偏线更改读取价格版本规则更新
	//@author 张兴旺  2014-5-13 08:59:57
	if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(queryBillCacilateDto.getProductCode())
								&& CollectionUtils.isEmpty(caculateresultList)){
		//重新赋予新值为当前时间以便得到新的计算方式
		queryBillCacilateDto.setReceiveDate(new Date());
		//时间重新赋值
		discountReceiveDate = queryBillCacilateDto.getReceiveDate();
		//重新计算价格
		caculateresultList = reCalateTranferForOuterprice(queryBillCacilateDto);
	}
	
	if (CollectionUtils.isEmpty(caculateresultList)) {
		return null;
	}
	
	//是否经济自提件赋值最低一票 
	assignEconomyMentMinFee(queryBillCacilateDto,caculateresultList,discountReceiveDate);
	//伙伴开单直接返回纯运费
	if(FossConstants.YES.equals(queryBillCacilateDto.getPartnerBillingLogo())&&!"ALIBABACXT".equals(queryBillCacilateDto.getChannelCode())){
		calateOuterPrice(partialLineCode,intProductCode,queryBillCacilateDto,discountReceiveDate,caculateresultList);
		return caculateresultList;
	}
	//如果到达部门不为空、则在价格计算完成后的基础上接着计算折扣、否则不计费算折扣、直接返回价格
	
/**
	 * 内部发货计算运费逻辑
	 */
	if((WaybillConstants.DELIVERY_PAY.equals(queryBillCacilateDto.getInternalDeliveryType())
			|| WaybillConstants.RECIVE_PAY.equals(queryBillCacilateDto.getInternalDeliveryType())
			) && StringUtil.isNotBlank(queryBillCacilateDto.getEmployeeNo())) {
		if(StringUtil.isBlank(destinationOrgCode)) {
			calateOuterPrice(partialLineCode,intProductCode,queryBillCacilateDto,discountReceiveDate,caculateresultList);
		}
		InempDiscountPlanEntity discountEntity = new InempDiscountPlanEntity();
		discountEntity.setBillTime(queryBillCacilateDto.getBillTime());
		discountEntity.setActive(FossConstants.ACTIVE);
		List<InempDiscountPlanEntity> discountEntityList = inempDiscountPlanService.queryInempDiscountPanByCondition(discountEntity);
		if(CollectionUtils.isNotEmpty(discountEntityList)) {
			discountEntity = discountEntityList.get(0);
			if(discountEntity.getHighstPreferentialLimit() != null &&
					discountEntity.getHighstPreferentialLimit().compareTo(BigDecimal.ZERO)>0) {
				BigDecimal amount = queryBillCacilateDto.getAmount();
				if(amount == null) {
					amount = BigDecimal.ZERO;
				}
				/**
				 * 剩余额度大于零才进行打折
				 */
				BigDecimal	differenceValue = discountEntity.getHighstPreferentialLimit().subtract(amount.divide(BigDecimal.valueOf(NUMBER_100)));
				if(differenceValue.compareTo(BigDecimal.ZERO)>0) {
					caculateresultList=doLTTInempFRTDiscount(queryBillCacilateDto,
							caculateresultList,discountEntity,amount);
				}
				
			}
			
		}
		return caculateresultList;
	} else {		
	if (StringUtil.isNotBlank(destinationOrgCode)) {
		/**
		 * 计算折扣
		 */
		List<ProductPriceDto> list = null;
		//查询当前客户是否为合同客户
		GradientDiscountItemDto gradientDiscountInfo = queryGradientDiscountInfo(queryBillCacilateDto.getCustomerCode(), discountReceiveDate,queryBillCacilateDto.getTotalAmount());
		//根据合同客户中的CRM_ID查询合同的绑定部门
		List<BargainAppOrgEntity> bargainAppOrgEntities = null;
		boolean isDiscount = false;
		if(gradientDiscountInfo!=null){
			 List<String> unifiedCodeList=new ArrayList<String>();
			 String unCode=orgAdministrativeInfoService.queryUnifiedCodeByCode(queryBillCacilateDto.getReceiveOrgCode());
			 if(unCode!=null){
				 unifiedCodeList.add(unCode);
			 }
			 bargainAppOrgEntities = bargainAppOrgService.queryAppOrgByBargainCrmId(gradientDiscountInfo.getProgramCrmId(),unifiedCodeList);


		if (CollectionUtils.isNotEmpty(bargainAppOrgEntities)) {
			for (int i = 0; i < bargainAppOrgEntities.size(); i++) {
				BargainAppOrgEntity bargainAppOrgEntity = bargainAppOrgEntities
						.get(i);
				String unifiedCode = bargainAppOrgEntity.getUnifiedCode();
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(unifiedCode);
				if (orgAdministrativeInfoEntity == null) {
					continue;
				}
				String orgCode = orgAdministrativeInfoEntity.getCode();
				if (StringUtils.equals(queryBillCacilateDto.getReceiveOrgCode(), orgCode)) {
					isDiscount = true;
				}
			}
		}
		if(queryBillCacilateDto.getOriginalRate() != null && queryBillCacilateDto.getOriginalRate().compareTo(BigDecimal.ZERO)>0) {
			gradientDiscountInfo.setRate(queryBillCacilateDto.getOriginalRate().doubleValue());
		}
		}

		if(isDiscount && gradientDiscountInfo.getRate()>0 &&
				!ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(queryBillCacilateDto.getProductCode())) {
			//零担梯度折扣
			list = doFRTGradientDiscount(caculateresultList, queryBillCacilateDto,gradientDiscountInfo);
		} else {
	       //新的折扣计算规则
			list = doFRTDiscount(originalOrgCode, destinationOrgCode, discountReceiveDate, weight,
					volume, originalId, destinationId, caculateresultList, queryBillCacilateDto);
		}
		calateOuterPrice(partialLineCode,intProductCode,queryBillCacilateDto,discountReceiveDate,caculateresultList);
		/**
		     * 处理运费CRM营销活动处理 
	     */
			list=crmActiveDiscountProcessFRT(queryBillCacilateDto,list);
		log.debug("FRT end calcuate>>"+new Date());
		return list;
	} else {
		calateOuterPrice(partialLineCode,intProductCode,queryBillCacilateDto,discountReceiveDate,caculateresultList);
		/**
	     * 处理运费CRM营销活动处理
	     */
		caculateresultList=crmActiveDiscountProcessFRT(queryBillCacilateDto,caculateresultList);
		return caculateresultList;
	}
	}
	/**  BillCaculateService 是开单计算价格、增值服务、折扣、优惠活动的实现类
	 *  计算运费分别为：FRT
	 *  
	 *  
	 *  汽运  - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 0
	 *  
	 *  空运 - t_srv_pice_plan价格表  字段 TRANSPORT_FLAG = 1
	 *  
	 *  计算增值服务：  VAS
	 *  
	 *  保费 - BF
	 *  
	 *  代收货款 - HK
	 *  
	 *  送货费 - SH
	 *  
	 *  接货费 - JH
	 *  
	 *  签收回单 -QS
	 *  
	 *  保管费 - CCF
	 *  
	 *  综合信息费 -ZHXX
	 *  
	 *  燃油附加费 - RYFJ
	 *  
	 *  中转费 - ZZ
	 *  
	 *  接货差额补差 -JHCEBC
	 *  
	 *  电子优惠券 - DZYHQ
	 *  
	 *  其他费用 -QT
	 *  
	 *  包装费 -BZ
	 *  
	 *  送货上楼费 -SHSL
	 *  
	 *  超远派送费 -CY
	 *  
	 *  送货进仓费 -SHJCF
	 *  
	 *  更改费 -GGF
	 *  
	 *  以上服务相关代码进行不同维度计算。
	 *  
	 *  可支绑定如产品、区域 等等进行价格、折扣、优惠计算
	 */
	
}

	/**
	 * 重新计算偏线货物在自有网点中转价格数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-5-6 16:32:19
	 * @param queryBillCacilateDto
	 * @param caculateresultList 
	 */
	protected List<ProductPriceDto> reCalateTranferForOuterprice(QueryBillCacilateDto queryBillCacilateDto) {
		// 如果当前货物为NULL,设置货物编码为通用状态
		if (StringUtil.isEmpty(queryBillCacilateDto.getGoodsCode())) {
			queryBillCacilateDto.setGoodsCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
		}
		//业务时间
		Date discountReceiveDate = queryBillCacilateDto.getReceiveDate();
		//获得当前传入的产品、始发部门、到达部门、业务日期、币种、货物类别，是否接货
		String productCode = queryBillCacilateDto.getProductCode();
		String originalOrgCode = queryBillCacilateDto.getOriginalOrgCode();
		String destinationOrgCode = queryBillCacilateDto.getDestinationOrgCode();
		Date receiveDate = queryBillCacilateDto.getReceiveDate();
		String currencyCode = queryBillCacilateDto.getCurrencyCdoe();
		String flightShift = queryBillCacilateDto.getFlightShift();
		String goodsCode = queryBillCacilateDto.getGoodsCode();
		String isReceiveGoods = queryBillCacilateDto.getIsReceiveGoods();
		//默认是否接货为否
		if (StringUtil.isEmpty(isReceiveGoods)) {
			isReceiveGoods = FossConstants.NO;
		}
		//重货
		BigDecimal weight = queryBillCacilateDto.getWeight();
		//轻货
		BigDecimal volume = queryBillCacilateDto.getVolume();
		//查询出发区域ID
		String originalId = regionService.findRegionOrgByDeptNo(originalOrgCode, queryBillCacilateDto.getReceiveDate(), null,
				PricingConstants.PRICING_REGION);
		//如果出发区域ID为空，不需要再查
		if(StringUtils.isEmpty(originalId)){
			return null;
		}
		// 查询目的地区域ID
		String destinationId = regionArriveService.findRegionOrgByDeptNo(destinationOrgCode, queryBillCacilateDto.getReceiveDate(), null,
				PricingConstants.ARRIVE_REGION);

		//如果出发区域ID为空，不需要再查
		if(StringUtils.isEmpty(destinationId)){
			return null;
		}	
			
		//运费查询Bean
		QueryProductPriceDto queryProductPriceDto = new QueryProductPriceDto();
		if (null == currencyCode) {
			currencyCode = FossConstants.CURRENCY_CODE_RMB;
		}
		//查询产品类型
		ProductEntity productEntity = productService.getProductByCache(productCode, discountReceiveDate);
		if (productEntity == null) {
			return null;
		}
		//根据客户端传入的三级产品得到二级产品
		productCode = productEntity.getParentCode();
		GoodsTypeEntity goodsTypeEntity = goodsTypeService.getGoodsTypeByCache(goodsCode, receiveDate);
		queryProductPriceDto.setProductCode(productCode);
		//设置货币、始发区域、到达区域、航班类别、货物、是否接货、计费规则类型、状态
		queryProductPriceDto.setCurrencyCode(currencyCode);
		queryProductPriceDto.setOriginalOrgId(originalId);
		queryProductPriceDto.setDestinationId(destinationId);
		queryProductPriceDto.setFlightShift(flightShift);
		queryProductPriceDto.setGoodsTypeCode(goodsCode);
		queryProductPriceDto.setReceiveDate(receiveDate);
		queryProductPriceDto.setIsReceiveGoods(isReceiveGoods);
		queryProductPriceDto.setType(PricingConstants.VALUATION_TYPE_PRICING);//价格定义 
		queryProductPriceDto.setActive(FossConstants.ACTIVE); 
		//计费条目
		PriceEntity priceEntity = priceEntryService.getPriceEntryByCache(PriceEntityConstants.PRICING_CODE_FRT, discountReceiveDate);
		if (priceEntity == null) {
			return null;
		}
		// 根据三级产品查询计算费用
		List<ResultProductPriceDto> resultList = priceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
		// 其他运输方式价格
		return PriceUtil.calculateCostServices(weight, volume, resultList, receiveDate,
				productEntity, goodsTypeEntity, priceEntity);				
	}

	protected void calateOuterPrice(String partialLineCode,String productCode,QueryBillCacilateDto queryBillCacilateDto,Date billTime, List<ProductPriceDto> caculateresultList){
		//如果当前是偏线产品
		if(StringUtils.equals(productCode, ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE)){
			ConfigurationParamsEntity entity = configurationParamsService.queryConfigurationParamsOneByCode(PricingConstants.OUTER_PRICE_DEFAULT_TIME);
			if(StringUtils.isNotEmpty(entity.getConfValue())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date outPriceDate = null;
				try {
					outPriceDate = sdf.parse(entity.getConfValue());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//如果在指定日期之后才按照新的偏线计算
				if(outPriceDate != null && billTime.after(outPriceDate)){
					QueryOuterPriceCaccilateDto queryOuterPriceCaccilateDto = new QueryOuterPriceCaccilateDto();
					queryOuterPriceCaccilateDto.setCurrencyCdoe(queryBillCacilateDto.getCurrencyCdoe());
					queryOuterPriceCaccilateDto.setOutFieldCode(queryBillCacilateDto.getLastOrgCode());
					queryOuterPriceCaccilateDto.setPartialLineCode(partialLineCode);
					queryOuterPriceCaccilateDto.setReceiveDate(billTime);
					queryOuterPriceCaccilateDto.setVolume(queryBillCacilateDto.getVolume());
					queryOuterPriceCaccilateDto.setWeight(queryBillCacilateDto.getWeight());
					queryOuterPriceCaccilateDto.setProductCode(productCode);
					ResultOuterPriceCaccilateDto result = null;
					//根据开单时间去查询偏线价格，如果查询不到，则使用当前时间去查询，如果当前时间查询不到，则需要抛出异常
					try{
						result = outerPriceCaculateService.calulateOuterPrice(queryOuterPriceCaccilateDto);
					}catch (OuterPriceCaculateServiceException e) {
						queryOuterPriceCaccilateDto.setReceiveDate(new Date());
						//再查询不到，需要抛出异常，否则偏线的价格没有添加，这是很严重的事情
						result = outerPriceCaculateService.calulateOuterPrice(queryOuterPriceCaccilateDto);
					}
					if(null!=result){
						if(CollectionUtils.isNotEmpty(caculateresultList)){
							List<ProductPriceDto> guiResultBillCalculates = null;
							for (ProductPriceDto billCalculate : caculateresultList) {
								guiResultBillCalculates = new ArrayList<ProductPriceDto>();
								//公布价费 + 偏线费
								billCalculate.setCaculateFee(billCalculate.getCaculateFee().add(result.getCalateFee()));
								//公布价费率  + 偏线费率
								billCalculate.setActualFeeRate(billCalculate.getActualFeeRate().add(result.getRateFee()));
								//设置偏线费率信息
								billCalculate.setResultOuterPriceCaccilateDto(result);
								//偏线费 by:352676
								billCalculate.setPartialTransportFee(result.getCalateFee());
								//存储新的数据
								guiResultBillCalculates.add(billCalculate);
							}
							//替换新的数据
							caculateresultList = guiResultBillCalculates;
						}
					}
				}
			}
		}
	}
	 
	/**
	 * 赋值经济自提件最低一票.
	 *
	 * @param queryBillCacilateDto the query bill cacilate dto
	 * @param caculateresultList the caculateresult list
	 * @param discountReceiveDate the discount receive date
	 */
	protected void  assignEconomyMentMinFee(QueryBillCacilateDto queryBillCacilateDto,List<ProductPriceDto> caculateresultList,Date discountReceiveDate){
		if(FossConstants.YES.equals(queryBillCacilateDto.getEconomySince())){
			MinFeePlanEntity minFeePlanEntity = minFeePlanService.getMinFeePlanByChannelCodeAndProductCodeAndSpecifiedDate(queryBillCacilateDto.getChannelCode(), queryBillCacilateDto.getProductCode(), discountReceiveDate);
			if(null != minFeePlanEntity){
				if(null!=minFeePlanEntity.getMinFee()){
					log.info("经济自提件");
					for (ProductPriceDto productPriceDto : caculateresultList) {
						//计算重货
						BigDecimal weight = BigDecimal.valueOf(productPriceDto.getHeavyFeeRate().doubleValue()).multiply(queryBillCacilateDto.getWeight());
						//计算轻货
						BigDecimal volume = BigDecimal.valueOf(productPriceDto.getLightFeeRate().doubleValue()).multiply(queryBillCacilateDto.getVolume());
						//如果重货价格大于轻货则以轻为主
						if(weight.compareTo(volume)==1){
							productPriceDto.setActualFeeRate(productPriceDto.getHeavyFeeRate());
							productPriceDto.setCaculateFee(weight);
						}else{
							productPriceDto.setActualFeeRate(productPriceDto.getLightFeeRate());
							productPriceDto.setCaculateFee(volume);
						} 
						//设置经济自提件最低一票
						productPriceDto.setMinFee(BigDecimal.valueOf(minFeePlanEntity.getMinFee()));
						//如果计算的经济自提件比经济自提件最低一票还低则以经济自提件最低为准
						if(productPriceDto.getCaculateFee().compareTo(BigDecimal.valueOf(minFeePlanEntity.getMinFee()))==-1){
							productPriceDto.setCaculateFee(BigDecimal.valueOf(minFeePlanEntity.getMinFee()));
						} 
					}
				}
			}
		}
	}
	
	/**
	 * Sets the preferential service.
	 *
	 * @param preferentialService the new preferential service
	 */
	public void setPreferentialService(IPreferentialService preferentialService) {
		this.preferentialService = preferentialService;
	}
	
	public void setBargainAppOrgService(
			IBargainAppOrgService bargainAppOrgService) {
		this.bargainAppOrgService = bargainAppOrgService;
	}
	/**
	 * 运费折扣计算.
	 *
	 * @param originalOrgCode the original org code
	 * @param destinationOrgCode the destination org code
	 * @param receiveDate the receive date
	 * @param weight the weight
	 * @param volume the volume
	 * @param originalId the original id
	 * @param destinationId the destination id
	 * @param caculateresultList the caculateresult list
	 * @param queryBillCacilateDto the query bill cacilate dto
	 * @return the list
	 * @author zhangdongping
	 * @date 2012-12-25 下午3:32:38
	 * @see
	 * 
	 * 计算规则：
	 * 
	 * 折扣根据价格计算的方式（体积、重量）来选择适用哪种方式的折扣。
	 * 
	 * 例如，如果价格以体积进行计算，则折扣也需要以体积折扣计算。同时，再选择直接费用折扣 计算（如果有的情况下）。
	 * 
	 * 比较二者打折后，选取优惠幅度更高的作为最终折扣。
	 * 
	 * 注意，在普通客户合同、渠道、产品折扣的情况下，最终计算出的价格不能低于价格最低一票。
	 */
	@Override
	public List<ProductPriceDto> doFRTDiscount(String originalOrgCode,
		String destinationOrgCode, Date receiveDate, BigDecimal weight,
		BigDecimal volume, String originalId, String destinationId,
		List<ProductPriceDto> caculateresultList,QueryBillCacilateDto queryBillCacilateDto) {
		//设置一个新的对象，主要用于，若是普元接口报异常，则使用老的计算运费折扣信息
		List<ProductPriceDto> oldCaculateresultList = new ArrayList<ProductPriceDto>();
	    List<ProductPriceDto> discountResultList = new ArrayList<ProductPriceDto>();
	    //拷贝list，用于若调用规则引擎抛异常，则直接调用老的折扣方法
	    if(CollectionUtils.isNotEmpty(caculateresultList)) {
	    	for(ProductPriceDto productPriceDto : caculateresultList){
	    		//拷贝属性，用于调用普元出错，使用老的折扣方法
	    		ProductPriceDto oldProductPriceDto =  new ProductPriceDto();
	    		BeanCopier copy = BeanCopier.create(productPriceDto.getClass(), oldProductPriceDto.getClass(), false);
	    		copy.copy(productPriceDto, oldProductPriceDto, null);
	    		oldCaculateresultList.add(oldProductPriceDto);
	    	}
	    	
	    }
	    boolean exceptionFlag = false;//规则引擎异常标示
	    boolean oldFlag = false ;
	    //获取折扣优先级
	    List<DiscountPriorityEntity> discountPriorityEntities = discountPriorityService.getDiscountPriorityByCache();
	    if(CollectionUtils.isNotEmpty(caculateresultList)) {
	    	boolean flag = false;
	    	for (int i = 0; i < caculateresultList.size(); i++) {
	    		flag = false;
	    		ProductPriceDto productPriceDto = caculateresultList.get(i);
	    		if(productPriceDto == null){
	    			continue;
	    		}
	    		//是否按照优先级处理
	    		if(CollectionUtils.isNotEmpty(discountPriorityEntities)) {
	    			DiscountParmDto discountParmDto = new DiscountParmDto();
	    			String departDept = null;
				    String destDept = null;
				    String startCityName = null;
				    String arrvCityName =null;
	    		    
	    		    //出发部门
	    		    OrgAdministrativeInfoEntity deptOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(originalOrgCode);
	    		    //到达部门
	    		    OrgAdministrativeInfoEntity arrvOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(destinationOrgCode);
	    		    //匹配出发、到达折扣的城市信息
	    		    if(deptOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(deptOrgAdministrativeInfoEntity.getCityCode())) {
	    		    	 discountParmDto.setDeptCityCode(deptOrgAdministrativeInfoEntity.getCityCode());
	    		    	 startCityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(deptOrgAdministrativeInfoEntity.getCityCode());
	    		    	 departDept = deptOrgAdministrativeInfoEntity.getName();
	    		    }
	    		    if(arrvOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(arrvOrgAdministrativeInfoEntity.getCityCode())) {
	    		    	discountParmDto.setArrvCityCode(arrvOrgAdministrativeInfoEntity.getCityCode());
	    		    	arrvCityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(arrvOrgAdministrativeInfoEntity.getCityCode());
	    		    	destDept = arrvOrgAdministrativeInfoEntity.getName();
	    		    }
	    		    //依次设置折扣信息的出发区域、到达区域、始发部门、到达部门、计价条目信息、
	    		    //产品、货物、业务日期、重货、轻货、原始费用、计费类别
	    		    discountParmDto.setCriteriaDetailId(productPriceDto.getId());
	    		    discountParmDto.setDeptRegionId(originalId);
	    		   
	    		    discountParmDto.setArrvRegionId(destinationId);
	    		   
	    		    discountParmDto.setOriginalOrgCode(originalOrgCode);
	    		    
	    		    discountParmDto.setDestinationOrgCode(destinationOrgCode);
	    		    
	    		    discountParmDto.setPricingEntryCode(productPriceDto.getPriceEntityCode());
	    		    discountParmDto.setPricingEntryName(productPriceDto.getPriceEntityName());
	    		    discountParmDto.setProductCode(productPriceDto.getProductCode());
	    		   
	    		    discountParmDto.setGoodsTypeCode(productPriceDto.getGoodsTypeCode());
	    		    
	    		    discountParmDto.setReceiveDate(receiveDate);
	    		   
	    		    discountParmDto.setWeight(weight);
	    		    discountParmDto.setVolume(volume);
	    		    //计算后的价格
	    		    discountParmDto.setOriginnalCost(productPriceDto.getCaculateFee());
	    		    discountParmDto.setCustomCode(queryBillCacilateDto.getCustomerCode());
					discountParmDto.setIndustryCode(queryBillCacilateDto.getIndustrulCode());
					discountParmDto.setMinFee(productPriceDto.getMinFee());
					discountParmDto.setMaxFee(productPriceDto.getMaxFee());
					//计费类型 体积 、重量、费用
					discountParmDto.setCaculateType(productPriceDto.getCaculateType());
					discountParmDto.setSaleChannelCode(queryBillCacilateDto.getChannelCode());
	    		    DiscountResultDto discountResult=null;
	    		    
	    		    //此处封装调用普元的参数,（规则类型、开单日期，是否享受过合同折扣，折前金额） 这几项是必须输入的
	    		    DiscountCondition discountCondition = new DiscountCondition();
	    		    /**
	    			 * FOSS20150924 DP-特惠活动客户折扣新增
	    			 * @author foss-206860
	    			 * */
	    			//DP-特惠活动客户折扣新增--begin
	    		    //根据客户编码查询对应客户特惠组编码和名称（查询FOSS综合的方法）
	    		    if(StringUtil.isNotBlank(queryBillCacilateDto.getCustomerCode())){
	    		    	//根据客户编码查询客户特惠组信息
	    		    	SpecialdiscountEntity specialdiscountEntity = specialdiscountService.selectBySpecialdiscountTime(queryBillCacilateDto.getCustomerCode(),receiveDate);
	    		    	if(specialdiscountEntity != null){
	    		    		//客户特惠组编码
	    		    		if(StringUtil.isNotEmpty(specialdiscountEntity.getProductcode())){
	    		    			discountCondition.setCustomerPreferencesCode(specialdiscountEntity.getProductcode());
	    		    		}
	    		    		//客户特惠组名称
	    		    		if(StringUtil.isNotEmpty(specialdiscountEntity.getProducttype())){
	    		    			discountCondition.setCustomerPreferencesName(specialdiscountEntity.getProducttype());
	    		    		}
	    		    	}
	    		    }
	    		    //DP-特惠活动客户折扣新增--end
	    		  //出发区域
    			    PriceRegionEntity priceRegionEntity = regionService.searchRegionByID(originalId, PricingConstants.PRICING_REGION);
	    		    if(priceRegionEntity!=null){
	    		    	discountCondition.setDepartArea(priceRegionEntity.getRegionName());
	    		    }
    			    
	    		    //到达区域
	    		    PriceRegionArriveEntity priceRegionArriveEntity = regionArriveService.searchRegionByID(destinationId, PricingConstants.PRICING_REGION);
	    		    if(priceRegionArriveEntity!=null){
	    		    	discountCondition.setDestArea(priceRegionArriveEntity.getRegionName());
	    		    }
	    		    //出发城市
	    		    discountCondition.setDepartCity(startCityName);
	    		    //到大城市
	    		    discountCondition.setDestCity(arrvCityName);
	    		    //出发网点
	    		    discountCondition.setDepartBranch(departDept);
	    		    //到达网点
	    		    discountCondition.setDestBranch(destDept);
	    		    //产品名称
	    		    discountCondition.setProduct(productPriceDto.getProductName());
	    		    //货物类型
	    		    if("H00001".equalsIgnoreCase(queryBillCacilateDto.getGoodsCode())){
	    		    	discountCondition.setGoodsType("全部");
	    		    }else{
	    		    	discountCondition.setGoodsType(queryBillCacilateDto.getGoodsTypeName());
	    		    }
	    		    //渠道名称
	    		    String termsCode=WaybillConstants.DICTIONARY_TERMS_CODE;
	    		    String valueCode=queryBillCacilateDto.getChannelCode();
	    		    if(StringUtil.isNotEmpty(valueCode)){	    		    	
	    		    	DataDictionaryValueEntity dvEntity=dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(termsCode, valueCode);
	    		    	if(dvEntity!=null){	    		    		
	    		    		discountCondition.setChannel(dvEntity.getValueName());
	    		    	}
	    		    }
	    			//行业名称
					if(StringUtils.isNotBlank(queryBillCacilateDto.getCustomerCode())){
					 CustomerIndustryEntity customerIndustry= customerIndustryService.querySecProfessionByCusCode(queryBillCacilateDto.getCustomerCode());
						 if(null!=customerIndustry){
						  discountCondition.setIndustry(customerIndustry.getProfessionName());
					  }
					 }
	    		    //开单日期
		    		String billTime = DateUtils.convert(queryBillCacilateDto.getBillTime()==null?new Date():queryBillCacilateDto.getBillTime(), DateUtils.DATE_TIME_FORMAT);
	    		    discountCondition.setBizDate(billTime);
	    		    //开单时间，开单日期的时分秒
	    		    if(billTime!=null){
	    		    	discountCondition.setBizTime(billTime.split(" ")[1]);
	    		    }
	    		  
					//计费重量或者计费体积
					if("WEIGHT".equalsIgnoreCase(productPriceDto.getCaculateType())){
						//计费类型
						discountCondition.setDiscountBase("重量");
						discountCondition.setAmount(weight.doubleValue()+"");
					}else if("VOLUME".equalsIgnoreCase(productPriceDto.getCaculateType())){
						//计费类型
						discountCondition.setDiscountBase("体积");
						discountCondition.setAmount(volume.doubleValue()+"");
					}
					//是否接货
					if("N".equalsIgnoreCase(queryBillCacilateDto.getIsReceiveGoods()) && !FossConstants.YES.equalsIgnoreCase(queryBillCacilateDto.getIsReceiveGoodsChange())){
						discountCondition.setIsReceive("否");
					}else{
						discountCondition.setIsReceive("是");
					}
	    		    //规则类型，这里是运费折扣计算
					discountCondition.setRuleTypeCode("foss.discount.transdiscount");
					//客户编码
					if(StringUtils.isNotBlank(queryBillCacilateDto.getCustomerCode())){
						discountCondition.setClientCode(queryBillCacilateDto.getCustomerCode());
					}else{
						discountCondition.setClientCode(null);
					}
					//调用普元接口后的结果
	    			DiscountCondition result = null;
	    			String condition =null;
//	    			clientPyService.type("multipart/mixed").accept("application/json"); 
				    String result1 = null;
					//定义折扣相关信息
	    		    List<PriceDiscountDto> priceDiscountDtoList = new ArrayList<PriceDiscountDto>();
	    		   // setRulengineTimeOut();
	    		    //对于合同客户优先打折计算
	    			for (int j = 0; j < discountPriorityEntities.size(); j++) {
	    				DiscountPriorityEntity entity = discountPriorityEntities.get(j);
	    				if(entity != null && entity.getCode() != null) {
	    					if(StringUtil.equals(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT, entity.getCode())) {
    							DiscountTypeInterface discountExe = discountTypeFactory.getDiscountTypeImpl(entity.getCode());
        						if(discountExe != null) {
        							//获取客户折扣操作类
        							discountResult = discountExe.doDiscount(discountParmDto);
        							//折扣存在,切折扣率不等于1
        							if(discountResult != null ) {
										//zxy 20140517 MANA-1689 start 新增:当开单选择“精准空运”时，不使用CRM客户合同中的的运费折扣
        								if (StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT,productPriceDto.getProductCode())
        										&& StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT
        										,discountResult.getDiscountMode())){
//        									discountResult.setDiscountRate(new BigDecimal("1"));	//设置为100%
        									continue;
        								}
        								//zxy 20140517 MANA-1689 end 新增:当开单选择“精准空运”时，不使用CRM客户合同中的的运费折扣
        								boolean isExpress = productService.onlineDetermineIsExpressByProductCode(discountParmDto.getProductCode(), discountParmDto.getReceiveDate());
        								//计算折扣
        								DiscountResultDto discountResultDto = PriceUtil.calculateCustomDiscountClientData(discountResult, discountParmDto, isExpress);
        								//将折扣价格赋值原计算后价格
        								productPriceDto.setCaculateFee(discountResultDto.getDiscountValue());
        								//将折扣价格赋值折扣价格
        								productPriceDto.setDiscountFee(discountResultDto.getDiscountValue());
        								//保留计价明细ID
        								productPriceDto.setId(discountResultDto.getId());
        								//折扣费率
	    								BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
	    								productPriceDto.setActualFeeRate(discountResultDto.getDiscountRate().multiply(actualFeeRate));
        								//保留折扣相关信息
	    								priceDiscountDtoList.addAll(discountResultDto.getDiscountPrograms());
//        								productPriceDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
        								//折前金额,若是合同客户，则是计算完合同折扣以后的金额，否则是计算运费后的金额
        								discountCondition.setAmountbeforediscount(discountResultDto.getDiscountValue().doubleValue()+"");
        								//设置为叠加折扣
        								discountCondition.setIsOverLay("2");
//    									flag = true;
    									break;
        							} else {
        								continue;
        							}
        						} else {
        							continue;
        						}
    						}else{
    						if(isStartBrms(queryBillCacilateDto.getChannelCode(),originalOrgCode)){
    							discountCondition.setAmountbeforediscount(productPriceDto.getCaculateFee().toString());
    							//设置为非叠加折扣
    		    				discountCondition.setIsOverLay("1");
    		    				condition = JSON.toJSONString(discountCondition);
    		    				JSONObject jsonObject = new JSONObject();
    		    				jsonObject.put("json", condition);
    		    				jsonObject.put("date", discountCondition.getBizDate());
    		    				jsonObject.put("ruleTypeCode", "foss.discount.transdiscount");
    		    				condition = JSON.toJSONString(jsonObject);
//    		    				List<Attachment> atts = new LinkedList<Attachment>(); 
//    						    atts.add(new Attachment("ruleTypeCode", "application/json", "foss.discount.transdiscount")); 
//    						    atts.add(new Attachment("date", "application/json", billTime));
//    		    				atts.add(new Attachment("json", "application/json", condition));
    		    				 try {//调用接口异常，则直接采用老的折扣运算规则
    		    					String bussinessDate = DateUtils.convert(new Date());
	    						    long begin=System.currentTimeMillis();
	    						    log.info("公布价规则引擎调用开始(非折上折):"+bussinessDate);
 		    				    	result1 = clientPyService.type("application/json").accept("application/json").post(condition,String.class);
  							    	result =JsonUtil.jsonToPojo(result1, DiscountCondition.class);//此处进行数据转换
  							    	long process=System.currentTimeMillis()-begin;
    						    	log.info("公布价规则引擎调用结束,业务发生时间为:"+bussinessDate+":持续时间为:"+process);
    						    	 if(process>=NUMBER_800){
    						    		 log.warn("公布价规则引擎调用持续时间太长了:"+process);
    						    	 }	
    		    				 } catch (Exception e) {
  									log.info("调用普元接口异常，请确认是否开启普元服务");
  									exceptionFlag = true;
  									oldFlag = true ;
  									break;
  								}
    		    				if(result != null){
    		 	    				if(Boolean.valueOf(result.getError())){//如果普元接口异常
    		 		    				log.info(result.getErrorContent());
    		 		    				exceptionFlag = true;
     									break;
    		 		    			}else{
    		 		    				//设置折扣相关信息
    		 		    				setDiscount(result, productPriceDto, priceDiscountDtoList, discountCondition, true);
//    		 							flag = true;
    		 							break;
    		 		    			}
    		 	    			}
    						 } 
    						}
	    				} else {
	    					continue;
	    				}
	    			}
	    			if(!exceptionFlag){//如果调用规则引擎进行普通折扣没有抛异常，则进行叠加折扣
	    				//当折扣一次之后，运费已经低于最低一票，则不调用规则引擎叠加折扣--Online-定价体系优化项目DJTWO-175-DJTWO-251
	    				if(productPriceDto != null && productPriceDto.getMinFee() != null
	    						&& productPriceDto.getCaculateFee() != null
	    						&& productPriceDto.getCaculateFee().compareTo(productPriceDto.getMinFee()) > 0){
		    				if(isStartBrms(queryBillCacilateDto.getChannelCode(),originalOrgCode)){
			    				//设置为叠加折扣
								discountCondition.setIsOverLay("2");
			    				// 此处调用普元接口，进行折上折
				    			condition = JSON.toJSONString(discountCondition);
				    			JSONObject jsonObject = new JSONObject();
			    				jsonObject.put("json", condition);
			    				jsonObject.put("date", discountCondition.getBizDate());
			    				jsonObject.put("ruleTypeCode", "foss.discount.transdiscount");
			    				condition = JSON.toJSONString(jsonObject);
		//		    			List<Attachment> atts = new LinkedList<Attachment>(); 
		//					    atts.add(new Attachment("ruleTypeCode", "application/json", "foss.discount.transdiscount")); 
		//					    atts.add(new Attachment("date", "application/json", billTime));
		//	    				atts.add(new Attachment("json", "application/json", condition));
			    				 try {//调用接口异常，则直接采用老的折扣运算规则
			    					    String bussinessDate = DateUtils.convert(new Date());
		    						    long begin=System.currentTimeMillis();
		    						    log.info("公布价规则引擎调用开始(折上折):"+bussinessDate);
			    				    	result1 = clientPyService.type("application/json").accept("application/json").post(condition,String.class);
								    	result =JsonUtil.jsonToPojo(result1, DiscountCondition.class);//此处进行数据转换
								    	long process=System.currentTimeMillis()-begin;
								    	log.info("公布价规则引擎调用结束,业务发生时间为:"+bussinessDate+":持续时间为:"+process);
	    						    	 if(process>=NUMBER_800){
	    						    		 log.warn("公布价规则引擎调用持续时间太长了:"+process);
	    						    	 }		
			    				 } catch (Exception e) {
										log.info("调用普元接口异常，请确认是否开启普元服务");
										exceptionFlag = true;
										oldFlag = true ;
										break;
									}
				    			if(result != null){
				    				if(Boolean.valueOf(result.getError())){//如果普元接口异常
					    				log.info(result.getErrorContent());
					    				exceptionFlag = true;
										break;
					    			}else{
					    				//设置折扣相关信息
					    				setDiscount(result, productPriceDto, priceDiscountDtoList,discountCondition,false);
					    				discountResultList.add(productPriceDto);//如果异常，折扣结果不采用折上折，继续使用原来计算后的结果
					    				flag = true;
					    			}
				    			 }
			    			}	
	    				}
		    			//保留折扣相关信息
						productPriceDto.setDiscountPrograms(priceDiscountDtoList);
						if(!flag) {
			    			discountResultList.add(productPriceDto);
			    		}
	    			}else{
	    				break;
	    			}
	    		} else {
	    			discountResultList.add(productPriceDto);
	    		}
	    	} 
	    } 	    
		if(oldFlag){
			return doOldFRTDiscount(originalOrgCode,
						destinationOrgCode, receiveDate, weight,
						volume, originalId, destinationId,
						oldCaculateresultList, queryBillCacilateDto);
		}
	    return discountResultList;
	    /**  BillCaculateService 是开单计算价格、增值服务、折扣、优惠活动的实现类
	     * 
		 *  计算运费分别为：FRT
		 *  
		 *  汽运  - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 0
		 *  
		 *  空运 - t_srv_pice_plan价格表  字段 TRANSPORT_FLAG = 1
		 *  
		 *  
		 *  计算增值服务：  VAS
		 *  
		 *  保费 - BF
		 *  
		 *  代收货款 - HK
		 *  
		 *  送货费 - SH
		 *  
		 *  接货费 - JH
		 *  
		 *  签收回单 -QS
		 *  
		 *  保管费 - CCF
		 *  
		 *  综合信息费 -ZHXX
		 *  
		 *  燃油附加费 - RYFJ
		 *  
		 *  中转费 - ZZ
		 *  
		 *  接货差额补差 -JHCEBC
		 *  
		 *  电子优惠券 - DZYHQ
		 *  
		 *  其他费用 -QT
		 *  
		 *  包装费 -BZ
		 *  
		 *  送货上楼费 -SHSL
		 *  
		 *  超远派送费 -CY
		 *  
		 *  送货进仓费 -SHJCF
		 *  
		 *  更改费 -GGF
		 *  
		 *  以上服务相关代码进行不同维度计算。
		 *  
		 *  可支绑定如产品、区域 等等进行价格、折扣、优惠计算
		 */
	}
	
	/**
	 * 设置折扣信息
	 */
	private void setDiscount(DiscountCondition result,ProductPriceDto productPriceDto , 
			List<PriceDiscountDto> priceDiscountDtoList,DiscountCondition discountCondition,boolean isOverLay){

		if(!"0".equalsIgnoreCase(result.getDiscountFlag())&&result.getDiscountFlag()!=null){
			//折扣的明细信息
			PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
			boolean flag = false;
			//普元折扣费率
				if(result.getDiscountFlag()!=null && "1".equals(result.getDiscountFlag())){
					priceDiscountDto.setDiscountRate(BigDecimal.valueOf(Double.valueOf(result.getDiscoutPercent())));
					//总折扣费率
					BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
					productPriceDto.setActualFeeRate(priceDiscountDto.getDiscountRate().multiply(actualFeeRate));
				}else{//直接减免，总折扣率还是折扣前的，因此不重新设置总折扣率
					//如果直接减免金额为0，则不添加该折扣的明细
					if(Double.valueOf(result.getDiscoutDecrease())==0){
						flag = true;
					}
					//设置为折扣金额
					priceDiscountDto.setDiscountRate(BigDecimal.valueOf(0-Double.valueOf(result.getDiscoutDecrease())));
				}
				//组装计价明细ID
				priceDiscountDto.setChargeDetailId(productPriceDto.getId());
				priceDiscountDto.setMarketName(productPriceDto.getPriceEntityName());
				//原始费用
				priceDiscountDto.setOriginnalCost(BigDecimal.valueOf(Double.valueOf(result.getAmountbeforediscount())));
				priceDiscountDto.setPriceEntryCode(productPriceDto.getPriceEntityCode());
				priceDiscountDto.setPriceEntryName(productPriceDto.getPriceEntityName());
				//优惠金额
				double reduceFee = Double.valueOf(result.getAmountbeforediscount())-Double.valueOf(result.getAmountafterdiscount());
				if(BigDecimal.valueOf(reduceFee).compareTo(BigDecimal.ZERO) == 0 ){
					flag = true;
				}
				 priceDiscountDto.setReduceFee(BigDecimal.valueOf(reduceFee));
				//方案名称
				priceDiscountDto.setTypeName(result.getRuleDefHeaderName());
				priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE_RULE_CODE);
				//折扣明细ID
				priceDiscountDto.setDiscountId(result.getRuleDefHeaderId());
				if(!flag){//如果直接减免金额为0，则不添加该折扣的明细,否则添加
					priceDiscountDtoList.add(priceDiscountDto);
				}
				
				
				//将折扣价格赋值原计算后价格
				productPriceDto.setCaculateFee(new BigDecimal(result.getAmountafterdiscount()));
				//将折扣价格赋值折扣价格
				productPriceDto.setDiscountFee(new BigDecimal(result.getAmountafterdiscount()));
				
//----begin------修复bug 定价体系优化项目POP-373				
				//当存在最第一票时，比较最低一票和折后金额
				if(null != productPriceDto.getMinFee()){
					//当折后金额 <最低一票
					if(productPriceDto.getCaculateFee().compareTo(productPriceDto.getMinFee()) < 0){
						log.debug("折扣价格低于最低一票");
						//将最低一票赋值原计算后价格
						productPriceDto.setCaculateFee(productPriceDto.getMinFee());
						//将最低一票赋值折扣价格
						productPriceDto.setDiscountFee(productPriceDto.getMinFee());
						//当折前金额低于最低一票时，默认设置优惠金额为0，折扣费率为1
						if(new BigDecimal(result.getAmountbeforediscount()).compareTo(productPriceDto.getMinFee()) >= 0){
							//优惠金额为折前金额-最低一票
							priceDiscountDto.setReduceFee(new BigDecimal(result.getAmountbeforediscount()).subtract(productPriceDto.getMinFee()));
							//折扣费率 = 最低一票/原始费用
							priceDiscountDto.setDiscountRate(productPriceDto.getMinFee().divide(new BigDecimal(result.getAmountbeforediscount()), 2, RoundingMode.HALF_DOWN));
						}else{
							//优惠金额为0
							priceDiscountDto.setReduceFee(BigDecimal.ZERO);
							//折扣费率 = 1
							priceDiscountDto.setDiscountRate(BigDecimal.ONE);
						}
						//--begin----定价体系优化项目POP-417
//						priceDiscountDto.setTypeName("最低一票");
						//--end----定价体系优化项目POP-417
					}
				}
//----end------修复bug 定价体系优化项目POP-373				
				
				if(isOverLay){//如果是普通折扣，则设置折前金额，为叠加折扣准备，若是叠加折扣，这不比设折前金额
					//设置折前金额
					discountCondition.setAmountbeforediscount(result.getAmountafterdiscount());
				}
				
		}
		
	
	}
	
	
	
	
	/**
	 * 老的运费折扣计算.
	 *
	 * @param originalOrgCode the original org code
	 * @param destinationOrgCode the destination org code
	 * @param receiveDate the receive date
	 * @param weight the weight
	 * @param volume the volume
	 * @param originalId the original id
	 * @param destinationId the destination id
	 * @param caculateresultList the caculateresult list
	 * @param queryBillCacilateDto the query bill cacilate dto
	 * @return the list
	 * @author zhangdongping
	 * @date 2012-12-25 下午3:32:38
	 * @see
	 * 
	 * 计算规则：
	 * 
	 * 折扣根据价格计算的方式（体积、重量）来选择适用哪种方式的折扣。
	 * 
	 * 例如，如果价格以体积进行计算，则折扣也需要以体积折扣计算。同时，再选择直接费用折扣 计算（如果有的情况下）。
	 * 
	 * 比较二者打折后，选取优惠幅度更高的作为最终折扣。
	 * 
	 * 注意，在普通客户合同、渠道、产品折扣的情况下，最终计算出的价格不能低于价格最低一票。
	 */
	private List<ProductPriceDto> doOldFRTDiscount(String originalOrgCode,
		String destinationOrgCode, Date receiveDate, BigDecimal weight,
		BigDecimal volume, String originalId, String destinationId,
		List<ProductPriceDto> caculateresultList,QueryBillCacilateDto queryBillCacilateDto) {
		
	    List<ProductPriceDto> discountResultList = new ArrayList<ProductPriceDto>();
	    //获取折扣优先级
	    List<DiscountPriorityEntity> discountPriorityEntities = discountPriorityService.getDiscountPriorityByCache();
	    if(CollectionUtils.isNotEmpty(caculateresultList)) {
	    	boolean flag = false;
	    	for (int i = 0; i < caculateresultList.size(); i++) {
	    		flag = false;
	    		ProductPriceDto productPriceDto = caculateresultList.get(i);
	    		//是否按照优先级处理
	    		if(CollectionUtils.isNotEmpty(discountPriorityEntities)) {
	    			DiscountParmDto discountParmDto = new DiscountParmDto();
	    		    //出发部门
	    		    OrgAdministrativeInfoEntity deptOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(originalOrgCode);
	    		    //到达部门
	    		    OrgAdministrativeInfoEntity arrvOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(destinationOrgCode);
	    		    //匹配出发、到达折扣的城市信息
	    		    if(deptOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(deptOrgAdministrativeInfoEntity.getCityCode())) {
	    		    	 discountParmDto.setDeptCityCode(deptOrgAdministrativeInfoEntity.getCityCode());
	    		    }
	    		    if(arrvOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(arrvOrgAdministrativeInfoEntity.getCityCode())) {
	    		    	discountParmDto.setArrvCityCode(arrvOrgAdministrativeInfoEntity.getCityCode());
	    		    }
	    		    //依次设置折扣信息的出发区域、到达区域、始发部门、到达部门、计价条目信息、
	    		    //产品、货物、业务日期、重货、轻货、原始费用、计费类别
	    		    discountParmDto.setCriteriaDetailId(productPriceDto.getId());
	    		    discountParmDto.setDeptRegionId(originalId);
	    		    discountParmDto.setArrvRegionId(destinationId);
	    		    discountParmDto.setOriginalOrgCode(originalOrgCode);
	    		    discountParmDto.setDestinationOrgCode(destinationOrgCode);
	    		    discountParmDto.setPricingEntryCode(productPriceDto.getPriceEntityCode());
	    		    discountParmDto.setPricingEntryName(productPriceDto.getPriceEntityName());
	    		    discountParmDto.setProductCode(productPriceDto.getProductCode());
	    		    discountParmDto.setGoodsTypeCode(productPriceDto.getGoodsTypeCode());
	    		    discountParmDto.setReceiveDate(receiveDate);
	    		    discountParmDto.setWeight(weight);
	    		    discountParmDto.setVolume(volume);
	    		    //计算后的价格
	    		    discountParmDto.setOriginnalCost(productPriceDto.getCaculateFee());
	    		    discountParmDto.setCustomCode(queryBillCacilateDto.getCustomerCode());
					discountParmDto.setIndustryCode(queryBillCacilateDto.getIndustrulCode());
					discountParmDto.setMinFee(productPriceDto.getMinFee());
					discountParmDto.setMaxFee(productPriceDto.getMaxFee());
					//计费类型 体积 、重量、费用
					discountParmDto.setCaculateType(productPriceDto.getCaculateType());
					discountParmDto.setSaleChannelCode(queryBillCacilateDto.getChannelCode());
	    		    DiscountResultDto discountResult=null;
	    		    //对于合同客户优先打折计算
	    			for (int j = 0; j < discountPriorityEntities.size(); j++) {
	    				DiscountPriorityEntity entity = discountPriorityEntities.get(j);
	    				if(entity != null && entity.getCode() != null) {
	    					if(StringUtil.equals(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT, entity.getCode())) {
    							DiscountTypeInterface discountExe = discountTypeFactory.getDiscountTypeImpl(entity.getCode());
        						if(discountExe != null) {
        							//获取客户折扣操作类
        							discountResult = discountExe.doDiscount(discountParmDto);
        							//折扣存在,切折扣率不等于1
        							if(discountResult != null ) {
										//zxy 20140517 MANA-1689 start 新增:当开单选择“精准空运”时，不使用CRM客户合同中的的运费折扣
        								if (StringUtil.equals(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT,productPriceDto.getProductCode())
        										&& StringUtil.equals(PriceEntityConstants.PRICING_CODE_FRT
        										,discountResult.getDiscountMode())){
//        									discountResult.setDiscountRate(new BigDecimal("1"));	//设置为100%
        									continue;
        								}
        								//zxy 20140517 MANA-1689 end 新增:当开单选择“精准空运”时，不使用CRM客户合同中的的运费折扣
        								boolean isExpress = productService.onlineDetermineIsExpressByProductCode(discountParmDto.getProductCode(), discountParmDto.getReceiveDate());
        								//计算折扣
        								DiscountResultDto discountResultDto = PriceUtil.calculateCustomDiscountClientData(discountResult, discountParmDto, isExpress);
        								//将折扣价格赋值原计算后价格
        								productPriceDto.setCaculateFee(discountResultDto.getDiscountValue());
        								//将折扣价格赋值折扣价格
        								productPriceDto.setDiscountFee(discountResultDto.getDiscountValue());
        								//保留计价明细ID
        								productPriceDto.setId(discountResultDto.getId());
        								//折扣费率
	    								BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
	    								productPriceDto.setActualFeeRate(discountResultDto.getDiscountRate().multiply(actualFeeRate));
        								//保留折扣相关信息
        								productPriceDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
        								discountResultList.add(productPriceDto);
    									flag = true;
    									break;
        							} else {
        								continue;
        							}
        						} else {
        							continue;
        						}
    						} else {
    							DiscountTypeInterface discountExe = discountTypeFactory.getDiscountTypeImpl(entity.getCode());
    	    					if(discountExe != null) {
    	    						try {
    	    							//获取渠道或是产品折扣操作类
    	    							discountResult =discountExe.doDiscount(discountParmDto);
    	    						} catch (Exception e) {
    	    							continue;
    	    						}
    	    						//折扣存在,切折扣率不等于100
    	    						if(discountResult!=null) {
    	    							//计算折扣
    	    							DiscountResultDto discountResultDto = PriceUtil.calculatePriceDiscountClientData(discountResult, discountParmDto);
    	    							if(discountResultDto != null && discountResultDto.getId() != null) {
    	    								//将折扣价格赋值原计算后价格
    	    								productPriceDto.setCaculateFee(discountResultDto.getDiscountValue());
    	    								//将折扣价格赋值折扣价格
    	    								productPriceDto.setDiscountFee(discountResultDto.getDiscountValue());
    	    								//保留计价明细ID
    	    								productPriceDto.setId(discountResultDto.getId());
    	    								//折扣费率
    	    								BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
    	    								productPriceDto.setActualFeeRate(discountResultDto.getDiscountRate().multiply(actualFeeRate));
    	    								//保留折扣相关信息
    	    								productPriceDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
    	    								discountResultList.add(productPriceDto);
    	    								flag = true;
    	    								break;
    	    							} else {
    	    								continue;
    	    							}
    	    						} else {
    	    							continue;
    	    						}
    	    					} else {
    	    						continue;
    	    					}
    						}
	    				} else {
	    					continue;
	    				}
	    			}
	    			if(!flag) {
		    			discountResultList.add(productPriceDto);
		    		}
	    		} else {
	    			discountResultList.add(productPriceDto);
	    		}
	    	} 
	    } 	    
		
	    return discountResultList;
	    /**  BillCaculateService 是开单计算价格、增值服务、折扣、优惠活动的实现类
	     * 
		 *  计算运费分别为：FRT
		 *  
		 *  汽运  - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 0
		 *  
		 *  空运 - t_srv_pice_plan价格表  字段 TRANSPORT_FLAG = 1
		 *  
		 *  
		 *  计算增值服务：  VAS
		 *  
		 *  保费 - BF
		 *  
		 *  代收货款 - HK
		 *  
		 *  送货费 - SH
		 *  
		 *  接货费 - JH
		 *  
		 *  签收回单 -QS
		 *  
		 *  保管费 - CCF
		 *  
		 *  综合信息费 -ZHXX
		 *  
		 *  燃油附加费 - RYFJ
		 *  
		 *  中转费 - ZZ
		 *  
		 *  接货差额补差 -JHCEBC
		 *  
		 *  电子优惠券 - DZYHQ
		 *  
		 *  其他费用 -QT
		 *  
		 *  包装费 -BZ
		 *  
		 *  送货上楼费 -SHSL
		 *  
		 *  超远派送费 -CY
		 *  
		 *  送货进仓费 -SHJCF
		 *  
		 *  更改费 -GGF
		 *  
		 *  以上服务相关代码进行不同维度计算。
		 *  
		 *  可支绑定如产品、区域 等等进行价格、折扣、优惠计算
		 */
	}
	
	/**
	 * 处理运费CRM营销活动处理
	 * @创建时间 2014-4-21 下午1:07:09   
	 * @创建人： WangQianJin
	 */
	private List<ProductPriceDto> crmActiveDiscountProcessFRT(QueryBillCacilateDto queryBillCacilateDto,List<ProductPriceDto> discountResultList){
		//获取运费
		if(CollectionUtils.isNotEmpty(discountResultList)){
			for (int i = 0; i < discountResultList.size(); i++) { 
				ProductPriceDto price=discountResultList.get(i);
				if(price!=null && PriceEntityConstants.PRICING_CODE_FRT.equals(price.getPriceEntityCode())){
					queryBillCacilateDto.setTransportFee(price.getCaculateFee());
					break;
				}
			}
		}
		
		//校验是否可以享有市场营销活动
		if(queryBillCacilateDto.isPda()){			
			PriceUtil.validateActiveDiscountPda(queryBillCacilateDto,markActivitiesService);			
		}else{
			PriceUtil.validateActiveDiscount(queryBillCacilateDto,customerService,markActivitiesService);	
		}		
		/**
		 * 判断是否计算市场活动折扣		
		 */
		if(queryBillCacilateDto.isCalActiveDiscount()){
			List<ProductPriceDto> productPriceDtos = new ArrayList<ProductPriceDto>();
			if(CollectionUtils.isNotEmpty(discountResultList)) {			
				for (int i = 0; i < discountResultList.size(); i++) {  
					//费用对象
					ProductPriceDto productPriceDto =  discountResultList.get(i);  
					//折扣列表
					List<PriceDiscountDto> priceDiscountDtos = productPriceDto.getDiscountPrograms();
					if(priceDiscountDtos==null){
						priceDiscountDtos=new ArrayList<PriceDiscountDto>();
					}
					//市场营销活动DTO
					MarkActivitiesQueryConditionDto activeDto=queryBillCacilateDto.getActiveDto();
					//计算出的价格
            		BigDecimal caculateFee=productPriceDto.getCaculateFee();
            		//ID
            		String id=productPriceDto.getId();
					//获取营销活动折扣
			    	if(activeDto!=null && CollectionUtils.isNotEmpty(activeDto.getOptionList())){			    			    		
			    		for(MarkOptionsEntity entity:activeDto.getOptionList()){			    			
			        		if(entity!=null && entity.getName()!=null && entity.getValue()!=null){        			
			            		//获取折扣类型(运费、保费等)
			            		String discountType=entity.getName();
			            		//获取折扣值
			            		BigDecimal discountRate=entity.getValue();
			            		//折扣实体
			            		PriceDiscountDto priceDiscountDto=null;		
			            		//营销活动折扣相应的CRM_ID
			            		BigDecimal crmId=entity.getCrmId();			            		
		            			//运费
			            		if(PriceEntityConstants.PRICING_CODE_FRT.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_FRT.equals(productPriceDto.getPriceEntityCode())){		            			
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,null);
			            			//计算折扣后的费用(折上折)
			            			BigDecimal discountFee = productPriceDto.getCaculateFee().multiply(discountRate);
			            			productPriceDto.setCaculateFee(discountFee);
			            			//折扣费率
    								BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
    								productPriceDto.setActualFeeRate(discountRate.multiply(actualFeeRate));
			            			priceDiscountDtos.add(priceDiscountDto);
			            		}			            				            					            		
			        		}
			    		}    		
			    	}
			    	//将折扣信息添加到费用对象中
			    	productPriceDto.setDiscountPrograms(priceDiscountDtos);
			    	//将费用对象添加费用集合中
			    	productPriceDtos.add(productPriceDto);
				}			
			}
			//返回费用集合
	    	return productPriceDtos;
		}else{
			//不计算市场活动折扣则直接返回
			return discountResultList;
		}
		
	}	
	
	/**
	 * 处理增值服务费CRM营销活动处理
	 * @创建时间 2014-4-21 下午1:07:09   
	 * @创建人： WangQianJin
	 */
	private List<ValueAddDto> crmActiveDiscountProcessValueAdd(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto,List<ValueAddDto> discountResultList){		
		//校验是否可以享有市场营销活动
		if(queryBillCacilateValueAddDto.isPda()){
			PriceUtil.validateActiveDiscountPda(queryBillCacilateValueAddDto,markActivitiesService);
		}else{
			PriceUtil.validateActiveDiscount(queryBillCacilateValueAddDto,customerService,markActivitiesService);
		}
		/**
		 * 判断是否计算市场活动折扣
		 */
		if(queryBillCacilateValueAddDto.isCalActiveDiscount()){
			List<ValueAddDto> valueAddDtos = new ArrayList<ValueAddDto>();
			if(CollectionUtils.isNotEmpty(discountResultList)) {			
				for (int i = 0; i < discountResultList.size(); i++) {  
					//费用对象
					ValueAddDto valueAddDto =  discountResultList.get(i);  
					//折扣列表
					List<PriceDiscountDto> priceDiscountDtos = valueAddDto.getDiscountPrograms();
					if(priceDiscountDtos==null){
						priceDiscountDtos=new ArrayList<PriceDiscountDto>();
					}
					//市场营销活动DTO
					MarkActivitiesQueryConditionDto activeDto=queryBillCacilateValueAddDto.getActiveDto();
					//计算出的价格
            		BigDecimal caculateFee=valueAddDto.getCaculateFee();
            		//ID
            		String id=valueAddDto.getId();
					//获取营销活动折扣
			    	if(activeDto!=null && CollectionUtils.isNotEmpty(activeDto.getOptionList())){			    			    		
			    		for(MarkOptionsEntity entity:activeDto.getOptionList()){			    			
			        		if(entity!=null && entity.getName()!=null && entity.getValue()!=null){        			
			            		//获取折扣类型(运费、保费等)
			            		String discountType=entity.getName();
			            		//获取折扣值
			            		BigDecimal discountRate=entity.getValue();
			            		//折扣实体
			            		PriceDiscountDto priceDiscountDto=null;	
			            		//营销活动折扣相应的CRM_ID
			            		BigDecimal crmId=entity.getCrmId();
			            		//费用子类型
			            		String subType=valueAddDto.getSubType(); 
		            			//接货费
			            		if(PriceEntityConstants.PRICING_CODE_JH.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_JH.equals(valueAddDto.getPriceEntityCode())){
			            			BigDecimal jhFee=caculateFee;
			            			BigDecimal discountFee=caculateFee;
			            			//计算折扣后的费用(折上折)
			            			if (null != valueAddDto.getFee()) {	
			            				jhFee=valueAddDto.getFee();
			            				//对客户取的固定接货费打折（为了前台获取接货费使用）
			            				discountFee = valueAddDto.getFee().multiply(discountRate);
				            			valueAddDto.setFee(discountFee);				            			
			            			}else {
			            				jhFee=valueAddDto.getMinFee();
			            				//对最低一票接货费打折（为了前台获取接货费使用）
				            			discountFee = valueAddDto.getMinFee().multiply(discountRate);
				            			valueAddDto.setMinFee(discountFee);				            			
			            			}
			            			valueAddDto.setCaculateFee(discountFee);
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,jhFee,id,activeDto,crmId,subType);			            			
			            			//折扣费率
    								BigDecimal actualFeeRate = new BigDecimal(valueAddDto.getActualFeeRate().doubleValue()/NUMBER_100);
    								valueAddDto.setActualFeeRate(discountRate.multiply(actualFeeRate));
			            			priceDiscountDtos.add(priceDiscountDto);
			            		}
			            		//送货费
			            		if(PriceEntityConstants.PRICING_CODE_SH.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_SH.equals(valueAddDto.getPriceEntityCode())){
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,subType);
			            			//计算折扣后的费用(折上折)
			            			BigDecimal discountFee = valueAddDto.getCaculateFee().multiply(discountRate);
			            			valueAddDto.setCaculateFee(discountFee);
			            			//折扣费率
    								BigDecimal actualFeeRate = valueAddDto.getActualFeeRate();
    								valueAddDto.setActualFeeRate(discountRate.multiply(actualFeeRate));
			            			priceDiscountDtos.add(priceDiscountDto);
			            		}
			            		//保价费
			            		if(PriceEntityConstants.PRICING_CODE_BF.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_BF.equals(valueAddDto.getPriceEntityCode())){
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,subType);
			            			//计算折扣后的费用(折上折)
			            			BigDecimal discountFee = valueAddDto.getCaculateFee().multiply(discountRate);
			            			valueAddDto.setCaculateFee(discountFee);
			            			//折扣费率
    								BigDecimal actualFeeRate = valueAddDto.getActualFeeRate();
    								valueAddDto.setActualFeeRate(discountRate.multiply(actualFeeRate));
			            			priceDiscountDtos.add(priceDiscountDto);
			            		}
			            		//包装费
			            		if(PriceEntityConstants.PRICING_CODE_BZ.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_BZ.equals(valueAddDto.getPriceEntityCode())
			            				&& (DictionaryValueConstants.PACKAGE_TYPE__FRAME.equals(subType) || DictionaryValueConstants.PACKAGE_TYPE__BOX.equals(subType))){
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,subType);
			            			//计算折扣后的费用(折上折)
			            			BigDecimal discountFee = valueAddDto.getCaculateFee().multiply(discountRate);
			            			valueAddDto.setCaculateFee(discountFee);
			            			//折扣费率
    								BigDecimal actualFeeRate = valueAddDto.getActualFeeRate();
    								valueAddDto.setActualFeeRate(discountRate.multiply(actualFeeRate));
			            			priceDiscountDtos.add(priceDiscountDto);
			            		}
			            		//代收货款
			            		if(PriceEntityConstants.PRICING_CODE_HK.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_HK.equals(valueAddDto.getPriceEntityCode())){
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,subType);
			            			//计算折扣后的费用(折上折)
			            			BigDecimal discountFee = valueAddDto.getCaculateFee().multiply(discountRate);
			            			valueAddDto.setCaculateFee(discountFee);
			            			//折扣费率
    								BigDecimal actualFeeRate = valueAddDto.getActualFeeRate();
    								valueAddDto.setActualFeeRate(discountRate.multiply(actualFeeRate));
			            			priceDiscountDtos.add(priceDiscountDto);
			            		}
			            		//综合信息服务费
			            		if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_QT.equals(valueAddDto.getPriceEntityCode())
			            				&& PriceEntityConstants.PRICING_CODE_ZHXX.equals(valueAddDto.getSubType())){
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,subType);
			            			//计算折扣后的费用(折上折)
			            			BigDecimal discountFee = valueAddDto.getCaculateFee().multiply(discountRate);
			            			valueAddDto.setCaculateFee(discountFee);
			            			//折扣费率
    								BigDecimal actualFeeRate = valueAddDto.getActualFeeRate();
    								valueAddDto.setActualFeeRate(discountRate.multiply(actualFeeRate));
			            			priceDiscountDtos.add(priceDiscountDto);
			            		}			            		
			            		
			        		}
			    		}    		
			    	}
			    	//将折扣信息添加到费用对象中
			    	valueAddDto.setDiscountPrograms(priceDiscountDtos);
			    	//将费用对象添加费用集合中
			    	valueAddDtos.add(valueAddDto);
				}			
			}
			//返回费用集合
	    	return valueAddDtos;
		}else{
			//不计算市场活动折扣则直接返回
			return discountResultList;
		}
		
	}
	
	/**
	 * 快递处理运费CRM营销活动处理
	 * @创建时间 2014-4-21 下午1:07:09   
	 * @创建人： WangQianJin
	 */
	private List<ProductPriceDto> crmActiveDiscountProcessFRTExpress(QueryBillCacilateDto queryBillCacilateDto,List<ProductPriceDto> discountResultList){
		//获取运费
		if(CollectionUtils.isNotEmpty(discountResultList)){
			for (int i = 0; i < discountResultList.size(); i++) { 
				ProductPriceDto price=discountResultList.get(i);
				if(price!=null && PriceEntityConstants.PRICING_CODE_FRT.equals(price.getPriceEntityCode())){
					queryBillCacilateDto.setTransportFee(price.getCaculateFee());
					break;
				}
			}
		}
		//校验是否可以享有市场营销活动
		if(queryBillCacilateDto.isPda()){
			PriceUtil.validateActiveDiscountPda(queryBillCacilateDto, markActivitiesService);
		}else{
			PriceUtil.validateActiveDiscount(queryBillCacilateDto,customerService,markActivitiesService);	
		}		
		/**
		 * 判断是否计算市场活动折扣		
		 */
		if(queryBillCacilateDto.isCalActiveDiscount()){			
			List<ProductPriceDto> productPriceDtos = new ArrayList<ProductPriceDto>();
			if(CollectionUtils.isNotEmpty(discountResultList)) {			
				for (int i = 0; i < discountResultList.size(); i++) {  
					//费用对象
					ProductPriceDto productPriceDto =  discountResultList.get(i);  
					//折扣列表
					List<PriceDiscountDto> priceDiscountDtos = productPriceDto.getDiscountPrograms();
					if(priceDiscountDtos==null){
						priceDiscountDtos=new ArrayList<PriceDiscountDto>();
					}
					//市场营销活动DTO
					MarkActivitiesQueryConditionDto activeDto=queryBillCacilateDto.getActiveDto();
					//计算出的价格
            		BigDecimal caculateFee=productPriceDto.getCaculateFee();
            		if(CollectionUtils.isNotEmpty(priceDiscountDtos)){
            			PriceDiscountDto dis=priceDiscountDtos.get(0);
            			if(dis!=null && dis.getOriginnalCost()!=null){
            				//获取原始费用
            				caculateFee=dis.getOriginnalCost();
            			}
            		}
            		//ID
            		String id=productPriceDto.getId();
					//获取营销活动折扣
			    	if(activeDto!=null && CollectionUtils.isNotEmpty(activeDto.getOptionList())){			    			    		
			    		for(MarkOptionsEntity entity:activeDto.getOptionList()){			    			
			        		if(entity!=null && entity.getName()!=null && entity.getValue()!=null){        			
			            		//获取折扣类型(运费、保费等)
			            		String discountType=entity.getName();
			            		//获取折扣值
			            		BigDecimal discountRate=entity.getValue();			            		
			            		//营销活动折扣相应的CRM_ID
			            		BigDecimal crmId=entity.getCrmId();			            		
		            			//运费
			            		if(PriceEntityConstants.PRICING_CODE_FRT.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_FRT.equals(productPriceDto.getPriceEntityCode())){		            			
			            			//折扣实体
				            		PriceDiscountDto priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,null);
				            		/**
				            		 * DMANA-3936 快递运费折扣运费调整
				            		 * 添加开单时间  更改单的时候用来判断是否执行最新的代码
				            		 */
				            		if(queryBillCacilateDto.getReceiveDate()!=null){
				            			priceDiscountDto.setReceiveDate(queryBillCacilateDto.getReceiveDate());
				            		}
				            		//营销活动折扣后的费用与FOSS折扣后的费用对比，取费用小的
				            		doCompareDiscountFRT(productPriceDto,priceDiscountDtos,discountRate,priceDiscountDto);			            			
			            		}			            				            				            		
			        		}
			    		}    		
			    	}
			    	//将折扣信息添加到费用对象中
			    	productPriceDto.setDiscountPrograms(priceDiscountDtos);
			    	//将费用对象添加费用集合中
			    	productPriceDtos.add(productPriceDto);
				}			
			}
			
			//返回费用集合
	    	return productPriceDtos;
		}else{
			//不计算市场活动折扣则直接返回
			return discountResultList;
		}		
	}
	
	/**
	 * 比较FOSS与CRM的运费折扣信息进行处理
	 * @创建时间 2014-4-25 下午1:11:09   
	 * @创建人： WangQianJin
	 */
	private boolean doCompareDiscountFRT(ProductPriceDto productPriceDto,List<PriceDiscountDto> priceDiscountDtos,
			BigDecimal discountRate,PriceDiscountDto priceDiscountDto){
		//是否享有活动折扣
		boolean isHaveActDis=false;
		//FOSS原有折扣后的费用
		BigDecimal fossDiscountFee = productPriceDto.getCaculateFee();
		//营销活动折扣后的费用
		BigDecimal crmDiscountFee=null;				            		
		//判断是否有折扣
		if(CollectionUtils.isNotEmpty(priceDiscountDtos)){	
			//有折扣
			PriceDiscountDto fossDiscount=priceDiscountDtos.get(0);
			if(fossDiscount!=null && fossDiscount.getOriginnalCost()!=null){		            						
    			crmDiscountFee = fossDiscount.getOriginnalCost().multiply(discountRate);    			
			}
		}else{
			//无折扣
			crmDiscountFee = fossDiscountFee.multiply(discountRate);
		}	
		//判断是否需要比较折扣
		if(fossDiscountFee!=null && crmDiscountFee!=null){
			//如果CRM折扣后的金额小于FOSS的，则取CRM的折扣金额(营销活动折扣后的费用与FOSS折扣后的费用对比，取费用小的)
			if(crmDiscountFee.doubleValue()<=fossDiscountFee.doubleValue()){
				productPriceDto.setCaculateFee(crmDiscountFee);				
				//原有费率
				BigDecimal initFeeRate = productPriceDto.getInitFeeRate();
				if(initFeeRate!=null){
					productPriceDto.setActualFeeRate(discountRate.multiply(initFeeRate));	
				}else{
					productPriceDto.setActualFeeRate(discountRate.multiply(productPriceDto.getActualFeeRate()));
				}	
				/**
			     *@author YANGKANG
			     *DMANA-3936  快递运费折扣率调整 
			     *打折之后的费率不能小于配置的最低费率
			     *需要重新计算运费和优惠金额
			     */
				 //获取配置的开始运费费率打折限制的时间
			     String configMinFeeRateStartDate = configurationParamsService.queryConfValueByCode(DiscountOrgConstants.EXPRESS_MIN_FEERATE_START_DATE);
			     Date startTime = null;
				if (StringUtils.isNotEmpty(configMinFeeRateStartDate)) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					// 指定校验的开始时间
					try {
						startTime = sdf.parse(configMinFeeRateStartDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}

				}
				//如果开单时间在配置的校验开始时间之后，则进行校验
				if(startTime!=null 
						&& priceDiscountDto.getReceiveDate()!=null 
						&& priceDiscountDto.getReceiveDate().after(startTime)){
					limitDiscountFeeRateCompare(productPriceDto,priceDiscountDto);
				}
				//删除原有折扣
				if(CollectionUtils.isNotEmpty(priceDiscountDtos)){
					//从折扣列表删除FOSS的折扣
					priceDiscountDtos.remove(0);
				}				
				//从折扣列表添加CRM的折扣
				priceDiscountDtos.add(priceDiscountDto);
				isHaveActDis=true;
			}
		}
		return isHaveActDis;
	}
	
	/**
	 * 快递处理增值服务费CRM营销活动处理
	 * @创建时间 2014-4-21 下午1:07:09   
	 * @创建人： WangQianJin
	 */
	private List<ValueAddDto> crmActiveDiscountProcessValueAddExpress(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto,List<ValueAddDto> discountResultList){
		//校验是否可以享有市场营销活动		
		if(queryBillCacilateValueAddDto.isPda()){
			PriceUtil.validateActiveDiscountPda(queryBillCacilateValueAddDto, markActivitiesService);
		}else{
			PriceUtil.validateActiveDiscount(queryBillCacilateValueAddDto,customerService,markActivitiesService);	
		}		
		/**
		 * 判断是否计算市场活动折扣		
		 */
		if(queryBillCacilateValueAddDto.isCalActiveDiscount()){						
			List<ValueAddDto> valueAddDtos = new ArrayList<ValueAddDto>();
			if(CollectionUtils.isNotEmpty(discountResultList)) {			
				for (int i = 0; i < discountResultList.size(); i++) {  
					//费用对象
					ValueAddDto valueAddDto =  discountResultList.get(i);  
					//折扣列表
					List<PriceDiscountDto> priceDiscountDtos = valueAddDto.getDiscountPrograms();
					if(priceDiscountDtos==null){
						priceDiscountDtos=new ArrayList<PriceDiscountDto>();
					}
					//市场营销活动DTO
					MarkActivitiesQueryConditionDto activeDto=queryBillCacilateValueAddDto.getActiveDto();
					//计算出的价格
            		BigDecimal caculateFee=valueAddDto.getCaculateFee();
            		if(CollectionUtils.isNotEmpty(priceDiscountDtos)){
            			PriceDiscountDto dis=priceDiscountDtos.get(0);
            			if(dis!=null && dis.getOriginnalCost()!=null){
            				//获取原始费用
            				caculateFee=dis.getOriginnalCost();
            			}
            		}
            		//ID
            		String id=valueAddDto.getId();
					//获取营销活动折扣
			    	if(activeDto!=null && CollectionUtils.isNotEmpty(activeDto.getOptionList())){			    			    		
			    		for(MarkOptionsEntity entity:activeDto.getOptionList()){			    			
			        		if(entity!=null && entity.getName()!=null && entity.getValue()!=null){        			
			            		//获取折扣类型(运费、保费等)
			            		String discountType=entity.getName();
			            		//获取折扣值
			            		BigDecimal discountRate=entity.getValue();
			            		//折扣实体
			            		PriceDiscountDto priceDiscountDto=null;	
			            		//营销活动折扣相应的CRM_ID
			            		BigDecimal crmId=entity.getCrmId();	
			            		//费用子类型
			            		String subType=valueAddDto.getSubType();
		            			//接货费
			            		if(PriceEntityConstants.PRICING_CODE_JH.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_JH.equals(valueAddDto.getPriceEntityCode())){
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,subType);
			            			//营销活动折扣后的费用与FOSS折扣后的费用对比，取费用小的
			            			doCompareDiscountValueAdd(valueAddDto,priceDiscountDtos,discountRate,priceDiscountDto);
			            		}
			            		//送货费
			            		if(PriceEntityConstants.PRICING_CODE_SH.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_SH.equals(valueAddDto.getPriceEntityCode())){
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,subType);
			            			//营销活动折扣后的费用与FOSS折扣后的费用对比，取费用小的
			            			doCompareDiscountValueAdd(valueAddDto,priceDiscountDtos,discountRate,priceDiscountDto);
			            		}
			            		//保价费
			            		if(PriceEntityConstants.PRICING_CODE_BF.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_BF.equals(valueAddDto.getPriceEntityCode())){
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,subType);
			            			//营销活动折扣后的费用与FOSS折扣后的费用对比，取费用小的
			            			doCompareDiscountValueAdd(valueAddDto,priceDiscountDtos,discountRate,priceDiscountDto);
			            		}
			            		//包装费(快递打折的是“快递包装费”)
			            		if(PriceEntityConstants.PRICING_CODE_BZ.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_QT.equals(valueAddDto.getPriceEntityCode())
			            				&& PriceEntityConstants.PRICING_CODE_KDBZF.equals(valueAddDto.getSubType())){
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,subType);
			            			//营销活动折扣后的费用与FOSS折扣后的费用对比，取费用小的
			            			doCompareDiscountValueAdd(valueAddDto,priceDiscountDtos,discountRate,priceDiscountDto);
			            		}
			            		//代收货款
			            		if(PriceEntityConstants.PRICING_CODE_HK.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_HK.equals(valueAddDto.getPriceEntityCode())){
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,subType);
			            			//营销活动折扣后的费用与FOSS折扣后的费用对比，取费用小的
			            			doCompareDiscountValueAdd(valueAddDto,priceDiscountDtos,discountRate,priceDiscountDto);
			            		}
			            		//综合信息服务费
			            		if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(discountType) 
			            				&& PriceEntityConstants.PRICING_CODE_QT.equals(valueAddDto.getPriceEntityCode())
			            				&& PriceEntityConstants.PRICING_CODE_ZHXX.equals(valueAddDto.getSubType())){
			            			priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,subType);
			            			//营销活动折扣后的费用与FOSS折扣后的费用对比，取费用小的
			            			doCompareDiscountValueAdd(valueAddDto,priceDiscountDtos,discountRate,priceDiscountDto);
			            		}         		
			            		
			        		}
			    		}    		
			    	}
			    	//将折扣信息添加到费用对象中
			    	valueAddDto.setDiscountPrograms(priceDiscountDtos);
			    	//将费用对象添加费用集合中
			    	valueAddDtos.add(valueAddDto);
				}			
			}
						
			//返回费用集合
	    	return valueAddDtos;
		}else{
			//不计算市场活动折扣则直接返回
			return discountResultList;
		}
		
	}
	
	/**
	 * 比较FOSS与CRM的运费折扣信息进行处理
	 * @创建时间 2014-4-25 下午1:11:09   
	 * @创建人： WangQianJin
	 */
	private boolean doCompareDiscountValueAdd(ValueAddDto valueAddDto,List<PriceDiscountDto> priceDiscountDtos,
			BigDecimal discountRate,PriceDiscountDto priceDiscountDto){
		//是否享有活动折扣
		boolean isHaveActDis=false;
		//FOSS原有折扣后的费用
		BigDecimal fossDiscountFee = valueAddDto.getCaculateFee();		
		//营销活动折扣后的费用
		BigDecimal crmDiscountFee=null;				            		
		//判断是否有折扣
		if(CollectionUtils.isNotEmpty(priceDiscountDtos)){	
			//有折扣
			PriceDiscountDto fossDiscount=priceDiscountDtos.get(0);
			if(fossDiscount!=null && fossDiscount.getOriginnalCost()!=null){		            						
    			crmDiscountFee = fossDiscount.getOriginnalCost().multiply(discountRate);    			
			}
		}else{
			//无折扣
			crmDiscountFee = fossDiscountFee.multiply(discountRate);			
		}
		//判断是否需要对比折扣
		if(fossDiscountFee!=null && crmDiscountFee!=null){
			//如果CRM折扣后的金额小于等于FOSS的，则取CRM的折扣金额(营销活动折扣后的费用与FOSS折扣后的费用对比，取费用小的)
			if(crmDiscountFee.doubleValue()<=fossDiscountFee.doubleValue()){
				valueAddDto.setCaculateFee(crmDiscountFee);				
				//原有费率
				BigDecimal initFeeRate = valueAddDto.getInitFeeRate();
				if(initFeeRate!=null){
					valueAddDto.setActualFeeRate(discountRate.multiply(initFeeRate));
				}else{
					valueAddDto.setActualFeeRate(discountRate.multiply(valueAddDto.getActualFeeRate()));
				}	
				//删除原有折扣
				if(CollectionUtils.isNotEmpty(priceDiscountDtos)){
					//从折扣列表删除FOSS的折扣
					priceDiscountDtos.remove(0);
				}				
				//从折扣列表添加CRM的折扣
				priceDiscountDtos.add(priceDiscountDto);
				isHaveActDis=true;
			}
		}
		return isHaveActDis;
	}
	
	/**
	 * 运费折扣计算.
	 * @param originalOrgCode the original org code
	 * @param destinationOrgCode the destination org code
	 * @param receiveDate the receive date
	 * @param weight the weight
	 * @param volume the volume
	 * @param originalId the original id
	 * @param destinationId the destination id
	 * @param caculateresultList the caculateresult list
	 * @param queryBillCacilateDto the query bill cacilate dto
	 * @return the list
	 * @author zhangdongping
	 * @date 2012-12-25 下午3:32:38
	 * @see
	 * 
	 * 计算规则：
	 * 
	 * 折扣根据价格计算的方式（体积、重量）来选择适用哪种方式的折扣。
	 * 
	 * 例如，如果价格以体积进行计算，则折扣也需要以体积折扣计算。同时，再选择直接费用折扣 计算（如果有的情况下）。
	 * 
	 * 比较二者打折后，选取优惠幅度更高的作为最终折扣。
	 * 
	 * 注意，在普通客户合同、渠道、产品折扣的情况下，最终计算出的价格不能低于价格最低一票。
	 */
	@Override
	public List<ProductPriceDto> doExpressFRTDiscount(String originalOrgCode,
		String destinationOrgCode, Date receiveDate, BigDecimal weight,
		BigDecimal volume, String originalId, String destinationId,
		List<ProductPriceDto> caculateresultList,QueryBillCacilateDto queryBillCacilateDto,
		BigDecimal firstDiscountRate,BigDecimal continueHeavyDiscount,BigDecimal lowestSecondDiscountFee) {
		
	    List<ProductPriceDto> discountResultList = new ArrayList<ProductPriceDto>();
	    //获取折扣优先级
	    List<DiscountPriorityEntity> discountPriorityEntities = discountPriorityService.getDiscountPriorityByCache();
	    if(CollectionUtils.isNotEmpty(caculateresultList)) {
	    	boolean flag = false;
	    	for (int i = 0; i < caculateresultList.size(); i++) {
	    		flag = false;
	    		ProductPriceDto productPriceDto = caculateresultList.get(i);
	    		//是否按照优先级处理
	    		if(CollectionUtils.isNotEmpty(discountPriorityEntities)) {
	    			DiscountParmDto discountParmDto = new DiscountParmDto();
	    		    //出发部门
	    		    OrgAdministrativeInfoEntity deptOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(originalOrgCode);
	    		    //到达部门
	    		    OrgAdministrativeInfoEntity arrvOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(destinationOrgCode);
	    		    //匹配出发、到达折扣的城市信息
	    		    if(deptOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(deptOrgAdministrativeInfoEntity.getCityCode())) {
	    		    	 discountParmDto.setDeptCityCode(deptOrgAdministrativeInfoEntity.getCityCode());
	    		    }
	    		    if(arrvOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(arrvOrgAdministrativeInfoEntity.getCityCode())) {
	    		    	discountParmDto.setArrvCityCode(arrvOrgAdministrativeInfoEntity.getCityCode());
	    		    }
	    		    //依次设置折扣信息的出发区域、到达区域、始发部门、到达部门、计价条目信息、
	    		    //产品、货物、业务日期、重货、轻货、原始费用、计费类别
	    		    discountParmDto.setCriteriaDetailId(productPriceDto.getId());
	    		    discountParmDto.setDeptRegionId(originalId);
	    		    discountParmDto.setArrvRegionId(destinationId);
	    		    discountParmDto.setOriginalOrgCode(originalOrgCode);
	    		    discountParmDto.setDestinationOrgCode(destinationOrgCode);
	    		    discountParmDto.setPricingEntryCode(productPriceDto.getPriceEntityCode());
	    		    discountParmDto.setPricingEntryName(productPriceDto.getPriceEntityName());
	    		    discountParmDto.setProductCode(productPriceDto.getProductCode());
	    		    discountParmDto.setGoodsTypeCode(productPriceDto.getGoodsTypeCode());
	    		    discountParmDto.setReceiveDate(receiveDate);
	    		    discountParmDto.setWeight(weight);
	    		    discountParmDto.setVolume(volume);
	    		    //计算后的价格
	    		    discountParmDto.setOriginnalCost(productPriceDto.getCaculateFee());
	    		    discountParmDto.setCustomCode(queryBillCacilateDto.getCustomerCode());
					discountParmDto.setIndustryCode(queryBillCacilateDto.getIndustrulCode());
					discountParmDto.setMinFee(productPriceDto.getMinFee());
					discountParmDto.setMaxFee(productPriceDto.getMaxFee());
					//计费类型 体积 、重量、费用
					discountParmDto.setCaculateType(productPriceDto.getCaculateType());
					discountParmDto.setSaleChannelCode(queryBillCacilateDto.getChannelCode());
	    		    DiscountResultDto discountResult=null;
	    		    //对于合同客户优先打折计算
	    			for (int j = 0; j < discountPriorityEntities.size(); j++) {
	    				DiscountPriorityEntity entity = discountPriorityEntities.get(j);
	    				if(entity != null && entity.getCode() != null) {
	    					if(StringUtil.equals(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT, entity.getCode())) {
    							DiscountTypeInterface discountExe = discountTypeFactory.getDiscountTypeImpl(entity.getCode());
        						if(discountExe != null) {
        							//获取客户折扣操作类(快递)
        							discountResult = discountExe.doExpressDiscount(discountParmDto);
        							//折扣存在,切折扣率不等于1
        							if(discountResult != null ) {
        								boolean isExpress = productService.onlineDetermineIsExpressByProductCode(discountParmDto.getProductCode(), discountParmDto.getReceiveDate());
        								//计算折扣
        								DiscountResultDto discountResultDto = PriceUtil.calculateCustomDiscountClientData(discountResult, discountParmDto, isExpress);								
        								//折扣费率
	    								BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
	    								//保留原有费率
        								productPriceDto.setInitFeeRate(actualFeeRate);
        								/**
        								 * fancy
        								 * 重新计算运费折扣打折信息
        								 */
        								calculateDiscountFeeRate(discountResultDto,productPriceDto,firstDiscountRate,continueHeavyDiscount,lowestSecondDiscountFee);
    								    		  
        								//保留折扣相关信息
        								productPriceDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
        								discountResultList.add(productPriceDto);
    									flag = true;
    									break;
        							} else {
        								continue;
        							}
        						} else {
        							continue;
        						}
    						} else {
    							DiscountTypeInterface discountExe = discountTypeFactory.getDiscountTypeImpl(entity.getCode());
    	    					if(discountExe != null) {
    	    						try {
    	    							//获取渠道或是产品折扣操作类
    	    							discountResult =discountExe.doExpressDiscount(discountParmDto);
    	    						} catch (Exception e) {
    	    							continue;
    	    						}
    	    						//折扣存在,切折扣率不等于100
    	    						if(discountResult!=null) {
    	    							//计算折扣
    	    							DiscountResultDto discountResultDto = PriceUtil.calculatePriceDiscountClientData(discountResult, discountParmDto);
    	    					
    	    							

    	    							if(discountResultDto != null && discountResultDto.getId() != null) {
       	    						     	//折扣费率
    	    			                    BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
    	    			                    //保留原有费率
    	    			                    productPriceDto.setInitFeeRate(actualFeeRate);
    	    			                    
    	    			                    /**
            								 * fancy
            								 * 重新计算运费折扣打折信息
            								 */
            								calculateDiscountFeeRate(discountResultDto,productPriceDto,firstDiscountRate,continueHeavyDiscount,lowestSecondDiscountFee);
    	    								//保留折扣相关信息
    	    								productPriceDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
    	    								discountResultList.add(productPriceDto);
    	    								flag = true;
    	    								break;
    	    							} else {
    	    								continue;
    	    							}
    	    						} else {
    	    							continue;
    	    						}
    	    					} else {
    	    						continue;
    	    					}
    						}
	    				} else {
	    					continue;
	    				}
	    			}
	    			if(!flag) {
		    			discountResultList.add(productPriceDto);
		    		}
	    		} else {
	    			discountResultList.add(productPriceDto);
	    		}
	    	} 
	    }
    	
	    return discountResultList;
	    /**  BillCaculateService 是开单计算价格、增值服务、折扣、优惠活动的实现类
	     * 
		 *  计算运费分别为：FRT
		 *  
		 *  汽运  - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 0
		 *  
		 *  空运 - t_srv_pice_plan价格表  字段 TRANSPORT_FLAG = 1
		 *  
		 *  
		 *  计算增值服务：  VAS
		 *  
		 *  保费 - BF
		 *  
		 *  代收货款 - HK
		 *  
		 *  送货费 - SH
		 *  
		 *  接货费 - JH
		 *  
		 *  签收回单 -QS
		 *  
		 *  保管费 - CCF
		 *  
		 *  综合信息费 -ZHXX
		 *  
		 *  燃油附加费 - RYFJ
		 *  
		 *  中转费 - ZZ
		 *  
		 *  接货差额补差 -JHCEBC
		 *  
		 *  电子优惠券 - DZYHQ
		 *  
		 *  其他费用 -QT
		 *  
		 *  包装费 -BZ
		 *  
		 *  送货上楼费 -SHSL
		 *  
		 *  超远派送费 -CY
		 *  
		 *  送货进仓费 -SHJCF
		 *  
		 *  更改费 -GGF
		 *  
		 *  以上服务相关代码进行不同维度计算。
		 *  
		 *  可支绑定如产品、区域 等等进行价格、折扣、优惠计算
		 */
	}
	
	/**
	 * 即日退增值服务折扣计算.
	 *
	 * @param originalOrgCode the original org code
	 * @param destinationOrgCode the destination org code
	 * @param receiveDate the receive date
	 * @param weight the weight
	 * @param volume the volume
	 * @param originalId the original id
	 * @param destinationId the destination id
	 * @param caculateresultList the caculateresult list
	 * @param queryBillCacilateDto the query bill cacilate dto
	 * @return the list
	 * @author zhangdongping
	 * @date 2012-12-25 下午3:32:38
	 * @see
	 * 
	 * 计算规则：
	 * 
	 * 折扣根据价格计算的方式（体积、重量）来选择适用哪种方式的折扣。
	 * 
	 * 例如，如果价格以体积进行计算，则折扣也需要以体积折扣计算。同时，再选择直接费用折扣 计算（如果有的情况下）。
	 * 
	 * 比较二者打折后，选取优惠幅度更高的作为最终折扣。
	 * 
	 * 注意，在普通客户合同、渠道、产品折扣的情况下，最终计算出的价格不能低于价格最低一票。
	 */
	@Override
	public List<ValueAddDto> doZZFRTDiscount(String originalOrgCode,
		String destinationOrgCode, Date receiveDate, BigDecimal weight,
		BigDecimal volume, String originalId, String destinationId,
		List<ValueAddDto> caculateresultList,QueryBillCacilateValueAddDto queryBillCacilateDto) {
		
	    List<ValueAddDto> discountResultList = new ArrayList<ValueAddDto>();
	    //获取折扣优先级
	    List<DiscountPriorityEntity> discountPriorityEntities = discountPriorityService.getDiscountPriorityByCache();
	    if(CollectionUtils.isNotEmpty(caculateresultList)) {
	    	boolean flag = false;
	    	for (int i = 0; i < caculateresultList.size(); i++) {
	    		flag = false;
	    		ValueAddDto valueAddDto = caculateresultList.get(i);
	    		//是否按照优先级处理
	    		if(CollectionUtils.isNotEmpty(discountPriorityEntities)) {
	    			DiscountParmDto discountParmDto = new DiscountParmDto();
	    		    //出发部门
	    		    OrgAdministrativeInfoEntity deptOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(originalOrgCode);
	    		    //到达部门
	    		    OrgAdministrativeInfoEntity arrvOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(destinationOrgCode);
	    		    //匹配出发、到达折扣的城市信息
	    		    if(deptOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(deptOrgAdministrativeInfoEntity.getCityCode())) {
	    		    	 discountParmDto.setDeptCityCode(deptOrgAdministrativeInfoEntity.getCityCode());
	    		    }
	    		    if(arrvOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(arrvOrgAdministrativeInfoEntity.getCityCode())) {
	    		    	discountParmDto.setArrvCityCode(arrvOrgAdministrativeInfoEntity.getCityCode());
	    		    }
	    		    //依次设置折扣信息的出发区域、到达区域、始发部门、到达部门、计价条目信息、
	    		    //产品、货物、业务日期、重货、轻货、原始费用、计费类别
	    		    discountParmDto.setCriteriaDetailId(valueAddDto.getId());
	    		    discountParmDto.setDeptRegionId(originalId);
	    		    discountParmDto.setArrvRegionId(destinationId);
	    		    discountParmDto.setOriginalOrgCode(originalOrgCode);
	    		    discountParmDto.setDestinationOrgCode(destinationOrgCode);
	    		    discountParmDto.setPricingEntryCode(valueAddDto.getPriceEntityCode());
	    		    discountParmDto.setPricingEntryName(valueAddDto.getPriceEntityName());
	    		    discountParmDto.setProductCode(valueAddDto.getProductCode());
	    		    discountParmDto.setGoodsTypeCode(valueAddDto.getGoodsTypeCode());
	    		    discountParmDto.setReceiveDate(receiveDate);
	    		    discountParmDto.setWeight(weight);
	    		    discountParmDto.setVolume(volume);
	    		    //计算后的价格
	    		    discountParmDto.setOriginnalCost(valueAddDto.getCaculateFee());
	    		    discountParmDto.setCustomCode(queryBillCacilateDto.getCustomerCode());
					discountParmDto.setIndustryCode(queryBillCacilateDto.getIndustrulCode());
					discountParmDto.setMinFee(valueAddDto.getMinFee());
					discountParmDto.setMaxFee(valueAddDto.getMaxFee());

	    		    //菜鸟折扣条件 是否返单折扣、返单号发货人客户编码、返单号 
	    		    discountParmDto.setIsCainiao(queryBillCacilateDto.getIsCainiao());
	    		    discountParmDto.setOldreceiveCustomerCode(queryBillCacilateDto.getOldreceiveCustomerCode());
	    		    discountParmDto.setReturnWaybillNo(queryBillCacilateDto.getReturnWaybillNo());
	    		    discountParmDto.setReturnInsuranceFee(queryBillCacilateDto.getReturnInsuranceFee());
	    		    discountParmDto.setReturnTransportFee(queryBillCacilateDto.getReturnTransportFee());
	    		    discountParmDto.setReturnbilltime(queryBillCacilateDto.getReturnbilltime());
	    		    discountParmDto.setOriginalReceiveOrgCode(queryBillCacilateDto.getOriginalReceiveOrgCode());
	    		    
					//计费类型 体积 、重量、费用
					discountParmDto.setCaculateType(valueAddDto.getCaculateType());
					discountParmDto.setSaleChannelCode(queryBillCacilateDto.getChannelCode());
	    		    DiscountResultDto discountResult=null;
	    		    //对于合同客户优先打折计算
	    			for (int j = 0; j < discountPriorityEntities.size(); j++) {
	    				DiscountPriorityEntity entity = discountPriorityEntities.get(j);
	    				if(entity != null && entity.getCode() != null) {
	    					if(StringUtil.equals(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT, entity.getCode())) {
    							DiscountTypeInterface discountExe = discountTypeFactory.getDiscountTypeImpl(entity.getCode());
        						if(discountExe != null) {
        							
        								//获取客户折扣操作类(快递)
        								discountResult = discountExe.doExpressDiscount(discountParmDto);
        								if(discountResult != null ) {
        								if(discountResult.getOriginnalCost()!=null && discountParmDto!=null && discountParmDto.getIsCainiao()){
        									discountParmDto.setOriginnalCost(discountResult.getOriginnalCost());
        								  }
        								}
        							//折扣存在,切折扣率不等于1
        							if(discountResult != null ) {
        								//boolean isExpress = productService.onlineDetermineIsExpressByProductCode(discountParmDto.getProductCode(), discountParmDto.getReceiveDate());
        								//计算折扣
        								DiscountResultDto discountResultDto = PriceUtil.calculateCustomDiscountClientData(discountResult, discountParmDto, true);
        								
        								if(discountResultDto == null){
        									continue;
        								}
        								//将折扣价格赋值原计算后价格
        								valueAddDto.setCaculateFee(discountResultDto.getDiscountValue());
        								if(PriceEntityConstants.PRICING_CODE_QS.equals(discountParmDto.getPricingEntryCode())){
        									valueAddDto.setFee(discountResultDto.getDiscountValue());
        								}
        								//将折扣价格赋值折扣价格
        								valueAddDto.setDiscountFee(discountResultDto.getDiscountValue());
        								//保留计价明细ID
        								valueAddDto.setId(discountResultDto.getId());
        								//折扣费率
	    								BigDecimal actualFeeRate = valueAddDto.getActualFeeRate();
	    								//保留原有费率
	    								valueAddDto.setInitFeeRate(actualFeeRate);
        								//重新设置费率
	    								if(actualFeeRate!=null && discountResultDto != null && discountResultDto.getDiscountRate() != null){
	    									valueAddDto.setActualFeeRate(discountResultDto.getDiscountRate().multiply(actualFeeRate));
	    								}
        								//保留折扣相关信息
        								valueAddDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
        								discountResultList.add(valueAddDto);
    									flag = true;
    									break;
        							} else {
        								continue;
        							}
        						} else {
        							continue;
        						}
    						} 
	    				} else {
	    					continue;
	    				}
	    			}
	    			if(!flag) {
		    			discountResultList.add(valueAddDto);
		    		}
	    		} else {
	    			discountResultList.add(valueAddDto);
	    		}
	    	} 
	    }
    	
	    return discountResultList;
	    /**  BillCaculateService 是开单计算价格、增值服务、折扣、优惠活动的实现类
	     * 
		 *  计算运费分别为：FRT
		 *  
		 *  汽运  - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 0
		 *  
		 *  空运 - t_srv_pice_plan价格表  字段 TRANSPORT_FLAG = 1
		 *  
		 *  
		 *  计算增值服务：  VAS
		 *  
		 *  保费 - BF
		 *  
		 *  代收货款 - HK
		 *  
		 *  送货费 - SH
		 *  
		 *  接货费 - JH
		 *  
		 *  签收回单 -QS
		 *  
		 *  保管费 - CCF
		 *  
		 *  综合信息费 -ZHXX
		 *  
		 *  燃油附加费 - RYFJ
		 *  
		 *  中转费 - ZZ
		 *  
		 *  接货差额补差 -JHCEBC
		 *  
		 *  电子优惠券 - DZYHQ
		 *  
		 *  其他费用 -QT
		 *  
		 *  包装费 -BZ
		 *  
		 *  送货上楼费 -SHSL
		 *  
		 *  超远派送费 -CY
		 *  
		 *  送货进仓费 -SHJCF
		 *  
		 *  更改费 -GGF
		 *  
		 *  以上服务相关代码进行不同维度计算。
		 *  
		 *  可支绑定如产品、区域 等等进行价格、折扣、优惠计算
		 */
	}
		
	
	/**
	 * 获取整车的保费相关信息
	 * @param queryBillCacilateValueAddDto
	 * @return
	 * @throws BillCaculateServiceException
	 */
	@Override
	public ValueAddDto getProductPriceDtoOfWVHAndBF(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto)
			throws BillCaculateServiceException {
		// 如果是整车+保费
//		if(StringUtil.equals(queryBillCacilateValueAddDto.getProductCode(),
//		PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE)
//		&& StringUtil.equals(queryBillCacilateValueAddDto.getPricingEntryCode(),
//				PriceEntityConstants.PRICING_CODE_BF)){
//（FOSS20150818）RFOSS2015052602 保价阶梯费率---206860
		if(StringUtil.equals(queryBillCacilateValueAddDto.getPricingEntryCode(),
				PriceEntityConstants.PRICING_CODE_BF)){
			/* 
			 * 查询时使用searchValueAddPriceList原有逻辑，
			 * 计算费用时则用GUI端传过来的feeRate覆盖searchValueAddPriceList查询
			 * 到的feeRate
			 */
//			queryBillCacilateValueAddDto.setFeeRate(null);
			List<ValueAddDto> list =  this.searchValueAddPriceList(queryBillCacilateValueAddDto);
			if(CollectionUtils.isNotEmpty(list)){
				for(ValueAddDto dto : list){
					if(PriceEntityConstants.PRICING_CODE_BF.equals(dto.getPriceEntityCode())){
						return dto;
					}
				}
			}			
		}
		return null;
	}
	
	/**
	 * <p>计算增值服务费</p>.
	 *
	 * @param queryBillCacilateValueAddDto the query bill cacilate value add dto
	 * @return the list
	 * @throws BillCaculateServiceException the bill caculate service exception
	 * @author DP-Foss-YueHongJie
	 * @date 2012-10-29 下午3:41:05
	 * @see
	 * 
	 * 计算规则：
	 * 
	 * （1）查询基础增值服务费率进行价格计算，如果未查询到，则直接返回空。
	 * 
	 * （2）查询区域或是产品增值服务费率进行价格计算，区域优先于产品，即当查询到区域增值服务费率之后，将不再查询产品。
	 * 
	 * （3）如果符合增值优惠的条件，对基础增值服务计算出的价格进行打折。
	 * 
	 * （4）将折扣后的价格与以区域或是产品增值服务费率计算出的价格进行比较，选取优惠幅度最大的作为最终价格。
	 * 
	 * （5）在普通客户合同、渠道、产品折扣的情况下，最终计算出的价格不能低于价格最低一票。
	 */
	@Override
	public List<ValueAddDto> searchValueAddPriceList(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto)throws BillCaculateServiceException {
	    /**
	     *   1 根据始发部门code 获取始发区域ID 
	     *   
	     *   
	    *  2  根据到达部门code 获取到达区域ID 
	    *  
	    *  
	    *  3 查询各种增值服务的费率，计算费用
	    *  
	    *  
	    *  3.1 查询区域增值服务   
	    *  
	    *  
	    *  
	    *  3.2 查询产品增值服务 去掉第一步已经查到的增值服务信息
	    *  
	    *  
	    *  
	    *  3.3 查询基础增值服务 去掉第二步和第一步已经查到的增值服务
	    *  
	    *  
	    *  
	    *  
	    *  根据传入的重量，体积，费用等 计算结果
	     */
		log.debug("value add start calcuate>>"+new Date());
		if (queryBillCacilateValueAddDto == null) {
			return null;
		}
		
	    BigDecimal originnalCost = queryBillCacilateValueAddDto.getOriginnalCost();
	    //校验输入参数	 
	    PriceUtil.checkQueryBillCacilateValueAddDtoDate(queryBillCacilateValueAddDto); 

	    //field
	    String originalOrgCode = queryBillCacilateValueAddDto.getOriginalOrgCode();
	    String destinationOrgCode = queryBillCacilateValueAddDto.getDestinationOrgCode();
	    Date receiveDate = queryBillCacilateValueAddDto.getReceiveDate();
	    String productCode = queryBillCacilateValueAddDto.getProductCode();
	    //为了后面判断是否是精准大票经济件和精准大票标准件，将三级产品保留
	    String sonProductCode = productCode;
	    queryBillCacilateValueAddDto.setActive(FossConstants.ACTIVE);
	    
		if (StringUtil.isEmpty(queryBillCacilateValueAddDto.getGoodsTypeCode())) {
			queryBillCacilateValueAddDto
					.setGoodsTypeCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
		} else {
			queryBillCacilateValueAddDto
					.setGoodsTypeCode(queryBillCacilateValueAddDto
							.getGoodsTypeCode());
		}
		String goodsTypeCode = queryBillCacilateValueAddDto.getGoodsTypeCode();
	    //处理客户端以元为单位的原始费用化成分
	    if(originnalCost!=null)
	    {
		originnalCost= new BigDecimal(originnalCost.doubleValue() * PricingConstants.YUTOFEN );
		 queryBillCacilateValueAddDto.setOriginnalCost(originnalCost) ;
	    }
	    //保费特殊处理保费默认值问题
		if (null == originnalCost
				&& StringUtil.equalsIgnoreCase(queryBillCacilateValueAddDto.getPricingEntryCode(),
						PriceEntityConstants.PRICING_CODE_BF)) {
			ConfigurationParamsEntity entity = configurationParamsService.queryConfigurationParamsByOrgCode(
					DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
					ConfigurationParamsConstants.PKP_PARM__DEFAULT_INSURANCE_AMOUNT, originalOrgCode);
			if (entity != null) {
				String parmValue = entity.getConfValue();
				try {
					originnalCost = new BigDecimal(String.valueOf(new Double(
							parmValue) * PricingConstants.YUTOFEN));
					queryBillCacilateValueAddDto.setOriginnalCost(originnalCost);

				} catch (BillCaculateServiceException e) {
				    throw new BillCaculateServiceException("很抱歉，查询出发部门对应的默认保费时出现异常信息",e.getMessage());
				}
			}else{
			    throw new BillCaculateServiceException("很抱歉，因开单时你没有传入保费的声明价值费用,并且出发部门也没有对应的默认声明价值费用，我们不能为你计算出最后的保费");
			}
		}
		if(originnalCost==null){
		    originnalCost = new BigDecimal(PricingConstants.ZERO);
		}
		/* 出发部门 code 定位增值区域信息 */
		String originalId = null;
		originalId = queryBillCacilateValueAddDto.getDeptRegionId();
		if (StringUtil.isEmpty(originalId)) {
			originalId = regionValueAddService.findRegionOrgByDeptNo(
					originalOrgCode, receiveDate, null,
					PricingConstants.VALUEADD_REGION);
		}
		/* 到达部门 code 定位增值区域信息 */
		String destinationId = null;
		destinationId = queryBillCacilateValueAddDto.getArrvRegionId();
		if (StringUtil.isEmpty(destinationId)) {
			destinationId = regionValueAddService.findRegionOrgByDeptNo(
					destinationOrgCode, receiveDate, null,
					PricingConstants.VALUEADD_REGION);
		}
	    queryBillCacilateValueAddDto.setDeptRegionId(originalId);
	    queryBillCacilateValueAddDto.setArrvRegionId(destinationId);
	    //如果客户端没有长短途,则系统根据始发部门与到达部门CODE来定位
	    String longOrShort = queryBillCacilateValueAddDto.getLongOrShort();
		if (StringUtil.isEmpty(longOrShort) && StringUtil.isNotEmpty(destinationOrgCode)) {
			longOrShort = getLongOrShort(originalOrgCode,
					destinationOrgCode, productCode, receiveDate);/* 查询长短途 */
			if (StringUtil.isEmpty(longOrShort)) {
				queryBillCacilateValueAddDto.setLongOrShort(PricingConstants.ALL);
			} else {
				queryBillCacilateValueAddDto.setLongOrShort(longOrShort);
			}
		}
		
		//--Online-定价体系优化项目DJTWO-151--解决关联行业查询不到增值服务的问题
		QueryBillCacilateValueAddDto queryBillCacilateValueAddOfIndustryCodeDto = new QueryBillCacilateValueAddDto();
		//拷贝list，为了在使用默认的方式查询不到增值服务信息时，调用正确的QueryBillCacilateValueAddDto实体类
		if(queryBillCacilateValueAddDto != null) {
				BeanCopier copy = BeanCopier.create(queryBillCacilateValueAddDto.getClass(), queryBillCacilateValueAddOfIndustryCodeDto.getClass(), false);
				copy.copy(queryBillCacilateValueAddDto, queryBillCacilateValueAddOfIndustryCodeDto, null);
	    	}
		
		//根据客户端传入的三级产品得到二级产品
		ProductEntity productEntity = productService.getProductByCache(productCode, receiveDate);
		if (productEntity != null) {
			productCode = productEntity.getParentCode();
		}
		
	    /* 筛选计费规则下的计费明细 */
		Map<String, List<ResultProductPriceDto>> resultMap = null;
		//当增值计费类型为大件上楼和纸纤包装时，不采用新规则进行增值计算
		/**
		 * @需求：大件上楼优化需求
		 * @功能：大件上楼计费规则同定价优化项目
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-05-20下午14:07
		 */
		if(!(StringUtil.equalsIgnoreCase(queryBillCacilateValueAddDto.getPricingEntryCode(),
						PriceEntityConstants.PRICING_CODE_ZQBZ))){
			//先使用默认的方式查询增值服务信息，若查询的增值服务信息为空，则不再关联二级行业进行查询
			resultMap = popValueAddedService.siftValuationBillingRuleService(queryBillCacilateValueAddDto);
			if(resultMap.size()<=0){
				//不关联二级行业进行查询
				//--Online-定价体系优化项目DJTWO-151--解决关联行业查询不到增值服务的问题
				resultMap = popValueAddedService.siftValuationBillingRuleServiceNoIndustry(queryBillCacilateValueAddOfIndustryCodeDto);
			}
			//如果根据新逻辑找不到增值服务，则根据原有老逻辑查询  DJTWO-3	
			if(null==resultMap || resultMap.size()<=0){
				resultMap = pricingValueAddedService.siftValueAddRuleService(queryBillCacilateValueAddOfIndustryCodeDto);
			}
		}else{
			resultMap = pricingValueAddedService.siftValueAddRuleService(queryBillCacilateValueAddDto);
		}

		//操作map，对精准包裹进行优化
		if(resultMap!=null && resultMap.size()>0 &&
				StringUtil.equals(queryBillCacilateValueAddDto.getProductCode(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PCP)){
			Set<Map.Entry<String, List<ResultProductPriceDto>>> entrySet = resultMap.entrySet();
			for(Map.Entry<String, List<ResultProductPriceDto>> temp:entrySet){
				List<ResultProductPriceDto> resultProductPriceDtos = temp.getValue();
				if(CollectionUtils.isNotEmpty(resultProductPriceDtos)){
					ListIterator<ResultProductPriceDto> resultProductPriceDtoListIterator = resultProductPriceDtos.listIterator();
					while(resultProductPriceDtoListIterator.hasNext()){
						ResultProductPriceDto resultProductPriceDto = resultProductPriceDtoListIterator.next();
						//dp-foss 343617 zhaoyiqing 20160817 如果是精准包裹，则移除燃油附加费和综合信息费用
						if (StringUtil.equals(queryBillCacilateValueAddDto.getPricingEntryCode(), PriceEntityConstants.PRICING_CODE_QT)
								&& (StringUtil.equals(resultProductPriceDto.getSubType(), PriceEntityConstants.PRICING_CODE_ZHXX) || StringUtil.equals(resultProductPriceDto.getSubType(), PriceEntityConstants.PRICING_CODE_RYFJ))) {
							resultProductPriceDtoListIterator.remove();
						}
						//dp-foss 343617 zhaoyiqing 20160817 精准包裹去掉保费最低一票
						if(StringUtil.equals(queryBillCacilateValueAddDto.getPricingEntryCode(), PriceEntityConstants.PRICING_CODE_BF)){
							resultProductPriceDto.setMinFee(0l);
						}
					}
				}
			}
		}


		/**
		 * 客户增值方案
		 * dp-foss-dongjialing
		 * 225131
		 */
		List<ValueAddDto> resultCustomerList = null;
		List<ResultProductPriceDto> resultCustomerPriceDtoList = resultMap.get("customer");
		if(StringUtil.isBlank(queryBillCacilateValueAddDto.getCustomerCode())) {
			resultCustomerPriceDtoList = null;
		}
		if(CollectionUtils.isNotEmpty(resultCustomerPriceDtoList)) {
			BigDecimal weight = queryBillCacilateValueAddDto.getWeight();
			if (weight == null) {
				weight = BigDecimal.ZERO;
			}
			BigDecimal volume = queryBillCacilateValueAddDto.getVolume();
			if (volume == null) {
				volume = BigDecimal.ZERO;
			}
			
			BigDecimal kilom = queryBillCacilateValueAddDto.getKilom();
			if (kilom == null) {
			    kilom = BigDecimal.ZERO;
			}
			resultCustomerList = PriceUtil.calculateValueAddedServices(originnalCost, weight, volume,kilom,
					resultCustomerPriceDtoList, receiveDate, queryBillCacilateValueAddDto.getPricingEntryCode());
			//如果根据客户增值计算同一区间条数大于1则取第一条
			if(resultCustomerList != null && resultCustomerList.size()>1 && !PriceEntityConstants.PRICING_CODE_QT.equals(resultCustomerList.get(0).getPriceEntityCode())){
				ValueAddDto valueAddDto=resultCustomerList.get(0);
				List<ValueAddDto> resultListNew = new ArrayList<ValueAddDto>();
				resultListNew.add(valueAddDto);
				resultCustomerList=resultListNew;
			}else if(resultCustomerList != null &&resultCustomerList.size()>1 && PriceEntityConstants.PRICING_CODE_QT.equals(resultCustomerList.get(0).getPriceEntityCode())){
				List<ValueAddDto> resultOtherListTemp=new ArrayList<ValueAddDto>();
				for(int i=0;i<resultCustomerList.size()-1;i++){
					 for(int j=i+1;j<resultCustomerList.size();j++){
						 if(null!=resultCustomerList.get(i) && null!=resultCustomerList.get(j)){
						      if(resultCustomerList.get(i).getSubType().equals(resultCustomerList.get(j).getSubType())){
						    	  resultOtherListTemp.add(resultCustomerList.get(j));
						       }
						 }
					 }
				}
				resultCustomerList.removeAll(resultOtherListTemp);
			}
			
		}
		if (CollectionUtils.isNotEmpty(resultCustomerList)) {
			for (int loop = 0; loop < resultCustomerList.size(); loop++) { 
				ValueAddDto vo = resultCustomerList.get(loop);
				PriceEntity priceEntity = priceEntryService.getPriceEntryByCache(vo.getPriceEntityCode(), receiveDate);
				String priceEntryName = null;
				if (priceEntity != null && priceEntity.getName() != null) {
					priceEntryName = priceEntity.getName();
				}
				vo.setPriceEntityName(priceEntryName);
				if(StringUtils.equals(PriceEntityConstants.PRICING_CODE_QT, vo.getPriceEntityCode())
						|| StringUtils.equals(PriceEntityConstants.PRICING_CODE_BZ, vo.getPriceEntityCode())) {
					PriceEntity priceEntitySub = priceEntryService.getPriceEntryByCache(vo.getSubType(), receiveDate);
					if(priceEntitySub != null) {
						// 抓取计价条目编码与名称
						vo.setSubTypeName(priceEntitySub.getName());
					}
				}
			}
			return resultCustomerList;
		}
		List<ResultProductPriceDto> resultProductPriceDtoList = resultMap.get("base");
		if(queryBillCacilateValueAddDto.getIsPicWayBill()!=null && queryBillCacilateValueAddDto.getIsPicWayBill()){
			if(queryBillCacilateValueAddDto.getWeight()==null
					&& queryBillCacilateValueAddDto.getVolume()==null && StringUtil.equalsIgnoreCase(queryBillCacilateValueAddDto.getPricingEntryCode(),
					PriceEntityConstants.PRICING_CODE_QT)){
				//根据基础增值服务计算
				List<ValueAddDto> resultList = PriceUtil.calculateValueAddedServicesPic(resultProductPriceDtoList);  
				List<ValueAddDto> resultListReturn = new ArrayList<ValueAddDto>();
				Set<String> subTypeSet = new HashSet<String>();
				for(ValueAddDto result:resultList){
					if(result.getSubType()!=null){
						PriceEntity priceEntitySub = priceEntryService.getPriceEntryByCache(result.getSubType(), queryBillCacilateValueAddDto.getReceiveDate());
						if(priceEntitySub != null) {
							// 抓取计价条目编码与名称
							result.setSubTypeName(priceEntitySub.getName());
						}
					}
					int  beforeSize = subTypeSet.size();
					subTypeSet.add(result.getSubType());
					int  afterSize = subTypeSet.size();
					if(beforeSize!=afterSize){
						resultListReturn.add(result);
					}
					
				}
				return resultListReturn;
			}
		}
		List<ResultProductPriceDto> resultOtherProductPriceDtoList = resultMap.get("other");
		
		if (CollectionUtils.isEmpty(resultProductPriceDtoList) && CollectionUtils.isEmpty(resultOtherProductPriceDtoList)) {
			return null;
		}
		// 如果是整车计算保价费的话，需要使用GUI端传入的feeRate覆盖查询出来的feeRate
		//		if(StringUtil.equals(queryBillCacilateValueAddDto.getProductCode(),
		//		PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE)
		//		&& StringUtil.equals(queryBillCacilateValueAddDto.getPricingEntryCode(),
		//				PriceEntityConstants.PRICING_CODE_BF)){
		//（FOSS20150818）RFOSS2015052602 保价阶梯费率----206860
		if(StringUtil.equals(queryBillCacilateValueAddDto.getPricingEntryCode(),
				PriceEntityConstants.PRICING_CODE_BF)){
			if(queryBillCacilateValueAddDto.getFeeRate() != null
					&& queryBillCacilateValueAddDto.isCalDiscount()){
				coverFeeRateOfBF(resultProductPriceDtoList, queryBillCacilateValueAddDto.getFeeRate().setScale(FOUR,BigDecimal.ROUND_HALF_UP));
				coverFeeRateOfBF(resultOtherProductPriceDtoList, queryBillCacilateValueAddDto.getFeeRate().setScale(FOUR,BigDecimal.ROUND_HALF_UP));
			}			
		}
		
		BigDecimal weight = queryBillCacilateValueAddDto.getWeight();
		if (weight == null) {
			weight = BigDecimal.ZERO;
		}
		BigDecimal volume = queryBillCacilateValueAddDto.getVolume();
		if (volume == null) {
			volume = BigDecimal.ZERO;
		}
		
		BigDecimal kilom = queryBillCacilateValueAddDto.getKilom();
		if (kilom == null) {
		    kilom = BigDecimal.ZERO;
		}
		//根据基础增值服务计算
		List<ValueAddDto> resultList = PriceUtil.calculateValueAddedServices(originnalCost, weight, volume,kilom,
				resultProductPriceDtoList, receiveDate, queryBillCacilateValueAddDto.getPricingEntryCode());
		//如果根据基础增值计算同一区间条数大于1则取第一条
		if(resultList != null &&resultList.size()>1 && !PriceEntityConstants.PRICING_CODE_QT.equals(resultList.get(0).getPriceEntityCode())){
			ValueAddDto valueAddDto=resultList.get(0);
			List<ValueAddDto> resultListNew = new ArrayList<ValueAddDto>();
			resultListNew.add(valueAddDto);
			resultList=resultListNew;
		}else if(resultList != null &&resultList.size()>1 && PriceEntityConstants.PRICING_CODE_QT.equals(resultList.get(0).getPriceEntityCode())){
			List<ValueAddDto> resultListTemp=new ArrayList<ValueAddDto>();
			for(int i=0;i<resultList.size()-1;i++){
				 for(int j=i+1;j<resultList.size();j++){
					 if(null!=resultList.get(i) && null!=resultList.get(j)){
					    if(resultList.get(i).getSubType().equals(resultList.get(j).getSubType())){
						 resultListTemp.add(resultList.get(j));
					   }
				    }
				 }
			}
			resultList.removeAll(resultListTemp);
		}
		//根据区域或是产品增值服务计算
		List<ValueAddDto> resultOtherList = null;
		if(CollectionUtils.isNotEmpty(resultOtherProductPriceDtoList)) {
			resultOtherList = PriceUtil.calculateValueAddedServices(originnalCost, weight, volume,kilom,
					resultOtherProductPriceDtoList, receiveDate, queryBillCacilateValueAddDto.getPricingEntryCode());
			//如果根据基础增值计算同一区间条数大于1则取第一条
			if(resultOtherList != null && resultOtherList.size()>1 && !PriceEntityConstants.PRICING_CODE_QT.equals(resultOtherList.get(0).getPriceEntityCode())){
				ValueAddDto valueAddDto=resultOtherList.get(0);
				List<ValueAddDto> resultListNew = new ArrayList<ValueAddDto>();
				resultListNew.add(valueAddDto);
				resultOtherList=resultListNew;
			}else if(resultOtherList != null &&resultOtherList.size()>1 && PriceEntityConstants.PRICING_CODE_QT.equals(resultOtherList.get(0).getPriceEntityCode())){
				List<ValueAddDto> resultOtherListTemp=new ArrayList<ValueAddDto>();
				for(int i=0;i<resultOtherList.size()-1;i++){
					 for(int j=i+1;j<resultOtherList.size();j++){
						 if(null!=resultOtherList.get(i) && null!=resultOtherList.get(j)){
						      if(resultOtherList.get(i).getSubType().equals(resultOtherList.get(j).getSubType())){
						    	  resultOtherListTemp.add(resultOtherList.get(j));
						       }
						 }
					 }
				}
				resultOtherList.removeAll(resultOtherListTemp);
			}
		}
		if (CollectionUtils.isNotEmpty(resultOtherList)) {
			for (int loop = 0; loop < resultOtherList.size(); loop++) { 
				ValueAddDto vo = resultOtherList.get(loop);
				PriceEntity priceEntity = priceEntryService.getPriceEntryByCache(vo.getPriceEntityCode(), receiveDate);
				String priceEntryName = null;
				if (priceEntity != null && priceEntity.getName() != null) {
					priceEntryName = priceEntity.getName();
				}
				vo.setPriceEntityName(priceEntryName);
				if(StringUtils.equals(PriceEntityConstants.PRICING_CODE_QT, vo.getPriceEntityCode())
						|| StringUtils.equals(PriceEntityConstants.PRICING_CODE_BZ, vo.getPriceEntityCode())) {
					PriceEntity priceEntitySub = priceEntryService.getPriceEntryByCache(vo.getSubType(), receiveDate);
					if(priceEntitySub != null) {
						// 抓取计价条目编码与名称
						vo.setSubTypeName(priceEntitySub.getName());
					}
				}
			}
		}
		PriceEntity priceEntity = priceEntryService.getPriceEntryByCache(queryBillCacilateValueAddDto.getPricingEntryCode(), receiveDate);
		String priceEntryName = null;
		if (priceEntity != null && priceEntity.getName() != null) {
			priceEntryName = priceEntity.getName();
		}
		productEntity = productService.getProductByCache(
				sonProductCode, receiveDate);
		
		GoodsTypeEntity goodsTypeEntity = goodsTypeService
				.getGoodsTypeByCache(goodsTypeCode, receiveDate);

		if (CollectionUtils.isNotEmpty(resultList)) {
			for (int loop = 0; loop < resultList.size(); loop++) {
				ValueAddDto vo = resultList.get(loop);
				vo.setPriceEntityCode(queryBillCacilateValueAddDto
						.getPricingEntryCode());
				vo.setPriceEntityName(priceEntryName);
				if (null != productEntity) {
					vo.setProductCode(productEntity.getCode());
					vo.setProductName(productEntity.getName());
				}
				if (null != goodsTypeEntity) {
					vo.setGoodsTypeName(goodsTypeEntity.getName());
					vo.setGoodsTypeCode(goodsTypeEntity.getCode());
				}
		    
				if (PriceEntityConstants.PRICING_CODE_QT.equals(vo.getPriceEntityCode()) 	
					|| StringUtils.equals(PriceEntityConstants.PRICING_CODE_BZ, vo.getPriceEntityCode())) {
					priceEntity = priceEntryService.getPriceEntryByCache(vo.getSubType(), receiveDate);
					if(priceEntity != null) {
						// 抓取计价条目编码与名称
						vo.setSubTypeName(priceEntity.getName());
						
						// 抓取计价条目归集类别编码与归集类别名称
						PriceEntity blongPriceEntity = priceEntryService.getPriceEntryByCache(
								priceEntity.getBlongPricingCode(), receiveDate);
						if(blongPriceEntity != null) {
							vo.setBelongToPriceEntityCode(blongPriceEntity
									.getCode());// 归集类别CODE
							vo.setBelongToPriceEntityName(blongPriceEntity
									.getName());// 归集类别名称
						}
					}
				} 
			}
		}
		List<ValueAddDto> result = null;
		
		/**
		 * @author YANGKANG
		 * DMANA-7725  新产品门到门和场到场  价格和增值服务 不参与任何的折扣
		 * 添加产品类型限制条件  过滤新产品门到门和场到场 不进行折扣计算
		 */
		if (StringUtil.isNotBlank(destinationOrgCode)) {
			
			if(CollectionUtils.isNotEmpty(resultOtherList)) {
				result = resultOtherList;
			} else {
				result = resultList;
			}
			
			if(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(productCode)
					|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(productCode)){
				//新产品逻辑
				
				return result;
			}
			/**
			 * 内部发货不进行任何增值打折
			 * dp-foss-dongjialing
			 * 225131
			 */
			if((WaybillConstants.DELIVERY_PAY.equals(queryBillCacilateValueAddDto.getInternalDeliveryType())
					|| WaybillConstants.RECIVE_PAY.equals(queryBillCacilateValueAddDto.getInternalDeliveryType())
					) && StringUtil.isNotBlank(queryBillCacilateValueAddDto.getEmployeeNo())) {
				return result;
			}
			log.debug("value add end calcuate>>"+new Date());
			
			/**
			 * DMANA-5564
			 * @author  YANGKANG
 			 * 送货费折扣读取规则变更  
 			 * 1.如果存在区域或者产品的送货费增值服务，则对区域或者产品增值服务中的送货费进行优惠计算，计算之后不用与基础增值服务中的送货费比较
 			 * 2.如果不存在区域或者产品的送货费增值服务，则直接取基础增值服务中的送货费进行优惠计算
			 */
			if(PriceEntityConstants.PRICING_CODE_SH.equals(queryBillCacilateValueAddDto.getPricingEntryCode())){
					
				if(CollectionUtils.isNotEmpty(resultOtherList)){
						//增值优惠计算
						//采用新的增值折扣
						result = doValueAddDiscount(originalOrgCode, destinationOrgCode,
									receiveDate, originalId, destinationId, weight, volume,
									resultOtherList, queryBillCacilateValueAddDto);
								
					}else{
						//增值优惠计算
						//采用新的增值折扣
						result = doValueAddDiscount(originalOrgCode, destinationOrgCode,
									receiveDate, originalId, destinationId, weight, volume,
									resultList, queryBillCacilateValueAddDto);
								
					}
				
			}else{
				//如果类型为保费且标识符为true时不走折扣逻辑---206860
				if(PriceEntityConstants.PRICING_CODE_BF.equals(queryBillCacilateValueAddDto.getPricingEntryCode())
						&& queryBillCacilateValueAddDto.isCalDiscount()){
					//当区域产品基础都存在增值服务，判断前台费率范围与哪个等同,若都不等同，取基础
					if(CollectionUtils.isNotEmpty(resultOtherList) && CollectionUtils.isNotEmpty(resultList)){
						for (int i = 0; i < resultOtherList.size(); i++) {
							if(PriceEntityConstants.PRICING_CODE_BF.equals(resultOtherList.get(i).getPriceEntityCode())){
								if(queryBillCacilateValueAddDto.getMinFeeRate() != null
										&& queryBillCacilateValueAddDto.getMaxFeeRate() != null){
									if(resultOtherList.get(i).getMinFeeRate() != null 
											&& resultOtherList.get(i).getMaxFeeRate() != null){
										if(queryBillCacilateValueAddDto.getMinFeeRate().compareTo(resultOtherList.get(i).getMinFeeRate()) == 0
												&& queryBillCacilateValueAddDto.getMaxFeeRate().compareTo(resultOtherList.get(i).getMaxFeeRate()) == 0){
											result = resultOtherList;
										}else{
											result = resultList;
										}
									}else{
										result = resultList;
									}
								}
							}else{
								result = resultList;
							}
						}
					}else{
						if(CollectionUtils.isNotEmpty(resultOtherList)) {
							result = resultOtherList;
						} else {
							result = resultList;
						}
					}
					//为防止点击计算总运费折扣计算时出错.---206860
					queryBillCacilateValueAddDto.setCalDiscount(false);
				}else{
					//增值优惠计算
					List<ValueAddDto> list = doValueAddDiscount(originalOrgCode, destinationOrgCode,
								receiveDate, originalId, destinationId, weight, volume,
								resultList, queryBillCacilateValueAddDto);
					
					boolean flag = false;
					if(CollectionUtils.isNotEmpty(list)) {
						for (int i = 0; i < list.size(); i++) {
							ValueAddDto addDto = list.get(i);
							
							
							if(addDto.getDiscountFee() != null) {
								flag = true;
								break;
							}
						}
					}
					if(flag) {
						result =  compareDiscountAndRegionList(list, resultOtherList);
					} else {
						if(CollectionUtils.isNotEmpty(resultOtherList)) {
							result = resultOtherList;
						} else {
							result = list;
						}
					}
				}
			}
			
		
		}
		/**
		 * DEFECT-6503
		 * FOSS开单时（GUI）默认保价费查询维护值
		 * @author：218371-foss-zhaoyanjun
		 * @date:2015-01-04下午16:29
		 */
		else {
				result = resultList;
		}
			
		//从数据字典查询8-30日期
		ConfigurationParamsEntity entity = configurationParamsService.queryConfigurationParamsOneByCode(PricingConstants.OUTER_PRICE_DEFAULT_TIME);
		if(null!=entity){
			if(StringUtils.isNotEmpty(entity.getConfValue())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date outPriceDate = null;
				try {
					outPriceDate = sdf.parse(entity.getConfValue());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(outPriceDate != null && queryBillCacilateValueAddDto.getReceiveDate().after(outPriceDate)){
					if(CollectionUtils.isNotEmpty(result)){
						for (int i = 0; i < result.size(); i++) {
							//如果存在偏线中转费
							if(PriceEntityConstants.PRICING_CODE_PXZZF.equals(result.get(i).getSubType())){
								result.remove(i);
							}
						}
					}
				}
			}
		}
		
			/**
		     * 处理增值服务费CRM营销活动处理
		     */
			result=crmActiveDiscountProcessValueAdd(queryBillCacilateValueAddDto,result);



		return result;
		/**  BillCaculateService 是开单计算价格、增值服务、折扣、优惠活动的实现类
		 * 
		 * 
		 *  计算运费分别为：FRT
		 *  
		 *  
		 *  
		 *  汽运  - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 0
		 *  
		 *  
		 *  空运 - t_srv_pice_plan价格表  字段 TRANSPORT_FLAG = 1
		 *  
		 *  计算增值服务：  VAS
		 *  
		 *  保费 - BF
		 *  
		 *  代收货款 - HK
		 *  
		 *  送货费 - SH
		 *  
		 *  接货费 - JH
		 *  
		 *  签收回单 -QS
		 *  
		 *  保管费 - CCF
		 *  
		 *  综合信息费 -ZHXX
		 *  
		 *  燃油附加费 - RYFJ
		 *  
		 *  中转费 - ZZ
		 *  
		 *  接货差额补差 -JHCEBC
		 *  
		 *  电子优惠券 - DZYHQ
		 *  
		 *  其他费用 -QT
		 *  
		 *  包装费 -BZ
		 *  
		 *  送货上楼费 -SHSL
		 *  
		 *  超远派送费 -CY
		 *  
		 *  送货进仓费 -SHJCF
		 *  
		 *  更改费 -GGF
		 *  
		 *  以上服务相关代码进行不同维度计算。
		 *  
		 *  可支绑定如产品、区域 等等进行价格、折扣、优惠计算
		 */
	}
	
	private boolean isStartBrms(String channelCode,String originalOrgCode){
		boolean isStartBrmsActive=true;
		ConfigurationParamsEntity configurationParamsEntity = new ConfigurationParamsEntity();
		configurationParamsEntity.setActive(FossConstants.ACTIVE);
		configurationParamsEntity.setConfType(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP);
		configurationParamsEntity.setCode(WaybillConstants.BRMS_ACTIVE_START);
		configurationParamsEntity.setOrgCode(FossConstants.ROOT_ORG_CODE);
		List<ConfigurationParamsEntity> configurationParamsEntitys= cityMarketPlanService.queryConfigurationParamsByEntity(configurationParamsEntity);
		if(configurationParamsEntitys==null || configurationParamsEntitys.size()==0){
			return true;
		}
		//如果总开关关了 直接返回false
		if(!FossConstants.YES.equals(configurationParamsEntitys.get(0).getConfValue())){
			return false;
		}
		/**
		 * 新增合伙人是否也享受规则引擎
		 * 自营开单||（合伙人开单&&开关开了）
		 */
		ConfigurationParamsEntity configurationEntity=null;
		SaleDepartmentEntity saleDepartmentEntity = null ;
		if(StringUtils.isNotBlank(originalOrgCode)){
			saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(originalOrgCode);
			
		}
		//如果营业部 不为空 却是 合伙人运单 则按下面的规则判断是否走规则引擎，否则按原来的逻辑判断（集中开单、图片开单）
		if(saleDepartmentEntity!=null && FossConstants.YES.equals(saleDepartmentEntity.getIsLeagueSaleDept())){
			configurationEntity=configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,ConfigurationParamsConstants.PARTNER_RULE_ENGINE, "DIP");
			if(configurationParamsEntitys!=null && configurationParamsEntitys.size()>0){
				if(configurationParamsEntitys.get(0)!=null){
					if(FossConstants.YES.equals(configurationParamsEntitys.get(0).getConfValue())
							&&("ALIBABACXT".equals(channelCode)
									||(configurationEntity!=null&&
									FossConstants.YES.equals(saleDepartmentEntity.getIsLeagueSaleDept())&&
									FossConstants.YES.equals(configurationEntity.getConfValue())))){
						isStartBrmsActive=true;
					}else{
						isStartBrmsActive=false;
					}
				}			
			}
		}else{
			if(configurationParamsEntitys!=null && configurationParamsEntitys.size()>0){
				if(configurationParamsEntitys.get(0)!=null){
					if(FossConstants.YES.equals(configurationParamsEntitys.get(0).getConfValue())){
						isStartBrmsActive=true;
					}else{
						isStartBrmsActive=false;
					}
				}			
			}
			
		}
		
		return isStartBrmsActive;
	}
	
	/**
	 * 计算增值服务折扣.
	 *
	 * @param originalOrgCode the original org code
	 * @param destinationOrgCode the destination org code
	 * @param receiveDate the receive date
	 * @param originalId the original id
	 * @param destinationId the destination id
	 * @param weight the weight
	 * @param volume the volume
	 * @param resultList the result list
	 * @param queryBillCacilateValueAddDte query bill cacilate value add dto
	 * @return the list
	 * @author zhangdongping
	 * @date 2012-12-25 下午3:27:04
	 * @see
	 */
	@Override
	public List<ValueAddDto> doValueAddDiscount(String originalOrgCode,
		String destinationOrgCode, Date receiveDate, String originalId,
		String destinationId, BigDecimal weight, BigDecimal volume,
		List<ValueAddDto> resultList, QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
	    /**
	     * 计算增值服务折扣，
	     * 通过增值服计算接口算出实际的请求所
	     * 需要的增值服务费率
	     * 再进行折扣计算。折扣计算步骤
	     * 1、先看是否为客户合同的客户信息。如果是则优先按照客户合同规则进行计算
	     * 2、如果不是客户合同类型的客户请求。则按照渠道优先
	     * 3、如果渠道折扣没有则按照产品折扣计算
	     *
	     */
		List<ValueAddDto> oldResultList = new ArrayList<ValueAddDto>();
		 //拷贝list，用于若调用规则引擎抛异常，则直接调用老的折扣方法
		if(CollectionUtils.isNotEmpty(resultList)) {
	    	for(ValueAddDto productPriceDto : resultList){
	    		//拷贝属性，用于调用普元出错，使用老的折扣方法
	    		ValueAddDto oldProductPriceDto =  new ValueAddDto();
	    		BeanCopier copy = BeanCopier.create(productPriceDto.getClass(), oldProductPriceDto.getClass(), false);
				copy.copy(productPriceDto, oldProductPriceDto, null);
    			oldResultList.add(oldProductPriceDto);
	    	}
	    	
	    }
	    boolean exceptionFlag = false;//规则引擎异常标示
	    boolean oldFlag = false ;//
	    List<ValueAddDto> discountResultList = new ArrayList<ValueAddDto>();
	    List<DiscountPriorityEntity> discountPriorityEntities = discountPriorityService.getDiscountPriorityByCache();
    	if(CollectionUtils.isNotEmpty(resultList)) {
    		boolean flag = false;
    		//获取行业数据
    		CustomerIndustryEntity customerIndustry = null;
    		if(StringUtils.isNotBlank(queryBillCacilateValueAddDto.getCustomerCode())){
				 customerIndustry= customerIndustryService.querySecProfessionByCusCode(queryBillCacilateValueAddDto.getCustomerCode());
    		}
    		//获取出发区域
    		PriceRegionValueAddEntity priceRegionEntity = null;
    		if (originalId != null) {
				priceRegionEntity = regionValueAddDao
						.searchRegionByID(originalId,
								PricingConstants.PRICING_REGION);
    		}
    		// 获取到达区域
    		PriceRegionValueAddEntity priceRegionArriveEntity = null;
    		if (null!=destinationId) {
				priceRegionArriveEntity = regionValueAddDao
						.searchRegionByID(destinationId,
								PricingConstants.PRICING_REGION);
    		}
    		String departDept = null;
    		String destDept = null;
    		String startCityName = null;
    		String arrvCityName =null;
    		OrgAdministrativeInfoEntity deptOrgAdministrativeInfoEntity = null;
    		if(StringUtil.isNotBlank(originalOrgCode)){
    			deptOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(originalOrgCode);
    			if(deptOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(deptOrgAdministrativeInfoEntity.getCityCode())) {
    				//出发城市
    				startCityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(deptOrgAdministrativeInfoEntity.getCityCode());
    				//出发网点
    				departDept = deptOrgAdministrativeInfoEntity.getName();
    			}
    		}
    		OrgAdministrativeInfoEntity arrvOrgAdministrativeInfoEntity = null;
    		if(StringUtil.isNotEmpty(destinationOrgCode)){
    			arrvOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(destinationOrgCode);
    			if(arrvOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(arrvOrgAdministrativeInfoEntity.getCityCode())) {
    				//到达城市
    				arrvCityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(arrvOrgAdministrativeInfoEntity.getCityCode());
    				//到达网点
    				destDept = arrvOrgAdministrativeInfoEntity.getName();
    			}
    		}
    		for (int i = 0; i < resultList.size(); i++) {
    			flag = false;
    			ValueAddDto productPriceDto = resultList.get(i);
    			//当费用类型为包装费且服务子类型为木托时，不进行任何折扣。
    			if(!(PriceEntityConstants.PRICING_CODE_BZ.equals(productPriceDto.getPriceEntityCode()) 
						&& PricingConstants.PACKAGE_TYPE_SALVER.equals(productPriceDto.getSubType()))){
	    			if(CollectionUtils.isNotEmpty(discountPriorityEntities)) {
	    				DiscountParmDto discountParmDto = new DiscountParmDto();
//	    			    OrgAdministrativeInfoEntity deptOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(originalOrgCode);
//	    			    OrgAdministrativeInfoEntity arrvOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(destinationOrgCode);
//	    			    String departDept = null;
//	    			    String destDept = null;
//	    			    String startCityName = null;
//	    			    String arrvCityName =null;
	    			    if(deptOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(deptOrgAdministrativeInfoEntity.getCityCode())) {
		    		    	 discountParmDto.setDeptCityCode(deptOrgAdministrativeInfoEntity.getCityCode());
//		    		    	 startCityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(deptOrgAdministrativeInfoEntity.getCityCode());
//		    		    	 departDept = deptOrgAdministrativeInfoEntity.getName();
		    		    }
		    		    if(arrvOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(arrvOrgAdministrativeInfoEntity.getCityCode())) {
		    		    	discountParmDto.setArrvCityCode(arrvOrgAdministrativeInfoEntity.getCityCode());
//		    		    	arrvCityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(arrvOrgAdministrativeInfoEntity.getCityCode());
//		    		    	destDept = arrvOrgAdministrativeInfoEntity.getName();
		    		    }
		    		    //依次设置折扣信息的出发区域、到达区域、始发部门、到达部门、计价条目信息、
		    		    //产品、货物、业务日期、重货、轻货、原始费用、计费类别
		    		    discountParmDto.setCriteriaDetailId(productPriceDto.getId());
	    			    discountParmDto.setDeptRegionId(originalId);
	    			    discountParmDto.setArrvRegionId(destinationId);
	    			    discountParmDto.setOriginalOrgCode(originalOrgCode);
	    			    discountParmDto.setDestinationOrgCode(destinationOrgCode);
	    			    discountParmDto.setPricingEntryCode(productPriceDto.getPriceEntityCode());
	    			    
	    			    if(StringUtil.isNotEmpty(productPriceDto.getSubTypeName())){
	    			    	discountParmDto.setPricingEntryName(productPriceDto.getPriceEntityName()+"("+productPriceDto.getSubTypeName()+")");
	    			    }else{
	    			    	discountParmDto.setPricingEntryName(productPriceDto.getPriceEntityName());
	    			    }
	    			    discountParmDto.setProductCode(productPriceDto.getProductCode());
	    			    discountParmDto.setGoodsTypeCode(productPriceDto.getGoodsTypeCode());
	    			    discountParmDto.setReceiveDate(receiveDate);
	    			    discountParmDto.setWeight(weight);
	    			    discountParmDto.setVolume(volume);
	    			    discountParmDto.setMinFee(productPriceDto.getMinFee());
	    			    discountParmDto.setMaxFee(productPriceDto.getMaxFee());
	    			    discountParmDto.setOriginnalCost(productPriceDto.getCaculateFee());
	    			    discountParmDto.setCustomCode(queryBillCacilateValueAddDto.getCustomerCode());
						discountParmDto.setIndustryCode("ALL");
						discountParmDto.setSaleChannelCode(queryBillCacilateValueAddDto.getChannelCode());
	    			    DiscountResultDto discountResult=null;
	    			    //封装普元规则引擎查询条件
	    			    DiscountCondition discountCondition = new DiscountCondition();
	    			    /**
		    			 * FOSS20150924 DP-特惠活动客户折扣新增
		    			 * @author foss-206860
		    			 * */
		    			//DP-特惠活动客户折扣新增--begin
		    		    //根据客户编码查询对应客户特惠组编码和名称（查询FOSS综合的方法）
		    		    if(StringUtil.isNotBlank(queryBillCacilateValueAddDto.getCustomerCode())){
		    		    	//根据客户编码查询客户特惠组信息
		    		    	SpecialdiscountEntity specialdiscountEntity = specialdiscountService.selectBySpecialdiscountTime(queryBillCacilateValueAddDto.getCustomerCode(),receiveDate);
		    		    	if(specialdiscountEntity != null){
		    		    		//客户特惠组编码
		    		    		if(StringUtil.isNotEmpty(specialdiscountEntity.getProductcode())){
		    		    			discountCondition.setCustomerPreferencesCode(specialdiscountEntity.getProductcode());
		    		    		}
		    		    		//客户特惠组名称
		    		    		if(StringUtil.isNotEmpty(specialdiscountEntity.getProducttype())){
		    		    			discountCondition.setCustomerPreferencesName(specialdiscountEntity.getProducttype());
		    		    		}
		    		    	}
		    		    }
		    		    //DP-特惠活动客户折扣新增--end
	    			    
//						if (originalId != null) {
//							PriceRegionValueAddEntity priceRegionEntity = regionValueAddDao
//									.searchRegionByID(originalId,
//											PricingConstants.PRICING_REGION);
	    			    // 出发区域
						if (null!=priceRegionEntity) {
							discountCondition
									.setDepartArea(priceRegionEntity
											.getRegionName());
						}
//						}
//						if (null!=destinationId) {
//							PriceRegionValueAddEntity priceRegionArriveEntity = regionValueAddDao
//									.searchRegionByID(destinationId,
//											PricingConstants.PRICING_REGION);
						// 到达区域
						if (null!=priceRegionArriveEntity ) {
							discountCondition
									.setDestArea(priceRegionArriveEntity
											.getRegionName());
						}
//						}
		    		    //出发城市
		    		    discountCondition.setDepartCity(startCityName);
		    		    //到大城市
		    		    discountCondition.setDestCity(arrvCityName);
		    		    //出发网点
		    		    discountCondition.setDepartBranch(departDept);
		    		    //到达网点
		    		    discountCondition.setDestBranch(destDept);
		    		    //产品名称
		    		    if(productPriceDto.getProductName()==null){
		    				String priceEntityName = priceEntryService.queryPriceEntryNameByCode(queryBillCacilateValueAddDto.getProductCode());
		    				 discountCondition.setProduct(priceEntityName);
		    		    }else{
			    		    discountCondition.setProduct(productPriceDto.getProductName());
		    		    }
		    		    //货物类型
	//	    		    discountCondition.setGoodsType(productPriceDto.getGoodsTypeCode());
		    		    if("H00001".equalsIgnoreCase(productPriceDto.getGoodsTypeCode())){
		    		    	discountCondition.setGoodsType("全部");
		    		    }else{
		    		    	discountCondition.setGoodsType(productPriceDto.getGoodsTypeName());
		    		    }
		    		    // 修复pop-463 开单-FOSS开单调用不到规则引擎里面配置了渠道的折扣方案
		    		    String termsCode=WaybillConstants.DICTIONARY_TERMS_CODE;
		    		    String valueCode=queryBillCacilateValueAddDto.getChannelCode();
		    		    if(StringUtil.isNotEmpty(valueCode)){	    		    	
		    		    	DataDictionaryValueEntity dvEntity=dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(termsCode, valueCode);
		    		    	if(dvEntity!=null){	    		    		
		    		    		discountCondition.setChannel(dvEntity.getValueName());
		    		    	}
		    		    }
		    		    //开单日期
		    		    String billTime = DateUtils.convert(queryBillCacilateValueAddDto.getBillTime()==null?new Date():queryBillCacilateValueAddDto.getBillTime(), DateUtils.DATE_TIME_FORMAT);
		    		    discountCondition.setBizDate(billTime);
		    		    //开单时间，开单日期的时分秒
		    		    if(billTime!=null){
		    		    	discountCondition.setBizTime(billTime.split(" ")[1]);
		    		    }
		    		  
						//计费重量或者计费体积
						if("WEIGHT".equalsIgnoreCase(queryBillCacilateValueAddDto.getCaculateType())){
							//计费类型
							discountCondition.setDiscountBase("重量");
							discountCondition.setAmount(weight.doubleValue()+"");
						}else if("VOLUME".equalsIgnoreCase(queryBillCacilateValueAddDto.getCaculateType())){
							//计费类型
							discountCondition.setDiscountBase("体积");
							discountCondition.setAmount(volume.doubleValue()+"");
						}
						//是否接货
						if(FossConstants.YES.equalsIgnoreCase(queryBillCacilateValueAddDto.getIsReceiveGoods())){
							discountCondition.setIsReceive("是");
						}else{
							discountCondition.setIsReceive("否");
						}
						//行业名称
						//---多次获取影响性能
//						if(StringUtils.isNotBlank(queryBillCacilateValueAddDto.getCustomerCode())){
//						 CustomerIndustryEntity customerIndustry= customerIndustryService.querySecProfessionByCusCode(queryBillCacilateValueAddDto.getCustomerCode());
						 if(null!=customerIndustry){
							 discountCondition.setIndustry(customerIndustry.getProfessionName());
						  }
						  //定价项目POP-407foss没有将接货金额、保费金额5个字段传给规则引擎
						if(queryBillCacilateValueAddDto.getValuedtos()!=null && queryBillCacilateValueAddDto.getValuedtos().get(PriceEntityConstants.PRICING_CODE_BF)!=null){
						GuiQueryBillCalculateSubDto bfDto=(GuiQueryBillCalculateSubDto)queryBillCacilateValueAddDto.getValuedtos().get(PriceEntityConstants.PRICING_CODE_BF);
							   if (bfDto!= null && bfDto.getOriginnalCost() != null) {
							        discountCondition.setSupportCount(bfDto.getOriginnalCost().toString());
							   }
						}
						if(queryBillCacilateValueAddDto.getValuedtos()!=null && queryBillCacilateValueAddDto.getValuedtos().get(PriceEntityConstants.PRICING_CODE_HK)!=null){
						GuiQueryBillCalculateSubDto hkDto=(GuiQueryBillCalculateSubDto) queryBillCacilateValueAddDto.getValuedtos().get(PriceEntityConstants.PRICING_CODE_HK);
							   if (hkDto!=null && hkDto.getOriginnalCost() != null) {
							        discountCondition.setCollectionCount(hkDto.getOriginnalCost().toString());
							   }
						}
						//送货费
						if(PriceEntityConstants.PRICING_CODE_SH.equals(productPriceDto.getPriceEntityCode())){
							discountCondition.setDeliveryCount(productPriceDto.getCaculateFee().toString());
						}
						//接货费
						if(PriceEntityConstants.PRICING_CODE_JH.equals(productPriceDto.getPriceEntityCode())){
							//是否接货
							if(FossConstants.YES.equalsIgnoreCase(queryBillCacilateValueAddDto.getIsReceiveGoods())){
								//定价体系优化项目POP-435
								//当接货费没有合同客户固定值时，获取增值服务中的接货默认费用
								if(null != productPriceDto.getFee()){
									discountCondition.setReceiveCount(productPriceDto.getFee().divide(BigDecimal.valueOf(NUMBER_100)).toString());
								}else{
									discountCondition.setReceiveCount(productPriceDto.getFeeRate().divide(BigDecimal.valueOf(NUMBER_100)).toString());
								}
							}else{
								//价格方案中设置是否接货为是时，不另行计算接货费
								discountCondition.setReceiveCount(BigDecimal.ZERO.toString());
							}
						}
						//包装费
						if(PriceEntityConstants.PRICING_CODE_BZ.equals(productPriceDto.getPriceEntityCode())){
							discountCondition.setPackageCount(productPriceDto.getCaculateFee().toString());
						}
		    		    //规则类型，这里是运费折扣计算
						discountCondition.setRuleTypeCode("foss.discount.valueadddiscount");
						//客户编码
						if(StringUtils.isNotBlank(queryBillCacilateValueAddDto.getCustomerCode())){
							discountCondition.setClientCode(queryBillCacilateValueAddDto.getCustomerCode());
						}else{
							discountCondition.setClientCode(null);
						}
						//增值服务类型
						//	PriceEntity priceEntity = priceEntryDao.queryPriceEntryByCode(queryBillCacilateValueAddDto.getPricingEntryCode());
							if (StringUtils.equals(productPriceDto.getPriceEntityCode(),PriceEntityConstants.PRICING_CODE_QT)) {
								if(StringUtil.isNotBlank(productPriceDto.getSubTypeName())){
									discountCondition.setValueAddType(productPriceDto.getSubTypeName().trim());
								}
							} else {
								//修复pop-434 foss开单 点击 打包装 系统报错
								if(StringUtil.isNotBlank(productPriceDto.getPriceEntityName())){
									discountCondition.setValueAddType(productPriceDto.getPriceEntityName().trim());
								}
						   }
						//当产品类型为接货费时，将接货费赋值给折前金额
						if(PriceEntityConstants.PRICING_CODE_JH.equals(productPriceDto.getPriceEntityCode())){
							discountCondition.setAmountbeforediscount(discountCondition.getReceiveCount());
						}else{
							//折前金额
							discountCondition.setAmountbeforediscount(productPriceDto.getCaculateFee().toString());
						}
							
						 //规则类型，这里是运费折扣计算
						discountCondition.setRuleTypeCode("foss.discount.valueadddiscount");
//						WebClient client = WebClient
//								.create(this.getPyServiceAddress());
						//将对象转化为string字符串
		    			String condition = null;
						DiscountCondition result = null;
						String result1 = null;
						//定义折扣相关信息
		    		    List<PriceDiscountDto> priceDiscountDtoList = new ArrayList<PriceDiscountDto>();
		    		    
	    			    if(StringUtils.equals(PriceEntityConstants.PRICING_CODE_QT, productPriceDto.getPriceEntityCode())
	    			    		|| StringUtils.equals(PriceEntityConstants.PRICING_CODE_BZ, productPriceDto.getPriceEntityCode())) {
	    			    	discountParmDto.setSubType(productPriceDto.getSubType());
	    			    }
	    			    //对于合同客户优先折扣计算
	    			   // setRulengineTimeOut();
	    				for (int j = 0; j < discountPriorityEntities.size(); j++) {
	    					DiscountPriorityEntity entity = discountPriorityEntities.get(j);
	    					if(entity != null && entity.getCode() != null) {
	    						if(StringUtil.equals(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT, entity.getCode())) {
	    							DiscountTypeInterface discountExe = discountTypeFactory.getDiscountTypeImpl(entity.getCode());
	        						if(discountExe != null) {
	        							if(StringUtils.equals(PriceEntityConstants.PRICING_CODE_QT, productPriceDto.getPriceEntityCode())
	        									&& !StringUtils.equals(PriceEntityConstants.QT_CODE_CZHCZFWF, productPriceDto.getSubType())
	        									/**
	        									 * @BUG号：DEFECT-8626
	        									 * @功能：获取“送货进仓”折扣
	        									 * @author:218371-foss-zhaoyanjun
	        									 * @date:2015-05-26
	        									 */
	        									&& !StringUtils.equals(PriceEntityConstants.PRICING_CODE_SHJC, productPriceDto.getSubType())
	        									){
	        								//判断是否是其他费用，子类型不是超重货服务费（其他费用中除了超重货服务费不会享受客户合同折扣，加判断是为了提高性能）
	        								discountResult = null;
	        							}else{
	        								//获取客户折扣操作类
	        								discountResult =discountExe.doDiscount(discountParmDto);
	        							}
	        							//折扣存在,切折扣率不等于1
	        							if(discountResult != null) {
	        								discountResult.setContractType(null);
	        								boolean isExpress = productService.onlineDetermineIsExpressByProductCode(discountParmDto.getProductCode(), discountParmDto.getReceiveDate());
	        								//计算折扣
	        								DiscountResultDto discountResultDto = PriceUtil.calculateCustomDiscountClientData(discountResult, discountParmDto, isExpress);
	        								//将折扣价格赋值原计算后价格
	        								productPriceDto.setCaculateFee(discountResultDto.getDiscountValue());
	        								
	        								/**
	        								* 设置fee值，前台通过该值来判断是否有客户固定接货费
	        								* 时间：2014-03-04
	        								* 内容：MANA-257接货费优化
	        								* 作者：026123
	        								*/
	        								if(PriceEntityConstants.PRICING_CODE_JH.equals(productPriceDto.getPriceEntityCode())){
	        									productPriceDto.setFee(discountResultDto.getDiscountValue());
	        								}
	        								if(PriceEntityConstants.PRICING_CODE_QS.equals(productPriceDto.getPriceEntityCode())){
	        									productPriceDto.setFee(discountResultDto.getDiscountValue());
	        								}
	        								//将折扣价格赋值折扣价格
	        								productPriceDto.setDiscountFee(discountResultDto.getDiscountValue());
	        								//保留计价明细ID
	        								productPriceDto.setId(discountResultDto.getId());
	        								//保留折扣相关信息
	        								priceDiscountDtoList.addAll(discountResultDto.getDiscountPrograms());
	//        								productPriceDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
	//    									discountResultList.add(productPriceDto);
	        								//折前金额,若是合同客户，则是计算完合同折扣以后的金额，否则是计算运费后的金额
	        								if(productPriceDto.getActualFeeRate() != null && productPriceDto.getActualFeeRate().compareTo(BigDecimal.ZERO) > 0){
	        									productPriceDto.setActualFeeRate(productPriceDto.getActualFeeRate().multiply(discountResultDto.getDiscountRate()));
	        								}else{
	        									//定价体系优化项目POP-447
	        									productPriceDto.setActualFeeRate(discountResultDto.getDiscountRate());
	        								}
	        								discountCondition.setAmountbeforediscount(discountResultDto.getDiscountValue().toString());
	        								//设置为享受过合同折扣
	        								discountCondition.setIsOverLay("2");
	        								 /**
	        								    * @BUG编号：DEFECT-8681
	        								    * @功能：合同种类
	        								    * @author:218371-foss-zhaoyanjun
	        								    * @date:2015-05-28
	        								    * @说明：
	        								    * 1.代表合同客户
	        								    */  
	        								productPriceDto.setContractType(1);
	//    									flag = true;
	    									break;
	        							} else {
	        								continue;
	        							}
	        						} else {
	        							continue;
	        						}
	    						}else{
	    							//是否启用规则引擎
	    							if(isStartBrms(queryBillCacilateValueAddDto.getChannelCode(),originalOrgCode)){
	    								//折前金额
										discountCondition.setAmountbeforediscount(productPriceDto.getCaculateFee().toString());
										//设置为非叠加折扣
										discountCondition.setIsOverLay("1");
		    							condition = JSON.toJSONString(discountCondition);
		    							JSONObject jsonObject = new JSONObject();
		    		    				jsonObject.put("json", condition);
		    		    				jsonObject.put("date", discountCondition.getBizDate());
		    		    				jsonObject.put("ruleTypeCode", "foss.discount.valueadddiscount");
		    		    				condition = JSON.toJSONString(jsonObject);
//		    							clientPyService.type("multipart/mixed").accept("application/json"); 
//		    						    List<Attachment> atts = new LinkedList<Attachment>(); 
//		    						    atts.add(new Attachment("ruleTypeCode", "application/json", "foss.discount.valueadddiscount")); 
//		    						    atts.add(new Attachment("date", "application/json", billTime));
//		    							//调用普元接口后的结果
//		    						    atts.add(new Attachment("json", "application/json", condition));
		    						    try {
		    						    	String bussinessDate = DateUtils.convert(new Date());
		    						    	long begin=System.currentTimeMillis();
		    						    	log.info("增值费用规则引擎调用开始(非折上折):"+bussinessDate);
				    				    	result1 = clientPyService.type("application/json").accept("application/json").post(condition,String.class);
		    						    	result = JsonUtil.jsonToPojo(result1, DiscountCondition.class);//此处调用普元结果进行转换
		    						    	long process=System.currentTimeMillis()-begin;
		    						    	log.info("增值费用规则引擎调用结束,业务发生时间为:"+bussinessDate+":持续时间为:"+process);
		    						    	 if(process>=NUMBER_800){
		    						    		 log.warn("增值费用规则引擎持续时间太长了:"+process);
		    						    	 }
		    						    } catch (Exception e) {
		    								log.info("调用普元接口异常，请确认是否开启普元服务");
		    								exceptionFlag = true;
		    								oldFlag = true ;
		    								break;
		    							}
		    						    if(result != null){
		    			    				if(Boolean.valueOf(result.getError())){//如果普元接口异常
		    				    				log.info(result.getErrorContent());
		    				    				exceptionFlag = true;
		    				    				break;
		    				    			}else{
		    				    				//设置折扣相关信息
		    				    				setValueDiscount(result, productPriceDto, priceDiscountDtoList, discountCondition, true);
		//    									flag = true;
		    									break;
		    				    			}
		    			    			}
	    							}
	    						    
	    						}
	    					} else {
	    						continue;
	    					}
	    				}
	    				if(!exceptionFlag){//若规则引擎接口没有抛异常，则进行叠加折扣
	    					//是否启用规则引擎
						if(isStartBrms(queryBillCacilateValueAddDto.getChannelCode(),originalOrgCode)){
								//设置为叠加折扣
		    					discountCondition.setIsOverLay("2");
		    					condition = JSON.toJSONString(discountCondition);
		    					JSONObject jsonObject = new JSONObject();
    		    				jsonObject.put("json", condition);
    		    				jsonObject.put("date", discountCondition.getBizDate());
    		    				jsonObject.put("ruleTypeCode", "foss.discount.valueadddiscount");
    		    				condition = JSON.toJSONString(jsonObject);
//		    					clientPyService.type("multipart/mixed").accept("application/json"); 
//							    List<Attachment> atts = new LinkedList<Attachment>(); 
//							    atts.add(new Attachment("ruleTypeCode", "application/json", "foss.discount.valueadddiscount")); 
//							    atts.add(new Attachment("date", "application/json", billTime));
		    					//调用普元接口后的结果
//		    				    atts.add(new Attachment("json", "application/json", condition));
		    				    try {
		    				    	String bussinessDate = DateUtils.convert(new Date());
		    				    	long begin=System.currentTimeMillis();
    						    	log.info("增值费用规则引擎调用开始(折上折):"+bussinessDate);
		    				    	result1 = clientPyService.type("application/json").accept("application/json").post(condition,String.class);
		    				    	result = JsonUtil.jsonToPojo(result1, DiscountCondition.class);//此处调用普元结果进行转换
		    				    	long process=System.currentTimeMillis()-begin;
    						    	log.info("增值费用规则引擎调用结束,业务发生时间为:"+bussinessDate+":持续时间为:"+process);
    						    	 if(process>=NUMBER_800){
    						    		 log.warn("增值费用规则引擎持续时间太长了:"+process);
    						    	 }
		    				    } catch (Exception e) {
		    						log.info("调用普元接口异常，请确认是否开启普元服务");
		    						exceptionFlag = true;
		    						oldFlag = true ;
		    						break;
		    					}
		    				  //此处调用普元接口，进行折上折 
		    	    			if(result != null){
		    	    				if(Boolean.valueOf(result.getError())){//如果普元接口异常
		    		    				log.info(result.getErrorContent());
		    		    				exceptionFlag = true;
		        						break;
		    		    			}else{
		    		    				//设置折扣相关信息
		    		    				setValueDiscount(result, productPriceDto, priceDiscountDtoList, discountCondition, false);
		    		    				discountResultList.add(productPriceDto);//如果异常，折扣结果不采用折上折，继续使用原来计算后的结果
		    							flag = true;
		    		    			}
		    	    			}	
							}
//	        				保留折扣相关信息
	    					productPriceDto.setDiscountPrograms(priceDiscountDtoList);	
	    					if(!flag){
	    						discountResultList.add(productPriceDto);
	    					}
	    				}else{//抛异常，终止循环，直接走老方法
	    					discountResultList.add(productPriceDto);
	    					break;
	    				}
	    			} 
	    		}else {
	    			discountResultList.add(productPriceDto);
	    		}
    		}
    	}
    	if(oldFlag){
    		discountResultList = doOldValueAddDiscount(originalOrgCode,
					destinationOrgCode, receiveDate, originalId,
					destinationId, weight, volume, oldResultList,
					queryBillCacilateValueAddDto);
    	}
    	//为了后期获取折扣子类型 POP-468,折扣用户 ，包装费子类型，有问题。
    	//上次修改忽略了掉浦源接口异常情况
    	for(ValueAddDto valueAddDto:discountResultList){
    		if(null!=valueAddDto.getSubType() && StringUtils.equals(PriceEntityConstants.PRICING_CODE_BZ, valueAddDto.getPriceEntityCode())){ 
    			DataDictionaryValueEntity dvEntity=dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(WaybillConstants.BZ_TERMS_CODE, valueAddDto.getSubType());
    			if(null!=dvEntity){
    				valueAddDto.setSubTypeName(dvEntity.getValueName());
    				//修复pop-488 foss开单计算总运费 系统提示 下标越界
    				if(null!=valueAddDto.getDiscountPrograms() && valueAddDto.getDiscountPrograms().size()>0 && null!=valueAddDto.getDiscountPrograms().get(0))
    					valueAddDto.getDiscountPrograms().get(0).setSubTypeName(dvEntity.getValueName());
    			}
    		}else{
    			valueAddDto.setSubTypeName(valueAddDto.getSubTypeName());
    		}
    	}
		return discountResultList;
		/**  BillCaculateService 是开单计算价格、增值服务、折扣、优惠活动的实现类
		 *  计算运费分别为：FRT
		 *  汽运  - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 0
		 *  空运 - t_srv_pice_plan价格表  字段 TRANSPORT_FLAG = 1
		 *  
		 *  计算增值服务：  VAS
		 *  
		 *  保费 - BF
		 *  
		 *  代收货款 - HK
		 *  
		 *  送货费 - SH
		 *  
		 *  接货费 - JH
		 *  
		 *  签收回单 -QS
		 *  
		 *  保管费 - CCF
		 *  
		 *  综合信息费 -ZHXX
		 *  
		 *  燃油附加费 - RYFJ
		 *  
		 *  中转费 - ZZ
		 *  
		 *  接货差额补差 -JHCEBC
		 *  
		 *  电子优惠券 - DZYHQ
		 *  
		 *  其他费用 -QT
		 *  
		 *  包装费 -BZ
		 *  
		 *  送货上楼费 -SHSL
		 *  
		 *  超远派送费 -CY
		 *  
		 *  送货进仓费 -SHJCF
		 *  
		 *  更改费 -GGF
		 *  
		 *  以上服务相关代码进行不同维度计算。
		 *  
		 *  可支绑定如产品、区域 等等进行价格、折扣、优惠计算
		 */
	}
	/**
	 * 设置增值折扣相关信息
	 * @param result 调用规则引擎返回的结果
	 * @param productPriceDto 折扣信息
	 * @param priceDiscountDtoList 折扣明细
	 * @param discountCondition 规则引擎入参
	 * @param isOverLay 是否会进行叠加折扣 TRUE 会，因此需要设置折前金额， FALSE 不会，不用设置折前金额
	 */
	private void setValueDiscount(DiscountCondition result, ValueAddDto productPriceDto, 
			List<PriceDiscountDto> priceDiscountDtoList, DiscountCondition discountCondition, boolean isOverLay){
		if(!"0".equalsIgnoreCase(result.getDiscountFlag())&&result.getDiscountFlag()!=null){//普元接口有对应的折扣规则
			PriceDiscountDto priceDiscountDto = new PriceDiscountDto();
			boolean flag = false;
				if(result.getDiscountFlag()!=null){
					if("2".equals(result.getDiscountFlag())){
						//直接减免
							if(Double.valueOf(result.getDiscoutDecrease())==0){
								flag = true;
							}
						}
					//----begin------修复bug 定价体系优化项目POP-367	
					//当存在最第一票时，比较最低一票和折后金额
					/**
					 * @BUG：DEFECT-8626
					 * @功能：送货进仓不校验最低一票 
					 * @author:218371-foss-zhaoyanjun
					 * @date:2015-05-27上午12:10	
					 */
					if(!(StringUtils.equals(PriceEntityConstants.PRICING_CODE_SHJC, productPriceDto.getSubType())&&productPriceDto.getContractType()==1)
							&&null != productPriceDto.getMinFee()){
						//当折后金额 <最低一票
						if(Double.parseDouble(result.getAmountafterdiscount()) < productPriceDto.getMinFee().doubleValue()){
							log.debug("折扣价格低于最低一票");
							//将最低一票赋值原计算后价格
							productPriceDto.setCaculateFee(productPriceDto.getMinFee());
							//将最低一票赋值折扣价格
							productPriceDto.setDiscountFee(productPriceDto.getMinFee());
							//当折前金额小于最低一票时，将优惠金额设置为0，折扣费率设置为1
							if(new BigDecimal(result.getAmountbeforediscount()).compareTo(productPriceDto.getMinFee()) >= 0){
								//优惠金额为折前金额-最低一票
								priceDiscountDto.setReduceFee(new BigDecimal(result.getAmountbeforediscount()).subtract(productPriceDto.getMinFee()));
								//折扣费率 = 最低一票/原始费用
								log.info(productPriceDto.getSubType()+"增值原始费用为"+result.getAmountbeforediscount());
								priceDiscountDto.setDiscountRate(productPriceDto.getMinFee().divide(new BigDecimal(result.getAmountbeforediscount()), 2, RoundingMode.HALF_DOWN));
							}else{
								//优惠金额为0
								priceDiscountDto.setReduceFee(BigDecimal.ZERO);
								//折扣费率 = 1
								priceDiscountDto.setDiscountRate(BigDecimal.ONE);
							}
							//---begin---定价体系优化项目POP-417
//							priceDiscountDto.setTypeName("最低一票");
							//---end---定价体系优化项目POP-417
						}else{
							//优惠金额
							double reduceFee = Double.valueOf(result.getAmountbeforediscount())-Double.valueOf(result.getAmountafterdiscount());
							if( BigDecimal.valueOf(reduceFee).compareTo(BigDecimal.ZERO) == 0 ){
								flag = true;
							}
							priceDiscountDto.setReduceFee(BigDecimal.valueOf(reduceFee));
							//将折扣价格赋值原计算后价格
							productPriceDto.setCaculateFee(new BigDecimal(result.getAmountafterdiscount()));
							//将折扣价格赋值折扣价格
							productPriceDto.setDiscountFee(new BigDecimal(result.getAmountafterdiscount()));
							if("2".equals(result.getDiscountFlag())){
							priceDiscountDto.setDiscountRate(new BigDecimal(result.getAmountafterdiscount()).divide(new BigDecimal(result.getAmountbeforediscount()), 2, RoundingMode.HALF_DOWN));
							}else{
								priceDiscountDto.setDiscountRate(BigDecimal.valueOf(Double.valueOf(result.getDiscoutPercent())));
							}
						}
					}else{
							if("2".equals(result.getDiscountFlag())){
								 if(Double.valueOf(result.getAmountafterdiscount())<=0){
										priceDiscountDto.setReduceFee(new BigDecimal(result.getAmountbeforediscount()));
										priceDiscountDto.setDiscountRate(BigDecimal.ZERO);
										//将折扣价格赋值原计算后价格
										productPriceDto.setCaculateFee(BigDecimal.ZERO);
										//将折扣价格赋值折扣价格
										productPriceDto.setDiscountFee(BigDecimal.ZERO);
										}else{
											//优惠金额
											double reduceFee = Double.valueOf(result.getAmountbeforediscount())-Double.valueOf(result.getAmountafterdiscount());
											if( BigDecimal.valueOf(reduceFee).compareTo(BigDecimal.ZERO) == 0 ){
												flag = true;
											}
											priceDiscountDto.setReduceFee(BigDecimal.valueOf(reduceFee));
											//将折扣价格赋值原计算后价格
											productPriceDto.setCaculateFee(new BigDecimal(result.getAmountafterdiscount()));
											//将折扣价格赋值折扣价格
											productPriceDto.setDiscountFee(new BigDecimal(result.getAmountafterdiscount()));
											priceDiscountDto.setDiscountRate(new BigDecimal(result.getAmountafterdiscount()).divide(new BigDecimal(result.getAmountbeforediscount()), 2, RoundingMode.HALF_DOWN));
										}
								}else{
									//优惠金额
									double reduceFee = Double.valueOf(result.getAmountbeforediscount())-Double.valueOf(result.getAmountafterdiscount());
									if( BigDecimal.valueOf(reduceFee).compareTo(BigDecimal.ZERO) == 0 ){
										flag = true;
									}
									priceDiscountDto.setReduceFee(BigDecimal.valueOf(reduceFee));
									//将折扣价格赋值原计算后价格
									productPriceDto.setCaculateFee(new BigDecimal(result.getAmountafterdiscount()));
									//将折扣价格赋值折扣价格
									productPriceDto.setDiscountFee(new BigDecimal(result.getAmountafterdiscount()));
									priceDiscountDto.setDiscountRate(BigDecimal.valueOf(Double.valueOf(result.getDiscoutPercent())));
								}
					}
	//----end------修复bug 定价体系优化项目POP-367	
					//总折扣费率
					BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
					//定价体系优化项目POP-433
					if(null != actualFeeRate){
						if(StringUtils.equals(productPriceDto.getPriceEntityCode(),PriceEntityConstants.PRICING_CODE_BF) 
								|| StringUtils.equals(productPriceDto.getPriceEntityCode(), PriceEntityConstants.PRICING_CODE_HK)){
							productPriceDto.setActualFeeRate(priceDiscountDto.getDiscountRate().multiply(actualFeeRate));
						}else{						
							productPriceDto.setActualFeeRate(priceDiscountDto.getDiscountRate().multiply(actualFeeRate.divide(new BigDecimal(NUMBER_100))));
						}					
					}else{
						productPriceDto.setActualFeeRate(priceDiscountDto.getDiscountRate());
					}
				}
				priceDiscountDto.setMarketName(productPriceDto.getPriceEntityName());
				//原始费用
				priceDiscountDto.setOriginnalCost(BigDecimal.valueOf(Double.valueOf(result.getAmountbeforediscount())));
				priceDiscountDto.setPriceEntryCode(productPriceDto.getPriceEntityCode());
				priceDiscountDto.setPriceEntryName(productPriceDto.getPriceEntityName());
				//定价体系优化项目POP-423
				//为了后期获取折扣子类型
				priceDiscountDto.setSubType(productPriceDto.getSubType());
				if(null!=productPriceDto.getSubType() && StringUtils.equals(PriceEntityConstants.PRICING_CODE_BZ, productPriceDto.getPriceEntityCode())){
    		    	DataDictionaryValueEntity dvEntity=dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(WaybillConstants.BZ_TERMS_CODE, productPriceDto.getSubType());
    				if(null!=dvEntity){
    		    	priceDiscountDto.setSubTypeName(dvEntity.getValueName());
    				}
				}else{
				    priceDiscountDto.setSubTypeName(productPriceDto.getSubTypeName());
				}
				//方案名称
				priceDiscountDto.setTypeName(result.getRuleDefHeaderName());
				priceDiscountDto.setType(DiscountTypeConstants.DISCOUNT_TYPE_RULE_CODE);
				priceDiscountDto.setDiscountId(result.getRuleDefHeaderId());
				if(!flag){//如果直接减免金额为零，则不显示该条明细
					priceDiscountDtoList.add(priceDiscountDto);
				}
				//组装计价明细ID
				priceDiscountDto.setChargeDetailId(productPriceDto.getId());
				if(isOverLay){
					//设置折前金额，为叠加折扣服务
					discountCondition.setAmountbeforediscount(productPriceDto.getCaculateFee().toString());
				}
		}
	}
	
	/**
	 * 计算增值服务折扣(没有引用规则引擎).
	 *
	 * @param originalOrgCode the original org code
	 * @param destinationOrgCode the destination org code
	 * @param receiveDate the receive date
	 * @param originalId the original id
	 * @param destinationId the destination id
	 * @param weight the weight
	 * @param volume the volume
	 * @param resultList the result list
	 * @param queryBillCacilateValueAddDto the query bill cacilate value add dto
	 * @return the list
	 * @author zhangdongping
	 * @date 2012-12-25 下午3:27:04
	 * @see
	 */
	private List<ValueAddDto> doOldValueAddDiscount(String originalOrgCode,
		String destinationOrgCode, Date receiveDate, String originalId,
		String destinationId, BigDecimal weight, BigDecimal volume,
		List<ValueAddDto> resultList, QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
	    /**
	     * 计算增值服务折扣，
	     * 通过增值服计算接口算出实际的请求所
	     * 需要的增值服务费率
	     * 再进行折扣计算。折扣计算步骤
	     * 1、先看是否为客户合同的客户信息。如果是则优先按照客户合同规则进行计算
	     * 2、如果不是客户合同类型的客户请求。则按照渠道优先
	     * 3、如果渠道折扣没有则按照产品折扣计算
	     *
	     */
	    List<ValueAddDto> discountResultList = new ArrayList<ValueAddDto>();
	    List<DiscountPriorityEntity> discountPriorityEntities = discountPriorityService.getDiscountPriorityByCache();
    	if(CollectionUtils.isNotEmpty(resultList)) {
    		boolean flag = false;
    		for (int i = 0; i < resultList.size(); i++) {
    			flag = false;
    			ValueAddDto productPriceDto = resultList.get(i);
    			//当费用类型为包装费且服务子类型为木托时，不进行任何折扣。
    			if(!(PriceEntityConstants.PRICING_CODE_BZ.equals(productPriceDto.getPriceEntityCode()) 
						&& PricingConstants.PACKAGE_TYPE_SALVER.equals(productPriceDto.getSubType()))){
	    			if(CollectionUtils.isNotEmpty(discountPriorityEntities)) {
	    				DiscountParmDto discountParmDto = new DiscountParmDto();
	    			    OrgAdministrativeInfoEntity deptOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(originalOrgCode);
	    			    OrgAdministrativeInfoEntity arrvOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(destinationOrgCode);
	    			    if(deptOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(deptOrgAdministrativeInfoEntity.getCityCode())) {
		    		    	 discountParmDto.setDeptCityCode(deptOrgAdministrativeInfoEntity.getCityCode());
		    		    }
		    		    if(arrvOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(arrvOrgAdministrativeInfoEntity.getCityCode())) {
		    		    	discountParmDto.setArrvCityCode(arrvOrgAdministrativeInfoEntity.getCityCode());
		    		    }
		    		    //依次设置折扣信息的出发区域、到达区域、始发部门、到达部门、计价条目信息、
		    		    //产品、货物、业务日期、重货、轻货、原始费用、计费类别
		    		    discountParmDto.setCriteriaDetailId(productPriceDto.getId());
	    			    discountParmDto.setDeptRegionId(originalId);
	    			    discountParmDto.setArrvRegionId(destinationId);
	    			    discountParmDto.setOriginalOrgCode(originalOrgCode);
	    			    discountParmDto.setDestinationOrgCode(destinationOrgCode);
	    			    discountParmDto.setPricingEntryCode(productPriceDto.getPriceEntityCode());
	    			    
	    			    if(StringUtil.isNotEmpty(productPriceDto.getSubTypeName())){
	    			    	discountParmDto.setPricingEntryName(productPriceDto.getPriceEntityName()+"("+productPriceDto.getSubTypeName()+")");
	    			    }else{
	    			    	discountParmDto.setPricingEntryName(productPriceDto.getPriceEntityName());
	    			    }
	    			    discountParmDto.setProductCode(productPriceDto.getProductCode());
	    			    discountParmDto.setGoodsTypeCode(productPriceDto.getGoodsTypeCode());
	    			    discountParmDto.setReceiveDate(receiveDate);
	    			    discountParmDto.setWeight(weight);
	    			    discountParmDto.setVolume(volume);
	    			    discountParmDto.setMinFee(productPriceDto.getMinFee());
	    			    discountParmDto.setMaxFee(productPriceDto.getMaxFee());
	    			    discountParmDto.setOriginnalCost(productPriceDto.getCaculateFee());
	    			    discountParmDto.setCustomCode(queryBillCacilateValueAddDto.getCustomerCode());
						discountParmDto.setIndustryCode("ALL");
						discountParmDto.setSaleChannelCode(queryBillCacilateValueAddDto.getChannelCode());
	    			    DiscountResultDto discountResult=null;
	    			    
	    			    if(StringUtils.equals(PriceEntityConstants.PRICING_CODE_QT, productPriceDto.getPriceEntityCode())
	    			    		|| StringUtils.equals(PriceEntityConstants.PRICING_CODE_BZ, productPriceDto.getPriceEntityCode())) {
	    			    	discountParmDto.setSubType(productPriceDto.getSubType());
	    			    }
	    			    //对于合同客户优先折扣计算
	    				for (int j = 0; j < discountPriorityEntities.size(); j++) {
	    					DiscountPriorityEntity entity = discountPriorityEntities.get(j);
	    					if(entity != null && entity.getCode() != null) {
	    						if(StringUtil.equals(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT, entity.getCode())) {
	    							DiscountTypeInterface discountExe = discountTypeFactory.getDiscountTypeImpl(entity.getCode());
	        						if(discountExe != null) {
	        							//获取客户折扣操作类
	        							discountResult =discountExe.doDiscount(discountParmDto);
	        							//折扣存在,切折扣率不等于1
	        							if(discountResult != null) {
	        								discountResult.setContractType(null);
	        								boolean isExpress = productService.onlineDetermineIsExpressByProductCode(discountParmDto.getProductCode(), discountParmDto.getReceiveDate());
	        								//计算折扣
	        								DiscountResultDto discountResultDto = PriceUtil.calculateCustomDiscountClientData(discountResult, discountParmDto, isExpress);
	        								//将折扣价格赋值原计算后价格
	        								productPriceDto.setCaculateFee(discountResultDto.getDiscountValue());
	        								
	        								/**
	        								* 设置fee值，前台通过该值来判断是否有客户固定接货费
	        								* 时间：2014-03-04
	        								* 内容：MANA-257接货费优化
	        								* 作者：026123
	        								*/
	        								if(PriceEntityConstants.PRICING_CODE_JH.equals(productPriceDto.getPriceEntityCode())){
	        									productPriceDto.setFee(discountResultDto.getDiscountValue());
	        								}
	        								if(PriceEntityConstants.PRICING_CODE_QS.equals(productPriceDto.getPriceEntityCode())){
	        									productPriceDto.setFee(discountResultDto.getDiscountValue());
	        								}
	        								//折前金额,若是合同客户，则是计算完合同折扣以后的金额，否则是计算运费后的金额
	        								if(productPriceDto.getActualFeeRate() != null && productPriceDto.getActualFeeRate().compareTo(BigDecimal.ZERO) > 0){
	        									productPriceDto.setActualFeeRate(productPriceDto.getActualFeeRate().multiply(discountResultDto.getDiscountRate()));
	        								}else{
	        									//定价体系优化项目POP-447
	        									productPriceDto.setActualFeeRate(discountResultDto.getDiscountRate());
	        								}
	        								//将折扣价格赋值折扣价格
	        								productPriceDto.setDiscountFee(discountResultDto.getDiscountValue());
	        								//保留计价明细ID
	        								productPriceDto.setId(discountResultDto.getId());
	        								//保留折扣相关信息
	        								productPriceDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
	    									discountResultList.add(productPriceDto);
	    									flag = true;
	    									break;
	        							} else {
	        								continue;
	        							}
	        						} else {
	        							continue;
	        						}
	    						} else {
	    							DiscountTypeInterface discountExe = discountTypeFactory.getDiscountTypeImpl(entity.getCode());
	        						if(discountExe != null) {
	        							//获取渠道或是产品折扣操作类
	        							discountResult =discountExe.doDiscount(discountParmDto);
	        							//折扣存在,切折扣率不等于1
	        							if(discountResult!=null ) {
	        								//计算折扣
	        								DiscountResultDto discountResultDto = PriceUtil.calculatePriceDiscountClientData(discountResult, discountParmDto);
	        								if(discountResultDto != null && discountResultDto.getId() != null) {
	        									//将折扣价格赋值原计算后价格
	        									productPriceDto.setCaculateFee(discountResultDto.getDiscountValue());
	        									//将折扣价格赋值折扣价格
	        									productPriceDto.setDiscountFee(discountResultDto.getDiscountValue());
	        									//保留计价明细ID
	        									productPriceDto.setId(discountResultDto.getId());
	        									//保留折扣相关信息
	        									productPriceDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
	        									discountResultList.add(productPriceDto);
	        									flag = true;
	        									break;
	        								} else {
	        									continue;
	        								}
	        							} else {
	        								continue;
	        							}
	        						} else {
	        							continue;
	        						}
	    						}
	    					} else {
	    						continue;
	    					}
	    				}
	    				if(!flag) {
	    	    			discountResultList.add(productPriceDto);
	    	    		} 
	    			} 
    			}else {
	    			discountResultList.add(productPriceDto);
	    		}
    		}
    	}
    	
		return discountResultList;
		/**  BillCaculateService 是开单计算价格、增值服务、折扣、优惠活动的实现类
		 *  计算运费分别为：FRT
		 *  汽运  - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 0
		 *  空运 - t_srv_pice_plan价格表  字段 TRANSPORT_FLAG = 1
		 *  
		 *  计算增值服务：  VAS
		 *  
		 *  保费 - BF
		 *  
		 *  代收货款 - HK
		 *  
		 *  送货费 - SH
		 *  
		 *  接货费 - JH
		 *  
		 *  签收回单 -QS
		 *  
		 *  保管费 - CCF
		 *  
		 *  综合信息费 -ZHXX
		 *  
		 *  燃油附加费 - RYFJ
		 *  
		 *  中转费 - ZZ
		 *  
		 *  接货差额补差 -JHCEBC
		 *  
		 *  电子优惠券 - DZYHQ
		 *  
		 *  其他费用 -QT
		 *  
		 *  包装费 -BZ
		 *  
		 *  送货上楼费 -SHSL
		 *  
		 *  超远派送费 -CY
		 *  
		 *  送货进仓费 -SHJCF
		 *  
		 *  更改费 -GGF
		 *  
		 *  以上服务相关代码进行不同维度计算。
		 *  
		 *  可支绑定如产品、区域 等等进行价格、折扣、优惠计算
		 */
	}
	/**
	 * 根据出发始发区域ID,目的地区域ID,产品编码,营业日期,确定获得唯一时效明细信息返回长短途标识.
	 *
	 * @param originalOrgCode the original org code
	 * @param destinationOrgCode the destination org code
	 * @param productCode 产品编码
	 * @param receiveDate 收货日期
	 * @return the long or short
	 * @author DP-Foss-YueHongJie
	 * @date 2012-11-9 下午2:37:08
	 */
	private String getLongOrShort(String originalOrgCode, String destinationOrgCode,String productCode,Date receiveDate){
	    List<EffectivePlanDto> effPlanDetailList = effectivePlanDetailService.queryEffectivePlanDetailListByOrgCode(originalOrgCode, destinationOrgCode, productCode,receiveDate);
	    if(CollectionUtils.isNotEmpty(effPlanDetailList)){
	    	return effPlanDetailList.get(0).getLongOrShort();    
	    }
	    return null;
	    /**  BillCaculateService 是开单计算价格、增值服务、折扣、优惠活动的实现类
		 *  计算运费分别为：FRT
		 *  汽运  - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 0
		 *  空运 - t_srv_pice_plan价格表  字段 TRANSPORT_FLAG = 1
		 *  
		 *  计算增值服务：  VAS
		 *  
		 *  保费 - BF
		 *  
		 *  代收货款 - HK
		 *  
		 *  送货费 - SH
		 *  
		 *  接货费 - JH
		 *  
		 *  签收回单 -QS
		 *  
		 *  保管费 - CCF
		 *  
		 *  综合信息费 -ZHXX
		 *  
		 *  燃油附加费 - RYFJ
		 *  
		 *  中转费 - ZZ
		 *  
		 *  接货差额补差 -JHCEBC
		 *  
		 *  电子优惠券 - DZYHQ
		 *  
		 *  其他费用 -QT
		 *  
		 *  包装费 -BZ
		 *  
		 *  送货上楼费 -SHSL
		 *  
		 *  超远派送费 -CY
		 *  
		 *  送货进仓费 -SHJCF
		 *  
		 *  更改费 -GGF
		 *  
		 *  以上服务相关代码进行不同维度计算。
		 *  
		 *  可支绑定如产品、区域 等等进行价格、折扣、优惠计算
		 */
	}
	
	/**
	 * 用新的feeRate覆盖查询到的feeRate
	 * (即使用GUI端操作员录入或默认的feeRate)
	 * @param list
	 * @param feeRate
	 * @return
	 */
	private List<ResultProductPriceDto> coverFeeRateOfBF(List<ResultProductPriceDto> list, BigDecimal feeRate){
		if(CollectionUtils.isNotEmpty(list)){
			for(ResultProductPriceDto dto : list){
					dto.setFeeRate(feeRate);
			}
		}		
		return list;
	}
	
	/**
	 * Compare discount and region list.
	 *
	 * @param discountList the discount list
	 * @param regionList the region list
	 * @return the list
	 * @Description: 比较优惠与折扣
	 * @author FOSSDP-sz
	 * @date 2013-3-27 上午10:11:23
	 * @version V1.0
	 */
	private List<ValueAddDto> compareDiscountAndRegionList(List<ValueAddDto> discountList, List<ValueAddDto> regionList) {
		if(CollectionUtils.isNotEmpty(regionList)) {
			List<ValueAddDto> result = new ArrayList<ValueAddDto>();
			for (int i = 0; i < regionList.size(); i++) {
				ValueAddDto valueAddDtoRegion = regionList.get(i);
				if(StringUtils.equals(valueAddDtoRegion.getPriceEntityCode(), PriceEntityConstants.PRICING_CODE_QT)) {
					for (int j = 0; j < discountList.size(); j++) {
						ValueAddDto valueAddDtoDiscount = discountList.get(j);
						if(StringUtils.equals(valueAddDtoDiscount.getSubType(), valueAddDtoRegion.getSubType())) {
							if(valueAddDtoDiscount.getDiscountFee() != null) {
								valueAddDtoRegion.setDiscountFee(valueAddDtoRegion.getCaculateFee());
								if(valueAddDtoDiscount.getCaculateFee().doubleValue() <= valueAddDtoRegion.getCaculateFee().doubleValue()) {
									result.add(valueAddDtoDiscount);
								} else {
									result.add(valueAddDtoRegion);
								}
								break;
							} else {
								result.add(valueAddDtoRegion);
							}
						}
					}
				} else {
					for (int j = 0; j < discountList.size(); j++) {
						ValueAddDto valueAddDtoDiscount = discountList.get(j);
						if(StringUtils.equals(valueAddDtoRegion.getPriceEntityCode(), valueAddDtoDiscount.getPriceEntityCode())) {
							if(valueAddDtoDiscount.getDiscountFee() != null) {
								if(valueAddDtoDiscount.getCaculateFee().doubleValue() <= valueAddDtoRegion.getCaculateFee().doubleValue()) {
									result.add(valueAddDtoDiscount);
								} else {
									result.add(valueAddDtoRegion);
								}
							} else {
								result.add(valueAddDtoRegion);
							}
						}
					}
				}
			}
			return result;
		} else {
			return discountList;
		}
	}
		
		/**
		 * 
		 * <p>
		 * Description: 查询快递产品时效 <br />
		 * </p>
		 * 
		 * @author DP-Foss-YueHongJie
		 * 
		 * @version 0.1 2012-10-25
		 * 
		 * @param originalOrgCode 出发部门
		 * 
		 * @param destinationOrgCode 到达部门
		 * 
		 * @param productCode 产品code
		 * 
		 * @parm  billDate 开单日期 可空 ，默认为当前时间
		 * 
		 * @return
		 * 
		 * List<EffectivePlanDto>
		 */
		@Override
		public List<EffectiveExpressPlanDto> searchExpressEffectivePlanDetailList(String originalOrgCode, 
				String destinationOrgCode, String productCode,Date billDate) throws BillCaculateServiceException{
			return effectiveExpressPlanDetailService.queryEffectiveExpressPlanDetailListByOrgCode(originalOrgCode, destinationOrgCode, productCode, billDate);			
			 
		} 
	 
	/**
	 * 
	 * <p>
	 * 
	 * Description:计算快递产品运费<br />
	 * 
	 * </p>
	 * 
	 * @author zdp
	 * 
	 * @version 0.1 2013-7-26
	 * 
	 * @parm queryBillCacilateDto 计算运费dto
	 * 
	 * @return
	 * 
	 *         List<ProductPriceDto>
	 * 
	 */
	@Override
	public List<ProductPriceDto> searchExpressProductPriceList(
			QueryBillCacilateDto queryBillCacilateDto)
			throws BillCaculateServiceException {
		
		/**
		 * 获取配置的快递定价开始时间
		 * 开单时间在开始时间之前的运单，按照原来的逻辑计算运费
		 * 开单时间在开始时间之后的运单，按照快递定价逻辑计算运费
		 */
	    String configMinFeeRateStartDate = configurationParamsService.queryConfValueByCode(DiscountOrgConstants.EXPRESS_PRICING_START_DATE);
	    Date startTime = null;
		if (StringUtils.isNotEmpty(configMinFeeRateStartDate)) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			//指定校验的开始时间
			try {
				startTime = sdf.parse(configMinFeeRateStartDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		//如果开单时间在配置的校验开始时间之前，则进行校验
		if(startTime!=null 
				&& queryBillCacilateDto.getReceiveDate()!=null 
				&& queryBillCacilateDto.getReceiveDate().before(startTime)){
			return searchExpressProductPriceListOld(queryBillCacilateDto);
		}
		/**
		 * 快递价格版本时间配置
		 */
		boolean versionFlag = false;
		String versionStartDate = configurationParamsService.queryConfValueByCode(DiscountOrgConstants.EXPRESS_VERSION_PRICING_START_DATE);
	    Date versionTime = null;
		if (StringUtils.isNotEmpty(versionStartDate)) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			//指定校验的开始时间
			try {
				versionTime = sdf.parse(versionStartDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		
		}
		if(versionTime!=null 
				&& queryBillCacilateDto.getReceiveDate()!=null 
				&& queryBillCacilateDto.getReceiveDate().before(versionTime)){
			versionFlag = true;
		}
		/**
		 * 1.1 根据始发部门code 获取始发区域IDT_SRV_PRICE_PLAN 1.2 根据达到部门code 获取到达区域ID 2.1
		 * 根据始发区域ID,到达区域ID, 营业部收货日期, 是否接货查询计费规则和计价方式明细列表，以ProductPriceDto对象返回；
		 */
		log.debug("FRT start calcuate>>" + new Date());
		// 数据检验
		PriceUtil.checkExpressQueryBillCacilateDtoData(queryBillCacilateDto);

		// 如果当前货物为NULL,设置货物编码为通用状态
		if (StringUtil.isEmpty(queryBillCacilateDto.getGoodsCode())) {
			queryBillCacilateDto
					.setGoodsCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
		}
		// 获得当前传入的产品、始发部门、到达部门、业务日期、币种、货物类别，是否接货
		String productCode = queryBillCacilateDto.getProductCode();
		String originalOrgCode = queryBillCacilateDto.getOriginalOrgCode();
		String destinationOrgCode = queryBillCacilateDto
				.getDestinationOrgCode();
		Date receiveDate = queryBillCacilateDto.getReceiveDate();
		String currencyCode = queryBillCacilateDto.getCurrencyCdoe();
		String goodsCode = queryBillCacilateDto.getGoodsCode();
		String isSelfPickUp = queryBillCacilateDto.getIsSelfPickUp();
		// 默认是否接货为否
		if (StringUtil.isEmpty(isSelfPickUp)) {
			isSelfPickUp = FossConstants.NO;
		}
		// 自提减的钱
		double selfPickUpSubStract = 0;

		if (FossConstants.YES.equalsIgnoreCase(isSelfPickUp)) {
			ConfigurationParamsEntity entity = configurationParamsService
					.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__BAS,
							ConfigurationParamsConstants.BAS_PARM__EXPRESS_SELF_PICKUP_SUBSTRACT,
							originalOrgCode);
			if (entity != null) {
				String parmValue = entity.getConfValue();
				try {
					selfPickUpSubStract = Double.valueOf(parmValue);
				} catch (BillCaculateServiceException e) {
					throw new BillCaculateServiceException("很抱歉，查询快递自提减免费用出错",
							e.getMessage());
				}
			} else {
				throw new BillCaculateServiceException(
						"很抱歉，因为开单时选择了自提，但是没有查询到自提直接减免的费用，我们不能为你计算出最后的费用");
			}
		}

		// 重货
		BigDecimal weight = queryBillCacilateDto.getWeight();
		// 轻货
		BigDecimal volume = queryBillCacilateDto.getVolume();
		
		//计算纯运费，在开单计算装卸费的时候用。
		BigDecimal purefreight = BigDecimal.ZERO;
		/**
		 * DMANA-7337:合同客户重泡比调整
		 * @author 200945 - wutao
		 * 通过客户编码和当前营业部收货时间去查询客户优惠折扣表，获取泡沫重泡比重字段
		 * 备注：
		 * <p>在CRM那变传递过来存在综合那边的信息只会传递零担和快递（二级），但是3.60特惠件和标准快递的二级产品是不同的【EXPRESS_RCP】和【EXPRESS】
		 * 	  重泡比不区分这俩种运输性质，因此在开单的时候选择的不论是【3.60】还是【标准快递】，在获取重泡比的时候都直接转为【EXPRESS】去获取；
		 *  <a>原因：因为这块之前在3.60的时候没有通知到CRM，因此CRM那变不会传递3.60特惠件的二级产品</a>
		 * </p>
		 *
		 */
		String prarentProductCode = "";
		if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(queryBillCacilateDto.getProductCode())
				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE.equals(queryBillCacilateDto.getProductCode())
				|| PricingConstants.ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE.equals(queryBillCacilateDto.getProductCode())
				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(queryBillCacilateDto.getProductCode())){
			prarentProductCode = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
		}
		
		BigDecimal volumeWeight = validateWeightBubbleRate(queryBillCacilateDto.getCustomerCode(),queryBillCacilateDto.getReceiveDate(),prarentProductCode,volume,queryBillCacilateDto.getReceiveOrgCode());
		
		// 查询出发区域ID
		String originalId = queryBillCacilateDto.getDeptRegionId();
		if (StringUtil.isEmpty(originalId)) {

			originalId = regionExpressService.findRegionOrgByDeptNo(
					originalOrgCode, receiveDate, null,
					PricingConstants.PRICING_REGION);
			if (StringUtil.isEmpty(originalId)) {
				return null;
			}else{
				queryBillCacilateDto.setDeptRegionId(originalId);
			}
			
		}
		// 查询目的地区域ID
		String destinationId = queryBillCacilateDto.getArrvRegionId();
		if (StringUtil.isEmpty(destinationId)
				&& StringUtil.isNotBlank(destinationOrgCode)) {
			destinationId = regionExpressService.findRegionOrgByDeptNo(
					destinationOrgCode, receiveDate, null,
					PricingConstants.PRICING_REGION);
			if (StringUtil.isEmpty(destinationId)) {
				return null;
			}else{
				queryBillCacilateDto.setArrvRegionId(destinationId);
			}
			
		}
		// 封装运费查询Bean(公用)
		QueryProductPriceDto queryProductPriceDto = new QueryProductPriceDto();
		if (null == currencyCode) {
			currencyCode = FossConstants.CURRENCY_CODE_RMB;
		}
		ProductEntity productEntity = productService.getProductByCache(
				productCode, receiveDate);
		if (productEntity == null) {
			return null;
		}
		// 根据客户端传入的三级产品得到二级产品
		productCode = productEntity.getParentCode();
		GoodsTypeEntity goodsTypeEntity = goodsTypeService.getGoodsTypeByCache(
				goodsCode, receiveDate);
		queryProductPriceDto.setProductCode(productCode);
		// 设置货币、始发区域、到达区域、航班类别、货物、是否接货、计费规则类型、状态
		queryProductPriceDto.setCurrencyCode(currencyCode);
		queryProductPriceDto.setOriginalOrgId(originalId);
		queryProductPriceDto.setDestinationId(destinationId);
		queryProductPriceDto.setGoodsTypeCode(goodsCode);
		queryProductPriceDto.setReceiveDate(receiveDate);
		queryProductPriceDto.setIsSelfPickUp(isSelfPickUp);
		queryProductPriceDto.setType(PricingConstants.VALUATION_TYPE_PRICING);// 价格定义
		queryProductPriceDto.setActive(FossConstants.ACTIVE);
		// 计费条目
		PriceEntity priceEntity = priceEntryService.getPriceEntryByCache(
				PriceEntityConstants.PRICING_CODE_FRT, receiveDate);
		if (priceEntity == null) {
			return null;
		}
		
		/**
		 * @author: 316759-王瑞鹏
		 * @date: 2017-02-21 10:21:33
		 * @Description: 专业市场计价需求
		 */
		//取出专业市场的客户活动编码
		String activeCustomerCode = queryBillCacilateDto.getActiveCustomerCode();
		if (null != activeCustomerCode) {
			queryProductPriceDto.setCustomerCode(activeCustomerCode);
			// 查询出来的快递价格方案
			List<ResultProductPriceDto> resultList = isExistCustomerExpressPrice(queryProductPriceDto);
			// 如果客户存在快递价格方案 则直接按照客户快递价格方案计算快递运费 不进行任何的折扣
			if (CollectionUtils.isNotEmpty(resultList)) {
				List<ProductPriceDto> calculateResultList = new ArrayList<ProductPriceDto>();
				// 计算运费
				calculateResultList = PriceUtil.calculateExpressCostServices(weight, volume, resultList,
						receiveDate, productEntity, goodsTypeEntity, priceEntity, selfPickUpSubStract, volumeWeight);
				if (CollectionUtils.isNotEmpty(calculateResultList)) {
					for (ProductPriceDto productPriceDto : calculateResultList) {
						if (PriceEntityConstants.PRICING_CODE_FRT.equals(productPriceDto.getPriceEntityCode())) {
							purefreight = productPriceDto.getCaculateFee();
							productPriceDto.setPurefreight(purefreight);
						}
					}
				}
				return calculateResultList;
			}
			queryProductPriceDto.setCustomerCode(null);
			queryProductPriceDto.setOriginalOrgId(originalId);
			queryProductPriceDto.setDestinationId(destinationId);
		}
		
		//U递“TIANXI”渠道的所有单子都只计算普通公布价（不考虑产品、客户，都不打折）葛亮亮2016年12月25日 11:42:20
		if("TIANXI".equals(queryBillCacilateDto.getOrderChannel())){
			List<ResultProductPriceDto> resultList = expressPriceValuationService
					.queryPriceValuationByCalculaCondition(queryProductPriceDto);
						
			if(CollectionUtils.isEmpty(resultList)){
				return null;
			}
			List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
			// 其他运输方式价格
			caculateresultList = PriceUtil.calculateExpressCostServices(weight,
								volume, resultList, receiveDate, productEntity,
								goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
			if(null!=caculateresultList&&0!=caculateresultList.size()){
			    for(ProductPriceDto p:caculateresultList){
					if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
						purefreight = p.getCaculateFee();
						p.setPurefreight(purefreight);
					}
				  }
		    }
			 
			return caculateresultList;
		}
		
		//用于存放标准快递 首重价格
		//BigDecimal standardFirstFee = null;
		//当客户编码为裹裹虚拟客户编码“470290159”时，直接读取价格方案，不打折
		if("470290159".equals(queryBillCacilateDto.getCustomerCode())){
			queryProductPriceDto.setCustomerCode("470290159");
			List<ResultProductPriceDto> resultList = isExistCustomerExpressPrice(queryProductPriceDto);
			 //如果客户存在快递价格方案 则直接按照客户快递价格方案计算快递运费 不进行任何的折扣
			 if(CollectionUtils.isNotEmpty(resultList)){
				 List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
				 //计算运费
				 caculateresultList = PriceUtil.calculateExpressCostServices(weight,
							volume, resultList, receiveDate, productEntity,
							goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
				 if(null!=caculateresultList&&0!=caculateresultList.size()){
					  for(ProductPriceDto p:caculateresultList){
					     if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
							purefreight = p.getCaculateFee();
							p.setPurefreight(purefreight);
					      }
					 }
				  }
				 return caculateresultList;
			 }
			 queryProductPriceDto.setOriginalOrgId(originalId);
				queryProductPriceDto.setDestinationId(destinationId);
				 queryProductPriceDto.setCustomerCode(null);
		}
		
		if(FossConstants.YES.equals(queryBillCacilateDto.getPartnerBillingLogo())){
			// 根据三级产品查询计算费用
			List<ResultProductPriceDto> resultList = expressPriceValuationService
					.queryPriceValuationByCalculaCondition(queryProductPriceDto);
						
			if(CollectionUtils.isEmpty(resultList)){
				return null;
			}
						
			List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
						// 其他运输方式价格
		    caculateresultList = PriceUtil.calculateExpressCostServices(weight,
								volume, resultList, receiveDate, productEntity,
								goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
			 if(null!=caculateresultList&&0!=caculateresultList.size()){
				    for(ProductPriceDto p:caculateresultList){
						if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
							purefreight = p.getCaculateFee();
							p.setPurefreight(purefreight);
						}
					  }
				    }
			return caculateresultList;
		}
		/**
		 * 商务专递不打折
		 * 232608
		 */
		List<ResultProductPriceDto> results=null;
		ConfigurationParamsEntity configEntity=configurationParamsService.queryConfigurationByCodeAndDate("DEAP_NO_DISCOUNT", queryBillCacilateDto.getReceiveDate());
		 //判断客户是否维护快递价格方案
		if("DEAP".equals(queryBillCacilateDto.getProductCode())){
			queryProductPriceDto.setCustomerCode(queryBillCacilateDto.getCustomerCode());
			results = isExistCustomerExpressPrice(queryProductPriceDto);
			//由于查询区域折扣方案的时候可能重新设置了区域ID，所以需要重新设置区域ID
			 queryProductPriceDto.setOriginalOrgId(originalId);
			 queryProductPriceDto.setDestinationId(destinationId);
			 queryProductPriceDto.setCustomerCode(null);
		}

		if("DEAP".equals(queryBillCacilateDto.getProductCode())&&//是商务专递
				null!=configEntity &&"Y".equals(configEntity.getConfValue())//且配置参数打开商务专递打折
				&&CollectionUtils.isEmpty(results)//且改产品没有单独报价
		 ){
			List<ResultProductPriceDto> resultList = expressPriceValuationService
					.queryPriceValuationByCalculaCondition(queryProductPriceDto);
						
			if(CollectionUtils.isEmpty(resultList)){
				return null;
			}
			List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
			caculateresultList = PriceUtil.calculateExpressCostServices(weight,
								volume, resultList, receiveDate, productEntity,
								goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
			 if(null!=caculateresultList&&0!=caculateresultList.size()){
				    for(ProductPriceDto p:caculateresultList){
						if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
							purefreight = p.getCaculateFee();
							p.setPurefreight(purefreight);
						}
					  }
				    }
			return caculateresultList;
		}
		
		//仿照商务专递不打折，处理快递国际件不打折（快递报关通-标（GTSE）、国际快递-标（ICES）、快递报关通-快（GTEC）、国际快递-标（ICEC）） 葛亮亮 308595 2016年5月24日 09:31:04
		if("GTSE".equals(queryBillCacilateDto.getProductCode())
				||"ICES".equals(queryBillCacilateDto.getProductCode())
				||"GTEC".equals(queryBillCacilateDto.getProductCode())
				||"ICEC".equals(queryBillCacilateDto.getProductCode())){
			queryProductPriceDto.setCustomerCode(queryBillCacilateDto.getCustomerCode());
			results = isExistCustomerExpressPrice(queryProductPriceDto);
			//由于查询区域折扣方案的时候可能重新设置了区域ID，所以需要重新设置区域ID
			queryProductPriceDto.setOriginalOrgId(originalId);
			queryProductPriceDto.setDestinationId(destinationId);
			queryProductPriceDto.setCustomerCode(null);
		}

		if(("GTSE".equals(queryBillCacilateDto.getProductCode())
				||"ICES".equals(queryBillCacilateDto.getProductCode())
				||"GTEC".equals(queryBillCacilateDto.getProductCode())
				||"ICEC".equals(queryBillCacilateDto.getProductCode()))
//				&&//是国际件
//				null!=configEntity &&"Y".equals(configEntity.getConfValue())//且配置参数打开商务专递打折
				&& //默认国际件打折配置都是打开的
				CollectionUtils.isEmpty(results)//且改产品没有单独报价。营销活动折扣都需要看（有单独保价的在下面再继续判断）			
		 ){
			//校验是否可以享有市场营销活动 2016年6月21日 10:08:27 添加
			if(queryBillCacilateDto.isPda()){
				PriceUtil.validateActiveDiscountPda(queryBillCacilateDto, markActivitiesService);
			}else{
				PriceUtil.validateActiveDiscount(queryBillCacilateDto,customerService,markActivitiesService);	
			}
			
			List<ResultProductPriceDto> resultList = expressPriceValuationService
					.queryPriceValuationByCalculaCondition(queryProductPriceDto);
						
			if(CollectionUtils.isEmpty(resultList)){
				return null;
			}
			List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
			caculateresultList = PriceUtil.calculateExpressCostServices(weight,
								volume, resultList, receiveDate, productEntity,
								goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
			 if(null!=caculateresultList&&0!=caculateresultList.size()){
			    for(ProductPriceDto p:caculateresultList){
					if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
						purefreight = p.getCaculateFee();
						p.setPurefreight(purefreight);
					}
				}
			}
			//判断是否有市场运费折扣活动并进行打折计算
			caculateresultList=doDiscountFRTExpressForActive(queryBillCacilateDto,caculateresultList);
			
			return caculateresultList;
		}
		
		/**
		 * 上门发货不打折
		 * dp-foss-dongjialing
		 */
		if(queryBillCacilateDto.isHomeDelivery()) {
			// 根据三级产品查询计算费用
			List<ResultProductPriceDto> resultList = expressPriceValuationService
								.queryPriceValuationByCalculaCondition(queryProductPriceDto);
									
						if(CollectionUtils.isEmpty(resultList)){
							return null;
						}
									
						List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
									// 其他运输方式价格
						caculateresultList = PriceUtil.calculateExpressCostServices(weight,
											volume, resultList, receiveDate, productEntity,
											goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
						 if(null!=caculateresultList&&0!=caculateresultList.size()){
							    for(ProductPriceDto p:caculateresultList){
									if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
										purefreight = p.getCaculateFee();
										p.setPurefreight(purefreight);
									}
								  }
							    }
						return caculateresultList;
		}
		
		
		/**
		 * 内部员工发货优先打折
		 * 是内部员工发货直接打折返回 
		 */
		if((WaybillConstants.DELIVERY_PAY.equals(queryBillCacilateDto.getInternalDeliveryType())
				|| WaybillConstants.RECIVE_PAY.equals(queryBillCacilateDto.getInternalDeliveryType())) && StringUtil.isNotBlank(queryBillCacilateDto.getEmployeeNo())) {
			// 根据三级产品查询计算费用
			List<ResultProductPriceDto> resultList = expressPriceValuationService
					.queryPriceValuationByCalculaCondition(queryProductPriceDto);
						
			if(CollectionUtils.isEmpty(resultList)){
				return null;
			}
						
			List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
						// 其他运输方式价格
			caculateresultList = PriceUtil.calculateExpressCostServices(weight,
								volume, resultList, receiveDate, productEntity,
								goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
			 if(null!=caculateresultList&&0!=caculateresultList.size()){
				    for(ProductPriceDto p:caculateresultList){
						if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
							purefreight = p.getCaculateFee();
							p.setPurefreight(purefreight);
						}
					  }
				    }
			//查询内部员工发货折扣方案
			InempDiscountPlanEntity discountEntity = new InempDiscountPlanEntity();
			discountEntity.setBillTime(receiveDate);
			List<InempDiscountPlanEntity> discountEntityList = inempDiscountPlanService.queryInempDiscountPanByCondition(discountEntity);
			if(CollectionUtils.isNotEmpty(discountEntityList)) {
				discountEntity = discountEntityList.get(0);
				if(discountEntity.getHighstPreferentialLimit() != null &&
						discountEntity.getHighstPreferentialLimit().compareTo(BigDecimal.ZERO)>0) {
					BigDecimal amount = queryBillCacilateDto.getAmount();
					if(amount == null) {
						amount = BigDecimal.ZERO;
					}
					/**
					 * 剩余额度大于零才进行打折
					 */
					BigDecimal	differenceValue = discountEntity.getHighstPreferentialLimit().subtract(amount.divide(BigDecimal.valueOf(NUMBER_100)));
					if(differenceValue.compareTo(BigDecimal.ZERO)>0) {
						caculateresultList=doExpressInempFRTDiscount(queryBillCacilateDto,caculateresultList,discountEntity,amount);
					}
					
				}
				
		   }
		   return caculateresultList;
		}
		
		/**
		 * 针对特殊折扣区域不打折
		 * 不读取合同折扣、市场推广活动，快递折扣方案--FOSS20151126版本需求撤销
		 */
     		WaybillSpecialDiscountEntity entity=specialDiscountDao.querySpecialDiscount(queryBillCacilateDto);
		PreferentialInfoDto preferentialInfo1 = preferentialService.queryPreferentialInfo(queryBillCacilateDto.getCustomerCode(),queryBillCacilateDto.getReceiveDate(), ProductEntityConstants.PRICING_PRODUCT_C2_C20005);		
		if((null!=entity &&preferentialInfo1!=null&&!"Y".equals(preferentialInfo1.getIsAloneQuotation()))||(null!=entity&&preferentialInfo1==null)){
			List<ResultProductPriceDto> resultList = expressPriceValuationService
					.queryPriceValuationByCalculaCondition(queryProductPriceDto);
						
			if(CollectionUtils.isEmpty(resultList)){
				return null;
			}
			List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
						// 其他运输方式价格
			caculateresultList = PriceUtil.calculateExpressCostServices(weight,
								volume, resultList, receiveDate, productEntity,
								goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
			 if(null!=caculateresultList&&0!=caculateresultList.size()){
				    for(ProductPriceDto p:caculateresultList){
						if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
							purefreight = p.getCaculateFee();
							p.setPurefreight(purefreight);
						}
					  }
				    }
			 
			//校验是否可以享有市场营销活动,国际件要求可以享受市场推广活动案  2016年7月6日 16:01:59 葛亮亮
			if(("GTSE".equals(queryBillCacilateDto.getProductCode())
					||"ICES".equals(queryBillCacilateDto.getProductCode())
					||"GTEC".equals(queryBillCacilateDto.getProductCode())
					||"ICEC".equals(queryBillCacilateDto.getProductCode()))){
				if(queryBillCacilateDto.isPda()){
					PriceUtil.validateActiveDiscountPda(queryBillCacilateDto, markActivitiesService);
				}else{
					PriceUtil.validateActiveDiscount(queryBillCacilateDto,customerService,markActivitiesService);	
				}
				//判断是否有市场运费折扣活动并进行打折计算
				caculateresultList=doDiscountFRTExpressForActive(queryBillCacilateDto,caculateresultList);
			}

			return caculateresultList;
		}
		
		/**
		 * 如果存在返货折扣则优先计算
		 */
		boolean isCaiNiao =queryBillCacilateDto.getIsCainiao()!=null?queryBillCacilateDto.getIsCainiao():false;

		if(isCaiNiao){
			
			queryBillCacilateDto.setIsCainiao(isCaiNiao);
			
			// 根据三级产品查询计算费用
			List<ResultProductPriceDto> resultList = expressPriceValuationService
					.queryPriceValuationByCalculaCondition(queryProductPriceDto);
			
			if(CollectionUtils.isEmpty(resultList)){
				return null;
			}
			
			List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
			// 其他运输方式价格
			caculateresultList = PriceUtil.calculateExpressCostServices(weight,
					volume, resultList, receiveDate, productEntity,
					goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
			 if(null!=caculateresultList&&0!=caculateresultList.size()){
				    for(ProductPriceDto p:caculateresultList){
						if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
							purefreight = p.getCaculateFee();
						}
					  }
				    }
			/**
			 *以下判断当前是否月结客户代码不会执行，可以不看
			 */
			// 如果当前是算月结客户
			if (StringUtils.equals(FossConstants.YES, queryBillCacilateDto.getIsMonthlyDate())) {
				// 根据三级产品查询计算费用
				queryProductPriceDto.setReceiveDate(receiveDate);
				List<ResultProductPriceDto> monthlyResultList = expressPriceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
				if (CollectionUtils.isEmpty(caculateresultList)) {

					// 其他运输方式价格
					caculateresultList = PriceUtil.calculateCostServices(weight, volume, monthlyResultList, receiveDate, productEntity, goodsTypeEntity, priceEntity);

				}
			}
			if (CollectionUtils.isEmpty(caculateresultList)) {
				return null;
			}

			// 如果到达部门不为空、则在价格计算完成后的基础上接着计算折扣、否则不计费算折扣、直接返回价格
			if (StringUtil.isNotBlank(destinationOrgCode)) {
				/**
				 * 计算快递折扣
				 */
				List<ProductPriceDto> list = doExpressFRTDiscountReturnGood(originalOrgCode, destinationOrgCode, receiveDate, weight, volume, originalId, destinationId, caculateresultList,
						queryBillCacilateDto);
				log.debug("FRT end calcuate>>" + new Date());
				/**
			     * 处理增值服务费CRM营销活动处理
			     */
				//list=crmActiveDiscountProcessFRTExpress(queryBillCacilateDto,list);
				return list;
			} else {			
				/**
			     * 处理增值服务费CRM营销活动处理
			     */
				caculateresultList=crmActiveDiscountProcessFRTExpress(queryBillCacilateDto,caculateresultList);
				return caculateresultList;
			}
		
		}else{
			// 根据三级产品查询价格(公用变量)
			List<ResultProductPriceDto> resultList = new ArrayList<ResultProductPriceDto>();
			//运费计算结果，未打折(公用变量)
			List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
			
			//校验是否可以享有市场营销活动(公用变量)
			if(queryBillCacilateDto.isPda()){
				PriceUtil.validateActiveDiscountPda(queryBillCacilateDto, markActivitiesService);
			}else{
				PriceUtil.validateActiveDiscount(queryBillCacilateDto,customerService,markActivitiesService);	
			}
						
			/**
			 * DMANA-10877  快递定价需求
			 * 1.非合同客户计算运费
			 * 2.合同客户计算运费(分为单独定价，非单独定价)
			 * dp-foss-dongjialing
			 * 225131
			 * 折扣方案优化
			 * 开单计算逻辑更改
			 */
			// 根据三级产品查询计算费用
			 resultList = expressPriceValuationService
											.queryPriceValuationByCalculaCondition(queryProductPriceDto);
												
			if(CollectionUtils.isEmpty(resultList)){
					return null;
			}
												
			caculateresultList = new ArrayList<ProductPriceDto>();
												// 其他运输方式价格
			caculateresultList = PriceUtil.calculateExpressCostServices(weight,
				volume, resultList, receiveDate, productEntity,
				goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
			 if(null!=caculateresultList&&0!=caculateresultList.size()){
				    for(ProductPriceDto p:caculateresultList){
						if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
							purefreight = p.getCaculateFee();
						}
					  }
				    }
			Map<String,Object> map = new HashMap<String, Object>();
			//判断客户是否维护快递区域折扣方案
			boolean isExpressDiscount = false;
			//当前客户为合同客户且当前收入部门为合同绑定部门  如果不是合同绑定部门 则按照非合同客户计算运费
			boolean isDiscount = false;
			ExpressDiscountDto resultDiscount = null;
			PreferentialInfoDto preferentialInfo = null;
			map = isExistCustomerWeekExpressDiscount(caculateresultList, queryBillCacilateDto, queryProductPriceDto, receiveDate, map);
			
			if((Boolean) map.get("isExpressDiscount") != null) {
				isExpressDiscount = (Boolean) map.get("isExpressDiscount");
			}
			if((ExpressDiscountDto)map.get("resultDiscount") != null) {
				resultDiscount = (ExpressDiscountDto)map.get("resultDiscount");
			}
			map = isCustomerBargain(queryBillCacilateDto, map);
			if((Boolean) map.get("isDiscount") != null) {
				isDiscount = (Boolean) map.get("isDiscount");
			}
			if((PreferentialInfoDto)map.get("preferentialInfo") != null) {
				preferentialInfo = (PreferentialInfoDto)map.get("preferentialInfo");
			}
			/**
			 * 合同客户运费计算
			 * 1.单独定价的价格方案优先
			 * 2.维护快递区域折扣方案不打crm合同折扣
			 * 3.有市场营销活动时，区域折扣或CRM合同折扣需和市场营销活动折扣比较取小
			 * 4.续重打折之后的费率不能低于相应的续重最低费率
			 */
			//当前客户为合同客户且当前收入部门为合同绑定部门  如果不是合同绑定部门 则按照非合同客户计算运费
			if(preferentialInfo!=null && isDiscount) {
				if(StringUtils.isNotBlank(preferentialInfo.getIsAloneQuotation())&&
						 StringUtils.equals(FossConstants.YES, preferentialInfo.getIsAloneQuotation())){
					/**
					 * 是单独定价，看是否有客户单独的价格方案
					 */
					//由于查询区域折扣方案的时候可能重新设置了区域ID，所以需要重新设置区域ID
					 queryProductPriceDto.setOriginalOrgId(originalId);
					 queryProductPriceDto.setDestinationId(destinationId);
					 //判断客户是否维护快递价格方案
					 queryProductPriceDto.setCustomerCode(queryBillCacilateDto.getCustomerCode());
					 resultList = isExistCustomerExpressPrice(queryProductPriceDto);
					 //如果客户存在快递价格方案 则直接按照客户快递价格方案计算快递运费 不进行任何的折扣
					 if(CollectionUtils.isNotEmpty(resultList)){
						 //计算运费
						 caculateresultList = PriceUtil.calculateExpressCostServices(weight,
									volume, resultList, receiveDate, productEntity,
									goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
						 if(null!=caculateresultList&&0!=caculateresultList.size()){
							  for(ProductPriceDto p:caculateresultList){
							     if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
									purefreight = p.getCaculateFee();
									p.setPurefreight(purefreight);
									if(resultDiscount != null){
										p.setExpressDiscountDto(resultDiscount);
									}
							      }
							 }
						  }
						 return caculateresultList;
						 
					 }
				}
				/**
				 * 维护了折扣方案
				 * 不走crm合同折扣
				 */
				if(resultDiscount != null && isExpressDiscount) {
					//由于查询区域价格的时候可能重新设置了区域ID，所以需要重新设置区域ID
					 queryProductPriceDto.setOriginalOrgId(originalId);
					 queryProductPriceDto.setDestinationId(destinationId);
					 queryProductPriceDto.setCustomerCode(null);
					 
					 /*添加合同客户价格时间版本(这边原先是不需要看用户版本时间，
					  悟空后来要求即使有快递折扣也需要先按照用户版本时间算出公布价运费) 2016年7月15日 17:13:36  葛亮亮 */
					 //先清空对象 
					 resultList = null;
					 //获取最终的公布价信息
					 resultList = getCustomerFrtPriceDto(resultList, versionFlag, preferentialInfo, 
							   					queryBillCacilateDto, queryProductPriceDto);
					 
					 //如果还未空直接返回 NULL
					 if(CollectionUtils.isEmpty(resultList)){
						return null;
					 }
					//根据公布价计算快递运费
					caculateresultList = PriceUtil.calculateExpressCostServices(weight,
							volume, resultList, receiveDate, productEntity,
							goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
					 if(null!=caculateresultList&&0!=caculateresultList.size()){
						  for(ProductPriceDto p:caculateresultList){
						     if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
								purefreight = p.getCaculateFee();
								p.setPurefreight(purefreight);
								if(resultDiscount != null){
									p.setExpressDiscountDto(resultDiscount);
								}
						      }
						 }
					  }
					/**
					 * 区域折扣方案打折
					 */
					caculateresultList = caculateExpressFRTByExpressDiscount(caculateresultList,resultDiscount,queryBillCacilateDto);
				} else {
					/**
					 * 没有维护区域折扣方案，按照crm合同打折
					 * 
					 * 业务逻辑：
				     * 合同客户开单：
							1.	该客户合同是否单独定价为“否”，存在首重、续重折扣
								b)	存在市场推广活动运费折扣时，
									① 客户合同首重、续重折扣一致时，运费折扣和市场推广活动折扣值对比取小，且折后续重费率不能低于相应的续重最低费率（历史合同客户不判断续重最低费率）
									② 客户合同首重、续重折扣不一致时，不论是否存在市场活动，都以合同折扣为准，且不能低于合同续重最低费率（历史合同客户不判断续重最低费率）
						  2. 该客户合同是否单独定价为“否”，不存在首重、续重折扣时，运费计算同非合同客户开单						
					 */
					//查询公布价
					 queryProductPriceDto.setOriginalOrgId(originalId);
					 queryProductPriceDto.setDestinationId(destinationId);
					 queryProductPriceDto.setCustomerCode(null);					 
					 
					 /*处理用客户合同版本时间计算公布价运费，
					   如果按照客户版本时间未能计算出费用，按照运单时间计算 2016年7月15日 17:52:20 葛亮亮*/
					 //先清空对象 
					 resultList = null;
					 //获取最终的公布价信息
					 resultList = getCustomerFrtPriceDto(resultList, versionFlag, preferentialInfo, 
							   					queryBillCacilateDto, queryProductPriceDto);
					 
					 //如果还未空，直接返回NULL
					 if(CollectionUtils.isEmpty(resultList)){
						return null;
					 }
					//根据公布价计算快递运费
					caculateresultList = PriceUtil.calculateExpressCostServices(weight,
							volume, resultList, receiveDate, productEntity,
							goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
					 if(null!=caculateresultList&&0!=caculateresultList.size()){
						  for(ProductPriceDto p:caculateresultList){
						     if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
								purefreight = p.getCaculateFee();
								if(resultDiscount != null){
									p.setExpressDiscountDto(resultDiscount);
								}
						      }
						 }
					  }
					//计算折扣
					caculateresultList = handleExpressFRTDiscountAndCrmActiveDiscount( queryBillCacilateDto.getOriginalOrgCode(),queryBillCacilateDto.getDestinationOrgCode(), queryBillCacilateDto.getReceiveDate(), queryBillCacilateDto.getWeight(),
		    				  queryBillCacilateDto.getVolume(),queryBillCacilateDto.getDeptRegionId(),queryBillCacilateDto.getArrvRegionId(),caculateresultList,queryBillCacilateDto);
				}
			} else {
				/**
				 * 非合同客户运费计算
				 */
				if(resultDiscount != null && isExpressDiscount) {
					//由于查询区域价格的时候可能重新设置了区域ID，所以需要重新设置区域ID
					 queryProductPriceDto.setOriginalOrgId(originalId);
					 queryProductPriceDto.setDestinationId(destinationId);
					 queryProductPriceDto.setCustomerCode(null);
					 //查询公布价
					 resultList = expressPriceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
						if(CollectionUtils.isEmpty(resultList)){
							return null;
						}
					//根据公布价计算快递运费
					caculateresultList = PriceUtil.calculateExpressCostServices(weight,
							volume, resultList, receiveDate, productEntity,
							goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
					 if(null!=caculateresultList&&0!=caculateresultList.size()){
						  for(ProductPriceDto p:caculateresultList){
						     if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
								purefreight = p.getCaculateFee();
								if(resultDiscount != null){
									p.setExpressDiscountDto(resultDiscount);
								}
						      }
						 }
					  }
					/**
					 * 区域折扣方案打折
					 */
					caculateresultList = caculateExpressFRTByExpressDiscount(caculateresultList,resultDiscount,queryBillCacilateDto);
				} else {
					resultList = expressPriceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
					if(CollectionUtils.isEmpty(resultList)){
						return null;
					}
					// 其他运输方式价格
					caculateresultList = PriceUtil.calculateExpressCostServices(weight,
							volume, resultList, receiveDate, productEntity,
							goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
					 if(null!=caculateresultList&&0!=caculateresultList.size()){
						  for(ProductPriceDto p:caculateresultList){
						     if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
								purefreight = p.getCaculateFee();
								if(resultDiscount != null){
									p.setExpressDiscountDto(resultDiscount);
								}
						      }
						 }
					  }
					caculateresultList=doDiscountFRTExpressForActive(queryBillCacilateDto,caculateresultList);
				}
			}
			
			//添加纯运费   265041
			if(null!=caculateresultList&&0!=caculateresultList.size()){
			for(ProductPriceDto p:caculateresultList){
				if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
					p.setPurefreight(purefreight);
				}
			 }
			}
			return caculateresultList;
		}		
	}

	/**
	 * 合同客户先按照客户合同版本计算公布价，如果按照版本时间获取不到价格，则按照运单时间获取
	 * 2016年7月15日 17:45:50 葛亮亮
	 */
	public List<ResultProductPriceDto> getCustomerFrtPriceDto(List<ResultProductPriceDto> resultList,
		   boolean versionFlag, PreferentialInfoDto preferentialInfo, 
		   QueryBillCacilateDto queryBillCacilateDto, QueryProductPriceDto queryProductPriceDto) {
		 //是否需要走版本时间
		 if(!versionFlag) {
			Date versionDate = preferentialInfo.getExPriceVersionDate();
			if(versionDate != null) {
				queryProductPriceDto.setReceiveDate(versionDate);
				//按照用户合同版本时间查询公布价
				resultList = expressPriceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto); 
			}
		 }
		 //如果为空，将时间设置成运单时间
		 if(CollectionUtils.isEmpty(resultList)){
			queryProductPriceDto.setReceiveDate(queryBillCacilateDto.getReceiveDate());
			//按照用户合同版本时间查询公布价
			resultList = expressPriceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
		 }
		 
		 return resultList;
	}
	
	
	/**
	 * dp-foss-dongjialing
	 * 225131
	 * 判断是否维护区域周特惠折扣方案
	 * 折扣方案开单计算优化
	 */
	public Map<String,Object> isExistCustomerWeekExpressDiscount(List<ProductPriceDto> caculateresultList,
			QueryBillCacilateDto queryBillCacilateDto,QueryProductPriceDto queryProductPriceDto
			,Date receiveDate,Map<String,Object> keyMap) {
		ProductPriceDto resultPrice = null;
	  	  //获取运费
			  if(CollectionUtils.isNotEmpty(caculateresultList)){
				for (int i = 0; i < caculateresultList.size(); i++) { 
					ProductPriceDto price=caculateresultList.get(i);
					if(price!=null && PriceEntityConstants.PRICING_CODE_FRT.equals(price.getPriceEntityCode())){
						queryBillCacilateDto.setTransportFee(price.getCaculateFee());
						resultPrice = price;
						break;
					}
				}
			  }
		 ExpressDiscountDto expressDiscountDto = new ExpressDiscountDto();		 
		 expressDiscountDto.setCustomerCode(queryBillCacilateDto.getCustomerCode());
		 expressDiscountDto.setArriveRegionCode(queryProductPriceDto.getDestinationId());
		 expressDiscountDto.setStartRegionCode(queryProductPriceDto.getOriginalOrgId());
		 expressDiscountDto.setGoodsTypeCode(queryProductPriceDto.getGoodsTypeCode());
		 expressDiscountDto.setActive(FossConstants.ACTIVE);
		 //由于快递折扣方案中存储的是三级产品   此处需要传递开单时选择的运输性质
		 expressDiscountDto.setProductCode(queryBillCacilateDto.getProductCode());
		 expressDiscountDto.setBillTime(receiveDate);
		 List<ExpressDiscountDto> discountResultList = new ArrayList<ExpressDiscountDto>();
		 discountResultList = isExistCustomerExpressDiscount(expressDiscountDto);
		 if(resultPrice!= null && CollectionUtils.isNotEmpty(discountResultList)){
			 ExpressDiscountDto resultDiscount = new ExpressDiscountDto();
			  
			  for(ExpressDiscountDto discountDto:discountResultList){
				  //快递区域折扣范围为左闭区间  右开区间
				  if(discountDto.getLeftRange().compareTo(resultPrice.getVolumeWeight())<=0 &&
						  discountDto.getRightRange().compareTo(resultPrice.getVolumeWeight())>0){
					  resultDiscount = discountDto;
					  break;
				  }
			  }			 
			  //根据选择的折扣明细查询对应的折扣方案
			  ExpressDiscountEntity discountPlanEntity = new ExpressDiscountEntity();
			  discountPlanEntity.setId(resultDiscount.getExpressDiscountPlanId());
			  discountPlanEntity = expressDiscountPlanService.queryExpressDiscountPlanById(discountPlanEntity);
			  /**
			   * 判断当前开单时间是不是周特惠方案
			   */
			  Map<String,String> map = weekMap();
			  Calendar cal = Calendar.getInstance();
			  cal.setTime(queryBillCacilateDto.getReceiveDate());
			  String weekDay = map.get(cal.get(Calendar.DAY_OF_WEEK)+"");
			  if((discountPlanEntity != null && StringUtil.isNotBlank(discountPlanEntity.getWeekCodes())
					  && !("ALL").equals(discountPlanEntity.getWeekCodes()) && discountPlanEntity.getWeekCodes().contains(weekDay))
					  || (discountPlanEntity != null && ("ALL").equals(discountPlanEntity.getWeekCodes()))) {
				  keyMap.put("resultDiscount", resultDiscount);
				  keyMap.put("isExpressDiscount", true);
			  }
				  
					
		 }
		 return keyMap;
	}
	/**
	 * 判断是否合同客户
	 * dp-foss-dongjialing
	 * 225131
	 * 折扣方案开单计算优化
	 */
	public Map<String,Object> isCustomerBargain(QueryBillCacilateDto queryBillCacilateDto,Map<String,Object> map) {
		//查询当前客户是否为合同客户
		PreferentialInfoDto preferentialInfo = preferentialService.queryPreferentialInfo(queryBillCacilateDto.getCustomerCode(),queryBillCacilateDto.getReceiveDate(), ProductEntityConstants.PRICING_PRODUCT_C2_C20005);		
		if(preferentialInfo!=null){
			List<BargainAppOrgEntity> bargainAppOrgEntities=null;
			//根据合同客户中的CRM_ID查询合同的绑定部门
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(queryBillCacilateDto.getReceiveOrgCode());
			if(orgAdministrativeInfoEntity!=null){
				bargainAppOrgEntities = bargainAppOrgService
							.queryAppOrgByBargainCrmIdAndUnifiedCode(preferentialInfo.getCusBargainId(),orgAdministrativeInfoEntity.getUnifiedCode());
			}
			
			
			if (CollectionUtils.isNotEmpty(bargainAppOrgEntities)) {
						map.put("isDiscount", true);
						map.put("preferentialInfo", preferentialInfo);					
			    }
			}
		return map;
	}	
	
	/**
	 * 返单折扣计算.
	 */
	@Override
	public List<ProductPriceDto> doExpressFRTDiscountReturnGood(String originalOrgCode,
		String destinationOrgCode, Date receiveDate, BigDecimal weight,
		BigDecimal volume, String originalId, String destinationId,
		List<ProductPriceDto> caculateresultList,QueryBillCacilateDto queryBillCacilateDto) {
		
	    List<ProductPriceDto> discountResultList = new ArrayList<ProductPriceDto>();
	    //获取折扣优先级
	    List<DiscountPriorityEntity> discountPriorityEntities = discountPriorityService.getDiscountPriorityByCache();
	    if(CollectionUtils.isNotEmpty(caculateresultList)) {
	    	boolean flag = false;
	    	for (int i = 0; i < caculateresultList.size(); i++) {
	    		flag = false;
	    		ProductPriceDto productPriceDto = caculateresultList.get(i);
	    		//是否按照优先级处理
	    		if(CollectionUtils.isNotEmpty(discountPriorityEntities)) {
	    			DiscountParmDto discountParmDto = new DiscountParmDto();
	    		    //出发部门
	    		    OrgAdministrativeInfoEntity deptOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(originalOrgCode);
	    		    //到达部门
	    		    OrgAdministrativeInfoEntity arrvOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(destinationOrgCode);
	    		    //匹配出发、到达折扣的城市信息
	    		    if(deptOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(deptOrgAdministrativeInfoEntity.getCityCode())) {
	    		    	 discountParmDto.setDeptCityCode(deptOrgAdministrativeInfoEntity.getCityCode());
	    		    }
	    		    if(arrvOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(arrvOrgAdministrativeInfoEntity.getCityCode())) {
	    		    	discountParmDto.setArrvCityCode(arrvOrgAdministrativeInfoEntity.getCityCode());
	    		    }
	    		    //依次设置折扣信息的出发区域、到达区域、始发部门、到达部门、计价条目信息、
	    		    //产品、货物、业务日期、重货、轻货、原始费用、计费类别
	    		    discountParmDto.setCriteriaDetailId(productPriceDto.getId());
	    		    discountParmDto.setDeptRegionId(originalId);
	    		    discountParmDto.setArrvRegionId(destinationId);
	    		    discountParmDto.setOriginalOrgCode(originalOrgCode);
	    		    discountParmDto.setDestinationOrgCode(destinationOrgCode);
	    		    discountParmDto.setPricingEntryCode(productPriceDto.getPriceEntityCode());
	    		    discountParmDto.setPricingEntryName(productPriceDto.getPriceEntityName());
	    		    discountParmDto.setProductCode(productPriceDto.getProductCode());
	    		    discountParmDto.setGoodsTypeCode(productPriceDto.getGoodsTypeCode());
	    		    discountParmDto.setReceiveDate(receiveDate);
	    		    discountParmDto.setWeight(weight);
	    		    discountParmDto.setVolume(volume);
	    		    //计算后的价格
	    		    discountParmDto.setOriginnalCost(productPriceDto.getCaculateFee());
	    		    discountParmDto.setCustomCode(queryBillCacilateDto.getCustomerCode());
					discountParmDto.setIndustryCode(queryBillCacilateDto.getIndustrulCode());
					discountParmDto.setMinFee(productPriceDto.getMinFee());
					discountParmDto.setMaxFee(productPriceDto.getMaxFee());
					//计费类型 体积 、重量、费用
					discountParmDto.setCaculateType(productPriceDto.getCaculateType());
					discountParmDto.setSaleChannelCode(queryBillCacilateDto.getChannelCode());
	    		    DiscountResultDto discountResult=null;
	    		    
	    		    //菜鸟折扣条件 是否返单折扣、返单号发货人客户编码、返单号 
	    		    discountParmDto.setIsCainiao(queryBillCacilateDto.getIsCainiao());
	    		    discountParmDto.setOldreceiveCustomerCode(queryBillCacilateDto.getOldreceiveCustomerCode());
	    		    discountParmDto.setReturnWaybillNo(queryBillCacilateDto.getReturnWaybillNo());
	    		    discountParmDto.setReturnInsuranceFee(queryBillCacilateDto.getReturnInsuranceFee());
	    		    discountParmDto.setReturnTransportFee(queryBillCacilateDto.getReturnTransportFee());
	    		    discountParmDto.setReturnbilltime(queryBillCacilateDto.getReturnbilltime());
	    		    discountParmDto.setOriginalReceiveOrgCode(queryBillCacilateDto.getOriginalReceiveOrgCode());
	    		    //对于合同客户优先打折计算
	    			for (int j = 0; j < discountPriorityEntities.size(); j++) {
	    				DiscountPriorityEntity entity = discountPriorityEntities.get(j);
	    				if(entity != null && entity.getCode() != null) {
	    					if(StringUtil.equals(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT, entity.getCode())) {
    							DiscountTypeInterface discountExe = discountTypeFactory.getDiscountTypeImpl(entity.getCode());
        						if(discountExe != null) {
        							//获取客户折扣操作类(快递)
    								discountResult = discountExe.doExpressDiscount(discountParmDto);
        							//返货折扣
        							if(discountParmDto!=null && discountParmDto.getIsCainiao()){
        								if(discountResult != null ) {
        								  if(discountResult.getOriginnalCost()!=null){
        									  discountParmDto.setOriginnalCost(discountResult.getOriginnalCost());
        								    }
        								  }
        							}
        							//折扣存在,切折扣率不等于1
        							if(discountResult != null ) {
        								//boolean isExpress = productService.onlineDetermineIsExpressByProductCode(discountParmDto.getProductCode(), discountParmDto.getReceiveDate());
        								//计算折扣
        								DiscountResultDto discountResultDto = PriceUtil.calculateCustomDiscountClientData(discountResult, discountParmDto, true);								
        								//折扣费率
	    								BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
	    								//保留原有费率
        								productPriceDto.setInitFeeRate(actualFeeRate);
        								
        								// 获取客户合同优惠信息
        								String productCodeTemp = null;
        								if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(queryBillCacilateDto.getProductCode())||
        										ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(queryBillCacilateDto.getProductCode())){
        									productCodeTemp = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
        								}
        								
        								 PreferentialInfoDto  preferentialInfo = preferentialService
        											.queryPreferentialInfo(queryBillCacilateDto.getOldreceiveCustomerCode(),
        													queryBillCacilateDto.getReturnbilltime(),productCodeTemp);
        								 
        								//根据合同客户中的CRM_ID查询合同的绑定部门
        									List<BargainAppOrgEntity> bargainAppOrgEntities = null;
        									if(preferentialInfo!=null){
        										OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(queryBillCacilateDto.getOriginalReceiveOrgCode());
        										if(orgAdministrativeInfoEntity!=null){
        											bargainAppOrgEntities = bargainAppOrgService
            												.queryAppOrgByBargainCrmIdAndUnifiedCode(preferentialInfo.getCusBargainId(),orgAdministrativeInfoEntity.getUnifiedCode());
        										}
        										
        									}
        								
        								/**
        	        				     *@author YANGKANG
        	        				     *DMANA-3936  快递运费折扣率调整 
        	        				     *打折之后的费率不能小于配置的最低费率
        	        				     */
        								 //获取配置的开始运费费率打折限制的时间
        							     String configMinFeeRateStartDate = configurationParamsService.queryConfValueByCode(DiscountOrgConstants.EXPRESS_MIN_FEERATE_START_DATE);
        							     Date startTime = null;
        								if (StringUtils.isNotEmpty(configMinFeeRateStartDate)) {
        									SimpleDateFormat sdf = new SimpleDateFormat(
        											"yyyy-MM-dd HH:mm:ss");
        									// 指定校验的开始时间
        									try {
        										startTime = sdf.parse(configMinFeeRateStartDate);
        									} catch (ParseException e) {
        										e.printStackTrace();
        									}

        								}
        								//如果开单时间在配置的校验开始时间之后，则进行校验
        								if(startTime!=null 
        										&& receiveDate!=null 
        										&& receiveDate.after(startTime)){
        									limitDiscountFeeRate(discountResultDto,productPriceDto);
        								}else{
        									//将折扣价格赋值原计算后价格
            								productPriceDto.setCaculateFee(discountResultDto.getDiscountValue());
            								//将折扣价格赋值折扣价格
            								productPriceDto.setDiscountFee(discountResultDto.getDiscountValue());
            								//保留计价明细ID
            								productPriceDto.setId(discountResultDto.getId()); 
            								//重新设置费率
    	    								productPriceDto.setActualFeeRate(discountResultDto.getDiscountRate().multiply(actualFeeRate));
        								}
        								if(CollectionUtils.isNotEmpty(bargainAppOrgEntities) && preferentialInfo!=null && PriceEntityConstants.OLD_PREFE.equals(preferentialInfo.getExpBackFreghtType())) {
    										//重新计算运费
    										calculateDiscountFeeRate(discountResultDto, productPriceDto, preferentialInfo.getChargeRebate(), preferentialInfo.getContinueHeavyDiscount(), preferentialInfo.getContinueHeavyLowestRate());
        								}		  
        								//保留折扣相关信息
        								productPriceDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
        								discountResultList.add(productPriceDto);
    									flag = true;
    									break;
        							} else {
        								continue;
        							}
        						} else {
        							continue;
        						}
    						} 
	    				} else {
	    					continue;
	    				}
	    			}
	    			if(!flag) {
		    			discountResultList.add(productPriceDto);
		    		}
	    		} else {
	    			discountResultList.add(productPriceDto);
	    		}
	    	} 
	    }
    	
	    return discountResultList;
	    /**  BillCaculateService 是开单计算价格、增值服务、折扣、优惠活动的实现类
	     * 
		 *  计算运费分别为：FRT
		 *  
		 *  汽运  - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 0
		 *  
		 *  空运 - t_srv_pice_plan价格表  字段 TRANSPORT_FLAG = 1
		 *  
		 *  
		 *  计算增值服务：  VAS
		 *  
		 *  保费 - BF
		 *  
		 *  代收货款 - HK
		 *  
		 *  送货费 - SH
		 *  
		 *  接货费 - JH
		 *  
		 *  签收回单 -QS
		 *  
		 *  保管费 - CCF
		 *  
		 *  综合信息费 -ZHXX
		 *  
		 *  燃油附加费 - RYFJ
		 *  
		 *  中转费 - ZZ
		 *  
		 *  接货差额补差 -JHCEBC
		 *  
		 *  电子优惠券 - DZYHQ
		 *  
		 *  其他费用 -QT
		 *  
		 *  包装费 -BZ
		 *  
		 *  送货上楼费 -SHSL
		 *  
		 *  超远派送费 -CY
		 *  
		 *  送货进仓费 -SHJCF
		 *  
		 *  更改费 -GGF
		 *  
		 *  以上服务相关代码进行不同维度计算。
		 *  
		 *  可支绑定如产品、区域 等等进行价格、折扣、优惠计算
		 */
	}
			
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService.limitDiscountFeeRate
	 * @Description:快递运费打折费率最低限制
	 *
	 * @param discountResultDto
	 * @param productPriceDto
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-10-25 上午9:55:04
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-10-25    DP-FOSS-YANGKANG      v1.0.0         create
	 */
 @Override
 public void limitDiscountFeeRate(DiscountResultDto discountResultDto,
         ProductPriceDto productPriceDto) {
	 
     //获取续重区间打折之后的费率
     BigDecimal firstDiscountRate =  discountResultDto.getDiscountRate().multiply(productPriceDto.getFirstTempRate());
     BigDecimal secondDiscountRate =  discountResultDto.getDiscountRate().multiply(productPriceDto.getSecondTempRate());
     //获取配置的最低运费费率
     String configMinFeeRateStr = configurationParamsService.queryConfValueByCode(DiscountOrgConstants.PKP_EXPRESS_MIN_FEERATE);
     BigDecimal discountTotalFee = BigDecimal.ZERO;
     if(StringUtils.isNotBlank(configMinFeeRateStr)){
         //由于保存数据在数据库中存储不一致 需将配置的最低费率*100转换之后进行等价的计算
         BigDecimal configMinFeeRate =  new BigDecimal(configMinFeeRateStr).multiply(BigDecimal.valueOf(NUMBER_100));
         if((firstDiscountRate.compareTo(BigDecimal.ZERO)>0&&firstDiscountRate.compareTo(configMinFeeRate)<0)||
                 (secondDiscountRate.compareTo(BigDecimal.ZERO)>0&&secondDiscountRate.compareTo(configMinFeeRate)<0)){
             
            if(firstDiscountRate.compareTo(BigDecimal.ZERO)>0&&firstDiscountRate.compareTo(configMinFeeRate)<0){
                    firstDiscountRate = configMinFeeRate;   
            }
            if(secondDiscountRate.compareTo(BigDecimal.ZERO)>0&&secondDiscountRate.compareTo(configMinFeeRate)<0){
                    secondDiscountRate = configMinFeeRate;
            } 
            //重新计算运费   需要将计算出的总运费/100
            BigDecimal discountFirstFee = productPriceDto.getFirstRateFee().multiply(discountResultDto.getDiscountRate());
            BigDecimal firstWeightFee = productPriceDto.getFirstWeight().multiply(firstDiscountRate);
            BigDecimal secondWeightFee = productPriceDto.getSecondWeight().multiply(secondDiscountRate);
            discountTotalFee =discountFirstFee.add(firstWeightFee).add(secondWeightFee);
            discountTotalFee = discountTotalFee.divide(BigDecimal.valueOf(NUMBER_100));
            discountResultDto.setDiscountValue(discountTotalFee);
            //重新计算优惠金额  原始费用与当前运费的差值
            BigDecimal reduceFee = productPriceDto.getCaculateFee().subtract(discountTotalFee);
            if(reduceFee.compareTo(BigDecimal.ZERO)>0){
            	discountResultDto.getDiscountPrograms().get(0).setReduceFee(reduceFee);
            }
         }  
     }
     
     //将折扣价格赋值原计算后价格
     productPriceDto.setCaculateFee(discountResultDto.getDiscountValue());
     //将折扣价格赋值折扣价格
     productPriceDto.setDiscountFee(discountResultDto.getDiscountValue());
     //保留计价明细ID
     if(StringUtils.isNotBlank(discountResultDto.getId())){
         productPriceDto.setId(discountResultDto.getId());
     }
     //重新赋值折扣费率
     BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
     BigDecimal discountActualFeeRate = discountResultDto.getDiscountRate().multiply(actualFeeRate);
     //重新赋值折扣费率  如果当前费率小于配置的最低费率 则显示最低的配置费率
     if(StringUtils.isNotBlank(configMinFeeRateStr)){
         BigDecimal configMinFeeRate =  new BigDecimal(configMinFeeRateStr).multiply(BigDecimal.valueOf(NUMBER_100));
         if(discountActualFeeRate.compareTo(configMinFeeRate)<0){
             discountActualFeeRate = configMinFeeRate;
         }
     }
     productPriceDto.setActualFeeRate(discountActualFeeRate);
 }
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService.limitDiscountFeeRate
	 * @Description:快递运费打折费率最低限制  用于crm打折活动中
	 *
	 * @param discountResultDto
	 * @param productPriceDto
	 * @param receiveDate  开单时间
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-10-26 上午9:55:04
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-10-26    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public void limitDiscountFeeRateCompare(ProductPriceDto productPriceDto,PriceDiscountDto priceDiscountDto) {
	
		 //获取配置的最低运费费率
	     String configMinFeeRateStr = configurationParamsService.queryConfValueByCode(DiscountOrgConstants.PKP_EXPRESS_MIN_FEERATE);
		//获取续重区间打折之后的费率
	     BigDecimal firstDiscountRate =  priceDiscountDto.getDiscountRate().multiply(productPriceDto.getFirstTempRate());
	     BigDecimal secondDiscountRate =  priceDiscountDto.getDiscountRate().multiply(productPriceDto.getSecondTempRate());
	     
	     BigDecimal discountTotalFee = BigDecimal.ZERO;
	     if(StringUtils.isNotBlank(configMinFeeRateStr)){
	         //由于保存数据在数据库中存储不一致 需将配置的最低费率*100转换之后进行等价的计算
	         BigDecimal configMinFeeRate =  new BigDecimal(configMinFeeRateStr).multiply(BigDecimal.valueOf(NUMBER_100));
	         if((firstDiscountRate.compareTo(BigDecimal.ZERO)>0&&firstDiscountRate.compareTo(configMinFeeRate)<0)||
	                 (secondDiscountRate.compareTo(BigDecimal.ZERO)>0&&secondDiscountRate.compareTo(configMinFeeRate)<0)){
	             
	            if(firstDiscountRate.compareTo(BigDecimal.ZERO)>0&&firstDiscountRate.compareTo(configMinFeeRate)<0){
	                    firstDiscountRate = configMinFeeRate;   
	            }
	            if(secondDiscountRate.compareTo(BigDecimal.ZERO)>0&&secondDiscountRate.compareTo(configMinFeeRate)<0){
	                    secondDiscountRate = configMinFeeRate;
	            } 
	            //重新计算运费   需要将计算出的总运费/100
	            BigDecimal discountFirstFee = productPriceDto.getFirstRateFee().multiply(priceDiscountDto.getDiscountRate());
	            BigDecimal firstWeightFee = productPriceDto.getFirstWeight().multiply(firstDiscountRate);
	            BigDecimal secondWeightFee = productPriceDto.getSecondWeight().multiply(secondDiscountRate);
	            discountTotalFee =discountFirstFee.add(firstWeightFee).add(secondWeightFee);
	            discountTotalFee = discountTotalFee.divide(BigDecimal.valueOf(NUMBER_100));
	           
	            /**
	             * 需要重新折扣的优惠金额  
	             * 可以由原来的优惠金额和折扣获取到原始费用
	             */
	            if(priceDiscountDto.getReduceFee().compareTo(BigDecimal.ZERO)>0){
	            	BigDecimal originnalCost = priceDiscountDto.getReduceFee().divide(BigDecimal.ONE.subtract(priceDiscountDto.getDiscountRate()));
           	    BigDecimal reduceFee = originnalCost.subtract(discountTotalFee);
		            if(reduceFee.compareTo(BigDecimal.ZERO)>0){
		            	priceDiscountDto.setReduceFee(reduceFee);
		            }
	            }
	          
	            //重新设置运费
	            productPriceDto.setDiscountFee(discountTotalFee);
	            productPriceDto.setCaculateFee(discountTotalFee);
	            			    
	         	}
	         }
	}
 
	/**
	 * <p>
	 * 计算增快递产品值服务费
	 * </p>
	 * 
	 * @author DP-Foss-zdp
	 * 
	 * @date 2013-7-26 下午3:41:05
	 * 
	 * @param originalOrgCode
	 *            出发部门
	 * 
	 * @return
	 * 
	 * @see 计算规则：
	 * 
	 *      （1）查询基础增值服务费率进行价格计算，如果未查询到，则直接返回空。
	 * 
	 *      （2）查询区域或是产品增值服务费率进行价格计算，区域优先于产品，即当查询到区域增值服务费率之后，将不再查询产品。
	 * 
	 *      （3）如果符合增值优惠的条件，对基础增值服务计算出的价格进行打折。
	 * 
	 *      （4）将折扣后的价格与以区域或是产品增值服务费率计算出的价格进行比较，选取优惠幅度最大的作为最终价格。
	 * 
	 *      （5）在普通客户合同、渠道、产品折扣的情况下，最终计算出的价格不能低于价格最低一票。
	 * 
	 */
	@Override
	public List<ValueAddDto> searchExpressValueAddPriceList(
			QueryBillCacilateValueAddDto queryBillCacilateValueAddDto)
			throws BillCaculateServiceException {
		/**
		 * 1 根据始发部门code 获取始发区域ID
		 * 
		 * 
		 * 2 根据到达部门code 获取到达区域ID
		 * 
		 * 
		 * 3 查询各种增值服务的费率，计算费用
		 * 
		 * 
		 * 3.1 查询区域增值服务
		 * 
		 * 
		 * 
		 * 3.2 查询产品增值服务 去掉第一步已经查到的增值服务信息
		 * 
		 * 
		 * 
		 * 3.3 查询基础增值服务 去掉第二步和第一步已经查到的增值服务
		 * 
		 * 
		 * 
		 * 
		 * 根据传入的重量，体积，费用等 计算结果
		 */
		//20140418		
		//如果是保费和代收货款此处插入大礼包增值服务计价searchDLBValueAddPriceList(queryBillCacilateValueAddDto);	
		if (queryBillCacilateValueAddDto != null && (StringUtil.isNotEmpty(queryBillCacilateValueAddDto.getCityMarketCode())&&(
					 StringUtil.equalsIgnoreCase(
								queryBillCacilateValueAddDto.getPricingEntryCode(),
								PriceEntityConstants.PRICING_CODE_BF)
								|| StringUtil.equalsIgnoreCase(	queryBillCacilateValueAddDto.getPricingEntryCode(),
										PriceEntityConstants.PRICING_CODE_HK)))) {
			List<ValueAddDto> resultDLBList = searchDLBValueAddPriceList(queryBillCacilateValueAddDto);
			return resultDLBList;
		}
		if (queryBillCacilateValueAddDto == null) {
			return null;
		}
		BigDecimal originnalCost = queryBillCacilateValueAddDto
				.getOriginnalCost();
		// 校验输入参数
		PriceUtil
				.checkExpressQueryBillCacilateValueAddDtoDate(queryBillCacilateValueAddDto);
		// field
		String originalOrgCode = queryBillCacilateValueAddDto
				.getOriginalOrgCode();
		String destinationOrgCode = queryBillCacilateValueAddDto
				.getDestinationOrgCode();
		Date receiveDate = queryBillCacilateValueAddDto.getReceiveDate();
		String productCode = queryBillCacilateValueAddDto.getProductCode();
		queryBillCacilateValueAddDto.setActive(FossConstants.ACTIVE);
		if (StringUtil.isEmpty(queryBillCacilateValueAddDto.getGoodsTypeCode())) {
			queryBillCacilateValueAddDto
					.setGoodsTypeCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
		} else {
			queryBillCacilateValueAddDto
					.setGoodsTypeCode(queryBillCacilateValueAddDto
							.getGoodsTypeCode());
		}
		//String goodsTypeCode = queryBillCacilateValueAddDto.getGoodsTypeCode();
		// String priceEntityCode =
		// queryBillCacilateValueAddDto.getPriceEntityCode();
		// 处理客户端以元为单位的原始费用化成分
		if (originnalCost != null) {
			originnalCost = new BigDecimal(String.valueOf(originnalCost
					.doubleValue() * PricingConstants.YUTOFEN));
			queryBillCacilateValueAddDto.setOriginnalCost(originnalCost);
		}
		// 保费特殊处理保费默认值问题
		if (null == originnalCost
				&& StringUtil.equalsIgnoreCase(
						queryBillCacilateValueAddDto.getPricingEntryCode(),
						PriceEntityConstants.PRICING_CODE_BF)) {
			ConfigurationParamsEntity entity = configurationParamsService
					.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
							ConfigurationParamsConstants.PKP_PARM__DEFAULT_INSURANCE_AMOUNT,
							originalOrgCode);
			if (entity != null) {
				String parmValue = entity.getConfValue();
				try {
					originnalCost = new BigDecimal(String.valueOf(new Double(
							parmValue) * PricingConstants.YUTOFEN));
					queryBillCacilateValueAddDto
							.setOriginnalCost(originnalCost);

				} catch (BillCaculateServiceException e) {
					throw new BillCaculateServiceException(
							"很抱歉，查询出发部门对应的默认保费时出现异常信息", e.getMessage());
				}
			} else {
				throw new BillCaculateServiceException(
						"很抱歉，因开单时你没有传入保费的声明价值费用,并且出发部门也没有对应的默认声明价值费用，我们不能为你计算出最后的保费");
			}
		}
		if (originnalCost == null) {
			originnalCost = new BigDecimal(PricingConstants.ZERO);
		}
		/* 始发部门code 定位价格区域信息 */
		String originalId = queryBillCacilateValueAddDto.getDeptRegionId();
		if (StringUtil.isEmpty(originalId)) {

			originalId = regionExpressService.findRegionOrgByDeptNo(
					originalOrgCode, receiveDate, null,
					PricingConstants.PRICING_REGION);
		}
		if (StringUtil.isEmpty(originalId)) {
			return null;
		}

		/* 到达部门 code 定位价格区域信息 */
		String destinationId = queryBillCacilateValueAddDto.getArrvRegionId();
		if (StringUtil.isEmpty(destinationId)
				&& StringUtil.isNotBlank(destinationOrgCode)) {
			destinationId = regionExpressService.findRegionOrgByDeptNo(
					destinationOrgCode, receiveDate, productCode,
					PricingConstants.PRICING_REGION);
		}
		queryBillCacilateValueAddDto.setDeptRegionId(originalId);
		queryBillCacilateValueAddDto.setArrvRegionId(destinationId);
		// 如果客户端没有长短途,则系统根据始发部门与到达部门CODE来定位
		String longOrShort = queryBillCacilateValueAddDto.getLongOrShort();
		if (StringUtil.isEmpty(longOrShort)
				&& StringUtil.isNotEmpty(destinationOrgCode)) {
			queryBillCacilateValueAddDto.setLongOrShort(PricingConstants.ALL);
		}
		/* 筛选计费规则下的计费明细 */
		Map<String, List<ResultProductPriceDto>> resultMap = expressPricingValueAddedService
				.siftValueAddRuleService(queryBillCacilateValueAddDto);
		//List<ResultProductPriceDto> resultProductPriceDtoList = resultMap
		//		.get("base");
		List<ResultProductPriceDto> resultOtherProductPriceDtoList = resultMap
				.get("other");

		if (CollectionUtils.isEmpty(resultOtherProductPriceDtoList)) {
			return null;
		}
		BigDecimal weight = queryBillCacilateValueAddDto.getWeight();
		if (weight == null) {
			weight = BigDecimal.ZERO;
		}
		BigDecimal volume = queryBillCacilateValueAddDto.getVolume();
		if (volume == null) {
			volume = BigDecimal.ZERO;
		}

		BigDecimal kilom = queryBillCacilateValueAddDto.getKilom();
		if (kilom == null) {
			kilom = BigDecimal.ZERO;
		}

		BigDecimal volumeWeight = volume.multiply(
				FossConstants.VOLUME_TO_WEIGHT).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		weight = weight.setScale(2, BigDecimal.ROUND_HALF_UP);
		if (volumeWeight.compareTo(weight) > 0) {
			weight = volumeWeight;
		}

		// 根据基础增值服务计算
		//List<ValueAddDto> resultList = PriceUtil.calculateExpressValueAddedServices(
		//		originnalCost, weight, volume, kilom,
		//		resultProductPriceDtoList, receiveDate,
		//		queryBillCacilateValueAddDto.getPricingEntryCode());
		// 根据区域或是产品增值服务计算
		List<ValueAddDto> resultOtherList = null;
		if (CollectionUtils.isNotEmpty(resultOtherProductPriceDtoList)) {
			resultOtherList = PriceUtil.calculateExpressValueAddedServices(
					originnalCost, weight, volume, kilom,
					resultOtherProductPriceDtoList, receiveDate,
					queryBillCacilateValueAddDto.getPricingEntryCode());
		}
		if (CollectionUtils.isNotEmpty(resultOtherList)) {
			for (int loop = 0; loop < resultOtherList.size(); loop++) {
				ValueAddDto vo = resultOtherList.get(loop);
				PriceEntity priceEntity = priceEntryService
						.getPriceEntryByCache(vo.getPriceEntityCode(),
								receiveDate);
				String priceEntryName = null;
				if (priceEntity != null && priceEntity.getName() != null) {
					priceEntryName = priceEntity.getName();
				}
				vo.setPriceEntityName(priceEntryName);
				if (StringUtils.equals(PriceEntityConstants.PRICING_CODE_QT,
						vo.getPriceEntityCode())) {
					PriceEntity priceEntitySub = priceEntryService
							.getPriceEntryByCache(vo.getSubType(), receiveDate);
					if (priceEntitySub != null) {
						// 抓取计价条目编码与名称
						vo.setSubTypeName(priceEntitySub.getName());
					}
				}
			}
			/**
			 * 内部发货不进行增值打折,直接返回增值费
			 * dp-foss-dongjialing
			 * 225131
			 */
			if((WaybillConstants.DELIVERY_PAY.equals(queryBillCacilateValueAddDto.getInternalDeliveryType())
					|| WaybillConstants.RECIVE_PAY.equals(queryBillCacilateValueAddDto.getInternalDeliveryType())
					) && StringUtil.isNotBlank(queryBillCacilateValueAddDto.getEmployeeNo())) {
				
				return resultOtherList;
				
			}
		if(((StringUtil.equals(DictionaryValueConstants.COD__COD_TYPE__RETURN_1_DAY,queryBillCacilateValueAddDto.getSubType())||
						StringUtil.equals(DictionaryValueConstants.COD__COD_TYPE__RETURN_3_DAY,queryBillCacilateValueAddDto.getSubType()))
						&&StringUtil.equals(PricingConstants.PriceEntityConstants.PRICING_CODE_HK,queryBillCacilateValueAddDto.getPricingEntryCode()))
					||StringUtil.equals(PricingConstants.PriceEntityConstants.PRICING_CODE_BF,queryBillCacilateValueAddDto.getPricingEntryCode())
					||StringUtil.equals(PricingConstants.PriceEntityConstants.PRICING_CODE_QS, queryBillCacilateValueAddDto.getPricingEntryCode())){
				resultOtherList = doZZFRTDiscount(originalOrgCode,destinationOrgCode,receiveDate,weight,volume, originalId, destinationId,
						resultOtherList,queryBillCacilateValueAddDto);	
			}
		}
		resultOtherList=crmActiveDiscountProcessValueAddExpress(queryBillCacilateValueAddDto,resultOtherList);
		return resultOtherList;

		//PriceEntity priceEntity = priceEntryService
		//		.getPriceEntryByCache(
		//				queryBillCacilateValueAddDto.getPricingEntryCode(),
		//				receiveDate);
		//String priceEntryName = null;
		//if (priceEntity != null && priceEntity.getName() != null) {
		//	priceEntryName = priceEntity.getName();
		//}
		//ProductEntity productEntity = productService.getProductByCache(
		//		productCode, receiveDate);

		//GoodsTypeEntity goodsTypeEntity = goodsTypeService.getGoodsTypeByCache(
		//		goodsTypeCode, receiveDate);

		//if (CollectionUtils.isNotEmpty(resultList)) {
		//	for (int loop = 0; loop < resultList.size(); loop++) {
		//		ValueAddDto vo = resultList.get(loop);
		//		vo.setPriceEntityCode(queryBillCacilateValueAddDto
		//				.getPricingEntryCode());
		//	vo.setPriceEntityName(priceEntryName);
		//		if (null != productEntity) {
		//			vo.setProductCode(productEntity.getCode());
		//			vo.setProductName(productEntity.getName());
		//		}
		//		if (null != goodsTypeEntity) {
		//			vo.setGoodsTypeName(goodsTypeEntity.getName());
		//			vo.setGoodsTypeCode(goodsTypeEntity.getCode());
		//	}

			//	if (PriceEntityConstants.PRICING_CODE_QT.equals(vo
			//			.getPriceEntityCode())) {
			//		priceEntity = priceEntryService.getPriceEntryByCache(
			//				vo.getSubType(), receiveDate);
			//		if (priceEntity != null) {
						// 抓取计价条目编码与名称
				//		vo.setSubTypeName(priceEntity.getName());

						// 抓取计价条目归集类别编码与归集类别名称
				//		PriceEntity blongPriceEntity = priceEntryService
				//				.getPriceEntryByCache(
				//						priceEntity.getBlongPricingCode(),
					//					receiveDate);
					//	if (blongPriceEntity != null) {
					//		vo.setBelongToPriceEntityCode(blongPriceEntity
					//				.getCode());// 归集类别CODE
					//		vo.setBelongToPriceEntityName(blongPriceEntity
					//				.getName());// 归集类别名称
					//	}
					//}
				//}

			//}
//			   若 即日退或保价费，有折扣
			//if((StringUtil.equals(DictionaryValueConstants.COD__COD_TYPE__RETURN_1_DAY,queryBillCacilateValueAddDto.getSubType())
					//&&StringUtil.equals(PricingConstants.PriceEntityConstants.PRICING_CODE_HK,queryBillCacilateValueAddDto.getPricingEntryCode()))
				//||StringUtil.equals(PricingConstants.PriceEntityConstants.PRICING_CODE_BF,queryBillCacilateValueAddDto.getPricingEntryCode())){
				//resultList=doZZFRTDiscount(originalOrgCode,destinationOrgCode,receiveDate,weight,volume, originalId, destinationId,
								//resultList,queryBillCacilateValueAddDto);	
			//}	
		//}

		//if (StringUtil.isNotBlank(destinationOrgCode)) {
			//log.debug("value add end calcuate>>" + new Date());
			// 增值优惠计算
			//List<ValueAddDto> list = resultList;
			//boolean flag = false;
			//if (CollectionUtils.isNotEmpty(list)) {
				//for (int i = 0; i < list.size(); i++) {
				//	ValueAddDto addDto = list.get(i);
				//	if (addDto.getDiscountFee() != null) {
				//		flag = true;
				//		break;
				//	}
				//}
			//}
			//if (flag) {
			//	list = compareDiscountAndRegionList(list, resultOtherList);
			//} else {
				//if (CollectionUtils.isNotEmpty(resultOtherList)) {
				//	list = resultOtherList;
				//} else {
					//return list;
				//}
			//}
			/**
		     * 处理增值服务费CRM营销活动处理
		     */
			//list=crmActiveDiscountProcessValueAddExpress(queryBillCacilateValueAddDto,list);
	    	//return list;
		//} else {
			/**
		     * 处理增值服务费CRM营销活动处理
		     */
			//resultList=crmActiveDiscountProcessValueAddExpress(queryBillCacilateValueAddDto,resultList);
			//return resultList;
		//}
		/**
		 * BillCaculateService 是开单计算价格、增值服务、折扣、优惠活动的实现类
		 * 
		 * 
		 * 计算运费分别为：FRT
		 * 
		 * 
		 * 
		 * 汽运 - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 0
		 * 
		 * 
		 * 空运 - t_srv_pice_plan价格表 字段 TRANSPORT_FLAG = 1
		 * 
		 * 计算增值服务： VAS
		 * 
		 * 保费 - BF
		 * 
		 * 代收货款 - HK
		 * 
		 * 送货费 - SH
		 * 
		 * 接货费 - JH
		 * 
		 * 签收回单 -QS
		 * 
		 * 保管费 - CCF
		 * 
		 * 综合信息费 -ZHXX
		 * 
		 * 燃油附加费 - RYFJ
		 * 
		 * 中转费 - ZZ
		 * 
		 * 接货差额补差 -JHCEBC
		 * 
		 * 电子优惠券 - DZYHQ
		 * 
		 * 其他费用 -QT
		 * 
		 * 包装费 -BZ
		 * 
		 * 送货上楼费 -SHSL
		 * 
		 * 超远派送费 -CY
		 * 
		 * 送货进仓费 -SHJCF
		 * 
		 * 更改费 -GGF
		 * 
		 * 以上服务相关代码进行不同维度计算。
		 * 
		 * 可支绑定如产品、区域 等等进行价格、折扣、优惠计算
		 */
	}
			
		private List<ValueAddDto> searchDLBValueAddPriceList(
		QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
			log.debug("value add start searchDLBValueAddPriceList>>" + new Date());
			if (queryBillCacilateValueAddDto == null) {
				return null;
			}
			BigDecimal originnalCost = queryBillCacilateValueAddDto
					.getOriginnalCost();
			// 校验输入参数
			PriceUtil
					.checkExpressQueryBillCacilateValueAddDtoDate(queryBillCacilateValueAddDto);
			// field
			String originalOrgCode = queryBillCacilateValueAddDto
					.getOriginalOrgCode();
			String destinationOrgCode = queryBillCacilateValueAddDto
					.getDestinationOrgCode();
			Date receiveDate = queryBillCacilateValueAddDto.getReceiveDate();
			String productCode = queryBillCacilateValueAddDto.getProductCode();
			queryBillCacilateValueAddDto.setActive(FossConstants.ACTIVE);
			if (StringUtil.isEmpty(queryBillCacilateValueAddDto.getGoodsTypeCode())) {
				queryBillCacilateValueAddDto
						.setGoodsTypeCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
			} else {
				queryBillCacilateValueAddDto
						.setGoodsTypeCode(queryBillCacilateValueAddDto
								.getGoodsTypeCode());
			}
			String goodsTypeCode = queryBillCacilateValueAddDto.getGoodsTypeCode();
			// String priceEntityCode =
			// queryBillCacilateValueAddDto.getPriceEntityCode();
			// 处理客户端以元为单位的原始费用化成分
			if (originnalCost != null) {
				originnalCost = new BigDecimal(String.valueOf(originnalCost
						.doubleValue() * PricingConstants.YUTOFEN));
				queryBillCacilateValueAddDto.setOriginnalCost(originnalCost);
			}
			// 保费特殊处理保费默认值问题
			if (null == originnalCost
					&& StringUtil.equalsIgnoreCase(
							queryBillCacilateValueAddDto.getPricingEntryCode(),
							PriceEntityConstants.PRICING_CODE_BF)) {
				ConfigurationParamsEntity entity = configurationParamsService
						.queryConfigurationParamsByOrgCode(
								DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
								ConfigurationParamsConstants.PKP_PARM__DEFAULT_INSURANCE_AMOUNT,
								originalOrgCode);
				if (entity != null) {
					String parmValue = entity.getConfValue();
					try {
						originnalCost = new BigDecimal(String.valueOf(new Double(
								parmValue) * PricingConstants.YUTOFEN));
						queryBillCacilateValueAddDto
								.setOriginnalCost(originnalCost);

					} catch (BillCaculateServiceException e) {
						throw new BillCaculateServiceException(
								"很抱歉，查询出发部门对应的默认保费时出现异常信息", e.getMessage());
					}
				} else {
					throw new BillCaculateServiceException(
							"很抱歉，因开单时你没有传入保费的声明价值费用,并且出发部门也没有对应的默认声明价值费用，我们不能为你计算出最后的保费");
				}
			}
			if (originnalCost == null) {
				originnalCost = new BigDecimal(PricingConstants.ZERO);
			}
			/* 始发部门code 定位价格区域信息 */
			String originalId = queryBillCacilateValueAddDto.getDeptRegionId();
			if (StringUtil.isEmpty(originalId)) {

				originalId = regionExpressService.findRegionOrgByDeptNo(
						originalOrgCode, receiveDate, null,
						PricingConstants.PRICING_REGION);
			}
			if (StringUtil.isEmpty(originalId)) {
				return null;
			}

			/* 到达部门 code 定位价格区域信息 */
			String destinationId = queryBillCacilateValueAddDto.getArrvRegionId();
			if (StringUtil.isEmpty(destinationId)
					&& StringUtil.isNotBlank(destinationOrgCode)) {
				destinationId = regionExpressService.findRegionOrgByDeptNo(
						destinationOrgCode, receiveDate, productCode,
						PricingConstants.PRICING_REGION);
			}
			queryBillCacilateValueAddDto.setDeptRegionId(originalId);
			queryBillCacilateValueAddDto.setArrvRegionId(destinationId);
			// 如果客户端没有长短途,则系统根据始发部门与到达部门CODE来定位
			String longOrShort = queryBillCacilateValueAddDto.getLongOrShort();
			if (StringUtil.isEmpty(longOrShort)
					&& StringUtil.isNotEmpty(destinationOrgCode)) {
				queryBillCacilateValueAddDto.setLongOrShort(PricingConstants.ALL);
			}
			/* 筛选计费规则下的计费明细 */
//			Map<String, List<ResultProductPriceDto>> resultMap = expressPricingValueAddedService
//					.siftValueAddRuleService(queryBillCacilateValueAddDto);
//			List<ResultProductPriceDto> resultProductPriceDtoList = resultMap
//					.get("base");
			BigDecimal weight = queryBillCacilateValueAddDto.getWeight();
			if (weight == null) {
				weight = BigDecimal.ZERO;
			}
			BigDecimal volume = queryBillCacilateValueAddDto.getVolume();
			if (volume == null) {
				volume = BigDecimal.ZERO;
			}

			BigDecimal kilom = queryBillCacilateValueAddDto.getKilom();
			if (kilom == null) {
				kilom = BigDecimal.ZERO;
			}

			BigDecimal volumeWeight = volume.multiply(
					FossConstants.VOLUME_TO_WEIGHT).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			weight = weight.setScale(2, BigDecimal.ROUND_HALF_UP);
			if (volumeWeight.compareTo(weight) > 0) {
				weight = volumeWeight;
			}

			String code = queryBillCacilateValueAddDto.getCityMarketCode();
			String deptcode = queryBillCacilateValueAddDto.getOriginalOrgCode();
			Date billDate=queryBillCacilateValueAddDto.getReceiveDate();
			if(null == billDate){
				billDate = new Date();
			}
			CityMarketPlanEntity cityMarketPlanEntity = cityMarketPlanService.getCityMarketPlanEntityCode(code, deptcode, billDate);
			// 根据基础增值服务计算
			List<ValueAddDto> resultList = PriceUtil.calculateDLBValueAddedServices(
					originnalCost, cityMarketPlanEntity,queryBillCacilateValueAddDto);
			PriceEntity priceEntity = priceEntryService
					.getPriceEntryByCache(
							queryBillCacilateValueAddDto.getPricingEntryCode(),
							receiveDate);
			String priceEntryName = null;
			if (priceEntity != null && priceEntity.getName() != null) {
				priceEntryName = priceEntity.getName();
			}		
			ProductEntity productEntity = productService.getProductByCache(
					productCode, receiveDate);

			GoodsTypeEntity goodsTypeEntity = goodsTypeService.getGoodsTypeByCache(
					goodsTypeCode, receiveDate);
			if (CollectionUtils.isNotEmpty(resultList)) {
				for (int loop = 0; loop < resultList.size(); loop++) {
					ValueAddDto vo = resultList.get(loop);
					vo.setPriceEntityCode(queryBillCacilateValueAddDto
							.getPricingEntryCode());
					vo.setPriceEntityName(priceEntryName);
					if (null != productEntity) {
						vo.setProductCode(productEntity.getCode());
						vo.setProductName(productEntity.getName());
					}
					if (null != goodsTypeEntity) {
						vo.setGoodsTypeName(goodsTypeEntity.getName());
						vo.setGoodsTypeCode(goodsTypeEntity.getCode());
					}
				}
			}
			return resultList;
		}
		
		/***
		 * FossConstants.VOLUME_TO_WEIGHT = new BigDecimal(1000.0/6.0)
	              业务逻辑：
	         1.系统读取客户合同中约定的体积重量转换值，当合同中数值为空时，体积重量转换公式仍为长*宽*高/6000
	         2.定体积重量转换值，当合同中数值不为空时，体积重量转换公式为长*宽*高/X(X为合同中值)
	         3.非合同客户仍使用现有体积重量转换公式
		 * @author 200945 wutao
		 * <a></a>
		 * @param customerCode
		 * @param date
		 * @param code
		 * @param volume
		 * @param originalOrgCode
		 * @return
		 * 
		 */
		public  BigDecimal validateWeightBubbleRate(String customerCode,Date date,String code,BigDecimal volume,String originalOrgCode){
			BigDecimal volumeWeight = null;
			//先查詢折扣信息
			PreferentialInfoDto entity = preferentialService.queryPreferentialInfo(customerCode, date, code);
			//查詢關聯的部門信息，判斷是否可以用重泡比
			if (null != entity && StringUtils.isNotEmpty(originalOrgCode)) {
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(originalOrgCode);
				if(orgAdministrativeInfoEntity==null){
					throw new BillCaculateServiceException("收货部门"+originalOrgCode+"不存在");
				}
				List<BargainAppOrgEntity> bargainAppOrgEntities = bargainAppOrgService
						.queryAppOrgByBargainCrmIdAndUnifiedCode(entity.getCusBargainId(),orgAdministrativeInfoEntity.getUnifiedCode());
				 
				//如果是關聯的營業部那麼就按照新的邏輯重新計算体积 ---> 体积重量 公式
				if(CollectionUtils.isNotEmpty(bargainAppOrgEntities)){
					if (entity.getWeightBubbleRate() != null) {
						volumeWeight = (volume.multiply(new BigDecimal(NUMBER_1000)))
								.divide((entity.getWeightBubbleRate()
										.divide(new BigDecimal(NUMBER_1000))),THREE,BigDecimal.ROUND_HALF_UP);
					} else {
						volumeWeight = volume.multiply(
								FossConstants.VOLUME_TO_WEIGHT).setScale(THREE,
								BigDecimal.ROUND_HALF_UP);
					}
				}else{ //否则按照之前的进行计算
					volumeWeight = volume.multiply(
							FossConstants.VOLUME_TO_WEIGHT).setScale(THREE,
							BigDecimal.ROUND_HALF_UP);
				}
			}else{
				volumeWeight = volume.multiply(
						FossConstants.VOLUME_TO_WEIGHT).setScale(THREE,
						BigDecimal.ROUND_HALF_UP);
			}
			return volumeWeight;
		}
		
		/***
		* 
		* @author: 200945 吴涛
		* @Title: handleExpressFRTDiscountAndCrmActiveDiscount 
		* @Description: (进行运费折扣信息处理) 
		* @param @param originalOrgCode
		* @param @param destinationOrgCode
		* @param @param receiveDate
		* @param @param weight
		* @param @param volume
		* @param @param originalId
		* @param @param destinationId
		* @param @param caculateresultList
		* @param @param queryBillCacilateDto
		* @param @return    设定文件 
		* @return List<ProductPriceDto>    返回类型 
		* @throws
		* 业务逻辑：
		* 	
		 */
	public List<ProductPriceDto> handleExpressFRTDiscountAndCrmActiveDiscount(
			String originalOrgCode, String destinationOrgCode,
			Date receiveDate, BigDecimal weight, BigDecimal volume,
			String originalId, String destinationId,
			List<ProductPriceDto> caculateresultList,
			QueryBillCacilateDto queryBillCacilateDto) {
		//
		List<ProductPriceDto> expressDiscountResultList = new ArrayList<ProductPriceDto>();
		// 获取运费
		if (CollectionUtils.isNotEmpty(caculateresultList)) {
			for (int i = 0; i < caculateresultList.size(); i++) {
				ProductPriceDto price = caculateresultList.get(i);
				if (price != null
						&& PriceEntityConstants.PRICING_CODE_FRT.equals(price
								.getPriceEntityCode())) {
					queryBillCacilateDto
							.setTransportFee(price.getCaculateFee());
					break;
				}
			}
		}
		
		// 校验是否可以享有市场营销活动
		if (queryBillCacilateDto.isPda()) {
			PriceUtil.validateActiveDiscountPda(queryBillCacilateDto,markActivitiesService);
		} else {
			PriceUtil.validateActiveDiscount(queryBillCacilateDto,customerService, markActivitiesService);
		}
		PreferentialInfoDto preferentialInfo = preferentialService.queryPreferentialInfo(queryBillCacilateDto.getCustomerCode(),receiveDate, ProductEntityConstants.PRICING_PRODUCT_C2_C20005);
		// 存在市场活动运费折扣
		if (queryBillCacilateDto.isCalActiveDiscount()) {
			// 对比市场与合同折扣值，取小，如果取CRM合同折扣，则打折后费率不能低于CRM合同最低费率；取市场折扣，折后费率不能低于市场最低费率
			
			if(preferentialInfo != null) {
				// 首重折扣
				BigDecimal firstHeavyDiscount = preferentialInfo.getChargeRebate();
				// 续重折扣
				BigDecimal continueHeavyDiscount = preferentialInfo.getContinueHeavyDiscount();
				
				// 合同续重的最低费率
				BigDecimal contractLowestRate = preferentialInfo.getContinueHeavyLowestRate();
				if(firstHeavyDiscount ==null && continueHeavyDiscount !=null){
					firstHeavyDiscount = continueHeavyDiscount;
				}
				if(continueHeavyDiscount==null && firstHeavyDiscount!=null){
					continueHeavyDiscount = firstHeavyDiscount;
				} 
				if (firstHeavyDiscount != null && continueHeavyDiscount != null) {
					if (firstHeavyDiscount.compareTo(continueHeavyDiscount) == 0) {
						if (CollectionUtils.isNotEmpty(caculateresultList)) {
							for (int i = 0; i < caculateresultList.size(); i++) {
								// 费用对象
								ProductPriceDto productPriceDto = caculateresultList.get(i);
								// 折扣列表
								List<PriceDiscountDto> priceDiscountDtos = productPriceDto.getDiscountPrograms();
								if (priceDiscountDtos == null) {
									priceDiscountDtos = new ArrayList<PriceDiscountDto>();
								}
								// 市场营销活动DTO
								MarkActivitiesQueryConditionDto activeDto = queryBillCacilateDto.getActiveDto();
								// 计算出的价格
								BigDecimal caculateFee = productPriceDto.getCaculateFee();
								if (CollectionUtils.isNotEmpty(priceDiscountDtos)) {
									PriceDiscountDto dis = priceDiscountDtos.get(0);
									if (dis != null && dis.getOriginnalCost() != null) {
										// 获取原始费用
										caculateFee = dis.getOriginnalCost();
									}
								}
								// ID
								String id = productPriceDto.getId();
								// 获取营销活动折扣
								if (activeDto != null && CollectionUtils.isNotEmpty(activeDto
												.getOptionList())) {
									for (MarkOptionsEntity entity : activeDto.getOptionList()) {
										if (entity != null && entity.getName() != null && entity.getValue() != null) {
											// 获取折扣类型(运费、保费等)
											String discountType = entity.getName();
											// 获取折扣值
											BigDecimal discountRate = entity.getValue();
											//续重最低费率
											BigDecimal continueHeavyLowestRate = entity.getContinueHeavyLowestRate();
											// 营销活动折扣相应的CRM_ID
											BigDecimal crmId = entity.getCrmId();

											if (PriceEntityConstants.PRICING_CODE_FRT.equals(discountType)
													&& PriceEntityConstants.PRICING_CODE_FRT.equals(productPriceDto.getPriceEntityCode())) {
												// 取CRM合同的
												if (firstHeavyDiscount.doubleValue() <= discountRate.doubleValue()) {
													// 走的CRM折扣(测试通过)
													expressDiscountResultList = doExpressFRTDiscount(
															originalOrgCode,
															destinationOrgCode,
															receiveDate, weight,
															volume, originalId,
															destinationId,
															caculateresultList,
															queryBillCacilateDto,
															firstHeavyDiscount,
															continueHeavyDiscount,
															contractLowestRate);
												} else {
													// 折扣实体(测试通过)
													PriceDiscountDto priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(
																	discountType,
																	discountRate,
																	caculateFee,
																	id, activeDto,
																	crmId, null);
													//重新计算运费，以及减免费
													caculateDiscountFeeForActive(
															productPriceDto,
															priceDiscountDto,
															priceDiscountDtos,continueHeavyLowestRate);
													// 将折扣信息添加到费用对象中
													productPriceDto
															.setDiscountPrograms(priceDiscountDtos);
													// 将费用对象添加费用集合中
													expressDiscountResultList
															.add(productPriceDto);
												}
											}
										}
									}
								}

							}
						}
					} else {// 按照CRM合同打折，且折后续重费率不能低于合同约定最低费率（CRM首重折扣和续重不相等）

						expressDiscountResultList = doExpressFRTDiscount(
								originalOrgCode, destinationOrgCode, receiveDate,
								weight, volume, originalId, destinationId,
								caculateresultList, queryBillCacilateDto,
								firstHeavyDiscount, continueHeavyDiscount,
								contractLowestRate);
					}
				} else {//(单元测试【通过】)存在市场活动，但是 （CRM）首重和续重不存在的情况下
					expressDiscountResultList = doDiscountFRTExpressForActive(queryBillCacilateDto,caculateresultList);
				}
			} else {
				expressDiscountResultList = doDiscountFRTExpressForActive(queryBillCacilateDto,caculateresultList);
			}
			
		} else {
			// 不存在:公布价基础上，根据CRM合同 【首重】、【续重】折扣 打折，且【续重折扣】不能低于CRM合同约定值（）
                if(preferentialInfo != null) {
                	BigDecimal firstHeavyDiscount = preferentialInfo.getChargeRebate();
    				// 续重折扣
    				BigDecimal continueHeavyDiscount = preferentialInfo.getContinueHeavyDiscount();
    				
    				// 合同续重的最低费率
    				BigDecimal contractLowestRate = preferentialInfo.getContinueHeavyLowestRate();
                	expressDiscountResultList = doExpressFRTDiscount(originalOrgCode,
    						destinationOrgCode, receiveDate, weight, volume,
    						originalId, destinationId, caculateresultList,
    						queryBillCacilateDto, firstHeavyDiscount,
    						continueHeavyDiscount, contractLowestRate);
                } else {
                	expressDiscountResultList = caculateresultList;
                }
				
			}
		return expressDiscountResultList;
		}/*else {// 不存在:公布价基础上，根据CRM合同 【首重】、【续重】折扣 打折，且【续重折扣】不能低于CRM合同约定值（）

			expressDiscountResultList = doExpressFRTDiscount(originalOrgCode,
					destinationOrgCode, receiveDate, weight, volume,
					originalId, destinationId, caculateresultList,
					queryBillCacilateDto, firstHeavyDiscount,
					continueHeavyDiscount, contractLowestRate);
		}*/
		
	
	
		/***
		* 
		* @author: 200945 吴涛
		* @Title: calculateDiscountFeeRate 
		* @Description: (此方法用于CRM合同客户进行运费打折，进行运费和折扣率进行重新的打折) 
		* @param @param discountResultDto
		* @param @param productPriceDto
		* @param @param newFirstDiscount 从合同读取出的首重折扣
		* @param @param newSecondDiscount 从合同读取出的续重折扣
		* @param @param lowestSecondDiscountFee    续重的最低费率（合同或市场推广活动中）
		* @return void    返回类型 
		* @throws
		 */
		 public void calculateDiscountFeeRate(DiscountResultDto discountResultDto,ProductPriceDto productPriceDto,
		         BigDecimal firstHeavyDiscount,BigDecimal continueHeavyDiscount,BigDecimal lowestSecondDiscountFee) {
			 /**
			  * 如果：CRM合同客户不存在首重折扣或者续重折扣，就不打折
			  */
			 if(firstHeavyDiscount == null){
				 firstHeavyDiscount = BigDecimal.ONE;
			 }
			 if(continueHeavyDiscount==null){
				 continueHeavyDiscount = BigDecimal.ONE;
			 }
			 if(lowestSecondDiscountFee==null){
				 lowestSecondDiscountFee = BigDecimal.ZERO; 
			 }
			 BigDecimal discountTotalFee = BigDecimal.ZERO;
			 BigDecimal topDiscountFee = (productPriceDto.getFirstRateFee().multiply(firstHeavyDiscount)).divide(new BigDecimal(NUMBER_100));
		     //获取续重区间1，续重区间2 打折之后的费率
		     BigDecimal firstDiscountFee =  (continueHeavyDiscount.multiply(productPriceDto.getFirstTempRate())).divide(new BigDecimal(NUMBER_100));
		     BigDecimal secondDiscountFee = (continueHeavyDiscount.multiply(productPriceDto.getSecondTempRate())).divide(new BigDecimal(NUMBER_100));
		     
		     //续重1的费率和续重的最低费率进行比较		   
		     if(firstDiscountFee.compareTo(BigDecimal.ZERO)> 0 && (firstDiscountFee.doubleValue() < lowestSecondDiscountFee.doubleValue())){
		    	 firstDiscountFee = lowestSecondDiscountFee;
		     }
		    //续重2的费率和续重的最低费率进行比较
		     if(secondDiscountFee.compareTo(BigDecimal.ZERO)> 0 && (secondDiscountFee.doubleValue() < lowestSecondDiscountFee.doubleValue())){
		    	 secondDiscountFee = lowestSecondDiscountFee;
		     }
		    
		        
            //重新计算运费   需要将计算出的总运费
		    //续重1折扣后费用 *续重之间的重量
            BigDecimal firstWeightFee = productPriceDto.getFirstWeight().multiply(firstDiscountFee);
            //续重2折扣后费用  【续重1和续重2折扣共同用一个】 *续重之间的重量
            BigDecimal secondWeightFee = productPriceDto.getSecondWeight().multiply(secondDiscountFee);
            //打折后的总费用
            discountTotalFee = topDiscountFee.add(firstWeightFee).add(secondWeightFee);
            
            discountResultDto.setDiscountValue(discountTotalFee);
            
            //重新计算优惠金额  原始费用与当前运费的差值
            //减免费用
            BigDecimal reduceFee = productPriceDto.getCaculateFee().subtract(discountTotalFee);
            if(reduceFee.compareTo(BigDecimal.ZERO)>0){
            	discountResultDto.getDiscountPrograms().get(0).setReduceFee(reduceFee);
            }
		     //将折扣价格赋值原计算后价格
		     productPriceDto.setCaculateFee(discountResultDto.getDiscountValue());
		     //将折扣价格赋值折扣价格
		     productPriceDto.setDiscountFee(discountResultDto.getDiscountValue());
		     //保留计价明细ID
		     if(StringUtils.isNotBlank(discountResultDto.getId())){
		         productPriceDto.setId(discountResultDto.getId());
		     }
		     //重新赋值折扣费率
		     BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
		     BigDecimal discountActualFeeRate = continueHeavyDiscount.multiply(actualFeeRate);
		     
		     //重新赋值折扣费率  如果当前费率小于配置的最低费率 则显示最低的配置费率
	         if(discountActualFeeRate.compareTo(lowestSecondDiscountFee.multiply(new BigDecimal(NUMBER_100)))<0){
	             discountActualFeeRate = lowestSecondDiscountFee.multiply(new BigDecimal(NUMBER_100));
	         }
		     //设置续重折扣
	         discountResultDto.setRenewalDiscountRate(continueHeavyDiscount);
	         if(continueHeavyDiscount.compareTo(BigDecimal.ZERO)>0) {
	        	 discountResultDto.getDiscountPrograms().get(0).setRenewalDiscountRate(continueHeavyDiscount);
	         }
		     productPriceDto.setActualFeeRate(discountActualFeeRate);
		 }

		 /**	
		  *    dongjialing
			 * 判断是否有市场运费折扣活动并进行打折计算
			 * @param queryBillCacilateDto
			 * @param caculateresultList
			 * @return
			 */
			private List<ProductPriceDto> doDiscountFRTExpressForActive(QueryBillCacilateDto queryBillCacilateDto,List<ProductPriceDto> caculateresultList) {
				//获取运费
				if(CollectionUtils.isNotEmpty(caculateresultList)){
					for (int i = 0; i < caculateresultList.size(); i++) { 
						ProductPriceDto price=caculateresultList.get(i);
						if(price!=null && PriceEntityConstants.PRICING_CODE_FRT.equals(price.getPriceEntityCode())){
							queryBillCacilateDto.setTransportFee(price.getCaculateFee());
							break;
						}
					}
				}
				/**
				 * 判断是否计算市场活动折扣		
				 */
				if(queryBillCacilateDto.isCalActiveDiscount()){			
					List<ProductPriceDto> productPriceDtos = new ArrayList<ProductPriceDto>();
					if(CollectionUtils.isNotEmpty(caculateresultList)) {
						 
						for (int i = 0; i < caculateresultList.size(); i++) {  
							//费用对象
							ProductPriceDto productPriceDto =  caculateresultList.get(i);  
							//折扣列表
							List<PriceDiscountDto> priceDiscountDtos = productPriceDto.getDiscountPrograms();
							if(priceDiscountDtos==null){
								priceDiscountDtos=new ArrayList<PriceDiscountDto>();
							}
							//市场营销活动DTO
							MarkActivitiesQueryConditionDto activeDto=queryBillCacilateDto.getActiveDto();
							//计算出的价格
		            		BigDecimal caculateFee=productPriceDto.getCaculateFee();
		            		if(CollectionUtils.isNotEmpty(priceDiscountDtos)){
		            			PriceDiscountDto dis=priceDiscountDtos.get(0);
		            			if(dis!=null && dis.getOriginnalCost()!=null){
		            				//获取原始费用
		            				caculateFee=dis.getOriginnalCost();
		            			}
		            		}
		            		//ID
		            		String id=productPriceDto.getId();
							//获取营销活动折扣
					    	if(activeDto!=null && CollectionUtils.isNotEmpty(activeDto.getOptionList())){			    			    		
					    		for(MarkOptionsEntity entity:activeDto.getOptionList()){			    			
					        		if(entity!=null && entity.getName()!=null && entity.getValue()!=null){        			
					            		//获取折扣类型(运费、保费等)
					            		String discountType=entity.getName();
					            		//获取折扣值
					            		BigDecimal discountRate=entity.getValue();
					            		//续重最低费率
					            		BigDecimal continueHeavyLowestRate = entity.getContinueHeavyLowestRate();
					            		//营销活动折扣相应的CRM_ID
					            		BigDecimal crmId=entity.getCrmId();			            		
				            			//运费
					            		if(PriceEntityConstants.PRICING_CODE_FRT.equals(discountType) 
					            				&& PriceEntityConstants.PRICING_CODE_FRT.equals(productPriceDto.getPriceEntityCode())){		            			
					            			
					            			//折扣实体
						            		PriceDiscountDto priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,null);
						            		
						            		//计算打折之后的费用
						            		//计算打折之后的续重费率和首重价格
						            		//续重折扣设置
						            		
						            		caculateDiscountFeeForActive(productPriceDto,priceDiscountDto,priceDiscountDtos,continueHeavyLowestRate);	
						            			
						            	
						            		
						            		
					            		}			            				            				            		
					        		}
					    		}    		
					    	}
					    	//将折扣信息添加到费用对象中
					    	productPriceDto.setDiscountPrograms(priceDiscountDtos);
					    	//将费用对象添加费用集合中
					    	productPriceDtos.add(productPriceDto);
						}			
					}
					
					//返回费用集合
			    	return productPriceDtos;
				}else{
					//不计算市场活动折扣则直接返回
					return caculateresultList;
				}		
			}
		 /****
		 * 
		 * @author: 225131 dongjialing
		 * @Title: caculateDiscountFeeForActive 
		 * @Description: (这个方法是用来计算【市场营销活动的运费打折后的信息】) 
		 * @param @param productPriceDto
		 * @param @param priceDiscountDto
		 * @param @param priceDiscountDtos    设定文件 
		 * @return void    返回类型 
		 * @throws
		  */
		 public void caculateDiscountFeeForActive(ProductPriceDto productPriceDto,PriceDiscountDto priceDiscountDto
					,List<PriceDiscountDto> priceDiscountDtos,BigDecimal continueHeavyLowestRate) {
			      	BigDecimal totalFee=BigDecimal.ZERO;
			      	BigDecimal firstDiscountFee = (productPriceDto.getFirstRateFee().multiply(priceDiscountDto.getDiscountRate())).divide(new BigDecimal(NUMBER_100));
			      	BigDecimal firstTempRate = (productPriceDto.getFirstTempRate().multiply(priceDiscountDto.getDiscountRate())).divide(new BigDecimal(NUMBER_100));
			      	BigDecimal secondTempRate = (productPriceDto.getSecondTempRate().multiply(priceDiscountDto.getDiscountRate())).divide(new BigDecimal(NUMBER_100));
			      	//BigDecimal.TEN为替换变量市场营销续重最低费率
			      	if(continueHeavyLowestRate!=null) {
			      		if(firstTempRate.compareTo(BigDecimal.ZERO)> 0 && firstTempRate.compareTo(continueHeavyLowestRate)<0) {
		    				firstTempRate = continueHeavyLowestRate;
		    			}
		    			if(secondTempRate.compareTo(BigDecimal.ZERO)> 0 && secondTempRate.compareTo(continueHeavyLowestRate)<0) {
		    				secondTempRate = continueHeavyLowestRate;
		    			}
			      	}
	    			
	    			totalFee = totalFee.add(firstDiscountFee).add(firstTempRate.multiply(productPriceDto.getFirstWeight()))
	    					.add(secondTempRate.multiply(productPriceDto.getSecondWeight()));
	    			/**
	    			 * 设置运费
	    			 */
	    			productPriceDto.setCaculateFee(totalFee);
	    			productPriceDto.setDiscountFee(totalFee);
	    			/**
	    			 * 设置费率
	    			 */
					BigDecimal oldActualFeeRate = productPriceDto.getActualFeeRate();
					BigDecimal discountFeeRate = oldActualFeeRate.multiply(priceDiscountDto.getDiscountRate()).divide(new BigDecimal(NUMBER_100));
					productPriceDto.setInitFeeRate(oldActualFeeRate);
					productPriceDto.setActualFeeRate(discountFeeRate.multiply(new BigDecimal(NUMBER_100)));
					if(continueHeavyLowestRate!=null) {
						if(discountFeeRate.compareTo(BigDecimal.ZERO)> 0 && discountFeeRate.compareTo(continueHeavyLowestRate)<0) {
							productPriceDto.setActualFeeRate(continueHeavyLowestRate.multiply(new BigDecimal(NUMBER_100)));
						}
					}
					
	    			 /**
		             * 需要重新折扣的优惠金额  
		             * 可以由原来的优惠金额和折扣获取到原始费用
		             */
		            if(priceDiscountDto.getReduceFee().compareTo(BigDecimal.ZERO)>0){
		            	BigDecimal originnalCost = priceDiscountDto.getReduceFee().divide(BigDecimal.ONE.subtract(priceDiscountDto.getDiscountRate()));
	           	    BigDecimal reduceFee = originnalCost.subtract(totalFee);
			            if(reduceFee.compareTo(BigDecimal.ZERO)>0){
			            	priceDiscountDto.setReduceFee(reduceFee);
			            }
		            }
		            priceDiscountDto.setRenewalDiscountRate(priceDiscountDto.getDiscountRate());
		            priceDiscountDtos.add(priceDiscountDto);  
			}
		
		

		public void setEffectiveExpressPlanDetailService(
			IEffectiveExpressPlanDetailService effectiveExpressPlanDetailService) {
		    this.effectiveExpressPlanDetailService = effectiveExpressPlanDetailService;
		}
		
		public void setRegionExpressService(IRegionExpressService regionExpressService) {
		    this.regionExpressService = regionExpressService;
		}

		public void setCityMarketPlanService(
				ICityMarketPlanService cityMarketPlanService) {
			this.cityMarketPlanService = cityMarketPlanService;
		}

		public IPriceValuationService getPriceValuationService() {
			return priceValuationService;
		}

		public IExpressPriceValuationService getExpressPriceValuationService() {
			return expressPriceValuationService;
		}

		public IEffectivePlanDetailService getEffectivePlanDetailService() {
			return effectivePlanDetailService;
		}

		public IRegionArriveService getRegionArriveService() {
			return regionArriveService;
		}

		public IRegionAirService getRegionAirService() {
			return regionAirService;
		}

		public IOuterPriceCaculateService getOuterPriceCaculateService() {
			return outerPriceCaculateService;
		}

		public IDataDictionaryValueService getDataDictionaryValueService() {
			return dataDictionaryValueService;
		}

		public IEffectiveExpressPlanDetailService getEffectiveExpressPlanDetailService() {
			return effectiveExpressPlanDetailService;
		}

		public IRegionExpressService getRegionExpressService() {
			return regionExpressService;
		}

		public ICityMarketPlanService getCityMarketPlanService() {
			return cityMarketPlanService;
		}

		public IRegionValueAddService getRegionValueAddService() {
			return regionValueAddService;
		}

		public IPricingValueAddedService getPricingValueAddedService() {
			return pricingValueAddedService;
		}

		public IExpressPricingValueAddedService getExpressPricingValueAddedService() {
			return expressPricingValueAddedService;
		}

		public IProductService getProductService() {
			return productService;
		}

		public IGoodsTypeService getGoodsTypeService() {
			return goodsTypeService;
		}

		public IPriceEntryDao getPriceEntryDao() {
			return priceEntryDao;
		}

		public IPriceEntryService getPriceEntryService() {
			return priceEntryService;
		}

		public IConfigurationParamsService getConfigurationParamsService() {
			return configurationParamsService;
		}

		public IMinFeePlanService getMinFeePlanService() {
			return minFeePlanService;
		}

		public IDiscountPriorityService getDiscountPriorityService() {
			return discountPriorityService;
		}

		public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
			return orgAdministrativeInfoService;
		}

		public IPreferentialService getPreferentialService() {
			return preferentialService;
		}

	    public IBargainAppOrgService getBargainAppOrgService() {
			return bargainAppOrgService;
		}
	    
	 /**
	   * 
	   *
       * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.BillCaculateService.isExistCustomerExpressPrice
       * @Description:判断当前合同客户是否存在快递客户价格方案
       *
       * @return
       *
       * @version:v1.0
       * @author:DP-FOSS-YANGKANG
       * @date:2015-1-22 上午10:54:21
       *
       * Modification History:
       * Date         Author      Version     Description
       * -----------------------------------------------------------------
       * 2015-1-22    DP-FOSS-YANGKANG      v1.0.0         create
       */
      public List<ResultProductPriceDto>  isExistCustomerExpressPrice(QueryProductPriceDto queryProductPriceDto){
        /**
         * 判断当前合同客户是否存在客户价格方案   
         * 首先根据出发区域  到达区域查询价格   如果没有则根据区域所在的省市区  由下往上查询价格
         */
    	List<ResultProductPriceDto>  resultPriceList = new ArrayList<ResultProductPriceDto>();
    	resultPriceList = expressPriceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
    	
    	if(CollectionUtils.isNotEmpty(resultPriceList)){
    		return resultPriceList;
    	}else{
    		//设置快递价格区域的查询参数
            PriceRegionExpressEntity queryRegionEntity =  new PriceRegionExpressEntity();
            queryRegionEntity.setActive(FossConstants.ACTIVE);
            queryRegionEntity.setId(queryProductPriceDto.getOriginalOrgId());
            queryRegionEntity.setRegionNature(PricingConstants.PRICING_REGION);
            //查询出快递价格出发区域
            PriceRegionExpressEntity startExpressRegin =null;
            List<PriceRegionExpressEntity> startReginList = regionExpressService.findRegionByCondition(queryRegionEntity);
            if(CollectionUtils.isNotEmpty(startReginList)){
            	startExpressRegin = startReginList.get(0);
            }
             
           
            queryRegionEntity.setId(queryProductPriceDto.getDestinationId());
            //查询出快递价格到达区域
            PriceRegionExpressEntity arriveExpressRegin =null;
            List<PriceRegionExpressEntity> arriveReginList = regionExpressService.findRegionByCondition(queryRegionEntity);
            if(CollectionUtils.isNotEmpty(arriveReginList)){
            	arriveExpressRegin = arriveReginList.get(0);
            }
            //根据到达区域和出发区域查询不到数据  则直接返回null
            if(startExpressRegin==null || arriveExpressRegin==null){
            	return  null;
            }
            //出发区域及其上级区域集合
            List<PriceRegionExpressEntity> startExpressUpRegins = new ArrayList<PriceRegionExpressEntity>();
            //到达区域及其上级区域集合
            List<PriceRegionExpressEntity> arriveExpressUpRegins = new ArrayList<PriceRegionExpressEntity>();
          
            //配置出发区域上级区域查询参数
            queryRegionEntity.setId(null);
            queryRegionEntity.setNationCode(startExpressRegin.getNationCode());
            queryRegionEntity.setProCode(startExpressRegin.getProCode());
            queryRegionEntity.setCityCode(startExpressRegin.getCityCode());
            queryRegionEntity.setCountyCode(startExpressRegin.getCountyCode());
            
            startExpressUpRegins = regionExpressService.searchExpressAllUpRegionByCondition(queryRegionEntity);
            
            //配置到达区域上级区域查询参数
            /*2016年7月7日 15:07:16 葛亮亮 （因为开到台湾，配置的是台湾远郊营业部组织，组织的价格区域没有地址信息，这样会造成将所有的价格区域取出来，再
                                   按照客户匹配到任意一个价格方案后就会返回，认为到目的地有单独价格方案，如果出发和到达配置了客户的单独报价那么在上面第一个IF判断就会取出来）
                                   现阶段只对国际件进行处理*/
            if(("GTSE".equals(queryProductPriceDto.getProductCode())
    				||"ICES".equals(queryProductPriceDto.getProductCode())
    				||"GTEC".equals(queryProductPriceDto.getProductCode())
    				||"ICEC".equals(queryProductPriceDto.getProductCode())
    			) && (null == arriveExpressRegin.getNationCode()
	            		&& null == arriveExpressRegin.getProCode()
	            		&& null == arriveExpressRegin.getCityCode()
	            		&& null == arriveExpressRegin.getCountyCode())
            		 ){
            	return resultPriceList;
            }            
            queryRegionEntity.setNationCode(arriveExpressRegin.getNationCode());
            queryRegionEntity.setProCode(arriveExpressRegin.getProCode());
            queryRegionEntity.setCityCode(arriveExpressRegin.getCityCode());
            queryRegionEntity.setCountyCode(arriveExpressRegin.getCountyCode());
            
            arriveExpressUpRegins = regionExpressService.searchExpressAllUpRegionByCondition(queryRegionEntity);
            //按照优先级对价格区域进行排序
            startExpressUpRegins = sortExpressReginByPriority(startExpressUpRegins);
            arriveExpressUpRegins = sortExpressReginByPriority(arriveExpressUpRegins);
            
            //循环匹配价格  如果查询到有价格  则停止查询
            boolean  isContinue =true;
            for(PriceRegionExpressEntity startRegin:startExpressUpRegins){
            	
            	if(isContinue){
            		for(PriceRegionExpressEntity arriveRegin:arriveExpressUpRegins){
                		queryProductPriceDto.setOriginalOrgId(startRegin.getId());
                		queryProductPriceDto.setDestinationId(arriveRegin.getId());
                		resultPriceList = expressPriceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
                		if(CollectionUtils.isNotEmpty(resultPriceList)){
                			isContinue = false;
                			break;
                		}
                	}
            	}
            	
            }
            return resultPriceList;
    	}
      }
      
      /**
       * 
       *
       * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.BillCaculateService.caculateExpressFRTByExpressDiscount
       * @Description:根据客户是否维护快递的区域折扣方案  对快递运费进行折扣计算
       *
       *
       * @version:v1.0
       * @author:DP-FOSS-YANGKANG
       * @date:2015-1-23 下午3:24:51
       *
       * Modification History:
       * Date         Author      Version     Description
       * -----------------------------------------------------------------
       * 2015-1-23    DP-FOSS-YANGKANG      v1.0.0         create
       */
      public List<ProductPriceDto>  caculateExpressFRTByExpressDiscount(List<ProductPriceDto> caculateresultList,ExpressDiscountDto resultDiscount,QueryBillCacilateDto queryBillCacilateDto){
    	  //打折之后运费返回的最终结果
     	  List<ProductPriceDto> discountFRTResultList = new ArrayList<ProductPriceDto>();
    	  ProductPriceDto resultPrice = null;
    	  //获取运费
		  if(CollectionUtils.isNotEmpty(caculateresultList)){
			for (int i = 0; i < caculateresultList.size(); i++) { 
				ProductPriceDto price=caculateresultList.get(i);
				if(price!=null && PriceEntityConstants.PRICING_CODE_FRT.equals(price.getPriceEntityCode())){
					queryBillCacilateDto.setTransportFee(price.getCaculateFee());
					resultPrice = price;
					break;
				}
			}
		  }
		  //如果运费为空直接返回
		  if(resultPrice==null){
			  return null;
		  }
		//如果客户在当前区域折扣中 没有匹配到当前的开单计费重量的折扣 则不进行折扣计算  直接返回
		  if(resultDiscount.getRenewalDiscountRate()==null||resultDiscount.getFirstDiscountRate()==null){
			  return caculateresultList;
		  }
		//根据选择的折扣明细查询对应的折扣方案
		  ExpressDiscountEntity discountPlanEntity = new ExpressDiscountEntity();
		  discountPlanEntity.setId(resultDiscount.getExpressDiscountPlanId());
		  discountPlanEntity = expressDiscountPlanService.queryExpressDiscountPlanById(discountPlanEntity);
		  /**
		   * dp-foss-dongjialing
		   * 225131
		   * 存在市场推广活动且折扣方案中首重续重折扣值相等，
		   * 区域折扣方案和市场推广活动比较取小对应的续重折后费率不能小于续重最低费率
		   */
    	  if(queryBillCacilateDto.isCalActiveDiscount()
    			  && resultDiscount.getFirstDiscountRate().compareTo(resultDiscount.getRenewalDiscountRate())==0) {
    		  //对比取小
    		  discountFRTResultList = compareCrmActiveDiscountAndExpressReginDsicount(caculateresultList,resultDiscount,queryBillCacilateDto);
    	  }else {
    		  if(CollectionUtils.isNotEmpty(discountPlanEntity.getChannelCodes()) && queryBillCacilateDto.getChannelCode()==null) {
					//同是否单独定价为“否”的情况
					  discountFRTResultList = handleExpressFRTDiscountAndCrmActiveDiscount( queryBillCacilateDto.getOriginalOrgCode(),queryBillCacilateDto.getDestinationOrgCode(), queryBillCacilateDto.getReceiveDate(), queryBillCacilateDto.getWeight(),
		    				  queryBillCacilateDto.getVolume(),queryBillCacilateDto.getDeptRegionId(),queryBillCacilateDto.getArrvRegionId(),caculateresultList,queryBillCacilateDto);
				  } else {
					  if(CollectionUtils.isNotEmpty(discountPlanEntity.getChannelCodes()) && queryBillCacilateDto.getChannelCode()!=null) {
						  if(discountPlanEntity.getChannelCodes().contains(queryBillCacilateDto.getChannelCode())){
								//按照客户FOSS快递区域折扣打折，且折后续重费率不能低于CRM合同约定最低续重费率
							    discountFRTResultList = calculateExpressReginDiscount(queryBillCacilateDto,resultPrice,resultDiscount);
							  }else{
									//同是否单独定价为“否”的情况
								  discountFRTResultList = handleExpressFRTDiscountAndCrmActiveDiscount( queryBillCacilateDto.getOriginalOrgCode(),queryBillCacilateDto.getDestinationOrgCode(), queryBillCacilateDto.getReceiveDate(), queryBillCacilateDto.getWeight(),
					    				  queryBillCacilateDto.getVolume(),queryBillCacilateDto.getDeptRegionId(),queryBillCacilateDto.getArrvRegionId(),caculateresultList,queryBillCacilateDto);
							  }
					  } else {
						//按照客户FOSS快递区域折扣打折，且折后续重费率不能低于CRM合同约定最低续重费率
						    discountFRTResultList = calculateExpressReginDiscount(queryBillCacilateDto,resultPrice,resultDiscount);
					  }
					  
				  }
    	  }
    		
			
    	  return discountFRTResultList;
    	  /*if(CollectionUtils.isNotEmpty(discountResultList)){
    		  //判断是否进行CRM市场活动运费折扣的计算
    		  if(queryBillCacilateDto.isCalActiveDiscount()){
    			 *//**
    			  * 1.判断客户FOSS快递区域折扣方案中首重、续重折扣是否一致
    			  * 2.如果一致，对比市场活动运费折扣与快递区域折扣值，取折扣小的进行打折计算，如果取快递区域折扣，则打折后费率不能低于CRM合同最低费率；取市场折扣，折后费率不能低于市场活动最低费率
    			  * 3.如果不一致，则按照客户FOSS快递区域折扣打折，且折后续重费率不能低于CRM合同约定最低续重费率
    			  *//*
				  //获取计算完的运费中的计费重量  根据计费重量确定采用那一段快递区域折扣数据
				  ExpressDiscountDto resultDiscount = new ExpressDiscountDto();
				  
				  for(ExpressDiscountDto discountDto:discountResultList){
					  //快递区域折扣范围为左闭区间  右开区间
					  if(discountDto.getLeftRange().compareTo(resultPrice.getVolumeWeight())<=0 &&
							  discountDto.getRightRange().compareTo(resultPrice.getVolumeWeight())>0){
						  resultDiscount = discountDto;
						  break;
					  }
				  }
				  //如果客户在当前区域折扣中 没有匹配到当前的开单计费重量的折扣 则不进行折扣计算  直接返回
				  if(resultDiscount.getRenewalDiscountRate()==null||resultDiscount.getFirstDiscountRate()==null){
					  return caculateresultList;
				  }
				  //根据选择的折扣明细查询对应的折扣方案
				  ExpressDiscountEntity discountPlanEntity = new ExpressDiscountEntity();
				  discountPlanEntity.setId(resultDiscount.getExpressDiscountPlanId());
				  discountPlanEntity = expressDiscountPlanService.queryExpressDiscountPlanById(discountPlanEntity);
				  
				  //判断客户FOSS快递区域折扣方案中首重、续重折扣是否一致
				  if(resultDiscount.getFirstDiscountRate().compareTo(resultDiscount.getRenewalDiscountRate())==0){
					  //对比市场活动运费折扣与快递区域折扣值，取折扣小的进行打折计算
					  discountFRTResultList = compareCrmActiveDiscountAndExpressReginDsicount(caculateresultList,resultDiscount,queryBillCacilateDto);
				  }else{
					  *//**
					   * 1.快递折扣方案中渠道为空，则表示合适全部渠道的订单，可以使用区域折扣
					   * 2.foss开单界面直接开单  渠道为null，可以使用区域折扣
					   * 3.快递折扣方案中渠道信息不为空，且包含当前运单的渠道，则可以使用区域折扣
					   *//*
					  if(CollectionUtils.isNotEmpty(discountPlanEntity.getChannelCodes()) && queryBillCacilateDto.getChannelCode()==null) {
						//同是否单独定价为“否”的情况
						  discountFRTResultList = handleExpressFRTDiscountAndCrmActiveDiscount( queryBillCacilateDto.getOriginalOrgCode(),queryBillCacilateDto.getDestinationOrgCode(), queryBillCacilateDto.getReceiveDate(), queryBillCacilateDto.getWeight(),
			    				  queryBillCacilateDto.getVolume(),queryBillCacilateDto.getDeptRegionId(),queryBillCacilateDto.getArrvRegionId(),caculateresultList,queryBillCacilateDto);
					  } else {
						  if(CollectionUtils.isNotEmpty(discountPlanEntity.getChannelCodes()) && queryBillCacilateDto.getChannelCode()!=null) {
							  if(discountPlanEntity.getChannelCodes().contains(queryBillCacilateDto.getChannelCode())){
									//按照客户FOSS快递区域折扣打折，且折后续重费率不能低于CRM合同约定最低续重费率
								    discountFRTResultList = calculateExpressReginDiscount(queryBillCacilateDto,resultPrice,resultDiscount);
								  }else{
										//同是否单独定价为“否”的情况
									  discountFRTResultList = handleExpressFRTDiscountAndCrmActiveDiscount( queryBillCacilateDto.getOriginalOrgCode(),queryBillCacilateDto.getDestinationOrgCode(), queryBillCacilateDto.getReceiveDate(), queryBillCacilateDto.getWeight(),
						    				  queryBillCacilateDto.getVolume(),queryBillCacilateDto.getDeptRegionId(),queryBillCacilateDto.getArrvRegionId(),caculateresultList,queryBillCacilateDto);
								  }
						  } else {
							//按照客户FOSS快递区域折扣打折，且折后续重费率不能低于CRM合同约定最低续重费率
							    discountFRTResultList = calculateExpressReginDiscount(queryBillCacilateDto,resultPrice,resultDiscount);
						  }
						  
					  }
					  if(CollectionUtils.isEmpty(discountPlanEntity.getChannelCodes())||queryBillCacilateDto.getChannelCode()==null
							  ){
						//按照客户FOSS快递区域折扣打折，且折后续重费率不能低于CRM合同约定最低续重费率
					    discountFRTResultList = calculateExpressReginDiscount(queryBillCacilateDto,resultPrice,resultDiscount);
					  }else{
						  
						  if(discountPlanEntity.getChannelCodes().contains(queryBillCacilateDto.getChannelCode())){
							//按照客户FOSS快递区域折扣打折，且折后续重费率不能低于CRM合同约定最低续重费率
						    discountFRTResultList = calculateExpressReginDiscount(queryBillCacilateDto,resultPrice,resultDiscount);
						  }else{
							//按照市场活动直接打折
							discountFRTResultList = doDiscountFRTExpressForActive(queryBillCacilateDto,caculateresultList);
						  }
					  }
					  
				  	}
				 		  
    		  }else{
    			  //获取计算完的运费中的计费重量  根据计费重量确定采用那一段快递区域折扣数据
				  ExpressDiscountDto resultDiscount = new ExpressDiscountDto();
				  
				  for(ExpressDiscountDto discountDto:discountResultList){
					  //快递区域折扣范围为左闭区间  右开区间
					  if(discountDto.getLeftRange().compareTo(resultPrice.getVolumeWeight())<=0 &&
							  discountDto.getRightRange().compareTo(resultPrice.getVolumeWeight())>0){
						  resultDiscount = discountDto;
						  break;
					  }
				  }
				  //如果客户在当前区域折扣中 没有匹配到当前的开单计费重量的折扣 则不进行折扣计算  直接返回
				  if(resultDiscount.getRenewalDiscountRate()==null||resultDiscount.getFirstDiscountRate()==null){
					  return caculateresultList;
				  }
				  
				  //根据选择的折扣明细查询对应的折扣方案
				  ExpressDiscountEntity discountPlanEntity = new ExpressDiscountEntity();
				  discountPlanEntity.setId(resultDiscount.getExpressDiscountPlanId());
				  discountPlanEntity = expressDiscountPlanService.queryExpressDiscountPlanById(discountPlanEntity);
				  /**
				   * 1.快递折扣方案中渠道为空，则表示合适全部渠道的订单，可以使用区域折扣
				   * 2.foss开单界面直接开单  渠道为null，可以使用区域折扣
				   * 3.快递折扣方案中渠道信息不为空，且包含当前运单的渠道，则可以使用区域折扣
				   *//*
				  if(CollectionUtils.isNotEmpty(discountPlanEntity.getChannelCodes()) && queryBillCacilateDto.getChannelCode()==null) {
						//同是否单独定价为“否”的情况
						  discountFRTResultList = handleExpressFRTDiscountAndCrmActiveDiscount( queryBillCacilateDto.getOriginalOrgCode(),queryBillCacilateDto.getDestinationOrgCode(), queryBillCacilateDto.getReceiveDate(), queryBillCacilateDto.getWeight(),
			    				  queryBillCacilateDto.getVolume(),queryBillCacilateDto.getDeptRegionId(),queryBillCacilateDto.getArrvRegionId(),caculateresultList,queryBillCacilateDto);
					  } else {
						  if(CollectionUtils.isNotEmpty(discountPlanEntity.getChannelCodes()) && queryBillCacilateDto.getChannelCode()!=null) {
							  if(discountPlanEntity.getChannelCodes().contains(queryBillCacilateDto.getChannelCode())){
									//按照客户FOSS快递区域折扣打折，且折后续重费率不能低于CRM合同约定最低续重费率
								    discountFRTResultList = calculateExpressReginDiscount(queryBillCacilateDto,resultPrice,resultDiscount);
								  }else{
										//同是否单独定价为“否”的情况
									  discountFRTResultList = handleExpressFRTDiscountAndCrmActiveDiscount( queryBillCacilateDto.getOriginalOrgCode(),queryBillCacilateDto.getDestinationOrgCode(), queryBillCacilateDto.getReceiveDate(), queryBillCacilateDto.getWeight(),
						    				  queryBillCacilateDto.getVolume(),queryBillCacilateDto.getDeptRegionId(),queryBillCacilateDto.getArrvRegionId(),caculateresultList,queryBillCacilateDto);
								  }
						  } else {
							//按照客户FOSS快递区域折扣打折，且折后续重费率不能低于CRM合同约定最低续重费率
							    discountFRTResultList = calculateExpressReginDiscount(queryBillCacilateDto,resultPrice,resultDiscount);
						  }
						  
					  }
				  if(CollectionUtils.isEmpty(discountPlanEntity.getChannelCodes())||queryBillCacilateDto.getChannelCode()==null
						  ){
					  //在公布价计算的运费基础上，根据客户FOSS快递区域折扣打折，打折后续重费率不能低于CRM合同最低费率
					  discountFRTResultList = calculateExpressReginDiscount(queryBillCacilateDto,resultPrice,resultDiscount);
				  }else{
					  
					  if(discountPlanEntity.getChannelCodes().contains(queryBillCacilateDto.getChannelCode())){
						  //在公布价计算的运费基础上，根据客户FOSS快递区域折扣打折，打折后续重费率不能低于CRM合同最低费率
						  discountFRTResultList = calculateExpressReginDiscount(queryBillCacilateDto,resultPrice,resultDiscount);
					  }else{
						  //不执行任何折扣的计算  直接返回计算后的价格
						  return caculateresultList;
					  }
				  }
				  
				  
    		  }
    	  }else{
    		  //同是否单独定价为“否”的情况
    		  discountFRTResultList = handleExpressFRTDiscountAndCrmActiveDiscount( queryBillCacilateDto.getOriginalOrgCode(),queryBillCacilateDto.getDestinationOrgCode(), queryBillCacilateDto.getReceiveDate(), queryBillCacilateDto.getWeight(),
    				  queryBillCacilateDto.getVolume(),queryBillCacilateDto.getDeptRegionId(),queryBillCacilateDto.getArrvRegionId(),caculateresultList,queryBillCacilateDto);

    	  }*/
      }
      
      /**
       * 首先根据出发区域  到达区域查询折扣   如果没有则根据区域所在的省市区  由下往上查询折扣
       * dp-foss-dongjialing
       * 225131
       */
      public List<ExpressDiscountDto> isExistCustomerExpressDiscount(ExpressDiscountDto expressDiscountDto) {
    	  List<ExpressDiscountDto> discountResultList = new ArrayList<ExpressDiscountDto>();
    	  discountResultList = expressDiscountPlanService.queryExpressDiscountPlanDetailByCondition(expressDiscountDto);
    	  if(CollectionUtils.isNotEmpty(discountResultList)){
        		return discountResultList;
        	}else{
        		//设置快递价格区域的查询参数
                PriceRegionExpressEntity queryRegionEntity =  new PriceRegionExpressEntity();
                queryRegionEntity.setActive(FossConstants.ACTIVE);
                queryRegionEntity.setId(expressDiscountDto.getStartRegionCode());
                queryRegionEntity.setRegionNature(PricingConstants.PRICING_REGION);
                //查询出快递价格出发区域
                PriceRegionExpressEntity startExpressRegin =null;
                List<PriceRegionExpressEntity> startReginList = regionExpressService.findRegionByCondition(queryRegionEntity);
                if(CollectionUtils.isNotEmpty(startReginList)){
                	startExpressRegin = startReginList.get(0);
                }
                 
               
                queryRegionEntity.setId(expressDiscountDto.getArriveRegionCode());
                //查询出快递价格到达区域
                PriceRegionExpressEntity arriveExpressRegin =null;
                List<PriceRegionExpressEntity> arriveReginList = regionExpressService.findRegionByCondition(queryRegionEntity);
                if(CollectionUtils.isNotEmpty(arriveReginList)){
                	arriveExpressRegin = arriveReginList.get(0);
                }
                //根据到达区域和出发区域查询不到数据  则直接返回null
                if(startExpressRegin==null || arriveExpressRegin==null){
                	return  null;
                }
                //出发区域及其上级区域集合
                List<PriceRegionExpressEntity> startExpressUpRegins = new ArrayList<PriceRegionExpressEntity>();
                //到达区域及其上级区域集合
                List<PriceRegionExpressEntity> arriveExpressUpRegins = new ArrayList<PriceRegionExpressEntity>();
              
                //配置出发区域上级区域查询参数
                queryRegionEntity.setId(null);
                queryRegionEntity.setNationCode(startExpressRegin.getNationCode());
                queryRegionEntity.setProCode(startExpressRegin.getProCode());
                queryRegionEntity.setCityCode(startExpressRegin.getCityCode());
                queryRegionEntity.setCountyCode(startExpressRegin.getCountyCode());
                
                startExpressUpRegins = regionExpressService.searchExpressAllUpRegionByCondition(queryRegionEntity);
                
                //配置到达区域上级区域查询参数
                queryRegionEntity.setNationCode(arriveExpressRegin.getNationCode());
                queryRegionEntity.setProCode(arriveExpressRegin.getProCode());
                queryRegionEntity.setCityCode(arriveExpressRegin.getCityCode());
                queryRegionEntity.setCountyCode(arriveExpressRegin.getCountyCode());
                
                arriveExpressUpRegins = regionExpressService.searchExpressAllUpRegionByCondition(queryRegionEntity);
                //按照优先级对价格区域进行排序
                startExpressUpRegins = sortExpressReginByPriority(startExpressUpRegins);
                arriveExpressUpRegins = sortExpressReginByPriority(arriveExpressUpRegins);
              //循环匹配价格  如果查询到有价格  则停止查询
                boolean  isContinue =true;
                for(PriceRegionExpressEntity startRegin:startExpressUpRegins){
                	
                	if(isContinue){
                		for(PriceRegionExpressEntity arriveRegin:arriveExpressUpRegins){
                			expressDiscountDto.setStartRegionCode(startRegin.getId());
                			expressDiscountDto.setArriveRegionCode(arriveRegin.getId());
                			discountResultList = expressDiscountPlanService.queryExpressDiscountPlanDetailByCondition(expressDiscountDto);
                    		if(CollectionUtils.isNotEmpty(discountResultList)){
                    			isContinue = false;
                    			break;
                    		}
                    	}
                	}
                	
                }
                return discountResultList;
                
        }
      }
      /**
       * 
       *
       * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.BillCaculateService.sortExpressReginByPriority
       * @Description:根据快递价格区域的优先级排序区域集合  由高到底
       *
       *
       * @version:v1.0
       * @author:DP-FOSS-YANGKANG
       * @date:2015-1-23 下午2:13:56
       *
       * Modification History:
       * Date         Author      Version     Description
       * -----------------------------------------------------------------
       * 2015-1-23    DP-FOSS-YANGKANG      v1.0.0         create
       */
      public  List<PriceRegionExpressEntity> sortExpressReginByPriority(List<PriceRegionExpressEntity> expressReginList){
    	  
    	  if(CollectionUtils.isEmpty(expressReginList)){
    		return null;
    	  }
    	  //按照优先级由低到高排序
    	  for(int i=0;i<expressReginList.size();i++){
    		  for(int j=i+1;j<expressReginList.size();j++){
    			  PriceRegionExpressEntity tempEntity = expressReginList.get(i);
    			  PriceRegionExpressEntity entity = expressReginList.get(j);
    			  if(tempEntity.getPriority()<entity.getPriority()){
    				  expressReginList.set(i, entity);
    				  expressReginList.set(j, tempEntity);
    			  }
    		  }
    	  }
    	  return expressReginList;
      }      
      /**
       * 
       *
       * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.BillCaculateService.calculateExpressReginDiscount
       * @Description:根据客户快递区域折扣方案在公布价的基础上进行折扣计算  并返回最终即如果
       *
       * @param queryBillCacilateDto
       * @param resultPrice
       * @param resultDiscount
       * @return
       *
       * @version:v1.0
       * @author:DP-FOSS-YANGKANG
       * @date:2015-1-24 上午10:03:34
       *
       * Modification History:
       * Date         Author      Version     Description
       * -----------------------------------------------------------------
       * 2015-1-24    DP-FOSS-YANGKANG      v1.0.0         create
       */
      public  List<ProductPriceDto> calculateExpressReginDiscount(QueryBillCacilateDto queryBillCacilateDto,ProductPriceDto resultPrice,ExpressDiscountDto resultDiscount){
    	    
    	    //打折之后运费返回的最终结果
    	    List<ProductPriceDto> discountFRTResultList = new ArrayList<ProductPriceDto>();
    	    //按照客户FOSS快递区域折扣打折，且折后续重费率不能低于CRM合同约定最低续重费率
		  	DiscountParmDto discountParmDto = new DiscountParmDto();
		    //出发部门
		  	OrgAdministrativeInfoEntity deptOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(queryBillCacilateDto.getOriginalOrgCode());
		    //到达部门
		  	OrgAdministrativeInfoEntity arrvOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(queryBillCacilateDto.getDestinationOrgCode());
		  	//匹配出发、到达折扣的城市信息
		    if(deptOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(deptOrgAdministrativeInfoEntity.getCityCode())) {
		    	 discountParmDto.setDeptCityCode(deptOrgAdministrativeInfoEntity.getCityCode());
		    }
		    if(arrvOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(arrvOrgAdministrativeInfoEntity.getCityCode())) {
		    	discountParmDto.setArrvCityCode(arrvOrgAdministrativeInfoEntity.getCityCode());
		    }
		    //依次设置折扣信息的出发区域、到达区域、始发部门、到达部门、计价条目信息、
		    //产品、货物、业务日期、重货、轻货、原始费用、计费类别
		    discountParmDto.setCriteriaDetailId(resultPrice.getId());
		    discountParmDto.setDeptRegionId(queryBillCacilateDto.getDeptRegionId());
		    discountParmDto.setArrvRegionId(queryBillCacilateDto.getArrvRegionId());
		    discountParmDto.setOriginalOrgCode(queryBillCacilateDto.getOriginalOrgCode());
		    discountParmDto.setDestinationOrgCode(queryBillCacilateDto.getDestinationOrgCode());
		    discountParmDto.setPricingEntryCode(resultPrice.getPriceEntityCode());
		    discountParmDto.setPricingEntryName(resultPrice.getPriceEntityName());
		    discountParmDto.setProductCode(resultPrice.getProductCode());
		    discountParmDto.setGoodsTypeCode(resultPrice.getGoodsTypeCode());
		    discountParmDto.setReceiveDate(queryBillCacilateDto.getReceiveDate());
		    discountParmDto.setWeight(queryBillCacilateDto.getWeight());
		    discountParmDto.setVolume(queryBillCacilateDto.getVolume());
		    //计算后的价格
		    discountParmDto.setOriginnalCost(resultPrice.getCaculateFee());
		    discountParmDto.setCustomCode(queryBillCacilateDto.getCustomerCode());
			discountParmDto.setIndustryCode(queryBillCacilateDto.getIndustrulCode());
			discountParmDto.setMinFee(resultPrice.getMinFee());
			discountParmDto.setMaxFee(resultPrice.getMaxFee());
			//计费类型 体积 、重量、费用
			discountParmDto.setCaculateType(resultPrice.getCaculateType());
			discountParmDto.setSaleChannelCode(queryBillCacilateDto.getChannelCode());
			
		    DiscountResultDto discountResult= new DiscountResultDto();
		    
		    if(resultDiscount != null){
		    	discountResult.setId(resultDiscount.getId());
			    //设置首重折扣
			    discountResult.setDiscountRate(resultDiscount.getFirstDiscountRate());		    
			    //设置续重折扣
			    discountResult.setRenewalDiscountRate(resultDiscount.getRenewalDiscountRate());
		    }
		    
		    //设置打折合同类型
		    discountResult.setContractType(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT);

		    discountResult.setDiscountMode(PriceEntityConstants.PRICING_CODE_FRT);
		    /*//查询客户合同优惠信息   方便与快递区域折扣进行比较
		    PreferentialInfoDto preferentialInfo = preferentialService.queryPreferentialInfo(queryBillCacilateDto.getCustomerCode(), queryBillCacilateDto.getReceiveDate(), ProductEntityConstants.PRICING_PRODUCT_C2_C20005);*/
		    //按照快递区域折扣方案进行打折计算
		    DiscountResultDto discountResultDto =PriceUtil.calculateExpressReginDiscountClientData(discountResult, discountParmDto, resultPrice,resultDiscount);
		    //折扣费率
			BigDecimal actualFeeRate = resultPrice.getActualFeeRate();
			//保留原有费率
			resultPrice.setInitFeeRate(actualFeeRate);
			//将折扣价格赋值原计算后价格
			resultPrice.setCaculateFee(discountResultDto.getDiscountValue());
			//将折扣价格赋值折扣价格
			resultPrice.setDiscountFee(discountResultDto.getDiscountValue());
			//保留计价明细ID
			resultPrice.setId(discountResultDto.getId()); 
			//重新设置费率
			if(resultDiscount!=null){
				//打折之后续重费率
				BigDecimal discountFeeRate = discountResultDto.getRenewalDiscountRate().multiply(actualFeeRate).divide(BigDecimal.valueOf(NUMBER_100));
				//如果打折之后的续重费率小于客户合同中配置的最低费率  则取合同中的最低费率
				if(resultDiscount.getContinueHeavyLowestRate()!=null 
		    			&&discountFeeRate.compareTo(resultDiscount.getContinueHeavyLowestRate())<0){
					resultPrice.setActualFeeRate(resultDiscount.getContinueHeavyLowestRate());
				}else{
					resultPrice.setActualFeeRate(discountResultDto.getRenewalDiscountRate().multiply(actualFeeRate).divide(BigDecimal.valueOf(NUMBER_100)));
				}
			}else{
				resultPrice.setActualFeeRate(discountResultDto.getRenewalDiscountRate().multiply(actualFeeRate).divide(BigDecimal.valueOf(NUMBER_100)));
			}
				  
			//保留折扣相关信息
			resultPrice.setDiscountPrograms(discountResultDto.getDiscountPrograms());
			discountFRTResultList.add(resultPrice);
			return discountFRTResultList;
      }
      /**
       * 
       *
       * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.BillCaculateService.compareCrmActiveDiscountAndExpressReginDsicount
       * @Description:当快递区域折扣的首重与续重一致时 与市场活动折扣做对比  取小进行快递运费折扣的计算
       *
       * @param caculateresultList
       * @param expressDiscountDto
       * @param queryBillCacilateDto
       * @return
       *
       * @version:v1.0
       * @author:DP-FOSS-YANGKANG
       * @date:2015-1-24 上午10:35:53
       *
       * Modification History:
       * Date         Author      Version     Description
       * -----------------------------------------------------------------
       * 2015-1-24    DP-FOSS-YANGKANG      v1.0.0         create
       */
      public List<ProductPriceDto> compareCrmActiveDiscountAndExpressReginDsicount(List<ProductPriceDto> caculateresultList,ExpressDiscountDto expressDiscountDto,QueryBillCacilateDto queryBillCacilateDto){
    	//获取运费
  		if(CollectionUtils.isNotEmpty(caculateresultList)){
  			for (int i = 0; i < caculateresultList.size(); i++) { 
  				ProductPriceDto price=caculateresultList.get(i);
  				if(price!=null && PriceEntityConstants.PRICING_CODE_FRT.equals(price.getPriceEntityCode())){
  					queryBillCacilateDto.setTransportFee(price.getCaculateFee());
  					break;
  				}
  			}
  		}
		List<ProductPriceDto> productPriceDtos = new ArrayList<ProductPriceDto>();
		if(CollectionUtils.isNotEmpty(caculateresultList)) {			
			for (int i = 0; i < caculateresultList.size(); i++) {  
				//费用对象
				ProductPriceDto productPriceDto =  caculateresultList.get(i);  
				//折扣列表
				List<PriceDiscountDto> priceDiscountDtos = productPriceDto.getDiscountPrograms();
				if(priceDiscountDtos==null){
					priceDiscountDtos=new ArrayList<PriceDiscountDto>();
				}
				//市场营销活动DTO
				MarkActivitiesQueryConditionDto activeDto=queryBillCacilateDto.getActiveDto();
				//计算出的价格
          		BigDecimal caculateFee=productPriceDto.getCaculateFee();
          		if(CollectionUtils.isNotEmpty(priceDiscountDtos)){
          			PriceDiscountDto dis=priceDiscountDtos.get(0);
          			if(dis!=null && dis.getOriginnalCost()!=null){
          				//获取原始费用
          				caculateFee=dis.getOriginnalCost();
          			}
          		}
          		//ID
          		String id=productPriceDto.getId();
				//获取营销活动折扣
		    	if(activeDto!=null && CollectionUtils.isNotEmpty(activeDto.getOptionList())){			    			    		
		    		for(MarkOptionsEntity entity:activeDto.getOptionList()){			    			
		        		if(entity!=null && entity.getName()!=null && entity.getValue()!=null){        			
		            		//获取折扣类型(运费、保费等)
		            		String discountType=entity.getName();
		            		//获取折扣值
		            		BigDecimal discountRate=entity.getValue();
		            		//续重最低费率
		            		BigDecimal continueHeavyLowestRate=entity.getContinueHeavyLowestRate();
		            		//营销活动折扣相应的CRM_ID
		            		BigDecimal crmId=entity.getCrmId();			            		
	            			//运费
		            		if(PriceEntityConstants.PRICING_CODE_FRT.equals(discountType) 
		            				&& PriceEntityConstants.PRICING_CODE_FRT.equals(productPriceDto.getPriceEntityCode())){		      
	            			 
	            			  //根据选择的折扣明细查询对应的折扣方案
		      				  ExpressDiscountEntity discountPlanEntity = new ExpressDiscountEntity();
		      				  discountPlanEntity.setId(expressDiscountDto.getExpressDiscountPlanId());
		      				  discountPlanEntity = expressDiscountPlanService.queryExpressDiscountPlanById(discountPlanEntity);
		      				 /**
							   * 1.快递折扣方案中渠道为空，则表示合适全部渠道的订单，可以使用区域折扣
							   * 2.foss开单界面直接开单  渠道为null，可以使用区域折扣
							   * 3.快递折扣方案中渠道信息不为空，且包含当前运单的渠道，则可以使用区域折扣
							   */
		      				  if(CollectionUtils.isEmpty(discountPlanEntity.getChannelCodes())||
		      						queryBillCacilateDto.getChannelCode()==null){
		      					  
		      					//对比市场与区域折扣值，取小，如果取区域折扣，则打折后费率不能低于CRM合同最低费率；取市场折扣，折后费率不能低于市场最低费率
	            				if(expressDiscountDto.getFirstDiscountRate().divide(BigDecimal.valueOf(NUMBER_100)).compareTo(discountRate)>0){
	            					//按照市场活动折扣进行折扣计算
	            					PriceDiscountDto priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,null);	
	            					caculateDiscountFeeForActive(productPriceDto,priceDiscountDto,priceDiscountDtos,continueHeavyLowestRate);
	            					//保留折扣相关信息
	            					productPriceDto.setDiscountPrograms(priceDiscountDtos);
	            					productPriceDtos.add(productPriceDto);
	            				}else{
	            					//按照快递区域折扣进行计算
	            					productPriceDtos = calculateExpressReginDiscount(queryBillCacilateDto,productPriceDto,expressDiscountDto);
	            				}

		            			}else{
		      					  	//快递折扣方案中渠道信息不为空，且包含当前运单的渠道，则可以使用区域折扣
		            				if(discountPlanEntity.getChannelCodes().contains(queryBillCacilateDto.getChannelCode())){
		            					//对比市场与区域折扣值，取小，如果取区域折扣，则打折后费率不能低于CRM合同最低费率；取市场折扣，折后费率不能低于市场最低费率
			            				if(expressDiscountDto.getFirstDiscountRate().divide(BigDecimal.valueOf(NUMBER_100)).compareTo(discountRate)>0){
			            					//按照市场活动折扣进行折扣计算
			            					PriceDiscountDto priceDiscountDto = PriceUtil.calculateActiveDiscountClientData(discountType,discountRate,caculateFee,id,activeDto,crmId,null);	
			            					caculateDiscountFeeForActive(productPriceDto,priceDiscountDto,priceDiscountDtos,continueHeavyLowestRate);
			            					//保留折扣相关信息
			            					productPriceDto.setDiscountPrograms(priceDiscountDtos);
			            					productPriceDtos.add(productPriceDto);
			            				}else{
			            					//按照快递区域折扣进行计算
			            					productPriceDtos = calculateExpressReginDiscount(queryBillCacilateDto,productPriceDto,expressDiscountDto);
			            				}
		            				}else{
		            					//直接使用市场活动折扣进行运费计算
		            					//按照市场活动直接打折
		            					productPriceDtos = doDiscountFRTExpressForActive(queryBillCacilateDto,caculateresultList);
		            				}
		      				  }
		            		}			            				            				            	
		        		}
		    		}    		
		    	}
			}		
			}	
		//返回费用集合
    	return productPriceDtos;
      }
      
    /**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService.searchExpressProductPriceListOld
	 * @Description:快递定价之前计算快递运费方法  确保快递定价上线之前运单更改单与开单保持一致
	 *
	 * @param queryBillCacilateDto
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-2-7 上午9:19:42
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-2-7    DP-FOSS-YANGKANG      v1.0.0         create
	 */
  	@Override
  	public List<ProductPriceDto> searchExpressProductPriceListOld(
  			QueryBillCacilateDto queryBillCacilateDto)
  			throws BillCaculateServiceException {
  		/**
  		 * 1.1 根据始发部门code 获取始发区域IDT_SRV_PRICE_PLAN 1.2 根据达到部门code 获取到达区域ID 2.1
  		 * 根据始发区域ID,到达区域ID, 营业部收货日期, 是否接货查询计费规则和计价方式明细列表，以ProductPriceDto对象返回；
  		 */
  		log.debug("FRT start calcuate>>" + new Date());
  		// 数据检验
  		PriceUtil.checkExpressQueryBillCacilateDtoData(queryBillCacilateDto);

  		// 如果当前货物为NULL,设置货物编码为通用状态
  		if (StringUtil.isEmpty(queryBillCacilateDto.getGoodsCode())) {
  			queryBillCacilateDto
  					.setGoodsCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
  		}
  		// 获得当前传入的产品、始发部门、到达部门、业务日期、币种、货物类别，是否接货
  		String productCode = queryBillCacilateDto.getProductCode();
  		String originalOrgCode = queryBillCacilateDto.getOriginalOrgCode();
  		String destinationOrgCode = queryBillCacilateDto
  				.getDestinationOrgCode();
  		Date receiveDate = queryBillCacilateDto.getReceiveDate();
  		String currencyCode = queryBillCacilateDto.getCurrencyCdoe();
  		String goodsCode = queryBillCacilateDto.getGoodsCode();
  		String isSelfPickUp = queryBillCacilateDto.getIsSelfPickUp();
  		// 默认是否接货为否
  		if (StringUtil.isEmpty(isSelfPickUp)) {
  			isSelfPickUp = FossConstants.NO;
  		}
  		// 自提减的钱
  		double selfPickUpSubStract = 0;

  		if (FossConstants.YES.equalsIgnoreCase(isSelfPickUp)) {
  			ConfigurationParamsEntity entity = configurationParamsService
  					.queryConfigurationParamsByOrgCode(
  							DictionaryConstants.SYSTEM_CONFIG_PARM__BAS,
  							ConfigurationParamsConstants.BAS_PARM__EXPRESS_SELF_PICKUP_SUBSTRACT,
  							originalOrgCode);
  			if (entity != null) {
  				String parmValue = entity.getConfValue();
  				try {
  					selfPickUpSubStract = Double.valueOf(parmValue);
  				} catch (BillCaculateServiceException e) {
  					throw new BillCaculateServiceException("很抱歉，查询快递自提减免费用出错",
  							e.getMessage());
  				}
  			} else {
  				throw new BillCaculateServiceException(
  						"很抱歉，因为开单时选择了自提，但是没有查询到自提直接减免的费用，我们不能为你计算出最后的费用");
  			}
  		}

  		// 重货
  		BigDecimal weight = queryBillCacilateDto.getWeight();
  		// 轻货
  		BigDecimal volume = queryBillCacilateDto.getVolume();
  		// 查询出发区域ID
  		String originalId = queryBillCacilateDto.getDeptRegionId();
  		if (StringUtil.isEmpty(originalId)) {

  			originalId = regionExpressService.findRegionOrgByDeptNo(
  					originalOrgCode, receiveDate, null,
  					PricingConstants.PRICING_REGION);
  			if (StringUtil.isEmpty(originalId)) {
  				return null;
  			}
  		}
  		// 查询目的地区域ID
  		String destinationId = queryBillCacilateDto.getArrvRegionId();
  		if (StringUtil.isEmpty(destinationId)
  				&& StringUtil.isNotBlank(destinationOrgCode)) {
  			destinationId = regionExpressService.findRegionOrgByDeptNo(
  					destinationOrgCode, receiveDate, null,
  					PricingConstants.PRICING_REGION);
  			if (StringUtil.isEmpty(destinationId)) {
  				return null;
  			}
  		}
  		// 运费查询Bean
  		QueryProductPriceDto queryProductPriceDto = new QueryProductPriceDto();
  		if (null == currencyCode) {
  			currencyCode = FossConstants.CURRENCY_CODE_RMB;
  		}
  		ProductEntity productEntity = productService.getProductByCache(
  				productCode, receiveDate);
  		if (productEntity == null) {
  			return null;
  		}
  		// 根据客户端传入的三级产品得到二级产品
  		productCode = productEntity.getParentCode();
  		GoodsTypeEntity goodsTypeEntity = goodsTypeService.getGoodsTypeByCache(
  				goodsCode, receiveDate);
  		queryProductPriceDto.setProductCode(productCode);
  		// 设置货币、始发区域、到达区域、航班类别、货物、是否接货、计费规则类型、状态
  		queryProductPriceDto.setCurrencyCode(currencyCode);
  		queryProductPriceDto.setOriginalOrgId(originalId);
  		queryProductPriceDto.setDestinationId(destinationId);
  		queryProductPriceDto.setGoodsTypeCode(goodsCode);
  		queryProductPriceDto.setReceiveDate(receiveDate);
  		queryProductPriceDto.setIsSelfPickUp(isSelfPickUp);
  		queryProductPriceDto.setType(PricingConstants.VALUATION_TYPE_PRICING);// 价格定义
  		queryProductPriceDto.setActive(FossConstants.ACTIVE);
  		// 计费条目
  		PriceEntity priceEntity = priceEntryService.getPriceEntryByCache(
  				PriceEntityConstants.PRICING_CODE_FRT, receiveDate);
  		if (priceEntity == null) {
  			return null;
  		}
  		// 根据三级产品查询计算费用
  		List<ResultProductPriceDto> resultList = expressPriceValuationService
  				.queryPriceValuationByCalculaCondition(queryProductPriceDto);
  		
  		if(CollectionUtils.isEmpty(resultList)){
  			return null;
  		}
  		
  		List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
  		/**
  		 * DMANA-7337:合同客户重泡比调整
  		 * @author 200945 - wutao
  		 * 通过客户编码和当前营业部收货时间去查询客户优惠折扣表，获取泡沫重泡比重字段
  		 * 备注：
  		 * <p>在CRM那变传递过来存在综合那边的信息只会传递零担和快递（二级），但是3.60特惠件和标准快递的二级产品是不同的【EXPRESS_RCP】和【EXPRESS】
  		 * 	  重泡比不区分这俩种运输性质，因此在开单的时候选择的不论是【3.60】还是【标准快递】，在获取重泡比的时候都直接转为【EXPRESS】去获取；
  		 *  <a>原因：因为这块之前在3.60的时候没有通知到CRM，因此CRM那变不会传递3.60特惠件的二级产品</a>
  		 * </p>
  		 *
  		 */
  		String prarentProductCode = "";
  		if(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(queryBillCacilateDto.getProductCode())
  				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE.equals(queryBillCacilateDto.getProductCode())){
  			prarentProductCode = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
  		}
  		
  		BigDecimal volumeWeight = validateWeightBubbleRate(queryBillCacilateDto.getCustomerCode(),queryBillCacilateDto.getReceiveDate(),prarentProductCode,volume,queryBillCacilateDto.getReceiveOrgCode());
  	
  		// 其他运输方式价格
  		caculateresultList = PriceUtil.calculateExpressCostServices(weight,
  				volume, resultList, receiveDate, productEntity,
  				goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
  		
  		/**
  		 *以下判断当前是否月结客户代码不会执行，可以不看
  		 */
  		// 如果当前是算月结客户
  		if (StringUtils.equals(FossConstants.YES, queryBillCacilateDto.getIsMonthlyDate())) {
  			// 根据三级产品查询计算费用
  			queryProductPriceDto.setReceiveDate(receiveDate);
  			List<ResultProductPriceDto> monthlyResultList = expressPriceValuationService.queryPriceValuationByCalculaCondition(queryProductPriceDto);
  			if (CollectionUtils.isEmpty(caculateresultList)) {
  				// 其他运输方式价格
  				caculateresultList = PriceUtil.calculateCostServices(weight, volume, monthlyResultList, receiveDate, productEntity, goodsTypeEntity, priceEntity);

  			}
  		}
  		if (CollectionUtils.isEmpty(caculateresultList)) {
  			return null;
  		}

		// 如果到达部门不为空、则在价格计算完成后的基础上接着计算折扣、否则不计费算折扣、直接返回价格
		if (StringUtil.isNotBlank(destinationOrgCode)) {
			/**
			 * 计算快递折扣
			 */
			List<ProductPriceDto> list = doExpressFRTDiscountOld(originalOrgCode, destinationOrgCode, receiveDate, weight, volume, originalId, destinationId, caculateresultList,
					queryBillCacilateDto);
			log.debug("FRT end calcuate>>" + new Date());
			/**
		     * 处理增值服务费CRM营销活动处理
		     */
			list=crmActiveDiscountProcessFRTExpress(queryBillCacilateDto,list);
			return list;
		} else { 				
			/**
		     * 处理增值服务费CRM营销活动处理
		     */
			caculateresultList=crmActiveDiscountProcessFRTExpress(queryBillCacilateDto,caculateresultList);
			return caculateresultList;
		}
  		
  	}
  	
  	
  	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.BillCaculateService.doExpressFRTDiscountOld
	 * @Description:快递定价上线前  计算快递运费折扣方法
	 *
	 * @param originalOrgCode
	 * @param destinationOrgCode
	 * @param receiveDate
	 * @param weight
	 * @param volume
	 * @param originalId
	 * @param destinationId
	 * @param caculateresultList
	 * @param queryBillCacilateDto
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-2-7 上午9:32:02
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-2-7    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public List<ProductPriceDto> doExpressFRTDiscountOld(String originalOrgCode,
		String destinationOrgCode, Date receiveDate, BigDecimal weight,
		BigDecimal volume, String originalId, String destinationId,
		List<ProductPriceDto> caculateresultList,QueryBillCacilateDto queryBillCacilateDto) {
		
	    List<ProductPriceDto> discountResultList = new ArrayList<ProductPriceDto>();
	    //获取折扣优先级
	    List<DiscountPriorityEntity> discountPriorityEntities = discountPriorityService.getDiscountPriorityByCache();
	    if(CollectionUtils.isNotEmpty(caculateresultList)) {
	    	boolean flag = false;
	    	for (int i = 0; i < caculateresultList.size(); i++) {
	    		flag = false;
	    		ProductPriceDto productPriceDto = caculateresultList.get(i);
	    		//是否按照优先级处理
	    		if(CollectionUtils.isNotEmpty(discountPriorityEntities)) {
	    			DiscountParmDto discountParmDto = new DiscountParmDto();
	    		    //出发部门
	    		    OrgAdministrativeInfoEntity deptOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(originalOrgCode);
	    		    //到达部门
	    		    OrgAdministrativeInfoEntity arrvOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(destinationOrgCode);
	    		    //匹配出发、到达折扣的城市信息
	    		    if(deptOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(deptOrgAdministrativeInfoEntity.getCityCode())) {
	    		    	 discountParmDto.setDeptCityCode(deptOrgAdministrativeInfoEntity.getCityCode());
	    		    }
	    		    if(arrvOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(arrvOrgAdministrativeInfoEntity.getCityCode())) {
	    		    	discountParmDto.setArrvCityCode(arrvOrgAdministrativeInfoEntity.getCityCode());
	    		    }
	    		    //依次设置折扣信息的出发区域、到达区域、始发部门、到达部门、计价条目信息、
	    		    //产品、货物、业务日期、重货、轻货、原始费用、计费类别
	    		    discountParmDto.setCriteriaDetailId(productPriceDto.getId());
	    		    discountParmDto.setDeptRegionId(originalId);
	    		    discountParmDto.setArrvRegionId(destinationId);
	    		    discountParmDto.setOriginalOrgCode(originalOrgCode);
	    		    discountParmDto.setDestinationOrgCode(destinationOrgCode);
	    		    discountParmDto.setPricingEntryCode(productPriceDto.getPriceEntityCode());
	    		    discountParmDto.setPricingEntryName(productPriceDto.getPriceEntityName());
	    		    discountParmDto.setProductCode(productPriceDto.getProductCode());
	    		    discountParmDto.setGoodsTypeCode(productPriceDto.getGoodsTypeCode());
	    		    discountParmDto.setReceiveDate(receiveDate);
	    		    discountParmDto.setWeight(weight);
	    		    discountParmDto.setVolume(volume);
	    		    //计算后的价格
	    		    discountParmDto.setOriginnalCost(productPriceDto.getCaculateFee());
	    		    discountParmDto.setCustomCode(queryBillCacilateDto.getCustomerCode());
					discountParmDto.setIndustryCode(queryBillCacilateDto.getIndustrulCode());
					discountParmDto.setMinFee(productPriceDto.getMinFee());
					discountParmDto.setMaxFee(productPriceDto.getMaxFee());
					//计费类型 体积 、重量、费用
					discountParmDto.setCaculateType(productPriceDto.getCaculateType());
					discountParmDto.setSaleChannelCode(queryBillCacilateDto.getChannelCode());
	    		    DiscountResultDto discountResult=null;
	    		    
	    		    //菜鸟折扣条件 是否返单折扣、返单号发货人客户编码、返单号 
	    		   /* discountParmDto.setIsCainiao(queryBillCacilateDto.getIsCainiao());
	    		    discountParmDto.setOldreceiveCustomerCode(queryBillCacilateDto.getOldreceiveCustomerCode());
	    		    discountParmDto.setReturnWaybillNo(queryBillCacilateDto.getReturnWaybillNo());
	    		    discountParmDto.setReturnInsuranceFee(queryBillCacilateDto.getReturnInsuranceFee());
	    		    discountParmDto.setReturnTransportFee(queryBillCacilateDto.getReturnTransportFee());
	    		    discountParmDto.setReturnbilltime(queryBillCacilateDto.getReturnbilltime());*/
	    		    //对于合同客户优先打折计算
	    			for (int j = 0; j < discountPriorityEntities.size(); j++) {
	    				DiscountPriorityEntity entity = discountPriorityEntities.get(j);
	    				if(entity != null && entity.getCode() != null) {
	    					if(StringUtil.equals(DiscountTypeConstants.DISCOUNT_TYPE__CUSTOMER_CONTRACT, entity.getCode())) {
    							DiscountTypeInterface discountExe = discountTypeFactory.getDiscountTypeImpl(entity.getCode());
        						if(discountExe != null) {
        							//获取客户折扣操作类(快递)
    								discountResult = discountExe.doExpressDiscount(discountParmDto);
        							//返货折扣
        							/*if(queryBillCacilateDto.getIsCainiao()){
        								if(discountResult != null ) {
        								  if(discountResult.getOriginnalCost()!=null){
        									  discountParmDto.setOriginnalCost(discountResult.getOriginnalCost());
        								    }
        								  }
        							}*/
        							//折扣存在,切折扣率不等于1
        							if(discountResult != null ) {
        								boolean isExpress = productService.onlineDetermineIsExpressByProductCode(discountParmDto.getProductCode(), discountParmDto.getReceiveDate());
        								//计算折扣
        								DiscountResultDto discountResultDto = PriceUtil.calculateCustomDiscountClientData(discountResult, discountParmDto, isExpress);								
        								//折扣费率
	    								BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
	    								//保留原有费率
        								productPriceDto.setInitFeeRate(actualFeeRate);
        								/**
        	        				     *@author YANGKANG
        	        				     *DMANA-3936  快递运费折扣率调整 
        	        				     *打折之后的费率不能小于配置的最低费率
        	        				     */
        								 //获取配置的开始运费费率打折限制的时间
        							     String configMinFeeRateStartDate = configurationParamsService.queryConfValueByCode(DiscountOrgConstants.EXPRESS_MIN_FEERATE_START_DATE);
        							     Date startTime = null;
        								if (StringUtils.isNotEmpty(configMinFeeRateStartDate)) {
        									SimpleDateFormat sdf = new SimpleDateFormat(
        											"yyyy-MM-dd HH:mm:ss");
        									// 指定校验的开始时间
        									try {
        										startTime = sdf.parse(configMinFeeRateStartDate);
        									} catch (ParseException e) {
        										e.printStackTrace();
        									}

        								}
        								//如果开单时间在配置的校验开始时间之后，则进行校验
        								if(startTime!=null 
        										&& receiveDate!=null 
        										&& receiveDate.after(startTime)){
        									limitDiscountFeeRate(discountResultDto,productPriceDto);
        								}else{
        									//将折扣价格赋值原计算后价格
            								productPriceDto.setCaculateFee(discountResultDto.getDiscountValue());
            								//将折扣价格赋值折扣价格
            								productPriceDto.setDiscountFee(discountResultDto.getDiscountValue());
            								//保留计价明细ID
            								productPriceDto.setId(discountResultDto.getId()); 
            								//重新设置费率
    	    								productPriceDto.setActualFeeRate(discountResultDto.getDiscountRate().multiply(actualFeeRate));
        								}
    								    		  
        								//保留折扣相关信息
        								productPriceDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
        								discountResultList.add(productPriceDto);
    									flag = true;
    									break;
        							} else {
        								continue;
        							}
        						} else {
        							continue;
        						}
    						} 
	    				} else {
	    					continue;
	    				}
	    			}
	    			if(!flag) {
		    			discountResultList.add(productPriceDto);
		    		}
	    		} else {
	    			discountResultList.add(productPriceDto);
	    		}
	    	} 
	    }
    	
	    return discountResultList;
	    
	}
	/**
	 * 内部员工折扣方法
	 * dp-foss-dongjialing
	 * 225131
	 */
	public List<ProductPriceDto> doLTTInempFRTDiscount(QueryBillCacilateDto queryBillCacilateDto,
			List<ProductPriceDto> caculateresultList,InempDiscountPlanEntity discountEntity,BigDecimal amount) {
		List<ProductPriceDto> discountResultList = new ArrayList<ProductPriceDto>();
		boolean flag = false;
	    if(CollectionUtils.isNotEmpty(caculateresultList)) {
	    	for (int i = 0; i < caculateresultList.size(); ) {
	    		    ProductPriceDto productPriceDto = caculateresultList.get(i);
	    		    if (productPriceDto != null
							&& PriceEntityConstants.PRICING_CODE_FRT.equals(productPriceDto
									.getPriceEntityCode())) {
						
	    		    	flag = true;
					}
	    		    if(flag) {
	    		    	DiscountParmDto discountParmDto = new DiscountParmDto();		    
		    		    //依次设置折扣信息的出发区域、到达区域、始发部门、到达部门、计价条目信息、
		    		    //产品、货物、业务日期、重货、轻货、原始费用、计费类别
		    		    discountParmDto.setCriteriaDetailId(productPriceDto.getId());		    	
		    		    discountParmDto.setPricingEntryCode(productPriceDto.getPriceEntityCode());
		    		    discountParmDto.setPricingEntryName(productPriceDto.getPriceEntityName());		    		    
		    		    //计算后的价格
		    		    discountParmDto.setOriginnalCost(productPriceDto.getCaculateFee());						
						//计费类型 体积 、重量、费用
						discountParmDto.setCaculateType(productPriceDto.getCaculateType());
						discountParmDto.setSaleChannelCode(queryBillCacilateDto.getChannelCode());
						
						//封装折扣信息
						 DiscountResultDto discountResult= new DiscountResultDto();
						    
						 discountResult.setId(discountEntity.getId());
						 //设置打折类型
						 discountResult.setContractType(DiscountTypeConstants.DISCOUNT_TYPE__INEMP);
						 //设置运费折扣
						 discountResult.setDiscountRate(discountEntity.getChargeRebate());		    
						
						 discountResult.setDiscountMode(PriceEntityConstants.PRICING_CODE_FRT);
						 
						 DiscountResultDto discountResultDto = PriceUtil.calculateInempDiscountClientData(discountResult, discountParmDto);
						 //根据剩余额度重新计算运费
						 if(discountResultDto != null){
							 isMothAmountEnable(discountResultDto,discountEntity,amount);
							 if(discountResultDto.getId() != null) {
									//将折扣价格赋值原计算后价格
									productPriceDto.setCaculateFee(discountResultDto.getDiscountValue());
									//将折扣价格赋值折扣价格
									productPriceDto.setDiscountFee(discountResultDto.getDiscountValue());
									//保留计价明细ID
									productPriceDto.setId(discountResultDto.getId());
									//折扣费率
									BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
									productPriceDto.setActualFeeRate(discountResultDto.getDiscountRate().multiply(actualFeeRate));
									//保留折扣相关信息
									productPriceDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
									
							 }
						 }
						 discountResultList.add(productPriceDto);
	    		    }
	    		    break;
	    		}
	    }
		return discountResultList;
		
	}
	/*** 内部员工快递折扣
	 * dp-foss-dongjialing
	 * 225131
	 */
	public List<ProductPriceDto> doFRTGradientDiscount(
			List<ProductPriceDto> caculateresultList,QueryBillCacilateDto queryBillCacilateDto,GradientDiscountItemDto gradientDiscount) {
		List<ProductPriceDto> discountResultList = new ArrayList<ProductPriceDto>();
		boolean flag = false;
	    if(CollectionUtils.isNotEmpty(caculateresultList)) {
	    	for (int i = 0; i < caculateresultList.size(); ) {
	    		    ProductPriceDto productPriceDto = caculateresultList.get(i);
	    		    if (productPriceDto != null
							&& PriceEntityConstants.PRICING_CODE_FRT.equals(productPriceDto
									.getPriceEntityCode())) {
						
	    		    	flag = true;
					}
	    		    if(flag) {
	    		    	DiscountParmDto discountParmDto = new DiscountParmDto();		    
		    		    //依次设置折扣信息的出发区域、到达区域、始发部门、到达部门、计价条目信息、
		    		    //产品、货物、业务日期、重货、轻货、原始费用、计费类别
		    		    discountParmDto.setCriteriaDetailId(productPriceDto.getId());		    	
		    		    discountParmDto.setPricingEntryCode(productPriceDto.getPriceEntityCode());
		    		    discountParmDto.setPricingEntryName(productPriceDto.getPriceEntityName());		    		    
		    		    //计算后的价格
		    		    discountParmDto.setOriginnalCost(productPriceDto.getCaculateFee());			    		   
		    		    discountParmDto.setCustomCode(queryBillCacilateDto.getCustomerCode());
						discountParmDto.setIndustryCode(queryBillCacilateDto.getIndustrulCode());
						discountParmDto.setMinFee(productPriceDto.getMinFee());
						discountParmDto.setMaxFee(productPriceDto.getMaxFee());
						//计费类型 体积 、重量、费用
						discountParmDto.setCaculateType(productPriceDto.getCaculateType());
						discountParmDto.setSaleChannelCode(queryBillCacilateDto.getChannelCode());
						
						//封装折扣信息
						 DiscountResultDto discountResult= new DiscountResultDto();
						    
						 discountResult.setId(gradientDiscount.getCustNum());
						 //设置打折类型
						// discountResult.setContractType(DiscountTypeConstants.DISCOUNT_TYPE__CONTRACT_NORMAL);
						 discountResult.setContractType(DiscountTypeConstants.DISCOUNT_TYPE__FRT_GRADESREBATE);
						 //设置运费折扣
						 discountResult.setDiscountRate(BigDecimal.valueOf(gradientDiscount.getRate()));		    
						
						 discountResult.setDiscountMode(PriceEntityConstants.PRICING_CODE_FRT);
						 boolean isExpress = productService.onlineDetermineIsExpressByProductCode(discountParmDto.getProductCode(), discountParmDto.getReceiveDate());
						 DiscountResultDto discountResultDto = PriceUtil.calculateCustomDiscountClientData(discountResult, discountParmDto, isExpress);					 
						 if(discountResultDto != null && discountResultDto.getId() != null) {
								//将折扣价格赋值原计算后价格
								productPriceDto.setCaculateFee(discountResultDto.getDiscountValue());
								//将折扣价格赋值折扣价格
								productPriceDto.setDiscountFee(discountResultDto.getDiscountValue());
								//保留计价明细ID
								productPriceDto.setId(discountResultDto.getId());
								//折扣费率
								BigDecimal actualFeeRate = productPriceDto.getActualFeeRate();
								productPriceDto.setActualFeeRate(discountResultDto.getDiscountRate().multiply(actualFeeRate));
								//保留折扣相关信息
								productPriceDto.setDiscountPrograms(discountResultDto.getDiscountPrograms());
								
							}
						 discountResultList.add(productPriceDto);
	    		    }
	    		    break;
	    		}
	    }
		return discountResultList;
		
		
	}
	 
	public List<ProductPriceDto> doExpressInempFRTDiscount(QueryBillCacilateDto queryBillCacilateDto,
			List<ProductPriceDto> caculateresultList,InempDiscountPlanEntity discountEntity,BigDecimal amount) {
		 //打折之后运费返回的最终结果
   	  List<ProductPriceDto> discountFRTResultList = new ArrayList<ProductPriceDto>();
  	  
  	  ProductPriceDto resultPrice = null;
  	  //获取运费
		  if(CollectionUtils.isNotEmpty(caculateresultList)){
			for (int i = 0; i < caculateresultList.size(); i++) { 
				ProductPriceDto price=caculateresultList.get(i);
				if(price!=null && PriceEntityConstants.PRICING_CODE_FRT.equals(price.getPriceEntityCode())){
					queryBillCacilateDto.setTransportFee(price.getCaculateFee());
					resultPrice = price;
					break;
				}
			}
		  }
		  //如果运费为空直接返回
		  if(resultPrice==null){
			  return null;
		  }
		  DiscountParmDto discountParmDto = new DiscountParmDto();
		    //出发部门
		  	OrgAdministrativeInfoEntity deptOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(queryBillCacilateDto.getOriginalOrgCode());
		    //到达部门
		  	OrgAdministrativeInfoEntity arrvOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(queryBillCacilateDto.getDestinationOrgCode());
		  	//匹配出发、到达折扣的城市信息
		    if(deptOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(deptOrgAdministrativeInfoEntity.getCityCode())) {
		    	 discountParmDto.setDeptCityCode(deptOrgAdministrativeInfoEntity.getCityCode());
		    }
		    if(arrvOrgAdministrativeInfoEntity != null && StringUtil.isNotBlank(arrvOrgAdministrativeInfoEntity.getCityCode())) {
		    	discountParmDto.setArrvCityCode(arrvOrgAdministrativeInfoEntity.getCityCode());
		    }
		    //依次设置折扣信息的出发区域、到达区域、始发部门、到达部门、计价条目信息、
		    //产品、货物、业务日期、重货、轻货、原始费用、计费类别
		    discountParmDto.setCriteriaDetailId(resultPrice.getId());
		    discountParmDto.setDeptRegionId(queryBillCacilateDto.getDeptRegionId());
		    discountParmDto.setArrvRegionId(queryBillCacilateDto.getArrvRegionId());
		    discountParmDto.setOriginalOrgCode(queryBillCacilateDto.getOriginalOrgCode());
		    discountParmDto.setDestinationOrgCode(queryBillCacilateDto.getDestinationOrgCode());
		    discountParmDto.setPricingEntryCode(resultPrice.getPriceEntityCode());
		    discountParmDto.setPricingEntryName(resultPrice.getPriceEntityName());
		    discountParmDto.setProductCode(resultPrice.getProductCode());
		    discountParmDto.setGoodsTypeCode(resultPrice.getGoodsTypeCode());
		    discountParmDto.setReceiveDate(queryBillCacilateDto.getReceiveDate());
		    discountParmDto.setWeight(queryBillCacilateDto.getWeight());
		    discountParmDto.setVolume(queryBillCacilateDto.getVolume());
		    //计算后的价格
		    discountParmDto.setOriginnalCost(resultPrice.getCaculateFee());
		    discountParmDto.setCustomCode(queryBillCacilateDto.getCustomerCode());
			discountParmDto.setIndustryCode(queryBillCacilateDto.getIndustrulCode());
			discountParmDto.setMinFee(resultPrice.getMinFee());
			discountParmDto.setMaxFee(resultPrice.getMaxFee());
			//计费类型 体积 、重量、费用
			discountParmDto.setCaculateType(resultPrice.getCaculateType());
			discountParmDto.setSaleChannelCode(queryBillCacilateDto.getChannelCode());
			
		    DiscountResultDto discountResult= new DiscountResultDto();
		    
		    discountResult.setId(discountEntity.getId());
		    //设置打折类型
		    discountResult.setContractType(DiscountTypeConstants.DISCOUNT_TYPE__INEMP);
		    //设置首重折扣
		    discountResult.setDiscountRate(discountEntity.getChargeRebate());		    
		    //设置续重折扣
		    discountResult.setRenewalDiscountRate(discountEntity.getChargeRebate());
		    discountResult.setDiscountMode(PriceEntityConstants.PRICING_CODE_FRT);
		    DiscountResultDto discountResultDto =PriceUtil.calculateExpressInempDiscountClientData(discountResult, discountParmDto, resultPrice);
		  //根据剩余额度重新计算运费
		    isMothAmountEnable(discountResultDto,discountEntity,amount);
		    //折扣费率
			BigDecimal actualFeeRate = resultPrice.getActualFeeRate();
			//保留原有费率
			resultPrice.setInitFeeRate(actualFeeRate);
			//将折扣价格赋值原计算后价格
			resultPrice.setCaculateFee(discountResultDto.getDiscountValue());
			//将折扣价格赋值折扣价格
			resultPrice.setDiscountFee(discountResultDto.getDiscountValue());
			//保留计价明细ID
			resultPrice.setId(discountResultDto.getId()); 		
			//打折之后续重费率
			BigDecimal discountFeeRate = discountResultDto.getRenewalDiscountRate().multiply(actualFeeRate).divide(BigDecimal.valueOf(NUMBER_100));				
			resultPrice.setActualFeeRate(discountFeeRate);	  
			//保留折扣相关信息
			resultPrice.setDiscountPrograms(discountResultDto.getDiscountPrograms());
			discountFRTResultList.add(resultPrice);
			
		return discountFRTResultList;
	}
	/**
	 * 根据剩余额度重新计算运费
	 * dp-foss-dongjialing
	 * 225131
	 * @param discountResultDto
	 * @param discountEntity
	 */
	private void isMothAmountEnable(DiscountResultDto discountResultDto,InempDiscountPlanEntity discountEntity,BigDecimal amount) {
		//剩余额度
		BigDecimal differenceValue = BigDecimal.ZERO;
		//折扣值
		BigDecimal reduceValue = discountResultDto.getOriginnalCost().subtract(discountResultDto.getDiscountValue());
		differenceValue = discountEntity.getHighstPreferentialLimit().subtract(amount.divide(BigDecimal.valueOf(NUMBER_100)));
		if(differenceValue.compareTo(BigDecimal.ZERO)>0) {
			if(reduceValue.compareTo(differenceValue)>=0) {
				reduceValue = differenceValue;
			}
			//重新赋值折扣后的价格  和减免费用
			discountResultDto.setDiscountValue(discountResultDto.getOriginnalCost().subtract(reduceValue));
			discountResultDto.getDiscountPrograms().get(0).setReduceFee(reduceValue);
		}
		
	}
	/**
	 * 零担梯度折扣
	 * dp-foss-dongjialing
	 * 225131
	 */
	public GradientDiscountItemDto queryGradientDiscountInfo(
			String customerCode, Date date, BigDecimal totalAmount) {
		if (StringUtil.isBlank(customerCode)) {
			return null;
		} else {
			if (null == date) {
				date = new Date();
			}
			if (totalAmount == null) {
				totalAmount = BigDecimal.ZERO;
			}
			GradientDiscountItemDto gradientDiscountItemDto = null;
			GradientDiscountEntity entity = new GradientDiscountEntity();
			entity.setCustNum(customerCode);
			entity.setPreferType(WaybillConstants.FRT_GRADESREBATE);
			List<GradientDiscountItemDto> list = gradientDiscountService
					.queryGradientDiscountByCondition(entity);
			if (CollectionUtils.isNotEmpty(list)) {
				for (GradientDiscountItemDto gradientDto : list) {
					if (null != gradientDto) {
						// 获取合同开始时间
						Date programBeginTime = gradientDto
								.getProgramBeginTime();
						// 获取合同结束时间
						Date programEndTime = gradientDto.getProgramEndTime();
						// 获取方案生效时间
						Date beginTime = gradientDto.getBeginTime();
						// 获取方案失效时间
						Date endTime = gradientDto.getEndTime();
						// 获取详情里的修改时间
						Date updateTime = gradientDto.getModifyDate();
						if (null != programBeginTime && null != programEndTime
								&& null != beginTime) {
							if (programBeginTime.getTime() <= date.getTime()
									&& programEndTime.getTime() > date
											.getTime()
									&& beginTime.getTime() <= date.getTime()) {
								if (gradientDto.getMinAmount() >= 0
										&& gradientDto.getMaxAmount() > 0
										&& gradientDto.getMinAmount() < gradientDto
												.getMaxAmount()) {
									if (totalAmount
											.compareTo(BigDecimal
													.valueOf(gradientDto
															.getMinAmount())) >= 0
											&& totalAmount.compareTo(BigDecimal
													.valueOf(gradientDto
															.getMaxAmount())) < 0) {
										if (null != endTime) {
											if (null != updateTime) {
												if (endTime.getTime() > date
														.getTime()
														&& updateTime.getTime() > date
																.getTime()) {
													gradientDiscountItemDto = gradientDto;
													break;

												}
											} else {
												if (endTime.getTime() > date
														.getTime()) {
													gradientDiscountItemDto = gradientDto;
													break;

												}
											}
										} else {
											if (null != updateTime) {
												if (updateTime.getTime() > date
														.getTime()) {
													gradientDiscountItemDto = gradientDto;
													break;
												}
											} else {
												gradientDiscountItemDto = gradientDto;
												break;
											}

										}
									}
								}

							}
						}

					}

				}
				return gradientDiscountItemDto;
			}
			return gradientDiscountItemDto;
		}
	}
	/**
	 * dp-foss-dongjialing
	 * 225131
	 * 周特惠对应map
	 */
	private Map<String,String> weekMap() {
		Map<String,String> map = new HashMap<String, String>();
		//周一
		map.put("2","MON");
		//周二
		map.put("3","TUE");
		//周三
		map.put("4","WED");
		//周四
		map.put("5", "THU");
		//周五
		map.put("6", "FRI");
		//周六
		map.put("7", "SAT");
		//周日
		map.put("1", "SUN");
		return map;
		
	}
	/**
	 * 计算纯运费，在开单页面计算装卸费用
	 * @author 265041
	 * */
	public BigDecimal calePurefreight(QueryProductPriceDto queryProductPriceDto,
			BigDecimal weight, BigDecimal volume,
			 Date receiveDate, ProductEntity productEntity,
			GoodsTypeEntity goodsTypeEntity, PriceEntity priceEntity,
			double selfPickUpSubStract,BigDecimal volumeWeight,
			PreferentialInfoDto preferentialInfo,Boolean isDiscount,Boolean versionFlag,
			String originalId,String destinationId){
		BigDecimal purefreight = BigDecimal.ZERO;
		// 根据三级产品查询计算费用
					List<ResultProductPriceDto> resultList = expressPriceValuationService
							.queryPriceValuationByCalculaCondition(queryProductPriceDto);
					if(CollectionUtils.isEmpty(resultList)){
						return null;
					}
					List<ProductPriceDto> caculateresultList = new ArrayList<ProductPriceDto>();
								// 其他运输方式价格
					   /**
					  * 添加合同客户价格时间版本
					  */
			if(preferentialInfo!=null && isDiscount) {
				//由于查询区域价格的时候可能重新设置了区域ID，所以需要重新设置区域ID
				 queryProductPriceDto.setOriginalOrgId(originalId);
				 queryProductPriceDto.setDestinationId(destinationId);
				 queryProductPriceDto.setCustomerCode(null);
				if(!versionFlag) {
						 Date versionDate = preferentialInfo.getExPriceVersionDate();
						 if(versionDate != null) {
							 queryProductPriceDto.setReceiveDate(versionDate);
						 }
					 }
			}
				    caculateresultList = PriceUtil.calculateExpressCostServices(weight,
										volume, resultList, receiveDate, productEntity,
										goodsTypeEntity, priceEntity, selfPickUpSubStract,volumeWeight);
				    if(caculateresultList!=null&&caculateresultList.size()!=0){
				    for(ProductPriceDto p:caculateresultList){
				    	if(PriceEntityConstants.PRICING_CODE_FRT.equals(p.getPriceEntityCode())){
				    		purefreight = p.getCaculateFee();
				    	}
				     }
				    }
		return purefreight;
		
	}
	
	/**
	 * 查询增值服务费，提供给快递系统
	 * @author 273279
	 */
	@Override
	public List<ValueAddDto> searchECSValueAddPriceList(
			QueryBillCacilateValueAddDto queryBillCacilateValueAddDto)
			throws BillCaculateServiceException {
		// 原始费用
		BigDecimal originnalCost = queryBillCacilateValueAddDto
				.getOriginnalCost();
		// 校验输入参数
		PriceUtil
				.checkExpressQueryBillCacilateValueAddDtoDate(queryBillCacilateValueAddDto);
		// 出发部门
		String originalOrgCode = queryBillCacilateValueAddDto
				.getOriginalOrgCode();
		// 到达部门
		String destinationOrgCode = queryBillCacilateValueAddDto
				.getDestinationOrgCode();
		// 营业部收货日期
		Date receiveDate = queryBillCacilateValueAddDto.getReceiveDate();
		// 产品code
		String productCode = queryBillCacilateValueAddDto.getProductCode();
		queryBillCacilateValueAddDto.setActive(FossConstants.ACTIVE);
		// 设置货物类型
		if (StringUtil.isEmpty(queryBillCacilateValueAddDto.getGoodsTypeCode())) {
			queryBillCacilateValueAddDto
					.setGoodsTypeCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
		} else {
			queryBillCacilateValueAddDto
					.setGoodsTypeCode(queryBillCacilateValueAddDto
							.getGoodsTypeCode());
		}
		// 处理客户端以元为单位的原始费用化成分
		if (originnalCost != null) {
			originnalCost = new BigDecimal(String.valueOf(originnalCost
					.doubleValue() * PricingConstants.YUTOFEN));
			queryBillCacilateValueAddDto.setOriginnalCost(originnalCost);
		}
		if (originnalCost == null) {
			originnalCost = new BigDecimal(PricingConstants.ZERO);
		}
		/* 始发部门code 定位价格区域信息 */
		String originalId = queryBillCacilateValueAddDto.getDeptRegionId();
		if (StringUtil.isEmpty(originalId)) {

			originalId = regionExpressService.findRegionOrgByDeptNo(
					originalOrgCode, receiveDate, null,
					PricingConstants.PRICING_REGION);
		}
		if (StringUtil.isEmpty(originalId)) {
			return null;
		}

		/* 到达部门 code 定位价格区域信息 */
		String destinationId = queryBillCacilateValueAddDto.getArrvRegionId();
		if (StringUtil.isEmpty(destinationId)
				&& StringUtil.isNotBlank(destinationOrgCode)) {
			destinationId = regionExpressService.findRegionOrgByDeptNo(
					destinationOrgCode, receiveDate, productCode,
					PricingConstants.PRICING_REGION);
		}
		//始发价格区域
		queryBillCacilateValueAddDto.setDeptRegionId(originalId);
		//到达价格区域
		queryBillCacilateValueAddDto.setArrvRegionId(destinationId);
		// 如果客户端没有长短途,则系统根据始发部门与到达部门CODE来定位
		String longOrShort = queryBillCacilateValueAddDto.getLongOrShort();
		if (StringUtil.isEmpty(longOrShort)
				&& StringUtil.isNotEmpty(destinationOrgCode)) {
			queryBillCacilateValueAddDto.setLongOrShort(PricingConstants.ALL);
		}
		/* 筛选计费规则下的计费明细 */
		Map<String, List<ResultProductPriceDto>> resultMap = expressPricingValueAddedService
				.siftValueAddRuleService(queryBillCacilateValueAddDto);
		//List<ResultProductPriceDto> resultProductPriceDtoList = resultMap
		//		.get("base");
		List<ResultProductPriceDto> resultOtherProductPriceDtoList = resultMap
				.get("other");
		
		if (CollectionUtils.isEmpty(resultOtherProductPriceDtoList)) {
			return null;
		}
		List<ValueAddDto> valueAddDtoList = new ArrayList<ValueAddDto>();
		
		for (ResultProductPriceDto resultDto : resultOtherProductPriceDtoList) {
			ValueAddDto valueAddDto = new ValueAddDto();
			// 固定费用
			valueAddDto.setFee(new BigDecimal(resultDto.getFee() != null ? resultDto.getFee() : 0));
			// 计算表达式
			valueAddDto.setCaculateExpression(resultDto.getExperssion());
			//valueAddDto.setCaculateType(caculateType);
			// 服务子类型
			valueAddDto.setSubType(resultDto.getSubType());
			// 费用类型code
			valueAddDto.setPriceEntityCode(resultDto.getPriceEntityCode());
			PriceEntity priceEntity = priceEntryService
					.getPriceEntryByCache(resultDto.getPriceEntityCode(),
							receiveDate);
			String priceEntryName = null;
			if (priceEntity != null && priceEntity.getName() != null) {
				priceEntryName = priceEntity.getName();
			}
			// 费用类型名称
			valueAddDto.setPriceEntityName(priceEntryName);
			// 是否可修改
			valueAddDto.setCanmodify(resultDto.getCanModify());
			// 是否可删除
			valueAddDto.setCandelete(resultDto.getCanDelete());
			// 最高费用
			if (null == resultDto.getMaxFee()) {
				resultDto.setMaxFee(NumberUtils.LONG_ZERO);
			}
			// 最低费用
			if (null == resultDto.getMinFee()) {
				resultDto.setMinFee(NumberUtils.LONG_ZERO);
			}
			valueAddDto.setMaxFee(new BigDecimal(new Double(String.valueOf(resultDto.getMaxFee()
					/ PricingConstants.YUTOFEN))));
			valueAddDto.setMinFee(new BigDecimal(new Double(String.valueOf(resultDto.getMinFee()
					/ PricingConstants.YUTOFEN))));
			if (resultDto.getFee() != null) {
				valueAddDto.setFee(new BigDecimal(new Double(String.valueOf(resultDto.getFee()
						/ PricingConstants.YUTOFEN))));
			}
			//产品code
			valueAddDto.setProductCode(resultDto.getProductCode());
			// 货物类型
			valueAddDto.setGoodsTypeCode(resultDto.getGoodsTypeCode());
			//ID
			valueAddDto.setId(resultDto.getPricingCriteriaDetailId());
			// 费率
			valueAddDto.setFeeRate(resultDto.getFeeRate());
			// 实际费率
			valueAddDto.setActualFeeRate(resultDto.getFeeRate());
			// 添加最低和最高费率
			valueAddDto.setMinFeeRate(resultDto.getMinFeeRate());
			valueAddDto.setMaxFeeRate(resultDto.getMaxFeeRate());
			//描述Description
			valueAddDto.setDescription(resultDto.getDescription());
			PriceEntity subPriceEntity = priceEntryService.getPriceEntryByCache(
					valueAddDto.getSubType(), receiveDate);
			if (subPriceEntity != null) {
				// 抓取计价条目编码与名称
				valueAddDto.setSubTypeName(subPriceEntity.getName());

				// 抓取计价条目归集类别编码与归集类别名称
				PriceEntity blongPriceEntity = priceEntryService
						.getPriceEntryByCache(
								subPriceEntity.getBlongPricingCode(), receiveDate);
				if (blongPriceEntity != null) {
					valueAddDto.setBelongToPriceEntityCode(blongPriceEntity
							.getCode());// 归集类别CODE
					valueAddDto.setBelongToPriceEntityName(blongPriceEntity
							.getName());// 归集类别名称
				}
			}

			
			/**
			* 时间：2014-02-27
			* 内容：MANA-257接货费优化
			* 作者：026123
			*/
			//若增值服务费的类型是接货，则直接返回最大值和最小值，费用值默认为最小值（将原值覆盖掉）
			if(PriceEntityConstants.PRICING_CODE_JH.equals(resultDto.getPricingEntryCode())){
				valueAddDto.setFee(null);
			}
			valueAddDtoList.add(valueAddDto);
		}
	
		return  valueAddDtoList;
	}
	

	//精准电商产品获取重泡比
	protected  BigDecimal validateWeightBubbleRateEcGoods(String customerCode,Date date,String code,BigDecimal volume,String originalOrgCode){
		BigDecimal volumeWeight = null;
		//先折扣信息
		PreferentialInfoDto entity = preferentialService.queryPreferentialInfo(customerCode, date, code);
		//查询部门信息，判断是否可用重泡比
		if (null != entity) {
			List<String> unifiedCodeList=new ArrayList<String>();
			String unCode=orgAdministrativeInfoService.queryUnifiedCodeByCode(originalOrgCode);
			if(unCode!=null){
				unifiedCodeList.add(unCode);
			}
			List<BargainAppOrgEntity> bargainAppOrgEntities = bargainAppOrgService
					.queryAppOrgByBargainCrmId(entity.getCusBargainId(),unifiedCodeList);
			boolean isDiscount = false;
			if (CollectionUtils.isNotEmpty(bargainAppOrgEntities)) {
				for (int i = 0; i < bargainAppOrgEntities.size(); i++) {
					BargainAppOrgEntity bargainAppOrgEntity = bargainAppOrgEntities
							.get(i);
					String unifiedCode = bargainAppOrgEntity.getUnifiedCode();
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(unifiedCode);
					if (orgAdministrativeInfoEntity == null) {
						continue;
					}
					String orgCode = orgAdministrativeInfoEntity.getCode();
					if (StringUtils.equals(originalOrgCode, orgCode)) {
						isDiscount = true;
					}
				}
			}

			//如果是关联营业部就按照新的逻辑重新计算体积重量
			if(isDiscount){
				if (entity.getLttWeightBubbleRate() != null) {
					//重泡比基数必须大于0，因为0不能做除数
					if(entity.getLttWeightBubbleRate().compareTo(BigDecimal.ZERO)==1) {
						//foss 343617 赵一清 20160918 修复两个BigDecimal计算，除不尽问题，由于页面输入只能精确到小数点1位，故此处逻辑相同。
						volumeWeight = (volume.multiply(new BigDecimal(NUMBER_1000000))).divide(entity.getLttWeightBubbleRate(),1,BigDecimal.ROUND_HALF_UP);
					}
				}
			}
		}

		return volumeWeight;
	}

	//抽取方法获取折扣信息和营销活动
	protected List<ProductPriceDto> getProductPriceDtos(QueryBillCacilateDto queryBillCacilateDto, Date discountReceiveDate, String productCode, String originalOrgCode, String destinationOrgCode, BigDecimal weight, BigDecimal volume, String originalId, String destinationId, List<ProductPriceDto> caculateresultList) {
		List<ProductPriceDto> list ;
		//新的折扣计算规则
		list = doFRTDiscount(originalOrgCode, destinationOrgCode, discountReceiveDate, weight,
					volume, originalId, destinationId, caculateresultList, queryBillCacilateDto);
		//处理运费CRM营销活动处理
		list=crmActiveDiscountProcessFRT(queryBillCacilateDto,list);
		return list;
	}
}