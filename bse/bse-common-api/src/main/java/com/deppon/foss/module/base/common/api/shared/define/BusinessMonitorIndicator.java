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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/define/BusinessMonitorIndicator.java
 * 
 * FILE NAME        	: BusinessMonitorIndicator.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.shared.domain
 * FILE    NAME: BusinessMonitorIndicator.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.common.api.shared.define;

/**
 * 业务监控指标
 * 
 * @author ibm-zhuwei
 * @date 2013-1-29 下午4:34:08
 */
public enum BusinessMonitorIndicator {
	
	/* 常规业务监控指标定义 */
	// 开单和签收运单量（提货方式）
	BILLING_DELIVERY_COUNT("BILLING_DELIVERY_COUNT", "开单送货票数", BusinessMonitorIndicator.TYPE_COUNTER),
	BILLING_PICKUP_COUNT("BILLING_PICKUP_COUNT", "开单自提票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_DELIVERY_COUNT("POD_DELIVERY_COUNT", "送货签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_PICKUP_COUNT("POD_PICKUP_COUNT", "自提签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 开单和签收运单量（运输性质）
	BILLING_RECISION_COUNT("BILLING_RECISION_COUNT", "开单为精准票数", BusinessMonitorIndicator.TYPE_COUNTER),
	BILLING_COMMON_COUNT("BILLING_COMMON_COUNT", "开单为普货票数", BusinessMonitorIndicator.TYPE_COUNTER),
	BILLING_AIR_COUNT("BILLING_AIR_COUNT", "开单为空运票数", BusinessMonitorIndicator.TYPE_COUNTER),
	BILLING_PARTIAL_COUNT("BILLING_PARTIAL_COUNT", "开单为偏线票数", BusinessMonitorIndicator.TYPE_COUNTER),
	BILLING_WHOLE_COUNT("BILLING_WHOLE_COUNT", "开单为整车票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_RECISION_COUNT("POD_RECISION_COUNT", "精准签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_COMMON_COUNT("POD_COMMON_COUNT", "普货签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_AIR_COUNT("POD_AIR_COUNT", "空运签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_PARTIAL_COUNT("POD_PARTIAL_COUNT", "偏线签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_WHOLE_COUNT("POD_WHOLE_COUNT", "整车签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	EXPRESS_LDP_COUNT("EXPRESS_LDP_COUNT", "快递代理签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 开单和签收运单量（付款方式）
	BILLING_PREPAY_COUNT("BILLING_PREPAY_COUNT", "开单为现金票数", BusinessMonitorIndicator.TYPE_COUNTER),
	BILLING_TOPAY_COUNT("BILLING_TOPAY_COUNT", "开单为到付票数", BusinessMonitorIndicator.TYPE_COUNTER),
	BILLING_CARD_COUNT("BILLING_CARD_COUNT", "开单为银行卡票数", BusinessMonitorIndicator.TYPE_COUNTER),
	BILLING_CREDIT_COUNT("BILLING_CREDIT_COUNT", "开单为月结票数", BusinessMonitorIndicator.TYPE_COUNTER),
	BILLING_DEBT_COUNT("BILLING_DEBT_COUNT", "开单为临时欠款票数", BusinessMonitorIndicator.TYPE_COUNTER),
	BILLING_ONLINE_COUNT("BILLING_ONLINE_COUNT", "开单为网上支付票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_PREPAY_COUNT("POD_PREPAY_COUNT", "现金签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_TOPAY_COUNT("POD_TOPAY_COUNT", "到付签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_CARD_COUNT("POD_CARD_COUNT", "银行卡签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_CREDIT_COUNT("POD_CREDIT_COUNT", "月结签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_DEBT_COUNT("POD_DEBT_COUNT", "临时欠款签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	POD_ONLINE_COUNT("POD_ONLINE_COUNT", "网上支付签收票数", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 开单和签收运单金额（运费类字段）
	BILLING_TOTAL_AMOUNT("BILLING_TOTAL_AMOUNT", "开单总金额", BusinessMonitorIndicator.TYPE_TOTALIZER),
	BILLING_INSURANCE_AMOUNT("BILLING_INSURANCE_AMOUNT", "开单保价金额", BusinessMonitorIndicator.TYPE_TOTALIZER),
	BILLING_COD_AMOUNT("BILLING_COD_AMOUNT", "开单代收货款金额", BusinessMonitorIndicator.TYPE_TOTALIZER),
	
	// 订单处理情况
	ORDER_TOTAL_COUNT("ORDER_TOTAL_COUNT", "订单总量", BusinessMonitorIndicator.TYPE_COUNTER),
	ORDER_PENDING_COUNT("ORDER_PENDING_COUNT", "未处理订单量", BusinessMonitorIndicator.TYPE_COUNTER),
	ORDER_PROCESSED_COUNT("ORDER_PROCESSED_COUNT", "已处理订单量", BusinessMonitorIndicator.TYPE_COUNTER),
	ORDER_CANCELED_COUNT("ORDER_CANCELED_COUNT", "已取消订单量", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 交接和配载情况
	HANDOVER_BILL_COUNT("HANDOVER_BILL_COUNT", "交接单数量", BusinessMonitorIndicator.TYPE_COUNTER),
	ASSEMBLE_BILL_COUNT("ASSEMBLE_BILL_COUNT", "配载单数量", BusinessMonitorIndicator.TYPE_COUNTER),
	HANDOVER_WAYBILL_COUNT("HANDOVER_WAYBILL_COUNT", "交接票数", BusinessMonitorIndicator.TYPE_COUNTER),
	HANDOVER_LABEL_COUNT("HANDOVER_LABEL_COUNT", "交接件数", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 发车台数情况
	TRUCK_LONG_DEPART_COUNT("TRUCK_LONG_DEPART_COUNT", "长途发车台次", BusinessMonitorIndicator.TYPE_COUNTER),
	TRUCK_SHORT_DEPART_COUNT("TRUCK_SHORT_DEPART_COUNT", "短途发车台次", BusinessMonitorIndicator.TYPE_COUNTER),
	TRUCK_SCHEDULE_DEPART_COUNT("TRUCK_SCHEDULE_DEPART_COUNT", "集中接送货车发车台次", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 外请车发车台数情况
	LEASED_LONG_DEPART_COUNT("LEASED_LONG_DEPART_COUNT", "长途外请车台次", BusinessMonitorIndicator.TYPE_COUNTER),
	LEASED_SHORT_DEPART_COUNT("LEASED_SHORT_DEPART_COUNT", "短途外请车台次", BusinessMonitorIndicator.TYPE_COUNTER),
	LEASED_SCHEDULE_DEPART_COUNT("LEASED_SCHEDULE_DEPART_COUNT", "集中接送货外请车台次", BusinessMonitorIndicator.TYPE_COUNTER),
	
	/* 新业务监控指标定义 */
	// 订单改派率
	ORDER_ASSIGN_COUNT("ORDER_ASSIGN_COUNT", "订单分派票数", BusinessMonitorIndicator.TYPE_COUNTER),
	ORDER_REASSIGN_COUNT("ORDER_REASSIGN_COUNT", "订单改派票数", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 订单处理效率（订单分派票数）
	
	// 自动预分配识别率
	ORDER_AUTO_ASSIGN_COUNT("ORDER_AUTO_ASSIGN_COUNT", "订单自动分配总量", BusinessMonitorIndicator.TYPE_COUNTER),
	ORDER_AUTO_ASSIGN_SUCCESS_COUNT("ORDER_AUTO_ASSIGN_SUCCESS_COUNT", "订单自动分配成功数", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 更改单内部更改比率
	WAYBILLRFC_TOTAL_COUNT("WAYBILLRFC_TOTAL_COUNT", "更改单总数", BusinessMonitorIndicator.TYPE_COUNTER),
	WAYBILLRFC_INTERNAL_COUNT("WAYBILLRFC_INTERNAL_COUNT", "更改单内部数", BusinessMonitorIndicator.TYPE_COUNTER),
	WAYBILLRFC_TOTAL_COUNT_WAY("WAYBILLRFC_TOTAL_COUNT_WAY", "快递更改单总数", BusinessMonitorIndicator.TYPE_COUNTER),
	WAYBILLRFC_INTERNAL_COUNT_WAY("WAYBILLRFC_INTERNAL_COUNT_WAY", "快递更改单内部数", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 更改单自动受理比率
	WAYBILLRFC_ACCEPT_COUNT("WAYBILLRFC_ACCEPT_COUNT", "更改单受理数", BusinessMonitorIndicator.TYPE_COUNTER),
	WAYBILLRFC_AUTO_ACCEPT_COUNT("WAYBILLRFC_AUTO_ACCEPT_COUNT", "更改单自动受理数", BusinessMonitorIndicator.TYPE_COUNTER),
	WAYBILLRFC_ACCEPT_COUNT_WAY("WAYBILLRFC_ACCEPT_COUNT_WAY", "快递更改单受理数", BusinessMonitorIndicator.TYPE_COUNTER),
	WAYBILLRFC_AUTO_ACCEPT_COUNT_WAY("WAYBILLRFC_AUTO_ACCEPT_WAY", "快递更改单自动受理数", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 派送排单效率
	DELIVERY_SCHEDULE_COUNT("DELIVERY_SCHEDULE_COUNT", "派送排单票数", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 发车计划更改条目
	TRUCK_DEPART_PLAN_COUNT("TRUCK_DEPART_PLAN_COUNT", "发车计划次数", BusinessMonitorIndicator.TYPE_COUNTER),
	TRUCK_DEPART_PLAN_MODIFY_COUNT("TRUCK_DEPART_PLAN_MODIFY_COUNT", "发车计划修改次数", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 月台匹配率
	OPTIMAL_PLATFORM_CALC_COUNT("OPTIMAL_PLATFORM_CALC_COUNT", "计算最优月台次数", BusinessMonitorIndicator.TYPE_COUNTER),
	OPTIMAL_PLATFORM_USE_COUNT("OPTIMAL_PLATFORM_USE_COUNT", "最优月台使用次数", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 月台吞吐货量
	PLATFORM_USE_LABEL_COUNT("PLATFORM_USE_LABEL_COUNT", "月台吞吐货量", BusinessMonitorIndicator.TYPE_COUNTER),
	
	/* 应用数据监控指标定义 */
	// 系统功能使用情况
	SYSTEM_RESOURCE_COUNT("SYSTEM_RESOURCE_COUNT", "系统功能使用情况", BusinessMonitorIndicator.TYPE_COUNTER),
	
	// 组织在线情况
	ORG_ONLINE_COUNT("ORG_ONLINE_COUNT", "组织在线情况", BusinessMonitorIndicator.TYPE_COUNTER),

	// 模拟登陆情况
	SIMULATE_LOGIN_COUNT("SIMULATE_LOGIN_COUNT", "模拟登陆情况", BusinessMonitorIndicator.TYPE_COUNTER),
	;
	
	/**
	 * 指标类型：计数器
	 */
	public static final String TYPE_COUNTER = "C";
	
	/**
	 * 指标类型：加法计算器
	 */
	public static final String TYPE_TOTALIZER = "T";

	/**
	 * 指标类型：动态计算
	 */
	public static final String TYPE_DYNAMIC = "D";

	/**
	 * 指标类别：常规业务
	 */
	public static final String CATEGORY_COMMON = "COMMON";

	/**
	 * 指标类别：新业务
	 */
	public static final String CATEGORY_NEW = "NEW";

	/**
	 * 指标类别：应用数据
	 */
	public static final String CATEGORY_APPLICATION = "APPLICATION";

	/**
	 * 指标编码
	 */
	private String code;

	/**
	 * 指标名称
	 */
	private String name;

	/**
	 * 指标类型：计数器、金额
	 */
	private String type;
	
	/**
	 * Constructor
	 * @author ibm-zhuwei
	 * @date 2013-1-29 下午5:09:26
	 */
	private BusinessMonitorIndicator(String code, String name, String type) {
		this.code = code;
		this.name = name;
		this.type = type;
	}

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}

}
