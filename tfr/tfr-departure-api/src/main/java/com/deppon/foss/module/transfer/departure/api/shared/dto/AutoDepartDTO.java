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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/AutoDepartDTO.java
 *  
 *  FILE NAME          :AutoDepartDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.dto;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

// TODO: Auto-generated Javadoc
/**
 * 自动放行接口DTO.
 *
 * @author IBM-liubinbin
 * @date 2012-11-5 下午4:11:08
 */
public class AutoDepartDTO extends BaseEntity{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3703272448562684594L;

	/** *********** 传入参数，自动放行类型 ***************. */
	private String autoDepartType;

	/** *********** 传入参数，申请人姓名 ***************. */
	private String applyUserName;

	/** *********** 车牌号 ***************. */
	private String vehicleNo;

	/** *********** 司机编号 ***************. */
	private String driverCode;

	/** *********** 司机姓名 ***************. */
	private String driverName;

	/** *********** 司机联系方式 ***************. */
	private String driverPhone;

	/** *********** 申请类型 ***************. */
	private String applyType;

	/** *********** 申请人编号 ***************. */
	private String applyUserCode;

	/** *********** 申请人部门编号 ***************. */
	private String applyOrgCode;

	/** *********** 申请时间 ***************. */
	private Date applyDepartTime;

	/** *********** 申请状态 ***************. */
	private String status;

	/** *********** 放行类型 ***************. */
	private String departType;

	/** *********** 放行事项 ***************. */
	private String departItems;

	/** *********** 货柜号 ***************. */
	private String containerNo;//
	
	/** *********** 创建时间 ***************. */
	private Date createTime;

	/** *********** 创建人工号 ***************. */
	private String createUserCode;

	/** *********** 创建人名称 ***************. */
	private String createUserName;

	/** *********** 创建部门编码 ***************. */
	private String createOrgCode;

	/** *********** 更新时间 ***************. */
	private Date updateTime;

	/** *********** 更新人工号 ***************. */
	private String updateUserCode;

	/** *********** 更新人名称 ***************. */
	private String updateUserName;

	/** *********** 更新部门 ***************. */
	private String updateOrgCode;

	/** *********** 任务车辆ID ***************. */
	private String truckTaskId;
	/**
	 * 获取 *********** 车牌号 ***************.
	 *
	 * @return the *********** 车牌号 ***************
	 */
	public String getVehicleNo(){
		return vehicleNo;
	}

	/**
	 * 设置 *********** 车牌号 ***************.
	 *
	 * @param vehicleNo the new *********** 车牌号 ***************
	 */
	public void setVehicleNo(String vehicleNo){
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 *********** 司机编号 ***************.
	 *
	 * @return the *********** 司机编号 ***************
	 */
	public String getDriverCode(){
		return driverCode;
	}

	/**
	 * 设置 *********** 司机编号 ***************.
	 *
	 * @param driverCode the new *********** 司机编号 ***************
	 */
	public void setDriverCode(String driverCode){
		this.driverCode = driverCode;
	}

	/**
	 * 获取 *********** 司机姓名 ***************.
	 *
	 * @return the *********** 司机姓名 ***************
	 */
	public String getDriverName(){
		return driverName;
	}

	/**
	 * 设置 *********** 司机姓名 ***************.
	 *
	 * @param driverName the new *********** 司机姓名 ***************
	 */
	public void setDriverName(String driverName){
		this.driverName = driverName;
	}

	/**
	 * 获取 *********** 司机联系方式 ***************.
	 *
	 * @return the *********** 司机联系方式 ***************
	 */
	public String getDriverPhone(){
		return driverPhone;
	}

	/**
	 * 设置 *********** 司机联系方式 ***************.
	 *
	 * @param driverPhone the new *********** 司机联系方式 ***************
	 */
	public void setDriverPhone(String driverPhone){
		this.driverPhone = driverPhone;
	}

	/**
	 * 获取 *********** 申请类型 ***************.
	 *
	 * @return the *********** 申请类型 ***************
	 */
	public String getApplyType(){
		return applyType;
	}

	/**
	 * 设置 *********** 申请类型 ***************.
	 *
	 * @param applyType the new *********** 申请类型 ***************
	 */
	public void setApplyType(String applyType){
		this.applyType = applyType;
	}

	/**
	 * 获取 *********** 申请人编号 ***************.
	 *
	 * @return the *********** 申请人编号 ***************
	 */
	public String getApplyUserCode(){
		return applyUserCode;
	}

	/**
	 * 设置 *********** 申请人编号 ***************.
	 *
	 * @param applyUserCode the new *********** 申请人编号 ***************
	 */
	public void setApplyUserCode(String applyUserCode){
		this.applyUserCode = applyUserCode;
	}

	/**
	 * 获取 *********** 申请人部门编号 ***************.
	 *
	 * @return the *********** 申请人部门编号 ***************
	 */
	public String getApplyOrgCode(){
		return applyOrgCode;
	}

	/**
	 * 设置 *********** 申请人部门编号 ***************.
	 *
	 * @param applyOrgCode the new *********** 申请人部门编号 ***************
	 */
	public void setApplyOrgCode(String applyOrgCode){
		this.applyOrgCode = applyOrgCode;
	}

	/**
	 * 获取 *********** 申请时间 ***************.
	 *
	 * @return the *********** 申请时间 ***************
	 */
	public Date getApplyDepartTime(){
		return applyDepartTime;
	}

	/**
	 * 设置 *********** 申请时间 ***************.
	 *
	 * @param applyDepartTime the new *********** 申请时间 ***************
	 */
	public void setApplyDepartTime(Date applyDepartTime){
		this.applyDepartTime = applyDepartTime;
	}

	/**
	 * Gets the creates the time.
	 *
	 * @return the creates the time
	 */
	public Date getCreateTime(){
		return createTime;
	}

	/**
	 * Sets the creates the time.
	 *
	 * @param createTime the new creates the time
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	/**
	 * Gets the creates the user code.
	 *
	 * @return the creates the user code
	 */
	public String getCreateUserCode(){
		return createUserCode;
	}

	/**
	 * Sets the creates the user code.
	 *
	 * @param createUserCode the new creates the user code
	 */
	public void setCreateUserCode(String createUserCode){
		this.createUserCode = createUserCode;
	}

	/**
	 * Gets the creates the user name.
	 *
	 * @return the creates the user name
	 */
	public String getCreateUserName(){
		return createUserName;
	}

	/**
	 * Sets the creates the user name.
	 *
	 * @param createUserName the new creates the user name
	 */
	public void setCreateUserName(String createUserName){
		this.createUserName = createUserName;
	}

	/**
	 * Gets the creates the org code.
	 *
	 * @return the creates the org code
	 */
	public String getCreateOrgCode(){
		return createOrgCode;
	}

	/**
	 * Sets the creates the org code.
	 *
	 * @param createOrgCode the new creates the org code
	 */
	public void setCreateOrgCode(String createOrgCode){
		this.createOrgCode = createOrgCode;
	}

	/**
	 * Gets the update time.
	 *
	 * @return the update time
	 */
	public Date getUpdateTime(){
		return updateTime;
	}

	/**
	 * Sets the update time.
	 *
	 * @param updateTime the new update time
	 */
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	/**
	 * Gets the update user code.
	 *
	 * @return the update user code
	 */
	public String getUpdateUserCode(){
		return updateUserCode;
	}

	/**
	 * Sets the update user code.
	 *
	 * @param updateUserCode the new update user code
	 */
	public void setUpdateUserCode(String updateUserCode){
		this.updateUserCode = updateUserCode;
	}

	/**
	 * Gets the update user name.
	 *
	 * @return the update user name
	 */
	public String getUpdateUserName(){
		return updateUserName;
	}

	/**
	 * Sets the update user name.
	 *
	 * @param updateUserName the new update user name
	 */
	public void setUpdateUserName(String updateUserName){
		this.updateUserName = updateUserName;
	}

	/**
	 * Gets the update org code.
	 *
	 * @return the update org code
	 */
	public String getUpdateOrgCode(){
		return updateOrgCode;
	}

	/**
	 * Sets the update org code.
	 *
	 * @param updateOrgCode the new update org code
	 */
	public void setUpdateOrgCode(String updateOrgCode){
		this.updateOrgCode = updateOrgCode;
	}

	/**
	 * 获取 *********** 申请状态 ***************.
	 *
	 * @return the *********** 申请状态 ***************
	 */
	public String getStatus(){
		return status;
	}

	/**
	 * 设置 *********** 申请状态 ***************.
	 *
	 * @param status the new *********** 申请状态 ***************
	 */
	public void setStatus(String status){
		this.status = status;
	}

	/**
	 * 获取 *********** 放行类型 ***************.
	 *
	 * @return the *********** 放行类型 ***************
	 */
	public String getDepartType(){
		return departType;
	}

	/**
	 * 设置 *********** 放行类型 ***************.
	 *
	 * @param departType the new *********** 放行类型 ***************
	 */
	public void setDepartType(String departType){
		this.departType = departType;
	}

	/**
	 * 获取 *********** 放行事项 ***************.
	 *
	 * @return the *********** 放行事项 ***************
	 */
	public String getDepartItems(){
		return departItems;
	}

	/**
	 * 设置 *********** 放行事项 ***************.
	 *
	 * @param departItems the new *********** 放行事项 ***************
	 */
	public void setDepartItems(String departItems){
		this.departItems = departItems;
	}

	/**
	 * 获取 *********** 传入参数，自动放行类型 ***************.
	 *
	 * @return the *********** 传入参数，自动放行类型 ***************
	 */
	public String getAutoDepartType(){
		return autoDepartType;
	}

	/**
	 * 设置 *********** 传入参数，自动放行类型 ***************.
	 *
	 * @param autoDepartType the new *********** 传入参数，自动放行类型 ***************
	 */
	public void setAutoDepartType(String autoDepartType){
		this.autoDepartType = autoDepartType;
	}

	/**
	 * 获取 *********** 传入参数，申请人姓名 ***************.
	 *
	 * @return the *********** 传入参数，申请人姓名 ***************
	 */
	public String getApplyUserName(){
		return applyUserName;
	}

	/**
	 * 设置 *********** 传入参数，申请人姓名 ***************.
	 *
	 * @param applyUserName the new *********** 传入参数，申请人姓名 ***************
	 */
	public void setApplyUserName(String applyUserName){
		this.applyUserName = applyUserName;
	}

	public String getTruckTaskId() {
		return truckTaskId;
	}

	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}

}