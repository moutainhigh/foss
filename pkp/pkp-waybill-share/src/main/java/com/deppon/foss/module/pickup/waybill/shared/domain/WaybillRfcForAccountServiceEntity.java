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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillRfcForAccountServiceEntity.java
 * 
 * FILE NAME        	: WaybillRfcForAccountServiceEntity.java
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
 * 官网更改单查询Entity
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-gengzhe,date:2013-1-23 下午3:07:42,
 * </p>
 * 
 * @author foss-gengzhe
 * @date 2013-1-23 下午3:07:42
 * @since
 * @version
 */
public class WaybillRfcForAccountServiceEntity extends BaseEntity {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -954207938975723852L;

	/**
	 * 联系人手机号
	 */
	private String contactHandy;
	/**
	 * 部门标杆编码
	 */
	private String unifieldCode;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 用户名
	 */
	private String applyPerson;
	/**
	 * 联系人姓名
	 */
	private String contactName;
	/**
	 * 运单号
	 */
	private String waybillNumber;
	/**
	 * 修改内容
	 */
	private String changeContent;
	/**
	 * 处理状态
	 */
	private String active;
	/**
	 * 处理时间
	 */
	private Date processTime;
	/**
	 * 处理人name
	 */
	private String processUserName;
	/**
	 * 处理人code
	 */
	private String processUserCode;
	/**
	 * 处理部门name
	 */
	private String processOrgName;
	/**
	 * 处理部门code
	 */
	private String processOrgCode;
	
	/**
	 * ids
	 */
	private String [] ids;

	/**
	 * 获取 联系人手机号.
	 *
	 * @return the 联系人手机号
	 */
	public String getContactHandy() {
		return contactHandy;
	}

	/**
	 * 设置 联系人手机号.
	 *
	 * @param contactHandy the new 联系人手机号
	 */
	public void setContactHandy(String contactHandy) {
		this.contactHandy = contactHandy;
	}

	/**
	 * 获取 部门标杆编码.
	 *
	 * @return the 部门标杆编码
	 */
	public String getUnifieldCode() {
		return unifieldCode;
	}

	/**
	 * 设置 部门标杆编码.
	 *
	 * @param unifieldCode the new 部门标杆编码
	 */
	public void setUnifieldCode(String unifieldCode) {
		this.unifieldCode = unifieldCode;
	}

	/**
	 * 获取 申请时间.
	 *
	 * @return the 申请时间
	 */
	public Date getApplyTime() {
		return applyTime;
	}

	/**
	 * 设置 申请时间.
	 *
	 * @param applyTime the new 申请时间
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	/**
	 * 获取 用户名.
	 *
	 * @return the 用户名
	 */
	public String getApplyPerson() {
		return applyPerson;
	}

	/**
	 * 设置 用户名.
	 *
	 * @param applyPerson the new 用户名
	 */
	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}

	/**
	 * 获取 联系人姓名.
	 *
	 * @return the 联系人姓名
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * 设置 联系人姓名.
	 *
	 * @param contactName the new 联系人姓名
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNumber() {
		return waybillNumber;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param waybillNumber the new 运单号
	 */
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	/**
	 * 获取 修改内容.
	 *
	 * @return the 修改内容
	 */
	public String getChangeContent() {
		return changeContent;
	}

	/**
	 * 设置 修改内容.
	 *
	 * @param changeContent the new 修改内容
	 */
	public void setChangeContent(String changeContent) {
		this.changeContent = changeContent;
	}

	/**
	 * 获取 处理状态.
	 *
	 * @return the 处理状态
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 处理状态.
	 *
	 * @param active the new 处理状态
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 获取 处理时间.
	 *
	 * @return the 处理时间
	 */
	public Date getProcessTime() {
		return processTime;
	}

	/**
	 * 设置 处理时间.
	 *
	 * @param processTime the new 处理时间
	 */
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	/**
	 * 获取 处理人name.
	 *
	 * @return the 处理人name
	 */
	public String getProcessUserName() {
		return processUserName;
	}

	/**
	 * 设置 处理人name.
	 *
	 * @param processUserName the new 处理人name
	 */
	public void setProcessUserName(String processUserName) {
		this.processUserName = processUserName;
	}

	/**
	 * 获取 处理人code.
	 *
	 * @return the 处理人code
	 */
	public String getProcessUserCode() {
		return processUserCode;
	}

	/**
	 * 设置 处理人code.
	 *
	 * @param processUserCode the new 处理人code
	 */
	public void setProcessUserCode(String processUserCode) {
		this.processUserCode = processUserCode;
	}

	/**
	 * 获取 处理部门name.
	 *
	 * @return the 处理部门name
	 */
	public String getProcessOrgName() {
		return processOrgName;
	}

	/**
	 * 设置 处理部门name.
	 *
	 * @param processOrgName the new 处理部门name
	 */
	public void setProcessOrgName(String processOrgName) {
		this.processOrgName = processOrgName;
	}

	/**
	 * 获取 处理部门code.
	 *
	 * @return the 处理部门code
	 */
	public String getProcessOrgCode() {
		return processOrgCode;
	}

	/**
	 * 设置 处理部门code.
	 *
	 * @param processOrgCode the new 处理部门code
	 */
	public void setProcessOrgCode(String processOrgCode) {
		this.processOrgCode = processOrgCode;
	}

	/**
	 * 
	 *序列化 id
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the ids
	 */
	public String[] getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}
}