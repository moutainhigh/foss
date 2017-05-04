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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/HandoverRefershDTO.java
 *  
 *  FILE NAME          :HandoverRefershDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.dto;

import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 更新运单时查询出需要调用接口的值.
 *
 * @author IBM-liubinbin
 * @date 2012-11-2 上午9:59:45
 */
public class HandoverRefershDTO extends BaseEntity{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3703272448562684594L;

	/** ********交接单***********. */
	private String handoverNo;
	
	/** ********状态***********. */
	private String status;
	
	/** ********单据类型***********. */
	private String billType;
	
	/** ********到达部门***********. */
	private String destOrgCode;
	
	/** ********交接单状态***********. */
	private int handoverStates;
	
	/** ********车牌号***********. */
	private String vehicleNo;

	/** ********出发部门***********. */
	private String origOrgCode;
	/** ********预计到达时间***********. */
	private Date planArriveTime;
	/**********是否整车************/
	private String beCarLoad;
	/**********车辆归属类型************/
	private String vehicleOwnerType;
	/*********交接单类型***********/
	private String handoverType;
	
	private String billLevel;
	/**
	 * 获取 ********交接单***********.
	 *
	 * @return the ********交接单***********
	 */
	public String getHandoverNo(){
		return handoverNo;
	}

	/**
	 * 设置 ********交接单***********.
	 *
	 * @param handoverNo the new ********交接单***********
	 */
	public void setHandoverNo(String handoverNo){
		this.handoverNo = handoverNo;
	}

	/**
	 * 获取 ********状态***********.
	 *
	 * @return the ********状态***********
	 */
	public String getStatus(){
		return status;
	}

	/**
	 * 设置 ********状态***********.
	 *
	 * @param status the new ********状态***********
	 */
	public void setStatus(String status){
		this.status = status;
	}

	/**
	 * Gets the dest org code.
	 *
	 * @return the dest org code
	 */
	public String getDestOrgCode(){
		return destOrgCode;
	}

	/**
	 * Sets the dest org code.
	 *
	 * @param destOrgCode the new dest org code
	 */
	public void setDestOrgCode(String destOrgCode){
		this.destOrgCode = destOrgCode;
	}

	/**
	 * Gets the bill type.
	 *
	 * @return the bill type
	 */
	public String getBillType(){
		return billType;
	}

	/**
	 * Sets the bill type.
	 *
	 * @param billType the new bill type
	 */
	public void setBillType(String billType){
		this.billType = billType;
	}

	/**
	 * Gets the handover states.
	 *
	 * @return the handover states
	 */
	public int getHandoverStates(){
		return handoverStates;
	}

	/**
	 * Sets the handover states.
	 *
	 * @param handoverStates the new handover states
	 */
	public void setHandoverStates(int handoverStates){
		this.handoverStates = handoverStates;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	public String getBeCarLoad() {
		return beCarLoad;
	}

	public void setBeCarLoad(String beCarLoad) {
		this.beCarLoad = beCarLoad;
	}

	public String getVehicleOwnerType() {
		return vehicleOwnerType;
	}

	public void setVehicleOwnerType(String vehicleOwnerType) {
		this.vehicleOwnerType = vehicleOwnerType;
	}

	public String getHandoverType() {
		return handoverType;
	}

	public void setHandoverType(String handoverType) {
		this.handoverType = handoverType;
	}

	public String getBillLevel() {
		return billLevel;
	}

	public void setBillLevel(String billLevel) {
		this.billLevel = billLevel;
	}

	
	
}