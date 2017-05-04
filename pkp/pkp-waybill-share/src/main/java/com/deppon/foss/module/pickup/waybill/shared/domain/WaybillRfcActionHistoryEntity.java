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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillRfcActionHistoryEntity.java
 * 
 * FILE NAME        	: WaybillRfcActionHistoryEntity.java
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
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 运单变更历史记录
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:44:55
 */
public class WaybillRfcActionHistoryEntity extends BaseEntity {


    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1985854586384842323L;

    // 运单更改ID
    private String waybillRfcId;

    // 备注
    private String notes;

    // 更改单状态
    private String status;

    // 操作人
    private String operator;

    // 操作人编码
    private String operatorCode;

    // 操作部门
    private String operateOrgName;

    // 操作部门编码
    private String operateOrgCode;

    // 操作时间
    private Date operateTime;
    
    /**
     * 运单更改ID
     * @return the waybillRfcId
     */
    public String getWaybillRfcId() {
    	return waybillRfcId;
    }
    
    /**
     * 运单更改ID
     * @param waybillRfcId the waybillRfcId to set
     */
    public void setWaybillRfcId(String waybillRfcId) {
    	this.waybillRfcId = waybillRfcId;
    }
    
    /**
     * @return the notes
     */
    public String getNotes() {
    	return notes;
    }
    
    /**
     * @param notes
     *            the notes to set
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
     * @param status
     *            the status to set
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
     * @param operator
     *            the operator to set
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
     * @param operatorCode
     *            the operatorCode to set
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
     * @param operateOrgName
     *            the operateOrgName to set
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
     * @param operateOrgCode
     *            the operateOrgCode to set
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
     * @param operateTime
     *            the operateTime to set
     */
    public void setOperateTime(Date operateTime) {
    	this.operateTime = operateTime;
    }
    

}