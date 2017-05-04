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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/domain/CertificatebagTakeEntity.java
 *  
 *  FILE NAME          :CertificatebagTakeEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 证件包领取entity
 * @author 099197-foss-liming
 * @date 2012-11-07 下午4:49:47
 */
public class CertificatebagTakeEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4266960095883125003L;

	/**
	 * id
	 */
	private String id;//

	/**
	 * 证件类型
	 */
	private String type;// 
	
	/**
	 * 证件包动作ID
	 */
	private String certificatebagActionId;//

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String createUserCode;

	/**
	 * 创建人姓名
	 */
	private String createUserName;

	/**
	 * 创建人部门
	 */
	private String createOrgCode;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 修改人
	 */
	private String updateUserCode;

	/**
	 * 修改人名字
	 */
	private String updateUserName;

	/**
	 * 修改人部门
	 */
	private String updateOrgCode;

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 证件类型.
	 *
	 * @return the 证件类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置 证件类型.
	 *
	 * @param type the new 证件类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取 证件包动作ID.
	 *
	 * @return the 证件包动作ID
	 */
	public String getCertificatebagActionId() {
		return certificatebagActionId;
	}

	/**
	 * 设置 证件包动作ID.
	 *
	 * @param certificatebagActionId the new 证件包动作ID
	 */
	public void setCertificatebagActionId(String certificatebagActionId) {
		this.certificatebagActionId = certificatebagActionId;
	}

	/**
	 * 获取 状态.
	 *
	 * @return the 状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置 状态.
	 *
	 * @param status the new 状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 创建人.
	 *
	 * @return the 创建人
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * 设置 创建人.
	 *
	 * @param createUserCode the new 创建人
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * 获取 创建人姓名.
	 *
	 * @return the 创建人姓名
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置 创建人姓名.
	 *
	 * @param createUserName the new 创建人姓名
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * 获取 创建人部门.
	 *
	 * @return the 创建人部门
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置 创建人部门.
	 *
	 * @param createOrgCode the new 创建人部门
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 修改时间.
	 *
	 * @return the 修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置 修改时间.
	 *
	 * @param updateTime the new 修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取 修改人.
	 *
	 * @return the 修改人
	 */
	public String getUpdateUserCode() {
		return updateUserCode;
	}

	/**
	 * 设置 修改人.
	 *
	 * @param updateUserCode the new 修改人
	 */
	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
	}

	/**
	 * 获取 修改人名字.
	 *
	 * @return the 修改人名字
	 */
	public String getUpdateUserName() {
		return updateUserName;
	}

	/**
	 * 设置 修改人名字.
	 *
	 * @param updateUserName the new 修改人名字
	 */
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	/**
	 * 获取 修改人部门.
	 *
	 * @return the 修改人部门
	 */
	public String getUpdateOrgCode() {
		return updateOrgCode;
	}

	/**
	 * 设置 修改人部门.
	 *
	 * @param updateOrgCode the new 修改人部门
	 */
	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}

	
}