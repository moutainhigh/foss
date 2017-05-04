/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/domain/DispatchOrderEntity.java
 * 
 * FILE NAME        	: DispatchOrderEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 调度订单实体
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-23 上午9:51:11
 */
public class DispatchOrderEntity extends BaseEntity {

	private static final long serialVersionUID = -3600442883838016544L;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 约车部门名称
	 */
	private String orderVehicleOrgName;
	/**
	 * 约车部门code
	 */
	private String orderVehicleOrgCode;
	/**
	 * 车队是否营业部
	 */
	private String fleetIsSaleDept;
	/**
	 * 约车时间
	 */
	private Date orderVehicleTime;
	/**
	 * 订单类型
	 */
	private String orderType;
	/**
	 * 车型
	 */
	private String vehicleType;
	/**
	 * 重量
	 */
	private BigDecimal weight;
	/**
	 * 体积
	 */	
	private BigDecimal volume;
	/**
	 * 接货省
	 */
	private String pickupProvince;
	/**
	 * 接货市
	 */
	private String pickupCity;
	/**
	 * 接货区
	 */
	private String pickupCounty;
	/**
	 * 接货其他地址信息
	 */
	private String pickupElseAddress;
	/**
	 * 接货最早时间
	 */
	private Date earliestPickupTime;
	/**
	 * 接货最晚时间
	 */
	private Date latestPickupTime;
	/**
	 * 客户姓名
	 */
	private String customerName;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 货物类型
	 */
	private String goodsType;
	/**
	 * 包装
	 */
	private String goodsPackage;
	/**
	 * 电话
	 */
	private String tel;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 件数
	 */
	private Integer goodsQty;
	/**
	 * 收货地址
	 */
	private String consigneeAddress;
	/**
	 * 备注
	 */
	private String orderNotes;
	/**
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 司机code
	 */
	private String driverCode;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 设备号
	 */
	private String deviceNo;
	/**
	 * 营业部名称
	 */
	private String salesDepartmentName;
	/**
	 * 营业部code
	 */
	private String salesDepartmentCode;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 订单发送状态
	 */
	private String orderSendStatus;
	/**
	 * 操作备注
	 */
	private String operateNotes;
	/**
	 * 操作人名称
	 */
	private String operator;
	/**
	 * 操作人code
	 */
	private String operatorCode;
	/**
	 * 操作部门code
	 */
	private String operateOrgName;
	/**
	 * 操作部门name
	 */
	private String operateOrgCode;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 定人定区id
	 */
	private String preprocessId;
	/**
	 * 预处理建议车辆
	 */
	private String vehicleNoSuggested;
	/**
	 * 预处理建议司机姓名
	 */
	private String driverNameSuggested;
	/**
	 * 预处理建议司机code
	 */
	private String driverCodeSuggested;
	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 * 是否PDA发送
	 */
	private String isPda;
	/**
	 * 是否短信发送
	 */
	private String isSms;
	/**
	 * 是否发送给客户
	 */
	private String isCustomer;
	/**
	 * 下单时间
	 */
	private Date orderTime;
	/**
	 * 创建人编码
	 */
	private String createUserCode;
	/**
	 * 创建人名称
	 */
	private String createUserName;
	/**
	 * 派车车队编码
	 */
	private String fleetCode;
	/**
	 * 提货网点
	 */
	private String customerPickupOrgCode;
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	/**
	 * 订单来源
	 */
	private String orderSource;
	/**
	 * 接货省code
	 */
	private String pickupProvinceCode;
	/**
	 * 接货城市code
	 */
	private String pickupCityCode;
	/**
	 * 接货区code
	 */
	private String pickupCountyCode;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 收入部门code
	 */
	private String receiveOrgCode;
	/**
	 * 收入部门name
	 */
	private String receiveOrgName;
	/**
	 * 付款方式
	 */
	protected String paidMethod;
	/**
	 * 预处理建议小区code
	 */
	private String smallZoneCodeSuggested;
	/**
	 * 预处理建议小区name
	 */
	private String smallZoneNameSuggested;
	/**
	 * 实际小区code
	 */
	private String smallZoneCodeActual;
	/**
	 * 是否已入库
	 */
	private String isCollect;
	/**
	 * 特殊地址类型
	 */
	private String addressType;
	/**
	 * 特殊地址类型Code
	 */
	private String addressTypeCode;
	/**
	 * 特殊地址车牌
	 */
	private String specialVehicleNo;

	/**
	 * PDA退回原因
	 */
	private String orderReturnReason;

	/**
	 * 转发人工号
	 */
	private String forwardDriverCode;

	/**
	 * 转发人
	 */
	private String forwardDriverName;
	/**
	 * 部门电话
	 */
	private String depTelephone;
	/**
	 * 已分配记录 14.7.3
	 */
	private String assignedRecord;
	/**
	 * 渠道单号
	 */
	private String channelNumber;
	/**
	 * 发货人工号
	 */
	private String senderCode;
	/**
	 * 费用承担部门
	 */
	private String paymentOrgCode;

	/**
	 * 记录自动分配日志
	 */
	private StringBuffer sbLog = new StringBuffer(); // zxy 20140709 内部优化:记录日志信息

	// 增值信息
	/**
	 * 代收货款类型
	 */
	private String reciveLoanType;

	/**
	 * 代收货款金额
	 */
	private BigDecimal reviceMoneyAmount;

	/**
	 * 报价申明价值
	 */
	private BigDecimal insuredAmount;

	/**
	 * 优惠券编码
	 */
	private String couponNumber;
	private String acceptStatus; // 14.7.30 订单受理方式
	private String driverMobile; // 14.9.13 快递员手机号

	/**
	 * 运单类型,传统运单为空，电子运单为EWAYBILL
	 */
	private String waybillType;

	/**
	 * 发货客户客户客户编码
	 */
	private String deliveryCustomerCode;

	/**
	 * 是否是电子运单大客户
	 */
	private String isEWaybillBigCustomer;

	/**
	 * 代收货款帐号
	 */
	private String codRefundAccount;

	/**
	 * 代收货款帐号开户名
	 */
	private String codRefundAccountName;

	/**
	 * 货物尺寸
	 */
	private String goodsSize;

	/**
	 * 发货具体地址备注
	 */
	private String deliveryCustomerAddressNote;

	/**
	 * 收货具体地址备注
	 */
	private String receiveCustomerAddressNote;

	/**
	 * 收货具体省份
	 */
	private String consigneeProvince;

	/**
	 * 收货具体城市
	 */
	private String consigneeCity;

	/**
	 * 收货具体市区
	 */
	private String consigneeCounty;
	/**
	 * 收货乡镇编码
	 */
	private String consigneeVillageCode;
	/**
	 * 收货乡镇
	 */
	private String consigneeVillage;

	/**
	 * 收货具体地址
	 */
	private String consigneeDetailAddress;

	/**
	 * 特安上限
	 */
	private BigDecimal teanLimit;

	/**
	 * 采购单号
	 */
	private String procurementNumber;

	/**
	 * 是否为特殊地址
	 */
	private String isSpecialAddress;
	
	/**
	 * 收货联系人姓名
	 */
	private String receiverCustName; 
	
	/**
	 * 收货联系人电话
	 */
	private String receiverCustPhone;
	
	/**
	 * 收货联系人手机
	 */
	private String receiverCustMobile;
	
	/**
	 * 客户分群
	 */
	private String customerGroup;

		/**
	 * 其他费用
	 */
	private BigDecimal otherFee;	
	/**
	 * 是否子母件
	 */
	private String isPicPackage;
	
	/**
	 * 原始单号
	 */
	private String originalNumber;
	
	/**
	 * 服务类型
	 */
	private String serviceType;
	
	/**
	 * 上门时效
	 */
	private Integer gotInTime;
	/**
	 * 流入时间
	 */
	private Date inflowTime;
	
	/**
	 * 抢单快递员编码
	 */
	private String pickupManId;
	
	/**
	 * 裹裹订单类型
	 */
	private String ggOrderType;
	
	/**
	 * 退款方式
	 */
	private String refundMethod;
	/**
	 * 是否菜鸟仓配
	 */
	private String cnWd;
	/**
	 * 服务标志:1-Cod 货到付款 2-预约配送 9-上门揽件（支持多个值，英文逗号分隔）
	 */
	private String serviceFlag;
	/**
	 * 计划类型:1-工作日，2-节假日，101-当日达，102-次晨达，103-次日达，104-预约达（在serviceFlag中包含预约配送时有效）
	 */
	private String scheduleType;
	
	/**
	 * 约车接货的时间
	 */
	private Date appointTime;
	
	/**
	 * 司机手机号
	 */
	private String driverPhone;
	
	/**
	 * appointType
	 */
	private String appointType;

	/**
	 * 灰度分配;0:FOSS处理1:RPS处理
	 */
	private String sysGray;
	
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public BigDecimal getTeanLimit() {
		return teanLimit;
	}

	public void setTeanLimit(BigDecimal teanLimit) {
		this.teanLimit = teanLimit;
	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	public String getIsEWaybillBigCustomer() {
		return isEWaybillBigCustomer;
	}

	public void setIsEWaybillBigCustomer(String isEWaybillBigCustomer) {
		this.isEWaybillBigCustomer = isEWaybillBigCustomer;
	}

	public String getCodRefundAccount() {
		return codRefundAccount;
	}

	public void setCodRefundAccount(String codRefundAccount) {
		this.codRefundAccount = codRefundAccount;
	}

	public String getCodRefundAccountName() {
		return codRefundAccountName;
	}

	public void setCodRefundAccountName(String codRefundAccountName) {
		this.codRefundAccountName = codRefundAccountName;
	}

	public String getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	public String getAssignedRecord() {
		return assignedRecord;
	}

	public void setAssignedRecord(String assignedRecord) {
		this.assignedRecord = assignedRecord;
	}

	/**
	 * @return the depTelephone
	 */
	public String getDepTelephone() {
		return depTelephone;
	}

	/**
	 * @param depTelephone
	 *            the depTelephone to set
	 */
	public void setDepTelephone(String depTelephone) {
		this.depTelephone = depTelephone;
	}

	/**
	 * @return the orderSource
	 */
	public String getOrderSource() {
		return orderSource;
	}

	/**
	 * @param orderSource
	 *            the orderSource to set
	 */
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	/**
	 * @return the receiveMethod
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * @param receiveMethod
	 *            the receiveMethod to set
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * @return the orderSendStatus
	 */
	public String getOrderSendStatus() {
		return orderSendStatus;
	}

	/**
	 * @param orderSendStatus
	 *            the orderSendStatus to set
	 */
	public void setOrderSendStatus(String orderSendStatus) {
		this.orderSendStatus = orderSendStatus;
	}

	/**
	 * @return the customerPickupOrgCode
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * @param customerPickupOrgCode
	 *            the customerPickupOrgCode to set
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	 * @return the orderTime
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime
	 *            the orderTime to set
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * @return the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode
	 *            the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName
	 *            the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return the fleetCode
	 */
	public String getFleetCode() {
		return fleetCode;
	}

	/**
	 * @param fleetCode
	 *            the fleetCode to set
	 */
	public void setFleetCode(String fleetCode) {
		this.fleetCode = fleetCode;
	}

	/**
	 * @return the isCustomer
	 */
	public String getIsCustomer() {
		return isCustomer;
	}

	/**
	 * @param isCustomer
	 *            the isCustomer to set
	 */
	public void setIsCustomer(String isCustomer) {
		this.isCustomer = isCustomer;
	}

	/**
	 * @return the isPda
	 */
	public String getIsPda() {
		return isPda;
	}

	/**
	 * @param isPda
	 *            the isPda to set
	 */
	public void setIsPda(String isPda) {
		this.isPda = isPda;
	}

	/**
	 * @return the isSms
	 */
	public String getIsSms() {
		return isSms;
	}

	/**
	 * @param isSms
	 *            the isSms to set
	 */
	public void setIsSms(String isSms) {
		this.isSms = isSms;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 *            the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo
	 *            the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the orderVehicleOrgName
	 */
	public String getOrderVehicleOrgName() {
		return orderVehicleOrgName;
	}

	/**
	 * @param orderVehicleOrgName
	 *            the orderVehicleOrgName to set
	 */
	public void setOrderVehicleOrgName(String orderVehicleOrgName) {
		this.orderVehicleOrgName = orderVehicleOrgName;
	}

	/**
	 * @return the orderVehicleOrgCode
	 */
	public String getOrderVehicleOrgCode() {
		return orderVehicleOrgCode;
	}

	/**
	 * @param orderVehicleOrgCode
	 *            the orderVehicleOrgCode to set
	 */
	public void setOrderVehicleOrgCode(String orderVehicleOrgCode) {
		this.orderVehicleOrgCode = orderVehicleOrgCode;
	}

	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType
	 *            the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * @param vehicleType
	 *            the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *            the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * @return the pickupProvince
	 */
	public String getPickupProvince() {
		return pickupProvince;
	}

	/**
	 * @param pickupProvince
	 *            the pickupProvince to set
	 */
	public void setPickupProvince(String pickupProvince) {
		this.pickupProvince = pickupProvince;
	}

	/**
	 * @return the pickupCity
	 */
	public String getPickupCity() {
		return pickupCity;
	}

	/**
	 * @param pickupCity
	 *            the pickupCity to set
	 */
	public void setPickupCity(String pickupCity) {
		this.pickupCity = pickupCity;
	}

	/**
	 * @return the pickupCounty
	 */
	public String getPickupCounty() {
		return pickupCounty;
	}

	/**
	 * @param pickupCounty
	 *            the pickupCounty to set
	 */
	public void setPickupCounty(String pickupCounty) {
		this.pickupCounty = pickupCounty;
	}

	/**
	 * @return the pickupElseAddress
	 */
	public String getPickupElseAddress() {
		return pickupElseAddress;
	}

	/**
	 * @param pickupElseAddress
	 *            the pickupElseAddress to set
	 */
	public void setPickupElseAddress(String pickupElseAddress) {
		this.pickupElseAddress = pickupElseAddress;
	}

	/**
	 * @return the earliestPickupTime
	 */
	public Date getEarliestPickupTime() {
		return earliestPickupTime;
	}

	/**
	 * @param earliestPickupTime
	 *            the earliestPickupTime to set
	 */
	public void setEarliestPickupTime(Date earliestPickupTime) {
		this.earliestPickupTime = earliestPickupTime;
	}

	/**
	 * @return the latestPickupTime
	 */
	public Date getLatestPickupTime() {
		return latestPickupTime;
	}

	/**
	 * @param latestPickupTime
	 *            the latestPickupTime to set
	 */
	public void setLatestPickupTime(Date latestPickupTime) {
		this.latestPickupTime = latestPickupTime;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel
	 *            the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the goodsQty
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}

	/**
	 * @param goodsQty
	 *            the goodsQty to set
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * @return the consigneeAddress
	 */
	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	/**
	 * @param consigneeAddress
	 *            the consigneeAddress to set
	 */
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	/**
	 * @return the orderNotes
	 */
	public String getOrderNotes() {
		return orderNotes;
	}

	/**
	 * @param orderNotes
	 *            the orderNotes to set
	 */
	public void setOrderNotes(String orderNotes) {
		this.orderNotes = orderNotes;
	}

	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName
	 *            the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * @param driverCode
	 *            the driverCode to set
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo
	 *            the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * @param deviceNo
	 *            the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	/**
	 * @return the salesDepartmentName
	 */
	public String getSalesDepartmentName() {
		return salesDepartmentName;
	}

	/**
	 * @param salesDepartmentName
	 *            the salesDepartmentName to set
	 */
	public void setSalesDepartmentName(String salesDepartmentName) {
		this.salesDepartmentName = salesDepartmentName;
	}

	/**
	 * @return the salesDepartmentCode
	 */
	public String getSalesDepartmentCode() {
		return salesDepartmentCode;
	}

	/**
	 * @param salesDepartmentCode
	 *            the salesDepartmentCode to set
	 */
	public void setSalesDepartmentCode(String salesDepartmentCode) {
		this.salesDepartmentCode = salesDepartmentCode;
	}

	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 *            the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the operateNotes
	 */
	public String getOperateNotes() {
		return operateNotes;
	}

	/**
	 * @param operateNotes
	 *            the operateNotes to set
	 */
	public void setOperateNotes(String operateNotes) {
		this.operateNotes = operateNotes;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 *            the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * @param operatorCode
	 *            the operatorCode to set
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime
	 *            the operateTime to set
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return the operateOrgName
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * @param operateOrgName
	 *            the operateOrgName to set
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * @return the operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * @param operateOrgCode
	 *            the operateOrgCode to set
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * @return the preprocessId
	 */
	public String getPreprocessId() {
		return preprocessId;
	}

	/**
	 * @param preprocessId
	 *            the preprocessId to set
	 */
	public void setPreprocessId(String preprocessId) {
		this.preprocessId = preprocessId;
	}

	/**
	 * @return the vehicleNoSuggested
	 */
	public String getVehicleNoSuggested() {
		return vehicleNoSuggested;
	}

	/**
	 * @param vehicleNoSuggested
	 *            the vehicleNoSuggested to set
	 */
	public void setVehicleNoSuggested(String vehicleNoSuggested) {
		this.vehicleNoSuggested = vehicleNoSuggested;
	}

	/**
	 * @return the driverNameSuggested
	 */
	public String getDriverNameSuggested() {
		return driverNameSuggested;
	}

	/**
	 * @param driverNameSuggested
	 *            the driverNameSuggested to set
	 */
	public void setDriverNameSuggested(String driverNameSuggested) {
		this.driverNameSuggested = driverNameSuggested;
	}

	/**
	 * @return the driverCodeSuggested
	 */
	public String getDriverCodeSuggested() {
		return driverCodeSuggested;
	}

	/**
	 * @param driverCodeSuggested
	 *            the driverCodeSuggested to set
	 */
	public void setDriverCodeSuggested(String driverCodeSuggested) {
		this.driverCodeSuggested = driverCodeSuggested;
	}

	/**
	 * @return the orderVehicleTime
	 */
	public Date getOrderVehicleTime() {
		return orderVehicleTime;
	}

	/**
	 * @param orderVehicleTime
	 *            the orderVehicleTime to set
	 */
	@DateFormat
	public void setOrderVehicleTime(Date orderVehicleTime) {
		this.orderVehicleTime = orderVehicleTime;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName
	 *            the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the goodsType
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * @param goodsType
	 *            the goodsType to set
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/**
	 * @return the goodsPackage
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * @param goodsPackage
	 *            the goodsPackage to set
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * @return the pickupProvinceCode
	 */
	public String getPickupProvinceCode() {
		return pickupProvinceCode;
	}

	/**
	 * @param pickupProvinceCode
	 *            the pickupProvinceCode to set
	 */
	public void setPickupProvinceCode(String pickupProvinceCode) {
		this.pickupProvinceCode = pickupProvinceCode;
	}

	/**
	 * @return the pickupCityCode
	 */
	public String getPickupCityCode() {
		return pickupCityCode;
	}

	/**
	 * @param pickupCityCode
	 *            the pickupCityCode to set
	 */
	public void setPickupCityCode(String pickupCityCode) {
		this.pickupCityCode = pickupCityCode;
	}

	/**
	 * @return the pickupCountyCode
	 */
	public String getPickupCountyCode() {
		return pickupCountyCode;
	}

	/**
	 * @param pickupCountyCode
	 *            the pickupCountyCode to set
	 */
	public void setPickupCountyCode(String pickupCountyCode) {
		this.pickupCountyCode = pickupCountyCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	/**
	 * 付款方式
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * 付款方式
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public String getSmallZoneCodeSuggested() {
		return smallZoneCodeSuggested;
	}

	public void setSmallZoneCodeSuggested(String smallZoneCodeSuggested) {
		this.smallZoneCodeSuggested = smallZoneCodeSuggested;
	}

	public String getSmallZoneNameSuggested() {
		return smallZoneNameSuggested;
	}

	public void setSmallZoneNameSuggested(String smallZoneNameSuggested) {
		this.smallZoneNameSuggested = smallZoneNameSuggested;
	}

	public String getSmallZoneCodeActual() {
		return smallZoneCodeActual;
	}

	public void setSmallZoneCodeActual(String smallZoneCodeActual) {
		this.smallZoneCodeActual = smallZoneCodeActual;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getSpecialVehicleNo() {
		return specialVehicleNo;
	}

	public void setSpecialVehicleNo(String specialVehicleNo) {
		this.specialVehicleNo = specialVehicleNo;
	}

	public String getAddressTypeCode() {
		return addressTypeCode;
	}

	public void setAddressTypeCode(String addressTypeCode) {
		this.addressTypeCode = addressTypeCode;
	}

	public String getOrderReturnReason() {
		return orderReturnReason;
	}

	public void setOrderReturnReason(String orderReturnReason) {
		this.orderReturnReason = orderReturnReason;
	}

	public String getForwardDriverCode() {
		return forwardDriverCode;
	}

	public void setForwardDriverCode(String forwardDriverCode) {
		this.forwardDriverCode = forwardDriverCode;
	}

	public String getForwardDriverName() {
		return forwardDriverName;
	}

	public void setForwardDriverName(String forwardDriverName) {
		this.forwardDriverName = forwardDriverName;
	}

	public StringBuffer getSbLog() {
		return sbLog;
	}

	/**
	 * 添加跟踪日志 appendLog: <br/>
	 * 
	 * Date:2014-7-8下午2:39:15
	 * 
	 * @author 157229-zxy
	 * @param log
	 * @since JDK 1.6
	 */
	public void appendLog(String log) {
		this.sbLog.append("=>").append(log);
	}

	/**
	 * 添加异常日志 appendException: <br/>
	 * 
	 * Date:2014-7-8下午2:39:24
	 * 
	 * @author 157229-zxy
	 * @param log
	 * @since JDK 1.6
	 */
	public void appendException(String log) {
		this.sbLog.insert(0, "【异常信息:" + log + "】");
	}

	public String getReciveLoanType() {
		return reciveLoanType;
	}

	public void setReciveLoanType(String reciveLoanType) {
		this.reciveLoanType = reciveLoanType;
	}

	public BigDecimal getReviceMoneyAmount() {
		return reviceMoneyAmount;
	}

	public void setReviceMoneyAmount(BigDecimal reviceMoneyAmount) {
		this.reviceMoneyAmount = reviceMoneyAmount;
	}

	public BigDecimal getInsuredAmount() {
		return insuredAmount;
	}

	public void setInsuredAmount(BigDecimal insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	public String getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getDeliveryCustomerAddressNote() {
		return deliveryCustomerAddressNote;
	}

	public void setDeliveryCustomerAddressNote(
			String deliveryCustomerAddressNote) {
		this.deliveryCustomerAddressNote = deliveryCustomerAddressNote;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}

	public void setSbLog(StringBuffer sbLog) {
		this.sbLog = sbLog;
	}

	public String getConsigneeProvince() {
		return consigneeProvince;
	}

	public void setConsigneeProvince(String consigneeProvince) {
		this.consigneeProvince = consigneeProvince;
	}

	public String getConsigneeCity() {
		return consigneeCity;
	}

	public void setConsigneeCity(String consigneeCity) {
		this.consigneeCity = consigneeCity;
	}

	public String getConsigneeCounty() {
		return consigneeCounty;
	}

	public void setConsigneeCounty(String consigneeCounty) {
		this.consigneeCounty = consigneeCounty;
	}

	public String getConsigneeDetailAddress() {
		return consigneeDetailAddress;
	}

	public void setConsigneeDetailAddress(String consigneeDetailAddress) {
		this.consigneeDetailAddress = consigneeDetailAddress;
	}

	public String getIsSpecialAddress() {
		return isSpecialAddress;
	}

	public void setIsSpecialAddress(String isSpecialAddress) {
		this.isSpecialAddress = isSpecialAddress;
	}

	public String getProcurementNumber() {
		return procurementNumber;
	}

	public void setProcurementNumber(String procurementNumber) {
		this.procurementNumber = procurementNumber;
	}

	public String getFleetIsSaleDept() {
		return fleetIsSaleDept;
	}

	public void setFleetIsSaleDept(String fleetIsSaleDept) {
		this.fleetIsSaleDept = fleetIsSaleDept;
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	public String getSenderCode() {
		return senderCode;
	}

	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}

	public String getPaymentOrgCode() {
		return paymentOrgCode;
	}

	public void setPaymentOrgCode(String paymentOrgCode) {
		this.paymentOrgCode = paymentOrgCode;
	}
				
	public String getReceiverCustName() {
		return receiverCustName;
	}

	public void setReceiverCustName(String receiverCustName) {
		this.receiverCustName = receiverCustName;
	}

	public String getReceiverCustPhone() {
		return receiverCustPhone;
	}

	public void setReceiverCustPhone(String receiverCustPhone) {
		this.receiverCustPhone = receiverCustPhone;
	}

	public String getReceiverCustMobile() {
		return receiverCustMobile;
	}

	public void setReceiverCustMobile(String receiverCustMobile) {
		this.receiverCustMobile = receiverCustMobile;
	}
	
	public String getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}

	/**
	 * @return the consigneeVillageCode
	 */
	public String getConsigneeVillageCode() {
		return consigneeVillageCode;
	}

	/**
	 * @param consigneeVillageCode the consigneeVillageCode to set
	 */
	public void setConsigneeVillageCode(String consigneeVillageCode) {
		this.consigneeVillageCode = consigneeVillageCode;
		}

	/**
	 * @return the consigneeVillage
	 */
	public String getConsigneeVillage() {
		return consigneeVillage;
	}
    /**
	 * @param consigneeVillage the consigneeVillage to set
	 */
	public void setConsigneeVillage(String consigneeVillage) {
		this.consigneeVillage = consigneeVillage;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
	
	/**
	 * @return the isPicPackage
	 */
	public String getIsPicPackage() {
		return isPicPackage;
	}

	/**
	 * @param isPicPackage the isPicPackage to set
	 */
	public void setIsPicPackage(String isPicPackage) {
		this.isPicPackage = isPicPackage;
	}

	/**
	 * @return the originalNumber
	 */
	public String getOriginalNumber() {
		return originalNumber;
	}

	/**
	 * @param originalNumber the originalNumber to set
	 */
	public void setOriginalNumber(String originalNumber) {
		this.originalNumber = originalNumber;
	}

	public Date getInflowTime() {
		return inflowTime;
	}

	public void setInflowTime(Date inflowTime) {
		this.inflowTime = inflowTime;
	}

	public String getPickupManId() {
		return pickupManId;
	}

	public void setPickupManId(String pickupManId) {
		this.pickupManId = pickupManId;
	}

	public String getGgOrderType() {
		return ggOrderType;
	}

	public void setGgOrderType(String ggOrderType) {
		this.ggOrderType = ggOrderType;
	}

	public Integer getGotInTime() {
		return gotInTime;
	}

	public void setGotInTime(Integer gotInTime) {
		this.gotInTime = gotInTime;
	}

	public String getRefundMethod() {
		return refundMethod;
	}

	public void setRefundMethod(String refundMethod) {
		this.refundMethod = refundMethod;
	}

	public String getCnWd() {
		return cnWd;
	}

	public void setCnWd(String cnWd) {
		this.cnWd = cnWd;
	}
	public Date getAppointTime() {
		return appointTime;
	}

	public void setAppointTime(Date appointTime) {
		this.appointTime = appointTime;
	}

	public String getServiceFlag() {
		return serviceFlag;
	}

	public void setServiceFlag(String serviceFlag) {
		this.serviceFlag = serviceFlag;
	}

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}



	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getAppointType() {
		return appointType;
	}

	public void setAppointType(String appointType) {
		this.appointType = appointType;
	}

	public String getSysGray() {
		return sysGray;
	}

	public void setSysGray(String sysGray) {
		this.sysGray = sysGray;
	}



}
