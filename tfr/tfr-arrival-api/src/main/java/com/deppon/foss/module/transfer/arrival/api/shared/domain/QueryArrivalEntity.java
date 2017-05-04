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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/shared/domain/QueryArrivalEntity.java
 *  
 *  FILE NAME          :QueryArrivalEntity.java
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

import java.util.Date;
import java.util.List;
import com.deppon.foss.framework.entity.BaseEntity;

/**
 * Entity开发规范 1.必须继承com.deppon.foss.framework.entity.BaseEntity 2.类名必须以Entity结尾
 * 3.必须生成serialVersionUID 4.建议属性名称与数据库字段命名规则一致
 */
public class QueryArrivalEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/********** 单据编号 ************/
	private String billNo;
	/********** 线路 ************/
	private String lineNo;
	/********** 线路 ************/
	private String lineName;
	/********** 车牌号 ************/
	private String vehicleNo;
	/********** 证件包情况 ************/
	private String certificaterBagStatus;
	/********** 时间类型 ************/
	private String timeType;
	/********** 起始时间 ************/
	private Date startTime;
	/********** 结束时间 ************/
	private Date endTime;
//	/********** 起始时间 （转化后）************/
//	private String startDate;
//	/********** 结束时间（转化后） ************/
//	private String endDate;
	
	/********** 车辆归属部门 ************/
	private String truckOrgCode;
	/********** 月台分配情况 ************/
	private String platformStatus;
	/********** 车辆业务类型 ************/
	private String businessType;
	/********** 到达情况 ************/
	private String arrivalStatus;
	/********** 出发部门 ************/
	private String origOrgCode;
	/********** 出发部门(复数) ************/
	private List<String> origOrgCodes;
	/********** 到达部门 ************/
	private String destOrgCode;
	/********** 到达部门 （复数）************/
	private List<String> destOrgCodes;
	/********** 结算情况 ************/
	private String clearingStatus;
	/********** 车辆归属类型 ************/
	private String vehicleOwnerType;
	/********** 司机编码 ************/
	private String driverCode;
	/**********线路虚拟code**********/
	private String lineVirtualCode;
	/**********班次************/
	private String frequencyNo;
	/**********当前用户所设置的部门************/
	private String currentOrgCode;
	/**********当前用户所设置的部门(复数)************/
	private List<String> currentOrgCodes;
	/**********任务明细ID(复数)************/
	private List<String> ids;
	/***接驳交接单号***/
	private String connectionBillNo;
	/***交接单号**/
	private String handoverNo;
	
	
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

	public List<String> getCurrentOrgCodes() {
		return currentOrgCodes;
	}

	public void setCurrentOrgCodes(List<String> currentOrgCodes) {
		this.currentOrgCodes = currentOrgCodes;
	}

	/**
	 * 
	 */
	private Date planArrivalTime;
	
	/**
	 * 获取 ******** 单据编号 ***********.
	 *
	 * @return the ******** 单据编号 ***********
	 */
	public String getBillNo(){
		return billNo;
	}
	
	/**
	 * 获取 ******** 线路 ***********.
	 *
	 * @return the ******** 线路 ***********
	 */
	public String getLineName(){
		return lineName;
	}
	
	/**
	 * 设置 ******** 线路 ***********.
	 *
	 * @param lineName the new ******** 线路 ***********
	 */
	public void setLineName(String lineName){
		this.lineName = lineName;
	}
	
	/**
	 * 设置 ******** 单据编号 ***********.
	 *
	 * @param billNo the new ******** 单据编号 ***********
	 */
	public void setBillNo(String billNo){
		this.billNo = billNo;
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
	 * 获取 ******** 证件包情况 ***********.
	 *
	 * @return the ******** 证件包情况 ***********
	 */
	public String getCertificaterBagStatus(){
		return certificaterBagStatus;
	}
	
	/**
	 * 设置 ******** 证件包情况 ***********.
	 *
	 * @param certificaterBagStatus the new ******** 证件包情况 ***********
	 */
	public void setCertificaterBagStatus(String certificaterBagStatus){
		this.certificaterBagStatus = certificaterBagStatus;
	}
	
	/**
	 * 获取 ******** 时间类型 ***********.
	 *
	 * @return the ******** 时间类型 ***********
	 */
	public String getTimeType(){
		return timeType;
	}
	
	/**
	 * 设置 ******** 时间类型 ***********.
	 *
	 * @param timeType the new ******** 时间类型 ***********
	 */
	public void setTimeType(String timeType){
		this.timeType = timeType;
	}

	/**
	 * 获取 ******** 起始时间 ***********.
	 *
	 * @return the ******** 起始时间 ***********
	 */
	public Date getStartTime(){
		return startTime;
	}
	
	/**
	 * 设置 ******** 起始时间 ***********.
	 *
	 * @param startTime the new ******** 起始时间 ***********
	 */
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	/**
	 * 获取 ******** 结束时间 ***********.
	 *
	 * @return the ******** 结束时间 ***********
	 */
	public Date getEndTime(){
		return endTime;
	}
	
	/**
	 * 设置 ******** 结束时间 ***********.
	 *
	 * @param endTime the new ******** 结束时间 ***********
	 */
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	
	/**
	 * 获取 ******** 车辆归属部门 ***********.
	 *
	 * @return the ******** 车辆归属部门 ***********
	 */
	public String getTruckOrgCode(){
		return truckOrgCode;
	}
	
	/**
	 * 设置 ******** 车辆归属部门 ***********.
	 *
	 * @param truckOrgCode the new ******** 车辆归属部门 ***********
	 */
	public void setTruckOrgCode(String truckOrgCode){
		this.truckOrgCode = truckOrgCode;
	}
	
	/**
	 * 获取 ******** 月台分配情况 ***********.
	 *
	 * @return the ******** 月台分配情况 ***********
	 */
	public String getPlatformStatus(){
		return platformStatus;
	}
	
	/**
	 * 设置 ******** 月台分配情况 ***********.
	 *
	 * @param platformStatus the new ******** 月台分配情况 ***********
	 */
	public void setPlatformStatus(String platformStatus){
		this.platformStatus = platformStatus;
	}
	
	/**
	 * 获取 ******** 车辆业务类型 ***********.
	 *
	 * @return the ******** 车辆业务类型 ***********
	 */
	public String getBusinessType(){
		return businessType;
	}
	
	/**
	 * 设置 ******** 车辆业务类型 ***********.
	 *
	 * @param businessType the new ******** 车辆业务类型 ***********
	 */
	public void setBusinessType(String businessType){
		this.businessType = businessType;
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
	 * 获取 ******** 结算情况 ***********.
	 *
	 * @return the ******** 结算情况 ***********
	 */
	public String getClearingStatus(){
		return clearingStatus;
	}
	
	/**
	 * 设置 ******** 结算情况 ***********.
	 *
	 * @param clearingStatus the new ******** 结算情况 ***********
	 */
	public void setClearingStatus(String clearingStatus){
		this.clearingStatus = clearingStatus;
	}
	

	
	/**
	 * 获取 ******** 车辆归属类型 ***********.
	 *
	 * @return the ******** 车辆归属类型 ***********
	 */
	public String getVehicleOwnerType(){
		return vehicleOwnerType;
	}

	/**
	 * 设置 ******** 车辆归属类型 ***********.
	 *
	 * @param vehicleOwnerType the new ******** 车辆归属类型 ***********
	 */
	public void setVehicleOwnerType(String vehicleOwnerType){
		this.vehicleOwnerType = vehicleOwnerType;
	}

	/**
	 * 获取 ******** 司机编码 ***********.
	 *
	 * @return the ******** 司机编码 ***********
	 */
	public String getDriverCode(){
		return driverCode;
	}
	
	/**
	 * 设置 ******** 司机编码 ***********.
	 *
	 * @param driverCode the new ******** 司机编码 ***********
	 */
	public void setDriverCode(String driverCode){
		this.driverCode = driverCode;
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
	 * 
	 *
	 * @return 
	 */
	public Date getPlanArrivalTime(){
		return planArrivalTime;
	}
	
	/**
	 * 获取 ********当前用户所设置的部门***********.
	 *
	 * @return the ********当前用户所设置的部门***********
	 */
	public String getCurrentOrgCode(){
		return currentOrgCode;
	}

	/**
	 * 设置 ********当前用户所设置的部门***********.
	 *
	 * @param currentOrgCode the new ********当前用户所设置的部门***********
	 */
	public void setCurrentOrgCode(String currentOrgCode){
		this.currentOrgCode = currentOrgCode;
	}

	/**
	 * 
	 *
	 * @param planArrivalTime 
	 */
	public void setPlanArrivalTime(Date planArrivalTime){
		this.planArrivalTime = planArrivalTime;
	}

	public List<String> getOrigOrgCodes() {
		return origOrgCodes;
	}

	public void setOrigOrgCodes(List<String> origOrgCodes) {
		this.origOrgCodes = origOrgCodes;
	}

	public List<String> getDestOrgCodes() {
		return destOrgCodes;
	}

	public void setDestOrgCodes(List<String> destOrgCodes) {
		this.destOrgCodes = destOrgCodes;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}