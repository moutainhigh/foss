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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dto/QueryPickupPointDto.java
 * 
 * FILE NAME        	: QueryPickupPointDto.java
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
package com.deppon.foss.module.pickup.common.client.dto;

import java.util.Date;

/**
 * 查询提货网点条件的DTO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-11-10 下午5:04:44,
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-11-10 下午5:04:44
 * @since
 * @version
 */
public class QueryPickupPointDto {

	// 以下两个参数 传值时候需要赋值
	// 提货方式
	private String pickUpType;

	// 运输性质 产品 区分查询自有网点 外部网点
	private String destNetType;

	// 产品编码
	private String transType;

	// 自有营业部类型
	private String salesType;

	// 以下两个参数实际情况只需要选择赋值一个
	// 1.目的站 开单界面如果根据目的站过滤提货网点 要给这个参数赋值
	private String orgSimpleName;

	// 2. 提货网点 开单界面如果根据提货网点名字过滤提货网点 要给这个参数赋值
	private String pickUpPoint;

	// ---------------------------------------------------
	// 以下变量 不需要赋值 ,传输QueryPickupPointDto 不需要赋值 ,在数据查询时候客户端判断使用
	// 定义区域表 T_BAS_DISTRICT degree
	private String cityDegree;

	// 定义区域表 是否启用
	private String cityActive;

	// 是否启用
	private String active;

	// 是否送货上门 派送
	private String pickUpDoor;

	// 是否自提
	private String pickUpSelf;
	
	// 开单营业部编码
	private String receiveOrgCode;
	
	//触发来源，区分是开单查网点，还是更改单中的返货查网点
	private String source;
	
	//当前时间
	private Date curDate;
	
	/**
     * 是否机场
     */
    private String isAirport;  
    
    /**
     * 是否可到达
     */
    private String arrive;
    
    /**
     * 是否可出发
     */	
    private String leave;
    /**
     * 提货网点
     */
    private String targetOrgCode;
    
    /**
     * 是否可家装送装
     */
    private String isHome;
    
	public String getIsHome() {
		return isHome;
	}
	public void setIsHome(String isHome) {
		this.isHome = isHome;
	}
	public String getTargetOrgCode() {
		return targetOrgCode;
	}
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}
	
	public String getIsAirport() {
		return isAirport;
	}

	
	public void setIsAirport(String isAirport) {
		this.isAirport = isAirport;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDestNetType() {
		return destNetType;
	}

	public void setDestNetType(String destNetType) {
		this.destNetType = destNetType;
	}

	public String getPickUpPoint() {
		return pickUpPoint;
	}

	public void setPickUpPoint(String pickUpPoint) {
		this.pickUpPoint = pickUpPoint;
	}

	public String getPickUpDoor() {
		return pickUpDoor;
	}

	public void setPickUpDoor(String pickUpDoor) {
		this.pickUpDoor = pickUpDoor;
	}

	public String getPickUpSelf() {
		return pickUpSelf;
	}

	public void setPickUpSelf(String pickUpSelf) {
		this.pickUpSelf = pickUpSelf;
	}

	public String getPickUpType() {
		return pickUpType;
	}

	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}

	public String getCityDegree() {
		return cityDegree;
	}

	public void setCityDegree(String cityDegree) {
		this.cityDegree = cityDegree;
	}

	public String getCityActive() {
		return cityActive;
	}

	public void setCityActive(String cityActive) {
		this.cityActive = cityActive;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	/**
	 * @return the orgSimpleName
	 */
	public String getOrgSimpleName() {
		return orgSimpleName;
	}

	/**
	 * @param orgSimpleName
	 *            the orgSimpleName to set
	 */
	public void setOrgSimpleName(String orgSimpleName) {
		this.orgSimpleName = orgSimpleName;
	}

	/**
	  * @return  the receiveOrgCode
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
	  * @return  the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}


	/**
	  * @return  the curDate
	 */
	public Date getCurDate() {
		return curDate;
	}


	/**
	 * @param curDate the curDate to set
	 */
	public void setCurDate(Date curDate) {
		this.curDate = curDate;
	}


	/**
	 * @return the arrive
	 */
	public String getArrive() {
		return arrive;
	}


	/**
	 * @param arrive the arrive to set
	 */
	public void setArrive(String arrive) {
		this.arrive = arrive;
	}


	
	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}
}