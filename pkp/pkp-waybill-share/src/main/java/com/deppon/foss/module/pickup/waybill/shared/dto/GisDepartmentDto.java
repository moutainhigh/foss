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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/GisDepartmentDto.java
 * 
 * FILE NAME        	: GisDepartmentDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.dto
 * FILE    NAME: GisDepartmentDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;


/**
 * 通过电子地图匹配到达网点得出的部门相关信息
 * @author 026123-foss-lifengteng
 * @date 2013-1-29 上午8:23:10
 */
public class GisDepartmentDto implements Serializable{

	/**
	 * 序列化标识，用于前台化对象序列化传输
	 */
	private static final long serialVersionUID = 6595174122088406617L;
	
	/**
	 * 部门编码
	 */
	private String deptNo;
	
	/**
	 * 部门名称
	 */
	private String deptName;
	
	/**
	 * 部门简称
	 */
	private String simpleName;
	
	/**
	 * 部门范围编码
	 */
	private String scopeCoordinatesId;
	
	/**
	 * 部门地址
	 */
	private String deptAddress;
	
	/**
	 * 部门点坐标编码
	 */
	private String coordinatesId;
	
	/**
	 * 是否代收货款
	 */
	private Boolean isAgencydeliver;
	
	/**
	 * 是否货到付款
	 */
	private Boolean isCashOnDelivery;
	
	/**
	 * 是否返回签单
	 */
	private Boolean isReturnSignBill;
	
	/**
	 * 是否派送
	 */
	private Boolean isDelivery;
	
	/**
	 * 是否自提
	 */
	private Boolean isPickupSelf;
	
	/**
	 * 是否出发
	 */
	private Boolean isLeave; 
	
	/**
	 * 是否偏线代理
	 */
	private Boolean isAgentCollected; 
	
	/**
	 * 派送范围
	 */
	private String deliverArea; 
	
	/**
	 * 自提范围
	 */
	private String pickupArea; 
	
	/**
	 * 匹配方式
	 */
	private String matchAddress;
	
	/**
	 * 部门联系方式
	 */
	private String contactway;

	
	/**
	 * @return the deptNo .
	 */
	public String getDeptNo() {
		return deptNo;
	}

	
	/**
	 *@param deptNo the deptNo to set.
	 */
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	
	/**
	 * @return the deptName .
	 */
	public String getDeptName() {
		return deptName;
	}

	
	/**
	 *@param deptName the deptName to set.
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
	/**
	 * @return the simpleName .
	 */
	public String getSimpleName() {
		return simpleName;
	}

	
	/**
	 *@param simpleName the simpleName to set.
	 */
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	
	/**
	 * @return the scopeCoordinatesId .
	 */
	public String getScopeCoordinatesId() {
		return scopeCoordinatesId;
	}

	
	/**
	 *@param scopeCoordinatesId the scopeCoordinatesId to set.
	 */
	public void setScopeCoordinatesId(String scopeCoordinatesId) {
		this.scopeCoordinatesId = scopeCoordinatesId;
	}

	
	/**
	 * @return the deptAddress .
	 */
	public String getDeptAddress() {
		return deptAddress;
	}

	
	/**
	 *@param deptAddress the deptAddress to set.
	 */
	public void setDeptAddress(String deptAddress) {
		this.deptAddress = deptAddress;
	}

	
	/**
	 * @return the coordinatesId .
	 */
	public String getCoordinatesId() {
		return coordinatesId;
	}

	
	/**
	 *@param coordinatesId the coordinatesId to set.
	 */
	public void setCoordinatesId(String coordinatesId) {
		this.coordinatesId = coordinatesId;
	}

	
	/**
	 * @return the isAgencydeliver .
	 */
	public Boolean getIsAgencydeliver() {
		return isAgencydeliver;
	}

	
	/**
	 *@param isAgencydeliver the isAgencydeliver to set.
	 */
	public void setIsAgencydeliver(Boolean isAgencydeliver) {
		this.isAgencydeliver = isAgencydeliver;
	}

	
	/**
	 * @return the isCashOnDelivery .
	 */
	public Boolean getIsCashOnDelivery() {
		return isCashOnDelivery;
	}

	
	/**
	 *@param isCashOnDelivery the isCashOnDelivery to set.
	 */
	public void setIsCashOnDelivery(Boolean isCashOnDelivery) {
		this.isCashOnDelivery = isCashOnDelivery;
	}

	
	/**
	 * @return the isReturnSignBill .
	 */
	public Boolean getIsReturnSignBill() {
		return isReturnSignBill;
	}

	
	/**
	 *@param isReturnSignBill the isReturnSignBill to set.
	 */
	public void setIsReturnSignBill(Boolean isReturnSignBill) {
		this.isReturnSignBill = isReturnSignBill;
	}

	
	/**
	 * @return the isDelivery .
	 */
	public Boolean getIsDelivery() {
		return isDelivery;
	}

	
	/**
	 *@param isDelivery the isDelivery to set.
	 */
	public void setIsDelivery(Boolean isDelivery) {
		this.isDelivery = isDelivery;
	}

	
	/**
	 * @return the isPickupSelf .
	 */
	public Boolean getIsPickupSelf() {
		return isPickupSelf;
	}

	
	/**
	 *@param isPickupSelf the isPickupSelf to set.
	 */
	public void setIsPickupSelf(Boolean isPickupSelf) {
		this.isPickupSelf = isPickupSelf;
	}

	
	/**
	 * @return the isLeave .
	 */
	public Boolean getIsLeave() {
		return isLeave;
	}

	
	/**
	 *@param isLeave the isLeave to set.
	 */
	public void setIsLeave(Boolean isLeave) {
		this.isLeave = isLeave;
	}

	
	/**
	 * @return the isAgentCollected .
	 */
	public Boolean getIsAgentCollected() {
		return isAgentCollected;
	}

	
	/**
	 *@param isAgentCollected the isAgentCollected to set.
	 */
	public void setIsAgentCollected(Boolean isAgentCollected) {
		this.isAgentCollected = isAgentCollected;
	}

	
	/**
	 * @return the deliverArea .
	 */
	public String getDeliverArea() {
		return deliverArea;
	}

	
	/**
	 *@param deliverArea the deliverArea to set.
	 */
	public void setDeliverArea(String deliverArea) {
		this.deliverArea = deliverArea;
	}

	
	/**
	 * @return the pickupArea .
	 */
	public String getPickupArea() {
		return pickupArea;
	}

	
	/**
	 *@param pickupArea the pickupArea to set.
	 */
	public void setPickupArea(String pickupArea) {
		this.pickupArea = pickupArea;
	}

	
	/**
	 * @return the matchAddress .
	 */
	public String getMatchAddress() {
		return matchAddress;
	}

	
	/**
	 *@param matchAddress the matchAddress to set.
	 */
	public void setMatchAddress(String matchAddress) {
		this.matchAddress = matchAddress;
	}

	
	/**
	 * @return the contactway .
	 */
	public String getContactway() {
		return contactway;
	}

	
	/**
	 *@param contactway the contactway to set.
	 */
	public void setContactway(String contactway) {
		this.contactway = contactway;
	}
	
	
}