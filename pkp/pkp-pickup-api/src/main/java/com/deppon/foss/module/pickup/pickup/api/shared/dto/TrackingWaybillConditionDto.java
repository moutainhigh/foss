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
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/dto/TrackingWaybillConditionDto.java
 * 
 * FILE NAME        	: TrackingWaybillConditionDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 
 * 运单跟踪查询条件Dto
 * 
 * @author ibm-wangfei
 * @date Dec 29, 2012 1:44:31 PM
 */
public class TrackingWaybillConditionDto implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 跟踪类型
	 */
	private String trackType;

	/**
	 * 开单时间起/收货日期起
	 */
	private Date billTimeFrom;

	/**
	 * 开单时间止/收货日期止
	 */
	private Date billTimeTo;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 发货客户联系人
	 */
	private String deliveryCustomerContact;

	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContact;

	/**
	 * 运输类型
	 */
	private String transportType;

	/**
	 * 运输性质
	 */
	private String productCode;

	/**
	 * 签收情况
	 */
	private String situation;

	/**
	 * 返单类别
	 */
	private String returnBillType;
	
	/**
	 * 返单类别 -- 页面录入
	 */
	private String returnBillFlg;
	
	/**
	 * 为mybatis判断进行添加
	 */
	private Integer returnBillFlgNum;

	/**
	 * 在库天数
	 */
	private String storageDay;
	
	/**
	 * 收货部门(出发部门) or 目的站
	 */
	private String receiveOrgCode;
	
	/**
	 * 默认状态
	 */
	private String active;
	
	/**
	 * 运单类型
	 */
	private String waybillType;
	
	/**
	 * 是否未签收
	 */
	private Integer noSignFlag;
	
	/**
	 * 出发客户群
	 */
	private String sendCustomerGroup;

	public String getSendCustomerGroup() {
		return sendCustomerGroup;
	}

	public void setSendCustomerGroup(String sendCustomerGroup) {
		this.sendCustomerGroup = sendCustomerGroup;
	}

	public String getTrackType() {
		return trackType;
	}

	public void setTrackType(String trackType) {
		this.trackType = trackType;
	}

	public Date getBillTimeFrom() {
		return billTimeFrom;
	}

	@DateFormat(formate = "yyyy-MM-dd hh:mm:ss")
	public void setBillTimeFrom(Date billTimeFrom) {
		this.billTimeFrom = billTimeFrom;
	}

	public Date getBillTimeTo() {
		return billTimeTo;
	}

	@DateFormat(formate = "yyyy-MM-dd hh:mm:ss")
	public void setBillTimeTo(Date billTimeTo) {
		this.billTimeTo = billTimeTo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getReturnBillType() {
		return returnBillType;
	}

	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	public String getStorageDay() {
		return storageDay;
	}

	public void setStorageDay(String storageDay) {
		this.storageDay = storageDay;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getReturnBillFlg() {
		return returnBillFlg;
	}

	public void setReturnBillFlg(String returnBillFlg) {
		this.returnBillFlg = returnBillFlg;
	}

	public Integer getReturnBillFlgNum() {
		return returnBillFlgNum;
	}

	public void setReturnBillFlgNum(Integer returnBillFlgNum) {
		this.returnBillFlgNum = returnBillFlgNum;
	}

	public Integer getNoSignFlag() {
		return noSignFlag;
	}

	public void setNoSignFlag(Integer noSignFlag) {
		this.noSignFlag = noSignFlag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}
	
}