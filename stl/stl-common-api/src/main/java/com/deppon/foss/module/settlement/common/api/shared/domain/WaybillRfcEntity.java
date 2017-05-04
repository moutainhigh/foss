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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillEntity.java
 * 
 * FILE NAME        	: WaybillEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 运单更改
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:邵宏亮,date:2012-10-10 下午6:54:38,
 * </p>
 * 
 * @author 邵宏亮
 * @date 2012-10-10 下午6:54:38
 * @since
 * @version
 */
public class WaybillRfcEntity extends BaseEntity {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 8554181972428281435L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 变更来源
	 */
	private String rfcSource;

	/**
	 * 变更类型
	 */
	private String rfcType;

	/**
	 * 变更原因
	 */
	private String rfcReason;

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
	 * 备注
	 */
	private String notes;

	/**
	 * 更改单状态
	 */
	private String status;

	/**
	 * 操作人
	 */
	private String operator;

	/**
	 * 操作编码
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
	 * 原版本运单ID
	 */
	private String oldVersionWaybillId;

	/**
	 * 新版本运单ID
	 */
	private String newVersionWaybillId;

	/**
	 * 是否财务类变更
	 */
	private String isFinanceChange;

	/**
	 * 变更内容
	 */
	private String changeItems;

	/**
	 * 收货人短信
	 */
	private String receiverSms;

	/**
	 * 发货人短信
	 */
	private String deliverSms;

	/**
	 * 是否目的站修改
	 */
	private String isChangeDestination;

	/**
	 * 是否需要财务核销
	 */
	private String needWriteOff;

	/**
	 * 核销状态
	 */
	private String writeOffStatus;
	
	/**
	 * 核销人姓名
	 */
	private String writeOffEmpName;
	
	/**
	 * 核销时间
	 */
	private Date writeOffTime;
	
	/**
	 * 核销备注
	 */
	private String writeOffNotes;

	/**
	 * 是否PDA提醒
	 */
	private String pdaNotice;
	
	/**
	 * 是否修改运单号
	 */
	private String isChangeWaybillNo;
	
	/**
	 * 是否自动审核
	 */
	private String isLabourHandle;
	
	
	/**
	 * 中转费
	 */
	private BigDecimal transportFee;
	
	
	/**
	 * 中转费率 
	 */
	private BigDecimal transportFeeRate;
	
	//货物范围--206860
	private String goodsRange;
	
	public String getGoodsRange() {
		return goodsRange;
	}

	public void setGoodsRange(String goodsRange) {
		this.goodsRange = goodsRange;
	}
	
	
	//转运或是返货的目的站变更实体---206860
/*	private List<WaybillRfcTranferEntity> rfcTranferList;
	

	public List<WaybillRfcTranferEntity> getRfcTranferList() {
		return rfcTranferList;
	}

	public void setRfcTranferList(List<WaybillRfcTranferEntity> rfcTranferList) {
		this.rfcTranferList = rfcTranferList;
	}*/

	
	/**
	 * 是否计算了公布价
	 * 
	 * @author Foss-206860
	 */
	private String isCalTraFee;

	public String getIsCalTraFee() {
		return isCalTraFee;
	}

	public void setIsCalTraFee(String isCalTraFee) {
		this.isCalTraFee = isCalTraFee;
	}
	
	/**
	 * 最低一票费用
	 * 
	 * @author Foss-206860
	 * */
	private BigDecimal minTransportFee;
	
	/**
	 *  最低一票费用
	 * @return the minTransportFee
	 */
	public BigDecimal getMinTransportFee() {
		return minTransportFee;
	}

	/**
	 *  最低一票费用
	 * @param minTransportFee the minTransportFee to set
	 */
	public void setMinTransportFee(BigDecimal minTransportFee) {
		this.minTransportFee = minTransportFee;
	}

	
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * @return the rfcSource
	 */
	public String getRfcSource() {
		return rfcSource;
	}

	
	/**
	 * @param rfcSource the rfcSource to set
	 */
	public void setRfcSource(String rfcSource) {
		this.rfcSource = rfcSource;
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

	
	/**
	 * @return the rfcReason
	 */
	public String getRfcReason() {
		return rfcReason;
	}

	
	/**
	 * @param rfcReason the rfcReason to set
	 */
	public void setRfcReason(String rfcReason) {
		this.rfcReason = rfcReason;
	}

	
	/**
	 * @return the draftOrgName
	 */
	public String getDraftOrgName() {
		return draftOrgName;
	}

	
	/**
	 * @param draftOrgName the draftOrgName to set
	 */
	public void setDraftOrgName(String draftOrgName) {
		this.draftOrgName = draftOrgName;
	}

	
	/**
	 * @return the draftOrgCode
	 */
	public String getDraftOrgCode() {
		return draftOrgCode;
	}

	
	/**
	 * @param draftOrgCode the draftOrgCode to set
	 */
	public void setDraftOrgCode(String draftOrgCode) {
		this.draftOrgCode = draftOrgCode;
	}

	
	/**
	 * @return the drafter
	 */
	public String getDrafter() {
		return drafter;
	}

	
	/**
	 * @param drafter the drafter to set
	 */
	public void setDrafter(String drafter) {
		this.drafter = drafter;
	}

	
	/**
	 * @return the drafterCode
	 */
	public String getDrafterCode() {
		return drafterCode;
	}

	
	/**
	 * @param drafterCode the drafterCode to set
	 */
	public void setDrafterCode(String drafterCode) {
		this.drafterCode = drafterCode;
	}

	
	/**
	 * @return the draftTime
	 */
	public Date getDraftTime() {
		return draftTime;
	}

	
	/**
	 * @param draftTime the draftTime to set
	 */
	public void setDraftTime(Date draftTime) {
		this.draftTime = draftTime;
	}

	
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	
	/**
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	
	/**
	 * @param operatorCode the operatorCode to set
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	
	/**
	 * @return the operateOrgName
	 */
	public String getOperateOrgName() {
		return operateOrgName;
	}

	
	/**
	 * @param operateOrgName the operateOrgName to set
	 */
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	
	/**
	 * @return the operateOrgCode
	 */
	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	
	/**
	 * @param operateOrgCode the operateOrgCode to set
	 */
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	
	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	
	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	
	/**
	 * @return the oldVersionWaybillId
	 */
	public String getOldVersionWaybillId() {
		return oldVersionWaybillId;
	}

	
	/**
	 * @param oldVersionWaybillId the oldVersionWaybillId to set
	 */
	public void setOldVersionWaybillId(String oldVersionWaybillId) {
		this.oldVersionWaybillId = oldVersionWaybillId;
	}

	
	/**
	 * @return the newVersionWaybillId
	 */
	public String getNewVersionWaybillId() {
		return newVersionWaybillId;
	}

	
	/**
	 * @param newVersionWaybillId the newVersionWaybillId to set
	 */
	public void setNewVersionWaybillId(String newVersionWaybillId) {
		this.newVersionWaybillId = newVersionWaybillId;
	}

	
	/**
	 * @return the isFinanceChange
	 */
	public String getIsFinanceChange() {
		return isFinanceChange;
	}

	
	/**
	 * @param isFinanceChange the isFinanceChange to set
	 */
	public void setIsFinanceChange(String isFinanceChange) {
		this.isFinanceChange = isFinanceChange;
	}

	
	/**
	 * @return the changeItems
	 */
	public String getChangeItems() {
		return changeItems;
	}

	
	/**
	 * @param changeItems the changeItems to set
	 */
	public void setChangeItems(String changeItems) {
		this.changeItems = changeItems;
	}

	
	/**
	 * @return the receiverSms
	 */
	public String getReceiverSms() {
		return receiverSms;
	}

	
	/**
	 * @param receiverSms the receiverSms to set
	 */
	public void setReceiverSms(String receiverSms) {
		this.receiverSms = receiverSms;
	}

	
	/**
	 * @return the deliverSms
	 */
	public String getDeliverSms() {
		return deliverSms;
	}

	
	/**
	 * @param deliverSms the deliverSms to set
	 */
	public void setDeliverSms(String deliverSms) {
		this.deliverSms = deliverSms;
	}

	
	/**
	 * @return the isChangeDestination
	 */
	public String getIsChangeDestination() {
		return isChangeDestination;
	}

	
	/**
	 * @param isChangeDestination the isChangeDestination to set
	 */
	public void setIsChangeDestination(String isChangeDestination) {
		this.isChangeDestination = isChangeDestination;
	}

	
	/**
	 * @return the needWriteOff
	 */
	public String getNeedWriteOff() {
		return needWriteOff;
	}

	
	/**
	 * @param needWriteOff the needWriteOff to set
	 */
	public void setNeedWriteOff(String needWriteOff) {
		this.needWriteOff = needWriteOff;
	}

	
	/**
	 * @return the writeOffStatus
	 */
	public String getWriteOffStatus() {
		return writeOffStatus;
	}

	
	/**
	 * @param writeOffStatus the writeOffStatus to set
	 */
	public void setWriteOffStatus(String writeOffStatus) {
		this.writeOffStatus = writeOffStatus;
	}

	
	/**
	 * @return the writeOffNotes
	 */
	public String getWriteOffNotes() {
		return writeOffNotes;
	}

	
	/**
	 * @param writeOffNotes the writeOffNotes to set
	 */
	public void setWriteOffNotes(String writeOffNotes) {
		this.writeOffNotes = writeOffNotes;
	}

	
	/**
	 * @return the pdaNotice
	 */
	public String getPdaNotice() {
		return pdaNotice;
	}

	
	/**
	 * @param pdaNotice the pdaNotice to set
	 */
	public void setPdaNotice(String pdaNotice) {
		this.pdaNotice = pdaNotice;
	}

	
	/**
	 * @return the isChangeWaybillNo
	 * 
	 * 是否修改运单号
	 */
	public String getIsChangeWaybillNo() {
		return isChangeWaybillNo;
	}

	
	/**
	 * @param isChangeWaybillNo the isChangeWaybillNo to set
	 */
	public void setIsChangeWaybillNo(String isChangeWaybillNo) {
		this.isChangeWaybillNo = isChangeWaybillNo;
	}

	
	/**
	 * @return the isLabourHandle
	 */
	public String getIsLabourHandle() {
		return isLabourHandle;
	}

	
	/**
	 * @param isLabourHandle the isLabourHandle to set
	 */
	public void setIsLabourHandle(String isLabourHandle) {
		this.isLabourHandle = isLabourHandle;
	}


	/**
	 * @return the transportFee
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}


	/**
	 * @param transportFee the transportFee to set
	 */
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}


	/**
	 * @return the transportFeeRate
	 */
	public BigDecimal getTransportFeeRate() {
		return transportFeeRate;
	}


	/**
	 * @param transportFeeRate the transportFeeRate to set
	 */
	public void setTransportFeeRate(BigDecimal transportFeeRate) {
		this.transportFeeRate = transportFeeRate;
	}


	public Date getWriteOffTime() {
		return writeOffTime;
	}


	public void setWriteOffTime(Date writeOffTime) {
		this.writeOffTime = writeOffTime;
	}


	public String getWriteOffEmpName() {
		return writeOffEmpName;
	}


	public void setWriteOffEmpName(String writeOffEmpName) {
		this.writeOffEmpName = writeOffEmpName;
	}


	/**
	 *定价优化项目：降价返券需求
	 *
	 *@author Foss-206860
	 * */
/*	private QueryBillCacilateDto queryBillCacilateDto;

	public QueryBillCacilateDto getQueryBillCacilateDto() {
		return queryBillCacilateDto;
	}

	public void setQueryBillCacilateDto(QueryBillCacilateDto queryBillCacilateDto) {
		this.queryBillCacilateDto = queryBillCacilateDto;
	}*/
	
	/**
	 * 合伙人 信息
	 */
/*	private PtpWaybillDto ptpWaybillDto ;

	public PtpWaybillDto getPtpWaybillDto() {
		return ptpWaybillDto;
	}

	public void setPtpWaybillDto(PtpWaybillDto ptpWaybillDto) {
		this.ptpWaybillDto = ptpWaybillDto;
	}*/
	
}
