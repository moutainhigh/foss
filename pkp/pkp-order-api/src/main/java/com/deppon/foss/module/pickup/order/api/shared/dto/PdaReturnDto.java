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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/ServiceFleetDto.java
 * 
 * FILE NAME        	: ServiceFleetDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: PdaReturnDto 
 * @Description: PDA退回或转发DTO
 * @author YANGBIN
 * @date 2014-5-29 上午10:16:24 
 *
 */
public class PdaReturnDto implements Serializable {

	/** 
	* @Fields serialVersionUID 
	*/
	private static final long serialVersionUID = 1L;
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
	 * 司机编码
	 */
	private String driverName;
	
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
	 * 操作状态(退回/转发)
	 */
	private String optState;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

}