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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/DepartInfoEntity.java
 *  
 *  FILE NAME          :DepartInfoEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class DepartInfoEntity.
 */
public class DepartInfoEntity extends BaseEntity{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3703272448562684594L;

	/** ********出发放行类型***********. */
	private String departType;
	
	/** ********申请部门***********. */
	private String applyOrgCode;
	
	/** ********申请人***********. */
	private String applyUserCode;
	
	/** ********申请时间***********. */
	private Date applyDepartTime;
	
	/** ********放行事项***********. */
	private String departItems;
	
	/** ********保安PDA放行时间***********. */
	private Date pdaDepartTime;
	
	/** ********放行方式***********. */
	private String applyType;
	
	/** ********打印放行条时间***********. */
	private Date manualDepartTime;
	
	/** ********备注***********. */
	private String manualDepartNotes;
	
	/** ********状态***********. */
	private String status;

	/**
	 * Gets the depart type.
	 *
	 * @return the depart type
	 */
	public String getDepartType() {
		return departType;
	}

	/**
	 * Sets the depart type.
	 *
	 * @param departType the new depart type
	 */
	public void setDepartType(String departType) {
		this.departType = departType;
	}

	/**
	 * Gets the apply org code.
	 *
	 * @return the apply org code
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}

	/**
	 * Sets the apply org code.
	 *
	 * @param applyOrgCode the new apply org code
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}

	/**
	 * Gets the apply user code.
	 *
	 * @return the apply user code
	 */
	public String getApplyUserCode() {
		return applyUserCode;
	}

	/**
	 * Sets the apply user code.
	 *
	 * @param applyUserCode the new apply user code
	 */
	public void setApplyUserCode(String applyUserCode) {
		this.applyUserCode = applyUserCode;
	}

	/**
	 * Gets the apply depart time.
	 *
	 * @return the apply depart time
	 */
	public Date getApplyDepartTime() {
		return applyDepartTime;
	}

	/**
	 * Sets the apply depart time.
	 *
	 * @param applyDepartTime the new apply depart time
	 */
	public void setApplyDepartTime(Date applyDepartTime) {
		this.applyDepartTime = applyDepartTime;
	}



	/**
	 * Gets the depart items.
	 *
	 * @return the depart items
	 */
	public String getDepartItems() {
		return departItems;
	}

	/**
	 * Sets the depart items.
	 *
	 * @param departItems the new depart items
	 */
	public void setDepartItems(String departItems) {
		this.departItems = departItems;
	}

	/**
	 * Gets the pda depart time.
	 *
	 * @return the pda depart time
	 */
	public Date getPdaDepartTime() {
		return pdaDepartTime;
	}

	/**
	 * Sets the pda depart time.
	 *
	 * @param pdaDepartTime the new pda depart time
	 */
	public void setPdaDepartTime(Date pdaDepartTime) {
		this.pdaDepartTime = pdaDepartTime;
	}

	/**
	 * Gets the apply type.
	 *
	 * @return the apply type
	 */
	public String getApplyType() {
		return applyType;
	}

	/**
	 * Sets the apply type.
	 *
	 * @param applyType the new apply type
	 */
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	/**
	 * Gets the manual depart time.
	 *
	 * @return the manual depart time
	 */
	public Date getManualDepartTime() {
		return manualDepartTime;
	}

	/**
	 * Sets the manual depart time.
	 *
	 * @param manualDepartTime the new manual depart time
	 */
	public void setManualDepartTime(Date manualDepartTime) {
		this.manualDepartTime = manualDepartTime;
	}

	/**
	 * Gets the manual depart notes.
	 *
	 * @return the manual depart notes
	 */
	public String getManualDepartNotes() {
		return manualDepartNotes;
	}

	/**
	 * Sets the manual depart notes.
	 *
	 * @param manualDepartNotes the new manual depart notes
	 */
	public void setManualDepartNotes(String manualDepartNotes) {
		this.manualDepartNotes = manualDepartNotes;
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
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	

	
}