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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillOfflineDto.java
 * 
 * FILE NAME        	: WaybillOfflineDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillOfflineEntity;


// TODO: Auto-generated Javadoc
/**
 * 离线运单DTO
 * @author 026123-foss-yangtong
 * @date 2012-11-2 下午7:21:33
 */
public class WaybillOfflineDto implements Serializable{

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1L;
	
    //------以下是配合查询页面,增加的属性 
    
    // 运单号/订单号
    /** The mix no. */
    private String mixNo;
    
    //开单开始时间
    /** The bill start time. */
    private Date billStartTime;
    
    //开单结束时间
    /** The bill end time. */
    private Date billEndTime;
    
    //Foss提交开始时间
    /** The create start time. */
    private Date createStartTime;
    
    //Foss提交结束时间
    /** The create end time. */
    private Date createEndTime;
    
    //待处理运单基础
    /** The waybill offline entity. */
    private WaybillOfflineEntity waybillOfflineEntity;

	/**
	 * Gets the mix no.
	 *
	 * @return the mix no
	 */
	public String getMixNo() {
		return mixNo;
	}

	/**
	 * Sets the mix no.
	 *
	 * @param mixNo the new mix no
	 */
	public void setMixNo(String mixNo) {
		this.mixNo = mixNo;
	}

	/**
	 * Gets the bill start time.
	 *
	 * @return the bill start time
	 */
	public Date getBillStartTime() {
		return billStartTime;
	}

	/**
	 * Sets the bill start time.
	 *
	 * @param billStartTime the new bill start time
	 */
	public void setBillStartTime(Date billStartTime) {
		this.billStartTime = billStartTime;
	}

	/**
	 * Gets the bill end time.
	 *
	 * @return the bill end time
	 */
	public Date getBillEndTime() {
		return billEndTime;
	}

	/**
	 * Sets the bill end time.
	 *
	 * @param billEndTime the new bill end time
	 */
	public void setBillEndTime(Date billEndTime) {
		this.billEndTime = billEndTime;
	}

	/**
	 * Gets the creates the start time.
	 *
	 * @return the creates the start time
	 */
	public Date getCreateStartTime() {
		return createStartTime;
	}

	/**
	 * Sets the creates the start time.
	 *
	 * @param createStartTime the new creates the start time
	 */
	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	/**
	 * Gets the creates the end time.
	 *
	 * @return the creates the end time
	 */
	public Date getCreateEndTime() {
		return createEndTime;
	}

	/**
	 * Sets the creates the end time.
	 *
	 * @param createEndTime the new creates the end time
	 */
	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	/**
	 * Gets the waybill offline entity.
	 *
	 * @return the waybill offline entity
	 */
	public WaybillOfflineEntity getWaybillOfflineEntity() {
		return waybillOfflineEntity;
	}

	/**
	 * Sets the waybill offline entity.
	 *
	 * @param waybillOfflineEntity the new waybill offline entity
	 */
	public void setWaybillOfflineEntity(WaybillOfflineEntity waybillOfflineEntity) {
		this.waybillOfflineEntity = waybillOfflineEntity;
	}
    
    

}