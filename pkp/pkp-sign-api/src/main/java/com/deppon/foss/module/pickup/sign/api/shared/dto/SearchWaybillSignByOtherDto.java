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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/SearchWaybillSignByOtherDto.java
 * 
 * FILE NAME        	: SearchWaybillSignByOtherDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.server.web.upload.AttachementEntity;

public class SearchWaybillSignByOtherDto implements java.io.Serializable {

	private static final long serialVersionUID = 1372509362360011358L;

	// 运单号
	private String waybillNo;

	// 经理受权密码
	private String authorizationCode;

	// 收货人电话
	private String receiveCustomerPhone;

	// 真实收货人
	private String receiveCustomerContact;

	// 当前部门
	private String currentDeptCode;
	
	/**
	 * 上传Dto
	 */
	private List<AttachementEntity> files = new ArrayList<AttachementEntity>();
	

	/**
	 * @return the files
	 */
	public List<AttachementEntity> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(List<AttachementEntity> files) {
		this.files = files;
	}

	/**
	 * @return the currentDeptCode
	 */
	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	/**
	 * @param currentDeptCode the currentDeptCode to see
	 */
	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	/**
	 * @return the receiveCustomerPhone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * @param receiveCustomerPhone the receiveCustomerPhone to see
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * @return the receiveCustomerContact
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * @param receiveCustomerContact the receiveCustomerContact to see
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the authorizationCode
	 */
	public String getAuthorizationCode() {
		return authorizationCode;
	}

	/**
	 * @param authorizationCode the authorizationCode to see
	 */
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

}