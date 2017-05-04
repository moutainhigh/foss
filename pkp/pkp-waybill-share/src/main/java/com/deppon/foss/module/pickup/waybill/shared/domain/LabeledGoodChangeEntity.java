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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/LabeledGoodChangeEntity.java
 * 
 * FILE NAME        	: LabeledGoodChangeEntity.java
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

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 更改单修改件数和打木架数量修改详细信息冗余保存对象
 * @author shi xiao wei
 *
 */
public class LabeledGoodChangeEntity 
	extends BaseEntity {

	 /**
     * 序列化对象ID
     */
    private static final long serialVersionUID = 2;
    
    /**
     * 运单号
     */
    private String waybillNo;
    
    /**
     * 从labeled good 货签中获得的id
     * 如果是界面上完全新增的货签 这个id无法关联原来的货签 则不存在的 此时类型肯定是 CHANGE_TYPE_NEW 或者 CHANGE_TYPE_WOODEN_ADD_NEW
     * 
     */
    private String labeledGoodId;
    
    /**
	 * 货签打印流水号
	 */
	private String serialNo;
	
	/**
	 * 更改的货签的类型  有可能是 
	 * 1新增一个货签   CHANGE_TYPE_NEW
	 * 2删除一个货签  CHANGE_TYPE_DELETE
	 * 3 对一个已经存在的货签进行新增打木架或者打木箱处理   CHANGE_TYPE_WOODEN_ADD
	 * 4对一个已经存在的要打木架或者打木箱的货签进行不再进行打木架或者打木箱操作 CHANGE_TYPE_WOODEN_DELETE
	 * 5对一个新增的货签进行打木架处理  CHANGE_TYPE_WOODEN_ADD_NEW
	 */
	private String changeType;
	
	/**
	 * 对于新增的货签 需要输入的货签接受组织编码
	 * 该编码可以是该运单走货路劲上的任意一个org code
	 * 无论外场或者营业部
	 */
	private String receiveOrgCode;
	
	/**
	 * 货签接受组织名称
	 */
	private String receiveOrgName;
	
	/**
	 * 该更改单的审批状态
	 */
	private String flowStatus;
	
	/**
	 * 审批修改的次数
	 */
	private String changeTimes;
	
	
	/**
	 * 是否是 需要调用 中转新增或者见啥接口
	 */
	private String needInvokeTrfInterface;
	
	private String packageSalver;
	
	/**
	 * @return the needInvokeTrfInterface
	 */
	public String getNeedInvokeTrfInterface() {
		return needInvokeTrfInterface;
	}

	/**
	 * @param needInvokeTrfInterface the needInvokeTrfInterface to set
	 */
	public void setNeedInvokeTrfInterface(String needInvokeTrfInterface) {
		this.needInvokeTrfInterface = needInvokeTrfInterface;
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
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}

	/**
	 * @param changeType the changeType to set
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	/**
	 * @return the receiveOrgCode
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * @param receiveOrgCode the receiveOrgCode to set
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * @return the receiveOrgName
	 */
	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	/**
	 * @param receiveOrgName the receiveOrgName to set
	 */
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	/**
	 * @return the flowStatus
	 */
	public String getFlowStatus() {
		return flowStatus;
	}

	/**
	 * @param flowStatus the flowStatus to set
	 */
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	/**
	 * @return the changeTimes
	 */
	public String getChangeTimes() {
		return changeTimes;
	}

	/**
	 * @param changeTimes the changeTimes to set
	 */
	public void setChangeTimes(String changeTimes) {
		this.changeTimes = changeTimes;
	}

	public String getPackageSalver() {
		return packageSalver;
	}

	public void setPackageSalver(String packageSalver) {
		this.packageSalver = packageSalver;
	}
	
	
}