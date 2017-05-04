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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/OrderHandleDto.java
 * 
 * FILE NAME        	: OrderHandleDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单处理Dto
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-30 上午9:02:28
 */
public class OrderHandleDto implements Serializable {

	private static final long serialVersionUID = 4975112945013819417L;
	/** 
	 * 订单ID
	 */
	private String id;
	/** 
	 * 订单号
	 */
	private String orderNo;
	
	/** 
	 * 订单号
	 */
	private String waybillNo;	
	
	/**
	 * 发货客户编码 
	 */
	private String deliveryCustomerCode;
	
	/**
	 * 发货客户手机
	 */
	private String deliveryCustomerMobile;
	
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/** 
	 * 订单类型 -- 接货or转货
	 */
	private String orderType;
	/** 
	 * 拒绝原因
	 */
	private String rejectReason;
	/** 
	 * 其他原因描述
	 */
	private String desc;
	/** 
	 * 订单待改状态
	 */
	private String orderStatus;
	/** 
	 * 订单原始状态
	 */
	private String originOrderStatus;
	/** 
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 接货最早时间
	 */
	private Date earliestPickupTime;
	/**
	 * 接货最晚时间
	 */
	private Date latestPickupTime;
	/** 
	 * 司机编码
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
	 * 订单操作备注
	 */
	private String operateNotes;
	/** 
	 * 操作人
	 */
	private String operator;
	/** 
	 * 操作人编码
	 */
	private String operatorCode;
	/** 
	 * 操作部门
	 */
	private String operateOrgName;
	/** 
	 * 操作部门编码
	 */
	private String operateOrgCode;
	/** 
	 * 操作时间
	 */
	private Date operateTime;
	/** 
	 * 是否PDA
	 */
	private String isPda;
	/** 
	 * 是否短信
	 */
	private String isSms;
	/** 
	 * 是否发送客户
	 */
	private String isCustomer;
	/** 
	 * 接货小区Code
	 */
	private String pickupRegionCode;
	/** 
	 * 接货小区名称
	 */
	private String pickupRegionName;
	/** 
	 * 重量
	 */
	private BigDecimal weight;
	/** 
	 * 体积
	 */
	private BigDecimal volume;
	/** 
	 * 件数
	 */
	private Integer goodsQty;
	/** 
	 * 司机手机
	 */
	private String driverMobile;
	/** 
	 * 客户手机
	 */
	private String customerMobile;
	/** 
	 * 接货地址
	 */
	private String pickupAddress;
	/** 
	 * 营业部名称
	 */
	private String salesDepartmentName;
	/** 
	 * 营业部编码
	 */
	private String salesDepartmentCode;
	/** 
	 * 备注
	 */
	private String orderNotes;
	/** 
	 * 接货时间
	 */
	private String pickupTime;
	
	/** 
	 * 接货时间 年-月-日 时：分
	 */
	private String owsPickupTime;
	
	/** 
	 * 客户姓名
	 */
	private String customerName;
	/** 
	 * 客户电话
	 */
	private String tel;
	/** 
	 * 订单发送状态
	 */
	private String orderSendStatus;
	/**
	 * 车型
	 */
	private String vehicleLengthName;
	/**
	 * 包装
	 */
	private String goodsPackage;
	
	/** 
	 * 商品名称
	 */
	private String goodsName;
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 实际小区code
	 */
	private String smallZoneCodeActual;
	private String smallZoneCodeSuggested;//预处理小区
	private String smallZoneNameSuggested;
	private String delGPS;//14.7.23 AUTO-195修改预处理收派小区时 是否删除GPS坐标 
	/**
	 * 小件：收入部门
	 */
 	private String receiveOrgCode;
 	private String receiveOrgName;
	private List<String> orderStatusList;
	/**
	 * 运输性质
	 */
	private String productCode;
	private String acceptStatus; //14.7.30 订单受理方式
	private String isCollect;
	/**
	 * 订单来源 
	 */
	private String orderSource;
	
	/**
	 * 出发城市 
	 **/
	private String pickupCity;
		
	public String getPickupCity() {
		return pickupCity;
	}

	public void setPickupCity(String pickupCity) {
		this.pickupCity = pickupCity;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	/**
	 * 预处理建议车辆
	 */
	private String vehicleNoSuggested; //14.8.20
	/**
	 * 预处理建议司机姓名
	 */
	private String driverNameSuggested;
	/**
	 * 预处理建议司机code
	 */
	private String driverCodeSuggested;
	
	public String getVehicleNoSuggested() {
		return vehicleNoSuggested;
	}

	public void setVehicleNoSuggested(String vehicleNoSuggested) {
		this.vehicleNoSuggested = vehicleNoSuggested;
	}

	public String getDriverNameSuggested() {
		return driverNameSuggested;
	}

	public void setDriverNameSuggested(String driverNameSuggested) {
		this.driverNameSuggested = driverNameSuggested;
	}

	public String getDriverCodeSuggested() {
		return driverCodeSuggested;
	}

	public void setDriverCodeSuggested(String driverCodeSuggested) {
		this.driverCodeSuggested = driverCodeSuggested;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public String getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	public String getDelGPS() {
		return delGPS;
	}

	public void setDelGPS(String delGPS) {
		this.delGPS = delGPS;
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

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public String getVehicleLengthName() {
		return vehicleLengthName;
	}

	public void setVehicleLengthName(String vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	}

	/**
	 * @return the salesDepartmentCode
	 */
	public String getSalesDepartmentCode() {
		return salesDepartmentCode;
	}

	/**
	 * @param salesDepartmentCode the salesDepartmentCode to set
	 */
	public void setSalesDepartmentCode(String salesDepartmentCode) {
		this.salesDepartmentCode = salesDepartmentCode;
	}

	/**
	 * @return the orderSendStatus
	 */
	public String getOrderSendStatus() {
		return orderSendStatus;
	}

	/**
	 * @param orderSendStatus the orderSendStatus to set
	 */
	public void setOrderSendStatus(String orderSendStatus) {
		this.orderSendStatus = orderSendStatus;
	}

	/**
	 * @return the pickupRegionName
	 */
	public String getPickupRegionName() {
		return pickupRegionName;
	}

	/**
	 * @param pickupRegionName the pickupRegionName to set
	 */
	public void setPickupRegionName(String pickupRegionName) {
		this.pickupRegionName = pickupRegionName;
	}

	/**
	 * @return the goodsQty
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}

	/**
	 * @param goodsQty the goodsQty to set
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
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
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the customerMobile
	 */
	public String getCustomerMobile() {
		return customerMobile;
	}

	/**
	 * @param customerMobile the customerMobile to set
	 */
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	/**
	 * @return the pickupAddress
	 */
	public String getPickupAddress() {
		return pickupAddress;
	}

	/**
	 * @param pickupAddress the pickupAddress to set
	 */
	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	/**
	 * @return the salesDepartmentName
	 */
	public String getSalesDepartmentName() {
		return salesDepartmentName;
	}

	/**
	 * @param salesDepartmentName the salesDepartmentName to set
	 */
	public void setSalesDepartmentName(String salesDepartmentName) {
		this.salesDepartmentName = salesDepartmentName;
	}

	/**
	 * @return the orderNotes
	 */
	public String getOrderNotes() {
		return orderNotes;
	}

	/**
	 * @param orderNotes the orderNotes to set
	 */
	public void setOrderNotes(String orderNotes) {
		this.orderNotes = orderNotes;
	}

	/**
	 * @return the pickupTime
	 */
	public String getPickupTime() {
		return pickupTime;
	}

	/**
	 * @param pickupTime the pickupTime to set
	 */
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the driverMobile
	 */
	public String getDriverMobile() {
		return driverMobile;
	}

	/**
	 * @param driverMobile the driverMobile to set
	 */
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
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
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * @return the pickupRegionCode
	 */
	public String getPickupRegionCode() {
		return pickupRegionCode;
	}

	/**
	 * @param pickupRegionCode the pickupRegionCode to set
	 */
	public void setPickupRegionCode(String pickupRegionCode) {
		this.pickupRegionCode = pickupRegionCode;
	}

	/**
	 * @return the isPda
	 */
	public String getIsPda() {
		return isPda;
	}

	/**
	 * @param isPda the isPda to set
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
	 * @param isSms the isSms to set
	 */
	public void setIsSms(String isSms) {
		this.isSms = isSms;
	}

	/**
	 * @return the isCustomer
	 */
	public String getIsCustomer() {
		return isCustomer;
	}

	/**
	 * @param isCustomer the isCustomer to set
	 */
	public void setIsCustomer(String isCustomer) {
		this.isCustomer = isCustomer;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the rejectReason
	 */
	public String getRejectReason() {
		return rejectReason;
	}

	/**
	 * @param rejectReason the rejectReason to set
	 */
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the originOrderStatus
	 */
	public String getOriginOrderStatus() {
		return originOrderStatus;
	}

	/**
	 * @param originOrderStatus the originOrderStatus to set
	 */
	public void setOriginOrderStatus(String originOrderStatus) {
		this.originOrderStatus = originOrderStatus;
	}

	/**
	 * @return the operateNotes
	 */
	public String getOperateNotes() {
		return operateNotes;
	}

	/**
	 * @param operateNotes the operateNotes to set
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
	 * @param operator the operator to set
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
	 * @param operatorCode the operatorCode to set
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * @return the operateOrgName
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * @param operateOrgName the operateOrgName to set
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
	 * @param operateOrgCode the operateOrgCode to set
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName the driverName to set
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
	 * @param driverCode the driverCode to set
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
	 * @param vehicleNo the vehicleNo to set
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
	 * @param deviceNo the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	@Override
	public String toString() {
		return "OrderHandleDto [orderNo=" + orderNo + ", orderStatus="
				+ orderStatus + ", driverName=" + driverName + ", driverCode="
				+ driverCode + ", vehicleNo=" + vehicleNo + ", driverMobile="
				+ driverMobile + ", customerMobile=" + customerMobile
				+ ", pickupTime=" + pickupTime + ", customerName="
				+ customerName + ", tel=" + tel + ", orderSendStatus="
				+ orderSendStatus + ", productCode=" + productCode
				+ ", acceptStatus=" + acceptStatus + ", orderSource="
				+ orderSource + ", pickupCity=" + pickupCity + "]";
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public List<String> getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List<String> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSmallZoneCodeActual() {
		return smallZoneCodeActual;
	}

	public void setSmallZoneCodeActual(String smallZoneCodeActual) {
		this.smallZoneCodeActual = smallZoneCodeActual;
	}
	
	public Date getEarliestPickupTime() {
		return earliestPickupTime;
	}

	public void setEarliestPickupTime(Date earliestPickupTime) {
		this.earliestPickupTime = earliestPickupTime;
	}

	public Date getLatestPickupTime() {
		return latestPickupTime;
	}

	public void setLatestPickupTime(Date latestPickupTime) {
		this.latestPickupTime = latestPickupTime;
	}
	
	public String getOwsPickupTime() {
		return owsPickupTime;
	}

	public void setOwsPickupTime(String owsPickupTime) {
		this.owsPickupTime = owsPickupTime;
	}

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getDeliveryCustomerMobile() {
		return deliveryCustomerMobile;
	}

	public void setDeliveryCustomerMobile(String deliveryCustomerMobile) {
		this.deliveryCustomerMobile = deliveryCustomerMobile;
	}
		
}