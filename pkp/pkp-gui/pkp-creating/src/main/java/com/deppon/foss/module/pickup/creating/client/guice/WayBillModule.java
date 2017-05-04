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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/guice/WayBillModule.java
 * 
 * FILE NAME        	: WayBillModule.java
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
package com.deppon.foss.module.pickup.creating.client.guice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.component.dataaccess.GuiceModule;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAsteriskSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillingGroupTransFerDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IDepartureStandardDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDepartureStandardDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressLineDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFinancialOrganizationsDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteLineDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IGradientDiscountDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILdpAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineItemDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupMixDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOutfieldDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPricingOrgDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesBillingGroupDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDescExpandDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.complex.IOrgAdministrativeInfoComplexDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAsteriskSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillingGroupTransFerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFinancialOrganizationsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGradientDiscountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineItemService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INetGroupService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesBillingGroupService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDescExpandService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISpecialdiscountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptAuthorityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptDataService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.AdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.AirAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.AsteriskSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.BillingGroupTransFerDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.DepartureStandardDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.EmployeeDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.ExpressDepartureStandardDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.ExpressLineDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.FinancialOrganizationsDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteLineDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.GoodsAreaDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.GradientDiscountDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LdpAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LineDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LineItemDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.MotorcadeDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.NetGroupMixDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.OrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.OutfieldDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.PricingOrgDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.SaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.SalesBillingGroupDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.SalesDescExpandDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.complex.OrgAdministrativeInfoComplexDao;
import com.deppon.foss.module.base.baseinfo.server.service.impl.AdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.AsteriskSalesDeptService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.BillingGroupTransFerService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.CusBargainService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.DepartureStandardService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.EmployeeService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.ExpressDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.ExpressLineService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.FinancialOrganizationsService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.FreightRouteLineService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.FreightRouteService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.GoodsAreaService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.GradientDiscountService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.LdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.LineItemService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.LineService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.MotorcadeService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.NetGroupService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.OrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.OutfieldService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.PreferentialService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.SaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.SalesBillingGroupService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.SalesDescExpandService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.SpecialdiscountService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.UserDeptAuthorityService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.UserDeptDataService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.VehicleAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.OrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.esb.SendOrgAdministrativeInfoService;
import com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.server.dao.impl.ConfigurationParamsDao;
import com.deppon.foss.module.base.dict.server.service.impl.ConfigurationParamsService;
import com.deppon.foss.module.pickup.common.client.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.pickup.common.client.dao.IDistrictDao;
import com.deppon.foss.module.pickup.common.client.dao.IFreightRouteDao;
import com.deppon.foss.module.pickup.common.client.dao.IInsurGoodsDao;
import com.deppon.foss.module.pickup.common.client.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.common.client.dao.INetGroupDao;
import com.deppon.foss.module.pickup.common.client.dao.IProductDao;
import com.deppon.foss.module.pickup.common.client.dao.IProhibitGoodsDao;
import com.deppon.foss.module.pickup.common.client.dao.ISalesDepartmentDao;
import com.deppon.foss.module.pickup.common.client.dao.ISysConfigDao;
import com.deppon.foss.module.pickup.common.client.dao.IVehicleAgencyDeptDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.DataDictionaryValueDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.DistrictDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.FreightRouteDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.InsurGoodsDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.LabeledGoodDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.NetGroupDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.ProductDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.ProhibitGoodsDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.SalesDepartmentDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.SysConfigDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.VehicleAgencyDeptDao;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.ICustomerService;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.common.client.service.IPriceRebateService;
import com.deppon.foss.module.pickup.common.client.service.ISalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillFreightRouteService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.CustomerService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.impl.LabeledGoodService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceRebateService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.service.impl.VehicleAgencyDeptService;
import com.deppon.foss.module.pickup.common.client.service.impl.WaybillFreightRouteService;
import com.deppon.foss.module.pickup.common.client.service.impl.WaybillPendingService;
import com.deppon.foss.module.pickup.creating.client.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.creating.client.dao.IWaybillCHDtlPendingDao;
import com.deppon.foss.module.pickup.creating.client.dao.IWaybillChargeDtlDao;
import com.deppon.foss.module.pickup.creating.client.dao.IWaybillDao;
import com.deppon.foss.module.pickup.creating.client.dao.IWaybillDisDtlDao;
import com.deppon.foss.module.pickup.creating.client.dao.IWaybillDisDtlPendingDao;
import com.deppon.foss.module.pickup.creating.client.dao.IWaybillOfflineDao;
import com.deppon.foss.module.pickup.creating.client.dao.IWaybillPaymentDao;
import com.deppon.foss.module.pickup.creating.client.dao.IWaybillPaymentPendingDao;
import com.deppon.foss.module.pickup.creating.client.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.creating.client.dao.IWoodenRequirePendingDao;
import com.deppon.foss.module.pickup.creating.client.dao.IWoodenRequirementsDao;
import com.deppon.foss.module.pickup.creating.client.dao.impl.ActualFreightDao;
import com.deppon.foss.module.pickup.creating.client.dao.impl.WaybillCHDtlPendingDao;
import com.deppon.foss.module.pickup.creating.client.dao.impl.WaybillChargeDtlDao;
import com.deppon.foss.module.pickup.creating.client.dao.impl.WaybillDao;
import com.deppon.foss.module.pickup.creating.client.dao.impl.WaybillDisDtlDao;
import com.deppon.foss.module.pickup.creating.client.dao.impl.WaybillDisDtlPendingDao;
import com.deppon.foss.module.pickup.creating.client.dao.impl.WaybillOfflineDao;
import com.deppon.foss.module.pickup.creating.client.dao.impl.WaybillPaymentDao;
import com.deppon.foss.module.pickup.creating.client.dao.impl.WaybillPaymentPendingDao;
import com.deppon.foss.module.pickup.creating.client.dao.impl.WaybillPendingDao;
import com.deppon.foss.module.pickup.creating.client.dao.impl.WoodenRequirePendingDao;
import com.deppon.foss.module.pickup.creating.client.dao.impl.WoodenRequirementsDao;
import com.deppon.foss.module.pickup.creating.client.service.ILocalRuleVerifyService;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillOffLineSubmitService;
import com.deppon.foss.module.pickup.creating.client.service.impl.LocalRuleVerifyService;
import com.deppon.foss.module.pickup.creating.client.service.impl.SalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.impl.WaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.service.impl.WaybillOffLineSubmitService;
import com.deppon.foss.module.pickup.pricing.api.server.dao.ICityMarketPlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IDiscountOrgDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IDiscountPriorityDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IInempDiscountPlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMarketingEventChannelDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMarketingEventDAO;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IOuterPriceDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopPriceDetailSectionDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopValueAddedDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceDiscountDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceRuleDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricingValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductItemDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPublishPriceDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionAirDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionArriveDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionBigGoodsArriveDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionBigGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionEcGoodsArriveDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionEcGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionValueAddDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IValueAddDiscountDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.ICityMarketPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDiscountPriorityService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectiveRegionOrgService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IInempDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IOuterPriceCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPopValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceCriteriaDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDiscountService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceEntryService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceValuationService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPricingValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductItemService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionAirService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionValueAddService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IValueAddDiscountService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IWaybillRegionImpPushService;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.CityMarketPlanDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.DiscountOrgDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.DiscountPriorityDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.EffectivePlanDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.EffectivePlanDetailDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.GoodsTypeDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.InempDiscountPlanDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.MarketingEventChannelDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.MarketingEventDAO;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.OuterPriceDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PopPriceDetailSectionDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PopValueAdddedDetailDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PopValueAddedDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PriceDiscountDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PriceEntryDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PricePlanDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PriceRuleDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PriceValuationDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PricingValueAddedDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.ProductItemDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.PublishPriceDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.RegionAirDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.RegionArriveDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.RegionBigGoodsArriveDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.RegionBigGoodsDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.RegionDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.RegionEcGoodsArriveDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.RegionEcGoodsDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.RegionValueAddDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.ValueAddDiscountDao;
import com.deppon.foss.module.pickup.pricing.server.service.impl.BillCaculateService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.CityMarketPlanService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.DiscountPriorityService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.EffectivePlanDetailService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.EffectivePlanService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.EffectiveRegionOrgService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressPricePlanService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.GoodsTypeService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.InempDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.OuterPriceCaculateService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PopValueAddedService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PriceCriteriaDetailService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PriceDiscountService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PriceEntryService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PriceValuationService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PricingValueAddedService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.ProductItemService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.ProductService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.RegionAirService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.RegionArriveService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.RegionService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.RegionValueAddService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.ValueAddDiscountService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.WaybillRegionImpPushService;
import com.google.inject.Binder;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 2012-5-14 linws 新增 </div>
 ******************************************** 
 */
public class WayBillModule extends GuiceModule {

	//log
	private static Log LOG = LogFactory
				.getLog(WayBillModule.class);

	/**
	 * guice model configuration
	 */
	@Override
	public void configure(Binder binder) {
		
		//数据字典
		injectBean(binder,IDataDictionaryValueDao.class, DataDictionaryValueDao.class);
		//限保物品
		injectBean(binder,IInsurGoodsDao.class, InsurGoodsDao.class);
		//禁运物品
		injectBean(binder,IProhibitGoodsDao.class, ProhibitGoodsDao.class);
		//行政区域数据
		injectBean(binder,IDistrictDao.class, DistrictDao.class);
		//产品
		injectBean(binder,IProductDao.class, ProductDao.class);
		//价格规则数据
		injectBean(binder,IPriceRuleDao.class, PriceRuleDao.class);
		//营业部数据
		injectBean(binder,ISalesDepartmentDao.class, SalesDepartmentDao.class);
		//用来操作交互“偏线代理网点”的数据库对应数据访问DAO接口
		injectBean(binder,IVehicleAgencyDeptDao.class, VehicleAgencyDeptDao.class);
		// 货签信息数据
		injectBean(binder,ILabeledGoodDao.class, LabeledGoodDao.class);
		//运单基础资料
		injectBean(binder,IBaseDataService.class, BaseDataService.class);
		//运单数据字典
		injectBean(binder,IDataDictionaryValueService.class, DataDictionaryValueService.class);
		//运单本地规则
		injectBean(binder,ILocalRuleVerifyService.class, LocalRuleVerifyService.class);
		//走货路径数据
		injectBean(binder,IFreightRouteDao.class, FreightRouteDao.class);
		//网点组数据
		injectBean(binder,INetGroupDao.class, NetGroupDao.class);
		//系统配置数据
		injectBean(binder,ISysConfigDao.class, SysConfigDao.class);
		//营业部服务
		injectBean(binder,ISalesDepartmentService.class, SalesDepartmentService.class);
		//价格服务
		injectBean(binder,IPriceRebateService.class, PriceRebateService.class);
		//偏线代理网点
		injectBean(binder,IVehicleAgencyDeptService.class, VehicleAgencyDeptService.class);
		//货签服务接口
		injectBean(binder,ILabeledGoodService.class, LabeledGoodService.class);
		//查询客户信息的服务接口
		injectBean(binder,ICustomerService.class, CustomerService.class);
		//管理部出发运单
		injectBean(binder,ISalesDeptWaybillService.class, SalesDeptWaybillService.class);
		// 离线开单信息
		injectBean(binder,IWaybillOfflineDao.class, WaybillOfflineDao.class);
		//待处理运单服务接口
		injectBean(binder,IWaybillPendingService.class, WaybillPendingService.class);
		// 价格区域
		injectBean(binder,IRegionValueAddDao.class,RegionValueAddDao.class);
		injectBean(binder,IRegionValueAddService.class,RegionValueAddService.class); 
		// ***************************************产品价格*******************
		injectBean(binder,IBillCaculateService.class, BillCaculateService.class);
		injectBean(binder,IPriceValuationService.class, PriceValuationService.class);
		injectBean(binder,IPriceValuationDao.class, PriceValuationDao.class);
		injectBean(binder,IEffectivePlanDetailService.class, EffectivePlanDetailService.class);
		injectBean(binder,IEffectivePlanService.class, EffectivePlanService.class);
		injectBean(binder,IEffectivePlanDao.class, EffectivePlanDao.class);
		injectBean(binder,IEffectivePlanDetailDao.class, EffectivePlanDetailDao.class);
		injectBean(binder,IProductService.class, ProductService.class);
		injectBean(binder,com.deppon.foss.module.pickup.pricing.api.server.dao.IProductDao.class, 
				com.deppon.foss.module.pickup.pricing.server.dao.impl.ProductDao.class);
		injectBean(binder, IProductItemDao.class, ProductItemDao.class);
		injectBean(binder, IGoodsTypeDao.class, GoodsTypeDao.class);
		injectBean(binder, IEmployeeService.class, EmployeeService.class);
		injectBean(binder, IEmployeeDao.class, EmployeeDao.class);
		injectBean(binder, IOrgAdministrativeInfoService.class, OrgAdministrativeInfoService.class);
		injectBean(binder, INetGroupMixDao.class, NetGroupMixDao.class);
		injectBean(binder, IWaybillRegionImpPushService.class, WaybillRegionImpPushService.class);

		injectBean(binder, ISendOrgAdministrativeInfoService.class, SendOrgAdministrativeInfoService.class);
		injectBean(binder, IEffectiveRegionOrgService.class, EffectiveRegionOrgService.class);
		injectBean(binder, IRegionService.class, RegionService.class);
		injectBean(binder, IRegionArriveService.class, RegionArriveService.class);
		injectBean(binder, IRegionArriveDao.class, RegionArriveDao.class);
		injectBean(binder, IPreferentialService.class, PreferentialService.class);

		injectBean(binder, IOrgAdministrativeInfoDao.class, OrgAdministrativeInfoDao.class);
		injectBean(binder, IFinancialOrganizationsService.class, FinancialOrganizationsService.class);
		injectBean(binder, IFinancialOrganizationsDao.class, FinancialOrganizationsDao.class);
		injectBean(binder, com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService.class, 
				com.deppon.foss.module.base.baseinfo.server.service.impl.VehicleAgencyDeptService.class);
		injectBean(binder, com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao.class, 
				com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyDeptDao.class);
		injectBean(binder, IVehicleAgencyCompanyService.class, VehicleAgencyCompanyService.class);
		injectBean(binder, IVehicleAgencyCompanyDao.class, VehicleAgencyCompanyDao.class);
		injectBean(binder, IAirAgencyCompanyDao.class, AirAgencyCompanyDao.class); 
		injectBean(binder, IAdministrativeRegionsService.class, AdministrativeRegionsService.class);
		injectBean(binder, IAdministrativeRegionsDao.class, AdministrativeRegionsDao.class);
		injectBean(binder, IPricingValueAddedDao.class, PricingValueAddedDao.class);
		injectBean(binder, IPriceCriteriaDetailDao.class, PriceCriteriaDetailDao.class);
		injectBean(binder, IProductItemService.class, ProductItemService.class);
		injectBean(binder, IConfigurationParamsService.class, ConfigurationParamsService.class);
		injectBean(binder, com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService.class, 
				com.deppon.foss.module.base.dict.server.service.impl.DataDictionaryValueService.class);
		injectBean(binder, com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao.class, 
				com.deppon.foss.module.base.dict.server.dao.impl.DataDictionaryValueDao.class);
		injectBean(binder, IOrgAdministrativeInfoComplexService.class, OrgAdministrativeInfoComplexService.class);
		injectBean(binder, IOrgAdministrativeInfoDao.class, OrgAdministrativeInfoDao.class);
		injectBean(binder, IOrgAdministrativeInfoComplexDao.class, OrgAdministrativeInfoComplexDao.class);
		injectBean(binder, IMotorcadeDao.class, MotorcadeDao.class);
		injectBean(binder, IPricingValueAddedService.class, PricingValueAddedService.class);
		injectBean(binder, IGoodsTypeService.class, GoodsTypeService.class);
		injectBean(binder, IPriceEntryDao.class, PriceEntryDao.class);
		injectBean(binder, IConfigurationParamsDao.class, ConfigurationParamsDao.class);
		injectBean(binder, IRegionDao.class, RegionDao.class);
		injectBean(binder, IPriceDiscountService.class, PriceDiscountService.class);
		injectBean(binder, IValueAddDiscountService.class, ValueAddDiscountService.class);
		injectBean(binder, IPopValueAddedService.class, PopValueAddedService.class);
		injectBean(binder, IPopValueAddedDao.class, PopValueAddedDao.class);
		injectBean(binder, IPopValueAddedDetailDao.class, PopValueAdddedDetailDao.class);
		
		injectBean(binder, IValueAddDiscountDao.class, ValueAddDiscountDao.class);
		injectBean(binder, IPriceDiscountDao.class, PriceDiscountDao.class);
		injectBean(binder, IMarketingEventDAO.class, MarketingEventDAO.class);
		
		injectBean(binder, IMarketingEventChannelDao.class, MarketingEventChannelDao.class);
		injectBean(binder, IDiscountOrgDao.class, DiscountOrgDao.class);
		injectBean(binder, IDiscountPriorityService.class, DiscountPriorityService.class);
		injectBean(binder, IDiscountPriorityDao.class, DiscountPriorityDao.class);
		injectBean(binder, IPricingOrgDao.class, PricingOrgDao.class);
		
		injectBean(binder, IPriceEntryService.class, PriceEntryService.class);
		
		injectBean(binder, IUserDeptDataService.class, UserDeptDataService.class);
		
		injectBean(binder, IMotorcadeService.class, MotorcadeService.class);
		
		injectBean(binder, IRegionAirService.class, RegionAirService.class);
		injectBean(binder, IRegionAirDao.class, RegionAirDao.class);
		
		// ***************************************产品价格*******************
		
		// ***************************************离线运单提交*******************
		injectBean(binder, IWaybillOffLineSubmitService.class, WaybillOffLineSubmitService.class);
		injectBean(binder, IWaybillDao.class, WaybillDao.class);
		injectBean(binder, IWaybillDisDtlDao.class, WaybillDisDtlDao.class);
		injectBean(binder, IWaybillChargeDtlDao.class, WaybillChargeDtlDao.class);
		injectBean(binder, IWaybillPaymentDao.class, WaybillPaymentDao.class);
		injectBean(binder, IWoodenRequirementsDao.class, WoodenRequirementsDao.class);
		injectBean(binder, IActualFreightDao.class, ActualFreightDao.class);
		// ***************************************离线运单提交*******************
		
		// ***************************************离线运单暂存*******************
		injectBean(binder, IWaybillOffLinePendingService.class, WaybillOffLinePendingService.class);
		injectBean(binder, IWaybillPendingDao.class, WaybillPendingDao.class);
		injectBean(binder, IWaybillDisDtlPendingDao.class, WaybillDisDtlPendingDao.class);
		injectBean(binder, IWaybillCHDtlPendingDao.class, WaybillCHDtlPendingDao.class);
		injectBean(binder, IWaybillPaymentPendingDao.class, WaybillPaymentPendingDao.class);
		injectBean(binder, IWoodenRequirePendingDao.class, WoodenRequirePendingDao.class);
		// ***************************************离线运单暂存*******************
		
		
		// ***************************************离线查询走货线路*******************
		injectBean(binder, IWaybillFreightRouteService.class, WaybillFreightRouteService.class);
		//走货路径
		injectBean(binder, IFreightRouteService.class, FreightRouteService.class);
		//走货路径Dao
		injectBean(binder, com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao.class, 
				com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteDao.class);
		//网点组服务类
		injectBean(binder, INetGroupService.class, NetGroupService.class);
		// 网点组Dao
		injectBean(binder, com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao.class, 
				com.deppon.foss.module.base.baseinfo.server.dao.impl.NetGroupDao.class);
		//线路Dao
		injectBean(binder, ILineDao.class, LineDao.class);
		//快递线路Dao
		injectBean(binder, IExpressLineDao.class, ExpressLineDao.class);
		//快递发车标准类
		injectBean(binder, IExpressDepartureStandardDao.class, ExpressDepartureStandardDao.class);
		// 线段服务类
		injectBean(binder, ILineItemService.class, LineItemService.class);
		//线段Dao
		injectBean(binder, ILineItemDao.class, LineItemDao.class);
		//库区服务
		injectBean(binder, IGoodsAreaService.class, GoodsAreaService.class);
		//库区Dao
		injectBean(binder, IGoodsAreaDao.class, GoodsAreaDao.class);
		
		//发车标准服务类
		injectBean(binder, IDepartureStandardService.class, DepartureStandardService.class);
		//走货路径线路服务接口
		injectBean(binder, IFreightRouteLineService.class, FreightRouteLineService.class);
		//走货路径线路Dao
		injectBean(binder, IFreightRouteLineDao.class, FreightRouteLineDao.class);
		//外场 Service接口
		injectBean(binder, IOutfieldService.class, OutfieldService.class);
		//发车标准Dao
		injectBean(binder, IDepartureStandardDao.class, DepartureStandardDao.class);
		//线路服务类
		injectBean(binder, ILineService.class, LineService.class);
		//快递线路服务类
		injectBean(binder, IExpressLineService.class, ExpressLineService.class);
		//快递发车标准服务类
		injectBean(binder, IExpressDepartureStandardService.class, ExpressDepartureStandardService.class);
		//营业部 Service接口
		injectBean(binder, ISaleDepartmentService.class, SaleDepartmentService.class);
		//外场 DAO接口
		injectBean(binder, IOutfieldDao.class, OutfieldDao.class);
		//营业部 DAO接口
		injectBean(binder, ISaleDepartmentDao.class, SaleDepartmentDao.class);
		
		//公布价接口
		//injectBean(binder, IPublishPriceService.class, PublishPriceService.class);

		//公布价detail接口
		injectBean(binder, IOuterPriceCaculateService.class, OuterPriceCaculateService.class);
		//公布价detail接口
		injectBean(binder, IPriceCriteriaDetailService.class, PriceCriteriaDetailService.class);
		//公布价dao接口
		injectBean(binder, IPublishPriceDao.class, PublishPriceDao.class);
		//用户数据权限service接口
		injectBean(binder, IUserDeptAuthorityService.class, UserDeptAuthorityService.class);
		//(计价条目服务)
		injectBean(binder, IPriceEntryService.class, PriceEntryService.class);
		
		injectBean(binder, ISalesBillingGroupDao.class, SalesBillingGroupDao.class);
		injectBean(binder, ISalesBillingGroupService.class, SalesBillingGroupService.class);
		
		injectBean(binder, IAsteriskSalesDeptDao.class, AsteriskSalesDeptDao.class);
		injectBean(binder,IAsteriskSalesDeptService.class, AsteriskSalesDeptService.class);
		
		injectBean(binder, IBillingGroupTransFerService.class, BillingGroupTransFerService.class);
		injectBean(binder,IBillingGroupTransFerDao.class, BillingGroupTransFerDao.class);
		
		injectBean(binder, ISalesDescExpandService.class, SalesDescExpandService.class);
		injectBean(binder,ISalesDescExpandDao.class, SalesDescExpandDao.class);
		injectBean(binder,ILdpAgencyDeptService.class, LdpAgencyDeptService.class);
		injectBean(binder,ILdpAgencyDeptDao.class, LdpAgencyDeptDao.class);
		//偏线价格  20140317 xmm MANA-2018
		injectBean(binder,IOuterPriceDao.class,OuterPriceDao.class);
		injectBean(binder,IRegionBigGoodsDao.class,RegionBigGoodsDao.class);
		injectBean(binder,IRegionBigGoodsArriveDao.class,RegionBigGoodsArriveDao.class);
		
		injectBean(binder,IRegionEcGoodsDao.class,RegionEcGoodsDao.class);
		injectBean(binder,IRegionEcGoodsArriveDao.class,RegionEcGoodsArriveDao.class);
		
		injectBean(binder,ICityMarketPlanService.class,CityMarketPlanService.class);
		
		injectBean(binder,ICityMarketPlanDao.class,CityMarketPlanDao.class);
		injectBean(binder,IPopPriceDetailSectionDao.class,PopPriceDetailSectionDao.class);
		injectBean(binder,ICusBargainService.class,CusBargainService.class);
		injectBean(binder,IExpressPricePlanService.class,ExpressPricePlanService.class);
		injectBean(binder,com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceRuleDao.class,com.deppon.foss.module.pickup.pricing.server.dao.impl.PriceRuleDao.class);
		injectBean(binder,IPricePlanDao.class,PricePlanDao.class);
		
		//injectBean(binder,IExpressDiscountPlanService.class,ExpressDiscountPlanService.class);
		
		//injectBean(binder,IExpressDiscountPlanDao.class,ExpressDiscountPlanDao.class);
		
		injectBean(binder,com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceRuleDao.class,com.deppon.foss.module.pickup.pricing.server.dao.impl.PriceRuleDao.class);
		//内部员工折扣方案注入
		injectBean(binder,IInempDiscountPlanService.class,InempDiscountPlanService.class);
		injectBean(binder,IInempDiscountPlanDao.class,InempDiscountPlanDao.class);
		injectBean(binder,IGradientDiscountService.class,GradientDiscountService.class);
		injectBean(binder,IGradientDiscountDao.class,GradientDiscountDao.class);	
		// ***************************************离线查询走货线路*******************
		//特惠活动客户打折扣service接口 
		injectBean(binder, ISpecialdiscountService.class, SpecialdiscountService.class);
		
		
		}

	/**
	 * guice注入bean
	 * @param binder
	 * @param interfaceClass
	 * @param implementClass
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	private void injectBean(Binder binder , Class interfaceClass, Class implementClass) {
		try {
			binder.bind(interfaceClass).to(implementClass).asEagerSingleton();
		} catch (Exception e) {
			System.out.println(interfaceClass.getName()+"||"+implementClass.getName());
			e.printStackTrace();
		}
		LOG.info(" 启动注入 :" +implementClass.getSimpleName()+" 成功");
	}
}