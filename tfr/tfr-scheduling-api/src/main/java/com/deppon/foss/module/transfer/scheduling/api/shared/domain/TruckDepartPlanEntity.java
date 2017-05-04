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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/TruckDepartPlanEntity.java
 * 
 *  FILE NAME     :TruckDepartPlanEntity.java
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

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 发车计划实体
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-12-25 下午5:40:24
 */
public class TruckDepartPlanEntity extends BaseEntity {

	private static final long serialVersionUID = 7096252159426246858L;

	/**
	 * TFR.T_OPT_TRUCK_DEPART_PLAN.ID
	 * 
	 * @mbggenerated Wed Nov 21 17:08:23 CST 2012
	 */
	private String id;

	/**
	 * 出发部门
	 * 
	 * @mbggenerated Wed Nov 21 17:08:23 CST 2012
	 */
	private String origOrgCode;

	/**
	 * 到达部门
	 * 
	 * @mbggenerated Wed Nov 21 17:08:23 CST 2012
	 */
	private String destOrgCode;

	/**
	 * 计划日期
	 * 
	 * @mbggenerated Wed Nov 21 17:08:23 CST 2012
	 */
	private Date planDate;

	/**
	 * 计划类型
	 * 
	 * @mbggenerated Wed Nov 21 17:08:23 CST 2012
	 */
	private String planType;

	/**
	 * 是否异常
	 * 
	 * @mbggenerated Wed Nov 21 17:08:23 CST 2012
	 */
	private String isIssue;

	/**
	 * 备注
	 * 
	 * @mbggenerated Wed Nov 21 17:08:23 CST 2012
	 */
	private String notes;

	/**
	 * 状态
	 * 
	 * @mbggenerated Wed Nov 21 17:08:23 CST 2012
	 */
	private String status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人编码
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
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 更新用户编码
	 */
	private String updateUserCode;

	/**
	 * 更新用户名
	 */
	private String updateUserName;

	/**
	 * 更新人部门
	 */
	private String updateOrgCode;

	/**
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-25 下午5:33:49
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-25 下午5:33:49
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 出发部门.
	 * 
	 * @return the 出发部门
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * 设置 出发部门.
	 * 
	 * @param origOrgCode
	 *            the new 出发部门
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * 获取 到达部门.
	 * 
	 * @return the 到达部门
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * 设置 到达部门.
	 * 
	 * @param destOrgCode
	 *            the new 到达部门
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * 获取 计划日期.
	 * 
	 * @return the 计划日期
	 */
	@DateFormat(formate = "yyyy-MM-dd")
	public Date getPlanDate() {
		return planDate;
	}

	/**
	 * 设置 计划日期.
	 * 
	 * @param planDate
	 *            the new 计划日期
	 */
	@DateFormat(formate = "yyyy-MM-dd")
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	/**
	 * 获取 计划类型.
	 * 
	 * @return the 计划类型
	 */
	public String getPlanType() {
		return planType;
	}

	/**
	 * 设置 计划类型.
	 * 
	 * @param planType
	 *            the new 计划类型
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	/**
	 * 获取 是否异常.
	 * 
	 * @return the 是否异常
	 */
	public String getIsIssue() {
		return isIssue;
	}

	/**
	 * 设置 是否异常.
	 * 
	 * @param isIssue
	 *            the new 是否异常
	 */
	public void setIsIssue(String isIssue) {
		this.isIssue = isIssue;
	}

	/**
	 * 获取 备注.
	 * 
	 * @return the 备注
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * 设置 备注.
	 * 
	 * @param notes
	 *            the new 备注
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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
	 * @param status
	 *            the new 状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取 创建时间.
	 * 
	 * @return the 创建时间
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间.
	 * 
	 * @param createTime
	 *            the new 创建时间
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 创建人编码.
	 * 
	 * @return the 创建人编码
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * 设置 创建人编码.
	 * 
	 * @param createUserCode
	 *            the new 创建人编码
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
	 * @param createUserName
	 *            the new 创建人姓名
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
	 * @param createOrgCode
	 *            the new 创建人部门
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 更新时间.
	 * 
	 * @return the 更新时间
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置 更新时间.
	 * 
	 * @param updateTime
	 *            the new 更新时间
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取 更新用户编码.
	 * 
	 * @return the 更新用户编码
	 */
	public String getUpdateUserCode() {
		return updateUserCode;
	}

	/**
	 * 设置 更新用户编码.
	 * 
	 * @param updateUserCode
	 *            the new 更新用户编码
	 */
	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
	}

	/**
	 * 获取 更新用户名.
	 * 
	 * @return the 更新用户名
	 */
	public String getUpdateUserName() {
		return updateUserName;
	}

	/**
	 * 设置 更新用户名.
	 * 
	 * @param updateUserName
	 *            the new 更新用户名
	 */
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	/**
	 * 获取 更新人部门.
	 * 
	 * @return the 更新人部门
	 */
	public String getUpdateOrgCode() {
		return updateOrgCode;
	}

	/**
	 * 设置 更新人部门.
	 * 
	 * @param updateOrgCode
	 *            the new 更新人部门
	 */
	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}

}