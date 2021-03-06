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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/TfrLoadTruckTaskDetailEntity.java
 * 
 *  FILE NAME     :TfrLoadTruckTaskDetailEntity.java
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

public class TfrLoadTruckTaskDetailEntity extends BaseEntity {

	private static final long serialVersionUID = -7741143340793889719L;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_TASK_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String truckTaskId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_NO
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String vehicleNo;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.LINE_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String lineName;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.BUSINESS_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String businessType;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ORIG_ORG_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String origOrgCode;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.DEST_ORG_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String destOrgCode;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.STATUS
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String status;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.CREATE_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private Date createTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_DEPART_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String truckDepartId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_ARRIVE_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String truckArriveId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String truckType;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.PLAN_DEPART_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private Date planDepartTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_DEPART_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private Date actualDepartTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.PLAN_ARRIVE_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private Date planArriveTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_ARRIVE_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private Date actualArriveTime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ORIG_ORG_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String origOrgName;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.DEST_ORG_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String destOrgName;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_SEAL_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String vehicleSealId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.BE_CAR_LOAD
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String beCarLoad;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_ORG_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String vehicleOrgCode;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_ORG_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String vehicleOrgName;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_OWNER_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String vehicleOwnerType;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.LOADER_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String loaderName;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.LOADER_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String loaderCode;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_DEPART_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String actualDepartType;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_ARRIVE_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String actualArriveType;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.PLATFORM_DISTRIBUTE_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String platformDistributeId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.FREQUENCY_NO
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String frequencyNo;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column TFR.T_OPT_TRUCK_TASK_DETAIL.LINE_VIRTUAL_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	private String lineVirtualCode;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ID
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ID
	 * 
	 * @param id
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_TASK_ID
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_TASK_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getTruckTaskId() {
		return truckTaskId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_TASK_ID
	 * 
	 * @param truckTaskId
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_TASK_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_NO
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_NO
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_NO
	 * 
	 * @param vehicleNo
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_NO
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.LINE_NAME
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.LINE_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.LINE_NAME
	 * 
	 * @param lineName
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.LINE_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.BUSINESS_TYPE
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.BUSINESS_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.BUSINESS_TYPE
	 * 
	 * @param businessType
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.BUSINESS_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ORIG_ORG_CODE
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.ORIG_ORG_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ORIG_ORG_CODE
	 * 
	 * @param origOrgCode
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.ORIG_ORG_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.DEST_ORG_CODE
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.DEST_ORG_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.DEST_ORG_CODE
	 * 
	 * @param destOrgCode
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.DEST_ORG_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.STATUS
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.STATUS
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.STATUS
	 * 
	 * @param status
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.STATUS
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.CREATE_TIME
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.CREATE_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.CREATE_TIME
	 * 
	 * @param createTime
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.CREATE_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_DEPART_ID
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_DEPART_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getTruckDepartId() {
		return truckDepartId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_DEPART_ID
	 * 
	 * @param truckDepartId
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_DEPART_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setTruckDepartId(String truckDepartId) {
		this.truckDepartId = truckDepartId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_ARRIVE_ID
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_ARRIVE_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getTruckArriveId() {
		return truckArriveId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_ARRIVE_ID
	 * 
	 * @param truckArriveId
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_ARRIVE_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setTruckArriveId(String truckArriveId) {
		this.truckArriveId = truckArriveId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_TYPE
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getTruckType() {
		return truckType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_TYPE
	 * 
	 * @param truckType
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.TRUCK_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.PLAN_DEPART_TIME
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.PLAN_DEPART_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public Date getPlanDepartTime() {
		return planDepartTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.PLAN_DEPART_TIME
	 * 
	 * @param planDepartTime
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.PLAN_DEPART_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setPlanDepartTime(Date planDepartTime) {
		this.planDepartTime = planDepartTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_DEPART_TIME
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_DEPART_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public Date getActualDepartTime() {
		return actualDepartTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_DEPART_TIME
	 * 
	 * @param actualDepartTime
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_DEPART_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setActualDepartTime(Date actualDepartTime) {
		this.actualDepartTime = actualDepartTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.PLAN_ARRIVE_TIME
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.PLAN_ARRIVE_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.PLAN_ARRIVE_TIME
	 * 
	 * @param planArriveTime
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.PLAN_ARRIVE_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_ARRIVE_TIME
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_ARRIVE_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public Date getActualArriveTime() {
		return actualArriveTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_ARRIVE_TIME
	 * 
	 * @param actualArriveTime
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_ARRIVE_TIME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ORIG_ORG_NAME
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.ORIG_ORG_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.ORIG_ORG_NAME
	 * 
	 * @param origOrgName
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.ORIG_ORG_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.DEST_ORG_NAME
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.DEST_ORG_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.DEST_ORG_NAME
	 * 
	 * @param destOrgName
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.DEST_ORG_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_SEAL_ID
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_SEAL_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getVehicleSealId() {
		return vehicleSealId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_SEAL_ID
	 * 
	 * @param vehicleSealId
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_SEAL_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setVehicleSealId(String vehicleSealId) {
		this.vehicleSealId = vehicleSealId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.BE_CAR_LOAD
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.BE_CAR_LOAD
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getBeCarLoad() {
		return beCarLoad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.BE_CAR_LOAD
	 * 
	 * @param beCarLoad
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.BE_CAR_LOAD
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setBeCarLoad(String beCarLoad) {
		this.beCarLoad = beCarLoad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_ORG_CODE
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_ORG_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getVehicleOrgCode() {
		return vehicleOrgCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_ORG_CODE
	 * 
	 * @param vehicleOrgCode
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_ORG_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setVehicleOrgCode(String vehicleOrgCode) {
		this.vehicleOrgCode = vehicleOrgCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_ORG_NAME
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_ORG_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getVehicleOrgName() {
		return vehicleOrgName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_ORG_NAME
	 * 
	 * @param vehicleOrgName
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_ORG_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setVehicleOrgName(String vehicleOrgName) {
		this.vehicleOrgName = vehicleOrgName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_OWNER_TYPE
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_OWNER_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getVehicleOwnerType() {
		return vehicleOwnerType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_OWNER_TYPE
	 * 
	 * @param vehicleOwnerType
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.VEHICLE_OWNER_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setVehicleOwnerType(String vehicleOwnerType) {
		this.vehicleOwnerType = vehicleOwnerType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.LOADER_NAME
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.LOADER_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getLoaderName() {
		return loaderName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.LOADER_NAME
	 * 
	 * @param loaderName
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.LOADER_NAME
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.LOADER_CODE
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.LOADER_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getLoaderCode() {
		return loaderCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.LOADER_CODE
	 * 
	 * @param loaderCode
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.LOADER_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_DEPART_TYPE
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_DEPART_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getActualDepartType() {
		return actualDepartType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_DEPART_TYPE
	 * 
	 * @param actualDepartType
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_DEPART_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setActualDepartType(String actualDepartType) {
		this.actualDepartType = actualDepartType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_ARRIVE_TYPE
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_ARRIVE_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getActualArriveType() {
		return actualArriveType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_ARRIVE_TYPE
	 * 
	 * @param actualArriveType
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.ACTUAL_ARRIVE_TYPE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setActualArriveType(String actualArriveType) {
		this.actualArriveType = actualArriveType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.PLATFORM_DISTRIBUTE_ID
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.PLATFORM_DISTRIBUTE_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getPlatformDistributeId() {
		return platformDistributeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.PLATFORM_DISTRIBUTE_ID
	 * 
	 * @param platformDistributeId
	 *            the value for
	 *            TFR.T_OPT_TRUCK_TASK_DETAIL.PLATFORM_DISTRIBUTE_ID
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setPlatformDistributeId(String platformDistributeId) {
		this.platformDistributeId = platformDistributeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.FREQUENCY_NO
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.FREQUENCY_NO
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getFrequencyNo() {
		return frequencyNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column TFR.T_OPT_TRUCK_TASK_DETAIL.FREQUENCY_NO
	 * 
	 * @param frequencyNo
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.FREQUENCY_NO
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.LINE_VIRTUAL_CODE
	 * 
	 * @return the value of TFR.T_OPT_TRUCK_TASK_DETAIL.LINE_VIRTUAL_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public String getLineVirtualCode() {
		return lineVirtualCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * TFR.T_OPT_TRUCK_TASK_DETAIL.LINE_VIRTUAL_CODE
	 * 
	 * @param lineVirtualCode
	 *            the value for TFR.T_OPT_TRUCK_TASK_DETAIL.LINE_VIRTUAL_CODE
	 * 
	 * @mbggenerated Thu Dec 20 15:33:30 CST 2012
	 */
	public void setLineVirtualCode(String lineVirtualCode) {
		this.lineVirtualCode = lineVirtualCode;
	}
}