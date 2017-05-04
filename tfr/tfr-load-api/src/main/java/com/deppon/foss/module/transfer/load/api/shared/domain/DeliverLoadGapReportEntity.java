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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/DeliverLoadGapReportEntity.java
 *  
 *  FILE NAME          :DeliverLoadGapReportEntity.java
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
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.domain
 * FILE    NAME: DeliverLoadGapReportEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * DeliverLoadGapReportEntity
 * @author 042795-foss-duyi
 * @date 2012-10-26 上午11:28:59
 */
public class DeliverLoadGapReportEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3998545908571489610L;
	/**id*/
	private String id;
	/**taskNo*/
	private String taskNo;
	/**reportNo*/
	private String reportNo;
	/**taskId*/
	private String taskId;
	/**deliverBillNo*/
	private String deliverBillNo;
	/**createTime*/
	private String createTime;
	/**state*/
	private String state;
	/**beValid*/
	private String beValid;
	/**planLoadGoodsQty*/
	private int planLoadGoodsQty;
	/**actualLoadGoodsQty*/
	private int actualLoadGoodsQty;
	/**lackGoodsQty*/
	private int lackGoodsQty;
	/**vehicleNo*/
	private String vehicleNo;
	/**origOrgCode*/
	private String origOrgCode;
	/**origOrgName*/
	private String origOrgName;
	private List<String> orgCodes;
	/** 运单号*/
	private String waybillNo;
	
	public List<String> getOrgCodes() {
		return orgCodes;
	}


	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}


	public String getId() {
		return id;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the taskNo.
	 *
	 * @return the taskNo
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * Sets the taskNo.
	 *
	 * @param taskNo the new taskNo
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	/**
	 * Gets the reportNo.
	 *
	 * @return the reportNo
	 */
	public String getReportNo() {
		return reportNo;
	}
	
	/**
	 * Sets the reportNo.
	 *
	 * @param reportNo the new reportNo
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	
	/**
	 * Gets the taskId.
	 *
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}
	
	/**
	 * Sets the taskId.
	 *
	 * @param taskId the new taskId
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	/**
	 * Gets the deliverBillNo.
	 *
	 * @return the deliverBillNo
	 */
	public String getDeliverBillNo() {
		return deliverBillNo;
	}
	
	/**
	 * Sets the deliverBillNo.
	 *
	 * @param deliverBillNo the new deliverBillNo
	 */
	public void setDeliverBillNo(String deliverBillNo) {
		this.deliverBillNo = deliverBillNo;
	}
	
	/**
	 * Gets the createTime.
	 *
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}
	
	/**
	 * Sets the createTime.
	 *
	 * @param createTime the new createTime
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Gets the beValid.
	 *
	 * @return the beValid
	 */
	public String getBeValid() {
		return beValid;
	}
	
	/**
	 * Sets the beValid.
	 *
	 * @param beValid the new beValid
	 */
	public void setBeValid(String beValid) {
		this.beValid = beValid;
	}
	
	/**
	 * Gets the planLoadGoodsQty.
	 *
	 * @return the planLoadGoodsQty
	 */
	public int getPlanLoadGoodsQty() {
		return planLoadGoodsQty;
	}
	
	/**
	 * Sets the planLoadGoodsQty.
	 *
	 * @param planLoadGoodsQty the new planLoadGoodsQty
	 */
	public void setPlanLoadGoodsQty(int planLoadGoodsQty) {
		this.planLoadGoodsQty = planLoadGoodsQty;
	}
	
	/**
	 * Gets the actualLoadGoodsQty.
	 *
	 * @return the actualLoadGoodsQty
	 */
	public int getActualLoadGoodsQty() {
		return actualLoadGoodsQty;
	}
	
	/**
	 * Sets the actualLoadGoodsQty.
	 *
	 * @param actualLoadGoodsQty the new actualLoadGoodsQty
	 */
	public void setActualLoadGoodsQty(int actualLoadGoodsQty) {
		this.actualLoadGoodsQty = actualLoadGoodsQty;
	}
	
	/**
	 * Gets the vehicleNo.
	 *
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * Sets the vehicleNo.
	 *
	 * @param vehicleNo the new vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * Gets the lackGoodsQty.
	 *
	 * @return the lackGoodsQty
	 */
	public int getLackGoodsQty() {
		return lackGoodsQty;
	}
	
	/**
	 * Sets the lackGoodsQty.
	 *
	 * @param lackGoodsQty the new lackGoodsQty
	 */
	public void setLackGoodsQty(int lackGoodsQty) {
		this.lackGoodsQty = lackGoodsQty;
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
	

	/**
	 * 
	 * Gets the  运单号 
	 * @author alfred
	 * @date 2014-1-10 上午10:14:47
	 * @return
	 * @see
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 
	 * Sets the 运单号
	 * @author alfred
	 * @date 2014-1-10 上午10:14:54
	 * @param waybillNo
	 * @see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
}