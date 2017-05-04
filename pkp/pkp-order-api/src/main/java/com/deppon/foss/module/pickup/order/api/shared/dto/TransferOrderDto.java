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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/TransferOrderDto.java
 * 
 * FILE NAME        	: TransferOrderDto.java
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

/**
 * 转货订单dto
 * 
 * @author 038590-foss-wanghui
 * @date 2012-11-29 下午1:46:04
 */
public class TransferOrderDto implements Serializable {

	private static final long serialVersionUID = -3935087119901336991L;
	/** 
	 * 约车号
	 */
	private String orderNo;
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
	 * 接货地址
	 */
	private String pickupElseAddress;
	/** 
	 * 用车部门名称
	 */
	private String customerName;
	/** 
	 * 电话
	 */
	private String tel;
	/** 
	 * 手机
	 */
	private String mobile;
	/** 
	 * 备注
	 */
	private String orderNotes;
	/** 
	 * 用车部门名称
	 */
	private String salesDepartmentName;
	/** 
	 * 用车部门编码
	 */
	private String salesDepartmentCode;
	/** 
	 * 预计用车时间
	 */
	private Date latestPickupTime;
	/** 
	 * 货物名称
	 */
	private String goodsName;
	/** 
	 * 货物类型
	 */
	private String goodsType;
	/** 
	 * 货物件数
	 */
	private Integer goodsQty;
	/** 
	 * 申请部门编码
	 */
	private String orderVehicleOrgCode;
	/** 
	 * 申请部门名称
	 */
	private String orderVehicleOrgName;
	/** 
	 * 申请时间
	 */
	private Date orderVehicleTime;
	/** 
	 * 申请人
	 */
	private String createUserName;
	/** 
	 * 申请人编码
	 */
	private String createUserCode;
	/** 
	 * 派车车队编码
	 */
	private String fleetCode;

	/**
	 * @return the fleetCode
	 */
	public String getFleetCode() {
		return fleetCode;
	}

	/**
	 * @param fleetCode the fleetCode to set
	 */
	public void setFleetCode(String fleetCode) {
		this.fleetCode = fleetCode;
	}

	/**
	 * @return the orderVehicleTime
	 */
	public Date getOrderVehicleTime() {
		return orderVehicleTime;
	}

	/**
	 * @param orderVehicleTime the orderVehicleTime to set
	 */
	public void setOrderVehicleTime(Date orderVehicleTime) {
		this.orderVehicleTime = orderVehicleTime;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return the orderVehicleOrgCode
	 */
	public String getOrderVehicleOrgCode() {
		return orderVehicleOrgCode;
	}

	/**
	 * @param orderVehicleOrgCode the orderVehicleOrgCode to set
	 */
	public void setOrderVehicleOrgCode(String orderVehicleOrgCode) {
		this.orderVehicleOrgCode = orderVehicleOrgCode;
	}

	/**
	 * @return the orderVehicleOrgName
	 */
	public String getOrderVehicleOrgName() {
		return orderVehicleOrgName;
	}

	/**
	 * @param orderVehicleOrgName the orderVehicleOrgName to set
	 */
	public void setOrderVehicleOrgName(String orderVehicleOrgName) {
		this.orderVehicleOrgName = orderVehicleOrgName;
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
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * @param vehicleType the vehicleType to set
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
	 * @return the pickupElseAddress
	 */
	public String getPickupElseAddress() {
		return pickupElseAddress;
	}

	/**
	 * @param pickupElseAddress the pickupElseAddress to set
	 */
	public void setPickupElseAddress(String pickupElseAddress) {
		this.pickupElseAddress = pickupElseAddress;
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
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	 * @return the latestPickupTime
	 */
	public Date getLatestPickupTime() {
		return latestPickupTime;
	}

	/**
	 * @param latestPickupTime the latestPickupTime to set
	 */
	public void setLatestPickupTime(Date latestPickupTime) {
		this.latestPickupTime = latestPickupTime;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName the goodsName to set
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
	 * @param goodsType the goodsType to set
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
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

}