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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/TruckDepartEntity.java
 *  
 *  FILE NAME          :TruckDepartEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.domain;
import java.util.Date;
import java.util.List;
import com.deppon.foss.framework.entity.BaseEntity;
/**
 * The Class TruckDepartEntity.
 */
public class TruckDepartEntity extends BaseEntity {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3703272448562684594L;
	/** ****** 车牌号 ***********. */
	private String vehicleNo;
	/** ****** 司机编码 ***********. */
	private String driverCode;// 司机编码
	/** ****** 司机姓名 ***********. */
	private String driverName;// 司机姓名
	/** ****** 司机电话 ***********. */
	private String driverPhone;// 司机电话
	/** ****** 货柜号 ***********. */
	private String containerNo;//
	/** ****** 状态 ***********. */
	private String status;// 状态
	/** ****** 到达部门（GPS专用） ***********. */
	private String destOrgCode;
	/** ****** 放行类型 ***********. */
	private String departType;// 放行类型
	/** ****** 放行事项 ***********. */
	private String departItems;// 放行事项
	/** ****** 申请类型 ***********. */
	private String applyType;// 申请类型
	/** ****** 申请人编码 ***********. */
	private String applyUserCode;// 申请人编码
	/** ****** 申请人姓名 ***********. */
	private String applyUserName;// 申请人姓名
	/** ****** 申请人部门编码 ***********. */
	private String applyOrgCode;// 申请人部门编码
	/** ****** 申请人部门名称 ***********. */
	private String applyOrgName;// 申请人部门名称
	/** ****** 申请时间 ***********. */
	private Date applyDepartTime;// 申请时间
	/** ****** 申请备注 ***********. */
	private String applyNotes;// 申请备注
	/** ****** PDA放行人编码 ***********. */
	private String pdaDepartUserCode;// PDA放行人编码
	/** ****** PDA放行人部门编码 ***********. */
	private String pdaDepartOrgCode;// PDA放行人部门编码
	/** ****** GPS放行部门编码 ***********. */
	private String gpsDepartOrgCode;// PDA放行人部门编码
	/** ****** PDA放行设备号 ***********. */
	private String pdaTerminalNo;// PDA放行设备号
	/** ****** PDA放行时间 ***********. */
	private Date pdaDepartTime;// PDA放行时间
	/** ****** GPS放行时间 ***********. */
	private Date gpsDepartTime;// GPS放行时间
	/** ****** 纸质放行人编码 ***********. */
	private String manualDepartUserCode;// 纸质放行人编码
	/** ****** 纸质放行人名称***********. */
	private String manualDepartUserName;
	/** ****** 纸质放行人部门编码 ***********. */
	private String manualDepartOrgCode;// 纸质放行人部门编码
	/** ****** 纸质放行时间 ***********. */
	private Date manualDepartTime;// 纸质放行时间
	/** ****** 纸质放行备注 ***********. */
	private String manualDepartNotes;// 纸质放行备注
	/** ****** 创建时间 ***********. */
	private Date createTime;// 创建时间
	/** ****** 创建人编码 ***********. */
	private String createUserCode;// 创建人编码
	/** ****** 创建人名称 **********. */
	private String createUserName;// 创建人名称
	/** ****** 创建人部门编码 ***********. */
	private String createOrgCode;// 创建人部门编码
	/** ****** 更新时间 ***********. */
	private Date updateTime;// 更新时间
	/** ****** 更新人编码 ***********. */
	private String updateUserCode;// 更新人编码
	/** ****** 更新人姓名 ***********. */
	private String updateUserName;// 更新人姓名
	/** ***** 更新人部门编码 ***********. */
	private String updateOrgCode;// 更新人部门编码
	/** ****** 车辆所属部门 ***********. */
	private String truckOrgCode;// 车辆所属部门
	/** ****** 车辆归属类型 ***********. */
	private String truckType;// 车辆归属类型
	/** ****** 计划放行时间 ***********. */
	private Date planDepartTime;// 计划放行时间
	/** ****** 是否选中 ***********. */
	private String isChecked;// 是否选中
	/** ****** 实际出发类型 ***********. */
	private String actualDepartType;// 实际出发类型
	/** ****** 际出发类型 ***********. */
	private Date actualDepartTime;// 际出发类型
	/** ****** 车牌号 ***********. */
	private List<String> departTypeList;
	/** ****** 任务车辆ID ***********. */
	private String truckTaskId;
	/** ****** 激活时间 ***********. */
	private Date activeTime;
	/** ****** 是否全部放行 ***********. */
	private String beDepartureAll;
	/**
	 * Gets the vehicle no.
	 * 
	 * @return the vehicle no
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * Sets the vehicle no.
	 * 
	 * @param vehicleNo
	 *            the new vehicle no
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * Gets the driver code.
	 * 
	 * @return the driver code
	 */
	public String getDriverCode() {
		return driverCode;
	}
	/**
	 * Sets the driver code.
	 * 
	 * @param driverCode
	 *            the new driver code
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	/**
	 * Gets the driver name.
	 * 
	 * @return the driver name
	 */
	public String getDriverName() {
		return driverName;
	}
	/**
	 * Sets the driver name.
	 * 
	 * @param driverName
	 *            the new driver name
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	/**
	 * Gets the driver phone.
	 * 
	 * @return the driver phone
	 */
	public String getDriverPhone() {
		return driverPhone;
	}
	/**
	 * Sets the driver phone.
	 * 
	 * @param driverPhone
	 *            the new driver phone
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	/**
	 * Gets the container no.
	 * 
	 * @return the container no
	 */
	public String getContainerNo() {
		return containerNo;
	}
	/**
	 * Sets the container no.
	 * 
	 * @param containerNo
	 *            the new container no
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * Gets the depart type.
	 * 
	 * @return the depart type
	 */
	public String getDepartType() {
		return departType;
	}
	/**
	 * Sets the depart type.
	 * 
	 * @param departType
	 *            the new depart type
	 */
	public void setDepartType(String departType) {
		this.departType = departType;
	}
	/**
	 * Gets the depart items.
	 * 
	 * @return the depart items
	 */
	public String getDepartItems() {
		return departItems;
	}
	/**
	 * Sets the depart items.
	 * 
	 * @param departItems
	 *            the new depart items
	 */
	public void setDepartItems(String departItems) {
		this.departItems = departItems;
	}
	/**
	 * Gets the apply type.
	 * 
	 * @return the apply type
	 */
	public String getApplyType() {
		return applyType;
	}
	/**
	 * Sets the apply type.
	 * 
	 * @param applyType
	 *            the new apply type
	 */
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	/**
	 * Gets the apply user code.
	 * 
	 * @return the apply user code
	 */
	public String getApplyUserCode() {
		return applyUserCode;
	}
	/**
	 * Sets the apply user code.
	 * 
	 * @param applyUserCode
	 *            the new apply user
	 *            code
	 */
	public void setApplyUserCode(String applyUserCode) {
		this.applyUserCode = applyUserCode;
	}
	/**
	 * Gets the apply user name.
	 * 
	 * @return the apply user name
	 */
	public String getApplyUserName() {
		return applyUserName;
	}
	/**
	 * Sets the apply user name.
	 * 
	 * @param applyUserName
	 *            the new apply user
	 *            name
	 */
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	/**
	 * Gets the apply org code.
	 * 
	 * @return the apply org code
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}
	/**
	 * Sets the apply org code.
	 * 
	 * @param applyOrgCode
	 *            the new apply org code
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}
	/**
	 * Gets the apply org name.
	 * 
	 * @return the apply org name
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}
	/**
	 * Sets the apply org name.
	 * 
	 * @param applyOrgName
	 *            the new apply org name
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}
	/**
	 * Gets the apply depart time.
	 * 
	 * @return the apply depart time
	 */
	public Date getApplyDepartTime() {
		return applyDepartTime;
	}
	/**
	 * Sets the apply depart time.
	 * 
	 * @param applyDepartTime
	 *            the new apply depart
	 *            time
	 */
	public void setApplyDepartTime(Date applyDepartTime) {
		this.applyDepartTime = applyDepartTime;
	}
	/**
	 * Gets the apply notes.
	 * 
	 * @return the apply notes
	 */
	public String getApplyNotes() {
		return applyNotes;
	}
	/**
	 * Sets the apply notes.
	 * 
	 * @param applyNotes
	 *            the new apply notes
	 */
	public void setApplyNotes(String applyNotes) {
		this.applyNotes = applyNotes;
	}
	/**
	 * Gets the pda depart user code.
	 * 
	 * @return the pda depart user code
	 */
	public String getPdaDepartUserCode() {
		return pdaDepartUserCode;
	}
	/**
	 * Sets the pda depart user code.
	 * 
	 * @param pdaDepartUserCode
	 *            the new pda depart
	 *            user code
	 */
	public void setPdaDepartUserCode(String pdaDepartUserCode) {
		this.pdaDepartUserCode = pdaDepartUserCode;
	}
	/**
	 * Gets the pda depart org code.
	 * 
	 * @return the pda depart org code
	 */
	public String getPdaDepartOrgCode() {
		return pdaDepartOrgCode;
	}
	/**
	 * Sets the pda depart org code.
	 * 
	 * @param pdaDepartOrgCode
	 *            the new pda depart org
	 *            code
	 */
	public void setPdaDepartOrgCode(String pdaDepartOrgCode) {
		this.pdaDepartOrgCode = pdaDepartOrgCode;
	}
	/**
	 * Gets the pda terminal no.
	 * 
	 * @return the pda terminal no
	 */
	public String getPdaTerminalNo() {
		return pdaTerminalNo;
	}
	/**
	 * Sets the pda terminal no.
	 * 
	 * @param pdaTerminalNo
	 *            the new pda terminal
	 *            no
	 */
	public void setPdaTerminalNo(String pdaTerminalNo) {
		this.pdaTerminalNo = pdaTerminalNo;
	}
	/**
	 * Gets the pda depart time.
	 * 
	 * @return the pda depart time
	 */
	public Date getPdaDepartTime() {
		return pdaDepartTime;
	}
	/**
	 * Sets the pda depart time.
	 * 
	 * @param pdaDepartTime
	 *            the new pda depart
	 *            time
	 */
	public void setPdaDepartTime(Date pdaDepartTime) {
		this.pdaDepartTime = pdaDepartTime;
	}
	/**
	 * Gets the gps depart time.
	 * 
	 * @return the gps depart time
	 */
	public Date getGpsDepartTime() {
		return gpsDepartTime;
	}
	/**
	 * Sets the gps depart time.
	 * 
	 * @param gpsDepartTime
	 *            the new gps depart
	 *            time
	 */
	public void setGpsDepartTime(Date gpsDepartTime) {
		this.gpsDepartTime = gpsDepartTime;
	}
	/**
	 * Gets the manual depart user code.
	 * 
	 * @return the manual depart user
	 *         code
	 */
	public String getManualDepartUserCode() {
		return manualDepartUserCode;
	}
	/**
	 * Sets the manual depart user code.
	 * 
	 * @param manualDepartUserCode
	 *            the new manual depart
	 *            user code
	 */
	public void setManualDepartUserCode(String manualDepartUserCode) {
		this.manualDepartUserCode = manualDepartUserCode;
	}
	/**
	 * Gets the manual depart org code.
	 * 
	 * @return the manual depart org
	 *         code
	 */
	public String getManualDepartOrgCode() {
		return manualDepartOrgCode;
	}
	/**
	 * Sets the manual depart org code.
	 * 
	 * @param manualDepartOrgCode
	 *            the new manual depart
	 *            org code
	 */
	public void setManualDepartOrgCode(String manualDepartOrgCode) {
		this.manualDepartOrgCode = manualDepartOrgCode;
	}
	/**
	 * Gets the manual depart time.
	 * 
	 * @return the manual depart time
	 */
	public Date getManualDepartTime() {
		return manualDepartTime;
	}
	/**
	 * Sets the manual depart time.
	 * 
	 * @param manualDepartTime
	 *            the new manual depart
	 *            time
	 */
	public void setManualDepartTime(Date manualDepartTime) {
		this.manualDepartTime = manualDepartTime;
	}
	/**
	 * Gets the manual depart notes.
	 * 
	 * @return the manual depart notes
	 */
	public String getManualDepartNotes() {
		return manualDepartNotes;
	}
	/**
	 * Sets the manual depart notes.
	 * 
	 * @param manualDepartNotes
	 *            the new manual depart
	 *            notes
	 */
	public void setManualDepartNotes(String manualDepartNotes) {
		this.manualDepartNotes = manualDepartNotes;
	}
	/**
	 * Gets the creates the time.
	 * 
	 * @return the creates the time
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * Sets the creates the time.
	 * 
	 * @param createTime
	 *            the new creates the
	 *            time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * Gets the creates the user code.
	 * 
	 * @return the creates the user code
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	/**
	 * Sets the creates the user code.
	 * 
	 * @param createUserCode
	 *            the new creates the
	 *            user code
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	/**
	 * Gets the creates the user name.
	 * 
	 * @return the creates the user name
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * Sets the creates the user name.
	 * 
	 * @param createUserName
	 *            the new creates the
	 *            user name
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 * Gets the creates the org code.
	 * 
	 * @return the creates the org code
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	/**
	 * Sets the creates the org code.
	 * 
	 * @param createOrgCode
	 *            the new creates the
	 *            org code
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	/**
	 * Gets the update time.
	 * 
	 * @return the update time
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * Sets the update time.
	 * 
	 * @param updateTime
	 *            the new update time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * Gets the update user code.
	 * 
	 * @return the update user code
	 */
	public String getUpdateUserCode() {
		return updateUserCode;
	}
	/**
	 * Sets the update user code.
	 * 
	 * @param updateUserCode
	 *            the new update user
	 *            code
	 */
	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
	}
	/**
	 * Gets the update user name.
	 * 
	 * @return the update user name
	 */
	public String getUpdateUserName() {
		return updateUserName;
	}
	/**
	 * Sets the update user name.
	 * 
	 * @param updateUserName
	 *            the new update user
	 *            name
	 */
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	/**
	 * Gets the update org code.
	 * 
	 * @return the update org code
	 */
	public String getUpdateOrgCode() {
		return updateOrgCode;
	}
	/**
	 * Sets the update org code.
	 * 
	 * @param updateOrgCode
	 *            the new update org
	 *            code
	 */
	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}
	/**
	 * Gets the truck org code.
	 * 
	 * @return the truck org code
	 */
	public String getTruckOrgCode() {
		return truckOrgCode;
	}
	/**
	 * Sets the truck org code.
	 * 
	 * @param truckOrgCode
	 *            the new truck org code
	 */
	public void setTruckOrgCode(String truckOrgCode) {
		this.truckOrgCode = truckOrgCode;
	}
	/**
	 * Gets the truck type.
	 * 
	 * @return the truck type
	 */
	public String getTruckType() {
		return truckType;
	}
	/**
	 * Sets the truck type.
	 * 
	 * @param truckType
	 *            the new truck type
	 */
	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}
	/**
	 * Gets the plan depart time.
	 * 
	 * @return the plan depart time
	 */
	public Date getPlanDepartTime() {
		return planDepartTime;
	}
	/**
	 * Sets the plan depart time.
	 * 
	 * @param planDepartTime
	 *            the new plan depart
	 *            time
	 */
	public void setPlanDepartTime(Date planDepartTime) {
		this.planDepartTime = planDepartTime;
	}
	/**
	 * Gets the checks if is checked.
	 * 
	 * @return the checks if is checked
	 */
	public String getIsChecked() {
		return isChecked;
	}
	/**
	 * Sets the checks if is checked.
	 * 
	 * @param isChecked
	 *            the new checks if is
	 *            checked
	 */
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	/**
	 * Gets the actual depart type.
	 * 
	 * @return the actual depart type
	 */
	public String getActualDepartType() {
		return actualDepartType;
	}
	/**
	 * Sets the actual depart type.
	 * 
	 * @param actualDepartType
	 *            the new actual depart
	 *            type
	 */
	public void setActualDepartType(String actualDepartType) {
		this.actualDepartType = actualDepartType;
	}
	/**
	 * Gets the actual depart time.
	 * 
	 * @return the actual depart time
	 */
	public Date getActualDepartTime() {
		return actualDepartTime;
	}
	/**
	 * Sets the actual depart time.
	 * 
	 * @param actualDepartTime
	 *            the new actual depart
	 *            time
	 */
	public void setActualDepartTime(Date actualDepartTime) {
		this.actualDepartTime = actualDepartTime;
	}
	/**
	 * Gets the depart type list.
	 * 
	 * @return the depart type list
	 */
	public List<String> getDepartTypeList() {
		return departTypeList;
	}
	/**
	 * Sets the depart type list.
	 * 
	 * @param departTypeList
	 *            the new depart type
	 *            list
	 */
	public void setDepartTypeList(List<String> departTypeList) {
		this.departTypeList = departTypeList;
	}
	public String getTruckTaskId() {
		return truckTaskId;
	}
	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}
	public String getGpsDepartOrgCode() {
		return gpsDepartOrgCode;
	}
	public void setGpsDepartOrgCode(String gpsDepartOrgCode) {
		this.gpsDepartOrgCode = gpsDepartOrgCode;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public String getManualDepartUserName() {
		return manualDepartUserName;
	}
	public void setManualDepartUserName(String manualDepartUserName) {
		this.manualDepartUserName = manualDepartUserName;
	}
	public String getBeDepartureAll() {
		return beDepartureAll;
	}
	public void setBeDepartureAll(String beDepartureAll) {
		this.beDepartureAll = beDepartureAll;
	}
	
}