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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CrmWaybillServiceDto.java
 * 
 * FILE NAME        	: CrmWaybillServiceDto.java
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
package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * CRM参数VO
 * @创建时间 2014-4-12 下午7:07:28   
 * @创建人： WangQianJin
 */
public class CrmParamVo implements Serializable {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 5277884441841343541L;
	
	//发货客户编码
	private String deliveryCustomerCode;
	//开始日期
	private Date startDate;
	//结束日期
	private Date endDate;
	//每页显示的条数
	private int pageSize;
	//当前页
	private int currentPage;
	//折扣类型
	private String discountType;
	//是否有效
	private String active;
	//作废状态
	private String obsolete;
	//中止状态
	private String aborted;	
	
	public String getObsolete() {
		return obsolete;
	}
	public void setObsolete(String obsolete) {
		this.obsolete = obsolete;
	}
	public String getAborted() {
		return aborted;
	}
	public void setAborted(String aborted) {
		this.aborted = aborted;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
}