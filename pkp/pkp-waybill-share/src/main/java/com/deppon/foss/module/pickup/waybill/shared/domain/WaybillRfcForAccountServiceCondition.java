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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillRfcForAccountServiceCondition.java
 * 
 * FILE NAME        	: WaybillRfcForAccountServiceCondition.java
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
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 官网更改单查询条件封装
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-gengzhe,date:2013-1-23 下午3:36:36,
 * </p>
 * 
 * @author foss-gengzhe
 * @date 2013-1-23 下午3:36:36
 * @since
 * @version
 */
public class WaybillRfcForAccountServiceCondition implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6173721575219559860L;

	/**
	 * 更改单记录ID
	 */
	private String changeBillId;

	/**
	 * 运单号
	 * 
	 */
	private String waybillNumber;

	/**
	 * 用户名
	 */
	private String applyPerson;

	/**
	 * 申请开始时间
	 */
	private Date startDate;

	/**
	 * 申请结束时间
	 */
	private Date endDate;

	/**
	 * 当前页码
	 */
	private Integer currentPage;

	/**
	 * 每页大小
	 */
	private Integer pageSize;

	/**
	 * 处理状态
	 */
	private String active;
	/**
	 * 处理时间开始
	 */
	private Date startProcessTime;
	/**
	 * 处理时间结束
	 */
	private Date endProcessTime;
	
	/**
	 * 部门标杆编码
	 */
	private String unifieldCode;
	/**
	 * 收货部门
	 */
	private String receiveOrgCode;
	/**
	 * 开单部门
	 */
	private String createOrgCode;

	/**
	 * 获取 更改单记录ID.
	 *
	 * @return the 更改单记录ID
	 */
	public String getChangeBillId() {
		return changeBillId;
	}

	/**
	 * 设置 更改单记录ID.
	 *
	 * @param changeBillId the new 更改单记录ID
	 */
	public void setChangeBillId(String changeBillId) {
		this.changeBillId = changeBillId;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param waybillNumber the new 运单号
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	/**
	 * 获取 用户名.
	 *
	 * @return the 用户名
	 */
	public String getApplyPerson() {
		return applyPerson;
	}

	/**
	 * 设置 用户名.
	 *
	 * @param applyPerson the new 用户名
	 */
	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}

	/**
	 * 获取 申请开始时间.
	 *
	 * @return the 申请开始时间
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * 设置 申请开始时间.
	 *
	 * @param startDate the new 申请开始时间
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 获取 申请结束时间.
	 *
	 * @return the 申请结束时间
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置 申请结束时间.
	 *
	 * @param endDate the new 申请结束时间
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取 当前页码.
	 *
	 * @return the 当前页码
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}

	/**
	 * 设置 当前页码.
	 *
	 * @param currentPage the new 当前页码
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 获取 每页大小.
	 *
	 * @return the 每页大小
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * 设置 每页大小.
	 *
	 * @param pageSize the new 每页大小
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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
	 * 获取 处理状态.
	 *
	 * @return the 处理状态
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 处理状态.
	 *
	 * @param active the new 处理状态
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 获取 处理时间开始.
	 *
	 * @return the 处理时间开始
	 */
	public Date getStartProcessTime() {
		return startProcessTime;
	}

	/**
	 * 设置 处理时间开始.
	 *
	 * @param startProcessTime the new 处理时间开始
	 */
	public void setStartProcessTime(Date startProcessTime) {
		this.startProcessTime = startProcessTime;
	}

	/**
	 * 获取 处理时间结束.
	 *
	 * @return the 处理时间结束
	 */
	public Date getEndProcessTime() {
		return endProcessTime;
	}

	/**
	 * 设置 处理时间结束.
	 *
	 * @param endProcessTime the new 处理时间结束
	 */
	public void setEndProcessTime(Date endProcessTime) {
		this.endProcessTime = endProcessTime;
	}

	/**
	 * 部门标杆编码
	 * @return the unifieldCode
	 */
	public String getUnifieldCode() {
		return unifieldCode;
	}

	/**
	 * 部门标杆编码
	 * @param unifieldCode the unifieldCode to set
	 */
	public void setUnifieldCode(String unifieldCode) {
		this.unifieldCode = unifieldCode;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	

}