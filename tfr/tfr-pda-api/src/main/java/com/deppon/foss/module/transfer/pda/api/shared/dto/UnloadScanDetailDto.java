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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/UnloadScanDetailDto.java
 *  
 *  FILE NAME          :UnloadScanDetailDto.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: UnloadScanDetailDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

/**
 * pda卸车扫描记录明细
 * @author dp-duyi
 * @date 2012-12-17 下午3:03:11
 */
public class UnloadScanDetailDto extends PDAScanDetailDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3162326047391802329L;
	/**origOrgCode*/
	private String origOrgCode;            //出发部门编号
	/**destOrgCode*/
	private String destOrgCode;            //到达部门编号
	/**billNo*/
	private String billNo;                 //单据编号
	/** zwd 200968  运单生效状态 - YES NO*/
	private String wayBillStatus; 
	/**交接件数*/
	private int handOverQty;
	/**是否分批配载*/
	private String bePartial;
	public String getBePartial() {
		return bePartial;
	}
	
	public void setBePartial(String bePartial) {
		this.bePartial = bePartial;
	}
	/**运单生效状态*/
	public String getWayBillStatus() {
		return wayBillStatus;
	}
	/**运单生效状态*/
	public void setWayBillStatus(String wayBillStatus) {
		this.wayBillStatus = wayBillStatus;
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
	 * Gets the billNo.
	 *
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}
	
	/**
	 * Sets the billNo.
	 *
	 * @param billNo the new billNo
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * Gets the 交接件数.
	 *
	 * @return the 交接件数
	 */
	public int getHandOverQty() {
		return handOverQty;
	}

	/**
	 * Sets the 交接件数.
	 *
	 * @param handOverQty the new 交接件数
	 */
	public void setHandOverQty(int handOverQty) {
		this.handOverQty = handOverQty;
	}
	
}