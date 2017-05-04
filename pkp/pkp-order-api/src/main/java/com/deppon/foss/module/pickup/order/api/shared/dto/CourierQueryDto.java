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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/OwnTruckSignOrSchedulingDto.java
 * 
 * FILE NAME        	: OwnTruckSignOrSchedulingDto.java
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
import java.util.List;


public class CourierQueryDto implements Serializable {
	/**
	 * Serializable ID
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * 签到快递员编号
	 */
	private String courierCode;
	
	/** 
	 * 所属收派大区集合Codes
	 */
	private List<String> bigZoneCodes;
	
	/** 
	 * 所属收派小区集合Codes
	 */
	private List<String> smallZoneCodes;
	
	/**
	 * 登录人所属快递大区下的所有行政区域（区县一级单位）
	 */
	private List<String> expressOrderCountyCodes;
	/**
	 * 登录人查询录入的行政区域（区县一级单位，必须在所属对应的快递大区下）
	 */
	private List<String> expressOrderNewCountyCodes;
	/**
	 * 查询起始时间
	 */
	private Date beginTime;
	/**
	 * 查询结束时间
	 */
	private Date endTime;
	/**
	 * 快递运输性质集合
	 */
	private List<String> productCodes;
	
	/**
	 * 日期版本号
	 */
	private String recieveOrderStatus; //14.10.8接收状态
	
	
	public String getRecieveOrderStatus() {
		return recieveOrderStatus;
	}
	public void setRecieveOrderStatus(String recieveOrderStatus) {
		this.recieveOrderStatus = recieveOrderStatus;
	}
	private String dateVersion;
	private List<String> partCodes;//点部 9.12
	
	public List<String> getPartCodes() {
		return partCodes;
	}
	public void setPartCodes(List<String> partCodes) {
		this.partCodes = partCodes;
	}
	public String getCourierCode() {
		return courierCode;
	}
	public void setCourierCode(String courierCode) {
		this.courierCode = courierCode;
	}
	public List<String> getBigZoneCodes() {
		return bigZoneCodes;
	}
	public void setBigZoneCodes(List<String> bigZoneCodes) {
		this.bigZoneCodes = bigZoneCodes;
	}
	public List<String> getSmallZoneCodes() {
		return smallZoneCodes;
	}
	public void setSmallZoneCodes(List<String> smallZoneCodes) {
		this.smallZoneCodes = smallZoneCodes;
	}
	public List<String> getExpressOrderCountyCodes() {
		return expressOrderCountyCodes;
	}
	public void setExpressOrderCountyCodes(List<String> expressOrderCountyCodes) {
		this.expressOrderCountyCodes = expressOrderCountyCodes;
	}
	public List<String> getExpressOrderNewCountyCodes() {
		return expressOrderNewCountyCodes;
	}
	public void setExpressOrderNewCountyCodes(
			List<String> expressOrderNewCountyCodes) {
		this.expressOrderNewCountyCodes = expressOrderNewCountyCodes;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getDateVersion() {
		return dateVersion;
	}
	public void setDateVersion(String dateVersion) {
		this.dateVersion = dateVersion;
	}
	/**
	 * @return the productCodes
	 */
	public List<String> getProductCodes() {
		return productCodes;
	}
	/**
	 * @param productCodes the productCodes to set
	 */
	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}
	
}