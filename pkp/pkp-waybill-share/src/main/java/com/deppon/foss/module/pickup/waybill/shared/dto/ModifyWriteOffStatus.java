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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/ModifyWriteOffStatus.java
 * 
 * FILE NAME        	: ModifyWriteOffStatus.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

/**
 * 更改核销状态
 * 
 * @author 026113-foss-linwensheng
 * 
 */
public class ModifyWriteOffStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 更改单ID集合
	private List<String> waybillChangIDs;
	// 备注
	private String writeOffNote;
	// 核销状态
	private String writeoffStatus;
	//操作人 
	private EmployeeEntity emp; 

	//核销/反核销时间
	private Date writeOffTime;
	
	//操作部门
	
	OrgAdministrativeInfoEntity depart ;

	

	public EmployeeEntity getEmp() {
		return emp;
	}

	public void setEmp(EmployeeEntity emp) {
		this.emp = emp;
	}

	public OrgAdministrativeInfoEntity getDepart() {
		return depart;
	}

	public void setDepart(OrgAdministrativeInfoEntity depart) {
		this.depart = depart;
	}

	public String getWriteoffStatus() {
		return writeoffStatus;
	}

	public void setWriteoffStatus(String writeoffStatus) {
		this.writeoffStatus = writeoffStatus;
	}

	public List<String> getWaybillChangIDs() {
		return waybillChangIDs;
	}

	public void setWaybillChangIDs(List<String> waybillChangIDs) {
		this.waybillChangIDs = waybillChangIDs;
	}

	public String getWriteOffNote() {
		return writeOffNote;
	}

	public void setWriteOffNote(String writeOffNote) {
		this.writeOffNote = writeOffNote;
	}

	public Date getWriteOffTime() {
		return writeOffTime;
	}

	public void setWriteOffTime(Date writeOffTime) {
		this.writeOffTime = writeOffTime;
	}

}