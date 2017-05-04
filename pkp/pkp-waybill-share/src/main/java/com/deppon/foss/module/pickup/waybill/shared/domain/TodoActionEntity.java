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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/TodoActionEntity.java
 * 
 * FILE NAME        	: TodoActionEntity.java
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
 * (待办事项)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:邵宏亮,date:2012-10-10 下午6:52:58, </p>
 * @author 邵宏亮
 * @date 2012-10-10 下午6:52:58
 * @since
 * @version
 */
public class TodoActionEntity extends BaseEntity {


    /**
     * 序列化版本
     */
    private static final long serialVersionUID = 5703286511506285051L;

    // 运单更改ID
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
     * @param handleOrgName
     *            the handleOrgName to set
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
     * @param handleOrgCode
     *            the handleOrgCode to set
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
     * @param createTime
     *            the createTime to set
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


}