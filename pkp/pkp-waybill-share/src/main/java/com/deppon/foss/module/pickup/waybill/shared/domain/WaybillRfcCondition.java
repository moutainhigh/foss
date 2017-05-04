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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillRfcCondition.java
 * 
 * FILE NAME        	: WaybillRfcCondition.java
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
import java.util.List;
/**
 * 更改单受理查询条件封装
* <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:suyujun,date:2012-10-10 下午6:54:38,
 * </p>
 * 
 * @author suyujun
 * @date 2012-10-10 下午6:54:38
 * @since
 * @author suyujun
 * @function  更改单受理查询条件封装
 *
 */
public class WaybillRfcCondition implements Serializable{

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -3985609624342325561L;
	/**
	 * 开始时间
	 */
	private Date startDate;
	/**
	 *结束时间00
	 */
	private Date endDate;
	/**
	 * 运单号
	 */
	private String waybillNumber;
	/**
	 * 处理状态
	 */
	private String dealStatus;
	/**
	 * 审核转态
	 */
	private String checkStatus;
	/**
	 * 部门编码
	 */
	private String deptCode;
	/**
	 * 状态列表
	 */
	private List<String> statusList;
	/**
	 * 修正状态列表
	 */
	private List<String> fixedStatusList;
	/**
	 * 审核
	 * 受理
	 */
	private String dealType;
	
	/**
	 * 审核状态全部
	 */
	private List<String> checkStatusList;
	/**
	 * 受理状态全部
	 */
	private List<String> dealStatusList;
	
	/**
	 * 是否预审核
	 */
	private boolean isCheckStatusIsPreAduit;
	/**
	 * 是否预受理
	 */
	private boolean isDealStatusIsPreAccecpt;
	/**
	 * 对应的中转外场
	 */
	private List<String> parentDeptCodes;
	
	/**
	 * 产品类型
	 */
	private List<String> productCodeList;
	
	/**
	 * 包含的产品
	 */
	private String includeProductCode;
	
	/**
	 * 排除的产品
	 */
	private String excludeProductCode;
	
	/**
	 * 是否是快递
	 */
	private int isExpress;
	
	
	
	
	public String getIncludeProductCode() {
		return includeProductCode;
	}
	public void setIncludeProductCode(String includeProductCode) {
		this.includeProductCode = includeProductCode;
	}
	public String getExcludeProductCode() {
		return excludeProductCode;
	}
	public void setExcludeProductCode(String excludeProductCode) {
		this.excludeProductCode = excludeProductCode;
	}
	public List<String> getProductCodeList() {
		return productCodeList;
	}
	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}
	public List<String> getParentDeptCodes() {
		return parentDeptCodes;
	}
	public void setParentDeptCodes(List<String> parentDeptCodes) {
		this.parentDeptCodes = parentDeptCodes;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the waybillNumber
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}
	/**
	 * @param waybillNumber the waybillNumber to set
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}
	/**
	 * @return the dealStatus
	 */
	public String getDealStatus() {
		return dealStatus;
	}
	/**
	 * @param dealStatus the dealStatus to set
	 */
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	/**
	 * @return the checkStatus
	 */
	public String getCheckStatus() {
		return checkStatus;
	}
	/**
	 * @param checkStatus the checkStatus to set
	 */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}
	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	/**
	 * @return the statusList
	 */
	public List<String> getStatusList() {
		return statusList;
	}
	/**
	 * @param statusList the statusList to set
	 */
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	/**
	 * @return the fixedStatusList
	 */
	public List<String> getFixedStatusList() {
		return fixedStatusList;
	}
	/**
	 * @param fixedStatusList the fixedStatusList to set
	 */
	public void setFixedStatusList(List<String> fixedStatusList) {
		this.fixedStatusList = fixedStatusList;
	}
	/**
	 * @return the dealType
	 */
	public String getDealType() {
		return dealType;
	}
	/**
	 * @param dealType the dealType to set
	 */
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	/**
	 * @return the checkStatusList
	 */
	public List<String> getCheckStatusList() {
		return checkStatusList;
	}
	/**
	 * @param checkStatusList the checkStatusList to set
	 */
	public void setCheckStatusList(List<String> checkStatusList) {
		this.checkStatusList = checkStatusList;
	}
	/**
	 * @return the dealStatusList
	 */
	public List<String> getDealStatusList() {
		return dealStatusList;
	}
	/**
	 * @param dealStatusList the dealStatusList to set
	 */
	public void setDealStatusList(List<String> dealStatusList) {
		this.dealStatusList = dealStatusList;
	}
	/**
	 * @return the isCheckStatusIsPreAduit
	 */
	public boolean isCheckStatusIsPreAduit() {
		return isCheckStatusIsPreAduit;
	}
	/**
	 * @param isCheckStatusIsPreAduit the isCheckStatusIsPreAduit to set
	 */
	public void setCheckStatusIsPreAduit(boolean isCheckStatusIsPreAduit) {
		this.isCheckStatusIsPreAduit = isCheckStatusIsPreAduit;
	}
	/**
	 * @return the isDealStatusIsPreAccecpt
	 */
	public boolean isDealStatusIsPreAccecpt() {
		return isDealStatusIsPreAccecpt;
	}
	/**
	 * @param isDealStatusIsPreAccecpt the isDealStatusIsPreAccecpt to set
	 */
	public void setDealStatusIsPreAccecpt(boolean isDealStatusIsPreAccecpt) {
		this.isDealStatusIsPreAccecpt = isDealStatusIsPreAccecpt;
	}
	public int getIsExpress() {
		return isExpress;
	}
	public void setIsExpress(int isExpress) {
		this.isExpress = isExpress;
	}
		
}