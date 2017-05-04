/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/shared/domain/WaybillSearchCondition.java
 * 
 * FILE NAME        	: WaybillSearchCondition.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-querying
 * PACKAGE NAME: com.deppon.foss.module.base.querying.shared
 * FILE    NAME: WaybillSearchCondition.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.querying.shared.domain;

import java.io.Serializable;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * 运单查询条件.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-24 下午5:08:58
 */
public class WaybillSearchCondition implements Serializable {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 3418901059439505029L;
	// 收货部门
	/** The dept code. */
	private String deptCode;
	// 目的站
	/** The dest station. */
	private String destStation;
	
	/** vehicleAssembleNo: "车次" ,airWaybillNo": "正单号". */
	private String freigthType;
	// 班次号
	/** The freight no. */
	private String freightNo;
	// 联系方式
	/** shipperMobile: "发货人手机  shipperTel : "发货人电话" receiverMobile": "收货人手机",receiverTel : "收货人电话". */
	private String contactWay;
	// 联系号码
	/** The contact no. */
	private String contactNo;
	// 联系人类型 --shipper:发货人 --reveiver--收货人
	/** The contact man type. */
	private String contactManType;
	// 联系人名称 
	/** The contact man name. */
	private String contactManName;
	// 运输性质
	/** The trans method. */
	private String transMethod;


	// 起始单号
	/** The start waybill no. */
	private String startWaybillNo;
	// 截止单号
	/** The end waybill no. */
	private String endWaybillNo;
	// 起止时间--开始时间
	/** The start date. */
	private Date startDate;
	// 起止时间--结束时间
	/** The end date. */
	private Date endDate;
	// 单号
	/** The waybill no. */
	private String waybillNo;
	// 流水号
	/** The serialNo no. */
	private String serialNo;
	/**
	 * 电话记录单号
	 */
	private String callRecordNo;

	/**
	 * getter.
	 *
	 * @return the dept code
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * setter.
	 *
	 * @param deptCode the new dept code
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * getter.
	 *
	 * @return the dest station
	 */
	public String getDestStation() {
		return destStation;
	}

	/**
	 * setter.
	 *
	 * @param destStation the new dest station
	 */
	public void setDestStation(String destStation) {
		this.destStation = destStation;
	}

	/**
	 * getter.
	 *
	 * @return the trans method
	 */
	public String getTransMethod() {
		return transMethod;
	}

	/**
	 * setter.
	 *
	 * @param transMethod the new trans method
	 */
	public void setTransMethod(String transMethod) {
		this.transMethod = transMethod;
	}

	/**
	 * getter.
	 *
	 * @return the waybill no
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * setter.
	 *
	 * @param waybillNo the new waybill no
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * getter.
	 *
	 * @return the contact way
	 */
	public String getContactWay() {
		return contactWay;
	}

	/**
	 * setter.
	 *
	 * @param contactWay the new contact way
	 */
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	/**
	 * getter.
	 *
	 * @return the contact no
	 */
	public String getContactNo() {
		return contactNo;
	}

	/**
	 * setter.
	 *
	 * @param contactNo the new contact no
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	/**
	 * getter.
	 *
	 * @return the freigth type
	 */
	public String getFreigthType() {
		return freigthType;
	}

	/**
	 * setter.
	 *
	 * @param freigthType the new freigth type
	 */
	public void setFreigthType(String freigthType) {
		this.freigthType = freigthType;
	}

	/**
	 * getter.
	 *
	 * @return the freight no
	 */
	public String getFreightNo() {
		return freightNo;
	}

	/**
	 * setter.
	 *
	 * @param freightNo the new freight no
	 */
	public void setFreightNo(String freightNo) {
		this.freightNo = freightNo;
	}

	/**
	 * getter.
	 *
	 * @return the contact man type
	 */
	public String getContactManType() {
		return contactManType;
	}

	/**
	 * setter.
	 *
	 * @param contactManType the new contact man type
	 */
	public void setContactManType(String contactManType) {
		this.contactManType = contactManType;
	}

	/**
	 * getter.
	 *
	 * @return the contact man name
	 */
	public String getContactManName() {
		return contactManName;
	}

	/**
	 * setter.
	 *
	 * @param contactManName the new contact man name
	 */
	public void setContactManName(String contactManName) {
		this.contactManName = contactManName;
	}

	/**
	 * getter.
	 *
	 * @return the start waybill no
	 */
	public String getStartWaybillNo() {
		return startWaybillNo;
	}

	/**
	 * setter.
	 *
	 * @param startWaybillNo the new start waybill no
	 */
	public void setStartWaybillNo(String startWaybillNo) {
		this.startWaybillNo = startWaybillNo;
	}

	/**
	 * getter.
	 *
	 * @return the end waybill no
	 */
	public String getEndWaybillNo() {
		return endWaybillNo;
	}

	/**
	 * setter.
	 *
	 * @param endWaybillNo the new end waybill no
	 */
	public void setEndWaybillNo(String endWaybillNo) {
		this.endWaybillNo = endWaybillNo;
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
	 * @return  the serialNo
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

	public String getCallRecordNo() {
		return callRecordNo;
	}

	public void setCallRecordNo(String callRecordNo) {
		this.callRecordNo = callRecordNo;
	}
	

}
