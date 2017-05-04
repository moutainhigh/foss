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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/ArriveSheetDto.java
 * 
 * FILE NAME        	: ArriveSheetDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;

/**
 * 到货通知的运单DTO
 * @author 243921-FOSS-zhangtingting
 * @date 2015-12-03 下午02:42:45
 */
public class ArrivalGoodsWaybillDto implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 开单件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 到达件数
	 */
	private Integer arriveGoodsQty;
	/**
	 * 卸车件数
	 */
	private Integer operationGoodsQty;
	/**
	 * 特殊增值服务类型  
	 */
	private String  specialValueAddedService;
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	/**
	 * 收货客户手机
	 */
	private String receiveCustomerMobilephone;
	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContact;
	/**
	 * 提货网点
	 */
	private String customerPickUpOrgCode;
	
	public String getReceiveMethod() {
		return receiveMethod;
	}
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}
	public String getCustomerPickUpOrgCode() {
		return customerPickUpOrgCode;
	}
	public void setCustomerPickUpOrgCode(String customerPickUpOrgCode) {
		this.customerPickUpOrgCode = customerPickUpOrgCode;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	public Integer getArriveGoodsQty() {
		return arriveGoodsQty;
	}
	public void setArriveGoodsQty(Integer arriveGoodsQty) {
		this.arriveGoodsQty = arriveGoodsQty;
	}
	public Integer getOperationGoodsQty() {
		return operationGoodsQty;
	}
	public void setOperationGoodsQty(Integer operationGoodsQty) {
		this.operationGoodsQty = operationGoodsQty;
	}
	public String getSpecialValueAddedService() {
		return specialValueAddedService;
	}
	public void setSpecialValueAddedService(String specialValueAddedService) {
		this.specialValueAddedService = specialValueAddedService;
	}
	
	
}