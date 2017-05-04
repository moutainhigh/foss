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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CrmModifyInfoDto.java
 * 
 * FILE NAME        	: CrmModifyInfoDto.java
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

/**
 * 
 * 订单状态更改请求实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-22 下午5:23:06,</p>
 * @author foss-sunrui
 * @date 2012-11-22 下午5:23:06
 * @since
 * @version
 */
public class CrmModifyInfoDto implements Serializable {

    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -4359975004077443380L;

    // 订单编号
    private String orderNumber;
    
    // 运单号码
    private String waybillNumber;
    
    // 操作人员
    private String oprateUserNum;
    
    // 操作人员所在部门
    private String oprateDeptCode;
    
    // 司机
    private String driverName;
    
    // 司机手机
    private String driverMobile;
    
    // 货物状态
    private String goodsStatus;
    
   //客户编码
    private String deliveryCustomerCode;
//    增加重量体积
    // 重量
    private Double weight;
    // 体积
    private Double volume;
    
    // 反馈信息
    private String backInfo;
    
    // 收入部门code
    private String earningDeptStandardCode;
    
    // 收入部门name
    private String earningDeptStandardName;
    
    //开单部门
    private String billingOrgCode;
    
    //开单部门电话
    private String billingOrgPhone;

	
	/**
	 * @return the orderNumber .
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	
	/**
	 *@param orderNumber the orderNumber to set.
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	
	/**
	 * @return the waybillNumber .
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}

	
	/**
	 *@param waybillNumber the waybillNumber to set.
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	
	/**
	 * @return the oprateUserNum .
	 */
	public String getOprateUserNum() {
		return oprateUserNum;
	}

	
	/**
	 *@param oprateUserNum the oprateUserNum to set.
	 */
	public void setOprateUserNum(String oprateUserNum) {
		this.oprateUserNum = oprateUserNum;
	}

	
	/**
	 * @return the oprateDeptCode .
	 */
	public String getOprateDeptCode() {
		return oprateDeptCode;
	}

	
	/**
	 *@param oprateDeptCode the oprateDeptCode to set.
	 */
	public void setOprateDeptCode(String oprateDeptCode) {
		this.oprateDeptCode = oprateDeptCode;
	}

	
	/**
	 * @return the driverName .
	 */
	public String getDriverName() {
		return driverName;
	}

	
	/**
	 *@param driverName the driverName to set.
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	
	/**
	 * @return the driverMobile .
	 */
	public String getDriverMobile() {
		return driverMobile;
	}

	
	/**
	 *@param driverMobile the driverMobile to set.
	 */
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	
	/**
	 * @return the goodsStatus .
	 */
	public String getGoodsStatus() {
		return goodsStatus;
	}

	
	/**
	 *@param goodsStatus the goodsStatus to set.
	 */
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	
	/**
	 * @return the backInfo .
	 */
	public String getBackInfo() {
		return backInfo;
	}

	
	/**
	 *@param backInfo the backInfo to set.
	 */
	public void setBackInfo(String backInfo) {
		this.backInfo = backInfo;
	}


	public String getEarningDeptStandardCode() {
		return earningDeptStandardCode;
	}


	public void setEarningDeptStandardCode(String earningDeptStandardCode) {
		this.earningDeptStandardCode = earningDeptStandardCode;
	}


	public String getEarningDeptStandardName() {
		return earningDeptStandardName;
	}


	public void setEarningDeptStandardName(String earningDeptStandardName) {
		this.earningDeptStandardName = earningDeptStandardName;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Double getWeight() {
		return weight;
	}


	public void setWeight(Double weight) {
		this.weight = weight;
	}


	public Double getVolume() {
		return volume;
	}


	public void setVolume(Double volume) {
		this.volume = volume;
	}


	/**
	 * @return the billingOrgCode
	 */
	public String getBillingOrgCode() {
		return billingOrgCode;
	}


	/**
	 * @param billingOrgCode the billingOrgCode to set
	 */
	public void setBillingOrgCode(String billingOrgCode) {
		this.billingOrgCode = billingOrgCode;
	}


	/**
	 * @return the billingOrgPhone
	 */
	public String getBillingOrgPhone() {
		return billingOrgPhone;
	}


	/**
	 * @param billingOrgPhone the billingOrgPhone to set
	 */
	public void setBillingOrgPhone(String billingOrgPhone) {
		this.billingOrgPhone = billingOrgPhone;
	}


	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}


	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}
	
}