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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/SignRfcEntity.java
 * 
 * FILE NAME        	: SignRfcEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 签收变更
 * @author ibm-lizhiguo
 * @date 2012-11-16 上午10:30:13
 */
public class SignRfcEntity extends BaseEntity{

	/** 
	 * @fields serialVersionUID 
	 */ 
	private static final long serialVersionUID = 1L;
	
	//关联ID  CUBC使用
	private String cubcID;

	public String getCubcID() {
		return cubcID;
	}

	public void setCubcID(String cubcID) {
		this.cubcID = cubcID;
	}

	/**
	 * 运单号
	 */
    private String waybillNo;

    /**
     * 变更类型
     */
    private String rfcType;

    /**
     * 起草部门
     */
    private String draftOrgName;

    /**
     * 起草部门编码
     */
    private String draftOrgCode;

    /**
     * 起草人
     */
    private String drafter;

    /**
     * 起草人编号
     */
    private String drafterCode;

    /**
     * 起草时间
     */
    private Date draftTime;

    /**
     * 原因
     */
    private String reason;

    /**
     * 备注
     */
    private String notes;

    /**
     * 更改单状态
     */
    private String status;
    /**
     * 原状态
     */
    private String oldStatus;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作人编码
     */
    private String operatorCode;

    /**
     * 操作部门
     */
    private String operateOrgName;

    /**
     * 操作部门编码
     */
    private String operateOrgCode;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 到达联ID
     */
    private String tSrvArrivesheetId;
    
    /**
     * 货签表ID
     * */
    private String tSrvLabelTableId;

    /**
     * 付款ID
     */
    private String tSrvRepaymentId;

    /**
     * 运单签收结果ID
     */
    private String tSrvWaybillSignResultId;
    
    /**
     * 申请流水号
     */
    private String rfcNo;
    
    /**
     * 到达联No
     */
    private String arrivesheetNo;

    /**
     * 起草时间--开始
     */
    private Date draftTimeStart;
    
    /**
     * 起草时间--结束
     */
    private Date draftTimeEnd;
    /**
     * 反签收明细类型
     *   ：反结清/反到达联、反签收结果（空运偏线）
     */
    private String rfcDetailType;
	public String getRfcDetailType() {
		return rfcDetailType;
	}
	
	/**
	 * @return waybillNo : return the property waybillNo.
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo : set the property waybillNo.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return rfcType : return the property rfcType.
	 */
	public String getRfcType() {
		return rfcType;
	}

	/**
	 * @param rfcType : set the property rfcType.
	 */
	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}

	/**
	 * @return draftOrgName : return the property draftOrgName.
	 */
	public String getDraftOrgName() {
		return draftOrgName;
	}

	/**
	 * @param draftOrgName : set the property draftOrgName.
	 */
	public void setDraftOrgName(String draftOrgName) {
		this.draftOrgName = draftOrgName;
	}

	/**
	 * @return draftOrgCode : return the property draftOrgCode.
	 */
	public String getDraftOrgCode() {
		return draftOrgCode;
	}

	/**
	 * @param draftOrgCode : set the property draftOrgCode.
	 */
	public void setDraftOrgCode(String draftOrgCode) {
		this.draftOrgCode = draftOrgCode;
	}

	/**
	 * @return drafter : return the property drafter.
	 */
	public String getDrafter() {
		return drafter;
	}

	/**
	 * @param drafter : set the property drafter.
	 */
	public void setDrafter(String drafter) {
		this.drafter = drafter;
	}

	/**
	 * @return drafterCode : return the property drafterCode.
	 */
	public String getDrafterCode() {
		return drafterCode;
	}

	/**
	 * @param drafterCode : set the property drafterCode.
	 */
	public void setDrafterCode(String drafterCode) {
		this.drafterCode = drafterCode;
	}

	/**
	 * @return draftTime : return the property draftTime.
	 */
	public Date getDraftTime() {
		return draftTime;
	}

	/**
	 * @param draftTime : set the property draftTime.
	 */
	public void setDraftTime(Date draftTime) {
		this.draftTime = draftTime;
	}

	/**
	 * @return reason : return the property reason.
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason : set the property reason.
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return notes : return the property notes.
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes : set the property notes.
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return status : return the property status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status : set the property status.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return operator : return the property operator.
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator : set the property operator.
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return operatorCode : return the property operatorCode.
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * @param operatorCode : set the property operatorCode.
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * @return operateOrgName : return the property operateOrgName.
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	/**
	 * @param operateOrgName : set the property operateOrgName.
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	/**
	 * @return operateOrgCode : return the property operateOrgCode.
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	/**
	 * @param operateOrgCode : set the property operateOrgCode.
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	/**
	 * @return operateTime : return the property operateTime.
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime : set the property operateTime.
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return tSrvArrivesheetId : return the property tSrvArrivesheetId.
	 */
	public String gettSrvArrivesheetId() {
		return tSrvArrivesheetId;
	}

	/**
	 * @param tSrvArrivesheetId : set the property tSrvArrivesheetId.
	 */
	public void settSrvArrivesheetId(String tSrvArrivesheetId) {
		this.tSrvArrivesheetId = tSrvArrivesheetId;
	}

	/**
	 * @return tSrvRepaymentId : return the property tSrvRepaymentId.
	 */
	public String gettSrvRepaymentId() {
		return tSrvRepaymentId;
	}

	/**
	 * @param tSrvRepaymentId : set the property tSrvRepaymentId.
	 */
	public void settSrvRepaymentId(String tSrvRepaymentId) {
		this.tSrvRepaymentId = tSrvRepaymentId;
	}

	/**
	 * @return tSrvWaybillSignResultId : return the property tSrvWaybillSignResultId.
	 */
	public String gettSrvWaybillSignResultId() {
		return tSrvWaybillSignResultId;
	}

	/**
	 * @param tSrvWaybillSignResultId : set the property tSrvWaybillSignResultId.
	 */
	public void settSrvWaybillSignResultId(String tSrvWaybillSignResultId) {
		this.tSrvWaybillSignResultId = tSrvWaybillSignResultId;
	}

	/**
	 * @return rfcNo : return the property rfcNo.
	 */
	public String getRfcNo() {
		return rfcNo;
	}

	/**
	 * @param rfcNo : set the property rfcNo.
	 */
	public void setRfcNo(String rfcNo) {
		this.rfcNo = rfcNo;
	}

	/**
	 * @return arrivesheetNo : return the property arrivesheetNo.
	 */
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}

	/**
	 * @param arrivesheetNo : set the property arrivesheetNo.
	 */
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
	}

	/**
	 * @return draftTimeStart : return the property draftTimeStart.
	 */
	public Date getDraftTimeStart() {
		return draftTimeStart;
	}

	/**
	 * @param draftTimeStart : set the property draftTimeStart.
	 */
	public void setDraftTimeStart(Date draftTimeStart) {
		this.draftTimeStart = draftTimeStart;
	}

	/**
	 * @return draftTimeEnd : return the property draftTimeEnd.
	 */
	public Date getDraftTimeEnd() {
		return draftTimeEnd;
	}

	/**
	 * @param draftTimeEnd : set the property draftTimeEnd.
	 */
	public void setDraftTimeEnd(Date draftTimeEnd) {
		this.draftTimeEnd = draftTimeEnd;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String gettSrvLabelTableId() {
		return tSrvLabelTableId;
	}

	public void settSrvLabelTableId(String tSrvLabelTableId) {
		this.tSrvLabelTableId = tSrvLabelTableId;
	}

    
}