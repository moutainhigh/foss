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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CrmOrderInfo.java
 * 
 * FILE NAME        	: CrmOrderInfo.java
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
 * The Class CrmOrderInfo.
 */
public class CrmOrderInfo implements Serializable {
    
    /**
	 * The Constant serialVersionUID.
	 */
    private static final long serialVersionUID = 1505059773623505155L;

    /**
	 * The order status.
	 */
    private String orderStatus;
    
    /**
     * 订单状态中文名称
     */
    private String orderStatusName;
    
    /**
	 * The resource.
	 */
    private String resource;
    
    
    /**
     * 资源中文名称
     */
    private String resourceName;
    
    /**
	 * The order number.
	 */
    private String orderNumber;
    
    /**
	 * The shipper name.
	 */
    private String shipperName;
    
    /**
	 * The contact name.
	 */
    private String contactName;
    
    /**
	 * The contact mobile.
	 */
    private String contactMobile;
    
    /**
	 * The contact phone.
	 */
    private String contactPhone;
    
    /**
	 * The contact address.
	 */
    private String contactAddress;
    
    /**
	 * The goods name.
	 */
    private String goodsName;
    
    /**
	 * The transport mode.
	 */
    private String transportMode;
    
    /**
     * 运输方式中文名称
     */
    private String transportModeName;
    
    /**
     * foss 运输方式code
     */
    private String transportModeFossCode;
    
    /**
	 * The start station.
	 */
    private String startStation;
    
    /**
     * 出发部门名称
     */
    private String startStationName;
/**
 * 到达部门名称
 */
  private String   receivingToPointName;
  
  /**
   * 运单号
   */
    private String waybillNumber;
    
 // 服务类型
 	private String serviceType;
  
  
	public String getServiceType() {
		return serviceType;
	}


	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}


	public String getWaybillNumber() {
	return waybillNumber;
}


public void setWaybillNumber(String waybillNumber) {
	this.waybillNumber = waybillNumber;
}


	public String getReceivingToPointName() {
	return receivingToPointName;
}


public void setReceivingToPointName(String receivingToPointName) {
	this.receivingToPointName = receivingToPointName;
}


	/**
	 * @return the startStationName
	 */
	public String getStartStationName() {
		return startStationName;
	}


	/**
	 * @param startStationName the startStationName to set
	 */
	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}


	/**
	 * @return the orderStatus .
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	
	/**
	 *@param orderStatus the orderStatus to set.
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	
	/**
	 * @return the resource .
	 */
	public String getResource() {
		return resource;
	}

	
	/**
	 *@param resource the resource to set.
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	
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
	 * @return the shipperName .
	 */
	public String getShipperName() {
		return shipperName;
	}

	
	/**
	 *@param shipperName the shipperName to set.
	 */
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	
	/**
	 * @return the contactName .
	 */
	public String getContactName() {
		return contactName;
	}

	
	/**
	 *@param contactName the contactName to set.
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	
	/**
	 * @return the contactMobile .
	 */
	public String getContactMobile() {
		return contactMobile;
	}

	
	/**
	 *@param contactMobile the contactMobile to set.
	 */
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	
	/**
	 * @return the contactPhone .
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	
	/**
	 *@param contactPhone the contactPhone to set.
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	
	/**
	 * @return the contactAddress .
	 */
	public String getContactAddress() {
		return contactAddress;
	}

	
	/**
	 *@param contactAddress the contactAddress to set.
	 */
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	
	/**
	 * @return the goodsName .
	 */
	public String getGoodsName() {
		return goodsName;
	}

	
	/**
	 *@param goodsName the goodsName to set.
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	
	/**
	 * @return the transportMode .
	 */
	public String getTransportMode() {
		return transportMode;
	}

	
	/**
	 *@param transportMode the transportMode to set.
	 */
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}

	
	/**
	 * @return the startStation .
	 */
	public String getStartStation() {
		return startStation;
	}

	
	/**
	 *@param startStation the startStation to set.
	 */
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}


	/**
	 * @return the orderStatusName
	 */
	public String getOrderStatusName() {
		return orderStatusName;
	}


	/**
	 * @param orderStatusName the orderStatusName to set
	 */
	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}


	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}


	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	/**
	 * @return the transportModeName
	 */
	public String getTransportModeName() {
		return transportModeName;
	}


	/**
	 * @param transportModeName the transportModeName to set
	 */
	public void setTransportModeName(String transportModeName) {
		this.transportModeName = transportModeName;
	}


	/**
	 * @return the transportModeFossCode
	 */
	public String getTransportModeFossCode() {
		return transportModeFossCode;
	}


	/**
	 * @param transportModeFossCode the transportModeFossCode to set
	 */
	public void setTransportModeFossCode(String transportModeFossCode) {
		this.transportModeFossCode = transportModeFossCode;
	}

   
    
}