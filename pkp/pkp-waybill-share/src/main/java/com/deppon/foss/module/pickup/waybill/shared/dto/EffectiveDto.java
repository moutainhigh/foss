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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/EffectiveDto.java
 * 
 * FILE NAME        	: EffectiveDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;


public class EffectiveDto  implements Serializable {
	
    private static final long serialVersionUID = 3526283885035252963L;
	/**
     * 自提承诺时间、派送承诺时间
     */
	private Date selfPickupTime;
    /**
     * 长短途
     */
    private String longOrShort;
    /**
     * 派送承诺时间
     */
    private Date deliveryDate;
	
	/**
	 * @return the selfPickupTime .
	 */
	public Date getSelfPickupTime() {
		return selfPickupTime;
	}
	
	/**
	 *@param selfPickupTime the selfPickupTime to set.
	 */
	public void setSelfPickupTime(Date selfPickupTime) {
		this.selfPickupTime = selfPickupTime;
	}
	
	/**
	 * @return the longOrShort .
	 */
	public String getLongOrShort() {
		return longOrShort;
	}
	
	/**
	 *@param longOrShort the longOrShort to set.
	 */
	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}
	
	/**
	 * @return the deliveryDate .
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	
	/**
	 *@param deliveryDate the deliveryDate to set.
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	


}