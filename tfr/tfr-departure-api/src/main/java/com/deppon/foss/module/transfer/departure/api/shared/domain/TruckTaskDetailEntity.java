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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/TruckTaskDetailEntity.java
 *  
 *  FILE NAME          :TruckTaskDetailEntity.java
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

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * The Class TruckTaskDetailEntity.
 */
public class TruckTaskDetailEntity  extends BaseEntity{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3703272448562684594L;
	
	/** ********任务ID***********. */
	private String truckTaskId;
	
	/** ********任务明细ID***********. */
	private String truckTaskDetailId;
	
	/** ********车牌号***********. */
    private String vehicleNo;
    
    /** ********线路***********. */
    private String lineNo;
    
    /** ********车辆业务类型***********. */
    private String businessType;
    
    /** ********出发部门***********. */
    private String origOrgCode;
    
    private String origOrgName;
    
    /** ********到达部门***********. */
    private String destOrgCode;
    
    private String destOrgName;
    
    /** ********状态***********. */
    private String status;
    
    /** ********创建时间***********. */
    private Date createTime;
    
    /** ********车辆出发ID***********. */
    private String truckDepartId;
    
    /** ********GPSID***********. */
    private String gpsId;
    
    /** ********车辆到达ID***********. */
    private String truckArriveId;
    
    /** ********计划出发时间***********. */
	private Date planDepartTime;
	
	/** ********实际出发时间***********. */
	private Date actualDepartTime;
	
	/** ********手工确认出发时间***********. */
	private Date manualDepartTime;
	
	/** ********手工确认出发时间***********. */
	private Date manualArriveTime;
	
	/** ********计划到达时间***********. */
	private Date planArriveTime;
	
	/** ********实际到达时间***********. */
	private Date actualArriveTime;
	
	/** ********实际出发类型***********. */
	private String actualDepartType;
	
	/** ********实际到达类型***********. */
	private String actualArriveType;
	
	/** ********月台号***********. */
	private String platformDistributeId; 
	
	/** ********是否整车***********. */
	private String beCarLoad;
	
	/** **************更新车辆状态是判断当前车辆的状态是否符合条件******************. */
	private String ostatus;
	
	/*******手工出发放行人编码*******/
	private String manualDepartUserCode;
	
	/*******手工出发放行人名称*******/
	private String manualDepartUserName;
	
	/*******手工出发放行人部门编码*******/
	private String manualDepartOrgCode;
	
	/*******手工出发放行人部门名称*******/
	private String manualDepartOrgName;
	
	/**
	 * 车辆所属类型，公司车、外请车
	 */ 
	private String vehicleOwnerType;
	
	//车队
	private String vehicleOrgCode;
	
	private String vehicleOrgName;
	

	//装车人code
	private String loaderCode;
	//装车人
	private String loaderName;

	
	public String getLoaderCode() {
		return loaderCode;
	}

	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}

	public String getLoaderName() {
		return loaderName;
	}

	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}

	public String getVehicleOwnerType() {
		return vehicleOwnerType;
	}

	public void setVehicleOwnerType(String vehicleOwnerType) {
		this.vehicleOwnerType = vehicleOwnerType;
	}

	/**
	 * Gets the truck task id.
	 *
	 * @return the truck task id
	 */
	public String getTruckTaskId(){
		return truckTaskId;
	}

	/**
	 * Sets the truck task id.
	 *
	 * @param truckTaskId the new truck task id
	 */
	public void setTruckTaskId(String truckTaskId){
		this.truckTaskId = truckTaskId;
	}

	/**
	 * Gets the truck task detail id.
	 *
	 * @return the truck task detail id
	 */
	public String getTruckTaskDetailId(){
		return truckTaskDetailId;
	}

	/**
	 * Sets the truck task detail id.
	 *
	 * @param truckTaskDetailId the new truck task detail id
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId){
		this.truckTaskDetailId = truckTaskDetailId;
	}

	/**
	 * Gets the vehicle no.
	 *
	 * @return the vehicle no
	 */
	public String getVehicleNo(){
		return vehicleNo;
	}

	/**
	 * Sets the vehicle no.
	 *
	 * @param vehicleNo the new vehicle no
	 */
	public void setVehicleNo(String vehicleNo){
		this.vehicleNo = vehicleNo;
	}

	/**
	 * Gets the line no.
	 *
	 * @return the line no
	 */
	public String getLineNo(){
		return lineNo;
	}

	/**
	 * Sets the line no.
	 *
	 * @param lineNo the new line no
	 */
	public void setLineNo(String lineNo){
		this.lineNo = lineNo;
	}

	/**
	 * Gets the business type.
	 *
	 * @return the business type
	 */
	public String getBusinessType(){
		return businessType;
	}

	/**
	 * Sets the business type.
	 *
	 * @param businessType the new business type
	 */
	public void setBusinessType(String businessType){
		this.businessType = businessType;
	}

	/**
	 * Gets the orig org code.
	 *
	 * @return the orig org code
	 */
	public String getOrigOrgCode(){
		return origOrgCode;
	}

	/**
	 * Sets the orig org code.
	 *
	 * @param origOrgCode the new orig org code
	 */
	public void setOrigOrgCode(String origOrgCode){
		this.origOrgCode = origOrgCode;
	}

	/**
	 * Gets the dest org code.
	 *
	 * @return the dest org code
	 */
	public String getDestOrgCode(){
		return destOrgCode;
	}

	/**
	 * Sets the dest org code.
	 *
	 * @param destOrgCode the new dest org code
	 */
	public void setDestOrgCode(String destOrgCode){
		this.destOrgCode = destOrgCode;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus(){
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status){
		this.status = status;
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
	 * Gets the truck depart id.
	 *
	 * @return the truck depart id
	 */
	public String getTruckDepartId(){
		return truckDepartId;
	}

	/**
	 * Sets the truck depart id.
	 *
	 * @param truckDepartId the new truck depart id
	 */
	public void setTruckDepartId(String truckDepartId){
		this.truckDepartId = truckDepartId;
	}

	/**
	 * Gets the truck arrive id.
	 *
	 * @return the truck arrive id
	 */
	public String getTruckArriveId(){
		return truckArriveId;
	}

	/**
	 * Sets the truck arrive id.
	 *
	 * @param truckArriveId the new truck arrive id
	 */
	public void setTruckArriveId(String truckArriveId){
		this.truckArriveId = truckArriveId;
	}

	/**
	 * Gets the plan depart time.
	 *
	 * @return the plan depart time
	 */
	public Date getPlanDepartTime(){
		return planDepartTime;
	}

	/**
	 * Sets the plan depart time.
	 *
	 * @param planDepartTime the new plan depart time
	 */
	public void setPlanDepartTime(Date planDepartTime){
		this.planDepartTime = planDepartTime;
	}

	/**
	 * Gets the actual depart time.
	 *
	 * @return the actual depart time
	 */
	public Date getActualDepartTime(){
		return actualDepartTime;
	}

	/**
	 * Sets the actual depart time.
	 *
	 * @param actualDepartTime the new actual depart time
	 */
	public void setActualDepartTime(Date actualDepartTime){
		this.actualDepartTime = actualDepartTime;
	}

	/**
	 * Gets the plan arrive time.
	 *
	 * @return the plan arrive time
	 */
	public Date getPlanArriveTime(){
		return planArriveTime;
	}

	/**
	 * Sets the plan arrive time.
	 *
	 * @param planArriveTime the new plan arrive time
	 */
	public void setPlanArriveTime(Date planArriveTime){
		this.planArriveTime = planArriveTime;
	}

	/**
	 * Gets the actual arrive time.
	 *
	 * @return the actual arrive time
	 */
	public Date getActualArriveTime(){
		return actualArriveTime;
	}

	/**
	 * Sets the actual arrive time.
	 *
	 * @param actualArriveTime the new actual arrive time
	 */
	public void setActualArriveTime(Date actualArriveTime){
		this.actualArriveTime = actualArriveTime;
	}

	/**
	 * Gets the actual depart type.
	 *
	 * @return the actual depart type
	 */
	public String getActualDepartType(){
		return actualDepartType;
	}

	/**
	 * Sets the actual depart type.
	 *
	 * @param actualDepartType the new actual depart type
	 */
	public void setActualDepartType(String actualDepartType){
		this.actualDepartType = actualDepartType;
	}

	/**
	 * Gets the actual arrive type.
	 *
	 * @return the actual arrive type
	 */
	public String getActualArriveType(){
		return actualArriveType;
	}

	/**
	 * Sets the actual arrive type.
	 *
	 * @param actualArriveType the new actual arrive type
	 */
	public void setActualArriveType(String actualArriveType){
		this.actualArriveType = actualArriveType;
	}

	/**
	 * Gets the platform distribute id.
	 *
	 * @return the platform distribute id
	 */
	public String getPlatformDistributeId(){
		return platformDistributeId;
	}

	/**
	 * Sets the platform distribute id.
	 *
	 * @param platformDistributeId the new platform distribute id
	 */
	public void setPlatformDistributeId(String platformDistributeId){
		this.platformDistributeId = platformDistributeId;
	}

	/**
	 * Gets the be car load.
	 *
	 * @return the be car load
	 */
	public String getBeCarLoad(){
		return beCarLoad;
	}

	/**
	 * Sets the be car load.
	 *
	 * @param beCarLoad the new be car load
	 */
	public void setBeCarLoad(String beCarLoad){
		this.beCarLoad = beCarLoad;
	}

	/**
	 * Gets the ostatus.
	 *
	 * @return the ostatus
	 */
	public String getOstatus(){
		return ostatus;
	}

	/**
	 * Sets the ostatus.
	 *
	 * @param ostatus the new ostatus
	 */
	public void setOstatus(String ostatus){
		this.ostatus = ostatus;
	}

	public String getGpsId() {
		return gpsId;
	}

	public void setGpsId(String gpsId) {
		this.gpsId = gpsId;
	}

	public Date getManualDepartTime() {
		return manualDepartTime;
	}

	public void setManualDepartTime(Date manualDepartTime) {
		this.manualDepartTime = manualDepartTime;
	}

	public Date getManualArriveTime() {
		return manualArriveTime;
	}

	public void setManualArriveTime(Date manualArriveTime) {
		this.manualArriveTime = manualArriveTime;
	}

	public String getManualDepartUserCode() {
		return manualDepartUserCode;
	}

	public void setManualDepartUserCode(String manualDepartUserCode) {
		this.manualDepartUserCode = manualDepartUserCode;
	}

	public String getManualDepartUserName() {
		return manualDepartUserName;
	}

	public void setManualDepartUserName(String manualDepartUserName) {
		this.manualDepartUserName = manualDepartUserName;
	}

	public String getManualDepartOrgCode() {
		return manualDepartOrgCode;
	}

	public void setManualDepartOrgCode(String manualDepartOrgCode) {
		this.manualDepartOrgCode = manualDepartOrgCode;
	}

	public String getManualDepartOrgName() {
		return manualDepartOrgName;
	}

	public void setManualDepartOrgName(String manualDepartOrgName) {
		this.manualDepartOrgName = manualDepartOrgName;
	}

	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public String getVehicleOrgCode() {
		return vehicleOrgCode;
	}

	public void setVehicleOrgCode(String vehicleOrgCode) {
		this.vehicleOrgCode = vehicleOrgCode;
	}

	public String getVehicleOrgName() {
		return vehicleOrgName;
	}

	public void setVehicleOrgName(String vehicleOrgName) {
		this.vehicleOrgName = vehicleOrgName;
	}
	
	
	
}