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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/shared/dto/AdjustPlanResultDto.java
 * 
 * FILE NAME        	: AdjustPlanResultDto.java
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
 * 手动执行计划查询结果 Dto
 * 
 * @author 105089-foss-yangtong
 * @date 2012-11-28 上午11:39:56
 * @since
 * @version
 */
public class AdjustPlanResultDto implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 5096093958301483828L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 标签编号
	 */
	private String serialNo;
	/**
	 * 收货部门(出发部门)CODE
	 */
	private String receiveOrgCode;
	/**
	 * 收货部门(出发部门)Name
	 */
	private String receiveOrgName;
	/**
	 * 提货网点(原到达部门)CODE
	 */
	private String ocustomerPickupOrgCode;
	/**
	 * 提货网点(原到达部门)Name
	 */
	private String ocustomerPickupOrgName;
	/**
	 * 提货网点(新到达部门)CODE
	 */
	private String dcustomerPickupOrgCode;
	/**
	 * 提货网点(新到达部门)Name
	 */
	private String dcustomerPickupOrgName;
	/**
	 * 更改类型
	 */
	private String rfcType;
	/**
	 * 变更受理开始时间
	 */
	private Date operateTimeStart;
	/**
	 * 变更受理结束时间
	 */
	private Date operateTimeEnd;

	/**
	 * 变更时间
	 */
	private Date operateTime;

	/**
	 * id
	 */
	private String waybillRfcId;

	/**
	 * 获取 运单号.
	 * 
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号.
	 * 
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 标签编号.
	 * 
	 * @return the 标签编号
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * 设置 标签编号.
	 * 
	 * @param serialNo the new 标签编号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 获取 收货部门(出发部门)CODE.
	 * 
	 * @return the 收货部门(出发部门)CODE
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * 设置 收货部门(出发部门)CODE.
	 * 
	 * @param receiveOrgCode the new 收货部门(出发部门)CODE
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * 获取 更改类型.
	 * 
	 * @return the 更改类型
	 */
	public String getRfcType() {
		return rfcType;
	}

	/**
	 * 设置 更改类型.
	 * 
	 * @param rfcType the new 更改类型
	 */
	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}

	/**
	 * 获取 变更受理开始时间.
	 * 
	 * @return the 变更受理开始时间
	 */
	public Date getOperateTimeStart() {
		return operateTimeStart;
	}

	/**
	 * 设置 变更受理开始时间.
	 * 
	 * @param operateTimeStart the new 变更受理开始时间
	 */
	public void setOperateTimeStart(Date operateTimeStart) {
		this.operateTimeStart = operateTimeStart;
	}

	/**
	 * 获取 变更受理结束时间.
	 * 
	 * @return the 变更受理结束时间
	 */
	public Date getOperateTimeEnd() {
		return operateTimeEnd;
	}

	/**
	 * 设置 变更受理结束时间.
	 * 
	 * @param operateTimeEnd the new 变更受理结束时间
	 */
	public void setOperateTimeEnd(Date operateTimeEnd) {
		this.operateTimeEnd = operateTimeEnd;
	}

	/**
	 * 获取 id.
	 * 
	 * @return the id
	 */
	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	/**
	 * 设置 id.
	 * 
	 * @param waybillRfcId the new id
	 */
	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}

	/**
	 * 获取 提货网点(新到达部门)CODE.
	 * 
	 * @return the 提货网点(新到达部门)CODE
	 */
	public String getDcustomerPickupOrgCode() {
		return dcustomerPickupOrgCode;
	}

	/**
	 * 设置 提货网点(新到达部门)CODE.
	 * 
	 * @param dcustomerPickupOrgCode the new 提货网点(新到达部门)CODE
	 */
	public void setDcustomerPickupOrgCode(String dcustomerPickupOrgCode) {
		this.dcustomerPickupOrgCode = dcustomerPickupOrgCode;
	}

	/**
	 * 获取 变更时间.
	 * 
	 * @return the 变更时间
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * 设置 变更时间.
	 * 
	 * @param operateTime the new 变更时间
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
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
	 * 获取 提货网点(原到达部门)CODE.
	 * 
	 * @return the 提货网点(原到达部门)CODE
	 */
	public String getOcustomerPickupOrgCode() {
		return ocustomerPickupOrgCode;
	}

	/**
	 * 设置 提货网点(原到达部门)CODE.
	 * 
	 * @param ocustomerPickupOrgCode the new 提货网点(原到达部门)CODE
	 */
	public void setOcustomerPickupOrgCode(String ocustomerPickupOrgCode) {
		this.ocustomerPickupOrgCode = ocustomerPickupOrgCode;
	}

	/**
	 * 获取 收货部门(出发部门)Name.
	 * 
	 * @return the 收货部门(出发部门)Name
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	/**
	 * 设置 收货部门(出发部门)Name.
	 * 
	 * @param receiveOrgName the new 收货部门(出发部门)Name
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	/**
	 * 获取 提货网点(原到达部门)Name.
	 * 
	 * @return the 提货网点(原到达部门)Name
	 */
	public String getOcustomerPickupOrgName() {
		return ocustomerPickupOrgName;
	}

	/**
	 * 设置 提货网点(原到达部门)Name.
	 * 
	 * @param ocustomerPickupOrgName the new 提货网点(原到达部门)Name
	 */
	public void setOcustomerPickupOrgName(String ocustomerPickupOrgName) {
		this.ocustomerPickupOrgName = ocustomerPickupOrgName;
	}

	/**
	 * 获取 提货网点(新到达部门)Name.
	 * 
	 * @return the 提货网点(新到达部门)Name
	 */
	public String getDcustomerPickupOrgName() {
		return dcustomerPickupOrgName;
	}

	/**
	 * 设置 提货网点(新到达部门)Name.
	 * 
	 * @param dcustomerPickupOrgName the new 提货网点(新到达部门)Name
	 */
	public void setDcustomerPickupOrgName(String dcustomerPickupOrgName) {
		this.dcustomerPickupOrgName = dcustomerPickupOrgName;
	}
}