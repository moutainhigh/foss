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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/HandOverAndUnloadDto.java
 *  
 *  FILE NAME          :HandOverAndUnloadDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.dto
 * FILE    NAME: HandOverAndUnloadDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 接送货接口返回值：运单交接、卸车情况
 * @author dp-duyi
 * @date 2012-11-26 上午10:35:44
 */
public class HandOverAndUnloadDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1280925428869145539L;
	/**handOverNo*/
	private String handOverNo;
	/**wayBillNo*/
	private String wayBillNo;
	/**origOrgCode*/
	private String origOrgCode;
	/**destOrgCode*/
	private String destOrgCode;
	/**destOrgName*/
	private String destOrgName;
	/**origOrgName*/
	private String origOrgName;
	/**handOverQty*/
	private int handOverQty;
	/**unloadQty*/
	private int unloadQty;
	/**serialNos*/
	private List<String> serialNos;
	
	/**
	 * Gets the handOverNo.
	 *
	 * @return the handOverNo
	 */
	public String getHandOverNo() {
		return handOverNo;
	}
	
	/**
	 * Sets the handOverNo.
	 *
	 * @param handOverNo the new handOverNo
	 */
	public void setHandOverNo(String handOverNo) {
		this.handOverNo = handOverNo;
	}
	
	/**
	 * Gets the wayBillNo.
	 *
	 * @return the wayBillNo
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}
	
	/**
	 * Sets the wayBillNo.
	 *
	 * @param wayBillNo the new wayBillNo
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	
	/**
	 * Gets the origOrgCode.
	 *
	 * @return the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	
	/**
	 * Sets the origOrgCode.
	 *
	 * @param origOrgCode the new origOrgCode
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * Gets the destOrgCode.
	 *
	 * @return the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	
	/**
	 * Sets the destOrgCode.
	 *
	 * @param destOrgCode the new destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * Gets the handOverQty.
	 *
	 * @return the handOverQty
	 */
	public int getHandOverQty() {
		return handOverQty;
	}
	
	/**
	 * Sets the handOverQty.
	 *
	 * @param handOverQty the new handOverQty
	 */
	public void setHandOverQty(int handOverQty) {
		this.handOverQty = handOverQty;
	}
	
	/**
	 * Gets the unloadQty.
	 *
	 * @return the unloadQty
	 */
	public int getUnloadQty() {
		return unloadQty;
	}
	
	/**
	 * Sets the unloadQty.
	 *
	 * @param unloadQty the new unloadQty
	 */
	public void setUnloadQty(int unloadQty) {
		this.unloadQty = unloadQty;
	}
	
	/**
	 * Gets the serialNos.
	 *
	 * @return the serialNos
	 */
	public List<String> getSerialNos() {
		return serialNos;
	}
	
	/**
	 * Sets the serialNos.
	 *
	 * @param serialNos the new serialNos
	 */
	public void setSerialNos(List<String> serialNos) {
		this.serialNos = serialNos;
	}
	
	/**
	 * Gets the destOrgName.
	 *
	 * @return the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	
	/**
	 * Sets the destOrgName.
	 *
	 * @param destOrgName the new destOrgName
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	
	/**
	 * Gets the origOrgName.
	 *
	 * @return the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	
	/**
	 * Sets the origOrgName.
	 *
	 * @param origOrgName the new origOrgName
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	
	@Override
	public String toString() {
		return "HandOverAndUnloadDto [handOverNo=" + handOverNo
				+ ", wayBillNo=" + wayBillNo + ", origOrgCode=" + origOrgCode
				+ ", destOrgCode=" + destOrgCode + ", destOrgName="
				+ destOrgName + ", origOrgName=" + origOrgName
				+ ", handOverQty=" + handOverQty + ", unloadQty=" + unloadQty
				+ ", serialNos=" + serialNos + "]";
	}
}