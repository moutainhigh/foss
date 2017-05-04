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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/dto/PdaOrderDto.java
 * 
 * FILE NAME        	: PdaOrderDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 调度订单DTO
 * @author ibm-wangfei
 * @date Dec 5, 2012 7:58:24 PM
 */
public class PdaOrderDto implements Serializable {
	private static final long serialVersionUID = 4843013229100678687L;
	/**
	 * 订单id
	 */
	private String id;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 订单类型 -- 接货or转货
	 */
	private String orderType;
	/**
	 * 司机编码
	 */
	private String driverCode;
	/**
	 * 更新时间
	 */
	private Date updateDateTime;
	/**
	 * 订单状态（已读、接收、退回）
	 */
	private String orderStatus;
	/**
	 * 退回原因
	 */
	private String returnReason;
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 转发人工号
	 */
	private String forwardDriverCode;
	
	/**
	 * 转发人姓名
	 */
	private String forwardDriverName;
	
	/**
	 * 操作状态(1、退回：ORDER_RETURN  2、转发：ORDER_FORWARD)
	 */
	private String optState;

	/**
	 * 接货最早时间
	 */
	private Date earliestPickupTime;
	/**
	 * 接货最晚时间
	 */
	private Date latestPickupTime;
	/**
	 * 快递员电话
	 */
	private String expressEmpTel;
	
	/**
	 * Gets the 快递员电话.
	 *
	 * @return the 快递员电话
	 */
	public String getExpressEmpTel() {
		return expressEmpTel;
	}
	/**
	 * Sets the 快递员电话.
	 *
	 * @param expressEmpTel the new 快递员电话
	 */
	public void setExpressEmpTel(String expressEmpTel) {
		this.expressEmpTel = expressEmpTel;
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
	 * Gets the 订单状态（已读、接收、退回）.
	 *
	 * @return the 订单状态（已读、接收、退回）
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * Sets the 订单状态（已读、接收、退回）.
	 *
	 * @param orderStatus the new 订单状态（已读、接收、退回）
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * Gets the 退回原因.
	 *
	 * @return the 退回原因
	 */
	public String getReturnReason() {
		return returnReason;
	}

	/**
	 * Sets the 退回原因.
	 *
	 * @param returnReason the new 退回原因
	 */
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	/**
	 * Gets the 备注.
	 *
	 * @return the 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Sets the 备注.
	 *
	 * @param remark the new 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the 订单类型 -- 接货or转货.
	 *
	 * @return the 订单类型 -- 接货or转货
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * Sets the 订单类型 -- 接货or转货.
	 *
	 * @param orderType the new 订单类型 -- 接货or转货
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * Gets the 司机编码.
	 *
	 * @return the 司机编码
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * Sets the 司机编码.
	 *
	 * @param driverCode the new 司机编码
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * Gets the 更新时间.
	 *
	 * @return the 更新时间
	 */
	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	/**
	 * Sets the 更新时间.
	 *
	 * @param updateDateTime the new 更新时间
	 */
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getOptState() {
		return optState;
	}

	public void setOptState(String optState) {
		this.optState = optState;
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
	
}