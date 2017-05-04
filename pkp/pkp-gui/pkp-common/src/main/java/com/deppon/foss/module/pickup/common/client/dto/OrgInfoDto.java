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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dto/OrgInfoDto.java
 * 
 * FILE NAME        	: OrgInfoDto.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;

/**
 * 走货路径 查询中文走货路径和最终配载部门 信息用到的 DTO
 * 
 * @author foss-jiangfei
 * @date 2012-11-21 下午2:15:21
 */

public class OrgInfoDto {

	// 出发配载部门
	private String firstLoadOrgCode;
	// 出发配载部门 名称
	private String firstLoadOrgName;
	// 出发营业部为驻地部门所属外场
	private String departureTransCenter;

	// 最终配载部门 编码
	private String lastLoadOrgCode;
	// 最终配载部门 名称
	private String lastLoadOrgName;
	// 走货路径完整中文名称
	private String routeLineName;

	// 走货路径完整编码(code连接串)
	private String routeLineCode;
	// 走货路径实体
	private FreightRouteEntity freightRoute;

	// 最终外场编码
	private String lastOutLoadOrgCode;

	// 走货路径是否区分AB货
//	private boolean goodsTypeIsAB;
	
	//集中接货开单组所在外场的驻地营业部编码
	private String pickupCentralizedDeptCode; 
	/**
	 * @return the lastOutLoadOrgCode
	 */
	public String getLastOutLoadOrgCode() {
		return lastOutLoadOrgCode;
	}

	/**
	 * @param lastOutLoadOrgCode
	 *            the lastOutLoadOrgCode to set
	 */
	public void setLastOutLoadOrgCode(String lastOutLoadOrgCode) {
		this.lastOutLoadOrgCode = lastOutLoadOrgCode;
	}

	public FreightRouteEntity getFreightRoute() {
		return freightRoute;
	}

	public void setFreightRoute(FreightRouteEntity freightRoute) {
		this.freightRoute = freightRoute;
	}

	public String getFirstLoadOrgCode() {
		return firstLoadOrgCode;
	}

	public void setFirstLoadOrgCode(String firstLoadOrgCode) {
		this.firstLoadOrgCode = firstLoadOrgCode;
	}

	public String getFirstLoadOrgName() {
		return firstLoadOrgName;
	}

	public void setFirstLoadOrgName(String firstLoadOrgName) {
		this.firstLoadOrgName = firstLoadOrgName;
	}

	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	public String getLastLoadOrgName() {
		return lastLoadOrgName;
	}

	public void setLastLoadOrgName(String lastLoadOrgName) {
		this.lastLoadOrgName = lastLoadOrgName;
	}

	public String getRouteLineName() {
		return routeLineName;
	}

	public void setRouteLineName(String routeLineName) {
		this.routeLineName = routeLineName;
	}

	public String getRouteLineCode() {
		return routeLineCode;
	}

	public void setRouteLineCode(String routeLineCode) {
		this.routeLineCode = routeLineCode;
	}

	/**
	 * @return the departureTransCenter
	 */
	public String getDepartureTransCenter() {
		return departureTransCenter;
	}

	/**
	 * @param departureTransCenter
	 *            the departureTransCenter to set
	 */
	public void setDepartureTransCenter(String departureTransCenter) {
		this.departureTransCenter = departureTransCenter;
	}

	/**
	  * @return  the goodsTypeIsAB
	 */
//	public boolean getGoodsTypeIsAB() {
//		return goodsTypeIsAB;
//	}

	/**
	 * @param goodsTypeIsAB the goodsTypeIsAB to set
	 */
//	public void setGoodsTypeIsAB(boolean goodsTypeIsAB) {
//		this.goodsTypeIsAB = goodsTypeIsAB;
//	}

	/**
	 * @return the pickupCentralizedDeptCode
	 */
	public String getPickupCentralizedDeptCode() {
		return pickupCentralizedDeptCode;
	}

	/**
	 * @param pickupCentralizedDeptCode the pickupCentralizedDeptCode to set
	 */
	public void setPickupCentralizedDeptCode(String pickupCentralizedDeptCode) {
		this.pickupCentralizedDeptCode = pickupCentralizedDeptCode;
	}
}