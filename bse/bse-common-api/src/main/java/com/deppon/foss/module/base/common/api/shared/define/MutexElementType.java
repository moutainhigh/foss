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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/define/MutexElementType.java
 * 
 * FILE NAME        	: MutexElementType.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.shared.define
 * FILE    NAME: MutexElementType.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.common.api.shared.define;

/**
 * 锁定业务类型
 * 
 * @author ibm-zhuwei
 * @date 2013-1-17 下午5:16:11
 */
public enum MutexElementType {
	CASH_IN_COME_LOCK_DEPT_CODE("LockCashInComeDeptCode_","按部门生成缴款报表时,锁定部门"),
	PDA_SIGNAL_CASHCONFIRM("LockWaybillSignalcashConfirm_","收银确认和PDA签收"),
	OTHER_REVENUE_NO("LockOtherRevenue_","小票"),
	WAYBILL_NO("LockW_", "运单"),
	WAYBILL_SERIAL_NO("LockS_", "流水号"),
	PDA_WAYBILL_NO("LockPdaW_", "中转PDA运单扫描"),
	VEHICLE_ASSEMBLE_NO("LockVA_", "汽运配载单号"),
	PENDING_WAYBILL_NO("LockPendingWaybillNo_", "补录运单编号"),
	IN_OUT_STOCK("LockInOutStock_", "出入库"),
	DELIVERBILL_NO("LockDeliverbillNo_","派送单"),
	NEW_DELIVERBILL_NO("LockNewDeliverbillNo_","新派送单"),
	DISPATCHORDER_NO("LockDispatchOrderNo_","订单"),
	DELIVERBILLDETAIL_NO("LockDeliverbillDetailNo_","派送单明细"),
	NEW_DELIVERBILLDETAIL_NO("LockNewDeliverbillDetailNo_","派送单明细"),
	REPAYMENT_NO("LockRepaymentNo_","结清货款"),
	RFC_SIGN("LockRfcSign_","签收"),
	WABYILL_SUBMIT("LockWS_", "运单提交"),
	RFC_IMPORT("LockRFCI_", "更改单导入"),
	RFC_ACCEPT("LockRFCA_", "更改单受理"),
	RFC_CHECK("LockRFCC_", "更改单审核"),
	TRUCK_SCHEDULING("LockTruckScheduling_", "发车计划"),
	TFR_LOAD_VEHICLEASSEMBLEBILL_ADDNEW("LockVehicleAssembleBillAddnew_","新增配载单"),
	TFR_LOAD_EXPRESS_HANDOVERBILL_ADDNEW("LockExpressHandOverBillAddnew_","新增包交接单"),
	TFR_PACKAGE_NEWSERIAL_MAX("LockVehicleAssembleBillAddnew_","打包装获取最大流水号"),
	APPLICATION_SIGN_RFC("LockApplicationSignRfc_","变更签收结果"),
	REVERSE_SIGN("LockReverseSign_","反签收"),
	GOODS_INADVANCE("goodsInadvance_","提前找货"),
	ACCEPT_RFC("LockAcceptRfc_","受理签收变更"),
	PACKAGING_PACKED("LockPacked","包装录入"),
	CUSTOMER_CRMID("LockCustomerCrmId_","客户信息"),
	CONTACT_CRMID("LockContactCrmId_","客户联系人信息"),
	BARGAIN_CRMID("LockBargainCrmId_","客户合同信息"),
	ADDRESS_CRMID("LockAddressCrmid_","客户接送货地址信息"),
	ACCOUNT_CRMID("LockAccountCrmId_","客户银行账号信息"),
	CONTACT_ADDRESS_CRMID("LockContactAddressCrmId_","联系人-偏好地址信息"),
	BARGAIN_APPORG_CRMID("LockBargainAppOrgCrmId_","客户合同适用部门信息"),
	PREFERENTIAL_CRMID("LockPreferentialCrmId_","客户合同优惠信息"),
	NONCUS_CRMID("LockNonCustomerCrmId_","散客信息"),
	ABANDON_GOODS_ID("LockAbandonGoodsId_","弃货ID"),
	ARRIVE_SHEET_CREATE_WAYBILLNO("LockWaybillNo_","运单号"),
	CUSTOMER_NOTIFY("LockNotifyWaybillNo_","客户通知-运单号"),
	CAR_STOPREASON_NO("LockReasonNo_","停车原因编码"),
	VEHICLELMS_CODE("LockVehicleLmsCode_","停车计划车辆LMS唯一编码"),
	DRIVER_NO("LockDriverNo_","司机信息司机工号"),
	VEHICLE_NO("LockVehicleNo_","拖头信息车牌号"),
	STAY_HANDOVER_VEHICLE_NO("LockStayHandoverVehicleNo_","司机接送货卸车车牌号"),
	TRAILER_VERSIONNO("LockTrailerVersionNo_","挂车信息执行LMS的版本号"),
	TRAILER_TYPENO("LockTrailerTypeNo_","挂车类型值编码"),
	VANINFO_VERSIONNO("LockVanInfoVersionNo_","厢式车信息LMS的版本号"),
	RQSVCINFO_VERSIONNO("LockRQSVCInfoVersionNo_","骨架车信息LMS的版本号"),
	CARBRAND_CARNO("LockCarBrandCarNo_","车辆品牌编码"),
	VEHICLELENGTH_NO("LockCarLengthNo_","车长信息编码"),
	PAYEEINFO_PAYEENO("LockPayeeNo_","收款方编码"),
	BANK_CODE("LockBankCode_","银行编码"),
	PROVINCECITY_ID("LockProvinceCityId_","银行省市编码"),
	UUMS_DEGREE_CODE("LockDegreeCode_","职等编码"),
	UUMS_EMP_CODE("LockEmpCode_","人员编码"),
	YUAN_GONG_CODE("lockEmpCode_","员工编码"),
	UUMS_FIN_ORG_CODE("LockFinOrgCode_","财务组织编码"),
	UUMS_ORG_CODE("LockOrgCode_","组织编码"),
	UUMS_POSITION_CODE("LockPositionCode_","职位编码"),
	FIN_PUBLIC_BANK_FID("LockPublicBankFid_","对公银行FID"),
	UUMS_ROLE_CODE("LockRoleCode_","角色编码"),
	UUMS_USER_CODE("LockUserCode_","用户编码"),
	BUSSINESS_ORG_CODE("LockBusOrgCode_","组织编码"),
	WDGH_SALES_CODE("LockWdghSalesCode_","营业部编码"),
	TFR_STOCKCHECKING_ADDNEW("LockStockCheckingAddnew_","新建清仓任务"),
	TFR_LOADER_WORKLOAD_ADDNEW("LockLoderWorkloadAddnew_","装卸量统计"),
	NEW_PRODUCT_CUSTOMER("LockNewProductCustomer","新产品客户协议"),
	MARKACTIVITY_CRMID("lockMarkActivityCrmId_","市场活动信息"),
	GIS_PROGRAM_NAME("LockGisProgramName_","快递支线班车方案名称"),
	TFR_DAILYLOADVOLUME_ADD("ADD_LOCKORG_","新增日承载货量"),
	TFR_DAILYLOADVOLUME_UPDATE("UPDATE_LOCKORG_","修改日承载货量"),
	PICKUP_BIGZONE_CODE("PICKUP_BIGZONE_CODE_","接送货大区编码"),
	PICKUP_SMALLZONE_NAME("PICKUP_SMALLZONE_NAME_","接送货小区名称"),
	COST_CENTER_DEPT_CODE("LockCostCenterDeptCode_","成本中心费用承担部门编码"),
	CUSORDERSOURCEINFO_VERSIONNO("CusOrderSourceInfoVersionNo","CRM行业、客户等级、订单来源信息版本号"),
	TFR_SCHEDULING_RENTALMARK_HANDOVERBILL("LockRentalMarkHandOverBill_","租车标记交接单"),
	TFR_SCHEDULING_RENTALMARK_DELIVERBILL("LockRentalMarkDeliverBill_","租车标记派送单"),
	TFR_SCHEDULING_RENTALMARK_STOWAGEBILL("LockRentalMarkStowageBill_","租车标记配载单"),
	TFR_SCHEDULING_MARK_STOWAGEBILL("LockMarkStowageBill_","租车标记快递交接单"),
	CC_PRESS_WAYBILL_NO("LockCcPressWaybillNo","催单凭证号"),
	CRM_RETURN_GOODS_Dealnumber("LockCrmReturnGoodsDealnumber","返货工单上报"),
	DELIVER_HANDOVERBILL("LockDeliverHandoverWaybillNo_","派送交单-运单号"),	CRM_DISCOUNT_BACK_ID("LockCrmDiscountBackId","事后折概要信息crmId"),
	CRM_CustomerCircleRelation_CRMID("LockCustomerCircleRelationCrmId","客户圈客户信息crmId"),
	CRM_GRADIENT_DISCOUNT_ID("LockCrmGradientDiscountID","梯度折扣"),
	WRAP_GREEN_HAND_WAYBILL("LockWrapGreenHandWaybill","裹裹自动核销"),
	CRM_COLLECTING_PAYMENT_ID("LockCollectingPaymentID","代收货款清单查询接口"),
	UUMS_EMP_CODE_AND_CREATE_TIME("LockEmpCodeAndCreateTime_","人员编码和创建时间"),
	EXPRESS_DELIVERY_SMALLZONE_REGIONCODE("EXPRESS_DELIVERY_SMALLZONE_REGIONCODE_","快递收派小区编码"),
	CRM_WAYBILLNO_SECTION("LockCrmWayBillNoSection","运单号段"),
	COURIERSCHEDULE_EXPRESS_SMALLZONE("COURIERSCHEDULE_EXPRESS_SMALLZONE","快递排班表根据小区编码"),	
	DOP_WAYBILL_NUMBER_DATE("LockWayBillNoDate_","DOP家装运单号加时间"),
	CRM_SPECIALDISCOUNT_CRMID("LockNoncustomerCrmId_","特惠分组信息crmId"),
	EXPRESS_VEHICLE_NO("LockExpressVehicleNo_","快递车辆车牌号"),
	SUPPLIER_CODE("LockSupplierCode_","供应商编码"),
	CAINIAO_YCD_NO("LockCainiaoYCDNo_","菜鸟异常单"),
	ONETICKETORNOTINFO_CODE("OneticketornotInfoCODE","客户编码"),
	EXPRESSPART_DEPT_CODE("ExpressPartDeptCode_","快递点部营业部映射编码"),
	VTS_FOSS_OUTVEHICLE_FEEADJUST("VtsFossOutVehicleFeeAdjust","VTS整车起草审批整车尾款费用调整"),
    VTS_FOSS_WSC_WAYBILL("VtsFossWscWaybill","VTS银行卡生成待刷卡单据"),

	PTP_SIGN_EXCEPTION_WAYBILL_NO("LockPtpExceptionSignWaybillNo","合伙人异常签收"),
	LEASED_DRIVER_IDCARD("LeasedDriverIdcard","外请司机身份证"),
	LEASED_VEHICLE_NO("LeasedVehicleNo","外请车车牌"),
	OWNED_SUBSIDIARY_CODE("owendSubsidiaryCode","所属子公司编码"),
	LONGHAUL_FLEET_CODE("longHaulFleetCode","长途车队编码"),
	AGENT_DEPT_CODE("agentDeptCode","偏线代理网点编码");
	private String prefix;

	private String name;

	private MutexElementType(String prefix, String name) {
		this.prefix = prefix;
		this.name = name;
	}

	/**
	 * @return prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
