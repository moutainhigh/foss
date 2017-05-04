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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/dto/PdaDispatchOrderDto.java
 * 
 * FILE NAME        	: PdaDispatchOrderDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 调度订单DTO
 * @author ibm-wangfei
 * @date Dec 5, 2012 7:58:24 PM
 */
public class PdaDispatchOrderDto implements Serializable {
	private static final long serialVersionUID = -2129946208347628436L;
	/**
	 * id
	 */
	private String id;
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
	 * 营业部联系电话
	 */
	private String salesDepartmentTel;
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
	 * 开单付款方式
	 */
	private String paidMethod;
	
	/**
	 * 是否已采集
	 */
	private String isCollect;

    //增值信息
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

    /**
	 * 转发人工号
	 */
	private String forwardDriverCode;
    
	/**
	 * 转发人
	 */
	private String forwardDriverName;
	
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
	* 客户分群
	*/
	private String customerGroup;
	
	/**
	 * 收货联系人姓名
	 */
	private String receiverCustName;
	
	/**
	 * 收货人联系方式(手机/固话)
	 */
	private String receiverCustContactInfo;
	
	/**
	 * 服务类型
	 */
	private String serviceType;
	
	/**
	 * 流入时间
	 */
	private Date inflowTime;
	
	/**
	 * 抢单快递员编码
	 */
	private String pickupManId;
	
	/**
	 * 上门时效
	 */
	private Integer gotInTime;
	
	/**
	 * 裹裹订单类型
	 */
	private String ggOrderType;
	
	/**
	 * 退款方式
	 */
	private String refundMethod;
	
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the id to see
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the orderSource.
	 * 
	 * @return the orderSource
	 */
	public String getOrderSource() {
		return orderSource;
	}

	/**
	 * Sets the orderSource.
	 * 
	 * @param orderSource the orderSource to see
	 */
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	/**
	 * Gets the 订单发送状态.
	 *
	 * @return the 订单发送状态
	 */
	public String getOrderSendStatus() {
		return orderSendStatus;
	}

	/**
	 * Sets the 订单发送状态.
	 *
	 * @param orderSendStatus the new 订单发送状态
	 */
	public void setOrderSendStatus(String orderSendStatus) {
		this.orderSendStatus = orderSendStatus;
	}

	/**
	 * Gets the 提货网点.
	 *
	 * @return the 提货网点
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * Sets the 提货网点.
	 *
	 * @param customerPickupOrgCode the new 提货网点
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	 * Gets the 下单时间.
	 *
	 * @return the 下单时间
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * Sets the 下单时间.
	 *
	 * @param orderTime the new 下单时间
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * Gets the 创建人编码.
	 *
	 * @return the 创建人编码
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * Sets the 创建人编码.
	 *
	 * @param createUserCode the new 创建人编码
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * Gets the 创建人名称.
	 *
	 * @return the 创建人名称
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * Sets the 创建人名称.
	 *
	 * @param createUserName the new 创建人名称
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * Gets the 派车车队编码.
	 *
	 * @return the 派车车队编码
	 */
	public String getFleetCode() {
		return fleetCode;
	}

	/**
	 * Sets the 派车车队编码.
	 *
	 * @param fleetCode the new 派车车队编码
	 */
	public void setFleetCode(String fleetCode) {
		this.fleetCode = fleetCode;
	}

	/**
	 * Gets the 是否发送给客户.
	 *
	 * @return the 是否发送给客户
	 */
	public String getIsCustomer() {
		return isCustomer;
	}

	/**
	 * Sets the 是否发送给客户.
	 *
	 * @param isCustomer the new 是否发送给客户
	 */
	public void setIsCustomer(String isCustomer) {
		this.isCustomer = isCustomer;
	}

	/**
	 * Gets the 是否PDA发送.
	 *
	 * @return the 是否PDA发送
	 */
	public String getIsPda() {
		return isPda;
	}

	/**
	 * Sets the 是否PDA发送.
	 *
	 * @param isPda the new 是否PDA发送
	 */
	public void setIsPda(String isPda) {
		this.isPda = isPda;
	}

	/**
	 * Gets the 是否短信发送.
	 *
	 * @return the 是否短信发送
	 */
	public String getIsSms() {
		return isSms;
	}

	/**
	 * Sets the 是否短信发送.
	 *
	 * @param isSms the new 是否短信发送
	 */
	public void setIsSms(String isSms) {
		this.isSms = isSms;
	}

	/**
	 * Gets the 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the 运输性质.
	 *
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the 订单号.
	 *
	 * @return the 订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the 订单号.
	 *
	 * @param orderNo the new 订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * Gets the 约车部门名称.
	 *
	 * @return the 约车部门名称
	 */
	public String getOrderVehicleOrgName() {
		return orderVehicleOrgName;
	}

	/**
	 * Sets the 约车部门名称.
	 *
	 * @param orderVehicleOrgName the new 约车部门名称
	 */
	public void setOrderVehicleOrgName(String orderVehicleOrgName) {
		this.orderVehicleOrgName = orderVehicleOrgName;
	}

	/**
	 * Gets the 约车部门code.
	 *
	 * @return the 约车部门code
	 */
	public String getOrderVehicleOrgCode() {
		return orderVehicleOrgCode;
	}

	/**
	 * Sets the 约车部门code.
	 *
	 * @param orderVehicleOrgCode the new 约车部门code
	 */
	public void setOrderVehicleOrgCode(String orderVehicleOrgCode) {
		this.orderVehicleOrgCode = orderVehicleOrgCode;
	}

	/**
	 * Gets the 订单类型.
	 *
	 * @return the 订单类型
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * Sets the 订单类型.
	 *
	 * @param orderType the new 订单类型
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * Gets the 车型.
	 *
	 * @return the 车型
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * Sets the 车型.
	 *
	 * @param vehicleType the new 车型
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * Gets the 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * Sets the 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * Gets the 体积.
	 *
	 * @return the 体积
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * Sets the 体积.
	 *
	 * @param volume the new 体积
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * Gets the 接货省.
	 *
	 * @return the 接货省
	 */
	public String getPickupProvince() {
		return pickupProvince;
	}

	/**
	 * Sets the 接货省.
	 *
	 * @param pickupProvince the new 接货省
	 */
	public void setPickupProvince(String pickupProvince) {
		this.pickupProvince = pickupProvince;
	}

	/**
	 * Gets the 接货市.
	 *
	 * @return the 接货市
	 */
	public String getPickupCity() {
		return pickupCity;
	}

	/**
	 * Sets the 接货市.
	 *
	 * @param pickupCity the new 接货市
	 */
	public void setPickupCity(String pickupCity) {
		this.pickupCity = pickupCity;
	}

	/**
	 * Gets the 接货区.
	 *
	 * @return the 接货区
	 */
	public String getPickupCounty() {
		return pickupCounty;
	}

	/**
	 * Sets the 接货区.
	 *
	 * @param pickupCounty the new 接货区
	 */
	public void setPickupCounty(String pickupCounty) {
		this.pickupCounty = pickupCounty;
	}

	/**
	 * Gets the 接货其他地址信息.
	 *
	 * @return the 接货其他地址信息
	 */
	public String getPickupElseAddress() {
		return pickupElseAddress;
	}

	/**
	 * Sets the 接货其他地址信息.
	 *
	 * @param pickupElseAddress the new 接货其他地址信息
	 */
	public void setPickupElseAddress(String pickupElseAddress) {
		this.pickupElseAddress = pickupElseAddress;
	}

	/**
	 * Gets the 接货最早时间.
	 *
	 * @return the 接货最早时间
	 */
	public Date getEarliestPickupTime() {
		return earliestPickupTime;
	}

	/**
	 * Sets the 接货最早时间.
	 *
	 * @param earliestPickupTime the new 接货最早时间
	 */
	public void setEarliestPickupTime(Date earliestPickupTime) {
		this.earliestPickupTime = earliestPickupTime;
	}

	/**
	 * Gets the 接货最晚时间.
	 *
	 * @return the 接货最晚时间
	 */
	public Date getLatestPickupTime() {
		return latestPickupTime;
	}

	/**
	 * Sets the 接货最晚时间.
	 *
	 * @param latestPickupTime the new 接货最晚时间
	 */
	public void setLatestPickupTime(Date latestPickupTime) {
		this.latestPickupTime = latestPickupTime;
	}

	/**
	 * Gets the 客户姓名.
	 *
	 * @return the 客户姓名
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Sets the 客户姓名.
	 *
	 * @param customerName the new 客户姓名
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Gets the 电话.
	 *
	 * @return the 电话
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * Sets the 电话.
	 *
	 * @param tel the new 电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * Gets the 手机.
	 *
	 * @return the 手机
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Sets the 手机.
	 *
	 * @param mobile the new 手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * Gets the 件数.
	 *
	 * @return the 件数
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}

	/**
	 * Sets the 件数.
	 *
	 * @param goodsQty the new 件数
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * Gets the 收货地址.
	 *
	 * @return the 收货地址
	 */
	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	/**
	 * Sets the 收货地址.
	 *
	 * @param consigneeAddress the new 收货地址
	 */
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	/**
	 * Gets the 备注.
	 *
	 * @return the 备注
	 */
	public String getOrderNotes() {
		return orderNotes;
	}

	/**
	 * Sets the 备注.
	 *
	 * @param orderNotes the new 备注
	 */
	public void setOrderNotes(String orderNotes) {
		this.orderNotes = orderNotes;
	}

	/**
	 * Gets the 司机姓名.
	 *
	 * @return the 司机姓名
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * Sets the 司机姓名.
	 *
	 * @param driverName the new 司机姓名
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * Gets the 司机code.
	 *
	 * @return the 司机code
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * Sets the 司机code.
	 *
	 * @param driverCode the new 司机code
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * Gets the 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * Sets the 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * Gets the 设备号.
	 *
	 * @return the 设备号
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * Sets the 设备号.
	 *
	 * @param deviceNo the new 设备号
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	/**
	 * Gets the 营业部名称.
	 *
	 * @return the 营业部名称
	 */
	public String getSalesDepartmentName() {
		return salesDepartmentName;
	}

	/**
	 * Sets the 营业部名称.
	 *
	 * @param salesDepartmentName the new 营业部名称
	 */
	public void setSalesDepartmentName(String salesDepartmentName) {
		this.salesDepartmentName = salesDepartmentName;
	}

	/**
	 * Gets the 营业部code.
	 *
	 * @return the 营业部code
	 */
	public String getSalesDepartmentCode() {
		return salesDepartmentCode;
	}

	/**
	 * Sets the 营业部code.
	 *
	 * @param salesDepartmentCode the new 营业部code
	 */
	public void setSalesDepartmentCode(String salesDepartmentCode) {
		this.salesDepartmentCode = salesDepartmentCode;
	}

	/**
	 * Gets the 订单状态.
	 *
	 * @return the 订单状态
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * Sets the 订单状态.
	 *
	 * @param orderStatus the new 订单状态
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * Gets the 操作备注.
	 *
	 * @return the 操作备注
	 */
	public String getOperateNotes() {
		return operateNotes;
	}

	/**
	 * Sets the 操作备注.
	 *
	 * @param operateNotes the new 操作备注
	 */
	public void setOperateNotes(String operateNotes) {
		this.operateNotes = operateNotes;
	}

	/**
	 * Gets the 操作人名称.
	 *
	 * @return the 操作人名称
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Sets the 操作人名称.
	 *
	 * @param operator the new 操作人名称
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Gets the 操作人code.
	 *
	 * @return the 操作人code
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * Sets the 操作人code.
	 *
	 * @param operatorCode the new 操作人code
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * Gets the 操作时间.
	 *
	 * @return the 操作时间
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * Sets the 操作时间.
	 *
	 * @param operateTime the new 操作时间
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * Gets the 操作部门code.
	 *
	 * @return the 操作部门code
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * Sets the 操作部门code.
	 *
	 * @param operateOrgName the new 操作部门code
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * Gets the 操作部门name.
	 *
	 * @return the 操作部门name
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * Sets the 操作部门name.
	 *
	 * @param operateOrgCode the new 操作部门name
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * Gets the 定人定区id.
	 *
	 * @return the 定人定区id
	 */
	public String getPreprocessId() {
		return preprocessId;
	}

	/**
	 * Sets the 定人定区id.
	 *
	 * @param preprocessId the new 定人定区id
	 */
	public void setPreprocessId(String preprocessId) {
		this.preprocessId = preprocessId;
	}

	/**
	 * Gets the 预处理建议车辆.
	 *
	 * @return the 预处理建议车辆
	 */
	public String getVehicleNoSuggested() {
		return vehicleNoSuggested;
	}

	/**
	 * Sets the 预处理建议车辆.
	 *
	 * @param vehicleNoSuggested the new 预处理建议车辆
	 */
	public void setVehicleNoSuggested(String vehicleNoSuggested) {
		this.vehicleNoSuggested = vehicleNoSuggested;
	}

	/**
	 * Gets the 预处理建议司机姓名.
	 *
	 * @return the 预处理建议司机姓名
	 */
	public String getDriverNameSuggested() {
		return driverNameSuggested;
	}

	/**
	 * Sets the 预处理建议司机姓名.
	 *
	 * @param driverNameSuggested the new 预处理建议司机姓名
	 */
	public void setDriverNameSuggested(String driverNameSuggested) {
		this.driverNameSuggested = driverNameSuggested;
	}

	/**
	 * Gets the 预处理建议司机code.
	 *
	 * @return the 预处理建议司机code
	 */
	public String getDriverCodeSuggested() {
		return driverCodeSuggested;
	}

	/**
	 * Sets the 预处理建议司机code.
	 *
	 * @param driverCodeSuggested the new 预处理建议司机code
	 */
	public void setDriverCodeSuggested(String driverCodeSuggested) {
		this.driverCodeSuggested = driverCodeSuggested;
	}

	/**
	 * Gets the 约车时间.
	 *
	 * @return the 约车时间
	 */
	public Date getOrderVehicleTime() {
		return orderVehicleTime;
	}

	/**
	 * Sets the 约车时间.
	 *
	 * @param orderVehicleTime the new 约车时间
	 */
	@DateFormat
	public void setOrderVehicleTime(Date orderVehicleTime) {
		this.orderVehicleTime = orderVehicleTime;
	}

	/**
	 * Gets the 货物名称.
	 *
	 * @return the 货物名称
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * Sets the 货物名称.
	 *
	 * @param goodsName the new 货物名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * Gets the 货物类型.
	 *
	 * @return the 货物类型
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * Sets the 货物类型.
	 *
	 * @param goodsType the new 货物类型
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/**
	 * Gets the 包装.
	 *
	 * @return the 包装
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * Sets the 包装.
	 *
	 * @param goodsPackage the new 包装
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * Gets the 提货方式.
	 *
	 * @return the 提货方式
	 */
	public String getReceiveMethod() {
		return receiveMethod; 
	}

	/**
	 * Sets the 提货方式.
	 *
	 * @param receiveRethod the new 提货方式
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * Gets the 营业部联系电话.
	 *
	 * @return the 营业部联系电话
	 */
	public String getSalesDepartmentTel() {
		return salesDepartmentTel;
	}

	/**
	 * Sets the 营业部联系电话.
	 *
	 * @param salesDepartmentTel the new 营业部联系电话
	 */
	public void setSalesDepartmentTel(String salesDepartmentTel) {
		this.salesDepartmentTel = salesDepartmentTel;
	}

	/**
	 * Gets the serialversionuid.
	 * 
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * 开单付款方式
	 */
	public String getPaidMethod() {
		return paidMethod;
	}
	
	/**
	 * 开单付款方式
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
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

	public String getDeliveryCustomerAddressNote() {
		return deliveryCustomerAddressNote;
	}

	public void setDeliveryCustomerAddressNote(String deliveryCustomerAddressNote) {
		this.deliveryCustomerAddressNote = deliveryCustomerAddressNote;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
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

	public BigDecimal getTeanLimit() {
		return teanLimit;
	}

	public void setTeanLimit(BigDecimal teanLimit) {
		this.teanLimit = teanLimit;
	}

	public String getProcurementNumber() {
		return procurementNumber;
	}

	public void setProcurementNumber(String procurementNumber) {
		this.procurementNumber = procurementNumber;
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	public String getReceiverCustName() {
		return receiverCustName;
	}

	public void setReceiverCustName(String receiverCustName) {
		this.receiverCustName = receiverCustName;
	}

	public String getReceiverCustContactInfo() {
		return receiverCustContactInfo;
	}

	public void setReceiverCustContactInfo(String receiverCustContactInfo) {
		this.receiverCustContactInfo = receiverCustContactInfo;
	}
	public String getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
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
}
	