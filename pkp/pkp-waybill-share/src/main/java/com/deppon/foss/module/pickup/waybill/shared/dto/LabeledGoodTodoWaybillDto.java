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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/LabeledGoodTodoWaybillDto.java
 * 
 * FILE NAME        	: LabeledGoodTodoWaybillDto.java
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
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 标签重打
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:43:41
 */
public class LabeledGoodTodoWaybillDto extends BaseEntity {

	/**
	 * 序列化版本
	 */
	private static final long serialVersionUID = -8089799225154490193L;

	// 货签ID
	private String labeledGoodId;

	// 待处理货件ID
	private String todoActionId;

	// 流水号
	private String serialNo;

	// 是否打印
	private String printed;

	// 打印时间
	private Date printTime;

	// 更改单ID
	private String waybillRfcId;

	// 处理部门
	private String handleOrgName;

	// 处理部门编码
	private String handleOrgCode;

	// 创建时间
	private Date createTime;

	// 操作人
	private String operator;

	// 操作人编码
	private String operatorCode;

	// 操作时间
	private Date operateTime;

	// 状态
	private String status;

	// 执行节点
	private String actuatingNode;

	
	//未入受理部门库存 - 货物入库时间
	//已入受理部门库存 - 生成待办事项时间
	private Date remindTime;
	
	//是否催促信息
	//已经发送的话为Y 不能够再次发送, 没有发送的话为N 可以再次发送
	private String isSendRemind;
	
	//运单号
	private String waybillNo;
	
	//开单时间
	private Date billTime;
	
	
	
	/**
	 * @return the billTime
	 */
	public Date getBillTime() {
		return billTime;
	}


	/**
	 * @param billTime the billTime to set
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
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
	 * @return the remindTime
	 */
	public Date getRemindTime() {
		return remindTime;
	}


	/**
	 * @param remindTime the remindTime to set
	 */
	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}


	/**
	 * @return the isSendRemind
	 */
	public String getIsSendRemind() {
		return isSendRemind;
	}


	/**
	 * @param isSendRemind the isSendRemind to set
	 */
	public void setIsSendRemind(String isSendRemind) {
		this.isSendRemind = isSendRemind;
	}


	/**
	 * @return the labeledGoodId
	 */
	public String getLabeledGoodId() {
		return labeledGoodId;
	}

	
	/**
	 * @param labeledGoodId the labeledGoodId to set
	 */
	public void setLabeledGoodId(String labeledGoodId) {
		this.labeledGoodId = labeledGoodId;
	}

	
	/**
	 * @return the todoActionId
	 */
	public String getTodoActionId() {
		return todoActionId;
	}

	
	/**
	 * @param todoActionId the todoActionId to set
	 */
	public void setTodoActionId(String todoActionId) {
		this.todoActionId = todoActionId;
	}

	
	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	
	/**
	 * @return the printed
	 */
	public String getPrinted() {
		return printed;
	}

	
	/**
	 * @param printed the printed to set
	 */
	public void setPrinted(String printed) {
		this.printed = printed;
	}

	
	/**
	 * @return the printTime
	 */
	public Date getPrintTime() {
		return printTime;
	}

	
	/**
	 * @param printTime the printTime to set
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	
	/**
	 * @return the waybillRfcId
	 */
	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	
	/**
	 * @param waybillRfcId the waybillRfcId to set
	 */
	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}

	
	/**
	 * @return the handleOrgName
	 */
	public String getHandleOrgName() {
		return handleOrgName;
	}

	
	/**
	 * @param handleOrgName the handleOrgName to set
	 */
	public void setHandleOrgName(String handleOrgName) {
		this.handleOrgName = handleOrgName;
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
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * @return the actuatingNode
	 */
	public String getActuatingNode() {
		return actuatingNode;
	}

	
	/**
	 * @param actuatingNode the actuatingNode to set
	 */
	public void setActuatingNode(String actuatingNode) {
		this.actuatingNode = actuatingNode;
	}


}