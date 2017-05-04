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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/guice/BaseInfoModule.java
 * 
 * FILE NAME        	: BaseInfoModule.java
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
package com.deppon.foss.module.pickup.common.client.guice;

import com.deppon.foss.framework.client.component.dataaccess.GuiceModule;
import com.deppon.foss.framework.client.widget.service.IUploadITService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillInspectionRemindService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkActivitiesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkOptionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISatellitePartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.BillInspectionRemindService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.CustomerService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.MarkActivitiesService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.MarkOptionsService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.SaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.SatellitePartSalesDeptService;
import com.deppon.foss.module.pickup.common.client.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.pickup.common.client.dao.IBankDao;
import com.deppon.foss.module.pickup.common.client.dao.IBusinessPartnerDao;
import com.deppon.foss.module.pickup.common.client.dao.IDataConsistencyCheckDao;
import com.deppon.foss.module.pickup.common.client.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.pickup.common.client.dao.IDepartureStandardDao;
import com.deppon.foss.module.pickup.common.client.dao.IDiscountOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.IDiscountPriorityDao;
import com.deppon.foss.module.pickup.common.client.dao.IDiscountProgramDao;
import com.deppon.foss.module.pickup.common.client.dao.IEffectivePlanDao;
import com.deppon.foss.module.pickup.common.client.dao.IEffectivePlanDetailDao;
import com.deppon.foss.module.pickup.common.client.dao.IEffectiveRegionDao;
import com.deppon.foss.module.pickup.common.client.dao.IEffectiveRegionOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.IExpressCityDao;
import com.deppon.foss.module.pickup.common.client.dao.IFinOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.IFreightRouteDao;
import com.deppon.foss.module.pickup.common.client.dao.IFreightRouteLineDao;
import com.deppon.foss.module.pickup.common.client.dao.IGoodsAreaDao;
import com.deppon.foss.module.pickup.common.client.dao.IGoodsTypeDao;
import com.deppon.foss.module.pickup.common.client.dao.ILimitedWarrantyItemsDao;
import com.deppon.foss.module.pickup.common.client.dao.ILineDao;
import com.deppon.foss.module.pickup.common.client.dao.ILineItemDao;
import com.deppon.foss.module.pickup.common.client.dao.IMakEventExOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.IMakEventInOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.IMarketingEventChannelDao;
import com.deppon.foss.module.pickup.common.client.dao.IMarketingEventDao;
import com.deppon.foss.module.pickup.common.client.dao.IMinFeePlanDao;
import com.deppon.foss.module.pickup.common.client.dao.INetGroupDao;
import com.deppon.foss.module.pickup.common.client.dao.IOrgInfoDao;
import com.deppon.foss.module.pickup.common.client.dao.IOuterBranchDao;
import com.deppon.foss.module.pickup.common.client.dao.IOuterPriceDao;
import com.deppon.foss.module.pickup.common.client.dao.IOutfieldDao;
import com.deppon.foss.module.pickup.common.client.dao.IPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.common.client.dao.IPriceDao;
import com.deppon.foss.module.pickup.common.client.dao.IPricePlanDao;
import com.deppon.foss.module.pickup.common.client.dao.IPriceRegioExpressOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.IPriceRegioOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.IPriceRegionAirDao;
import com.deppon.foss.module.pickup.common.client.dao.IPriceRegionDao;
import com.deppon.foss.module.pickup.common.client.dao.IPriceRegionExpressDao;
import com.deppon.foss.module.pickup.common.client.dao.IPriceRegionOrgAirDao;
import com.deppon.foss.module.pickup.common.client.dao.IPriceRuleDao;
import com.deppon.foss.module.pickup.common.client.dao.IPriceValuationDao;
import com.deppon.foss.module.pickup.common.client.dao.IProductDao;
import com.deppon.foss.module.pickup.common.client.dao.IProductItemDao;
import com.deppon.foss.module.pickup.common.client.dao.IProhibitGoodsDao;
import com.deppon.foss.module.pickup.common.client.dao.IRegionArriveDao;
import com.deppon.foss.module.pickup.common.client.dao.IRegionValueAddDao;
import com.deppon.foss.module.pickup.common.client.dao.IResourcesDao;
import com.deppon.foss.module.pickup.common.client.dao.IRoleDao;
import com.deppon.foss.module.pickup.common.client.dao.IRoleResourcesDao;
import com.deppon.foss.module.pickup.common.client.dao.ISalesDepartmentDao;
import com.deppon.foss.module.pickup.common.client.dao.ISalesDescExpandsDao;
import com.deppon.foss.module.pickup.common.client.dao.ISalesProductDao;
import com.deppon.foss.module.pickup.common.client.dao.ISysConfigDao;
import com.deppon.foss.module.pickup.common.client.dao.IUserDao;
import com.deppon.foss.module.pickup.common.client.dao.IUserOrgRoleDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.AdministrativeRegionsDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.BankDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.BusinessPartnerDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.DataConsistencyCheckDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.DataDictionaryValueDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.DepartureStandardDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.DiscountOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.DiscountPriorityDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.DiscountProgramDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.EffectivePlanDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.EffectivePlanDetailDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.EffectiveRegionDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.EffectiveRegionOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.ExpressCityDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.FinOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.FreightRouteDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.FreightRouteLineDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.GoodsAreaDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.GoodsTypeDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.LimitedWarrantyItemsDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.LineDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.LineItemDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.MakEventExOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.MakEventInOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.MarketingEventChannelDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.MarketingEventDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.MinFeePlanDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.NetGroupDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.OrgInfoDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.OuterBranchDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.OuterPriceDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.OutfieldDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.PriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.PriceDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.PricePlanDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.PriceRegioExpressOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.PriceRegioOrgDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.PriceRegionAirDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.PriceRegionDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.PriceRegionExpressDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.PriceRegionOrgAirDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.PriceRuleDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.PriceValuationDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.ProductDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.ProductItemDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.ProhibitGoodsDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.RegionArriveDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.RegionValueAddDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.ResourcesDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.RoleDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.RoleResourcesDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.SalesDepartmentDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.SalesDescExpandsDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.SalesProductDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.SysConfigDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.UserDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.UserOrgRoleDao;
import com.deppon.foss.module.pickup.common.client.service.IAdministrativeRegionsService;
import com.deppon.foss.module.pickup.common.client.service.IBankService;
import com.deppon.foss.module.pickup.common.client.service.IBusinessPartnerService;
import com.deppon.foss.module.pickup.common.client.service.IConfigService;
import com.deppon.foss.module.pickup.common.client.service.IDataConsistencyCheckService;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.IDepartureStandardService;
import com.deppon.foss.module.pickup.common.client.service.IDiscountOrgService;
import com.deppon.foss.module.pickup.common.client.service.IDiscountProgramService;
import com.deppon.foss.module.pickup.common.client.service.IEffectivePlanDetailService;
import com.deppon.foss.module.pickup.common.client.service.IEffectivePlanService;
import com.deppon.foss.module.pickup.common.client.service.IEffectiveRegionOrgService;
import com.deppon.foss.module.pickup.common.client.service.IEffectiveRegionService;
import com.deppon.foss.module.pickup.common.client.service.IExpressCityService;
import com.deppon.foss.module.pickup.common.client.service.IFinOrgService;
import com.deppon.foss.module.pickup.common.client.service.IFreightRouteLineService;
import com.deppon.foss.module.pickup.common.client.service.IFreightRouteService;
import com.deppon.foss.module.pickup.common.client.service.IGoodsAreaService;
import com.deppon.foss.module.pickup.common.client.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.common.client.service.ILimitedWarrantyItemsService;
import com.deppon.foss.module.pickup.common.client.service.ILineItemService;
import com.deppon.foss.module.pickup.common.client.service.ILineService;
import com.deppon.foss.module.pickup.common.client.service.IMakEventExOrgService;
import com.deppon.foss.module.pickup.common.client.service.IMakEventInOrgService;
import com.deppon.foss.module.pickup.common.client.service.IMarketingEventChannelService;
import com.deppon.foss.module.pickup.common.client.service.IMarketingEventService;
import com.deppon.foss.module.pickup.common.client.service.INetGroupService;
import com.deppon.foss.module.pickup.common.client.service.IOrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.IOuterBranchService;
import com.deppon.foss.module.pickup.common.client.service.IOutfieldService;
import com.deppon.foss.module.pickup.common.client.service.IPriceCriteriaDetailService;
import com.deppon.foss.module.pickup.common.client.service.IPricePlanService;
import com.deppon.foss.module.pickup.common.client.service.IPriceRegioExpressOrgService;
import com.deppon.foss.module.pickup.common.client.service.IPriceRegioOrgService;
import com.deppon.foss.module.pickup.common.client.service.IPriceRegionAirService;
import com.deppon.foss.module.pickup.common.client.service.IPriceRegionExpressService;
import com.deppon.foss.module.pickup.common.client.service.IPriceRegionOrgAirService;
import com.deppon.foss.module.pickup.common.client.service.IPriceRegionService;
import com.deppon.foss.module.pickup.common.client.service.IPriceRuleService;
import com.deppon.foss.module.pickup.common.client.service.IPriceService;
import com.deppon.foss.module.pickup.common.client.service.IPriceValuationService;
import com.deppon.foss.module.pickup.common.client.service.IProductItemService;
import com.deppon.foss.module.pickup.common.client.service.IProductService;
import com.deppon.foss.module.pickup.common.client.service.IProhibitedArticlesService;
import com.deppon.foss.module.pickup.common.client.service.IResourceService;
import com.deppon.foss.module.pickup.common.client.service.IRoleResourceService;
import com.deppon.foss.module.pickup.common.client.service.IRoleService;
import com.deppon.foss.module.pickup.common.client.service.ISalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.service.ISalesDescExpandsService;
import com.deppon.foss.module.pickup.common.client.service.ISalesProductService;
import com.deppon.foss.module.pickup.common.client.service.ISysConfigService;
import com.deppon.foss.module.pickup.common.client.service.IUserOrgRoleService;
import com.deppon.foss.module.pickup.common.client.service.IUserService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillInfoService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillStockService;
import com.deppon.foss.module.pickup.common.client.service.impl.AdministrativeRegionsService;
import com.deppon.foss.module.pickup.common.client.service.impl.BankService;
import com.deppon.foss.module.pickup.common.client.service.impl.BusinessPartnerService;
import com.deppon.foss.module.pickup.common.client.service.impl.ConfigService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataConsistencyCheckService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.impl.DepartureStandardService;
import com.deppon.foss.module.pickup.common.client.service.impl.DiscountOrgService;
import com.deppon.foss.module.pickup.common.client.service.impl.DiscountProgramService;
import com.deppon.foss.module.pickup.common.client.service.impl.EffectivePlanDetailService;
import com.deppon.foss.module.pickup.common.client.service.impl.EffectivePlanService;
import com.deppon.foss.module.pickup.common.client.service.impl.EffectiveRegionOrgService;
import com.deppon.foss.module.pickup.common.client.service.impl.EffectiveRegionService;
import com.deppon.foss.module.pickup.common.client.service.impl.ExpressCityService;
import com.deppon.foss.module.pickup.common.client.service.impl.FinOrgService;
import com.deppon.foss.module.pickup.common.client.service.impl.FreightRouteLineService;
import com.deppon.foss.module.pickup.common.client.service.impl.FreightRouteService;
import com.deppon.foss.module.pickup.common.client.service.impl.GoodsAreaService;
import com.deppon.foss.module.pickup.common.client.service.impl.GoodsTypeService;
import com.deppon.foss.module.pickup.common.client.service.impl.LimitedWarrantyItemsService;
import com.deppon.foss.module.pickup.common.client.service.impl.LineItemService;
import com.deppon.foss.module.pickup.common.client.service.impl.LineService;
import com.deppon.foss.module.pickup.common.client.service.impl.MakEventExOrgService;
import com.deppon.foss.module.pickup.common.client.service.impl.MakEventInOrgService;
import com.deppon.foss.module.pickup.common.client.service.impl.MarketingEventChannelService;
import com.deppon.foss.module.pickup.common.client.service.impl.MarketingEventService;
import com.deppon.foss.module.pickup.common.client.service.impl.NetGroupService;
import com.deppon.foss.module.pickup.common.client.service.impl.OrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.impl.OuterBranchService;
import com.deppon.foss.module.pickup.common.client.service.impl.OutfieldService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceCriteriaDetailService;
import com.deppon.foss.module.pickup.common.client.service.impl.PricePlanService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceRegioExpressOrgService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceRegioOrgService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceRegionAirService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceRegionExpressService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceRegionOrgAirService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceRegionService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceRuleService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceValuationService;
import com.deppon.foss.module.pickup.common.client.service.impl.ProductItemService;
import com.deppon.foss.module.pickup.common.client.service.impl.ProductService;
import com.deppon.foss.module.pickup.common.client.service.impl.ProhibitedArticlesService;
import com.deppon.foss.module.pickup.common.client.service.impl.ResourceService;
import com.deppon.foss.module.pickup.common.client.service.impl.RoleResourceService;
import com.deppon.foss.module.pickup.common.client.service.impl.RoleService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDescExpandsService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesProductService;
import com.deppon.foss.module.pickup.common.client.service.impl.SysConfigService;
import com.deppon.foss.module.pickup.common.client.service.impl.UploadITServiceImpl;
import com.deppon.foss.module.pickup.common.client.service.impl.UserOrgRoleService;
import com.deppon.foss.module.pickup.common.client.service.impl.UserService;
import com.deppon.foss.module.pickup.common.client.service.impl.WaybillInfoService;
import com.deppon.foss.module.pickup.common.client.service.impl.WaybillStockService;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IDistrictRegionDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDistrictRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IInempDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterEffectivePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterPriceService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionBigGoodsArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionBigGoodsService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionEcGoodsArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionEcGoodsService;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.DistrictRegionDao;
import com.deppon.foss.module.pickup.pricing.server.service.impl.DistrictRegionService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.GuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.InempDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.OuterEffectivePlanService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.OuterPriceService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PublishPriceService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.RegionBigGoodsArriveService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.RegionBigGoodsService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.RegionEcGoodsArriveService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.RegionEcGoodsService;
import com.google.inject.Binder;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IfibelPaperPackingUnitPriceDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.FibelPaperPackingUnitPriceDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillManagerService;

/**
 * 基本信息模块
 * 该模块在启动的时候加载 用于注入guice
 * 等必要的注入信息
 * @author 105089-foss-yangtong
 * @date 2012-10-22 下午4:06:05
 */
public class BaseInfoModule extends GuiceModule {

	
	/**
	 * 功能：configure
	 * 基本信息模块
	 * 该模块在启动的时候加载 用于注入guice
	 * 等必要的注入信息
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public void configure(Binder binder) {
		
		// 组织信息
		binder.bind(IPriceRegionOrgAirDao.class).to(PriceRegionOrgAirDao.class).asEagerSingleton();
		// 组织信息
		binder.bind(IPriceRegionAirDao.class).to(PriceRegionAirDao.class).asEagerSingleton();
		// 组织信息
		binder.bind(IPriceRegionAirService.class).to(PriceRegionAirService.class).asEagerSingleton();
		// 组织信息
		binder.bind(IPriceRegionOrgAirService.class).to(PriceRegionOrgAirService.class).asEagerSingleton();
						
		
		// 组织信息
		binder.bind(IOrgInfoDao.class).to(OrgInfoDao.class).asEagerSingleton();
		// 组织信息service
		binder.bind(IOrgInfoService.class).to(OrgInfoService.class)
				.asEagerSingleton();
		// 线路信息
		binder.bind(ILineDao.class).to(LineDao.class).asEagerSingleton();
		// 线路信息service
		binder.bind(ILineService.class).to(LineService.class).asEagerSingleton();
		// 走货路径线路
		binder.bind(IFreightRouteLineDao.class).to(FreightRouteLineDao.class).asEagerSingleton();
		// 走货路径线路service
		binder.bind(IFreightRouteLineService.class).to(FreightRouteLineService.class).asEagerSingleton();

		// 库区
		binder.bind(IGoodsAreaDao.class).to(GoodsAreaDao.class).asEagerSingleton();
		// 库区service
		binder.bind(IGoodsAreaService.class).to(GoodsAreaService.class).asEagerSingleton();

		// 银行
		binder.bind(IBankDao.class).to(BankDao.class).asEagerSingleton();
		// 银行service
		binder.bind(IBankService.class).to(BankService.class).asEagerSingleton();

		// 行政区域
		binder.bind(IAdministrativeRegionsDao.class).to(AdministrativeRegionsDao.class).asEagerSingleton();
		// 行政区域service
		binder.bind(IAdministrativeRegionsService.class).to(AdministrativeRegionsService.class).asEagerSingleton();

		// 走货路径
		binder.bind(IFreightRouteDao.class).to(FreightRouteDao.class).asEagerSingleton();
		// 走货路径service
		binder.bind(IFreightRouteService.class).to(FreightRouteService.class).asEagerSingleton();

		// 限保物品
		binder.bind(ILimitedWarrantyItemsDao.class).to(LimitedWarrantyItemsDao.class).asEagerSingleton();
		// 限保物品service
		binder.bind(ILimitedWarrantyItemsService.class).to(LimitedWarrantyItemsService.class).asEagerSingleton();

		// 网点组
		binder.bind(INetGroupDao.class).to(NetGroupDao.class).asEagerSingleton();
		// 网点组service
		binder.bind(INetGroupService.class).to(NetGroupService.class).asEagerSingleton();

		// 外部网点
		binder.bind(IOuterBranchDao.class).to(OuterBranchDao.class).asEagerSingleton();
		// 外部网点service
		binder.bind(IOuterBranchService.class).to(OuterBranchService.class).asEagerSingleton();

		// 禁运物品
		binder.bind(IProhibitGoodsDao.class).to(ProhibitGoodsDao.class).asEagerSingleton();
		// 禁运物品service
		binder.bind(IProhibitedArticlesService.class).to(ProhibitedArticlesService.class).asEagerSingleton();

		// 权限
		binder.bind(IResourcesDao.class).to(ResourcesDao.class).asEagerSingleton();
		// 权限service
		binder.bind(IResourceService.class).to(ResourceService.class).asEagerSingleton();

		// 角色
		binder.bind(IRoleDao.class).to(RoleDao.class).asEagerSingleton();
		// 角色service
		binder.bind(IRoleService.class).to(RoleService.class).asEagerSingleton();

		// 角色权限
		binder.bind(IRoleResourcesDao.class).to(RoleResourcesDao.class).asEagerSingleton();
		// 角色权限service
		binder.bind(IRoleResourceService.class).to(RoleResourceService.class).asEagerSingleton();

		// 营业部
		binder.bind(ISalesDepartmentDao.class).to(SalesDepartmentDao.class).asEagerSingleton();
		// 营业部service
		binder.bind(ISalesDepartmentService.class).to(SalesDepartmentService.class).asEagerSingleton();

		// 外场
		binder.bind(IOutfieldDao.class).to(OutfieldDao.class).asEagerSingleton();
		// 外场service
		binder.bind(IOutfieldService.class).to(OutfieldService.class).asEagerSingleton();

		// 用户
		binder.bind(IUserDao.class).to(UserDao.class).asEagerSingleton();
		// 用户service
		binder.bind(IUserService.class).to(UserService.class).asEagerSingleton();

		// 用户部门角色
		binder.bind(IUserOrgRoleDao.class).to(UserOrgRoleDao.class).asEagerSingleton();
		// 用户部门角色service
		binder.bind(IUserOrgRoleService.class).to(UserOrgRoleService.class).asEagerSingleton();

		/********分割*********/
		// 打折处理程序
		binder.bind(IDiscountProgramDao.class).to(DiscountProgramDao.class).asEagerSingleton();
		// 打折处理程序service
		binder.bind(IDiscountProgramService.class).to(DiscountProgramService.class).asEagerSingleton();

		// 时效方案主信息
		binder.bind(IEffectivePlanDao.class).to(EffectivePlanDao.class).asEagerSingleton();
		// 时效方案主信息service
		binder.bind(IEffectivePlanService.class).to(EffectivePlanService.class).asEagerSingleton();

		// 时效方案详细信息
		binder.bind(IEffectivePlanDetailDao.class).to(EffectivePlanDetailDao.class).asEagerSingleton();
		// 时效方案详细信息service
		binder.bind(IEffectivePlanDetailService.class).to(EffectivePlanDetailService.class).asEagerSingleton();

		// 货物类型
		binder.bind(IGoodsTypeDao.class).to(GoodsTypeDao.class).asEagerSingleton();
		// 货物类型service
		binder.bind(IGoodsTypeService.class).to(GoodsTypeService.class).asEagerSingleton();

		// 市场活动不包含组织
		binder.bind(IMakEventExOrgDao.class).to(MakEventExOrgDao.class).asEagerSingleton();
		// 市场活动不包含组织service
		binder.bind(IMakEventExOrgService.class).to(MakEventExOrgService.class).asEagerSingleton();

		// 市场活动包含组织
		binder.bind(IMakEventInOrgDao.class).to(MakEventInOrgDao.class).asEagerSingleton();
		// 市场活动包含组织service
		binder.bind(IMakEventInOrgService.class).to(MakEventInOrgService.class).asEagerSingleton();

		// 市场活动
		binder.bind(IMarketingEventDao.class).to(MarketingEventDao.class).asEagerSingleton();
		// 市场活动service
		binder.bind(IMarketingEventService.class).to(MarketingEventService.class).asEagerSingleton();

		// 价格方案主信息
		binder.bind(IPricePlanDao.class).to(PricePlanDao.class).asEagerSingleton();
		// 价格方案主信息service
		binder.bind(IPricePlanService.class).to(PricePlanService.class).asEagerSingleton();

		// 价格区域信息
		binder.bind(IPriceRegionDao.class).to(PriceRegionDao.class).asEagerSingleton();
		// 价格区域信息service
		binder.bind(IPriceRegionService.class).to(PriceRegionService.class).asEagerSingleton();

		// 价格区域与部门对应关系
		binder.bind(IPriceRegioOrgDao.class).to(PriceRegioOrgDao.class).asEagerSingleton();
		// 价格区域与部门对应关系service
		binder.bind(IPriceRegioOrgService.class).to(PriceRegioOrgService.class).asEagerSingleton();

		// 价格计算表达式
		binder.bind(IPriceRuleDao.class).to(PriceRuleDao.class).asEagerSingleton();
		// 价格计算表达式service
		binder.bind(IPriceRuleService.class).to(PriceRuleService.class).asEagerSingleton();

		// 计价方式明细
		binder.bind(IPriceCriteriaDetailDao.class).to(PriceCriteriaDetailDao.class).asEagerSingleton();
		// 计价方式明细service
		binder.bind(IPriceCriteriaDetailService.class).to(PriceCriteriaDetailService.class).asEagerSingleton();

		// 计价条目
		binder.bind(IPriceDao.class).to(PriceDao.class).asEagerSingleton();
		// 计价条目service
		binder.bind(IPriceService.class).to(PriceService.class).asEagerSingleton();

		// 计费规则
		binder.bind(IPriceValuationDao.class).to(PriceValuationDao.class).asEagerSingleton();
		// 计费规则service
		binder.bind(IPriceValuationService.class).to(PriceValuationService.class).asEagerSingleton();

		// 产品
		binder.bind(IProductDao.class).to(ProductDao.class).asEagerSingleton();
		// 产品service
		binder.bind(IProductService.class).to(ProductService.class).asEagerSingleton();

		// 产品条目
		binder.bind(IProductItemDao.class).to(ProductItemDao.class).asEagerSingleton();
		// 产品条目service
		binder.bind(IProductItemService.class).to(ProductItemService.class).asEagerSingleton();

		// 数据字典
		binder.bind(IDataDictionaryValueDao.class).to(DataDictionaryValueDao.class).asEagerSingleton();
		// 数据字典service
		binder.bind(IDataDictionaryValueService.class).to(DataDictionaryValueService.class).asEagerSingleton();

		// 销售产品
		binder.bind(ISalesProductDao.class).to(SalesProductDao.class).asEagerSingleton();
		// 销售产品service
		binder.bind(ISalesProductService.class).to(SalesProductService.class)
				.asEagerSingleton();

		// 配置信息
		binder.bind(ISysConfigDao.class).to(SysConfigDao.class).asEagerSingleton();
		// 配置信息Service
		binder.bind(ISysConfigService.class).to(SysConfigService.class).asEagerSingleton();
		// 配置信息service
		binder.bind(IConfigService.class).to(ConfigService.class).asEagerSingleton();

		// 时效区域信息
		binder.bind(IEffectiveRegionDao.class).to(EffectiveRegionDao.class).asEagerSingleton();
		// 时效区域信息service
		binder.bind(IEffectiveRegionService.class).to(EffectiveRegionService.class).asEagerSingleton();

		// 时效区域与部门对应关系
		binder.bind(IEffectiveRegionOrgDao.class).to(EffectiveRegionOrgDao.class).asEagerSingleton();
		// 时效区域与部门对应关系service
		binder.bind(IEffectiveRegionOrgService.class).to(EffectiveRegionOrgService.class).asEagerSingleton();
		
		// 折扣适用起始目的组织网点
		binder.bind(IDiscountOrgDao.class).to(DiscountOrgDao.class).asEagerSingleton();
		// 折扣适用起始目的组织网点service
		binder.bind(IDiscountOrgService.class).to(DiscountOrgService.class).asEagerSingleton();
		
		// 市场活动适用渠道
		binder.bind(IMarketingEventChannelDao.class).to(MarketingEventChannelDao.class).asEagerSingleton();
		// 市场活动适用渠道service
		binder.bind(IMarketingEventChannelService.class).to(MarketingEventChannelService.class).asEagerSingleton();
		
		// 合作伙伴
		binder.bind(IBusinessPartnerDao.class).to(BusinessPartnerDao.class).asEagerSingleton();
		// 合作伙伴service
		binder.bind(IBusinessPartnerService.class).to(BusinessPartnerService.class).asEagerSingleton();
		
		// 发车线路实效
		binder.bind(ILineItemDao.class).to(LineItemDao.class).asEagerSingleton();
		// 发车线路实效service
		binder.bind(ILineItemService.class).to(LineItemService.class).asEagerSingleton();
		
		// 财务组织数据
		binder.bind(IFinOrgDao.class).to(FinOrgDao.class).asEagerSingleton();
		// 财务组织数据service
		binder.bind(IFinOrgService.class).to(FinOrgService.class).asEagerSingleton();
		
		//出发信息
		binder.bind(IDepartureStandardDao.class).to(DepartureStandardDao.class).asEagerSingleton();
		
		//出发信息
		binder.bind(IDepartureStandardService.class).to(DepartureStandardService.class).asEagerSingleton();

		// 绑定库存服务
		binder.bind(IWaybillStockService.class).to(WaybillStockService.class);
		//区域服务
		binder.bind(IDistrictRegionService.class).to(DistrictRegionService.class);
		
		//大票区域服务
		binder.bind(IRegionBigGoodsService.class).to(RegionBigGoodsService.class);
		       
		binder.bind(IRegionBigGoodsArriveService.class).to(RegionBigGoodsArriveService.class);
		
		//大票区域服务
		binder.bind(IRegionEcGoodsService.class).to(RegionEcGoodsService.class);
		       
		binder.bind(IRegionEcGoodsArriveService.class).to(RegionEcGoodsArriveService.class);
		            
//		binder.bind(IPopPriceDetailSectionDao.class).to(PopPriceDetailSectionDao.class);
		//价格
		binder.bind(IPublishPriceService.class).to(PublishPriceService.class);

		//最新价格
		binder.bind(IGuiBillCaculateService.class).to(GuiBillCaculateService.class);
		
		//行政区域与时效、汽运、空运价格区域关系表DAO
		binder.bind(IDistrictRegionDao.class).to(DistrictRegionDao.class);
		
		//同步检查Service
		binder.bind(IDataConsistencyCheckService.class).to(DataConsistencyCheckService.class).asEagerSingleton();
		
		//同步检查Dao
		binder.bind(IDataConsistencyCheckDao.class).to(DataConsistencyCheckDao.class).asEagerSingleton();
		

		
		binder.bind(com.deppon.foss.module.pickup.common.client.service.ISalesBillingGroupService.class)
			.to(com.deppon.foss.module.pickup.common.client.service.impl.SalesBillingGroupService.class);
		binder.bind(com.deppon.foss.module.pickup.common.client.dao.ISalesBillingGroupDao.class)
			.to(com.deppon.foss.module.pickup.common.client.dao.impl.SalesBillingGroupDao.class);
		
		binder.bind(com.deppon.foss.module.pickup.common.client.service.IAsteriskSalesDeptService.class)
			.to(com.deppon.foss.module.pickup.common.client.service.impl.AsteriskSalesDeptService.class);
		binder.bind(com.deppon.foss.module.pickup.common.client.dao.IAsteriskSalesDeptDao.class)
			.to(com.deppon.foss.module.pickup.common.client.dao.impl.AsteriskSalesDeptDao.class);
		
		
		binder.bind(com.deppon.foss.module.pickup.common.client.service.IBillingGroupTransFerService.class)
			.to(com.deppon.foss.module.pickup.common.client.service.impl.BillingGroupTransFerService.class);
		binder.bind(com.deppon.foss.module.pickup.common.client.dao.IBillingGroupTransFerDao.class)
			.to(com.deppon.foss.module.pickup.common.client.dao.impl.BillingGroupTransFerDao.class);

		binder.bind(com.deppon.foss.module.pickup.common.client.dao.INetGroupMixDao.class)
			.to(com.deppon.foss.module.pickup.common.client.dao.impl.NetGroupMixDao.class);
		binder.bind(com.deppon.foss.module.pickup.common.client.service.INetGroupMixService.class)
			.to(com.deppon.foss.module.pickup.common.client.service.impl.NetGroupMixService.class);
	
		binder.bind(ISalesDescExpandsDao.class).to(SalesDescExpandsDao.class).asEagerSingleton();
		binder.bind(ISalesDescExpandsService.class).to(SalesDescExpandsService.class).asEagerSingleton();
		


		binder.bind(IExpressCityDao.class).to(ExpressCityDao.class).asEagerSingleton();
		binder.bind(IExpressCityService.class).to(ExpressCityService.class).asEagerSingleton();
	
		binder.bind(IPriceRegionExpressDao.class).to(PriceRegionExpressDao.class).asEagerSingleton();
		binder.bind(IPriceRegionExpressService.class).to(PriceRegionExpressService.class).asEagerSingleton();
		
		
		binder.bind(IPriceRegioExpressOrgDao.class).to(PriceRegioExpressOrgDao.class).asEagerSingleton();
		binder.bind(IPriceRegioExpressOrgService.class).to(PriceRegioExpressOrgService.class).asEagerSingleton();
	
		//zxy 20131010 BUG-55198 start 新增：离线下载数据服务注入
		//增值区域
		binder.bind(IRegionValueAddDao.class).to(RegionValueAddDao.class).asEagerSingleton();
		//到达区域
		binder.bind(IRegionArriveDao.class).to(RegionArriveDao.class).asEagerSingleton();
		//偏线价格
		binder.bind(IOuterPriceDao.class).to(OuterPriceDao.class).asEagerSingleton();
		//最低一票
		binder.bind(IMinFeePlanDao.class).to(MinFeePlanDao.class).asEagerSingleton();
		//折扣优先级
		binder.bind(IDiscountPriorityDao.class).to(DiscountPriorityDao.class).asEagerSingleton();
		//zxy 20131010 BUG-55198 end 新增：离线下载数据服务注入
		
		//IT服务台一键上报
		binder.bind(IUploadITService.class).to(UploadITServiceImpl.class).asEagerSingleton();
		
		//偏线服务
		binder.bind(IOuterPriceService.class).to(OuterPriceService.class).asEagerSingleton();
		
		//营业部服务
		binder.bind(ISaleDepartmentService.class).to(SaleDepartmentService.class).asEagerSingleton();
		
		//营业部服务
		binder.bind(ISatellitePartSalesDeptService.class).to(SatellitePartSalesDeptService.class).asEagerSingleton();
		
		//偏线时效服务
		binder.bind(IOuterEffectivePlanService.class).to(OuterEffectivePlanService.class).asEagerSingleton();
		
		//市场活动服务
		binder.bind(IMarkActivitiesService.class).to(MarkActivitiesService.class).asEagerSingleton();
		
		//市场推广活动多选服务
		binder.bind(IMarkOptionsService.class).to(MarkOptionsService.class).asEagerSingleton();
		
		//客户信息服务
		binder.bind(ICustomerService.class).to(CustomerService.class).asEagerSingleton();
		
		//
		binder.bind(IBillInspectionRemindService.class).to(BillInspectionRemindService.class).asEagerSingleton();
		
		/**
		 * 将IfibelPaperPackingUnitPriceDao和FibelPaperPackingUnitPriceDao进行绑定
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-11-20上午8:12
		 */
		binder.bind(IfibelPaperPackingUnitPriceDao.class).to(FibelPaperPackingUnitPriceDao.class).asEagerSingleton();
		/**
		 * 内部发货
		 */
		binder.bind(IWaybillManagerService.class).to(WaybillManagerService.class).asEagerSingleton();
		binder.bind(IInempDiscountPlanService.class).to(InempDiscountPlanService.class).asEagerSingleton();
		binder.bind(IWaybillInfoService.class).to(WaybillInfoService.class).asEagerSingleton();
	}

}