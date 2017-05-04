/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/domain/TruckSchedulingEntity.java
 * 
 * FILE NAME        	: TruckSchedulingEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.domain;

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
	 * @return the schedulingDate
	 */
	public Date getSchedulingDate() {
		return schedulingDate;
	}

	/**
	 * @param schedulingDate the schedulingDate to set
	 */
	public void setSchedulingDate(Date schedulingDate) {
		this.schedulingDate = schedulingDate;
	}

	/**
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * @param driverCode the driverCode to set
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * @return the planType
	 */
	public String getPlanType() {
		return planType;
	}

	/**
	 * @param planType the planType to set
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}

	/**
	 * @return the driverOrgCode
	 */
	public String getDriverOrgCode() {
		return driverOrgCode;
	}

	/**
	 * @param driverOrgCode the driverOrgCode to set
	 */
	public void setDriverOrgCode(String driverOrgCode) {
		this.driverOrgCode = driverOrgCode;
	}

	/**
	 * @return the driverPhone
	 */
	public String getDriverPhone() {
		return driverPhone;
	}

	/**
	 * @param driverPhone the driverPhone to set
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	/**
	 * @return the schedulingStatus
	 */
	public String getSchedulingStatus() {
		return schedulingStatus;
	}

	/**
	 * @param schedulingStatus the schedulingStatus to set
	 */
	public void setSchedulingStatus(String schedulingStatus) {
		this.schedulingStatus = schedulingStatus;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode the createOrgCode to set
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the updateUserCode
	 */
	public String getUpdateUserCode() {
		return updateUserCode;
	}

	/**
	 * @param updateUserCode the updateUserCode to set
	 */
	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
	}

	/**
	 * @return the updateUserName
	 */
	public String getUpdateUserName() {
		return updateUserName;
	}

	/**
	 * @param updateUserName the updateUserName to set
	 */
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	/**
	 * @return the updateOrgCode
	 */
	public String getUpdateOrgCode() {
		return updateOrgCode;
	}

	/**
	 * @param updateOrgCode the updateOrgCode to set
	 */
	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}

	/**
	 * @return the dateNum
	 */
	public int getDateNum() {
		return dateNum;
	}

	/**
	 * @param dateNum the dateNum to set
	 */
	public void setDateNum(int dateNum) {
		this.dateNum = dateNum;
	}

	/**
	 * @return the yearMonth
	 */
	public String getYearMonth() {
		return yearMonth;
	}

	/**
	 * @param yearMonth the yearMonth to set
	 */
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	/**
	 * @return the schedulingType
	 */
	public String getSchedulingType() {
		return schedulingType;
	}

	/**
	 * @param schedulingType the schedulingType to set
	 */
	public void setSchedulingType(String schedulingType) {
		this.schedulingType = schedulingType;
	}

	/**
	 * 重写toString方法
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 上午9:50:27
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(
				"TruckSchedulingEntity [schedulingDate=" + schedulingDate + ", driverCode=" + driverCode + ", planType=" + planType + ", driverOrgCode=" + driverOrgCode + ", driverPhone="
						+ driverPhone + ", schedulingStatus=" + schedulingStatus + ", createTime=" + createTime + ", createUserCode=" + createUserCode + ", createUserName=" + createUserName
						+ ", createOrgCode=" + createOrgCode + ", updateTime=" + updateTime + ", updateUserCode=" + updateUserCode + ", updateUserName=" + updateUserName + ", updateOrgCode="
						+ updateOrgCode + ", dateNum=" + dateNum + ", yearMonth=" + yearMonth + ", schedulingType=" + schedulingType + "]").toString();
	}

}