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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillRfcChangeDto.java
 * 
 * FILE NAME        	: WaybillRfcChangeDto.java
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
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;

/**
 * 
 * @author suyujun
 * @function 更改单查询返回对象Dto
 * @date 2012-11-27
 * 
 */
public class WaybillRfcChangeDto implements Serializable{
	private static final long serialVersionUID = 4043153872589839545L;
	// 更改单Id
	private String changeBillId;
	//更改后WaybillId
	private String newWaybillId;
	// 单号
	private String waybillNumber;
	// 受理状态状态
	private String dealStauts;
	//审核状态
	private String checkStatus;
	// 变更属性-变更来源
	private String rfcSource;
	// 变更原因
	private String rfcReason;
	// 库存状态
	private String stockStatus;
	// 申请部门名称
	private String applyDeptName;
	// 申请部门Code
	private String applyDeptCode;
	// 申请人
	private String applyPerson;
	// 受理部门名称
	private String handleDeptName;
	// 受理部门Code
	private String handleDeptCode;
	// 受理人
	private String handlePerson;
	// 受理人
	private String handlePersonCode;
	// 申请时间
	private Date applyTime;
	// 受理时间
	private Date handleTime;
	//受理备注
	private String handleNotes;
	//审核备注
	private String checkNotes;
	//审核人
	private String checkPerson;
	//审核人编码
	private String checkPersonCode;
	//审核时间
	private Date checkDate;
	//变更类型
	private String rfcType;
	
	private List<WaybillRfcChangeDetailEntity> changeList;
	
	
	/**
	 * @return changeList : set the property changeList.
	 */
	public List<WaybillRfcChangeDetailEntity> getChangeList() {
		return changeList;
	}
	/**
	 * @param changeList : return the property changeList.
	 */
	public void setChangeList(List<WaybillRfcChangeDetailEntity> changeList) {
		this.changeList = changeList;
	}
	/**
	 * @return changeBillId : set the property changeBillId.
	 */
	public String getChangeBillId() {
		return changeBillId;
	}
	/**
	 * @param changeBillId : return the property changeBillId.
	 */
	public void setChangeBillId(String changeBillId) {
		this.changeBillId = changeBillId;
	}
	/**
	 * @return waybillNumber : set the property waybillNumber.
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}
	/**
	 * @param waybillNumber : return the property waybillNumber.
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}
	/**
	 * @return dealStauts : set the property dealStauts.
	 */
	public String getDealStauts() {
		return dealStauts;
	}
	/**
	 * @param dealStauts : return the property dealStauts.
	 */
	public void setDealStauts(String dealStauts) {
		this.dealStauts = dealStauts;
	}
	/**
	 * @return checkStatus : set the property checkStatus.
	 */
	public String getCheckStatus() {
		return checkStatus;
	}
	/**
	 * @param checkStatus : return the property checkStatus.
	 */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	/**
	 * @return rfcSource : set the property rfcSource.
	 */
	public String getRfcSource() {
		return rfcSource;
	}
	/**
	 * @param rfcSource : return the property rfcSource.
	 */
	public void setRfcSource(String rfcSource) {
		this.rfcSource = rfcSource;
	}
	/**
	 * @return rfcReason : set the property rfcReason.
	 */
	public String getRfcReason() {
		return rfcReason;
	}
	/**
	 * @param rfcReason : return the property rfcReason.
	 */
	public void setRfcReason(String rfcReason) {
		this.rfcReason = rfcReason;
	}
	/**
	 * @return stockStatus : set the property stockStatus.
	 */
	public String getStockStatus() {
		return stockStatus;
	}
	/**
	 * @param stockStatus : return the property stockStatus.
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	/**
	 * @return applyDeptName : set the property applyDeptName.
	 */
	public String getApplyDeptName() {
		return applyDeptName;
	}
	/**
	 * @param applyDeptName : return the property applyDeptName.
	 */
	public void setApplyDeptName(String applyDeptName) {
		this.applyDeptName = applyDeptName;
	}
	/**
	 * @return applyDeptCode : set the property applyDeptCode.
	 */
	public String getApplyDeptCode() {
		return applyDeptCode;
	}
	/**
	 * @param applyDeptCode : return the property applyDeptCode.
	 */
	public void setApplyDeptCode(String applyDeptCode) {
		this.applyDeptCode = applyDeptCode;
	}
	/**
	 * @return applyPerson : set the property applyPerson.
	 */
	public String getApplyPerson() {
		return applyPerson;
	}
	/**
	 * @param applyPerson : return the property applyPerson.
	 */
	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}
	/**
	 * @return handleDeptName : set the property handleDeptName.
	 */
	public String getHandleDeptName() {
		return handleDeptName;
	}
	/**
	 * @param handleDeptName : return the property handleDeptName.
	 */
	public void setHandleDeptName(String handleDeptName) {
		this.handleDeptName = handleDeptName;
	}
	/**
	 * @return handleDeptCode : set the property handleDeptCode.
	 */
	public String getHandleDeptCode() {
		return handleDeptCode;
	}
	/**
	 * @param handleDeptCode : return the property handleDeptCode.
	 */
	public void setHandleDeptCode(String handleDeptCode) {
		this.handleDeptCode = handleDeptCode;
	}
	/**
	 * @return handlePerson : set the property handlePerson.
	 */
	public String getHandlePerson() {
		return handlePerson;
	}
	/**
	 * @param handlePerson : return the property handlePerson.
	 */
	public void setHandlePerson(String handlePerson) {
		this.handlePerson = handlePerson;
	}
	/**
	 * @return handlePersonCode : set the property handlePersonCode.
	 */
	public String getHandlePersonCode() {
		return handlePersonCode;
	}
	/**
	 * @param handlePersonCode : return the property handlePersonCode.
	 */
	public void setHandlePersonCode(String handlePersonCode) {
		this.handlePersonCode = handlePersonCode;
	}
	/**
	 * @return applyTime : set the property applyTime.
	 */
	public Date getApplyTime() {
		return applyTime;
	}
	/**
	 * @param applyTime : return the property applyTime.
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	/**
	 * @return handleTime : set the property handleTime.
	 */
	public Date getHandleTime() {
		return handleTime;
	}
	/**
	 * @param handleTime : return the property handleTime.
	 */
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	/**
	 * @return handleNotes : set the property handleNotes.
	 */
	public String getHandleNotes() {
		return handleNotes;
	}
	/**
	 * @param handleNotes : return the property handleNotes.
	 */
	public void setHandleNotes(String handleNotes) {
		this.handleNotes = handleNotes;
	}
	/**
	 * @return checkNotes : set the property checkNotes.
	 */
	public String getCheckNotes() {
		return checkNotes;
	}
	/**
	 * @param checkNotes : return the property checkNotes.
	 */
	public void setCheckNotes(String checkNotes) {
		this.checkNotes = checkNotes;
	}
	/**
	 * @return checkPerson : set the property checkPerson.
	 */
	public String getCheckPerson() {
		return checkPerson;
	}
	/**
	 * @param checkPerson : return the property checkPerson.
	 */
	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}
	/**
	 * @return checkPersonCode : set the property checkPersonCode.
	 */
	public String getCheckPersonCode() {
		return checkPersonCode;
	}
	/**
	 * @param checkPersonCode : return the property checkPersonCode.
	 */
	public void setCheckPersonCode(String checkPersonCode) {
		this.checkPersonCode = checkPersonCode;
	}
	/**
	 * @return checkDate : set the property checkDate.
	 */
	public Date getCheckDate() {
		return checkDate;
	}
	/**
	 * @param checkDate : return the property checkDate.
	 */
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public String getRfcType() {
		return rfcType;
	}
	
	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}
	
	public String getNewWaybillId() {
		return newWaybillId;
	}
	
	public void setNewWaybillId(String newWaybillId) {
		this.newWaybillId = newWaybillId;
	}
	
}