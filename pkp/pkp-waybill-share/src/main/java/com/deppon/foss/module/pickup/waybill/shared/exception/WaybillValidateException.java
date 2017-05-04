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
package com.deppon.foss.module.pickup.waybill.shared.exception;

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
	 * 一票多件货物件数最多为9件，请修改 
	 * */
	public static final String ONEGOODSQTYTOTAL_GOODSQTYTOTAL = "foss.gui.creating.calculateAction.exception.oneGoodsQtyTotalGoods";
	/**
	 * 重量不能为0
	 */
	public static final String NULL_GOODSWEIGHTTOTAL = "foss.gui.creating.calculateAction.exception.nullGoodsWeightTotal";
	/**
	 * 一票多件货物总重量最多为100kg，请修改
	 * */
	public static final String ONEGOODSQTYTOTAL_GOODSWEIGHTTOTAL = "foss.gui.creating.calculateAction.exception.oneGoodsQtyTotalWeightTotal";
	/**
	 * 体积不能为0
	 */
	public static final String NULL_GOODSVOLUMETOTAL = "foss.gui.creating.calculateAction.exception.nullGoodsVolumeTotal";
	/**
	 * 一票多件货物总体积不得超过0.6方，请修改
	 * */
	public static final String ONEGOODSQTYTOTAL_GOODSVOLUMETOTAL = "foss.gui.creating.calculateAction.exception.oneGoodsQtyTotalVolumeTotal";
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
   	//根据完成接货任务未找到对应子母单信息
   	public static final String INNER_PICKUP_DELEIVERY_EMPLOYEE_CODE_IS_INVALID ="foss.waybill.eWaybillService.innerPickupDeliveryEmployeeCodeIsInvalid";
	//未找到对应运单号或者运单号为空
   	public static final String EWAYBILL_ACTIVE_WAYBILL_NO_IS_NULL ="foss.waybill.eWaybillService.waybillNoIsNull";
	//未找到对应定单号或者定单号为空
   	public static final String EWAYBILL_ACTIVE_ORDER_NO_IS_NULL ="foss.waybill.eWaybillService.orderNoIsNull";
    //未找到对应原始订单或者原始订单
   	public static final String EWAYBILL_ACTIVE_ORIGINAL_NUMBER_IS_NULL ="foss.waybill.eWaybillService.originalNumberIsNull";
    //未找到对应ecom_waybill_relate,pda_scan相关有效数据
   	public static final String EWAYBILL_ACTIVE_EffectiveData_IS_NULL ="foss.waybill.eWaybillService.effectiveDataIsNull";
	//计算总费用出现未知异常
   	public static final String EWAYBILL_CALCULATE_ALL_FEE_UNKNOW_EXCEPTION ="foss.waybill.eWaybillService.calculateTransportFeeUnknowException";
   	//子件不允许更改
   	public static final String CHILD_NOT_ALLOW_CHANGE = "foss.waybill.eWaybillService.childNotAllowChange";
   	//当前库存部门编码不能为空
   	public static final String CURRENT_ORG_CODE_NOT_ALLOW_NULL = "foss.waybill.eWaybillService.CurrentOrgCodeNotAllowNull";
   	//收货部门不能为空
   	public static final String BILLING_ORG_CODE_NOT_ALLOW_NULL = "foss.waybill.eWaybillService.BillingOrgCodeNotAllowNull";
   	
	
   	/**
   	 * 零担电子运单新增数据校验
   	 */
   	//为查询到OMS订单
   	public static final String LTLEWAYBILL_OMS_ORDER_IS_NULL = "foss.waybill.lTLEwaybillService.omsOrderIsNull";
   	//OMS订单已经取消 
   	public static final String LTLEWAYBILL_OMS_ORDER_STATUS_CANCEL = "foss.waybill.lTLEwaybillService.omsOrderAlreadyCancel";
    //运单校验异常,运单号异常,应该为7开头九位数！
  	public static final String WAYBILL_ERROR="foss.pkp.waybill.lTLEwaybillService.exception.wayBillError";
  	//不支持所选运输性质 (产品code)
  	public static final String PRODUCT_CODE_NO_ALLOW= "foss.pkp.waybill.lTLEwaybillService.exception.productCodeNoAllow";
  	//不支持该提货方式
  	public static final String PICKUP_METHOD_NO_ALLOW= "foss.pkp.waybill.lTLEwaybillService.exception.pickupMethodNoAllow";
  	//固定电话和手机号码重复，请重新输入
  	public static final String MOBILE_TELEPHONE_RE= "foss.pkp.waybill.lTLEwaybillService.exception.mobileTelephoneRe";
  	//收货省市区不能为空
  	public static final String PRO_CITY_COUNTY= "foss.pkp.waybill.lTLEwaybillService.exception.proCityCounty";
  	//收货客户地址只能录入（汉字、-、数字、字母）！
  	public static final String RECEIVE_ADDRESS_ERROR= "foss.pkp.waybill.lTLEwaybillService.exception.receiveAddressError";
  	//提货网点不能为空
  	public static final String PICK_UP_ORGCODE_NO_ALLOW= "foss.pkp.waybill.lTLEwaybillService.exception.pickupOrgcodeNoAllow";
  	//退款类型不能为空
  	public static final String CODE_TYPE_NO_ALLOW= "foss.pkp.waybill.lTLEwaybillService.exception.codeTypeNoAllow";
  	//除审核退的代收货款业务类型必须填写客户银行账号信息
  	public static final String AUDIT_BANK_NO_ALLOW= "foss.pkp.waybill.lTLEwaybillService.exception.auditBankNoAllow";
  	//只有来源于官网的订单并且订单的付款方式为网上支付才能选择网上支付
  	public static final String INTERNET_PAY_FOR_DOP= "foss.pkp.waybill.lTLEwaybillService.exception.internetFayForDop";
   	//代收货款不能小于0
	public static final String NULLORZERO_COD_AMOUNT="foss.pkp.waybill.lTLEwaybillService.exception.nullOrZeroCodAmount";
  	//装卸费率为空
  	public static final String NULL_PARAMS_ENTITY_CONFVALUE="foss.pkp.waybill.lTLEwaybillService.exception.NullParamsEntityConfValue";
  	//当前部门不存在装卸费比率，无法进行装卸费计算
  	public static final String NULL_PARAMS_ENTITY="foss.pkp.waybill.lTLEwaybillService.exception.NullParamsEntity";
  	//大件上楼资料未维护，请维护相关资料！
  	public static final String NEED_LARGE_DELIVER_UP_DATA="foss.pkp.waybill.lTLEwaybillService.exception.needLargeDeliverUpData";
  	//货物基本信息不能为空
  	public static final String WAYBILLPENDINGENTITY_NULL="foss.pkp.waybill.lTLEwaybillService.exception.nullWaybillPendingEntity";
  	//货物体积不能为空或0
  	public static final String NULLORZERO_GOODSVOLUME = "foss.pkp.waybill.lTLEwaybillService.exception.nullOrZeroGoodsVolume";
  	//货物重量不能为空或0
  	public static final String NULLORZERO_GOODSWEIGHT = "foss.pkp.waybill.lTLEwaybillService.exception.nullOrZeroGoodsWeight";
  	//该单为空运单据且送货
  	public static final String DELIVER_AIR = "foss.pkp.waybill.lTLEwaybillService.exception.deliverAir";
  	//目的地是香港的，提货方式不为送货
  	public static final String IS_HK_NOT_DELIVER = "foss.pkp.waybill.lTLEwaybillService.exception.isHKNotDeliver";
  	//包装信息不能大于50字符(注意:中文占2个字符)
  	public static final String PACKAGE_CH_OVER_50 = "foss.pkp.waybill.lTLEwaybillService.exception.packageChOver50";
  	//货物名称不能空
  	public static final String NULL_GOODS_NAME = "foss.pkp.waybill.lTLEwaybillService.exception.nullGoodsName";
  	//件数不能小于等于0
  	public static final String NUMBER_OF_CASES_LE_ZERO = "foss.pkp.waybill.lTLEwaybillService.exception.numberOfCasesLeZero";
  	//货物重量不能等于0
  	public static final String GOODSWEIGHTTOTAL_LE_ZERO = "foss.pkp.waybill.lTLEwaybillService.exception.goodsWeightTotalLeZero";
  	//货物总体积不能等于0
  	public static final String GOODSVOLUMETOTAL_LE_ZERO = "foss.pkp.waybill.lTLEwaybillService.exception.goodsVolumeTotalLeZero";
  	//当前线路无法代打木架，如果需要打木架请重新选择线路
  	public static final String UNABLE_MAKE_YOKE = "foss.pkp.waybill.lTLEwaybillService.exception.unableMakeYoke";
  	//单票全打包装时,打木架体积+打木箱体积必须等于开单总体积
  	public static final String SINGLE_VOTE_PACKAGE_ERROR = "foss.pkp.waybill.lTLEwaybillService.exception.singleVotePackageError";
  	//发票标记不能为空
  	public static final String NULL_INVOICETYPE= "foss.pkp.waybill.lTLEwaybillService.exception.nullInvoiceType";
  	//运输性质不能为空
  	public static final String NULL_PRODUCTCODE= "foss.pkp.waybill.lTLEwaybillService.exception.nullProductCode";
  	//不符合开精准大票条件！
  	public static final String UNABLE_RECISION_PRODUCTS_BG= "foss.pkp.waybill.lTLEwaybillService.exception.unableRecisionProductsBg";
  	//包超重货操作服务费不能开精准大票
  	public static final String OVER_WEIGHTFEE_RECISION_PRODUCTS_BG= "foss.pkp.waybill.lTLEwaybillService.exception.overWeightFeeRecisionProductsBg";
  	//此工号优惠额度已用完，不能开内部发货运单
  	public static final String OVER_HIGHST_PREFERENTIAL= "foss.pkp.waybill.lTLEwaybillService.exception.overHighstPreferential";
  	//对内备注录入错误：最大字符不能超过1300个
  	public static final String INNERNOTES_CH_OVER_1300= "foss.pkp.waybill.lTLEwaybillService.exception.innerNotesChOver1300";
  	//储运事项 录入错误：最大字符不能超过1300个！
  	public static final String TRANSNOTES_CH_OVER_1300= "foss.pkp.waybill.lTLEwaybillService.exception.transNotesChOver1300";
  	//当前运单单件重量超出该提货网点单件重量上限，请选择其他网点
  	public static final String OVER_SINGLETON_WEIGHT_LIMIT= "foss.pkp.waybill.lTLEwaybillService.exception.overSingletonWeightLimit";
  	//当前运单单件体积超出该提货网点单件体积上限，请选择其他网点
  	public static final String OVER_SINGLETON_VOLUME_LIMIT= "foss.pkp.waybill.lTLEwaybillService.exception.overSingletonVolumeLimit";
  	//当前运单总重量超出该提货网点单票重量上限，请选择其他网点
  	public static final String OVER_TOTAL_WEIGHT_LIMIT= "foss.pkp.waybill.lTLEwaybillService.exception.overTotalWeightLimit";
  	//当前运单总体积超出该提货网点单票体积上限，请选择其他网点
  	public static final String OVER_TOTAL_VOLUME_LIMIT= "foss.pkp.waybill.lTLEwaybillService.exception.overTotalVolumeLimit";
  	//提货方式不能为空
  	public static final String NULL_RECEIVE_METHOD= "foss.pkp.waybill.lTLEwaybillService.exception.nullReceiveMethod";
  	//货物总重量不可为空或者小于等于0
  	public static final String NULLORZERO_GOODSWEIGHTTOTAL = "foss.pkp.waybill.lTLEwaybillService.exception.nullOrZeroGoodsWeightTotal";
  	//货物总件数不可为空或者小于等于0
  	public static final String NULLORZERO_NUMBER_OF_CASES_TOTAL = "foss.pkp.waybill.lTLEwaybillService.exception.nullOrZeroNumberOfUnitsTotal";
  	//单件货物重量超过50公斤不可开送货上楼
  	public static final String DELIVER_UP_SINGLETON_WEIGHT_OVER_50 = "foss.pkp.waybill.lTLEwaybillService.exception.deliverUpSingletonWeightOver50";
  	//有打木架情况下，单件货物重量超过65公斤不可开送货上楼
  	public static final String WOODYOKE_DELIVER_UP_SINGLETON_WEIGHT_OVER_65 = "foss.pkp.waybill.lTLEwaybillService.exception.woodyokeDeliverUpSingletonWeightOver65";
  	//单票货物重量小于50公斤不可开大件上楼
  	public static final String LARGE_DELIVER_UP_SINGLETON_WEIGHT_LES_50 = "foss.pkp.waybill.lTLEwaybillService.exception.largeDeliverUpSingletonWeightLes50";
  	//单件货物重量大于130公斤不可开大件上楼
  	public static final String LARGE_DELIVER_UP_SINGLETON_WEIGHT_OVER_130 = "foss.pkp.waybill.lTLEwaybillService.exception.largeDeliverUpSingletonWeightOver130";
  	//有打木架情况下，单件货物重量大于170公斤不可开大件上楼
  	public static final String WOODYOKE_LARGE_DELIVER_UP_SINGLETON_WEIGHT_OVER_170 = "foss.pkp.waybill.lTLEwaybillService.exception.woodyokeLargeDeliverUpSingletonWeightOver170";
  	//开单营业部编码根据外场部门的驻地营业部编码获得，不能为空
  	public static final String BILLING_DEPARTMENTENTITY_NULL= "foss.pkp.waybill.lTLEwaybillService.exception.billingDepartmententityNull";
  	//开单人不能为空
  	public static final String BILLING_USER_NULL= "foss.pkp.waybill.lTLEwaybillService.exception.billingUserNull";
  	//不存在运单
  	public static final String UNEXIST_WAYBILL = "foss.pkp.waybill.lTLEwaybillService.exception.unExistWaybill";
  	//不存在待补录
  	public static final String UNEXIST_WAYBILL_PENDING = "foss.pkp.waybill.lTLEwaybillService.exception.unExistWaybillPending";
    //发货客户信息不存在(323098)
   	public static final String CUSTOMER_NOT_ALLOW= "foss.pkp.waybill.lTLEwaybillService.exception.customer_not_allow";
   	//订单号或运单号不支持多次分配,关系异常
   	public static final String ORDERNOORWAYBILLNO_ONE_TO_MANY = "foss.pkp.waybill.lTLEwaybillService.exception.orderOrWaybillNoOneToMany";
   	//该客户编码与收货部门之间无归属或绑定关系，不允许开月结
   	public static final String CAN_NOT_OPEN_MONTH_PAYMENT = "foss.pkp.waybill.lTLEwaybillService.exception.canNotOpenMonthPayment";
   	//发货人的合同部门编码且催款部门是为空，不能开月结 
   	public static final String START_CONTRACT_OR_REMINDER_ORGCODE_EMPTY = "foss.pkp.waybill.lTLEwaybillService.exception.startContractOrReminderOrgCodeEmpty";
   	//运单的收货部门（出发部门）、创建部门，最终配载部门不能为空!
	public static final String DEPARTMENT_INFO_CANNOT_EMPTY = "foss.pkp.waybill.lTLEwaybillService.exception.departmentInfoCannotEmpty";
   	//明细之和不等于总运费，不能继续操作
	public static final String ACCOUNTING_ERROR = "foss.pkp.waybill.lTLEwaybillService.exception.accountingError";
	//运单部分字段金额不正确，不能继续操作
	public static final String PART_OF_AMOUNT_NOT_CORRECT="foss.pkp.waybill.lTLEwaybillService.exception.partOfAmountNotCorrect";
	//当代收不为0的时候,代收货款类型（即日退、三日退、审核退）不能为空
	public static final String REFUND_TYPE_IS_EMPTY="foss.pkp.waybill.lTLEwaybillService.exception.refundTypeIsEmpty";
	//当代收不为0的时候,发货客户编码不能为空
	public static final String DELIVERY_CUSTOMER_CODE_EMPTY="foss.pkp.waybill.lTLEwaybillService.exception.deliveryCustomerCodeEmpty";
	//代收货款类型为即日退，则代收货款的退款账号类型不能为对公帐号 
	public static final String REFUND_ACCOUNT_TYPES_CANNOT_ACCOUNT="foss.pkp.waybill.lTLEwaybillService.exception.refundAccountTypesCannotAccount";
	//即日退、三日退类型代收货款,收款人不能为空
	public static final String ACCOUNT_NAME_EMPTY="foss.pkp.waybill.lTLEwaybillService.exception.accountNameEmpty";
	//即日退、三日退类型代收货款,银行帐号不能为空
	public static final String ACCOUNT_CODE_EMPTY="foss.pkp.waybill.lTLEwaybillService.exception.accountCodeEmpty";
	//即日退、三日退类型代收货款,开户行编码不能为空
	public static final String BANK_HQ_CODE_EMPTY="foss.pkp.waybill.lTLEwaybillService.exception.bankHQCodeEmpty";
	//即日退、三日退类型代收货款,开户行不能为空
	public static final String ACCOUNT_BANK_EMPTY="foss.pkp.waybill.lTLEwaybillService.exception.accountBankEmpty";
	//即日退、三日退类型代收货款,省不能为空
	public static final String PROVINCE_CODE_EMPTY="foss.pkp.waybill.lTLEwaybillService.exception.provinceCodeEmpty";
	//即日退、三日退类型代收货款,市不能为空
	public static final String CITY_CODE_OR_NAME_EMPTY="foss.pkp.waybill.lTLEwaybillService.exception.cityCodeOrNameEmpty";
	//即日退、三日退类型代收货款,对公对私标志不能为空
	public static final String PUBLIC_PRIVATE_FLAG_EMPTY="foss.pkp.waybill.lTLEwaybillService.exception.publicPrivateFlagEmpty";
	//即日退、三日退类型代收货款,支行编码/名称不能为空
	public static final String BANK_BRANCH_CODE_OR_NAME_EMPTY="foss.pkp.waybill.lTLEwaybillService.exception.bankBranchCodeOrNameEmpty";
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