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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/shared/domain/ArrivalEntity.java
 *  
 *  FILE NAME          :ArrivalEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 到达的数据展示
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午6:53:50
 */
public class ArrivalEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/********** 车牌号 ************/
	private String vehicleNo;

	/********** 线路 ************/
	private String lineNo;
	
	/********** 出发部门 ************/
	private String origOrgName;

	/********** 到达部门 ************/
	private String destOrgName;
	
	/********** 出发部门 ************/
	private String origOrgCode;

	/********** 到达部门 ************/
	private String destOrgCode;

	/********** 出发时间（获取类型） ************/
	private String departTime;
	
	/**出发时间**/
	private Date departTime2;

	/********** 到达时间（获取类型） ************/
	private String arrivalTime;
	
	/********** 出发时间 ************/
	private Date actualDepartTime;
	
	/********** 手工确认出发时间 ************/
	private Date manualConfirmDepartTime;
	
	/********** 外场确认出发时间 ************/
	private Date manualDepartTime;
	
	/********** 保安PDA放行时间确认出发时间 ************/
	private Date pdaDepartTime;
	
	/********** GPS出发时间 ************/
	private Date gpsDepartTime;
	
	/********** 手工到达时间 ************/
	private Date manualArriveTime;
	
	/********** 保安PDA放行时间确认到达时间 ************/
	private Date pdaArriveTime;
	
	/********** GPS到达时间 ************/
	private Date gpsArriveTime;
	
	/********** 到达时间 ************/
	private Date actualArrivalTime;

	/********** 预计到达时间 ************/
	private Date planArrivalTime;
	
	/********** 预计出发时间 ************/
	private Date planDepartTime;

	/********** 到达情况 ************/
	private String arrivalStatus;

	/********** 证件包情况 ************/
	private String certificteBagStatus;

	/********** 月台分配 ************/
	private String platformStatus;

	/********** 未结清金额 ************/
	private BigDecimal fee;

	/********** 装车人 ************/
	private String loader;
	
	/***********车辆状态***********/
	private String detailStatus;
	
	/**********任务车辆ID******/
	private String truckTaskId;
	
	/**********任务车辆明细ID******/
	private String truckTaskDetailId;
	
	/**********车辆到达ID******/
	private String truckArriveId;

	/**********实际出发类型******/
	private String actualDepartType;
	
	/**********实际到达类型******/
	private String actualArriveType;
	
	/**********线路虚拟code**********/
	private String lineVirtualCode;
	/**********班次************/
	private String frequencyNo;
	/**********车辆归属类型***/
	private String vehicleOwnerType;
	
	/**********司机编号***/
	private String driverCode;
	/*********是否整车***/
	private String beCarLoad;
	/*********车辆归属部门***/
	private String truckOrgCode;
	/*********车辆任务类型***/
	private String businessType;
	/*********是否高亮***/
	private String isBright;
	/*********交接单/配载单***/
	private String billNos;
	
	//到达校验时间
	private Date arrCheckTime;
	//封签校验类型
	private String checkType;
	//月台号
	private String platformCode;
	
	//接驳单号
	private String connectionBillNo;
	
	//交接单号
	private String handoverNo;
	
	//发车操作人
	private String departOperator;
	//到达操作人
	private String arrivalOperator;
	
	private String chargingAssmebleNo;
	
	
	public String getDepartOperator() {
		return departOperator;
	}

	public void setDepartOperator(String departOperator) {
		this.departOperator = departOperator;
	}

	public String getArrivalOperator() {
		return arrivalOperator;
	}

	public void setArrivalOperator(String arrivalOperator) {
		this.arrivalOperator = arrivalOperator;
	}

	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public String getConnectionBillNo() {
		return connectionBillNo;
	}

	public void setConnectionBillNo(String connectionBillNo) {
		this.connectionBillNo = connectionBillNo;
	}

	/**
	 * 获取 ******** 车牌号 ***********.
	 *
	 * @return the ******** 车牌号 ***********
	 */
	public String getVehicleNo(){
		return vehicleNo;
	}
	
	/**
	 * 设置 ******** 车牌号 ***********.
	 *
	 * @param vehicleNo the new ******** 车牌号 ***********
	 */
	public void setVehicleNo(String vehicleNo){
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 获取 ******** 线路 ***********.
	 *
	 * @return the ******** 线路 ***********
	 */
	public String getLineNo(){
		return lineNo;
	}
	
	/**
	 * 设置 ******** 线路 ***********.
	 *
	 * @param lineNo the new ******** 线路 ***********
	 */
	public void setLineNo(String lineNo){
		this.lineNo = lineNo;
	}
	
	/**
	 * 获取 ******** 出发部门 ***********.
	 *
	 * @return the ******** 出发部门 ***********
	 */
	public String getOrigOrgName(){
		return origOrgName;
	}
	
	/**
	 * 设置 ******** 出发部门 ***********.
	 *
	 * @param origOrgName the new ******** 出发部门 ***********
	 */
	public void setOrigOrgName(String origOrgName){
		this.origOrgName = origOrgName;
	}
	
	/**
	 * 获取 ******** 到达部门 ***********.
	 *
	 * @return the ******** 到达部门 ***********
	 */
	public String getDestOrgName(){
		return destOrgName;
	}
	
	/**
	 * 设置 ******** 到达部门 ***********.
	 *
	 * @param destOrgName the new ******** 到达部门 ***********
	 */
	public void setDestOrgName(String destOrgName){
		this.destOrgName = destOrgName;
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
	 * 获取 ******** 出发时间（获取类型） ***********.
	 *
	 * @return the ******** 出发时间（获取类型） ***********
	 */
	public String getDepartTime(){
		return departTime;
	}
	
	/**
	 * 设置 ******** 出发时间（获取类型） ***********.
	 *
	 * @param departTime the new ******** 出发时间（获取类型） ***********
	 */
	public void setDepartTime(String departTime){
		this.departTime = departTime;
	}
	
	/**
	 * 获取 ******** 到达时间（获取类型） ***********.
	 *
	 * @return the ******** 到达时间（获取类型） ***********
	 */
	public String getArrivalTime(){
		return arrivalTime;
	}
	
	/**
	 * 设置 ******** 到达时间（获取类型） ***********.
	 *
	 * @param arrivalTime the new ******** 到达时间（获取类型） ***********
	 */
	public void setArrivalTime(String arrivalTime){
		this.arrivalTime = arrivalTime;
	}
	
	/**
	 * 获取 ******** 出发时间 ***********.
	 *
	 * @return the ******** 出发时间 ***********
	 */
	public Date getActualDepartTime(){
		return actualDepartTime;
	}
	
	/**
	 * 设置 ******** 出发时间 ***********.
	 *
	 * @param actualDepartTime the new ******** 出发时间 ***********
	 */
	public void setActualDepartTime(Date actualDepartTime){
		this.actualDepartTime = actualDepartTime;
	}
	
	/**
	 * 获取 ******** 到达时间 ***********.
	 *
	 * @return the ******** 到达时间 ***********
	 */
	public Date getActualArrivalTime(){
		return actualArrivalTime;
	}
	
	/**
	 * 设置 ******** 到达时间 ***********.
	 *
	 * @param actualArrivalTime the new ******** 到达时间 ***********
	 */
	public void setActualArrivalTime(Date actualArrivalTime){
		this.actualArrivalTime = actualArrivalTime;
	}
	
	/**
	 * 获取 ******** 预计到达时间 ***********.
	 *
	 * @return the ******** 预计到达时间 ***********
	 */
	public Date getPlanArrivalTime(){
		return planArrivalTime;
	}
	
	/**
	 * 设置 ******** 预计到达时间 ***********.
	 *
	 * @param planArrivalTime the new ******** 预计到达时间 ***********
	 */
	public void setPlanArrivalTime(Date planArrivalTime){
		this.planArrivalTime = planArrivalTime;
	}
	
	/**
	 * 获取 ******** 到达情况 ***********.
	 *
	 * @return the ******** 到达情况 ***********
	 */
	public String getArrivalStatus(){
		return arrivalStatus;
	}
	
	/**
	 * 设置 ******** 到达情况 ***********.
	 *
	 * @param arrivalStatus the new ******** 到达情况 ***********
	 */
	public void setArrivalStatus(String arrivalStatus){
		this.arrivalStatus = arrivalStatus;
	}
	
	/**
	 * 获取 ******** 证件包情况 ***********.
	 *
	 * @return the ******** 证件包情况 ***********
	 */
	public String getCertificteBagStatus(){
		return certificteBagStatus;
	}
	
	/**
	 * 设置 ******** 证件包情况 ***********.
	 *
	 * @param certificteBagStatus the new ******** 证件包情况 ***********
	 */
	public void setCertificteBagStatus(String certificteBagStatus){
		this.certificteBagStatus = certificteBagStatus;
	}
	
	/**
	 * 获取 ******** 月台分配 ***********.
	 *
	 * @return the ******** 月台分配 ***********
	 */
	public String getPlatformStatus(){
		return platformStatus;
	}
	
	/**
	 * 设置 ******** 月台分配 ***********.
	 *
	 * @param platformStatus the new ******** 月台分配 ***********
	 */
	public void setPlatformStatus(String platformStatus){
		this.platformStatus = platformStatus;
	}
	
	/**
	 * 获取 ******** 未结清金额 ***********.
	 *
	 * @return the ******** 未结清金额 ***********
	 */
	public BigDecimal getFee(){
		return fee;
	}
	
	/**
	 * 设置 ******** 未结清金额 ***********.
	 *
	 * @param fee the new ******** 未结清金额 ***********
	 */
	public void setFee(BigDecimal fee){
		this.fee = fee;
	}
	
	/**
	 * 获取 ******** 装车人 ***********.
	 *
	 * @return the ******** 装车人 ***********
	 */
	public String getLoader(){
		return loader;
	}
	
	/**
	 * 设置 ******** 装车人 ***********.
	 *
	 * @param loader the new ******** 装车人 ***********
	 */
	public void setLoader(String loader){
		this.loader = loader;
	}
	
	/**
	 * 获取 *********车辆状态**********.
	 *
	 * @return the *********车辆状态**********
	 */
	public String getDetailStatus(){
		return detailStatus;
	}
	
	/**
	 * 设置 *********车辆状态**********.
	 *
	 * @param detailStatus the new *********车辆状态**********
	 */
	public void setDetailStatus(String detailStatus){
		this.detailStatus = detailStatus;
	}
	
	/**
	 * 获取 ********任务车辆ID*****.
	 *
	 * @return the ********任务车辆ID*****
	 */
	public String getTruckTaskId(){
		return truckTaskId;
	}
	
	/**
	 * 设置 ********任务车辆ID*****.
	 *
	 * @param truckTaskId the new ********任务车辆ID*****
	 */
	public void setTruckTaskId(String truckTaskId){
		this.truckTaskId = truckTaskId;
	}
	
	/**
	 * 获取 ********任务车辆明细ID*****.
	 *
	 * @return the ********任务车辆明细ID*****
	 */
	public String getTruckTaskDetailId(){
		return truckTaskDetailId;
	}
	
	/**
	 * 设置 ********任务车辆明细ID*****.
	 *
	 * @param truckTaskDetailId the new ********任务车辆明细ID*****
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId){
		this.truckTaskDetailId = truckTaskDetailId;
	}
	
	/**
	 * 获取 ********车辆到达ID*****.
	 *
	 * @return the ********车辆到达ID*****
	 */
	public String getTruckArriveId(){
		return truckArriveId;
	}
	
	/**
	 * 设置 ********车辆到达ID*****.
	 *
	 * @param truckArriveId the new ********车辆到达ID*****
	 */
	public void setTruckArriveId(String truckArriveId){
		this.truckArriveId = truckArriveId;
	}
	
	/**
	 * 获取 ********实际出发类型*****.
	 *
	 * @return the ********实际出发类型*****
	 */
	public String getActualDepartType(){
		return actualDepartType;
	}
	
	/**
	 * 设置 ********实际出发类型*****.
	 *
	 * @param actualDepartType the new ********实际出发类型*****
	 */
	public void setActualDepartType(String actualDepartType){
		this.actualDepartType = actualDepartType;
	}
	
	/**
	 * 获取 ********实际到达类型*****.
	 *
	 * @return the ********实际到达类型*****
	 */
	public String getActualArriveType(){
		return actualArriveType;
	}
	
	/**
	 * 设置 ********实际到达类型*****.
	 *
	 * @param actualArriveType the new ********实际到达类型*****
	 */
	public void setActualArriveType(String actualArriveType){
		this.actualArriveType = actualArriveType;
	}
	
	/**
	 * 获取 ********线路虚拟code*********.
	 *
	 * @return the ********线路虚拟code*********
	 */
	public String getLineVirtualCode(){
		return lineVirtualCode;
	}
	
	/**
	 * 设置 ********线路虚拟code*********.
	 *
	 * @param lineVirtualCode the new ********线路虚拟code*********
	 */
	public void setLineVirtualCode(String lineVirtualCode){
		this.lineVirtualCode = lineVirtualCode;
	}
	
	/**
	 * 获取 ********班次***********.
	 *
	 * @return the ********班次***********
	 */
	public String getFrequencyNo(){
		return frequencyNo;
	}
	
	/**
	 * 设置 ********班次***********.
	 *
	 * @param frequencyNo the new ********班次***********
	 */
	public void setFrequencyNo(String frequencyNo){
		this.frequencyNo = frequencyNo;
	}

	/**
	 * 获取 ********车辆归属类型**.
	 *
	 * @return the ********车辆归属类型**
	 */
	public String getVehicleOwnerType(){
		return vehicleOwnerType;
	}

	/**
	 * 设置 ********车辆归属类型**.
	 *
	 * @param vehicleOwnerType the new ********车辆归属类型**
	 */
	public void setVehicleOwnerType(String vehicleOwnerType){
		this.vehicleOwnerType = vehicleOwnerType;
	}

	/**
	 * 获取 ********司机编号**.
	 *
	 * @return the ********司机编号**
	 */
	public String getDriverCode(){
		return driverCode;
	}

	/**
	 * 设置 ********司机编号**.
	 *
	 * @param driverCode the new ********司机编号**
	 */
	public void setDriverCode(String driverCode){
		this.driverCode = driverCode;
	}

	/**
	 * 获取 *******是否整车**.
	 *
	 * @return the *******是否整车**
	 */
	public String getBeCarLoad(){
		return beCarLoad;
	}

	/**
	 * 设置 *******是否整车**.
	 *
	 * @param beCarLoad the new *******是否整车**
	 */
	public void setBeCarLoad(String beCarLoad){
		this.beCarLoad = beCarLoad;
	}

	/**
	 * 获取 *******车辆归属部门**.
	 *
	 * @return the *******车辆归属部门**
	 */
	public String getTruckOrgCode(){
		return truckOrgCode;
	}

	/**
	 * 设置 *******车辆归属部门**.
	 *
	 * @param truckOrgCode the new *******车辆归属部门**
	 */
	public void setTruckOrgCode(String truckOrgCode){
		this.truckOrgCode = truckOrgCode;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getIsBright() {
		return isBright;
	}

	public void setIsBright(String isBright) {
		this.isBright = isBright;
	}

	public Date getPlanDepartTime() {
		return planDepartTime;
	}

	public void setPlanDepartTime(Date planDepartTime) {
		this.planDepartTime = planDepartTime;
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

	public Date getManualConfirmDepartTime() {
		return manualConfirmDepartTime;
	}

	public void setManualConfirmDepartTime(Date manualConfirmDepartTime) {
		this.manualConfirmDepartTime = manualConfirmDepartTime;
	}

	public Date getPdaDepartTime() {
		return pdaDepartTime;
	}

	public void setPdaDepartTime(Date pdaDepartTime) {
		this.pdaDepartTime = pdaDepartTime;
	}

	public Date getGpsDepartTime() {
		return gpsDepartTime;
	}

	public void setGpsDepartTime(Date gpsDepartTime) {
		this.gpsDepartTime = gpsDepartTime;
	}

	public Date getPdaArriveTime() {
		return pdaArriveTime;
	}

	public void setPdaArriveTime(Date pdaArriveTime) {
		this.pdaArriveTime = pdaArriveTime;
	}

	public Date getGpsArriveTime() {
		return gpsArriveTime;
	}

	public void setGpsArriveTime(Date gpsArriveTime) {
		this.gpsArriveTime = gpsArriveTime;
	}

	public String getBillNos() {
		return billNos;
	}

	public void setBillNos(String billNos) {
		this.billNos = billNos;
	}

	public Date getDepartTime2() {
		return departTime2;
	}

	public void setDepartTime2(Date departTime2) {
		this.departTime2 = departTime2;
	}

	/**
	 * @return the arrCheckTime
	 */
	public Date getArrCheckTime() {
		return arrCheckTime;
	}

	/**
	 * @param arrCheckTime the arrCheckTime to set
	 */
	public void setArrCheckTime(Date arrCheckTime) {
		this.arrCheckTime = arrCheckTime;
	}

	/**
	 * @return the checkType
	 */
	public String getCheckType() {
		return checkType;
	}

	/**
	 * @param checkType the checkType to set
	 */
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	/**
	 * @return the platformCode
	 */
	public String getPlatformCode() {
		return platformCode;
	}

	/**
	 * @param platformCode the platformCode to set
	 */
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getChargingAssmebleNo() {
		return chargingAssmebleNo;
	}

	public void setChargingAssmebleNo(String chargingAssmebleNo) {
		this.chargingAssmebleNo = chargingAssmebleNo;
	}
	
	
}