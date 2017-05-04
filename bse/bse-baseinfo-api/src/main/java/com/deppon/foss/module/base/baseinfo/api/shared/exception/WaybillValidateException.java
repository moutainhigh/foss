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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/exception/WaybillValidateException.java
 * 
 * FILE NAME        	: WaybillValidateException.java
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
package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;



/**
 * 
 * 用于运单校验时异常处理
 * @author 025000-FOSS-helong
 * @date 2012-11-23 上午10:31:39
 */
public class WaybillValidateException extends BusinessException {

	 
	/**
	 * 快递装卸费不能为负数
	 */
	public static final String NOT_NEGATIVE_SERVICEFEE= "foss.waybill.eWaybillService.notnegativeservice";
	/**
	 * 快递装卸费超过限制
	 */
	public static final String NOT_SVALE_SERVICEFEE= "foss.waybill.eWaybillService.notsvaleservice";
	/**
	 * 快递装卸费业务方式为折让方式不允许修改
	 */
	public static final String NOT_THEWAY_SERVICEFEE= "foss.waybill.eWaybillService.notwayservice";
	/**
	 * 未获取到有效的装卸费比例
	 */
	public static final String NOT_GET_SERVICEFEE= "foss.waybill.eWaybillService.notgetservice";
	/**
	 * 装卸费率为空
	 */
	public static final String NOT_NULL_SERVICEFEE= "foss.waybill.eWaybillService.notnullservice";
	/**
	 * 航班类型不能为空
	 */
	public static final String NULL_FLIGHTNUMBERTYPE = "foss.gui.creating.calculateAction.exception.nullFlightNumberType";
	
	/**
	 * 合票方式不能为空！
	 */
	public static final String NULL_FREIGHTMETHOD = "foss.gui.creating.calculateAction.exception.nullFreightMethod";
	
	/**
	 * 保险声明价不能为0，请重新输入！
	 */
	public static final String NULL_INSURANCEAMOUNT = "foss.gui.creating.calculateAction.exception.nullInsuranceAmount";
	/**
	 * 整车约车编号不能为空
	 */
	public static final String null_VehicleNumber = "foss.gui.creating.calculateAction.exception.nullVehicleNumber";
	/**
	 * 件数不能为0
	 */
	public static final String NULL_GOODSQTYTOTAL = "foss.gui.creating.calculateAction.exception.nullGoodsQtyTotal";
	/**
	 * 重量不能为0
	 */
	public static final String NULL_GOODSWEIGHTTOTAL = "foss.gui.creating.calculateAction.exception.nullGoodsWeightTotal";
	/**
	 * 体积不能为0
	 */
	public static final String NULL_GOODSVOLUMETOTAL = "foss.gui.creating.calculateAction.exception.nullGoodsVolumeTotal";
	/**
	 * 体积不能为空
	 */
	public static final String NULL_GOODSVOLUMETOTAL1 = "foss.gui.creating.calculateAction.exception.nullGoodsVolumeTotal1";
	
	/**
	 * 空运货物类型不能为空
	 */
	public static final String NULL_AIRGOODSTYPE = "foss.gui.creating.calculateAction.exception.airGoodsType";
	
	//运单校验异常,运单号["{0}"]已存在！
	public static final String WAYBILL_EXSITS="foss.pkp.waybill.waybillManagerService.exception.wayBillExsits";

    //运单校验异常,订单号["{0}"]已导入开单！
	public static final String WAYBILL_IMPORT="foss.pkp.waybill.waybillManagerService.exception.wayBillImport";
	
    //运单校验异常,开单提货方式非[内部带货自提],费用明细信息不能为空！
	public static final String CHARGEDETAIL_EMPTY="foss.pkp.waybill.waybillManagerService.exception.emptyChargeDetail";
	
    //运单校验异常,开单提货方式非[内部带货自提],付款明细信息不能为空！
	public static final String PAYMENTLIST_EMPTY="foss.pkp.waybill.waybillManagerService.exception.emptyPaymentList";
	
    //运单校验异常,开单提货方式为[内部带货自提],折扣明细信息必须为空！
	public static final String DISCOUTDETAIL_NULL="foss.pkp.waybill.waybillManagerService.exception.nullDiscoutDetail";
	
    //运单校验异常,运单基本信息不能为空！
	public static final String WAYBILLENTITY_NULL="foss.pkp.waybill.waybillManagerService.exception.nullWaybillEntity";
	 
    //未查询到始发部门信息:
	public static final String DEPARTMENTENTITY_NULL="foss.pkp.waybill.waybillManagerService.exception.nullDepartmentEntity";
    
    //未查询到开单营业部始发班车信息：
	public static final String DEPARTURESTANDARD_NULL="foss.pkp.waybill.waybillManagerService.exception.nullDepartureStandard";
    
	 //运单号列表为空！
	public static final String WAYBILLNOLIST_NULL="foss.pkp.waybill.waybillManagerService.exception.nullWaybillNoList";
    
	//弃货运单号为空，请输入运单号！
    public static final String ABANDONEDWAYBILLNO_NULL="foss.pkp.waybill.waybillManagerService.exception.nullAbandonedWaybillNo";
    
    //运单校验异常,运单号[{0}]已存在！
    public static final String WAYBILLNO_EXSITS="foss.pkp.waybill.WaybillPendingService.exception.wayBillNoExsits";
	
    //最终配载部门为空！
    public static final String LAST_LOAD_ORG_CODE_NULL="foss.pkp.waybill.waybillStockService.exception.nullLastLoadOrgCode";
    
    //始发配载部门为空！
    public static final String LOAD_ORG_CODE_NULL = "foss.pkp.waybill.waybillStockService.exception.nullLoadOrgCode"; 
    
    //最终外场编码为空！
    public static final String  LAST_LOAD_OUT_ORG_INFO_CODE_NULL  = "foss.pkp.waybill.waybillStockService.exception.lastLoadOrgCodeIsNull"; 
    
    //查询不到库区！
    public static final String GOODS_AREAS_NULL="foss.pkp.waybill.waybillStockService.exception.nullGoodsAreas";
	
    //对应库区编码为空！
    public static final String GOODS_AREAS_CODE_NULL="foss.pkp.waybill.waybillStockService.exception.nullGoodsAreaCode";
	
    /** 自提情况下只能选择非虚拟营业部 */
    public static final String WAYBILLENTITY_EXPRESS="foss.pkp.waybill.waybillManagerService.exception.nullWaybillEntityeExpress";
    /** 送货情况下只能选择虚拟营业部 */
    public static final String WAYBILLENTITY_EXPRESS_VIRTAL="foss.pkp.waybill.waybillManagerService.exception.nullWaybillEntityeExpressVirtual";
    
    //此单号已被转储，不能再使用！
   	public static final String WAYBILL_EXSITS_TRAN="foss.gui.waybill.waybilldescriptor.waybillno.isbusinessexist";
   	
   	//快递员为空！
   	public static final String DRIVER_CODE_NULL = "foss.waybill.eWaybillService.driverCodeIsNull";
   	
   	//订单为空！
   	public static final String DISPATCH_ORDER_NULL = "foss.waybill.eWaybillService.dispatchOrderIsNull";
   	
   	//订单号为空
   	public static final String DISPATCH_ORDERNO_NULL = "foss.waybill.eWaybillService.dispatchOrderNOIsNull";
   	
   	//非电子运单订单
   	public static final String NOT_EWAYBILL_ORDER = "foss.waybill.eWaybillService.isNotEWaybillOrder";
   	
   	//快递单件重量超重
   	public static final String EXPWAYBILL_WEIGHT_OVERLIMIT = "foss.waybill.eWaybillService.expWaybillWeightIsOverLimit";
   	
   	//快递单件体积超方
   	public static final String EXPWAYBILL_VOLUME_OVERLIMIT = "foss.waybill.eWaybillService.expWaybillVolumeIsOverLimit";
   	
   	//目的站为空
   	public static final String  DESTINATION_NULL = "foss.waybill.eWaybillService.destinationIsNull";
   	
   	//产品类型为空
   	public static final String  PRODUCT_CODE_NULL = "foss.waybill.eWaybillService.productCodeIsNull";
   	
	//包装为空
   	public static final String  GOOD_PACKAGE_NULL = "foss.waybill.eWaybillService.goodPackageIsNULL";
   	
   	//包装件数大于开单件数
   	public static final String  GOOD_PACKAGE_OVERLIMIT = "foss.waybill.eWaybillService.goodPackageOverQtyLimit";
   	
   	//没有合同或者合同找不到
   	public static final String  CUSTOMER_BARGAIN_NULL = "foss.waybill.eWaybillService.customerBargainIsNull";
   	
   	//匹配不到对应的收货部门
   	public static final String  RECEIVE_SALESDEPARTMENT_UNMATCHED = "foss.waybill.eWaybillService.receiveSalesDepartmentUnMatched";
   	
   	//发货联系人不能为空
   	public static final String DELIVERY_CUSTOER_CONTACT_NULL = "foss.waybill.eWaybillService.deliveryCustmerContactIsNull";
   	
   	//收货联系人不能为空
   	public static final String RECEIVE_CUSTOER_CONTACT_NULL = "foss.waybill.eWaybillService.receiveCustmerContactIsNull";  
   	
	//发货客户手机号码必须是8位或者11位
   	public static final String DELIVERY_MOBILEPHONE_LENGTH_ILLEGAL = "foss.waybill.eWaybillService.deliveryMobilePhoneLengthIsIllegal";
   	
   	//发货客户手机号码11位时，必须1开头
   	public static final String DELIVERY_MOBILEPHONE_FIRST_NUMBER_ILLEGAL = "foss.waybill.eWaybillService.deliveryMobilePhoneFirstNumberIsIllegal";
   	
   	//收货客户手机号码必须是8位或者11位
   	public static final String RECEIVER_MOBILEPHONE_LENGTH_ILLEGAL = "foss.waybill.eWaybillService.receiveMobilePhoneLengthIsIllegal";
   	
   	//收货客户手机号码11位时，必须1开头
   	public static final String RECEIVER_MOBILEPHONE_FIRST_NUMBER_ILLEGAL = "foss.waybill.eWaybillService.receiveMobilePhoneFirstNumberIsIllegal";
   	
   	//发货人手机或者电话号码必须有一项不为空
   	public static final String DELIVERY_CONTACT_NULL = "foss.waybill.eWaybillService.deliveryContactIsNull";
   	
 	//收货人手机或者电话号码必须有一项不为空
   	public static final String RECEIVER_CONTACT_NULL = "foss.waybill.eWaybillService.receiveContactIsNull";
   	
   	//发货人地址为空
   	public static final String DELIVERY_CUSTOMER_ADDRESS_NULL = "foss.waybill.eWaybillService.deliveryCustomerAddressIsNull";
   	
   	//收货人地址为空
   	public static final String RECEIVER_CUSTOMER_ADDRESS_NULL = "foss.waybill.eWaybillService.receiveCustomerAddressIsNull";
   	
   	//提货方式为空
   	public static final String RECEIVE_METHOD_NULL = "foss.waybill.eWaybillService.receiveMethodIsNull";
   	
   	//对内备注超过1300字符
   	public static final String INNERNOTES_OVERLIMIT = "foss.waybill.eWaybillService.innerNotesLengthIsOverLimit";
   	
   	//储运事项超过1300字符
   	public static final String TRANSNOTES_OVERLIMIT = "foss.waybill.eWaybillService.tansNotesIsOverLimit";
   	
   	//支付方式为空
   	public static final String PAID_METHOD_NULL ="foss.waybill.eWaybillService.paidMethodIsNull";
   	
   	//非月结客户付款方式不能为月结
   	public static final String PAID_METHOD_CT_ILLEGAL ="foss.waybill.eWaybillService.paidMethodCTIsIllegal";
   	
   	//提货方式为机场自提，付款方式不能选择到付
   	public static final String PAID_METHOD_FC_ILLEGAL ="foss.waybill.eWaybillService.paidMethodFCIsIllegal";
   	
   	//提货方式为【机场自提】时，到付金额必须为0
   	public static final String AIRPORT_PICKUP_TOPAY_AMOUNT_ILLEGAL ="foss.waybill.eWaybillService.airPortPickupToPayAmountIsNotZero";
   	
   	//提货网点不支持货到付款
   	public static final String PICKUP_STATSION_CANNOT_CASH_ON_DELIVERY ="foss.waybill.eWaybillService.pickupStationCannotCashOnDelivery";
   	
   	//空运以及偏线无法选择网上支付
	public static final String PAID_METHOD_OL_ILLEGAL ="foss.waybill.eWaybillService.paidMethodOLIsIllegal";
	
	//客户编码不能为空
	public static final String CUSTOMER_CODE_NULL ="foss.waybill.eWaybillService.customerCodeIsNull";
   	
   	//代收货款超过规定最大值
   	public static final String COD_OVERLIMIT  = "foss.waybill.eWaybillService.CODIsOverLimit";
   	
   	//收货部门为空或未找到
   	public static final String RECEIVE_ORG_NULL = "foss.waybill.eWaybillService.receiveOrgIsNull";
   	
   	//返单返货时，必须填写原始运单号
   	public static final String ORIGINAL_WAYBILL_NO_NULL = "foss.waybill.eWaybillService.originalWaybillIsNull";
   	
   	//查询不到时效
   	public static final String TIME_EFFECTIVENESS_NULL = "foss.waybill.eWaybillService.timeEffectivenessIsNull";
   	
   	//走货线路查询不到
   	public static final String FREIGHT_ROUTE_NOT_FOUND ="foss.scheduling.transportPath.cantFindData";
   	
   	//保险声明价值为0的情况下保费不能大于0
   	public static final String INSURANCE_AMOUNT_FEE_ILLEGAL ="foss.waybill.eWaybillService.insuranceAmountFeeIsIllegal";
   	
   	//内部带货发货人工号为空
   	public static final String INNER_PICKUP_DELIVERY_EMPLOYEE_CODE_NULL ="foss.waybill.eWaybillService.innerPickupDeliveryEmployeeCodeIsNull";
   	
   	//当前运单单件重量超出该提货网点单件重量上限
   	public static final String SINGLE_PIECE_WEIGHT_OVER_LIMIT ="foss.waybill.eWaybillService.singlePieceWeightIsOverLimit";      

   	//当前运单单件体积超出该提货网点单件体积上限
   	public static final String SINGLE_PIECE_VOLUME_OVER_LIMIT ="foss.waybill.eWaybillService.singlePieceVolumeIsOverLimit";
   	
   	//当前运单总重量超出该提货网点单票重量上限
   	public static final String SINGLE_BILL_WEIGHT_OVER_LIMIT ="foss.waybill.eWaybillService.singleBillWeightIsOverLimit";   
   	
   	//当前运单总体积超出该提货网点单票体积上限
   	public static final String SINGLE_BILL_VOLUME_OVER_LIMIT ="foss.waybill.eWaybillService.singleBillVolumeIsOverLimit";   
   	
   	//目的站所选网点不能开代收货款
   	public static final String PICKUP_STATSION_CANNOT_COD ="foss.waybill.eWaybillService.pickupStationCannotCOD";
   	
   	//找不到银行账户信息
   	public static final String CUSTOMER_ACCOUNT_NULL ="foss.waybill.eWaybillService.customerAccountIsNull";
   	
   	//选中的银行账户没有设置对公或者对私属性
   	public static final String ACCOUNT_NATURE_NULL ="foss.waybill.eWaybillService.accountNatureIsNull"; 
   	
   	//即日退只能选择对私账户
   	public static final String REFUND_TYPE_R1_NOT_PRIVATE_ACCOUNT ="foss.waybill.eWaybillService.refundTypeR1IsNotPrivateAccount"; 
   	
   	//尺寸计算的结果超过最大值
   	public static final String CALCULATED_VOLUME_OVER_LIMIT = "foss.waybill.eWaybillService.calculateVolumeIsOverLimit";
   	
   	//体积不能为负值
   	public static final String VOLUME_NEGATIVE= "foss.waybill.eWaybillService.volumeCannotBeNeagtive";
   	
   	//没有找到对应的价格
   	public static final String PRICE_NOT_FOUND ="foss.waybill.eWaybillService.priceCannotFound";
   	
   	//价格不存在,公布价运费为0
	public static final String TRANSPORT_FEE_NOT_FOUND = "foss.waybill.eWaybillService.transportFeeCannotBeFound";
	
	//优惠券未启用
	public static final String PROMOTIONS_CODE_UNUSED = "foss.waybill.eWaybillService.promotionsCodeIsUnused";
	
	//市场推广活动未启用
	public static final String CRM_ACTIVE_UNUSED = "foss.waybill.eWaybillService.CRMActiveIsUnused";
	
	//未找到开单部门，不能开快递单
	public static final String CAN_NOT_EXPRESS_CREATESALES_NULL ="foss.waybill.eWaybillService.expWaybillCreateSalesDeptIsNull";
	
	//未找到提货网点，不能开快递单
	public static final String CAN_NOT_EXPRESS_TARGETSALES_NULL ="foss.waybill.eWaybillService.expWaybillTargetSaledDeptIsNull";
	
	//非试点营业部，不能开快递单
	public static final String CAN_NOT_EXPRESS_SALEDEPT_UNTEST ="foss.waybill.eWaybillService.expWaybillSalesDeptIsNotTestDept";
	
	//试点城市和非试点城市之间提货方式只能开自提
	public static final String CAN_SELF_PICKUP_ONLY_ONECITY_UNTEST ="foss.waybill.eWaybillService.canSelfPickupOnlyOneCtiyUntest";
	
	//非试点城市和非试点城市之间提货方式只能开自提
	public static final String CAN_SELF_PICKUP_ONLY_BOTHCITY_UNTEST ="foss.waybill.eWaybillService.canSelfPickupOnlyBothCtiyUntest";
	
	//试点城市和非试点城市之间不能开返单
	public static final String CAN_NOT_RETURN_BILL_ONECITY_UNTEST ="foss.waybill.eWaybillService.cannotReturnBillOneCityUntest";
	
	//试点城市和非试点城市之间不能开返单
	public static final String CAN_NOT_RETURN_BILL_BOTHCITY_UNTEST ="foss.waybill.eWaybillService.cannotReturnBillBothCityUntest";
	
	//试点城市和快递代理理城市之间不能开即日退代收货款
	public static final String CAN_NOT_REFUND_TYPE_ONE_DAY ="foss.waybill.waybillExpressService.cannotRefundTypeOneDay";
	
	//非试点城市和其他城市之间只能开淘宝订单
	public static final String CRM_ORDER_CHANNEL_TAOBAO_ONLY_ONECITY_UNTEST ="foss.waybill.eWaybillService.CRMOrderChannelTaobaoOnlyOneCtiyUntest";
	
	//非试点城市和非试点城市之间只能开淘宝订单
	public static final String CRM_ORDER_CHANNEL_TAOBAO_ONLY_BOTHCITY_UNTEST ="foss.waybill.eWaybillService.CRMOrderChannelTaobaoOnlyBothCtiyUntest";
	
	//非试点城市快递代理城市之间不能发送货快递运单(落低配的英文名实在是找不到啊亲)
	public static final String CAN_NOT_EXPRESS_BETWEEN_UNTEST_LDPCITY ="foss.waybill.eWaybillService.cannotExpressBetweenUntestLDPCity";
	
	//到达城市是非试点的情况下，不能开返单
	public static final String CAN_NOT_RETURN_BILL_TARGETCITY_UNTEST ="foss.waybill.eWaybillService.cannotReturnBillTargetCityIsUntest";
	
	//非自提只能选择虚拟营业部作为提货网点开单
	public static final String CAN_CHOOSE_VIRTUAL_SALESDEPT_ONLY = "foss.waybill.eWaybillService.canChooseVirtualSalesDeptOnly";
	
	//自提只能选择自有网点，不能选择虚拟营业部作为提货网点开单
	public static final String CAN_CHOOSE_SELFOWN_SALESDEPT_ONLY = "foss.waybill.eWaybillService.canChooseSelfOwnSalesDeptOnly";
	
	//提货网点不能做快递自提，请配置基础资料
	public static final String SALESDEPT_CAN_NOT_SELF_PICKUP = "foss.waybill.eWaybillService.salesDeptCannotSelfPickup";
	
	//提货网点不能做快递返单，请配置基础资料
	public static final String SALESDEPT_CAN_NOT_RETURN_SIGNBILL= "foss.waybill.eWaybillService.salesDeptCanNotReturnSignBill";
	
	//没有查到营业部信息，请配置基础资料
	public static final String SALESDEPT_NOT_FOUND = "foss.waybill.eWaybillService.salesDeptNotFound";
	
	//收货部门为空
	public static final String  RECEIVE_ORG_FOUND = "foss.waybill.eWaybillService.receiveOrgIsNull";
	
	//未查询到对应的保险手续费，请联系管理员添加
	public static final String  INSURANCE_FEE_NULL = "foss.waybill.eWaybillService.insuranceFeeIsNull";
	
	//没有对应的代收货款手续费，请联系管理员添加或者选择其他类型
	public static final String COD_FEE_NULL = "foss.waybill.eWaybillService.CODFeeIsNull";
	
	//总费用为空
	public static final String TOTAL_FEE_NULL = "foss.waybill.eWaybillService.totalFeeIsNull";
	
	//对象拷贝失败
	public static final String OBJECT_COPY_ERROR ="foss.waybill.eWaybillService.objectCopyError";
	
	//传入的拷贝对象类型不正确
	public static final String PARAM_FORCOPY_ERROR ="foss.waybill.eWaybillService.paramForCopyError"; 
	
	//非电子运单订单
   	public static final String NOT_EXPRESS_EWAYBILL_ORDER = "foss.waybill.eWaybillService.isNotExpressEWaybillOrder";
	
   	//没有查询到对应的运单实体数据
   	public static final String CREATEORG_CODE_NULL = "foss.gui.common.waybillStockService.exception.nullCreateOrgCode";
   	
   	//没有查询到对应的运单实体数据
   	public static final String CREATEORG_INFO_NULL = "foss.gui.common.waybillStockService.exception.nullCreateOrgInfo";

   	//没有查询到对应的运单实体数据
   	public static final String CREATEORG_SALEDEPT_INFO_NULL = "foss.gui.common.waybillStockService.exception.nullCreateOrgSaleDepartmentEntity";
	
	//不是开单组织
   	public static final String IS_NOT_SALEDEPT = "foss.gui.common.waybillStockService.exception.nullCreateOrgSaleDepartmentEntity";
	
   	//该开单部门不能开一票多件
   	public static final String CANNOT_CREATE_ONETOMANY_EXP = "foss.gui.creatingexp.expCalculateAction.oneGoodsQtyTotalByCustomer";
   	
   	//对不起，没有查询到对应的待处理运单信息！
   	public static final String CANNOT_FIND_PENDING_COMPLETE_INFO = "foss.gui.creating.pendingCompleteDialog.msgbox.label";
   	
	//没有找到需要激活的电子运单订单
   	public static final String CANNOT_FIND_UNACTIVE_EWAYBILL_ORDER = "foss.waybill.eWaybillService.cannotFindEWaybillOrder";
   	
   	//传入的Dto对象为空
   	public static final String SUBMIT_EWAYBILL_DTO_IS_NULL = "foss.pkp.waybill.waybillQueryService.exception.nullWaybillList";
   	
   	//传入的Dto对象为空
   	public static final String CAN_NOT_SUPPORT_RETURN_BILL = "foss.gui.creating.printSignUI.printSerial.cannotCanAgentCollected";
   	//传入的Dto对象为空
   	public static final String EXIST_UNACTIVE_WAYBILL_PENDING_DATA = "foss.gui.creating.printSignUI.printSerial.existUnactiveWaybillPendingData";
   	//传入的Dto对象为空
   	public static final String EXIST_ACTIVE_WAYBILL_DATA = "foss.gui.creating.printSignUI.printSerial.existActiveWaybillData";
   	//没有找到对应收货人所在市级名称
   	public static final String NOT_EXIST_RECEIVECITY_ENTITY = "foss.waybill.eWaybillService.notExistReceiveCityEntity";
   	//没有找到对应收货人所在区县名称
   	public static final String NOT_EXIST_RECEIVECITY_COUNTY = "foss.waybill.eWaybillService.notExistReceiveCountyEntity";
	//收货部门编码，发货客户编码不能同时为空
   	public static final String NOT_ALL_NULL_RECEIVEORGCODE_DELIVER_CUSTOMER_CODE = "foss.waybill.eWaybillService.notAllReceiveOrgCodeAndDeliverCustomerCode";
   	//根据营业部没有查询到对应快递点部信息
   	public static final String NOT_EXIST_EXPRESS_PART_INFO = "foss.waybill.eWaybillService.notExistExpressPartInfo";
   	//根据司机没有查询到其对应车辆信息
   	public static final String NOT_EXIST_EXPRESS_CHELIANG_INFO = "foss.waybill.eWaybillService.notExistExpressCheliangInfo";
   	//根据收货人所在市级所在城市没有找到对应快递区域所述快递派送部门
   	public static final String NOT_EXIST_EXPRESS_DELIVER_SALESDEPT_INFO_OF_CITY_REGION = "foss.waybill.eWaybillService.notExistExpressDeliverDeptInfoOfCityRegion";
   	//根据收货人所在县级所在城市没有找到对应快递区域所述快递派送部门
   	public static final String NOT_EXIST_EXPRESS_DELIVER_SALESDEPT_INFO_OF_COUNTY_REGION = "foss.waybill.eWaybillService.notExistExpressDeliverDeptInfoOfCountyRegion";
   	//目的站为空的情况下收货人市区编码与区县编码不能为空
   	public static final String DESTINATION_IS_NULL_CITY_CODE_DIST_CODE_NOT_ALL_NULL = "foss.waybill.eWaybillService.DestinationIsNullAndCityCodeAndDistCodeNotAllNull";
   	//电子运单预处理线程处理失败
   	public static final String EWAYBILL_ORDER_ACCEPT_FAIL = "foss.waybill.eWaybillService.ewaybillOrderAcceptFailure";
   	//请求参数实体数据为空
   	public static final String REQUEST_DATA_IS_NULL = "foss.waybill.eWaybillService.ewaybillRequestDataIsNull";
   	//请求参数实体对应的运单号与FOSS自有运单号不一致
   	public static final String REQUEST_DATA_OF_WAYBILL_IS_NOT_SAME = "foss.waybill.eWaybillService.ewaybillRequestWaybillNoIsNotSame";
   	//开单时校验运单信息出现未知异常
   	public static final String WAYBILL_VALIDATE_UNKOWN_EXCEPTION = "foss.waybill.eWaybillService.waybillValidateUnkownException";
   	//运单更改时校验运单信息出现未知异常
   	public static final String WAYBILLRFC_VALIDATE_UNKOWN_EXCEPTION = "foss.waybill.eWaybillService.waybillRfcValidateUnkownException";
   	//运单开单时校验结算信息出现未知异常,请检查客户信息是否欠款或者月结额度不足
   	public static final String WAYBILL_SETTLE_VALIDATE_UNKOWN_EXCEPTION = "foss.waybill.eWaybillService.waybillSettleValidateUnkownException";
   	//运单开单时生成路由信息出现未知异常,请先检查走货路由信息是否存在,确定路由信息无误后请保证对应每条路由信息对应计划出发、到达、经停时间是否配置
   	public static final String HANDLE_TRANSPORT_PATH_UNKOWN_EXCEPTION = "foss.waybill.eWaybillService.HandleTransportPathUnkownException";
   	//运单开单时订单数据更新信息校验出现异常
   	public static final String WAYBILL_ORDER_HANDLE_VALIDATE_UNKOWN_EXCEPTION = "foss.waybill.eWaybillService.WaybillOrderHandleUnkownException";
   	//运单开单时运单提交出现未知异常
   	public static final String WAYBILL_SUBMIT_UNKOWN_EXCEPTION = "foss.waybill.eWaybillService.WaybillSubmitUnkownException";
   
   	//运单开单时运单提交出现未知异常
   	public static final String ILLEGAL_ARGUMENT_INPUT_EXCEPTION = "foss.waybill.eWaybillService.inputIllegalArgumentException";
   	//运单开单时PDA接口出现未知异常
   	public static final String PDA_INTERFACE_INPUT_EXCEPTION = "foss.waybill.eWaybillService.PdaInterfaceException";
   	//运单开单时电子运单出现未知异常
   	public static final String EWAYBILL_UNKOWN_EXCEPTION = "foss.waybill.eWaybillService.ewaybillUnKnownException";
   	//产品为电商尊享件时，只能开月结单
   	public static final String IS_ECOMMERCE_PROMOTIONAL_ONLY_MONTH_END = "foss.waybill.eWaybillService.isEcommercePromotionalProductOnlyMonthEnd";
   	//发货客户不是电商尊享客户，不能享受电商尊享优惠
   	public static final String IS_NOT_ECOMMERCE_PROMOTIONAL_CUSTOMER = "foss.waybill.eWaybillService.isNotEcommercePromotionalCustomerException";
   	//内部带货费用承担部门不能为空
   	public static final String INNER_PICKUP_DEPT_NAME_IS_NULL = "foss.waybill.eWaybillService.innerDeptNameIsNullException";
   	//内部带货费用承担部门不能为空
   	public static final String INNER_PICKUP_DEPT_INFO_IS_VALID = "foss.waybill.eWaybillService.innerDeptInfoIsNullException";
   	//该客户不是电商尊享客户，不能开电商尊享单
   	public static final String IS_NOT_ECOMMERCE_CUSTOMER = "foss.waybill.eWaybillService.isNotEcommerceCustomerException";
   	//订单预处理失败，需人工受理
   	public static final String ORDER_PRE_HANDLE_FAULURE = "foss.waybill.eWaybillService.preHandleFalureException";
   	//存在已经待激活的数据
	public static final String IS_EXISIST_UNACTIVE_EWAYBILL_INFO = "foss.waybill.eWaybillService.isExisistUnActiveEWaybillInfo";
   	//货物总件数不能超过10000件
	public static final String GOODS_QTY_TOTAL_NO_MORE_THAN_TEN_THOUSAND = "foss.waybill.eWaybillService.goodQtyTotalnoMoreThanTenThousand";
	//系统分配的快递员FOSS没有登陆权限
	public static final String DRIVER_NOT_HAVE_AUTHORITY_IN_FOSS = "foss.waybill.eWaybillService.distributionDriverHavenoAuthority";
	//系统分配的快递员不是本公司员工
	public static final String DRIVER_NOT_OWNER_EMPLOYEE = "foss.waybill.eWaybillService.distributionDriverIsNotOwnerEmployee";
	//快递员车辆未绑定对应营业部
	public static final String EXPRESS_VEHICLE_IS_NOT_BIND_SALESDEPARTMENT = "foss.waybill.eWaybillService.expressVehicleIsNotBindSalesDepartMent";
	//根据内部带货发货人工号未查询到有效的员工信息
   	public static final String INNER_PICKUP_DELIVERY_EMPLOYEE_CODE_IS_INVALID ="foss.waybill.eWaybillService.innerPickupDeliveryEmployeeCodeIsInvalid";
   	/**
   	 * 内部发货额度不足
   	 */
   	public static final String EMPLOYEE_HIGHSTPREFERENTIAL_LIMIT_NOT_ENABLE = "foss.gui.common.commonutils.employeeHighstPreferentialLimitNotEnable";
   	public static final String  NOT_INEMP_DISCOUNT_PLAN= "foss.gui.common.commonutils.notInempDiscountPlan";
   	public static final String  DELIVERY_NOTMATCHED= "foss.gui.common.commonutils.employeeNo.delivery.notMatched";
   	public static final String  RECEIVE_NOTMATCHED= "foss.gui.common.commonutils.employeeNo.receive.notMatched";
   	
   	/**
	 * 序列化标识 
	 */
	private static final long serialVersionUID = 4668118737380229341L;

	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public WaybillValidateException() {
	    super();
	}

	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public WaybillValidateException(String code, String msg, String natvieMsg, Throwable cause) {
	    super(code, msg, natvieMsg, cause);
	}

	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public WaybillValidateException(String code, String msg, String natvieMsg) {
	    super(code, msg, natvieMsg);
	}

	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public WaybillValidateException(String code, String msg, Throwable cause) {
	    super(code, msg, cause);
	}

	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public WaybillValidateException(String code, String msg) {
	    super(code, msg);
	    this.errCode=msg;
	}
	
	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public WaybillValidateException(String code,Object... args) {
		super(code,args);
		this.errCode = code;
	}
	
	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public WaybillValidateException(String msg, Throwable cause) {
	    super(msg, cause);
	    this.errCode = msg;
	}

	/**
	 *  （创建一个新的实例 ）WaybillValidateException
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-23 下午4:20:19
	 */
	public WaybillValidateException(String msg) {
	    super(msg);
	    this.errCode=msg;
	}

	
}