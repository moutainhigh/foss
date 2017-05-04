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
 *  PROJECT NAME  : tfr-arrival-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/shared/dto/OperationDTO.java
 *  
 *  FILE NAME          :OperationDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 操作到达记录时，传值到后台进行验证，操作
 * 
 * @author IBM-liubinbin
 * @date 2012-11-5 下午4:11:08
 */
public class OperationDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/************* ID ****************/
	private String id;

	/************* 车辆状态 ****************/
	private String vehicleStatus;
	/************* 车辆状态(复数) ****************/
	private List<String> statuses;

	/************** 到达情况 *************/
	private String arrivalStatus;

	/************** 任务车辆ID *************/
	private String truckTaskId;

	/************** 任务车辆明细ID *************/
	private String truckTaskDetailId;

	/************** 车牌号 ************/
	private String vehicleNo;

	/************** 预计到达时间 ************/
	private Date planArrivalTime;

	/************** 车辆到达ID ************/
	private String truckArriveId;
	/********** 出发部门 ************/
	private String origOrgCode;
	
	/********** 任务类型 ************/
	private String businessType;

	/********** 到达部门 ************/
	private String destOrgCode;
	
	/********** 是否整车 ************/
	private String beCarLoad;
	
	/*********实际放行类型********/
	private String actualDepartType;//实际出发类型
	/*********实际放行时间********/
	private Date actualDepartTime;
	/*********实际放行时间********/
	private Date manualDepartTime;
	/*********实际放行时间********/
	private Date manualArriveTime;
	/*********实际放行时间********/
	private Date manualConfirmDepartTime;
	
	/*********实际到达类型********/
	private String actualArriveType;//实际到达类型
	
	/*********线路********/
	private String lineName;
	
	/***发车操作人***/
	private String departOperator;
	
	/**到达操作人**/
	private String arrivalOperator;
	
	
	public String getArrivalOperator() {
		return arrivalOperator;
	}

	public void setArrivalOperator(String arrivalOperator) {
		this.arrivalOperator = arrivalOperator;
	}

	/**
	 * 操作类型(取消发车/取消到达)DepartureConstant.ACTION_TYPE
	 */
	private String actionType;
	
	/**二程接驳出发单ids*/
	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	
	public String getDepartOperator() {
		return departOperator;
	}

	public void setDepartOperator(String departOperator) {
		this.departOperator = departOperator;
	}

	/**
	 * 获取 *********** ID ***************.
	 *
	 * @return the *********** ID ***************
	 */
	public String getId(){
		return id;
	}

	public Date getManualConfirmDepartTime() {
		return manualConfirmDepartTime;
	}

	public void setManualConfirmDepartTime(Date manualConfirmDepartTime) {
		this.manualConfirmDepartTime = manualConfirmDepartTime;
	}

	/**
	 * 设置 *********** ID ***************.
	 *
	 * @param id the new *********** ID ***************
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * 获取 *********** 车辆状态 ***************.
	 *
	 * @return the *********** 车辆状态 ***************
	 */
	public String getVehicleStatus(){
		return vehicleStatus;
	}

	/**
	 * 获取 ******** 出发部门 ***********.
	 *
	 * @return the ******** 出发部门 ***********
	 */
	public String getOrigOrgCode(){
		return origOrgCode;
	}

	/**
	 * 设置 ******** 出发部门 ***********.
	 *
	 * @param origOrgCode the new ******** 出发部门 ***********
	 */
	public void setOrigOrgCode(String origOrgCode){
		this.origOrgCode = origOrgCode;
	}

	public Date getManualArriveTime() {
		return manualArriveTime;
	}

	public void setManualArriveTime(Date manualArriveTime) {
		this.manualArriveTime = manualArriveTime;
	}

	/**
	 * 获取 ******** 到达部门 ***********.
	 *
	 * @return the ******** 到达部门 ***********
	 */
	public String getDestOrgCode(){
		return destOrgCode;
	}

	/**
	 * 设置 ******** 到达部门 ***********.
	 *
	 * @param destOrgCode the new ******** 到达部门 ***********
	 */
	public void setDestOrgCode(String destOrgCode){
		this.destOrgCode = destOrgCode;
	}

	/**
	 * 设置 *********** 车辆状态 ***************.
	 *
	 * @param vehicleStatus the new *********** 车辆状态 ***************
	 */
	public void setVehicleStatus(String vehicleStatus){
		this.vehicleStatus = vehicleStatus;
	}

	/**
	 * 获取 ************ 到达情况 ************.
	 *
	 * @return the ************ 到达情况 ************
	 */
	public String getArrivalStatus(){
		return arrivalStatus;
	}

	/**
	 * 设置 ************ 到达情况 ************.
	 *
	 * @param arrivalStatus the new ************ 到达情况 ************
	 */
	public void setArrivalStatus(String arrivalStatus){
		this.arrivalStatus = arrivalStatus;
	}

	/**
	 * 获取 ************ 任务车辆ID ************.
	 *
	 * @return the ************ 任务车辆ID ************
	 */
	public String getTruckTaskId(){
		return truckTaskId;
	}

	/**
	 * 设置 ************ 任务车辆ID ************.
	 *
	 * @param truckTaskId the new ************ 任务车辆ID ************
	 */
	public void setTruckTaskId(String truckTaskId){
		this.truckTaskId = truckTaskId;
	}

	/**
	 * 获取 ************ 任务车辆明细ID ************.
	 *
	 * @return the ************ 任务车辆明细ID ************
	 */
	public String getTruckTaskDetailId(){
		return truckTaskDetailId;
	}

	/**
	 * 设置 ************ 任务车辆明细ID ************.
	 *
	 * @param truckTaskDetailId the new ************ 任务车辆明细ID ************
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId){
		this.truckTaskDetailId = truckTaskDetailId;
	}

	/**
	 * 获取 ************ 车牌号 ***********.
	 *
	 * @return the ************ 车牌号 ***********
	 */
	public String getVehicleNo(){
		return vehicleNo;
	}

	/**
	 * 设置 ************ 车牌号 ***********.
	 *
	 * @param vehicleNo the new ************ 车牌号 ***********
	 */
	public void setVehicleNo(String vehicleNo){
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 ************ 预计到达时间 ***********.
	 *
	 * @return the ************ 预计到达时间 ***********
	 */
	public Date getPlanArrivalTime(){
		return planArrivalTime;
	}

	/**
	 * 设置 ************ 预计到达时间 ***********.
	 *
	 * @param planArrivalTime the new ************ 预计到达时间 ***********
	 */
	public void setPlanArrivalTime(Date planArrivalTime){
		this.planArrivalTime = planArrivalTime;
	}

	/**
	 * 获取 ************ 车辆到达ID ***********.
	 *
	 * @return the ************ 车辆到达ID ***********
	 */
	public String getTruckArriveId(){
		return truckArriveId;
	}

	/**
	 * 设置 ************ 车辆到达ID ***********.
	 *
	 * @param truckArriveId the new ************ 车辆到达ID ***********
	 */
	public void setTruckArriveId(String truckArriveId){
		this.truckArriveId = truckArriveId;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getActualDepartType(){
		return actualDepartType;
	}

	/**
	 * 
	 *
	 * @param actualDepartType 
	 */
	public void setActualDepartType(String actualDepartType){
		this.actualDepartType = actualDepartType;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getActualArriveType(){
		return actualArriveType;
	}

	public Date getManualDepartTime() {
		return manualDepartTime;
	}

	public void setManualDepartTime(Date manualDepartTime) {
		this.manualDepartTime = manualDepartTime;
	}

	/**
	 * 
	 *
	 * @param actualArriveType 
	 */
	public void setActualArriveType(String actualArriveType){
		this.actualArriveType = actualArriveType;
	}

	public String getBeCarLoad(){
		return beCarLoad;
	}

	public void setBeCarLoad(String beCarLoad){
		this.beCarLoad = beCarLoad;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public List<String> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	public Date getActualDepartTime() {
		return actualDepartTime;
	}

	public void setActualDepartTime(Date actualDepartTime) {
		this.actualDepartTime = actualDepartTime;
	}

	/**
	 * @return set the actionType
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
}