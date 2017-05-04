/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-arrival-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/shared/domain/TruckArrivalEntity.java
 *  
 *  FILE NAME          :TruckArrivalEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:54:24
 */
public class TodoWhenArriveTruckEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********发现时间***********/
    private Date discoverTime;             
    
    /**********流水号***********/
    private String serialNo;     
    
    /**********运单号**********/
    private String waybillNo;      
    
    /**********到达部门***********/
    private String destOrgCode;  		  
    
    /**********到达部门名称***********/
    private String destOrgName;			  
    
    /**********交接单号***********/
    private String handoverNo;			  
    
    /**********配载单号***********/
    private String vehicleassembleNo;

	public Date getDiscoverTime() {
		return discoverTime;
	}

	public void setDiscoverTime(Date discoverTime) {
		this.discoverTime = discoverTime;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public String getVehicleassembleNo() {
		return vehicleassembleNo;
	}

	public void setVehicleassembleNo(String vehicleassembleNo) {
		this.vehicleassembleNo = vehicleassembleNo;
	}  
    
   
	
	
}