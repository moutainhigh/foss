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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/define/GisConstants.java
 * 
 * FILE NAME        	: GisConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.define
 * FILE    NAME: GisConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.common.client.define;



/**
 * 数据下载的词条信息
 * @author foss-dengyao
 * @date 2013-04-27 下午4:27:11
 */
public class DataConsistencyConstants {
	
	/*********** 数据下载实体名与表名映射 **********/ 
	/**
	 * 线路表
	 */
	public final static String T_BAS_LINE = "com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity";

	/**
	 * 外场
	 */
	public final static String T_BAS_TRANSFER_CENTER = "com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity";

	/**
	 * 使用折扣组织
	 */
	public final static String T_SRV_DISCOUNT_ORG = "com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountOrgEntity";
	
	/**
	 * 角色-资源表
	 */
	public final static String T_BAS_ROLE_RESOURCES = "com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity";

	/**
	 * 角色
	 */
	public final static String T_BAS_ROLE = "com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity";

	/**
	 * 时效方案详细信息
	 */
	public final static String T_SRV_EFFECTIVE_PLAN_DETAIL = "com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity";

	/**
	 * 合作伙伴 代理公司
	 */
	public final static String T_BAS_BUSINESS_PARTNER = "com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity";
	
	/**
	 * 价格计算表达式
	 */
	public final static String T_SRV_PRICE_RULE = "com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity";
	
	/**
	 * 营业部
	 */
	public final static String T_BAS_SALES_DEPARTMENT = "com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity";
	
	/**
	 * 行政区域
	 */
	public final static String T_BAS_DISTRICT = "com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity";
	
	/**
	 * 资源表
	 */
	public final static String T_BAS_RESOURCES = "com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity";
	
	/**
	 * 用户表
	 */
	public final static String T_BAS_USER = "com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity";
	
	/**
	 * 部门组织表
	 */
	public final static String T_BAS_ORG = "com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity";
	
	/**
	 * 时效区域信息
	 */
	public final static String T_SRV_EFFECTIVE_REGION = "com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveRegionEntity";
	
	/**
	 * 发车标准
	 */
	public final static String T_BAS_DEPARTURE_STD = "com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity";
	
	/**
	 * 路段信息
	 */
	public final static String T_BAS_LINE_ITEM = "com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity";
	
	/**
	 * 用户部门角色
	 */
	public final static String T_BAS_USER_ORG_ROLE = "com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity";
	
	/**
	 * 折扣/优惠方案
	 */
	public final static String T_SRV_MARKETING_EVENT = "com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventEntity";
	
	/**
	 * 禁运物品
	 */
	public final static String T_BAS_PROHIBIT_GOODS = "com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity";
	
	/**
	 * 库区
	 */
	public final static String T_BAS_GOODS_AREA = "com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity";
	
	/**
	 * 走货路径
	 */
	public final static String T_BAS_FREIGHT_ROUTE = "com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity";
	
	/**
	 * 价格区域与部门对应关系
	 */
	public final static String T_SRV_PRICE_REGION_ORG = "com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity";
	
	/**
	 * 产品
	 */
	public final static String T_SRV_PRODUCT = "com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity";
	
	/**
	 * 折扣渠道
	 */
	public final static String T_SRV_MARKETING_EVENT_CHANEL = "com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventChannelEntity";
	
	/**
	 * 产品条目
	 */
	public final static String T_SRV_PRODUCT_ITEM = "com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity";
	
	/**
	 * 营业部适用产品
	 */
	public final static String T_BAS_PRO_SALESDEPT = "com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity";
	
	/**
	 * 时效方案主信息
	 */
	public final static String T_SRV_EFFECTIVE_PLAN = "com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanEntity";
	
	/**
	 * 网点组
	 */
	public final static String T_BAS_NET_GROUP = "com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity";
	
	/**
	 * 限保物品
	 */
	public final static String T_BAS_INSUR_GOODS = "com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity";
	
	/**
	 * 数据字典-值
	 */
	public final static String T_BAS_DATA_DICTIONARY_VALUE = "com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity";
	
	/**
	 * 价格区域信息
	 */
	public final static String T_SRV_PRICE_REGION = "com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity";
	
	/**
	 * 外部网点
	 */
	public final static String T_BAS_OUTER_BRANCH = "com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity";
	
	/**
	 * 计价条目
	 */
	public final static String T_SRV_PRICING_ENTRY = "com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity";
	
	/**
	 * 系统配置参数
	 */
	public final static String T_BAS_SYS_CONFIG = "com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity";
	
	/**
	 * 走货路径线路
	 */
	public final static String T_BAS_FREIGHT_ROUTE_LINE = "com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity";
	
	/**
	 * 计价规则
	 */
	public final static String[] T_SRV_PRICING_VALUATION = {"com.deppon.foss.module.pickup.waybill.shared.dto.PriceValuationBasisAddDto",
		                                                    "com.deppon.foss.module.pickup.waybill.shared.dto.PriceValuationProductAddDto",
		                                                    "com.deppon.foss.module.pickup.waybill.shared.dto.PriceValuationAirDto",
		                                                    "com.deppon.foss.module.pickup.waybill.shared.dto.PriceValuationFreightDto",
		                                                    "com.deppon.foss.module.pickup.waybill.shared.dto.PriceValuationRegionAddDto"};
	
	/**
	 * 时效区域与部门对应关系
	 */
	public final static String T_SRV_EFFECTIVE_REGION_ORG = "com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveRegionOrgEntity";
	
	/**
	 * 货物类型
	 */
	public final static String T_SRV_GOODSTYPE = "com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity";
	
	/**
	 * 空运价格
	 */
	public final static String T_SRV_PRICE_REGION_AIR = "com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionAirEntity";
	
	/**
	 * 空运价格区域
	 */
	public final static String T_SRV_PRICE_REGION_ORG_AIR = "com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgAirEntity";
	
	public final static String T_BAS_SALESDEPT_BILLINGGROUP = "com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity";
	/**
	 * 价格方案主信息
	 */
	public final static String[] T_SRV_PRICE_PLAN = {"com.deppon.foss.module.pickup.waybill.shared.dto.PriceAirPlanDto",
		                                             "com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity"};

	
	public final static String T_BAS_SALESDEPT_ASTERISK="com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity";

	
}