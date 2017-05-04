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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/BorrowVehicleEntity.java
 * 
 *  FILE NAME     :BorrowVehicleEntity.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 借车申请实体
 * @author 104306-foss-wangLong
 * @date 2012-12-03 下午1:06:56
 */
public class BorrowVehicleEntity extends BaseEntity {
	
	private static final long serialVersionUID = 5517223836501784L;
		
	/** 借车编号 */
	private String borrowNo;
	
	/** 申请申请时间 */
	private Date applyTime;

	/** 使用用途 */
	private String borrowPurpose;
		
	/** 使用类型  */
	private String useType;
		
	/** 车型 */
	private String orderVehicleModel;
		
	/** 受理部门编码  */
	private String auditOrgCode;
		
	/** 受理部门名称 */
	private String auditOrgName;
		
	/** 备注 */
	private String notes;
		
	/** 货物重量 */
	private BigDecimal weight;
		
	/** 货物体积 */
	private BigDecimal volume;
		
	/** 借车开始时间 */
	private Date borrowBeginTime;
		
	/** 借车结束时间 */
	private Date borrowEndTime;
		
	/** 申请人员编码 */
	private String applyEmpCode;
		
	/** 申请人员名称 */
	private String applyEmpName;

	/** 固定电话 */
	private String telephoneNo;
		
	/** 手机 */
	private String mobilephoneNo;
		
	/** 借车部门编码 */
	private String applyOrgCode;
		
	/** 借车部门名称 */
	private String applyOrgName;
		
	/** 借车状态 */
	private String status;
		
	/**
	 * 获得borrowNo
	 * @return the borrowNo
	 */
	public String getBorrowNo() {
		return borrowNo;
	}
	
	/**
	 * 获得applyTime
	 * @return the applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}
	
	/**
	 * 获得borrowPurpose
	 * @return the borrowPurpose
	 */
	public String getBorrowPurpose() {
		return borrowPurpose;
	}
	
	/**
	 * 获得useType
	 * @return the useType
	 */
	public String getUseType() {
		return useType;
	}
	
	/**
	 * 获得orderVehicleModel
	 * @return the orderVehicleModel
	 */
	public String getOrderVehicleModel() {
		return orderVehicleModel;
	}
	
	/**
	 * 获得auditOrgCode
	 * @return the auditOrgCode
	 */
	public String getAuditOrgCode() {
		return auditOrgCode;
	}
	
	/**
	 * 获得auditOrgName
	 * @return the auditOrgName
	 */
	public String getAuditOrgName() {
		return auditOrgName;
	}
	
	/**
	 * 获得notes
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * 获得weight
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	
	/**
	 * 获得volume
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	
	/**
	 * 获得borrowBeginTime
	 * @return the borrowBeginTime
	 */
	public Date getBorrowBeginTime() {
		return borrowBeginTime;
	}
	
	/**
	 * 获得borrowEndTime
	 * @return the borrowEndTime
	 */
	public Date getBorrowEndTime() {
		return borrowEndTime;
	}
	
	/**
	 * 获得applyEmpCode
	 * @return the applyEmpCode
	 */
	public String getApplyEmpCode() {
		return applyEmpCode;
	}
	
	/**
	 * 获得applyEmpName
	 * @return the applyEmpName
	 */
	public String getApplyEmpName() {
		return applyEmpName;
	}
	
	/**
	 * 获得telephoneNo
	 * @return the telephoneNo
	 */
	public String getTelephoneNo() {
		return telephoneNo;
	}
	
	/**
	 * 获得mobilephoneNo
	 * @return the mobilephoneNo
	 */
	public String getMobilephoneNo() {
		return mobilephoneNo;
	}
	
	/**
	 * 获得applyOrgCode
	 * @return the applyOrgCode
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}
	
	/**
	 * 获得applyOrgName
	 * @return the applyOrgName
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}
	
	/**
	 * 获得status
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
		/**
	 * 设置borrowNo
	 * @param borrowNo the borrowNo to set
	 */
	public void setBorrowNo(String borrowNo) {
		this.borrowNo = borrowNo;
	}
	
	/**
	 * 设置applyTime
	 * @param applyTime the applyTime to set
	 */
	@DateFormat
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	/**
	 * 设置borrowPurpose
	 * @param borrowPurpose the borrowPurpose to set
	 */
	public void setBorrowPurpose(String borrowPurpose) {
		this.borrowPurpose = borrowPurpose;
	}
	
	/**
	 * 设置useType
	 * @param useType the useType to set
	 */
	public void setUseType(String useType) {
		this.useType = useType;
	}
	
	/**
	 * 设置orderVehicleModel
	 * @param orderVehicleModel the orderVehicleModel to set
	 */
	public void setOrderVehicleModel(String orderVehicleModel) {
		this.orderVehicleModel = orderVehicleModel;
	}
	
	/**
	 * 设置auditOrgCode
	 * @param auditOrgCode the auditOrgCode to set
	 */
	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}
	
	/**
	 * 设置auditOrgName
	 * @param auditOrgName the auditOrgName to set
	 */
	public void setAuditOrgName(String auditOrgName) {
		this.auditOrgName = auditOrgName;
	}
	
	/**
	 * 设置notes
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * 设置weight
	 * @param weight the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	/**
	 * 设置volume
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	
	/**
	 * 设置borrowBeginTime
	 * @param borrowBeginTime the borrowBeginTime to set
	 */
	@DateFormat
	public void setBorrowBeginTime(Date borrowBeginTime) {
		this.borrowBeginTime = borrowBeginTime;
	}
	
	/**
	 * 设置borrowEndTime
	 * @param borrowEndTime the borrowEndTime to set
	 */
	@DateFormat
	public void setBorrowEndTime(Date borrowEndTime) {
		this.borrowEndTime = borrowEndTime;
	}
	
	/**
	 * 设置applyEmpCode
	 * @param applyEmpCode the applyEmpCode to set
	 */
	public void setApplyEmpCode(String applyEmpCode) {
		this.applyEmpCode = applyEmpCode;
	}
	
	/**
	 * 设置applyEmpName
	 * @param applyEmpName the applyEmpName to set
	 */
	public void setApplyEmpName(String applyEmpName) {
		this.applyEmpName = applyEmpName;
	}
	
	/**
	 * 设置telephoneNo
	 * @param telephoneNo the telephoneNo to set
	 */
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	
	/**
	 * 设置mobilephoneNo
	 * @param mobilephoneNo the mobilephoneNo to set
	 */
	public void setMobilephoneNo(String mobilephoneNo) {
		this.mobilephoneNo = mobilephoneNo;
	}
	
	/**
	 * 设置applyOrgCode
	 * @param applyOrgCode the applyOrgCode to set
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}
	
	/**
	 * 设置applyOrgName
	 * @param applyOrgName the applyOrgName to set
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}
	
	/**
	 * 设置status
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
		
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[borrowNo,").append(borrowNo).append("]");
		stringBuilder.append("[applyTime,").append(applyTime).append("]");
		stringBuilder.append("[borrowPurpose,").append(borrowPurpose).append("]");
		stringBuilder.append("[useType,").append(useType).append("]");
		stringBuilder.append("[orderVehicleModel,").append(orderVehicleModel).append("]");
		stringBuilder.append("[auditOrgCode,").append(auditOrgCode).append("]");
		stringBuilder.append("[auditOrgName,").append(auditOrgName).append("]");
		stringBuilder.append("[notes,").append(notes).append("]");
		stringBuilder.append("[weight,").append(weight).append("]");
		stringBuilder.append("[volume,").append(volume).append("]");
		stringBuilder.append("[borrowBeginTime,").append(borrowBeginTime).append("]");
		stringBuilder.append("[borrowEndTime,").append(borrowEndTime).append("]");
		stringBuilder.append("[applyEmpCode,").append(applyEmpCode).append("]");
		stringBuilder.append("[applyEmpName,").append(applyEmpName).append("]");
		stringBuilder.append("[telephoneNo,").append(telephoneNo).append("]");
		stringBuilder.append("[mobilephoneNo,").append(mobilephoneNo).append("]");
		stringBuilder.append("[applyOrgCode,").append(applyOrgCode).append("]");
		stringBuilder.append("[applyOrgName,").append(applyOrgName).append("]");
		stringBuilder.append("[status,").append(status).append("]");
		return stringBuilder.toString();
	}
}