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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/DriverDto.java
 * 
 * FILE NAME        	: DriverDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.util.List;

/**
 * 公司司机Dto.
 *
 * @author ibm-
 * 		wangxiexu
 * @date 2012-12-29 
 * 		上午12:21:52
 * @since
 * @version
 */
public class DriverDto {
	
	/** 司机工号. */
	private String empCode;

	/** 司机姓名. */
	private String empName;

	/** 司机电话. */
	private String empPhone;

	/** 所属部门. */
	private String orgId;
	
	/** 所属部门List */
	private List<String> orgIdList;

	/** 是否启用. */
	private String active;

	/** 是否PDA签到. */
	private String pdaSigned;
	
	/** 物流班车. */
	private String shuttlefleettype;
	
	/** 接送货车. */
	private String goodsfleettype;
	
	/** 部门codeList */
	private List<String> subOrgCodeList;

	
	public String getShuttlefleettype() {
		return shuttlefleettype;
	}

	public void setShuttlefleettype(String shuttlefleettype) {
		this.shuttlefleettype = shuttlefleettype;
	}

	public String getGoodsfleettype() {
		return goodsfleettype;
	}

	public void setGoodsfleettype(String goodsfleettype) {
		this.goodsfleettype = goodsfleettype;
	}

	/**
	 * Gets the emp code.
	 *
	 * @return the emp code
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * Sets the emp code.
	 *
	 * @param empCode the new emp code
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * Gets the emp name.
	 *
	 * @return the emp name
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * Sets the emp name.
	 *
	 * @param empName the new emp name
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * Gets the emp phone.
	 *
	 * @return the emp phone
	 */
	public String getEmpPhone() {
		return empPhone;
	}

	/**
	 * Sets the emp phone.
	 *
	 * @param empPhone the new emp phone
	 */
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	/**
	 * Gets the org id.
	 *
	 * @return the org id
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * Sets the org id.
	 *
	 * @param orgId the new org id
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the pda signed.
	 *
	 * @return the pda signed
	 */
	public String getPdaSigned() {
		return pdaSigned;
	}

	/**
	 * Sets the pda signed.
	 *
	 * @param pdaSigned the new pda signed
	 */
	public void setPdaSigned(String pdaSigned) {
		this.pdaSigned = pdaSigned;
	}

	public List<String> getSubOrgCodeList() {
		return subOrgCodeList;
	}

	public void setSubOrgCodeList(List<String> subOrgCodeList) {
		this.subOrgCodeList = subOrgCodeList;
	}

	public List<String> getOrgIdList() {
		return orgIdList;
	}

	public void setOrgIdList(List<String> orgIdList) {
		this.orgIdList = orgIdList;
	}
	
}