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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/TodoConditionDto.java
 * 
 * FILE NAME        	: TodoConditionDto.java
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

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;

/**
 * 
 * 待办事宜查询dto
 * 
 * @author dp-zhaobin
 * @date 2012-10-20 下午3:25:47
 */
public class TodoConditionDto implements Serializable{
	private static final long serialVersionUID = -8361365807166285001L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 更改单ID
	 */
	private String waybillRfcId;

	/**
	 * 处理状态
	 */
	private String status;

	/**
	 * 更改单状态
	 */
	private String rfcStatus;

	/**
	 * 变更起草部门
	 */
	private String darftOrgName;
	
	/**
	 * 变更起草部门
	 */
	private String darftOrgCode;

	/**
	 * 更改受理时间
	 */
	private Date operateTimeBegin;

	/**
	 * 更改受理时间
	 */
	private Date operateTimeEnd;
	
	/**
	 * 入库开始时间
	 */
	private Date remainTimeBegin;
	
	/**
	 * 入库结束时间
	 */
	private Date remainTimeEnd;
	
	/**
	 * 待办受理部门
	 */
	private String handleOrgCode;
	
	/**
	 * 派送部的最终配载部门
	 */
	private String lastLoadOrgCode;
	
	/**
	 * 是否打印
	 */
	private String isPrinted;
	
	/**
	 * 当前查询部门
	 */
	private String currentDept;
	
	/**
	 * 交接单编号
	 */
	private String handlerOverNo;
	
	/**
	 * 配载单编号
	 */
	private String loadNo;
	
	/**
	 * 操作人编码
	 */
	private String operateOrgCode;
	
	/**
	 * 操作人姓名
	 */
	private String operateOrgName;
	
	/**
	 * 是否涉及目的站变更
	 */
	private String isDestiChage;
	
	/**
	 * @return the handlerOverNo
	 */
	public String getHandlerOverNo() {
		return handlerOverNo;
	}

	/**
	 * @param handlerOverNo the handlerOverNo to set
	 */
	public void setHandlerOverNo(String handlerOverNo) {
		this.handlerOverNo = handlerOverNo;
	}


	/**
	 * @return the loadNo
	 */
	public String getLoadNo() {
		return loadNo;
	}


	/**
	 * @param loadNo the loadNo to set
	 */
	public void setLoadNo(String loadNo) {
		this.loadNo = loadNo;
	}


	public String getCurrentDept() {
		return currentDept;
	}


	public void setCurrentDept(String currentDept) {
		this.currentDept = currentDept;
	}


	/**
	 * @return the handleOrgCode
	 */
	public String getHandleOrgCode() {
		return handleOrgCode;
	}

	
	/**
	 * @param handleOrgCode the handleOrgCode to set
	 */
	public void setHandleOrgCode(String handleOrgCode) {
		this.handleOrgCode = handleOrgCode;
	}

	/**
	 * 打印List
	 */
	private  List<LabeledGoodTodoEntity> labeledGoodTodoEntityList;

	/**
	 * Gets the waybill no.
	 *
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the waybill rfc id.
	 *
	 * @return the waybillRfcId
	 */
	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	/**
	 * Sets the waybill rfc id.
	 *
	 * @param waybillRfcId the waybillRfcId to set
	 */
	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the rfc status.
	 *
	 * @return the rfcStatus
	 */
	public String getRfcStatus() {
		return rfcStatus;
	}

	/**
	 * Sets the rfc status.
	 *
	 * @param rfcStatus the rfcStatus to set
	 */
	public void setRfcStatus(String rfcStatus) {
		this.rfcStatus = rfcStatus;
	}

	/**
	 * Gets the darft org name.
	 *
	 * @return the darftOrgName
	 */
	public String getDarftOrgName() {
		return darftOrgName;
	}

	/**
	 * Sets the darft org name.
	 *
	 * @param darftOrgName the darftOrgName to set
	 */
	public void setDarftOrgName(String darftOrgName) {
		this.darftOrgName = darftOrgName;
	}

	/**
	 * Gets the operate time begin.
	 *
	 * @return the operateTimeBegin
	 */
	public Date getOperateTimeBegin() {
		return operateTimeBegin;
	}

	/**
	 * Sets the operate time begin.
	 *
	 * @param operateTimeBegin the operateTimeBegin to set
	 */
	public void setOperateTimeBegin(Date operateTimeBegin) {
		this.operateTimeBegin = operateTimeBegin;
	}

	/**
	 * Gets the operate time end.
	 *
	 * @return the operateTimeEnd
	 */
	public Date getOperateTimeEnd() {
		return operateTimeEnd;
	}

	/**
	 * Sets the operate time end.
	 *
	 * @param operateTimeEnd the operateTimeEnd to set
	 */
	public void setOperateTimeEnd(Date operateTimeEnd) {
		this.operateTimeEnd = operateTimeEnd;
	}

	/**
	 * Gets the labeled good todo entity list.
	 *
	 * @return the labeled good todo entity list
	 */
	public List<LabeledGoodTodoEntity> getLabeledGoodTodoEntityList()
	{
		return labeledGoodTodoEntityList;
	}

	/**
	 * Sets the labeled good todo entity list.
	 *
	 * @param labeledGoodTodoEntityList the new labeled good todo entity list
	 */
	public void setLabeledGoodTodoEntityList(List<LabeledGoodTodoEntity> labeledGoodTodoEntityList)
	{
		this.labeledGoodTodoEntityList = labeledGoodTodoEntityList;
	}


	public String getIsPrinted() {
		return isPrinted;
	}


	public void setIsPrinted(String isPrinted) {
		this.isPrinted = isPrinted;
	}


	/**
	 * @return the lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}


	/**
	 * @param lastLoadOrgCode the lastLoadOrgCode to set
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	public Date getRemainTimeBegin() {
		return remainTimeBegin;
	}

	public void setRemainTimeBegin(Date remainTimeBegin) {
		this.remainTimeBegin = remainTimeBegin;
	}

	public Date getRemainTimeEnd() {
		return remainTimeEnd;
	}

	public void setRemainTimeEnd(Date remainTimeEnd) {
		this.remainTimeEnd = remainTimeEnd;
	}

	public String getDarftOrgCode() {
		return darftOrgCode;
	}

	public void setDarftOrgCode(String darftOrgCode) {
		this.darftOrgCode = darftOrgCode;
	}

	public String getOperateOrgCode() {
		return operateOrgCode;
	}

	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}

	public String getOperateOrgName() {
		return operateOrgName;
	}

	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}

	public String getIsDestiChage() {
		return isDestiChage;
	}

	public void setIsDestiChage(String isDestiChage) {
		this.isDestiChage = isDestiChage;
	}
}