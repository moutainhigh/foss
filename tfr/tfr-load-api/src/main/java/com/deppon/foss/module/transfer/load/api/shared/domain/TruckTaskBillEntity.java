/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/TruckTaskBillEntity.java
 *  
 *  FILE NAME          :TruckTaskBillEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.domain
 * FILE    NAME: TruckTaskBillEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务车辆单据明细
 * @author dp-duyi
 * @date 2012-11-7 上午9:07:33
 */
public class TruckTaskBillEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -14826009128305406L;
	/**id*/
	private String id;
	/**parentId*/
	private String parentId;
	/**单据级别*/
	private String billLevel;
	/**单据类型*/
	private String billType;
	/**单据编号*/
	private String billNo;
	/**分配状态*/
	private String assignState;
	/**装车任务编号*/
	private String loadTaskNo;
	/**制单时间*/
	private Date billingTime;
	/**创建时间*/
	private Date createTime;
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the parentId.
	 *
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	
	/**
	 * Sets the parentId.
	 *
	 * @param parentId the new parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * Gets the 单据级别.
	 *
	 * @return the 单据级别
	 */
	public String getBillLevel() {
		return billLevel;
	}
	
	/**
	 * Sets the 单据级别.
	 *
	 * @param billLevel the new 单据级别
	 */
	public void setBillLevel(String billLevel) {
		this.billLevel = billLevel;
	}
	
	/**
	 * Gets the 单据类型.
	 *
	 * @return the 单据类型
	 */
	public String getBillType() {
		return billType;
	}
	
	/**
	 * Sets the 单据类型.
	 *
	 * @param billType the new 单据类型
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}
	
	/**
	 * Gets the 单据编号.
	 *
	 * @return the 单据编号
	 */
	public String getBillNo() {
		return billNo;
	}
	
	/**
	 * Sets the 单据编号.
	 *
	 * @param billNo the new 单据编号
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	/**
	 * Gets the 分配状态.
	 *
	 * @return the 分配状态
	 */
	public String getAssignState() {
		return assignState;
	}
	
	/**
	 * Sets the 分配状态.
	 *
	 * @param assignState the new 分配状态
	 */
	public void setAssignState(String assignState) {
		this.assignState = assignState;
	}
	
	/**
	 * Gets the 装车任务编号.
	 *
	 * @return the 装车任务编号
	 */
	public String getLoadTaskNo() {
		return loadTaskNo;
	}
	
	/**
	 * Sets the 装车任务编号.
	 *
	 * @param loadTaskNo the new 装车任务编号
	 */
	public void setLoadTaskNo(String loadTaskNo) {
		this.loadTaskNo = loadTaskNo;
	}
	
	/**
	 * Gets the 制单时间.
	 *
	 * @return the 制单时间
	 */
	public Date getBillingTime() {
		return billingTime;
	}
	
	/**
	 * Sets the 制单时间.
	 *
	 * @param billingTime the new 制单时间
	 */
	public void setBillingTime(Date billingTime) {
		this.billingTime = billingTime;
	}

	/**
	 * Gets the 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}