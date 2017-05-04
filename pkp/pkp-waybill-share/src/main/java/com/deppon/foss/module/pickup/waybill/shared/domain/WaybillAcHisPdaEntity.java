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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillAcHisPdaEntity.java
 * 
 * FILE NAME        	: WaybillAcHisPdaEntity.java
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
 * PDA运单操作历史
 * @author 026123-foss-lifengteng
 * @date 2012-12-10 下午4:43:34
 */
public class WaybillAcHisPdaEntity extends BaseEntity{
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1659129808389530620L;

    /** 
     *  待处理运单基本信息ID
     */
    private String tSrvWaybillPending;

    /** 
     *  变更项目
     */
    private String actionItem;

    /** 
     *  变更项名称
     */
    private String actionName;

    /** 
     *  变更前内容
     */
    private String beforeChange;

    /**
     *   变更后内容
     */
    private String afterChange;

    /**
     *   操作人编号
     */
    private String operatorCode;

    /**  
     * 操作人
     */
    private String operator;

    /**
     *   操作人所在部门编号
     */
    private String operatorOrgCode;

    /**
     *   操作人所在部门名称
     */
    private String operatorOrg;

    /** 
     *  操作时间
     */
    private Date operateTime;

    /**
     * 运单编号
     * @liyongfei DMANA-3119货物轨迹查询优化需求
     */
	private String waybillNo;
	
	/**
	 * @return the tSrvWaybillPending .
	 */
	public String gettSrvWaybillPending() {
		return tSrvWaybillPending;
	}

	
	/**
	 *@param tSrvWaybillPending the tSrvWaybillPending to set.
	 */
	public void settSrvWaybillPending(String tSrvWaybillPending) {
		this.tSrvWaybillPending = tSrvWaybillPending;
	}

	
	/**
	 * @return the actionItem .
	 */
	public String getActionItem() {
		return actionItem;
	}

	
	/**
	 *@param actionItem the actionItem to set.
	 */
	public void setActionItem(String actionItem) {
		this.actionItem = actionItem;
	}

	
	/**
	 * @return the actionName .
	 */
	public String getActionName() {
		return actionName;
	}

	
	/**
	 *@param actionName the actionName to set.
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	
	/**
	 * @return the beforeChange .
	 */
	public String getBeforeChange() {
		return beforeChange;
	}

	
	/**
	 *@param beforeChange the beforeChange to set.
	 */
	public void setBeforeChange(String beforeChange) {
		this.beforeChange = beforeChange;
	}

	
	/**
	 * @return the afterChange .
	 */
	public String getAfterChange() {
		return afterChange;
	}

	
	/**
	 *@param afterChange the afterChange to set.
	 */
	public void setAfterChange(String afterChange) {
		this.afterChange = afterChange;
	}

	
	/**
	 * @return the operatorCode .
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	
	/**
	 *@param operatorCode the operatorCode to set.
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	
	/**
	 * @return the operator .
	 */
	public String getOperator() {
		return operator;
	}

	
	/**
	 *@param operator the operator to set.
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	
	/**
	 * @return the operatorOrgCode .
	 */
	public String getOperatorOrgCode() {
		return operatorOrgCode;
	}

	
	/**
	 *@param operatorOrgCode the operatorOrgCode to set.
	 */
	public void setOperatorOrgCode(String operatorOrgCode) {
		this.operatorOrgCode = operatorOrgCode;
	}

	
	/**
	 * @return the operatorOrg .
	 */
	public String getOperatorOrg() {
		return operatorOrg;
	}

	
	/**
	 *@param operatorOrg the operatorOrg to set.
	 */
	public void setOperatorOrg(String operatorOrg) {
		this.operatorOrg = operatorOrg;
	}

	
	/**
	 * @return the operateTime .
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	
	/**
	 *@param operateTime the operateTime to set.
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}


	public String getWaybillNo() {
		return waybillNo;
	}


	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

 
}