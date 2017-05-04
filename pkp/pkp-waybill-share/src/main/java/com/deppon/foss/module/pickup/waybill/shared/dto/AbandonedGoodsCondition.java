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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/AbandonedGoodsCondition.java
 * 
 * FILE NAME        	: AbandonedGoodsCondition.java
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

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * 弃货查询条件实体
 * </p>
 * 
 * @title AbandonedGoodsCondition.java
 * @package com.deppon.foss.module.pickup.waybill.shared.dto
 * @author suyujun
 * @version 0.1 2012-12-15
 */
public class AbandonedGoodsCondition implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -4373946475199407922L;

	/**
	 * waybillNo
	 */
	private String waybillNo;

	/**
	 * shipper
	 */
	private String shipper;

	/**
	 * exceptionType
	 */
	private String exceptionType;

	/**
	 * preTreatPerson
	 */
	private String preTreatPerson;

	/**
	 * beginDate
	 */
	private Date beginDate;

	/**
	 * endDate
	 */
	private Date endDate;
	
	/**
	 * active 是否有效
	 */
	private String active;

	/**
	 * @return waybillNo : return the property waybillNo.
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            : set the property waybillNo.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return shipper : return the property shipper.
	 */
	public String getShipper() {
		return shipper;
	}

	/**
	 * @param shipper
	 *            : set the property shipper.
	 */
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	/**
	 * @return exceptionType : return the property exceptionType.
	 */
	public String getExceptionType() {
		return exceptionType;
	}

	/**
	 * @param exceptionType
	 *            : set the property exceptionType.
	 */
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	/**
	 * @return preTreatPerson : return the property preTreatPerson.
	 */
	public String getPreTreatPerson() {
		return preTreatPerson;
	}

	/**
	 * @param preTreatPerson
	 *            : set the property preTreatPerson.
	 */
	public void setPreTreatPerson(String preTreatPerson) {
		this.preTreatPerson = preTreatPerson;
	}

	/**
	 * @return beginDate : return the property beginDate.
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate
	 *            : set the property beginDate.
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return endDate : return the property endDate.
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            : set the property endDate.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	

}