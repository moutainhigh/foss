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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/LeaveChangeByWaybillNoResultDto.java
 * 
 * FILE NAME        	: LeaveChangeByWaybillNoResultDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;

/**
 * 根据运单号查询运单出发更改单信息。返回一个对象的集合，对象包括的属性有： 1、 单据编号 2、 变更信息 3、 起草人： 姓名 4、 起草时间 5、
 * 受理部门：部门名称 6、 受理备注： 7、 变更原因 8、 受理状态
 * 
 * @author 026113-foss-linwensheng
 * 
 */
public class LeaveChangeByWaybillNoResultDto {

	//更改单ID
	String id;
	
	// 运单号
	private String waybillNo;

	// 变更内容
	private String changeItems;

	// 起草人
	private String drafter;

	// 起草时间
	private Date draftTime;
	// 起草部门
	private String draftOrgName;
	/**
	 *  受理部门
	 */
	private String operateOrgName;
	/**
	 *  受理时间
	 */
	private String operateTime;
	
	//受理人
	private String auditor;
	
	// 更改单受理状态
	private String status;
	
	// 受理备注
	private String notes;
	
	//审核时间
	private String acceptTime;
	
	//审核人
	private String acceptor;
	
	//审核状态
	private String acceptStatus;
	
	//审核备注
	private String acceptNotes;

	// 变更原因
	private String rfcReason;


	//需要受理部门
	private String needAcceptOrg;
	
	//变更类型  转货 返货
	private String rfcType;

	//是否自动受理
	private String isLabourHandle;
	
	
	
	/**
	 * 是否自动受理
	 * @return
	 */
	public String getIsLabourHandle() {
		return isLabourHandle;
	}

	public void setIsLabourHandle(String isLabourHandle) {
		this.isLabourHandle = isLabourHandle;
	}

	/**
	 * @return the rfcType
	 */
	public String getRfcType() {
		return rfcType;
	}

	/**
	 * @param rfcType the rfcType to set
	 */
	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}

	public String getNeedAcceptOrg() {
		return needAcceptOrg;
	}

	public void setNeedAcceptOrg(String needAcceptOrg) {
		this.needAcceptOrg = needAcceptOrg;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getChangeItems() {
		return changeItems;
	}

	public void setChangeItems(String changeItems) {
		this.changeItems = changeItems;
	}

	public String getDrafter() {
		return drafter;
	}

	public void setDrafter(String drafter) {
		this.drafter = drafter;
	}

	public Date getDraftTime() {
		return draftTime;
	}

	public void setDraftTime(Date draftTime) {
		this.draftTime = draftTime;
	}

	public String getDraftOrgName() {
		return draftOrgName;
	}

	public void setDraftOrgName(String draftOrgName) {
		this.draftOrgName = draftOrgName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getRfcReason() {
		return rfcReason;
	}

	public void setRfcReason(String rfcReason) {
		this.rfcReason = rfcReason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(String acceptor) {
		this.acceptor = acceptor;
	}

	public String getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	public String getAcceptNotes() {
		return acceptNotes;
	}

	public void setAcceptNotes(String acceptNotes) {
		this.acceptNotes = acceptNotes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}