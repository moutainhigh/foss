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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/UnloadGoodsDetailDto.java
 *  
 *  FILE NAME          :UnloadGoodsDetailDto.java
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
 * FILE    NAME: UnloadGoodsDetailDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;


/**
 * 返回给PDA卸车任务列表Dto
 * @author dp-duyi
 * @date 2012-12-17 下午2:41:53
 */
public class UnloadGoodsDetailDto extends PDAGoodsDetailDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -249390413404671759L;
	
	/**beContraband*/
	private String beContraband;           //是否违禁品/***/
	/**origOrgCode*/
	private String origOrgCode;            //出发部门编号
	/**destOrgCode*/
	private String destOrgCode;            //到达部门编号
	/**billNo*/
	private String billNo;                 //单据编号
	//7天返货类型
	private String isSevenDaysReturn;

	/** zwd 200968  运单生效状态 - YES NO*/
	private String wayBillStatus;
	/** zwd 200968  运单生效状态 - YES NO*/
	public String getWayBillStatus() {
		return wayBillStatus;
	}
	/** zwd 200968  运单生效状态 - YES NO*/
	public void setWayBillStatus(String wayBillStatus) {
		this.wayBillStatus = wayBillStatus;
	}

	/**
	 * Gets the beContraband.
	 *
	 * @return the beContraband
	 */
	public String getBeContraband() {
		return beContraband;
	}
	
	/**
	 * Sets the beContraband.
	 *
	 * @param beContraband the new beContraband
	 */
	public void setBeContraband(String beContraband) {
		this.beContraband = beContraband;
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
	 * Gets the isSevenDaysReturn.
	 *
	 * @return the isSevenDaysReturn
	 */
	public String getIsSevenDaysReturn() {
		return isSevenDaysReturn;
	}
	
	/**
	 * Sets isSevenDaysReturn.
	 *
	 * @param isSevenDaysReturn the new isSevenDaysReturn
	 */
	public void setIsSevenDaysReturn(String isSevenDaysReturn) {
		this.isSevenDaysReturn = isSevenDaysReturn;
	}
	/**
	 * 是否转寄退回件
	 */
	private String isHandle;
	public String getIsHandle() {
		return isHandle;
	}
	public void setIsHandle(String isHandle) {
		this.isHandle = isHandle;
	}
	
}