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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/LoadScanDetailDto.java
 *  
 *  FILE NAME          :LoadScanDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.server.service
 * FILE    NAME: PDALoadScanDetail.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;


/**
 * pda装车扫描记录明细
 * @author dp-duyi
 * @date 2012-12-6 下午2:31:43
 */
public class LoadScanDetailDto extends PDAScanDetailDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7701873652579759939L;
	
	/**是否合车*/
	private String beJoinCar;
	/**库存件数*/
	private int stockQty;           
	/**收货部门名称*/
    private String receiveOrgName;	
    /**到达部门名称*/
    private String reachOrgName;	
    /**备注*/
    private String notes;
    /**PDA扫描记录*/
    private String pdaScanId;
    
	/**
	 * @return the pdaScanId
	 */
	public String getPdaScanId() {
		return pdaScanId;
	}

	/**
	 * @param pdaScanId the pdaScanId to set
	 */
	public void setPdaScanId(String pdaScanId) {
		this.pdaScanId = pdaScanId;
	}

	/**
	 * Gets the 是否合车.
	 *
	 * @return the 是否合车
	 */
	public String getBeJoinCar() {
		return beJoinCar;
	}
	
	/**
	 * Sets the 是否合车.
	 *
	 * @param beJoinCar the new 是否合车
	 */
	public void setBeJoinCar(String beJoinCar) {
		this.beJoinCar = beJoinCar;
	}
	
	/**
	 * Gets the 库存件数.
	 *
	 * @return the 库存件数
	 */
	public int getStockQty() {
		return stockQty;
	}
	
	/**
	 * Sets the 库存件数.
	 *
	 * @param stockQty the new 库存件数
	 */
	public void setStockQty(int stockQty) {
		this.stockQty = stockQty;
	}
	
	/**
	 * Gets the 收货部门名称.
	 *
	 * @return the 收货部门名称
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	
	/**
	 * Sets the 收货部门名称.
	 *
	 * @param receiveOrgName the new 收货部门名称
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	
	/**
	 * Gets the 到达部门名称.
	 *
	 * @return the 到达部门名称
	 */
	public String getReachOrgName() {
		return reachOrgName;
	}
	
	/**
	 * Sets the 到达部门名称.
	 *
	 * @param reachOrgName the new 到达部门名称
	 */
	public void setReachOrgName(String reachOrgName) {
		this.reachOrgName = reachOrgName;
	}
	
	/**
	 * Gets the 备注.
	 *
	 * @return the 备注
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * Sets the 备注.
	 *
	 * @param notes the new 备注
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "LoadScanDetailDto [beJoinCar=" + beJoinCar + ", stockQty=" + stockQty + ", receiveOrgName="
				+ receiveOrgName + ", reachOrgName=" + reachOrgName + ", notes=" + notes + ", getTaskNo()="
				+ getTaskNo() + ", getWayBillNo()=" + getWayBillNo() + ", getSerialNo()=" + getSerialNo()
				+ ", getType()=" + getType() + ", getWeight()=" + getWeight() + ", getVolume()=" + getVolume()
				+ ", getGoodsName()=" + getGoodsName() + ", getDeviceNo()=" + getDeviceNo() + ", getScanState()="
				+ getScanState() + ", getScanTime()=" + getScanTime() + ", getTransportType()=" + getTransportType()
				+ ", getPack()=" + getPack() + ", getReachOrgCode()=" + getReachOrgCode() + ", getIsPackageScan()="
				+ getIsPackageScan() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}