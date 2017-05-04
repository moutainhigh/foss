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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/domain/CertificatebagQueryEntity.java
 *  
 *  FILE NAME          :CertificatebagQueryEntity.java
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
 * 证件包查询entity
 * 
 * @author 099197-foss-liming
 * @date 2012-11-02 下午4:54:47
 */
public class CertificatebagQueryEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1057354820287420186L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 任务车辆ID
	 */
	private String truckTaskId;

	/**
	 * 车牌号/货柜号
	 */
	private String vehicleNo;

	/**
	 * 车辆归属部门
	 */
	private String orgId;

	/**
	 * 证件包类型
	 */
	private String certificatebagType;

	/**
	 * 业务类型
	 */
	private String businessType;

	/**
	 * 交接情况
	 */
	private String handOverStatus;

	/**
	 * 证件包状态
	 */
	private String certificatebagStatus;

	/**
	 * 计划领取地点
	 */
	private String planTakeOrgCode;

	/**
	 * 计划归还地点
	 */
	private String planReturnOrgCode;

	/**
	 * 领取人编号
	 */
	private String actualTakeUserCode;

	/**
	 * 领取人姓名
	 */
	private String actualTakeUserName;

	/**
	 * 领取操作人
	 */
	private String actualTakeOperator;

	/**
	 * 领取地点
	 */
	private String actualTakeOrgCode;

	/**
	 * 领取时间
	 */
	private Date actualTakeTime;

	/**
	 * 领取备注
	 */
	private String actualTakeNotes;

	/**
	 * 归还人编号
	 */
	private String actualReturnUserCode;

	/**
	 * 归还人姓名
	 */
	private String actualReturnUserName;

	/**
	 * 归还操作人
	 */
	private String actualReturnOperator;

	/**
	 * 归还地点
	 */
	private String actualReturnOrgCode;

	/**
	 * 归还时间
	 */
	private Date actualReturnTime;

	/**
	 * 归还备注
	 */
	private String actualReturnNotes;

	/**
	 * 关联id
	 */
	private String refId;

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

	/**
	 * 领取证件类型
	 */
	private String takeType;

	/**
	 * 领取证件包状态
	 */
	private String takeStatus;

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
	 * 获取 任务车辆ID.
	 *
	 * @return the 任务车辆ID
	 */
	public String getTruckTaskId() {
		return truckTaskId;
	}

	/**
	 * 设置 任务车辆ID.
	 *
	 * @param truckTaskId the new 任务车辆ID
	 */
	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}

	/**
	 * 获取 车牌号/货柜号.
	 *
	 * @return the 车牌号/货柜号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号/货柜号.
	 *
	 * @param vehicleNo the new 车牌号/货柜号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 车辆归属部门.
	 *
	 * @return the 车辆归属部门
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * 设置 车辆归属部门.
	 *
	 * @param orgId the new 车辆归属部门
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * 获取 证件包类型.
	 *
	 * @return the 证件包类型
	 */
	public String getCertificatebagType() {
		return certificatebagType;
	}

	/**
	 * 设置 证件包类型.
	 *
	 * @param certificatebagType the new 证件包类型
	 */
	public void setCertificatebagType(String certificatebagType) {
		this.certificatebagType = certificatebagType;
	}

	/**
	 * 获取 业务类型.
	 *
	 * @return the 业务类型
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * 设置 业务类型.
	 *
	 * @param businessType the new 业务类型
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 获取 交接情况.
	 *
	 * @return the 交接情况
	 */
	public String getHandOverStatus() {
		return handOverStatus;
	}

	/**
	 * 设置 交接情况.
	 *
	 * @param handOverStatus the new 交接情况
	 */
	public void setHandOverStatus(String handOverStatus) {
		this.handOverStatus = handOverStatus;
	}

	/**
	 * 获取 证件包状态.
	 *
	 * @return the 证件包状态
	 */
	public String getCertificatebagStatus() {
		return certificatebagStatus;
	}

	/**
	 * 设置 证件包状态.
	 *
	 * @param certificatebagStatus the new 证件包状态
	 */
	public void setCertificatebagStatus(String certificatebagStatus) {
		this.certificatebagStatus = certificatebagStatus;
	}

	/**
	 * 获取 计划领取地点.
	 *
	 * @return the 计划领取地点
	 */
	public String getPlanTakeOrgCode() {
		return planTakeOrgCode;
	}

	/**
	 * 设置 计划领取地点.
	 *
	 * @param planTakeOrgCode the new 计划领取地点
	 */
	public void setPlanTakeOrgCode(String planTakeOrgCode) {
		this.planTakeOrgCode = planTakeOrgCode;
	}

	/**
	 * 获取 计划归还地点.
	 *
	 * @return the 计划归还地点
	 */
	public String getPlanReturnOrgCode() {
		return planReturnOrgCode;
	}

	/**
	 * 设置 计划归还地点.
	 *
	 * @param planReturnOrgCode the new 计划归还地点
	 */
	public void setPlanReturnOrgCode(String planReturnOrgCode) {
		this.planReturnOrgCode = planReturnOrgCode;
	}

	/**
	 * 获取 领取人编号.
	 *
	 * @return the 领取人编号
	 */
	public String getActualTakeUserCode() {
		return actualTakeUserCode;
	}

	/**
	 * 设置 领取人编号.
	 *
	 * @param actualTakeUserCode the new 领取人编号
	 */
	public void setActualTakeUserCode(String actualTakeUserCode) {
		this.actualTakeUserCode = actualTakeUserCode;
	}

	/**
	 * 获取 领取人姓名.
	 *
	 * @return the 领取人姓名
	 */
	public String getActualTakeUserName() {
		return actualTakeUserName;
	}

	/**
	 * 设置 领取人姓名.
	 *
	 * @param actualTakeUserName the new 领取人姓名
	 */
	public void setActualTakeUserName(String actualTakeUserName) {
		this.actualTakeUserName = actualTakeUserName;
	}

	/**
	 * 获取 领取操作人.
	 *
	 * @return the 领取操作人
	 */
	public String getActualTakeOperator() {
		return actualTakeOperator;
	}

	/**
	 * 设置 领取操作人.
	 *
	 * @param actualTakeOperator the new 领取操作人
	 */
	public void setActualTakeOperator(String actualTakeOperator) {
		this.actualTakeOperator = actualTakeOperator;
	}

	/**
	 * 获取 领取地点.
	 *
	 * @return the 领取地点
	 */
	public String getActualTakeOrgCode() {
		return actualTakeOrgCode;
	}

	/**
	 * 设置 领取地点.
	 *
	 * @param actualTakeOrgCode the new 领取地点
	 */
	public void setActualTakeOrgCode(String actualTakeOrgCode) {
		this.actualTakeOrgCode = actualTakeOrgCode;
	}

	/**
	 * 获取 领取时间.
	 *
	 * @return the 领取时间
	 */
	public Date getActualTakeTime() {
		return actualTakeTime;
	}

	/**
	 * 设置 领取时间.
	 *
	 * @param actualTakeTime the new 领取时间
	 */
	public void setActualTakeTime(Date actualTakeTime) {
		this.actualTakeTime = actualTakeTime;
	}

	/**
	 * 获取 领取备注.
	 *
	 * @return the 领取备注
	 */
	public String getActualTakeNotes() {
		return actualTakeNotes;
	}

	/**
	 * 设置 领取备注.
	 *
	 * @param actualTakeNotes the new 领取备注
	 */
	public void setActualTakeNotes(String actualTakeNotes) {
		this.actualTakeNotes = actualTakeNotes;
	}

	/**
	 * 获取 归还人编号.
	 *
	 * @return the 归还人编号
	 */
	public String getActualReturnUserCode() {
		return actualReturnUserCode;
	}

	/**
	 * 设置 归还人编号.
	 *
	 * @param actualReturnUserCode the new 归还人编号
	 */
	public void setActualReturnUserCode(String actualReturnUserCode) {
		this.actualReturnUserCode = actualReturnUserCode;
	}

	/**
	 * 获取 归还人姓名.
	 *
	 * @return the 归还人姓名
	 */
	public String getActualReturnUserName() {
		return actualReturnUserName;
	}

	/**
	 * 设置 归还人姓名.
	 *
	 * @param actualReturnUserName the new 归还人姓名
	 */
	public void setActualReturnUserName(String actualReturnUserName) {
		this.actualReturnUserName = actualReturnUserName;
	}

	/**
	 * 获取 归还操作人.
	 *
	 * @return the 归还操作人
	 */
	public String getActualReturnOperator() {
		return actualReturnOperator;
	}

	/**
	 * 设置 归还操作人.
	 *
	 * @param actualReturnOperator the new 归还操作人
	 */
	public void setActualReturnOperator(String actualReturnOperator) {
		this.actualReturnOperator = actualReturnOperator;
	}

	/**
	 * 获取 归还地点.
	 *
	 * @return the 归还地点
	 */
	public String getActualReturnOrgCode() {
		return actualReturnOrgCode;
	}

	/**
	 * 设置 归还地点.
	 *
	 * @param actualReturnOrgCode the new 归还地点
	 */
	public void setActualReturnOrgCode(String actualReturnOrgCode) {
		this.actualReturnOrgCode = actualReturnOrgCode;
	}

	/**
	 * 获取 归还时间.
	 *
	 * @return the 归还时间
	 */
	public Date getActualReturnTime() {
		return actualReturnTime;
	}

	/**
	 * 设置 归还时间.
	 *
	 * @param actualReturnTime the new 归还时间
	 */
	public void setActualReturnTime(Date actualReturnTime) {
		this.actualReturnTime = actualReturnTime;
	}

	/**
	 * 获取 归还备注.
	 *
	 * @return the 归还备注
	 */
	public String getActualReturnNotes() {
		return actualReturnNotes;
	}

	/**
	 * 设置 归还备注.
	 *
	 * @param actualReturnNotes the new 归还备注
	 */
	public void setActualReturnNotes(String actualReturnNotes) {
		this.actualReturnNotes = actualReturnNotes;
	}

	/**
	 * 获取 关联id.
	 *
	 * @return the 关联id
	 */
	public String getRefId() {
		return refId;
	}

	/**
	 * 设置 关联id.
	 *
	 * @param refId the new 关联id
	 */
	public void setRefId(String refId) {
		this.refId = refId;
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

	/**
	 * 获取 领取证件类型.
	 *
	 * @return the 领取证件类型
	 */
	public String getTakeType() {
		return takeType;
	}

	/**
	 * 设置 领取证件类型.
	 *
	 * @param takeType the new 领取证件类型
	 */
	public void setTakeType(String takeType) {
		this.takeType = takeType;
	}

	/**
	 * 获取 领取证件包状态.
	 *
	 * @return the 领取证件包状态
	 */
	public String getTakeStatus() {
		return takeStatus;
	}

	/**
	 * 设置 领取证件包状态.
	 *
	 * @param takeStatus the new 领取证件包状态
	 */
	public void setTakeStatus(String takeStatus) {
		this.takeStatus = takeStatus;
	}

}