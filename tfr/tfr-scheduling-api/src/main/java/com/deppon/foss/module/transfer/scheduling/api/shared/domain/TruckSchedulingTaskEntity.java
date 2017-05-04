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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/TruckSchedulingTaskEntity.java
 * 
 *  FILE NAME     :TruckSchedulingTaskEntity.java
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

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 排班实体
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-12-25 下午5:39:58
 */
public class TruckSchedulingTaskEntity extends BaseEntity {

	private static final long serialVersionUID = 9215041081292750116L;
	
	/**
	 * id
	 */
	private String id;

	/**
	 * 短途排班表ID T_OPT_TRUCK_SCHEDULING_ID
	 */
	private String schedulingId;

	/**
	 * 车牌号VEHICLE_NO
	 */
	private String vehicleNo;

	/**
	 * 车型TRUCK_MODEL
	 */
	private String truckModel;

	/**
	 * 车辆所属车队
	 */
	private String carOwner;

	/**
	 * 线路LINE_NO
	 */
	private String lineNo;

	/**
	 * 班次FREQUENCY_NO
	 */
	private String frequencyNo;

	/**
	 * 发车时间DEPART_TIME
	 */
	private Date departTime;

	/**
	 * 定人定区ZONE_CODE
	 */
	private String zoneCode;
	
	/**
	 * 定人定区送货区域code
	 */
	private String deliveryAreaCode;

	/**
	 * 任务状态TASK_STATUS
	 */
	private String taskStatus;

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
	 * 任务序号
	 */
	private String taskNo;
	/**
	 * 是否本车队小组的车（Y是N否）
	 */
	private String truckFlag;
	
	//定人定区接货小区
	private String pkpAreaNames;
	
	//非工作日送货区域拼接名称
	private String deliveryRegionalNames;
	
	//是否带快递货（Y是N否）
	private String isBringExpress;
		
	//预计带货方数（方/F）
	private  BigDecimal expectedBringVolume;
	
    //出车任务
	private String dispatchVehicleTask;
	
	//预计出车时间
	private String expectedDispatchVehicleTime; 
	
	//预计二次出车时间
	private String isTheTwoTripTime; 
	
	//带货部门
	private String takeGoodsDepartment;
	
	//转货部门
	private String transferGoodsDepartment; 
	
	public String getDispatchVehicleTask() {
		return dispatchVehicleTask;
	}

	public void setDispatchVehicleTask(String dispatchVehicleTask) {
		this.dispatchVehicleTask = dispatchVehicleTask;
	}

	public String getExpectedDispatchVehicleTime() {
		return expectedDispatchVehicleTime;
	}

	public void setExpectedDispatchVehicleTime(String expectedDispatchVehicleTime) {
		this.expectedDispatchVehicleTime = expectedDispatchVehicleTime;
	}

	public String getIsTheTwoTripTime() {
		return isTheTwoTripTime;
	}

	public void setIsTheTwoTripTime(String isTheTwoTripTime) {
		this.isTheTwoTripTime = isTheTwoTripTime;
	}

	public String getTakeGoodsDepartment() {
		return takeGoodsDepartment;
	}

	public void setTakeGoodsDepartment(String takeGoodsDepartment) {
		this.takeGoodsDepartment = takeGoodsDepartment;
	}

	public String getTransferGoodsDepartment() {
		return transferGoodsDepartment;
	}

	public void setTransferGoodsDepartment(String transferGoodsDepartment) {
		this.transferGoodsDepartment = transferGoodsDepartment;
	}

	public String getIsBringExpress() {
		return isBringExpress;
	}

	public void setIsBringExpress(String isBringExpress) {
		this.isBringExpress = isBringExpress;
	}

	public BigDecimal getExpectedBringVolume() {
		return expectedBringVolume;
	}

	public void setExpectedBringVolume(BigDecimal expectedBringVolume) {
		this.expectedBringVolume = expectedBringVolume;
	}

	public String getDeliveryRegionalNames() {
		return deliveryRegionalNames;
	}

	public void setDeliveryRegionalNames(String deliveryRegionalNames) {
		this.deliveryRegionalNames = deliveryRegionalNames;
	}

	public String getDeliveryAreaCode() {
		return deliveryAreaCode;
	}

	public void setDeliveryAreaCode(String deliveryAreaCode) {
		this.deliveryAreaCode = deliveryAreaCode;
	}

	/**
	 * 获取 短途排班表ID T_OPT_TRUCK_SCHEDULING_ID.
	 * 
	 * @return the 短途排班表ID T_OPT_TRUCK_SCHEDULING_ID
	 */
	public String getSchedulingId() {
		return schedulingId;
	}

	/**
	 * 设置 短途排班表ID T_OPT_TRUCK_SCHEDULING_ID.
	 * 
	 * @param schedulingId
	 *            the new 短途排班表ID T_OPT_TRUCK_SCHEDULING_ID
	 */
	public void setSchedulingId(String schedulingId) {
		this.schedulingId = schedulingId;
	}

	/**
	 * 获取 车牌号VEHICLE_NO.
	 * 
	 * @return the 车牌号VEHICLE_NO
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号VEHICLE_NO.
	 * 
	 * @param vehicleNo
	 *            the new 车牌号VEHICLE_NO
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 车型TRUCK_MODEL.
	 * 
	 * @return the 车型TRUCK_MODEL
	 */
	public String getTruckModel() {
		return truckModel;
	}

	/**
	 * 设置 车型TRUCK_MODEL.
	 * 
	 * @param truckModel
	 *            the new 车型TRUCK_MODEL
	 */
	public void setTruckModel(String truckModel) {
		this.truckModel = truckModel;
	}

	/**
	 * 获取 线路LINE_NO.
	 * 
	 * @return the 线路LINE_NO
	 */
	public String getLineNo() {
		return lineNo;
	}

	/**
	 * 设置 线路LINE_NO.
	 * 
	 * @param lineNo
	 *            the new 线路LINE_NO
	 */
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * 获取 班次FREQUENCY_NO.
	 * 
	 * @return the 班次FREQUENCY_NO
	 */
	public String getFrequencyNo() {
		return frequencyNo;
	}

	/**
	 * 设置 班次FREQUENCY_NO.
	 * 
	 * @param frequencyNo
	 *            the new 班次FREQUENCY_NO
	 */
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}

	/**
	 * 获取 发车时间DEPART_TIME.
	 * 
	 * @return the 发车时间DEPART_TIME
	 */
	public Date getDepartTime() {
		return departTime;
	}

	/**
	 * 设置 发车时间DEPART_TIME.
	 * 
	 * @param departTime
	 *            the new 发车时间DEPART_TIME
	 */
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	/**
	 * 获取 任务状态TASK_STATUS.
	 * 
	 * @return the 任务状态TASK_STATUS
	 */
	public String getTaskStatus() {
		return taskStatus;
	}

	/**
	 * 设置 任务状态TASK_STATUS.
	 * 
	 * @param taskStatus
	 *            the new 任务状态TASK_STATUS
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
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
	 * 获取 车辆所属车队.
	 * 
	 * @return the 车辆所属车队
	 */
	public String getCarOwner() {
		return carOwner;
	}

	/**
	 * 设置 车辆所属车队.
	 * 
	 * @param carOwner
	 *            the new 车辆所属车队
	 */
	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	/**
	 * 获取 定人定区ZONE_CODE.
	 * 
	 * @return the 定人定区ZONE_CODE
	 */
	public String getZoneCode() {
		return zoneCode;
	}

	/**
	 * 设置 定人定区ZONE_CODE.
	 * 
	 * @param zoneCode
	 *            the new 定人定区ZONE_CODE
	 */
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	/**
	 * 获取 任务序号.
	 * 
	 * @return the 任务序号
	 */
	public String getTaskNo() {
		return taskNo;
	}

	/**
	 * 设置 任务序号.
	 * 
	 * @param taskNo
	 *            the new 任务序号
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	/**
	 * 获取 是否本车队小组的车（Y是N否）.
	 *
	 * @return the 是否本车队小组的车（Y是N否）
	 */
	public String getTruckFlag() {
		return truckFlag;
	}

	/**
	 * 设置 是否本车队小组的车（Y是N否）.
	 *
	 * @param truckFlag the new 是否本车队小组的车（Y是N否）
	 */
	public void setTruckFlag(String truckFlag) {
		this.truckFlag = truckFlag;
	}
	
	public String getPkpAreaNames() {
		return pkpAreaNames;
	}

	public void setPkpAreaNames(String pkpAreaNames) {
		this.pkpAreaNames = pkpAreaNames;
	}

	/**   
	 * id   
	 *   
	 * @return  the id   
	 */
	
	public String getId() {
		return id;
	}

	/**   
	 * @param id the id to set
	 * Date:2013-5-23下午3:25:41
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append(
				"TruckSchedulingTaskEntity [tOptTruckSchedulingId=" + schedulingId + ", vehicleNo=" + vehicleNo
						+ ", truckModel=" + truckModel + ", lineNo=" + lineNo + ", frequencyNo=" + frequencyNo
						+ ", departTime=" + departTime + ", taskStatus=" + taskStatus + ", createTime=" + createTime
						+ ", createUserCode=" + createUserCode + ", createUserName=" + createUserName
						+ ", createOrgCode=" + createOrgCode + ", updateTime=" + updateTime + ", updateUserCode="
						+ updateUserCode + ", updateUserName=" + updateUserName + ", updateOrgCode=" + updateOrgCode
						+ "]").toString();
	}

}