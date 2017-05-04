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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/TruckSchedulingEntity.java
 * 
 *  FILE NAME     :TruckSchedulingEntity.java
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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 短途排班表
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-24 下午4:33:31
 */
public class TruckSchedulingEntity extends BaseEntity {

	private static final long serialVersionUID = 3103979655505852873L;

	/**
	 * 日期SCHEDULING_DATE
	 */
	private Date schedulingDate;

	/**
	 * 司机代码DRIVER_CODE
	 */
	private String driverCode;

	/**
	 * 工作类别PLAN_TYPE
	 */
	private String planType;

	/**
	 * 司机所属部门DRIVER_ORG_CODE
	 */
	private String driverOrgCode;

	/**
	 * 司机电话DRIVER_PHONE
	 */
	private String driverPhone;

	/**
	 * 计划状态SCHEDULING_STATUS
	 */
	private String schedulingStatus;
	/**
	 * 创建时间CREATE_TIME
	 */
	private Date createTime;

	/**
	 * 创建人代码CREATE_USER_CODE
	 */
	private String createUserCode;

	/**
	 * 创建人姓名CREATE_USER_NAME
	 */
	private String createUserName;

	/**
	 * 创建人所属部门代码CREATE_ORG_CODE
	 */
	private String createOrgCode;

	/**
	 * 最近更新时间UPDATE_TIME
	 */
	private Date updateTime;

	/**
	 * 更新人代码UPDATE_USER_CODE
	 */
	private String updateUserCode;

	/**
	 * 更新人姓名UPDATE_USER_NAME
	 */
	private String updateUserName;

	/**
	 * 更新人部门代码UPDATE_ORG_CODE
	 */
	private String updateOrgCode;

	/**
	 * 日期数字DATE_NUM
	 */
	private int dateNum;

	/**
	 * 年月YEAR_MONTH
	 */
	private String yearMonth;

	/**
	 * 排班类型（1短途排班2接送货排班） SCHEDULING_TYPE
	 */
	private String schedulingType;
	/**
	 * 排班部门编码
	 */
	private String schedulingDepartCode;

	/**
	 * 复制排班年月
	 * */
	private String copyYearMonth;
	
	
	
	
	
	
	public String getCopyYearMonth() {
		return copyYearMonth;
	}

	public void setCopyYearMonth(String copyYearMonth) {
		this.copyYearMonth = copyYearMonth;
	}

	/**
	 * 获取 日期SCHEDULING_DATE.
	 * 
	 * @return the 日期SCHEDULING_DATE
	 */
	public Date getSchedulingDate() {
		return schedulingDate;
	}

	/**
	 * 设置 日期SCHEDULING_DATE.
	 * 
	 * @param schedulingDate
	 *            the new 日期SCHEDULING_DATE
	 */
	public void setSchedulingDate(Date schedulingDate) {
		this.schedulingDate = schedulingDate;
	}

	/**
	 * 获取 司机代码DRIVER_CODE.
	 * 
	 * @return the 司机代码DRIVER_CODE
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 设置 司机代码DRIVER_CODE.
	 * 
	 * @param driverCode
	 *            the new 司机代码DRIVER_CODE
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * 获取 工作类别PLAN_TYPE.
	 * 
	 * @return the 工作类别PLAN_TYPE
	 */
	public String getPlanType() {
		return planType;
	}

	/**
	 * 设置 工作类别PLAN_TYPE.
	 * 
	 * @param planType
	 *            the new 工作类别PLAN_TYPE
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	/**
	 * 获取 司机所属部门DRIVER_ORG_CODE.
	 * 
	 * @return the 司机所属部门DRIVER_ORG_CODE
	 */
	public String getDriverOrgCode() {
		return driverOrgCode;
	}

	/**
	 * 设置 司机所属部门DRIVER_ORG_CODE.
	 * 
	 * @param driverOrgCode
	 *            the new 司机所属部门DRIVER_ORG_CODE
	 */
	public void setDriverOrgCode(String driverOrgCode) {
		this.driverOrgCode = driverOrgCode;
	}

	/**
	 * 获取 司机电话DRIVER_PHONE.
	 * 
	 * @return the 司机电话DRIVER_PHONE
	 */
	public String getDriverPhone() {
		return driverPhone;
	}

	/**
	 * 设置 司机电话DRIVER_PHONE.
	 * 
	 * @param driverPhone
	 *            the new 司机电话DRIVER_PHONE
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	/**
	 * 获取 计划状态SCHEDULING_STATUS.
	 * 
	 * @return the 计划状态SCHEDULING_STATUS
	 */
	public String getSchedulingStatus() {
		return schedulingStatus;
	}

	/**
	 * 设置 计划状态SCHEDULING_STATUS.
	 * 
	 * @param schedulingStatus
	 *            the new 计划状态SCHEDULING_STATUS
	 */
	public void setSchedulingStatus(String schedulingStatus) {
		this.schedulingStatus = schedulingStatus;
	}

	/**
	 * 获取 创建时间CREATE_TIME.
	 * 
	 * @return the 创建时间CREATE_TIME
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间CREATE_TIME.
	 * 
	 * @param createTime
	 *            the new 创建时间CREATE_TIME
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 创建人代码CREATE_USER_CODE.
	 * 
	 * @return the 创建人代码CREATE_USER_CODE
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * 设置 创建人代码CREATE_USER_CODE.
	 * 
	 * @param createUserCode
	 *            the new 创建人代码CREATE_USER_CODE
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * 获取 创建人姓名CREATE_USER_NAME.
	 * 
	 * @return the 创建人姓名CREATE_USER_NAME
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置 创建人姓名CREATE_USER_NAME.
	 * 
	 * @param createUserName
	 *            the new 创建人姓名CREATE_USER_NAME
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * 获取 创建人所属部门代码CREATE_ORG_CODE.
	 * 
	 * @return the 创建人所属部门代码CREATE_ORG_CODE
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置 创建人所属部门代码CREATE_ORG_CODE.
	 * 
	 * @param createOrgCode
	 *            the new 创建人所属部门代码CREATE_ORG_CODE
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 最近更新时间UPDATE_TIME.
	 * 
	 * @return the 最近更新时间UPDATE_TIME
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置 最近更新时间UPDATE_TIME.
	 * 
	 * @param updateTime
	 *            the new 最近更新时间UPDATE_TIME
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取 更新人代码UPDATE_USER_CODE.
	 * 
	 * @return the 更新人代码UPDATE_USER_CODE
	 */
	public String getUpdateUserCode() {
		return updateUserCode;
	}

	/**
	 * 设置 更新人代码UPDATE_USER_CODE.
	 * 
	 * @param updateUserCode
	 *            the new 更新人代码UPDATE_USER_CODE
	 */
	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
	}

	/**
	 * 获取 更新人姓名UPDATE_USER_NAME.
	 * 
	 * @return the 更新人姓名UPDATE_USER_NAME
	 */
	public String getUpdateUserName() {
		return updateUserName;
	}

	/**
	 * 设置 更新人姓名UPDATE_USER_NAME.
	 * 
	 * @param updateUserName
	 *            the new 更新人姓名UPDATE_USER_NAME
	 */
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	/**
	 * 获取 更新人部门代码UPDATE_ORG_CODE.
	 * 
	 * @return the 更新人部门代码UPDATE_ORG_CODE
	 */
	public String getUpdateOrgCode() {
		return updateOrgCode;
	}

	/**
	 * 设置 更新人部门代码UPDATE_ORG_CODE.
	 * 
	 * @param updateOrgCode
	 *            the new 更新人部门代码UPDATE_ORG_CODE
	 */
	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}

	/**
	 * 获取 日期数字DATE_NUM.
	 * 
	 * @return the 日期数字DATE_NUM
	 */
	public int getDateNum() {
		return dateNum;
	}

	/**
	 * 设置 日期数字DATE_NUM.
	 * 
	 * @param dateNum
	 *            the new 日期数字DATE_NUM
	 */
	public void setDateNum(int dateNum) {
		this.dateNum = dateNum;
	}

	/**
	 * 获取 年月YEAR_MONTH.
	 * 
	 * @return the 年月YEAR_MONTH
	 */
	public String getYearMonth() {
		return yearMonth;
	}

	/**
	 * 设置 年月YEAR_MONTH.
	 * 
	 * @param yearMonth
	 *            the new 年月YEAR_MONTH
	 */
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	/**
	 * 获取 排班类型（1短途排班2接送货排班） SCHEDULING_TYPE.
	 * 
	 * @return the 排班类型（1短途排班2接送货排班） SCHEDULING_TYPE
	 */
	public String getSchedulingType() {
		return schedulingType;
	}

	/**
	 * 设置 排班类型（1短途排班2接送货排班） SCHEDULING_TYPE.
	 * 
	 * @param schedulingType
	 *            the new 排班类型（1短途排班2接送货排班） SCHEDULING_TYPE
	 */
	public void setSchedulingType(String schedulingType) {
		this.schedulingType = schedulingType;
	}

	/**
	 * 获取 排班部门编码.
	 *
	 * @return the 排班部门编码
	 */
	public String getSchedulingDepartCode() {
		return schedulingDepartCode;
	}

	/**
	 * 设置 排班部门编码.
	 *
	 * @param schedulingDepartCode the new 排班部门编码
	 */
	public void setSchedulingDepartCode(String schedulingDepartCode) {
		this.schedulingDepartCode = schedulingDepartCode;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(
				"TruckSchedulingEntity [schedulingDate=" + schedulingDate + ", driverCode=" + driverCode
						+ ", planType=" + planType + ", driverOrgCode=" + driverOrgCode + ", driverPhone="
						+ driverPhone + ", schedulingStatus=" + schedulingStatus + ", createTime=" + createTime
						+ ", createUserCode=" + createUserCode + ", createUserName=" + createUserName
						+ ", createOrgCode=" + createOrgCode + ", updateTime=" + updateTime + ", updateUserCode="
						+ updateUserCode + ", updateUserName=" + updateUserName + ", updateOrgCode=" + updateOrgCode
						+ ", dateNum=" + dateNum + ", yearMonth=" + yearMonth + ", schedulingType=" + schedulingType
						+ "]").toString();
	}

}