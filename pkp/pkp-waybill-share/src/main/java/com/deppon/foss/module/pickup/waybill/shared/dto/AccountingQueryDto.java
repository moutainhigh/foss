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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/AccountingQueryDto.java
 * 
 * FILE NAME        	: AccountingQueryDto.java
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
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;

/**
 * 
 * 查询运单参数
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-12-7 下午2:47:16
 */
public class AccountingQueryDto {

	/**
	 * 用户名
	 */
	protected String name;

	/**
	 * 联系人ID
	 */
	protected String linkmanId;

	/**
	 * 运单号
	 */
	protected String waybillNum;

	/**
	 * 收货人姓名
	 */
	protected String consigneeName;

	/**
	 * 货物名称
	 */
	protected String goodsName;

	/**
	 * 开始时间
	 */
	protected Date startDate;

	/**
	 * 截止时间
	 */
	protected Date endDate;

	/**
	 * 付款方式(1、现金；2、到付；3、月结；4、银行卡；5、临时欠款)
	 */
	protected String payWay;

	/**
	 * 物流状态(1、库存中；2、在途运输；3、正常签收；4、异常签收)
	 */
	protected String status;

	/**
	 * 页号
	 */
	protected int pageNum;

	/**
	 * 每页大小
	 */
	protected int pageSize;

	/**
	 * @return the name .
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the linkmanId .
	 */
	public String getLinkmanId() {
		return linkmanId;
	}

	/**
	 * @param linkmanId
	 *            the linkmanId to set.
	 */
	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	/**
	 * @return the waybillNum .
	 */
	public String getWaybillNum() {
		return waybillNum;
	}

	/**
	 * @param waybillNum
	 *            the waybillNum to set.
	 */
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	/**
	 * @return the consigneeName .
	 */
	public String getConsigneeName() {
		return consigneeName;
	}

	/**
	 * @param consigneeName
	 *            the consigneeName to set.
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	/**
	 * @return the goodsName .
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName
	 *            the goodsName to set.
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the startDate .
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate .
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the payWay .
	 */
	public String getPayWay() {
		return payWay;
	}

	/**
	 * @param payWay
	 *            the payWay to set.
	 */
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	/**
	 * @return the status .
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the pageNum .
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum
	 *            the pageNum to set.
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the pageSize .
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}