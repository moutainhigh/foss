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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/shared/dto/AdjustPlanSearcherDto.java
 * 
 * FILE NAME        	: AdjustPlanSearcherDto.java
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
package com.deppon.foss.module.pickup.waybill.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

 
/***
 * 手动执行计划查询条件 Dto
 * @author 105089-foss-yangtong
 * @date 2012-11-28 上午11:39:56
 * @since
 * @version
 */
public class AdjustPlanSearcherDto implements Serializable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 打印流水号(标签编号)
	 */
	private String serialNo;
	/**
	 * 收货部门(出发部门)
	 */
	private String receiveOrgCode;
	/**
	 * 提货网点(到达部门)
	 */
	private String customerPickupOrgCode;
	/**
	 * 变更类型
	 */
	private String rfcType;
	/**
	 * 变更时间(开始)
	 */
	private Date operateTimeStart;
	
	/**
	 * 变更时间(结束)
	 */
	private Date operateTimeEnd;
	
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return the receiveOrgCode
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	/**
	 * @param receiveOrgCode the receiveOrgCode to set
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	/**
	 * @return the customerPickupOrgCode
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}
	/**
	 * @param customerPickupOrgCode the customerPickupOrgCode to set
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}
	/**
	 * @return the rfcType
	 */
	public String getRfcType() {
		return rfcType;
	}
	/**
	 * @param rfcType the rfcType to set
	 */
	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}
	/**
	 * @return the operateTimeStart
	 */
	public Date getOperateTimeStart() {
		return operateTimeStart;
	}
	/**
	 * @param operateTimeStart the operateTimeStart to set
	 */
	public void setOperateTimeStart(Date operateTimeStart) {
		this.operateTimeStart = operateTimeStart;
	}
	/**
	 * @return the operateTimeEnd
	 */
	public Date getOperateTimeEnd() {
		return operateTimeEnd;
	}
	/**
	 * @param operateTimeEnd the operateTimeEnd to set
	 */
	public void setOperateTimeEnd(Date operateTimeEnd) {
		this.operateTimeEnd = operateTimeEnd;
	}
	
}